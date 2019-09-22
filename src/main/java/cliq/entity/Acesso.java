package cliq.entity;

import gate.annotation.Entity;

import java.io.Serializable;

@Entity
public class Acesso implements Serializable
{

	private Integer id;
	private Equipe role;
	private Categoria categoria;

	private static final long serialVersionUID = 1L;

	public Integer getId()
	{
		return id;
	}

	public Acesso setId(Integer id)
	{
		this.id = id;
		return this;
	}

	public Equipe getRole()
	{
		if (role == null)
			role = new Equipe();
		return role;
	}

	public Acesso setRole(Equipe role)
	{
		this.role = role;
		return this;
	}

	public Categoria getCategoria()
	{
		if (categoria == null)
			categoria = new Categoria();
		return categoria;
	}

	public Acesso setCategoria(Categoria categoria)
	{
		this.categoria = categoria;
		return this;
	}
}
