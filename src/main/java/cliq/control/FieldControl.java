package cliq.control;

import cliq.dao.FormDao;
import cliq.entity.Categoria;
import gate.base.Control;
import gate.error.ConstraintViolationException;
import gate.type.Field;
import gate.type.Form;

public class FieldControl extends Control
{

	public Field select(Categoria categoria, Integer index)
	{
		try (FormDao dao = new FormDao())
		{
			return dao.select(categoria).getFields().get(index);
		}
	}

	public void insert(Categoria categoria, Field field) throws ConstraintViolationException
	{
		try (FormDao dao = new FormDao())
		{
			dao.beginTran();
			Form form = dao.select(categoria);
			if (form == null)
				form = new Form();
			form.getFields().add(field);
			dao.update(categoria, form);
			dao.commit();
		}
	}

	public void update(Categoria categoria, Integer index, Field field) throws ConstraintViolationException
	{
		try (FormDao dao = new FormDao())
		{
			dao.beginTran();
			Form form = dao.select(categoria);
			form.getFields().set(index, field);
			dao.update(categoria, form);
			dao.commit();
		}
	}

	public void delete(Categoria categoria, Integer index) throws ConstraintViolationException
	{
		try (FormDao dao = new FormDao())
		{
			dao.beginTran();
			Form form = dao.select(categoria);
			form.getFields().remove(index.intValue());
			dao.update(categoria, form);
			dao.commit();
		}
	}

	public void update(Categoria categoria, int index1, int index2) throws ConstraintViolationException
	{
		try (FormDao dao = new FormDao())
		{
			dao.beginTran();
			Form form = dao.select(categoria);
			Field field = form.getFields().remove(index1);
			form.getFields().add(index2, field);
			dao.update(categoria, form);
			dao.commit();
		}
	}
}
