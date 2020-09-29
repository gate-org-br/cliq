<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/PAGE.jsp">
	<cliq:atendimentos chamados="${screen.chamados}"/>
</g:template>
