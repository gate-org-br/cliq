package cliq.entity;

import gate.annotation.Entity;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.annotation.Schema;
import gate.annotation.Table;
import gate.entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table("Uzer")
@Schema("gate")
@Name("Usuário")
@Icon("gate.entity.User")
public class Pessoa extends User implements Serializable
{

	private static final long serialVersionUID = 1L;

	private Collection<Chamado> chamados;
	private Collection<Chamado> capturas;

	public Collection<Chamado> getChamados()
	{
		if (chamados == null)
			chamados = new ArrayList<>();
		return chamados;
	}

	public void setChamados(Collection<Chamado> chamados)
	{
		this.chamados = chamados;
	}

	public Collection<Chamado> getCapturas()
	{
		if (capturas == null)
			capturas = new ArrayList<>();
		return capturas;
	}

	public void setCapturas(Collection<Chamado> capturados)
	{
		this.capturas = capturados;
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Pessoa && Objects.equals(this.getId(), ((Pessoa) obj).getId());
	}

	@Override
	public int hashCode()
	{
		return getId() == null ? 0 : getId().getValue();
	}
}
