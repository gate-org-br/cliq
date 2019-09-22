package cliq.dao;

import cliq.entity.Anexo;
import cliq.entity.Categoria;
import cliq.entity.Evento;
import gate.base.Dao;
import gate.error.ConstraintViolationException;
import gate.error.NotFoundException;
import gate.sql.Link;
import gate.sql.condition.Condition;
import gate.sql.delete.Delete;
import gate.sql.insert.Insert;
import gate.sql.select.Select;
import gate.type.ID;
import gate.type.mime.MimeDataFile;

public class AnexoDao extends Dao
{

	public AnexoDao()
	{
	}

	public AnexoDao(Link link)
	{
		super(link);
	}

	public MimeDataFile select(ID id) throws NotFoundException
	{
		return getLink()
			.from(Select.expression("arquivo")
				.from("Anexo")
				.where(Condition.of("id").eq(ID.class, id)))
			.fetchObject(MimeDataFile.class)
			.orElseThrow(NotFoundException::new);
	}

	public Anexo insert(Anexo anexo) throws ConstraintViolationException
	{
		if (anexo != null && anexo.getArquivo() != null)
			Insert.into("Anexo")
				.set("arquivo", anexo.getArquivo())
				.build()
				.connect(getLink())
				.fetchGeneratedKeys(ID.class)
				.forEach(anexo::setId);
		return anexo;
	}

	public void delete(Anexo anexo) throws ConstraintViolationException
	{
		getLink()
			.prepare(Delete.from("Anexo")
				.where(Condition.of("id").eq(ID.class, anexo.getId())))
			.execute();
	}

	public void delete(Categoria categoria) throws ConstraintViolationException
	{
		getLink()
			.prepare(Delete.from("Anexo")
				.where(Condition.of("Anexo.id")
					.isEq(Select.expression("Categoria.Anexo$id").from("Categoria")
						.where(Condition.of("Categoria.id").eq(categoria.getId())))))
			.execute();
	}

	public void delete(Evento evento) throws ConstraintViolationException
	{
		getLink()
			.prepare(Delete.from("Anexo")
				.where(Condition.of("Anexo.id")
					.isEq(Select.expression("Evento.Anexo$id").from("Evento")
						.where(Condition.of("Evento.id").eq(evento.getId())))))
			.execute();
	}
}
