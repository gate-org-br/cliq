<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
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
				Responsável:
				<span>
					<g:hidden id="form.pessoaHomologadora.id" property="form.pessoaHomologadora.id" required=''/>
					<g:hidden id="form.equipeHomologadora.id" property="form.equipeHomologadora.id" required=''/>
					<g:text id="responsavel" property="responsavel" tabindex='1' required='' data-getter="form.responsavel"/>
					<g:shortcut id="form.responsavel" module="#" screen="Ator" action="Search" arguments="form=@{responsavel}"
						    data-get="null, form.pessoaHomologadora.id, form.equipeHomologadora.id, responsavel"/>
				</span>
			</label>

			<fieldset style="height: 400px">
				<g:text-editor id="textarea" id="form.evento.observacoes"
					       property="form.evento.observacoes" tabindex='1'/>
			</fieldset>

			<label>
				Anexo:
				<span>
					<g:file property="form.evento.anexo.arquivo" tabindex='2' required=""/>
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