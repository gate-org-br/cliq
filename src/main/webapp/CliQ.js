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
		CKEDITOR.config.toolbar =
			[
				{name: 'Font', items: ['Font', 'FontSize']},
				{name: 'Format', items: ['Bold', 'Italic', 'Underline', 'Strike']},
				{name: 'Color', items: ['TextColor', 'BGColor']},
				{name: 'Align', items: ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock']},
				{name: 'Identation', items: ['Outdent', 'Indent']},
				{name: 'Clear', items: ['RemoveFormat']},
				{name: 'List', items: ['NumberedList', 'BulletedList']},
				{name: 'Links', items: ['Link', 'Unlink']},
				{name: 'Insert', items: ['HorizontalRule', 'Table', 'Smiley']}
			];

		var tabindex = e.getAttribute("tabindex");
		if (tabindex)
		{
			e.removeAttribute("tabindex");
			CKEDITOR.config.tabIndex = tabindex;
		}

		if (e.getAttribute("autofocus"))
			e.removeAttribute("autofocus");

		if (document.activeElement === e)
			CKEDITOR.config.startupFocus = true;

		CKEDITOR.config.language = 'pt-br';
		CKEDITOR.config.allowedContent = true;
		CKEDITOR.config.extraPlugins = 'maximize';
		CKEDITOR.config.width = "100%";
		CKEDITOR.config.height = e.parentNode.clientHeight + "px";
		CKEDITOR.config.resize_enabled = false;
		CKEDITOR.replace(e.getAttribute("id"));
	});
});