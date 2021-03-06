<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method='POST' action='#' enctype='multipart/form-data'>
		<fieldset>
			<legend>
				<g:icon type="${action}"/>
				<g:name type="${action}"/>
				<g:hidden property="form.id"/>
			</legend>
			<label>
				Atendente:
				<span>
					<g:select property="form.atendente"
						  options="${equipe.users}" tabindex='1'
						  title='Selecione um atendente.' required='required'/>
				</span>
			</label>
			<fieldset style="height: 400px">
				<g:text-editor id="textarea" property="form.evento.observacoes" tabindex='1'/>
			</fieldset>
			<label>
				Anexo:
				<span>
					<g:file property="form.evento.anexo.arquivo" tabindex='1' required=""/>
				</span>
			</label>
		</fieldset>
		<g-coolbar>
			<g:link method="post" module="#" screen="#" action="#" tabindex='3' class="Commit">
				Concluir<g:icon type="commit"/>
			</g:link>
			<hr/>
			<g:link module="#" screen="#" action="Select" arguments="form.id=${screen.form.id}" tabindex='3' class="Cancel">
				Desistir<g:icon type="cancel"/>
			</g:link>
		</g-coolbar>
	</form>
</g:template>
