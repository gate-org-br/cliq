<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/PAGE.jsp">
	<g:choose>
		<g:when condition="${empty screen.page}">
			<div class='TEXT'>
				<h1>
					Nenhuma subcategoria cadastrada
					<br/>Clique em Nova para criar uma subcategoria
				</h1>
			</div>
		</g:when>
		<g:otherwise>
			<table>
				<col style='width: 06%'/>
				<col style='width: 54%'/>
				<col style='width: 10%'/>
				<col style='width: 10%'/>
				<col style='width: 10%'/>
				<col style='width: 10%'/>

				<caption>
					SUBCATEGORIAS
				</caption>

				<thead>
					<tr>
						<th style='text-align: center;'>
							#
						</th>
						<th style='text-align: left;'>
							Nome
						</th>
						<th style='text-align: center;'>
							Visibilidade
						</th>
						<th style='text-align: center;'>
							Triagem
						</th>
						<th style='text-align: center;'>
							Prioridade
						</th>
						<th style='text-align: center;'>
							Complex
						</th>
					</tr>
				</thead>

				<tfoot></tfoot>

				<tbody>
					<g:iterator source="${screen.page}" target="categoria"
						    index="index" children="${e -> e.children}">
						<tr data-target='_parent'
						    data-action='Gate?MODULE=cliq.modulos.admin&SCREEN=Categoria&ACTION=Select&form.id=${categoria.id}'>
							<td style='text-align: center; padding: 5px;'>
								<img src='Gate?MODULE=cliq.modulos&SCREEN=Categoria&ACTION=Icon&form.id=${categoria.id}' style='width: 24px; height: 24px;'/>
							</td>
							<td style='text-align: left;'>
								${categoria.nome}
							</td>
							<td style='text-align: center;'>
								${categoria.visibilidade}
							</td>
							<td style='text-align: center;'>
								<g:print value="${categoria.triagem}"/>
							</td>
							<td style='text-align: center;'>
								${g:print(categoria.prioridade)}
							</td>
							<td style='text-align: center;'>
								${g:print(categoria.complexidade)}
							</td>
						</tr>
					</g:iterator>
				</tbody>
			</table>
		</g:otherwise>
	</g:choose>
	<g-coolbar>
		<g:link target='_parent' module="#" screen="#" action="Insert" arguments="form.categoria.id=${screen.form.id}">
			Nova<g:icon type="insert"/>
		</g:link>
	</g-coolbar>
</g:template>