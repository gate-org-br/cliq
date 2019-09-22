<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<fieldset>
		<legend>
			<g:icon type="cliq.modulos.solicitante.SolicitanteScreen"/>Solicitações
		</legend>
		<ul class='DeskPane'>
			<g:menuitem module='#' screen='Solicitante' action="Insert"/>
			<g:menuitem module='#' screen='Pessoais'/>
			<g:menuitem module='#' screen='DaEquipe'/>
			<g:menuitem module='#' screen='Pesquisa'/>
		</ul>
	</fieldset>
</g:template>
