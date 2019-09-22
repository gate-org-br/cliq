<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<cliq:pendencias/>

	<div class='LinkControl'>
		<ul>
			<g:menuitem module="#" screen="Solicitante" action="Insert" data-selected="${ACTION eq 'Insert'}"/>
			<g:menuitem module="#" screen="Pessoais" data-selected="${SCREEN eq 'Pessoais'}"/>
			<g:menuitem module="#" screen="DaEquipe" data-selected="${SCREEN eq 'DaEquipe'}"/>
			<g:menuitem module="#" screen="Pesquisa" data-selected="${SCREEN eq 'Pesquisa'}"/>
		</ul>
		<div>
			<g:insert/>
		</div>
	</div>
</g:template>
