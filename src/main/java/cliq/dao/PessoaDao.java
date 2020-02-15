package cliq.dao;

import cliq.entity.Chamado;
import cliq.entity.Pessoa;
import gate.base.Dao;
import gate.entity.Role;
import gate.entity.User;
import gate.error.NotFoundException;
import gate.sql.Link;
import gate.sql.condition.Condition;
import gate.sql.select.Select;
import gate.type.ID;
import java.util.List;

public class PessoaDao extends Dao
{

	public PessoaDao()
	{

	}

	public PessoaDao(Link link)
	{
		super(link);
	}

	public List<User> search(String filter)
	{
		return Select.expression("id")
			.expression("userID")
			.expression("name")
			.expression("email")
			.expression("Role$id").as("role.id")
			.from("gate.Uzer")
			.where(Condition.of("userID").eq(String.class, filter).or("email").eq(String.class, filter).or("name").lk(filter))
			.build()
			.connect(getLink())
			.fetchEntityList(User.class);
	}

	public List<User> search(Role role, String filter)
	{
		return Select.expression("id")
			.expression("userID")
			.expression("name")
			.expression("email")
			.expression("Role$id").as("role.id")
			.from("gate.Uzer")
			.where(Condition.of("Role$id").in(role.toList(Role::getId))
				.and(Condition.of("userID").eq(String.class, filter).or("email").eq(String.class, filter).or("name").lk(filter)))
			.build()
			.connect(getLink())
			.fetchEntityList(User.class);
	}

	public List<gate.entity.User> search(User filter)
	{
		return getLink()
			.from(Select.of(getClass().getResource("PessoaDao/search(User).sql"))
				.where(Condition
					.of("Uzer.active").isEq("true")
					.and("Role.id").eq(filter.getRole().getId())
					.and("Uzer.userID").eq(filter.getUserID())
					.and("Uzer.name").lk(filter.getName()))
				.orderBy("Uzer.name"))
			.fetchEntityList(User.class);
	}

	public Pessoa select(ID id) throws NotFoundException
	{
		return getLink()
			.from(getClass().getResource("PessoaDao/select(ID).sql"))
			.parameters(id)
			.fetchEntity(Pessoa.class)
			.orElseThrow(NotFoundException::new);
	}
}
