package cliq.dao;

import cliq.entity.Chamado;
import gate.sql.Link;
import java.util.List;

public class FeedbackDao extends ChamadoDao
{
	public FeedbackDao(Link link)
	{
		super(link);
	}

	public List<Chamado> search()
	{
		return getLink().from(getClass().getResource("FeedbackDao/search().sql"))
			.constant()
			.fetchEntityList(Chamado.class);
	}
}
