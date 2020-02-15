<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form action="#" method="POST">
		<fieldset>
			<legend>
				Pesquisar<g:icon type="search"/>
			</legend>
			<label>
				Nome:
				<span>
					<g:text property="form.nome" tabindex='2' pattern="^.{3,}$"
						title="Entre com no mínimo 3 caracteres"/>
				</span>
			</label>
		</fieldset>
		<g:choose>
			<div class='Coolbar'>
				<a class="Cancel Hide" href='#' tabindex='3'> 
					Desistir<g:icon type='cancel'/>
				</a>
				<g:link class="Commit" method='post' 
					module='#' screen='#' action="#" tabindex='4'/>
			</div>

			<g:when condition="${empty screen.page}">
				<div class='TEXT'>
					<h4>
						Nenhum contato encontrado
					</h4>
				</div>
			</g:when>
			<g:otherwise>
				<table class="c1 c3 c4 c5">
					<caption>
						CONTATOS ENCONTRADOS: ${screen.page.paginator.dataSize}
					</caption>

					<thead>
						<tr>
							<th style='width: 50px'>
								Tipo
							</th>
							<th>
								Nome
							</th>
							<th style='width: 150px'>
								Telefone
							</th>
							<th style='width: 150px'>
								Celular
							</th>
							<th style='width: 300px'>
								E-Mail
							</th>
						</tr>
					</thead>

					<tfoot>
						<tr>
							<td colspan='5' style='text-align: right'>
								<g:paginator/>
							</td>
						</tr>
					</tfoot>

					<tbody>
						<g:iterator source="${screen.page}" target="contato" index="index">
							<tr title='${contato.nome}'
							    tabindex="1"
							    data-ret='${contato.id}, ${contato.tipo.ordinal()}, ${contato.nome}'>
								<td><g:icon type="${contato.tipo}"/></td>
								<td>${contato.nome}</td>
								<g:choose>
									<g:when condition="${not empty contato.telefone}">
										<td>
											<g:print value='${contato.telefone}'/>
										</td>
									</g:when>
									<g:otherwise>
										<td style='color: #CCCCCC; font-size: 10px'>
											Não Especificado
										</td>
									</g:otherwise>
								</g:choose>
								<g:choose>
									<g:when condition="${not empty contato.celular}">
										<td>
											<g:print value='${contato.celular}'/>
										</td>
									</g:when>
									<g:otherwise>
										<td style='color: #CCCCCC; font-size: 10px'>
											Não Especificado
										</td>
									</g:otherwise>
								</g:choose>
								<g:choose>
									<g:when condition="${not empty contato.email}">
										<td>${contato.email}</td>
									</g:when>
									<g:otherwise>
										<td style='color: #CCCCCC; font-size: 10px'>
											Não Especificado
										</td>
									</g:otherwise>
								</g:choose>
							</tr>
						</g:iterator>
					</tbody>
				</table>
			</g:otherwise>
		</g:choose>
	</form>

	<g:if condition="${screen.page.paginator.data.size() eq 1}">
		<script>
			window.addEventListener("load", () => document.querySelectorAll("tr[data-ret]")[0].click());
		</script>
	</g:if>
</g:template>