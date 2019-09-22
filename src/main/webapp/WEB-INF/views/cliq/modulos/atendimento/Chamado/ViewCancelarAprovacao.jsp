<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method='POST' action='#' enctype='multipart/form-data'>
		<fieldset>
			<g:hidden property="form.id"/>
			<legend>
				<g:icon type="${action}"/>
				<g:name type="${action}"/>
			</legend>
			<label>
				Comentário:
				<span class="Editor">
					<g:textarea id="textarea" property="form.evento.observacoes" tabindex="1"/>
				</span>
			</label>
			<label>
				Anexo:
				<span>
					<g:file property="form.evento.anexo.arquivo" tabindex='1' required=""/>
				</span>
			</label>
		</fieldset>
		<div class='Coolbar'>
			<g:link class="Commit" method="post" module="#" screen="#" action="#" tabindex='3'>
				Concluir<g:icon type="commit"/>
			</g:link>
			<g:link class="Cancel" module="#" screen="#" action="Select" arguments="form.id=${screen.form.id}" tabindex='3'>
				Desistir<g:icon type="cancel"/>
			</g:link>
		</div>
	</form>
</g:template>