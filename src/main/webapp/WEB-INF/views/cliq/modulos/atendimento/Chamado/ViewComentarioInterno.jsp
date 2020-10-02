<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<div class='LinkControl'>
		<ul>
			<li>
				<g:link module="#" screen="#" action="ComentarioSimples" arguments="form.id=${screen.form.id}">
					Comentário Simples<g:icon type="cliq.entity.Evento$Tipo:COMENTARIO_SIMPLES"/>
				</g:link>
			</li>
			<li data-selected='true'>
				<g:link module="#" screen="#" action="ComentarioInterno" arguments="form.id=${screen.form.id}">
					Comentário Interno<g:icon type="cliq.entity.Evento$Tipo:COMENTARIO_INTERNO"/>
				</g:link>
			</li>
		</ul>
		<div>
			<form method='POST' action='#' enctype='multipart/form-data'>
				<fieldset>
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
				<g:hidden property="form.id"/>
			</form>
		</div>
	</div>
</g:template>