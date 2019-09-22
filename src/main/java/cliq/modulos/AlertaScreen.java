package cliq.modulos;

import cliq.control.AlertaControl;
import cliq.entity.Chamado;
import gate.error.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

public class AlertaScreen extends CLIQScreen
{

	private List<Chamado> page;

	@Inject
	private AlertaControl control;

	@Override
	public Integer call()
	{
		return control.count();
	}

	public String callSelect()
	{
		page = control.search();
		return "/WEB-INF/views/cliq/modulos/Alerta.jsp";
	}

	public String callDelete()
	{
		try
		{
			control.delete();
		} catch (ConstraintViolationException ex)
		{
			setMessages(ex.getMessages());
		}
		return callSelect();
	}

	public Collection<Chamado> getPage()
	{
		return page;
	}

	public List<String> getLinks()
	{
		List<String> links = new ArrayList<>();
		for (Chamado chamado : getPage())
		{
			if (getUser().equals(chamado.getSolicitante()))
				links.add(String.format("'Gate?MODULE=cliq.modulos.solicitante&SCREEN=Solicitante&ACTION=Select&form.id=%s'", chamado.getId()));
			else if (getUser().equals(chamado.getAtendente()))
				links.add(String.format("'Gate?MODULE=cliq.modulos.atendimento&SCREEN=Pessoais&ACTION=Select&form.id=%s'", chamado.getId()));
			else
				links.add(String.format("'Gate?MODULE=cliq.modulos.atendimento&SCREEN=DaEquipe&ACTION=Select&form.id=%s'", chamado.getId()));
		}
		return links;
	}
}
