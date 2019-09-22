<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method='POST' action='#' enctype='multipart/form-data'>
		<fieldset>
			<g:hidden property="form.id"/>
			<legend>
				<g:icon type="cliq.entity.Evento$Tipo:PRAZO_RESPOSTA"/>Definir prazo de resposta
			</legend>
			<label>
				Novo Prazo:
				<span>
					<g:text class='DateTime' property="form.prazoResposta" title='Entre com o novo prazo de resposta.' tabindex='1'/>
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
