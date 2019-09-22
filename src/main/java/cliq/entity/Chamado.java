package cliq.entity;

import cliq.type.Atendimento;
import cliq.type.Checklist;
import cliq.type.Complexidade;
import cliq.type.Nivel;
import cliq.type.Nota;
import cliq.type.Pendencia;
import cliq.type.Prioridade;
import cliq.type.Situacao;
import cliq.type.Status;
import gate.annotation.Converter;
import gate.annotation.Description;
import gate.annotation.Entity;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.constraint.Maxlength;
import gate.constraint.Required;
import gate.converter.EnumStringConverter;
import gate.entity.Role;
import gate.entity.User;
import gate.type.Duration;
import gate.type.Form;
import gate.type.ID;
import gate.type.LocalDateInterval;
import gate.type.LocalTimeInterval;
import gate.type.collections.StringSet;
import gate.util.Comparer;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Icon("2223")
public class Chamado implements Serializable, Comparable<Chamado>
{

	private static final long serialVersionUID = 1L;

	@Required
	@Name("Número")
	@Description("Número do chamado")
	private ID id;

	@Required
	@Name("Solicitante")
	@Description("Usuário solicitante do chamado")
	private User solicitante;

	@Required
	@Name("Orígem")
	@Description("Equipe solicitante do chamado")
	private gate.entity.Role origem;

	@Required
	@Name("Localização")
	@Description("Setor responsável pelo atendimento")
	private gate.entity.Role localizacao;

	@Name("Atendente")
	@Description("Responsável pelo atendimento")
	private User atendente;

	private Chamado chamado;

	@Name("Tipo")
	@Description("Categoria inicial do chamado")
	private Categoria tipo;

	@Name("Categoria")
	@Description("Categoria atual do chamado")
	private Categoria categoria;

	@Required
	@Maxlength(128)
	@Description("Título do chamado")
	private String titulo;

	@Name("Descrição")
	@Description("Descrição do chamado")
	private String descricao;

	@Required
	@Name("Data")
	@Description("Data de criação do chamado")
	private LocalDateTime data;

	@Name("Resposta")
	@Description("Data da resposta do chamado")
	public LocalDateTime resposta;

	@Name("Encerramento")
	@Description("Data da solução do chamado")
	public LocalDateTime solucao;

	@Required
	@Name("Situacao")
	@Description("Situação atual do chamado")
	private Situacao situacao;

	@Required
	@Name("Sigiloso")
	@Description("Sigilo do chamado. Chamados sigilosos só são acessíveis ao seus solicitantes e atendentes")
	private Boolean sigiloso;

	@Required
	@Name("Prioridade")
	@Description("Prioridade do chamado")
	private Prioridade prioridade;

	@Required
	@Name("Complexidade")
	@Description("Complexidade do chamado")
	private Complexidade complexidade;

	private Contato contato;

	@Name("Pazo de Resposta")
	@Description("Define o prazo entre a abertura do chamado e o primeiro atendimento")
	private LocalDateTime prazoResposta;

	@Name("Pazo de Solução")
	@Description("Define o prazo entre a abertura do chamado e sua conclusão")
	private LocalDateTime prazoSolucao;

	@Name("Feedback")
	@Description("Data do envio para feedback")
	private LocalDateTime feedback;

	@Name("Capturado")
	@Description("O chamado está sob a responsabilidade de alguém?")
	private Boolean capturado;

	@Name("Anexo")
	@Description("Anexo do chamado")
	private Anexo anexo;

	@Required
	@Name("Documentação")
	@Description("Define se o chamado é considerado como parte do catálogo de soluções")
	private Boolean documentacao;

	@Name("Classificado")
	@Description("Define se o chamado já foi classificado")
	private Boolean classificado;

	@Required
	@Name("Projeto")
	@Description("Define se o chamado é um projeto")
	private Boolean projeto;

	@Name("Formulário")
	@Description("Formulário do chamado")
	private Form formulario;

	private Boolean notificado;
	private StringSet notificados;

	@Description("Define a data na qual o chamado será retomado automaticamente")
	private LocalDateTime retomada;

	@Name("Agendado")
	@Description("Define se o chamado foi criado automaticamente")
	private Boolean agendado;

	@Name("Pessoa aprovadora")
	@Description("Pessoa responsável pelo aprovação do chamado")
	private User pessoaAprovadora;

