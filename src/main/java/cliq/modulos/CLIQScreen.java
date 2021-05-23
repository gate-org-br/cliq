package cliq.modulos;

import cliq.control.PessoaControl;
import gate.base.Screen;
import gate.entity.User;
import gate.io.URL;
import java.util.List;
import javax.inject.Inject;

public class CLIQScreen extends Screen
{

	@Inject
	private PessoaControl pessoaControl;

	public Object call()
	{
		if (getUser().checkAccess("cliq.modulos.atendimento", "DaEquipe", null))
			return new URL(String.format("Gate?MODULE=cliq.modulos.atendimento&SCREEN=DaEquipe&page.id=%d",
				getUser().getRole().getId().getValue()));

		return new URL("Gate?MODULE=cliq.modulos.solicitante&ACTION=Insert");
	}

	public List<User> getUsers()
	{
		return pessoaControl.search(new User());
	}
}
