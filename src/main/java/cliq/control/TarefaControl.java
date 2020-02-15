package cliq.control;

import cliq.dao.CategoriaDao;
import cliq.dao.ChamadoDao;
import cliq.dao.EquipeDao;
import cliq.dao.EventoDao;
import cliq.dao.PessoaDao;
import cliq.dao.SLADao;
import cliq.dao.TarefaDao;
import cliq.entity.Chamado;
import cliq.entity.Evento;
import cliq.entity.SLA;
import cliq.entity.Tarefa;
import cliq.type.Pendencia;
import cliq.type.Situacao;
import gate.base.Control;
import gate.constraint.Constraints;
import gate.entity.Role;
import gate.error.AppException;
import gate.error.NotFoundException;
import gate.sql.Link;
import gate.type.ID;
import gate.type.collections.StringSet;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class TarefaControl extends Control
{

	@Inject
	protected Event<Chamado> event;

	public List<Tarefa> search(Role role)
	{
		try (TarefaDao dao = new TarefaDao())
		{
			return dao.search(role);
		}
	}

	public Tarefa select(ID id) throws NotFoundException
	{
		try (TarefaDao dao = new TarefaDao())
		{
			return dao.select(id);
		}
	}

	public void insert(Tarefa model) throws AppException
	{
		Constraints.validate(model, "inicio", "periodo.valor", "periodo.unidade", "categoria.id", "nome", "descricao");
		if (model.getPeriodo().getValor() == null && model.getPeriodo().getUnidade() != null)
			throw new AppException("Tentativa de cadastrar período sem informar valor.");

		try (TarefaDao dao = new TarefaDao())
		{
			dao.insert(model);
		}
	}

	public void update(Tarefa model) throws AppException
	{
		Constraints.validate(model, "inicio", "periodo.valor", "periodo.unidade", "categoria.id", "nome", "descricao");
		if (model.getPeriodo().getValor() == null && model.getPeriodo().getUnidade() != null)
			throw new AppException("Tentativa de cadastrar período sem informar valor.");

		try (TarefaDao dao = new TarefaDao())
		{
			dao.update(model);
		}
	}

	public void delete(Tarefa model) throws AppException
	{
		try (TarefaDao dao = new TarefaDao())
		{
			dao.delete(model);
		}
	}

	public void createChamados() throws AppException
	{
		try (Link link = new Link();
			SLADao SLADao = new SLADao(link);
			TarefaDao tarefaDao = new TarefaDao(link);
			EquipeDao equipeDao = new EquipeDao(link);
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link);
			PessoaDao pessoaDao = new PessoaDao(link);
			CategoriaDao categoriaDao = new CategoriaDao(link))
		{
			for (Tarefa tarefa : tarefaDao.search())
			{
				link.beginTran();

				tarefa.setCategoria(categoriaDao.select(tarefa.getCategoria().getId()));

				Chamado chamado = new Chamado()
					.setDocumentacao(false)
					.setTitulo(tarefa.getNome())
					.setData(tarefa.getInicio())
					.setOrigem(tarefa.getOrigem())
					.setSituacao(Situacao.PENDENTE)
					.setPendencia(Pendencia.NENHUMA)
					.setNotificados(new StringSet())
					.setAtendente(tarefa.getAtendente())
					.setDescricao(tarefa.getDescricao())
					.setNivel(tarefa.getCategoria().getNivel())
					.setProjeto(tarefa.getCategoria().getProjeto())
					.setLocalizacao(tarefa.getCategoria().getRole())
					.setSigiloso(tarefa.getCategoria().getSigilosa())
					.setChecklist(tarefa.getCategoria().getChecklist())
					.setPrioridade(tarefa.getCategoria().getPrioridade())
					.setComplexidade(tarefa.getCategoria().getComplexidade())
					.setEquipeAprovadora(tarefa.getCategoria().getEquipeAprovadora())
					.setPessoaAprovadora(tarefa.getCategoria().getPessoaAprovadora())
					.setCategoria(tarefa.getCategoria().getTemporaria() ? null : tarefa.getCategoria());

				if (Boolean.TRUE.equals(tarefa.getCategoria().getTriagem()))
					chamado.setPendencia(Pendencia.TRIAGEM);

				SLA sla = SLADao.search().stream().filter(e -> e.matches(chamado)).findFirst().orElse(null);
				if (sla != null)
				{
					chamado.setPrazoResposta(sla.getPrazoResposta(chamado.getData()));
					chamado.setPrazoSolucao(sla.getPrazoSolucao(chamado.getData()));
				}

				chamadoDao.insert(chamado);

				chamadoDao.update(chamado.setEvento(eventoDao
					.insert(new Evento()
						.setChamado(chamado)
						.setData(chamado.getData())
						.setTipo(Evento.Tipo.CRIACAO)
						.setDescricao("Chamado criado pelo CliQ"))));

				if (chamado.getPessoaAprovadora().getId() != null)
				{

					chamadoDao.update(chamado
						.setPendencia(Pendencia.APROVACAO)
						.setEvento(eventoDao
							.insert(new Evento()
								.setChamado(chamado)
								.setTipo(Evento.Tipo.APROVACAO)
								.setData(chamado.getEvento().getData().plusSeconds(1))
								.setDescricao(String.format("Chamado necessita da aprovação de %s para ser concluído.",
									pessoaDao.select(chamado.getPessoaAprovadora().getId()).getName())))));

				} else if (chamado.getEquipeAprovadora().getId() != null)
				{
					chamadoDao.update(chamado
						.setPendencia(Pendencia.APROVACAO)
						.setEvento(eventoDao
							.insert(new Evento()
								.setChamado(chamado)
								.setTipo(Evento.Tipo.APROVACAO)
								.setData(chamado.getEvento().getData().plusSeconds(1))
								.setDescricao(String.format("Chamado necessita da aprovação de %s para ser concluído.",
									equipeDao.select(chamado.getEquipeAprovadora().getId()).getName())))));
				}

				if (sla != null)
				{

					eventoDao.insert(new Evento()
						.setChamado(chamado)
						.setTipo(Evento.Tipo.PRAZO_RESPOSTA)
						.setData(chamado.getEvento().getData().plusSeconds(1))
						.setDescricao(String.format("Prazo de resposta ajustado automaticamente para %s pela política de SLA %s.",
							chamado.getPrazoResposta().toString(), sla.getNome())));

					chamadoDao.update(chamado
						.setEvento(eventoDao
							.insert(eventoDao.insert(new Evento()
								.setChamado(chamado)
								.setTipo(Evento.Tipo.PRAZO_SOLUCAO)
								.setData(chamado.getEvento().getData().plusSeconds(1))
								.setDescricao(String.format("Prazo de solução ajustado automaticamente para %s pela política de SLA %s.",
									chamado.getPrazoSolucao().toString(), sla.getNome()))))));
				}

				if (tarefa.getPeriodo().getValor() == null || tarefa.getPeriodo().getUnidade() == null)
					tarefaDao.delete(tarefa);
				else
					tarefaDao.update(tarefa.setInicio(tarefa.getPeriodo().addTo(tarefa.getInicio())));

				event.fire(chamado);
				link.commit();
			}

		} catch (RuntimeException ex)
		{
			throw new AppException(ex, "Erro ao criar chamados");
		}
	}
}
