<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<%@ attribute name="chamados" required="true" type="gate.util.Page"%>

<form id="form" method="post" action="#">
	<g:choose>
		<g:when condition="${not empty chamados}">
			<g:if condition="${screen.page.paginator.size eq 1}">
				<fieldset>
					<label>
						<span>
							<g:icon type="filter"/>
							<input type="TEXT" data-filter placeholder="Filtrar">
							</input>
						</span>
					</label>
				</fieldset>
			</g:if>
			<table class="Solicitacoes" data-collapse="1200px">
				<caption>
					CHAMADOS: ${chamados.paginator.data.size()}						
				</caption>
				<thead>

					<tr>
						<th>
							<g:ordenator property="tipo.nome">
								Tipo	
							</g:ordenator>
						</th>
						<th>
							<g:ordenator property="titulo">
								Chamado	
							</g:ordenator>
						</th>
						<th>
							<g:ordenator property="id">
								N&ordm;	
							</g:ordenator>
						</th>
						<th>
							<g:ordenator property="data">
								Data	
							</g:ordenator>
						</th>
						<th>
							<g:ordenator property="situacao">
								Situação
							</g:ordenator>
						</th>
						<th>
							<g:ordenator property="solicitante.name">
								Solicitante	
							</g:ordenator>
						</th>
						<th>
							<g:ordenator property="atendente.name">
								Atendente	
							</g:ordenator>
						</th>
						<th>
							#
						</th>
					</tr>
				</thead>

				<tfoot>
					<tr>
						<td colspan="8" style="text-align: right">
							<g:paginator/>
						</td>
					</tr>
				</tfoot>

				<tbody>
					<g:iterator source="${chamados}" target="chamado">
						<tr tabindex="1"
						    title="Chamados"
						    data-target='_dialog'
						    data-action='Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Select&form.id=${chamado.id}'>
							<td>
								<cliq:tipo chamado="${chamado}"/>
							</td>
							<td title="Título">
								<g:print value="${chamado.titulo}"/>	
							</td>
							<td title="Número">
								<g:print value="${chamado.id}"/>
							</td>
							<td title='Data'>
								<cliq:data chamado="${chamado}"/>
							</td>
							<td title="Situação">
								<cliq:situacao chamado="${chamado}"/>
							</td>
							<td title="Solicitante">
								<cliq:solicitante chamado="${chamado}"/>
							</td>
							<td title="Atendente">
								<cliq:atendente chamado="${chamado}"/>
							</td>
							<td>
								<cliq:notificacao chamado="${chamado}"/>
							</td>
						</tr>
					</g:iterator>

				</tbody>
			</table>
		</g:when>
		<g:otherwise>
			<div class='TEXT'>
				<h1>Nenhum chamado encontrado</h1>
			</div>
		</g:otherwise>
	</g:choose>
</form>

