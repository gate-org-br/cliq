package cliq.modulos.solicitante;

import cliq.entity.Chamado;
import cliq.report.SolicitanteReport;
import cliq.type.Situacao;
import gate.annotation.CopyIcon;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.entity.User;
import gate.report.Doc;

@Name("Pessoais")
@CopyIcon(User.class)
public class PessoaisScreen extends Screen
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
			.setSolicitante(getUser())
			.setSituacao(getForm().getSituacao() != null ? getForm().getSituacao() : Situacao.PENDENTE);
	}

}
