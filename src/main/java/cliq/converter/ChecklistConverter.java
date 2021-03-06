package cliq.converter;

import cliq.type.Checklist;
import gate.constraint.Constraint;
import gate.error.ConversionException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.List;

public class ChecklistConverter implements gate.converter.Converter
{

	@Override
	public String getMask()
	{

		return null;
	}

	@Override
	public String getDescription()
	{
		return "Passo a passo de como executar o chamado.";
	}

	@Override
	public List<Constraint.Implementation<?>> getConstraints()
	{
		return Collections.emptyList();
	}

	@Override
	public String toText(Class<?> type, Object object)
	{
		return object != null ? object.toString() : "";
	}

	@Override
	public String toText(Class<?> type, Object object, String format)
	{
		return object != null ? String.format(format, object) : "";
	}

	@Override
	public String toString(Class<?> type, Object object)
	{
		if (object == null)
			return "";
		return object.toString();
	}

	@Override
	public Object ofString(Class<?> type, String string) throws ConversionException
	{
		if (string == null || string.length() == 0)
			return null;
		return new Checklist(string);
	}

	@Override
	public Object readFromResultSet(ResultSet rs, String fields, Class<?> type) throws SQLException, ConversionException
	{

		String string = rs.getString(fields);
		return string == null ? null : Checklist.ofJson(string);
	}

	@Override
	public Object readFromResultSet(ResultSet rs, int index, Class<?> type) throws SQLException, ConversionException
	{
		String string = rs.getString(index);
		return string == null ? null : Checklist.ofJson(string);
	}

	@Override
	public int writeToPreparedStatement(PreparedStatement ps, int index, Object value) throws SQLException
	{
		if (value != null)
			ps.setString(index++, Checklist.toJson(((Checklist) value)));
		else
			ps.setNull(index++, Types.VARCHAR);
		return index;
	}
}
