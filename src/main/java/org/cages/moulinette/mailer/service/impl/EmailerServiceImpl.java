package org.cages.moulinette.mailer.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.cages.moulinette.mailer.model.ConfigurationEmail;
import org.cages.moulinette.mailer.model.MessageEmail;
import org.cages.moulinette.mailer.model.PieceJointe;
import org.cages.moulinette.mailer.service.ConfigurationEmailRepository;
import org.cages.moulinette.mailer.service.EmailerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("emailerService")
public class EmailerServiceImpl implements EmailerService {

	private Logger logger = LoggerFactory.getLogger(EmailerServiceImpl.class);
	private ConfigurationEmail configurationEmail;

	@Autowired
	private ConfigurationEmailRepository configurationEmailRepository;

	@Override
	public void envoyer(MessageEmail messageEmail) {
		configurationEmail = configurationEmailRepository.getConfigurationEmailSendInBlue();
		
		Properties properties = new Properties();
		properties.put("mail.smtp.host", configurationEmail.getHost());
		properties.put("mail.smtp.port", configurationEmail.getPort());
		properties.put("mail.smtp.auth", configurationEmail.getAuth());
		properties.put("mail.smtp.starttls.enable", configurationEmail.getEnableStarttls());

		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(configurationEmail.getIdentifiant(), configurationEmail.getMotDePasse());
			}
		};

		Session session = Session.getInstance(properties, authenticator);

		MimeMessage mimeMessage = new MimeMessage(session);

		try {
			mimeMessage.addHeader("Content-type", messageEmail.getHeader().getContentType());
			mimeMessage.addHeader("format", messageEmail.getHeader().getFormat());
			mimeMessage.addHeader("Content-Transfer-Encoding", messageEmail.getHeader().getContentTransferEncoding());

			mimeMessage.setFrom(new InternetAddress("noreply@sigma.sn", "SIGMA [ne_pas_repondre]"));
			mimeMessage.setReplyTo(InternetAddress.parse("noreply@sigma.sn", false));
			mimeMessage.setSubject(messageEmail.getContenuEmail().getObjet(), "UTF-8");
			mimeMessage.setSentDate(new Date());
			
			String destinataire = messageEmail.getDestinataire().getAdresse();
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire, false));

			BodyPart messageBodyPartCorps = new MimeBodyPart();
			messageBodyPartCorps.setContent(messageEmail.getContenuEmail().getCorps(), "text/html; charset=utf-8");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPartCorps);

			for (PieceJointe pieceJointe : messageEmail.getPiecesJointes()) {
				BodyPart messageBodyPartPieceJointe = new MimeBodyPart();
				DataSource dataSource = new FileDataSource(pieceJointe.getCheminAbsoluVersLeFichier());
				messageBodyPartPieceJointe.setDataHandler(new DataHandler(dataSource));
				messageBodyPartPieceJointe.setFileName(dataSource.getName());
				multipart.addBodyPart(messageBodyPartPieceJointe);
			}

			mimeMessage.setContent(multipart);

			Transport.send(mimeMessage);

		} catch (MessagingException messagingException) {
			gereErreur(messageEmail, messagingException);
		} catch (UnsupportedEncodingException unsupportedEncodingException) {
			gereErreur(messageEmail, unsupportedEncodingException);
		}
	}

	// Log en cas d'erreur uniquement
	// Les exceptions ne sont pas propag�es car l'echec d'envoi d'un email ne doit
	// pas etre bloquant pour les process qui envoient des mails
	private void gereErreur(MessageEmail messageEmail, Exception exception) {
		StringBuilder messageErreur = new StringBuilder();
		messageErreur.append("Imposible d'envoyer le mail : ");
		messageErreur.append(messageEmail);
		String messageErreurToString = messageErreur.toString();
		logger.error(messageErreurToString, exception);
	}
}
