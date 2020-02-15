package cliq.modulos.admin;

import cliq.modulos.CLIQScreen;
import gate.entity.Role;


public class RoleScreen extends CLIQScreen
{

	private Role form;

	public String callSearch()
	{
		return "/WEB-INF/views/cliq/modulos/admin/Role/ViewSearch.jsp";
	}

	public Role getForm()
	{
		if (form == null)
		{
			form = new Role();
		}
		return form;
	}

	public void setForm(Role form)
	{
		this.form = form;
	}
}
