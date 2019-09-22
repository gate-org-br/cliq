<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<%@ attribute name="chamado" required="true" type="cliq.entity.Chamado"%>

<ul style="margin: 0; padding: 0; list-style-type: none; width: 100%; display: block">
	<li style="width: 112px">
		<g:icon type="2223"/>&nbsp;<g:print value="${chamado.data}"/>		
	</li>
	<g:if condition="${chamado.pendencia eq 'NENHUMA'}">
		<g:if condition="${not empty chamado.prazoResposta}">
			<g:choose>
				<g:when condition="${empty chamado.resposta and chamado.prazoResposta gt dateTime}">
					<li style="color: #999900;">
						<g:icon type="2224" style='color: #999900'/>&nbsp;<g:print value="${chamado.prazoResposta}"/>
					</li>
				</g:when>
				<g:when condition="${empty chamado.resposta and chamado.prazoResposta lt dateTime}">
					<li style="color: #999900">
						<g:icon type="2224" style='color: #660000'/>&nbsp;<g:print value="${chamado.prazoResposta}"/>
					</li>
				</g:when>
				<g:when condition="${not empty chamado.resposta and chamado.prazoResposta gt chamado.resposta}">
					<li style="color: #999900">
						<g:icon type="2224" style='color: #006600'/>&nbsp;<g:print value="${chamado.prazoResposta}"/>
					</li>
				</g:when>
				<g:when condition="${not empty chamado.resposta and chamado.prazoResposta lt chamado.resposta}">
					<li style="color: #660000">
						<g:icon type="2224" style='color: #006600'/>&nbsp;<g:print value="${chamado.prazoResposta}"/>
					</li>
				</g:when>
			</g:choose>
		</g:if>
		<g:if condition="${not empty chamado.prazoSolucao}">
			<g:choose>
				<g:when condition="${empty chamado.solucao and chamado.prazoSolucao gt dateTime}">
					<li style="color: #999900">
						<g:icon type="2224" style='color: #999900'/>&nbsp;<g:print value="${chamado.prazoSolucao}"/>
					</li>
				</g:when>
				<g:when condition="${empty chamado.solucao and chamado.prazoSolucao lt dateTime}">
					<li style="color: #999900">
						<g:icon type="2224" style='color: #660000'/>&nbsp;<g:print value="${chamado.prazoSolucao}"/>
					</li>
				</g:when>
				<g:when condition="${not empty chamado.solucao and chamado.prazoSolucao gt chamado.solucao}">
					<li style="color: #999900">
						<g:icon type="2224" style='color: #006600'/>&nbsp;<g:print value="${chamado.prazoSolucao}"/>
					</li>
				</g:when>
				<g:when condition="${not empty chamado.solucao and chamado.prazoSolucao lt chamado.solucao}">
					<li style="color: #660000">
						<g:icon type="2224" style='color: #006600'/>&nbsp;<g:print value="${chamado.prazoSolucao}"/>
					</li>
				</g:when>
			</g:choose>
		</g:if>
	</g:if>
</ul>
