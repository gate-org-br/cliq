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