	@Name("Equipe aprovadora")
	@Description("Equipe responsável pelo aprovação do chamado")
	private gate.entity.Role equipeAprovadora;

	@Name("Pessoa homologadora")
	@Description("Pessoa responsável pelo homologação do chamado")
	private User pessoaHomologadora;

	@Name("Equipe homologadora")
	@Description("Equipe responsável pelo homologação do chamado")
	private gate.entity.Role equipeHomologadora;

	private Checklist checklist;

	@Name("Nível")
	@Description("Nível do chamado")
	private Nivel nivel;

	@Name("Atendimento")
	@Description("Define se o atendimento foi local ou remoto")
	private Atendimento atendimento;

	@Required
	@Description("Nota atribuída ao chamado pelo solicitante")
	private Nota nota;

	@Name("Atrasado")
	@Description("Define se o chamado está fora do prazo")
	private Boolean atrasado;

	@Name("Evento")
	@Description("Último evento do chamado")
	private Evento evento;

	@Name("Alteração")
	@Description("Data da última alteração do chamado")
	private LocalDateTime alteracao;

	@Required
	@Name("Pendência")
	@Description("Pendência do chamado")
	private Pendencia pendencia;

	private List<Evento> eventos;
	private transient Boolean avaliado;
	private List<Compartilhamento> compartilhamentos;
	private transient LocalDateInterval.Mutable periodo;
	private transient LocalTimeInterval.Mutable horario;

	public Boolean getNotificado()
	{
		return notificado;
	}

	public void setNotificado(Boolean notificado)
	{
		this.notificado = notificado;
	}

	public StringSet getNotificados()
	{
		if (notificados == null)
			notificados = new StringSet();
		return notificados;
	}

	public Chamado setNotificados(StringSet notificados)
	{
		this.notificados = notificados;
		return this;
	}

	public Contato getContato()
	{
		if (contato == null)
			contato = new Contato();
		return contato;
	}

	public Chamado setContato(Contato contato)
	{
		this.contato = contato;
		return this;
	}

	public Prioridade getPrioridade()
	{
		return prioridade;
	}

