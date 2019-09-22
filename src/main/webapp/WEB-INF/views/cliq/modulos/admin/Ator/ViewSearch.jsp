<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method='POST' action='#'>
		<fieldset>
			<label>
				Pesquisar:
				<span>
					<g:text property='form' tabindex='1'/>
				</span>
			</label>
		</fieldset>

		<div class='Coolbar'>
			<g:link method="post" module="#" screen="#" action="Search" tabindex='2'>
				Pesquisar<g:icon type="search"/>
			</g:link>
		</div>

		<g:if condition="${not empty screen.page}">
			<table class="c1 c3 c4">
				<caption>
					ATORES ENCONTRADOS: ${screen.page.size()}
				</caption>

				<thead>
					<tr>
						<th  style='width: 64px'>
							Tipo
						</th>
						<th>
							Nome
						</th>
						<th  style='width: 128px'>
							Sigla
						</th>
						<th  style='width: 256px'>
							E-Mail
						</th>
					</tr>
				</thead>
				<tbody>
					<g:iterator source="${screen.page}">
						<g:choose>
							<g:when condition="${target.tipo eq 'PESSOA'}">
								<tr data-ret='${target.tipo},${target.id},,${target.nome}'>
									<td>
										<g:icon type="gate.entity.User"/>
									</td>
									<td>
										${target.nome}
									</td>
									<td>
										${target.sigla}
									</td>
									<td>
										${target.email}
									</td>
								</tr>
							</g:when>
							<g:when condition="${target.tipo eq 'EQUIPE'}">
								<tr data-ret='${target.tipo},,${target.id},${target.nome}'>
									<td>
										<g:icon type="gate.entity.Role"/>
									</td>
									<td>
										${target.nome}
									</td>
									<td>
										${target.sigla}
									</td>
									<td>
										${target.email}
									</td>
								</tr>
							</g:when>
						</g:choose>
					</g:iterator>
				</tbody>
			</table>
		</g:if>
	</form>

	<g:if condition="${screen.page.size() eq 1}">
		<script>
			window.addEventListener("load", () => document.querySelectorAll("tr[data-ret]")[0].click());
		</script>
	</g:if>
</g:template>