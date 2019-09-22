<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<cliq:pendencias/>
	
	<cliq:estatisticas/>

	<div class='LinkControl'>
		<ul>
			<g:menuitem module='#' screen='Pessoais' data-selected="${SCREEN eq 'Pessoais'}"/>
			<g:menuitem module='#' screen='DaEquipe' data-selected="${SCREEN eq 'DaEquipe'}"/>
			<g:menuitem module='#' screen='Chamados' data-selected="${SCREEN eq 'Chamados'}"/>
			<g:menuitem module='#' screen='Pesquisa' data-selected="${SCREEN eq 'Pesquisa'}"/>
			<g:menuitem module='#' screen='Evento' data-selected="${SCREEN eq 'Evento'}"/>
			<g:menuitem module='#' screen='Contato' data-selected="${SCREEN eq 'Contato'}"/>
		</ul>
		<div>
			<g:insert/>
		</div>
	</div>
</g:template>