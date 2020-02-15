<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>

<g:template filename="/WEB-INF/views/MAIN.jsp">
	<main>
		<header>
			<img src='Logo.svg'/>
			<label>${app.id}</label>
			<label>Versão ${version}</label>
		</header>

		<nav>
			<g-tabbar>
				<g:link module='cliq.modulos.solicitante' screen='Solicitante'/>
				<g:link module='cliq.modulos.atendimento' screen="Atendimento"/>
				<g:link module='cliq.modulos.admin' screen="Admin"/>
				<hr/>
				<g:link/>
			</g-tabbar>
		</nav>

		<section>
			<g:insert/>
			<g:alert/>
		</section>

		<g:if condition="${not empty screen.user.id}">
			<footer>
				<label>${screen.user.role.name}</label>
				<label>${screen.user.name}</label>
			</footer>
		</g:if>
	</main>
</g:template>
