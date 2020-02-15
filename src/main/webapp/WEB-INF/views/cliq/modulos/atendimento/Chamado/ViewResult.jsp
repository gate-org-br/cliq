<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/MAIN.jsp">
	<script>
		window.addEventListener("load", () => window.frameElement.dialog.hide());
		<g:iterator source="${screen.messages}">
		alert('${target}');
		</g:iterator>
	</script>
</g:template>
