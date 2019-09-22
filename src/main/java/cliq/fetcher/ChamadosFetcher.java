package cliq.fetcher;

import cliq.entity.Categoria;
import cliq.entity.Chamado;
import cliq.entity.Contato;
import cliq.type.Nivel;
import cliq.type.Pendencia;
import cliq.type.Prioridade;
import cliq.type.Situacao;
import gate.entity.Role;
import gate.entity.User;
import gate.sql.Cursor;
import gate.sql.fetcher.Fetcher;
import gate.type.Form;
import gate.type.ID;
import gate.type.collections.StringSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChamadosFetcher implements Fetcher<List<Chamado>>
{

	private final String user;

	public ChamadosFetcher(User user)
	{
		this.user = user.getId().toString();
	}

	@Override
	public List<Chamado> fetch(Cursor cursor)
	{
		List<Chamado> chamados = new ArrayList<>();
		while (cursor.next())
			chamados.add(new Chamado().setId(cursor.getValue(ID.class, "id"))
				.setPendencia(cursor.getValue(Pendencia.class, "pendencia"))
				.setData(cursor.getValue(LocalDateTime.class, "data"))
				.setSituacao(cursor.getValue(Situacao.class, "situacao"))
				.setPrioridade(cursor.getValue(Prioridade.class, "prioridade"))
				.setTitulo(cursor.getValue(String.class, "titulo"))
				.setDescricao(cursor.getValue(String.class, "descricao"))
				.setNivel(cursor.getValue(Nivel.class, "nivel"))
				.setResposta(cursor.getValue(LocalDateTime.class, "resposta"))
				.setSolucao(cursor.getValue(LocalDateTime.class, "solucao"))
				.setProjeto(cursor.getValue(Boolean.class, "projeto"))
				.setPrazoResposta(cursor.getValue(LocalDateTime.class, "prazoResposta"))
				.setPrazoSolucao(cursor.getValue(LocalDateTime.class, "prazoSolucao"))
				.setFormulario(cursor.getValue(Form.class, "formulario"))
				.setDocumentacao(cursor.getValue(Boolean.class, "documentacao"))
				.setNotificados(cursor.getValue(StringSet.class, "notificados"))
				.setAtrasado(cursor.getValue(Boolean.class, "atrasado"))
				.setTipo(new Categoria()
					.setId(cursor.getValue(ID.class, "tipo.id"))
					.setNome(cursor.getValue(String.class, "tipo.nome")))
				.setCategoria(new Categoria()
					.setId(cursor.getValue(ID.class, "categoria.id"))
					.setNome(cursor.getValue(String.class, "categoria.nome")))
				.setOrigem(new Role()
					.setId(cursor.getValue(ID.class, "origem.id"))
					.setName(cursor.getValue(String.class, "origem.name")))
				.setSolicitante(new User()
					.setId(cursor.getValue(ID.class, "solicitante.id"))
					.setName(cursor.getValue(String.class, "solicitante.name"))
					.setUserID(cursor.getValue(String.class, "solicitante.userID"))
					.setEmail(cursor.getValue(String.class, "solicitante.email")))
				.setAtendente(new User()
					.setId(cursor.getValue(ID.class, "atendente.id"))
					.setName(cursor.getValue(String.class, "atendente.name"))
					.setUserID(cursor.getValue(String.class, "atendente.userID"))
					.setEmail(cursor.getValue(String.class, "atendente.email")))
				.setContato(new Contato()
					.setId(cursor.getValue(ID.class, "contato.id"))
					.setNome(cursor.getValue(String.class, "contato.nome")))
				.setPessoaAprovadora(new User()
					.setId(cursor.getValue(ID.class, "pessoaAprovadora.id")))
				.setEquipeAprovadora(new Role()
					.setId(cursor.getValue(ID.class, "equipeAprovadora.id"))));

		chamados.forEach(e -> e.setNotificado(e.getNotificados() == null
			|| e.getNotificados().contains(user)));
		Collections.sort(chamados);
		return chamados;
	}
}
