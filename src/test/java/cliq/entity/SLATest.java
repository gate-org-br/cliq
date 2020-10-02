package cliq.entity;

import cliq.entity.SLA.Unidade;
import gate.error.AppException;
import gate.type.LocalTimeInterval;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import org.junit.Assert;
import org.junit.Test;

public class SLATest
{

	@Test
	public void test1Minute() throws AppException
	{
		SLA sla = new SLA();

		sla.setUrgente(false);
		sla.setMon(LocalTimeInterval.of(LocalTime.of(8, 0), LocalTime.of(16, 0)));
		sla.setTue(LocalTimeInterval.of(LocalTime.of(8, 0), LocalTime.of(16, 0)));
		sla.setWed(LocalTimeInterval.of(LocalTime.of(8, 0), LocalTime.of(16, 0)));
		sla.setFri(LocalTimeInterval.of(LocalTime.of(8, 0), LocalTime.of(16, 0)));
		sla.setThu(LocalTimeInterval.of(LocalTime.of(8, 0), LocalTime.of(16, 0)));

		sla.setFim(1);
		sla.setUfim(Unidade.Minuto);

		var expected = LocalDateTime.of(2020, Month.MARCH, 2, 8, 1);
		var result = sla.getPrazoSolucao(LocalDateTime.of(2020, Month.MARCH, 1, 8, 0));
		Assert.assertEquals(expected, result);
	}

	@Test
	public void test2Hours() throws AppException
	{
		SLA sla = new SLA();

		sla.setUrgente(false);
		sla.setMon(LocalTimeInterval.of(LocalTime.of(8, 0), LocalTime.of(16, 0)));
		sla.setTue(LocalTimeInterval.of(LocalTime.of(8, 0), LocalTime.of(16, 0)));
		sla.setWed(LocalTimeInterval.of(LocalTime.of(8, 0), LocalTime.of(16, 0)));
		sla.setFri(LocalTimeInterval.of(LocalTime.of(8, 0), LocalTime.of(16, 0)));
		sla.setThu(LocalTimeInterval.of(LocalTime.of(8, 0), LocalTime.of(16, 0)));

		sla.setFim(2);
		sla.setUfim(Unidade.Hora);

		var expected = LocalDateTime.of(2020, Month.MARCH, 2, 10, 0);
		var result = sla.getPrazoSolucao(LocalDateTime.of(2020, Month.MARCH, 1, 8, 0));
		Assert.assertEquals(expected, result);
	}

	@Test
	public void test1Day() throws AppException
	{
		SLA sla = new SLA();

		sla.setUrgente(false);
		sla.setMon(LocalTimeInterval.of(LocalTime.of(8, 0), LocalTime.of(15, 59)));
		sla.setTue(LocalTimeInterval.of(LocalTime.of(8, 0), LocalTime.of(15, 59)));
		sla.setWed(LocalTimeInterval.of(LocalTime.of(8, 0), LocalTime.of(15, 59)));
		sla.setFri(LocalTimeInterval.of(LocalTime.of(8, 0), LocalTime.of(15, 59)));
		sla.setThu(LocalTimeInterval.of(LocalTime.of(8, 0), LocalTime.of(15, 59)));

		sla.setFim(1);
		sla.setUfim(Unidade.Dia);

		var expected = LocalDateTime.of(2020, Month.MARCH, 4, 16, 0);
		var result = sla.getPrazoSolucao(LocalDateTime.of(2020, Month.MARCH, 1, 8, 0));
		Assert.assertEquals(expected, result);
	}
}
