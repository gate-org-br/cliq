<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method='POST' action='#' 
	      enctype='multipart/form-data'>
		<fieldset>
			<legend>
				<g:hidden property="form.evento.id"/>
				<g:hidden property="form.evento.chamado.id"/>
				<g:icon type='update'/>Alterar <g:print value="${screen.form.evento.tipo}"/>
			</legend>
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
		<div class='Coolbar'>
			<g:link method="post" module="#" screen="#" action="#" tabindex='3' style='color: #006600'>
				Concluir<g:icon type="commit"/>
			</g:link>
			<g:link module="#" screen="#" action="Select" arguments="form.id=${screen.form.evento.chamado.id}" tabindex='3' style='float: left; color: #660000'>
				Desistir<g:icon type="cancel"/>
			</g:link>
		</div>
	</form>	
</g:template>