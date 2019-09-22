package cliq.dao;

import cliq.entity.Equipe;
import cliq.type.Estatisticas;
import gate.base.Dao;
import gate.type.Month;
import java.util.List;

public class EstatisticaDao extends Dao
{

	public Estatisticas search(Equipe equipe)
	{
		return getLink()
			.from(getClass().getResource("EstatisticaDao/search(Equipe).sql"))
			.parameters(equipe.getId(), equipe.getId())
			.fetchEntity(Estatisticas.class)
			.orElseGet(Estatisticas::new);
	}

	public List<Object[]> getDesempenhoMensal(Equipe equipe)
	{
		return getLink()
			.from(getClass().getResource("EstatisticaDao/getDesempenhoMensal(Equipe).sql"))
			.parameters(equipe.getId())
			.fetchDataGrid(Month.class, Integer.class, Integer.class, Integer.class);
	}

	public List<Object[]> getChamadosPorAtendente(Equipe equipe)
	{
		return getLink()
			.from(getClass().getResource("EstatisticaDao/getChamadosPorAtendente(Equipe).sql"))
			.parameters(equipe.getId())
			.fetchDataGrid(String.class, Integer.class);
	}

	public List<Object[]> getChamadosPorOrigem(Equipe equipe)
	{
		return getLink()
			.from(getClass().getResource("EstatisticaDao/getChamadosPorOrigem(Equipe).sql"))
			.parameters(equipe.getId())
			.fetchDataGrid(String.class, Integer.class);
	}

	public List<Object[]> getChamadosPorCategoria(Equipe equipe)
	{
		return getLink()
			.from(getClass().getResource("EstatisticaDao/getChamadosPorCategoria(Equipe).sql"))
			.parameters(equipe.getId())
			.fetchDataGrid(String.class, Integer.class);
	}
}
