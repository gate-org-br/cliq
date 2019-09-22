<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<%@ attribute name="chamado" required="true" type="cliq.entity.Chamado"%>

<g:choose>
	<g:when condition="${not empty chamado.categoria.id}">
		<img src='Gate?MODULE=cliq.modulos&SCREEN=Categoria&ACTION=Icon&form.id=${chamado.categoria.id}' 
		     style='width: 24px; height: 24px;'/>
	</g:when>
	<g:otherwise>
		?
	</g:otherwise>
</g:choose>