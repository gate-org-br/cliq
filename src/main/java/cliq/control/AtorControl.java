package cliq.control;

import cliq.dao.AtorDao;
import gate.base.Control;
import gate.error.AppException;
import java.util.List;
import java.util.Map;

public class AtorControl extends Control
{

	public List<Map<String, Object>> search(String filter) throws AppException
	{
		if (filter == null || filter.length() < 3)
			throw new AppException("Entre com no mínimo 3 caracteres no filtro");
		try (AtorDao dao = new AtorDao())
		{
			return dao.search(filter);
		}
	}
}
