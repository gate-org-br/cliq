package cliq.dao;

import cliq.entity.Categoria;
import gate.base.Dao;
import gate.entity.Role;
import gate.entity.User;
import gate.error.AppException;
import gate.error.ConstraintViolationException;
import gate.error.NotFoundException;
import gate.sql.Link;
import gate.sql.condition.Condition;
import gate.sql.delete.Delete;
import gate.sql.insert.Insert;
import gate.sql.update.Update;
import gate.type.Hierarchy;
import gate.type.ID;
import gate.type.mime.MimeDataFile;
import java.util.List;
import java.util.stream.Collectors;

public class CategoriaDao extends Dao
{

	public CategoriaDao()
	{

	}

	public CategoriaDao(Link link)
	{
		super(link);
	}

	public List<Categoria> search(Role role) throws AppException
	{
		return Hierarchy.setup(getLink()
			.from(getClass().getResource("CategoriaDao/search(Role).sql"))
			.parameters(role.getId())
			.fetchEntityList(Categoria.class));
	}

	public List<Categoria> search(Categoria categoria)
	{
		return getLink()
			.from(getClass().getResource("CategoriaDao/search(Categoria).sql"))
			.parameters(categoria.getId())
			.fetchEntityList(Categoria.class);
	}

	public List<Categoria> search(User usuario) throws AppException
	{
		List<Categoria> categorias = getLink()
			.from(getClass().getResource("CategoriaDao/search(Usuario).sql"))
			.parameters(usuario.getRole().getId(), usuario.getRole().getId())
			.fetchEntityList(Categoria.class);
		categorias.stream().forEach(p -> p.setChildren(categorias.stream()
			.filter(c -> c.getParent().equals(p))
			.peek(c -> c.setParent(p))
			.collect(Collectors.toList())));
		categorias.removeIf(e -> e.getParent().getId() != null);
		return categorias;
	}

	public Categoria select(ID id) throws AppException
	{
		List<Categoria> categorias = getLink()
			.from(getClass().getResource("CategoriaDao/select(ID).sql"))
			.constant()
			.fetchEntityList(Categoria.class);
		Hierarchy.setup(categorias);
		return categorias.stream().filter(e -> e.getId().equals(id)).findFirst().orElseThrow(NotFoundException::new);
	}

	public Categoria select(Role role, String nome)
	{
		return getLink()
			.from(getClass().getResource("CategoriaDao/select(Role, String).sql"))
			.parameters(role.getId(), nome)
			.fetchEntity(Categoria.class)
			.orElse(null);
	}

	public void insert(Categoria categoria) throws AppException
	{
		Insert.into("Categoria")
			.set("Role$id", categoria.getRole().getId())
			.set("Parent$id", categoria.getParent().getId())
			.set("Atribuir$id", categoria.getAtribuir().getId())
			.set("Encaminhar$id", categoria.getEncaminhar().getId())
			.set("PessoaAprovadora$id", categoria.getPessoaAprovadora().getId())
			.set("EquipeAprovadora$id", categoria.getEquipeAprovadora().getId())
			.set("PessoaHomologadora$id", categoria.getPessoaHomologadora().getId())
			.set("EquipeHomologadora$id", categoria.getEquipeHomologadora().getId())
			.set("visibilidade", categoria.getVisibilidade())
			.set("nivel", categoria.getNivel())
			.set("triagem", categoria.getTriagem())
			.set("temporaria", categoria.getTemporaria())
			.set("sigilosa", categoria.getSigilosa())
			.set("nome", categoria.getNome())
			.set("campos", categoria.getCampos())
			.set("formulario", categoria.getFormulario())
			.set("descricao", categoria.getDescricao())
			.set("prioridade", categoria.getPrioridade())
			.set("complexidade", categoria.getComplexidade())
			.set("icon", categoria.getIcon())
			.set("projeto", categoria.getProjeto())
			.set("avaliacao", categoria.getAvaliacao())
			.set("feedback", categoria.getFeedback())
			.set("duracao", categoria.getDuracao())
			.set("checklist", categoria.getChecklist())
			.set("conclusoes", categoria.getConclusoes())
			.set("atalho", categoria.getAtalho())
			.set("Anexo$id", categoria.getAnexo().getId())
			.build()
			.connect(getLink())
			.fetchGeneratedKey(ID.class)
			.ifPresent(categoria::setId);
	}

	public void update(Categoria categoria) throws AppException
	{
		Update.table("Categoria")
			.set("Parent$id", categoria.getParent().getId())
			.set("Atribuir$id", categoria.getAtribuir().getId())
			.set("Encaminhar$id", categoria.getEncaminhar().getId())
			.set("PessoaAprovadora$id", categoria.getPessoaAprovadora().getId())
			.set("EquipeAprovadora$id", categoria.getEquipeAprovadora().getId())
			.set("PessoaHomologadora$id", categoria.getPessoaHomologadora().getId())
			.set("EquipeHomologadora$id", categoria.getEquipeHomologadora().getId())
			.set("visibilidade", categoria.getVisibilidade())
			.set("nivel", categoria.getNivel())
			.set("triagem", categoria.getTriagem())
			.set("temporaria", categoria.getTemporaria())
			.set("sigilosa", categoria.getSigilosa())
			.set("nome", categoria.getNome())
			.set("campos", categoria.getCampos())
			.set("formulario", categoria.getFormulario())
			.set("descricao", categoria.getDescricao())
			.set("prioridade", categoria.getPrioridade())
			.set("complexidade", categoria.getComplexidade())
			.when(categoria.getIcon() != null).set("icon", categoria.getIcon())
			.set("projeto", categoria.getProjeto())
			.set("avaliacao", categoria.getAvaliacao())
			.set("feedback", categoria.getFeedback())
			.set("duracao", categoria.getDuracao())
			.set("checklist", categoria.getChecklist())
			.set("conclusoes", categoria.getConclusoes())
			.set("atalho", categoria.getAtalho())
			.set("Anexo$id", categoria.getAnexo().getId())
			.where(Condition.of("id").eq(ID.class, categoria.getId()))
			.build()
			.connect(getLink())
			.execute();
	}

	public void delete(Categoria categoria) throws AppException
	{
		Delete.from("Categoria")
			.where(Condition.of("id").eq(ID.class, categoria.getId()))
			.build()
			.connect(getLink())
			.execute();
	}

	public boolean exists(Role role)
	{
		return getLink()
			.from("select exists (select id from Categoria where Role$id = ?)")
			.parameters(role.getId())
			.fetchObject(Boolean.class)
			.orElse(Boolean.FALSE);
	}

	public void relate(Categoria child, Categoria parent) throws ConstraintViolationException
	{
		Update.table("Categoria")
			.set("Parent$id", parent.getId())
			.where(Condition.of("id").eq(ID.class, child.getId()))
			.build()
			.connect(getLink())
			.execute();
	}

	public MimeDataFile getIcon(Categoria categoria) throws NotFoundException
	{
		return getLink()
			.from("select icon from Categoria where id = ?")
			.parameters(categoria.getId())
			.fetchObject(MimeDataFile.class)
			.orElseThrow(NotFoundException::new);
	}

}
