package cliq.type;

import java.util.List;

public class Resumo
{

	public List<Object[]> porMes;
	public List<Object[]> porPrioridade;
	public List<Object[]> porAtribuicao;
	public List<Object[]> porAtendimento;
	public List<Object[]> porComplexidade;

	public List<Object[]> getPorMes()
	{
		return porMes;
	}

	public void setPorMes(List<Object[]> porMes)
	{
		this.porMes = porMes;
	}

	public List<Object[]> getPorPrioridade()
	{
		return porPrioridade;
	}

	public void setPorPrioridade(List<Object[]> porPrioridade)
	{
		this.porPrioridade = porPrioridade;
	}

	public List<Object[]> getPorAtribuicao()
	{
		return porAtribuicao;
	}

	public void setPorAtribuicao(List<Object[]> porAtribuicao)
	{
		this.porAtribuicao = porAtribuicao;
	}

	public List<Object[]> getPorAtendimento()
	{
		return porAtendimento;
	}

	public void setPorAtendimento(List<Object[]> porAtendimento)
	{
		this.porAtendimento = porAtendimento;
	}

	public List<Object[]> getPorComplexidade()
	{
		return porComplexidade;
	}

	public void setPorComplexidade(List<Object[]> porComplexidade)
	{
		this.porComplexidade = porComplexidade;
	}
}
