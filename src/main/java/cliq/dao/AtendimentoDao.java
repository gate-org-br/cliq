package cliq.dao;

import cliq.entity.Chamado;
import cliq.fetcher.ChamadosFetcher;
import cliq.type.Situacao;
import gate.entity.Role;
import gate.entity.User;
import gate.sql.Link;
import gate.sql.condition.CompiledCondition;
import gate.sql.condition.Condition;
import gate.sql.condition.ConstantCondition;
import gate.sql.select.Select;
import gate.type.Duration;
import gate.type.ID;
import gate.type.Percentage;
import gate.util.SearchParser;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class AtendimentoDao extends ChamadoDao
{

	public AtendimentoDao()
	{
	}

	public AtendimentoDao(Link link)
	{
		super(link);
	}

	public List<Chamado> search(Role role, Chamado filter)
	{
		return getLink()
			.from(Select
				.of(getClass().getResource("AtendimentoDao/search(Role, Chamado).sql"))
				.where(AtendimentoCondition.of(getUser(), filter)))
			.fetch(new ChamadosFetcher(getUser()));
	}

	public List<Map<String, Object>> search(Chamado.Agrupamento agrupamento, Role role, Chamado chamado)
	{
		List<Map<String, Object>> result = getLink().from(Select
			.expression(agrupamento.getCampo()).as("agrupamento")
			.expression("coalesce(count(Chamado.id), 0)").as("quantidade")
			.expression("0").as("percentual")
			.expression("avg(TIMESTAMPDIFF(SECOND, Chamado.data, COALESCE(Chamado.resposta, Chamado.solucao, now())))").as("resposta")
			.expression("avg(TIMESTAMPDIFF(SECOND, Chamado.data, COALESCE(Chamado.solucao, now())))").as("solucao")
			.from("Chamado")
			.leftJoin("Categoria").on(Condition.of("Chamado.Categoria$id").isEq("Categoria.id"))
			.leftJoin("gate.Uzer").as("Solicitante").on(Condition.of("Chamado.Solicitante$id").isEq("Solicitante.id"))
			.leftJoin("gate.Role").as("Origem").on(Condition.of("Chamado.Origem$id").isEq("Origem.id"))
			.leftJoin("gate.Uzer").as("Atendente").on(Condition.of("Chamado.Atendente$id").isEq("Atendente.id"))
			.leftJoin("Contato").on(Condition.of("Chamado.Contato$id").isEq("Contato.id"))
			.where(AtendimentoCondition.of(getUser(), chamado))
			.groupBy(agrupamento.getColunas()))
			.fetchMapList(agrupamento.getTipo(),
				Integer.class,
				Double.class,
				Duration.class,
				Duration.class);
		int total = result.stream().mapToInt(e -> (int) e.get("quantidade")).sum();
		if (total > 0)
			result.stream()
				.forEach(e -> e.put("percentual", new Percentage(new BigDecimal((((Integer) e.get("quantidade")).doubleValue() / total) * 100))));
		return result;
	}

	private static class AtendimentoCondition
	{

		private static final ConstantCondition SOLUCAO_ATRASADA = Condition.of("Chamado.prazoSolucao").isNotNull().and("Chamado.prazoSolucao").isLt("now()");
		private static final ConstantCondition RESPOSTA_ATRASADA = Condition.of("Chamado.prazoReposta").isNotNull().and("Chamado.prazoReposta").isLt("now()");
		private static final CompiledCondition ATRASADO = Condition.of("Chamado.situacao").eq(Situacao.PENDENTE).and(RESPOSTA_ATRASADA.or(SOLUCAO_ATRASADA));

		private static CompiledCondition of(User user, Chamado chamado)
		{

			CompiledCondition condition = Condition.TRUE
				.of(Condition
					.of("sigiloso").eq("false")
					.or("Chamado.Solicitante$id").eq(ID.class, user.getId())
					.or("Chamado.PessoaAprovadora$id").eq(ID.class, user.getId())
					.or("Chamado.Localizacao$id").eq(ID.class, user.getRole().getId())
					.or("Chamado.EquipeAprovadora$id").eq(ID.class, user.getRole().getId()))
				.and("Chamado.pendencia").eq(chamado.getPendencia())
				.and("Categoria.id").eq(chamado.getCategoria().getId())
				.and("Chamado.documentacao").eq(chamado.getDocumentacao())
				.and("Chamado.Solicitante$id").eq(chamado.getSolicitante().getId())
				.and("Chamado.id").eq(chamado.getId())
				.and("Chamado.nivel").eq(chamado.getNivel())
				.and("Chamado.Atendente$id").eq(chamado.getAtendente().getId())
				.and("Chamado.situacao").eq(chamado.getSituacao())
				.and("Chamado.prioridade").eq(chamado.getPrioridade())
				.and("Chamado.complexidade").eq(chamado.getComplexidade())
				.and("Chamado.projeto").eq(chamado.getProjeto())
				.and("Chamado.nota").eq(chamado.getNota());

			if (Boolean.TRUE.equals(chamado.getCapturado()))
				condition = condition.and("Chamado.Atendente$id").isNotNull();
			else if (Boolean.FALSE.equals(chamado.getCapturado()))
				condition = condition.and("Chamado.Atendente$id").isNull();

			if (Boolean.TRUE.equals(chamado.getAgendado()))
				condition = condition.and("Chamado.Solicitante$id").isNull();
			else if (Boolean.FALSE.equals(chamado.getAgendado()))
				condition = condition.and("Chamado.Solicitante$id").isNotNull();

			if (Boolean.TRUE.equals(chamado.getAvaliado()))
				condition = condition.and("Chamado.nota").isNotNull();
			else if (Boolean.FALSE.equals(chamado.getAgendado()))
				condition = condition.and("Chamado.nota").isNull();

			if (Boolean.TRUE.equals(chamado.getAtrasado()))
				condition = condition.and(ATRASADO);
			else if (Boolean.FALSE.equals(chamado.getAtrasado()))
				condition = condition.and().not(ATRASADO);

			if (chamado.getOrigem().getId() != null)
				if (Boolean.TRUE.equals(chamado.getOrigem().getRecursive()))
					condition = condition.and("Chamado.Origem$id").in(user.getRole().getRoot()
						.select(chamado.getOrigem().getId()).toList(Role::getId));
				else
					condition = condition.and("Chamado.Origem$id").eq(chamado.getOrigem().getId());

			if (chamado.getLocalizacao().getId() != null)
				if (Boolean.TRUE.equals(chamado.getOrigem().getRecursive()))
					condition = condition.and("Chamado.Localizacao$id").in(user.getRole().getRoot()
						.select(chamado.getLocalizacao().getId()).toList(Role::getId));
				else
					condition = condition.and("Chamado.Localizacao$id").eq(chamado.getLocalizacao().getId());

			for (String token : new SearchParser(chamado.getTitulo()).getTokens())
				condition = condition.and(Condition
					.of("Chamado.titulo").lk(token)
					.or("Chamado.descricao").lk(token)
					.or("Chamado.formulario").lk(token)
					.or().exists(Select.expression("Evento.id").from("Evento")
						.where(Condition.of("Chamado.id").isEq("Evento.Chamado$id")
							.and(Condition.of("Evento.descricao")
								.lk(token).or("Evento.observacoes").lk(token)))));

			if (chamado.getEvento().getPeriodo().getMin() != null
				|| chamado.getEvento().getPeriodo().getMax() != null
				|| chamado.getEvento().getUser().getId() != null
				|| chamado.getEvento().getTipo() != null)
				condition = condition.and().exists(Select.expression("id")
					.from("Evento")
					.where(Condition.of("Evento.Chamado$id").isEq("Chamado.id")
						.and("Evento.tipo").eq(chamado.getEvento().getTipo())
						.and("Evento.Uzer$id").eq(chamado.getEvento().getUser().getId())
						.and("Evento.data").ge(chamado.getEvento().getPeriodo().getMin())
						.and("Evento.data").le(chamado.getEvento().getPeriodo().getMax())));

			return condition;
		}
	}

}
