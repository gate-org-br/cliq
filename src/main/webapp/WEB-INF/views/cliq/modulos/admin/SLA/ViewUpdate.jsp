<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<form method='POST' action='#'>
		<fieldset>
			<legend>
				<g:path/>
			</legend>
			<fieldset style='width: 50%; margin: 0'>
				<legend>
					SLA<g:icon type="cliq.entity.SLA"/>
				</legend>
				<label>
					Nome:
					<span>
						<g:text property='form.nome' tabindex='1'/>
					</span>
				</label>
				<label data-size='8'>
					Preced&ecirc;ncia:
					<span>
						<g:icon type="�"/>
						<g:text property='form.precedencia' tabindex='1'/>
					</span>
				</label>
				<label data-size='8'>
					Urgente:
					<span>
						<g:select property='form.urgente'/>
					</span>
				</label>
			</fieldset>
			<fieldset style='width: 50%; margin: 0'>
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
			<fieldset>
				<legend>
					A quais chamados este SLA se aplica?
				</legend>
				<label data-size='2'>
					Projeto:
					<span>
						<g:select property='form.projeto' tabindex='1'/>
					</span>
				</label>
				<label data-size='2'>
					N�vel:
					<span>
						<g:select property='form.nivel' tabindex='1'/>
					</span>
				</label>
				<label data-size='2'>
					Prioridade:
					<span>
						<g:select property='form.prioridade' tabindex='1'/>
					</span>
				</label>
				<label data-size='2'>
					Complexidade:
					<span>
						<g:select property='form.complexidade' tabindex='1'/>
					</span>
				</label>
				<label data-size='8'>
					Categoria:
					<span>
						<g:hidden id="form.categoria.id" property="form.categoria.id" required=""/>
						<g:text id="form.categoria.nome" property="form.categoria.nome" required=""
							readonly="readonly"/>
						<g:shortcut module="#" screen="Categoria" action="Search"
							    data-get='form.categoria.id, form.categoria.nome'
							    tabindex='1' title='Selecionar Caregoria'>
							<g:icon type="search"/>
						</g:shortcut>
					</span>
				</label>
				<label data-size='8'>
					Origem:
					<span>
						<g:select property='form.origem.id' options="${screen.user.role.getRoot()}"
							  children="${e -> e.children}" tabindex='1' values="${e -> e.id}" required='' title='A qual grupo este SLA se aplica?'>
							${option.name}
						</g:select>
					</span>
				</label>
				<label data-size='8'>
					Solicitante:
					<span>
						<g:select property='form.solicitante.id' options="${screen.users}"
							  tabindex='1' values="${e -> e.id}" required='' title='A qual solicitante este SLA se aplica?'/>
					</span>
				</label>
			</fieldset>
			<fieldset style='width: 100%'>
				<legend>
					Que prazos este SLA deve aplicar aos chamados?
				</legend>
				<label data-size='4'>
					Tempo de Resposta:
					<span>
						<g:icon type="gate.type.Duration"/>
						<g:text property='form.ini'/>
					</span>
				</label>
				<label data-size='4'>
					Unidade:
					<span>
						<g:select property='form.uini'/>
					</span>
				</label>
				<label data-size='4'>
					Tempo de Solu��o:
					<span>
						<g:icon type="gate.type.Duration"/>
						<g:text property='form.fim'/>
					</span>
				</label>
				<label data-size='4'>
					Unidade:
					<span>
						<g:select property='form.ufim'/>
					</span>
				</label>
			</fieldset>
		</fieldset>

		<g-coolbar>
			<g:link method="post" module="#" screen="#" action="#" tabindex='2' style='color: #006600'>
				Concluir<g:icon type='commit'/>
			</g:link>
			<hr/>
			<g:link module="#" screen="#" action="Select" arguments="form.id=${screen.form.id}" tabindex='3' style='float: left; color: #660000'>
				Desistir<g:icon type='cancel'/>
			</g:link>
		</g-coolbar>

		<g:hidden property="form.id"/>
	</form>
</g:template>