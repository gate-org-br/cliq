<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<article>
		<section>
			<cliq:pendencia/>
			<form method='POST'>
				<div class='TEXT'>
					<div class='MINIBAR'>
						<hr/>
						<g:shortcut target='_blank' module="#" screen="#" action="Select"
							arguments="form.id=${screen.form.id}" title="Destacar chamado">
							<g:icon type="2188"/>
						</g:shortcut>
						<g:shortcut module="#" screen="#" action="Export" arguments="form.id=${screen.form.id}" target="_report"/>
						<g:shortcut module="#" screen="#" action="Update" arguments="form.id=${screen.form.id}" condition="${screen.user.id eq screen.form.solicitante.id}" />
						<g:shortcut module="#" screen="Checklist" action="Insert" arguments="form.id=${screen.form.id}&checkitem.nome=?{Nome}"/>
					</div>

					<g:if condition="${not empty screen.form.alteracao}">
						<h1>
							Este chamado foi alterado em <g:print value="${screen.form.alteracao}"/>
						</h1>
					</g:if>

					<g:if condition="${not empty screen.form.pessoaAprovadora.id}">
						<h1 style='color: #660000'>
							Este chamado precisa ser aprovado por ${screen.form.pessoaAprovadora.name} para que possa ser executado
						</h1>
					</g:if>
					<g:if condition="${not empty screen.form.equipeAprovadora.id}">
						<h1 style='color: #660000'>
							Este chamado precisa ser aprovado por ${screen.form.equipeAprovadora.name} para que possa ser executado
						</h1>
					</g:if>
					<g:if condition="${not empty screen.form.pessoaHomologadora.id}">
						<h1 style='color: #660000'>
							Este chamado precisa ser homologado por ${screen.form.pessoaHomologadora.name} para que possa ser concluído
						</h1>
					</g:if>
					<g:if condition="${not empty screen.form.equipeHomologadora.id}">
						<h1 style='color: #660000'>
							Este chamado precisa ser homologado por ${screen.form.equipeHomologadora.name} para que possa ser concluído
						</h1>
					</g:if>

					<fieldset>
						<legend>
							<g:if condition="${not empty screen.form.categoria.id}" otherwise='Chamado'>
								<img src='Gate?MODULE=cliq.modulos&SCREEN=Categoria&ACTION=Icon&form.id=${screen.form.categoria.id}'
								     style='width: 20px; height: 20px'/>
								<label>
									${screen.form.categoria.nome}
								</label>
							</g:if>
						</legend>

						<label data-size='2'>
							Número:
							<span>
								<g:icon type="#"/>
								<g:label property="form.id"/>
							</span>
						</label>
						<label data-size='2'>
							Data:
							<span>
								<g:icon type="date"/>
								<g:label property="form.data"/>
							</span>
						</label>
						<label data-size='2'>
							Situação:
							<span>
								<g:icon type="${screen.form.situacao}"/>
								<g:label property="form.situacao"/>
							</span>
						</label>
						<label data-size='2'>
							Pendência:
							<span>
								<g:icon type="${screen.form.pendencia}"/>
								<g:label property="form.pendencia"/>
							</span>
						</label>
						<label data-size='4'>
							Tipo:
							<span>
								<g:icon type="2014"/>
								<g:label property="form.tipo.nome"/>
							</span>
						</label>
						<label data-size='4'>
							Categoria:
							<span>
								<g:label property="form.categoria.nome" empty="N/A"/>
								<g:shortcut condition="${screen.form.localizacao.id eq equipe.id}"
									    module='#' screen='#' action='Categorizar' arguments="form.id=${screen.form.id}"/>
							</span>
						</label>
						<label data-size='4'>
							Origem:
							<span>
								<g:icon type="gate.entity.Role"/>
								<g:label property="form.origem.name" empty="N/A"/>
							</span>
						</label>

						<g:choose>
							<g:when condition="${not empty screen.form.solicitante.id}">
								<label data-size='4'>
									Solicitante:
									<span>
										<g:label property="form.solicitante.name"/>
										<g:shortcut target="_dialog" module="cliq.modulos" screen="Contato" action="Select" arguments="form.tipo=2&form.id=${screen.form.solicitante.id}"/>
										<g:shortcut target="_dialog" module='cliq.modulos' screen='Compartilhamento' arguments="form.chamado.id=${screen.form.id}" title="Compartilhamento"/>
									</span>
								</label>
							</g:when>
							<g:when condition="${not empty screen.form.contato.id}">
								<label data-size='4'>
									Solicitante:
									<span>
										<g:label property="form.contato.nome"/>
										<g:shortcut target="_dialog" module="cliq.modulos" screen='Contato' action='Select' arguments="form.contato.tipo=${screen.form.contato.tipo}&form.id=${screen.form.contato.id}"/>
										<g:shortcut target="_dialog" module='cliq.modulos' screen='Compartilhamento' arguments="form.chamado.id=${screen.form.id}" title="Compartilhamento"/>
									</span>
								</label>
							</g:when>
							<g:otherwise>
								<label data-size='4'>
									Solicitante:
									<span>
										Criado pelo CliQ
									</span>
								</label>
							</g:otherwise>
						</g:choose>
						<label data-size='4'>
							Localização:
							<span>
								<g:label property="form.localizacao.name"/>
								<g:shortcut module="#" screen='#' action="Encaminhar" arguments="form.id=${screen.form.id}"/>
							</span>
						</label>
						<label data-size='4'>
							Atendente:
							<span>
								<g:choose>
									<g:when condition="${screen.form.situacao eq 'PENDENTE' and screen.form.localizacao.id eq equipe.id}">
										<g:secure module='cliq.modulos.admin' screen='Chamado' action='Atribuir' otherwise="${g:print(screen.form.atendente.name)}">
											<g:select property="form.atendente.id" options="${equipe.users}" required='' values="${e -> e.id}"
												  data-method='post' data-action="Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Atribuir"/>
										</g:secure>
									</g:when>
									<g:otherwise>
										<g:icon type="gate.entity.User"/>
										<g:label property="form.atendente.name" empty="Não atribuído"/>
									</g:otherwise>
								</g:choose>
							</span>
						</label>
						<label data-size='2'>
							Prioridade:
							<span>
								<g:if condition="${screen.form.situacao eq 'PENDENTE' and screen.form.localizacao.id eq equipe.id}" otherwise="${g:print(screen.form.prioridade)}">
									<g:secure module='#' screen='#' action='Prioridade' otherwise="${g:print(screen.form.prioridade)}">
										<g:select property="form.prioridade" data-method='post' data-action="Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Prioridade"/>
									</g:secure>
								</g:if>
							</span>
						</label>
						<label data-size='2'>
							Complexidade:
							<span>
								<g:if condition="${screen.form.situacao eq 'PENDENTE' and screen.form.localizacao.id eq equipe.id}"
								      otherwise="${g:print(screen.form.complexidade)}">
									<g:secure module='#' screen='#' action='Complexidade'  otherwise="${g:print(screen.form.complexidade)}">
										<g:select property="form.complexidade" data-method='post' data-action="Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Complexidade" />
									</g:secure>
								</g:if>
							</span>
						</label>
						<label data-size='2'>
							Projeto:
							<span>
								<g:if condition="${screen.form.localizacao.id eq equipe.id}" otherwise="${g:print(screen.form.projeto)}">
									<g:secure module='cliq.modulos.admin' screen='Chamado' action='Projeto' otherwise="${g:print(screen.form.projeto)}">
										<g:select property="form.projeto" data-method='post' data-action="Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Projeto"/>
									</g:secure>
								</g:if>
							</span>
						</label>
						<label data-size='2'>
							Nível:
							<span>
								<g:if condition="${screen.form.situacao eq 'PENDENTE'}" otherwise="${g:print(screen.form.nivel)}">
									<g:secure module="#" screen="#" action="Nivel" otherwise="${g:print(screen.form.nivel)}">
										<g:select property="form.nivel" data-method='post' data-action="Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Nivel"/>
									</g:secure>
								</g:if>
							</span>
						</label>
						<label data-size='2'>
							Sigiloso:
							<span>
								<g:label property="form.sigiloso"/>
							</span>
						</label>
						<label data-size='2'>
							Documentação:
							<span>
								<g:if condition="${screen.form.situacao eq 'CONCLUIDO' and screen.form.localizacao.id eq equipe.id}" otherwise="${g:print(screen.form.documentacao)}">
									<g:secure module='cliq.modulos.admin' screen='Chamado' action='Documentacao' otherwise='${g:print(screen.form.documentacao)}'>
										<g:select property="form.documentacao" data-method='post' data-action="Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Documentacao"/>
									</g:secure>
								</g:if>
							</span>
						</label>
						<label data-size='2'>
							Atendimento:
							<span>
								<g:secure module="#" screen="#" action="Atendimento" otherwise="${g:print(screen.form.atendimento)}">
									<g:select property="form.atendimento" data-method='post' data-action="Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Atendimento"/>
								</g:secure>
							</span>
						</label>
						<g:choose>
							<g:when condition="${not empty screen.form.retomada}">
								<label data-size='2'>
									Retomada:
									<span>
										<g:icon type="date"/>
										<g:label property="form.retomada"/>
									</span>
								</label>
							</g:when>
							<g:otherwise>
								<label data-size='2'>
									Avaliação:
									<span>
										<g:if condition="${not empty screen.form.nota}" otherwise="N/A">
											<g:label property="form.nota"/>
											<g:icon type="${screen.form.nota}"/>
										</g:if>
									</span>
								</label>
							</g:otherwise>
						</g:choose>

						<fieldset data-size="8">
							<legend>
								Resposta<g:icon type="cliq.entity.Evento$Tipo:ATENDIMENTO"/>
							</legend>
							<label data-size='4'>
								Data:
								<span>
									<g:icon type="date"/>
									<g:print value="${screen.form.resposta}" empty="N/A"/>
								</span>
							</label>
							<label data-size='4'>
								Tempo:
								<span>
									<g:icon type="gate.type.Duration"/>
									<g:print value="${screen.form.tempoResposta}" empty="N/A"/>
								</span>
							</label>
							<g:choose>
								<g:when condition="${screen.form.situacao eq 'PENDENTE' and screen.form.localizacao.id eq equipe.id}">
									<label data-size='4'>
										Prazo:
										<span>
											<g:text property="form.prazoResposta" disabled='disabled' style='width: calc(100% - 32px)'/>
											<g:link module="#" screen='#' action="Resposta" arguments="form.id=${screen.form.id}" title='Definir prazo de reposta' style='width: 32px'>
												<g:icon type="2003"/>
											</g:link>
										</span>
									</label>
								</g:when>
								<g:otherwise>
									<label data-size='4'>
										Prazo:
										<span>
											<g:icon type="date"/>
											<g:print value="${screen.form.prazoResposta}" empty="N/A"/>
										</span>
									</label>
								</g:otherwise>
							</g:choose>
							<label data-size='4'>
								Status:
								<span style='color: ${g:color(screen.form.statusResposta)}'>
									<g:icon type="${screen.form.statusResposta}" style='color: ${g:color(screen.form.statusResposta)}'/>
									<g:print value="${screen.form.statusResposta}" empty="N/A"/>
								</span>
							</label>
						</fieldset>
						<fieldset data-size="8">
							<legend>
								Solução<g:icon type="cliq.entity.Evento$Tipo:CONCLUSAO"/>
							</legend>
							<label data-size='4'>
								Data:
								<span>
									<g:icon type="date"/>
									<g:print value="${screen.form.solucao}" empty="N/A"/>
								</span>
							</label>
							<label data-size='4'>
								Tempo:
								<span>
									<g:icon type="gate.type.Duration"/>
									<g:print value="${screen.form.tempoSolucao}" empty="N/A"/>
								</span>
							</label>
							<g:choose>
								<g:when condition="${screen.form.situacao eq 'PENDENTE' and screen.form.localizacao.id eq equipe.id}">
									<label data-size='4'>
										Prazo
										<span>
											<g:text property="form.prazoSolucao" disabled='disabled' style='width: calc(100% - 32px)'/>
											<g:link module="#" screen='#' action="Solucao" arguments="form.id=${screen.form.id}" title='Definir prazo de solução' style='width: 32px'>
												<g:icon type="2003"/>
											</g:link>
										</span>
									</label>
								</g:when>
								<g:otherwise>
									<label data-size='4'>
										Prazo
										<span>
											<g:icon type="date"/>
											<g:print value="${screen.form.prazoSolucao}" empty="N/A"/>
										</span>
									</label>
								</g:otherwise>
							</g:choose>
							<label data-size='4'>
								Status:
								<span style='color: ${g:color(screen.form.statusSolucao)}'>
									<g:icon type="${screen.form.statusSolucao}" style='color: ${g:color(screen.form.statusSolucao)}'/>
									<g:print value="${screen.form.statusSolucao}" empty="N/A"/>
								</span>
							</label>
						</fieldset>

						<cliq:anexo chamado="${screen.form}"/>

						<cliq:checklist checklist="${screen.form.checklist}"/>
					</fieldset>


					<h4 style="margin-top: 40px">
						${screen.form.titulo}
					</h4>

					<g:if condition="${not empty screen.form.descricao}">
						<div style="margin-top: 40px">
							${screen.form.descricao}
						</div>
					</g:if>

					<g:print value="${screen.form.formulario}"/>
				</div>

				<cliq:eventos eventos="${screen.form.eventos}"/>

				<g:hidden property="form.id"/>
				<g:hidden property="form.localizacao.id"/>
			</form>
		</section>
		<footer>
			<g-coolbar>
				<g:link module="#" screen='#' action="ComentarioSimples" arguments="form.id=${screen.form.id}"/>

				<g:if condition="${equipe.contains(screen.form.localizacao)}">
					<g:choose>
						<g:when condition="${screen.form.pendencia eq 'TRIAGEM'}">
							<g:link module="#" screen='#' action="Distribuir"
								arguments="form.id=${screen.form.id}&form.localizacao.id=${screen.form.localizacao.id}"/>
							<g:link module="#" screen='#' action="Importar" arguments="form.id=${screen.form.id}"/>
						</g:when>
						<g:when condition="${screen.form.pendencia eq 'FEEDBACK'}">
							<g:link module="#" screen='#' action="CancelarFeedback" arguments="form.id=${screen.form.id}"/>
						</g:when>
						<g:when condition="${screen.form.pendencia eq 'APROVACAO'}">
							<g:link module="#" screen='#' action="CancelarAprovacao" arguments="form.id=${screen.form.id}"/>
						</g:when>
						<g:when condition="${screen.form.pendencia eq 'HOMOLOGACAO'}">
							<g:link module="#" screen='#' action="CancelarHomologacao" arguments="form.id=${screen.form.id}"/>
						</g:when>
						<g:when condition="${screen.form.pendencia eq 'COMPLEMENTACAO'}">
							<g:link module="#" screen='#' action="CancelarComplementacao" arguments="form.id=${screen.form.id}"/>
						</g:when>
						<g:when condition="${screen.form.situacao eq 'PENDENTE'}">
							<g:choose>
								<g:when condition="${not empty screen.form.categoria.id}">
									<g:link module="#" screen='#' action="Concluir" arguments="form.id=${screen.form.id}"/>
									<g:link module="#" screen='#' action="SolicitarFeedback" arguments="form.id=${screen.form.id}"/>
									<g:link module="#" screen='#' action="SolicitarHomologacao" arguments="form.id=${screen.form.id}"/>
								</g:when>
								<g:otherwise>
									<g:link module="#" screen='#' action="Concluir" arguments="form.id=${screen.form.id}" data-cancel="Selecione uma categoria para o chamado."/>
									<g:link module="#" screen='#' action="SolicitarFeedback" arguments="form.id=${screen.form.id}" data-cancel="Selecione uma categoria para o chamado."/>
									<g:link module="#" screen='#' action="SolicitarHomologacao" arguments="form.id=${screen.form.id}" data-cancel="Selecione uma categoria para o chamado."/>
								</g:otherwise>
							</g:choose>

							<g:link condition="${screen.form.pendencia ne 'COMPLEMENTACAO'}" module="#" screen='#' action="SolicitarComplementacao" arguments="form.id=${screen.form.id}"/>

							<g:link module="#" screen='#' action="Cancelar" arguments="form.id=${screen.form.id}"/>

							<g:link condition="${empty screen.form.resposta and screen.form.atendente.id eq screen.user.id}"
								module="#" screen='#' action="Atender" arguments="form.id=${screen.form.id}"/>

							<g:link module="#" screen='#' action="Atribuir" arguments="form.id=${screen.form.id}"/>

							<g:link condition="${screen.form.atendente.id eq screen.user.id}" module="#" screen='#' action="Liberar" arguments="form.id=${screen.form.id}"/>
							<g:link condition="${screen.form.atendente.id ne screen.user.id}" module="#" screen='#' action="Capturar" arguments="form.id=${screen.form.id}"/>

							<g:link condition="${screen.form.pendencia eq 'SUSPENSO'}" module="#" screen='#' action="Retomar" arguments="form.id=${screen.form.id}"/>
							<g:link condition="${screen.form.pendencia ne 'SUSPENSO'}" module="#" screen='#' action="Suspender" arguments="form.id=${screen.form.id}"/>

							<g:link module="#" screen='#' action="Encaminhar" arguments="form.id=${screen.form.id}"/>
							<g:link module="#" screen='#' action="SolicitarAprovacao" arguments="form.id=${screen.form.id}"/>
						</g:when>
						<g:when condition="${screen.form.situacao eq 'CONCLUIDO'}">
							<g:link module="#" screen='#' action="Reabrir" arguments="form.id=${screen.form.id}"/>
							<g:link condition="${empty screen.form.nota and screen.form.pendencia eq 'NENHUMA'}" module="#" screen='#'
								action="Avaliacao" arguments="form.id=${screen.form.id}"/>
						</g:when>
						<g:when condition="${screen.form.situacao eq 'CANCELADO'}">
							<g:link module="#" screen='#' action="Reabrir" arguments="form.id=${screen.form.id}"/>
						</g:when>
					</g:choose>
				</g:if>
				<hr/>
				<a href='#' class="Cancel Hide">
					Fechar<g:icon type="cancel"/>
				</a>
			</g-coolbar>
		</footer>

		<script>
			window.addEventListener("load",
				() => Array.from(document.querySelectorAll("a[target='_dialog'], tr[data-target='_dialog']"))
					.forEach(element => element.addEventListener("hide", () => window.location = "Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Select&form.id=${screen.form.id}")));
		</script>
	</article>
</g:template>