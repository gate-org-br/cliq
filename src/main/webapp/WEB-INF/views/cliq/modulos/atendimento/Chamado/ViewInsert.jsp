<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<fieldset style="width: 100%">
		<legend>
			Selecione a Categoria<g:icon type="cliq.entity.Categoria"/>
		</legend>
		<ul class='DeskPane'>
			<g:parent source="${screen.categorias}" target="categoria">
				<g:choose>
					<g:when condition="${not empty categoria.atalho}">
						<li>
							<a target="_blank" href="${categoria.atalho}" title='Novo Chamado'>
								<img src="Gate?MODULE=cliq.modulos&SCREEN=Categoria&ACTION=Icon&form.id=${categoria.id}"/>
								${categoria.nome}
							</a>
						</li>
					</g:when>
					<g:otherwise>
						<li>
							<g:link module="#" screen="#" action="Commit" arguments="form.categoria.id=${categoria.id}" title="Novo Chamado">
								<img src="Gate?MODULE=cliq.modulos&SCREEN=Categoria&ACTION=Icon&form.id=${categoria.id}"/>
								${categoria.nome}
							</g:link>
							<g:if condition="${not empty categoria.children}">
								<ul>
									<g:child source="${categoria.children}"/>
								</ul>
							</g:if>
						</li>
					</g:otherwise>
				</g:choose>
			</g:parent>
		</ul>
	</fieldset>
	<g-coolbar>
		<a target="_hide" class="Cancel" href='#' tabindex='3'>
			Desistir<g:icon type='cancel'/>
		</a>
	</g-coolbar>
</g:template>