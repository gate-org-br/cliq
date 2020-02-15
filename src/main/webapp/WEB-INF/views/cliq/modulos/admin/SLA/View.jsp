<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<div class='Coolbar'>
		<g:link module="#" screen="#" action="Insert">
			Inserir<g:icon type="insert"/>
		</g:link>
	</div>
	<g:choose>
		<g:when condition="${empty screen.page}">
			<div class='TEXT'>
				<h1>
					Nenhuma Política de SLA encontrada
					<br/>Clique em Inserir para cadastrar uma nova Política de SLA
				</h1>
			</div>
		</g:when>
		<g:otherwise>
			<table>
				<col style='width: 5%'>
				<col style='width: 10%'>
				<col style='width: 10%'>
				<col style='width: 10%'>
				<col style='width: 10%'>
				<col style='width: 10%'>
				<col style='width: 10%'>
				<col style='width: 10%'>
				<col style='width: 10%'>
				<col style='width: 10%'>
				<col style='width: 5%'>
				<caption>
					POL&Iacute;TICAS DE SLA ENCONTRADAS: ${screen.page.size()}
				</caption>
				<thead>
					<tr>
						<th style='text-align: center'>
							#
						</th>
						<th style='text-align: center'>
							Nome
						</th>
						<th style='text-align: center'>
							Categoria
						</th>
						<th style='text-align: center'>
							Origem
						</th>
						<th style='text-align: center'>
							Solicitante
						</th>
						<th style='text-align: center'>
							Prioridade
						</th>
						<th style='text-align: center'>
							Complex
						</th>
						<th style='text-align: center'>
							Projeto
						</th>
						<th style='text-align: center'>
							Resposta
						</th>
						<th style='text-align: center'>
							Solução
						</th>
						<th style='text-align: center'>
							Urgente
						</th>
					</tr>
				</thead>
				<tbody>
					<g:iterator source="${screen.page}" target="target" index="index">
						<tr
							data-action="Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Select&form.id=${target.id}">
							<td style='text-align: center'>
								<g:print value="${target.precedencia}"/>
							</td>
							<td style='text-align: center'>
								<g:print value="${target.nome}"/>
							</td>
							<td style='text-align: center'>
								<g:print value="${target.categoria.nome}" empty="Todas"/>
							</td>
							<td style='text-align: center'>
								<g:if condition="${not empty target.origem.id}" otherwise="Todos">
									<g:print value="${screen.user.role.root.getRole(target.origem.id)}"/>
								</g:if>
							</td>
							<td style='text-align: center'>
								<g:print value="${screen.form.solicitante.name}" empty="Todos"/>
							</td>
							<td style='text-align: center'>
								<g:print value="${target.prioridade}" empty="Todas"/>
							</td>
							<td style='text-align: center'>
								<g:print value="${target.complexidade}" empty="Todas"/>
							</td>
							<td style='text-align: center'>
								<g:print value="${target.projeto}" empty="N/A"/>
							</td>
							<td style='text-align: center'>
								<g:print value="${target.ini}"/>
								<g:print value="${target.uini}"/>
							</td>
							<td style='text-align: center'>
								<g:print value="${target.fim}"/>
								<g:print value="${target.ufim}"/>
							</td>
							<td style='text-align: center'>
								<g:print value="${target.urgente}"/>
							</td>
						</tr>
					</g:iterator>
				</tbody>
			</table>
		</g:otherwise>
	</g:choose>
</g:template>