package cliq.modulos.atendimento;

import cliq.control.AtendimentoControl;
import cliq.control.CategoriaControl;
import cliq.control.ContatoControl;
import cliq.entity.Categoria;
import cliq.entity.Chamado;
import cliq.entity.Contato;
import cliq.entity.Equipe;
import cliq.modulos.CLIQScreen;
import cliq.report.AtendimentoExport;
import gate.annotation.Color;
import gate.annotation.Current;
import gate.annotation.Description;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.error.AppException;
import gate.report.Doc;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

@Icon("2155")
@Name("Atendimentos")
public class AtendimentoScreen extends CLIQScreen
{

	protected Chamado form;
	protected Doc.Type type;
	protected String responsavel;

	@Inject
	@Current
	protected Equipe equipe;

	@Inject
	protected AtendimentoControl control;

	@Inject
	private ContatoControl contatoControl;

	@Inject
	private CategoriaControl categoriaControl;

	@Override
	public String call()
	{
		return "/WEB-INF/views/cliq/modulos/atendimento/View.jsp";
	}

	@Icon("2256")
	@Name("Detalhes")
	public String callSelect()
	{
		try
		{
			form = control.select(form.getId());
			return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewSelect.jsp";
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
			return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewResult.jsp";
		}
	}

	@Icon("search")
	@Name("Localizar chamado")
	public String callLocalizar()
	{
		if (getMessages().isEmpty())
			try
			{
				form = control.select(form.getId());
				return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewSelect.jsp";
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewResult.jsp";
	}

	@Name("Novo")
	@Icon("insert")
	@Description("Novo chamado")
	public String callInsert()
	{
		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewInsert.jsp";
	}

	public String callCommit()
	{
		if (isGET())
			try
			{
				getForm().setCategoria(categoriaControl.select(getForm().getCategoria().getId()));
				getForm().setFormulario(getForm().getCategoria().getFormulario());
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
				return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewResult.jsp";
			}
		else if (getMessages().isEmpty())
			try
			{
				if (Contato.Tipo.USUARIO.equals(getForm().getContato().getTipo()))
				{
					getForm().getSolicitante().setId(getForm().getContato().getId());
					getForm().setContato(null);
				}

				control.insert(getForm());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}

		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewCommit.jsp";
	}

	@Icon("update")
	@Name("Alterar chamado")
	public String callUpdate()
	{
		if (isGET())
			try
			{
				form = control.select(getForm().getId());
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
				return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewResult.jsp";
			}
		else if (getMessages().isEmpty())
		{
			try
			{
				control.update(getForm().getId(), getForm().getTitulo(),
					getForm().getFormulario(), getForm().getDescricao(), getForm().getAnexo());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		}
		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewUpdate.jsp";
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Suspensão
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Name("Suspender")
	@Description("Suspender chamado")
	@Icon("cliq.entity.Evento$Tipo:SUSPENSAO")
	public String callSuspender()
	{
		if (isPOST() && getMessages().isEmpty())
			try
			{
				control.suspender(form,
					form.getRetomada(),
					form.getEvento().getObservacoes(),
					form.getEvento().getAnexo());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewSuspender.jsp";
	}

	@Name("Retomar")
	@Description("Retomar chamado suspenso")
	@Icon("cliq.entity.Evento$Tipo:RETOMADA")
	public String callRetomar()
	{
		if (isPOST() && getMessages().isEmpty())
			try
			{
				control.retomar(form,
					form.getEvento().getObservacoes(),
					form.getEvento().getAnexo());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());

			}
		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewRetomar.jsp";
	}

	@Name("Encaminhar")
	@Icon("cliq.entity.Evento$Tipo:ENCAMINHAMENTO")
	public String callEncaminhar()
	{
		if (isPOST())
			try
			{
				control.encaminhar(form,
					form.getLocalizacao(),
					form.getEvento().getObservacoes(),
					form.getEvento().getAnexo());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewEncaminhar.jsp";
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Conclusão
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Name("Concluir")
	@Icon("cliq.entity.Evento$Tipo:CONCLUSAO")
	public String callConcluir()
	{
		if (isGET())
			try
			{
				form = control.select(form.getId());
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
				return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewResult.jsp";
			}
		else if (getMessages().isEmpty())
			try
			{
				control.concluir(form,
					form.getAtendente(),
					form.getEvento().getStatus(),
					form.getEvento().getObservacoes(),
					form.getEvento().getAnexo());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}

		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewConcluir.jsp";
	}

	@Name("Cancelar")
	@Icon("cliq.entity.Evento$Tipo:CANCELAMENTO")
	public String callCancelar()
	{
		if (isPOST() && getMessages().isEmpty())
			try
			{
				if (form.getAtendente().getId() == null)
					form.setAtendente(getUser());
				control.cancelar(form, form.getEvento().getObservacoes(), form.getEvento().getAnexo());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}

		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewCancelar.jsp";
	}

	@Name("Reabrir")
	@Icon("cliq.entity.Evento$Tipo:REABERTURA")
	public String callReabrir()
	{
		if (isPOST() && getMessages().isEmpty())
			try
			{
				control.reabrir(form,
					form.getEvento().getObservacoes(),
					form.getEvento().getAnexo());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewReabrir.jsp";
	}

	@Icon("update")
	@Name("Categorizar")
	public String callCategorizar()
	{
		if (isPOST() && getMessages().isEmpty())
			try
			{
				form = control.categorizar(getForm(), getForm().getCategoria());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
				return callSelect();
			}
		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewCategorizar.jsp";
	}

	public String callPrioridade()
	{
		try
		{
			form = control.definirPrioridade(getForm().getId(), getForm().getPrioridade());
			return callSelect();
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
			return callSelect();
		}
	}

	public String callComplexidade()
	{
		try
		{
			form = control.definirComplexidade(getForm().getId(), getForm().getComplexidade());
			return callSelect();
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
			return callSelect();
		}
	}

	public String callResposta()
	{
		if (isPOST() && getMessages().isEmpty())
			try
			{
				form = control
					.definirPrazoResposta(getForm(), getForm().getPrazoResposta(),
						getForm().getEvento().getObservacoes(),
						getForm().getEvento().getAnexo());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}

		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewResposta.jsp";
	}

	@Name("Atender")
	@Icon("cliq.entity.Evento$Tipo:ATENDIMENTO")
	public String callAtender()
	{
		try
		{
			control.atender(getForm(),
				getForm().getEvento().getObservacoes(),
				getForm().getEvento().getAnexo());
			return callSelect();
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
			return callSelect();
		}
	}

	public String callSolucao()
	{
		if (isPOST() && getMessages().isEmpty())
			try
			{
				form = control.definirPrazoSolucao(getForm(),
					getForm().getPrazoSolucao(),
					getForm().getEvento().getObservacoes(),
					getForm().getEvento().getAnexo());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}

		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewSolucao.jsp";
	}

	public String callDocumentacao()
	{
		try
		{
			control.setDocumentacao(getForm().getId(), getForm().getDocumentacao());
			return callSelect();
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
			return callSelect();
		}
	}

	public String callProjeto()
	{
		try
		{
			control.setProjeto(getForm().getId(), getForm().getProjeto());
			return callSelect();
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
			return callSelect();
		}
	}

	public String callAtendimento()
	{
		try
		{
			control.setAtendimento(getForm().getId(), getForm().getAtendimento());
			return callSelect();
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
			return callSelect();
		}
	}

	@Icon("report")
	@Name("Imprimir")
	public Object callExport()
	{
		try
		{
			form = control.select(getForm().getId());
			return Doc.create(type, new AtendimentoExport(getOrg(), form));
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
			return callSelect();
		}
	}

	public Chamado getForm()
	{
		if (form == null)
			form = new Chamado();
		return form;
	}

	public Collection<Contato> getContatos() throws AppException
	{
		return contatoControl.search(getForm().getContato().getNome());
	}

	public List<Categoria> getCategorias() throws AppException
	{
		return categoriaControl.search(equipe);
	}

	public String callNivel()
	{
		try
		{
			control.setNivel(getForm().getId(), getForm().getNivel());
			return callSelect();
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
			return callSelect();
		}
	}

	public void setForm(Chamado form)
	{
		this.form = form;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Comentário
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Name("Comentário")
	@Icon("cliq.entity.Evento$Tipo:COMENTARIO_SIMPLES")
	public String callComentarioSimples()
	{
		if (isPOST())
			try
			{
				control.comentarioSimples(form,
					form.getEvento().getObservacoes(),
					form.getEvento().getAnexo());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}

		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewComentarioSimples.jsp";
	}

	@Name("Comentário")
	@Icon("cliq.entity.Evento$Tipo:COMENTARIO_INTERNO")
	public String callComentarioInterno()
	{
		if (isPOST())
			try
			{
				control.comentarioInterno(form,
					form.getEvento().getObservacoes(),
					form.getEvento().getAnexo());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}

		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewComentarioInterno.jsp";
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Atribuição
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Name("Atribuir")
	@Icon("cliq.entity.Evento$Tipo:ATRIBUICAO")
	public String callAtribuir()
	{
		if (isPOST())
			try
			{
				control.atribuir(form,
					form.getAtendente(),
					form.getEvento().getObservacoes(),
					form.getEvento().getAnexo());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}

		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewAtribuir.jsp";
	}

	@Name("Capturar")
	@Icon("cliq.entity.Evento$Tipo:CAPTURA")
	public String callCapturar()
	{
		try
		{
			control.capturar(form.getId());
			return callSelect();
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
			return callSelect();
		}
	}

	@Name("Liberar")
	@Icon("cliq.entity.Evento$Tipo:LIBERACAO")
	public String callLiberar()
	{
		try
		{
			control.liberar(form.getId());
			return callSelect();
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
			return callSelect();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Avaliação
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Name("Avaliação")
	@Icon("cliq.entity.Evento$Tipo:AVALIACAO")
	public String callAvaliacao()
	{
		try
		{
			control.solicitarAvaliacao(form.getId());
			return callSelect();
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
			return callSelect();
		}
	}

	@Name("Avaliar")
	public String callAvaliar()
	{
		try
		{
			control.avaliar(form, form.getNota(), form.getEvento().getObservacoes(), form.getEvento().getAnexo());
			getMessages().add("Avaliação do chamado feita com sucesso");
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return callSelect();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Aprovação
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Name("Aprovação")
	@Description("Soliciar aprovação")
	@Icon("cliq.entity.Evento$Tipo:APROVACAO")
	public String callSolicitarAprovacao()
	{
		if (isPOST() && getMessages().isEmpty())
		{
			try
			{
				if (form.getPessoaAprovadora().getId() != null)
					control.solicitarAprovacao(form,
						form.getPessoaAprovadora(),
						form.getEvento().getObservacoes(),
						form.getEvento().getAnexo());
				else if (form.getEquipeAprovadora().getId() != null)
					control.solicitarAprovacao(form,
						form.getEquipeAprovadora(),
						form.getEvento().getObservacoes(),
						form.getEvento().getAnexo());
				else
					throw new AppException("Selecione um aprovador");

				getMessages().add("Chamado enviado para aprovação.");
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		}

		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewSolicitarAprovacao.jsp";
	}

	@Name("Cancelar aprovação")
	@Description("Cancelar aprovação")
	@Icon("cliq.entity.Evento$Tipo:APROVACAO_CANCELAMENTO")
	public String callCancelarAprovacao()
	{
		if (isPOST())
			try
			{
				control.cancelarAprovacao(form,
					form.getEvento().getObservacoes(),
					form.getEvento().getAnexo());
				getMessages().add("Aprovação do chamado cancelada com sucesso.");
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}

		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewCancelarAprovacao.jsp";
	}

	@Name("Sim")
	@Color("#006600")
	@Description("Aceitar aprovação")
	@Icon("cliq.entity.Evento$Tipo:APROVACAO_ACEITE")
	public String callAceitarAprovacao()
	{
		try
		{
			control.aceitarAprovacao(form,
				form.getEvento().getObservacoes(),
				form.getEvento().getAnexo());
			getMessages().add("Aprovação do chamado aceita com sucesso");
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
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
			control.recusarAprovacao(form,
				form.getEvento().getObservacoes(),
				form.getEvento().getAnexo());
			getMessages().add("Aprovação do chamado resusada com sucesso");

		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return callSelect();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Complementação
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Name("Complemento")
	@Description("Solicitar complementação")
	@Icon("cliq.entity.Evento$Tipo:COMPLEMENTACAO")
	public String callSolicitarComplementacao()
	{
		if (isPOST() && getMessages().isEmpty())
		{
			try
			{
				control.solicitarComplementacao(form,
					form.getEvento().getObservacoes(),
					form.getEvento().getAnexo());

				getMessages().add("Chamado enviado para complementação.");
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		}

		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewSolicitarComplementacao.jsp";
	}

	@Color("#660000")
	@Name("Cancelar complementação")
	@Description("Cancelar complementação")
	@Icon("cliq.entity.Evento$Tipo:COMPLEMENTACAO_CANCELAMENTO")
	public String callCancelarComplementacao()
	{
		if (isPOST())
			try
			{
				control.cancelarComplementacao(form,
					form.getEvento().getObservacoes(),
					form.getEvento().getAnexo());
				getMessages().add("Complementação do chamado cancelada com sucesso.");
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}

		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewCancelarComplementacao.jsp";
	}

	@Name("Concluir")
	@Color("#006600")
	@Description("Complementar chamado")
	@Icon("cliq.entity.Evento$Tipo:COMPLEMENTO")
	public String callComplementar()
	{
		try
		{
			control.complementar(form,
				form.getEvento().getObservacoes(),
				form.getEvento().getAnexo());
			getMessages().add("Chamado complementado com sucesso");
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return callSelect();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Homologacao
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Name("Homologação")
	@Description("Soliciar homologação")
	@Icon("cliq.entity.Evento$Tipo:HOMOLOGACAO")
	public String callSolicitarHomologacao()
	{
		if (isPOST() && getMessages().isEmpty())
		{
			try
			{
				if (form.getPessoaHomologadora().getId() != null)
					control.solicitarHomologacao(form,
						form.getPessoaHomologadora(),
						form.getEvento().getObservacoes(),
						form.getEvento().getAnexo());
				else if (form.getEquipeHomologadora().getId() != null)
					control.solicitarHomologacao(form,
						form.getEquipeHomologadora(),
						form.getEvento().getObservacoes(),
						form.getEvento().getAnexo());
				else
					throw new AppException("Selecione um homologador");

				getMessages().add("Chamado enviado para homologação.");
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		}

		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewSolicitarHomologacao.jsp";
	}

	@Color("#660000")
	@Name("Cancelar homologação")
	@Description("Cancelar homologação")
	@Icon("cliq.entity.Evento$Tipo:HOMOLOGACAO_CANCELAMENTO")
	public String callCancelarHomologacao()
	{
		if (isPOST())
			try
			{
				control.cancelarHomologacao(form,
					form.getEvento().getObservacoes(),
					form.getEvento().getAnexo());
				getMessages().add("Homologação do chamado cancelada com sucesso.");
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}

		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewCancelarHomologacao.jsp";
	}

	@Name("Sim")
	@Color("#006600")
	@Description("Aceitar homologação")
	@Icon("cliq.entity.Evento$Tipo:HOMOLOGACAO_ACEITE")
	public String callAceitarHomologacao()
	{
		try
		{
			control.aceitarHomologacao(form,
				form.getEvento().getObservacoes(),
				form.getEvento().getAnexo());
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
			control.recusarHomologacao(form,
				form.getEvento().getObservacoes(),
				form.getEvento().getAnexo());
			getMessages().add("Homologação do chamado resusada com sucesso");
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return callSelect();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Feedback
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Name("Feedback")
	@Description("Soliciar feedback")
	@Icon("cliq.entity.Evento$Tipo:FEEDBACK")
	public String callSolicitarFeedback()
	{
		if (isPOST())
			try
			{
				control.solicitarFeedback(form,
					form.getAtendente(),
					form.getEvento().getObservacoes(),
					form.getEvento().getAnexo());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}

		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewSolicitarFeedback.jsp";
	}

	@Color("#660000")
	@Name("Cancelar feedback")
	@Description("Cancelar feedback")
	@Icon("cliq.entity.Evento$Tipo:FEEDBACK_CANCELAMENTO")
	public String callCancelarFeedback()
	{
		if (isPOST())
			try
			{
				control.cancelarFeedback(form,
					form.getEvento().getObservacoes(),
					form.getEvento().getAnexo());
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}

		return "/WEB-INF/views/cliq/modulos/atendimento/Chamado/ViewCancelarFeedback.jsp";
	}

	@Name("Sim")
	@Color("#006600")
	@Description("Aceitar solução do chamado")
	@Icon("cliq.entity.Evento$Tipo:FEEDBACK_ACEITE")
	public String callAceitarFeedback()
	{
		try
		{
			control.aceitarFeedback(form,
				form.getEvento().getObservacoes(),
				form.getEvento().getAnexo());
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
			control.recusarFeedback(form,
				form.getEvento().getObservacoes(),
				form.getEvento().getAnexo());
			getMessages().add("Solução do chamado recusada com sucesso");
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return callSelect();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Triagem
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Name("Importar")
	@Description("Importar chamado")
	@Icon("cliq.entity.Evento$Tipo:IMPORTACAO")
	public String callImportar()
	{
		try
		{
			control.importar(getForm().getId());
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}

		return callSelect();
	}

	@Name("Distribuir")
	@Description("Distribuir chamado")
	@Icon("cliq.entity.Evento$Tipo:DISTRIBUICAO")
	public String callDistribuir()
	{
		if (isPOST() && getMessages().isEmpty())
		{
			try
			{
				control.distribuir(getForm(), getUser().getRole().getRoot().select(getForm().getLocalizacao().getId()));
				return callSelect();
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		}

		getForm().setLocalizacao(getUser().getRole()
			.getRoot().select(getForm().getLocalizacao().getId()));

		return "/WEB-INF/views/cliq/modulos/atendimento/ViewDistribuir.jsp";
	}

	public void setType(Doc.Type type)
	{
		this.type = type;
	}

	public String getResponsavel()
	{
		return responsavel;
	}

	public void setResponsavel(String responsavel)
	{
		this.responsavel = responsavel;
	}
}
