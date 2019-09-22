package cliq.modulos;

import cliq.control.CategoriaControl;
import cliq.entity.Categoria;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.error.NotFoundException;
import javax.inject.Inject;

public class CategoriaScreen extends CLIQScreen
{

	private Categoria form;

	@Inject
	private CategoriaControl control;

	@Icon("dwload")
	@Name("Baixar")
	public Object callIcon()
	{
		try
		{
			return control.getIcon(form);
		} catch (NotFoundException ex)
		{
			return ex.getMessage();
		}
	}

	public Categoria getForm()
	{
		if (form == null)
			form = new Categoria();
		return form;
	}
}
