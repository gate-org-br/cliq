/* global customElements */

class Alerta extends HTMLElement
{
	constructor()
	{
		super();
	}

	connectedCallback()
	{
		var url = new URL("Gate").setModule("cliq.modulos").setScreen("Alerta");
		this.setAttribute("value", url.get());
		window.setInterval(() => this.setAttribute("value", url.get()), 30000);
	}
}

customElements.define('cliq-alerta', Alerta);
window.addEventListener("load", function ()
{
	Array.from(document.querySelectorAll("table.Atendimentos > thead > tr > th > input")).forEach(function (e)
	{
		e.addEventListener("click", event => event.stopPropagation());
		e.addEventListener("change", () => e.parentNode.parentNode
				.parentNode.parentNode.setAttribute("checked", e.checked));
	});
});
window.addEventListener("load", function ()
{
	Array.from(document.querySelectorAll("span.Editor > textarea")).forEach(function (e)
	{
		ClassicEditor.create(e,
			{

				toolbar: {
					items: [
						'bold',
						'underline',
						'italic',
						'link',
						'removeFormat',
						'|',
						'bulletedList',
						'numberedList'
					]
				},
				language: 'pt-br'

			}).then(editor =>
		{
			editor.editing.view.change(writer =>
			{
				writer.setStyle('width', e.parentNode.clientWidth + "px", editor.editing.view.document.getRoot());
				writer.setStyle('height', e.parentNode.clientHeight + "px", editor.editing.view.document.getRoot());
				editor.editing.view.focus();
			});
		}).catch(error => console.error(error));
	});
});