package cliq.entity;

import gate.annotation.Description;
import gate.annotation.Entity;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.constraint.Required;
import gate.type.ID;
import gate.entity.User;
import gate.entity.Role;

@Entity
@Icon("2005")
public class Compartilhamento
{

	@Required()
	@Name("Compartilhamento")
	@Description("Compartilhamento de Solicitação")
	private ID id;

	@Name("Equipe")
	@Description("Equipe com a qual o chamado está sendo compartilhado")
	private Role equipe;

	@Name("Pessoa")
	@Description("Pessoa com a qual o chamado está sendo compartilhado")
	private User pessoa;

	@Name("Pessoa")
	@Description("Chamado compartilhado")
	private Chamado chamado;

	public ID getId()
	{
		return id;
	}

	public Compartilhamento setId(ID id)
	{
		this.id = id;
		return this;
	}

	public Role getEquipe()
	{
		if (equipe == null)
			equipe = new Role();
		return equipe;
	}

	public Compartilhamento setEquipe(Role equipe)
	{
		this.equipe = equipe;
		return this;
	}

	public User getPessoa()
	{
		if (pessoa == null)
			pessoa = new User();
		return pessoa;
	}

	public Compartilhamento setPessoa(User pessoa)
	{
		this.pessoa = pessoa;
		return this;
	}

	public Chamado getChamado()
	{
		if (chamado == null)
			chamado = new Chamado();
		return chamado;
	}

	public Compartilhamento setChamado(Chamado chamado)
	{
		this.chamado = chamado;
		return this;
	}
}
