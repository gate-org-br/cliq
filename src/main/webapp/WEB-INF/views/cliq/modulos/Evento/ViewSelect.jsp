<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<g:if condition="${not empty screen.form.alteracao}">
		<div class="TEXT">
			<h1>
				Este evento foi alterado em <g:print value="${screen.form.alteracao}"/>
			</h1>
		</div>
	</g:if>

	<fieldset>
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
			<span style='flex-basis: 400px;'>
				<g:label property='form.observacoes'/>
			</span>
		</label>
		<g:if condition="${not empty screen.form.anexo.id}">
			<label>
				Anexo:
				<span>
					<label>
						Baixar
					</label>
					<g:shortcut module="cliq.modulos" screen="Anexo" arguments="form.id=${screen.form.anexo.id}"/>
				</span>
			</label>

		</g:if>
	</fieldset>

	<g-coolbar>
		<g:link condition="${screen.form.user.id eq screen.user.id}"
			module="#" screen="#" action="Update" arguments="form.id=${screen.form.id}"/>
		<hr/>
		<a target="_hide" class='Cancel' href="#">
			Fechar<g:icon type="cancel"/>
		</a>
	</g-coolbar>
</g:template>