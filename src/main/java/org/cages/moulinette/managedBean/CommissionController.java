package org.cages.moulinette.managedBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.cages.moulinette.dto.CoupleDTO;
import org.cages.moulinette.dto.EnrollementDTO;
import org.cages.moulinette.dto.InfosCommunesDTO;
import org.cages.moulinette.dto.PersonnePhysiqueDTO;
import org.cages.moulinette.dto.PointDeVenteDTO;
import org.cages.moulinette.dto.ProducteurDTO;
import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.exceptions.EnrollementException;
import org.cages.moulinette.exceptions.EntityPersisitanteException;
import org.cages.moulinette.model.PersonnePhysique;
import org.cages.moulinette.service.IAgentService;
import org.cages.moulinette.service.ICommonService;
import org.cages.moulinette.service.IPersonnePhysiqueService;
import org.cages.moulinette.service.IProducteurService;
import org.cages.moulinette.service.IUserService;
import org.cages.moulinette.utils.CONSTANTES;
import org.cages.moulinette.utils.UtilString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "CommissionController")
@SessionScoped
@Getter
@Setter
public class CommissionController {

	private Logger logger = LoggerFactory.getLogger(CommissionController.class);
	private boolean blocAlert;
	private String blocAlertTitre, blocAlertMsg, blocAlertBackroundColor;

	PersonnePhysiqueDTO beneficiaireEnrollement;

	EnrollementDTO infosEnrollement;
	List<ProducteurDTO> listProducteursDTO;

	private UserInfosDTO connectedUserDetails = new UserInfosDTO();
	private List<PersonnePhysiqueDTO> personnePhysiqueDTOs = new ArrayList<>();
	private List<PersonnePhysique> personnePhysiques = new ArrayList<>();
	private List<PersonnePhysiqueDTO> selectedPphs = new ArrayList<>();

	private ProducteurDTO producateurAcrer = new ProducteurDTO();

	private Date dateImmatriculation;
	HttpServletRequest request;
	Integer nbrOrdreATraiter;
	Long pphIdAdd;
	String matriculeUp;
	private String blocErreurMsg, blocConfirmationMsg;
	private String typeProducteur = "PersonneMorale";
	private Long destinationListDTOSelectedId;

	@ManagedProperty(value = "#{userService}")
	private IUserService userService;

	@ManagedProperty(value = "#{agentService}")
	private IAgentService agentService;

	@ManagedProperty(value = "#{personnePhysiqueService}")
	private IPersonnePhysiqueService personnePhysiqueService;

	@ManagedProperty(value = "#{producteurService}")
	private IProducteurService producteurService;

