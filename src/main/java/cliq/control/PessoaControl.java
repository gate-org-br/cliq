package cliq.control;

import cliq.dao.PessoaDao;
import cliq.entity.Chamado;
import gate.base.Control;
import gate.entity.User;
import gate.error.AppException;
import gate.error.NotFoundException;
import gate.type.ID;
import java.util.List;

public class PessoaControl extends Control
{

	public List<User> search(String filter) throws AppException
	{
		if (filter == null || filter.length() < 3)
			throw new AppException("Entre com no mínimo 3 caracteres no filtro");
		try (PessoaDao dao = new PessoaDao())
		{
			return getUser().checkAccess("cliq.modulos.atendimento", null, null)
				|| getUser().checkAccess("cliq.modulos.admin", null, null)
				? dao.search(filter)
				: dao.search(getUser().getRole().getMasterRole(), filter);
		}
	}

	public List<gate.entity.User> search(User filter)
	{
		try (PessoaDao dao = new PessoaDao())
		{
			return dao.search(filter);
		}
	}

	public User select(ID id) throws NotFoundException
	{
		try (PessoaDao dao = new PessoaDao())
		{
			return dao.select(id);
		}
	}
}
