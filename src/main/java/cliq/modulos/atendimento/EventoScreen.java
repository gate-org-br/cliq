package cliq.modulos.atendimento;

import cliq.control.EventoControl;
import cliq.entity.Evento;
import gate.annotation.CopyIcon;
import gate.annotation.Name;
import gate.base.Screen;
import java.util.List;
import javax.inject.Inject;

@Name("Eventos")
@CopyIcon(Evento.class)
public class EventoScreen extends Screen
{

	private Evento form;
	private Object page;
	private Evento.Agrupamento agrupamento;

	@Inject
	private EventoControl control;

	public String call()
	{

		if (isPOST())
			if (getAgrupamento() == null)
			{
				List<Evento> eventos = control.search(getForm());
				if (eventos.size() == 1001 && eventos.remove(1000) != null)
					getMessages().add("Sua busca foi limitada a 1000 registros. \\nCaso seu objetivo seja apenas contar o número de eventos, selecione um agrupamento para fazer a pesquisa.");
				page = paginate(ordenate(eventos));
			} else
				page = ordenate(control.search(getAgrupamento(), getForm()));

		return "/WEB-INF/views/cliq/modulos/atendimento/Evento/View.jsp";
	}

	public Evento getForm()
	{
		if (form == null)
			form = new Evento();
		return form;
	}

	public Object getPage()
	{
		return page;
	}

	public Evento.Agrupamento getAgrupamento()
	{
		return agrupamento;
	}

	public void setAgrupamento(Evento.Agrupamento agrupamento)
	{
		this.agrupamento = agrupamento;
	}
}
