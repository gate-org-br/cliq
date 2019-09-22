package cliq.dao;

import cliq.entity.SLA;
import gate.base.Dao;
import gate.error.ConstraintViolationException;
import gate.error.NotFoundException;
import gate.sql.Link;
import gate.type.ID;
import java.util.List;

public class SLADao extends Dao
{

	public SLADao()
	{
		
	}

	public SLADao(Link link)
	{
		super(link);
	}

	public List<SLA> search()
	{
		return getLink()
				.from(getClass().getResource("SLADao/search().sql"))
				.constant()
				.fetchEntityList(SLA.class);
	}

	public SLA select(ID id) throws NotFoundException
	{
		return getLink()
				.from(getClass().getResource("SLADao/select(ID).sql"))
				.parameters(ID.class, id)
				.fetchEntity(SLA.class)
				.orElseThrow(NotFoundException::new);
	}

	public void insert(SLA value) throws ConstraintViolationException
	{
		getLink().insert(SLA.class)
				.properties("nome", "categoria.id", "origem.id", "solicitante.id", "projeto", "prioridade", "complexidade", "ini", "fim", "uini", "ufim", "urgente", "precedencia")
				.execute(value);
	}

	public void update(SLA value) throws ConstraintViolationException
	{
		getLink().update(SLA.class)
				.properties("=id", "nome", "categoria.id", "origem.id", "solicitante.id", "projeto", "prioridade", "complexidade", "ini", "fim", "uini", "ufim", "urgente", "precedencia")
				.execute(value);
	}

	public void delete(SLA value) throws ConstraintViolationException
	{
		getLink().delete(SLA.class)
				.criteria("=id")
				.execute(value);
	}
}
