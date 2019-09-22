package cliq.modulos;

import cliq.control.SolicitanteControl;
import cliq.entity.Chamado;
import gate.annotation.Color;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.error.AppException;
import javax.inject.Inject;

public class HomologacaoScreen extends CLIQScreen
{

	private Chamado form;

	@Inject
	private SolicitanteControl control;

	@Override
	@Name("Homologação")
	@Icon("cliq.entity.Evento$Tipo:HOMOLOGACAO")
	public String call()
	{
		try
		{
			form = control.select(getForm().getId());
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return "/WEB-INF/views/cliq/modulos/Homologacao.jsp";
	}

	@Name("Sim")
	@Color("#006600")
	@Icon("cliq.entity.Evento$Tipo:HOMOLOGACAO_ACEITE")
	public String callAceitarHomologacao()
	{
		try
		{
			control.aceitarHomologacao(form, form.getEvento().getObservacoes(), form.getEvento().getAnexo());
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
	@Icon("cliq.entity.Evento$Tipo:HOMOLOGACAO_RECUSA")
	public String callRecusarHomologacao()
	{
		try
		{
			control.recusarHomologacao(form, form.getEvento().getObservacoes(), form.getEvento().getAnexo());
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
