package cliq.modulos.solicitante;

import cliq.report.SolicitanteReport;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.report.Doc;

@Icon("search")
@Name("Pesquisar")
public class PesquisaScreen extends SolicitanteScreen
{

	@Override
	public String call()
	{
		if (isPOST())
			page = paginate(ordenate(control.search(getForm())));
		return "/WEB-INF/views/cliq/modulos/solicitante/Chamado/ViewPesquisa.jsp";
	}

	@Icon("report")
	@Name("Imprimir")
	public Object callReport()
	{
		return Doc.create(type, new SolicitanteReport(type, getOrg(), control.search(getForm())));
	}

}
