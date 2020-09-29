package cliq.fetcher;

import cliq.entity.Chamado;
import cliq.entity.Evento;
import cliq.type.Atendimento;
import cliq.type.Checklist;
import cliq.type.Complexidade;
import cliq.type.Nivel;
import cliq.type.Nota;
import cliq.type.Pendencia;
import cliq.type.Prioridade;
import cliq.type.Situacao;
import gate.sql.Cursor;
import gate.sql.fetcher.Fetcher;
import gate.type.Form;
import gate.type.ID;
import gate.type.Phone;
import gate.type.collections.StringList;
import gate.type.collections.StringSet;
import java.time.LocalDateTime;
import java.util.Optional;

public class ChamadoFetcher implements Fetcher<Optional<Chamado>>
{

	@Override
	public Optional<Chamado> fetch(Cursor cursor)
	{
		if (!cursor.next())
			return Optional.empty();

		Chamado chamado = new Chamado();
		chamado.setId(cursor.getValue(ID.class, "id"));
		chamado.setPendencia(cursor.getValue(Pendencia.class, "pendencia"));
		chamado.setAlteracao(cursor.getValue(LocalDateTime.class, "alteracao"));
		chamado.setNivel(cursor.getValue(Nivel.class, "nivel"));
		chamado.setFeedback(cursor.getValue(LocalDateTime.class, "feedback"));
		chamado.getChamado().setId(cursor.getValue(ID.class, "chamado.id"));
		chamado.setSituacao(cursor.getValue(Situacao.class, "situacao"));
		chamado.setSigiloso(cursor.getValue(Boolean.class, "sigiloso"));
		chamado.setData(cursor.getValue(LocalDateTime.class, "data"));
		chamado.getAnexo().setId(cursor.getValue(ID.class, "anexo.id"));
		chamado.getCategoria().setId(cursor.getValue(ID.class, "categoria.id"));
		chamado.getCategoria().setNome(cursor.getValue(String.class, "categoria.nome"));
		chamado.getCategoria().setAvaliacao(cursor.getValue(Boolean.class, "categoria.avaliacao"));
		chamado.getCategoria().setTemporaria(cursor.getValue(Boolean.class, "categoria.temporaria"));
		chamado.getCategoria().getAnexo().setId(cursor.getValue(ID.class, "categoria.anexo.id"));
		chamado.getCategoria().setConclusoes(cursor.getValue(StringList.LineBreak.class, "categoria.conclusoes"));
		chamado.setTitulo(cursor.getValue(String.class, "titulo"));
		chamado.getSolicitante().setId(cursor.getValue(ID.class, "solicitante.id"));
		chamado.getSolicitante().setName(cursor.getValue(String.class, "solicitante.name"));
		chamado.getSolicitante().setUserID(cursor.getValue(String.class, "solicitante.userID"));
		chamado.getSolicitante().setEmail(cursor.getValue(String.class, "solicitante.email"));
		chamado.getOrigem().setId(cursor.getValue(ID.class, "origem.id"));
		chamado.getOrigem().setName(cursor.getValue(String.class, "origem.name"));
		chamado.setDescricao(cursor.getValue(String.class, "descricao"));
		chamado.getLocalizacao().setId(cursor.getValue(ID.class, "localizacao.id"));
		chamado.getLocalizacao().setName(cursor.getValue(String.class, "localizacao.name"));
		chamado.getLocalizacao().setEmail(cursor.getValue(String.class, "localizacao.email"));
		chamado.getAtendente().setId(cursor.getValue(ID.class, "atendente.id"));
		chamado.getAtendente().setName(cursor.getValue(String.class, "atendente.name"));
		chamado.getAtendente().setEmail(cursor.getValue(String.class, "atendente.email"));
		chamado.getContato().setNome(cursor.getValue(String.class, "contato.nome"));
		chamado.getContato().setTelefone(cursor.getValue(Phone.class, "contato.telefone"));
		chamado.getContato().setCelular(cursor.getValue(Phone.class, "contato.celular"));
		chamado.getContato().setEmail(cursor.getValue(String.class, "contato.email"));
		chamado.getContato().setId(cursor.getValue(ID.class, "contato.id"));
		chamado.setPrioridade(cursor.getValue(Prioridade.class, "prioridade"));
		chamado.setComplexidade(cursor.getValue(Complexidade.class, "complexidade"));
		chamado.setPrazoResposta(cursor.getValue(LocalDateTime.class, "prazoResposta"));
		chamado.setPrazoSolucao(cursor.getValue(LocalDateTime.class, "prazoSolucao"));
		chamado.setDocumentacao(cursor.getValue(Boolean.class, "documentacao"));
		chamado.setProjeto(cursor.getValue(Boolean.class, "projeto"));
		chamado.setNotificados(cursor.getValue(StringSet.class, "notificados"));
		chamado.setFormulario(cursor.getValue(Form.class, "formulario"));
		chamado.setRetomada(cursor.getValue(LocalDateTime.class, "retomada"));
		chamado.getPessoaAprovadora().setId(cursor.getValue(ID.class, "pessoaAprovadora.id"));
		chamado.getPessoaAprovadora().setName(cursor.getValue(String.class, "pessoaAprovadora.name"));
		chamado.getPessoaAprovadora().setEmail(cursor.getValue(String.class, "pessoaAprovadora.email"));
		chamado.getEquipeAprovadora().setId(cursor.getValue(ID.class, "equipeAprovadora.id"));
		chamado.getEquipeAprovadora().setName(cursor.getValue(String.class, "equipeAprovadora.name"));
		chamado.getEquipeAprovadora().setEmail(cursor.getValue(String.class, "equipeAprovadora.email"));
		chamado.getPessoaHomologadora().setId(cursor.getValue(ID.class, "pessoaHomologadora.id"));
		chamado.getPessoaHomologadora().setName(cursor.getValue(String.class, "pessoaHomologadora.name"));
		chamado.getPessoaHomologadora().setEmail(cursor.getValue(String.class, "pessoaHomologadora.email"));
		chamado.getEquipeHomologadora().setId(cursor.getValue(ID.class, "equipeHomologadora.id"));
		chamado.getEquipeHomologadora().setName(cursor.getValue(String.class, "equipeHomologadora.name"));
		chamado.getEquipeHomologadora().setEmail(cursor.getValue(String.class, "equipeHomologadora.email"));
		chamado.setChecklist(cursor.getValue(Checklist.class, "checklist"));
		chamado.setAtendimento(cursor.getValue(Atendimento.class, "atendimento"));
		chamado.setNota(cursor.getValue(Nota.class, "nota"));
		chamado.setResposta(cursor.getValue(LocalDateTime.class, "resposta"));
		chamado.setSolucao(cursor.getValue(LocalDateTime.class, "solucao"));
		chamado.getCategoria().setFeedback(cursor.getValue(Boolean.class, "categoria.feedback"));
		chamado.getEvento().setId(cursor.getValue(ID.class, "ultimoEvento.id"));

		do
		{
			Evento evento = new Evento();
			evento.setChamado(chamado);
			evento.setId(cursor.getValue(ID.class, "evento.id"));
			evento.setAlteracao(cursor.getValue(LocalDateTime.class, "evento.alteracao"));
			evento.setTipo(cursor.getValue(Evento.Tipo.class, "evento.tipo"));
			evento.setDescricao(cursor.getValue(String.class, "evento.descricao"));
			evento.setData(cursor.getValue(LocalDateTime.class, "evento.data"));
			evento.getUser().setId(cursor.getValue(ID.class, "evento.user.id"));
			evento.getUser().setName(cursor.getValue(String.class, "evento.user.name"));
			evento.getUser().setEmail(cursor.getValue(String.class, "evento.user.email"));
			evento.setObservacoes(cursor.getValue(String.class, "evento.observacoes"));
			evento.setStatus(cursor.getValue(String.class, "evento.status"));
			evento.getAnexo().setId(cursor.getValue(ID.class, "evento.anexo.id"));

			if (evento.getId() != null)
				chamado.getEventos().add(evento);
		} while (cursor.next());

		return Optional.of(chamado);
	}
}
