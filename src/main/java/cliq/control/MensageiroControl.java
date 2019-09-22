package cliq.control;

import cliq.dao.MensageiroDao;
import cliq.entity.Chamado;
import gate.entity.User;
import java.util.List;

public class MensageiroControl
{

	public List<User> search(Chamado chamado)
	{
		try (MensageiroDao dao = new MensageiroDao())
		{
			return chamado.getEvento().getTipo().getPublico()
				? dao.publico(chamado) : dao.privado(chamado);
		}
	}
}
