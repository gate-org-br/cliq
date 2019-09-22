<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">

	<cliq:pendencia/>

	<form method='POST' action='#'>
		<div class='TEXT'>
			<div class='MINIBAR'>
				<hr/>
				<g:shortcut condition="${screen.user.id eq screen.form.solicitante.id}" module="#" screen="#" action="Update" arguments="form.id=${screen.form.id}"/>
				<g:shortcut target="_report-dialog" module="#" screen="#" action="Export" arguments="form.id=${screen.form.id}"/>
			</div>
			<fieldset>
				<legend style='padding: 4px'>
					<g:if condition="${not empty screen.form.tipo.id}" otherwise='Chamado'>
						<img src='Gate?MODULE=cliq.modulos&SCREEN=Categoria&ACTION=Icon&form.id=${screen.form.tipo.id}'/>
						<label>${screen.form.tipo.nome}</label>
					</g:if>
				</legend>

				<label data-size='2'>
					N&ordm;:
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
						<g:label property="form.tipo.nome" empty="N/A"/>
					</span>
				</label>
				<label data-size='4'>
					Sigiloso:
					<span>
						<g:secure module='#' screen='Chamado' action='Sigilo' otherwise="${g:print(screen.form.sigiloso)}">
							<g:select property="form.sigiloso"
								  data-method='post' data-action="Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Sigilo"/>
						</g:secure>
					</span>
				</label>
				<g:choose>
					<g:when condition="${not empty screen.form.solicitante.id}">
						<label data-size='4'>
							Origem:
							<span>
								<g:icon type="gate.entity.Role"/>
								<g:label property="form.origem.name"/>
							</span>
						</label>
						<label data-size='4'>
							Solicitante:
							<span>
								<g:label property="form.solicitante.name"/>
								<g:shortcut target="_dialog" module="cliq.modulos" screen="Contato" action="Select"
									    arguments="form.tipo=2&form.id=${screen.form.solicitante.id}"
									    title="Solicitante"/>
								<g:shortcut target="_dialog" module='cliq.modulos' screen='Compartilhamento' arguments="form.chamado.id=${screen.form.id}" title='Compartilhamentos'/>
							</span>
						</label>
					</g:when>
					<g:otherwise>
						<label data-size='8'>
							Solicitante:
							<span>
								<label>
									Criado pelo CliQ
								</label>
								<g:shortcut target="_dialog" module='cliq.modulos' screen='Compartilhamento' arguments="form.chamado.id=${screen.form.id}" title='Compartilhamentos'/>
							</span>
						</label>
					</g:otherwise>
				</g:choose>
				<label data-size='4'>
					Setor Responsável:
					<span>
						<g:icon type="gate.entity.Role"/>
						<g:label property="form.localizacao.name"/>
					</span>
				</label>
				<label data-size='4'>
					Atendente Responsável:
					<span>
						<g:label property="form.atendente.name" empty="Não Atribuído"/>
						<g:shortcut condition="${not empty screen.form.atendente.id}"
							    target="_dialog" module="cliq.modulos" screen="Contato" action="Select"
							    arguments="form.tipo=2&form.id=${screen.form.atendente.id}"/>
					</span>
				</label>

				<g:if condition="${not empty screen.form.anexo.id}">
					<fieldset>
						<legend>
							Anexos
						</legend>
						<label>
							<span>
								<label>
									Clique para baixar o anexo do chamado
								</label>
								<g:shortcut  module="cliq.modulos" screen="Anexo" 
									     arguments="form.id=${screen.form.anexo.id}"/>
							</span>
						</label>
					</fieldset>
				</g:if>
			</fieldset>

			<h4 style="margin-top: 40px">
				${screen.form.titulo}
			</h4>

			<g:print value="${screen.form.formulario}"/>

			<g:if condition="${not empty screen.form.descricao}">
				<div style="margin-top: 40px">
					${screen.form.descricao}
				</div>
			</g:if>
		</div>

		<div class='Coolbar'>
			<a class='Cancel Hide' href='#' tabindex='1'>
				Fechar<g:icon type='cancel'/>
			</a>
			<g:link condition="${screen.form.situacao eq 'PENDENTE'}"
				module="#" screen="#" action="ComentarioSimples" arguments="form.id=${screen.form.id}"/>
			<g:link condition="${screen.form.situacao eq 'PENDENTE'}"
				module="#" screen="#" action="Cancelar" arguments="form.id=${screen.form.id}"/>
			<g:link condition="${screen.form.situacao ne 'PENDENTE'}"
				module="#" screen="#" action="Reabrir" arguments="form.id=${screen.form.id}"/>
		</div>

		<cliq:eventos eventos="${screen.form.eventos.stream()
					 .filter(e -> e.tipo.publico).toList()}"/>

		<g:hidden property="form.id"/>
	</form>

	<script>
		window.addEventListener("load",
			() => Array.from(document.querySelectorAll("a[target='_dialog'], tr[data-target='_dialog']"))
				.forEach(element => element.addEventListener("hide", () => window.location = "Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Select&form.id=${screen.form.id}")));
	</script>
</g:template>
