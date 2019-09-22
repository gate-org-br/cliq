<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<%@ attribute name="chamado" required="true" type="cliq.entity.Chamado"%>

<g:choose>
	<g:when condition="${not empty chamado.atendente.id}">
		<g:link target="_dialog" module="cliq.modulos" screen="Contato" action="Select"
			arguments="form.tipo=2&form.id=${chamado.atendente.id}"
			title="Contato" otherwise=" ${chamado.atendente.name}">
			${chamado.atendente.name}
		</g:link>
	</g:when>
	<g:otherwise>
		<span style='color: #999999; font-size: 10px'>Não Atribuído</span>
	</g:otherwise>
</g:choose>