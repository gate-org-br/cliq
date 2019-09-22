package cliq.modulos;

import cliq.control.SolicitanteControl;
import cliq.entity.Chamado;
import gate.annotation.Color;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.error.AppException;
import javax.inject.Inject;

public class FeedbackScreen extends CLIQScreen
{

	private Chamado form;

	@Inject
	private SolicitanteControl control;

	@Override
	@Name("Feedback")
	@Icon("cliq.entity.Evento$Tipo:FEEDBACK")
	public String call()
	{
		try
		{
			form = control.select(getForm().getId());
			return "/WEB-INF/views/cliq/modulos/Feedback.jsp";
		} catch (AppException ex)
		{
			return ex.getMessage();
		}
	}

	@Name("Sim")
	@Color("#006600")
	@Icon("cliq.entity.Evento$Tipo:FEEDBACK_ACEITE")
	public String callAceitarFeedback()
	{
		try
		{
			control.aceitarFeedback(getForm(), getForm().getEvento().getObservacoes(), form.getEvento().getAnexo());
			getMessages().add("Solução do chamado aceita com sucesso");
			return "/WEB-INF/views/cliq/modulos/Refresh.jsp";
		} catch (AppException e)
		{
			setMessages(e.getMessages());
			return call();
		}
	}

	@Name("Não")
	@Color("#660000")
	@Icon("cliq.entity.Evento$Tipo:FEEDBACK_RECUSA")
	public String callRecusarFeedback()
	{
		try
		{
			control.recusarFeedback(getForm(), getForm().getEvento().getObservacoes(), form.getEvento().getAnexo());
			getMessages().add("Solução do chamado recusada com sucesso");
			return "/WEB-INF/views/cliq/modulos/Refresh.jsp";
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
			return call();
		}
	}

	public Chamado getForm()
	{
		if (form == null)
			form = new Chamado();
		return form;
	}
}
