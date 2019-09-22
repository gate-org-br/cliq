<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>

<g:template filename="/WEB-INF/views/cliq/modulos/atendimento/Main.jsp">
	<form id='form' method='POST' action='#'>
		<fieldset>
			<fieldset data-size="4" style="margin: 0">
				<legend>
					Tipo:
				</legend>
				<label>
					<span>
						<g:select property="form.tipo" required=""/>
					</span>
				</label>
			</fieldset>
			<fieldset data-size="4" style="margin: 0">
				<legend>
					Usuário:
				</legend>
				<label>
					<span>
						<g:hidden id="form.user.id" property="form.user.id" required=''/>
						<g:text id="form.user.name"
							property="form.user.name"
							tabindex='1'
							required=''
							data-getter="form.user"/>
						<g:shortcut id="form.user" module="cliq.modulos"
							    screen="Pessoa" action="Search"
							    arguments="form=@{form.user.name}"
							    data-get="form.user.id, form.user.name"/>
					</span>
				</label>
			</fieldset>

			<fieldset data-size="4" style="margin: 0">
				<legend>
					Período:
				</legend>
				<label style="width: 50%">
					<span>
						<g:text class="Date" property="form.periodo.min"/>
					</span>
				</label>
				<label style="width: 50%">
					<span>
						<g:text class="Date" property="form.periodo.max"/>
					</span>
				</label>
			</fieldset>

			<fieldset data-size="4" style="margin: 0">
				<legend>
					Horário:
				</legend>
				<label style="width: 50%">
					<span>
						<g:text class="Time" property="form.horario.min"/>
					</span>
				</label>
				<label style="width: 50%">
					<span>
						<g:text class="Time" property="form.horario.max"/>
					</span>
				</label>
			</fieldset>

			<fieldset>
				<legend>
					Observações:
				</legend>
				<label>
					<span style="flex-basis: 90px">
						<g:textarea property="form.observacoes"/>
					</span>
				</label>
			</fieldset>
		</fieldset>


		<div class='Coolbar'>
			<g:link class="Action" method="post" module="#" screen="#" action="#" arguments="agrupamento=${screen.agrupamento.name()}" tabindex='1'>
				Pesquisar<g:icon type="search"/>
			</g:link>
		</div>


		<div class='LinkControl'>
			<ul>
				<g:menuitem method="${screen.method}" module="#" screen="#" 
					    data-selected='${empty screen.agrupamento}'>
					Eventos<g:icon type="cliq.entity.Evento"/>
				</g:menuitem>
				<g:values source="cliq.entity.Evento$Agrupamento" target="agrupamento">
					<g:menuitem method="${screen.method}" module="#" screen="#" 
						    arguments="agrupamento=${agrupamento.name()}"
						    data-selected='${screen.agrupamento eq agrupamento}'>
						<g:icon type="${agrupamento}"/>
						<g:name type="${agrupamento}"/>
					</g:menuitem>
				</g:values>
			</ul>
			<div>
				<g:if condition="${not empty screen.page}">
					<g:choose>
						<g:when condition="${empty screen.agrupamento}">
							<table class="c1 c2 c3 c4">
								<caption>
									EVENTOS: ${screen.page.paginator.data.size()}
								</caption>
								<thead>
									<tr>
										<th style="width: 60px">
											#
										</th>
										<th style="width: 160px">
											Tipo
										</th>
										<th style="width: 160px">
											Data
										</th>
										<th style="width: 240px">
											Usuário
										</th>
										<th>
											Descrição
										</th>
									</tr>
								</thead>
								<tbody>
									<g:iterator source="${screen.page}">
										<tr data-target="_dialog" data-action="Gate?MODULE=cliq.modulos&SCREEN=Evento&ACTION=Select&form.id=${target.id}">
											<td>
												<g:icon type="${target.tipo}"/>
											</td>
											<td>
												<g:print value="${target.tipo}"/>
											</td>
											<td>
												<g:print value="${target.data}"/>
											</td>
											<td>
												<g:print value="${target.user.name}"/>
											</td>
											<td>
												<strong><g:print value="${target.descricao}"/></strong>
												<g:print value="${target.observacoes}"/>
											</td>
										</tr>
									</g:iterator>

								</tbody>
								<tfoot>
									<tr>
										<td colspan="5" style="text-align: right">
											<g:paginator/>
										</td>
									</tr>
								</tfoot>
							</table>
						</g:when>
						<g:otherwise>
							<table class="c1 c2">
								<caption>
									CLIQUE SOBRE OS TOTAIS PARA GERAR GRÁFICOS
								</caption>
								<thead>
									<tr>
										<th>
											Quantidade
										</th>
										<th>
											Percentual
										</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="CChart"
										    title='Quantidades por ${screen.agrupamento}'
										    data-series='[["${screen.agrupamento}", "Quantidade"], ${g:print(screen.page.stream().map(e -> "[\"".concat(empty e.agrupamento ? "Indefinido" : g:print(e.agrupamento)).concat("\",").concat(e.quantidade).concat("]")).toList())}]'>
											${screen.page.stream().map(e -> e.quantidade).sum()}
										</td>
										<td class="CChart"
										    title='Percentuais por ${screen.agrupamento}'
										    data-series='[["${screen.agrupamento}", "Percentual"], ${g:print(screen.page.stream().map(e -> "[\"".concat(empty e.agrupamento ? "Indefinido" : g:print(e.agrupamento)).concat("\",").concat(e.percentual.value).concat("]")).toList())}]'>
											100%
										</td>
									</tr>
								</tbody>
							</table>

							<table class="c2 c3" style='margin: 0px;'>
								<caption>
									EVENTOS
								</caption>

								<thead>
									<tr>
										<th style='width: 50%'>
											<g:ordenator method="post" property="this.agrupamento">
												<g:print value="${screen.agrupamento}"/>
											</g:ordenator>
										</th>
										<th style='width: 25%'>
											<g:ordenator method="post" property="this.quantidade">
												Qtde
											</g:ordenator>
										</th>
										<th style='width: 25%'>
											<g:ordenator method="post" property="this.percentual">
												Percentual
											</g:ordenator>
										</th>
									</tr>
								</thead>
								<tbody>
									<g:iterator source="${screen.page}">
										<tr>
											<td>
												<g:print value="${target.agrupamento}" empty="Indefinido"/>
											</td>
											<td>
												<g:print value="${target.quantidade}"/>
											</td>
											<td>
												<g:print value="${target.percentual}"/>%
											</td>
										</tr>
									</g:iterator>
								</tbody>
							</table>
						</g:otherwise>
					</g:choose>
				</g:if>
			</div>
		</div>
	</form>
</g:template>