package cliq.modulos.atendimento;

import cliq.control.AnexoControl;
import cliq.entity.Anexo;
import cliq.modulos.CLIQScreen;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.error.NotFoundException;
import javax.inject.Inject;

@Icon("cliq.entity.Anexo")
public class AnexoScreen extends CLIQScreen
{

	private Anexo form;

	@Inject
	private AnexoControl control;

	@Icon("dwload")
	@Name("Baixar")
	public Object callDwload()
	{
		try
		{
			return control.select(form.getId());
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
