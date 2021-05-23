package cliq.control;

import cliq.dao.EventoDao;
import cliq.dao.FeedbackDao;
import cliq.entity.Chamado;
import cliq.entity.Evento;
import cliq.type.Situacao;
import gate.base.Control;
import gate.error.AppException;
import gate.error.ConstraintViolationException;
import gate.sql.Link;
import java.time.LocalDateTime;

public class FeedbackControl extends Control
{

	public void execute() throws AppException
	{
		try (Link link = new Link();
			EventoDao eventoDao = new EventoDao(link);
			FeedbackDao feedbackDao = new FeedbackDao(link))
		{
			link.beginTran();
			LocalDateTime aceitacao = LocalDateTime.now();
			for (Chamado chamado : feedbackDao.search())
			{
				Evento feedback = chamado.getEvento();

				eventoDao.insert(new Evento()
					.setData(aceitacao)
					.setChamado(chamado)
					.setTipo(Evento.Tipo.FEEDBACK_ACEITE)
					.setDescricao("Solução do chamado aceita automaticamente por inércia do solicitante"));

				feedbackDao.update(chamado,
					Situacao.CONCLUIDO,
					eventoDao.insert(new Evento()
						.setChamado(chamado)
						.setData(feedback.getData())
						.setUser(feedback.getUser())
						.setTipo(Evento.Tipo.CONCLUSAO)
						.setDescricao("Chamado concluído por " + feedback.getUser().getName())));
			}

			link.commit();
		} catch (ConstraintViolationException | RuntimeException ex)
		{
			throw new AppException(ex, "Erro ao aceitar chamados automaticamente");
		}
	}
}
