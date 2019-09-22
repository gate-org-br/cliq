package cliq.report;

import cliq.entity.Chamado;
import gate.entity.Org;
import gate.lang.xml.XMLCleaner;
import gate.report.Doc;
import gate.report.Grid;
import java.util.List;

public class SolicitanteReport extends CliQReport
{

	public SolicitanteReport(Doc.Type type, Org org, List<Chamado> chamados)
	{
		super(org, Orientation.LANDSCAPE);

		addHeader("Relatório de Chamados");

		addLineBreak();

		if (!chamados.isEmpty())
		{
			Grid<Chamado> grid = addGrid(Chamado.class, chamados);
			grid.setCaption(String.format("%d CHAMADOS ENCONTRADOS", chamados.size()));
			if (chamados.size() == 1001 && chamados.remove(1000) != null)
				grid.setCaption(grid.getCaption() + " (limitado a 1000 registros)");

			if (type == Doc.Type.XLS || type == Doc.Type.CSV)
			{
				grid.add().head("Número").body(Chamado::getId);
				grid.add().head("Categoria").body(e -> e.getCategoria().getNome());
				grid.add().head("Data").body(Chamado::getData);
				grid.add().head("Situação").body(Chamado::getSituacao);
				grid.add().head("Pendência").body(Chamado::getPendencia);

				grid.add().head("Origem").body(e -> e.getOrigem().getName());
				grid.add().head("Solicitante").body(e -> e.getSolicitante().getName());
				grid.add().head("Localicação").body(e -> e.getLocalizacao().getName());
				grid.add().head("Atendente").body(e -> e.getAtendente().getName());

				grid.add().head("Título").body(e -> e.getTitulo());
				grid.add().head("Descrição").body(e -> XMLCleaner.cleanup(e.getDescricao()));
			} else
			{

				grid.add().head("Categoria").body(e -> e.getCategoria().getNome()).style().width(10).center();
				grid.add().head("Data").body(Chamado::getData).style().width(15).center();
				grid.add().head("Situação").body(Chamado::getSituacao).style().width(10).center();
				grid.add().head("Solicitante").body(e -> e.getSolicitante().getName()).style().width(15).center();
				grid.add().head("Atendente").body(e -> e.getAtendente().getName()).style().width(15).center();
				grid.add().head("Chamado").body(e -> e.getTitulo()).style().width(35).justify();
			}
		} else
			addParagraph("Nenhum chamado encontrado para os critérios de busca selecionados.").style().center();
	}
}
