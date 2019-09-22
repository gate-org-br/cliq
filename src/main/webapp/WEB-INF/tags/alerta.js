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