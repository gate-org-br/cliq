package cliq.report;

import cliq.entity.Chamado;
import gate.entity.Org;
import gate.report.Grid;
import java.util.List;
import java.util.Map;

public class AtendimentoResume extends CliQReport
{

	public AtendimentoResume(Org org,
		Chamado.Agrupamento agrupamento, List<Map<String, Object>> categorias)
	{
		super(org, Orientation.LANDSCAPE);
		addHeader("Chamados agrupados por " + agrupamento);

		addLineBreak();

		Grid<Map<String, Object>> grid = addGrid((Class<Map<String, Object>>) (Class) Map.class, categorias);
		grid.setCaption(String.format("%d CHAMADOS ENCONTRADOS", categorias.stream().mapToInt(e -> (int) e.get("quantidade")).sum()));
		grid.add().head(agrupamento.toString()).body(e -> e.get("agrupamento")).style().width(50).left();
		grid.add().head("Quantidade").body(e -> e.get("quantidade")).style().width(12.5).center();
		grid.add().head("Percentual").body(e -> e.get("percentual")).style().width(12.5).center();
		grid.add().head("Tempo Médio de Resposta").body(e -> e.get("resposta")).style().width(12.5).center();
		grid.add().head("Tempo Médio de Solução").body(e -> e.get("solucao")).style().width(12.5).center();
	}
}
