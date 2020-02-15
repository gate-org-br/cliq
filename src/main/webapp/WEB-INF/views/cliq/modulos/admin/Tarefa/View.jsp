<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<div class='Coolbar'>
		<g:link module="#" screen="#" action="Insert" arguments="form.categoria.role.id=${screen.form.categoria.role.id}"/>
	</div>
	<g:choose>
		<g:when condition="${empty screen.page}">
			<div class='TEXT'>
				<h1>
					Nenhuma Tarefa encontrada
					<br/>Clique em Inserir para cadastrar uma nova Tarefa
				</h1>
			</div>
		</g:when>
		<g:otherwise>
			<table id="Tarefas" class="c1 c3 c4 c5">
				<caption>
					TAREFAS DA <g:print value="${equipe.name}" format="%S"/>: ${screen.page.size()}
				</caption>

				<thead>
					<tr>
						<th style='width: 50px'>
							#
						</th>
						<th data-sortable>
							Nome
						</th>
						<th data-sortable style='width: 200px'>
							Categoria
						</th>
						<th data-sortable style='width: 200px'>
							Início
						</th>
						<th data-sortable style='width: 200px'>
							Período
						</th>
					</tr>
				</thead>

				<tbody>
					<g:iterator source="${screen.page}" target="tarefa" index="index">
						<tr data-action='Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Select&form.id=${tarefa.id}'>
							<td style='padding: 5px;'>
								<img  src='Gate?MODULE=cliq.modulos&SCREEN=Categoria&ACTION=Icon&form.id=${tarefa.categoria.id}' style='width: 24px; height: 24px;'/>
							</td>
							<td>
								${tarefa.nome}
							</td>
							<td>
								${tarefa.categoria.nome}
							</td>
							<td data-value="${g:number(tarefa.inicio)}">
								<g:print value="${tarefa.inicio}"/>
							</td>
							<td>
								${tarefa.periodo}
							</td>
						</tr>
					</g:iterator>
				</tbody>
			</table>
		</g:otherwise>
	</g:choose>
</g:template>

<style>
	#Tarefas > * > tr > *:nth-child(1) { display: none }
	#Tarefas > * > tr > *:nth-child(3) { display: none }
	#Tarefas > * > tr > *:nth-child(4) { display: none }
	#Tarefas > * > tr > *:nth-child(5) { display: none }

	@media only screen and (min-width: 400px) { #Tarefas > * > tr > *:nth-child(1) { display: table-cell } }
	@media only screen and (min-width: 600px) { #Tarefas > * > tr > *:nth-child(4) { display: table-cell } }
	@media only screen and (min-width: 800px) { #Tarefas > * > tr > *:nth-child(5) { display: table-cell } }
	@media only screen and (min-width: 1000px) { #Tarefas > * > tr > *:nth-child(3) { display: table-cell } }
</style>