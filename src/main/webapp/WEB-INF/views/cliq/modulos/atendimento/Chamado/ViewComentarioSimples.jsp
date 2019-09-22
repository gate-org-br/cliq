<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<div class='LinkControl'>
		<ul>
			<li data-selected='true'>
				<g:link module="#" screen="#" action="ComentarioSimples" arguments="form.id=${screen.form.id}">
					Comentário Simples<g:icon type="cliq.entity.Evento$Tipo:COMENTARIO_SIMPLES"/>
				</g:link>
			</li>
			<li>
				<g:link module="#" screen="#" action="ComentarioInterno" arguments="form.id=${screen.form.id}">
					Comentário Interno<g:icon type="cliq.entity.Evento$Tipo:COMENTARIO_INTERNO"/>
				</g:link>
			</li>
		</ul>
		<div>
			<form method='POST' action='#' enctype='multipart/form-data'>
				<fieldset>
					<legend>
						<g:icon type="${action}"/>
						<g:name type="${action}"/>
						<g:hidden property="form.id"/>
					</legend>
					<label>
						Comentário:
						<span class="Editor">
							<g:textarea tabindex='1'
								    id="textarea" property="form.evento.observacoes"/>
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
		</div>
	</div>
</g:template>