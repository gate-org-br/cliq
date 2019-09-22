<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<%@ attribute name="chamado" required="true" type="cliq.entity.Chamado"%>

<g:choose>
	<g:when condition="${not empty chamado.origem.id}">
		<g:print value="${chamado.origem.name}"/>
	</g:when>
	<g:when condition="${not empty chamado.contato.id}">
		<span style='color: #999999; font-size: 10px'>
			Não se Aplica
		</span>
	</g:when>
	<g:otherwise>
		<span style='color: #999999; font-size: 10px'>
			Criado pelo CliQ
		</span>
	</g:otherwise>
</g:choose>