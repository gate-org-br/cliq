<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method='POST' action='#'>
		<table>
			<caption>
				EQUIPES
			</caption>

			<thead>
				<tr>
					<th>
						EQUIPE	
					</th>
				</tr>
			</thead>

			<tbody>
				<g:iterator source="${screen.user.role.root}" children="${e -> e.children}">
					<tr data-ret='${target.id}, ${target.name}'>
						<td style='padding-left: ${depth*40}px;'>
							${target.name}		
						</td>
					</tr>
				</g:iterator>
			</tbody>
		</table>
	</form>
</g:template>