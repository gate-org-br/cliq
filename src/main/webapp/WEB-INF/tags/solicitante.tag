<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<%@ attribute name="chamado" required="true" type="cliq.entity.Chamado"%>

<g:choose>
	<g:when condition="${not empty chamado.solicitante.id}">
		<g:link target="_dialog" module="cliq.modulos" screen="Contato" action="Select"
			arguments="form.tipo=USUARIO&form.id=${chamado.solicitante.id}"
			title="Usuário" otherwise="${chamado.solicitante.name}">
			<g:print value="${chamado.solicitante.name}"/>
		</g:link>
	</g:when>
	<g:when condition="${not empty chamado.contato.id}">
		<g:link target="_dialog" module="cliq.modulos" screen="Contato" action="Select"
			arguments="form.tipo=${screen.form.contato.tipo}&form.id=${chamado.contato.id}"
			title="Contato" otherwise="${chamado.contato.nome}">
			<g:print value="${chamado.contato.nome}"/>
		</g:link>
	</g:when>
	<g:otherwise>
		<span style='color: #999999; font-size: 10px'>
			Criado pelo CliQ
		</span>
	</g:otherwise>
</g:choose>