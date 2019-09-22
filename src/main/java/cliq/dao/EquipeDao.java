package cliq.dao;

import cliq.entity.Categoria;
import cliq.entity.Equipe;
import cliq.type.Complexidade;
import cliq.type.Prioridade;
import cliq.type.Visibilidade;
import gate.base.Dao;
import gate.entity.Role;
import gate.error.NotFoundException;
import gate.sql.Command;
import gate.sql.Cursor;
import gate.sql.Link;
import gate.sql.condition.Condition;
import gate.sql.select.Select;
import gate.type.ID;
import java.util.List;

public class EquipeDao extends Dao
{

	public EquipeDao()
	{

	}

	public EquipeDao(Link link)
	{
		super(link);
	}

	public List<Equipe> search()
	{
		return getLink()
			.from(getClass().getResource("EquipeDao/search().sql"))
			.constant()
			.fetchEntityList(Equipe.class);
	}

	public List<Role> search(String filter)
	{
		return Select.expression("id")
			.expression("roleID")
			.expression("name")
			.expression("email")
			.from("gate.Role")
			.where(Condition.of("roleID").eq(String.class, filter)
				.or("email").eq(String.class, filter)
				.or("name").lk(filter))
			.build()
			.connect(getLink())
			.fetchEntityList(Role.class);
	}

	public List<Role> search(Role role, String filter)
	{
		return Select.expression("id")
			.expression("roleID")
			.expression("name")
			.expression("email")
			.from("gate.Role")
			.where(Condition
				.of("id").in(role.toList(Role::getId))
				.and(Condition.of("roleID").eq(String.class, filter)
					.or("email").eq(String.class, filter)
					.or("name").lk(filter)))
			.build()
			.connect(getLink())
			.fetchEntityList(Role.class);
	}

	public boolean exists(Role role)
	{
		return getLink().from("select exists (select id from Role where id = ?)")
			.parameters(role.getId())
			.fetchObject(Boolean.class)
			.orElse(Boolean.FALSE);
	}

	public Equipe select(ID id) throws NotFoundException
	{
		try (Command command = getLink().from(getClass().getResource("EquipeDao/select(ID).sql"))
			.parameters(id)
			.createCommand())
		{
			try (Cursor cursor = command.getCursor())
			{
				if (!cursor.next())
					throw new NotFoundException();

				Equipe equipe = new Equipe();
				equipe.setId(cursor.getValue(ID.class, "id"));
				equipe.setEmail(cursor.getValue(String.class, "email"));
				equipe.setRoleID(cursor.getValue(String.class, "roleID"));
				equipe.getRole().setId(cursor.getValue(ID.class, "role.id"));
				equipe.getRole().setName(cursor.getValue(String.class, "role.name"));
				equipe.setName(cursor.getValue(String.class, "name"));
				equipe.setDescription(cursor.getValue(String.class, "description"));

				do
				{
					gate.entity.User user = new gate.entity.User();
					user.setRole(equipe);
					user.setId(cursor.getValue(ID.class, "user.id"));
					user.setUserID(cursor.getValue(String.class, "user.userID"));
					user.setName(cursor.getValue(String.class, "user.name"));
					user.setEmail(cursor.getValue(String.class, "user.email"));

					if (user.getId() != null && !equipe.getUsers().contains(user))
						equipe.getUsers().add(user);

					Categoria categoria = new Categoria();
					categoria.setId(cursor.getValue(ID.class, "categoria.id"));
					categoria.setVisibilidade(cursor.getValue(Visibilidade.class, "categoria.visibilidade"));
					categoria.setNome(cursor.getValue(String.class, "categoria.nome"));
					categoria.setTriagem(cursor.getValue(Boolean.class, "categoria.triagem"));
					categoria.setPrioridade(cursor.getValue(Prioridade.class, "categoria.prioridade"));
					categoria.setComplexidade(cursor.getValue(Complexidade.class, "categoria.complexidade"));
					categoria.setProjeto(cursor.getValue(Boolean.class, "categoria.projeto"));
					categoria.setDescricao(cursor.getValue(String.class, "categoria.descricao"));
					categoria.getParent().setId(cursor.getValue(ID.class, "categoria.parent.id"));
					if (categoria.getId() != null && !equipe.getCategorias().contains(categoria))
						equipe.getCategorias().add(categoria);
				} while (cursor.next());

				equipe.getCategorias()
					.forEach(p -> equipe.getCategorias().stream().filter(c -> c.getParent().equals(p))
					.forEach(c -> p.getChildren().add(c.setParent(p))));
				return equipe;
			}
		}
	}
}
