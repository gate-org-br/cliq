package cliq.dao;

import cliq.entity.Categoria;
import gate.base.Dao;
import gate.error.ConstraintViolationException;
import gate.type.Form;

public class FormDao extends Dao
{

	public FormDao()
	{
		
	}

	public Form select(Categoria categoria)
	{
		return getLink()
				.from("select formulario from Categoria where id = ?")
				.parameters(categoria.getId())
				.fetchObject(Form.class)
				.orElse(null);
	}

	public void update(Categoria categoria, Form form) throws ConstraintViolationException
	{
		getLink()
				.prepare("update Categoria set formulario = ? where id = ?")
				.parameters(form, categoria.getId())
				.execute();
	}
}
