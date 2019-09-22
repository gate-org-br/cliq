package cliq.type;

import gate.annotation.Converter;
import gate.converter.EnumStringConverter;

@Converter(EnumStringConverter.class)
public enum Ator
{
	PESSOA("Pessoa"),
	EQUIPE("Equipe");

	private final String string;

	Ator(String string)
	{
		this.string = string;
	}

	@Override
	public String toString()
	{
		return string;
	}
}
