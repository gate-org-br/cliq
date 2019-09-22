package cliq.type;

import gate.annotation.Converter;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.converter.EnumStringConverter;

@Icon("2199")
@Converter(EnumStringConverter.class)
public enum Visibilidade
{
	@Icon("2005")
	@Name("Privada")
	PRIVADA,
	@Icon("2006")
	@Name("Pública")
	PUBLICA;

	@Override
	public String toString()
	{
		return Name.Extractor.extract(this).orElse(name());
	}

}
