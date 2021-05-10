package cliq.modulos.solicitante;

import cliq.entity.Chamado;
import cliq.report.SolicitanteReport;
import cliq.type.Situacao;
import gate.annotation.CopyIcon;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.entity.Role;
import gate.report.Doc;

@Name("Equipe")
@CopyIcon(Role.class)
public class DaEquipeScreen extends Screen
{

	@Override
	public String call()
	{
		page = paginate(ordenate(control.search(getFilter())));
		return "/WEB-INF/views/cliq/modulos/solicitante/Chamado/ViewPessoa.jsp";
	}

	@Icon("report")
	@Name("Imprimir")
	public Object callReport()
	{
		return Doc.create(type, new SolicitanteReport(type, getOrg(), control.search(getFilter())));
	}

	public Chamado getFilter()
	{
		return new Chamado()
			.setOrigem(equipe)
			.setSituacao(getForm().getSituacao() != null ? getForm().getSituacao() : Situacao.PENDENTE);
	}

}
