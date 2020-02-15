package cliq.modulos;

import cliq.control.ContatoControl;
import cliq.entity.Contato;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.error.AppException;
import javax.inject.Inject;

@Name("Contatos")
@Icon("cliq.entity.Contato")
public class ContatoScreen extends CLIQScreen
{

	private Contato form;

	@Inject
	private ContatoControl control;

	@Icon("select")
	@Name("Contato")
	public String callSelect()
	{
		try
		{
			form = control.select(getForm().getTipo(), getForm().getId());
			return "/WEB-INF/views/cliq/modulos/Contato.jsp";
		} catch (AppException ex)
		{
			return ex.getMessage();
		}
	}

	public Contato getForm()
	{
		if (form == null)
			form = new Contato();
		return form;
	}
}
