<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<article>
		<section>
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
					<table class="c1 c2 c3 c4 c5 c6 c7 c8 c9 c10 c11">
						<caption>
							POL&Iacute;TICAS DE SLA ENCONTRADAS: ${screen.page.size()}
						</caption>
						<thead>
							<tr>
								<th style="width: 64px">
									#
								</th>
								<th>
									Nome
								</th>
								<th>
									Categoria
								</th>
								<th>
									Origem
								</th>
								<th>
									Solicitante
								</th>
								<th>
									Prioridade
								</th>
								<th>
									Complex
								</th>
								<th>
									Projeto
								</th>
								<th>
									Resposta
								</th>
								<th>
									Solução
								</th>
								<th style="width: 64px">
									Urgente
								</th>
							</tr>
						</thead>
						<tbody>
							<g:iterator source="${screen.page}" target="target" index="index">
								<tr data-action="Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Select&form.id=${target.id}">
									<td>
										<g:print value="${target.precedencia}"/>
									</td>
									<td>
										<g:print value="${target.nome}"/>
									</td>
									<td>
										<g:print value="${target.categoria.nome}" empty="Todas"/>
									</td>
									<td>
										<g:if condition="${not empty target.origem.id}" otherwise="Todos">
											<g:print value="${screen.user.role.root.getRole(target.origem.id)}"/>
										</g:if>
									</td>
									<td>
										<g:print value="${screen.form.solicitante.name}" empty="Todos"/>
									</td>
									<td>
										<g:print value="${target.prioridade}" empty="Todas"/>
									</td>
									<td>
										<g:print value="${target.complexidade}" empty="Todas"/>
									</td>
									<td>
										<g:print value="${target.projeto}" empty="N/A"/>
									</td>
									<td>
										<g:print value="${target.ini}"/>
										<g:print value="${target.uini}"/>
									</td>
									<td>
										<g:print value="${target.fim}"/>
										<g:print value="${target.ufim}"/>
									</td>
									<td>
										<g:print value="${target.urgente}"/>
									</td>
								</tr>
							</g:iterator>
						</tbody>
					</table>
				</g:otherwise>
			</g:choose>
		</section>
		<footer>
			<g-coolbar>
				<g:link module="#" screen="#" action="Insert"/>
			</g-coolbar>
		</footer>
	</article>
</g:template>