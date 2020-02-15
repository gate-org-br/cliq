<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>
<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>

<form method="POST" action="#" enctype='multipart/form-data' style="margin-bottom: 12px">
	<g:if condition="${screen.form.pendencia eq 'APROVACAO' 
			   and (screen.form.pessoaAprovadora.id eq screen.user.id 
			   or screen.form.equipeAprovadora.id eq screen.user.role.id)}">
	      <div class="Panel">
		      <fieldset>
			      <legend>
				      <g:icon type="${screen.form.pendencia}"/>
				      <g:description type="${screen.form.pendencia}"/>
			      </legend>
			      <label>
				      <span class="Editor">
					      <g:textarea id="textarea" property="form.evento.observacoes" tabindex='1'/>
				      </span>
			      </label>
			      <label>
				      <span>
					      <g:file property="form.evento.anexo.arquivo" tabindex='1' value='' required=""/>
				      </span>
			      </label>
		      </fieldset>

		      <div class='Coolbar'>
			      <g:link method="post" module="#" screen="#" action="AceitarAprovacao" arguments="form.id=${screen.form.id}"
				      data-confirm='Tem certeza de que deseja aceitar a aprovação do chamado?'/>
			      <g:link method="post" module="#" screen="#" action="RecusarAprovacao" arguments="form.id=${screen.form.id}"
				      data-confirm='Tem certeza de que deseja recusar a aprovação do chamado?'/>
			      <a class='Cancel Hide' href="#" tabindex='3'>
				      Fechar<g:icon type="cancel"/>
			      </a>
		      </div>
	      </div>
	</g:if>
	<g:if condition="${screen.form.pendencia eq 'COMPLEMENTACAO' and screen.form.solicitante.id eq screen.user.id}">
		<div class="Panel">
			<fieldset>
				<legend>
					<g:icon type="${screen.form.pendencia}"/>
					<g:description type="${screen.form.pendencia}"/>
				</legend>
				<label>
					<span class="Editor">
						<g:textarea id="textarea" property="form.evento.observacoes" tabindex='1'/>
					</span>
				</label>
				<label>
					<span>
						<g:file property="form.evento.anexo.arquivo" tabindex='1' value='' required=""/>
					</span>
				</label>
			</fieldset>

			<div class='Coolbar'>
				<g:link method="post" module="#" screen="#" action="Complementar" arguments="form.id=${screen.form.id}"/>
				<a class='Cancel Hide' href="#" tabindex='3'>
					Fechar<g:icon type="cancel"/>
				</a>
			</div>
		</div>
	</g:if>
	<g:if condition="${screen.form.pendencia eq 'HOMOLOGACAO' 
			   and (screen.form.pessoaHomologadora.id eq screen.user.id 
			   or screen.form.equipeHomologadora.id eq screen.user.role.id)}">
	      <div class="Panel">
		      <fieldset>
			      <legend>
				      <g:icon type="${screen.form.pendencia}"/>
				      <g:description type="${screen.form.pendencia}"/>
			      </legend>
			      <label>
				      <span class="Editor">
					      <g:textarea id="textarea" property="form.evento.observacoes" tabindex='1'/>
				      </span>
			      </label>
			      <label>
				      <span>
					      <g:file property="form.evento.anexo.arquivo" tabindex='1' value='' required=""/>
				      </span>
			      </label>
		      </fieldset>

		      <div class='Coolbar'>
			      <g:link method="post" module="#" screen="#" action="AceitarHomologacao" arguments="form.id=${screen.form.id}"
				      data-confirm='Tem certeza de que deseja aceitar a homologação do chamado?'/>
			      <g:link method="post" module="#" screen="#" action="RecusarHomologacao" arguments="form.id=${screen.form.id}"
				      data-confirm='Tem certeza de que deseja recusar a homologação do chamado?'/>
			      <a class='Cancel Hide' href="#" tabindex='3'>
				      Fechar<g:icon type="cancel"/>
			      </a>
		      </div>
	      </div>
	</g:if>
	<g:if condition="${screen.form.pendencia eq 'FEEDBACK' and screen.form.solicitante.id eq screen.user.id}">
		<div class="Panel">
			<fieldset>
				<legend>
					<g:icon type="${screen.form.pendencia}"/>
					<g:description type="${screen.form.pendencia}"/>
				</legend>
				<label>
					<span class="Editor">
						<g:textarea id="textarea" property="form.evento.observacoes" tabindex='1'/>
					</span>
				</label>
				<label>
					<span>
						<g:file property="form.evento.anexo.arquivo" tabindex='1' value='' required=""/>
					</span>
				</label>
			</fieldset>

			<div class='Coolbar'>
				<g:link method="post" module="#" screen="#" action="AceitarFeedback" arguments="form.id=${screen.form.id}"
					data-confirm='Tem certeza de que deseja aceitar a solução do chamado?'/>
				<g:link method="post" module="#" screen="#" action="RecusarFeedback" arguments="form.id=${screen.form.id}"
					data-confirm='Tem certeza de que deseja recusar a solução do chamado?'/>
				<a class='Cancel Hide' href="#" tabindex='3'>
					Fechar<g:icon type="cancel"/>
				</a>
			</div>
		</div>
	</g:if>
	<g:if condition="${screen.form.pendencia eq 'AVALIACAO' and screen.form.solicitante.id eq screen.user.id}">
		<div class="Panel">
			<fieldset>
				<legend>
					<g:icon type="${screen.form.pendencia}"/>
					<g:description type="${screen.form.pendencia}"/>
				</legend>
				<label>
					<span class="Editor">
						<g:textarea id="textarea" property="form.evento.observacoes" tabindex='1'/>
					</span>
				</label>
				<label>
					<span>
						<g:file property="form.evento.anexo.arquivo" tabindex='1' value='' required=""/>
					</span>
				</label>
			</fieldset>

			<div class='Coolbar'>
				<g:values source="cliq.entity.Chamado$Nota" target="nota" reverse="true">
					<g:link method="post" module="#" screen="#" action="Avaliar"
						arguments="form.id=${screen.form.id}&form.nota=${nota.ordinal()}"
						class='R' style='color: ${nota.color}'
						data-confirm='Tem certeza de que deseja avaliar o serviço prestado como ${g:print(nota)}?'>
						<g:print value="${nota}"/><g:icon type="${nota}"/>
					</g:link>
				</g:values>
				<a class='Cancel Hide' href="#" tabindex='3'>
					Fechar<g:icon type="cancel"/>
				</a>
			</div>
		</div>
	</g:if>
</form>
