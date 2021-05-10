<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<fieldset>
		<legend>
			<g:path/>
		</legend>
		<ul class='DeskPane'>
			<g:menuitem module='cliq.modulos.admin' screen='Tarefa'/>
			<g:menuitem module='cliq.modulos.admin' screen='Categoria'/>
			<g:menuitem module='cliq.modulos.admin' screen='SLA'/>
		</ul>
	</fieldset>
</g:template>
