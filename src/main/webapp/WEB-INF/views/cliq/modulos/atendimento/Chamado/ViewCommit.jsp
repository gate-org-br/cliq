<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method='POST' action='Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Commit' enctype='multipart/form-data'>
		<fieldset>
			<label>
				Solicitante:
				<span>
					<g:hidden id='form.contato.id' property="form.contato.id" required='required'/>
					<g:hidden id='form.contato.tipo' property="form.contato.tipo" required='required'/>
					<g:text id='form.contato.nome' property='form.contato.nome' required='required' data-getter="form.contato" tabindex="1"/>
					<g:link id="form.contato" module="#" screen="Contato" action="Search" arguments="form.nome=@{form.contato.nome}"
						data-get="form.contato.id, form.contato.tipo, form.contato.nome"
						title='Selecione um solicitante'
						tabindex='1'>
						<g:icon type="search"/>
					</g:link>
				</span>
			</label>
		</fieldset>

		<cliq:formulario categoria="${screen.form.categoria}" form="form"/>

		<div class='Coolbar'>
			<g:link class="Commit" method="post" module="#" screen="#" action="Commit" tabindex='3'>
				Concluir<g:icon type="commit"/>
			</g:link>
			<a href="#" class="Cancel Hide" tabindex='3' >
				Desistir<g:icon type='cancel'/>
			</a>
		</div>

		<g:hidden property="form.categoria.id"/>
	</form>
</g:template>