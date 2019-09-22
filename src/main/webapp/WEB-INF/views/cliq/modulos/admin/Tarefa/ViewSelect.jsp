<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<fieldset>
		<legend>
			<g:icon type="cliq.modulos.admin.TarefaScreen:callSelect()"/>
			<g:name type="cliq.modulos.admin.TarefaScreen:callSelect()"/>
		</legend>
		<label data-size="8">
			Título:
			<span>
				<g:label property='form.nome'/>
			</span>
		</label>
		<label data-size="4">
			Categoria:
			<span>
				<g:label property="form.categoria.nome"/>
				<g:shortcut module="#" screen="Categoria" action="Select" arguments="form.id=${screen.form.categoria.id}"/>
			</span>
		</label>
		<label data-size="2">
			Início:
			<span>
				<g:icon type="date"/>
				<g:label property='form.inicio'/>
			</span>
		</label>
		<label data-size="1">
			Período:
			<span>
				<g:label property='form.periodo.valor'/>
			</span>
		</label>
		<label data-size="1">
			Unidade:
			<span>
				<g:label property='form.periodo.unidade'/>
			</span>
		</label>
		<label data-size="8">
			Origem:
			<span>
				<g:icon type="gate.entity.Role"/>
				<g:if condition="${not empty screen.form.origem.id}" otherwise="N/A">
					<g:print value="${screen.user.role.getRoot().select(screen.form.origem.id)}"/>
				</g:if>
			</span>
		</label>
		<label data-size="8">
			Atendente:
			<span>
				<g:icon type="gate.entity.User"/>
				<g:print value="${screen.form.atendente.name}"/>
			</span>
		</label>
		<label>
			Descrição:
			<span style='flex-basis: 120px;'>
				<g:label property='form.descricao'/>
			</span>
		</label>
	</fieldset>

	<div class='Coolbar'>
		<g:link module="#" screen="#" action="Update" arguments="form.id=${screen.form.id}" tabindex='1'/>
		<g:link module="#" screen="#" action="Delete" arguments="form.id=${screen.form.id}"
			tabindex='3' data-confirm='Tem certeza de que deseja remover o registro?'/>
		<g:link module="#" screen="#" tabindex='2' style='float: left'>
			Retornar<g:icon type='return'/>
		</g:link>
	</div>
</g:template>