package cliq.entity;

import gate.annotation.Converter;
import gate.annotation.Entity;
import gate.annotation.Schema;
import gate.annotation.Table;
import gate.converter.custom.EntityConverter;
import gate.entity.Role;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table("Role")
@Schema("gate")
@Converter(EntityConverter.class)
public class Equipe extends Role implements Serializable
{

	private boolean contemCategorias;
	private List<Categoria> categorias;
	private List<Chamado> atendimentos;
	private List<Chamado> solicitacoes;

	public List<Chamado> getAtendimentos()
	{
		if (atendimentos == null)
			atendimentos = new ArrayList<>();
		return atendimentos;
	}

	public void setAtendimentos(List<Chamado> atendimentos)
	{
		this.atendimentos = atendimentos;
	}

	public List<Categoria> getCategorias()
	{
		if (categorias == null)
			categorias = new ArrayList<>();
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias)
	{
		this.categorias = categorias;
	}

	public List<Chamado> getSolicitacoes()
	{
		if (solicitacoes == null)
			solicitacoes = new ArrayList<>();
		return solicitacoes;
	}

	public void setSolicitacoes(List<Chamado> solicitacoes)
	{
		this.atendimentos = solicitacoes;
	}

	public boolean contemCategorias()
	{
		return contemCategorias
			|| getRoles().stream().map(e -> (Equipe) e)
				.anyMatch(e -> e.contemCategorias);
	}

	public boolean getContemCategorias()
	{
		return contemCategorias;
	}

	public void setContemCategorias(boolean contemCategorias)
	{
		this.contemCategorias = contemCategorias;
	}

}
