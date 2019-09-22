package cliq.producer;

import cliq.control.EquipeControl;
import cliq.entity.Equipe;
import gate.annotation.Current;
import gate.entity.User;
import gate.error.NotFoundException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@SessionScoped
public class EquipeSelecionadaProducer implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Inject
	@Current
	private User user;

	private Equipe equipe;

	@Inject
	private EquipeControl control;

	public void set(Equipe equipe)
	{
		this.equipe = equipe;
	}

	@Current
	@Produces
	@Named("equipe")
	public Equipe get() throws NotFoundException
	{
		if (equipe == null)
			equipe = control.select(user.getRole().getId());
		return equipe;
	}
}
