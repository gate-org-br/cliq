<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method='POST' action='#' enctype='multipart/form-data'>
		<fieldset>
			<legend>
				<g:name type="${action}"/>
				<g:icon type="${action}"/>
			</legend>
			<label>
				Título:
				<span>
					<g:text property="form.titulo" tabindex='1'/>
				</span>
			</label>
			<g:form property="form.formulario" tabindex='1'/>
			<label>
				Descrição:
				<span class="Editor">
					<g:textarea id="textarea" property="form.descricao"/>
				</span>
			</label>
			<label>
				Arquivo:
				<span>
					<g:file property="form.anexo.arquivo" tabindex='1' required= ""/>
				</span>
			</label>
		</fieldset>

		<div class='Coolbar'>
			<g:link class="Commit" method="post" module="#" screen="#" action="#" tabindex='2'>
				Concluir<g:icon type='commit'/>
			</g:link>
			<g:link class="Cancel" module="#" screen="#" action="Select" arguments="form.id=${screen.form.id}" tabindex='3'>
				Desistir<g:icon type='cancel'/>
			</g:link>
		</div>
		<g:hidden property="form"/>
	</form>
</g:template>