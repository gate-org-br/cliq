package cliq.service;

import cliq.control.FeedbackControl;
import gate.error.AppException;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import org.slf4j.Logger;

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class Feedback
{

	@Inject
	private Logger logger;

	@Inject
	private FeedbackControl control;

	@Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)
	public void execute()
	{
		try
		{
			control.execute();
		} catch (AppException | RuntimeException ex)
		{
			logger.error(ex.getMessage(), ex.getCause());
		}
	}
}
