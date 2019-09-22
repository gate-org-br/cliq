<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/cliq/modulos/solicitante/Main.jsp">
	<div class='Coolbar'>
		<g:link class="Action" module="#" screen="#" action="Insert" tabindex='1'/>
		<g:link target="_report" module="#" screen="#" action="Report" tabindex='1' 
			arguments="form.situacao=${screen.form.situacao.name()}"
			style="float: left"/>
	</div>
	<div class="Panel">
		<div class='LinkControl'>
			<ul>
				<li data-selected='${screen.form.situacao eq 'PENDENTE'}' style="color: ${g:color('cliq.type.Situacao:PENDENTE')}">
					<g:link module="#" screen="#" action="#" arguments="form.situacao=PENDENTE">
						Pendentes<g:icon type="cliq.type.Situacao:PENDENTE"/>
					</g:link>
				</li>
				<li data-selected='${screen.form.situacao eq 'CONCLUIDO'}' style="color: ${g:color('cliq.type.Situacao:CONCLUIDO')}">
					<g:link module="#" screen="#" action="#" arguments="form.situacao=CONCLUIDO">
						Concluídas<g:icon type="cliq.type.Situacao:CONCLUIDO"/>
					</g:link>
				</li>
				<li data-selected='${screen.form.situacao eq 'CANCELADO'}' style="color: ${g:color('cliq.type.Situacao:CANCELADO')}">
					<g:link module="#" screen="#" action="#" arguments="form.situacao=CANCELADO">
						Canceladas<g:icon type="cliq.type.Situacao:CANCELADO"/>
					</g:link>
				</li>
			</ul>

			<div>
				<cliq:solicitacoes chamados="${screen.page}"/>
			</div>
		</div>
	</div>

	<script>
		window.addEventListener("load",
			() => Array.from(document.querySelectorAll("a[target='_dialog'], tr[data-target='_dialog']"))
				.forEach(element => element.addEventListener("hide", () => window.location = window.location.href)));
	</script>
</g:template>
