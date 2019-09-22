package cliq.report;

import cliq.entity.Chamado;
import gate.entity.Org;
import gate.lang.xml.XMLCleaner;
import gate.report.Form;
import gate.util.Toolkit;
import java.util.stream.Collectors;

public class SolicitanteExport extends CliQReport
{

	public SolicitanteExport(Org org, Chamado chamado)
	{
		super(org, Orientation.LANDSCAPE);

		addHeader("Chamado");

		addLineBreak();

		Form formulario = addForm(8);
		formulario.setCaption(chamado.getCategoria().getNome() == null ? "Chamado" : chamado.getCategoria().getNome());
		formulario.add("Nº:", chamado.getId());
		formulario.add("Data:", chamado.getData());
		formulario.add("Situação:", chamado.getSituacao());
		formulario.add("Pendência:", chamado.getPendencia());
		formulario.add("Categoria:", chamado.getTipo().getNome()).colspan(2);
		formulario.add("Sigiloso:", chamado.getSigiloso()).colspan(2);
		formulario.add("Origem:", chamado.getOrigem().getName()).colspan(2);
		formulario.add("Solicitante:", chamado.getSolicitante().getName()).colspan(2);
		formulario.add("Localização:", chamado.getLocalizacao().getName()).colspan(2);
		formulario.add("Atendente:", chamado.getAtendente().getName()).colspan(2);

		addLineBreak();

		addHeader(chamado.getTitulo());

		if (chamado.getDescricao() != null)
		{
			addLineBreak();
			addParagraph(XMLCleaner.cleanup(chamado.getDescricao()));
		}

		if (!Toolkit.isEmpty(chamado.getFormulario().getFields()))
		{
			addLineBreak();
			addForm(chamado.getFormulario());
		}

		if (chamado.getEventos().stream().anyMatch(e -> e.getTipo().getPublico()))
		{
			addLineBreak();
			add(new EventoGrid(chamado.getEventos().stream().filter(e -> e.getTipo().getPublico()).collect(Collectors.toList())));
		}
	}
}
