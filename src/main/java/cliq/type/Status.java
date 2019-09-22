package cliq.type;

import gate.annotation.Color;
import gate.annotation.Icon;
import gate.annotation.Name;

@Name("Status")
public enum Status
{
	@Icon("1014")
	@Color("#666666")
	@Name("Não se Aplica")
	NaoSeAplica,
	@Name("OK")
	@Icon("1010")
	@Color("#006600")
	OK,
	@Icon("1011")
	@Color("#660000")
	@Name("Fora do Prazo")
	ForaDoPrazo;

	@Override
	public String toString()
	{
		return Name.Extractor.extract(this).orElse(name());
	}

}
