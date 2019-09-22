package cliq.dao;

import cliq.entity.Contato;
import cliq.type.Visibilidade;
import gate.base.Dao;
import gate.error.AppException;
import gate.error.ConstraintViolationException;
import gate.error.NotFoundException;
import gate.sql.Link;
import gate.sql.condition.Condition;
import gate.sql.insert.Insert;
import gate.sql.select.Select;
import gate.sql.update.Update;
import gate.type.ID;
import java.util.List;

public class ContatoDao extends Dao
{

	public ContatoDao()
	{

	}

	public ContatoDao(Link link)
	{
		super(link);
	}

	public List<Contato> search(String string)
	{

		return Select.of(getClass().getResource("ContatoDao/search(Contato).sql"))
			.where(Condition
				.of(Condition.of("Role$id").eq(ID.class, getUser().getRole().getId())
					.or(Condition.of("visibilidade").eq(Visibilidade.PUBLICA)).and().not().exists(Select.expression("id").from("Acesso").where(Condition.of("Acesso.Contato$id").isEq("Contatos.id").and("Acesso.Role$id").eq(ID.class, getUser().getRole().getId())
					.or(Condition.of("visibilidade").eq(Visibilidade.PRIVADA)).and().exists(Select.expression("id").from("Acesso").where(Condition.of("Acesso.Contato$id").isEq("Contatos.id").and("Acesso.Role$id").eq(ID.class, getUser().getRole().getId()))))))
				.and(Condition.FALSE.or("Contatos.email").eq(string).or("Contatos.login").eq(string).or("Contatos.nome").lk(string)))
			.build()
			.connect(getLink())
			.fetchEntityList(Contato.class);
	}

	public Contato select(Contato.Tipo tipo, ID id) throws AppException
	{
		return getLink()
			.from(getClass().getResource("ContatoDao/select(Contato.Tipo, ID).sql"))
			.parameters(tipo, id).fetchEntity(Contato.class)
			.orElseThrow(NotFoundException::new);
	}

	public void insert(Contato value) throws ConstraintViolationException
	{
		getLink().prepare(Insert
			.into("Contato")
			.set("Role$id", getUser().getRole().getId())
			.set("tipo", value.getTipo())
			.set("visibilidade", value.getVisibilidade())
			.set("nome", value.getNome())
			.set("telefone", value.getTelefone())
			.set("celular", value.getCelular())
			.set("email", value.getEmail())
			.set("comentarios", value.getComentarios()))
			.fetchGeneratedKeys(ID.class)
			.forEach(value::setId);
	}

	public void update(Contato model) throws AppException
	{

		Update.table("Contato")
			.set("tipo", model.getTipo())
			.set("visibilidade", model.getVisibilidade())
			.set("nome", model.getNome())
			.set("telefone", model.getTelefone())
			.set("celular", model.getCelular())
			.set("email", model.getEmail())
			.set("comentarios", model.getComentarios())
			.where(Condition.of("id").eq(ID.class, model.getId())
				.and(Condition.of("Role$id").eq(ID.class, getUser().getRole().getId())
					.or(Condition.of("visibilidade").eq(Visibilidade.PUBLICA)).and().not().exists(Select.expression("id").from("Acesso").where(Condition.of("Acesso.Contato$id").isEq("Contato.id").and("Acesso.Role$id").eq(ID.class, getUser().getRole().getId())
					.or(Condition.of("visibilidade").eq(Visibilidade.PRIVADA)).and().exists(Select.expression("id").from("Acesso").where(Condition.of("Acesso.Contato$id").isEq("Contato.id").and("Acesso.Role$id").eq(ID.class, getUser().getRole().getId())))))))
			.build()
			.connect(getLink())
			.execute();
	}

	public void delete(Contato model) throws AppException
	{
		if (getLink()
			.prepare("delete from Contato where id = ?")
			.parameters(model.getId()).execute() == 0)
			throw new AppException("Tentativa de remover contato inexistente.");
	}
}
