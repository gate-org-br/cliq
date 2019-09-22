package cliq.modulos;

import cliq.control.EquipeControl;
import cliq.entity.Equipe;
import cliq.producer.EquipeSelecionadaProducer;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.entity.Role;
import gate.error.AppException;
import gate.error.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class EquipeScreen extends CLIQScreen
{

	private Role form;
	private List<Role> page;

	@Inject
	private EquipeControl control;

	@Inject
	private EquipeSelecionadaProducer equipeSelecionada;

	@Icon("search")
	@Name("Pesquisar")
	public String callSearch()
	{
		try
		{
			if (getForm().getName() != null
				&& getForm().getName().length() >= 3)
				page = control.search(getForm().getName());
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return "/WEB-INF/views/cliq/modulos/Equipe/ViewSearch.jsp";
	}

	public Object callChoose()
	{
		if (isPOST())
		{
			try
			{
				Equipe equipe = control.select(getForm().getId());
				if (!equipe.getUsers().contains(getUser()))
					equipe.getUsers().add(getUser());
				equipeSelecionada.set(equipe);
				return call();
			} catch (NotFoundException ex)
			{
				setMessages(ex.getMessages());
			}
		}

		return "/WEB-INF/views/cliq/modulos/Equipe/ViewChoose.jsp";
	}

	public Role getForm()
	{
		if (form == null)
			form = new Role();
		return form;
	}

	public List<Role> getPage()
	{
		if (page == null)
			page = new ArrayList<>();
		return page;
	}

}
