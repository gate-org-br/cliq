package cliq.modulos.atendimento;

import cliq.control.CategoriaControl;
import cliq.entity.Categoria;
import cliq.entity.Chamado;
import cliq.entity.Equipe;
import cliq.modulos.CLIQScreen;
import gate.annotation.Current;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.error.AppException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@Name("Categorias")
@Icon("cliq.entity.Categoria")
public class CategoriaScreen extends CLIQScreen
{

	private Chamado form;
	private List<Categoria> page;

	@Inject
	@Current
	private Equipe equipe;

	@Inject
	private CategoriaControl control;

	@Icon("update")
	@Name("Categorizar")
	public String callUpdate()
	{
		try
		{
			page = control.search(equipe);
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return "/WEB-INF/views/cliq/modulos/atendimento/Categoria/ViewSearch.jsp";
	}

	public Chamado getForm()
	{
		if (form == null)
			form = new Chamado();
		return form;
	}

	public List<Categoria> getPage()
	{
		if (page == null)
			page = new ArrayList<>();
		return page;
	}
}
