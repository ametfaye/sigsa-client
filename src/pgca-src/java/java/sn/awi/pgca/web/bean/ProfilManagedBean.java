package sn.awi.pgca.web.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.LogFactory;

import sn.awi.pgca.business.exception.ProfilException;
import sn.awi.pgca.business.service.IProfilService;
import sn.awi.pgca.utils.StringDisplayer;
import sn.awi.pgca.utils.UtilString;
import sn.awi.pgca.web.dto.ActionDTO;
import sn.awi.pgca.web.dto.MenusousMenusDTO;
import sn.awi.pgca.web.dto.ProfilDTO;

@ManagedBean(name = "profilMB")
@RequestScoped
public class ProfilManagedBean implements Serializable {

	private static final long serialVersionUID = -7103834664247383927L;
	private static final org.apache.commons.logging.Log LOG = LogFactory
			.getLog(ProfilManagedBean.class);

	private long idSelectedProfil;

	@ManagedProperty(value = "#{profilService}")
	private IProfilService profilService;

	private ProfilDTO profilDTO = new ProfilDTO();
	List<ProfilDTO> profilDTOs = null;
	List<ActionDTO> profilActionsList = new ArrayList<ActionDTO>();
	List<MenusousMenusDTO> profilMenuSousMenusList = new ArrayList<MenusousMenusDTO>();

	private ProfilDTO selectedProfilDTO = new ProfilDTO();
	private String code; // recup�ration
							// dernier
							// code
							// profil

	public ProfilManagedBean() {
	}

	@PostConstruct
	public void init() {
		//recupListAction();
		//recupListMenuSousmenu();
	}

	public String initCreateProfil() {
		code = getLastCodeProfil();
		return "creerProfil";
	}

	public String initModifierProfil() throws IOException {
		try {
			if (selectedProfilDTO == null || selectedProfilDTO.getId() == null
					|| selectedProfilDTO.getId().longValue() == 0) {
				FacesMessage msg = new FacesMessage(
						"Selectionnez le profil à modifier");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
				// return ConstantPGCA.ERROR;
			}

			String filtreProfil = null;
			String codeEJ = null;
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			if (request != null) {
				HttpSession session = request.getSession(false);
				filtreProfil = (String) session
						.getAttribute(ConstantPGCA.SESSION_CODE_PROFIL);
				codeEJ = (String) session
						.getAttribute(ConstantPGCA.SESSION_ID_ENTITE_JURIDIQUE);
			}
			if (filtreProfil.equals("ADMN")
					&& selectedProfilDTO.getCodeEj() != Long.parseLong(codeEJ)) // Super
																				// admin
			// les
			// users!!!!
			{
				FacesMessage msg = new FacesMessage(
						"Vous n'etes pas habiliter à modifier le profil "
								+ selectedProfilDTO.getLibelle());
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
			}

			profilDTO = profilService.getProfilById(selectedProfilDTO.getId());

		} catch (ProfilException e) {
			return ConstantPGCA.ERROR;
		}
		return ConstantPGCA.UPDATE;
	}

	public String initDetailsProfil() throws IOException {
		try {
			if (selectedProfilDTO == null || selectedProfilDTO.getId() == null
					|| selectedProfilDTO.getId().longValue() == 0) {
				FacesMessage msg = new FacesMessage(
						"Selectionner un profil à voir");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
				// return ConstantPGCA.ERROR;

			}

			profilDTO = profilService.getProfilById(selectedProfilDTO.getId());
			// return ConstantPGCA.DETAILS;
			return ConstantPGCA.DETAILS;

		} catch (ProfilException e) {
			return ConstantPGCA.ERROR;
		}
	}

	public String updateProfil() throws IOException {
		/*try {
			if (profilService.modifierProfil(profilDTO)) {
				showMessage(false, profilDTO.getLibelle());
				return ConstantPGCA.RETURN;
			}
		} catch (Exception e) {
		}
		showMessage(false, profilDTO.getLibelle());*/
		return ConstantPGCA.ERROR;
	}

	public String createProfil() {
		profilDTO.setCode(code); // setter le code referentiel !
		boolean b = false;
		try {
			b = profilService.creerProfil(profilDTO);
		} catch (ProfilException pe) {
			LOG.error("PM :: Impossible de creer le Profil ....... "
					+ pe.getStackTrace() + pe.getCause());
		}
		return showMessage(b, profilDTO.getLibelle());
	}




	public List<ProfilDTO> recupListProfil() {
		try {
			return profilService.recupererAllProfil();
		} catch (ProfilException pe) {
			return null;
		}
	}

	public String getLastCodeProfil() {
		try {
			String nr = profilService.getCodeLastProfil();
			return nr;
		} catch (ProfilException pe) {
			LOG.error("Erreur recuperation referentiel profil");
		}
		return null;
	}

