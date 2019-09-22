package cliq.modulos;

import cliq.control.CompartilhamentoControl;
import cliq.entity.Compartilhamento;
import gate.annotation.Color;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.error.AppException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@Name("Compartilhamento")
@Icon("cliq.entity.Compartilhamento")
public class CompartilhamentoScreen extends CLIQScreen
{

	private Compartilhamento form;
	private List<Compartilhamento> page;

	@Inject
	private CompartilhamentoControl control;

	@Override
	@Name("Compartilhar")
	@Icon("cliq.entity.Compartilhamento")
	public String call()
	{
		page = control.search(getForm().getChamado());
		return "/WEB-INF/views/cliq/modulos/Compartilhamento/View.jsp";
	}

	@Icon("commit")
	@Color("#006600")
	@Name("Compartilhar")
	public String callPessoa()
	{
		try
		{
			control.compartilhar(getForm().getChamado(), getForm().getPessoa());
			getForm().setPessoa(null);
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return call();
	}

	@Icon("commit")
	@Color("#006600")
	@Name("Compartilhar")
	public String callEquipe()
	{
		try
		{
			control.compartilhar(getForm().getChamado(), getForm().getEquipe());
			getForm().setEquipe(null);
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return call();
	}

	@Icon("delete")
	@Name("Remover")
	@Color("#660000")
	public String callDelete()
	{
		try
		{
			control.descompartilhar(getForm());
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return call();
	}

	public Compartilhamento getForm()
	{
		if (form == null)
			form = new Compartilhamento();
		return form;
	}

	public List<Compartilhamento> getPage()
	{
		if (page == null)
			page = new ArrayList<>();
		return page;
	}
}
