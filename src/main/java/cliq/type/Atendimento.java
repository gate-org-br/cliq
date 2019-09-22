package cliq.type;

import gate.annotation.Converter;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.converter.EnumStringConverter;

@Name("Atendimento")
@Converter(EnumStringConverter.class)
public enum Atendimento
{
	@Icon("2008")
	@Name("Local")
	LOCAL,
	@Icon("2154")
	@Name("Remoto")
	REMOTO;

	@Override
	public String toString()
	{
		return Name.Extractor.extract(this).orElse(name());
	}

}
