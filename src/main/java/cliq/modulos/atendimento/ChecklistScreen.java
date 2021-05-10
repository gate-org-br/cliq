package cliq.modulos.atendimento;

import cliq.control.ChecklistControl;
import cliq.type.Checklist;
import gate.annotation.Color;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.error.AppException;
import javax.inject.Inject;

public class ChecklistScreen extends Screen
{

	private Checklist.Checkitem checkitem;

	@Inject
	private ChecklistControl control;

	@Override
	@Icon("1012")
	@Name("Adicionar tarefa")
	public String callInsert()
	{
		try
		{
			control.insert(getForm().getId(), getCheckitem());

		} catch (AppException ex)
		{
			getMessages().add(ex.getMessage());
		}

		return callSelect();
	}

	@Icon("delete")
	@Color("#660000")
	@Name("Remover tarefa")
	public String callDelete()
	{
		try
		{
			control.delete(getForm().getId(), getCheckitem());

		} catch (AppException ex)
		{
			getMessages().add(ex.getMessage());
		}

		return callSelect();
	}

	@Override
	@Icon("1014")
	@Color("#000066")
	@Name("Concluir tarefa")
	public String callCommit()
	{
		try
		{
			control.commit(getForm().getId(), getCheckitem());

		} catch (AppException ex)
		{
			getMessages().add(ex.getMessage());
		}

		return callSelect();
	}

	@Icon("1010")
	@Color("#006600")
	@Name("Cancelar tarefa")
	public String callCancel()
	{
		try
		{
			control.cancel(getForm().getId(), getCheckitem());

		} catch (AppException ex)
		{
			getMessages().add(ex.getMessage());
		}

		return callSelect();
	}

	public Checklist.Checkitem getCheckitem()
	{
		if (checkitem == null)
			checkitem = new Checklist.Checkitem();
		return checkitem;
	}
}
