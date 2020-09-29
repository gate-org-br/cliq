package cliq.control;

import cliq.dao.AcessoDao;
import cliq.dao.AnexoDao;
import cliq.dao.CategoriaDao;
import cliq.dao.EquipeDao;
import cliq.dao.PessoaDao;
import cliq.entity.Categoria;
import cliq.entity.Equipe;
import gate.annotation.Current;
import gate.base.Control;
import gate.constraint.Constraints;
import gate.entity.Role;
import gate.entity.User;
import gate.error.AppException;
import gate.error.NotFoundException;
import gate.sql.Link;
import gate.type.ID;
import gate.type.mime.MimeDataFile;
import java.util.List;
import javax.inject.Inject;

public class CategoriaControl extends Control
{

	@Inject
	@Current
	private Equipe equipe;

	public List<Categoria> search(Role role) throws AppException
	{
		try (CategoriaDao dao = new CategoriaDao())
		{
			return dao.search(role);
		}
	}

	public List<Categoria> search(Categoria categoria)
	{
		try (CategoriaDao dao = new CategoriaDao())
		{
			return dao.search(categoria);
		}
	}

	public List<Categoria> search(User usuario) throws AppException
	{
		try (CategoriaDao dao = new CategoriaDao())
		{
			return dao.search(usuario);
		}
	}

	public Categoria select(ID id) throws AppException
	{
		try (Link link = new Link();
			AcessoDao acessoDao = new AcessoDao(link);
			CategoriaDao categoriaDao = new CategoriaDao(link))
		{
			link.beginTran();
			Categoria categoria = categoriaDao.select(id);
			categoria.setRoles(acessoDao.search(categoria));
			link.commit();
			return categoria;
		}
	}

