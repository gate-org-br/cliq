<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<fieldset>
		<legend>
			<g:icon type="cliq.entity.SLA"/>Política de SLA
		</legend>
		<fieldset style='width: 50%'>
			<legend>
				SLA<g:icon type="cliq.entity.SLA"/>
			</legend>
			<label>
				Nome:
				<span>
					<g:print value='${screen.form.nome}'/>
				</span>
			</label>
			<label data-size='8'>
				Preced&ecirc;ncia:
				<span>
					<g:icon type="#"/>
					<g:print value='${screen.form.precedencia}'/>
				</span>
			</label>
			<label data-size='8'>
				Urgente:
				<span>
					<g:print value='${screen.form.urgente}'/>
				</span>
			</label>
		</fieldset>
		<fieldset style='width: 50%'>
			<legend>
				<g:icon type="2003"/>Expediente
			</legend>
			<label style='width: 20%'>
				Seg:
				<span>
					<g:icon type="time"/>
					<g:print value="${org.mon}" empty="N/A"/>
				</span>
			</label>
			<label style='width: 20%'>
				Ter:
				<span>
					<g:icon type="time"/>
					<g:print value="${org.tue}" empty="N/A"/>
				</span>
			</label>
			<label style='width: 20%'>
				Qua:
				<span>
					<g:icon type="time"/>
					<g:print value="${org.wed}" empty="N/A"/>
				</span>
			</label>
			<label style='width: 20%'>
				Qui:
				<span>
					<g:icon type="time"/>
					<g:print value="${org.thu}" empty="N/A"/>
				</span>
			</label>
			<label style='width: 20%'>
				Sex:
				<span>
					<g:icon type="time"/>
					<g:print value="${org.fri}" empty="N/A"/>
				</span>
			</label>
			<label style='width: 20%; float: right'>
				Sab:
				<span>
					<g:icon type="time"/>
					<g:print value="${org.sat}" empty="N/A"/>
				</span>
			</label>
			<label style='width: 20%; float: right'>
				Dom:
				<span>
					<g:icon type="time"/>
					<g:print value="${org.sun}" empty="N/A"/>
				</span>
			</label>
		</fieldset>
		<fieldset style='width: 100%'>
			<legend>
				A quais chamados este SLA se aplica?
			</legend>
			<label data-size='2'>
				Projeto:
				<span>
					<g:print value='${screen.form.projeto}' empty="N/A"/>
				</span>
			</label>
			<label data-size='2'>
				Nível:
				<span>
					<g:print value='${screen.form.nivel}' empty="N/A"/>
				</span>
			</label>
			<label data-size='2'>
				Prioridade:
				<span>
					<g:print value='${screen.form.prioridade}' empty="N/A"/>
				</span>
			</label>
			<label data-size='2'>
				Complexidade:
				<span>
					<g:print value='${screen.form.complexidade}' empty="N/A"/>
				</span>
			</label>
			<label data-size='8'>
				Categoria:
				<span>
					<g:icon type="cliq.entity.Categoria"/>
					<g:if condition="${not empty screen.form.categoria.id}" otherwise="N/A">
						<g:link module="cliq.modulos.admin" screen="Categoria" action="Select" arguments="form.id=${screen.form.categoria.id}"
							otherwise="${screen.form.categoria.nome}">
							<g:print value='${screen.form.categoria.nome}' empty="N/A"/>
						</g:link>
					</g:if>
				</span>
			</label>
			<label data-size='8'>
				Origem:
				<span>
					<g:icon type="gate.entity.Role"/>
					<g:if condition="${not empty screen.form.origem.id}" otherwise="N/A">
						<g:print value="${screen.user.role.root.select(screen.form.origem.id)}"/>
					</g:if>
				</span>
			</label>
			<label data-size='8'>
				Solicitante:
				<span>
					<g:icon type="gate.entity.User"/>
					<g:print value="${screen.form.solicitante.name}"/>
				</span>
			</label>
		</fieldset>

		<fieldset style='width: 100%'>
			<legend>
				Que prazos aplicar?
			</legend>
			<label data-size='4'>
				Tempo de Resposta:
				<span>
					<g:print value='${screen.form.ini}'/>
				</span>
			</label>
			<label data-size='4'>
				Unidade:
				<span>
					<g:print value='${screen.form.uini}'/>
				</span>
			</label>
			<label data-size='4'>
				Tempo de Solução:
				<span>
					<g:print value='${screen.form.fim}'/>
				</span>
			</label>
			<label data-size='4'>
				Unidade:
				<span>
					<g:print value='${screen.form.ufim}'/>
				</span>
			</label>
		</fieldset>
	</fieldset>

	<div class='Coolbar'>
		<a href='Gate?MODULE=cliq.modulos.admin&SCREEN=SLA&ACTION=Update&form.id=${screen.form.id}' tabindex='1'>
			Alterar<g:icon type='update'/>
		</a>
		<a style='color: #660000' href='Gate?MODULE=cliq.modulos.admin&SCREEN=SLA&ACTION=Delete&form.id=${screen.form.id}' tabindex='3' data-confirm='Tem certeza de que deseja remover o registro?'>
			Remover<i>&#x2026;</i>
		</a>
		<a href='Gate?MODULE=cliq.modulos.admin&SCREEN=SLA' tabindex='2' style='float: left'>
			Retornar<i>&#x2023;</i>
		</a>
	</div>
</g:template>