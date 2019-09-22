<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<form method='POST' action='Gate?MODULE=cliq.modulos.admin&SCREEN=Organizacao&ACTION=Insert'>
		<fieldset>
			<legend>
				<i>&#x1002;</i>Novo Organização
			</legend>
			<label style='width: 100%;'>
				Nome:
				<span>
					<g:text property='form.nome' tabindex='1'/>
				</span>
			</label>
		</fieldset>

		<div class='Coolbar'>
			<g:link method='post' module="#" screen="#" action="#" tabindex='2' style='color: #006600'>
				Concluir<g:icon type='commit'/>
			</g:link>
			<g:link module="#" screen="#" tabindex='2' style='float: left; color: #660000'>
				Desistir<g:icon type='cancel'/>
			</g:link>
		</div>
	</form>
</g:template>