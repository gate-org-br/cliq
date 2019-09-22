package cliq.producer;

import cliq.control.AlertaControl;
import gate.annotation.Current;
import gate.error.NotFoundException;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
public class AlertaProducer
{

	@Inject
	private AlertaControl control;

	@Current
	@Produces
	@Named("alertas")
	public int get() throws NotFoundException
	{
		return control.count();
	}

}
