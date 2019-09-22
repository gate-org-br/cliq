<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<script>
		window.parent.location.href = 'Gate?MODULE=${MODULE}&SCREEN=Categoria&ACTION=Select&form.id=${screen.categoria.id}&tab=Formulario';
	</script>
</g:template>