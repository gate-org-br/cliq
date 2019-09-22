package cliq.control;

import cliq.dao.AnexoDao;
import cliq.dao.CategoriaDao;
import cliq.dao.ChamadoDao;
import cliq.dao.CompartilhamentoDao;
import cliq.dao.EquipeDao;
import cliq.dao.EventoDao;
import cliq.dao.PessoaDao;
import cliq.dao.SLADao;
import cliq.entity.Anexo;
import cliq.entity.Chamado;
import cliq.entity.Evento;
import cliq.entity.SLA;
import cliq.type.Checklist.Checkitem;
import cliq.type.Nota;
import cliq.type.Pendencia;
import cliq.type.Situacao;
import gate.base.Control;
import gate.constraint.Constraints;
import gate.entity.User;
import gate.error.AppException;
import gate.sql.Link;
import gate.type.Form;
import gate.type.ID;
import gate.type.collections.StringSet;
import gate.util.Comparer;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public abstract class ChamadoControl extends Control
{

	@Inject
	protected Event<Chamado> event;

	public Chamado select(ID id) throws AppException
	{
		try (ChamadoDao dao = new ChamadoDao())
		{
			dao.beginTran();
			Chamado chamado = dao.select(id);
			chamado.getNotificados().add(getUser().getId().toString());
			dao.update(chamado);
			dao.commit();
			return chamado;
		}
	}

	public void insert(Chamado... chamados) throws AppException
	{
		try (Link link = new Link();
			SLADao salDao = new SLADao(link);
			AnexoDao anexoDao = new AnexoDao(link);
			EquipeDao equipeDao = new EquipeDao(link);
			PessoaDao pessoaDao = new PessoaDao(link);
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link);
			CategoriaDao categoriaDao = new CategoriaDao(link))
		{
			link.beginTran();

			for (Chamado chamado : chamados)
			{
				chamado.getFormulario().validate();
				Constraints.validate(chamado, "categoria.id");

				if (chamado.getOrigem().getId() != null && !getUser().getRole().contains(chamado.getOrigem()))
					throw new AppException("Tentativa de criar chamado com origem inválida");

				if (chamado.getSolicitante().getId() != null && chamado.getContato().getId() != null)
					throw new AppException("Um chamado não pode ter solicitante e contato ao mesmo tempo");

				if (chamado.getPrazoResposta() != null
					&& chamado.getPrazoSolucao() != null
					&& Comparer.gt(chamado.getPrazoResposta(), chamado.getPrazoSolucao()))
					throw new AppException("O prazo de início deve vir antes do prazo de conclusão");

				if (chamado.getSolicitante().getId() != null)
					chamado.setSolicitante(pessoaDao.select(chamado.getSolicitante().getId()));

				if (chamado.getOrigem().getId() == null)
					chamado.setOrigem(chamado.getSolicitante().getRole());

				chamado.setCategoria(categoriaDao.select(chamado.getCategoria().getId()));

				if (chamado.getCategoria().getCampos().contains("#titulo") && chamado.getTitulo() == null)
					throw new AppException("Entre com o título do chamado");
				if (chamado.getCategoria().getCampos().contains("#prioridade") && chamado.getPrioridade() == null)
					throw new AppException("Entre com a prioridade do chamado");
				if (chamado.getCategoria().getCampos().contains("#complexidade") && chamado.getComplexidade() == null)
					throw new AppException("Entre com a complexidade do chamado");
				if (chamado.getCategoria().getCampos().contains("#prazoResposta") && chamado.getPrazoResposta() == null)
					throw new AppException("Entre com o prazo de resposta do chamado");
				if (chamado.getCategoria().getCampos().contains("#prazoSolucao") && chamado.getPrazoSolucao() == null)
					throw new AppException("Entre com o prazo de solução do chamado");
				if (chamado.getCategoria().getCampos().contains("#origem") && chamado.getOrigem().getId() == null)
					throw new AppException("Entre com a origem do chamado");
				if (chamado.getCategoria().getCampos().contains("#descricao") && chamado.getDescricao() == null)
					throw new AppException("Entre com a descrição do chamado");
				if (chamado.getCategoria().getCampos().contains("#arquivo") && chamado.getAnexo().getArquivo() == null)
					throw new AppException("Entre com o anexo do chamado");

				chamado.setProjeto(false)
					.setDocumentacao(false)
					.setData(LocalDateTime.now())
					.setSituacao(Situacao.PENDENTE)
					.setTipo(chamado.getCategoria())
					.setNivel(chamado.getCategoria().getNivel())
					.setLocalizacao(chamado.getCategoria().getRole())
					.setSigiloso(chamado.getCategoria().getSigilosa())
					.setChecklist(chamado.getCategoria().getChecklist())
					.setPrioridade(chamado.getCategoria().getPrioridade())
					.setComplexidade(chamado.getCategoria().getComplexidade())
					.setNotificados(getUser() != null ? new StringSet(getUser().getId().toString()) : new StringSet())
					.setPendencia(Boolean.TRUE.equals(chamado.getCategoria().getTriagem()) ? Pendencia.TRIAGEM : Pendencia.NENHUMA);

				if (chamado.getTitulo() == null)
					chamado.setTitulo(chamado.getCategoria().getNome());

				if (chamado.getCategoria().getPessoaAprovadora().getId() != null)
					chamado.setPessoaAprovadora(pessoaDao.select(chamado.getCategoria().getPessoaAprovadora().getId()));
				else if (chamado.getCategoria().getEquipeAprovadora().getId() != null)
					chamado.setEquipeAprovadora(equipeDao.select(chamado.getCategoria().getEquipeAprovadora().getId()));

				if (chamado.getCategoria().getPessoaHomologadora().getId() != null)
					chamado.setPessoaHomologadora(pessoaDao.select(chamado.getCategoria().getPessoaHomologadora().getId()));
				else if (chamado.getCategoria().getEquipeHomologadora().getId() != null)
					chamado.setEquipeAprovadora(equipeDao.select(chamado.getCategoria().getEquipeHomologadora().getId()));

				if (getUser() != null && chamado.getSolicitante().getRole().getId() != null)
					chamado.getSolicitante().setRole(getUser().getRole().getRoot().select(chamado.getSolicitante().getRole().getId()));

				if (chamado.getAnexo().getArquivo() != null)
					anexoDao.insert(chamado.getAnexo());

				chamadoDao.insert(chamado);

				chamadoDao.update(chamado
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setData(chamado.getData())
							.setTipo(Evento.Tipo.CRIACAO)
							.setDescricao(getUser() != null ? "Chamado criado por " + getUser() : "Chamado criado"))));

				if (chamado.getPessoaAprovadora().getId() != null)
					chamadoDao.update(chamado
						.setPendencia(Pendencia.APROVACAO)
						.setEvento(eventoDao
							.insert(new Evento()
								.setChamado(chamado)
								.setTipo(Evento.Tipo.APROVACAO)
								.setData(chamado.getEvento().getData().plusSeconds(1))
								.setDescricao("Chamado necessita da aprovação de " + chamado.getPessoaAprovadora() + " para ser executado"))));
				else if (chamado.getEquipeAprovadora().getId() != null)
					chamadoDao.update(chamado
						.setPendencia(Pendencia.APROVACAO)
						.setEvento(eventoDao.insert(new Evento()
							.setChamado(chamado)
							.setTipo(Evento.Tipo.APROVACAO)
							.setData(chamado.getEvento().getData().plusSeconds(1))
							.setDescricao("Chamado necessita da aprovação de " + chamado.getEquipeAprovadora().getName() + " para ser executado"))));

				SLA sla = salDao.search().stream()
					.filter(e -> e.matches(chamado))
					.findFirst()
					.orElse(null);
				if (sla != null)
					chamadoDao
						.update(chamado
							.setPrazoResposta(sla.getPrazoResposta(chamado.getData()))
							.setEvento(eventoDao
								.insert(new Evento()
									.setChamado(chamado)
									.setTipo(Evento.Tipo.PRAZO_RESPOSTA)
									.setData(chamado.getEvento().getData().plusSeconds(1))
									.setDescricao("Prazo de resposta ajustado automaticamente para " + chamado.getPrazoResposta() + " pela política de SLA " + sla.getNome())))
							.setPrazoSolucao(sla.getPrazoSolucao(chamado.getData()))
							.setEvento(eventoDao
								.insert(new Evento()
									.setChamado(chamado)
									.setTipo(Evento.Tipo.PRAZO_SOLUCAO)
									.setData(chamado.getEvento().getData().plusSeconds(1))
									.setDescricao("Prazo de solução ajustado automaticamente para " + chamado.getPrazoSolucao() + " pela política de SLA " + sla.getNome()))));

				if (chamado.getCategoria().getAtribuir().getId() != null)
				{
					if (chamado.getLocalizacao().equals(chamado.getCategoria().getAtribuir().getRole()))
						chamadoDao.update(chamado.
							setAtendente(chamado.getCategoria().getAtribuir())
							.setEvento(eventoDao
								.insert(new Evento()
									.setChamado(chamado)
									.setTipo(Evento.Tipo.ATRIBUICAO)
									.setData(chamado.getEvento().getData().plusSeconds(1))
									.setDescricao("Chamado atribuído automaticamente para " + chamado.getCategoria().getAtribuir().getName()))));
					else

						chamadoDao.update(chamado.setEvento(eventoDao.insert(new Evento()
							.setChamado(chamado)
							.setTipo(Evento.Tipo.COMENTARIO_INTERNO)
							.setData(chamado.getEvento().getData().plusSeconds(1))
							.setDescricao("Tentativa e atribuir chamado automaticamente para usuário de outro setor"))));
				} else if (chamado.getCategoria().getEncaminhar().getId() != null)
				{
					if (categoriaDao.exists(chamado.getCategoria().getEncaminhar()))
					{
						chamado.setLocalizacao(chamado.getCategoria().getEncaminhar());

						chamado.setCategoria(categoriaDao.select(chamado.getLocalizacao(), chamado.getCategoria().getNome()));
						if (chamado.getCategoria().getId() != null)
							chamado.setPrioridade(chamado.getCategoria().getPrioridade())
								.setNivel(chamado.getCategoria().getNivel())
								.setChecklist(chamado.getCategoria().getChecklist())
								.setComplexidade(chamado.getCategoria().getComplexidade());

						chamadoDao.update(chamado
							.setEvento(eventoDao
								.insert(new Evento()
									.setChamado(chamado)
									.setTipo(Evento.Tipo.ENCAMINHAMENTO)
									.setData(chamado.getEvento().getData().plusSeconds(1))
									.setDescricao(String.format("Chamado encaminhado automaticamente do setor %s para o setor %s",
										getUser().getRole().getRoot().select(chamado.getOrigem().getId()),
										getUser().getRole().getRoot().select(chamado.getLocalizacao().getId()))))));
					} else
						chamadoDao.update(chamado
							.setEvento(eventoDao
								.insert(new Evento()
									.setChamado(chamado)
									.setTipo(Evento.Tipo.COMENTARIO_INTERNO)
									.setData(chamado.getEvento().getData().plusSeconds(1))
									.setDescricao("Tentativa e encaminhar chamado automaticamente para setor sem categoria"))));
				}
			}

			link.commit();

			for (Chamado chamado : chamados)
				event.fire(chamado);
		}
	}

	public void update(ID id, String titulo, Form formulario, String descricao, Anexo anexo) throws AppException
	{
		if (titulo == null || titulo.isEmpty())
			throw new AppException("O campo título é obrigatório");

		if (formulario != null)
			formulario.validate();

		try (Link link = new Link();
			ChamadoDao dao = new ChamadoDao(link);
			AnexoDao anexoDao = new AnexoDao(link))
		{
			link.beginTran();
			Chamado chamado = dao.select(id);

			if (!getUser().equals(chamado.getSolicitante()))
				throw new AppException("Tentativa de alterar chamado de outro solicitante");

			if (anexo != null && anexo.getArquivo() != null)
			{
				if (chamado.getAnexo().getId() != null)
					anexoDao.delete(chamado.getAnexo());
				anexoDao.insert(anexo);
			}

			dao.update(id, titulo, formulario, descricao, anexo);
			link.commit();
		}
	}

	public void concluir(Chamado chamado, User user, String conclusao, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link);
			PessoaDao pessoaDao = new PessoaDao(link))
		{
			link.beginTran();

			chamado = chamadoDao.select(chamado.getId());

			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Somente chamados pendentes podem ser concluídos");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());
			if (!getUser().getRole().contains(chamado.getLocalizacao()))
				throw new AppException("Apenas o técnicos lotados onde um chamado está localizado podem concluí-lo");

			if (chamado.getCategoria().getId() == null)
				throw new AppException("Selecione uma categoria antes de concluir o chamado");
			if (Boolean.TRUE.equals(chamado.getCategoria().getTemporaria()))
				throw new AppException("Categorias temporárias podem ser utilizadas para criar chamados, mas não para concluí-los");
			for (Checkitem checkitem : chamado.getChecklist())
				if (!Boolean.TRUE.equals(checkitem.getExecutada()))
					throw new AppException("O chamado possui tarefas pendentes que devem ser concluídas antes que ele possa ser concluído");
			if (!chamado.getCategoria().getConclusoes().isEmpty()
				&& (conclusao == null || !chamado.getCategoria().getConclusoes().contains(conclusao)))
				throw new AppException("Selecione um status de conclusão válido para o chamado");

			if (chamado.getCategoria().getPessoaHomologadora().getId() != null)
				chamadoDao
					.update(chamado
						.setPendencia(Pendencia.HOMOLOGACAO)
						.setPessoaHomologadora(chamado.getCategoria().getPessoaHomologadora())
						.setAtendente(user != null && user.getId() != null ? pessoaDao.select(user.getId()) : getUser())
						.setEvento(eventoDao
							.insert(new Evento()
								.setUser(getUser())
								.setChamado(chamado)
								.setObservacoes(observacoes)
								.setData(LocalDateTime.now())
								.setAnexo(anexoDao.insert(anexo))
								.setTipo(Evento.Tipo.HOMOLOGACAO)
								.setDescricao("Chamado necessita da homologação de " + chamado.getCategoria().getPessoaHomologadora().getName() + " para ser concluído"))));
			else if (chamado.getCategoria().getEquipeHomologadora().getId() != null)
				chamadoDao
					.update(chamado.setPendencia(Pendencia.HOMOLOGACAO)
						.setEquipeHomologadora(chamado.getCategoria().getEquipeHomologadora())
						.setAtendente(user != null && user.getId() != null ? pessoaDao.select(user.getId()) : getUser())
						.setEvento(eventoDao
							.insert(new Evento()
								.setUser(getUser())
								.setChamado(chamado)
								.setObservacoes(observacoes)
								.setAnexo(anexoDao.insert(anexo))
								.setTipo(Evento.Tipo.HOMOLOGACAO)
								.setData(chamado.getEvento().getData().plusSeconds(1))
								.setDescricao("Chamado necessita da homologação de " + chamado.getCategoria().getEquipeHomologadora().getName() + " para ser concluído"))));
			else if (Boolean.TRUE.equals(chamado.getCategoria().getFeedback())
				&& chamado.getSolicitante().getId() != null
				&& !chamado.getSolicitante().equals(getUser()))
				chamadoDao
					.update(chamado
						.setPendencia(Pendencia.FEEDBACK)
						.setNotificados(new StringSet(getUser().getId().toString()))
						.setAtendente(user != null && user.getId() != null ? pessoaDao.select(user.getId()) : getUser())
						.setEvento(eventoDao
							.insert(new Evento()
								.setUser(getUser())
								.setChamado(chamado)
								.setStatus(conclusao)
								.setObservacoes(observacoes)
								.setData(LocalDateTime.now())
								.setTipo(Evento.Tipo.FEEDBACK)
								.setAnexo(anexoDao.insert(anexo))
								.setDescricao("Chamado enviado para feedback do solicitante por " + getUser().getName()))));
			else
				chamadoDao
					.update(chamado
						.setSolucao(LocalDateTime.now())
						.setSituacao(Situacao.CONCLUIDO)
						.setNotificados(new StringSet(getUser().getId().toString()))
						.setPendencia(chamado.getCategoria().getAvaliacao() ? Pendencia.AVALIACAO : Pendencia.NENHUMA)
						.setAtendente(user != null && user.getId() != null ? pessoaDao.select(user.getId()) : getUser())
						.setEvento(eventoDao
							.insert(new Evento()
								.setUser(getUser())
								.setChamado(chamado)
								.setStatus(conclusao)
								.setObservacoes(observacoes)
								.setData(chamado.getSolucao())
								.setTipo(Evento.Tipo.CONCLUSAO)
								.setAnexo(anexoDao.insert(anexo))
								.setDescricao("Chamado concluído por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	public void cancelar(Chamado chamado, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link);
			CompartilhamentoDao compartilhamentoDao = new CompartilhamentoDao(link))
		{
			link.beginTran();

			chamado = chamadoDao.select(chamado.getId());

			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Apenas chamados pendentes podem ser cancelados");

			if (!getUser().equals(chamado.getSolicitante())
				&& !getUser().getRole().contains(chamado.getLocalizacao())
				&& !compartilhamentoDao.exists(chamado, getUser())
				&& !compartilhamentoDao.exists(chamado, getUser().getRole()))
				throw new AppException("Você não possui permissão para cancelar o chamado");

			chamadoDao
				.update(chamado
					.setFeedback(null)
					.setRetomada(null)
					.setAtendente(null)
					.setEquipeAprovadora(null)
					.setPessoaAprovadora(null)
					.setEquipeHomologadora(null)
					.setPessoaHomologadora(null)
					.setPendencia(Pendencia.NENHUMA)
					.setSolucao(LocalDateTime.now())
					.setSituacao(Situacao.CANCELADO)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(chamado.getSolucao())
							.setAnexo(anexoDao.insert(anexo))
							.setTipo(Evento.Tipo.CANCELAMENTO)
							.setDescricao("Chamado cancelado por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	public void reabrir(Chamado chamado, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link);
			CompartilhamentoDao compartilhamentoDao = new CompartilhamentoDao(link))
		{
			link.beginTran();

			chamado = chamadoDao.select(chamado.getId());

			if (chamado.getSituacao() == Situacao.PENDENTE)
				throw new AppException("Somente chamados concluídos ou cancelados podem ser reabertos");
			if (chamado.getEventos().stream()
				.filter(e -> e.getTipo() == Evento.Tipo.CONCLUSAO || e.getTipo() == Evento.Tipo.CANCELAMENTO)
				.map(e -> e.getData()).allMatch(e -> LocalDateTime.now().until(e, ChronoUnit.DAYS) > 10))
				throw new AppException("Encerrado o prazo de 10 dias para reabertura do chamado");

			if (!getUser().equals(chamado.getSolicitante())
				&& !getUser().getRole().contains(chamado.getLocalizacao())
				&& !compartilhamentoDao.exists(chamado, getUser())
				&& !compartilhamentoDao.exists(chamado, getUser().getRole()))
				throw new AppException("Você não possui permissão para reabrir este chamado");

			eventoDao.update(chamado, Evento.Tipo.CONCLUSAO, Evento.Tipo.CONCLUSAO_REJEITADA);
			eventoDao.update(chamado, Evento.Tipo.CANCELAMENTO, Evento.Tipo.CANCELAMENTO_REJEITADO);

			chamadoDao
				.update(chamado
					.setNota(null)
					.setSolucao(null)
					.setResposta(null)
					.setAtendente(null)
					.setAtendimento(null)
					.setSituacao(Situacao.PENDENTE)
					.setPendencia(Pendencia.NENHUMA)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setTipo(Evento.Tipo.REABERTURA)
							.setAnexo(anexoDao.insert(anexo))
							.setDescricao("Chamado reaberto por %s" + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	public void definirSigilo(ID id, Boolean sigiloso) throws AppException
	{
		try (Link link = new Link();
			ChamadoDao chamadoDao = new ChamadoDao(link);
			CompartilhamentoDao compartilhamentoDao = new CompartilhamentoDao(link))
		{
			link.beginTran();

			Chamado chamado = chamadoDao.select(id);

			if (!getUser().equals(chamado.getSolicitante())
				&& !getUser().getRole().contains(chamado.getLocalizacao())
				&& !compartilhamentoDao.exists(chamado, getUser())
				&& !compartilhamentoDao.exists(chamado, getUser().getRole()))
				throw new AppException("Você não possui permissão para definir o sigilo do chamado");

			chamadoDao.update(chamado
				.setSigiloso(sigiloso));

			link.commit();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Comentário
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void comentarioSimples(Chamado chamado, String comentario, Anexo anexo) throws AppException
	{
		if (comentario == null && (anexo == null || anexo.getArquivo() == null))
			throw new AppException("Entre com um comentário ou um arquivo");

		if (comentario == null)
			comentario = anexo.getArquivo().getName();

		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link))
		{
			link.beginTran();

			chamado = chamadoDao.select(chamado.getId());

			chamadoDao
				.update(chamado
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(comentario)
							.setData(LocalDateTime.now())
							.setAnexo(anexoDao.insert(anexo))
							.setTipo(Evento.Tipo.COMENTARIO_SIMPLES))));

			if (chamado.getPendencia() == Pendencia.SUSPENSO
				&& chamado.getRetomada() == null
				&& getUser().equals(chamado.getSolicitante()))
				chamadoDao
					.update(chamado
						.setPendencia(Pendencia.NENHUMA)
						.setEvento(eventoDao
							.insert(new Evento()
								.setChamado(chamado)
								.setTipo(Evento.Tipo.RETOMADA)
								.setData(chamado.getEvento().getData().plusSeconds(1))
								.setDescricao("Chamado retomado automaticamente pelo cliq"))));

			if (chamado.getResposta() == null
				&& getUser().equals(chamado.getAtendente())
				&& chamado.getPendencia() == Pendencia.NENHUMA)
				chamadoDao
					.update(chamado
						.setResposta(LocalDateTime.now())
						.setEvento(eventoDao
							.insert(new Evento()
								.setChamado(chamado)
								.setTipo(Evento.Tipo.ATENDIMENTO)
								.setData(chamado.getEvento().getData().plusSeconds(1))
								.setDescricao("Atendimento iniciado por " + getUser()))));
			link.commit();
			event.fire(chamado);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Avaliação
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void avaliar(Chamado chamado, Nota nota, String observacoes, Anexo anexo) throws AppException
	{
		if (nota == null)
			throw new AppException("A nota é obrigatória");

		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link);
			CompartilhamentoDao compartilhamentoDao = new CompartilhamentoDao(link))
		{
			link.beginTran();

			chamado = chamadoDao.select(chamado.getId());

			if (chamado.getPendencia() != Pendencia.AVALIACAO)
				throw new AppException("O chamado não se encontra em avaliação");

			if (chamado.getNota() != null)
				throw new AppException("Chamado já foi avaliado pelo solicitante");

			if (!getUser().equals(chamado.getSolicitante())
				&& !compartilhamentoDao.exists(chamado, getUser())
				&& !compartilhamentoDao.exists(chamado, getUser().getRole()))
				throw new AppException("Apenas o solicitante do chamado pode avaliá-lo");

			if (nota.equals(Nota.PESSIMO)
				&& (observacoes == null || observacoes.trim().isEmpty()))
				throw new AppException("Para que possamos melhorar nosso atendimento, indique o motivo pelo qual você o classifica como péssimo");

			chamadoDao
				.update(chamado
					.setNota(nota)
					.setPendencia(Pendencia.NENHUMA)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setTipo(Evento.Tipo.AVALIACAO)
							.setAnexo(anexoDao.insert(anexo))
							.setDescricao("Chamado avaliado como " + nota + " por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Homologação
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void aceitarHomologacao(Chamado chamado, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link))
		{
			link.beginTran();

			chamado = chamadoDao.select(chamado.getId());
			if (chamado.getPendencia() != Pendencia.HOMOLOGACAO)
				throw new AppException("O chamado não se encontra em homologação");
			if (!getUser().equals(chamado.getPessoaHomologadora())
				&& !getUser().getRole().equals(chamado.getEquipeHomologadora()))
				throw new AppException("O chamado não pode ser homologado por você");

			chamadoDao
				.update(chamado
					.setPessoaHomologadora(null)
					.setEquipeHomologadora(null)
					.setPendencia(Pendencia.NENHUMA)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setAnexo(anexoDao.insert(anexo))
							.setTipo(Evento.Tipo.HOMOLOGACAO_ACEITE)
							.setDescricao("Chamado homologado por " + getUser()))));

			Evento solicitacaoHomologacao = chamado.getEventos().stream()
				.filter(e -> e.getTipo().equals(Evento.Tipo.HOMOLOGACAO))
				.reduce((previous, current) -> current).get();

			if (Boolean.TRUE.equals(chamado.getCategoria().getFeedback())
				&& chamado.getSolicitante().getId() != null
				&& !chamado.getSolicitante().equals(getUser()))
				chamadoDao
					.update(chamado
						.setPendencia(Pendencia.FEEDBACK)
						.setFeedback(LocalDateTime.now())
						.setAtendente(solicitacaoHomologacao.getUser())
						.setEvento(eventoDao
							.insert(new Evento()
								.setChamado(chamado)
								.setTipo(Evento.Tipo.FEEDBACK)
								.setUser(solicitacaoHomologacao.getUser())
								.setData(chamado.getEvento().getData().plusSeconds(1))
								.setDescricao("Chamado enviado para feedback do solicitante por " + solicitacaoHomologacao.getUser().getName()))));
			else
				chamadoDao
					.update(chamado
						.setSituacao(Situacao.CONCLUIDO)
						.setAtendente(solicitacaoHomologacao.getUser())
						.setSolucao(chamado.getEvento().getData().plusSeconds(1))
						.setPendencia(chamado.getCategoria().getAvaliacao() ? Pendencia.AVALIACAO : Pendencia.NENHUMA)
						.setEvento(eventoDao
							.insert(new Evento()
								.setAnexo(anexo)
								.setChamado(chamado)
								.setObservacoes(observacoes)
								.setData(chamado.getSolucao())
								.setTipo(Evento.Tipo.CONCLUSAO)
								.setUser(solicitacaoHomologacao.getUser())
								.setDescricao("Chamado concluído por " + solicitacaoHomologacao.getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	public void recusarHomologacao(Chamado chamado, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link))
		{
			link.beginTran();

			chamado = chamadoDao.select(chamado.getId());
			if (chamado.getPendencia() != Pendencia.HOMOLOGACAO)
				throw new AppException("O chamado não se encontra em homologação");
			if (!getUser().equals(chamado.getPessoaHomologadora())
				&& !getUser().getRole().equals(chamado.getEquipeHomologadora()))
				throw new AppException("O chamado não pode ser homologado por você");

			chamadoDao
				.update(chamado
					.setPessoaHomologadora(null)
					.setEquipeHomologadora(null)
					.setPendencia(Pendencia.NENHUMA)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao.
						insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setAnexo(anexoDao.insert(anexo))
							.setTipo(Evento.Tipo.HOMOLOGACAO_RECUSA)
							.setDescricao("Homologação do chamado recusada por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Aprovação
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void aceitarAprovacao(Chamado chamado, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link))
		{
			link.beginTran();

			chamado = chamadoDao.select(chamado.getId());
			if (chamado.getPendencia() != Pendencia.APROVACAO)
				throw new AppException("O chamado não se encontra em aprovação");
			if (!getUser().equals(chamado.getPessoaAprovadora())
				&& !getUser().getRole().equals(chamado.getEquipeAprovadora()))
				throw new AppException("O chamado não pode ser aprovado por você");

			chamadoDao.update(chamado
				.setPessoaAprovadora(null)
				.setEquipeAprovadora(null)
				.setPendencia(Pendencia.NENHUMA)
				.setNotificados(new StringSet(getUser().getId().toString()))
				.setEvento(eventoDao
					.insert(new Evento()
						.setUser(getUser())
						.setChamado(chamado)
						.setObservacoes(observacoes)
						.setData(LocalDateTime.now())
						.setAnexo(anexoDao.insert(anexo))
						.setTipo(Evento.Tipo.APROVACAO_ACEITE)
						.setDescricao("Chamado aprovado por " + getUser()))));

			link.commit();

			event.fire(chamado);
		}
	}

	public void recusarAprovacao(Chamado chamado, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link))
		{
			link.beginTran();

			chamado = chamadoDao.select(chamado.getId());

			if (chamado.getPendencia() != Pendencia.APROVACAO)
				throw new AppException("O chamado não se encontra em aprovação");
			if (!getUser().equals(chamado.getPessoaAprovadora())
				&& !getUser().getRole().equals(chamado.getEquipeAprovadora()))
				throw new AppException("O chamado não pode ser aprovado por você");

			chamadoDao.update(chamado
				.setPessoaAprovadora(null)
				.setEquipeAprovadora(null)
				.setPendencia(Pendencia.NENHUMA)
				.setNotificados(new StringSet(getUser().getId().toString()))
				.setEvento(eventoDao
					.insert(new Evento()
						.setUser(getUser())
						.setChamado(chamado)
						.setObservacoes(observacoes)
						.setData(LocalDateTime.now())
						.setAnexo(anexoDao.insert(anexo))
						.setTipo(Evento.Tipo.APROVACAO_RECUSA)
						.setDescricao("Chamado reprovado por " + getUser()))));

			Evento aprovacao = chamado.getEventos().stream().filter(e -> e.getTipo() == Evento.Tipo.APROVACAO).reduce((p, c) -> c).get();
			if (aprovacao != null && aprovacao.getUser().getId() == null)
				chamadoDao.update(chamado
					.setSituacao(Situacao.CANCELADO)
					.setSolucao(LocalDateTime.now().plusSeconds(1))
					.setEvento(eventoDao
						.insert(new Evento()
							.setChamado(chamado)
							.setData(chamado.getSolucao())
							.setTipo(Evento.Tipo.CANCELAMENTO)
							.setDescricao("Chamado cancelado por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Feedback
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void aceitarFeedback(Chamado chamado, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link);
			CompartilhamentoDao compartilhamentoDao = new CompartilhamentoDao(link))
		{
			link.beginTran();

			chamado = chamadoDao.select(chamado.getId());

			if (chamado.getPendencia() != Pendencia.FEEDBACK)
				throw new AppException("O chamado não se encontra em feedabck");

			if (!getUser().equals(chamado.getSolicitante())
				&& !compartilhamentoDao.exists(chamado, getUser())
				&& !compartilhamentoDao.exists(chamado, getUser().getRole()))
				throw new AppException("Apenas o solicitante do chamado pode aceitar sua solução");

			chamado.setEvento(eventoDao
				.insert(new Evento()
					.setUser(getUser())
					.setChamado(chamado)
					.setObservacoes(observacoes)
					.setData(LocalDateTime.now())
					.setAnexo(anexoDao.insert(anexo))
					.setTipo(Evento.Tipo.FEEDBACK_ACEITE)
					.setDescricao("Solução do chamado aceita por " + getUser())));

			Evento feedback = chamado.getEventos().stream()
				.filter(e -> e.getTipo().equals(Evento.Tipo.FEEDBACK))
				.reduce((previous, current) -> current).get();

			chamadoDao
				.update(chamado.setFeedback(null)
					.setPendencia(Pendencia.NENHUMA)
					.setSituacao(Situacao.CONCLUIDO)
					.setSolucao(feedback.getData().plusSeconds(1))
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setChamado(chamado)
							.setUser(feedback.getUser())
							.setData(chamado.getSolucao())
							.setTipo(Evento.Tipo.CONCLUSAO)
							.setDescricao("Chamado concluído por " + feedback.getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	public void recusarFeedback(Chamado chamado, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link);
			CompartilhamentoDao compartilhamentoDao = new CompartilhamentoDao(link))
		{
			link.beginTran();

			chamado = chamadoDao.select(chamado.getId());

			if (chamado.getPendencia() != Pendencia.FEEDBACK)
				throw new AppException("O chamado não se encontra em feedabck");

			if (!getUser().equals(chamado.getSolicitante())
				&& !compartilhamentoDao.exists(chamado, getUser())
				&& !compartilhamentoDao.exists(chamado, getUser().getRole()))
				throw new AppException("Apenas o solicitante do chamado pode rejeitar sua solução");

			chamadoDao
				.update(chamado
					.setFeedback(null)
					.setPendencia(Pendencia.NENHUMA)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setAnexo(anexoDao.insert(anexo))
							.setTipo(Evento.Tipo.FEEDBACK_RECUSA)
							.setDescricao("Solução do chamado recusada por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Complementação
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void complementar(Chamado chamado, String observacoes, Anexo anexo) throws AppException
	{
		if (observacoes == null
			&& (anexo == null || anexo.getArquivo() == null))
			throw new AppException("Entre com a informação solicitada para complementar o chamado");

		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link);
			CompartilhamentoDao compartilhamentoDao = new CompartilhamentoDao(link))
		{
			link.beginTran();

			chamado = chamadoDao.select(chamado.getId());
			if (chamado.getPendencia() != Pendencia.COMPLEMENTACAO)
				throw new AppException("O chamado não se encontra em complementação");

			if (!getUser().equals(chamado.getSolicitante())
				&& !compartilhamentoDao.exists(chamado, getUser())
				&& !compartilhamentoDao.exists(chamado, getUser().getRole()))
				throw new AppException("Apenas o solicitante do chamado pode complementá-lo");

			chamadoDao
				.update(chamado
					.setPendencia(Pendencia.NENHUMA)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setAnexo(anexoDao.insert(anexo))
							.setTipo(Evento.Tipo.COMPLEMENTACAO)
							.setDescricao("Chamado complementado por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}
}
