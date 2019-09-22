package cliq.report;

import cliq.entity.Chamado;
import gate.entity.Org;
import gate.report.Grid;
import java.util.List;
import java.util.Map;

public class EventoResume extends CliQReport
{

	public EventoResume(Org org,
		Chamado.Agrupamento agrupamento, List<Map<String, Object>> categorias)
	{
		super(org, Orientation.LANDSCAPE);
		addHeader("Eventos agrupados por " + agrupamento);

		addLineBreak();

		Grid<Map<String, Object>> grid = addGrid((Class<Map<String, Object>>) (Class) Map.class, categorias);
		grid.setCaption(String.format("%d EVENTOS ENCONTRADOS", categorias.stream().mapToInt(e -> (int) e.get("quantidade")).sum()));
		grid.add().head(agrupamento.toString()).body(e -> e.get("agrupamento")).style().width(50).left();
		grid.add().head("Quantidade").body(e -> e.get("quantidade")).style().width(25).center();
		grid.add().head("Percentual").body(e -> e.get("percentual")).style().width(25).center();
	}
}
