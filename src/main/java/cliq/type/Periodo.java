package cliq.type;

import gate.annotation.Name;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Periodo implements Serializable
{

	private static final long serialVersionUID = 1L;

	private Integer valor;
	private Unidade unidade;

	public Periodo()
	{
	}

	public enum Unidade
	{
		@Name("Minuto")
		MINUTO,
		@Name("Hora")
		HORA,
		@Name("Dia")
		DIA,
		@Name("Dia útil")
		DIA_UTIL,
		@Name("Semana")
		SEMANA,
		@Name("Mês")
		MES,
		@Name("Ano")
		ANO;

		@Override
		public String toString()
		{
			return Name.Extractor.extract(this).orElse(name());
		}
	}

	public Integer getValor()
	{
		return valor;
	}

	public void setValor(Integer value)
	{
		this.valor = value;
	}

	public Unidade getUnidade()
	{
		return unidade;
	}

	public void setUnidade(Unidade unidade)
	{
		this.unidade = unidade;
	}

	public LocalDateTime addTo(LocalDateTime dateTime)
	{
		if (getValor() != null && getUnidade() != null)
		{
			switch (getUnidade())
			{
				case MINUTO:
					return dateTime.plusMinutes(valor);
				case HORA:
					return dateTime.plusHours(valor);
				case DIA:
					dateTime = dateTime.plusDays(valor);
					break;
				case DIA_UTIL:
					for (int i = 0; i < getValor(); i++)
					{
						dateTime = dateTime.plusDays(1);
						while (dateTime.getDayOfWeek() == DayOfWeek.SATURDAY
							|| dateTime.getDayOfWeek() == DayOfWeek.SUNDAY)
							dateTime = dateTime.plusDays(1);
					}
					break;
				case SEMANA:
					dateTime = dateTime.plusWeeks(valor);
					break;
				case MES:
					dateTime = dateTime.plusMonths(valor);
					break;
				case ANO:
					dateTime = dateTime.plusYears(valor);
					break;
			}
		}

		return dateTime;
	}

	@Override
	public String toString()
	{
		if (getValor() != null && getUnidade() != null)
		{
			switch (getUnidade())
			{
				case DIA:
					return String.format("%d %s", getValor(), getValor() == 1 ? "Dia" : "Dias");
				case SEMANA:
					return String.format("%d %s", getValor(), getValor() == 1 ? "Semana" : "Semanas");
				case MES:
					return String.format("%d %s", getValor(), getValor() == 1 ? "Mês" : "Meses");
				case ANO:
					return String.format("%d %s", getValor(), getValor() == 1 ? "Ano" : "Anos");
				case HORA:
					return String.format("%d %s", getValor(), getValor() == 1 ? "Hora" : "Horas");
				case MINUTO:
					return String.format("%d %s", getValor(), getValor() == 1 ? "Minuto" : "Minutos");
				case DIA_UTIL:
					return String.format("%d %s", getValor(), getValor() == 1 ? "Dia útil" : "Dias úteis");
			}
		}

		return "";
	}
}
