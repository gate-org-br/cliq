package cliq.modulos;

import cliq.control.SolicitanteControl;
import cliq.entity.Chamado;
import gate.annotation.Color;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.error.AppException;
import javax.inject.Inject;

public class AprovacaoScreen extends CLIQScreen
{

	private Chamado form;

	@Inject
	private SolicitanteControl control;

	@Override
	@Name("Aprovação")
	@Icon("cliq.entity.Evento$Tipo:APROVACAO")
	public String call()
	{
		try
		{
			form = control.select(getForm().getId());
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return "/WEB-INF/views/cliq/modulos/Aprovacao.jsp";
	}

	@Name("Sim")
	@Color("#006600")
	@Icon("cliq.entity.Evento$Tipo:APROVACAO_ACEITE")
	public String callAceitarAprovacao()
	{
		try
		{
			control.aceitarAprovacao(form, form.getEvento().getObservacoes(), form.getEvento().getAnexo());
			getMessages().add("Chamado aprovado com sucesso");
			return "/WEB-INF/views/cliq/modulos/Refresh.jsp";
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
			return call();
		}
	}

	@Name("Não")
	@Color("#660000")
	@Icon("cliq.entity.Evento$Tipo:APROVACAO_RECUSA")
	public String callRecusarAprovacao()
	{
		try
		{
			control.recusarAprovacao(form, form.getEvento().getObservacoes(), form.getEvento().getAnexo());
			getMessages().add("Chamado recusado com sucesso");
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
