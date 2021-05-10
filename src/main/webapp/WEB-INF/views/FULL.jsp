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
				<g:link module="cliq.modulos.solicitante"/>
				<g:link module="cliq.modulos.atendimento"/>
				<g:link module="cliq.modulos.admin" />
				<g:link target="_dialog" module="cliq.modulos.atendimento" 
					screen="Atendimento" action="Localizar" arguments="form.id=?{Número do chamado}"/>
				<g:link module="cliq.modulos" screen="Alerta" action="Select">
					<g:icon type="1007"/>
					<cliq-alerta></cliq-alerta>
				</g:link>
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
				<label>
					<g:link module="cliq.modulos" screen="Equipe" action="Choose" otherwise="${equipe.name}">
						${equipe.name}	
					</g:link>
				</label>
				<label>
					${screen.user.name}	
				</label>
			</footer>
		</g:if>
	</main>
</g:template>
