package cliq.modulos.solicitante;

import cliq.control.CategoriaControl;
import cliq.control.EventoControl;
import cliq.control.SolicitanteControl;
import cliq.entity.Categoria;
import cliq.entity.Chamado;
import cliq.entity.Equipe;
import cliq.modulos.CLIQScreen;
import cliq.report.SolicitanteExport;
import gate.annotation.Color;
import gate.annotation.Current;
import gate.annotation.Description;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.error.AppException;
import gate.report.Doc;
import java.util.List;
import javax.inject.Inject;

@Icon("2223")
@Name("Solicitações")
public class SolicitanteScreen extends CLIQScreen
{

	protected Object page;
	protected Chamado form;
	protected Doc.Type type;
	protected Categoria parent;

	@Inject
	@Current
	protected Equipe equipe;

	@Inject
	protected SolicitanteControl control;

	@Inject
	protected EventoControl eventoControl;

	@Inject
	protected CategoriaControl categoriaControl;

	@Override
	public String call()
	{
		return "/WEB-INF/views/cliq/modulos/solicitante/Chamado/View.jsp";
	}

	public String callSelect()
	{
		try
		{
			setForm(control.select(getForm().getId()));
			return "/WEB-INF/views/cliq/modulos/solicitante/Chamado/ViewSelect.jsp";
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
			return call();
		}
	}

	@Name("Novo chamado")
	@Icon("cliq.entity.Chamado")
	public String callInsert()
	{
		return "/WEB-INF/views/cliq/modulos/solicitante/Chamado/ViewInsert.jsp";
	}

	public String callCommit()
	{
		if (isGET())
			try
			{
				getForm().setCategoria(categoriaControl.select(getForm().getCategoria().getId()))
					.setFormulario(getForm().getCategoria().getFormulario());
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
				return "/WEB-INF/views/cliq/modulos/solicitante/Chamado/ViewResult.jsp";
			}
		else if (isPOST() && getMessages().isEmpty())
			try
			{

				getForm().setSolicitante(getUser());
				control.insert(getForm());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}

		return "/WEB-INF/views/cliq/modulos/solicitante/Chamado/ViewCommit.jsp";
	}

