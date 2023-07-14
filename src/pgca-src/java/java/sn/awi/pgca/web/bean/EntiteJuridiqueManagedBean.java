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

import org.apache.commons.logging.LogFactory;

import sn.awi.pgca.business.exception.EntiteJuridiqueException;
import sn.awi.pgca.business.exception.TarificationException;
import sn.awi.pgca.business.service.IEntiteJuridiqueService;
import sn.awi.pgca.business.service.ITresorerieService;
import sn.awi.pgca.utils.SpringJSFUtil;
import sn.awi.pgca.utils.StringDisplayer;
import sn.awi.pgca.utils.UtilString;
import sn.awi.pgca.web.dto.EntiteJuridiqueDTO;
import sn.awi.pgca.web.dto.ParamTarificationDTO;

@ManagedBean(name = "entiteJMB")
@SessionScoped
public class EntiteJuridiqueManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long														serialVersionUID	= 2286123543121411520L;

	private static final org.apache.commons.logging.Log	LOG								= LogFactory.getLog(EntiteJuridiqueManagedBean.class);

	@ManagedProperty(value = "#{entiteJuridiqueService}")
	private transient IEntiteJuridiqueService						entiteJuridiqueService;

	@ManagedProperty(value = "#{tarifService}")
	private transient ITresorerieService							tarifService;

	private EntiteJuridiqueDTO													enjuDTO						= new EntiteJuridiqueDTO();

	List<EntiteJuridiqueDTO>														enjuDTOs;

	private EntiteJuridiqueDTO													selectedEnjuDTO		= new EntiteJuridiqueDTO();

	public List<EntiteJuridiqueDTO> getEnjuDTOs() {
		return enjuDTOs;
	}

	public void setEnjuDTOs(List<EntiteJuridiqueDTO> enjuDTOs) {
		this.enjuDTOs = enjuDTOs;
	}

	public EntiteJuridiqueDTO getSelectedEnjuDTO() {
		return selectedEnjuDTO;
	}

	public void setSelectedEnjuDTO(EntiteJuridiqueDTO selectedEnjuDTO) {
		this.selectedEnjuDTO = selectedEnjuDTO;
	}

	@PostConstruct
	public void init() {
		//recupListEntiteJuridique();
	}

	public String initCreation() {
		String filtreProfil = null;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			filtreProfil = (String) session.getAttribute(ConstantPGCA.SESSION_CODE_PROFIL);
		}
		if (filtreProfil.equals("ADMN")) // Super admin on enlve les filtre
																			// d'entites juridiques car il doit voir
																			// tout les users!!!!
		{
			FacesMessage msg = new FacesMessage("Vous n'etes pas habiliter à Créer une entité juridique ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}

		System.out.println(" init -------------------c profil --> success returned ");
		return ConstantPGCA.SUCCESS;
	}

	public boolean afficherEjPrincipal() {
		if (enjuDTO.getIdType() != null)
			return true;
		return false;
	}

	public String creerEntiteJurique() {

		String filtreProfil = null;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			filtreProfil = (String) session.getAttribute(ConstantPGCA.SESSION_CODE_PROFIL);
		}
		if (filtreProfil.equals("ADMN")) // Super admin on enlve les filtre
																			// d'entites juridiques car il doit voir
																			// tout les users!!!!
		{
			// FacesMessage msg = new
			// FacesMessage("Vous n'etes pas habiliter à créer une entité juridique ");
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Création Entité juridique : ", "Vous n'etes pas habiliter à créer une entité juridique");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}

		boolean b = false;
		try {
			b = entiteJuridiqueService.creerEntiteJuridique(enjuDTO);
			recupListEntiteJuridique();
			if (b) {
				showMessage(b, enjuDTO.getLibelle());
				return ConstantPGCA.SUCCESS;
			}
		} catch (EntiteJuridiqueException pe) {
			LOG.error("PM :: Impossible  de creer l'entite juridique ....... " + pe.getStackTrace() + pe.getCause());

		}
		showMessage(b, enjuDTO.getLibelle());
		return ConstantPGCA.ERROR;
	}

	public String supprimerEntiteJurique() {

		if (selectedEnjuDTO == null || selectedEnjuDTO.getId() == null) /*
																																		 * ||
																																		 * selectedEnjuDTO
																																		 * .getId().
																																		 * longValue
																																		 * () == 0)
																																		 */{
			FacesMessage msg = new FacesMessage("Veuillez selectionner une ligne");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
			// return ConstantPGCA.ERROR
		}
		/* verifions si le user est habiliter a supprimer */
		String filtreProfil = null;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			filtreProfil = (String) session.getAttribute(ConstantPGCA.SESSION_CODE_PROFIL);
		}
		if (filtreProfil.equals("ADMN")) // Super admin on enlve les filtre
																			// d'entites juridiques car il doit voir
																			// tout les users!!!!
		{
			FacesMessage msg = new FacesMessage("Vous n'etes pas habiliter a supprimer l'entité juridique" + selectedEnjuDTO.getLibelle());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
		boolean b = false;
		try {
			LOG.info("EJ_MB :: Appel Service  de suppression  l'entite juridique ....... " + selectedEnjuDTO.getId());
			b = entiteJuridiqueService.supprimerEntiteJuridique(selectedEnjuDTO.getId());
			showMessageSuppression(b, "");
			recupListEntiteJuridique();
			return ConstantPGCA.UPDATE;
		} catch (EntiteJuridiqueException pe) {
			LOG.error("PM :: Impossible  de supprimer l'entite juridique ....... " + pe.getStackTrace() + pe.getCause());
		}
		showMessage(b, "");
		return ConstantPGCA.ERROR;
	}

	// init modifier entite juridique

	public String initModifierEntiteJuridique() {
		try {
			if (selectedEnjuDTO == null || selectedEnjuDTO.getId() == null) /*
																																			 * ||
																																			 * selectedEnjuDTO
																																			 * .
																																			 * getId()
																																			 * .
																																			 * longValue
																																			 * () ==
																																			 * 0)
																																			 */{
				FacesMessage msg = new FacesMessage("Veuillez selectionner une ligne");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
				// return ConstantPGCA.ERROR;
			}
			String filtreProfil = null;
			String codeEJ = null;
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			if (request != null) {
				HttpSession session = request.getSession(false);
				filtreProfil = (String) session.getAttribute(ConstantPGCA.SESSION_CODE_PROFIL);  
				codeEJ = (String) session.getAttribute(ConstantPGCA.SESSION_ID_ENTITE_JURIDIQUE);
			}
			if (filtreProfil.equals("ADMN") && selectedEnjuDTO.getId() != Long.parseLong(codeEJ)) // Super
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
				FacesMessage msg = new FacesMessage("Vous n'etes pas habiliter à modifier l'entité juridique " + selectedEnjuDTO.getLibelle());
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
			}

			enjuDTO = entiteJuridiqueService.recupEntiteJuridique(selectedEnjuDTO.getId());
			return ConstantPGCA.SUCCESS;
		} catch (Exception e) {
			return ConstantPGCA.ERROR;
		}

	}

	public String modifierEntiteJurique() {
		boolean b = false;
		try {
			b = entiteJuridiqueService.modifierEntiteJuridique(enjuDTO);
			recupListEntiteJuridique();
			if (b) {
				showMessage(b, enjuDTO.getLibelle());
				return ConstantPGCA.UPDATE;
			}
		} catch (EntiteJuridiqueException pe) {
			LOG.error("PM :: Impossible  de modifier l'entite juridique ....... " + pe.getStackTrace() + pe.getCause());
		}

		showMessage(b, enjuDTO.getLibelle());

		return ConstantPGCA.ERROR;
	}

	private String recupListEntiteJuridique() {
		String outcome = "";

		try {
			enjuDTOs = entiteJuridiqueService.recupererAllEntiteJuridique();
			outcome = "listeEntiteJuridique";
		} catch (EntiteJuridiqueException ee) {
			outcome = "creerEntiteJuridique";
		}
		return outcome;
	}

	// initiation détail entite juridique
	public String detailEntitejuridique() {
		try {
			if (selectedEnjuDTO == null || selectedEnjuDTO.getId() == null || selectedEnjuDTO.getId().longValue() == 0) {
				FacesMessage msg = new FacesMessage("Veuillez selectionner une ligne");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
				// return ConstantPGCA.ERROR;
			}

			if (entiteJuridiqueService == null)
				setEntiteJuridiqueService(entiteJuridiqueServiceBean());
			enjuDTO = entiteJuridiqueService.recupEntiteJuridique(selectedEnjuDTO.getId());
			return ConstantPGCA.SUCCESS;
		} catch (Exception e) {
			return ConstantPGCA.ERROR;
		}

	}

	private static IEntiteJuridiqueService entiteJuridiqueServiceBean() {
		return (IEntiteJuridiqueService) SpringJSFUtil.getBean("entiteJuridiqueService");
	}

	// methode utilitaire pour l'affichage des messages
	public void showMessage(boolean status, String val) {
		if (status) {
			// FacesMessage msg = new FacesMessage(val + " enregistré ");

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour Entité juridique : ", "L'entité juridique '" + val + "' est correctement enregistrée");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// return ConstantPGCA.SUCCESS;
		} else {
			// FacesMessage msg = new FacesMessage("echec enregistrement !!  <" + val
			// + ">");
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mise à jour Entité juridique : ", "Echec enregistrement de l'entité juridique '" + val + "'");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// return ConstantPGCA.ERROR;
		}
	}

	public String showMessageSuppression(boolean status, String val) {
		if (status) {
			FacesMessage msg = new FacesMessage(val + " Supprimé !! ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return ConstantPGCA.SUCCESS;
		} else {
			FacesMessage msg = new FacesMessage("echec suppression." + val);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return ConstantPGCA.ERROR;
		}
	}

	public IEntiteJuridiqueService getEntiteJuridiqueService() {
		return entiteJuridiqueService;
	}

	public void setEntiteJuridiqueService(IEntiteJuridiqueService entiteJuridiqueService) {
		this.entiteJuridiqueService = entiteJuridiqueService;
	}

	public EntiteJuridiqueDTO getEnjuDTO() {
		return enjuDTO;
	}

	public void setEnjuDTO(EntiteJuridiqueDTO enjuDTO) {
		this.enjuDTO = enjuDTO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void onEdit() throws Exception {

	}

	public String StringCutter(String text, int index) {
		if (text != null)
			if (text.length() > index)
				return StringDisplayer.cutString(text, index);
		return (text);
	}

	public void ajouterTarif() {
		if (tarifService == null)
			setTarifService(tarifServiceBean());
		if (enjuDTO.getTarificationDTO() != null) {
			enjuDTO.getTarificationDTO().setIdEntiteJuridique(enjuDTO.getId().longValue()+"");
			String serreur = incorrectSaisieTarif(enjuDTO.getTarificationDTO());
			if (UtilString.isCorrect(serreur)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur Ajout Tarif", serreur);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
			//tarifService.mettreAJourTarification(enjuDTO.getTarificationDTO());
			enjuDTO.getTarificationDTOs().clear();
			//List<ParamTarificationDTO> lParamTarificationDTOs = tarifService.loadTarificationByEntiteJuridique(enjuDTO.getId().longValue() + "");
			//enjuDTO.getTarificationDTOs().addAll(lParamTarificationDTOs);
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur Ajout Tarif", "Le tarif doit être renseigné");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void initModifTarif(Long numero) {
		if (enjuDTO == null || enjuDTO.getTarificationDTOs().size() == 0)
			return;
		for (ParamTarificationDTO a : enjuDTO.getTarificationDTOs()) {
			if (a.getId().longValue() == numero.longValue()) {
				copyTarificationDTO(enjuDTO.getTarificationDTO2(), a);
				break;
			}
		}
	}

	public void modifierTarif() {
		if (enjuDTO == null || enjuDTO.getTarificationDTOs().size() == 0)
			return;
		String serreur = incorrectSaisieTarif(enjuDTO.getTarificationDTO2());
		if (UtilString.isCorrect(serreur)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur Modification tarif", serreur);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		//tarifService.mettreAJourTarification(enjuDTO.getTarificationDTO2());
		enjuDTO.getTarificationDTOs().clear();
		//List<ParamTarificationDTO> lParamTarificationDTOs = tarifService.loadTarificationByEntiteJuridique(enjuDTO.getId().longValue() + "");
		//enjuDTO.getTarificationDTOs().addAll(lParamTarificationDTOs);
	}

	private void copyTarificationDTO(ParamTarificationDTO tarDTO, ParamTarificationDTO srcTarifDTO) {
		tarDTO.setIdEntiteJuridique(srcTarifDTO.getIdEntiteJuridique());
		tarDTO.setIdTypeFormulaire(srcTarifDTO.getIdTypeFormulaire());
		tarDTO.setCoutBase(srcTarifDTO.getCoutBase());
		tarDTO.setCoutParUniteBase(srcTarifDTO.getCoutParUniteBase());
		tarDTO.setFraisDeGestion(srcTarifDTO.getFraisDeGestion());
		tarDTO.setUniteDeBase(srcTarifDTO.getUniteDeBase());
		tarDTO.setId(srcTarifDTO.getId());
	}

	// private void copyTarificationDTOAndClear(ParamTarificationDTO tarDTO,
	// ParamTarificationDTO srcTarifDTO) {
	// tarDTO.setIdEntiteJuridique(srcTarifDTO.getIdEntiteJuridique());
	// tarDTO.setIdTypeFormulaire(srcTarifDTO.getIdTypeFormulaire());
	// tarDTO.setCoutBase(srcTarifDTO.getCoutBase());
	// tarDTO.setCoutParUniteBase(srcTarifDTO.getCoutParUniteBase());
	// tarDTO.setFraisDeGestion(srcTarifDTO.getFraisDeGestion());
	// tarDTO.setUniteDeBase(srcTarifDTO.getUniteDeBase());
	// tarDTO.setStrCoutBase(CurrencyUtils.formatMontantSansMonnaie(srcTarifDTO.getCoutBase()));
	// tarDTO.setStrCoutParUniteBase(CurrencyUtils.formatMontantSansMonnaie(srcTarifDTO.getCoutParUniteBase()));
	// tarDTO.setStrFraisDeGestion(CurrencyUtils.formatMontantSansMonnaie(srcTarifDTO.getFraisDeGestion()));
	// tarDTO.setStrUniteDeBase(CurrencyUtils.formatMontantSansMonnaie(srcTarifDTO.getUniteDeBase()));
	//
	// srcTarifDTO.setIdEntiteJuridique("");
	// srcTarifDTO.setIdTypeFormulaire("");
	// srcTarifDTO.setCoutBase(0);
	// srcTarifDTO.setCoutParUniteBase(0);
	// srcTarifDTO.setFraisDeGestion(0);
	// srcTarifDTO.setUniteDeBase(0);
	// }

	private String incorrectSaisieTarif(ParamTarificationDTO tarifDto) {
		List<String> incorrectAttributes = tarifDto.incorrectAttribute();
		if (incorrectAttributes != null && incorrectAttributes.size() > 0) {
			StringBuffer sb = new StringBuffer("");
			if (incorrectAttributes.size() > 1)
				sb.append("Les champs suivants sont obligatoires : ");
			else
				sb.append("Le champs suivant est obligatoire : ");
			boolean first = true;
			for (String s : incorrectAttributes) {
				if (!first)
					sb.append(", ");
				sb.append(s);
				first = false;
			}
			return sb.toString();
		}
		return null;
	}

	public ITresorerieService getTarifService() {
		return tarifService;
	}

	public void setTarifService(ITresorerieService tarifService) {
		this.tarifService = tarifService;
	}

	private static ITresorerieService tarifServiceBean() {
		return (ITresorerieService) SpringJSFUtil.getBean("tarifService");
	}
}
