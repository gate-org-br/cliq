package cliq.service;

import cliq.control.RetomadaControl;
import gate.error.AppException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class Retomada
{

	@Inject
	private RetomadaControl control;

	@Schedule(hour = "*", minute = "*/5", persistent = false)
	public void execute()
	{
		try
		{
			control.execute();
		} catch (AppException | RuntimeException ex)
		{
			Logger.getLogger(Retomada.class.getName())
				.log(Level.SEVERE, ex.getMessage(), ex.getCause());
		}
	}
}
