<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<%@ attribute name="chamados" required="true" type="java.util.List"%>

<g:choose>
	<g:when condition="${not empty chamados}">
		<fieldset>
			<label>
				<span>
					<g:icon type="filter"/>
					<input type="TEXT" data-filter="Atendimentos" placeholder="Filtrar">
					</input>
				</span>
			</label>
		</fieldset>
		<table id="Atendimentos" class="Atendimentos" data-collapse="1200px">
			<caption>
				CHAMADOS ENCONTRADOS: ${chamados.size()}
			</caption>

			<thead>
				<tr>
					<th data-sortable>
						Tipo
					</th>
					<th data-sortable>
						Título	
					</th>
					<th data-sortable>
						N&ordm;
					</th>
					<th data-sortable>
						Prioridade
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
						<input type="checkbox" title="Mostrar descrição">

						</input>
					</th>
				</tr>
			</thead>

			<tbody data-navigate>
				<g:iterator source="${chamados}" target="chamado">
					<tr tabindex="1"
					    title="Chamados"
					    data-target='_dialog'
					    data-on-hide="reload"
					    data-action='Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Select&form.id=${chamado.id}'>
						<td>
							<cliq:categoria chamado="${chamado}"/>
						</td>
						<td title="Chamado">
							<label>
								<g:print value="${chamado.titulo}"/>	
								<div>
									<g:print value="${chamado.descricao}"/>	
								</div>
							</label>
						</td>
						<td title="Número">
							<g:print value="${chamado.id}"/>
						</td>
						<td title="Prioridade" data-value="${chamado.prioridade.ordinal()}">
							<cliq:prioridade chamado="${chamado}"/>
						</td>
						<td title="Data" data-value="${g:number(chamado.data)}">
							<cliq:data chamado="${chamado}"/>
						</td>
						<td  title="Origem">
							<cliq:origem chamado="${chamado}"/>
						</td>
						<td  title="Solicitante">
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