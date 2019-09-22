package cliq.modulos.atendimento;

import cliq.control.EstatisticaControl;
import cliq.entity.Equipe;
import gate.annotation.Current;
import gate.base.Screen;
import gate.error.NotFoundException;
import javax.inject.Inject;

public class EstatisticaScreen extends Screen
{

	@Inject
	@Current
	private Equipe equipe;

	@Inject
	private EstatisticaControl control;

	public String callChamadosPorAtendente() throws NotFoundException
	{
		return control.getChamadosPorAtendente(equipe).toString();
	}

	public String callDesempenhoMensal() throws NotFoundException
	{
		return control.getDesempenhoMensal(equipe).toString();
	}

	public String callChamadosPorOrigem() throws NotFoundException
	{
		return control.getChamadosPorOrigem(equipe).toString();
	}

	public String callChamadosPorCategoria() throws NotFoundException
	{
		return control.getChamadosPorCategoria(equipe).toString();
	}
}
