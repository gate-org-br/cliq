package cliq.dao;

import cliq.entity.Chamado;
import gate.base.Dao;
import gate.entity.User;
import gate.error.ConstraintViolationException;
import gate.sql.condition.CompiledCondition;
import gate.sql.condition.Condition;
import gate.sql.select.Select;
import gate.sql.update.Update;
import gate.type.ID;
import java.util.List;

public class AlertaDao extends Dao
{

	public AlertaDao()
	{

	}

	public int count(User user)
	{
		return Select.expression("count(id)")
			.from("Chamado")
			.where(getCondition(user))
			.build()
			.connect(getLink())
			.fetchInt();
	}

	public List<Chamado> search(User user)
	{
		return Select.of(getClass().getResource("AlertaDao/search(User).sql"))
			.where(getCondition(user))
			.build()
			.connect(getLink())
			.fetchEntityList(Chamado.class);
	}

	public void update(List<Chamado> chamados) throws ConstraintViolationException
	{
		Update.table("Chamado")
			.from(Chamado.class)
			.set("notificados", Chamado::getNotificados)
			.where(Condition.of("id").from(Chamado.class).eq(Chamado::getId))
			.build()
			.connect(getLink())
			.execute(chamados);
	}

	public CompiledCondition getCondition(User user)
	{
		return Condition.of(Condition.of("Solicitante$id").eq(ID.class, user.getId())
			.or("Atendente$id").eq(ID.class, user.getId())
			.or(Condition.of("Atendente$id").isNull().and("Localizacao$id").eq(ID.class, user.getRole().getId())
				.or().exists(Select.expression("Compartilhamento.id").from("Compartilhamento").where(Condition.of("Compartilhamento.Chamado$id").eq("Chamado.id").and("Compartilhamento.Pessoa$id").eq(ID.class, user.getId())))
				.or().exists(Select.expression("Compartilhamento.id").from("Compartilhamento").where(Condition.of("Compartilhamento.Chamado$id").eq("Chamado.id").and("Compartilhamento.Equipe$id").eq(ID.class, user.getRole().getId())))))
			.and().not("notificados").lk(user.getId().toString());
	}
}
