<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/cliq/modulos/solicitante/Main.jsp">
	<fieldset style='padding: 4px;'>
		<legend>
			<g:icon type="cliq.entity.Chamado"/>Nova solicitação
		</legend>
		<ul class='DeskPane'>
			<g:parent source="${screen.categorias}" target="categoria">
				<g:choose>
					<g:when condition="${empty categoria.atalho}">
						<li>
							<g:link target="_dialog"
								module="#"
								screen="#"
								action="Commit"
								arguments="form.categoria.id=${categoria.id}"
								title="Novo Chamado">
								<img src="Gate?MODULE=cliq.modulos&SCREEN=Categoria&ACTION=Icon&form.id=${categoria.id}"/>
								${categoria.nome}
							</g:link>
							<g:if condition="${not empty categoria.children}">
								<ul>
									<g:child source="${categoria.children}"/>
								</ul>
							</g:if>
						</li>
					</g:when>
					<g:otherwise>
						<li>
							<a target="_blank" href="${categoria.atalho}">
								<img src="Gate?MODULE=cliq.modulos&SCREEN=Categoria&ACTION=Icon&form.id=${categoria.id}"/>
								${categoria.nome}
							</a>
						</li>
					</g:otherwise>
				</g:choose>
			</g:parent>
		</ul>
	</fieldset>
</g:template>