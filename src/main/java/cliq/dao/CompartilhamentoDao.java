package cliq.dao;

import cliq.entity.Chamado;
import cliq.entity.Compartilhamento;
import gate.base.Dao;
import gate.entity.Role;
import gate.entity.User;
import gate.error.ConstraintViolationException;
import gate.error.NotFoundException;
import gate.sql.Link;
import gate.sql.condition.Condition;
import gate.sql.delete.Delete;
import gate.sql.insert.Insert;
import gate.type.ID;
import java.util.List;

public class CompartilhamentoDao extends Dao
{

	public CompartilhamentoDao()
	{

	}

	public CompartilhamentoDao(Link link)
	{
		super(link);
	}

	public List<Compartilhamento> search(Chamado chamado)
	{
		return getLink()
			.from(getClass().getResource("CompartilhamentoDao/search(Chamado).sql"))
			.parameters(chamado.getId())
			.fetchEntityList(Compartilhamento.class);
	}

	public boolean exists(Chamado chamado, Role role)
	{
		return getLink()
			.from("select exists (select id from Comparilhamento where Chamado$id = ? and Equipe$id = ?)")
			.parameters(chamado.getId(), role.getId())
			.fetchBoolean();
	}

	public boolean exists(Chamado chamado, User user)
	{
		return getLink()
			.from("select exists (select id from Comparilhamento where Chamado$id = ? and Pessoa$id = ?)")
			.parameters(chamado.getId(), user.getId())
			.fetchBoolean();
	}

	public Compartilhamento select(ID id) throws NotFoundException
	{
		return getLink().from(getClass().getResource("CompartilhamentoDao/select(ID).sql"))
			.parameters(id)
			.fetchEntity(Compartilhamento.class)
			.orElseThrow(NotFoundException::new);
	}

	public void insert(Compartilhamento compartilhamento) throws ConstraintViolationException
	{
		getLink()
			.prepare(Insert.into("Compartilhamento")
				.set("Chamado$id", compartilhamento.getChamado().getId())
				.set("Pessoa$id", compartilhamento.getPessoa().getId())
				.set("Equipe$id", compartilhamento.getEquipe().getId()))
			.execute();
	}

	public void delete(Compartilhamento compartilhamento) throws ConstraintViolationException
	{
		getLink()
			.prepare(Delete
				.from("Compartilhamento")
				.where(Condition
					.of("id").eq(ID.class, compartilhamento.getId())))
			.execute();
	}
}
