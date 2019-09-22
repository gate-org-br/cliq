package cliq.modulos.admin;

import cliq.modulos.CLIQScreen;
import gate.annotation.Icon;
import gate.annotation.Name;

@Icon("2020")
@Name("Gerência")
public class AdminScreen extends CLIQScreen
{

	@Override
	public String call()
	{
		return "/WEB-INF/views/cliq/modulos/admin/View.jsp";
	}
}
