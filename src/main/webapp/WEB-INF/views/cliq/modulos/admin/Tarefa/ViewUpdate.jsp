<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<form method='POST' action='#'>
		<fieldset>
			<legend>
				<g:hidden property="form.id"/>
				<g:icon type="update"/>Alterar Tarefa
			</legend>
			<label data-size="8">
				Título:
				<span>
					<g:text property='form.nome' tabindex='1'/>
				</span>
			</label>
			<label data-size="4">
				Categoria:
				<span>
					<g:select property='form.categoria.id' options="${equipe.categorias}" values="${e -> e.id}" tabindex='1'/>
				</span>
			</label>
			<label data-size="2">
				Início:
				<span>
					<g:text class='DateTime' property="form.inicio" tabindex='1'/>
				</span>
			</label>
			<label data-size="1">
				Período:
				<span>
					<g:text property='form.periodo.valor' tabindex='1'/>
				</span>
			</label>
			<label data-size="1">
				Unidade:
				<span>
					<g:select property='form.periodo.unidade' tabindex='1'/>
				</span>
			</label>
			<label data-size="8">
				Origem:
				<span>
					<g:select property='form.origem.id' options="${screen.user.role.getRoot()}"
						  children="${e -> e.children}" values="${e -> e.id}" tabindex='1' required=''>
						${option.name}
					</g:select>
				</span>
			</label>
			<label data-size="8">
				Atendente:
				<span>
					<g:select property='form.atendente.id' options="${equipe.users}" values="${e -> e.id}" tabindex='1' required=''/>
				</span>
			</label>
			<label>
				Descrição:
				<span style='flex-basis: 120px;'>
					<g:textarea property='form.descricao' tabindex='1'/>
				</span>
			</label>
		</fieldset>

		<div class='Coolbar'>
			<g:link method="post" module="#" screen="#" action="#" tabindex='2' style='color: #006600'>
				Concluir<g:icon type='commit'/>
			</g:link>
			<g:link module="#" screen="#" action="Select" arguments="form.id=${screen.form.id}" tabindex='3' style='float: left; color: #660000'>
				Desistir<g:icon type='cancel'/>
			</g:link>
		</div>
	</form>
</g:template>