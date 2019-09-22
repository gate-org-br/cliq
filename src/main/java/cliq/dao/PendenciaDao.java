package cliq.dao;

import cliq.entity.Chamado;
import gate.base.Dao;
import gate.entity.User;
import java.util.List;

public class PendenciaDao extends Dao
{

	public List<Chamado> search(User filter)
	{
		return getLink()
			.from(getClass().getResource("PendenciaDao/search(User).sql"))
			.parameters(filter.getId(),
				filter.getRole().getId(),
				filter.getId(),
				filter.getRole().getId(),
				filter.getId(),
				filter.getId(),
				filter.getId(),
				filter.getRole().getRole().getId())
			.fetchEntityList(Chamado.class);
	}
}
