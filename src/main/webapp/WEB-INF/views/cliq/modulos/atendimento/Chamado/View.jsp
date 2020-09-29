<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>

<g:template filename="/WEB-INF/views/cliq/modulos/atendimento/Main.jsp">
	<g-coolbar>
		<g:link target="_report" module='#' screen='#' action='Report'/>
		<hr/>
		<g:link target='_dialog' module='#' screen='#' action='Insert' data-on-hide="reload"/>
	</g-coolbar>

	<div id="PageControl" class='PageControl'>
		<ul>
			<li style='min-width: 50px; max-width: 50px'>
				<g:link module="#" screen="#" action="Import" 
					style="justify-content: center" title='Todos os Chamados'>
					<g:icon type="cliq.entity.Chamado" style="font-size: 24px"/>
				</g:link>
				<div>
					<cliq:atendimentos chamados="${screen.chamados}"/>
				</div>
			</li>

			<g:if condition="${screen.chamados.stream().anyMatch(e -> e.pendencia eq 'NENHUMA')}">
				<li style='min-width: 50px; max-width: 50px'>
					<g:link module="#" screen="#" action="Import"
						arguments="form.pendencia=NENHUMA"
						style="justify-content: center"
						title='Chamados ativos'>
						<g:icon type="cliq.type.Pendencia:NENHUMA" style="font-size: 24px"/>
					</g:link>
				</li>
			</g:if>

			<g:if condition="${screen.chamados.stream().anyMatch(e -> e.pendencia eq 'SUSPENSO')}">
				<li style='min-width: 50px; max-width: 50px'>
					<g:link module="#" screen="#" action="Import"
						arguments="form.pendencia=SUSPENSO"
						style="justify-content: center"
						title='Chamados suspensos'>
						<g:icon type="cliq.type.Pendencia:SUSPENSO" style="font-size: 24px"/>
					</g:link>
				</li>
			</g:if>

			<g:if condition="${screen.chamados.stream().anyMatch(e -> e.pendencia eq 'APROVACAO')}">
				<li style='min-width: 50px; max-width: 50px'>
					<g:link module="#" screen="#" action="Import"
						arguments="form.pendencia=APROVACAO"
						style="justify-content: center"
						title='Chamados em aprovação'>
						<g:icon type="cliq.type.Pendencia:APROVACAO" style="font-size: 24px"/>
					</g:link>
				</li>
			</g:if>

			<g:if condition="${screen.chamados.stream().anyMatch(e -> e.pendencia eq 'COMPLEMENTACAO')}">
				<li style='min-width: 50px; max-width: 50px'>
					<g:link module="#" screen="#" action="Import"
						arguments="form.pendencia=COMPLEMENTACAO"
						style="justify-content: center"
						title='Chamados em complementação'>
						<g:icon type="cliq.type.Pendencia:COMPLEMENTACAO" style="font-size: 24px"/>
					</g:link>
				</li>
			</g:if>

			<g:if condition="${screen.chamados.stream().anyMatch(e -> e.pendencia eq 'FEEDBACK')}">
				<li style='min-width: 50px; max-width: 50px'>
					<g:link module="#" screen="#" action="Import"
						arguments="form.pendencia=FEEDBACK"
						style="justify-content: center"
						title='Chamados em Feedback'>
						<g:icon type="cliq.type.Pendencia:FEEDBACK" style="font-size: 24px"/>
					</g:link>
				</li>
			</g:if>

			<g:iterator source="${screen.chamados.stream().map(e -> e.categoria).filter(e -> not empty e.id).distinct().sorted().toList()}">
				<li style='min-width: 50px; max-width: 50px'>
					<g:link module="#" screen="#" action="Import"
						arguments="form.categoria.id=${target.id}"
						style="justify-content: center"
						title='${target.nome}'>
						<img src='Gate?MODULE=cliq.modulos&SCREEN=Categoria&ACTION=Icon&form.id=${target.id}'
						     style="height: 24px;"/>
					</g:link>
				</li>
			</g:iterator>

			<g:iterator source="${screen.chamados.stream().map(e -> e.nivel).filter(e -> not empty e).distinct().sorted().toList()}">
				<li style='min-width: 100px; max-width: 100px'>
					<g:link module="#" screen="#" action="Import"
						arguments="form.nivel=${target.ordinal()}"
						style="justify-content: center; height: 34px"
						title='${g:print(target)}'>
						<g:print value="${target}"/>
					</g:link>
				</li>
			</g:iterator>
		</ul>
	</div>
</g:template>