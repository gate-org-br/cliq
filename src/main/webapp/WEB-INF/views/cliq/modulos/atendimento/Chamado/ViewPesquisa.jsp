<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>

<g:template filename="/WEB-INF/views/cliq/modulos/atendimento/Main.jsp">
	<form id='form' method='POST' action='#'>
		<fieldset>
			<label data-size="2">
				N&ordm;:
				<span>
					<g:text property="form.id" required='' tabindex='1'/>
				</span>
			</label>
			<label data-size="2">
				Projeto:
				<span>
					<g:select property="form.projeto" required='' tabindex='1'/>
				</span>
			</label>
			<label data-size="2">
				Situação:
				<span>
					<g:select property="form.situacao" required='' tabindex='1'/>
				</span>
			</label>
			<label data-size="2">
				Pendência:
				<span>
					<g:select property="form.pendencia" required='' tabindex='1'/>
				</span>
			</label>
			<label data-size="8">
				Categoria:
				<span>
					<g:select property="form.classificado" required='' tabindex='1' style="flex-basis: 64px"/>
					<g:select property="form.categoria.id" options="${equipe.categorias}" 
						  values="${e -> e.id}" required='' tabindex='1' style="flex-basis: calc(100% - 64px)"/>
				</span>
			</label>
			<label data-size="4">
				Origem:
				<span>
					<g:hidden id="form.origem.id" property="form.origem.id" required=''/>
					<g:text id="form.origem.name" property="form.origem.name" tabindex='1'
						required='' data-getter="form.origem" style="flex-basis: calc(100% - 64px)"/>
					<g:shortcut id="form.origem" module="cliq.modulos"
						    screen="Equipe" action="Search"
						    arguments="form.name=@{form.origem.name}"
						    data-get="form.origem.id, form.origem.name" style="flex-basis: 32px"/>
					<g:checkbox value="true" property="form.origem.recursive" tabindex='1' style="flex-basis: 16px"/>
				</span>
			</label>
			<label data-size="4">
				Solicitante:
				<span>
					<g:hidden id="form.solicitante.id" property="form.solicitante.id" required=''/>
					<g:select property="form.agendado" required='' tabindex='1'  style="flex-basis: 60px; flex-shrink: 0"/>
					<g:text id="form.solicitante.name"
						property="form.solicitante.name"
						tabindex='1'
						required=''
						data-getter="form.solicitante"
						style="flex-basis: calc(100% - 92px)"/>
					<g:shortcut id="form.solicitante" module="cliq.modulos"
						    screen="Pessoa" action="Search"
						    arguments="form=@{form.solicitante.name}"
						    data-get="form.solicitante.id, form.solicitante.name"  style="flex-basis: 32px"/>
				</span>
			</label>
			<label data-size="4">
				Localização:
				<span>
					<g:hidden id="form.localizacao.id" property="form.localizacao.id" required=''/>
					<g:text id="form.localizacao.name" property="form.localizacao.name" tabindex='1'
						required='' data-getter="form.localizacao" style="flex-basis: calc(100% - 64px)"/>
					<g:shortcut id="form.localizacao" module="cliq.modulos"
						    screen="Equipe" action="Search"
						    arguments="form.name=@{form.localizacao.name}"
						    data-get="form.localizacao.id, form.localizacao.name" style="flex-basis: 32px"/>
					<g:checkbox value="true" property="form.localizacao.recursive" tabindex='1' style="flex-basis: 16px"/>
				</span>
			</label>
			<label data-size="4">
				Atendente:
				<span>
					<g:hidden id="form.atendente.id" property="form.atendente.id" required=''/>
					<g:select property="form.capturado" required='' tabindex='1'  style="flex-basis: 64px; flex-shrink: 0"/>
					<g:text id="form.atendente.name" property="form.atendente.name" tabindex='1'
						required='' data-getter="form.atendente"  style="flex-basis: calc(100% - 92px)"/>
					<g:shortcut id="form.atendente" module="cliq.modulos"
						    screen="Pessoa" action="Search" style="flex-basis: 32px"
						    arguments="form=@{form.atendente.name}"
						    data-get="form.atendente.id, form.atendente.name"/>
				</span>
			</label>
			<label data-size="2">
				Prioridade:
				<span>
					<g:select property="form.prioridade" required='' tabindex='1' style='font-size: 10px;'/>
				</span>
			</label>
			<label data-size="2">
				Complexidade:
				<span>
					<g:select property="form.complexidade" required='' tabindex='1' style='font-size: 10px;'/>
				</span>
			</label>
			<label data-size="2">
				Documentação:
				<span>
					<g:select property="form.documentacao" required='' tabindex='1'/>
				</span>
			</label>
			<label data-size="2">
				Sigiloso:
				<span>
					<g:select property="form.sigiloso" required='' tabindex='1'/>
				</span>
			</label>
			<label data-size="2">
				Atrasado:
				<span>
					<g:select property="form.atrasado" required='' tabindex='1'/>
				</span>
			</label>
			<label data-size="2">
				Nível:
				<span>
					<g:select property="form.nivel" required='' tabindex='1'/>
				</span>
			</label>
			<label data-size="4">
				Avaliação:
				<span>
					<g:select property="form.avaliado" required='' tabindex='1'  style="flex-basis: 64px"/>
					<g:select property="form.nota" required='' tabindex='1' style="flex-basis: calc(100% - 64px)"/>
				</span>
			</label>
			<fieldset data-size="8">
				<label data-size="8">
					Evento:
					<span>
						<g:select property="form.evento.tipo" required=""/>	
					</span>
				</label>
				<label data-size="4">
					Data Min:
					<span>
						<g:icon type="date"/>
						<g:text property="form.evento.periodo.min"/>	
					</span>
				</label>
				<label data-size="4">
					Data Max:
					<span>
						<g:icon type="date"/>
						<g:text property="form.evento.periodo.max"/>	
					</span>
				</label>
				<label>
					Usuário:
					<span>
						<g:hidden id="form.evento.user.id" property="form.evento.user.id" required=''/>
						<g:text id="form.evento.user.name" property="form.evento.user.name" tabindex='1'
							required='' data-getter="form.evento.user"  style="flex-basis: calc(100% - 92px)"/>
						<g:shortcut id="form.solicitante" module="cliq.modulos"
							    screen="Pessoa" action="Search"
							    arguments="form=@{form.evento.user.name}"
							    data-get="form.evento.user.id, form.evento.user.name"  style="flex-basis: 32px"/>	
					</span>
				</label>
			</fieldset>
			<fieldset data-size="8">
				<label>
					Pesquisa avançada:
					<span style="flex-basis: 86px">
						<g:textarea property="form.titulo" required='' tabindex='1'/>
					</span>
				</label>
			</fieldset>
		</fieldset>

		<g-coolbar>
			<g:link class="Action" method="post" module="#" screen="#" action="#"
				arguments="agrupamento=${empty screen.agrupamento ? '' : screen.agrupamento.ordinal()}" tabindex='1'>
				Pesquisar<g:icon type="search"/>
			</g:link>

			<g:link method="post" target="_report" module="#" screen="#" action="Report"
				arguments="agrupamento=${empty screen.agrupamento ? '' : screen.agrupamento.ordinal()}&orderBy=${screen.orderBy}" tabindex='1'/>

			<g:link target='_dialog' module='#' screen='#' action='Insert' tabindex='1'/>
		</g-coolbar>

		<div class='LinkControl'>
			<ul>
				<g:menuitem method="post" module="#" screen="#" 
					    data-selected='${empty screen.agrupamento}'>
					Chamados<g:icon type="cliq.entity.Chamado"/>
				</g:menuitem>
				<g:values source="cliq.entity.Chamado$Agrupamento" target="agrupamento">
					<g:menuitem method="post" module="#" screen="#" 
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
							<div style="overflow-x: scroll">
								<table class="c2 c3 c4 c5 c6 c7 c8 c9 c10" style="min-width: 1246px">
									<caption>
										${screen.page.paginator.dataSize} CHAMADOS ENCONTRADOS
									</caption>

									<thead>
										<tr>
											<th>
												<g:ordenator method='post' property="titulo">
													Título
												</g:ordenator>
											</th>
											<th style="width: 120px">
												<g:ordenator method='post' property="id">
													N&ordm;
												</g:ordenator>
											</th>
											<th style="width: 120px">
												<g:ordenator method='post' property="categoria.nome">
													Tipo
												</g:ordenator>
											</th>
											<th style="width: 120px">
												<g:ordenator method='post' property="data">
													Data
												</g:ordenator>
											</th>
											<th style="width: 120px">
												<g:ordenator method='post' property="situacao">
													Situacao
												</g:ordenator>
											</th>
											<th style="width: 120px">
												<g:ordenator method='post' property="prioridade">
													Prioridade
												</g:ordenator>
											</th>
											<th style="width: 120px">
												<g:ordenator method='post' property="origem.name">
													Origem
												</g:ordenator>
											</th>
											<th style="width: 120px">
												<g:ordenator method='post' property="solicitante.name">
													Solicitante
												</g:ordenator>
											</th>
											<th style="width: 120px">
												<g:ordenator method='post' property="atendente.name">
													Atendente
												</g:ordenator>
											</th>
											<th style="width: 50px">
												#
											</th>
										</tr>
									</thead>

									<tfoot>
										<tr>
											<td colspan='10' style='text-align: right'>
												<g:paginator/>
											</td>
										</tr>
									</tfoot>

									<tbody>
										<g:iterator source="${screen.page}" target="chamado" index="index">
											<tr tabindex='1'
											    title='Chamado'
											    data-target="_dialog"
											    data-action='Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Select&form.id=${chamado.id}'>
												<td>
													${chamado.titulo}
												</td>
												<td>
													<g:print value="${chamado.id}"/>
												</td>
												<td>
													<g:print value="${chamado.categoria.nome}"/>
												</td>
												<td>
													<cliq:data chamado="${chamado}"/>
												</td>
												<td style="color: ${g:color(chamado.situacao)}">
													<g:print value="${chamado.situacao}"/>
												</td>
												<td>
													<cliq:prioridade chamado="${chamado}"/>
												</td>
												<td>
													<cliq:origem chamado="${chamado}"/>
												</td>
												<td>
													<cliq:solicitante chamado="${chamado}"/>
												</td>
												<td>
													<cliq:atendente chamado="${chamado}"/>

												</td>
												<td>
													<g:choose>
														<g:when condition="${chamado.notificado}">
															<i style='font-size: 18px; color: #432F21;'>&#x2224;</i>
														</g:when>
														<g:otherwise>
															<i style='font-size: 18px; color: #660000;' data-switch='&#x2225;'>&#x2224;</i>
														</g:otherwise>
													</g:choose>
												</td>
											</tr>
										</g:iterator>
									</tbody>
								</table>
							</div>
						</g:when>
						<g:otherwise>
							<table class="c1 c2 c3 c4">
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
										<th>
											Tempo Médio de Resposta
										</th>
										<th>
											Tempo Médio de Solução
										</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="CChart"
										    title='Quantidades por ${screen.agrupamento}'
										    data-series='[["${screen.agrupamento}", "Quantidade"], ${g:print(screen.page.stream().map(e -> "[\"".concat(empty e.agrupamento ? "Indefinido" : e.agrupamento).concat("\",").concat(e.quantidade).concat("]")).toList())}]'>
											${screen.page.stream().map(e -> e.quantidade).sum()}
										</td>
										<td class="CChart"
										    title='Percentuais por ${screen.agrupamento}'
										    data-series='[["${screen.agrupamento}", "Percentual"], ${g:print(screen.page.stream().map(e -> "[\"".concat(empty e.agrupamento ? "Indefinido" : e.agrupamento).concat("\",").concat(e.percentual.value).concat("]")).toList())}]'>
											100%
										</td>
										<td class="CChart"
										    title='Tempos de Resposta por ${screen.agrupamento}'
										    data-series='[["${screen.agrupamento}", "Tempos de Resposta"], ${g:print(screen.page.stream().map(e -> "[\"".concat(empty e.agrupamento ? "Indefinido" : e.agrupamento).concat("\",").concat(e.resposta.asM()).concat("]")).toList())}]'>
											${screen.page.stream().map(e -> e.resposta).reduce((a, b) -> a.add(b)).get().divide(screen.page.size())}
										</td>
										<td class="CChart"
										    title='Tempos de Solução por ${screen.agrupamento}'
										    data-series='[["${screen.agrupamento}", "Tempos de Solução"], ${g:print(screen.page.stream().map(e -> "[\"".concat(empty e.agrupamento ? "Indefinido" : e.agrupamento).concat("\",").concat(e.solucao.asM()).concat("]")).toList())}]'>
											${screen.page.stream().map(e -> e.solucao).reduce((a, b) -> a.add(b)).get().divide(screen.page.size())}

										</td>
									</tr>
								</tbody>
							</table>

							<table class="c2 c3 c4 c5" style='margin: 0px;'>
								<caption>
									CHAMADOS
								</caption>

								<thead>
									<tr>
										<th style='width: 50%'>
											<g:ordenator method="post" property="agrupamento">
												<g:print value="${screen.agrupamento}"/>
											</g:ordenator>
										</th>
										<th style='width: 12.5%'>
											<g:ordenator method="post" property="quantidade">
												Qtde
											</g:ordenator>
										</th>
										<th style='width: 12.5%'>
											<g:ordenator method="post" property="percentual">
												Percentual
											</g:ordenator>
										</th>
										<th style='width: 12.5%'>
											<g:ordenator method="post" property="resposta">
												Tempo Médio de Resposta
											</g:ordenator>
										</th>
										<th style='width: 12.5%'>
											<g:ordenator method="post" property="solucao">
												Tempo Médio de Solução
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
											<td>
												<g:print value="${target.resposta}"/>
											</td>
											<td>
												<g:print value="${target.solucao}"/>
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
	<g:if condition="${screen.POST and empty screen.page}">
		<script>
			alert('Nenhum chamado encontrado.');
		</script>
	</g:if>
</g:template>