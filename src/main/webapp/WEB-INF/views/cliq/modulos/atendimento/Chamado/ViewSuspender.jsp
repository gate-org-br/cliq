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
			<label>
				Comentário:
				<span class="Editor">
					<g:textarea id="textarea" property="form.evento.observacoes" tabindex="1"/>
				</span>
			</label>
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
		<div class='Coolbar'>
			<g:link class="Commit" method="post" module="#" screen="#" action="#" tabindex='2'>
				Concluir<g:icon type="commit"/>
			</g:link>
			<g:link class="Cancel" module="#" screen="#" action="Select" arguments="form.id=${screen.form.id}" tabindex='3'>
				Desistir<g:icon type="cancel"/>
			</g:link>
		</div>
	</form>
</g:template>