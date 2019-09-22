<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/PAGE.jsp">
	<script>
		window.addEventListener("load",
			() => Array.from(document.querySelectorAll("a[target='_dialog'], tr[data-target='_dialog']"))
				.forEach(element => element.addEventListener("hide", () => document.getElementById("form").submit())));
	</script>

	
	<cliq:atendimentos chamados="${screen.chamados}"/>
</g:template>
