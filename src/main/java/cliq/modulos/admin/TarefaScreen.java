package cliq.modulos.admin;

import cliq.control.CategoriaControl;
import cliq.control.TarefaControl;
import cliq.entity.Categoria;
import cliq.entity.Equipe;
import cliq.entity.Tarefa;
import cliq.modulos.CLIQScreen;
import gate.annotation.Color;
import gate.annotation.Current;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.error.AppException;
import gate.error.NotFoundException;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

@Name("Tarefas")
@Icon("cliq.entity.Tarefa")
public class TarefaScreen extends CLIQScreen
{

	private Tarefa form;
	private List<Tarefa> page;

	@Inject
	private TarefaControl control;

	@Inject
	@Current
	private Equipe equipe;

	@Override
	public String call()
	{
		page = ordenate(control.search(equipe));
		return "/WEB-INF/views/cliq/modulos/admin/Tarefa/View.jsp";
	}

	@Icon("select")
	@Name("Tarefa")
	public String callSelect()
	{
		try
		{
			form = control.select(getForm().getId());
			return "/WEB-INF/views/cliq/modulos/admin/Tarefa/ViewSelect.jsp";
		} catch (NotFoundException ex)
		{
			setMessages(ex.getMessages());
			return call();
		}
	}

	@Name("Nova")
	@Icon("insert")
	public String callInsert()
	{
		if (isPOST() && getMessages().isEmpty())
		{
			try
			{
				control.insert(getForm());
				return callSelect();
			} catch (AppException e)
			{
				setMessages(e.getMessages());
			}
		}
		return "/WEB-INF/views/cliq/modulos/admin/Tarefa/ViewInsert.jsp";
	}

	@Icon("update")
	@Name("Alterar")
	public String callUpdate() throws AppException
	{
		if (isGET())
			try
			{
				form = control.select(getForm().getId());
			} catch (NotFoundException ex)
			{
				setMessages(ex.getMessages());
				return call();
			}
		else if (getMessages().isEmpty())
			try
			{
				control.update(getForm());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}

		return "/WEB-INF/views/cliq/modulos/admin/Tarefa/ViewUpdate.jsp";
	}

	@Icon("delete")
	@Name("Remover")
	@Color("#660000")
	public String callDelete() throws AppException
	{
		control.delete(getForm());
		getMessages().add("A tarefa foi removida com sucesso.");
		return call();
	}

	public Collection<Categoria> getCategorias() throws AppException
	{
		return new CategoriaControl()
			.search(getForm().getCategoria().getRole());
	}

	public Tarefa getForm()
	{
		if (form == null)
			form = new Tarefa();
		return form;
	}

	public List<Tarefa> getPage()
	{
		return page;
	}
}
