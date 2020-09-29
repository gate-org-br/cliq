<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<g-coolbar>
		<g:link module="#" screen="#" action="Insert"/>
		<g:link module="#" screen="#" action="Upload"/>
	</g-coolbar>

	<table class="c2 c3 c4 c5 c6 c7 c8 c9">
		<caption>
			CATEGORIAS
		</caption>

		<thead>
			<tr>
				<th>
					Nome
				</th>
				<th style='width: 120px'>
					Visibilidade
				</th>
				<th style='width: 120px'>
					Nível
				</th>
				<th style='width: 120px'>
					Temporária
				</th>
				<th style='width: 120px'>
					Sigilosa
				</th>
				<th style='width: 120px'>
					Exige Triagem
				</th>
				<th style='width: 120px'>
					Exige Avaliacao
				</th>
				<th style='width: 120px'>
					Exige Feedback
				</th>
				<th style='width: 120px'>
					Exige aprovação
				</th>
			</tr>
		</thead>

		<tfoot></tfoot>

		<tbody>
			<tr data-role data-depth='${d1}'>
				<td style='text-align: left; padding-left: ${d1*60}px;'>
					<strong>
						<i style='color: gray; margin: 0 4px 0 8px;'>&#x2006;</i>${equipe.name}
						<g:if condition="${not empty equipe.roleID}">
							(<g:print value="${equipe.roleID}"/>)
						</g:if>
					</strong>
				</td>
				<td style='color: #CCCCCC;'>N/A</td>
				<td style='color: #CCCCCC;'>N/A</td>
				<td style='color: #CCCCCC;'>N/A</td>
				<td style='color: #CCCCCC;'>N/A</td>
				<td style='color: #CCCCCC;'>N/A</td>
				<td style='color: #CCCCCC;'>N/A</td>
				<td style='color: #CCCCCC;'>N/A</td>
				<td style='color: #CCCCCC;'>N/A</td>
			</tr>

			<g:iterator source="${screen.page}" target="categoria" depth='d2' children="${e -> e.children}">
				<tr data-categoria='Categoria' id='ID${categoria.id}' data-depth='${d1+d2+1}' data-action='Gate?MODULE=cliq.modulos.admin&SCREEN=Categoria&ACTION=Select&form.id=${categoria.id}'>
					<td style='padding-left: ${(d1+d2+1)*60}px;'>
						<img src='Gate?MODULE=cliq.modulos&SCREEN=Categoria&ACTION=Icon&form.id=${categoria.id}' style='width: 24px; height: 24px;'/>
						<g:print value="${categoria.nome}"/>
					</td>
					<td><g:print value="${categoria.visibilidade}"/></td>
					<td><g:print value="${categoria.nivel}" empty="N/A"/></td>
					<td><g:print value="${categoria.temporaria}"/></td>
					<td><g:print value="${categoria.sigilosa}"/></td>
					<td><g:print value="${categoria.triagem}"/></td>
					<td><g:print value="${categoria.avaliacao}"/></td>
					<td><g:print value="${categoria.feedback}"/></td>
					<g:choose>
						<g:when condition="${not empty categoria.pessoaAprovadora.id}">
							<td><g:print value="${categoria.pessoaAprovadora.name}"/></td>
						</g:when>
						<g:when condition="${not empty categoria.equipeAprovadora.id}">
							<td><g:print value="${categoria.equipeAprovadora.name}"/></td>
						</g:when>
						<g:otherwise>
							<td style='color: #999999;'>Não</td>
						</g:otherwise>
					</g:choose>
				</tr>
			</g:iterator>
		</tbody>
	</table>

	<script type='text/javascript'>
		window.addEventListener("load", function ()
		{
			Array.from(document.querySelectorAll("tr[data-categoria]")).forEach(function (element)
			{
				element.draggable = true;
				if (element.id.substring(2) === '${screen.form.id}')
					element.scrollIntoView(true);

				element.ondragover = event => event.preventDefault();

				element.ondragstart = function (event)
				{
					event.dataTransfer.setData("id", this.id.substring(2));
				};

				element.ondrop = function (event)
				{
					new URL("Gate")
						.setModule("${MODULE}")
						.setScreen("${SCREEN}")
						.setAction("Relate")
						.setParameter("form.id", event.dataTransfer.getData("id"))
						.setParameter("form.parent.id", this.id.substring(2))
						.go();
				};
			});

			Array.from(document.querySelectorAll("tr[data-role]")).forEach(function (element)
			{
				element.ondragover = event => event.preventDefault();

				element.ondrop = function ()
				{
					new URL("Gate")
						.setModule("${MODULE}")
						.setScreen("${SCREEN}")
						.setAction("Relate")
						.setParameter("form.id", event.dataTransfer.getData("id"))
						.go();
				}
			});
		});
	</script>
</g:template>