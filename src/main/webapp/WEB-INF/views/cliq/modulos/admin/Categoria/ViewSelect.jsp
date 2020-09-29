<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<g-tab-control>

		<a href="#" data-selected='${param.tab eq 'Categoria'}'>
			<g:name type="${action}"/>
			<g:icon type="${action}"/>
		</a>
		<div>
			<fieldset>
				<legend>
					<g-path>
						<g:if condition="${not empty screen.form.parent.id}">
							<g:link module="#" screen="#" action="Select"
								arguments="form.id=${screen.form.parent.id}">
								${screen.form.parent.nome}
							</g:link>
						</g:if>
						<g:link module="#" screen="#" action="Select"
							arguments="form.id=${screen.form.id}">
							${screen.form.nome}
							<g:icon type="cliq.entity.Categoria"/>
						</g:link>
					</g-path>
				</legend>
				<label data-size='8'>
					Nome:
					<span>
						<g:label property='form.nome'/>
					</span>
				</label>
				<label data-size='4'>
					Visibilidade:
					<span>
						<g:label property='form.visibilidade'/>
						<a target="_popup" href="#">
							<g:icon type="2000"/>
							<div title='Exceções'>
								<fieldset>
									<g:selectn property="form.roles[]"
										   disabled="disabled"
										   options="${screen.user.role.getRoot()}"
										   children="${e -> e.children}"
										   labels="${e -> e.name}"/>
								</fieldset>
							</div>
						</a>
					</span>
				</label>
				<label data-size='4'>
					Ícone:
					<span>
						<label>
							Clique para baixar
						</label>
						<g:shortcut module="cliq.modulos"
							    screen="Categoria" action="Icon" arguments="form.id=${screen.form.id}"/>
					</span>
				</label>
				<label data-size='2'>
					Prioridade:
					<span>
						<g:label property='form.prioridade'/>
					</span>
				</label>
				<label data-size='2'>
					Complexidade:
					<span>
						<g:label property='form.complexidade'/>
					</span>
				</label>
				<label data-size='1'>
					Triagem:
					<span>
						<g:label property='form.triagem'/>
					</span>
				</label>
				<label data-size='1'>
					Temporária:
					<span>
						<g:label property='form.temporaria'/>
					</span>
				</label>
				<label data-size='1'>
					Sigilosa:
					<span>
						<g:label property='form.sigilosa'/>
					</span>
				</label>
				<label data-size='1'>
					Projeto:
					<span>
						<g:label property='form.projeto'/>
					</span>
				</label>
				<label data-size='2'>
					Exige Feedback
					<span>
						<g:label property="form.feedback"/>
					</span>
				</label>
				<label data-size='2'>
					Exige Avaliação
					<span>
						<g:label property="form.avaliacao"/>
					</span>
				</label>
				<label data-size='2'>
					Nível:
					<span>
						<g:label property="form.nivel" empty="N/A"/>
					</span>
				</label>
				<label data-size='2'>
					Duração:
					<span>
						<g:icon type="gate.type.Duration"/>
						<g:label property="form.duracao" empty="N/A"/>
					</span>
				</label>
				<label data-size='4'>
					Atribuir automaticamente:
					<span>
						<g:label property="form.atribuir.name" empty="N/A"/>
					</span>
				</label>
				<label data-size='4'>
					Encaminhar automaticamente:
					<span>
						<g:label property="form.encaminhar.name" empty="N/A"/>
					</span>
				</label>
				<label data-size='4'>
					Exige Aprovação:
					<span>
						<g:choose>
							<g:when condition="${not empty screen.form.pessoaAprovadora.id}">
								<g:icon type="gate.entity.User"/>
								<g:label property="form.pessoaAprovadora.name"/>
							</g:when>
							<g:when condition="${not empty screen.form.equipeAprovadora.id}">
								<g:icon type="gate.entity.Role"/>
								<g:label property="form.equipeAprovadora.name"/>
							</g:when>
							<g:otherwise>
								<label>
									Não
								</label>
							</g:otherwise>
						</g:choose>
					</span>
				</label>
				<label data-size='4'>
					Exige Homologação:
					<span>
						<g:choose>
							<g:when condition="${not empty screen.form.pessoaHomologadora}">
								<g:icon type="gate.entity.User"/>
								<g:label property="form.pessoaHomologadora.name"/>
							</g:when>
							<g:when condition="${not empty screen.form.equipeHomologadora}">
								<g:icon type="gate.entity.Role"/>
								<g:label property="form.equipeHomologadora.name"/>
							</g:when>
							<g:otherwise>
								<label>
									Não
								</label>
							</g:otherwise>
						</g:choose>
					</span>
				</label>
				<label data-size="8">
					Atalho:
					<span>
						<g:label property="form.atalho" empty="N/A"/>
					</span>
				</label>
				<label data-size="8">
					Anexo:
					<span>
						<g:choose>
							<g:when condition="${not empty screen.form.anexo.id}">
								<label>
									Baixar
								</label>
								<g:shortcut module="cliq.modulos" screen="Anexo"
									    arguments="form.id=${screen.form.anexo.id}"/>
							</g:when>
							<g:otherwise>
								N/A
							</g:otherwise>
						</g:choose>
					</span>
				</label>
				<label>
					Descrição:
					<span style='flex-basis: 80px'>
						<g:label property='form.descricao' empty="N/A"/>
					</span>
				</label>
				<fieldset style="width: 50%">
					<legend>
						Checklist<g:icon type="commit"/>
					</legend>
					<div  style="height: calc(100vh - 705px); overflow: auto;">
						<g:if condition="${not empty screen.form.checklist}">
							<g:list options="${screen.form.checklist}"
								labels="${e -> e.nome}"/>
						</g:if>
					</div>
				</fieldset>
				<fieldset style="width: 50%">
					<legend>
						Conclusões<g:icon type="1010"/>
					</legend>
					<div  style="height: calc(100vh - 705px); overflow: auto;">
						<g:if condition="${not empty screen.form.conclusoes}">
							<g:list options="${screen.form.conclusoes}"/>
						</g:if>
					</div>
				</fieldset>
			</fieldset>

			<g-coolbar>
				<g:link module="#" screen="#" action="Update" arguments="form.id=${screen.form.id}" tabindex='1'/>
				<g:link module="#" screen="#" action="Delete" arguments="form.id=${screen.form.id}" tabindex='1'
					data-confirm='Tem certeza de que deseja remover o registro?'/>
				<hr/>
				<g:link condition="${not empty screen.form.parent.id}"
					class="Cancel" module="#" screen="#" action="Select"
					arguments="form.id=${screen.form.parent.id}" tabindex='2'>
					Retornar<g:icon type="return"/>
				</g:link>
				<g:link condition="${empty screen.form.parent.id}" class="Cancel" module="#" screen="#"
					arguments="form.role.id=${screen.form.role.id}" tabindex='2'>
					Retornar<g:icon type="return"/>
				</g:link>
			</g-coolbar>
		</div>

		<a href="#" data-selected='${param.tab eq 'Formulario'}'>
			Formulário<g:icon type="gate.type.Form"/>
		</a>
		<div>
			<div class="PageControl" data-type="Fetch">
				<ul>
					<li style="width: 200px">
						<a href="#">
							Definir Campos<g:icon type="2057"/>
						</a>
						<div>
							<form method='POST' action='Gate?MODULE=cliq.modulos.admin&SCREEN=Categoria&ACTION=Campos&tab=Formulario'>
								<fieldset style='width: 100%'>
									<label data-size='2'>
										Título:
										<span>
											<select name='this.form.campos[]' data-method='post' data-action='Gate?MODULE=cliq.modulos.admin&SCREEN=Categoria&ACTION=Campos&tab=Formulario'>
												<option value=''>N/A</option>
												<option value='@titulo' ${screen.form.campos.contains("@titulo") ? "selected='selected'" : ""}>Opcional</option>
												<option value='#titulo' ${screen.form.campos.contains("#titulo") ? "selected='selected'" : ""}>Requerido</option>
											</select>
										</span>
									</label>
									<label data-size='2'>
										Descrição:
										<span>
											<select name='this.form.campos[]' data-method='post' data-action='Gate?MODULE=cliq.modulos.admin&SCREEN=Categoria&ACTION=Campos&tab=Formulario'>
												<option value=''>N/A</option>
												<option value='@descricao' ${screen.form.campos.contains("@descricao") ? "selected='selected'" : ""}>Opcional</option>
												<option value='#descricao' ${screen.form.campos.contains("#descricao") ? "selected='selected'" : ""}>Requerido</option>
											</select>
										</span>
									</label>
									<label data-size='2'>
										Prioridade:
										<span>
											<select name='this.form.campos[]' data-method='post' data-action='Gate?MODULE=cliq.modulos.admin&SCREEN=Categoria&ACTION=Campos&tab=Formulario'>
												<option value=''>N/A</option>
												<option value='@prioridade' ${screen.form.campos.contains("@prioridade") ? "selected='selected'" : ""}>Opcional</option>
												<option value='#prioridade' ${screen.form.campos.contains("#prioridade") ? "selected='selected'" : ""}>Requerido</option>
											</select>
										</span>
									</label>
									<label data-size='2'>
										Complexidade:
										<span>
											<select name='this.form.campos[]' data-method='post' data-action='Gate?MODULE=cliq.modulos.admin&SCREEN=Categoria&ACTION=Campos&tab=Formulario'>
												<option value=''>N/A</option>
												<option value='@complexidade' ${screen.form.campos.contains("@complexidade") ? "selected='selected'" : ""}>Opcional</option>
												<option value='#complexidade' ${screen.form.campos.contains("#complexidade") ? "selected='selected'" : ""}>Requerido</option>
											</select>
										</span>
									</label>
									<label data-size='2'>
										Prazo de Resposta:
										<span>
											<select name='this.form.campos[]' data-method='post' data-action='Gate?MODULE=cliq.modulos.admin&SCREEN=Categoria&ACTION=Campos&tab=Formulario'>
												<option value=''>N/A</option>
												<option value='@prazoResposta' ${screen.form.campos.contains("@prazoResposta") ? "selected='selected'" : ""}>Opcional</option>
												<option value='#prazoResposta' ${screen.form.campos.contains("#prazoResposta") ? "selected='selected'" : ""}>Requerido</option>
											</select>
										</span>
									</label>
									<label data-size='2'>
										Prazo de Solução:
										<span>
											<select name='this.form.campos[]' data-method='post' data-action='Gate?MODULE=cliq.modulos.admin&SCREEN=Categoria&ACTION=Campos&tab=Formulario'>
												<option value=''>N/A</option>
												<option value='@prazoSolucao' ${screen.form.campos.contains("@prazoSolucao") ? "selected='selected'" : ""}>Opcional</option>
												<option value='#prazoSolucao' ${screen.form.campos.contains("#prazoSolucao") ? "selected='selected'" : ""}>Requerido</option>
											</select>
										</span>
									</label>
									<label data-size='2'>
										Anexo:
										<span>
											<select name='this.form.campos[]' data-method='post' data-action='Gate?MODULE=cliq.modulos.admin&SCREEN=Categoria&ACTION=Campos&tab=Formulario'>
												<option value=''>N/A</option>
												<option value='@arquivo' ${screen.form.campos.contains("@arquivo") ? "selected='selected'" : ""}>Opcional</option>
												<option value='#arquivo' ${screen.form.campos.contains("#arquivo") ? "selected='selected'" : ""}>Requerido</option>
											</select>
										</span>
									</label>
									<label data-size='2'>
										Local de Origem:
										<span>
											<select name='this.form.campos[]' data-method='post' data-action='Gate?MODULE=cliq.modulos.admin&SCREEN=Categoria&ACTION=Campos&tab=Formulario'>
												<option value=''>N/A</option>
												<option value='@origem' ${screen.form.campos.contains("@origem") ? "selected='selected'" : ""}>Opcional</option>
												<option value='#origem' ${screen.form.campos.contains("#origem") ? "selected='selected'" : ""}>Requerido</option>
											</select>
										</span>
									</label>
								</fieldset>
								<fieldset style='width: 100%'>
									<table class='c1 c2 c3 c4 c5 c6 c7 c8 c9 c10'>
										<caption>
											CAMPOS CUSTOMIZADOS
										</caption>
										<thead>
											<tr>
												<th>
													Nome
												</th>
												<th>
													Colunas
												</th>
												<th>
													Requerido
												</th>
												<th>
													Múltiplo
												</th>
												<th>
													Tam Max
												</th>
												<th>
													Padrão
												</th>
												<th>
													Máscara
												</th>
												<th>
													Opções
												</th>
												<th>
													Valor
												</th>
												<th>
													Descrição
												</th>
											</tr>
										</thead>
										<g:iterator source="${screen.form.formulario.fields}" target="field" index="index">
											<tbody>
												<tr data-method='get'
												    data-target='_dialog'
												    title='${field.name}'
												    data-field="${index}"
												    data-action='Gate?MODULE=${MODULE}&SCREEN=Field&ACTION=Select&categoria.id=${screen.form.id}&index=${index}'>
													<td>
														<g:print value='${field.name}'/>
													</td>
													<td>
														<g:print value='${field.size}'/>
													</td>
													<td>
														<g:print value='${field.required}'/>
													</td>
													<td>
														<g:print value='${field.multiple}'/>
													</td>
													<td>
														<g:print value='${field.maxlength}' empty="N/A"/>
													</td>
													<td>
														<g:print value='${field.pattern}' empty="N/A"/>
													</td>
													<td>
														<g:print value='${field.mask}' empty="N/A"/>
													</td>
													<td>
														<g:print value='${field.options}' empty="N/A"/>
													</td>
													<td>
														<g:print value='${field.value}' empty="N/A"/>
													</td>
													<td>
														<g:print value='${field.description}' empty="N/A"/>
													</td>
												</tr>
											</tbody>
										</g:iterator>
									</table>
								</fieldset>
								<g:hidden property="form"/>
								<g:hidden property="form.campos" value=""/>
							</form>

							<div class='Coolbar'>
								<g:link target='_dialog' module="#" screen="Field" action="Insert"
									arguments="categoria.id=${screen.form.id}" title='Novo Campo'>
									Novo Campo<g:icon type="gate.type.Field"/>
								</g:link>
							</div>
						</div>
					</li>
					<li style="width: 200px">
						<a href="#">
							Preview<g:icon type="2055"/>
						</a>
						<div>
							<form method='POST' action='#'>
								<cliq:formulario categoria="${screen.form}" form="chamado"/>
							</form>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<a href='Gate?MODULE=cliq.modulos.admin&SCREEN=Categoria&ACTION=Import&form.id=${screen.form.id}'
		   data-selected='${param.tab eq 'Subcategoria'}'>
			Subcategorias<g:icon type="2014"/>
		</a>
	</div>

	<script type='text/javascript'>
		window.addEventListener("load", function ()
		{
			Array.from(document.querySelectorAll("tr[data-field]")).forEach(function (element)
			{
				element.draggable = true;
				element.ondragover = event => event.preventDefault();
				element.ondragstart = event => event.dataTransfer.setData("index", element.getAttribute("data-field"));
				element.ondrop = event => new URL("Gate")
						.setModule("${MODULE}")
						.setScreen("Field")
						.setAction("Move")
						.setParameter("categoria.id", "${screen.form.id}")
						.setParameter("index", event.dataTransfer.getData("index"))
						.setParameter("target", element.getAttribute("data-field"))
						.go();
			});
		});
	</script>
</g:template>