<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<fieldset>
		<legend>
			<g:icon type="cliq.entity.Evento$Tipo:CAPTURA"/>Contato
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
				<g:label property="form.nome"/>
			</span>
		</label>
		<label data-size="2">
			Telefone:
			<span>
				<g:icon type="gate.type.Phone"/>
				<g:label property="form.telefone"/>
			</span>
		</label>
		<label data-size="2">
			Celular:
			<span>
				<g:icon type="gate.type.Phone"/>
				<g:label property="form.celular"/>
			</span>
		</label>
		<label data-size="4">
			E-Mail:
			<span>
				<g:icon type="gate.type.EMail"/>
				<g:label property="form.email"/>
			</span>
		</label>
		<label>
			Comentários:
			<span style='flex-basis: 80px'>
				<g:label property="form.comentarios"/>
			</span>
		</label>

		<fieldset>
			<label data-size='8'>
				Equipe:
				<span>
					<g:icon type="gate.entity.Role"/>
					<label>
						<g:print value="${screen.user.role.getRoot().select(screen.form.role.id)}"/>	
					</label>
				</span>
			</label>
			<label data-size='8'>
				Visibilidade:
				<span>
					<g:label property='form.visibilidade'/>
					<a target="_popup" href="#">
						<g:icon type="2000"/>
						<div title='Exceções'>
							<fieldset>
								<g:selectn property="form.roles[]"
									   disabled="disabled"
									   options="${screen.user.role.getRoot()}"
									   children="${e -> e.children}"
									   labels="${e -> e.name}"/>
							</fieldset>
						</div>
					</a>
				</span>
			</label>
		</fieldset>
	</fieldset>

	<div class='Coolbar'>
		<g:if condition="${screen.form.tipo ne 'USUARIO'}">
			<g:link module="#" screen="#" action="Update" arguments="form.tipo=${screen.form.tipo.name()}&form.id=${screen.form.id}" tabindex='2'/>
			<g:link module="#" screen="#" action="Delete" arguments="form.tipo=${screen.form.tipo.name()}&form.id=${screen.form.id}" tabindex='3' 
				data-confirm='Tem certeza de que deseja remover o registro?'/>
		</g:if>
		<g:link class="Cancel" module="#" screen="#">
			Retornar<g:icon type="return"/>
		</g:link>
	</div>
</g:template>