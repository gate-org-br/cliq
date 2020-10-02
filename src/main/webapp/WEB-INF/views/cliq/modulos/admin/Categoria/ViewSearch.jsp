<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method='POST' action='Gate?MODULE=cliq.admin.screen&SCREEN=CATEGORIA&ACTION=Search'>
		<g:if condition="${not empty screen.page}">
			<table>
				<tbody>
					<g:iterator source="${screen.page}" target="categoria" index="index" children="${e -> e.children}" depth="depth">
						<tr data-ret='"${categoria.id}", "${categoria.nome}"'>
							<td style='padding-left: ${20 + (depth*60)}px;'>
								<img src='Gate?MODULE=cliq.modulos&SCREEN=Categoria&ACTION=Icon&form.id=${categoria.id}' style='width: 24px; height: 24px;'/>
								<g:print value="${categoria.nome}"/>
							</td>
						</tr>
					</g:iterator>
				</tbody>
			</table>
		</g:if>
	</form>
</g:template>