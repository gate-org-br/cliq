<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<%@ attribute name="checklist" required="true" type="java.util.List"%>

<g:if condition="${not empty checklist}">
	<fieldset>
		<legend>
			Tarefas<g:icon type="cliq.type.Checklist"/>
		</legend>
		<table class="Checklist">
			<thead>
				<tr>
					<th>
						#
					</th>
					<th>
						Nome
					</th>
					<th>
						#
					</th>
				</tr>
			</thead>
			<tbody>
				<g:iterator source="${checklist}" target="checkitem">
					<tr>
						<td>
							<g:shortcut module="#" screen="Checklist" action="Delete"
								    arguments="form.id=${screen.form.id}&checkitem.index=${checkitem.index}"
								    data-confirm='Tem certeza de que deseja remover a tarefa?'/>
						</td>
						<td>
							<g:print value="${checkitem.nome}"/>
						</td>
						<td>
							<g:shortcut condition="${checkitem.executada}"
								    module="#" screen="Checklist" action="Cancel"
								    arguments="form.id=${screen.form.id}&checkitem.index=${checkitem.index}"
								    data-confirm='Tem certeza de que deseja desfazer a tarefa?'/>
							<g:shortcut condition="${not checkitem.executada}"
								    module="#" screen="Checklist" action="Commit"
								    arguments="form.id=${screen.form.id}&checkitem.index=${checkitem.index}"/>
						</td>
					</tr>
				</g:iterator>
			</tbody>
		</table>
	</fieldset>
</g:if>
