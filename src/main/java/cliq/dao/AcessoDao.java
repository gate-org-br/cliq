package cliq.dao;

import cliq.entity.Categoria;
import cliq.entity.Contato;
import cliq.entity.Equipe;
import gate.base.Dao;
import gate.error.ConstraintViolationException;
import gate.sql.Link;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AcessoDao extends Dao
{

	public AcessoDao(Link link)
	{
		super(link);
	}

	public void delete(Categoria categoria) throws ConstraintViolationException
	{
		getLink()
				.prepare("delete from Acesso where Categoria$id = ?")
				.parameters(categoria.getId())
				.execute();
	}

	public void delete(Contato contato) throws ConstraintViolationException
	{
		getLink()
				.prepare("delete from Acesso where Contato$id = ?")
				.parameters(contato.getId())
				.execute();
	}

	public List<Equipe> search(Categoria categoria)
	{
		return getLink()
				.from("select Role.id as id, Role.name as name from gate.Role left join Acesso on Acesso.Role$id = Role.id where Acesso.Categoria$id = ?")
				.parameters(categoria.getId())
				.fetchEntityList(Equipe.class
				);
	}

	public List<Equipe> search(Contato contato)
	{
		return getLink()
				.from("select Role.id as id, Role.name as name from gate.Role left join Acesso on Acesso.Role$id = Role.id where Acesso.Contato$id = ?")
				.parameters(contato.getId())
				.fetchEntityList(Equipe.class
				);
	}

	public void insert(Contato contato, List<Equipe> roles) throws ConstraintViolationException
	{
		getLink()
				.prepare("insert into Acesso (Contato$id, Role$id) values (?, ?)")
				.batch(roles.stream().map(e -> Arrays.asList(contato.getId(), e.getId())).collect(Collectors.toList()))
				.execute();
	}

	public void insert(Categoria categoria, List<Equipe> roles) throws ConstraintViolationException
	{
		getLink()
				.prepare("insert into Acesso (Categoria$id, Role$id) values (?, ?)")
				.batch(roles.stream().map(e -> Arrays.asList(categoria.getId(), e.getId())).collect(Collectors.toList()))
				.execute();
	}

}
