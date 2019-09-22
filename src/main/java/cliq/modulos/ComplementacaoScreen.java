package cliq.modulos;

import cliq.control.SolicitanteControl;
import cliq.entity.Chamado;
import gate.annotation.Color;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.error.AppException;
import javax.inject.Inject;

public class ComplementacaoScreen extends CLIQScreen
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
		return "/WEB-INF/views/cliq/modulos/Complementacao.jsp";
	}

	@Icon("commit")
	@Color("#006600")
	@Name("Complementar")
	public String callComplementar()
	{
		try
		{
			control.complementar(form, form.getEvento().getObservacoes(), form.getEvento().getAnexo());
			getMessages().add("Chamado complementado com sucesso");
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
