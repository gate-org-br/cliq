package cliq.modulos.admin;

import cliq.control.CategoriaControl;
import cliq.control.EquipeControl;
import cliq.entity.Categoria;
import cliq.entity.Chamado;
import cliq.entity.Equipe;
import cliq.modulos.CLIQScreen;
import cliq.producer.EquipeSelecionadaProducer;
import gate.annotation.Color;
import gate.annotation.Current;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.constraint.Pattern;
import gate.constraint.Required;
import gate.error.AppException;
import gate.error.NotFoundException;
import gate.type.collections.StringList;
import java.util.ArrayList;
import javax.inject.Inject;

@Name("Categorias")
@Icon("cliq.entity.Categoria")
public class CategoriaScreen extends CLIQScreen
{

	private Object page;
	private Categoria form;
	private Chamado chamado;

	@Required
	@Name("Pesquisa")
	@Pattern("^.{3,}$")
	private String aprovador;

	@Required
	@Name("Pesquisa")
	@Pattern("^.{3,}$")
	private String homologador;

	@Inject
	@Current
	private Equipe equipe;

	@Inject
	private CategoriaControl control;

	@Inject
	private EquipeControl equipeControl;

	@Inject
	private EquipeSelecionadaProducer equipeSelecionada;

	@Override
	public String call()
	{
		try
		{
			page = control.search(equipe);
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return "/WEB-INF/views/cliq/modulos/admin/Categoria/View.jsp";
	}

	public String callImport()
	{
		page = control.search(getForm());
		return "/WEB-INF/views/cliq/modulos/admin/Categoria/ViewImport.jsp";
	}

	public String callSearch()
	{
		try
		{
			page = control.search(equipe);
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return "/WEB-INF/views/cliq/modulos/admin/Categoria/ViewSearch.jsp";
	}

	@Name("Categoria")
	@Icon("cliq.entity.Categoria")
	public String callSelect()
	{
		try
		{
			form = control.select(getForm().getId());

			if (form.getPessoaAprovadora().getId() != null)
				aprovador = form.getPessoaAprovadora().getName();
			else if (form.getEquipeAprovadora().getId() != null)
				aprovador = form.getEquipeAprovadora().getName();

			if (form.getPessoaHomologadora() != null)
				homologador = form.getPessoaHomologadora().getName();
			if (form.getEquipeHomologadora() != null)
				homologador = form.getEquipeHomologadora().getName();

			return "/WEB-INF/views/cliq/modulos/admin/Categoria/ViewSelect.jsp";
		} catch (AppException ex)
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
			try
			{
				getForm().setRole(equipe);
				control.insert(getForm());
				equipeSelecionada.set(equipeControl.select(equipe.getId()));
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
				return "/WEB-INF/views/cliq/modulos/admin/Categoria/ViewInsert.jsp";
			}

		getForm().setCampos(new StringList("#titulo", "@descricao", "@arquivo"));
		return "/WEB-INF/views/cliq/modulos/admin/Categoria/ViewInsert.jsp";
	}

	@Icon("update")
	@Name("Alterar")
	public String callUpdate() throws AppException
	{
		if (isGET())
			try
			{
				form = control.select(getForm().getId());

				if (form.getPessoaAprovadora().getId() != null)
					aprovador = form.getPessoaAprovadora().getName();
				else if (form.getEquipeAprovadora().getId() != null)
					aprovador = form.getEquipeAprovadora().getName();

				if (form.getPessoaHomologadora() != null)
					homologador = form.getPessoaHomologadora().getName();
				if (form.getEquipeHomologadora() != null)
					homologador = form.getEquipeHomologadora().getName();

			} catch (NotFoundException ex)
			{
				setMessages(ex.getMessages());
				return call();
			}
		else if (getMessages().isEmpty())
			try
			{
				control.update(getForm());
				equipeSelecionada.set(equipeControl.select(equipe.getId()));
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}

		return "/WEB-INF/views/cliq/modulos/admin/Categoria/ViewUpdate.jsp";
	}

	@Icon("delete")
	@Name("Remover")
	@Color("#660000")
	public String callDelete() throws AppException
	{
		try
		{
			control.delete(getForm());
			equipeSelecionada.set(equipeControl.select(equipe.getId()));
			getMessages().add("A categoria foi removida com sucesso.");
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return "/WEB-INF/views/cliq/modulos/admin/Categoria/ViewResult.jsp";
	}

	public String callCampos() throws AppException
	{
		control.update(getForm());
		return "/WEB-INF/views/cliq/modulos/admin/Categoria/ViewSelect.jsp";
	}

	public String callRelate()
	{
		try
		{
			control.relate(getForm(), getForm().getParent());
		} catch (AppException e)
		{
			setMessages(e.getMessages());
		}
		return call();
	}

	public Categoria getForm()
	{
		if (form == null)
			form = new Categoria();
		return form;
	}

	public void setForm(Categoria form)
	{
		this.form = form;
	}

	public Object getPage()
	{
		if (page == null)
			page = new ArrayList<>();
		return page;
	}

	public Chamado getChamado()
	{
		if (chamado == null)
			chamado = new Chamado();
		return chamado;
	}

	public String getAprovador()
	{
		return aprovador;
	}

	public void setAprovador(String aprovador)
	{
		this.aprovador = aprovador;
	}

	public String getHomologador()
	{
		return homologador;
	}

	public void setHomologador(String homologador)
	{
		this.homologador = homologador;
	}
}
