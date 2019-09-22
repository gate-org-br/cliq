package cliq.modulos;

import cliq.control.EventoControl;
import cliq.entity.Evento;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.base.Screen;
import gate.error.AppException;
import gate.error.NotFoundException;
import javax.inject.Inject;

public class EventoScreen extends Screen
{

	private Evento form;

	@Inject
	private EventoControl control;

	@Icon("select")
	@Name("Detalhe")
	public String callSelect()
	{
		try
		{
			form = control.select(getForm().getId());
			return "/WEB-INF/views/cliq/modulos/Evento/ViewSelect.jsp";
		} catch (NotFoundException ex)
		{
			setMessages(ex.getMessages());
			return "/WEB-INF/views/cliq/modulos/Evento/ViewResult.jsp";
		}
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
				return "/WEB-INF/views/cliq/modulos/Evento/ViewResult.jsp";
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
		return "/WEB-INF/views/cliq/modulos/Evento/ViewUpdate.jsp";
	}

	public Evento getForm()
	{
		if (form == null)
			form = new Evento();
		return form;
	}

}
