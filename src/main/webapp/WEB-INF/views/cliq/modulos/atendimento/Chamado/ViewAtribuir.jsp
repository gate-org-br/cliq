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
				Atendente:
				<span>
					<g:select property="form.atendente"
						  options="${equipe.users}" tabindex='1'
						  title='Selecione um atendente.' required='required'/>
				</span>
			</label>
			<label>
				Comentário:
				<span class="Editor">
					<g:textarea id="textarea" property="form.evento.observacoes"/>
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
			<g:link method="post" module="#" screen="#" action="#" tabindex='3' style='color: #006600'>
				Concluir<g:icon type="commit"/>
			</g:link>
			<g:link module="#" screen="#" action="Select" arguments="form.id=${screen.form.id}" tabindex='3' style='float: left; color: #660000'>
				Desistir<g:icon type="cancel"/>
			</g:link>
		</div>
	</form>
</g:template>