	public boolean onEdit() throws Exception {
		if (selectedProfilDTO != null
				&& UtilString.isCorrect(selectedProfilDTO.getLibelle())) {
			System.out.println("Select Event" + selectedProfilDTO.getLibelle()
					+ "donc j'envoi true");
			return true;
		} else {
			System.out
					.println("No Slect Event donc j'envoi false et tu peux pas passer !!!");
			return false;
		}
	}

	// methode utilitaire pour l'affichage des messages
	public String showMessage(boolean status, String val) {
		if (status) {
			// FacesMessage msg = new FacesMessage(val + " enregistré ");
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"MAJ Profil : ", "Le profil  " + val + " a été enregistré.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return ConstantPGCA.SUCCESS;
		} else {
			// FacesMessage msg = new FacesMessage("echec enregistrement." +
			// val);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"MAJ Profil : ", "Echec de l'enregistrement du profil  "
							+ val + ".");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return ConstantPGCA.ERROR;
		}
	}

	public String Cancel() {
		return ConstantPGCA.RETURN;
	}

	public String onDelete() {
		try {

			if (selectedProfilDTO == null || selectedProfilDTO.getId() == null
					|| selectedProfilDTO.getId().longValue() == 0) {
				FacesMessage msg = new FacesMessage(
						"Selectionnez le profil à supprimer");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
				// return ConstantPGCA.ERROR;

			} else {

				String filtreProfil = null;
				String codeEJ = null;
				HttpServletRequest request = (HttpServletRequest) FacesContext
						.getCurrentInstance().getExternalContext().getRequest();
				if (request != null) {
					HttpSession session = request.getSession(false);
					filtreProfil = (String) session
							.getAttribute(ConstantPGCA.SESSION_CODE_PROFIL);
					codeEJ = (String) session
							.getAttribute(ConstantPGCA.SESSION_ID_ENTITE_JURIDIQUE);
				}
				if (filtreProfil.equals("ADMN")
						&& selectedProfilDTO.getCodeEj() != Long
								.parseLong(codeEJ)) // Super
													// admin
													// on
													// enlve
													// les
													// filtre
													// d'entites
													// juridiques
													// car
													// il
													// doit
													// voir
													// tout
													// les
													// users!!!!
				{
					FacesMessage msg = new FacesMessage(
							"Vous n'etes pas habiliter à modifier le profil "
									+ selectedProfilDTO.getLibelle());
					FacesContext.getCurrentInstance().addMessage(null, msg);
					return null;
				}

				code = selectedProfilDTO.getCode(); // utilé juste pour afficher
													// le code
													// du message supprimé
				if (profilService.removeProfil(selectedProfilDTO.getId())) {
					recupListProfil();
					FacesMessage msg = new FacesMessage("Profil  " + code
							+ "  supprimé ...");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				} else {
					FacesMessage msg = new FacesMessage(
							"Echec suppression  du profil  " + code + "...");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			}
		} catch (ProfilException e) {
			e.printStackTrace();
		}
		return ConstantPGCA.SUCCESS;
	}

	public IProfilService getProfilService() {
		return profilService;
	}

	public void setProfilService(IProfilService profilService) {
		this.profilService = profilService;
	}

	public ProfilDTO getProfilDTO() {
		if (profilDTO == null)
			profilDTO = new ProfilDTO();
		return profilDTO;
	}

	public void setProfilDTO(ProfilDTO profilDTO) {
		this.profilDTO = profilDTO;
	}

	public List<ProfilDTO> getProfilDTOs() {
		return profilDTOs;
	}

	public void setProfilDTOs(List<ProfilDTO> profilDTOs) {
		this.profilDTOs = profilDTOs;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Collection<ActionDTO> getProfilActionsList() {
		return profilActionsList;
	}

	public Collection<MenusousMenusDTO> getProfilMenuSousMenusList() {
		return profilMenuSousMenusList;
	}

	public void setProfilActionsList(List<ActionDTO> profilActionsList) {
		this.profilActionsList = profilActionsList;
	}

	public ProfilDTO getSelectedProfilDTO() {
		return selectedProfilDTO;
	}

	public void setSelectedProfilDTO(ProfilDTO selectedProfilDTO) {
		this.selectedProfilDTO = selectedProfilDTO;
	}

	public void setProfilMenuSousMenusList(
			List<MenusousMenusDTO> profilMenuSousMenusList) {
		this.profilMenuSousMenusList = profilMenuSousMenusList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getIdSelectedProfil() {
		return idSelectedProfil;
	}

	public void setIdSelectedProfil(long idSelectedProfil) {
		this.idSelectedProfil = idSelectedProfil;
	}

	public String StringCutter(String text, int index) {
		if (text != null)
			if (text.length() > index)
				return StringDisplayer.cutString(text, index);
		return (text);
	}

}
