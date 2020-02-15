<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<form method='POST' action='#'>
		<fieldset>
			<legend>
				<g:icon type="insert"/>Inserir Contato
			</legend>
			<label data-size='4'>
				Tipo:
				<span>
					<g:select property="form.tipo" options="${screen.tipos}" tabindex='1'/>
				</span>
			</label>
			<label data-size='4'>
				Nome:
				<span>
					<g:text property="form.nome" tabindex='1'/>
				</span>
			</label>
			<label data-size='2'>
				Telefone:
				<span>
					<g:icon type="gate.type.Phone"/>
					<g:text property="form.telefone" tabindex='1'/>
				</span>
			</label>
			<label data-size='2'>
				Celular:
				<span>
					<g:icon type="gate.type.Phone"/>
					<g:text property="form.celular" tabindex='1'/>
				</span>
			</label>
			<label data-size='4'>
				E-Mail:
				<span>
					<g:icon type="gate.type.EMail"/>
					<g:text property="form.email" tabindex='1'/>
				</span>
			</label>
			<label>
				Comentários:
				<span style='flex-basis: 80px'>
					<g:textarea property="form.comentarios" tabindex='1'/>
				</span>
			</label>

			<fieldset>
				<label data-size='8'>
					Equipe:
					<span>
						<g:icon type="gate.entity.Role"/>
						<g:print value="${screen.user.role}"/>
					</span>
				</label>
				<label data-size='8'>
					Visibilidade:
					<span>
						<g:select property='form.visibilidade' tabindex='1'/>
						<a target="_popup" href="#">
							<g:icon type="2000"/>
							<div title='Exceções'>
								<fieldset>
									<g:selectn property="form.roles[]"
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
			<g:link class="Commit" method='post' module="#" screen="#" action="#" tabindex='2'>
				Concluir<g:icon type='commit'/>
			</g:link>
			<g:link class="Cancel" module="#" screen="#" tabindex='2'>
				Desistir<g:icon type='cancel'/>
			</g:link>
		</div>
	</form>
</g:template>