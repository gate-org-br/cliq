<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method='POST' action='#' enctype='multipart/form-data'>
		<fieldset>
			<legend style='padding: 5px;'>
				<img src='Gate?MODULE=cliq.modulos&SCREEN=Categoria&ACTION=Icon&form.id=${screen.form.tipo.id}'/>
				<g:label property="form.tipo.nome"/>
			</legend>
			<label>
				T�tulo:
				<span>
					<g:text property="form.titulo" tabindex='1'/>
				</span>
			</label>
			<g:form property="form.formulario" tabindex='1'/>
			<fieldset style="height: 400px">
				<g:text-editor id="textarea" property="form.descricao" tabindex='1'/>
			</fieldset>
			<label>
				Arquivo:
				<span>
					<g:file property="form.anexo.arquivo" tabindex='1' required=""/>
				</span>
			</label>
			<g:hidden property="form.id"/>
		</fieldset>

		<g-coolbar>
			<g:link class="Commit" method="post" module="#" screen="#" action="#" arguments="form.id=${screen.form.id}" tabindex='2'>
				Concluir<g:icon type="commit"/>
			</g:link>
			<hr/>
			<g:link class="Cancel" module="#" screen="#" action="Select" arguments="form.id=${screen.form.id}" tabindex='3'>
				Desistir<g:icon type="cancel"/>
			</g:link>
		</g-coolbar>
	</form>
</g:template>