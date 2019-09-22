package cliq.modulos;

import cliq.control.SolicitanteControl;
import cliq.entity.Chamado;
import gate.error.AppException;
import javax.inject.Inject;

public class AvaliacaoScreen extends CLIQScreen
{

	private Chamado form;

	@Inject
	private SolicitanteControl control;

	@Override
	public String call()
	{
		try
		{
			form = control.select(getForm().getId());
			return "/WEB-INF/views/cliq/modulos/Avaliacao.jsp";
		} catch (AppException ex)
		{
			return ex.getMessage();
		}
	}

	public String callAvaliar()
	{
		try
		{
			control.avaliar(getForm(),
				getForm().getNota(),
				getForm().getEvento().getObservacoes(),
				form.getEvento().getAnexo());
			getMessages().add("Chamado avaliado com sucesso");

			return "/WEB-INF/views/cliq/modulos/Refresh.jsp";
		} catch (AppException e)
		{
			setMessages(e.getMessages());
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
