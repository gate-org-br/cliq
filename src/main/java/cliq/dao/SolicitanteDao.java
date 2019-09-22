package cliq.dao;

import cliq.entity.Chamado;
import cliq.fetcher.ChamadosFetcher;
import gate.entity.Role;
import gate.sql.Link;
import gate.sql.condition.CompiledCondition;
import gate.sql.condition.Condition;
import gate.sql.select.Select;
import gate.util.SearchParser;
import java.util.List;

public class SolicitanteDao extends ChamadoDao
{

	public SolicitanteDao()
	{
	}

	public SolicitanteDao(Link link)
	{
		super(link);
	}

	public List<Chamado> search(Chamado chamado)
	{
		CompiledCondition condition
			= Condition
				.of(Condition.of("Chamado.sigiloso").isEq("false")
					.or("Chamado.Solicitante$id").eq(getUser().getId())
					.or().exists(Select.expression("id").from("Compartilhamento").where(Condition.of("Chamado$id").isEq("Chamado.id").and("Pessoa$id").eq(getUser().getId())))
					.or().exists(Select.expression("id").from("Compartilhamento").where(Condition.of("Chamado$id").isEq("Chamado.id").and("Equipe$id").eq(getUser().getRole().getId()))))
				.and("Chamado.Origem$id").in(getUser().getRole().toList(Role::getId))
				.and("Chamado.id").eq(chamado.getId())
				.and("Chamado.situacao").eq(chamado.getSituacao())
				.and("Date(Chamado.data)").ge(chamado.getPeriodo().getMin())
				.and("Date(Chamado.data)").le(chamado.getPeriodo().getMax())
				.and("Date(Chamado.data)").ge(chamado.getHorario().getMin())
				.and("Time(Chamado.data)").le(chamado.getHorario().getMax())
				.and("Tipo.nome").lk(chamado.getTipo().getNome())
				.and("Atendente.name").lk(chamado.getAtendente().getName())
				.and("Solicitante.name").lk(chamado.getSolicitante().getName());

		if (chamado.getSolicitante().getId() != null)
			condition = condition.and(Condition.of("Chamado.Solicitante$id").eq(getUser().getId())
				.or().exists(Select.expression("id").from("Compartilhamento").where(Condition.of("Chamado$id").isEq("Chamado.id").and("Pessoa$id").eq(getUser().getId())))
				.or().exists(Select.expression("id").from("Compartilhamento").where(Condition.of("Chamado$id").isEq("Chamado.id").and("Equipe$id").eq(getUser().getRole().getId()))));

		if (chamado.getOrigem().getId() != null)
			if (Boolean.TRUE.equals(chamado.getOrigem().getRecursive()))
				condition = condition.and("Chamado.Origem$id").in(getUser().getRole().getRoot()
					.select(chamado.getOrigem().getId()).toList(Role::getId));
			else
				condition = condition.and("Chamado.Origem$id").eq(chamado.getOrigem().getId());

		if (chamado.getLocalizacao().getId() != null)
			if (Boolean.TRUE.equals(chamado.getOrigem().getRecursive()))
				condition = condition.and("Chamado.Localizacao$id").in(getUser().getRole().getRoot()
					.select(chamado.getLocalizacao().getId()).toList(Role::getId));
			else
				condition = condition.and("Chamado.Localizacao$id").eq(chamado.getLocalizacao().getId());

		for (String search : new SearchParser(chamado.getTitulo()))
			condition = condition.and(Condition
				.of("Chamado.titulo").lk(search)
				.or("Chamado.descricao").lk(search)
				.or("Chamado.formulario").lk(search)
				.or().exists(Select.expression("Evento.id").from("Evento")
					.where(Condition.of("Chamado.id").isEq("Evento.Chamado$id")
						.and(Condition.of("Evento.descricao")
							.lk(search).or("Evento.observacoes").lk(search)))));
		return getLink()
			.from(Select
				.of(getClass().getResource("SolicitanteDao/search(Chamado).sql"))
				.where(condition)
				.limit(1001))
			.fetch(new ChamadosFetcher(getUser()));
	}
}
