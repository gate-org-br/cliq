package cliq.control;

import cliq.dao.EventoDao;
import cliq.dao.RetomadaDao;
import cliq.entity.Chamado;
import cliq.entity.Evento;
import cliq.type.Situacao;
import gate.base.Control;
import gate.error.AppException;
import gate.error.ConstraintViolationException;
import gate.sql.Link;
import java.time.LocalDateTime;

public class RetomadaControl extends Control
{

	public void execute() throws AppException
	{
		try (Link link = new Link();
			EventoDao eventoDao = new EventoDao(link);
			RetomadaDao retomadaDao = new RetomadaDao(link))
		{
			link.beginTran();
			for (Chamado chamado : retomadaDao.search())
				retomadaDao.update(chamado,
					Situacao.PENDENTE,
					eventoDao.insert(new Evento()
						.setChamado(chamado)
						.setData(LocalDateTime.now())
						.setTipo(Evento.Tipo.RETOMADA)
						.setDescricao("Chamado retomado automaticamente")));
			link.commit();
		} catch (ConstraintViolationException | RuntimeException ex)
		{
			throw new AppException(ex, "Erro ao retomar chamados automaticamente");
		}
	}
}
