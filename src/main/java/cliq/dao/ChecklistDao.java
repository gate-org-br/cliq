package cliq.dao;

import cliq.entity.Chamado;
import cliq.type.Checklist;
import gate.base.Dao;
import gate.error.ConstraintViolationException;
import gate.sql.Link;
import gate.sql.condition.Condition;
import gate.sql.update.Update;
import gate.type.ID;

public class ChecklistDao extends Dao
{

	public ChecklistDao()
	{
	}

	public ChecklistDao(Link link)
	{
		super(link);
	}

	public void update(Chamado chamado, Checklist checklist) throws ConstraintViolationException
	{
		Update.table("Chamado")
			.set("checklist", checklist)
			.where(Condition.of("id").eq(ID.class, chamado.getId()))
			.build()
			.connect(getLink())
			.execute();
	}
}