	public void insert(Categoria categoria) throws AppException
	{
		Constraints.validate(categoria, "visibilidade", "nivel", "temporaria", "sigilosa", "role.id",
			"nome", "formulario", "descricao", "prioridade", "complexidade", "projeto", "checklist",
			"avaliacao", "feedback", "duracao", "conclusoes", "atalho", "icon");

		if (categoria.getAtribuir().getId() != null
			&& categoria.getEncaminhar().getId() != null)
			throw new AppException("Tentativa de atribuir e encaminhar chamado ao mesmo tempo.");

		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			PessoaDao pessoaDao = new PessoaDao(link);
			EquipeDao equipeDao = new EquipeDao(link);
			AcessoDao acessoDao = new AcessoDao(link);
			CategoriaDao catetoriaDao = new CategoriaDao(link))
		{
			link.beginTran();

			if (categoria.getParent().getId() != null)
				categoria.setRole(catetoriaDao.select(categoria.getParent().getId()).getRole());

			if (categoria.getAtribuir().getId() != null)
			{
				categoria.setAtribuir(pessoaDao.select(categoria.getAtribuir().getId()));
				if (!categoria.getAtribuir().getRole().getId().equals(categoria.getRole().getId()))
					throw new AppException("Tentativa de atribuir chamado para usuário de outro setor.");
			}

			if (categoria.getEncaminhar().getId() != null)
			{
				if (categoria.getEncaminhar().getId().equals(categoria.getRole().getId()))
					throw new AppException("Tentativa de encaminhar chamado para o mesmo setor.");

				categoria.setEncaminhar(equipeDao.select(categoria.getEncaminhar().getId()));
				if (((cliq.entity.Equipe) categoria.getEncaminhar()).getCategorias().isEmpty())
					throw new AppException("Tentativa de encaminhar chamado para setor sem categorias.");
			}

			if (categoria.getAnexo().getArquivo() != null)
				anexoDao.insert(categoria.getAnexo());

			catetoriaDao.insert(categoria);
			acessoDao.insert(categoria, categoria.getRoles());
			link.commit();
		}
	}

	public void upload(List<Categoria> categorias) throws AppException
	{
		Constraints.validate(Categoria.class, categorias, "visibilidade", "nivel", "temporaria", "sigilosa",
			"nome", "formulario", "descricao", "prioridade", "complexidade", "projeto", "checklist",
			"avaliacao", "feedback", "duracao", "conclusoes", "atalho", "icon");

		if (categorias.stream().anyMatch(e -> e.getAtribuir().getId() != null
			&& e.getEncaminhar().getId() != null))
			throw new AppException("Tentativa de atribuir e encaminhar chamado ao mesmo tempo.");

		try (CategoriaDao dao = new CategoriaDao())
		{
			dao.beginTran();
			for (Categoria categoria : categorias)
				dao.insert(categoria.setRole(equipe));
			dao.commit();
		}
	}

	public void update(Categoria categoria) throws AppException
	{
		Constraints.validate(categoria, "visibilidade", "nivel", "temporaria", "sigilosa", "role.id", "nome",
			"formulario", "descricao", "prioridade", "complexidade",
			"projeto", "checklist", "avaliacao",
			"feedback", "duracao", "conclusoes", "atalho");

		if (categoria.getAtribuir().getId() != null
			&& categoria.getEncaminhar().getId() != null)
			throw new AppException("Tentativa de atribuir e encaminhar o chamado ao mesmo tempo.");

		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			PessoaDao pessoaDao = new PessoaDao(link);
			EquipeDao equipeDao = new EquipeDao(link);
			AcessoDao acessoDao = new AcessoDao(link);
			CategoriaDao catetoriaDao = new CategoriaDao(link))
		{
			link.beginTran();

			if (categoria.getAtribuir().getId() != null)
			{
				categoria.setAtribuir(pessoaDao.select(categoria.getAtribuir().getId()));
				if (!categoria.getAtribuir().getRole().equals(categoria.getRole()))
					throw new AppException("Tentativa de atribuir chamado para usuário de outro setor.");
			}

			if (categoria.getEncaminhar().getId() != null)
			{
				if (categoria.getEncaminhar().getId().equals(categoria.getRole().getId()))
					throw new AppException("Tentativa de encaminhar chamado para o mesmo setor.");
				categoria.setEncaminhar(equipeDao.select(categoria.getEncaminhar().getId()));
				if (((cliq.entity.Equipe) categoria.getEncaminhar()).getCategorias().isEmpty())
					throw new AppException("Tentativa de encaminhar chamado para setor sem categorias.");
			}

			if (categoria.getAnexo().getArquivo() != null)
			{
				anexoDao.delete(categoria);
				anexoDao.insert(categoria.getAnexo());
			}

			catetoriaDao.update(categoria);
			acessoDao.delete(categoria);
			acessoDao.insert(categoria, categoria.getRoles());
			link.commit();
		}
	}

	public void delete(Categoria categoria) throws AppException
	{
		try (CategoriaDao dao = new CategoriaDao())
		{
			dao.delete(categoria);
		}
	}

	public void relate(Categoria child, Categoria parent) throws AppException
	{
		try (CategoriaDao dao = new CategoriaDao())
		{
			dao.getLink().beginTran();
			child = dao.select(child.getId());
			if (parent.getId() != null)
			{
				parent = dao.select(parent.getId());
				if (!child.getRole().getId().equals(parent.getRole().getId()))
					throw new AppException("Apenas categorias de mesmo setor podem ser relacionadas.");
				if (child.getId().equals(parent.getId()))
					throw new AppException("Tentativa de criar referência circular.");
				if (child.isParentOf(parent))
					throw new AppException("Tentativa de criar referência circular.");
				dao.relate(child, parent);
			} else
				dao.relate(child, parent);
			dao.getLink().commit();
		}
	}

	public MimeDataFile getIcon(Categoria categoria) throws NotFoundException
	{
		try (CategoriaDao dao = new CategoriaDao())
		{
			return dao.getIcon(categoria);
		}
	}
}
