<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<%@ attribute name="chamado" required="true" type="cliq.entity.Chamado"%>


<g:choose>
	<g:when condition="${chamado.pendencia ne 'NENHUMA'}">
		<div style='color: ${g:color(chamado.pendencia)}'>
			<g:print value="${chamado.pendencia}"/>
		</div>
	</g:when>
	<g:when condition="${chamado.situacao ne 'PENDENTE'}">
		<div style='color: ${g:color(chamado.situacao)}'>
			<g:print value="${chamado.situacao}"/>
		</div>
	</g:when>
	<g:otherwise>
		<span style='color: ${g:color(chamado.prioridade)}'>
			<g:print value="${chamado.prioridade}"/>
		</span>
	</g:otherwise>
</g:choose>
