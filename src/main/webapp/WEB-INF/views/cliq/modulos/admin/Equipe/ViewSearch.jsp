<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<fieldset style='margin: 0; height: calc(100vh - 40px); overflow: auto'>
		<ul>
			<g:parent source="${screen.page}" target="target">
				<g:if condition="${target.contemCategorias()}">
					<g:choose>
						<g:when condition="${target.contemCategorias}">
							<li style="color: #000000" data-ret="${target.id}, ${target.name}">
								<label>
									<g:print value="${target.name}"/>
								</label>
								<ul>
									<g:child source="${target.children}"/>
								</ul>
							</li>
						</g:when>
						<g:otherwise>
							<li style="color: #999999">
								<label>
									<g:print value="${target.name}"/>
								</label>
								<ul>
									<g:child source="${target.children}"/>
								</ul>
							</li>
						</g:otherwise>	
					</g:choose>
				</g:if>
			</g:parent>
		</ul>
	</fieldset>
</g:template>