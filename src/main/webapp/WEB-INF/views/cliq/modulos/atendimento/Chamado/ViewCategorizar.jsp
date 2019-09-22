<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<form method="post" action="#">
		<g:with name="page" value="${screen.categorias}">
			<g:if condition="${not empty page}">
				<fieldset style="height: 600px; margin: 0; overflow: auto">
					<legend>
						<g:icon type="${action}"/>
						<g:name type="${action}"/>
						<g:hidden property="form.id"/>
					</legend>

					<table class="c1" style="margin: 0">
						<col style="width: 48px"/>
						<col/>
						<tbody>
							<g:iterator source="${page}" target="target" children="${e -> e.children}">
								<tr data-method="post" data-action='Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Categorizar&form.categoria.id=${target.id}'>
									<td>
										<img  src='Gate?MODULE=cliq.modulos&SCREEN=Categoria&ACTION=Icon&form.id=${target.id}'
										      style='width: 24px; height: 24px;'/>
									</td>
									<td style='padding-left: ${depth*40}px;'>
										${target.nome}
									</td>
								</tr>
							</g:iterator>
						</tbody>
					</table>
				</fieldset>
			</g:if>
		</g:with>

		<div class='Coolbar'>
			<g:link class="Cancel" module="#" screen="#" action="Select" arguments="form.id=${screen.form.id}" tabindex='3'>
				Desistir<g:icon type="cancel"/>
			</g:link>
		</div>
	</form>
</g:template>