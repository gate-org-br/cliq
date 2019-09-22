package cliq.control;

import cliq.dao.ChamadoDao;
import cliq.dao.ChecklistDao;
import cliq.dao.EventoDao;
import cliq.entity.Chamado;
import cliq.entity.Evento;
import cliq.type.Checklist.Checkitem;
import cliq.type.Pendencia;
import cliq.type.Situacao;
import gate.base.Control;
import gate.error.AppException;
import gate.sql.Link;
import gate.type.ID;
import java.time.LocalDateTime;

public class ChecklistControl extends Control
{

	public Chamado insert(ID id, Checkitem checkitem) throws AppException
	{
		try (Link link = new Link();
			ChamadoDao chamadoDao = new ChamadoDao(link);
			ChecklistDao checklistDao = new ChecklistDao(link))
		{
			link.beginTran();

			Chamado chamado = chamadoDao.select(id);
			if (!Situacao.PENDENTE.equals(chamado.getSituacao()))
				throw new AppException("Tarefas só podem ser criadas em chamados pendentes.");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());

			chamado.getChecklist().add(checkitem);
			checklistDao.update(chamado, chamado.getChecklist());

			link.commit();
			return chamado;
		}
	}

	public Chamado delete(ID id, Checkitem checkitem) throws AppException
	{
		try (Link link = new Link();
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link);
			ChecklistDao checklistDao = new ChecklistDao(link))
		{
			link.beginTran();

			Chamado chamado = chamadoDao.select(id);
			if (!Situacao.PENDENTE.equals(chamado.getSituacao()))
				throw new AppException("Tarefas só podem ser removidas de chamados pendentes.");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());
			if (chamado.getChecklist().size() <= checkitem.getIndex())
				throw new AppException("Tarefa não encontrada.");

			if (Boolean.TRUE.equals(chamado.getChecklist().get(checkitem.getIndex()).getExecutada()))
				chamado.setEvento(eventoDao.insert(new Evento()
					.setUser(getUser())
					.setChamado(chamado)
					.setData(LocalDateTime.now())
					.setTipo(Evento.Tipo.CANCELAMENTO_TAREFA)
					.setDescricao(String.format("Tarefa cancelada: %s.",
						chamado.getChecklist().get(checkitem.getIndex()).getNome()))));

			chamado.getChecklist().remove(checkitem.getIndex());

			checklistDao.update(chamado, chamado.getChecklist());
			link.commit();

			return chamado;
		}
	}

	public Chamado commit(ID id, Checkitem checkitem) throws AppException
	{
		try (Link link = new Link();
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link);
			ChecklistDao checklistDao = new ChecklistDao(link))
		{
			link.beginTran();

			Chamado chamado = chamadoDao.select(id);
			if (!Situacao.PENDENTE.equals(chamado.getSituacao()))
				throw new AppException("Tarefas só podem ser removidas de chamados pendentes.");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());

			if (chamado.getChecklist().size() <= checkitem.getIndex())
				throw new AppException("Tarefa não encontrada no chamado.");
			if (Boolean.TRUE.equals(chamado.getChecklist().get(checkitem.getIndex()).getExecutada()))
				throw new AppException("Tarefa já se encontra concluída.");

			chamado.getChecklist()
				.get(checkitem.getIndex())
				.setExecutada(true);

			chamado.setEvento(eventoDao.insert(new Evento()
				.setUser(getUser())
				.setChamado(chamado)
				.setData(LocalDateTime.now())
				.setTipo(Evento.Tipo.EXECUCAO_TAREFA)
				.setDescricao(String.format("Tarefa concluída: %s.", chamado.getChecklist().get(checkitem.getIndex()).getNome()))));
			checklistDao.update(chamado, chamado.getChecklist());

			link.commit();
			return chamado;
		}
	}

	public Chamado cancel(ID id, Checkitem checkitem) throws AppException
	{
		try (Link link = new Link();
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao chamadoDao = new ChamadoDao(link);
			ChecklistDao checklistDao = new ChecklistDao(link))
		{
			link.beginTran();

			Chamado chamado = chamadoDao.select(id);
			if (!Situacao.PENDENTE.equals(chamado.getSituacao()))
				throw new AppException("Tarefas só podem ser removidas de chamados pendentes.");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());

			if (chamado.getChecklist().size() <= checkitem.getIndex())
				throw new AppException("Tarefa não encontrada no chamado.");
			if (Boolean.FALSE.equals(chamado.getChecklist().get(checkitem.getIndex()).getExecutada()))
				throw new AppException("Tarefa ainda se encontra pendente.");

			chamado.getChecklist().get(checkitem.getIndex()).setExecutada(false);
			chamado.setEvento(eventoDao.insert(new Evento()
				.setUser(getUser())
				.setChamado(chamado)
				.setData(LocalDateTime.now())
				.setTipo(Evento.Tipo.CANCELAMENTO_TAREFA)
				.setDescricao(String.format("Tarefa cancelada: %s.", chamado.getChecklist().get(checkitem.getIndex()).getNome()))));
			checklistDao.update(chamado, chamado.getChecklist());

			link.commit();
			return chamado;
		}
	}
}
