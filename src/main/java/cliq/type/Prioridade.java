package cliq.type;

import gate.annotation.Color;
import gate.annotation.Converter;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.converter.EnumStringConverter;

@Icon("2128")
@Name("Prioridade")
@Converter(EnumStringConverter.class)
public enum Prioridade
{

	@Icon("2109")
	@Name("Baixa")
	@Color("#006600")
	BAIXA,
	@Icon("2114")
	@Name("Média")
	@Color("#000000")
	MEDIA,
	@Name("Alta")
	@Icon("2112")
	@Color("#660000")
	ALTA,
	@Icon("2113")
	@Name("Urgente")
	@Color("#EE0000")
	URGENTE;

	@Override
	public String toString()
	{
		return Name.Extractor.extract(this).orElse(name());
	}
}
