package cliq.modulos.admin;

import cliq.control.PessoaControl;
import cliq.control.SLAControl;
import cliq.entity.SLA;
import cliq.modulos.CLIQScreen;
import gate.annotation.Color;
import gate.annotation.CopyIcon;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.entity.User;
import gate.error.AppException;
import gate.error.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@CopyIcon(SLA.class)
@Name("Política de SLA")
public class SLAScreen extends CLIQScreen
{

	private SLA form;
	private List<SLA> page;

	@Inject
	private SLAControl control;

	@Override
	public String call()
	{
		page = control.search();
		return "/WEB-INF/views/cliq/modulos/admin/SLA/View.jsp";
	}

	@Icon("select")
	@Name("Detalhe")
	public String callSelect()
	{
		try
		{
			form = control.select(getForm().getId());
			return "/WEB-INF/views/cliq/modulos/admin/SLA/ViewSelect.jsp";
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
			try
			{
				control.insert(getForm());
				return callSelect();
			} catch (AppException e)
			{
				setMessages(e.getMessages());
			}

		return "/WEB-INF/views/cliq/modulos/admin/SLA/ViewInsert.jsp";
	}

	@Icon("update")
	@Name("Alterar")
	public String callUpdate()
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

		return "/WEB-INF/views/cliq/modulos/admin/SLA/ViewUpdate.jsp";
	}

	@Icon("delete")
	@Name("Remover")
	@Color("#660000")
	public String callDelete()
	{
		try
		{
			control.delete(getForm());
		} catch (AppException e)
		{
			setMessages(e.getMessages());
		}
		return call();
	}

	public SLA getForm()
	{
		if (form == null)
			form = new SLA();
		return form;
	}

	public List<SLA> getPage()
	{
		if (page == null)
			page = new ArrayList<>();
		return page;
	}

	@Override
	public List<User> getUsers()
	{
		return new PessoaControl().search(new User());
	}
}
