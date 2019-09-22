package cliq.dao;

import cliq.entity.Chamado;
import cliq.entity.Equipe;
import cliq.entity.Evento;
import gate.base.Dao;
import gate.error.ConstraintViolationException;
import gate.error.NotFoundException;
import gate.sql.Link;
import gate.sql.condition.CompiledCondition;
import gate.sql.condition.Condition;
import gate.sql.insert.Insert;
import gate.sql.select.Select;
import gate.sql.update.Update;
import gate.type.ID;
import gate.type.Percentage;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class EventoDao extends Dao
{

	public EventoDao()
	{

	}

	public EventoDao(Link link)
	{
		super(link);
	}

	public List<Evento> search(Equipe equipe, Evento filter)
	{
		return Select.of(getClass().getResource("EventoDao/search(Evento).sql"))
			.where(EventoCondition.of(equipe, filter))
			.build()
			.connect(getLink())
			.fetchEntityList(Evento.class);

	}

	public List<Map<String, Object>> search(Evento.Agrupamento agrupamento, Equipe equipe, Evento filter)
	{
		List<Map<String, Object>> result = getLink().from(Select
			.expression(agrupamento.getCampo()).as("agrupamento")
			.expression("count(Evento.id)").as("quantidade")
			.expression("0").as("percentual")
			.from("Evento")
			.join("Chamado").on(Condition.of("Evento.Chamado$id").isEq("Chamado.id"))
			.leftJoin("gate.Uzer").on(Condition.of("Evento.Uzer$id").isEq("Uzer.id"))
			.leftJoin("gate.Role").as("Origem").on(Condition.of("Evento.Origem$id").isEq("Origem.id"))
			.where(EventoCondition.of(equipe, filter))
			.groupBy(agrupamento.getColunas()))
			.fetchMapList(agrupamento.getTipo(), Integer.class, Integer.class);

		int total = result.stream().mapToInt(e -> (int) e.get("quantidade")).sum();
		if (total > 0)
			result.stream()
				.forEach(e -> e.put("percentual", new Percentage(new BigDecimal((((Integer) e.get("quantidade")).doubleValue() / total) * 100))));
		return result;
	}

	public Evento insert(Evento evento) throws ConstraintViolationException
	{
		Insert.into("Evento")
			.set("Chamado$id", evento.getChamado().getId())
			.set("Uzer$id", evento.getUser().getId())
			.set("Origem$id", evento.getUser().getRole().getId())
			.set("tipo", evento.getTipo())
			.set("data", evento.getData())
			.set("descricao", evento.getDescricao())
			.set("observacoes", evento.getObservacoes())
			.set("status", evento.getStatus())
			.set("Anexo$id", evento.getAnexo().getId())
			.build()
			.connect(getLink())
			.fetchGeneratedKey(ID.class)
			.ifPresent(evento::setId);
		return evento;
	}

	public Evento select(ID id) throws NotFoundException
	{
		return getLink()
			.from(getClass().getResource("EventoDao/select(ID).sql"))
			.parameters(id)
			.fetchEntity(Evento.class)
			.orElseThrow(NotFoundException::new);
	}

	public boolean update(Evento evento) throws ConstraintViolationException
	{
		return getLink()
			.prepare(Update
				.table("Evento")
				.set("alteracao", LocalDateTime.now())
				.set("observacoes", evento.getObservacoes())
				.when(evento.getAnexo().getId() != null).set("Anexo$id", evento.getAnexo().getId())
				.where(Condition.of("id").eq(ID.class, evento.getId())))
			.execute() > 0;
	}

	public void update(Chamado chamado, Evento.Tipo tipo, Evento.Tipo novo) throws ConstraintViolationException
	{
		Update
			.table("Evento")
			.set("tipo", novo)
			.where(Condition.of("Chamado$id").eq(ID.class, chamado.getId()).and("tipo").eq(tipo))
			.build()
			.connect(getLink())
			.execute();
	}

	private static class EventoCondition
	{

		private static CompiledCondition of(Equipe equipe, Evento filter)
		{

			return Condition.TRUE
				.and("Evento.Origem$id").eq(ID.class, equipe.getId())
				.and("Evento.tipo").eq(filter.getTipo())
				.and("date(Evento.data)").ge(filter.getPeriodo().getMin())
				.and("date(Evento.data)").le(filter.getPeriodo().getMax())
				.and("time(Evento.data)").ge(filter.getHorario().getMin())
				.and("time(Evento.data)").le(filter.getHorario().getMax())
				.and("Evento.descricao").lk(filter.getDescricao())
				.and("Evento.observacoes").lk(filter.getObservacoes())
				.and("Evento.status").lk(filter.getStatus());
		}
	}
}
