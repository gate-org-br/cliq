package cliq.entity;

import gate.annotation.Color;
import gate.annotation.Column;
import gate.annotation.Converter;
import gate.annotation.Description;
import gate.annotation.Entity;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.constraint.Required;
import gate.converter.EnumStringConverter;
import gate.entity.Role;
import gate.entity.User;
import gate.type.ID;
import gate.type.LocalDateInterval;
import gate.type.LocalTimeInterval;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Entity
@Icon("1007")
public class Evento implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Required
	@Description("Qual é o número do evento?")
	private ID id;

	@Required
	@Description("Qual é o tipo do evento?")
	private Tipo tipo;

	@Required
	@Column("Uzer")
	@Description("Quem fez o registro do evento?")
	private User user;

	@Column("Origem")
	@Description("Origem do evento")
	private Role origem;

	@Required
	@Description("A qual chamado pertence do evento?")
	private Chamado chamado;

	@Required
	@Description("Quando o evento foi registrada?")
	private LocalDateTime data;

	@Required
	@Name("Status")
	@Description("Selecione um status para o evento")
	private String status;

	@Name("Alteração")
	@Description("Data da última alteração do evento")
	private LocalDateTime alteracao;

	private String descricao;

	private String observacoes;

	private Anexo anexo;

	private transient LocalDateInterval.Mutable periodo;
	private transient LocalTimeInterval.Mutable horario;

	public String getStatus()
	{
		return status;
	}

	public Evento setStatus(String status)
	{
		this.status = status;
		return this;
	}

	public ID getId()
	{
		return id;
	}

	public Evento setId(ID id)
	{
		this.id = id;
		return this;
	}

	public Tipo getTipo()
	{
		return tipo;
	}

	public Evento setTipo(Tipo tipo)
	{
		this.tipo = tipo;
		return this;
	}

	public User getUser()
	{
		if (user == null)
			user = new Pessoa();
		return user;
	}

	public Evento setUser(User user)
	{
		this.user = user;
		return this;
	}

	public Role getOrigem()
	{
		if (origem == null)
			origem = new Role();
		return origem;
	}

	public void setOrigem(Role origem)
	{
		this.origem = origem;
	}

	public Chamado getChamado()
	{
		if (chamado == null)
			chamado = new Chamado();
		return chamado;
	}

	public Evento setChamado(Chamado chamado)
	{
		this.chamado = chamado;
		return this;
	}

	public LocalDateTime getAlteracao()
	{
		return alteracao;
	}

	public void setAlteracao(LocalDateTime alteracao)
	{
		this.alteracao = alteracao;
	}

	public LocalDateTime getData()
	{
		return data;
	}

	public Evento setData(LocalDateTime data)
	{
		this.data = data;
		return this;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public Evento setDescricao(String descricao)
	{
		this.descricao = descricao;
		return this;
	}

	public Anexo getAnexo()
	{
		if (anexo == null)
			anexo = new Anexo();
		return anexo;
	}

	public Evento setAnexo(Anexo anexo)
	{
		this.anexo = anexo;
		return this;
	}

	public String getObservacoes()
	{
		return observacoes;
	}

	public Evento setObservacoes(String observacoes)
	{
		this.observacoes = observacoes;
		return this;
	}

	public LocalDateInterval.Mutable getPeriodo()
	{
		if (periodo == null)
			periodo = new LocalDateInterval.Mutable();
		return periodo;
	}

	public LocalTimeInterval.Mutable getHorario()
	{
		if (horario == null)
			horario = new LocalTimeInterval.Mutable();
		return horario;
	}

	public void setPeriodo(LocalDateInterval.Mutable periodo)
	{
		this.periodo = periodo;
	}

	public void setHorario(LocalTimeInterval.Mutable horario)
	{
		this.horario = horario;
	}

	public String getTexto()
	{
		return getObservacoes() != null ? getObservacoes() : getDescricao();
	}

	@Icon("2013")
	@Converter(EnumStringConverter.class)
	public enum Tipo
	{

		@Icon("2223")
		@Name("Criação")
		CRIACAO(true),
		@Icon("1000")
		@Name("Conclusão")
		CONCLUSAO(true),
		@Icon("1001")
		@Name("Cancelamento")
		CANCELAMENTO(true),
		@Icon("2015")
		@Name("Comentário Simples")
		COMENTARIO_SIMPLES(true),
		@Icon("2016")
		@Name("Comentário Interno")
		COMENTARIO_INTERNO(false),
		@Icon("2034")
		@Name("Encaminhamento")
		ENCAMINHAMENTO(true),
		@Icon("2004")
		@Name("Captura")
		CAPTURA(true),
		@Icon("2005")
		@Name("Liberação")
		LIBERACAO(true),
		@Icon("2039")
		@Name("Atribuição")
		ATRIBUICAO(true),
		@Icon("2017")
		@Name("Suspensão")
		SUSPENSAO(true),
		@Icon("2020")
		@Name("Retomada")
		RETOMADA(true),
		@Icon("2226")
		@Name("Importação")
		IMPORTACAO(true),
		@Icon("2227")
		@Name("Distribuição")
		DISTRIBUICAO(true),
		@Icon("2224")
		@Name("Reabertura")
		REABERTURA(true),
		@Icon("2072")
		@Name("Excecução de Tarefa")
		EXECUCAO_TAREFA(true),
		@Icon("2027")
		@Name("Cancelamento de Tarefa")
		CANCELAMENTO_TAREFA(true),
		@Icon("2100")
		@Name("Promoção")
		PROMOCAO(true),
		@Icon("2117")
		@Name("Rebaixamento")
		REBAIXAMENTO(true),
		@Icon("2003")
		@Name("Prazo de Resposta")
		PRAZO_RESPOSTA(true),
		@Icon("2003")
		@Name("Prazo de Solução")
		PRAZO_SOLUCAO(true),
		@Icon("2239")
		@Name("Envio para avaliação")
		ENVIO_AVALIACAO(true),
		@Icon("2102")
		@Name("Avaliação")
		AVALIACAO(true),
		@Icon("2225")
		@Name("Resposta")
		ATENDIMENTO(true),
		@Icon("2005")
		@Name("Compartilhamento")
		COMPARTILHAMENTO(true),
		@Icon("2004")
		@Name("Descompartilhamento")
		DESCOMPARTILHAMENTO(true),
		@Icon("2200")
		@Name("Solicitação de homologação")
		HOMOLOGACAO(false),
		@Icon("2037")
		@Name("Aceite de homologação")
		HOMOLOGACAO_ACEITE(false),
		@Icon("2038")
		@Name("Recusa de homologação")
		HOMOLOGACAO_RECUSA(false),
		@Icon("cancel")
		@Name("Cancelamento de homologação")
		HOMOLOGACAO_CANCELAMENTO(false),
		@Icon("2058")
		@Name("Solicitação de feedback")
		FEEDBACK(true),
		@Icon("2037")
		@Name("Aceite de solução")
		FEEDBACK_ACEITE(true),
		@Icon("2038")
		@Name("Recusa de solução")
		FEEDBACK_RECUSA(true),
		@Icon("2231")
		@Name("Cancelamento de feedback")
		FEEDBACK_CANCELAMENTO(true),
		@Icon("2045")
		@Name("Envio para Aprovação")
		APROVACAO(true),
		@Icon("2037")
		@Name("Aceite de aprovação")
		APROVACAO_ACEITE(true),
		@Icon("2038")
		@Name("Recusa de aprovação")
		APROVACAO_RECUSA(true),
		@Icon("2120")
		@Name("Cancelamento de aprovação")
		APROVACAO_CANCELAMENTO(true),
		@Icon("1006")
		@Name("Envio para complementação")
		COMPLEMENTACAO(true),
		@Icon("2037")
		@Name("Complemento de chamado")
		COMPLEMENTO(true),
		@Icon("2120")
		@Name("Cancelamento de complementação")
		COMPLEMENTACAO_CANCELAMENTO(true),
		@Icon("1000")
		@Color("#660000")
		@Name("Conclusão rejeitada")
		CONCLUSAO_REJEITADA(true),
		@Icon("1001")
		@Color("#660000")
		@Name("Cancelamento rejeitado")
		CANCELAMENTO_REJEITADO(true);

		private final boolean publico;

		Tipo(boolean publico)
		{
			this.publico = publico;
		}

		@Override
		public String toString()
		{
			return Name.Extractor.extract(this).orElse(name());
		}

		public boolean getPublico()
		{
			return publico;
		}
	}

	@Converter(EnumStringConverter.class)
	public enum Agrupamento
	{

		@Name("Tipo")
		@Icon("cliq.entity.Evento$Tipo")
		TIPO(Evento.Tipo.class, "Evento.tipo", "Evento.tipo"),
		@Name("Usuário")
		@Icon("gate.entity.User")
		USUARIO(String.class, "Uzer.name", "Uzer.name");

		private final Class<?> tipo;
		private final String coluna;
		private final List<String> colunas;

		Agrupamento(Class<?> tipo, String coluna, String... colunas)
		{
			this.tipo = tipo;
			this.coluna = coluna;
			this.colunas = Arrays.asList(colunas);
		}

		public Class<?> getTipo()
		{
			return tipo;
		}

		public String getCampo()
		{
			return coluna;
		}

		public List<String> getColunas()
		{
			return colunas;
		}

		@Override
		public String toString()
		{
			return Name.Extractor.extract(this).orElse(name());
		}
	}
}
