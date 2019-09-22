package cliq.dao;

import cliq.entity.Chamado;
import gate.base.Dao;
import gate.entity.User;
import java.util.List;

public class MensageiroDao extends Dao
{

	public List<User> publico(Chamado chamado)
	{
		return getLink().from(getClass().getResource("MensageiroDao/publico().sql"))
			.parameters(chamado.getLocalizacao().getId(),
				chamado.getEquipeAprovadora().getId(),
				chamado.getId(),
				chamado.getSolicitante().getId(),
				chamado.getAtendente().getId(),
				chamado.getPessoaAprovadora().getId(),
				chamado.getId())
			.fetchEntityList(User.class);
	}

	public List<User> privado(Chamado chamado)
	{
		return getLink().from(getClass().getResource("MensageiroDao/privado().sql"))
			.parameters(chamado.getLocalizacao().getId(),
				chamado.getEquipeAprovadora().getId(),
				chamado.getAtendente().getId(),
				chamado.getPessoaAprovadora().getId())
			.fetchEntityList(User.class);
	}
}