	@ManagedProperty(value = "#{commonServices}")
	private ICommonService commonServices;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String msg) {
		kafkaTemplate.send("topic_0", msg);
	}

	@PostConstruct
	private void initConfiguration() {
		resetErrorMsgs();
		producateurAcrer = new ProducteurDTO();
		beneficiaireEnrollement = new PersonnePhysiqueDTO();
		infosEnrollement = new EnrollementDTO();
		loadListRegion();
	}

	public String initListProducteur() {
		if (listProducteursDTO == null)
			try {
				listProducteursDTO = producteurService.loadListProducteur();
			} catch (EntityPersisitanteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return "pgcaAgriculteursList.xhtml";

	}

	public String checkValidityEnrollement() {
		resetErrorMsgs();

		commonServices.sendMessage(
				"msg from SIGSA" + beneficiaireEnrollement.getPrenom() + " " + beneficiaireEnrollement.getNom());

		if (UtilString.isEmpty(infosEnrollement.getBeneficiaireEnrollement().getNom())) {
			createAlernMsg(CONSTANTES.AlertWARN, "Données obligatoire", " Le nom est requis ");
			return "pgcaAgriculteurEdition.xhtml?faces-redirect=true";
		}

		if (UtilString.isEmpty(infosEnrollement.getBeneficiaireEnrollement().getPrenom())) {
			createAlernMsg(CONSTANTES.AlertWARN, "Données obligatoire", " Le prenom est requis ");
			return "pgcaAgriculteurEdition.xhtml?faces-redirect=true";
		}

		String utlRedirect = "";

		try {

			infosEnrollement.setIdRegion(idSelectedRegion);
			infosEnrollement.setIdDepartement(idSelectedDept);
			infosEnrollement.setIdCommune(idSelectedCom);

			return utlRedirect = processEnrollement();
		} catch (EnrollementException e) {
			logger.error("Erreur Enrollment surveneue  : " + e.getStackTrace());
		}

		return utlRedirect;
	}

	ProducteurDTO createdProducteurDTO;

	public String processEnrollement() throws EnrollementException {

		try {
			createdProducteurDTO = producteurService.createProducteur(infosEnrollement);
			createAlernMsg(CONSTANTES.AlertValid, "Confirmation", "Producteur enrollé avec success : ");

		} catch (EntityPersisitanteException e) {
			logger.error("Erreur Enrollment surveneue  : " + e.getStackTrace());
			createAlernMsg(CONSTANTES.AlertError, "Données obligatoire", " Le NINEA est requis ");
			return "pgcaAgriculteurEdition.xhtml?faces-redirect=true";

		}
		return "pgcaAgriculteursDetails.xhtml?faces-redirect=true";
	}

	public String createError(String attribute, String ErrorPage) {
		blocErreurMsg = "le champ : " + attribute + " est obligatoire";
		return (ErrorPage);
	}

	public void resetErrorMsgs() {
		blocAlert = false;
		setBlocConfirmationMsg(null);
		setBlocErreurMsg(null);
	}

	public void createAlernMsg(String type, String titre, String message) {
		blocAlert = true;

		blocAlertBackroundColor = type;
		blocAlertTitre = titre;
		blocAlertMsg = message;
	}

	/** UTILS METHODES GROUP ***/

	List<CoupleDTO> reflistRegion;
	Long idSelectedRegion;

	public List<CoupleDTO> loadListRegion() {
		if (reflistRegion != null && reflistRegion.size() > 0)
			return reflistRegion;

		reflistRegion = commonServices.loadRegion();
		return reflistRegion;
	}

	/*** END UTILS ****/

	/**** METHODE AJAX ****/
	List<CoupleDTO> reflistDepart;
	Long idSelectedDept;

	public void loadDepartementsByIdRegion(final AjaxBehaviorEvent event) {
		/*
		 * if(reflistDepart != null && reflistDepart.size() > 0 ) return ;
		 */
		reflistDepart = commonServices.loadAllDepartementOfRegion(idSelectedRegion);
	}

	List<CoupleDTO> reflistCom;
	Long idSelectedCom;

	public void loadCommunesByIdDepart(final AjaxBehaviorEvent event) {
		/*
		 * if(reflistDepart != null && reflistDepart.size() > 0 ) return ;
		 */
		reflistCom = commonServices.loadAllCommunOfdepartement(idSelectedDept);
	}

	/***
	 * INIT METHODES
	 */

	public String initEnrollementProcess() {
		loadListRegion();
		return "pgcaAgriculteursCommissionEdition.xhtml";
	}

	public String initLoadDetailsAgriculteurs() {

		request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String numprd = request.getParameter("idSelectedProdId");

		try {
			createdProducteurDTO = producteurService.loadProducteurByNumero(numprd);
			return "pgcaAgriculteurDetails.xhtml";

		} catch (EntityPersisitanteException e) {
			logger.error("Erreur survenue lors de la recupération" + e.getMessage());
			e.printStackTrace();
		}
		return "";

	}
	
	List<CoupleDTO> communeAjouter;

	public String ajouterCommune() {
		
		if(null == communeAjouter)
			communeAjouter = new ArrayList<>();
		
		CoupleDTO c = reflistCom.stream().filter(user -> user.getIdGenerique().equals(idSelectedCom)).findFirst().get();
		
		if(! communeAjouter.contains(c))
			communeAjouter.add(c);
		
		return "pgcaAgriculteursCommissionEdition.xhtml";

	}

}
