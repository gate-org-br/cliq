package cliq.modulos.admin;

import cliq.control.PessoaControl;
import cliq.modulos.CLIQScreen;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.entity.User;
import gate.error.AppException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class UsuarioScreen extends CLIQScreen
{

	private String form;

	private List<User> page;
	@Inject
	private PessoaControl control;

	@Icon("search")
	@Name("Pesquisar")
	public String callSearch()
	{
		try
		{
			page = control.search(form);
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return "/WEB-INF/views/cliq/modulos/admin/Usuario/ViewSearch.jsp";
	}

	public List<User> getPage()
	{
		if (page == null)
			page = new ArrayList<>();
		return page;
	}

	public void setForm(String form)
	{
		this.form = form;
	}

	public String getForm()
	{
		return form;
	}

}
