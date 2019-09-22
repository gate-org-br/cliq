package cliq.control;

import cliq.dao.AnexoDao;
import gate.base.Control;
import gate.error.NotFoundException;
import gate.type.ID;
import gate.type.mime.MimeDataFile;

public class AnexoControl extends Control
{

	public MimeDataFile select(ID id) throws NotFoundException
	{
		try (AnexoDao dao = new AnexoDao())
		{
			return dao.select(id);
		}
	}
}
