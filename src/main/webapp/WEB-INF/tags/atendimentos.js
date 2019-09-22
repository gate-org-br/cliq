window.addEventListener("load", function ()
{
	Array.from(document.querySelectorAll("table.Atendimentos > thead > tr > th > input")).forEach(function (e)
	{
		e.addEventListener("click", event => event.stopPropagation());
		e.addEventListener("change", () => e.parentNode.parentNode
				.parentNode.parentNode.setAttribute("checked", e.checked));
	});
});