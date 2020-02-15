<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/cliq/modulos/atendimento/Main.jsp">
	<form action="#" method="POST">
		<fieldset>
			<legend>
				Pesquisar Contatos<g:icon type="search"/>
			</legend>
			<label>
				Nome:
				<span>
					<g:text property="form.nome" tabindex='1' required=''/>
				</span>
			</label>
		</fieldset>
		<div class='Coolbar'>
			<g:link method="post" module='#' screen='#' >
				Pesquisar<g:icon type="search"/>
			</g:link>
			<g:link module='#' screen='#' action='Insert'>
				Inserir<g:icon type="insert"/>
			</g:link>
		</div>

		<g:choose>
			<g:when condition="${screen.GET}">
				<div class='TEXT'>
					<h4>
						Entre com os critérios de busca e cique em Pesquisar
					</h4>
				</div>
			</g:when>
			<g:when condition="${empty screen.page}">
				<div class='TEXT'>
					<h4>
						Nenhum registro encontrado para os critérios de busca selecionados
					</h4>
				</div>
			</g:when>
			<g:otherwise>
				<table style="table-layout: fixed">
					<col style='width: 50px'/>
					<col/>
					<col style='width: 150px'/>
					<col style='width: 150px'/>
					<col style='width: 300px'/>

					<caption>
						CONTATOS ENCONTRADOS: ${screen.page.paginator.dataSize}
					</caption>

					<thead>
						<tr>
							<th style='text-align: center'>
								Tipo
							</th>
							<th style='text-align: left'>
								Nome
							</th>
							<th style='text-align: center'>
								Telefone
							</th>
							<th style='text-align: center'>
								Celular
							</th>
							<th style='text-align: center'>
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
							<tr
								title='${contato.nome}'
								data-action='Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Select&form.tipo=${contato.tipo.ordinal()}&form.id=${contato.id}'>
								<td style='text-align: center'><g:icon type="${contato.tipo}"/></td>
								<td style='text-align: left'>${contato.nome}</td>
								<g:choose>
									<g:when condition="${not empty contato.telefone}">
										<td style='text-align: center'>
											<g:print value='${contato.telefone}'/>
										</td>
									</g:when>
									<g:otherwise>
										<td style='text-align: center; color: #CCCCCC; font-size: 10px'>
											Não Especificado
										</td>
									</g:otherwise>
								</g:choose>
								<g:choose>
									<g:when condition="${not empty contato.celular}">
										<td style='text-align: center'>
											<g:print value='${contato.celular}'/>
										</td>
									</g:when>
									<g:otherwise>
										<td style='text-align: center; color: #CCCCCC; font-size: 10px'>
											Não Especificado
										</td>
									</g:otherwise>
								</g:choose>
								<g:choose>
									<g:when condition="${not empty contato.email}">
										<td style='text-align: center'>${contato.email}</td>
									</g:when>
									<g:otherwise>
										<td style='text-align: center; color: #CCCCCC; font-size: 10px'>
											Não Especificado
										</td>
									</g:otherwise>
								</g:choose>
							</tr>
						</g:iterator>
					</tbody>
				</table>

				<div class='Coolbar'>
					<g:link module='#' screen='#' action='Insert'>
						Inserir<g:icon type="insert"/>
					</g:link>
				</div>
			</g:otherwise>
		</g:choose>
	</form>
</g:template>
