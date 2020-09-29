package cliq.entity;

import cliq.type.Checklist;
import cliq.type.Complexidade;
import cliq.type.Nivel;
import cliq.type.Prioridade;
import cliq.type.Visibilidade;
import gate.annotation.Description;
import gate.annotation.Entity;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.constraint.Maxlength;
import gate.constraint.Required;
import gate.entity.Role;
import gate.entity.User;
import gate.error.AppException;
import gate.language.Language;
import gate.type.Duration;
import gate.type.Form;
import gate.type.Hierarchy;
import gate.type.ID;
import gate.type.collections.StringList;
import gate.type.mime.MimeDataFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Icon("2013")
public class Categoria implements Serializable, Comparable<Categoria>, Hierarchy<Categoria>
{

	public static final MimeDataFile ICON = MimeDataFile.of(Categoria.class.getResource("Categoria.png"), "Categoria.png");

	@Required
	@Description("ID da categoria")
	private ID id;

	@Required
	@Description("Equipe responsável pelo atendimento dos chamados da categoria")
	private Role role;

	private Categoria parent;

	@Required
	@Maxlength(48)
	@Description("Nome da categoria")
	private String nome;

	@Maxlength(1024)
	@Description("Descrição da categoria")
	private String descricao;

	@Required
	@Description("Ícone que será utilizado para representar esta categoria")
	private MimeDataFile icon;

	@Required
	@Description("Tipo da categoria. \nSolicitações podem ser feitas apenas a partir de categorias públicas. \nCategorias privadas podem ser utilizadas pelos atendentes para reclassificar solicitações. \nCategorias externas são utilizadas para registrar solicitações feitas por terceiros")
	private Visibilidade visibilidade;

	@Required
	@Description("Defina se os chamados desta categoria serão por padrão projetos ou não")
	private Boolean projeto;

	@Required
	@Description("Categorias temporárias podem ser utilizadas para criar chamados, mas não para concluí-los")
	private Boolean temporaria;

	@Required
	@Description("Prioridade padrão dos chamados desta categoria")
	private Prioridade prioridade;

	@Required
	@Description("Complexidade padrão dos chamados desta categoria")
	private Complexidade complexidade;

	@Required
	@Description("Grau de sigilo padrão dos chamados desta categoria. \nChamados criados a partir de categorias sigilosas serão por padrão sigilosos. \nUm chamado sigiloso pode ser visto apenas por seu solicitante ou pelos técnicos lotados no setor onde ela se encontra")
	private Boolean sigilosa;

	private List<Equipe> roles;

	private StringList campos;
	private Form formulario;

	@Required
	@Name("Triagem")
	@Description("Define se os chamados dessa categoria exigem triagem antes de serem executados")
	private Boolean triagem;

	@Description("Define a pessoa competente para aprovar os chamdos desta categoria. Deixe este campo em branco se quiser que o criador do chamado determine a pessoa competente")
	private User pessoaAprovadora;

	@Description("Define a equipe competente para aprovar os chamdos desta categoria. Deixe este campo em branco se quiser que o criador do chamado determine a equipe competente")
	private Role equipeAprovadora;

	@Description("Define a pessoa competente para homologar os chamdos desta categoria. Deixe este campo em branco se quiser que o criador do chamado determine a pessoa competente")
	private User pessoaHomologadora;

	@Description("Define a equipe competente para homologar os chamdos desta categoria. Deixe este campo em branco se quiser que o criador do chamado determine a equipe competente")
	private Role equipeHomologadora;

	private Checklist checklist;

	@Name("Nível")
	@Description("Define o nível padrão de todos os chamados criados a partir desta categoria")
	private Nivel nivel;

	@Required
	@Name("Avaliação")
	@Description("Define se os chamados desta categoria exigirão avaliacao do solicitante por padrão")
	private Boolean avaliacao;

	@Required
	@Name("Feedback")
	@Description("Define se os chamados desta categoria devem ter por padrão suas soluções aprovadas pelo solicitante")
	private Boolean feedback;

	@Name("Duração")
	@Description("Estimativa de tempo que um chamado desta categoria leva para ser resolvido")
	private Duration duracao;

