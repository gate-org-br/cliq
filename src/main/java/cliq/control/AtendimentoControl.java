package cliq.control;

import cliq.dao.AnexoDao;
import cliq.dao.AtendimentoDao;
import cliq.dao.CategoriaDao;
import cliq.dao.ChamadoDao;
import cliq.dao.EventoDao;
import cliq.dao.PessoaDao;
import cliq.entity.Anexo;
import cliq.entity.Categoria;
import cliq.entity.Chamado;
import cliq.entity.Evento;
import cliq.type.Atendimento;
import cliq.type.Complexidade;
import cliq.type.Nivel;
import cliq.type.Pendencia;
import cliq.type.Prioridade;
import cliq.type.Situacao;
import gate.entity.Role;
import gate.entity.User;
import gate.error.AppException;
import gate.sql.Link;
import gate.type.ID;
import gate.type.collections.StringSet;
import gateconsole.dao.RoleDao;
import gateconsole.dao.UserDao;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AtendimentoControl extends ChamadoControl
{

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Pesquisa
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List<Chamado> search(Role role, Chamado chamado)
	{
		try (AtendimentoDao dao = new AtendimentoDao())
		{
			return dao.search(role, chamado);
		}
	}

	public List<Map<String, Object>> search(Chamado.Agrupamento agrupamento, Role role, Chamado chamado)
	{
		try (AtendimentoDao dao = new AtendimentoDao())
		{
			return dao.search(agrupamento, role, chamado);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Atributos
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Chamado definirPrioridade(ID id, Prioridade prioridade)
		throws AppException
	{
		try (AtendimentoDao dao = new AtendimentoDao())
		{
			dao.beginTran();

			Chamado chamado = dao.select(id);

			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Somente chamados pendentes podem ter sua prioridade modificada");

			dao.update(chamado.setPrioridade(prioridade));

			dao.commit();
			return chamado;
		}
	}

	public Chamado definirComplexidade(ID id, Complexidade complexidade) throws AppException
	{
		try (AtendimentoDao dao = new AtendimentoDao())
		{
			dao.beginTran();

			Chamado chamado = dao.select(id);

			dao.update(chamado.setComplexidade(complexidade));

			dao.commit();
			return chamado;
		}
	}

	public void setDocumentacao(ID id, Boolean valor) throws AppException
	{
		try (AtendimentoDao dao = new AtendimentoDao())
		{
			dao.beginTran();

			Chamado chamado = dao.select(id);

			if (chamado.getSituacao() != Situacao.CONCLUIDO)
				throw new AppException("Somente chamados concluídos podem servir como documentação");

			dao.update(chamado.setDocumentacao(valor));

			dao.commit();
		}
	}

	public void setProjeto(ID id, Boolean valor) throws AppException
	{
		try (AtendimentoDao dao = new AtendimentoDao())
		{
			dao.beginTran();

			dao.update(dao.select(id).setProjeto(valor));

			dao.commit();
		}
	}

	public void setAtendimento(ID id, Atendimento atendimento) throws AppException
	{
		try (AtendimentoDao dao = new AtendimentoDao())
		{
			dao.beginTran();

			dao.update(dao.select(id).setAtendimento(atendimento));

			dao.commit();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Atendimento
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void atender(Chamado chamado, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());
			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Atendimentos só podem ser iniciados em chamados pendentes");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());

			if (chamado.getResposta() != null)
				throw new AppException("O chamado já se encontra em atendimento");

			if (!getUser().getRole().equals(chamado.getLocalizacao()))
				throw new AppException("Apenas um atendente pode iniciar o atendimento do chamado");

			atendimentoDao
				.update(chamado
					.setResposta(LocalDateTime.now())
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setTipo(Evento.Tipo.ATENDIMENTO)
							.setAnexo(anexoDao.insert(anexo))
							.setDescricao("Atendimento iniciado por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Categorização
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Chamado categorizar(Chamado chamado, Categoria categoria) throws AppException
	{
		try (Link link = new Link();
			AtendimentoDao atendimentoDao = new AtendimentoDao(link);
			CategoriaDao categoriaDao = new CategoriaDao(link))
		{
			link.beginTran();
			chamado = atendimentoDao.select(chamado.getId());

			if (chamado.getSituacao() != Situacao.PENDENTE
				&& (categoria == null || categoria.getId() == null))
				throw new AppException("Tentativa de atribuir categoria indefinida a chamado não pendente");

			if (categoria != null && categoria.getId() != null)
			{
				categoria = categoriaDao.select(categoria.getId());
				chamado.setChecklist(categoria.getChecklist());
				chamado.setNivel(categoria.getNivel());
				chamado.setPrioridade(categoria.getPrioridade());
				chamado.setComplexidade(categoria.getComplexidade());
			}

			atendimentoDao.update(chamado
				.setCategoria(categoria));

			link.commit();
			return chamado;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Nivelamento
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setNivel(ID id, Nivel nivel) throws AppException
	{
		try (Link link = new Link();
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			Chamado chamado = atendimentoDao.select(id);
			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Somente chamados pendentes podem ser promovidos ou rebaixados");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());

			if (Objects.equals(chamado.getNivel(), nivel))
				throw new AppException("Tentativa de ajustar o nível do chamado para o mesmo nível no qual ele se encontra");

			Evento evento = new Evento();
			if (nivel == null && chamado.getNivel() != null)
				evento.setTipo(Evento.Tipo.REBAIXAMENTO)
					.setDescricao("Chamado rebaixado do " + chamado.getNivel() + " por " + getUser());
			else if (nivel != null && chamado.getNivel() == null)
				evento.setTipo(Evento.Tipo.PROMOCAO)
					.setDescricao("Chamado ajustado para o " + nivel + " por " + getUser());
			else if (nivel != null && chamado.getNivel() != null && nivel.ordinal() > chamado.getNivel().ordinal())
				evento.setTipo(Evento.Tipo.PROMOCAO)
					.setDescricao("Chamado promovido do " + chamado.getNivel() + " para o " + nivel + " por " + getUser());
			else if (nivel != null && chamado.getNivel() != null && nivel.ordinal() < chamado.getNivel().ordinal())
				evento.setTipo(Evento.Tipo.REBAIXAMENTO)
					.setDescricao("Chamado rebaixado do " + chamado.getNivel() + " para o " + nivel + " por " + getUser());

			atendimentoDao
				.update(chamado
					.setNivel(nivel)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(evento
							.setUser(getUser())
							.setChamado(chamado)
							.setData(LocalDateTime.now()))));

			link.commit();
			event.fire(chamado);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Encaminhamento
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void encaminhar(Chamado chamado, Role role, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link);
			CategoriaDao categoriaDao = new CategoriaDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());
			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Apenas chamados pendentes podem ser encaminhados");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());

			if (chamado.getLocalizacao().equals(role))
				throw new AppException("Tentativa de encaminhar chamado para o mesmo setor");
			if (!categoriaDao.exists(role))
				throw new AppException("Tentativa de encaminhar chamado para setor sem categorias");

			if (chamado.getCategoria().getId() != null)
			{
				chamado.setCategoria(categoriaDao.select(role, chamado.getCategoria().getNome()));
				if (chamado.getCategoria().getId() != null)
					chamado.setPrioridade(chamado.getCategoria().getPrioridade())
						.setComplexidade(chamado.getCategoria().getComplexidade())
						.setNivel(chamado.getCategoria().getNivel())
						.setChecklist(chamado.getCategoria().getChecklist());
			}

			atendimentoDao
				.update(chamado
					.setNivel(null)
					.setAtendente(null)
					.setLocalizacao(role)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setData(LocalDateTime.now())
							.setObservacoes(observacoes)
							.setAnexo(anexoDao.insert(anexo))
							.setTipo(Evento.Tipo.ENCAMINHAMENTO)
							.setDescricao("Chamado encaminhado para " + getUser().getRole().getRoot().select(role.getId())))));

			link.commit();
			event.fire(chamado);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// SLA
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Chamado definirPrazoResposta(Chamado chamado, LocalDateTime dateTime, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());

			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Somente chamados pendentes podem ter seus prazos se resposta modificados");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());
			if (dateTime != null && chamado.getPrazoSolucao() != null
				&& dateTime.compareTo(chamado.getPrazoSolucao()) >= 0)
				throw new AppException("O prazo de resposta deve ser anterior ao prazo de solução");
			if (dateTime != null && dateTime.compareTo(LocalDateTime.now()) < 0)
				throw new AppException("O prazo de resposta deve ser uma data futura");
			if (observacoes == null || observacoes.isEmpty())
				throw new AppException("Entre com o motivo da alteração do prazo de resposta");

			atendimentoDao
				.update(chamado
					.setPrazoResposta(dateTime)
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setAnexo(anexoDao.insert(anexo))
							.setTipo(Evento.Tipo.PRAZO_RESPOSTA)
							.setDescricao("Prazo de resposta ajustado para " + (dateTime != null ? dateTime : "nenhum") + " por " + getUser()))));

			link.commit();
			return chamado;
		}
	}

	public Chamado definirPrazoSolucao(Chamado chamado, LocalDateTime dateTime, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());
			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Somente chamados pendentes podem ter seus prazos se solução modificados");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());
			if (dateTime != null && chamado.getPrazoResposta() != null && dateTime.compareTo(chamado.getPrazoResposta()) <= 0)
				throw new AppException("O prazo de solução deve ser posterior ao prazo de resposta");
			if (dateTime != null && dateTime.compareTo(LocalDateTime.now()) < 0)
				throw new AppException("O prazo de solução deve ser uma data futura");
			if (observacoes == null || observacoes.isEmpty())
				throw new AppException("Entre com o motivo da alteração do prazo de solução");

			atendimentoDao
				.update(chamado
					.setPrazoSolucao(dateTime)
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setAnexo(anexoDao.insert(anexo))
							.setTipo(Evento.Tipo.PRAZO_SOLUCAO)
							.setDescricao("Prazo de solução ajustado para " + (dateTime != null ? dateTime : "nenhum") + " por " + getUser()))));

			link.commit();
			return chamado;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Comentário
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void comentarioInterno(Chamado chamado, String comentario, Anexo anexo) throws AppException
	{
		if (comentario == null && (anexo == null || anexo.getArquivo() == null))
			throw new AppException("Entre com um comentário ou um arquivo");

		if (comentario == null)
			comentario = anexo.getArquivo().getName();

		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			ChamadoDao atendimentoDao = new ChamadoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());

			atendimentoDao
				.update(chamado
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(comentario)
							.setData(LocalDateTime.now())
							.setAnexo(anexoDao.insert(anexo))
							.setTipo(Evento.Tipo.COMENTARIO_INTERNO))));

			link.commit();
			event.fire(chamado);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Triagem
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void importar(ID id) throws AppException
	{
		try (Link link = new Link();
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			Chamado chamado = atendimentoDao.select(id);

			if (chamado.getPendencia() != Pendencia.TRIAGEM)
				throw new AppException("Apenas chamados em triagem podem ser importados");

			atendimentoDao.update(chamado
				.setCategoria(null)
				.setPendencia(Pendencia.NENHUMA)
				.setLocalizacao(getUser().getRole())
				.setNotificados(new StringSet(getUser().getId().toString()))
				.setEvento(eventoDao
					.insert(new Evento()
						.setUser(getUser())
						.setChamado(chamado)
						.setData(LocalDateTime.now())
						.setTipo(Evento.Tipo.IMPORTACAO)
						.setDescricao("Chamado importado para " + getUser().getRole().getName()))));

			link.commit();
			event.fire(chamado);
		}
	}

	public void distribuir(Chamado chamado, Role role) throws AppException
	{
		try (Link link = new Link();
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());

			if (chamado.getPendencia() != Pendencia.TRIAGEM)
				throw new AppException("Apenas chamados em triagem podem ser distribuídos");

			atendimentoDao
				.update(chamado.setCategoria(null)
					.setPendencia(Pendencia.NENHUMA)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setLocalizacao(getUser().getRole().getRoot().select(role.getId()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setData(LocalDateTime.now())
							.setTipo(Evento.Tipo.DISTRIBUICAO)
							.setDescricao("Chamado distribuído para " + role.getName()))));
			link.commit();
			event.fire(chamado);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Suspensão
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void suspender(Chamado chamado, LocalDateTime retomada, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());
			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Somente chamados pendentes podem ser suspensos");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());

			if (observacoes == null)
				throw new AppException("Indique o motivo da suspensão do chamado");

			atendimentoDao
				.update(chamado.setRetomada(retomada)
					.setPendencia(Pendencia.SUSPENSO)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setData(LocalDateTime.now())
							.setObservacoes(observacoes)
							.setTipo(Evento.Tipo.SUSPENSAO)
							.setAnexo(anexoDao.insert(anexo))
							.setDescricao(retomada != null ? "Chamado suspenso até " + retomada + " por " + getUser()
								: "Chamado suspenso por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	public void retomar(Chamado chamado, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());
			if (chamado.getPendencia() != Pendencia.SUSPENSO)
				throw new AppException("Tentativa de suspender chamado não suspenso");

			atendimentoDao
				.update(chamado
					.setRetomada(null)
					.setPendencia(Pendencia.NENHUMA)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setTipo(Evento.Tipo.RETOMADA)
							.setAnexo(anexoDao.insert(anexo))
							.setDescricao("Chamado retomado por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Atribuição
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void atribuir(Chamado chamado, gate.entity.User atendente, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			PessoaDao pessoaDao = new PessoaDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());

			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Somente chamados pendentes podem ser atribuidos");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());

			if (atendente.getRole().getId() != null && !atendente.getRole().getId().equals(chamado.getLocalizacao().getId()))
				throw new AppException("Tentativa de atribuir chamado para usuário de outro setor");
			if (!getUser().getRole().equals(chamado.getLocalizacao()) && !getUser().getRole().isParentOf(chamado.getLocalizacao()))
				throw new AppException("Apenas o técnicos lotados onde um chamado está localizado podem atribuí-lo");

			if (atendente.getId() != null)
			{
				atendente = pessoaDao.select(atendente.getId());
				if (!atendente.getRole().equals(chamado.getLocalizacao())
					&& !atendente.getRole().isParentOf(chamado.getLocalizacao()))
					throw new AppException("Chamados podem ser atribuídos apenas para técnicos lotados no setor onde ele está localizado");
			}

			atendimentoDao
				.update(chamado
					.setAtendente(atendente)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setTipo(Evento.Tipo.ATRIBUICAO)
							.setAnexo(anexoDao.insert(anexo))
							.setDescricao("Chamado atribuído para " + (chamado.getAtendente().getId() != null ? chamado.getAtendente() : " a equipe")))));

			link.commit();
			event.fire(chamado);
		}
	}

	public void capturar(ID id) throws AppException
	{
		try (Link link = new Link();
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			Chamado chamado = atendimentoDao.select(id);
			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Somente chamados pendentes podem ser capturados");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());

			if (!getUser().getRole().contains(chamado.getLocalizacao()))
				throw new AppException("Apenas o técnicos lotados onde um chamado está localizado podem capturá-lo");

			atendimentoDao
				.update(chamado.setAtendente(getUser())
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setData(LocalDateTime.now())
							.setTipo(Evento.Tipo.CAPTURA)
							.setDescricao("Chamado capturado por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	public void liberar(ID id) throws AppException
	{
		try (Link link = new Link();
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			Chamado chamado = atendimentoDao.select(id);

			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Somente chamados pendentes podem ser liberados");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());
			if (chamado.getAtendente().getId() == null)
				throw new AppException("Somente chamados capturados podem ser liberados");

			atendimentoDao
				.update(chamado
					.setAtendente(null)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setData(LocalDateTime.now())
							.setTipo(Evento.Tipo.LIBERACAO)
							.setDescricao("Chamado liberado por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Avaliação
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void solicitarAvaliacao(ID id) throws AppException
	{
		try (Link link = new Link();
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			Chamado chamado = atendimentoDao.select(id);
			if (chamado.getNota() != null)
				throw new AppException("Chamado já foi avaliado pelo solicitante");
			if (!Situacao.CONCLUIDO.equals(chamado.getSituacao()))
				throw new AppException("Apenas chamados concluídos podem ser enviados para avaliação");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());

			atendimentoDao
				.update(chamado
					.setPendencia(Pendencia.AVALIACAO)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setData(LocalDateTime.now())
							.setTipo(Evento.Tipo.ENVIO_AVALIACAO)
							.setDescricao("Chamado enviado para avaliacao do solicitante por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Homologação
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void solicitarHomologacao(Chamado chamado, Role homologador, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			RoleDao roleDao = new RoleDao(link);
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());

			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Apenas chamados pendentes podem ser enviados para homologação");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());

			atendimentoDao
				.update(chamado
					.setPendencia(Pendencia.HOMOLOGACAO)
					.setEquipeHomologadora(roleDao.select(homologador.getId()))
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setData(LocalDateTime.now())
							.setObservacoes(observacoes)
							.setTipo(Evento.Tipo.HOMOLOGACAO)
							.setAnexo(anexoDao.insert(anexo))
							.setDescricao("Chamado enviado a " + chamado.getEquipeHomologadora() + " para homologação"))));

			link.commit();
			event.fire(chamado);
		}
	}

	public void solicitarHomologacao(Chamado chamado, User homologador, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			UserDao userDao = new UserDao(link);
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());

			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Apenas chamados pendentes podem ser enviados para homologação");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());

			atendimentoDao
				.update(chamado
					.setPendencia(Pendencia.HOMOLOGACAO)
					.setPessoaHomologadora(userDao.select(homologador.getId()))
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setTipo(Evento.Tipo.HOMOLOGACAO)
							.setAnexo(anexoDao.insert(anexo))
							.setDescricao("Chamado enviado a " + chamado.getPessoaHomologadora() + " para homologação"))));

			link.commit();
			event.fire(chamado);
		}
	}

	public void cancelarHomologacao(Chamado chamado, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());
			if (chamado.getPendencia() != Pendencia.HOMOLOGACAO)
				throw new AppException("O chamado não se encontra em homologação");

			if (!getUser().getRole().contains(chamado.getLocalizacao()))
				throw new AppException("Você não tem permissão para cancelar a homologação deste chamado");

			if (observacoes == null)
				throw new AppException("Entre com o motivo pelo qual a homologação está sendo cancelada");

			atendimentoDao
				.update(chamado
					.setPessoaHomologadora(null)
					.setEquipeHomologadora(null)
					.setPendencia(Pendencia.NENHUMA)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setAnexo(anexoDao.insert(anexo))
							.setTipo(Evento.Tipo.HOMOLOGACAO_CANCELAMENTO)
							.setDescricao("Homologação do chamado cancelada por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Aprovação
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void solicitarAprovacao(Chamado chamado, User aprovador, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			UserDao userDao = new UserDao(link);
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());

			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Apenas chamados pendentes podem ser enviados para aprovação");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());

			atendimentoDao.update(chamado
				.setPendencia(Pendencia.APROVACAO)
				.setPessoaAprovadora(userDao.select(aprovador.getId()))
				.setNotificados(new StringSet(getUser().getId().toString()))
				.setEvento(eventoDao
					.insert(new Evento()
						.setUser(getUser())
						.setChamado(chamado)
						.setObservacoes(observacoes)
						.setData(LocalDateTime.now())
						.setTipo(Evento.Tipo.APROVACAO)
						.setAnexo(anexoDao.insert(anexo))
						.setDescricao("Chamado enviado a " + chamado.getPessoaAprovadora() + " para aprovação"))));

			link.commit();
			event.fire(chamado);
		}
	}

	public Chamado solicitarAprovacao(Chamado chamado, Role aprovador, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			RoleDao roleDao = new RoleDao(link);
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());

			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Apenas chamados pendentes podem ser enviados para aprovação");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra " + chamado.getPendencia());

			atendimentoDao
				.update(chamado
					.setPendencia(Pendencia.APROVACAO)
					.setEquipeAprovadora(roleDao.select(aprovador.getId()))
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setTipo(Evento.Tipo.APROVACAO)
							.setAnexo(anexoDao.insert(anexo))
							.setDescricao("Chamado enviado a " + chamado.getEquipeAprovadora() + " para aprovação"))));

			link.commit();
			return chamado;
		}
	}

	public void cancelarAprovacao(Chamado chamado, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());

			if (chamado.getPendencia() != Pendencia.APROVACAO)
				throw new AppException("O chamado não se encontra em aprovação");
			if (!getUser().getRole().equals(chamado.getLocalizacao())
				&& !getUser().getRole().isParentOf(chamado.getLocalizacao()))
				throw new AppException("Você não tem permissão para cancelar a aprovação deste chamado");
			if (observacoes == null)
				throw new AppException("Entre com o motivo pelo qual a aprovação está sendo cancelada");

			atendimentoDao
				.update(chamado
					.setPessoaAprovadora(null)
					.setEquipeAprovadora(null)
					.setPendencia(Pendencia.NENHUMA)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setAnexo(anexoDao.insert(anexo))
							.setTipo(Evento.Tipo.APROVACAO_CANCELAMENTO)
							.setDescricao("Aprovação do chamado cancelada por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Feedback
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void solicitarFeedback(Chamado chamado, User user, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			UserDao userDao = new UserDao(link);
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());
			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Apenas chamados pendentes podem ser enviados para feedabck");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra em " + chamado.getPendencia());

			if (chamado.getSolicitante().getId() == null)
				throw new AppException("O chamado não possui um solicitante");
			if (chamado.getSolicitante().equals(getUser()))
				throw new AppException("Tentativa de obter feedback de si próprio");

			if (chamado.getCategoria().getId() == null)
				throw new AppException("Tentativa enviar chamado para feedback sem definir sua categoria");
			if (Boolean.TRUE.equals(chamado.getCategoria().getTemporaria()))
				throw new AppException("Categorias temporárias podem ser utilizadas para criar chamados, mas não para concluí-los");

			atendimentoDao
				.update(chamado
					.setPendencia(Pendencia.FEEDBACK)
					.setFeedback(LocalDateTime.now())
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setAtendente(user != null && user.getId() != null ? userDao.select(user.getId()) : getUser())
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setTipo(Evento.Tipo.FEEDBACK)
							.setData(chamado.getFeedback())
							.setAnexo(anexoDao.insert(anexo))
							.setDescricao("Chamado enviado para feedback por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	public void cancelarFeedback(Chamado chamado, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());
			if (chamado.getPendencia() != Pendencia.FEEDBACK)
				throw new AppException("O chamado não se encontra em feedback");

			if (!getUser().getRole().contains(chamado.getLocalizacao()))
				throw new AppException("Você não tem permissão para cancelar o feedback deste chamado");

			atendimentoDao
				.update(chamado
					.setFeedback(null)
					.setPendencia(Pendencia.NENHUMA)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setAnexo(anexoDao.insert(anexo))
							.setTipo(Evento.Tipo.FEEDBACK_CANCELAMENTO)
							.setDescricao("Feedback do chamado cancelado por " + getUser()))));
			link.commit();
			event.fire(chamado);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Complemento
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void solicitarComplementacao(Chamado chamado, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());
			if (chamado.getSituacao() != Situacao.PENDENTE)
				throw new AppException("Apenas chamados pendentes podem ser enviados para complementação");
			if (chamado.getPendencia() != Pendencia.NENHUMA)
				throw new AppException("O chamado se encontra em " + chamado.getPendencia());

			if (chamado.getSolicitante().getId() == null)
				throw new AppException("O chamado não possui um solicitante");
			if (chamado.getSolicitante().equals(getUser()))
				throw new AppException("Tentativa de obter complementação de si próprio");

			atendimentoDao
				.update(chamado
					.setPendencia(Pendencia.COMPLEMENTACAO)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setAnexo(anexoDao.insert(anexo))
							.setTipo(Evento.Tipo.COMPLEMENTACAO)
							.setDescricao("Chamado enviado para complementação por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}

	public void cancelarComplementacao(Chamado chamado, String observacoes, Anexo anexo) throws AppException
	{
		try (Link link = new Link();
			AnexoDao anexoDao = new AnexoDao(link);
			EventoDao eventoDao = new EventoDao(link);
			AtendimentoDao atendimentoDao = new AtendimentoDao(link))
		{
			link.beginTran();

			chamado = atendimentoDao.select(chamado.getId());
			if (chamado.getPendencia() != Pendencia.COMPLEMENTACAO)
				throw new AppException("O chamado não se encontra em complementação");

			if (!getUser().getRole().contains(chamado.getLocalizacao()))
				throw new AppException("Você não tem permissão para cancelar a complementação deste chamado");

			atendimentoDao
				.update(chamado
					.setPendencia(Pendencia.NENHUMA)
					.setNotificados(new StringSet(getUser().getId().toString()))
					.setEvento(eventoDao
						.insert(new Evento()
							.setUser(getUser())
							.setChamado(chamado)
							.setObservacoes(observacoes)
							.setData(LocalDateTime.now())
							.setAnexo(anexoDao.insert(anexo))
							.setTipo(Evento.Tipo.COMPLEMENTACAO_CANCELAMENTO)
							.setDescricao("complementação do chamado cancelada por " + getUser()))));

			link.commit();
			event.fire(chamado);
		}
	}
}
