package cliq.dao;

import cliq.entity.Chamado;
import gate.sql.Link;
import java.util.List;

public class RetomadaDao extends ChamadoDao
{
	public RetomadaDao(Link link)
	{
		super(link);
	}

	public List<Chamado> search()
	{
		return getLink()
			.from(getClass().getResource("RetomadaDao/search().sql"))
			.constant()
			.fetchEntityList(Chamado.class);
	}
}