	@Name("Status")
	@Description("Um status de conclusão deverá ser selecionado desta lista sempre que um chamado desta categoria for concluído")
	private StringList.LineBreak conclusoes;

	@Name("Atalho")
	@Description("URL para a qual o usuário será deslocado ao selecionar esta categoria")
	private String atalho;

	@Name("Anexo")
	@Description("Anexo da categoria")
	private Anexo anexo;

	private List<Categoria> children;

	private User atribuir;
	private Role encaminhar;

	public Categoria()
	{

	}

	@Override
	public ID getId()
	{
		return id;
	}

	public Categoria setId(ID id)
	{
		this.id = id;
		return this;
	}

	public Visibilidade getVisibilidade()
	{
		return visibilidade;
	}

	public Categoria setVisibilidade(Visibilidade visibilidade)
	{
		this.visibilidade = visibilidade;
		return this;
	}

	public Role getRole()
	{
		if (role == null)
			role = new Role();
		return role;
	}

	public Categoria setRole(Role role)
	{
		this.role = role;
		return this;
	}

	public String getNome()
	{
		return nome;
	}

	public Categoria setNome(String nome)
	{
		this.nome = nome;
		return this;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public Categoria setDescricao(String descricao)
	{
		this.descricao = descricao;
		return this;
	}

	public MimeDataFile getIcon()
	{
		return icon;
	}

	public Categoria setIcon(MimeDataFile icon)
	{
		this.icon = icon;
		return this;
	}

	public Boolean getTriagem()
	{
		return triagem;
	}

	public Categoria setTriagem(Boolean triagem)
	{
		this.triagem = triagem;
		return this;
	}

	public List<Equipe> getRoles()
	{
		if (roles == null)
			roles = new ArrayList<>();
		return roles;
	}

	public void setRoles(List<Equipe> roles)
	{
		this.roles = roles;
	}

	public Form getFormulario()
	{
		if (formulario == null)
			formulario = new Form();
		return formulario;
	}

	public void setFormulario(Form form)
	{
		this.formulario = form;
	}

	public Prioridade getPrioridade()
	{
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade)
	{
		this.prioridade = prioridade;
	}

	public Complexidade getComplexidade()
	{
		return complexidade;
	}

	public void setComplexidade(Complexidade complexidade)
	{
		this.complexidade = complexidade;
	}

	@Override
	public String toString()
	{
		return nome != null ? nome : "?";
	}

	@Override
	public int compareTo(Categoria o)
	{
		return nome != null && o.nome != null
			? nome.compareTo(o.nome)
			: nome != null ? 1 : -1;
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Categoria && Objects.equals(id, ((Categoria) obj).id);
	}

	@Override
	public int hashCode()
	{
		return getId() == null ? 0 : getId().getValue();
	}

	public Checklist getChecklist()
	{
		if (checklist == null)
			checklist = new Checklist();
		return checklist;
	}

	public void setChecklist(Checklist checklist)
	{
		this.checklist = checklist;
	}

	public Boolean getProjeto()
	{
		return projeto;
	}

	public void setProjeto(Boolean projeto)
	{
		this.projeto = projeto;
	}

	@Override
	public Categoria getParent()
	{
		if (parent == null)
			parent = new Categoria();
		return parent;
	}

	@Override
	public Categoria setParent(Categoria parent)
	{
		this.parent = parent;
		return this;
	}

	@Override
	public List<Categoria> getChildren()
	{
		if (children == null)
			children = new ArrayList<>();
		return children;
	}

	@Override
	public Categoria setChildren(List<Categoria> children)
	{
		this.children = children;
		return this;
	}

	public StringList getCampos()
	{
		if (campos == null)
			campos = new StringList();
		return campos;
	}

	public void setCampos(StringList campos)
	{
		this.campos = campos;
	}

	public Boolean getTemporaria()
	{
		return temporaria;
	}

	public void setTemporaria(Boolean temporaria)
	{
		this.temporaria = temporaria;
	}

	public User getPessoaHomologadora()
	{
		if (pessoaHomologadora == null)
			pessoaHomologadora = new User();
		return pessoaHomologadora;
	}

	public void setPessoaHomologadora(User pessoaHomologadora)
	{
		this.pessoaHomologadora = pessoaHomologadora;
	}

	public Role getEquipeHomologadora()
	{
		if (equipeHomologadora == null)
			equipeHomologadora = new Role();
		return equipeHomologadora;
	}

	public void setEquipeHomologadora(Role equipeHomologadora)
	{
		this.equipeHomologadora = equipeHomologadora;
	}

	public User getPessoaAprovadora()
	{
		if (pessoaAprovadora == null)
			pessoaAprovadora = new User();
		return pessoaAprovadora;
	}

	public void setPessoaAprovadora(User pessoaAprovadora)
	{
		this.pessoaAprovadora = pessoaAprovadora;
	}

	public Role getEquipeAprovadora()
	{
		if (equipeAprovadora == null)
			equipeAprovadora = new Equipe();
		return equipeAprovadora;
	}

	public void setEquipeAprovadora(Role equipeAprovadora)
	{
		this.equipeAprovadora = equipeAprovadora;
	}

	public Boolean getSigilosa()
	{
		return sigilosa;
	}

	public void setSigilosa(Boolean sigilosa)
	{
		this.sigilosa = sigilosa;
	}

	public Boolean getFeedback()
	{
		return feedback;
	}

	public void setFeedback(Boolean feedback)
	{
		this.feedback = feedback;
	}

	public Nivel getNivel()
	{
		return nivel;
	}

	public void setNivel(Nivel nivel)
	{
		this.nivel = nivel;
	}

	public Boolean getAvaliacao()
	{
		return avaliacao;
	}

	public void setAvaliacao(Boolean avaliacao)
	{
		this.avaliacao = avaliacao;
	}

	public Duration getDuracao()
	{
		return duracao;
	}

	public void setDuracao(Duration tempo)
	{
		this.duracao = tempo;
	}

	public StringList.LineBreak getConclusoes()
	{
		if (conclusoes == null)
			conclusoes = new StringList.LineBreak();
		return conclusoes;
	}

	public void setConclusoes(StringList.LineBreak conclusoes)
	{
		this.conclusoes = conclusoes;
	}

	public String getAtalho()
	{
		return atalho;
	}

	public void setAtalho(String atalho)
	{
		this.atalho = atalho;
	}

	public User getAtribuir()
	{
		if (atribuir == null)
			atribuir = new Pessoa();
		return atribuir;
	}

	public void setAtribuir(User atribuir)
	{
		this.atribuir = atribuir;
	}

	public Role getEncaminhar()
	{
		if (encaminhar == null)
			encaminhar = new Equipe();
		return encaminhar;
	}

	public void setEncaminhar(Role encaminhar)
	{
		this.encaminhar = encaminhar;
	}

	public Anexo getAnexo()
	{
		if (anexo == null)
			anexo = new Anexo();
		return anexo;
	}

	public void setAnexo(Anexo anexo)
	{
		this.anexo = anexo;
	}

	public static Categoria of(List<String> string) throws AppException
	{
		Categoria categoria = new Categoria();
		if (string.isEmpty() || string.get(0).isBlank())
			throw new AppException("Tentativa de inserir categoria sem especificar o nome");
		categoria.setNome(Language.PORTUGUESE.capitalize(string.get(0)));

		categoria.setIcon(Categoria.ICON);

		categoria.setVisibilidade(Visibilidade.PUBLICA);
		categoria.setPrioridade(Prioridade.MEDIA);
		categoria.setComplexidade(Complexidade.MEDIA);
		categoria.setTriagem(Boolean.FALSE);
		categoria.setTemporaria(Boolean.FALSE);
		categoria.setSigilosa(Boolean.FALSE);
		categoria.setProjeto(Boolean.FALSE);
		categoria.setFeedback(Boolean.FALSE);
		categoria.setAvaliacao(Boolean.FALSE);

		if (string.size() > 1 && !string.get(1).isBlank())
			switch (string.get(1).toUpperCase())
			{
				case "PUBLICA":
				case "PÚBLICA":
					categoria.setVisibilidade(Visibilidade.PUBLICA);
					break;
				case "PRIVADA":
					categoria.setVisibilidade(Visibilidade.PRIVADA);
					break;
				default:
					throw new AppException("Visibilidade inválida: " + string.get(1));
			}

		if (string.size() > 2 && !string.get(2).isBlank())
			switch (string.get(2).toUpperCase())
			{
				case "BAIXA":
					categoria.setPrioridade(Prioridade.BAIXA);
					break;
				case "MEDIA":
				case "MÉDIA":
					categoria.setPrioridade(Prioridade.MEDIA);
					break;
				case "ALTA":
					categoria.setPrioridade(Prioridade.ALTA);
					break;
				case "URGENTE":
					categoria.setPrioridade(Prioridade.URGENTE);
					break;
				default:
					throw new AppException("Prioridade inválida: " + string.get(2));
			}

		if (string.size() > 3 && !string.get(3).isBlank())
			switch (string.get(3).toUpperCase())
			{
				case "BAIXA":
					categoria.setComplexidade(Complexidade.BAIXA);
					break;
				case "MEDIA":
				case "MÉDIA":
					categoria.setComplexidade(Complexidade.MEDIA);
					break;
				case "ALTA":
					categoria.setComplexidade(Complexidade.ALTA);
					break;
				case "CRITICA":
				case "CRÍTICA":
					categoria.setComplexidade(Complexidade.CRITICA);
					break;
				default:
					throw new AppException("Complexidade inválida: " + string.get(3));
			}

		if (string.size() > 4 && !string.get(4).isBlank())
			switch (string.get(4).toUpperCase())
			{
				case "SIM":
					categoria.setTriagem(Boolean.TRUE);
					break;
				case "NAO":
				case "NÃO":
					categoria.setTriagem(Boolean.FALSE);
					break;
				default:
					throw new AppException(string.get(4) + " não é um valor válido para o campo Triagem");
			}

		if (string.size() > 5 && !string.get(5).isBlank())
			switch (string.get(5).toUpperCase())
			{
				case "SIM":
					categoria.setTemporaria(Boolean.TRUE);
					break;
				case "NAO":
				case "NÃO":
					categoria.setTemporaria(Boolean.FALSE);
					break;
				default:
					throw new AppException(string.get(5) + " não é um valor válido para o campo Temporária");
			}

		if (string.size() > 6 && !string.get(6).isBlank())
			switch (string.get(6).toUpperCase())
			{
				case "SIM":
					categoria.setSigilosa(Boolean.TRUE);
					break;
				case "NAO":
				case "NÃO":
					categoria.setSigilosa(Boolean.FALSE);
					break;
				default:
					throw new AppException(string.get(6) + " não é um valor válido para o campo Sigilosa");
			}
		if (string.size() > 7 && !string.get(7).isBlank())
			switch (string.get(7).toUpperCase())
			{
				case "SIM":
					categoria.setProjeto(Boolean.TRUE);
					break;
				case "NAO":
				case "NÃO":
					categoria.setProjeto(Boolean.FALSE);
					break;
				default:
					throw new AppException(string.get(7) + " não é um valor válido para o campo Projeto");
			}

		if (string.size() > 8 && !string.get(8).isBlank())
			switch (string.get(8).toUpperCase())
			{
				case "SIM":
					categoria.setSigilosa(Boolean.TRUE);
					break;
				case "NAO":
				case "NÃO":
					categoria.setSigilosa(Boolean.FALSE);
					break;
				default:
					throw new AppException(string.get(8) + " não é um valor válido para o campo Exige Feedback");
			}

		if (string.size() > 9 && !string.get(9).isBlank())
			switch (string.get(9).toUpperCase())
			{
				case "SIM":
					categoria.setSigilosa(Boolean.TRUE);
					break;
				case "NAO":
				case "NÃO":
					categoria.setSigilosa(Boolean.FALSE);
					break;
				default:
					throw new AppException(string.get(9) + " não é um valor válido para o campo Exige Avaliação");
			}

		return categoria;
	}
}
