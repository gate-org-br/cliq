<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<g:hidden property="form.id"/>
	<fieldset>
		<legend>
			Organização
		</legend>
		<label style='width: 100%;'>
			Nome:
			<span>
				<g:label property='form.nome'/>
			</span>
		</label>
	</fieldset>

	<div class='Coolbar'>
		<a href='Gate?MODULE=cliq.modulos.admin&SCREEN=Organizacao&ACTION=Update&form.id=${screen.form.id}' style='float: right; margin-left: 8px;' tabindex='2'>
			Alterar<g:icon type='update'/>
		</a>
		<a style='color: #660000;' href='Gate?MODULE=cliq.modulos.admin&SCREEN=Organizacao&ACTION=Delete&form.id=${screen.form.id}' tabindex='3' data-confirm='Tem certeza de que deseja remover o registro?'>
			Remover<i>&#x2026;</i>
		</a>
		<a href='Gate?MODULE=cliq.modulos.admin&SCREEN=Organizacao' style='float: left' tabindex='1'>
			Retornar<i>&#x2023;</i>
		</a>
	</div>

	<div class='PageControl' data-type='Fetch'>
		<ul>
			<li data-selected='true'>
				<a href='Gate?MODULE=cliq.modulos.admin&SCREEN=Contato&form.organizacao.id=${screen.form.id}' style='text-align: left;'>
					Contatos<g:icon type="cliq.entity.Evento$Tipo:LIBERACAO"/>
				</a>
			</li>
		</ul>
		<div></div>
	</div>
</g:template>