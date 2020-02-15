<%@ tag language="java" pageEncoding="UTF-8"  body-content="empty"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="cliq" %>
<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<%@ attribute name="chamado" required="true" type="cliq.entity.Chamado"%>

<g:choose>
	<g:when condition="${not empty chamado.categoria.anexo.id and not empty chamado.anexo.id}">
		<fieldset>
			<legend>
				Anexos<g:icon type="attach"/>
			</legend>
			<label data-size="8">
				<span>
					<label>
						Anexo 1
					</label>
					<g:shortcut  module="cliq.modulos" screen="Anexo"
						     arguments="form.id=${chamado.anexo.id}"/>
				</span>
			</label>
			<label data-size="8">
				<span>
					<label>
						Anexo 2
					</label>
					<g:shortcut module="cliq.modulos" screen="Anexo"
						    arguments="form.id=${chamado.categoria.anexo.id}"/>
				</span>
			</label>
		</fieldset>
	</g:when>
	<g:when condition="${not empty chamado.categoria.anexo.id}">
		<fieldset>
			<legend>
				Anexos
			</legend>
			<label>
				<span>
					<label>
						Baixar
					</label>
					<g:shortcut module="cliq.modulos" screen="Anexo"
						    arguments="form.id=${chamado.categoria.anexo.id}"/>
				</span>
			</label>
		</fieldset>
	</g:when>
	<g:when condition="${not empty chamado.anexo.id}">
		<fieldset>
			<legend>
				Anexos
			</legend>
			<label>
				<span>
					<label>
						Baixar
					</label>
					<g:shortcut  module="cliq.modulos" screen="Anexo"
						     arguments="form.id=${chamado.anexo.id}"/>
				</span>
			</label>
		</fieldset>
	</g:when>
</g:choose>