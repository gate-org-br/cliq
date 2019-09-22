package cliq.control;

import cliq.dao.ChamadoDao;
import cliq.dao.CompartilhamentoDao;
import cliq.dao.EquipeDao;
import cliq.dao.EventoDao;
import cliq.dao.PessoaDao;
import cliq.entity.Chamado;
import cliq.entity.Compartilhamento;
import cliq.entity.Evento;
import gate.base.Control;
import gate.entity.Role;
import gate.entity.User;
import gate.error.AppException;
import gate.error.ConstraintViolationException;
import gate.sql.Link;
import java.time.LocalDateTime;
import java.util.List;

public class CompartilhamentoControl extends Control
{

	public CompartilhamentoControl()
	{
		super();
	}

	public List<Compartilhamento> search(Chamado chamado)
	{
		try (CompartilhamentoDao dao = new CompartilhamentoDao())
		{
			return dao.search(chamado);
		}
	}

	public void compartilhar(Chamado chamado, User pessoa) throws AppException
	{
		if (chamado == null || chamado.getId() == null)
			throw new AppException("Tentativa de compartilhar chamado inexistente.");
		if (pessoa == null || pessoa.getId() == null)
			throw new AppException("Tentativa de compartilhar chamado com equipe inexistente.");

		try (Link link = new Link();
			EventoDao eventoDao = new EventoDao(link);
			PessoaDao pessoaDao = new PessoaDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link);
			CompartilhamentoDao compartilhamentoDao = new CompartilhamentoDao(link))
		{
			link.beginTran();

			pessoa = pessoaDao.select(pessoa.getId());

			if (!getUser().checkAccess("cliq.modulos.atendimento", null, null)
				&& !getUser().getRole().getMasterRole().contains(pessoa.getRole()))
				throw new AppException("Tentativa de compartilhar chamado com pessoa inexistente");

			chamado = chamadoDao.select(chamado.getId());

			compartilhamentoDao
				.insert(new Compartilhamento()
					.setChamado(chamado)
					.setPessoa(pessoa));

			chamado.setEvento(eventoDao
				.insert(new Evento()
					.setUser(getUser())
					.setData(LocalDateTime.now())
					.setTipo(Evento.Tipo.COMPARTILHAMENTO)
					.setChamado(chamado)
					.setDescricao(String.format("%s compartilhou o chamado com %s.", getUser().getName(), pessoa.getName()))));

			chamadoDao.update(chamado);

			link.commit();
		}
	}

	public void compartilhar(Chamado chamado, Role equipe) throws AppException
	{
		if (chamado == null || chamado.getId() == null)
			throw new AppException("Tentativa de compartilhar chamado inexistente.");
		if (equipe == null || equipe.getId() == null)
			throw new AppException("Tentativa de compartilhar chamado com equipe inexistente.");

		try (Link link = new Link();
			EquipeDao equipeDao = new EquipeDao(link);
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link);
			CompartilhamentoDao compartilhamentoDao = new CompartilhamentoDao(link))
		{
			link.beginTran();

			equipe = equipeDao.select(equipe.getId());

			if (!getUser().checkAccess("cliq.modulos.atendimento", null, null)
				&& !getUser().getRole().getMasterRole().contains(equipe))
				throw new AppException("Tentativa de compartilhar chamado com pessoa inexistente");

			chamado = chamadoDao.select(chamado.getId());

			compartilhamentoDao
				.insert(new Compartilhamento()
					.setChamado(chamado)
					.setEquipe(equipe));

			chamado.setEvento(eventoDao
				.insert(new Evento()
					.setUser(getUser())
					.setData(LocalDateTime.now())
					.setTipo(Evento.Tipo.COMPARTILHAMENTO)
					.setChamado(chamado)
					.setDescricao(String.format("%s compartilhou o chamado com %s.", getUser().getName(), equipe.getName()))));

			chamadoDao.update(chamado);

			link.commit();
		}
	}

	public void descompartilhar(Compartilhamento compartilhamento) throws AppException
	{
		if (compartilhamento == null || compartilhamento.getId() == null)
			throw new AppException("Tentativa de remover compartilhamento inexistente.");

		try (Link link = new Link();
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link);
			CompartilhamentoDao compartilhamentoDao = new CompartilhamentoDao(link))
		{
			link.beginTran();

			compartilhamento = compartilhamentoDao.select(compartilhamento.getId());
			Chamado chamado = chamadoDao.select(compartilhamento.getChamado().getId());

			chamado.setEvento(eventoDao.insert(new Evento()
				.setUser(getUser())
				.setData(LocalDateTime.now())
				.setTipo(Evento.Tipo.DESCOMPARTILHAMENTO)
				.setChamado(compartilhamento.getChamado())
				.setDescricao(String.format("%s removeu o compartilhamento com %s.",
					getUser().getName(),
					compartilhamento.getPessoa().getId() != null
					? compartilhamento.getPessoa().getName()
					: compartilhamento.getEquipe().getName()))));

			compartilhamentoDao.delete(compartilhamento);
			chamadoDao.update(chamado);

			link.commit();
		} catch (ConstraintViolationException e)
		{
			throw new AppException(e.getMessage());
		}
	}
}
