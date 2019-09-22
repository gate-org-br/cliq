package cliq.type;

import gate.annotation.Converter;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.converter.EnumStringConverter;

@Converter(EnumStringConverter.class)
public enum Nivel
{
	@Icon("1")
	@Name("1º Nível")
	NIVEL1,
	@Icon("2")
	@Name("2º Nível")
	NIVEL2,
	@Icon("3")
	@Name("3º Nível")
	NIVEL3;

	@Override
	public String toString()
	{
		return Name.Extractor.extract(this).orElse(name());
	}
}
