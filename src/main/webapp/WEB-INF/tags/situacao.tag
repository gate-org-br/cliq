<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<%@ attribute name="chamado" required="true" type="cliq.entity.Chamado"%>

<g:choose>
	<g:when condition="${chamado.pendencia ne 'NENHUMA'}">
		<label style="color: ${g:color(chamado.pendencia)}">
			<g:print value="${chamado.pendencia}"/>
		</label>
	</g:when>
	<g:otherwise>
		<label style="color: ${g:color(chamado.situacao)}">
			<g:print value="${chamado.situacao}"/>
		</label>
	</g:otherwise>
</g:choose>