<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/cliq/modulos/solicitante/Main.jsp">
	<g-coolbar>
		<g:link class="Action" module="#" screen="#" action="Insert" tabindex='1'/>
		<hr/>
		<g:link target="_report" module="#" screen="#" action="Report" tabindex='1' 
			arguments="form.situacao=${screen.form.situacao.name()}"/>
	</g-coolbar>

	<div class="Panel">
		<g-tab-control type="dummy">
			<g:link module="#" screen="#" action="#" arguments="form.situacao=PENDENTE" 
				data-selected='${empty screen.form.situacao or screen.form.situacao eq "PENDENTE"}' 
				style="color: ${g:color('cliq.type.Situacao:PENDENTE')}">
				Pendentes<g:icon type="cliq.type.Situacao:PENDENTE"/>
			</g:link>
			<g:link module="#" screen="#" action="#" arguments="form.situacao=CONCLUIDO" 
				data-selected='${screen.form.situacao eq "CONCLUIDO"}' 
				style="color: ${g:color('cliq.type.Situacao:CONCLUIDO')}">
				Concluídos<g:icon type="cliq.type.Situacao:CONCLUIDO"/>
			</g:link>
			<g:link module="#" screen="#" action="#" arguments="form.situacao=CANCELADO" 
				data-selected='${screen.form.situacao eq "CANCELADO"}' 
				style="color: ${g:color('cliq.type.Situacao:CANCELADO')}">
				Cancelados<g:icon type="cliq.type.Situacao:CANCELADO"/>
			</g:link>
			<div>
				<cliq:solicitacoes chamados="${screen.page}"/>
			</div>
		</g-tab-control>
	</div>
</g:template>
