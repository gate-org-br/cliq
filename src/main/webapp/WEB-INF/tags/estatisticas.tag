<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>

<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<fieldset>
	<table class="Estatisticas">
		<thead>
			<tr>
				<th>
					Pendentes
				</th>
				<th>
					Atribuídos
				</th>
				<th>
					Em atendimento
				</th>
				<th>
					Origens
				</th>
				<th>
					Categorias
				</th>
				<th>
					Prioridade
				</th>
				<th>
					Complexidade
				</th>
				<th>
					Desempenho
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class='PChart' 
				    title='Chamados por pendência'
				    data-series='[["Pendencia", "Chamados"]
				    ,["Sem pendências", ${estatisticas.semPendencias}]
				    ,["Triagem", ${estatisticas.triagem}]
				    ,["Aprovação", ${estatisticas.aprovacao}]
				    ,["Complementação", ${estatisticas.complementacao}]
				    ,["Suspensos", ${estatisticas.suspensos}]
				    ,["Homologação", ${estatisticas.homologacao}]
				    ,["Feedback", ${estatisticas.feedback}]]'>
					<div>
						<span title='Chamados pendentes'>
							<g:icon type="cliq.entity.Chamado"/>
							<g:print value="${estatisticas.pendentes}"/>	
						</span>
						<span title='${g:name("cliq.type.Pendencia:NENHUMA")}'>
							<g:icon type="cliq.type.Pendencia:NENHUMA"/>
							<g:print value="${estatisticas.semPendencias}"/>
						</span>
						<span title='${g:name("cliq.type.Pendencia:TRIAGEM")}'>
							<g:icon type="cliq.type.Pendencia:TRIAGEM"/>
							<g:print value="${estatisticas.triagem}"/>
						</span>
						<span title='${g:name("cliq.type.Pendencia:APROVACAO")}'>
							<g:icon type="cliq.type.Pendencia:APROVACAO"/>
							<g:print value="${estatisticas.aprovacao}"/>
						</span>
						<span title='${g:name("cliq.type.Pendencia:COMPLEMENTACAO")}'>
							<g:icon type="cliq.type.Pendencia:COMPLEMENTACAO"/>
							<g:print value="${estatisticas.complementacao}"/>
						</span>
						<span title='${g:name("cliq.type.Pendencia:SUSPENSO")}'>
							<g:icon type="cliq.type.Pendencia:SUSPENSO"/>
							<g:print value="${estatisticas.suspensos}"/>
						</span>
						<span title='${g:name("cliq.type.Pendencia:HOMOLOGACAO")}'>
							<g:icon type="cliq.type.Pendencia:HOMOLOGACAO"/>
							<g:print value="${estatisticas.homologacao}"/>
						</span>
						<span title='${g:name("cliq.type.Pendencia:FEEDBACK")}'>
							<g:icon type="cliq.type.Pendencia:FEEDBACK"/>
							<g:print value="${estatisticas.feedback}"/>
						</span>
					</div>
				</td>
				<td class='CChart'
				    title='Chamados pendentes por atendente'
				    data-action='Gate?MODULE=${MODULE}&SCREEN=Estatistica&ACTION=ChamadosPorAtendente'>
					<g:print value="${estatisticas.atribuidos}"/> / <g:print value="${estatisticas.pendentes}"/>
				</td>
				<td class='PChart' 
				    title='Chamados pendentes em atendimento'
				    data-series='[["Em Atendimento", "Chamados"]
				    ,["Sim", ${estatisticas.emAtendimento}]
				    ,["Não", ${estatisticas.pendentes - estatisticas.emAtendimento}]]'>
					<g:print value="${estatisticas.emAtendimento}"/> / <g:print value="${estatisticas.pendentes}"/>
				</td>
				<td class='CChart' 
				    title='Chamados pendentes por origem'
				    data-action='Gate?MODULE=${MODULE}&SCREEN=Estatistica&ACTION=ChamadosPorOrigem'>
					${estatisticas.origens}
				</td>
				<td class='CChart' 
				    title='Chamados pendentes por categoria'
				    data-action='Gate?MODULE=${MODULE}&SCREEN=Estatistica&ACTION=ChamadosPorCategoria'>
					${estatisticas.categorias}
				</td>
				<td class='PChart' 
				    title='Chamados pendentes por prioridade'
				    data-series='[["Prioridade", "Chamados"]
				    ,["Baixa", ${estatisticas.prioridadeBaixa}]
				    ,["Média", ${estatisticas.prioridadeMedia}]
				    ,["Alta", ${estatisticas.prioridadeAlta}]
				    ,["Urgente", ${estatisticas.prioridadeUrgente}]]'>
					<div>
						<span style='color: ${g:color("cliq.type.Prioridade:BAIXA")}' 
						      title='${g:name("cliq.type.Prioridade:BAIXA")}'>
							<g:icon type="cliq.type.Prioridade:BAIXA"/>
							<g:print value="${estatisticas.prioridadeBaixa}"/>
						</span>
						<span style='color: ${g:color("cliq.type.Prioridade:MEDIA")}' 
						      title='${g:name("cliq.type.Prioridade:MEDIA")}'>
							<g:icon type="cliq.type.Prioridade:MEDIA"/>
							<g:print value="${estatisticas.prioridadeMedia}"/>
						</span>
						<span style='color: ${g:color("cliq.type.Prioridade:ALTA")}' 
						      title='${g:name("cliq.type.Prioridade:ALTA")}'>
							<g:icon type="cliq.type.Prioridade:ALTA"/>
							<g:print value="${estatisticas.prioridadeAlta}"/>
						</span>
						<span style='color: ${g:color("cliq.type.Prioridade:URGENTE")}' 
						      title='${g:name("cliq.type.Prioridade:URGENTE")}'>
							<g:icon type="cliq.type.Prioridade:URGENTE"/>
							<g:print value="${estatisticas.prioridadeUrgente}"/>
						</span>
					</div>
				</td>
				<td class='PChart' 
				    title='Chamados pendentes por complexidade'
				    data-series='[["Complexidade", "Chamados"]
				    ,["Baixa", ${estatisticas.complexidadeBaixa}]
				    ,["Média", ${estatisticas.complexidadeMedia}]
				    ,["Alta", ${estatisticas.complexidadeAlta}]
				    ,["Urgente", ${estatisticas.complexidadeCritica}]]'>
					<div>
						<span style='color: ${g:color("cliq.type.Complexidade:BAIXA")}' 
						      title='${g:name("cliq.type.Complexidade:BAIXA")}'>
							<g:icon type="cliq.type.Complexidade:BAIXA"/>
							<g:print value="${estatisticas.complexidadeBaixa}"/>
						</span>
						<span style='color: ${g:color("cliq.type.Complexidade:MEDIA")}' 
						      title='${g:name("cliq.type.Complexidade:MEDIA")}'>
							<g:icon type="cliq.type.Complexidade:MEDIA"/>
							<g:print value="${estatisticas.complexidadeMedia}"/>
						</span>
						<span style='color: ${g:color("cliq.type.Complexidade:ALTA")}' 
						      title='${g:name("cliq.type.Complexidade:ALTA")}'>
							<g:icon type="cliq.type.Complexidade:ALTA"/>
							<g:print value="${estatisticas.complexidadeAlta}"/>
						</span>
						<span style='color: ${g:color("cliq.type.Complexidade:CRITICA")}' 
						      title='${g:name("cliq.type.Complexidade:CRITICA")}'>
							<g:icon type="cliq.type.Complexidade:CRITICA"/>
							<g:print value="${estatisticas.complexidadeCritica}"/>
						</span>
					</div>
				</td>
				<td class='LChart'
				    data-action='Gate?MODULE=${MODULE}&SCREEN=Estatistica&ACTION=DesempenhoMensal'
				    title='Desempenho mensal'>
					<div>
						<span style='color: #000000' title='Criados'>
							<g:icon type="cliq.entity.Evento$Tipo:CRIACAO"/>
							${estatisticas.criacoes}
						</span>
						<span style='color: #006600' title='Concluídos'>
							<g:icon type="cliq.entity.Evento$Tipo:CONCLUSAO"/>
							${estatisticas.conclusoes}
						</span>
						<span style='color: #660000' title='Cancelados'>
							<g:icon type="cliq.entity.Evento$Tipo:CANCELAMENTO"/>
							${estatisticas.cancelamentos}
						</span>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</fieldset>