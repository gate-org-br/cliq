package cliq.dao;

import cliq.entity.Anexo;
import cliq.entity.Chamado;
import cliq.entity.Evento;
import cliq.fetcher.ChamadoFetcher;
import cliq.type.Pendencia;
import cliq.type.Situacao;
import gate.base.Dao;
import gate.error.AppException;
import gate.error.ConstraintViolationException;
import gate.error.NotFoundException;
import gate.sql.Link;
import gate.sql.condition.Condition;
import gate.sql.insert.Insert;
import gate.sql.update.Update;
import gate.type.Form;
import gate.type.ID;
import gate.type.collections.StringSet;
import java.time.LocalDateTime;

public class ChamadoDao extends Dao
{

	public ChamadoDao()
	{

	}

	public ChamadoDao(Link link)
	{
		super(link);
	}

	public Chamado select(ID id) throws NotFoundException
	{
		return getLink().from(getClass().getResource("ChamadoDao/select(ID).sql"))
			.parameters(getUser().getId(),
				getUser().getId(),
				getUser().getRole().getId(),
				getUser().getRole().getId(),
				id)
			.fetch(new ChamadoFetcher()).orElseThrow(NotFoundException::new);
	}

	public void insert(Chamado chamado) throws ConstraintViolationException
	{
		Insert.into("Chamado")
			.set("Tipo$id", chamado.getTipo().getId())
			.set("Evento$id", chamado.getEvento().getId())
			.set("Categoria$id", chamado.getCategoria().getId())
			.set("pendencia", chamado.getPendencia())
			.set("nivel", chamado.getNivel())
			.set("Solicitante$id", chamado.getSolicitante().getId())
			.set("Origem$id", chamado.getOrigem().getId())
			.set("Localizacao$id", chamado.getLocalizacao().getId())
			.set("titulo", chamado.getTitulo())
			.set("descricao", chamado.getDescricao())
			.set("formulario", chamado.getFormulario())
			.set("data", chamado.getData())
			.set("prazoResposta", chamado.getPrazoResposta())
			.set("prazoSolucao", chamado.getPrazoSolucao())
			.set("situacao", chamado.getSituacao())
			.set("Atendente$id", chamado.getAtendente().getId())
			.set("Contato$id", chamado.getContato().getId())
			.set("prioridade", chamado.getPrioridade())
			.set("complexidade", chamado.getComplexidade())
			.set("Anexo$id", chamado.getAnexo().getId())
			.set("sigiloso", chamado.getSigiloso())
			.set("documentacao", chamado.getDocumentacao())
			.set("projeto", chamado.getProjeto())
			.set("notificados", chamado.getNotificados())
			.set("PessoaAprovadora$id", chamado.getPessoaAprovadora().getId())
			.set("EquipeAprovadora$id", chamado.getEquipeAprovadora().getId())
			.set("PessoaHomologadora$id", chamado.getPessoaHomologadora().getId())
			.set("EquipeHomologadora$id", chamado.getEquipeHomologadora().getId())
			.set("checklist", chamado.getChecklist())
			.set("atendimento", chamado.getAtendimento())
			.set("nota", chamado.getNota())
			.build()
			.connect(getLink())
			.fetchGeneratedKey(ID.class)
			.ifPresent(chamado::setId);
	}

	public void update(Chamado chamado) throws AppException
	{
		if (getLink().prepare(Update.table("Chamado")
			.set("pendencia", chamado.getPendencia())
			.set("Chamado$id", chamado.getChamado().getId())
			.set("nivel", chamado.getNivel())
			.set("situacao", chamado.getSituacao())
			.set("sigiloso", chamado.getSigiloso())
			.set("Localizacao$id", chamado.getLocalizacao().getId())
			.set("Atendente$id", chamado.getAtendente().getId())
			.set("Categoria$id", chamado.getCategoria().getId())
			.set("Contato$id", chamado.getContato().getId())
			.set("prioridade", chamado.getPrioridade())
			.set("complexidade", chamado.getComplexidade())
			.set("prazoResposta", chamado.getPrazoResposta())
			.set("prazoSolucao", chamado.getPrazoSolucao())
			.set("retomada", chamado.getRetomada())
			.set("documentacao", chamado.getDocumentacao())
			.set("projeto", chamado.getProjeto())
			.set("notificados", chamado.getNotificados())
			.set("PessoaAprovadora$id", chamado.getPessoaAprovadora().getId())
			.set("EquipeAprovadora$id", chamado.getEquipeAprovadora().getId())
			.set("PessoaHomologadora$id", chamado.getPessoaHomologadora().getId())
			.set("EquipeHomologadora$id", chamado.getEquipeHomologadora().getId())
			.set("checklist", chamado.getChecklist())
			.set("atendimento", chamado.getAtendimento())
			.set("nota", chamado.getNota())
			.set("resposta", chamado.getResposta())
			.set("solucao", chamado.getSolucao())
			.set("Evento$id", chamado.getEvento().getId())
			.where(Condition
				.of("id").eq(chamado.getId())))
			.execute() == 0)
			throw new AppException("Tentativa de alterar chamado inexistente");
	}

	public void update(ID id, String titulo, Form formulario, String descricao, Anexo anexo) throws AppException
	{
		if (getLink().prepare(Update.table("Chamado")
			.set("titulo", titulo)
			.set("Anexo$id", anexo != null ? anexo.getId() : null)
			.set("descricao", descricao)
			.set("formulario", formulario)
			.set("alteracao", LocalDateTime.now())
			.where(Condition.of("id").eq(id))).execute() == 0)
			throw new AppException("Tentativa de corrigir chamado inexistente");
	}

	public void update(Chamado chamado, Situacao situacao, Evento evento)
		throws ConstraintViolationException
	{
		Update.table("Chamado")
			.set("retomada", null)
			.set("feedback", null)
			.set("situacao", situacao)
			.set("Evento$id", evento.getId())
			.set("pendencia", Pendencia.NENHUMA)
			.set("notificados", new StringSet())
			.where(Condition.of("id").eq(ID.class, chamado.getId()))
			.build()
			.connect(getLink())
			.execute();
	}

}
