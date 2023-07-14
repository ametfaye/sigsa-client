package sn.awi.pgca.web.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jfree.util.Log;

import sn.awi.pgca.business.exception.ConnectionException;
import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.service.ICommonService;
import sn.awi.pgca.business.service.IConnectionService;
import sn.awi.pgca.business.service.IProgrammeAgricol;
import sn.awi.pgca.dataModel.PointDeVente;
import sn.awi.pgca.web.dto.ConnectionDTO;
import sn.awi.pgca.web.dto.DashbordDTO;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.ThemeDTO;

@ManagedBean(name = "connexionMB")
@SessionScoped
public class ConnexionManagedBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6938659451535735261L;

	@ManagedProperty(value = "#{connectionService}")
	private IConnectionService connectionService;

	@ManagedProperty(value = "#{programmeAgricolService}")
	private IProgrammeAgricol programmAgricoleService;

	@ManagedProperty(value = "#{commonService}")
	private ICommonService commonService;
	
	private DashbordDTO dashbordDTO;

	boolean connectedUserProfilIsAdmin;
	boolean connectedUserProfilIManager;
	boolean connectedUserProfilIsAgentSaisie;
	boolean connectedUserProfilIsMagasinier;
	boolean connectedUserProfilIsDRDR;
	boolean connectedUserProfilIsAgentCollecte;
	Long connectedUserThemeId;
	private List<PointDeVente> listDesPointDeVenteAff;
	
	private int nbPvaffectes;

	
	ThemeDTO theme = new ThemeDTO();

	// Data Transfert Object
	private ConnectionDTO connectionDTO;

	private enum TYPEPROFIL {
		manager, admin, agentcollecte, agentsaisie, magasinier, drdr
	};

	@PostConstruct
	private void init() {
		// loadDashBord();
	}

	public ConnexionManagedBean() {
	}

	public String changeTheme() {
		try {
			theme.setIdTheme(connectedUserThemeId);

			theme = connectionService.changethemene(connectionDTO.getId(), theme);
			return "";
		} catch (ConnectionException | EntityDBDAOException ce) {
			Log.error("Theme invisible , le theme par defaut sera appliqué");
			return "";
		}
	}

	// graphique chart agent de saisie
	String StockParFounisseurJSONFomrat = "";

	public String login() throws ConnectionException  {

		String outcome = "";

		Log.info("Tentative de connexion  User " + connectionDTO.getCodeUtilisateur());

		boolean conneted = false;

		try {
			conneted = connectionService.validate(connectionDTO);
			// theme.setNomTheme(connectionDTO.getConnectedUserTheme());
			// theme.setThemeHeaderBG(connectionDTO.getConnectedUserThemeBG());
			// theme.setHeaderBG(connectionDTO.getConnectedUserThemeCol());
		} catch (ConnectionException ce) {
			conneted = false;
		}

		if (conneted) {
			if (!connectionDTO.isMdpmodifier()) {

				HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
						.getRequest();
				if (request != null) {
					HttpSession session = request.getSession(false);
					if (session != null) {
						Log.info("User < " + connectionDTO.getCodeUtilisateur() + " > connecté avec un profil < "
								+ session.getAttribute("profil"));

						outcome = getDashboardCodeFromConnnedtedUser(session);
						if (outcome.equals("manager"))
							connectedUserProfilIManager = true;
						else if (outcome.equals("agentsaisie")) {
							connectedUserProfilIsAgentSaisie = true;
						}

						else if (outcome.equals("agentcollecte"))
							{
								connectedUserProfilIsAgentCollecte = true;
							}
							
						else if (outcome.equals("admin"))
							connectedUserProfilIsAdmin = true;
						else if (outcome.equals("magasinier"))
							{
								connectedUserProfilIsMagasinier = true;

							try {
								listDesPointDeVenteAff = commonService.loadAllIntrantFromAffectations(connectionDTO.getId());
								nbPvaffectes = listDesPointDeVenteAff != null ? listDesPointDeVenteAff.size() : 0;
							} catch (EntityDBDAOException e) {
								e.printStackTrace();
							}

							}
						else if (outcome.equals("drdr"))
							connectedUserProfilIsDRDR = true;
					}
				}
			} else
				outcome = "change";

			// si le MDP est modifié par admin , il faut obliger un changement
			// de mot de passe
			if (connectionDTO.getMotDePasse().equals("achanger")) {
				// conneted = false;
				return "reglagesoffline.xhtml?faces-redirect=true&idBlocToShow=1";

			}
		} else {
			outcome = ConstantPGCA.ERROR;
		}

		if (!conneted) {

			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, connectionDTO.getMsgEchecConnection(), "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "connexion.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		return outcome;
	}

	// Deconnexion

	public String logout() {
		try {
			connectionService.seDeconnecter();
		} catch (ConnectionException ce) {
			return ConstantPGCA.ERROR;
		}
		return ConstantPGCA.LOGOUT;
	}

	public String getDashboardCodeFromConnnedtedUser(HttpSession session) {
		String profilType = (String) session.getAttribute("profil");

		if (profilType.equals("manager"))
			return TYPEPROFIL.manager.toString();
		else if (profilType.equals("agentsaisie"))
			return TYPEPROFIL.agentsaisie.toString();
		else if (profilType.equals("agentcollecte"))
			return TYPEPROFIL.agentcollecte.toString();
		else if (profilType.equals("admin"))
			return TYPEPROFIL.admin.toString();
		else if (profilType.equals("magasinier"))
			return TYPEPROFIL.magasinier.toString();
		else if (profilType.equals("drdr"))
			return TYPEPROFIL.drdr.toString();

		return "defaultProfil";
	}

	public boolean isMangerProfil() {
		return connectedUserProfilIManager;
	}

	String motdePasseActuel;
	String nouveauMotDePasse;
	String nouveauMotDePasseRepeat;

	public String updatePassword() {

		if (nouveauMotDePasse.length() < 6) {
			FacesMessage msg = new FacesMessage("Votre mot de passe doit contenir au moins six caractères ...");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		if (nouveauMotDePasse.equals(motdePasseActuel)) {
			FacesMessage msg = new FacesMessage("Le nouveau mot de passe doit etre différent de l'ancien ...");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}

		if (!nouveauMotDePasse.equals(nouveauMotDePasseRepeat)) {
			FacesMessage msg = new FacesMessage("Les deux nouveaux mot de passe sont différents ...");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}

		try {
			connectionDTO.setMotDePasse(motdePasseActuel);
			if (connectionService.validate(connectionDTO)) {

				connectionDTO.setNewPassword(nouveauMotDePasseRepeat);
				connectionService.updatePassword(connectionDTO);
				FacesMessage msg = new FacesMessage("mot de passe modifié avec succès ...");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
			} else {
				FacesMessage msg = new FacesMessage("Votre mot de passe actuel est incorrect ...");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
			}
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String updatePasswordModeOffline() {

		if (nouveauMotDePasse.length() < 6) {
			FacesMessage msg = new FacesMessage("Votre mot de passe doit contenir au moins six caractères ...");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		if (nouveauMotDePasse.equals(motdePasseActuel)) {
			FacesMessage msg = new FacesMessage("Le nouveau mot de passe doit etre différent de l'ancien ...");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}

		if (!nouveauMotDePasse.equals(nouveauMotDePasseRepeat)) {
			FacesMessage msg = new FacesMessage("Les deux nouveaux mot de passe sont différents ...");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}

		try {
			connectionDTO.setMotDePasse(motdePasseActuel);
			if (connectionService.validate(connectionDTO)) {

				connectionDTO.setNewPassword(nouveauMotDePasseRepeat);
				connectionService.updatePassword(connectionDTO);
				FacesMessage msg = new FacesMessage("mot de passe modifié avec succès ...");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return redirectDashboard();
			} else {
				FacesMessage msg = new FacesMessage("Votre mot de passe actuel est incorrect ...");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
			}
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	// Change Password
	public String changePassword() throws ConnectionException {
		String outcome = "";
		FacesContext context = FacesContext.getCurrentInstance();
		// test de conformité entre le nouveau password et celui de confirmation

		if (!connectionDTO.getNewPassword().equals(connectionDTO.getConfirmPassword()))

			context.addMessage(null, new FacesMessage(
					"Le nouveau mot de passe et le mot de passe de confirmation sont différents! réessayez"));
		else
			try {
				if (connectionDTO.getNewPassword().equals(connectionDTO.getMotDePasse()))
					context.addMessage(null, new FacesMessage(
							"L'ancien et le nouveau mot de passe doivent être differents! Try again."));
				// Si tout est ok
				else {
					connectionService.updatePassword(connectionDTO);
					HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
							.getExternalContext().getRequest();

					if (request != null) {
						HttpSession session = request.getSession(false);
						if (session != null) {
							System.out.println("P ->" + session.getAttribute("profil"));
							String type = (String) session.getAttribute("profil");

							if (type.equals("SADMN") || type.equals("AGDS") || type.equals("GFEC")
									|| type.equals("ADMN") || type.equals("GCHT") || type.equals("GREF")
									|| type.equals("GCHTU"))
								outcome = type;
							else
								outcome = "GCHT";
						}
					}
				}
			} catch (ConnectionException ce) {
				ce.printStackTrace();
			}

		return outcome;
	}

	public String loadDashBord() {

		return ConstantPGCA.SUCCESS;

	}

	// Gestion redirection dashboad vers le profil connecté
	public String redirectDashboard() {
		String profilCode = null;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		if (request != null) {
			HttpSession session = request.getSession(false);//
			profilCode = (String) session.getAttribute("profil");

			if (profilCode == null) {
				return null;
			}
			if (profilCode.equals("manager")) {
				connectedUserProfilIManager = true;
				return "dashboardManager.xhtml?idBlocToShow=1";
			} else if (profilCode.equals("agentsaisie")) {
				connectedUserProfilIsAgentSaisie = true;
				return "dashboardAgentsaisie.xhtml?idBlocToShow=1";
			} else if (profilCode.equals("agentcollecte")) {
				connectedUserProfilIsAgentCollecte = true;
				return "dashboardAgentcollecte.xhtml?idBlocToShow=1";
			} else if (profilCode.equals("admin")) {
				connectedUserProfilIsAdmin = true;
				return "dashboardAdmin.xhtml?idBlocToShow=1";
			} else if (profilCode.equals("magasinier")) {
				connectedUserProfilIsMagasinier = true;
				return "dashboardMagasinier.xhtml?idBlocToShow=1";
			} else if (profilCode.equals("drdr")) {
				connectedUserProfilIsMagasinier = true;
				return "dashboardDRDR.xhtml?idBlocToShow=1";
			}

		}
		return "dashboardDefault";
	}

	// Gestion redirection dashboad vers le profil connecté
	public String redirectTORecherche() {
		String profilCode = null;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		if (request != null) {
			HttpSession session = request.getSession(false);
			profilCode = (String) session.getAttribute("profil");

			if (profilCode != null && profilCode.equals("manager") || profilCode.equals("agentsaisie"))
				return "recherchepgcaGlobal.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		return "#";
	}

	public IConnectionService getConnectionService() {
		return connectionService;
	}

	public void setConnectionService(IConnectionService connectionService) {
		this.connectionService = connectionService;
	}

	public IProgrammeAgricol getProgrammAgricoleService() {
		return programmAgricoleService;
	}

	public void setProgrammAgricoleService(IProgrammeAgricol programmAgricoleService) {
		this.programmAgricoleService = programmAgricoleService;
	}

	public ConnectionDTO getConnectionDTO() {
		if (connectionDTO == null)
			connectionDTO = new ConnectionDTO();
		return connectionDTO;
	}

	public void setConnectionDTO(ConnectionDTO connectionDTO) {
		this.connectionDTO = connectionDTO;
	}

	public DashbordDTO getDashbordDTO() {
		return dashbordDTO;
	}

	public void setDashbordDTO(DashbordDTO dashbordDTO) {
		this.dashbordDTO = dashbordDTO;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public boolean isConnectedUserProfilIsAdmin() {
		return connectedUserProfilIsAdmin;
	}

	public void setConnectedUserProfilIsAdmin(boolean connectedUserProfilIsAdmin) {
		this.connectedUserProfilIsAdmin = connectedUserProfilIsAdmin;
	}

	public boolean isConnectedUserProfilIManager() {
		return connectedUserProfilIManager;
	}

	public void setConnectedUserProfilIManager(boolean connectedUserProfilIManager) {
		this.connectedUserProfilIManager = connectedUserProfilIManager;
	}

	public boolean isConnectedUserProfilIsAgentSaisie() {
		return connectedUserProfilIsAgentSaisie;
	}

	public void setConnectedUserProfilIsAgentSaisie(boolean connectedUserProfilIsAgentSaisie) {
		this.connectedUserProfilIsAgentSaisie = connectedUserProfilIsAgentSaisie;
	}

	public boolean isConnectedUserProfilIsMagasinier() {
		return connectedUserProfilIsMagasinier;
	}

	public void setConnectedUserProfilIsMagasinier(boolean connectedUserProfilIsMagasinier) {
		this.connectedUserProfilIsMagasinier = connectedUserProfilIsMagasinier;
	}

	public String getStockParFounisseurJSONFomrat() {
		return StockParFounisseurJSONFomrat;
	}

	public void setStockParFounisseurJSONFomrat(String stockParFounisseurJSONFomrat) {
		StockParFounisseurJSONFomrat = stockParFounisseurJSONFomrat;
	}

	public boolean isConnectedUserProfilIsAgentCollecte() {
		return connectedUserProfilIsAgentCollecte;
	}

	public void setConnectedUserProfilIsAgentCollecte(boolean connectedUserProfilIsAgentCollecte) {
		this.connectedUserProfilIsAgentCollecte = connectedUserProfilIsAgentCollecte;
	}

	public Long getConnectedUserThemeId() {
		return connectedUserThemeId;
	}

	public void setConnectedUserThemeId(Long connectedUserThemeId) {
		this.connectedUserThemeId = connectedUserThemeId;
	}

	public boolean isConnectedUserProfilIsDRDR() {
		return connectedUserProfilIsDRDR;
	}

	public void setConnectedUserProfilIsDRDR(boolean connectedUserProfilIsDRDR) {
		this.connectedUserProfilIsDRDR = connectedUserProfilIsDRDR;
	}

	public ThemeDTO getTheme() {
		return theme;
	}

	public void setTheme(ThemeDTO theme) {
		this.theme = theme;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMotdePasseActuel() {
		return motdePasseActuel;
	}

	public void setMotdePasseActuel(String motdePasseActuel) {
		this.motdePasseActuel = motdePasseActuel;
	}

	public String getNouveauMotDePasse() {
		return nouveauMotDePasse;
	}

	public void setNouveauMotDePasse(String nouveauMotDePasse) {
		this.nouveauMotDePasse = nouveauMotDePasse;
	}

	public String getNouveauMotDePasseRepeat() {
		return nouveauMotDePasseRepeat;
	}

	public List<PointDeVente> getListDesPointDeVenteAff() {
		return listDesPointDeVenteAff;
	}

	public void setListDesPointDeVenteAff(List<PointDeVente> listDesPointDeVenteAff) {
		this.listDesPointDeVenteAff = listDesPointDeVenteAff;
	}

	public void setNouveauMotDePasseRepeat(String nouveauMotDePasseRepeat) {
		this.nouveauMotDePasseRepeat = nouveauMotDePasseRepeat;
	}

	public int getNbPvaffectes() {
		return nbPvaffectes;
	}

	public void setNbPvaffectes(int nbPvaffectes) {
		this.nbPvaffectes = nbPvaffectes;
	}
}
