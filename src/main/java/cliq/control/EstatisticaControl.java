package cliq.control;

import cliq.dao.EstatisticaDao;
import cliq.entity.Equipe;
import cliq.type.Estatisticas;
import gate.base.Control;
import java.util.List;

public class EstatisticaControl extends Control
{

	public Estatisticas search(Equipe equipe)
	{
		try (EstatisticaDao dao = new EstatisticaDao())
		{
			return dao.search(equipe);
		}
	}

	public List<Object[]> getDesempenhoMensal(Equipe equipe)
	{
		try (EstatisticaDao dao = new EstatisticaDao())
		{
			return dao.getDesempenhoMensal(equipe);
		}
	}

	public List<Object[]> getChamadosPorAtendente(Equipe equipe)
	{
		try (EstatisticaDao dao = new EstatisticaDao())
		{
			return dao.getChamadosPorAtendente(equipe);
		}
	}

	public List<Object[]> getChamadosPorOrigem(Equipe equipe)
	{
		try (EstatisticaDao dao = new EstatisticaDao())
		{
			return dao.getChamadosPorOrigem(equipe);
		}
	}

	public List<Object[]> getChamadosPorCategoria(Equipe equipe)
	{
		try (EstatisticaDao dao = new EstatisticaDao())
		{
			return dao.getChamadosPorCategoria(equipe);
		}
	}

}
