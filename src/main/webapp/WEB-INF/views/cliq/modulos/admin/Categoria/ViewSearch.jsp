<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method='POST' action='Gate?MODULE=cliq.admin.screen&SCREEN=CATEGORIA&ACTION=Search'>
		<g:if condition="${not empty screen.page}">
			<table>
				<col style='width: 90%;'/>
				<col style='width: 10%;'/>
				<thead>
					<tr>
						<th style='text-align: center;'>
							#
						</th>
						<th>
							NOME
						</th>
					</tr>
				</thead>

				<tfoot></tfoot>

				<tbody>
					<g:iterator source="${screen.page}" target="categoria" index="index" children="${e -> e.children}" depth="depth">
						<tr
							data-value='form.id=${categoria.id}&form.nome=${categoria.nome}'>
							<td style='text-align: center; padding: 5px;'>
								<img  src='Gate?MODULE=cliq.modulos&SCREEN=Categoria&ACTION=Icon&form.id=${categoria.id}'
								      style='width: 24px; height: 24px;'/>
							</td>
							<td style='padding-left: ${depth*40}px;'>
								${categoria.nome}
							</td>
						</tr>
					</g:iterator>
				</tbody>
			</table>
		</g:if>
	</form>
</g:template>