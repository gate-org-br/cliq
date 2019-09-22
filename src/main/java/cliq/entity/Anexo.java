package cliq.entity;

import gate.annotation.Description;
import gate.annotation.Entity;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.constraint.Required;
import gate.type.ID;
import gate.type.mime.MimeDataFile;
import java.io.Serializable;

@Entity
@Icon("attach")
public class Anexo implements Serializable
{

	@Required
	@Description("O campo ANEXO é requerido.")
	private ID id;

	@Required
	@Name("Arquivo")
	@Description("Entre com o arquivo do anexo")
	private MimeDataFile arquivo;

	public ID getId()
	{
		return id;
	}

	public void setId(ID id)
	{
		this.id = id;
	}

	public MimeDataFile getArquivo()
	{
		return arquivo;
	}

	public void setArquivo(MimeDataFile arquivo)
	{
		this.arquivo = arquivo;
	}

}
