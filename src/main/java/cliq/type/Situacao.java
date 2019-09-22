package cliq.type;

import gate.annotation.Color;
import gate.annotation.Converter;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.converter.EnumStringConverter;

@Icon("1024")
@Name("Situação")
@Converter(EnumStringConverter.class)
public enum Situacao
{
	@Icon("2020")
	@Name("Pendente")
	@Color("#000000")
	PENDENTE,
	@Icon("1000")
	@Color("#006600")
	@Name("Concluído")
	CONCLUIDO,
	@Icon("2027")
	@Color("#660000")
	@Name("Cancelado")
	CANCELADO;

	@Override
	public String toString()
	{
		return Name.Extractor.extract(this).orElse(name());
	}

}
