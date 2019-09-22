<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>

<g:template filename="/WEB-INF/views/MAIN.jsp">
	<body>
		<div class="Main">
			<div>
				<img src='Logo.svg'/>
				<label>${app.id} - ${app.name}</label>
				<label>Versão ${version}</label>
				<label>
					<g:link condition="${not empty user.role.children}" 
						module="cliq.modulos" screen="Equipe" action="Choose" otherwise="${equipe.name}">
						${equipe.name}	
					</g:link>
				</label>
			</div>

			<div>
				<ul class='HMenu'>
					<g:menu module="cliq.modulos.solicitante" screen="Solicitante">
						<g:menuitem module='cliq.modulos.solicitante' screen='Solicitante' action="Insert"/>
						<g:menuitem module='cliq.modulos.solicitante' screen='Pessoais'/>
						<g:menuitem module='cliq.modulos.solicitante' screen='DaEquipe'/>
						<g:menuitem module='cliq.modulos.solicitante' screen='Pesquisa'/>
					</g:menu>
					<g:menu module="cliq.modulos.atendimento" screen="Atendimento">
						<g:menuitem module='cliq.modulos.atendimento' screen='Pessoais'/>
						<g:menuitem module='cliq.modulos.atendimento' screen='DaEquipe'/>
						<g:menuitem module='cliq.modulos.atendimento' screen='Chamados'/>
						<g:menuitem module='cliq.modulos.atendimento' screen='Pesquisa'/>
						<g:menuitem module='cliq.modulos.atendimento' screen='Evento'/>
						<g:menuitem module='cliq.modulos.atendimento' screen='Contato'/>
					</g:menu>
					<g:menu module="cliq.modulos.admin" screen="Admin">
						<g:menuitem module='cliq.modulos.admin' screen='Tarefa'/>
						<g:menuitem module='cliq.modulos.admin' screen='Categoria'/>
						<g:menuitem module='cliq.modulos.admin' screen='SLA'/>
					</g:menu>
					<li></li>

					<li>
						<g:link module="cliq.modulos" screen="Alerta" action="Select" style="width: 80px;">
						<cliq-alerta></cliq-alerta>
						</g:link>
						<g:link target="_dialog" module="cliq.modulos.atendimento" 
							screen="Atendimento" action="Localizar" arguments="form.id=?{Número do chamado}"/>
						<g:shortcut/>
					</li>
				</ul>
			</div>

			<div>
				<g:insert/>
				<g:alert/>
			</div>
		</div>
	</body>
</g:template>
