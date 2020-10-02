<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<form method='POST' action='#' enctype='multipart/form-data'>
		<fieldset>
			<legend>
				<g:path/>
			</legend>
			<label data-size="8">
				Categoria:
				<span>
					<g:hidden id="form.id" property="form.id" required=""/>
					<g:text id="form.nome" property="form.nome" required=""
						readonly="readonly"/>
					<g:shortcut module="#" screen="Categoria" action="Search"
						    data-get='form.id, form.nome'
						    tabindex='1' title='Selecionar Caregoria'>
						<g:icon type="search"/>
					</g:shortcut>
				</span>
			</label>
			<label data-size="8">
				Arquivo:
				<span>
					<g:file property="form.anexo.arquivo" tabindex='1'/>
				</span>
			</label>
		</fieldset>

		<g-coolbar>
			<g:link class="Commit" method="post" module="#" screen="#" action="#" tabindex='2'>
				Concluir<g:icon type='commit'/>
			</g:link>
			<hr/>
			<g:link class="Cancel" module="#" screen="#" arguments="form.role.id=${screen.form.role.id}" tabindex='3'>
				Desistir<g:icon type='cancel'/>
			</g:link>
		</g-coolbar>
	</form>

	<div class="TEXT">
		<h1>
			Formato do arquivo CSV
		</h1>
		<p>
			A codificação do arquivo CSV deve ser <strong>UTF-8</strong>, o separador das colunas deve ser o <strong>ponto e vírgula (;)</strong>
			e o delimitador de texto deve ser <strong>áspas duplas (")</strong>.
		</p>

		<table class="c2 c3 c4">
			<caption>
				CAMPOS
			</caption>
			<thead>
				<tr>
					<th>
						Campo
					</th>
					<th style="width: 200px">
						Requerido
					</th>
					<th style="width: 200px">
						Formato
					</th>
					<th style="width: 200px">
						Padrão
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						Nome
					</td>
					<td>
						Sim
					</td>
					<td>
						N/A
					</td>
					<td>
						N/A
					</td>
				</tr>
				<tr>
					<td>
						Visibilidade
					</td>
					<td>
						Não
					</td>
					<td>
						<ul style="text-align: left">
							<li>Pública</li>
							<li>Privada</li>
						</ul>
					</td>
					<td>
						Pública
					</td>
				</tr>
				<tr>
					<td>
						Prioridade
					</td>
					<td>
						Não
					</td>
					<td>
						<ul style="text-align: left">
							<li>Baixa</li>
							<li>Média</li>
							<li>Alta</li>
							<li>Urgente</li>
						</ul>
					</td>
					<td>
						Média
					</td>
				</tr>
				<tr>
					<td>
						Complexidade
					</td>
					<td>
						Não
					</td>
					<td>
						<ul style="text-align: left">
							<li>Baixa</li>
							<li>Média</li>
							<li>Alta</li>
							<li>Crítica</li>
						</ul>
					</td>
					<td>
						Média
					</td>
				</tr>

				<tr>
					<td>
						Triagem
					</td>
					<td>
						Não
					</td>
					<td>
						<ul style="text-align: left">
							<li>Sim</li>
							<li>Não</li>
						</ul>
					</td>
					<td>
						Não
					</td>
				</tr>

				<tr>
					<td>
						Temporária
					</td>
					<td>
						Não
					</td>
					<td>
						<ul style="text-align: left">
							<li>Sim</li>
							<li>Não</li>
						</ul>
					</td>
					<td>
						Não
					</td>
				</tr>

				<tr>
					<td>
						Sigilosa
					</td>
					<td>
						Não
					</td>
					<td>
						<ul style="text-align: left">
							<li>Sim</li>
							<li>Não</li>
						</ul>
					</td>
					<td>
						Não
					</td>
				</tr>

				<tr>
					<td>
						Projeto
					</td>
					<td>
						Não
					</td>
					<td>
						<ul style="text-align: left">
							<li>Sim</li>
							<li>Não</li>
						</ul>
					</td>
					<td>
						Não
					</td>
				</tr>

				<tr>
					<td>
						Exige Feedback
					</td>
					<td>
						Não
					</td>
					<td>
						<ul style="text-align: left">
							<li>Sim</li>
							<li>Não</li>
						</ul>
					</td>
					<td>
						Não
					</td>
				</tr>

				<tr>
					<td>
						Exige Avaliação
					</td>
					<td>
						Não
					</td>
					<td>
						<ul style="text-align: left">
							<li>Sim</li>
							<li>Não</li>
						</ul>
					</td>
					<td>
						Não
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</g:template>