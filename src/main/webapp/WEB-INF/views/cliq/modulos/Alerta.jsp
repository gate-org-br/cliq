<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<g:choose>
		<g:when condition="${not empty screen.page}">
			<g-coolbar>
				<g:link module="#" screen="#" action="Delete"
					data-confirm='Tem certeza de que deseja limpar sua lista de eventos?'>
					Limpar<g:icon type="delete"/>
				</g:link>
			</g-coolbar>
			<table class="c1 c2 c3 c4 c5 c6" data-collapse="Tablet">
				<caption>
					EVENTOS PENDENTES: ${screen.page.size()}
				</caption>

				<thead>
					<tr>
						<th style='width: 50px'>
							#
						</th>
						<th style='width: 150px'>
							Evento
						</th>
						<th style='width: 150px'>
							Data
						</th>
						<th>
							Responsável
						</th>
						<th>
							Chamado
						</th>
						<th>
							Observações
						</th>
					</tr>
				</thead>

				<tbody>
					<g:iterator source="${screen.page}" target="chamado" >
						<tr data-action='${chamado.getURL(screen.user)}'
						    title="Chamados"
						    tabindex="1"
						    data-target='_dialog'>
							<td>
								<g:icon type="${chamado.evento.tipo}"/>
							</td>
							<td title="Tipo">
								<g:print value="${chamado.evento.tipo}"/>
							</td>
							<td title="Data">
								<g:print value="${chamado.data}"/>
							</td>
							<td title="Responsável">
								<g:print value="${chamado.evento.user.name}" empty="Criado pelo CliQ"/>
							</td>
							<td title="Chamado">
								<g:print value="${chamado.titulo}"/>
							</td>
							<td title="Observações">
								<div>
									<g:print value="${chamado.evento.descricao}"/>
								</div>
								<g:if condition="${not empty chamado.evento.observacoes}">
									<div style="font-weight: bold; padding: 5px; text-align: justify">
										<g:print value="${chamado.evento.observacoes}"/>
									</div>
								</g:if>
							</td>
						</tr>
					</g:iterator>
				</tbody>
			</table>
		</g:when>
		<g:otherwise>
			<div class='TEXT'>
				<h1>Nenhum envento pendente de visualização</h1>
			</div>
		</g:otherwise>
	</g:choose>

	<script>
		window.addEventListener("load",
			() => Array.from(document.querySelectorAll("a[target='_dialog'], tr[data-target='_dialog']"))
				.forEach(element => element.addEventListener("hide", () => window.top.location = window.top.location.href)));
	</script>
</g:template>