	@Name("Comentário")
	@Description("Comentar chamado")
	@Icon("cliq.entity.Evento$Tipo:COMENTARIO_SIMPLES")
	public String callComentarioSimples()
	{
		if (isPOST() && getMessages().isEmpty())
		{
			try
			{
				control.comentarioSimples(getForm(), getForm().getEvento().getObservacoes(), form.getEvento().getAnexo());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		}

		return "/WEB-INF/views/cliq/modulos/solicitante/Chamado/ViewAction.jsp";
	}

	@Name("Cancelar")
	@Description("Cancelar chamado")
	@Icon("cliq.entity.Evento$Tipo:CANCELAMENTO")
	public String callCancelar()
	{
		if (isPOST() && getMessages().isEmpty())
		{
			try
			{
				control.cancelar(getForm(), getForm().getEvento().getObservacoes(), form.getEvento().getAnexo());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		}

		return "/WEB-INF/views/cliq/modulos/solicitante/Chamado/ViewAction.jsp";
	}

	@Name("Reabrir")
	@Description("Reabrir chamado")
	@Icon("cliq.entity.Evento$Tipo:REABERTURA")
	public String callReabrir()
	{
		if (isPOST() && getMessages().isEmpty())
		{
			try
			{
				control.reabrir(getForm(), getForm().getEvento().getObservacoes(), form.getEvento().getAnexo());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		}

		return "/WEB-INF/views/cliq/modulos/solicitante/Chamado/ViewAction.jsp";
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Homologacao
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Name("Sim")
	@Color("#006600")
	@Description("Aceitar homologação")
	@Icon("cliq.entity.Evento$Tipo:HOMOLOGACAO_ACEITE")
	public String callAceitarHomologacao()
	{
		try
		{
			control.aceitarHomologacao(form, form.getEvento().getObservacoes(), form.getEvento().getAnexo());
			getMessages().add("Homologação do chamado aceita com sucesso");
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return callSelect();
	}

	@Name("Não")
	@Color("#660000")
	@Description("Recusar homologação")
	@Icon("cliq.entity.Evento$Tipo:HOMOLOGACAO_RECUSA")
	public String callRecusarHomologacao()
	{
		try
		{
			control.recusarHomologacao(form, form.getEvento().getObservacoes(), form.getEvento().getAnexo());
			getMessages().add("Homologação do chamado resusada com sucesso");
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return callSelect();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Aprovação
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Name("Sim")
	@Color("#006600")
	@Description("Aceitar aprovação")
	@Icon("cliq.entity.Evento$Tipo:APROVACAO_ACEITE")
	public String callAceitarAprovacao()
	{
		try
		{
			control.aceitarAprovacao(getForm(), getForm().getEvento().getObservacoes(), form.getEvento().getAnexo());
			getMessages().add("Chamado aprovado com sucesso");
		} catch (AppException e)
		{
			setMessages(e.getMessages());
		}
		return callSelect();
	}

	@Name("Não")
	@Color("#660000")
	@Description("Recusar aprovação")
	@Icon("cliq.entity.Evento$Tipo:APROVACAO_RECUSA")
	public String callRecusarAprovacao()
	{
		try
		{
			control.recusarAprovacao(getForm(), getForm().getEvento().getObservacoes(), form.getEvento().getAnexo());
			getMessages().add("Aprovação do chamado resusada com sucesso");
		} catch (AppException e)
		{
			setMessages(e.getMessages());
		}
		return callSelect();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Feedback
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Name("Sim")
	@Color("#006600")
	@Description("Aceitar solução do chamado")
	@Icon("cliq.entity.Evento$Tipo:FEEDBACK_ACEITE")
	public String callAceitarFeedback()
	{
		try
		{
			control.aceitarFeedback(form, form.getEvento().getObservacoes(), form.getEvento().getAnexo());
			getMessages().add("Solução do chamado aceita com sucesso");
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return callSelect();
	}

	@Name("Não")
	@Color("#660000")
	@Description("Recusar solução do chamado")
	public String callRecusarFeedback()
	{
		try
		{
			control.recusarFeedback(form, form.getEvento().getObservacoes(), form.getEvento().getAnexo());
			getMessages().add("Solução do chamado recusada com sucesso");
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return callSelect();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Avaliação
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Name("Avaliar")
	@Description("Avaliar chamado")
	@Icon("cliq.entity.Evento$Tipo:AVALIACAO")
	public String callAvaliar()
	{
		try
		{
			control.avaliar(getForm(), getForm().getNota(), getForm().getEvento().getObservacoes(), form.getEvento().getAnexo());
			getMessages().add("Avaliação do chamado feita com sucesso");
		} catch (AppException e)
		{
			setMessages(e.getMessages());
		}
		return callSelect();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Complementação
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Name("Concluir")
	@Color("#006600")
	@Description("Complementar chamado")
	@Icon("cliq.entity.Evento$Tipo:COMPLEMENTO")
	public String callComplementar()
	{
		try
		{
			control.complementar(form, form.getEvento().getObservacoes(), form.getEvento().getAnexo());
			getMessages().add("Chamado complementado com sucesso");
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return callSelect();
	}

	public String callSigilo()
	{
		try
		{
			control.definirSigilo(getForm().getId(), getForm().getSigiloso());
		} catch (AppException e)
		{
			setMessages(e.getMessages());
		}
		return callSelect();
	}

	@Icon("update")
	@Name("Alterar")
	@Description("Alterar chamado")
	public String callUpdate()
	{
		try
		{
			if (isGET())
			{
				setForm(control.select(getForm().getId()));
				return "/WEB-INF/views/cliq/modulos/solicitante/Chamado/ViewUpdate.jsp";
			} else
			{
				control.update(getForm().getId(), getForm().getTitulo(), getForm().getFormulario(), getForm().getDescricao(), getForm().getAnexo());
				return callSelect();
			}
		} catch (AppException e)
		{
			setMessages(e.getMessages());
			return "/WEB-INF/views/cliq/modulos/solicitante/Chamado/ViewUpdate.jsp";
		}
	}

	@Icon("report")
	@Name("Imprimir")
	@Description("Imprimir chamado")
	public Object callExport()
	{
		try
		{
			form = control.select(getForm().getId());
			return Doc.create(type, new SolicitanteExport(getOrg(), form));
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
			return callSelect();
		}
	}

	public Object getPage()
	{
		return page;
	}

	public void setType(Doc.Type type)
	{
		this.type = type;
	}

	public Chamado getForm()
	{
		if (form == null)
			form = new Chamado();
		return form;
	}

	public void setForm(Chamado form)
	{
		this.form = form;
	}

	public List<Categoria> getCategorias() throws AppException
	{
		return new CategoriaControl().search(getUser());
	}
}
