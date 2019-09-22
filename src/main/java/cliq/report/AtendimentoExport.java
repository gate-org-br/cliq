package cliq.report;

import cliq.entity.Chamado;
import gate.entity.Org;
import gate.lang.xml.XMLCleaner;
import gate.report.Form;
import gate.util.Toolkit;

public class AtendimentoExport extends CliQReport
{

	public AtendimentoExport(Org org, Chamado chamado)
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
		formulario.add("Tipo:", chamado.getTipo().getNome()).colspan(2);
		formulario.add("Categoria:", chamado.getCategoria().getNome()).colspan(2);
		formulario.add("Origem:", chamado.getOrigem().getName()).colspan(2);
		formulario.add("Solicitante:", chamado.getSolicitante().getName()).colspan(2);
		formulario.add("Localização:", chamado.getLocalizacao().getName()).colspan(2);
		formulario.add("Atendente:", chamado.getAtendente().getName()).colspan(2);

		formulario.add("Prioridade:", chamado.getPrioridade());
		formulario.add("Complexidade:", chamado.getComplexidade());
		formulario.add("Projeto:", chamado.getProjeto());
		formulario.add("Nível:", chamado.getNivel());
		formulario.add("Sigiloso:", chamado.getSigiloso());
		formulario.add("Documentação:", chamado.getDocumentacao());
		formulario.add("Atendimento:", chamado.getAtendimento());
		formulario.add("Avaliação:", chamado.getNota());

		formulario.add("Resposta:", chamado.getResposta());
		formulario.add("Tempo:", chamado.getTempoResposta());
		formulario.add("Prazo:", chamado.getPrazoResposta());
		formulario.add("Status:", chamado.getStatusResposta());

		formulario.add("Solução:", chamado.getSolucao());
		formulario.add("Tempo:", chamado.getTempoSolucao());
		formulario.add("Prazo:", chamado.getPrazoSolucao());
		formulario.add("Status:", chamado.getStatusSolucao());

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

		if (!chamado.getEventos().isEmpty())
		{
			addLineBreak();
			add(new EventoGrid(chamado.getEventos()));
		}
	}
}
