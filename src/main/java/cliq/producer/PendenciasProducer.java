package cliq.producer;

import cliq.control.PendenciaControl;
import cliq.entity.Chamado;
import gate.annotation.Current;
import gate.error.NotFoundException;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
public class PendenciasProducer implements Serializable
{

	@Inject
	private PendenciaControl control;

	private static final long serialVersionUID = 1L;

	@Current
	@Produces
	@Named("pendencias")
	public List<Chamado> search() throws NotFoundException
	{
		return control.search();
	}
}
