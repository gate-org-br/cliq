<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method='POST' action='#' enctype='multipart/form-data'>
		<fieldset>
			<legend>
				<g:name type="${action}"/>
				<g:icon type="${action}"/>
				<g:hidden property="form.id"/>
			</legend>

			<fieldset style="height: 400px">
				<g:text-editor id="textarea" 
					       property="form.evento.observacoes" tabindex='1'/>
			</fieldset>

			<label data-size='8'>
				Anexo:
				<span>
					<g:file property="form.evento.anexo.arquivo" tabindex='1' required=""/>
				</span>
			</label>
			<label data-size='8'>
				Retomar em:
				<span>
					<g:text class='DateTime' property="form.retomada"
						title='Entre com a data de retomada.' tabindex='1'/>
				</span>
			</label>
		</fieldset>
		<g-coolbar>
			<g:link class="Commit" method="post" module="#" screen="#" action="#" tabindex='2'>
				Concluir<g:icon type="commit"/>
			</g:link>
			<hr/>
			<g:link class="Cancel" module="#" screen="#" action="Select" arguments="form.id=${screen.form.id}" tabindex='3'>
				Desistir<g:icon type="cancel"/>
			</g:link>
		</g-coolbar>
	</form>
</g:template>