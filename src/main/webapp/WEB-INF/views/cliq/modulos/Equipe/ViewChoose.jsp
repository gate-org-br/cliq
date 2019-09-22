<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<form method='POST' action='#'>
		<table>
			<caption>
				ALTERAR EQUIPE
			</caption>

			<tbody>
				<g:iterator source="${screen.user.role}" children="${e -> e.children}">
					<tr data-method='post'
					    data-action='Gate?MODULE=cliq.modulos&SCREEN=Equipe&ACTION=Choose&form.id=${target.id}'>
						<td style='padding-left: ${depth*40}px;'>
							${target.name}
							<g:if condition="${not empty target.roleID}">
								<span style='color: gray;'>
									(<g:print value="${target.roleID}"/>)
								</span>
							</g:if>
						</td>
					</tr>
				</g:iterator>
			</tbody>
		</table>
	</form>
</g:template>