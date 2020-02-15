package cliq.modulos.atendimento;

import cliq.control.ContatoControl;
import cliq.entity.Contato;
import cliq.modulos.CLIQScreen;
import gate.annotation.Color;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.error.AppException;
import gate.util.Page;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

@Name("Contatos")
@Icon("cliq.entity.Contato")
public class ContatoScreen extends CLIQScreen
{

	private Contato form;
	private Page<Contato> page;

	@Inject
	private ContatoControl control;

	@Override
	@Name("Contatos")
	@Icon("cliq.entity.Contato")
	public String call()
	{
		try
		{
			if (isPOST())
				page = paginate(control.search(getForm().getNome()));
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return "/WEB-INF/views/cliq/modulos/atendimento/Contato/View.jsp";
	}

	@Icon("search")
	@Name("Pesquisar")
	public String callSearch()
	{
		try
		{
			if (getForm().getNome() != null && getForm().getNome().length() >= 3)
				page = paginate(control.search(getForm().getNome()));
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return "/WEB-INF/views/cliq/modulos/atendimento/Contato/ViewSearch.jsp";
	}

	@Icon("select")
	@Name("Contato")
	public String callSelect()
	{
		try
		{
			form = control.select(getForm().getTipo(), getForm().getId());
			return "/WEB-INF/views/cliq/modulos/atendimento/Contato/ViewSelect.jsp";
		} catch (AppException e)
		{
			setMessages(e.getMessages());
			return call();
		}
	}

	@Name("Novo")
	@Icon("insert")
	public String callInsert()
	{
		if (isPOST() && getMessages().isEmpty())
			try
			{
				control.insert(getForm());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		return "/WEB-INF/views/cliq/modulos/atendimento/Contato/ViewInsert.jsp";
	}

	@Icon("update")
	@Name("Alterar")
	public String callUpdate()
	{
		if (isGET())
			try
			{
				form = control.select(getForm().getTipo(), getForm().getId());
			} catch (AppException ex)
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
		return "/WEB-INF/views/cliq/modulos/atendimento/Contato/ViewUpdate.jsp";

	}

	@Icon("delete")
	@Name("Remover")
	@Color("#660000")
	public String callDelete()
	{
		try
		{
			control.delete(getForm());
			getMessages().add("O CONTATO foi removido com sucesso.");
			return call();
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
			return callSelect();
		}
	}

	public Contato getForm()
	{
		if (form == null)
			form = new Contato();
		return form;
	}

	public Page<Contato> getPage()
	{
		return page;
	}

	public List<Contato.Tipo> getTipos()
	{
		return Arrays.asList(Contato.Tipo.CIDADAO,
			Contato.Tipo.EMPRESA);
	}
}
