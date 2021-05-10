<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method="POST" action="#" enctype='multipart/form-data'>
		<g-tab-control>
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
					<g:link method="post" module="cliq.modulos" screen="#" action="AceitarFeedback"
						arguments="form.id=${screen.form.id}" tabindex='2'
						data-confirm='Tem certeza de que deseja aceitar a solu��o do chamado?'/>

					<g:link method="post" module="cliq.modulos" screen="#" action="RecusarFeedback"
						arguments="form.id=${screen.form.id}" tabindex='2'
						data-confirm='Tem certeza de que deseja recusar a solu��o do chamado?'/>
					<hr/>
					<a class='Hide' href="#" tabindex='3'>
						Fechar<g:icon type="cancel"/>
					</a>
				</g-coolbar>								
			</div>
			<a href="#">
				Chamado<g:icon type="cliq.entity.Chamado"/>
			</a>
			<div>
				<div style="max-height: calc(100vh - 100px); overflow: auto">
					<cliq:chamado/>	
				</div>
			</div>


			<g:hidden property="form.id"/>
		</g-tab-control>
	</form>
</g:template>