<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>

<g:template filename="/WEB-INF/views/cliq/modulos/solicitante/Main.jsp">
	<form id='form' method='POST' action='#'>
		<fieldset>
			<legend>
				<g:icon type="search"/>Pesquisar chamados
			</legend>
			<label data-size="2">
				N&ordm;
				<span>
					<g:text property="form.id" required='' tabindex='2'/>
				</span>
			</label>
			<label data-size="2">
				Situação:
				<span>
					<g:select property="form.situacao" required='' tabindex='2'/>
				</span>
			</label>
			<label data-size="4">
				Tipo:
				<span>
					<g:text property="form.tipo.nome" required='' tabindex='2'/>
				</span>
			</label>
			<label data-size="2">
				Data min:
				<span>
					<g:text class='Date' property="form.periodo.min"/>
				</span>
			</label>
			<label data-size="2">
				Data max:
				<span>
					<g:text class='Date' property="form.periodo.max"/>
				</span>
			</label>
			<label data-size="2">
				Hora min:
				<span>
					<g:text class='Time' property="form.horario.min"/>
				</span>
			</label>
			<label data-size="2">
				Hora max:
				<span>
					<g:text class='Time' property="form.horario.max"/>
				</span>
			</label>

			<label data-size="4">
				Origem:
				<span>
					<g:hidden id="form.origem.id" property="form.origem.id" required=''/>
					<g:text id="form.origem.name" property="form.origem.name" tabindex='1'
						required='' data-getter="form.origem" style="flex-basis: calc(100% - 64px)"/>
					<g:shortcut id="form.origem" module="cliq.modulos"
						    screen="Equipe" action="Search"
						    arguments="form.name=@{form.origem.name}"
						    data-get="form.origem.id, form.origem.name" style="flex-basis: 32px"/>
					<g:checkbox value="true" property="form.origem.recursive" tabindex='1' style="flex-basis: 16px"/>
				</span>
			</label>
			<label data-size="4">
				Solicitante:
				<span>
					<g:hidden id="form.solicitante.id" property="form.solicitante.id" required=''/>
					<g:text id="form.solicitante.name"
						property="form.solicitante.name"
						tabindex='1'
						required=''
						data-getter="form.solicitante"
						style="flex-basis: calc(100% - 32px)"/>
					<g:shortcut id="form.solicitante" module="cliq.modulos"
						    screen="Pessoa" action="Search"
						    arguments="form=@{form.solicitante.name}"
						    data-get="form.solicitante.id, form.solicitante.name"  style="flex-basis: 32px"/>
				</span>
			</label>
			<label data-size="4">
				Localização:
				<span>
					<g:hidden id="form.localizacao.id" property="form.localizacao.id" required=''/>
					<g:text id="form.localizacao.name" property="form.localizacao.name" tabindex='1'
						required='' data-getter="form.localizacao" style="flex-basis: calc(100% - 64px)"/>
					<g:shortcut id="form.localizacao" module="cliq.modulos"
						    screen="Equipe" action="Search"
						    arguments="form.name=@{form.localizacao.name}"
						    data-get="form.localizacao.id, form.localizacao.name" style="flex-basis: 32px"/>
					<g:checkbox value="true" property="form.localizacao.recursive" tabindex='1' style="flex-basis: 16px"/>
				</span>
			</label>
			<label data-size="4">
				Atendente:
				<span>
					<g:hidden id="form.atendente.id" property="form.atendente.id" required=''/>
					<g:select property="form.capturado" required='' tabindex='1'  style="flex-basis: 64px; flex-shrink: 0"/>
					<g:text id="form.atendente.name" property="form.atendente.name" tabindex='1'
						required='' data-getter="form.atendente"  style="flex-basis: calc(100% - 92px)"/>
					<g:shortcut id="form.atendente" module="cliq.modulos"
						    screen="Pessoa" action="Search" style="flex-basis: 32px"
						    arguments="form=@{form.atendente.name}"
						    data-get="form.atendente.id, form.atendente.name"/>
				</span>
			</label>
			<fieldset>
				<legend>
					Pesquisa avançada:
				</legend>
				<label>
					<span style='flex-basis: 80px;'>
						<g:textarea property="form.titulo" required='' tabindex='2'/>
					</span>
				</label>
			</fieldset>
		</fieldset>

		<div class='Coolbar'>
			<g:link class="Action" method="post" module="#" screen="#" action="#" tabindex='2'>
				Pesquisar<g:icon type="search"/>
			</g:link>
			<g:link method="post" target="_report" module="#" screen="#" action="Report" tabindex='1'/>
			<g:link module='#' screen='#' action='Insert' style='float: left' tabindex='2'/>
		</div>

		<cliq:solicitacoes chamados="${screen.page}"/>

		<script>
			window.addEventListener("load",
				() => Array.from(document.querySelectorAll("a[target='_dialog'], tr[data-target='_dialog']"))
					.forEach(element => element.addEventListener("hide", () => document.getElementById("form").submit())));
		</script>
	</form>

</g:template>
