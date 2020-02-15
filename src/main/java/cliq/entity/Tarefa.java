package cliq.entity;

import cliq.type.Periodo;
import gate.annotation.Description;
import gate.annotation.Entity;
import gate.annotation.Icon;
import gate.constraint.Maxlength;
import gate.constraint.Required;
import gate.type.ID;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Icon("2003")
public class Tarefa implements Serializable
{

	@Required
	private ID id;

	@Required
	@Maxlength(64)
	@Description("O campo NOME é requerido e deve conter no máximo 64 caracteres.")
	private String nome;

	@Required
	@Description("O campo CATEGORIA é requerido.")
	private Categoria categoria;

	@Required
	@Description("O campo INÍCIO é requerido. %s")
	private LocalDateTime inicio;

	@Required
	@Description("Frequência da tarefa. %s")
	private Periodo periodo;

	@Description("Define a origem de todos os chamados criados por esta tarefa?")
	private Equipe origem;

	@Description("Deseja atribuir automaticamente os chamados criados por esta tarefa?")
	private Pessoa atendente;

	@Required
	@Description("O campo DESCRIÇÃO é requerido.")
	private String descricao;

	public ID getId()
	{
		return id;
	}

	public Tarefa setId(ID id)
	{
		this.id = id;
		return this;
	}

	public String getNome()
	{
		return nome;
	}

	public Tarefa setNome(String nome)
	{
		this.nome = nome;
		return this;
	}

	public Categoria getCategoria()
	{
		if (categoria == null)
			categoria = new Categoria();
		return categoria;
	}

	public Tarefa setCategoria(Categoria categoria)
	{
		this.categoria = categoria;
		return this;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public Tarefa setDescricao(String descricao)
	{
		this.descricao = descricao;
		return this;
	}

	public LocalDateTime getInicio()
	{
		return inicio;
	}

	public Tarefa setInicio(LocalDateTime inicio)
	{
		this.inicio = inicio;
		return this;
	}

	public Periodo getPeriodo()
	{
		if (periodo == null)
			periodo = new Periodo();
		return periodo;
	}

	public Tarefa setPeriodo(Periodo periodo)
	{
		this.periodo = periodo;
		return this;
	}

	public Pessoa getAtendente()
	{
		if (atendente == null)
			atendente = new Pessoa();
		return atendente;
	}

	public Tarefa setAtendente(Pessoa user)
	{
		this.atendente = user;
		return this;
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
}
