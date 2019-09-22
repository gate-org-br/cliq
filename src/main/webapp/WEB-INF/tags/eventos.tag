<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<%@ attribute name="eventos" required="true" type="java.util.List"%>

<fieldset>
	<table class="Eventos" data-collapse="1000px">
		<caption>
			EVENTOS
		</caption>

		<thead>
			<tr>
				<th>Tipo</th>
				<th>Data</th>
				<th>Usuário</th>
				<th>Descrição</th>
			</tr>
		</thead>

		<tbody>
			<g:iterator source="${eventos}" target="evento" index="index">
				<tr data-target="_dialog" title="Evento"
				    data-action="Gate?MODULE=cliq.modulos&SCREEN=Evento&ACTION=Select&form.id=${evento.id}">
					<td title='${g:print(evento.tipo)}'>
						<g:icon style='font-size: 20px; color: #444466;' type="${evento.tipo}"/>
					</td>
					<td title='Data'>
						<g:choose>
							<g:when condition="${not empty evento.alteracao}">
								<g:print value="${evento.alteracao}"/>
								<span style="text-decoration: line-through">
									<g:print value="${evento.data}"/>
								</span>
							</g:when>
							<g:otherwise>
								<g:print value="${evento.data}"/>
							</g:otherwise>
						</g:choose>
					</td>
					<g:choose>
						<g:when condition="${not empty evento.user.id}">
							<td title='Usuário'>
								<g:link target="_dialog" module="cliq.modulos" screen="Contato" action="Select" title="Usuário"
									arguments="form.tipo=2&form.id=${evento.user.id}" otherwise="${evento.user.name}">
									${evento.user.name}
								</g:link>
							</td>
						</g:when>
						<g:otherwise>
							<td title='Usuário' style='color: #999999;'>
								Criado pelo CliQ
							</td>
						</g:otherwise>
					</g:choose>
					<td title='Descrição'>
						<g:if condition="${not empty evento.descricao}">
							<div style='font-weight: bold; padding: 4px'>
								<g:print value='${evento.descricao}'/>
							</div>
						</g:if>
						<g:if condition="${not empty evento.observacoes}">
							<div style='padding: 4px'>
								${evento.observacoes}
							</div>
						</g:if>

						<g:if condition="${(index eq 0 and evento.user.id eq screen.user.id) or not empty evento.anexo.id}">
							<div class='MINIBAR' style="float: right">
								<g:shortcut condition="${not empty evento.anexo.id}" module="cliq.modulos" screen="Anexo"
									    arguments="form.id=${evento.anexo.id}" title='Baixar anexo do evento'/>
							</div>
						</g:if>
					</td>
				</tr>
			</g:iterator>
		</tbody>
	</table>
</fieldset>
