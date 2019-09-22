package cliq.type;

import gate.annotation.Converter;
import gate.annotation.Description;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.converter.EnumStringConverter;

@Icon("1007")
@Name("Pendência")
@Converter(EnumStringConverter.class)
public enum Pendencia
{
	@Icon("commit")
	@Name("Sem pendências")
	@Description("Sem pendências")
	NENHUMA,
	@Icon("2006")
	@Name("Triagem")
	@Description("Fazer triagem")
	TRIAGEM,
	@Name("Aprovação")
	@Icon("cliq.entity.Evento$Tipo:APROVACAO")
	@Description("Aprovar execução")
	APROVACAO,
	@Name("Complementação")
	@Icon("cliq.entity.Evento$Tipo:COMPLEMENTACAO")
	@Description("Complementar")
	COMPLEMENTACAO,
	@Name("Suspenso")
	@Icon("cliq.entity.Evento$Tipo:SUSPENSAO")
	@Description("Chamado suspenso")
	SUSPENSO,
	@Name("Homologação")
	@Icon("cliq.entity.Evento$Tipo:HOMOLOGACAO")
	@Description("Homologar chamado")
	HOMOLOGACAO,
	@Name("Feedback")
	@Icon("cliq.entity.Evento$Tipo:FEEDBACK")
	@Description("Aceitar solução")
	FEEDBACK,
	@Name("Avaliação")
	@Icon("cliq.entity.Evento$Tipo:ENVIO_AVALIACAO")
	@Description("Avaliar chamado")
	AVALIACAO;

	@Override
	public String toString()
	{
		return Name.Extractor.extract(this).orElse(name());
	}
}
