package cliq.modulos.atendimento;

import cliq.entity.Chamado;
import cliq.report.AtendimentoReport;
import cliq.type.Situacao;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.report.Doc;
import java.util.ArrayList;
import java.util.List;

@Icon("2006")
@Name("Todos")
public class ChamadosScreen extends Screen
{

	private List<Chamado> chamados;

	@Override
	@Icon("2006")
	@Name("Todos")
	public String call()
	{
		chamados = ordenate(control.search(getUser().getRole(), getFilter()));
		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/View.jsp";
	}

	public String callImport()
	{
		chamados = ordenate(control.search(getUser().getRole(), getFilter()));
		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewImport.jsp";
	}

	@Icon("report")
	@Name("Imprimir")
	public Doc callReport()
	{
		return Doc.create(type, new AtendimentoReport(type, getOrg(),
			ordenate(control.search(getUser().getRole(), getFilter()))));
	}

	public List<Chamado> getChamados()
	{
		if (chamados == null)
			chamados = new ArrayList<>();
		return chamados;
	}

	private Chamado getFilter()
	{
		return new Chamado()
			.setLocalizacao(equipe)
			.setSituacao(Situacao.PENDENTE)
			.setNivel(getForm().getNivel())
			.setCategoria(getForm().getCategoria())
			.setPendencia(getForm().getPendencia());
	}
}
