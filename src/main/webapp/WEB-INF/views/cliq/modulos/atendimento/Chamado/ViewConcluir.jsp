<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<g-dialog-config caption="Concluir">
	</g-dialog-config>
	<article>
		<section>
			<form id="form" method='POST' action='#'
			      enctype='multipart/form-data'>
				<fieldset>
					<g:if condition="${not empty screen.form.categoria.conclusoes}">
						<label>
							Status:
							<span>
								<g:select property="form.evento.status" required='required'
									  options="${screen.form.categoria.conclusoes}"
									  tabindex='1' title='Selecione o status da conclusão.'/>
							</span>
						</label>
					</g:if>
					<label>
						Comentário:
						<span
							style="flex-basis: calc(100vh - 260px)">
							<g:textarea id="textarea" property="form.evento.observacoes" tabindex="1"/>
						</span>
					</label>
					<label>
						Anexo:
						<span>
							<g:file property="form.evento.anexo.arquivo" tabindex='1' required=""/>
						</span>
					</label>
					<label>
						Atendente:
						<span>
							<g:select property="form.atendente.id" options="${equipe.users}" values="${e -> e.id}"
								  tabindex='1' required='' title='Selecione um atendente.' empty="Eu mesmo"/>
						</span>
					</label>
				</fieldset>
				<g:hidden property="form.id"/>
			</form>
		</section>
		<footer>
			<g-coolbar>
				<g:link form="form"
					class="Commit" method="post" module="#" screen="#" action="#" tabindex='2'>
					Concluir<g:icon type="commit"/>
				</g:link>
				<hr/>
				<g:link class="Cancel" module="#" screen="#" action="Select" arguments="form.id=${screen.form.id}" tabindex='3'>
					Desistir<g:icon type="cancel"/>
				</g:link>
			</g-coolbar>
		</footer>
	</article>
</g:template>
