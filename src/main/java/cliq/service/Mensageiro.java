package cliq.service;

import cliq.control.MensageiroControl;
import cliq.entity.Chamado;
import gate.entity.User;
import gate.lang.template.Template;
import gate.messaging.MessageException;
import gate.messaging.Messenger;
import gate.type.mime.MimeList;
import gate.type.mime.MimeMail;
import gate.type.mime.MimeText;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class Mensageiro
{

	@Inject
	private Messenger messenger;

	@Inject
	private MensageiroControl control;

	private static final Template INTERNO = Template.compile(Mensageiro.class.getResource("Mensageiro.Interno.gtf"));
	private static final Template PUBLICO = Template.compile(Mensageiro.class.getResource("Mensageiro.Publico.gtf"));

	public void post(@Observes Chamado chamado)
	{
		try
		{
			String titulo = "Chamado " + chamado.getId() + ": " + chamado.getEvento().getTipo() + " - " + chamado.getTitulo();
			MimeMail<MimeList> publico = MimeMail.of(titulo, new MimeText("text", "html", "utf-8", PUBLICO.evaluate(chamado)));
			MimeMail<MimeList> interno = MimeMail.of(titulo, new MimeText("text", "html", "utf-8", INTERNO.evaluate(chamado)));

			if (chamado.getLocalizacao().getEmail() != null)
				messenger.post(chamado.getLocalizacao().getEmail(), interno);

			for (User user : control.search(chamado))
				if (!user.equals(chamado.getEvento().getUser()))
					if (user.equals(chamado.getAtendente())
						|| user.getRole().equals(chamado.getLocalizacao()))
						messenger.post(user.getEmail(), interno);
					else
						messenger.post(user.getEmail(), publico);
		} catch (RuntimeException | MessageException ex)
		{
			Logger.getLogger(Mensageiro.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
