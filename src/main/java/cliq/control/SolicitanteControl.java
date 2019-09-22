package cliq.control;

import cliq.dao.SolicitanteDao;
import cliq.entity.Chamado;
import java.util.List;

public class SolicitanteControl extends ChamadoControl
{

	public List<Chamado> search(Chamado chamado)
	{
		try (SolicitanteDao dao = new SolicitanteDao())
		{
			return dao.search(chamado);
		}
	}
}
