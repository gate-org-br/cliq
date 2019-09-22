<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<fieldset>
		<legend>
			<g:icon type="cliq.modulos.atendimento.AtendimentoScreen"/>Atendimentos
		</legend>
		<ul class='DeskPane'>
			<g:menuitem module='#' screen='Pessoais'/>
			<g:menuitem module='#' screen='DaEquipe'/>
			<g:menuitem module='#' screen='Chamados'/>
			<g:menuitem module='#' screen='Pesquisa'/>
			<g:menuitem module='#' screen='Contato'/>
			<g:menuitem/>
		</ul>
	</fieldset>
</g:template>
