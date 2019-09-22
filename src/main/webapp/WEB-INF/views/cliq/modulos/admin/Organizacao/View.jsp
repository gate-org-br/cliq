<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<div class='Coolbar'>
		<g:secure module='cliq.modulos.admin' screen='Organizacao' action='Insert'>						
			<a href='Gate?MODULE=cliq.modulos.admin&SCREEN=Organizacao&ACTION=Insert' style='float: left'>
				Novo<i>&#x1002;</i>
			</a>
		</g:secure>
	</div>	
	<g:if condition="${not empty screen.page}">
		<table>
			<col style='width: 100%'/>
		
			<caption>
				ORGANIZA&Ccedil;&Otilde;ES
			</caption>

			<thead>
				<tr>
					<th style='text-align: left;'>
						Nome
					</th>
				</tr>
			</thead>

			<tfoot></tfoot>
			
			<tbody>
				<g:iterator source="${screen.page}" target="organizacao" index="index">
					<tr
					data-action='Gate?MODULE=cliq.modulos.admin&SCREEN=Organizacao&ACTION=Select&form.id=${organizacao.id}'>
						<td style='text-align: left;'>${organizacao.nome}</td>
					</tr>
				</g:iterator>
			</tbody>
		</table>
	</g:if>
</g:template>
