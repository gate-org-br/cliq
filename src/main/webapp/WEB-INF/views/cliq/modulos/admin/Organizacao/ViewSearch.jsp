<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method='POST' action='Gate?MODULE=cliq.modulos.admin&SCREEN=Organizacao&ACTION=Search'>
		<g:if condition="${not empty screen.page}">
			<table>
				<tbody>
					<g:iterator source="${screen.page}" target="organizacao" index="index">
						<tr
						data-value='Gate?MODULE=cliq.modulos.admin&SCREEN=Contato&ACTION=Search&id=${organizacao.id}'>
							<td>
								${organizacao.nome}		
							</td>
						</tr>
					</g:iterator>
				</tbody>
			</table>
		</g:if>
	</form>
</g:template>