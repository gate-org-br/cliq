<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method="POST" action="#" enctype='multipart/form-data'>
		<div class="PageControl">
			<ul>
				<li>
					<a href="#">
						<g:name type="${screen.form.pendencia}"/>
						<g:icon type="${screen.form.pendencia}"/>
					</a>
					<div>
						<fieldset>
							<fieldset style="height: 400px">
								<g:text-editor id="textarea" property="form.evento.observacoes" tabindex='1'/>
							</fieldset>
							<label>
								<span>
									<g:file property="form.evento.anexo.arquivo" tabindex='1' value='' required=""/>
								</span>
							</label>
						</fieldset>

						<div class='Coolbar'>
							<g:link method="post" module="cliq.modulos" screen="#" action="AceitarFeedback"
								arguments="form.id=${screen.form.id}" tabindex='2'
								data-confirm='Tem certeza de que deseja aceitar a solução do chamado?'/>

							<g:link method="post" module="cliq.modulos" screen="#" action="RecusarFeedback"
								arguments="form.id=${screen.form.id}" tabindex='2'
								data-confirm='Tem certeza de que deseja recusar a solução do chamado?'/>

							<a class='Cancel Hide' href="#" tabindex='3'>
								Fechar<g:icon type="cancel"/>
							</a>
						</div>								
					</div>
				</li>
				<li>
					<a href="#">
						Chamado<g:icon type="cliq.entity.Chamado"/>
					</a>
					<div>
						<div style="max-height: calc(100vh - 100px); overflow: auto">
							<cliq:chamado/>	
						</div>
					</div>
				</li>
			</ul>
		</div>

		<g:hidden property="form.id"/>
	</form>
</g:template>