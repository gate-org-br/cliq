package cliq.type;

import gate.annotation.Color;
import gate.annotation.Converter;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.converter.EnumStringConverter;

@Name("Nota")
@Icon("2121")
@Converter(EnumStringConverter.class)
public enum Nota
{
	@Icon("2110")
	@Name("Péssimo")
	@Color("#880000")
	PESSIMO,
	@Icon("2106")
	@Name("Ruim")
	@Color("#440000")
	RUIM,
	@Icon("2114")
	@Color("#000000")
	@Name(("Regular"))
	REGULAR,
	@Name("Bom")
	@Icon("2104")
	@Color("#004400")
	BOM,
	@Icon("2108")
	@Color("#008800")
	@Name("Excelente")
	EXCELENTE;

	@Override
	public String toString()
	{
		return Name.Extractor.extract(this).orElse(name());
	}

	public String getColor()
	{
		return Color.Extractor.extract(this).orElse("#000000");
	}

}
