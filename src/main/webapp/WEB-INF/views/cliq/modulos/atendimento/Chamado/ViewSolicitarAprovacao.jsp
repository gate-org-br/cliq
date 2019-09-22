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
					<g:hidden id="form.pessoaAprovadora.id" property="form.pessoaAprovadora.id" required=''/>
					<g:hidden id="form.equipeAprovadora.id" property="form.equipeAprovadora.id" required=''/>
					<g:text id="responsavel" property="responsavel" tabindex='1' required='' data-getter="form.responsavel"/>
					<g:shortcut id="form.responsavel" module="#" screen="Ator" action="Search" arguments="form=@{responsavel}"
						    data-get="null, form.pessoaAprovadora.id, form.equipeAprovadora.id, responsavel"/>
				</span>
			</label>

			<label>
				Comentário:
				<span class="Editor">
					<g:textarea id="textarea" property="form.evento.observacoes" tabindex='2'/>
				</span>
			</label>
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