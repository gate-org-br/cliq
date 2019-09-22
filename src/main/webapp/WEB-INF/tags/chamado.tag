<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<div class='TEXT'>
	<fieldset>
		<legend style='padding: 4px;'>
			<g:if condition="${not empty screen.form.categoria.id}" otherwise='Chamado'>
				<img src='Gate?MODULE=cliq.modulos&SCREEN=Categoria&ACTION=Icon&form.id=${screen.form.categoria.id}'
				     style='width: 20px; height: 20px; vertical-align: middle;'/>
				${screen.form.categoria.nome}
			</g:if>
		</legend>

		<label data-size='2'>
			N&ordm;:
			<span>
				<g:icon type="#"/>
				${screen.form.id}
			</span>
		</label>
		<label data-size='2'>
			Data:
			<span>
				<g:icon type="date"/>
				<g:print value="${screen.form.data}"/>
			</span>
		</label>
		<label data-size='2'>
			Situação:
			<span>
				<g:icon type="${screen.form.situacao}"/>
				<g:print value="${screen.form.situacao}"/>
			</span>
		</label>
		<label data-size='2'>
			Pendência:
			<span>
				<g:icon type="${screen.form.pendencia}"/>
				<g:print value="${screen.form.pendencia}"/>
			</span>
		</label>
		<label data-size='8'>
			Categoria:
			<span>
				<g:icon type="cliq.entity.Categoria"/>
				<g:print value="${screen.form.categoria.nome}" empty="N/A"/>
			</span>
		</label>
		<g:choose>
			<g:when condition="${not empty screen.form.solicitante.id}">
				<label data-size='4'>
					Origem:
					<span>
						<g:icon type="gate.entity.Role"/>
						<g:print value="${screen.form.origem.name}"/>
					</span>
				</label>
				<label data-size='4'>
					Solicitante:
					<span>
						<g:label property="form.solicitante.name"/>
						<g:shortcut target="_dialog" module="cliq.modulos" screen="Contato" action="Select"
							    arguments="form.tipo=2&form.id=${screen.form.solicitante.id}"
							    title="Usuário" otherwise="${screen.form.solicitante.name}"/>
					</span>
				</label>
			</g:when>
			<g:otherwise>
				<label data-size='8'>
					Solicitante:
					<span>
						Criado pelo CliQ
					</span>
				</label>
			</g:otherwise>
		</g:choose>
		<label data-size='4'>
			Setor Responsável:
			<span>
				<g:icon type="gate.entity.Role"/>
				${screen.form.localizacao.name}
			</span>
		</label>
		<label data-size='4'>
			Atendente Responsável:
			<span>
				<g:icon type="gate.entity.User"/>
				<g:choose>
					<g:when condition="${not empty screen.form.atendente.id}">
						<g:link target="_dialog" module="cliq.modulos" screen="Contato" action="Select"
							arguments="form.tipo=2&form.id=${screen.form.atendente.id}"
							title="Usuário" otherwise="${screen.form.atendente.name}">
							<g:print value="${screen.form.atendente.name}"/>
						</g:link>
					</g:when>
					<g:otherwise>
						Não Atribuído
					</g:otherwise>
				</g:choose>
			</span>
		</label>
		<g:if condition="${not empty screen.form.anexo.id}">
			<label>
				Anexo:
				<span>
					<label>
						Baixar
					</label>
					<g:shortcut  module="cliq.modulos" screen="Anexo" arguments="form.id=${screen.form.anexo.id}"/>
				</span>
			</label>
		</g:if>
	</fieldset>

	<h4>
		${screen.form.titulo}
	</h4>

	<g:print value="${screen.form.formulario}"/>

	<g:if condition="${not empty screen.form.descricao}">
		<p>
			<g:print value="${screen.form.descricao}"/>
		</p>
	</g:if>
</div>

<cliq:eventos eventos="${screen.form.eventos.stream()
			 .filter(e -> e.tipo.publico).toList()}"/>