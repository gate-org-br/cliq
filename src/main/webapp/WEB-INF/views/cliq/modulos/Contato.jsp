<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<fieldset>
		<legend>
			<g:icon type="${action}"/>
			<g:name type="${action}"/>
		</legend>
		<label data-size="4">
			Tipo:
			<span>
				<g:icon type="${screen.form.tipo}"/>
				<g:print value="${screen.form.tipo}"/>
			</span>
		</label>
		<label data-size="4">
			Nome:
			<span>
				<g:print value="${screen.form.nome}"/>
			</span>
		</label>
		<label data-size="2">
			Telefone:
			<span>
				<g:print value="${screen.form.telefone}"/>
			</span>
		</label>
		<label data-size="2">
			Celular:
			<span>
				<g:print value="${screen.form.celular}"/>
			</span>
		</label>
		<label data-size="4">
			E-Mail:
			<span>
				<g:print value="${screen.form.email}"/>
			</span>
		</label>
		<label>
			Comentários:
			<span style='flex-basis: 80px'>
				<g:print value="${screen.form.email}"/>
			</span>
		</label>
	</fieldset>
</g:template>
