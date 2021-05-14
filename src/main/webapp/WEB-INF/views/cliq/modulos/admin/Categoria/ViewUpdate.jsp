<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<form method='POST' action='#' enctype='multipart/form-data'>
		<fieldset>
			<legend>
				<g:path/>
			</legend>
			<label data-size='8'>
				Nome:
				<span>
					<g:text property='form.nome' tabindex='1'/>
				</span>
			</label>
			<label data-size='4'>
				Visibilidade:
				<span>
					<g:select property='form.visibilidade' tabindex='1'/>
					<a target="_popup" href="#">
						<g:icon type="2000"/>
						<template title='Exceções'>
							<fieldset>
								<g:selectn property="form.roles[]"
									   options="${screen.user.role.getRoot()}"
									   children="${e -> e.children}"
									   labels="${e -> e.name}"/>
							</fieldset>
						</template>
					</a>
				</span>
			</label>
			<label data-size='4'>
				Ícone:
				<span>
					<g:file property="form.icon" tabindex='1' required=''/>
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
			<label data-size='1'>
				Triagem:
				<span>
					<g:select property='form.triagem' tabindex='1'/>
				</span>
			</label>
			<label data-size='1'>
				Temporária:
				<span>
					<g:select property='form.temporaria' tabindex='1'/>
				</span>
			</label>
			<label data-size='1'>
				Sigilosa:
				<span>
					<g:select property='form.sigilosa' tabindex='1'/>
				</span>
			</label>
			<label data-size='1'>
				Projeto:
				<span>
					<g:select property='form.projeto' tabindex='1'/>
				</span>
			</label>
			<label data-size='2'>
				Exige Feedback:
				<span>
					<g:select property='form.feedback' tabindex='1'/>
				</span>
			</label>
			<label data-size='2'>
				Exige Avaliação:
				<span>
					<g:select property='form.avaliacao' tabindex='1'/>
				</span>
			</label>
			<label data-size='2'>
				Nível:
				<span>
					<g:select property='form.nivel' tabindex='1'/>
				</span>
			</label>
			<label data-size='2'>
				Duração:
				<span>
					<g:icon type="gate.type.Duration"/>
					<g:text property='form.duracao' tabindex='1'/>
				</span>
			</label>
			<label data-size='4'>
				Atribuir automaticamente:
				<span>
					<g:select property='form.atribuir.id'
						  options="${equipe.users}"
						  values="${e -> e.id}"
						  required=''
						  tabindex='1'/>
				</span>
			</label>
			<label data-size='4'>
				Encaminhar automaticamente:
				<span>
					<g:hidden id="form.encaminhar.id" property="form.encaminhar.id" required=''/>
					<g:text id="form.encaminhar.name" property="form.encaminhar.name" required='' readonly="readonly"/>
					<g:shortcut module="#" screen="Equipe" action="Search" data-get="form.encaminhar.id, form.encaminhar.name"/>
				</span>
			</label>
			<label data-size="4">
				Exige Aprovação:
				<span>
					<g:hidden id="form.pessoaAprovadora.id" property="form.pessoaAprovadora.id" required=''/>
					<g:hidden id="form.equipeAprovadora.id" property="form.equipeAprovadora.id" required=''/>
					<g:text id="aprovador" property="aprovador" tabindex='1' required='' data-getter="form.aprovador"/>
					<g:shortcut id="form.aprovador" module="#" screen="Ator" action="Search" arguments="form=@{aprovador}"
						    data-get="null, form.pessoaAprovadora.id, form.equipeAprovadora.id, aprovador"/>
				</span>
			</label>
			<label data-size="4">
				Exige Homologação:
				<span>
					<g:hidden id="form.pessoaHomologadora.id" property="form.pessoaHomologadora.id" required=''/>
					<g:hidden id="form.equipeHomologadora.id" property="form.equipeHomologadora.id" required=''/>
					<g:text id="homologador" property="homologador" tabindex='1' required='' data-getter="form.homologador"/>
					<g:shortcut id="form.homologador" module="#" screen="Ator" action="Search" arguments="form=@{homologador}"
						    data-get="null, form.pessoaHomologadora.id, form.equipeHomologadora.id, homologador"/>
				</span>
			</label>
			<label data-size="8">
				Atalho:
				<span>
					<g:text property='form.atalho' tabindex='1'/>
				</span>
			</label>
			<label data-size="8">
				Anexo:
				<span>
					<g:file property='form.anexo.arquivo' tabindex='1' required=""/>
				</span>
			</label>
			<label>
				Descrição:
				<span style='flex-basis: 80px'>
					<g:textarea property='form.descricao' tabindex='1'/>
				</span>
			</label>

			<fieldset style="width: 50%">
				<legend>
					Checklist:
				</legend>
				<label style="width: 100%">
					<span style='flex-basis: calc(100vh - 660px)'>
						<g:textarea property='form.checklist' tabindex='1'/>
					</span>
				</label>
			</fieldset>
			<fieldset style="width: 50%">
				<legend>
					Conclusões:
				</legend>
				<label style="width: 100%">
					<span style='flex-basis: calc(100vh - 660px)'>
						<g:textarea property='form.conclusoes' tabindex='1'/>
					</span>
				</label>
			</fieldset>
		</fieldset>

		<g-coolbar>
			<g:link class="Commit" method="post" module="#" screen="#" action="#" tabindex='2'>
				Concluir<g:icon type='commit'/>
			</g:link>
			<hr/>
			<g:link class="Cancel" module="#" screen="#" action="Select" arguments="form.id=${screen.form.id}" tabindex='3'>
				Desistir<g:icon type='cancel'/>
			</g:link>
		</g-coolbar>

		<g:hidden property='form'/>
		<g:hidden property='form.roles' value=''/>
	</form>

	<script>
		window.addEventListener("load", function ()
		{
			Array.from(document.querySelectorAll("input[type=checkbox]")).forEach(function (element)
			{
				element.addEventListener("change", () =>
					Array.from(element.parentNode.parentNode.querySelectorAll("input"))
						.filter(e => e !== element)
						.forEach(e => e.checked = element.checked));
			});
		});
	</script>
</g:template>