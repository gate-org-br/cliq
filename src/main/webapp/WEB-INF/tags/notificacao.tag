<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<%@ attribute name="chamado" required="true" type="cliq.entity.Chamado"%>

<g:choose>
	<g:when condition="${chamado.notificado}">
		<i style='font-size: 18px; color: #432F21'>&#x2224;</i>
	</g:when>
	<g:otherwise>
		<i style='font-size: 18px; color: #660000' data-switch='&#x2225'>&#x2224;</i>
	</g:otherwise>
</g:choose>