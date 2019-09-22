package cliq.modulos.admin;

import cliq.control.AtorControl;
import cliq.modulos.CLIQScreen;
import gate.annotation.Description;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.constraint.Pattern;
import gate.constraint.Required;
import gate.error.AppException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class AtorScreen extends CLIQScreen
{

	@Required
	@Pattern(".{3,}")
	@Description("Entre com no mínimo 3 caracteres")
	private String form;

	@Inject
	private AtorControl control;

	private List<Map<String, Object>> page;

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
		return "/WEB-INF/views/cliq/modulos/admin/Ator/ViewSearch.jsp";
	}

	public List<Map<String, Object>> getPage()
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
