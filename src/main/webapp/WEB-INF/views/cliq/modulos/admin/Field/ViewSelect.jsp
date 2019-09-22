<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method='POST' action='#'>
		<fieldset>
			<label data-size='4'>
				Name:
				<span>
					<g:text property='form.name' tabindex='1'/>
				</span>
			</label>
			<label data-size='4'>
				Máscara:
				<span>
					<g:text property='form.mask' tabindex='1'/>
				</span>
			</label>
			<label data-size='2'>
				Colunas:
				<span>
					<g:select property='form.size' tabindex='1'/>
				</span>
			</label>
			<label data-size='2'>
				Multiplo:
				<span>
					<g:select property='form.multiple' tabindex='1'/>
				</span>
			</label>
			<label data-size='2'>
				Requerido:
				<span>
					<g:select property='form.required' tabindex='1'/>
				</span>
			</label>
			<label data-size='2'>
				Tamanho Max:
				<span>
					<g:text property='form.maxlength' tabindex='1'/>
				</span>
			</label>
			<label data-size='8'>
				Expressão Regular:
				<span style='flex-basis: 120px;'>
					<g:textarea property='form.pattern' tabindex='1'/>
				</span>
			</label>
			<label data-size='8'>
				Descrição:
				<span style='flex-basis: 120px;'>
					<g:textarea property='form.description' tabindex='1'/>
				</span>
			</label>
			<label data-size='8'>
				Opções:
				<span style='flex-basis: 120px;'>
					<g:textarea property='form.options' tabindex='1'/>
				</span>
			</label>
			<label data-size='8'>
				Valor:
				<span style='flex-basis: 120px;'>
					<g:textarea property='form.value' tabindex='1'/>
				</span>
			</label>
		</fieldset>

		<div class='Coolbar'>
			<g:link method='post' module="#" screen="#" action="Update">
				Alterar<g:icon type="update"/>
			</g:link>
			<g:link method='post' module="#" screen="#" action="Delete" style='color: #660000'>
				Remover<g:icon type="delete"/>
			</g:link>
			<a class='Hide' href="#"  style='float: left'>
				Fechar<g:icon type="cancel"/>
			</a>
		</div>

		<g:hidden property="index"/>
		<g:hidden property="categoria.id"/>
	</form>
</g:template>