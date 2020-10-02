package cliq.entity;

import cliq.type.Complexidade;
import cliq.type.Nivel;
import cliq.type.Prioridade;
import gate.annotation.Description;
import gate.annotation.Entity;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.constraint.Maxlength;
import gate.constraint.Required;
import gate.error.AppException;
import gate.type.ID;
import gate.type.LocalTimeInterval;
import gate.util.Comparer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

@Entity
@Icon("2058")
public class SLA
{

	@Required
	@Name("ID")
	@Description("Política de SLA")
	private ID id;

	@Required
	@Name("Precedência")
	@Description("Precedência da regra.")
	private Integer precedencia;

	@Required
	@Name("Nome")
	@Maxlength(256)
	@Description("Nome da política de SLA.")
	private String nome;

	@Name("Origem")
	@Description("Origem à qual a política de SLA se aplica.")
	private Equipe origem;

	@Name("Solicitante")
	@Description("Solicitante ao qual a política de SLA se aplica.")
	private Pessoa solicitante;

	@Name("Categoria")
	@Description("Tipo de chamado ao qual a política de SLA se aplica.")
	private Categoria categoria;

	@Name("Projeto")
	@Description("Define se a política se aplica apenas a chamados ou apenas a projetos.")
	private Boolean projeto;

	@Name("Nível")
	@Description("Define se a política se aplica apenas a chamados de um determinado nível.")
	private Nivel nivel;

	@Name("Prioridade")
	@Description("Define se a política se aplica apenas a chamados de determinada prioridade.")
	private Prioridade prioridade;

	@Name("Complexidade")
	@Description("Define se a política se aplica apenas a chamados de determinada complexidade.")
	private Complexidade complexidade;

	@Required
	@Name("Tempo de Resposta")
	@Description("Prazo para o primeiro atendimento do chamado.")
	private Integer ini;

	@Required
	@Name("Tempo de Solução")
	@Description("Prazo para a solução do chamado.")
	private Integer fim;

	@Required
	@Name("Unidade do Tempo de Resposta")
	@Description("Unidade do prazo para o primeiro atendimento do chamado.")
	private Unidade uini;

	@Required
	@Name("Unidade do Tempo de Resposta")
	@Description("Unidade do prazo para a solução do chamado.")
	private Unidade ufim;

	@Required
	@Name("Urgente")
	@Description("Políticas de SLA urgentes não consideram o horário de expediente da organização.")
	private Boolean urgente;

	@Description("Expediente da organização nos domingos")
	private transient LocalTimeInterval sun;

	@Description("Expediente da organização nas segundas")
	private transient LocalTimeInterval mon;

	@Description("Expediente da organização nas terças")
	private transient LocalTimeInterval tue;

	@Description("Expediente da organização nas quartas")
	private transient LocalTimeInterval wed;

	@Description("Expediente da organização nas quintas")
	private transient LocalTimeInterval thu;

	@Description("Expediente da organização nas sextas")
	private transient LocalTimeInterval fri;

	@Description("Expediente da organização nas sábados")
	private transient LocalTimeInterval sat;

	public ID getId()
	{
		return id;
	}

	public void setId(ID id)
	{
		this.id = id;
	}

	public enum Unidade
	{

		Minuto(1),
		Hora(60),
		Dia(24 * 60),
		Semana(7 * 24 * 60);

		int fator;

		Unidade(int fator)
		{
			this.fator = fator;
		}

		public int getFator()
		{
			return fator;
		}
	}

	public Integer getIni()
	{
		return ini;
	}

	public void setIni(Integer ini)
	{
		this.ini = ini;
	}

	public Integer getFim()
	{
		return fim;
	}

	public void setFim(Integer fim)
	{
		this.fim = fim;
	}

	public Unidade getUini()
	{
		return uini;
	}

	public void setUini(Unidade uini)
	{
		this.uini = uini;
	}

	public Unidade getUfim()
	{
		return ufim;
	}

	public void setUfim(Unidade ufim)
	{
		this.ufim = ufim;
	}

	public Categoria getCategoria()
	{
		if (categoria == null)
			categoria = new Categoria();
		return categoria;
	}

	public void setCategoria(Categoria categoria)
	{
		this.categoria = categoria;
	}

	public Boolean getUrgente()
	{
		return urgente;
	}

	public void setUrgente(Boolean urgente)
	{
		this.urgente = urgente;
	}

	public Equipe getOrigem()
	{
		if (origem == null)
			origem = new Equipe();
		return origem;
	}

	public void setOrigem(Equipe origem)
	{
		this.origem = origem;
	}

	public LocalDateTime getPrazoResposta(LocalDateTime data) throws AppException
	{
		return getData(data, getIni(), getUini());
	}

	public LocalDateTime getPrazoSolucao(LocalDateTime data) throws AppException
	{
		return getData(data, getFim(), getUfim());
	}

	public Prioridade getPrioridade()
	{
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade)
	{
		this.prioridade = prioridade;
	}

