package cliq.dao;

import cliq.entity.SLA;
import gate.base.Dao;
import gate.error.AppException;
import gate.error.ConstraintViolationException;
import gate.error.NotFoundException;
import gate.sql.Link;
import gate.sql.condition.Condition;
import gate.sql.delete.Delete;
import gate.sql.insert.Insert;
import gate.sql.update.Update;
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
			.parameters(id)
			.fetchEntity(SLA.class)
			.orElseThrow(NotFoundException::new);
	}

	public void insert(SLA value) throws ConstraintViolationException
	{
		Insert.into("SLA")
			.set("nome", value.getNome())
			.set("Categoria$id", value.getCategoria().getId())
			.set("Origem$id", value.getOrigem().getId())
			.set("Solicitante$id", value.getSolicitante().getId())
			.set("projeto", value.getProjeto())
			.set("prioridade", value.getPrioridade())
			.set("complexidade", value.getComplexidade())
			.set("ini", value.getIni())
			.set("fim", value.getFim())
			.set("uini", value.getUini())
			.set("ufim", value.getUfim())
			.set("urgente", value.getUrgente())
			.set("precedencia", value.getPrecedencia())
			.build()
			.connect(getLink())
			.fetchGeneratedKey(ID.class).ifPresent(value::setId);
	}

	public void update(SLA value) throws AppException
	{

		if (Update.table("SLA")
			.set("nome", value.getNome())
			.set("Categoria$id", value.getCategoria().getId())
			.set("Origem$id", value.getOrigem().getId())
			.set("Solicitante$id", value.getSolicitante().getId())
			.set("projeto", value.getProjeto())
			.set("prioridade", value.getPrioridade())
			.set("complexidade", value.getComplexidade())
			.set("ini", value.getIni())
			.set("fim", value.getFim())
			.set("uini", value.getUini())
			.set("ufim", value.getUfim())
			.set("urgente", value.getUrgente())
			.set("precedencia", value.getPrecedencia())
			.where(Condition.of("id").eq(value.getId()))
			.build()
			.connect(getLink())
			.execute() == 0)
			throw new NotFoundException();
	}

	public void delete(SLA value) throws AppException
	{
		if (Delete.from("SLA")
			.where(Condition.of("id").eq(value.getId()))
			.build()
			.connect(getLink())
			.execute() == 0)
			throw new NotFoundException();
	}
}
