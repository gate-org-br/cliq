<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method='POST' action='Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Commit' enctype='multipart/form-data'>
		<g:if condition="${not empty screen.form.categoria.descricao}">
			<div class='TEXT'>
				<g:print value="${screen.form.categoria.descricao}"/>
			</div>
		</g:if>

		<cliq:formulario categoria="${screen.form.categoria}" form="form"/>

		<div class='Coolbar'>
			<g:link method="post" module="#" screen="#" action="Commit" tabindex='2' style='color: #006600'>
				Concluir<g:icon type='commit'/>
			</g:link>
			<a class='Hide' style='float: left; color: #660000' tabindex='3'>
				Desistir<g:icon type='cancel'/>
			</a>
		</div>
	</form>
</g:template>