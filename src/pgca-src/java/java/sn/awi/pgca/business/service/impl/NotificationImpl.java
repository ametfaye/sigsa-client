package sn.awi.pgca.business.service.impl;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import sn.awi.pgca.business.dao.ModelDAO;
import sn.awi.pgca.business.service.ICommonService;
import sn.awi.pgca.business.service.IProgrammeAgricol;
import sn.awi.pgca.business.service.ITresorerieService;
import sn.awi.pgca.business.service.Inotification;
import sn.awi.pgca.dataModel.Utilisateur;
import sn.awi.pgca.web.bean.ConstantPGCA;
import sn.awi.pgca.web.dto.AlertesInformationsDTO;
import sn.awi.pgca.web.dto.CoupleDTO;
import sn.awi.pgca.web.dto.CreditDTO;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.MiseEnplaceAgregation;
import sn.awi.pgca.web.dto.MiseEnplaceDTOPointDeVente;
import sn.awi.pgca.web.dto.UtilisateurDTO;
import sn.awi.pgca.web.dto.VentesDTO;
import sn.awi.pgca.web.dto.VersementBanqueDTO;
import sn.awi.pgca.web.export.CreatePdfFrorReportActivitesParMail;

@Named("notificationService")
public class NotificationImpl implements Inotification , Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5894249848743013118L;

	/**
	 * Logger.
	 */
	private static final Log LOG = LogFactory.getLog(NotificationImpl.class);

	@Inject
	private ModelDAO modelDAO;
	
	
	@Autowired
	private ICommonService commonService;
	
	@Autowired
	private IProgrammeAgricol programmAgricoleService;
	
	@Autowired
	private ITresorerieService tresorerieService;
	

	@Override
	public boolean sendEmailWithAttachement(String subject, String message, File attachement) {

		try {
			
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(attachement.getAbsolutePath());
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("Sedab Notification");
			attachment.setName("Sedab Siege");

			// Create the email message
			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(587);
			email.setAuthenticator(new DefaultAuthenticator("sedabsenegal", "asock2mf"));
			email.setSSLOnConnect(true);
			email.addTo("ametfaye@gmail.com");
			email.setFrom("sedabsenegale@gmail.com");
			email.setSubject(subject);
			email.setMsg(message);
			email.setCharset("utf-8");


			// add the attachment
			// email.attach(attachment);

			// SEND IMAGE HTML

			URL url = new URL(ConstantPGCA.LDIRECTORY_PATH_HEADER_MAIL); /*DIRECTORY_PATH_HEADER_MAIL*/
			String cid = email.embed(url, "eSADAB");

			URL urlLogo = new URL(ConstantPGCA.LDIRECTORY_PATH_TMP_PDF); /*LDIRECTORY_PATH_FOOTER_PDF  */

			String logo = email.embed(urlLogo, "Logo SEDAB");

			email.setHtmlMsg(
					"<div style=\"background: #daded7; margin-right: 1%; border-bottom: 1px solid #afdd89;\"><img style=\" margin-left: 10%; width: 80%;\"   src=\"cid:"
							+ cid + "\"> <h3 style=\"text-align : center ; color : #1f1e1d\">" + subject
							+ "</h3>  </div> " +

							  message +

							"<div style=\"margin-right: 1%;  border-bottom: 1px solid #983629;\" >    <img style=\"margin-left: 30%; width : 30%\"   src=\"cid:"
							+ logo + "\"> "
							+ "<br/> Vous recevez ce message  car vous êtes enregistrés  en tant que manager dans la liste de diffusion SEDAB-NOTIF. <br/> Pour ne plus recevoir ces notifications, veuillez  contacter l\'administrateur e-SEDAB à l\'adresse admin@esedab.com pour lui demander de vous retirer de la liste de diffusion.<br/><br/></div> <br/>SEDAB - SARL   <span style=\"float : right \"> Beau Rivage - 5, Bd du Centenaire de la Commune, Dakar</span><br/>  <span style=\"float : right \"> TEL :  +221 832 56 09  --  FAX : +221 832 27 94</span> </div>");

			email.send();
			return true;

		} catch (Exception e) {
			LOG.error("Une erreur est surveneue pendant l'envoi du mail :  " + e.getMessage());
			return false;
		}
	}

	
	
	
	
	@Override
	public boolean sendEmailWithoutAttachement(String emailDest , String subject, String message) {

		try {

			// Create the email message
			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(587);
			email.setAuthenticator(new DefaultAuthenticator("sedabsenegal", "asock2mf"));
			email.setSSLOnConnect(true);
			email.addTo(emailDest);
			email.setFrom("sedabsenegale@gmail.com");
			email.setSubject(subject);
			email.setMsg(message);
			email.setCharset("utf-8");


			
			URL url = new URL(ConstantPGCA.LDIRECTORY_PATH_HEADER_MAIL);
			String cid = email.embed(url, "eSADAB");

			URL urlLogo = new URL(ConstantPGCA.LDIRECTORY_PATH_HEADER_MAIL);

			String logo = email.embed(urlLogo, "Logo SEDAB");

			email.setHtmlMsg(
					"<div style=\"background: #daded7; margin-right: 1%; border-bottom: 1px solid #afdd89;\"><img style=\" margin-left: 10%; width: 80%;\"   src=\"cid:"
							+ cid + "\"> <h3 style=\"text-align : center ; color : #1f1e1d\">" + subject
							+ "</h3>  </div> " +

							"<br/> Bonjour, <br/><br/>" + message +

							"<div style=\"margin-right: 1%;  border-bottom: 1px solid #983629;\" >    <img style=\"margin-left: 30%; width : 30%\"   src=\"cid:"
							+ logo + "\"> "
							+ "<br/> Vous recevez ce message  car vous êtes enregistré dans la liste de diffusion SEDAB-NOTIF. <br/> Pour ne plus recevoir ces notifications, veuillez  contacter l\'administrateur e-SEDAB à l\'adresse admin@esedab.com pour lui demander de vous retirer de la liste de diffusion.<br/><br/></div> <br/>SEDAB - SARL   <span style=\"float : right \"> Beau Rivage - 5, Bd du Centenaire de la Commune, Dakar</span><br/>  <span style=\"float : right \"> TEL :  +221 832 56 09  --  FAX : +221 832 27 94</span> </div>");

			email.send();
			return true;

		} catch (Exception e) {
			LOG.error("Une erreur est surveneue pendant l'envoi du mail :  " + e.getMessage());
			LOG.warn("Notification non transmise  : "   + e.getCause());
			return false;

		}
	}
	
	
	

	
	
	@Override
	public boolean envoyerConfirmation(Utilisateur utilisateurDTO) {

		try {

			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(587);
			email.setAuthenticator(new DefaultAuthenticator("sedabsenegal", "asock2mf"));
			email.setSSLOnConnect(true);
			email.addTo(utilisateurDTO.getEmail());
			email.setFrom("sedabsenegale@gmail.com");
			email.setCharset("utf-8");

			email.setSubject("Création de compte compte e-SEDAB ");
			email.setMsg(ConstantPGCA.MAIL_CONFIRMATION_CREATION_UTILISATEUR);
			

			
			URL url = new URL(ConstantPGCA.LDIRECTORY_PATH_HEADER_MAIL);
			String cid = email.embed(url, "eSADAB");

			//URL urlLogo = new URL(ConstantPGCA.PICTO_NERICA_MAIL);

			//String logo = email.embed(urlLogo, "Logo SEDAB");

			email.setHtmlMsg(
					"<div style=\"background: #daded7; margin-right: 1%; border-bottom: 1px solid #afdd89;\"><img style=\" margin-left: 10%; width: 80%;\"   src=\"cid:"
							+ cid + "\"> <h3 style=\"text-align : center ; color : #1f1e1d\">" + "Votre compte e-SEDAB est créé !"
							+ "</h3>  </div> " +

							"<br/> Bonjour "  +  utilisateurDTO.getPersonne().getPrenom()  +  " " +  utilisateurDTO.getPersonne().getNom()  +", <br/><br/>" + 
							ConstantPGCA.MAIL_CONFIRMATION_CREATION_UTILISATEUR.replace("{USER}",  utilisateurDTO.getEmail()) 
								
							+ " <h3 style='color : #98362a'> - Votre mot de passe par défaut doit être modifié impérativement lors de votre première connexion. </h3>"

							+ "<div style=\"margin-right: 1%;  border-bottom: 1px solid #983629;\" >    <img style=\"margin-left: 30%; width : 30%\"   src=\"cid:"
							+ /* logo  + */ "\"> "
						
								+ " <p style=''> A très bientôt sur votre compte e-SEDAB.</p>"
								+ " <p style=''> Service e-SEDAB.</p>"
			
							+ "</div> <br/>SEDAB - SARL   <span style=\"float : right \"> "
							+ "Beau Rivage - 5, Bd du Centenaire de la Commune, Dakar</span><br/>  <span style=\"float : right \"> TEL :  +221 832 56 09  --  FAX : +221 832 27 94</span> </div>");

			email.send();
			return true;

		} catch (Exception e) {
			LOG.error("Une erreur est surveneue pendant l'envoi du mail :  " + e.getMessage());
			LOG.warn("Notification non transmise  : "   + e.getCause());
			return false;

		}
	}
	
	
	/**********  RAPPORT AUTOMATIQUE *******************/
