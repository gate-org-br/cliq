package cliq.modulos;

import cliq.control.PessoaControl;
import gate.annotation.Description;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.constraint.Pattern;
import gate.constraint.Required;
import gate.entity.User;
import gate.error.AppException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class PessoaScreen extends CLIQScreen
{

	@Required
	@Pattern(".{3,}")
	@Description("Entre com no mínimo 3 caracteres")
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
			if (form != null && form.length() >= 3)
				page = control.search(form);
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return "/WEB-INF/views/cliq/modulos/Pessoa/ViewSearch.jsp";
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
