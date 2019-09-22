package cliq.entity;

import cliq.type.Visibilidade;
import gate.annotation.Converter;
import gate.annotation.Description;
import gate.annotation.Entity;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.constraint.Maxlength;
import gate.constraint.Required;
import gate.converter.EnumStringConverter;
import gate.type.ID;
import gate.type.Phone;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Icon("2059")
@Name("Contato")
public class Contato implements Serializable, Comparable<Contato>
{

	@Required
	@Name("ID")
	@Description("ID do contato")
	private ID id;

	@Required
	@Name("Tipo")
	@Description("Tipo do contato")
	private Tipo tipo;

	@Required
	@Name("Compartilhamento")
	@Description("Compartilhamento do contato")
	private Visibilidade visibilidade;

	@Maxlength(64)
	@Name("Login do Contato")
	@Description("O campo LOGIN deve possuir no máximo 64 caracteres")
	private String login;

	@Required
	@Name("Nome")
	@Maxlength(256)
	@Description("Nome do contato")
	private String nome;

	@Name("EMail")
	@Maxlength(256)
	@Description("Email do contato")
	private String email;

	@Maxlength(20)
	@Name("Telefone")
	@Description("Telefone do contato")
	private Phone telefone;

	@Maxlength(20)
	@Name("Celular")
	@Description("Celular do contato")
	private Phone celular;

	@Name("Comentários")
	@Description("Comentários sobre o contato")
	private String comentarios;

	@Name("Equipe")
	@Description("Equipe criadora do contrato")
	private Equipe role;

	private List<Equipe> roles;

	public ID getId()
	{
		return id;
	}

	public Contato setId(ID id)
	{
		this.id = id;
		return this;
	}

	public String getNome()
	{
		return nome;
	}

	public Contato setNome(String nome)
	{
		this.nome = nome;
		return this;
	}

	public String getEmail()
	{
		return email;
	}

	public Contato setEmail(String email)
	{
		this.email = email;
		return this;
	}

	public Phone getTelefone()
	{
		return telefone;
	}

	public Contato setTelefone(Phone telefone)
	{
		this.telefone = telefone;
		return this;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public Phone getCelular()
	{
		return celular;
	}

	public Contato setCelular(Phone celular)
	{
		this.celular = celular;
		return this;

	}

	public String getComentarios()
	{
		return comentarios;
	}

	public Contato setComentarios(String comentarios)
	{
		this.comentarios = comentarios;
		return this;
	}

	public Tipo getTipo()
	{
		return tipo;
	}

	public Contato setTipo(Tipo tipo)
	{
		this.tipo = tipo;
		return this;
	}

	public Visibilidade getVisibilidade()
	{
		return visibilidade;
	}

	public Contato setVisibilidade(Visibilidade visibilidade)
	{
		this.visibilidade = visibilidade;
		return this;
	}

	public Equipe getRole()
	{
		if (role == null)
			role = new Equipe();
		return role;
	}

	public Contato setRole(Equipe role)
	{
		this.role = role;
		return this;
	}

	public List<Equipe> getRoles()
	{
		if (roles == null)
			roles = new ArrayList<>();
		return roles;
	}

	public Contato setRoles(List<Equipe> roles)
	{
		this.roles = roles;
		return this;
	}

	@Override
	public int compareTo(Contato o)
	{
		if (nome != null)
			if (o.nome != null)
				return nome.compareTo(o.nome);
			else
				return 1;
		else if (o.nome != null)
			return -1;
		else
			return 0;
	}

	@Converter(EnumStringConverter.class)
	public enum Tipo
	{
		@Icon("2059")
		@Name("Cidadão")
		CIDADAO,
		@Icon("2008")
		@Name("Empresa")
		EMPRESA,
		@Icon("2004")
		@Name("Usuário")
		USUARIO;

		@Override
		public String toString()
		{
			return Name.Extractor.extract(this).orElse(name());
		}
	}

}
