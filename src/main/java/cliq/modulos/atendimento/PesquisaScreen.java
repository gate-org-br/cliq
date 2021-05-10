package cliq.modulos.atendimento;

import cliq.entity.Chamado;

import cliq.report.AtendimentoResume;
import cliq.report.AtendimentoReport;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.report.Doc;
import java.util.List;

@Icon("search")
@Name("Pesquisar")
public class PesquisaScreen extends Screen
{

	private Object page;
	private Chamado.Agrupamento agrupamento;

	@Override
	@Icon("search")
	@Name("Pesquisar")
	public String call()
	{
		if (isPOST())
			if (getAgrupamento() == null)
			{
				List<Chamado> chamados = control.search(equipe, getForm());
				if (chamados.size() == 1001 && chamados.remove(1000) != null)
					getMessages().add("Sua busca foi limitada a 1000 registros. \\nCaso seu objetivo seja apenas contar o número de chamados, selecione um agrupamento para fazer a pesquisa.");
				page = paginate(ordenate(chamados));
			} else
				page = ordenate(control.search(getAgrupamento(), equipe, getForm()));
		else
			getForm().setLocalizacao(equipe);
		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewPesquisa.jsp";
	}

	@Icon("report")
	@Name("Imprimir")
	public Object callReport()
	{
		if (getAgrupamento() != null)
			return Doc.create(type, new AtendimentoResume(getOrg(),
				getAgrupamento(), control.search(getAgrupamento(), equipe, getForm())));

		return Doc.create(type, new AtendimentoReport(type, getOrg(), control.search(equipe, getForm())));
	}

	public Object getPage()
	{
		return page;
	}

	public Chamado.Agrupamento getAgrupamento()
	{
		return agrupamento;
	}

	public void setAgrupamento(Chamado.Agrupamento agrupamento)
	{
		this.agrupamento = agrupamento;
	}
}
