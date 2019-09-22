<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method="post" action="#" enctype="multipart/form-data">
		<fieldset>
			<legend>
				<g:icon type="${action}"/>
				<g:name type="${action}"/>
			</legend>

			<label data-size='2'>
				Tipo:
				<span>
					<g:icon type="${screen.form.tipo}"/>
					<g:label property="form.tipo"/>
				</span>
			</label>
			<label data-size='2'>
				Data:
				<span>
					<g:icon type="date"/>
					<g:label property="form.data"/>
				</span>
			</label>
			<label data-size='4'>
				Responsável:
				<span>
					<g:icon type="gate.entity.User"/>
					<g:label property="form.user.name" empty="Criado pelo CliQ"/>
				</span>
			</label>
			<label data-size='8'>
				Descrição:
				<span>
					<g:label property="form.descricao"/>
				</span>
			</label>
			<label>
				Observações:
				<span style='flex-basis: 120px;'>
					<g:textarea property="form.observacoes" tabindex="1"/>
				</span>
			</label>
			<label>
				Arquivo:
				<span>
					<g:file property="form.anexo.arquivo" tabindex='1' required= ""/>
				</span>
			</label>
		</fieldset>

		<div class='Coolbar'>
			<g:link class="Commit" method="post" module="#" screen="#" action="#" tabindex='2'>
				Concluir<g:icon type='commit'/>
			</g:link>
			<g:link class="Cancel" module="#" screen="#" action="Select"
				arguments="form.id=${screen.form.id}" tabindex='3'>
				Desistir<g:icon type='cancel'/>
			</g:link>
		</div>

		<g:hidden property="form.id"/>
	</form>
</g:template>