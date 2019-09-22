package cliq.dao;

import cliq.entity.Tarefa;
import gate.base.Dao;
import gate.entity.Role;
import gate.error.AppException;
import gate.error.ConstraintViolationException;
import gate.error.NotFoundException;
import gate.sql.Link;
import gate.type.ID;
import java.util.List;

public class TarefaDao extends Dao
{

	public TarefaDao()
	{
		
	}

	public TarefaDao(Link link)
	{
		super(link);
	}

	public List<Tarefa> search()
	{
		return getLink()
				.from(getClass().getResource("TarefaDao/search().sql"))
				.constant()
				.fetchEntityList(Tarefa.class);
	}

	public List<Tarefa> search(Role role)
	{
		return getLink()
				.from(getClass().getResource("TarefaDao/search(Role).sql"))
				.parameters(role.getId())
				.fetchEntityList(Tarefa.class);
	}

	public Tarefa select(ID id) throws NotFoundException
	{
		return getLink().select(Tarefa.class)
				.properties("=id", "inicio", "periodo.valor", "periodo.unidade", "categoria.id", "categoria.nome", "atendente.id", "atendente.name", "origem.id", "origem.name", "nome", "descricao")
				.parameters(id)
				.orElseThrow(NotFoundException::new);
	}

	public void insert(Tarefa model) throws AppException
	{
		getLink().insert(Tarefa.class)
				.properties("inicio", "periodo.valor", "periodo.unidade", "categoria.id", "atendente.id", "origem.id", "nome", "descricao")
				.execute(model);
	}

	public void update(Tarefa model) throws ConstraintViolationException
	{
		getLink().update(Tarefa.class)
				.properties("=id", "inicio", "periodo.valor", "periodo.unidade", "categoria.id", "atendente.id", "origem.id", "nome", "descricao")
				.execute(model);
	}

	public void delete(Tarefa model) throws ConstraintViolationException
	{
		getLink().delete(Tarefa.class)
				.criteria("=id")
				.execute(model);
	}
}