	private LocalDateTime getData(LocalDateTime data, Integer value, Unidade unidade)
		throws AppException
	{
		if (Boolean.TRUE.equals(getUrgente()))
		{
			switch (unidade)
			{
				case Dia:
					return data.plusDays(value);
				case Hora:
					return data.plusHours(value);
				case Minuto:
					return data.plusMinutes(value);
				case Semana:
					return data.plusWeeks(value);
			}
		} else
		{
			var expediente = Stream.of(getSun(), getMon(), getTue(), getWed(), getThu(), getFri(), getSat())
				.filter(e -> e != null)
				.map(e -> e.getDuration())
				.reduce((a, b) -> a.plus(b))
				.orElse(Duration.ZERO);

			if (expediente.get(ChronoUnit.SECONDS) < 8 * 3600)
				throw new AppException("Tentativa de atribuir SLA não urgente sem especificar pelo menos 8 horas de expediente semanal");

			int minutes = unidade.getFator() * value;

			while (minutes > 0)
			{
				switch (data.getDayOfWeek())
				{
					case SUNDAY:
						if (getSun() != null && getSun().contains(data.toLocalTime()))
							minutes--;
						break;
					case MONDAY:
						if (getMon() != null && getMon().contains(data.toLocalTime()))
							minutes--;
						break;
					case TUESDAY:
						if (getTue() != null && getTue().contains(data.toLocalTime()))
							minutes--;
						break;
					case WEDNESDAY:
						if (getWed() != null && getWed().contains(data.toLocalTime()))
							minutes--;
						break;
					case THURSDAY:
						if (getThu() != null && getThu().contains(data.toLocalTime()))
							minutes--;
						break;
					case FRIDAY:
						if (getFri() != null && getFri().contains(data.toLocalTime()))
							minutes--;
						break;
					case SATURDAY:
						if (getSat() != null && getSat().contains(data.toLocalTime()))
							minutes--;
						break;
				}

				data = data.plusMinutes(1);
			}
		}
		return data;
	}

	public boolean matches(Chamado chamado)
	{
		return (Boolean.TRUE.equals(getUrgente())
			|| (getMon() != null && Comparer.gt(getMon().getDuration(), Duration.ZERO))
			|| (getTue() != null && Comparer.gt(getTue().getDuration(), Duration.ZERO))
			|| (getWed() != null && Comparer.gt(getWed().getDuration(), Duration.ZERO))
			|| (getThu() != null && Comparer.gt(getThu().getDuration(), Duration.ZERO))
			|| (getFri() != null && Comparer.gt(getFri().getDuration(), Duration.ZERO))
			|| (getSat() != null && Comparer.gt(getSat().getDuration(), Duration.ZERO)))
			&& (nivel == null || nivel.equals(chamado.getNivel()))
			&& (projeto == null || projeto.equals(chamado.getProjeto()))
			&& (prioridade == null || prioridade.equals(chamado.getPrioridade()))
			&& (complexidade == null || complexidade.equals(chamado.getComplexidade()))
			&& (getCategoria().getId() == null || getCategoria().getId().equals(chamado.getCategoria().getId()))
			&& (getSolicitante().getId() == null || getSolicitante().getId().equals(chamado.getSolicitante().getId()))
			&& (getOrigem().getId() == null || getOrigem().getId().equals(chamado.getSolicitante().getRole().getId()));
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Boolean getProjeto()
	{
		return projeto;
	}

	public void setProjeto(Boolean projeto)
	{
		this.projeto = projeto;
	}

	public Complexidade getComplexidade()
	{
		return complexidade;
	}

	public void setComplexidade(Complexidade complexidade)
	{
		this.complexidade = complexidade;
	}

	public LocalTimeInterval getSun()
	{
		return sun;
	}

	public void setSun(LocalTimeInterval sun)
	{
		this.sun = sun;
	}

	public LocalTimeInterval getMon()
	{
		return mon;
	}

	public void setMon(LocalTimeInterval mon)
	{
		this.mon = mon;
	}

	public LocalTimeInterval getTue()
	{
		return tue;
	}

	public void setTue(LocalTimeInterval tue)
	{
		this.tue = tue;
	}

	public LocalTimeInterval getWed()
	{
		return wed;
	}

	public void setWed(LocalTimeInterval wed)
	{
		this.wed = wed;
	}

	public LocalTimeInterval getThu()
	{
		return thu;
	}

	public void setThu(LocalTimeInterval thu)
	{
		this.thu = thu;
	}

	public LocalTimeInterval getFri()
	{
		return fri;
	}

	public void setFri(LocalTimeInterval fri)
	{
		this.fri = fri;
	}

	public LocalTimeInterval getSat()
	{
		return sat;
	}

	public void setSat(LocalTimeInterval sat)
	{
		this.sat = sat;
	}

	public Integer getPrecedencia()
	{
		return precedencia;
	}

	public void setPrecedencia(Integer precedencia)
	{
		this.precedencia = precedencia;
	}

	public Pessoa getSolicitante()
	{
		if (solicitante == null)
			solicitante = new Pessoa();
		return solicitante;
	}

	public void setSolicitante(Pessoa solicitante)
	{
		this.solicitante = solicitante;
	}

	public Nivel getNivel()
	{
		return nivel;
	}

	public void setNivel(Nivel nivel)
	{
		this.nivel = nivel;
	}
}
