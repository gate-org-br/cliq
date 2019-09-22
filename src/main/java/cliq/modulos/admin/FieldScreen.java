package cliq.modulos.admin;

import cliq.control.FieldControl;
import cliq.entity.Categoria;
import cliq.modulos.CLIQScreen;
import gate.error.ConstraintViolationException;
import gate.io.URL;
import gate.type.Field;
import javax.inject.Inject;

public class FieldScreen extends CLIQScreen
{

	private Field form;
	private Integer index;
	private Integer target;
	private Categoria categoria;

	@Inject
	private FieldControl control;

	public String callInsert()
	{
		if (isPOST() && getMessages().isEmpty())
		{
			try
			{
				control.insert(getCategoria(), getForm());
				return "/WEB-INF/views/cliq/modulos/admin/Field/ViewResult.jsp";
			} catch (ConstraintViolationException ex)
			{
				setMessages(ex.getMessages());
			}
		}
		return "/WEB-INF/views/cliq/modulos/admin/Field/ViewInsert.jsp";
	}

	public String callSelect()
	{
		setForm(control.select(getCategoria(), getIndex()));
		return "/WEB-INF/views/cliq/modulos/admin/Field/ViewSelect.jsp";
	}

	public String callUpdate()
	{
		if (isPOST() && getMessages().isEmpty())
		{
			try
			{
				control.update(getCategoria(), getIndex(), getForm());
				return "/WEB-INF/views/cliq/modulos/admin/Field/ViewResult.jsp";
			} catch (ConstraintViolationException ex)
			{
				setMessages(ex.getMessages());
			}
		}
		return callSelect();
	}

	public String callDelete()
	{
		try
		{
			control.delete(getCategoria(), getIndex());
			return "/WEB-INF/views/cliq/modulos/admin/Field/ViewResult.jsp";
		} catch (ConstraintViolationException ex)
		{
			setMessages(ex.getMessages());
			return callSelect();
		}
	}

	public URL callMove()
	{
		try
		{
			control.update(getCategoria(), getIndex(), getTarget());
		} catch (ConstraintViolationException ex)
		{
			setMessages(ex.getMessages());
		}

		return new URL("Gate").setModule(getModule())
			.setScreen("Categoria")
			.setAction("Select")
			.setParameter("form.id", getCategoria().getId())
			.setParameter("tab", "Formulario")
			.setMessages(getMessages());
	}

	public Field getForm()
	{
		if (form == null)
			form = new Field();
		return form;
	}

	public void setForm(Field form)
	{
		this.form = form;
	}

	public Categoria getCategoria()
	{
		if (categoria == null)
			categoria = new Categoria();
		return categoria;
	}

	public void setCategoria(Categoria categoria)
	{
		this.categoria = categoria;
	}

	public Integer getIndex()
	{
		return index;
	}

	public void setIndex(Integer index)
	{
		this.index = index;
	}

	public Integer getTarget()
	{
		return target;
	}

	public void setTarget(Integer target)
	{
		this.target = target;
	}
}
