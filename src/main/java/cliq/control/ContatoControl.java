package cliq.control;

import cliq.dao.AcessoDao;
import cliq.dao.ContatoDao;
import cliq.entity.Contato;
import gate.base.Control;
import gate.constraint.Constraints;
import gate.error.AppException;
import gate.sql.Link;
import gate.type.ID;
import java.util.List;

public class ContatoControl extends Control
{

	public ContatoControl()
	{
		super();
	}

	public List<Contato> search(String string) throws AppException
	{
		if (string == null || string.length() < 3)
			throw new AppException("Entre com no mínimo 3 caracteres no filtro");
		try (ContatoDao dao = new ContatoDao())
		{
			return dao.search(string);
		}
	}

	public Contato select(Contato.Tipo tipo, ID id) throws AppException
	{
		try (Link link = new Link();
			AcessoDao acessoDao = new AcessoDao(link);
			ContatoDao contatoDao = new ContatoDao(link))
		{
			Contato contato = contatoDao.select(tipo, id);
			if (Contato.Tipo.CIDADAO.equals(contato.getTipo())
				|| Contato.Tipo.EMPRESA.equals(contato.getTipo()))
				contato.setRoles(acessoDao.search(contato));
			return contato;
		}
	}

	public void insert(Contato model) throws AppException
	{
		Constraints.validate(model, "tipo", "visibilidade", "nome", "telefone", "celular", "email", "comentarios");
		if (Contato.Tipo.USUARIO.equals(model.getTipo()))
			throw new AppException("Usuários do Gate devem ser cadastrados pelo Gate.");

		try (Link link = new Link();
			AcessoDao acessoDao = new AcessoDao(link);
			ContatoDao contatoDao = new ContatoDao(link))
		{
			link.beginTran();
			contatoDao.insert(model);
			acessoDao.insert(model, model.getRoles());
			link.commit();
		}
	}

	public void update(Contato model) throws AppException
	{
		Constraints.validate(model, "tipo", "visibilidade", "nome", "telefone", "celular", "email", "comentarios");
		if (Contato.Tipo.USUARIO.equals(model.getTipo()))
			throw new AppException("Usuários do Gate devem ser alterados pelo Gate.");
		try (Link link = new Link();
			AcessoDao acessoDao = new AcessoDao(link);
			ContatoDao contatoDao = new ContatoDao(link))
		{
			link.beginTran();
			contatoDao.update(model);
			acessoDao.delete(model);
			acessoDao.insert(model, model.getRoles());
			link.commit();
		}
	}

	public void delete(Contato model) throws AppException
	{
		try (ContatoDao dao = new ContatoDao())
		{
			dao.delete(model);
		}
	}
}
