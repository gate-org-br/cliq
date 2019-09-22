package cliq.control;

import cliq.dao.EquipeDao;
import cliq.entity.Equipe;
import gate.base.Control;
import gate.entity.Role;
import gate.error.AppException;
import gate.error.NotFoundException;
import gate.type.ID;
import java.io.Serializable;
import java.util.List;

public class EquipeControl extends Control implements Serializable
{

	private static final long serialVersionUID = 1L;

	public EquipeControl()
	{
		super();
	}

	public List<Role> search(String filter) throws AppException
	{
		if (filter == null || filter.length() < 3)
			throw new AppException("Entre com no mínimo 3 caracteres no filtro");
		try (EquipeDao dao = new EquipeDao())
		{
			return getUser().checkAccess("cliq.modulos.atendimento", null, null)
				|| getUser().checkAccess("cliq.modulos.admin", null, null)
				? dao.search(filter)
				: dao.search(getUser().getRole().getMasterRole(), filter);
		}
	}

	public List<Equipe> search()
	{
		try (EquipeDao dao = new EquipeDao())
		{
			List<Equipe> roles = dao.search();
			roles.stream().forEach(parent -> roles.stream().filter(child -> child.getRole().equals(parent))
				.forEach(child -> parent.getRoles().add(child.setRole(parent))));
			roles.removeIf(e -> e.getRole().getId() != null);
			return roles;
		}
	}

	public Equipe select(ID id) throws NotFoundException
	{
		try (EquipeDao dao = new EquipeDao())
		{
			return dao.select(id);
		}
	}
}
