<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<%@ attribute name="form" required="true" type="java.lang.String"%>
<%@ attribute name="categoria" required="true" type="cliq.entity.Categoria"%>

<fieldset>
	<g:hidden property="${form}.categoria.id"/>
	<legend style='padding: 5px;'>
		<img src='Gate?MODULE=cliq.modulos&SCREEN=Categoria&ACTION=Icon&${form}.id=${categoria.id}'
		     style='width: 20px; height: 20px; vertical-align: middle;'/>
		${categoria.nome}
	</legend>

	<g:choose>
		<g:when condition="${categoria.campos.contains('@titulo')}">
			<label>
				Título:
				<span>
					<g:text property="${form}.titulo" tabindex='1' required=''/>
				</span>
			</label>
		</g:when>
		<g:when condition="${categoria.campos.contains('#titulo')}">
			<label>
				Título:
				<span>
					<g:text property="${form}.titulo" tabindex='1' required='required'/>
				</span>
			</label>
		</g:when>
	</g:choose>
	<g:choose>
		<g:when condition='${categoria.campos.contains("@prioridade")}'>
			<label data-size='4'>
				Prioridade:
				<span>
					<g:select property="${form}.prioridade" required=''/>
				</span>
			</label>
		</g:when>
		<g:when condition='${categoria.campos.contains("#prioridade")}'>
			<label data-size='4'>
				Prioridade:
				<span>
					<g:select property="${form}.prioridade"/>
				</span>
			</label>
		</g:when>
	</g:choose>
	<g:choose>
		<g:when condition='${categoria.campos.contains("@complexidade")}'>
			<label data-size='4'>
				Complexidade:
				<span>
					<g:select property="${form}.complexidade" required=''/>
				</span>
			</label>
		</g:when>
		<g:when condition='${categoria.campos.contains("#complexidade")}'>
			<label data-size='4'>
				Complexidade:
				<span>
					<g:select property="${form}.complexidade"/>
				</span>
			</label>
		</g:when>
	</g:choose>
	<g:choose>
		<g:when condition='${categoria.campos.contains("@prazoResposta")}'>
			<label data-size='4'>
				Prazo de resposta:
				<span>
					<g:text property="${form}.prazoResposta"/>
				</span>
			</label>
		</g:when>
		<g:when condition='${categoria.campos.contains("#prazoResposta")}'>
			<label data-size='4'>
				Prazo de resposta:
				<span>
					<g:text property="${form}.prazoResposta" required='required'/>
				</span>
			</label>
		</g:when>
	</g:choose>
	<g:choose>
		<g:when condition='${categoria.campos.contains("@prazoSolucao")}'>
			<label data-size='4'>
				Prazo de solução:
				<span>
					<g:text property="${form}.prazoSolucao"/>
				</span>
			</label>
		</g:when>
		<g:when condition='${categoria.campos.contains("#prazoSolucao")}'>
			<label data-size='4'>
				Prazo de solução:
				<span>
					<g:text property="${form}.prazoSolucao" required='required'/>
				</span>
			</label>
		</g:when>
	</g:choose>

	<g:choose>
		<g:when condition="${categoria.campos.contains('@origem')}">
			<label>
				Local de origem:
				<span>
					<g:select property="${form}.origem.id"
						  options="${screen.user.role}" 
						  children="${e -> e.children}" 
						  labels="${e -> e.name}"
						  values="${e -> e.id}"
						  title='Selecione o local de origem.'
						  tabindex='1' />
				</span>
			</label>
		</g:when>
		<g:when condition="${categoria.campos.contains('#origem')}">
			<label>
				Local de origem:
				<span>
					<g:select property="${form}.origem.id"
						  options="${screen.user.role}" 
						  children="${e -> e.children}" 
						  labels="${e -> e.name}"
						  values="${e -> e.id}"
						  title='Selecione o local de origem.'
						  tabindex='1'
						  required="required"/>
				</span>
			</label>
		</g:when>
	</g:choose>

	<g:form property="${form}.formulario" tabindex='1'/>

	<label>
		Descrição:
		<span class="Editor">
			<g:textarea id="${form}.descricao"
				    property="${form}.descricao"
				    tabindex="1"/>
		</span>
	</label>

	<g:choose>
		<g:when condition="${categoria.campos.contains('@arquivo')}">
			<label>
				Anexo:
				<span>
					<g:file property="${form}.anexo.arquivo" tabindex='1' title='Selecione o anexo.' required=""/>
				</span>
			</label>
		</g:when>
		<g:when condition="${categoria.campos.contains('#arquivo')}">
			<label>
				Anexo:
				<span>
					<g:file property="${form}.anexo.arquivo" tabindex='1' title='Selecione o anexo.' required='required'/>
				</span>
			</label>
		</g:when>
	</g:choose>
</fieldset>