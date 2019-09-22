<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/DIAG.jsp">
	<div class="Panel" style="display: flex">
		<div style="padding: 8px; flex-basis: 50%">
			<form  method="post"
			       action="Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Insert">
				<fieldset>
					<legend>
						Compartilhar com pessoa<g:icon type="gate.entity.User"/>
					</legend>
					<label>
						<span>
							<g:hidden id="form.pessoa.id" property="form.pessoa.id"/>
							<g:text id="form.pessoa.name" property="form.pessoa.name" tabindex='1'
								data-getter="form.pessoa"/>
							<g:shortcut id="form.pessoa" module="#"
								    screen="Pessoa" action="Search"
								    arguments="form=@{form.pessoa.name}"
								    data-get="form.pessoa.id, form.pessoa.name"/>
						</span>
					</label>
				</fieldset>

				<div class='Coolbar'>
					<g:link method="post" module="#" screen="#" action="Pessoa"/>
				</div>

				<g:hidden property="form.chamado.id"/>
			</form>
		</div>
		<div style="padding: 8px; flex-basis: 50%">
			<form  method="post"
			       action="Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Insert" style="flex-basis: 50%">
				<fieldset>
					<legend>
						Compartilhar com equipe<g:icon type="gate.entity.Role"/>
					</legend>
					<label>
						<span>
							<g:hidden id="form.equipe.id" property="form.equipe.id"/>
							<g:text id="form.equipe.name" property="form.equipe.name" tabindex='1'
								data-getter="form.equipe"/>
							<g:shortcut id="form.equipe" module="#"
								    screen="Equipe" action="Search"
								    arguments="form.name=@{form.equipe.name}"
								    data-get="form.equipe.id, form.equipe.name"/>
						</span>
					</label>
				</fieldset>

				<div class='Coolbar'>
					<g:link method="post" module="#" screen="#" action="Equipe"/>
				</div>

				<g:hidden property="form.chamado.id"/>
			</form>
		</div>
	</div>
	<g:choose>
		<g:when condition="${empty screen.page}">
			<div class='TEXT'>
				<h1>
					Nenhum compartilhamento definido para este chamado
				</h1>
			</div>
		</g:when>
		<g:otherwise>
			<table class="c1 c3">
				<caption>
					COMPARTILHAMENTOS ENCONTRADOS: ${screen.page.size()}
				</caption>

				<thead>
					<tr>
						<th style="width: 48px">
							Tipo
						</th>
						<th>
							Nome
						</th>
						<th style="width: 48px">
							<g:icon type="delete"/>
						</th>
					</tr>
				</thead>

				<tbody>
					<g:iterator source="${screen.page}" target="target">
						<tr>
							<g:choose>
								<g:when condition="${not empty target.pessoa.id}">
									<td style='text-align: center'>
										<g:icon type="gate.entity.User"/>
									</td>
									<td>
										<g:print value="${target.pessoa.name}"/>
									</td>
								</g:when>
								<g:when condition="${not empty target.equipe.id}">
									<td style='text-align: center'>
										<g:icon type="gate.entity.Role"/>
									</td>
									<td>
										<g:print value="${target.equipe.name}"/>
									</td>
								</g:when>
							</g:choose>
							<td style='text-align: center'>
								<g:shortcut module="#" screen="#" action="Delete"
									    arguments="form.id=${target.id}&form.chamado.id=${target.chamado.id}"/>
							</td>
						</tr>
					</g:iterator>
				</tbody>
			</table>
		</g:otherwise>
	</g:choose>
	<div class='Coolbar'>
		<a class='Hide' href='#'>
			Fechar<g:icon type="cancel"/>
		</a>
	</div>
</g:template>
