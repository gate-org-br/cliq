package cliq.control;

import cliq.dao.AlertaDao;
import cliq.entity.Chamado;
import gate.annotation.Current;
import gate.base.Control;
import gate.entity.User;
import gate.error.ConstraintViolationException;
import java.util.List;
import javax.inject.Inject;

public class AlertaControl extends Control
{

	@Inject
	@Current
	private User user;

	public AlertaControl()
	{
		super();
	}

	public int count()
	{
		try (AlertaDao dao = new AlertaDao())
		{
			return dao.count(user);
		}
	}

	public List<Chamado> search()
	{
		try (AlertaDao dao = new AlertaDao())
		{
			return dao.search(user);
		}
	}

	public void delete() throws ConstraintViolationException
	{
		try (AlertaDao dao = new AlertaDao())
		{
			dao.beginTran();
			List<Chamado> chamados = dao.search(user);
			chamados.forEach(e -> e.getNotificados().add(user.getId().toString()));
			dao.update(chamados);
			dao.commit();
		}
	}
}
