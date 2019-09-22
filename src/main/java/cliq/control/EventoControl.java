package cliq.control;

import cliq.dao.AnexoDao;
import cliq.dao.EventoDao;
import cliq.entity.Equipe;
import cliq.entity.Evento;
import gate.annotation.Current;
import gate.base.Control;
import gate.constraint.Constraints;
import gate.error.AppException;
import gate.error.NotFoundException;
import gate.sql.Link;
import gate.type.ID;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class EventoControl extends Control
{

	@Inject
	@Current
	private Equipe equipe;

	public EventoControl()
	{
		super();
	}

	public List<Evento> search(Evento evento)
	{
		try (EventoDao dao = new EventoDao())
		{
			return dao.search(equipe, evento);
		}
	}

	public List<Map<String, Object>> search(Evento.Agrupamento agrupamento, Evento evento)
	{
		try (EventoDao dao = new EventoDao())
		{
			return dao.search(agrupamento, equipe, evento);
		}
	}

	public Evento select(ID id) throws NotFoundException
	{
		try (EventoDao dao = new EventoDao())
		{
			return dao.select(id);
		}
	}

	public void update(Evento evento) throws AppException
	{
		Constraints.validate(evento, "descricao");

		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link))
		{
			link.beginTran();

			if (evento.getAnexo().getArquivo() != null)
			{
				anexoDao.delete(evento);
				anexoDao.insert(evento.getAnexo());
			}
			eventoDao.update(evento);
			link.commit();
		}
	}
}
