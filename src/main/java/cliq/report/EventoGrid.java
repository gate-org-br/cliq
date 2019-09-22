package cliq.report;

import cliq.entity.Evento;
import gate.lang.xml.XMLCleaner;
import gate.report.Grid;

public class EventoGrid extends Grid<Evento>
{

	public EventoGrid(Iterable<Evento> datasource)
	{
		super(datasource);

		setCaption("EVENTOS");
		add().head("Tipo").body(Evento::getTipo).style().width(10).center();
		add().head("Data").body(Evento::getData).style().width(10).center();
		add().head("Usuário").body(e -> e.getUser().getName()).style().width(20).center();
		add().head("Descrição").body(Evento::getDescricao).style().width(30).justify();
		add().head("Observações").body(e -> XMLCleaner.cleanup(e.getObservacoes())).style().width(30).justify();
	}

}
