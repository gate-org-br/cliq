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
				Equipe:
				<span>
					<g:hidden id="form.localizacao.id" property="form.localizacao.id" required=''/>
					<g:text id="form.localizacao.name" property="form.localizacao.name" required='' readonly="readonly"/>
					<g:shortcut module="#" screen="Equipe" action="Search" data-get="form.localizacao.id, form.localizacao.name"
						    data-autoclick="data-autoclick" title="Selecione a equipe"/>
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
			<g:link class="Commit" method="post" module="#" screen="#" action="#" tabindex='3'>
				Concluir<g:icon type="commit"/>
			</g:link>
			<hr/>
			<g:link class="Cancel" module="#" screen="#" action="Select" arguments="form.id=${screen.form.id}" tabindex='3'>
				Desistir<g:icon type="cancel"/>
			</g:link>
		</g-coolbar>
	</form>
</g:template>