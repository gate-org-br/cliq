<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<cliq:pendencias/>

	<cliq:estatisticas/>

	<g-tab-control type="dummy">
		<g:link module='#' screen='Pessoais' data-selected="${SCREEN eq 'Pessoais'}"/>
		<g:link module='#' screen='DaEquipe' data-selected="${SCREEN eq 'DaEquipe'}"/>
		<g:link module='#' screen='Chamados' data-selected="${SCREEN eq 'Chamados'}"/>
		<g:link module='#' screen='Pesquisa' data-selected="${SCREEN eq 'Pesquisa'}"/>
		<g:link module='#' screen='Evento' data-selected="${SCREEN eq 'Evento'}"/>
		<g:link module='#' screen='Contato' data-selected="${SCREEN eq 'Contato'}"/>
		<div>
			<g:insert/>
		</div>
	</div>
</g:template>