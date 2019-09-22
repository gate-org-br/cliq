package cliq.modulos.admin;

import cliq.modulos.*;
import cliq.control.EquipeControl;
import cliq.entity.Equipe;
import gate.annotation.Icon;
import gate.annotation.Name;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class EquipeScreen extends CLIQScreen
{

	private List<Equipe> page;

	@Inject
	private EquipeControl control;

	@Icon("search")
	@Name("Pesquisar")
	public String callSearch()
	{
		page = control.search();
		return "/WEB-INF/views/cliq/modulos/admin/Equipe/ViewSearch.jsp";
	}

	public List<Equipe> getPage()
	{
		if (page == null)
			page = new ArrayList<>();
		return page;
	}

}
