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

						<g-coolbar>
							<g:values source="cliq.type.Nota" target="nota" reverse="true">
								<g:link method="post" module="cliq.modulos" screen="Avaliacao" action="Avaliar"
									arguments="form.id=${screen.form.id}&form.nota=${nota.ordinal()}" style='color: ${nota.color}' tabindex='2'
									data-confirm='Tem certeza de que deseja avaliar o serviço prestado como ${g:print(nota)}?'>
									<g:print value="${nota}"/><g:icon type="${nota}"/>
								</g:link>
							</g:values>
							<hr/>
							<a target="_hide" class='Cancel' href="#" tabindex='3'>
								Fechar<g:icon type="cancel"/>
							</a>
						</g-coolbar>								
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