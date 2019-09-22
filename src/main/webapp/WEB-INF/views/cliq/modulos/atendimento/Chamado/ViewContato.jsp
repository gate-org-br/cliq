<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form action="#" method="POST">
		<fieldset>
			<legend>
				Selecione o Solicitante<g:icon type="search"/>
			</legend>
			<label>
				Nome:
				<span>
					<g:text property="form.contato.nome" tabindex='2' required=''/>
				</span>
			</label>
		</fieldset>
		<g:choose>
			<div class='Coolbar'>
				<a class="Hide" href='#' tabindex='3'
				   style='float: left; color: #660000'>
					Desistir<g:icon type='cancel'/>
				</a>
				<g:link method='post' module='#' screen='#' action="#"
					tabindex='4' >
					Pesquisar<g:icon type="search"/>
				</g:link>
			</div>

			<g:when condition="${screen.GET}">
				<div class='TEXT'>
					<h4>
						Entre com parte do nome do solicitante e clique em Pesquisar
					</h4>
				</div>
			</g:when>
			<g:when condition="${empty screen.contatos}">
				<div class='TEXT'>
					<h4>
						Nenhum contato encontrado
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
						CONTATOS ENCONTRADOS: ${screen.contatos.size()}
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
					</tfoot>

					<tbody>
						<g:iterator source="${screen.contatos}" target="contato" index="index">
							<tr title='${contato.nome}' tabindex="1"
							    data-action='Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Insert&form.contato.tipo=${contato.tipo.ordinal()}&form.contato.id=${contato.id}&form.contato.nome=${contato.nome}'>
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
			</g:otherwise>
		</g:choose>
	</form>
</g:template>