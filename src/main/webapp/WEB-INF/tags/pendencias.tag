<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:if condition="${not empty pendencias}">
	<table class="Pendencias">
		<caption>PENDÊNCIAS: ${pendencias.size()}</caption>

		<thead>
			<tr>
				<th data-sortable>
					Tipo
				</th>
				<th data-sortable>
					N&ordm;
				</th>
				<th data-sortable>
					Data
				</th>
				<th data-sortable>
					Origem
				</th>
				<th data-sortable>
					Solicitante
				</th>
				<th data-sortable>
					Atendente
				</th>
				<th>
					Chamado
				</th>
				<th data-sortable>
					Pendência
				</th>
				<th data-sortable>
					#
				</th>
			</tr>
		</thead>

		<tbody>
			<g:iterator source="${pendencias}" target="chamado">
				<g:choose>
					<g:when condition="${chamado.pendencia eq 'APROVACAO'}">
						<tr data-target='_dialog' data-action='Gate?MODULE=cliq.modulos&SCREEN=Aprovacao&form.id=${chamado.id}' title='${g:description(chamado.pendencia)}' data-on-hide="reload">
							<td>
								<cliq:categoria chamado="${chamado}"/>
							</td>
							<td>
								${chamado.id}
							</td>
							<td>
								<cliq:data chamado="${chamado}"/>
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
								${chamado.titulo}
							</td>
							<td>
								<g:print value="${chamado.pendencia}"/>
							</td>
							<td>
								<g:icon type="${chamado.pendencia}"/>
							</td>
						</tr>
					</g:when>
					<g:when condition="${chamado.pendencia eq 'HOMOLOGACAO'}">
						<tr data-target='_dialog' data-action='Gate?MODULE=cliq.modulos&SCREEN=Homologacao&form.id=${chamado.id}' title='${g:description(chamado.pendencia)}' data-on-hide="reload">
							<td>
								<cliq:categoria chamado="${chamado}"/>
							</td>
							<td>
								${chamado.id}
							</td>
							<td>
								<cliq:data chamado="${chamado}"/>
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
								${chamado.titulo}
							</td>
							<td>
								<g:print value="${chamado.pendencia}"/>
							</td>
							<td>
								<g:icon type="${chamado.pendencia}"/>
							</td>
						</tr>
					</g:when>
					<g:when condition="${chamado.pendencia eq 'FEEDBACK'}">
						<tr data-target='_dialog' data-action='Gate?MODULE=cliq.modulos&SCREEN=Feedback&form.id=${chamado.id}' title='${g:description(chamado.pendencia)}' data-on-hide="reload">
							<td>
								<cliq:categoria chamado="${chamado}"/>
							</td>
							<td>
								${chamado.id}
							</td>
							<td>
								<cliq:data chamado="${chamado}"/>
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
								${chamado.titulo}
							</td>
							<td>
								<g:print value="${chamado.pendencia}"/>
							</td>
							<td>
								<g:icon type="${chamado.pendencia}"/>
							</td>
						</tr>
					</g:when>
					<g:when condition="${chamado.pendencia eq 'AVALIACAO'}">
						<tr data-target='_dialog' data-action='Gate?MODULE=cliq.modulos&SCREEN=Avaliacao&form.id=${chamado.id}' title='${g:description(chamado.pendencia)}' data-on-hide="reload">
							<td>
								<cliq:categoria chamado="${chamado}"/>
							</td>
							<td>
								${chamado.id}
							</td>
							<td>
								<cliq:data chamado="${chamado}"/>
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
								${chamado.titulo}
							</td>
							<td>
								<g:print value="${chamado.pendencia}"/>
							</td>
							<td>
								<g:icon type="${chamado.pendencia}"/>
							</td>
						</tr>
					</g:when>
					<g:when condition="${chamado.pendencia eq 'COMPLEMENTACAO'}">
						<tr data-target='_dialog' data-action='Gate?MODULE=cliq.modulos&SCREEN=Complementacao&form.id=${chamado.id}' title='${g:description(chamado.pendencia)}' data-on-hide="reload">
							<td>
								<cliq:categoria chamado="${chamado}"/>
							</td>
							<td>
								${chamado.id}
							</td>
							<td>
								<cliq:data chamado="${chamado}"/>
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
								${chamado.titulo}
							</td>
							<td>
								<g:print value="${chamado.pendencia}"/>
							</td>
							<td>
								<g:icon type="${chamado.pendencia}"/>
							</td>
						</tr>
					</g:when>
				</g:choose>
			</g:iterator>
		</tbody>
	</table>
</g:if>

