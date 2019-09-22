package cliq.control;

import cliq.dao.SLADao;
import cliq.entity.SLA;
import gate.base.Control;
import gate.constraint.Constraints;
import gate.error.AppException;
import gate.error.ConstraintViolationException;
import gate.error.NotFoundException;
import gate.type.ID;
import java.util.List;

public class SLAControl extends Control
{

	public SLAControl()
	{
		super();
	}

	public List<SLA> search()
	{
		try (SLADao dao = new SLADao())
		{
			return dao.search();
		}
	}

	public SLA select(ID id) throws NotFoundException
	{
		try (SLADao dao = new SLADao())
		{
			return dao.select(id);
		}
	}

	public void insert(SLA sla) throws AppException
	{
		Constraints.validate(sla, "nome", "projeto", "prioridade", "complexidade", "ini", "fim", "uini", "ufim", "urgente");
		if (sla.getIni() * sla.getUini().getFator() > sla.getFim() * sla.getUfim().getFator())
			throw new AppException("O tempo de solução não pode ser menor do que o de resposta.");
		try (SLADao dao = new SLADao())
		{
			dao.insert(sla);
		} catch (ConstraintViolationException e)
		{
			throw new AppException(e.getMessage());
		}
	}

	public void update(SLA sla) throws AppException
	{
		Constraints.validate(sla, "nome", "projeto", "prioridade", "complexidade", "ini", "fim", "uini", "ufim", "urgente");
		if (sla.getIni() * sla.getUini().getFator() > sla.getFim() * sla.getUfim().getFator())
			throw new AppException("O tempo de solução não pode ser menor do que o de resposta.");
		try (SLADao dao = new SLADao())
		{
			dao.update(sla);
		} catch (ConstraintViolationException e)
		{
			throw new AppException(e.getMessage());
		}
	}

	public void delete(SLA sla) throws AppException
	{
		try (SLADao dao = new SLADao())
		{
			dao.delete(sla);
		} catch (ConstraintViolationException e)
		{
			throw new AppException(e.getMessage());
		}
	}
}
