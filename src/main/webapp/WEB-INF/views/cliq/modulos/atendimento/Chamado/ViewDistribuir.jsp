<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method='POST' action='#'>
		<g:hidden property="form"/>

		<table>
			<col style='width: 100%'/>

			<caption>
				SELECIONE O SETOR PARA O QUAL DESEJA DISTRIBUIR O CHAMADO
			</caption>

			<thead></thead>
			<tfoot></tfoot>

			<tbody>
				<g:iterator source="${screen.form.localizacao}" 
					    target="role" index="index" children="${e -> e.children}" depth="depth">
					<tr data-method='post' data-action='#&form.localizacao.id=${role.id}'>
						<td style='text-align: left; padding-left: ${40 * depth}px;'>
							${role.name}
							<g:if condition="${not empty role.roleID}">
								<span style='color: gray;'>
									(<g:print value="${role.roleID}"/>)
								</span>
							</g:if>									
						</td>
					</tr>
				</g:iterator>
			</tbody>
		</table>
	</form>
</g:template>