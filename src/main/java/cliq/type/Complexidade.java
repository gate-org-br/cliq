package cliq.type;

import gate.annotation.Color;
import gate.annotation.Converter;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.converter.EnumStringConverter;

@Icon("2112")
@Name("Complexidade")
@Converter(EnumStringConverter.class)
public enum Complexidade
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
	@Name("Crítica")
	@Color("#EE0000")
	CRITICA;

	@Override
	public String toString()
	{
		return Name.Extractor.extract(this).orElse(name());
	}
}
