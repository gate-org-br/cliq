<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method='POST' action='#'>
		<fieldset>
			<label>
				Pesquisar:
				<span>
					<g:text property='form.name' tabindex='1'/>
				</span>
			</label>
		</fieldset>

		<div class='Coolbar'>
			<g:link class="Commit" method="post" module="#" screen="#" action="#" tabindex='2'>
				Pesquisar<g:icon type="search"/>
			</g:link>
		</div>

		<g:choose>
			<g:when condition="${not empty screen.page}">
				<table class="c1 c3 c4">
					<caption>
						EQUIPES ENCONTRADAS: ${screen.page.size()}
					</caption>

					<thead>
						<tr>
							<th  style='width: 64px'>
								#
							</th>
							<th>
								Nome
							</th>
							<th  style='width: 128px'>
								Login
							</th>
							<th  style='width: 256px'>
								E-Mail
							</th>
						</tr>
					</thead>
					<tbody>
						<g:iterator source="${screen.page}">
							<tr data-ret='${target.id},${target.name},${target.roleID},${target.email}'>
								<td>
									<g:icon type="gate.entity.User"/>
								</td>
								<td>
									${target.name}
								</td>
								<td>
									${target.roleID}
								</td>
								<td>
									${target.email}
								</td>
							</tr>
						</g:iterator>
					</tbody>
				</table>
			</g:when>
			<g:otherwise>
				<div class="TEXT">
					<h1>
						Entre com o no mínimo 3 caracteres e clique em pesquisar
					</h1>
				</div> 
			</g:otherwise>
		</g:choose>

	</form>

	<g:if condition="${screen.page.size() eq 1}">
		<script>
			window.addEventListener("load", () => document.querySelectorAll("tr[data-ret]")[0].click());
		</script>
	</g:if>
</g:template>