	public Chamado setPrioridade(Prioridade prioridade)
	{
		this.prioridade = prioridade;
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

	public List<Evento> getEventos()
	{
		if (eventos == null)
			eventos = new ArrayList<>();
		return eventos;
	}

	public void setEventos(List<Evento> eventos)
	{
		this.eventos = eventos;
	}

	public Situacao getSituacao()
	{
		return situacao;
	}

	public Chamado setSituacao(Situacao situacao)
	{
		this.situacao = situacao;
		return this;
	}

	public ID getId()
	{
		return id;
	}

	public Chamado setId(ID id)
	{
		this.id = id;
		return this;
	}

	public gate.entity.Role getLocalizacao()
	{
		if (localizacao == null)
			localizacao = new Equipe();
		return localizacao;
	}

	public Chamado setLocalizacao(Role role)
	{
		this.localizacao = role;
		return this;
	}

	public Categoria getCategoria()
	{
		if (categoria == null)
			categoria = new Categoria();
		return categoria;
	}

	public Chamado setCategoria(Categoria categoria)
	{
		this.categoria = categoria;
		return this;
	}

	public Categoria getTipo()
	{
		if (categoria == null)
			categoria = new Categoria();
		return categoria;
	}

	public Chamado setTipo(Categoria tipo)
	{
		this.tipo = tipo;
		return this;
	}

	public String getTitulo()
	{
		return titulo;
	}

	public Chamado setTitulo(String titulo)
	{
		this.titulo = titulo;
		return this;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public Chamado setDescricao(String descricao)
	{
		this.descricao = descricao;
		return this;
	}

	public LocalDateTime getData()
	{
		return data;
	}

	public Chamado setData(LocalDateTime data)
	{
		this.data = data;
		return this;
	}

	public User getSolicitante()
	{
		if (solicitante == null)
			solicitante = new Pessoa();
		return solicitante;
	}

	public Chamado setSolicitante(User user)
	{
		this.solicitante = user;
		return this;
	}

	public User getAtendente()
	{
		if (atendente == null)
			atendente = new Pessoa();
		return atendente;
	}

	public Chamado setAtendente(User atendente)
	{
		this.atendente = atendente;
		return this;
	}

	public Complexidade getComplexidade()
	{
		return complexidade;
	}

	public Chamado setComplexidade(Complexidade complexidade)
	{
		this.complexidade = complexidade;
		return this;
	}

	public LocalDateTime getPrazoResposta()
	{
		return prazoResposta;
	}

	public Chamado setPrazoResposta(LocalDateTime prazoResposta)
	{
		this.prazoResposta = prazoResposta;
		return this;
	}

	public LocalDateTime getPrazoSolucao()
	{
		return prazoSolucao;
	}

	public Chamado setPrazoSolucao(LocalDateTime prazoSolucao)
	{
		this.prazoSolucao = prazoSolucao;
		return this;
	}

	public Boolean getCapturado()
	{
		return capturado;
	}

	public Chamado setCapturado(Boolean capturado)
	{
		this.capturado = capturado;
		return this;
	}

	public Anexo getAnexo()
	{
		if (anexo == null)
			anexo = new Anexo();
		return anexo;
	}

	public Chamado setAnexo(Anexo anexo)
	{
		this.anexo = anexo;
		return this;
	}

	public Boolean getDocumentacao()
	{
		return documentacao;
	}

	public Chamado setDocumentacao(Boolean documentacao)
	{
		this.documentacao = documentacao;
		return this;
	}

	@Override
	public String toString()
	{
		StringBuilder string = new StringBuilder();
		if (getTitulo() != null)
			string.append(String.format("%s%n", getTitulo()));
		if (getDescricao() != null)
			string.append(String.format("%s%n", getDescricao()));
		return string.toString();
	}

	public String getURL(User user)
	{
		if (user.equals(getAtendente()))
			return String.format("Gate?MODULE=cliq.modulos.atendimento&SCREEN=Pessoais&ACTION=Select&form.id=%s", getId());
		else if (user.getRole().equals(getLocalizacao()))
			return String.format("Gate?MODULE=cliq.modulos.atendimento&SCREEN=DaEquipe&ACTION=Select&form.id=%s", getId());
		else
			return String.format("Gate?MODULE=cliq.modulos.solicitante&SCREEN=Solicitante&ACTION=Select&form.id=%s", getId());
	}

	@Override
	public int compareTo(Chamado chamado)
	{
		if (!situacao.equals(chamado.situacao))
			return situacao.compareTo(chamado.situacao);

		if (!Objects.equals(getPendencia(), chamado.getPendencia()))
			if (getPendencia() != null && chamado.getPendencia() == null)
				return 1;
			else if (getPendencia() == null && chamado.getPendencia() != null)
				return -1;
			else
				return getPendencia().compareTo(chamado.getPendencia());

		if (!Objects.equals(prazoResposta, chamado.prazoResposta))
			if (prazoResposta != null && chamado.prazoResposta == null)
				return -1;
			else if (prazoResposta == null && chamado.prazoResposta != null)
				return 1;
			else
				return prazoResposta.compareTo(chamado.prazoResposta);

		if (!Objects.equals(prazoSolucao, chamado.prazoSolucao))
			if (prazoSolucao != null && chamado.prazoSolucao == null)
				return -1;
			else if (prazoSolucao == null && chamado.prazoSolucao != null)
				return 1;
			else
				return prazoSolucao.compareTo(chamado.prazoSolucao);

		if (!prioridade.equals(chamado.prioridade))
			return chamado.prioridade.compareTo(prioridade);

		if (!data.equals(chamado.data))
			return data.compareTo(chamado.data);

		return id.compareTo(chamado.id);
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Chamado && id.equals(((Chamado) obj).id);
	}

	@Override
	public int hashCode()
	{
		return id != null ? id.getValue() : 0;
	}

	public Boolean getAtrasado()
	{
		return atrasado;
	}

	public Chamado setAtrasado(Boolean atrasado)
	{
		this.atrasado = atrasado;
		return this;
	}

	public Boolean getProjeto()
	{
		return projeto;
	}

	public Chamado setProjeto(Boolean projeto)
	{
		this.projeto = projeto;
		return this;
	}

	public Boolean getClassificado()
	{
		return classificado;
	}

	public void setClassificado(Boolean classificado)
	{
		this.classificado = classificado;
	}

	public Chamado getChamado()
	{
		if (chamado == null)
			chamado = new Chamado();
		return chamado;
	}

	public void setChamado(Chamado chamado)
	{
		this.chamado = chamado;
	}

	public Form getFormulario()
	{
		if (formulario == null)
			formulario = new Form();
		return formulario;
	}

	public Chamado setFormulario(Form form)
	{
		this.formulario = form;
		return this;
	}

	public LocalDateTime getFeedback()
	{
		return feedback;
	}

	public Chamado setFeedback(LocalDateTime feedback)
	{
		this.feedback = feedback;
		return this;
	}

	public LocalDateTime getRetomada()
	{
		return retomada;
	}

	public Chamado setRetomada(LocalDateTime retomada)
	{
		this.retomada = retomada;
		return this;
	}

	public User getPessoaAprovadora()
	{
		if (pessoaAprovadora == null)
			pessoaAprovadora = new Pessoa();
		return pessoaAprovadora;
	}

	public Chamado setPessoaAprovadora(User pessoaAprovadora)
	{
		this.pessoaAprovadora = pessoaAprovadora;
		return this;
	}

	public gate.entity.Role getEquipeAprovadora()
	{
		if (equipeAprovadora == null)
			equipeAprovadora = new Equipe();
		return equipeAprovadora;
	}

	public Chamado setEquipeAprovadora(gate.entity.Role equipeAprovadora)
	{
		this.equipeAprovadora = equipeAprovadora;
		return this;
	}

	public User getPessoaHomologadora()
	{
		if (pessoaHomologadora == null)
			pessoaHomologadora = new Pessoa();
		return pessoaHomologadora;
	}

	public Chamado setPessoaHomologadora(User pessoaHomologadora)
	{
		this.pessoaHomologadora = pessoaHomologadora;
		return this;
	}

	public gate.entity.Role getEquipeHomologadora()
	{
		if (equipeHomologadora == null)
			equipeHomologadora = new Equipe();
		return equipeHomologadora;
	}

	public Chamado setEquipeHomologadora(gate.entity.Role equipeHomologadora)
	{
		this.equipeHomologadora = equipeHomologadora;
		return this;
	}

	public Boolean getAgendado()
	{
		return agendado;
	}

	public void setAgendado(Boolean agendado)
	{
		this.agendado = agendado;
	}

	public gate.entity.Role getOrigem()
	{
		if (origem == null)
			origem = new Equipe();
		return origem;
	}

	public Chamado setOrigem(gate.entity.Role origem)
	{
		this.origem = origem;
		return this;
	}

	public Boolean getSigiloso()
	{
		return sigiloso;
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

	public Chamado setSigiloso(Boolean sigiloso)
	{
		this.sigiloso = sigiloso;
		return this;
	}

	public Nivel getNivel()
	{
		return nivel;
	}

	public Chamado setNivel(Nivel nivel)
	{
		this.nivel = nivel;
		return this;
	}

	public Checklist getChecklist()
	{
		if (checklist == null)
			checklist = new Checklist();
		return checklist;
	}

	public Chamado setChecklist(Checklist checklist)
	{
		this.checklist = checklist;
		return this;
	}

	public Atendimento getAtendimento()
	{
		return atendimento;
	}

	public Chamado setAtendimento(Atendimento atendimento)
	{
		this.atendimento = atendimento;
		return this;
	}

	public Nota getNota()
	{
		return nota;
	}

	public Chamado setNota(Nota nota)
	{
		this.nota = nota;
		return this;
	}

	public LocalDateTime getResposta()
	{
		return resposta;
	}

	public Chamado setResposta(LocalDateTime resposta)
	{
		this.resposta = resposta;
		return this;
	}

	public LocalDateTime getSolucao()
	{
		return solucao;
	}

	public Chamado setSolucao(LocalDateTime solucao)
	{
		this.solucao = solucao;
		return this;
	}

	public Pendencia getPendencia()
	{
		return pendencia;
	}

	public Chamado setPendencia(Pendencia pendencia)
	{
		this.pendencia = pendencia;
		return this;
	}

	public Duration getTempoResposta()
	{
		if (resposta != null)
			return Duration.of(data.until(resposta, ChronoUnit.SECONDS));
		else if (solucao != null)
			return Duration.of(data.until(solucao, ChronoUnit.SECONDS));
		else
			return null;
	}

	public Duration getTempoSolucao()
	{
		if (solucao != null)
			return Duration.of(data.until(solucao, ChronoUnit.SECONDS));
		else
			return null;
	}

	public Evento getEvento()
	{
		if (evento == null)
			evento = new Evento();
		return evento;
	}

	public Boolean getAvaliado()
	{
		return avaliado;
	}

	public void setAvaliado(Boolean avaliado)
	{
		this.avaliado = avaliado;
	}

	public Chamado setEvento(Evento evento)
	{
		this.evento = evento;
		getEventos().add(0, evento);
		return this;
	}

	public List<Compartilhamento> getCompartilhamentos()
	{
		if (compartilhamentos == null)
			compartilhamentos = new ArrayList<>();
		return compartilhamentos;
	}

	public void setCompartilhamentos(List<Compartilhamento> compartilhamentos)
	{
		this.compartilhamentos = compartilhamentos;
	}

	public Status getStatusResposta()
	{
		if (prazoResposta != null)
			if (resposta != null)
				if (Comparer.le(resposta, prazoResposta))
					return Status.OK;
				else
					return Status.ForaDoPrazo;
			else if (solucao != null)
				if (Comparer.le(solucao, prazoResposta))
					return Status.OK;
				else
					return Status.ForaDoPrazo;
			else if (Comparer.le(LocalDateTime.now(), prazoResposta))
				return Status.OK;
			else
				return Status.ForaDoPrazo;
		else
			return Status.NaoSeAplica;
	}

	public Status getStatusSolucao()
	{
		if (prazoSolucao != null)
			if (solucao != null)
				if (Comparer.le(solucao, prazoSolucao))
					return Status.OK;
				else
					return Status.ForaDoPrazo;
			else if (Comparer.le(LocalDateTime.now(), prazoSolucao))
				return Status.OK;
			else
				return Status.ForaDoPrazo;
		return Status.NaoSeAplica;
	}

	@Converter(EnumStringConverter.class)
	public enum Agrupamento
	{

		@Name("Categoria")
		@Icon("cliq.entity.Categoria")
		CATEGORIA(String.class, "Categoria.nome", "Categoria.nome"),
		@Icon("2227")
		@Name("Origem")
		ORIGEM(String.class, "Origem.name", "Origem.name"),
		@Icon("2059")
		@Name("Solicitante")
		SOLICITANTE(String.class, "Solicitante.name", "Solicitante.name"),
		@Icon("2226")
		@Name("Localização")
		LOCALIZACAO(String.class, "Localizacao.name", "Localizacao.name"),
		@Icon("2075")
		@Name("Atendente")
		ATENDENTE(String.class, "Atendente.name", "Atendente.name"),
		@Name("Situação")
		@Icon("cliq.type.Situacao")
		SITUACAO(Situacao.class, "Chamado.situacao", "Chamado.situacao"),
		@Name("Pendencia")
		@Icon("cliq.type.Pendencia")
		PENDENCIA(Pendencia.class, "Chamado.pendencia", "Chamado.pendencia"),
		@Name("Prioridade")
		@Icon("cliq.type.Prioridade")
		PRIORIDADE(Prioridade.class, "Chamado.prioridade", "Chamado.prioridade"),
		@Name("Complexidade")
		@Icon("cliq.type.Complexidade")
		COMPLEXIDADE(Complexidade.class, "Chamado.complexidade", "Chamado.complexidade"),
		@Icon("2043")
		@Name("Projeto")
		PROJETO(Boolean.class, "Chamado.projeto", "Chamado.projeto"),
		@Name("Nota")
		@Icon("cliq.type.Nota")
		NOTA(Nota.class, "Chamado.nota", "Chamado.nota"),
		@Name("Ano")
		@Icon("date")
		ANO(Integer.class, "year(Chamado.data)", "year(Chamado.data)"),
		@Name("Mês")
		@Icon("date")
		MES(YearMonth.class, "date(Chamado.data)", "year(Chamado.data)", "month(Chamado.data)"),
		@Name("Data")
		@Icon("date")
		DATA(LocalDate.class, "date(Chamado.data)", "date(Chamado.data)"),
		@Icon("1000")
		@Name("Status de Conclusão")
		STATUS_CONCLUSAO(String.class, "Status.status", "Status.status");

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