//	@Scheduled(cron = "0 * 0 ? * * *")
	@Override
	public boolean envoyerRapportDactivites() {

		try {
			ArrayList<CoupleDTO> listdestinataire = new ArrayList<CoupleDTO>(); 
			
			/*listdestinataire.add( new CoupleDTO("modouthiam@sedabsenegal.com" , "Modou Thiam")) ;
			listdestinataire.add(new CoupleDTO("moulayekande@sedabsenegal.com" , "Moulaye Kande"));
			listdestinataire.add(new CoupleDTO("mamourniang@sedabsenegal.com" , "Mamour Niang"));
			listdestinataire.add(new CoupleDTO("astougueye@sedabsenegal.com" , "Astou Gueye"));
			listdestinataire.add(new CoupleDTO("ibrahimafall@sedabsenegal.com", "Ibrahima Fall"));
			listdestinataire.add(new CoupleDTO("aronasavare@sedabsenegal.com" , "Arona Savare"));
			listdestinataire.add(new CoupleDTO("lansananiabaly@sedabsenegal.com" , "Lansana Savare"));
			listdestinataire.add(new CoupleDTO("abdoumboup@sedabsenegal.com", "Abdou Mboup"));
			listdestinataire.add(new CoupleDTO("moussadiop@sedabsenegal.com" , "Moussa Diop"));
			listdestinataire.add(new CoupleDTO("sedabsenegal@gmail.com" , "Admin Sedad"));*/
			
			listdestinataire.add(new CoupleDTO("ametfaye@gmail.com" , "Ameth Faye")); // test a enlever


			
			
			for(CoupleDTO dest : listdestinataire)
				envoyerRapportDactivitesParAgent(dest.getValeur() , dest.getClefValeur());
			 
			 return true;

		} catch (Exception e) {
			LOG.error("Une erreur est surveneue pendant l'envoi du mail :  " + e.getMessage());
			return false;
		}
	}
	
	
	
	
	
	
	private boolean envoyerRapportDactivitesParAgent(String destinataire , String nom) {

		try {
			Date dateInstance = new Date();
			 
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateInstance);
			cal.add(Calendar.DATE, -7); // - 7 jours 
			Date dateBefore7Days = cal.getTime();
			SimpleDateFormat DateFormatter = new SimpleDateFormat("dd-MM-yyyy");

			
			AlertesInformationsDTO params  = new AlertesInformationsDTO();
			
		
			CreatePdfFrorReportActivitesParMail pdfattachmentGenerator = new CreatePdfFrorReportActivitesParMail(); 
			List<MiseEnplaceDTOPointDeVente> listeDesMisesEnPlaceAenvoyer = programmAgricoleService.listeAggregationMiseenPlaceParIntrant();
			List<MiseEnplaceDTOPointDeVente> listeDesMisesEnPlaceDetaisAenvoyer = programmAgricoleService.getAllMiseEnPlaceEncours();
			List<IntrantDTO> listeDesStockResiduelAenvoyer = programmAgricoleService.loadStockResiduel();
			List<CreditDTO> listeDesCreditsAenvoyer = tresorerieService.loadAllCreditsOfProgramme();
			List<VersementBanqueDTO> listeDesVersementAenvoyer = tresorerieService.recupererAllVersementBanqueByCampagne();
			List<IntrantDTO> listeStockResiduel = programmAgricoleService.loadStockResiduel();
			List<IntrantDTO> listeStockFournisseur = programmAgricoleService.getlistIntrantDTOFromFournisseurFournisseur();

			// Listes des ventes des 7 derniers jours
			List<VentesDTO> listeVentes = tresorerieService.loadAllVentesParPeriode(dateBefore7Days, dateInstance);
			Double totauxventes =  0.0;
			int totauxventesSize =  listeVentes != null ? listeVentes.size() : 0;
			for (VentesDTO  vente: listeVentes)
				totauxventes += vente.getMontantVente();
			
			
			
			params.setDateDebut(dateBefore7Days);
			params.setDateFin(dateInstance);
			params.setListeDesMisesEnPlaceAenvoyer(listeDesMisesEnPlaceAenvoyer);
			params.setListeDesStockResiduelAenvoyer(listeDesStockResiduelAenvoyer);
			params.setListeDesCreditsAenvoyer(listeDesCreditsAenvoyer);
			params.setListeDesVersementAenvoyer(listeDesVersementAenvoyer);
			params.setListeStockResiduelAEnvoyer(listeStockResiduel);
			params.setListeStockFournisseurAenvoyer(listeStockFournisseur);
			params.setListeDesMisesEnPlaceDetaisAenvoyer(listeDesMisesEnPlaceDetaisAenvoyer);
	
			Double 		montantTotallisteDesCreditsAenvoyer = 0.0;
			Double 		montantTotallVersementsAenvoyer = 0.0;

			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

			
			montantTotallisteDesCreditsAenvoyer =  0.0;
			for (CreditDTO  credit: listeDesCreditsAenvoyer)
				montantTotallisteDesCreditsAenvoyer += credit.getMontantRestantApayer();
			
			for (VersementBanqueDTO  versement: listeDesVersementAenvoyer)
				montantTotallVersementsAenvoyer += versement.getMontantVersment();
			
			
			params.setTotallisteDesCreditsAenvoyer(montantTotallisteDesCreditsAenvoyer);
			params.setTotallisteDesVersementAenvoyer(montantTotallVersementsAenvoyer);
			

			pdfattachmentGenerator.createFileAttachementForAlert(params);
		
			
			File attachement = new File (ConstantPGCA.LFENERATEDREPOT); // a pdf a créer 

			String msgAEnvoyer =  ConstantPGCA.MAIL_MSG ; 
			
			msgAEnvoyer  = msgAEnvoyer.replace("{NOM}", nom);
			
			msgAEnvoyer  = msgAEnvoyer.replace("{PERIOD1}", DateFormatter.format(dateBefore7Days) + "");
			msgAEnvoyer  = msgAEnvoyer.replace("{PERIOD2}", DateFormatter.format(dateInstance) +"");

			msgAEnvoyer  = msgAEnvoyer.replace("{VENTES}",  decimalFormat.format(totauxventes) + " F CFA" );
			msgAEnvoyer  = msgAEnvoyer.replace("{VENTESNB}",  totauxventesSize + " ventes enregistée(s)" );

			msgAEnvoyer  = msgAEnvoyer.replace("{MEP}",  listeDesMisesEnPlaceAenvoyer != null ? listeDesMisesEnPlaceAenvoyer.size()  + " catégorie(s) d'intrant(s) " : " 0 catégorie d'intrant");
			msgAEnvoyer  = msgAEnvoyer.replace("{STOCKR}",  listeDesStockResiduelAenvoyer != null ? listeDesStockResiduelAenvoyer.size()  + " catégorie(s) d'intrant(s)" : " 0 catégorie d'intrant");
			
			msgAEnvoyer  = msgAEnvoyer.replace("{CREDITS}",    decimalFormat.format(montantTotallisteDesCreditsAenvoyer) + " F CFA");
			msgAEnvoyer  = msgAEnvoyer.replace("{VERSEMENTS}", decimalFormat.format(montantTotallVersementsAenvoyer) + " F CFA");


			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(attachement.getAbsolutePath());
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("Sedab Notification");
			attachment.setName("Sedab Siege");

			// Create the email message
			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.ionos.com");
			email.setSmtpPort(465);
			//FIXME :  A EXTERNALISER DANS LA TABLE PARAMETRE
			//email.setAuthenticator(new DefaultAuthenticator("mtallathiam@mail.sedabsenegal.com", "Sedab221")); 
			email.setAuthentication("rapport@mail.sedabsenegal.com", "Sedab221$");
			email.setSSLOnConnect(true);
			email.addTo(destinataire);
			email.setFrom("rapport@mail.sedabsenegal.com");
			email.setSubject(ConstantPGCA.MAIL_TITLE);
			email.setMsg(msgAEnvoyer);
			email.setCharset("utf-8");


			// add the attachment
			// email.attach(attachment);

			// SEND IMAGE HTML

			URL url = new URL(ConstantPGCA.LDIRECTORY_PATH_HEADER_MAIL); /*DIRECTORY_PATH_HEADER_MAIL*/
			String cid = email.embed(url, "eSADAB");

			URL urlLogo = new URL("file:" + ConstantPGCA.LFENERATEDREPOT); /*LDIRECTORY_PATH_FOOTER_PDF  */

			String logo = email.embed(urlLogo, "Rapport d'activités du " + DateFormatter.format(dateBefore7Days) + " au " + DateFormatter.format(dateInstance));

			email.setHtmlMsg(
					"<div style=\"background: #daded7; margin-right: 1%; border-bottom: 1px solid #afdd89;\"><img style=\" margin-left: 10%; width: 80%;\"   src=\"cid:"
							+ cid + "\"> <h3 style=\"text-align : center ; color : #1f1e1d\">" +  ConstantPGCA.MAIL_TITLE
							+ "</h3>  </div> " +

							 msgAEnvoyer +

							"<div style=\"margin-right: 1%;  border-bottom: 1px solid #983629;\" >    <img style=\"margin-left: 30%; width : 30%\"   src=\"cid:"
							+ logo + "\"> "
							+ "<br/> Vous recevez ce message  car vous êtes enregistré  en tant que manager dans la liste de diffusion SEDAB-NOTIF. <br/> Pour ne plus recevoir ces notifications, veuillez  contacter l\'administrateur e-SEDAB à l\'adresse admin@esedab.com pour lui demander de vous retirer de la liste de diffusion.<br/><br/></div> <br/>SEDAB - SARL   <span style=\"float : right \"> Beau Rivage - 5, Bd du Centenaire de la Commune, Dakar</span><br/>  <span style=\"float : right \"> TEL :  +221 832 56 09  --  FAX : +221 832 27 94</span> </div>");

			email.send();
			return true;

		} catch (Exception e) {
			LOG.error("Une erreur est surveneue pendant l'envoi du mail :  " + e.getMessage());
			return false;
		}
	}
	
	
	@Override
	public boolean envoyerRapportDeMisesEnPlace() {

		try {
			

			List<MiseEnplaceAgregation> listeDesMisesEnPlaceAenvoyer = commonService.loadAggregationMiseEnPlace();
			List<IntrantDTO> listeDesStockResiduelAenvoyer = programmAgricoleService.loadStockResiduel();
			List<CreditDTO> listeDesCreditsAenvoyer = tresorerieService.loadAllCreditsOfProgramme();
			List<VersementBanqueDTO> listeDesVersementAenvoyer = tresorerieService.recupererAllVersementBanqueByCampagne();

			
			Double 		montantTotallisteDesCreditsAenvoyer = 0.0;
			Double 		montantTotallVersementsAenvoyer = 0.0;

			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

			
			montantTotallisteDesCreditsAenvoyer =  0.0;
			for (CreditDTO  credit: listeDesCreditsAenvoyer)
				montantTotallisteDesCreditsAenvoyer += credit.getMontantRestantApayer();
			
			for (VersementBanqueDTO  versement: listeDesVersementAenvoyer)
				montantTotallVersementsAenvoyer += versement.getMontantVersment();
			
			

			
			File attachement = new File (ConstantPGCA.LDIRECTORY_PATH_TMP_PDF); // a pdf a créer 

			String msgAEnvoyer =  ConstantPGCA.MAIL_MSG ; 
			
			msgAEnvoyer  = msgAEnvoyer.replace("{NOM}", "Mouhamed Faye");
			
			msgAEnvoyer  = msgAEnvoyer.replace("{PERIOD1}", "10-10-2017");
			msgAEnvoyer  = msgAEnvoyer.replace("{PERIOD2}", "10-17-2017");

			msgAEnvoyer  = msgAEnvoyer.replace("{MEP}",  listeDesMisesEnPlaceAenvoyer != null ? listeDesMisesEnPlaceAenvoyer.size()  + " catégorie(s) d'intrant(s) " : " 0 catégorie d'intrant");
			msgAEnvoyer  = msgAEnvoyer.replace("{STOCKR}",  listeDesStockResiduelAenvoyer != null ? listeDesStockResiduelAenvoyer.size()  + " catégorie(s) d'intrant(s)" : " 0 catégorie d'intrant");
			
		


			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(attachement.getAbsolutePath());
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("Sedab Notification");
			attachment.setName("Sedab Siege");

			// Create the email message
			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(587);
			email.setAuthenticator(new DefaultAuthenticator("sedabsenegal", "asock2mf"));
			email.setSSLOnConnect(true);
			email.addTo("ametfaye@gmail.com");
			email.setFrom("sedabsenegale@gmail.com");
			email.setSubject(ConstantPGCA.MAIL_TITLE);
			email.setMsg(msgAEnvoyer);
			email.setCharset("utf-8");


			// add the attachment
			// email.attach(attachment);

			// SEND IMAGE HTML

			URL url = new URL(ConstantPGCA.LDIRECTORY_PATH_HEADER_MAIL); /*DIRECTORY_PATH_HEADER_MAIL*/
			String cid = email.embed(url, "eSADAB");

			URL urlLogo = new URL(ConstantPGCA.LDIRECTORY_PATH_TMP_PDF); /*LDIRECTORY_PATH_FOOTER_PDF  */

			String logo = email.embed(urlLogo, "Logo SEDAB");

			email.setHtmlMsg(
					"<div style=\"background: #daded7; margin-right: 1%; border-bottom: 1px solid #afdd89;\"><img style=\" margin-left: 10%; width: 80%;\"   src=\"cid:"
							+ cid + "\"> <h3 style=\"text-align : center ; color : #1f1e1d\">" +  ConstantPGCA.MAIL_TITLE
							+ "</h3>  </div> " +

							 msgAEnvoyer +

							"<div style=\"margin-right: 1%;  border-bottom: 1px solid #983629;\" >    <img style=\"margin-left: 30%; width : 30%\"   src=\"cid:"
							+ logo + "\"> "
							+ "<br/> Vous recevez ce message  car vous êtes enregistré  en tant que manager dans la liste de diffusion SEDAB-NOTIF. <br/> Pour ne plus recevoir ces notifications, veuillez  contacter l\'administrateur e-SEDAB à l\'adresse admin@esedab.com pour lui demander de vous retirer de la liste de diffusion.<br/><br/></div> <br/>SEDAB - SARL   <span style=\"float : right \"> Beau Rivage - 5, Bd du Centenaire de la Commune, Dakar</span><br/>  <span style=\"float : right \"> TEL :  +221 832 56 09  --  FAX : +221 832 27 94</span> </div>");

			email.send();
			return true;

		} catch (Exception e) {
			LOG.error("Une erreur est surveneue pendant l'envoi du mail :  " + e.getMessage());
			return false;
		}
	}
	
	
	
}