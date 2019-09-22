package cliq.producer;

import cliq.control.EstatisticaControl;
import cliq.entity.Equipe;
import cliq.type.Estatisticas;
import gate.annotation.Current;
import gate.error.NotFoundException;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
public class EstatisticasProducer implements Serializable
{

	@Inject
	@Current
	private Equipe equipe;

	private Estatisticas estatisticas;

	@Inject
	private EstatisticaControl control;

	private static final long serialVersionUID = 1L;

	@Current
	@Produces
	@Named("estatisticas")
	public Estatisticas search() throws NotFoundException
	{
		if (estatisticas == null)
			estatisticas = control.search(equipe);
		return estatisticas;
	}
}
