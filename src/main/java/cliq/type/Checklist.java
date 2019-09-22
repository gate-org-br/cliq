package cliq.type;

import cliq.converter.ChecklistConverter;
import cliq.type.Checklist.Checkitem;
import gate.annotation.Converter;
import gate.annotation.Description;
import gate.annotation.ElementType;
import gate.annotation.Icon;
import gate.constraint.Required;
import gate.error.ConversionException;
import gate.lang.json.JsonArray;
import gate.lang.json.JsonObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Icon("2093")
@Converter(ChecklistConverter.class)
@ElementType(Checklist.Checkitem.class)
public class Checklist extends ArrayList<Checkitem>
{

	private static final long serialVersionUID = 1L;

	public int getPendentes()
	{
		return (int) stream()
			.filter(e -> Boolean.FALSE.equals(e.getExecutada()))
			.count();
	}

	public Checklist()
	{
	}

	public Checklist(String string)
	{
		Stream.of(string.split(" *\\r?\\n *")).map(e -> e.trim())
			.filter(e -> !e.isEmpty()).map(e -> new Checkitem(e)).forEach(e -> add(e));
	}

	@Override
	public String toString()
	{
		return stream().map(e -> e.toString()).collect(Collectors.joining("\n"));
	}

	public static class Checkitem implements Serializable, Comparable<Checkitem>
	{

		private static final long serialVersionUID = 1L;

		private int index;

		@Required
		@Description("Qual o nome do item?")
		private String nome;

		@Description("A tarefa foi executada?")
		private Boolean executada;

		public Checkitem()
		{

		}

		public Checkitem(String nome)
		{
			this.nome = nome;
		}

		public Checkitem(String nome, Boolean executada)
		{
			this.nome = nome;
			this.executada = executada;
		}

		public String getNome()
		{
			return nome;
		}

		public void setNome(String nome)
		{
			this.nome = nome;
		}

		public Boolean getExecutada()
		{
			if (executada == null)
				executada = Boolean.FALSE;
			return executada;
		}

		public void setExecutada(Boolean executada)
		{
			this.executada = executada;
		}

		public int getIndex()
		{
			return index;
		}

		public void setIndex(int index)
		{
			this.index = index;
		}

		@Override
		public String toString()
		{
			return nome;
		}

		@Override
		public int compareTo(Checkitem o)
		{
			return this.nome.compareTo(o.nome);
		}
	}

	public static Checklist ofJson(String string) throws ConversionException
	{
		Checklist checklist = JsonArray.parse(string).stream()
			.map(e -> (JsonObject) e)
			.map(e -> new Checklist.Checkitem(e.getString("nome").get(),
			Boolean.parseBoolean(e.getString("executada").orElse("false"))))
			.collect(Collectors.toCollection(() -> new Checklist()));
		for (int i = 0; i < checklist.size(); i++)
			checklist.get(i).setIndex(i);
		return checklist;
	}

	public static String toJson(Checklist checklist)
	{
		return checklist.stream().map(e -> new JsonObject()
			.setString("nome", e.getNome())
			.setString("executada", e.getExecutada().toString()))
			.collect(Collectors.toCollection(() -> new JsonArray()))
			.toString();
	}
}
