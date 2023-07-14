package sn.awi.pgca.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sn.awi.pgca.business.exception.UtilisateurException;
import sn.awi.pgca.business.service.IAffectationService;
import sn.awi.pgca.business.service.ICommonService;
import sn.awi.pgca.business.service.IUtilisateurService;
import sn.awi.pgca.business.service.Inotification;
import sn.awi.pgca.utils.DownloadTools;
import sn.awi.pgca.utils.StringDisplayer;
import sn.awi.pgca.utils.UtilString;
import sn.awi.pgca.web.dto.CoupleDTO;
import sn.awi.pgca.web.dto.GedAbstractDocumentDTO;
import sn.awi.pgca.web.dto.InfosCommunesDTO;
import sn.awi.pgca.web.dto.PointDeVenteDTO;
import sn.awi.pgca.web.dto.UtilisateurDTO;

@ManagedBean(name = "utilisateurMB")
@SessionScoped
public class UtilisateurManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5113177848794047450L;

	// @Inject
	private static final org.apache.commons.logging.Log logger = LogFactory.getLog(UtilisateurManagedBean.class);

	@ManagedProperty(value = "#{utilisateurService}")
	private IUtilisateurService utilisateurService;
	@ManagedProperty(value = "#{commonService}")
	private ICommonService communService;

	@ManagedProperty(value = "#{servicesAffectation}")
	private IAffectationService affectationServices;

	private UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
	private List<UtilisateurDTO> utilisateurDTOs;
	private List<UtilisateurDTO> utilisateurDTOsFiltred;
	private List<CoupleDTO> entiteJuridiqueDTOs;
	private List<CoupleDTO> profilDTOs;
	private UtilisateurDTO selectedUtilisateurDTO = new UtilisateurDTO();
	private String code;
	private String mode;
	private List<CoupleDTO> listeDesPointsDeVente;

	@PostConstruct
	public void init() {
		recupListUtilisateur();
		profilDTOs = communService.loadProfil();
		listeDesPointsDeVente = new ArrayList<>();
		selectedUtilisateurDTO = null;
		mode = "0";
		
		
	}

	public String initModifierUtilisateur() {

		try {
			if (selectedUtilisateurDTO == null || selectedUtilisateurDTO.getId() == null
					|| selectedUtilisateurDTO.getId().longValue() == 0) {
				FacesMessage msg = new FacesMessage("Veuillez selectionner un utilisateur");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
				// return ConstantPGCA.ERROR;

			}
			utilisateurDTO = utilisateurService.recupUtilisateur(selectedUtilisateurDTO.getId());
			return ConstantPGCA.SUCCESS;
		} catch (Exception e) {
			return ConstantPGCA.ERROR;
		}

	}

	public String initModifierMotdePasse() {
		try {
			utilisateurDTO = utilisateurService.recupUtilisateur(utilisateurDTO.getId());
			return "changerMotDePasse";
		} catch (Exception e) {
			return "connexion";
		}
	}

	public void switchMode() {
		mode = utilisateurDTO.getProfilId();
	}

	Long selectedPVaAffecter;

	public String affecterPointDeVenteUser() {

		if (null == selectedPVaAffecter) {
			FacesMessage msg = new FacesMessage("Veuillez selectionner un point de vente");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "adminUtilisateurCreation.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		
		if(null == listeDesPointsDeVente)
			return "";
		
		for (CoupleDTO f : listeDesPointsDeVente) {
			if (f.getClef() == selectedPVaAffecter) {

				FacesMessage msg = new FacesMessage("Le point de vente est déjà ajouté");
				FacesContext.getCurrentInstance().addMessage(null, msg);

				return "adminUtilisateurCreation.xhtml?faces-redirect=true&idBlocToShow=1";
			}
		}
		PointDeVenteDTO infosSelectedPv = communService.loadPointDeVenteById(selectedPVaAffecter);

		CoupleDTO dto = new CoupleDTO(infosSelectedPv.getIdPv(), infosSelectedPv.getLibelle(), infosSelectedPv.getLibelle());
		//dto.setStatus(1);
		dto.setShowBlocActif(true);
		listeDesPointsDeVente.add(dto);

		return "";
	}

	Long idPointdeVenteToDelete;

	public String supprimeraffecterPointDeVenteUser() {

		if (null == idPointdeVenteToDelete) {
			FacesMessage msg = new FacesMessage("Veuillez selectionner un point de vente");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "adminUtilisateurCreation.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		for (CoupleDTO f : listeDesPointsDeVente) {
			if (f.getClef() == idPointdeVenteToDelete) {
				listeDesPointsDeVente.remove(f);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Le point de vente est déjà ajouté"));
				return "adminUtilisateurCreation.xhtml?faces-redirect=true&idBlocToShow=1";
			}
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Erreur survenue lors de la suppression"));
		return "adminUtilisateurCreation.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public String desactiverffecterPointDeVenteUser() {

		if (null == idPointdeVenteToDelete) {
			FacesMessage msg = new FacesMessage("Veuillez selectionner un point de vente");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "adminUtilisateurCreation.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		for (CoupleDTO f : listeDesPointsDeVente) {
			if (f.getClef() == idPointdeVenteToDelete) {
				// listeDesPointsDeVente.remove(f);
				f.setBlocmsg("Activer affectation");
				f.setStatus(1);
				f.setShowBlocActif(false);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Le point de vente est affecté !"));
				return "dashboardAdminUpdateUser.xhtml?faces-redirect=true&idBlocToShow=1";
			}
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Erreur survenue lors de la modification"));
		return "dashboardAdminUpdateUser.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public String activerffecterPointDeVenteUser() {

		if (null == idPointdeVenteToDelete) {
			FacesMessage msg = new FacesMessage("Veuillez selectionner un point de vente");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "adminUtilisateurCreation.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		for (CoupleDTO f : listeDesPointsDeVente) {
			if (f.getClef() == idPointdeVenteToDelete) {
				// listeDesPointsDeVente.remove(f);
				f.setBlocmsg("Desactiver affectation");
				f.setStatus(0);
				f.setShowBlocActif(true);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Le point de vente est déssacfecté !"));
				return "dashboardAdminUpdateUser.xhtml?faces-redirect=true&idBlocToShow=1";
			}
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Erreur survenue lors de la modification"));
		return "dashboardAdminUpdateUser.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public String modifierPassword() throws UtilisateurException {
		String outcome = "";
		FacesContext context = FacesContext.getCurrentInstance();

		if (!utilisateurDTO.getNewPassword().equals(utilisateurDTO.getConfirmPassword())) {

			context.addMessage(null, new FacesMessage(
					"Le nouveau mot de passe et le mot de passe de confirmation sont différents! réessayez"));
		} else {
			utilisateurService.updateMotDePasse(utilisateurDTO);
			outcome = "adminUtilisateurListe";

		}
		return outcome;
	}

	public String detailUtilisateur() {
		try {
			if (selectedUtilisateurDTO == null || selectedUtilisateurDTO.getId() == null
					|| selectedUtilisateurDTO.getId().longValue() == 0) {
				FacesMessage msg = new FacesMessage("Veuillez selectionner un utilisateur");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
				// return ConstantPGCA.ERROR;

			}
			utilisateurDTO = utilisateurService.recupUtilisateur(selectedUtilisateurDTO.getId());
			return ConstantPGCA.SUCCESS;
		} catch (Exception e) {
			return ConstantPGCA.ERROR;
		}

	}

	public String verouillerUtilisateur() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		try {
			if (selectedUtilisateurDTO == null || selectedUtilisateurDTO.getId() == null
					|| selectedUtilisateurDTO.getId().longValue() == 0) {
				FacesMessage msg = new FacesMessage("Veuillez selectionner un utilisateur");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "dashboardAdmin.xhtml?faces-redirect=true&idBlocToShow=1";

			}

			// un admin connecté ne peut pas se verouiller lui meme
			Long connectedUserid = null;
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			if (request != null) {
				HttpSession session = request.getSession(false);
				connectedUserid = (Long) session.getAttribute("connectedUserid");
			}

			if (connectedUserid != null && selectedUtilisateurDTO.getId() == connectedUserid) {

				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Echec: ",
						"impossible de  vérouiller  votre propre compte administrateur, seul un autre administrateur peut verouiller votre compte administrateur");
				FacesContext.getCurrentInstance().addMessage(null, msg);

				return "dashboardAdmin.xhtml?faces-redirect=true&idBlocToShow=1";
			}

			if (utilisateurService.verouillerUtilisateur(selectedUtilisateurDTO.getId())) {

				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification: ",
						"L'utilisateur '" + selectedUtilisateurDTO.getCode() + "' est vérouillé.");
				FacesContext.getCurrentInstance().addMessage(null, msg);

				recupListUtilisateur();

			} else {

				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Modification Utilisateur : ",
						"Erreur survenue  !! L'utilisateur '" + utilisateurDTO.getCode()
								+ "' n'a pas pu être vérouiller.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

			return "dashboardAdmin.xhtml?faces-redirect=true&idBlocToShow=1";
		} catch (Exception e) {
			return ConstantPGCA.ERROR;
		}
	}

	public String deverouillerUtilisateur() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		try {
			if (selectedUtilisateurDTO == null || selectedUtilisateurDTO.getId() == null
					|| selectedUtilisateurDTO.getId().longValue() == 0) {
				FacesMessage msg = new FacesMessage("Veuillez selectionner un utilisateur");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
				// return ConstantPGCA.ERROR;

			}
			if (utilisateurService.deverouillerUtilisateur(selectedUtilisateurDTO.getId())) {

				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification",
						"L'utilisateur '" + selectedUtilisateurDTO.getCode() + "' est dévérouillé.");
				FacesContext.getCurrentInstance().addMessage(null, msg);

				recupListUtilisateur();

			} else {

				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Modification",
						"Erreur survenue  !! L'utilisateur '" + selectedUtilisateurDTO.getCode()
								+ "' n'a pas pu être vérouiller.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

			return "dashboardAdmin.xhtml?faces-redirect=true&idBlocToShow=1";
		} catch (Exception e) {
			return ConstantPGCA.ERROR;
		}
	}

	public String reinitialiserMDP() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		try {
			if (selectedUtilisateurDTO == null || selectedUtilisateurDTO.getId() == null
					|| selectedUtilisateurDTO.getId().longValue() == 0) {
				FacesMessage msg = new FacesMessage("Veuillez selectionner un utilisateur");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "";

			}

			// un admin connecté ne peut pas se verouiller lui meme
			Long connectedUserid = null;
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			if (request != null) {
				HttpSession session = request.getSession(false);
				connectedUserid = (Long) session.getAttribute("connectedUserid");
			}

			if (connectedUserid != null && selectedUtilisateurDTO.getId() == connectedUserid) {

				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Echec: ",
						"impossible de  réinitilialiser  votre propre compte mot de passe, seul un autre administrateur peut le faire pour vous");
				FacesContext.getCurrentInstance().addMessage(null, msg);

				return "";
			}

			if (utilisateurService.reinitialiserMDP(selectedUtilisateurDTO.getId())) {

				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vérouillage: ",
						"Le mot de passe de utilisateur '" + selectedUtilisateurDTO.getCode()
								+ "' est réinitialisé avec succès, le mot de passe temporaire est  <b>'achanger' <b/>.");
				FacesContext.getCurrentInstance().addMessage(null, msg);

				recupListUtilisateur();

			} else {

				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Modification Utilisateur : ",
						"Erreur survenue  !! L'utilisateur '" + utilisateurDTO.getCode()
								+ "' n'a pas pu être vérouiller.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

			return "";
		} catch (Exception e) {
			return ConstantPGCA.ERROR;
		}
	}

	List<PointDeVenteDTO> listeAffectationsDesPointDeVentes;

	public String initModifierUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		try {
			if (selectedUtilisateurDTO == null || selectedUtilisateurDTO.getId() == null
					|| selectedUtilisateurDTO.getId().longValue() == 0) {
				FacesMessage msg = new FacesMessage("Veuillez selectionner un utilisateur à  modifier");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "";
			}
			// listeAffectationsDesPointDeVentes =
			// pre remplir les champs
			utilisateurDTO.setProfilId(selectedUtilisateurDTO.getProfilId());
			utilisateurDTO.setEmail(selectedUtilisateurDTO.getEmail() != null ?  selectedUtilisateurDTO.getEmail() : "" );
			utilisateurDTO.setTel(selectedUtilisateurDTO.getTel() != null ?  selectedUtilisateurDTO.getTel() : "");
			mode = selectedUtilisateurDTO.getProfilId(); // block gestion PV
			listeDesPointsDeVente = affectationServices.loadAffectationsByIdUser(selectedUtilisateurDTO.getId());
			return "dashboardAdminUpdateUser.xhtml";
		} catch (Exception e) {
			return ConstantPGCA.ERROR;
		}
	}

	public String updateUtilisateur() {
		try {
				utilisateurDTO.setListAffectationsPV(listeDesPointsDeVente);
				utilisateurDTO.setId(selectedUtilisateurDTO.getId());
				utilisateurDTO.setEmail(selectedUtilisateurDTO.getEmail());
				
			if (utilisateurService.modifierUtilisateur(utilisateurDTO)) {
				recupListUtilisateur();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "L'utilisateur '" + utilisateurDTO.getEmail() + "' a été modifé.",
						"L'utilisateur '" + utilisateurDTO.getEmail() + "' a été modifé.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return ConstantPGCA.SUCCESS;
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Modification Utilisateur : ",
						"Impossible de modifier l'utilisateur '" + utilisateurDTO.getCode() + "'");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return ConstantPGCA.ERROR;
			}
		} catch (Exception e) {
			logger.warn("Echec suppression de l'utilisateur " + e.getMessage(), e);
		}
		return ConstantPGCA.ERROR;
	}

	public boolean showbuttonDesactiverMDP() {
		if (selectedUtilisateurDTO == null || selectedUtilisateurDTO.getId() == null
				|| selectedUtilisateurDTO.getId().longValue() == 0 || selectedUtilisateurDTO.getStatutUser() == null) {
			return false;
		}
		if (selectedUtilisateurDTO.getStatutUser().equals("Actif"))
			return true;
		else
			return false;
	}

	public boolean showbuttonActiverMDP() {

		if (selectedUtilisateurDTO == null || selectedUtilisateurDTO.getId() == null
				|| selectedUtilisateurDTO.getId().longValue() == 0 || selectedUtilisateurDTO.getStatutUser() == null) {
			return false;

		}

		if (!selectedUtilisateurDTO.getStatutUser().equals("Actif"))
			return true;
		else
			return false;
	}

	public List<CoupleDTO> getProfilDTOs() {
		return profilDTOs;
	}

	public void setProfilDTOs(List<CoupleDTO> profilDTOs) {
		this.profilDTOs = profilDTOs;
	}

	public String createUtilisateur() {
		// utilisateurDTO.setCode(code);
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		Pattern pattern = Pattern.compile("^.+@.+\\..+$");
		Matcher matcher = pattern.matcher(utilisateurDTO.getEmail());

		if (!matcher.find()) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"L email de l'utilisateur à créer  n'est pas valide", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			return ConstantPGCA.ERROR;
		}

		boolean b = false;
		try {
			// si c un magasinier/superviseur il faut au moins un point de vente
			if (utilisateurDTO.getProfilId().equals("5")) {
				if (listeDesPointsDeVente.size() == 0) {
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Le compte de " + utilisateurDTO.getPrenom() + " " + utilisateurDTO.getNom()
									+ " a été créé avec  succès , l'identifiant  de connexion est "
									+ utilisateurDTO.getTel()
									+ ", le mot de passe par défaut est 'achanger' . Lors de la première connexion son changement sera requis ",
							"");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					return "dashboardAdmin.xhtml?faces-redirect=true";
				} else
					utilisateurDTO.setListAffectationsPV(listeDesPointsDeVente);
			}

			b = utilisateurService.creerUtilisateur(utilisateurDTO);
			recupListUtilisateur();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Le compte de " + utilisateurDTO.getPrenom() + " " + utilisateurDTO.getNom()
							+ " a été créé avec  succès , l'identifiant  de connexion est " + utilisateurDTO.getTel()
							+ ", le mot de passe par défaut est 'achanger' . Lors de la première connexion son changement sera requis ",
					"");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			utilisateurDTO = new UtilisateurDTO();

			// on notifie le user par mail
			return "dashboardAdmin.xhtml?faces-redirect=true";

		} catch (UtilisateurException ue) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Opps !! un utilisateur existe déjà avec l'adresse email     " + utilisateurDTO.getEmail(), "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		if (b)
			return ConstantPGCA.ERROR;
		return ConstantPGCA.ERROR;
	}

	public String genererChangePasswordDocument() {
		// utilisateurDTO.setCode(code);
		try {
			utilisateurService.genererChangePasswordDocument(utilisateurDTO);
			FacesMessage msg = new FacesMessage(
					"Utilisateur " + utilisateurDTO.getCode() + " : le document est généré");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return ConstantPGCA.SUCCESS;
		} catch (UtilisateurException ue) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur Génération document",
					ue.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return ConstantPGCA.ERROR;
		}
	}

	public boolean onEdit() throws Exception {
		if (selectedUtilisateurDTO != null && UtilString.isCorrect(selectedUtilisateurDTO.getLibelle())) {
			System.out.println("Select Event" + selectedUtilisateurDTO.getLibelle() + "donc j'envoi true");
			return true;
		} else {
			System.out.println("No Slect Event donc j'envoi false et tu peux pas passer !!!");
			return false;
		}
	}

	public String recupListUtilisateur() {

		try {
			utilisateurDTOs = utilisateurService.recupererAllUtilisateur();
			return ConstantPGCA.SUCCESS;
		} catch (UtilisateurException ue) {
			return ConstantPGCA.ERROR;
		}
	}

	// convert SQL format to format 2018-01-27 20:55:07.698 -> 27/01/2018 20:55
	public String convertFormatDate(String dateSql) {

		String formatSimple = "";
		if (!dateSql.trim().equals("")) {
			String[] tab = dateSql.split(" ");

			if (tab != null && tab.length > 1) {
				String[] date = tab[0].split("-"); // 2018-01-27
				if (date != null && date.length > 2)
					formatSimple = date[2] + "-" + date[1] + "-" + date[0];

				String[] time = tab[1].split(":"); // 20:55:07.698
				if (time != null && time.length > 2)
					formatSimple += " à " + time[0] + "-" + time[1];

				return formatSimple;
			}
		}

		return dateSql;
	}

	public IUtilisateurService getUtilisateurService() {
		return utilisateurService;
	}

	public void setUtilisateurService(IUtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	public UtilisateurDTO getUtilisateurDTO() {
		if (utilisateurDTO == null)
			utilisateurDTO = new UtilisateurDTO();
		return utilisateurDTO;
	}

	public void setUtilisateurDTO(UtilisateurDTO utilisateurDTO) {
		this.utilisateurDTO = utilisateurDTO;
	}

	public List<UtilisateurDTO> getUtilisateurDTOs() {
		return utilisateurDTOs;
	}

	public ICommonService getCommunService() {
		return communService;
	}

	public void setCommunService(ICommonService communService) {
		this.communService = communService;
	}

	public List<CoupleDTO> getEntiteJuridiqueDTOs() {
		return entiteJuridiqueDTOs;
	}

	public void setEntiteJuridiqueDTOs(List<CoupleDTO> entiteJuridiqueDTOs) {
		this.entiteJuridiqueDTOs = entiteJuridiqueDTOs;
	}

	public String onDelete() {
		try {
			if (selectedUtilisateurDTO == null || selectedUtilisateurDTO.getId() == null
					|| selectedUtilisateurDTO.getId().longValue() == 0) {
				FacesMessage msg = new FacesMessage("Veuillez selectionner un utilisateur");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return ConstantPGCA.ERROR;

			} else {
				if (utilisateurService.removeUtilisateur(selectedUtilisateurDTO.getId()))

				{
					recupListUtilisateur();
					FacesMessage msg = new FacesMessage(
							"utilisateur  " + selectedUtilisateurDTO.getCode() + "  supprimé ...");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					return ConstantPGCA.SUCCESS;
				} else {
					FacesMessage msg = new FacesMessage("Echec suppression  de l'utilisateur  " + code + "...");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					return ConstantPGCA.ERROR;
				}
			}
		} catch (UtilisateurException e) {
			logger.warn("Echec suppression de l'utilisateur " + e.getMessage(), e);
		}
		return ConstantPGCA.ERROR;
	}

	public String showMessage(boolean status, String val) {
		if (status) {
			FacesMessage msg = new FacesMessage(val + " enregistré ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return ConstantPGCA.SUCCESS;
		} else {
			FacesMessage msg = new FacesMessage("echec enregistrement." + val);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return ConstantPGCA.ERROR;
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public void setUtilisateurDTOs(List<UtilisateurDTO> utilisateurDTOs) {
		this.utilisateurDTOs = utilisateurDTOs;
	}

	public UtilisateurDTO getSelectedUtilisateurDTO() {
		return selectedUtilisateurDTO;
	}

	public void setSelectedUtilisateurDTO(UtilisateurDTO selectedUtilisateurDTO) {
		this.selectedUtilisateurDTO = selectedUtilisateurDTO;
	}

	public String downloadChangePassword() {
		GedAbstractDocumentDTO document = utilisateurDTO.getGedDocumentDTO();
		if (document != null)
			return DownloadTools.download(document.getNomFichier(), document.getMimeType().getMimetype(),
					document.getFlux());

		return null;
	}

	public List<UtilisateurDTO> getUtilisateurDTOsFiltred() {
		return utilisateurDTOsFiltred;
	}

	public void setUtilisateurDTOsFiltred(List<UtilisateurDTO> utilisateurDTOsFiltred) {
		this.utilisateurDTOsFiltred = utilisateurDTOsFiltred;
	}

	public String StringCutter(String text, int index) {
		if (text != null)
			if (text.length() > index)
				return StringDisplayer.cutString(text, index);
		return (text);
	}

	public List<CoupleDTO> getListeDesPointsDeVente() {
		return listeDesPointsDeVente;
	}

	public List<PointDeVenteDTO> getListeAffectationsDesPointDeVentes() {
		return listeAffectationsDesPointDeVentes;
	}

	public IAffectationService getAffectationServices() {
		return affectationServices;
	}

	public void setAffectationServices(IAffectationService affectationServices) {
		this.affectationServices = affectationServices;
	}

	public void setListeAffectationsDesPointDeVentes(List<PointDeVenteDTO> listeAffectationsDesPointDeVentes) {
		this.listeAffectationsDesPointDeVentes = listeAffectationsDesPointDeVentes;
	}

	public Long getSelectedPVaAffecter() {
		return selectedPVaAffecter;
	}

	public void setSelectedPVaAffecter(Long selectedPVaAffecter) {
		this.selectedPVaAffecter = selectedPVaAffecter;
	}

	public void setListeDesPointsDeVente(List<CoupleDTO> listeDesPointsDeVente) {
		this.listeDesPointsDeVente = listeDesPointsDeVente;
	}

	public Long getIdPointdeVenteToDelete() {
		return idPointdeVenteToDelete;
	}

	public void setIdPointdeVenteToDelete(Long idPointdeVenteToDelete) {
		this.idPointdeVenteToDelete = idPointdeVenteToDelete;
	}

}
