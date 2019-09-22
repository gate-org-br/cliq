package cliq.control;

import cliq.dao.PendenciaDao;
import cliq.entity.Chamado;
import gate.base.Control;
import java.util.List;

public class PendenciaControl extends Control
{
	public List<Chamado> search()
	{
		try (PendenciaDao dao = new PendenciaDao())
		{
			return dao.search(getUser());
		}
	}

}
