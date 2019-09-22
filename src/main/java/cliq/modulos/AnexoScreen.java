package cliq.modulos;

import cliq.control.AnexoControl;
import cliq.entity.Anexo;
import gate.annotation.Description;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.error.NotFoundException;
import javax.inject.Inject;

public class AnexoScreen extends CLIQScreen
{

	private Anexo form;

	@Inject
	private AnexoControl control;

	@Name("Anexo")
	@Icon("attach")
	@Description("Fazer download do anexo")
	public Object call()
	{
		try
		{
			return control.select(getForm().getId());
		} catch (NotFoundException ex)
		{
			return ex.getMessage();
		}
	}

	public Anexo getForm()
	{
		if (form == null)
			form = new Anexo();
		return form;
	}
}
