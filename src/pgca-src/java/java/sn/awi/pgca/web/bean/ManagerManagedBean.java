package sn.awi.pgca.web.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.util.Log;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.exception.ProgrammeException;
import sn.awi.pgca.business.service.ICommonService;
import sn.awi.pgca.business.service.IManagerService;
import sn.awi.pgca.business.service.IProgrammeAgricol;
import sn.awi.pgca.business.service.ITresorerieService;
import sn.awi.pgca.dataModel.Intrant;
import sn.awi.pgca.dataModel.PointDeVente;
import sn.awi.pgca.utils.UtilString;
import sn.awi.pgca.web.dto.AllocationDTO;
import sn.awi.pgca.web.dto.AvanceCreditDTO;
import sn.awi.pgca.web.dto.CampagneAgricoleDTO;
import sn.awi.pgca.web.dto.ChequeDTO;
import sn.awi.pgca.web.dto.CollaborateurDTO;
import sn.awi.pgca.web.dto.CommandeDTO;
import sn.awi.pgca.web.dto.CommissionDTO;
import sn.awi.pgca.web.dto.CoupleDTO;
import sn.awi.pgca.web.dto.CreditDTO;
import sn.awi.pgca.web.dto.EngraisDTO;
import sn.awi.pgca.web.dto.IndicateursDashboardDTO;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.IntrantTypeDTO;
import sn.awi.pgca.web.dto.LitigesDTO;
import sn.awi.pgca.web.dto.MiseEnplaceDTOCommune;
import sn.awi.pgca.web.dto.MiseEnplaceDTOPointDeVente;
import sn.awi.pgca.web.dto.PersonneDTO;
import sn.awi.pgca.web.dto.PointDeCollecteDTO;
import sn.awi.pgca.web.dto.PointDeVenteDTO;
import sn.awi.pgca.web.dto.ProduitDTO;
import sn.awi.pgca.web.dto.ProgrammeAgricolDTO;
import sn.awi.pgca.web.dto.SubventionDTO;
import sn.awi.pgca.web.dto.VentesDTO;
import sn.awi.pgca.web.dto.VersementBanqueDTO;

@ManagedBean(name = "managerManagedBean")
@SessionScoped
public class ManagerManagedBean implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 3792792279382904542L;

	@ManagedProperty(value = "#{commonService}")
	private ICommonService commonService;
	@ManagedProperty(value = "#{programmeAgricolService}")
	private IProgrammeAgricol programmAgricoleService;

	@ManagedProperty(value = "#{managerService}")
	private IManagerService managerService;

	@ManagedProperty(value = "#{tresorerieService}")
	private ITresorerieService tresorerieService;

	/****** AUTO Complemention des referentiels ****/
	private String listOfProductFromDBInJSON;
	/****** MAP DATA ****/
	private String selectedAreaOnMap; // Id region clické depuis MAP
	private String mapAllPointDeVenteInJSON;
	private String mapRegion;
	private String mapAllProduitofPointDeVenteInJSON; // Tous les produit du
														// poit de vente en JSON
	private List<PointDeVenteDTO> allpointdeVenteOfSelectedRegion;
	private String selectedPointDeVente; // Point de vente Speficique sur liste
											// des PV mapAllPointDeVenteInJSON
	private Long selectedPointDeVenteId;
	private String selectedPointDeVenteCommune;
	private String selectedPointDeVenteDepartement;

	private UtilString utils = new UtilString();
	private Long selectedProduitId;
	// informations du point de vente selectionne
	private List<VentesDTO> listeVenteDTOofSelectePV;
	private float listeCreditDTOofSelectePVTotal;
	private List<CreditDTO> listecreditDTOofSelectePV;
	private float listecreditDTOofSelectePVTotal;
	private List<VersementBanqueDTO> listeversementBanqueofSelectePV;
	private float listeversementBanqueofSelectePVTotal;
	private List<VentesDTO> listeVenteofSelectePV;
	private float listeVentefSelectePVTotal;

	// Commission
	private List<CommissionDTO> listedesCommission;
	private CommissionDTO selectedCommisionDTO;

	// Allocation budgetaire
	private AllocationDTO budgetAlloue;
	private SessionManagedBean sessionMB;
	private List<CoupleDTO> paysDtos;
	private List<CoupleDTO> regionDtos;
	private List<CoupleDTO> departements;
	private List<CoupleDTO> communes;

	private VentesDTO selectedVenteDTO;
	private List<CollaborateurDTO> collaborateursDTOs;
	private List<PointDeCollecteDTO> pointdeCollecteDTOs;
	private PointDeCollecteDTO selectedPointdeCollecte;
	private PointDeVenteDTO selectedPv;

	private CampagneAgricoleDTO selectedCampagneAgricoleDTO;
	private PointDeVenteDTO pointDeVente;
	private EngraisDTO selectedEngraisDTO;
	private IntrantDTO selectedIntrantDTO;
	private IntrantDTO engraisDTO;
	private List<ProduitDTO> listProduitslieeSelectedVente;
	private CoupleDTO stockDTO = new CoupleDTO("", "");
	private CampagneAgricoleDTO campagneDTO;
	private float montantTotalDeposeEnBanque;
	private ChequeDTO selectedChequeDTO;
	private List<CommandeDTO> listBLAvalider;
	private List<CommandeDTO> listOLAvalider;

	// referentiels
	private IntrantTypeDTO intrantType;

	// Ordre de libvraions :
	private CommandeDTO commandeDTO;
	private List<ProduitDTO> listProduitCommande;
	private Long olIdPointdeVente;
	private Long olIdProgramme;
	private int olstatut;
	private String olclient;

	private PointDeCollecteDTO selectedPointDeCollecte;

	private ProgrammeAgricolDTO selectedProgrammeAgricolDTO;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		sessionMB = new SessionManagedBean();
		engraisDTO = new IntrantDTO();
		selectedIntrantDTO = new IntrantDTO();
		paysDtos = commonService.loadPays();
		pointDeVente = new PointDeVenteDTO();
		campagneDTO = new CampagneAgricoleDTO();
		budgetAlloue = new AllocationDTO();
		intrantType = new IntrantTypeDTO();

		// init Bl a valider
		loadAllOrdresDeLivraion();

		// GEt current campagne from session
		Long idProgammeActif = 0l;
		
		try {
			mapAllPointDeVenteInJSON = getAllPointDeVenteOfCamapagne(idProgammeActif);
		} catch (Exception e) {
			Log.error("Erreur chargement MAP pour les managers " + e.getMessage());
		}
		
		listOfProductFromDBInJSON = commonService.loadAllProductInJsonFormat(1000);
		
		loadAllMiseEnplace();
		// load mep en cours 
		//getAllMiseEnPlaceEncours();
	}

	/******* UTILS FOR MAP ******/
	public String getAllPointDeVenteOfCamapagne(Long idProgramme) {
		List<PointDeVenteDTO> allPV = programmAgricoleService.loadAllPointDeVenteByIdProgramme(0L);

		if (allPV == null)
			return "";

		JSONArray allPVinJsonFormat = new JSONArray();
		String infoDetailsStock = " <br/>";
		for (PointDeVenteDTO pv : allPV) {
			infoDetailsStock += "<u>" + pv.getLibelle() + "</u> <br/>";
			if (pv.getIdstockReference() != null) // Stock PV
			{
				try {
					List<ProduitDTO> stockInfos = programmAgricoleService
							.loadAallProduitFromStockbyIdStock(pv.getIdstockReference());
					if (stockInfos != null) {
						for (ProduitDTO p : stockInfos) {

							infoDetailsStock += p.getQuantite() + "T de " + p.getLibelle() + "<br/>";
						}
					} else
						infoDetailsStock += "Auncun Produit disponible <br/>";

				} catch (EntityDBDAOException e) {
					Log.error("Une erreur est surveneue lors de la lecture du stock avec id stock = "
							+ pv.getIdstockReference());
					e.printStackTrace();
				}
			}

			JSONObject j = new JSONObject();

			j.put("id", translateCodeRegionToMapCoord(pv.getCodeRegion()));
			j.put("color", "#002839");
			j.put("title", infoDetailsStock);
			j.put("idRegion", pv.getIdRegion());

			allPVinJsonFormat.put(j);
			infoDetailsStock = "";
		}
		return allPVinJsonFormat.toString();
	}

	public String redirectDetailSelectedArea() {

		Long idProgramme = 0L; // TODO ID PROGRAMME A RECUPERRE

		/*******
		 * Recuperation des points de vente d'une region selectionné depuis la
		 * map a partir du code region
		 *****/
		try {
			allpointdeVenteOfSelectedRegion = programmAgricoleService
					.loadAllPointDeVenteByIdProgrammeAndCodeRegion(idProgramme, new Long(selectedAreaOnMap));

			JSONArray allProduitOFZoneJsonFormat = new JSONArray();
			List<ProduitDTO> stock;

			for (PointDeVenteDTO point : allpointdeVenteOfSelectedRegion) {
				stock = point.getStockPointDeVente();
				int i = 1;

				for (ProduitDTO p : stock) {
					JSONObject produit = new JSONObject();
					produit.put("iDstockPV", point.getIdStockReference());
					produit.put("iDPv", point.getIdPv());
					produit.put("name", p.getLibelle());
					produit.put("y", p.getQuantite());
					if (i == 1) {
						produit.put("sliced", true);
						produit.put("selected", true);
					}
					i++;
					allProduitOFZoneJsonFormat.put(produit);
				}
				mapRegion = point.getRegion();
			}
			// map data
			mapAllProduitofPointDeVenteInJSON = allProduitOFZoneJsonFormat.toString();
		} catch (EntityDBDAOException e) {
			Log.error("Erreur de recuperation du code Region " + e.getMessage());
		}
		return "managerDetailsSelectedRegion.xhtml?faces-redirect=false&idBlocToShow=1";
	}

	/*****
	 * ALLOCATION BUGDET DE COLLECTE
	 * 
	 * @throws FileNotFoundException
	 *******************/

	public String enregistrerAllocationBudeget() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		Log.info("Tentative Allocation  ::::::::::::: " + budgetAlloue.getMontantalloue());

		// Pré requis Allocation

		if (budgetAlloue.getMontantalloue() == 0) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le montant de collecte est requis");
			return "";
		}

		try {
			budgetAlloue.setAuteurAllocation(SessionManagedBean.getSessionDataByTag("connectedUserName"));

			if (tresorerieService.AllouerBudget(budgetAlloue)) {
				showMessage(FacesMessage.SEVERITY_INFO,
						"Budget de " + utils.formatFloatToCFA(budgetAlloue.getMontantalloue())
								+ " alloué avec succès à " + budgetAlloue.getCollaborateurBeneficiare());
				return "managerServices.xhtml?faces-redirect=true&idBlocToShow=2";
			}
		} catch (Exception e) {
			Log.error("Une erreur est survenue lors de l'allocation du budget de collecte");
			e.printStackTrace();
		}
		return "managerServices.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public List<PersonneDTO> loadColloborateurOfPointDeCollecte() {

		if (budgetAlloue != null && budgetAlloue.getIdPointDecollecteBeneficiaire() != null)
			try {
				return commonService
						.loadUtilisateurFromPointDeCollecte(budgetAlloue.getIdPointDecollecteBeneficiaire());
			} catch (EntityDBDAOException e) {
				Log.error("Erreur de recup des des coolaborateur" + e.getMessage());
			}
		return null;
	}

	public List<CoupleDTO> loadProduitByCategorie() {
		if (budgetAlloue != null && budgetAlloue.getIdCategorieProduitACollecter() != null)
			try {
				return commonService.loadAllProduitByIdTypeProduit(budgetAlloue.getIdCategorieProduitACollecter());
			} catch (EntityDBDAOException e) {
				Log.error("Erreur de recup des des collaborateur" + e.getMessage());
			}
		return null;
	}

	public void loadAllOrdresDeLivraion() {
		try {
			listOLAvalider = programmAgricoleService.managerLoadOrdresAvalider();
		} catch (Exception e) {
			Log.error("Erreur de recup des ordres" + e.getMessage());
			e.printStackTrace();
		}
	}

	float totalSubvention;
	List<SubventionDTO> listSubvention;

	public List<SubventionDTO> loadAllSubventions() {
		totalSubvention = 0;

		try {
			listSubvention = tresorerieService.loadAllSubventions();

			for (SubventionDTO s : listSubvention)
				totalSubvention += s.getMontantSubvention();

			return listSubvention;

		} catch (EntityDBDAOException e) {
			Log.error("Erreur de recup des subventions" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public void loadOrdresEnvoyesByPointDeCollecteParCriteria() {
		try {

			listOLAvalider = programmAgricoleService.managerLoadOrdresAvaliderByCriteria(olIdProgramme,
					olIdPointdeVente, olstatut, olclient);
		} catch (Exception e) {
			Log.error("Erreur de recup des ordres" + e.getMessage());
			e.printStackTrace();
		}
	}

	public List<CommandeDTO> loadAllCommande() {
		try {

			return programmAgricoleService.getListCommandeEnvoyeByidPointdeCollect(0L);

		} catch (Exception e) {
			Log.error("Erreur de recup des ordres" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public int loadOrdresEnAttentDeValidation() {
		try {

			loadAllOrdresDeLivraion();
			return managerService.loadOlenAttenteDeValidation(0L);
		} catch (Exception e) {
			Log.error("Erreur de recup des ordres à valider" + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	public String loadDetailsSelectedOrdre() {
		if (commandeDTO == null || commandeDTO.getIdOrdre() == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner un ordre de livraison");
			return "managerGestionOL.xhtml?faces-redirect=true&idBlocToShow=1b";
		}
		try {
			commandeDTO = programmAgricoleService.loadOrdreInfosById(commandeDTO.getIdOrdre());
			return "managerGestionOL.xhtml?faces-redirect=true&idBlocToShow=1b";
		} catch (Exception e) {
			Log.error("Erreur survenue lors de la lecture des details de l'ordre " + e.getMessage());
			e.printStackTrace();
		}

		showMessage(FacesMessage.SEVERITY_WARN, "Impossible de lire les détails de l'ordre");

		return "managerGestionOL.xhtml?faces-redirect=true&idBlocToShow=5b";
	}

	public String validerOrdreDeLivraison() {

		if (commandeDTO == null) {
			// showMessage(FacesMessage.SEVERITY_WARN, "Une erreur est survenue
			// pendant la validation de l'ordre");
			commonService.showMessage(FacesMessage.SEVERITY_WARN, "Merci de selectionner un ordre.");
			return "managerGestionOL.xhtml?idBlocToShow=1b";
		}

		if (programmAgricoleService.validerOrdreDeLivraisonById(commandeDTO.getIdOrdre())) {
			// showMessage(FacesMessage.SEVERITY_INFO, "L'ordre de livraison a
			// validé avec succès");
			commonService.showMessage(FacesMessage.SEVERITY_INFO, "L'ordre de livraison est validé avec succès");
			return "managerGestionOL.xhtml?idBlocToShow=1b";
		} else {
			// showMessage(FacesMessage.SEVERITY_WARN, "Une erreur est survenue
			// pendant la validation de l'ordre");
			commonService.showMessage(FacesMessage.SEVERITY_INFO,
					"Une erreur est survenue pendant la validation de l'ordre");
			return "managerGestionOL.xhtml?idBlocToShow=1b";
		}
	}

	public String filterCommande() {

		loadAllOrdresDeLivraion(); // olclient

		if (olIdProgramme > 0) // filtre Programme
		{
			List<CommandeDTO> filtredCommande = new ArrayList<CommandeDTO>();

			for (CommandeDTO c : listOLAvalider) {
				if (c.getIdProgramme() == olIdProgramme)
					filtredCommande.add(c);
			}
			listOLAvalider = filtredCommande;
		}

		if (olIdPointdeVente > 0) // filtre point de vente
		{
			List<CommandeDTO> filtredCommande2 = new ArrayList<CommandeDTO>();
			for (CommandeDTO c : listOLAvalider) {
				if (c.getIdPointdeVente() == olIdPointdeVente)
					filtredCommande2.add(c);
			}
			listOLAvalider = filtredCommande2;
		}

		if (olstatut > 0) // Status
		{
			List<CommandeDTO> filtredCommande3 = new ArrayList<CommandeDTO>();
			for (CommandeDTO c : listOLAvalider) {
				if (c.getStatus() == olstatut)
					filtredCommande3.add(c);
			}
			listOLAvalider = filtredCommande3;
		}

		return "managerGestionOL.xhtml?faces-redirect=true&idBlocToShow";
	}

	float montantTotalAllocation = 0;

	public List<AllocationDTO> loadAllAllocation() {
		montantTotalAllocation = 0l;
		Long idProg = 0L;
		try {
			List<AllocationDTO> listAlloc = tresorerieService.loadAllAllocation(idProg);

			for (AllocationDTO al : listAlloc)
				montantTotalAllocation += al.getMontantalloue();

			return listAlloc;
		} catch (EntityDBDAOException e) {
			Log.error("Erreur de recup des allocations" + e.getMessage());
		}
		return null;
	}

	public int callServiceRecuperationANBAllocation() {
		try {
			return tresorerieService.loadAllAllocation(0L).size();
		} catch (EntityDBDAOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public float loadMontantTotalAllocation() {
		float montant = 0;

		List<AllocationDTO> allocs = loadAllAllocation();

		for (AllocationDTO a : allocs)
			montant += a.getMontantalloue();

		return montant;
	}

	/*****
	 * REDIRECTION
	 * 
	 * @throws
	 *******************/

	public String initAllocation() {
		budgetAlloue = new AllocationDTO();
		return "managerServices.xhtml?idBlocToShow=2";
	}

	public String showDetailsSelectedBudget() {

		if (budgetAlloue != null && budgetAlloue.getIdPointDecollecteBeneficiaire() != null) {
			return "managerServices.xhtml?faces-redirect=true&idBlocToShow=3";

		} else {
			showMessage(FacesMessage.SEVERITY_WARN, "Merci de selectionner un budget ....");
			return "managerServices.xhtml?faces-redirect=true&idBlocToShow=2";
		}
	}

	/***** FIN COMMUN **************/

	public ProgrammeAgricolDTO getSelectedProgrammeAgricolDTO() {
		return selectedProgrammeAgricolDTO;
	}

	public void setSelectedProgrammeAgricolDTO(ProgrammeAgricolDTO selectedProgrammeAgricolDTO) {
		this.selectedProgrammeAgricolDTO = selectedProgrammeAgricolDTO;
	}

	/***** FONCTION UTILS POUR MAP *******/
	public String translateCodeRegionToMapCoord(String codeRegion) {

		if (codeRegion.equals("DKR"))
			return "SN-DK";
		else if (codeRegion.equals("DRB"))
			return "SN-FK";
		else if (codeRegion.equals("FTK"))
			return "SN-DB";
		else if (codeRegion.equals("KFN"))
			return "SN-KA";
		else if (codeRegion.equals("KLK"))
			return "SN-KL";
		else if (codeRegion.equals("KLD"))
			return "SN-KD";
		else if (codeRegion.equals("KEG"))
			return "SN-KE";
		else if (codeRegion.equals("LGA"))
			return "SN-LG";
		else if (codeRegion.equals("MTM"))
			return "SN-MT";
		else if (codeRegion.equals("STL"))
			return "SN-SL";
		else if (codeRegion.equals("SED"))
			return "SN-SE";
		else if (codeRegion.equals("TMB"))
			return "SN-TC";
		else if (codeRegion.equals("THS"))
			return "SN-TH";
		else if (codeRegion.equals("ZIG"))
			return "SN-ZG";

		return "";
	}

	public String createCampagneAgricole() {
		Log.info("création d'un engrais .............");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		try {
			if (programmAgricoleService.createCampagneAgricole(campagneDTO)) {
				// FacesMessage msg = new
				// FacesMessage(FacesMessage.SEVERITY_INFO, "", "La campagne est
				// crée avec succès");
				// FacesContext.getCurrentInstance().addMessage(null, msg);

				return "managerGestionDesCampagnes.xhtml?faces-redirect=true&idBlocToShow=3c";
			}
		} catch (ProgrammeException e) {

			e.printStackTrace();
		}
		return "managerGestionDesCampagnes.xhtml?faces-redirect=true&idBlocToShow=1";

	}

	public String createProgrammeAgricole() {
		Log.info("création d'un engrais .............");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (campagneDTO == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Une erreur est survenue  !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "managerGestionDesProgrammes.xhtml?faces-redirect=true";
		}

		if (campagneDTO.getDateFermeture().before(campagneDTO.getDateFermeture())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"La date de fermeture ne doit pas etre posterieure à la date d'ouverture  !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "managerGestionDesProgrammes.xhtml?faces-redirect=true";
		}

		if (campagneDTO.getLibelleCampagne().trim().equals("")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
					"Le nom du programme est obligatoire!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "managerGestionDesProgrammes.xhtml?faces-redirect=true";
		}

		if (campagneDTO.getIdCampagne() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "La campagne est obligatoire!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "managerGestionDesProgrammes.xhtml?faces-redirect=true";
		}

		if (programmAgricoleService.campapgneNameExist(campagneDTO.getLibelleCampagne(), campagneDTO.getIdCampagne())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
					"Le programme  " + campagneDTO.getLibelleCampagne() + " existe déjà pour la campagne "
							+ campagneDTO.getLibelleCampagne());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "managerGestionDesProgrammes.xhtml?faces-redirect=true";
		}

		try {
			try {
				if (programmAgricoleService.createCampagneProgrammeAgricole(campagneDTO)) {
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Programme créé avec succès !");
					FacesContext.getCurrentInstance().addMessage(null, msg);

					campagneDTO = new CampagneAgricoleDTO();
					return "managerGestionDesProgrammes.xhtml?faces-redirect=true&idBlocToShow=2c";
				}
			} catch (EntityDBDAOException e) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Une erreur est survenue !");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "managerGestionDesProgrammes.xhtml?faces-redirect=true&idBlocToShow=2c";
			}
		} catch (ProgrammeException e) {

			e.printStackTrace();
		}
		return "managerGestionDesProgrammes.xhtml?faces-redirect=true&idBlocToShow=2c";

	}

	public String removeCampagneAgricole() {
		Log.info("création d'un engrais .............");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (campagneDTO == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Aucune campagne selectionnée !");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			return "managerGestionDesCampagnes.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		try {
			if (programmAgricoleService.removeCampagneAgricole(campagneDTO.getIdCampagne())) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "La campagne supprimé avec succès");
				FacesContext.getCurrentInstance().addMessage(null, msg);

				return "managerGestionDesCampagnes.xhtml?faces-redirect=true&idBlocToShow=1";
			}
		} catch (ProgrammeException e) {

			e.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
				"Erreur lors de la suppression de la campagne");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		return "managerGestionDesCampagnes.xhtml?faces-redirect=true&idBlocToShow=1";

	}

	public String removeTypeIntrant() {
		Log.info("suppression type intrant d'un engrais .............");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (intrantType == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Aucun intrant selectionné !");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			return "managerGestionDesRerefentiels.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		try {
			if (programmAgricoleService.removeTypeIntrantFromCampagne(intrantType.getId())) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
						"Le type d'intrant " + intrantType.getLibelle() + " est supprimé avec succès");
				FacesContext.getCurrentInstance().addMessage(null, msg);

				return "managerGestionDesRerefentiels.xhtml?faces-redirect=true&idBlocToShow=1";
			}
		} catch (ProgrammeException e) {

			e.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
				"Erreur survenue lors de la suppression du type d'intrant : Verifiez s'il y'a pas de produit de ce type et supprimer le avant");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		return "managerGestionDesRerefentiels.xhtml?faces-redirect=true&idBlocToShow=1";

	}

	/**** Intrants ****/

	public List<IntrantTypeDTO> getAllTypeIntrantDTO() {
		return commonService.loadAllTypeIntrants();
	}

	public List<IntrantDTO> getAllIntrantDTO() {
		return null;// commonService.loadAllIntrants();
	}

	public List<IntrantTypeDTO> loadTypesIntrant() {
		return commonService.loadAllTypeIntrants();
	}

	public List<CoupleDTO> loadAllPointdeCollecte() {
		return commonService.loadPointDeCollecte();
	}

	private PointDeCollecteDTO selecttedPointDeCollecteDTO;

	public List<PointDeCollecteDTO> loadAllPointdeCollecteDTO() {
		return commonService.loadPointdeCollecteDTOs();
	}

	public List<CollaborateurDTO> loadAllAgentDeCollecte() {
		return commonService.loadAllCollaborateurDTOsPersonne();
	}

	public List<CoupleDTO> loadAllACampagne() throws EntityDBDAOException {
		return commonService.loadAllACampagne();
	}

	public float getMontantTotalAllocation() {
		return montantTotalAllocation;
	}

	public void setMontantTotalAllocation(float montantTotalAllocation) {
		this.montantTotalAllocation = montantTotalAllocation;
	}

	public String loadDetailsSelectedIntrant() {
		return "gestionDesIntrants.xhtml?faces-redirect=true&idBlocToShow=3";
	}

	public float getTotalSubvention() {
		return totalSubvention;
	}

	public void setTotalSubvention(float totalSubvention) {
		this.totalSubvention = totalSubvention;
	}

	public String createTypeIntrant() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		if (intrantType == null || intrantType.getLibelle().trim().equals("")
				|| intrantType.getUnitedeMesure().trim().equals("")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
					"Erreur erreur est survenue ,Merci de  verifier les données que vous avez saisi ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "managerGestionDesRerefentiels.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		Log.info("création d'un type intrant ............." + intrantType.getLibelle());
		if (programmAgricoleService.verifyExistingTypeIntrantByLibelle(intrantType.getLibelle())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"L'intrant de type " + intrantType.getLibelle() + " existe déjà");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "managerGestionDesRerefentiels.xhtml?faces-redirect=true&idBlocToShow=1b";
		}
		if (programmAgricoleService.createTypeIntrant(intrantType)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Le type d'intrant " + intrantType.getLibelle() + " est créé avec succès");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "managerGestionDesRerefentiels.xhtml?faces-redirect=true&idBlocToShow=1";
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Une erreur est survenur lors de 'edition du type d'intrant ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "managerGestionDesRerefentiels.xhtml?faces-redirect=true&idBlocToShow=1";
		}
	}

	private SubventionDTO selectedSubvention;

	public void reportListSubvention() throws IOException {
		// ManagerExportDataServices exporter = new ManagerExportDataServices();

		// exporter.downloadListSubvention(null, "", 0, 0);

		String FILE_NAME = "/home/awa/GED-PGCA/Versement/Liste-Des-Subventions.xlsx";

		File yourFile = new File(FILE_NAME);
		yourFile.createNewFile(); // if file already exists will do nothing

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Datatypes in Java");

		sheet.setColumnWidth(0, 300);
		Row title = sheet.createRow(1);
		Cell titleVal = title.createCell(5);

		// titleVal.getCellStyle().setBorderBottom(IndexedColors.BROWN.getIndex());
		titleVal.setCellValue("   Liste des subventions à encaisser  ");
		// titleVal.getRow().getCell(0).getRow().getRowStyle().setShrinkToFit(true);

		List<CoupleDTO> lp = new ArrayList<CoupleDTO>();

		List<String> titles = new ArrayList<String>();
		titles.add("Point de vente           ");
		titles.add("Bénéficiaire           ");
		// titles.add("Taux Subvention");
		titles.add("Montant Subvention          ");
		titles.add("Date opération           ");

		// create methode insert TItle
		Font font3 = workbook.createFont();
		font3.setFontHeightInPoints((short) 16);
		font3.setFontName("Times New Roman");
		font3.setColor(IndexedColors.LIGHT_GREEN.getIndex());
		CellStyle style3 = workbook.createCellStyle();
		style3.setFont(font3);

		// fontHeader.setUnderline(mef.toBinaryString(1));

		titleVal.setCellStyle(style3);

		Object[][] night = new Object[listSubvention.size() + 1][];

		int size = 0;
		night[size] = titles.toArray();
		size++;

		for (SubventionDTO c : listSubvention) {
			Object n[] = { c.getZone(), c.getNomClientBenefiaire(), c.getMontantSubvention(), c.getDateSubvention() };
			night[size++] = n;
		}

		int rowNum = 5;
		System.out.println("Creating excel");

		for (Object[] datatype : night) {
			Row row = sheet.createRow(rowNum++);

			int colNum = 5;
			for (Object field : datatype) {

				Cell cell = row.createCell(colNum++);
				// sheet.autoSizeColumn(colNum);

				// CellUtil.setCellStyleProperties(cell, properties);

				if (field instanceof String) {
					cell.setCellValue((String) field);
					// sheet.autoSizeColumn(colNum);

				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					// sheet.autoSizeColumn(colNum);

				} else if (field instanceof Date) {
					cell.setCellValue(field != null ? field.toString() : "");
					// sheet.autoSizeColumn(colNum);

				} else if (field instanceof Float) {
					cell.setCellValue(field != null ? (String) field.toString() : "");
					// sheet.autoSizeColumn(colNum);

				}

			}

			// sheet.autoSizeColumn(0);
			// sheet.autoSizeColumn(200,true);
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(FILE_NAME, false);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		File file = new File(FILE_NAME);

		FacesContext facesContext = FacesContext.getCurrentInstance();

		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

		response.reset();
		response.setHeader("Content-Type", "application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=\"my.xlsx\"");

		OutputStream responseOutputStream = response.getOutputStream();

		InputStream fileInputStream = new FileInputStream(file);

		byte[] bytesBuffer = new byte[2048];
		int bytesRead;
		while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) {
			responseOutputStream.write(bytesBuffer, 0, bytesRead);
		}

		responseOutputStream.flush();

		fileInputStream.close();
		responseOutputStream.close();

		facesContext.responseComplete();

	}

	public void downloadRapportCampagne() throws IOException {
		String FILE_NAME = "/Users/Amet/Documents/exportExport/MyFirstExcel.xlsx";

		File file = new File(FILE_NAME);

		FacesContext facesContext = FacesContext.getCurrentInstance();

		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

		response.reset();
		response.setHeader("Content-Type", "application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=\"my.xlsx\"");

		OutputStream responseOutputStream = response.getOutputStream();

		InputStream fileInputStream = new FileInputStream(file);

		byte[] bytesBuffer = new byte[2048];
		int bytesRead;
		while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) {
			responseOutputStream.write(bytesBuffer, 0, bytesRead);
		}

		responseOutputStream.flush();

		fileInputStream.close();
		responseOutputStream.close();

		facesContext.responseComplete();

	}

	public List<VentesDTO> callServiceRecuperationVentes() throws EntityDBDAOException {

		return commonService.loadAllVentesForManager();
	}

	public float getMontantTotalVente() throws EntityDBDAOException {

		List<VentesDTO> lv = commonService.loadAllVentesForManager();

		if (lv == null)
			return 0L;

		float montantTotal = 0;

		for (VentesDTO v : lv) {
			montantTotal += v.getMontantVente();
		}

		return montantTotal;
	}

	public float getMontantTotalDepotBanque() throws EntityDBDAOException {
		return tresorerieService.evaluerMontantTotalDepotBanqueByCampagne();
	}

	/******* Commun Element ********/

	public List<CoupleDTO> loadAllRegions() {
		return commonService.loadRegion();
	}

	public List<CoupleDTO> loadAllDepartement() {
		return commonService.loadAllDepartement();
	}

	public List<CoupleDTO> loadAllCommune() {
		return commonService.loadRegion();
	}

	public void refreshDepartementByRegionId() {
		System.out.println("--------------------------- ::: Refresh Liste des departement Id Region = "
				+ pointDeVente.getIdRegion());

		departements = commonService.loadAllDepartementOfRegion(pointDeVente.getIdRegion());
	}

	public void refreshCommunesByDepratementId() {
		System.out.println(
				"--------------------------- ::: Refresh Liste des communes Id Region = " + pointDeVente.getIdRegion());

		communes = commonService.loadAllCommunOfdepartement(pointDeVente.getIdDepartement());
	}

	/******* Stock de vente ********/

	public List<CoupleDTO> loadAllStock() {
		return commonService.loadAllStock();
	}

	/******* Fin Point de vente ********/

	/********* Produit (Engrais , Intrans, autres .....) **************/

	public List<EngraisDTO> getlisteEngraisDTO() {
		return commonService.loadAllEngraisDTO();
	}

	/*** Campagne Agricole : Programme **/
	public List<CampagneAgricoleDTO> getlisteProgrammeDTO() {
		return commonService.loadAllCampagneAgricole();
	}

	/*
	 * CREATION ENTITY -----------------------------------
	 */

	/******* Module de gestion des engrais ******/

	public String createEngrais() {
		Log.info("création d'un engrais .............");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (engraisDTO == null || engraisDTO.getLibelleProduit().trim().equals("")
				|| engraisDTO.getQuantite().longValue() < 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Création Engrais : ",
					"Une erreur est survenue lors de la création de lengrais");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			return "gestionDesEngrais.xhtml?faces-redirect=true&idBlocToShow=2";
		}

		if (commonService.createIntrant(engraisDTO)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"L'engrais " + engraisDTO.getLibelleProduit() + " créé avec succès ...");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesEngrais.xhtml?faces-redirect=true&idBlocToShow=1";

		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Création Engrais : ",
					"Une erreur est survenue lors de la création de lengrais");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesEngrais.xhtml?faces-redirect=true&idBlocToShow=1";
		}
	}

	/* INIT THE DELETE SERVICE */
	public String initUpdate() {
		System.err.println("------ INIT Update Entity Modele : Engrais");
		if (selectedEngraisDTO == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Modification Engrais :",
					"Veuillez selectionner un produit à modifier");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesEngrais.xhtml?faces-redirect=true&idBlocToShow=3";
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression Engrais :",
					"Une erreur est survenue lors de la suppression de l'engrais");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesEngrais.xhtml?faces-redirect=true&idBlocToShow=3";
		}
	}

	/* DELETE */
	public String deleteEngrais() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		System.err.println("------ Delete Entity Modele : Engrais");
		if (selectedEngraisDTO == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
					"Veuiller selectionner un engrais à supprimer");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesEngrais.xhtml?faces-redirect=truee&idBlocToShow=1";
		}

		if (commonService.deleteEntityModele(selectedEngraisDTO.getIdEngrais(), Intrant.class)) {

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Engrais " + selectedEngraisDTO.getLibelleProduit() + "supprimé avec avec succès");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesEngrais.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		return "gestionDesEngrais.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public String deleteEngraisBis() {
		System.out.println("------------  DELETE ");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		System.out.println("------ Delete Entity Point de vente : ");
		if (pointDeVente == null) {
			System.out.println("------ Entity à supprimer est null Delete Entity Point de vente : ");
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
					"Veuiller selectionner un point de vente  à supprimer");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesPointsdeVente.xhtml?faces-redirect=truee&idBlocToShow=1";
		}

		if (commonService.deletePointdeVente(pointDeVente)) {

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Point de vente " + selectedEngraisDTO.getLibelleProduit() + "supprimé avec  succès");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesPointsdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		return "gestionDesPointsdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	/** UPDATE ENGRAIS */
	public String updateEngrais() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		System.err.println("------ Update Entity Modele : Engrais");
		ProduitDTO dtoProduit = new ProduitDTO();
		dtoProduit.setIdProduit(selectedEngraisDTO.getIdEngrais());
		dtoProduit.setQuantite(selectedEngraisDTO.getQuantite());
		dtoProduit.setLibelle(selectedEngraisDTO.getLibelleProduit());
		dtoProduit.setIdtypeProduit(selectedEngraisDTO.getIdTypeEngrais());
		dtoProduit.setIdStockProduit(selectedEngraisDTO.getIdPointStock());

		if (commonService.updateProduit(dtoProduit)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Modification engrais effectuée avec succès");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			return "gestionDesEngrais.xhtml?faces-redirect=true&idBlocToShow=1";
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Modification Engrais :",
					"Une erreur est survenue lors de la modification de l'engrais");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesEngrais.xhtml?faces-redirect=true&idBlocToShow=1";
		}
	}

	/**** UPDATE PINT DE VENTE ***/

	public String initUpdatePointDeVente() {
		System.err.println("------ INIT Update Entity Modele : Point de Vente");
		if (pointDeVente == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Modification Point de Vente :",
					"Veuillez selectionner un point de vente à modifier");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesPointsdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
		} else {

			collaborateursDTOs = loadCollaborateurDTOs();
			regionDtos = loadAllRegions();
			departements = commonService.loadAllDepartementOfRegion(pointDeVente.getIdRegion());
			communes = commonService.loadAllCommunOfdepartement(pointDeVente.getIdDepartement());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Modification du point de vente " + pointDeVente.getLibelle());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesPointsdeVenteModification.xhtml";
		}
	}

	public String updatePointdeVente() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		System.err.println("------ Update Entity Modele : Point de Vente " + pointDeVente.getLibelle());

		if (commonService.updatePointdeVente(pointDeVente)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"La modification du point de vente " + pointDeVente.getLibelle() + "est effectuée avec succès");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			return "gestionDesPointsdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Une erreur est survenue lors de la modification du point de vente");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesPointsdeVenteModification.xhtml";
		}
	}

	public List<CollaborateurDTO> loadCollaborateurDTOs() {
		return commonService.loadAllCollaborateurDTOs();
	}

	public List<PointDeCollecteDTO> loadPointdeCollecteDTOs() {
		return commonService.loadPointdeCollecteDTOs();
	}

	/************
	 * POINT DE VENTE *****
	 * 
	 *****************************************************************/

	public List<PointDeVenteDTO> loadAllPointDeVentes() {
		return commonService.loadAllPointDeVente(false);

	}

	public String createPointdeVente() {
		System.out.println("création d'un Point de vente" + pointDeVente.getLibelle() + " .............");

		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (commonService.createPointdeVente(pointDeVente)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Le point de vente " + pointDeVente.getLibelle() + " est créé avec succès ...");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			System.out.println("création d'un Point de vente" + pointDeVente.getLibelle() + " OK 42");
			return "gestionDesPointsdeVente.xhtml?faces-redirect=truee&idBlocToShow=1";

		} else {
			System.out.println("création d'un Point de vente" + pointDeVente.getLibelle() + " KO 42");
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, " ",
					"Une erreur est survenue lors de la création du point de vente ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesPointsdeVente.xhtml?faces-redirect=truee&idBlocToShow=1";
		}
	}

	public SubventionDTO getSelectedSubvention() {
		return selectedSubvention;
	}

	public void setSelectedSubvention(SubventionDTO selectedSubvention) {
		this.selectedSubvention = selectedSubvention;
	}

	public String deletePointdeVente() {

		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		System.err.println("------ Delete Entity Point de vente : ");
		if (pointDeVente == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
					"Veuiller selectionner un point de vente  à supprimer");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesPointsdeVente.xhtml?faces-redirect=truee&idBlocToShow=1";
		}

		if (commonService.deleteEntityModele(pointDeVente.getIdPv(), PointDeVente.class)) {

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Point de vente " + selectedEngraisDTO.getLibelleProduit() + "supprimé avec  succès");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesPointsdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		return "gestionDesPointsdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	/*************** PV END ***************/

	public void loadDetailsVentesbyIdVente() {
		if (selectedVenteDTO != null)
			try {
				listProduitslieeSelectedVente = programmAgricoleService
						.getListProduitsVEnduFromIdVente(selectedVenteDTO.getVentes_id());
			} catch (ProgrammeException | EntityDBDAOException e) {

				Log.error("Erreur de recuperation produit lié  a une vente");
				e.printStackTrace();
			}
	}

	/****** Navigation ***/

	public String navigateToUrl(String url) {

		if (url.equals("2b")) // verifier si une campgné n'est pas déjà créé
		{
			try {
				List<CampagneAgricoleDTO> campagnesExistantes = programmAgricoleService
						.verifierExistanceCampagneParAnnee(campagneDTO.getDateOuverture(),
								campagneDTO.getDateFermeture());

				if (campagnesExistantes.size() > 0) {
					showMessage(FacesMessage.SEVERITY_WARN, "Une camapgne existe déjà sur la période "
							+ utils.getFormatedDateFromString(campagnesExistantes.get(0).getDateOuverture()) + " - "
							+ utils.getFormatedDateFromString(campagnesExistantes.get(0).getDateFermeture())
							+ " , Vous pouvez ajouter des programmes à cette campagne mais pas la dupliquer.");
					return "managerGestionDesCampagnes.xhtml?faces-redirect=true";
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return "managerGestionDesCampagnes.xhtml?faces-redirect=true&idBlocToShow=" + url;
	}

	public String getDetailsIntrant() {
		if (selectedIntrantDTO == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Veuillez selectionner un intrant");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "managerGestionDesStock.xhtml?faces-redirect=true";
		} else {

			try {
				selectedIntrantDTO.setTotauxGlobalVentesToutlesPV(
						tresorerieService.loadMontantTotalDesVentesByIdProduit(selectedIntrantDTO.getIdProduit()));
			} catch (EntityDBDAOException e) {
				Log.error("Impossible de recupereer les ventes effectues sur le produit ...");
				e.printStackTrace();
				return "managerGestionDesStock.xhtml?faces-redirect=true";

			}
			return "managerGestionDesStock.xhtml?faces-redirect=true&idBlocToShow=0";
		}
	}

	public List<IntrantDTO> refreslistListIntrantByType() {
		if (budgetAlloue != null)
			try {
				return programmAgricoleService
						.loadReferentielIntrantByType(budgetAlloue.getIdCategorieProduitACollecter());
			} catch (EntityDBDAOException e) {
				Log.error("Erreur recuperation type intrant");
				e.printStackTrace();
			}
		return null;
	}

	public String redirectSpecifiqueProduitInforations() {
		try {

			listeCreditDTOofSelectePVTotal = 0;
			Long idProg = 0L;

			listProduitslieeSelectedVente = commonService
					.loadStockProduitByIdPointdeVenteMagasinier(selectedPointDeVenteId);

			listecreditDTOofSelectePV = tresorerieService.loadAllCreditsByIdPointDeVente(selectedPointDeVenteId,
					idProg);

			for (CreditDTO c : listecreditDTOofSelectePV)
				listeCreditDTOofSelectePVTotal += c.getMontantRestantApayer();

			// listeVenteDTOofSelectePV =
			// tresorerieService.recupererVersementBanqueByIdPointDeVente(selectedPointDeVenteId);

			/*
			 * to continue private List<VentesDTO> listeVenteDTOofSelectePV;
			 * private float listeVenteDTOofSelectePVTotal; private
			 * List<CreditDTO> listecreditDTOofSelectePV; private float
			 * listecreditDTOofSelectePVTotal; private List<VersementBanqueDTO>
			 * listeversementBanqueofSelectePV; private float
			 * listeversementBanqueofSelectePVTotal;
			 */

		} catch (EntityDBDAOException e) {
			Log.error("Erreur de recuperation des infos du produit " + e.getMessage());
			e.printStackTrace();
		}
		// listProduitslieeSelectedVente =
		// programmAgricoleService.getListProduitsVEnduFromIdVente(selectedVenteDTO.getVentes_id());
		return "ProduitinfosByPointDeVente.xhtml?faces-redirect=true&idBlocToShow=2";
	}

	public String callredirectSpecifiquePointdeVente() {

		if (selectedPv == null) {
			commonService.showMessage(FacesMessage.SEVERITY_ERROR, "Veuillez selectionner un point de vente");
			return "managergestionDesPointsdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		selectedPointDeVenteId = selectedPv.getIdPv();
		return redirectSpecifiquePointdeVente();
	}

	public String redirectSpecifiquePointdeVente() {

		try {
			listeCreditDTOofSelectePVTotal = 0;
			listeversementBanqueofSelectePVTotal = 0;
			listeVentefSelectePVTotal = 0l;
			Long idProg = 0L;

			PointDeVenteDTO pv = commonService.loadPointDeVenteById(selectedPointDeVenteId);

			if (pv != null)
				selectedPointDeVenteId = pv.getIdstockReference();

			listProduitslieeSelectedVente = commonService
					.loadStockProduitByIdPointdeVenteMagasinier(selectedPointDeVenteId);

			listecreditDTOofSelectePV = tresorerieService.loadAllCreditsByIdPointDeVente(selectedPointDeVenteId,
					idProg);
			for (CreditDTO c : listecreditDTOofSelectePV)
				listeCreditDTOofSelectePVTotal += c.getMontantRestantApayer();

			listeversementBanqueofSelectePV = tresorerieService
					.loadAllVersementBanqueByIdPointDeVente(selectedPointDeVenteId, idProg);
			for (VersementBanqueDTO v : listeversementBanqueofSelectePV)
				listeversementBanqueofSelectePVTotal += v.getMontantVersment();

			listeVenteofSelectePV = tresorerieService.loadAllVentesByIdPointDeVente(selectedPointDeVenteId, idProg);
			for (VentesDTO v : listeVenteofSelectePV)
				listeVentefSelectePVTotal += v.getMontantEncaisse();

		} catch (EntityDBDAOException e) {
			Log.error("Erreur de recuperation de la liste  des produits" + e.getMessage());
			e.printStackTrace();
		}
		// listProduitslieeSelectedVente =
		// programmAgricoleService.getListProduitsVEnduFromIdVente(selectedVenteDTO.getVentes_id());

		return "managerDetailsSelectedRegionPointDeVente.xhtml?faces-redirect=true&idBlocToShow=2";
	}

	MiseEnplaceDTOCommune planMiseEnPlace = new MiseEnplaceDTOCommune();
	List<MiseEnplaceDTOPointDeVente> listPVOfCommune; // = new ArrayList<>();
	List<MiseEnplaceDTOPointDeVente> FiltredlistPVOfCommune = new ArrayList<>();

	int nbPointeDeVenteDelaCommune = 0;
	int totalReliquatCommune = 0;
	int totalCumulCommune = 0;
	int totalMiseEnPlaceAffectuer = 0;

//	public void loadAllMiseEnPlace() {
//		{
//			nbPointeDeVenteDelaCommune = 0;
//			totalReliquatCommune = 0;
//			totalCumulCommune = 0;
//			totalMiseEnPlaceAffectuer = 0;
//			listPVOfCommune.clear();
//
//			try {
//				List<MiseEnplaceDTOPointDeVente> listPv = programmAgricoleService.loadAllMiseEnPlaceOfCampagne();
//
//				for (MiseEnplaceDTOPointDeVente pv : listPv) {
//
//					listPVOfCommune.add(pv);
//					totalReliquatCommune += pv.getReliquatIntrantAMettreEnplace();
//					totalCumulCommune += pv.getCumulIntrantAMettreEnplace();
//					totalMiseEnPlaceAffectuer += pv.getQuotaIntrantAMettreEnplace();
//				}
//			} catch (EntityDBDAOException e) {
//				// TODO Auto-generated catch block
//				Log.error("Erreur surveneur lors de la recuperation des mises en place");
//			}
//
//		}
//	}

//	public int callCulerTauxMiseEnPlace() {
//
//		loadAllMiseEnPlace();
//
//		return listPVOfCommune != null ? listPVOfCommune.size() : 0;
//
//	}

	/***** Recuperation des mise en place d'une commune ***/
	String nomCommune;
	MiseEnplaceDTOPointDeVente selectedMiseEnplaceDTOPointDeVente;

	public void loadAllMiseEnPlaceByIdCommune() {
		planMiseEnPlace = new MiseEnplaceDTOCommune();
		listPVOfCommune = new ArrayList<>();
		nbPointeDeVenteDelaCommune = 0;
		totalReliquatCommune = 0;
		totalCumulCommune = 0;
		totalMiseEnPlaceAffectuer = 0;

		String[] tmp = nomCommune.split("-");

		if (tmp.length > 0) {
			try {
				Long idPV = Long.parseLong(tmp[0]);

				List<MiseEnplaceDTOPointDeVente> listPv = programmAgricoleService.loadAllMiseEnPlaceByIdCommune(idPV);

				planMiseEnPlace.setNbPointDeVente(
						listPv != null ? listPv.size() + "Point (s) trouvé (s)" : "Aucun Point de vente ");

				for (MiseEnplaceDTOPointDeVente pv : listPv) {

					listPVOfCommune.add(pv);
					totalReliquatCommune += pv.getReliquatIntrantAMettreEnplace();
					totalCumulCommune += pv.getCumulIntrantAMettreEnplace();
					totalMiseEnPlaceAffectuer += pv.getQuotaIntrantAMettreEnplace();
				}
			} catch (EntityDBDAOException e) {
				// TODO Auto-generated catch block
				Log.error("Erreur surveneur lors de la recuperation des mises en place");
			}

		}
	}

	public void showMessage(Severity severity, String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		FacesMessage msg = new FacesMessage(severity, "", message);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public int loadAllMEPtermines() {
		try {
			return programmAgricoleService.loadAllMiseEnPlaceByTermine();
		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	int nbMiseEnplaceEnCours;
	public List<MiseEnplaceDTOPointDeVente>  loadAllMiseEnplace() { 
		try {
			if(listPVOfCommune ==  null)
				listPVOfCommune = programmAgricoleService.getAllMiseEnPlaceEncours();
//			
//			nbMiseEnplaceEnCours = listPVOfCommune != null ? listPVOfCommune.size() : 0;
//			if(listPVOfCommune != null && listPVOfCommune.size() > 1);
//				listPVOfCommune.get(0).setActiveCss("active");
			
		} catch (EntityDBDAOException e) {
			Log.error("Une erreur est survenue lors de lecture des MEP");
		}
		return listPVOfCommune;
	}
	


	/*public int loadAllMiseEnPlaceEncours() {
		try {
			return programmAgricoleService.loadAllMiseEnPlaceEncours();
		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public String getAllMiseEnPlaceEncours() {
		try {
			listPVOfCommune = programmAgricoleService.getAllMiseEnPlaceEncours();
			
			if(listPVOfCommune != null && listPVOfCommune.size() > 1);
				listPVOfCommune.get(0).setActiveCss("active");
			
		} catch (EntityDBDAOException e) {
			Log.error("Une erreur est survenue lors de lecture des MEP");
		}
		return "";
	}

	public List<MiseEnplaceDTOPointDeVente> loadAllMiseEnplace() {
		try {
			if (listPVOfCommune != null)
			{

				if(listPVOfCommune != null && listPVOfCommune.size() > 1);
					listPVOfCommune.get(0).setActiveCss("active");
			
				return listPVOfCommune;
			}
			else
				listPVOfCommune = programmAgricoleService.getAllMiseEnPlaceEncours();
		} catch (EntityDBDAOException e) {
			Log.error("Une erreur est survenue lors de lecture des MEP");
		}
		
		//style class Jquery firl elem
		if(listPVOfCommune != null && listPVOfCommune.size() > 1);
			listPVOfCommune.get(0).setActiveCss("active");
		return listPVOfCommune;
	}
	*/

	List<LitigesDTO> listLitigesDTO;
	LitigesDTO selectedLitigesDTO;

	public List<LitigesDTO> redirectToListLitiges() {
		return commonService.loadAllLitiges();
	}

	public List<CoupleDTO> refreshProfilDTOs() {
		return commonService.loadProfil();
	}

	CreditDTO selectedCreditDTO;
	List<AvanceCreditDTO> listeAvance;

	public String showDeatilsCredit() {
		if (selectedCreditDTO == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner un crédit ");
			return "magasisnierGestionCredits.xhtml?faces-redirect=true";
		}
		return "ManagerGestionCredits.xhtml?faces-redirect=true&idBlocToShow=5b";
	}

	List<ChequeDTO> listeAllCheques;
	float montantAencaisser;

	public MiseEnplaceDTOCommune getPlanMiseEnPlace() {
		return planMiseEnPlace;
	}

	public String removeSpace(String field) {
		return field!= null ?   field.replaceAll(" ", "").toLowerCase() : "";
	}
	
	public void setPlanMiseEnPlace(MiseEnplaceDTOCommune planMiseEnPlace) {
		this.planMiseEnPlace = planMiseEnPlace;
	}

	public int getNbPointeDeVenteDelaCommune() {
		return nbPointeDeVenteDelaCommune;
	}

	public void setNbPointeDeVenteDelaCommune(int nbPointeDeVenteDelaCommune) {
		this.nbPointeDeVenteDelaCommune = nbPointeDeVenteDelaCommune;
	}

	public MiseEnplaceDTOPointDeVente getSelectedMiseEnplaceDTOPointDeVente() {
		return selectedMiseEnplaceDTOPointDeVente;
	}

	public void setSelectedMiseEnplaceDTOPointDeVente(MiseEnplaceDTOPointDeVente selectedMiseEnplaceDTOPointDeVente) {
		this.selectedMiseEnplaceDTOPointDeVente = selectedMiseEnplaceDTOPointDeVente;
	}

	public List<MiseEnplaceDTOPointDeVente> getListPVOfCommune() {
		return listPVOfCommune;
	}

	public void setListPVOfCommune(List<MiseEnplaceDTOPointDeVente> listPVOfCommune) {
		this.listPVOfCommune = listPVOfCommune;
	}

	public int getTotalReliquatCommune() {
		return totalReliquatCommune;
	}

	public void setTotalReliquatCommune(int totalReliquatCommune) {
		this.totalReliquatCommune = totalReliquatCommune;
	}

	public int getTotalCumulCommune() {
		return totalCumulCommune;
	}

	public void setTotalCumulCommune(int totalCumulCommune) {
		this.totalCumulCommune = totalCumulCommune;
	}

	public int getTotalMiseEnPlaceAffectuer() {
		return totalMiseEnPlaceAffectuer;
	}

	public void setTotalMiseEnPlaceAffectuer(int totalMiseEnPlaceAffectuer) {
		this.totalMiseEnPlaceAffectuer = totalMiseEnPlaceAffectuer;
	}

	public String getNomCommune() {
		return nomCommune;
	}

	public void setNomCommune(String nomCommune) {
		this.nomCommune = nomCommune;
	}

	public List<ChequeDTO> callServiceRecuperationALlCheques() {
		montantAencaisser = 0;
		List<ChequeDTO> list = tresorerieService.recupererAllCheques();

		for (ChequeDTO c : list) {
			if (c.getStatut() == 0)
				montantAencaisser += c.getMontantCheque();
		}

		listeAllCheques = list;
		return list;
	}

	public String redircetandInitChequeList() {

		callServiceRecuperationALlCheques();

		return "managerGestionCheques.xhtml?faces-redirect=true&idBlocToShow=5b";

	}

	public String callEnregistrerCredit() {

		if (selectedCreditDTO == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner un crédit ");
			return "ManagerGestionCredits.xhtml?faces-redirect=true";
		}

		listeAvance = getlistAvanceByDetailsSelectedCredit(selectedCreditDTO.getCredit_id());
		return "ManagerGestionCredits.xhtml?faces-redirect=true&idBlocToShow=5c";

	}

	public List<LitigesDTO> getListLitigesDTO() {
		return listLitigesDTO;
	}

	public LitigesDTO getSelectedLitigesDTO() {
		return selectedLitigesDTO;
	}

	public void setListLitigesDTO(List<LitigesDTO> listLitigesDTO) {
		this.listLitigesDTO = listLitigesDTO;
	}

	public void setSelectedLitigesDTO(LitigesDTO selectedLitigesDTO) {
		this.selectedLitigesDTO = selectedLitigesDTO;
	}

	AvanceCreditDTO avanceDTO = new AvanceCreditDTO();

	public String callServiceAvanceSurCredit() {

		Map<Long, String> save = tresorerieService.enregistrerAvance(avanceDTO, selectedCreditDTO.getCredit_id());

		Long returnType = null;
		String returnessage = null;

		for (Map.Entry<Long, String> entry : save.entrySet()) {
			returnType = entry.getKey();
			returnessage = entry.getValue();
		}

		if (returnType == 1L) {
			showMessage(FacesMessage.SEVERITY_INFO, returnessage);

			return "ManagerGestionCredits.xhtml?faces-redirect=true";

		} else {

			showMessage(FacesMessage.SEVERITY_WARN, returnessage);

			return "ManagerGestionCredits.xhtml?faces-redirect=true&idBlocToShow=5b";
		}
	}

	public List<AvanceCreditDTO> getlistAvanceByDetailsSelectedCredit(Long idCredit) {

		try {
			return tresorerieService.getListAvancebyIdCredit(selectedCreditDTO.getCredit_id()).getListAvanceCredits();

		} catch (EntityDBDAOException e) {

			return null;
		}

	}

	public void refreshRegion(String id) {
		regionDtos = commonService.loadRegionbyPays(id);
	}

	public List<CoupleDTO> getPaysDtos() {
		return paysDtos;
	}

	public void setPaysDtos(List<CoupleDTO> paysDtos) {
		this.paysDtos = paysDtos;
	}

	public List<CoupleDTO> getRegionDtos() {
		return regionDtos;
	}

	public void setRegionDtos(List<CoupleDTO> regionDtos) {
		this.regionDtos = regionDtos;
	}

	public SessionManagedBean getSessionMB() {
		return sessionMB;
	}

	public void setSessionMB(SessionManagedBean sessionMB) {
		this.sessionMB = sessionMB;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public List<CollaborateurDTO> getCollaborateursDTOs() {
		return collaborateursDTOs;
	}

	public void setCollaborateursDTOs(List<CollaborateurDTO> collaborateursDTOs) {
		this.collaborateursDTOs = collaborateursDTOs;
	}

	public List<PointDeCollecteDTO> getPointdeCollecteDTOs() {
		return pointdeCollecteDTOs;
	}

	public CoupleDTO getStockDTO() {
		return stockDTO;
	}

	public void setStockDTO(CoupleDTO stockDTO) {
		this.stockDTO = stockDTO;
	}

	public IntrantDTO getSelectedIntrantDTO() {
		return selectedIntrantDTO;
	}

	public void setSelectedIntrantDTO(IntrantDTO selectedIntrantDTO) {
		this.selectedIntrantDTO = selectedIntrantDTO;
	}

	public void setPointdeCollecteDTOs(List<PointDeCollecteDTO> pointdeCollecteDTOs) {
		this.pointdeCollecteDTOs = pointdeCollecteDTOs;
	}

	public PointDeCollecteDTO getSelectedPointdeCollecte() {
		return selectedPointdeCollecte;
	}

	public void setSelectedPointdeCollecte(PointDeCollecteDTO selectedPointdeCollecte) {
		this.selectedPointdeCollecte = selectedPointdeCollecte;
	}

	public IntrantDTO getEngraisDTO() {
		return engraisDTO;
	}

	public List<ChequeDTO> getListeAllCheques() {
		return listeAllCheques;
	}

	public void setListeAllCheques(List<ChequeDTO> listeAllCheques) {
		this.listeAllCheques = listeAllCheques;
	}

	public float getMontantAencaisser() {
		return montantAencaisser;
	}

	public void setMontantAencaisser(float montantAencaisser) {
		this.montantAencaisser = montantAencaisser;
	}

	public void setEngraisDTO(IntrantDTO engraisDTO) {
		this.engraisDTO = engraisDTO;
	}

	public List<MiseEnplaceDTOPointDeVente> getFiltredlistPVOfCommune() {
		return FiltredlistPVOfCommune;
	}

	public void setFiltredlistPVOfCommune(List<MiseEnplaceDTOPointDeVente> filtredlistPVOfCommune) {
		FiltredlistPVOfCommune = filtredlistPVOfCommune;
	}

	public EngraisDTO getSelectedEngraisDTO() {
		return selectedEngraisDTO;
	}

	public void setSelectedEngraisDTO(EngraisDTO selectedEngraisDTO) {
		this.selectedEngraisDTO = selectedEngraisDTO;
	}

	public List<CoupleDTO> getCommunes() {
		return communes;
	}

	public void setCommunes(List<CoupleDTO> communes) {
		this.communes = communes;
	}

	public List<CoupleDTO> getDepartements() {
		return departements;
	}

	public void setDepartements(List<CoupleDTO> departements) {
		this.departements = departements;
	}

	public PointDeVenteDTO getPointDeVente() {
		return pointDeVente;
	}

	public void setPointDeVente(PointDeVenteDTO pointDeVente) {
		this.pointDeVente = pointDeVente;
	}

	public CampagneAgricoleDTO getSelectedCampagneAgricoleDTO() {
		return selectedCampagneAgricoleDTO;
	}

	public void setSelectedCampagneAgricoleDTO(CampagneAgricoleDTO selectedCampagneAgricoleDTO) {
		this.selectedCampagneAgricoleDTO = selectedCampagneAgricoleDTO;
	}

	public IProgrammeAgricol getProgrammAgricoleService() {
		return programmAgricoleService;
	}

	public void setProgrammAgricoleService(IProgrammeAgricol programmAgricoleService) {
		this.programmAgricoleService = programmAgricoleService;
	}

	public VentesDTO getSelectedVenteDTO() {
		return selectedVenteDTO;
	}

	public void setSelectedVenteDTO(VentesDTO selectedVenteDTO) {
		this.selectedVenteDTO = selectedVenteDTO;
	}

	public List<ProduitDTO> getListProduitslieeSelectedVente() {
		return listProduitslieeSelectedVente;
	}

	public void setListProduitslieeSelectedVente(List<ProduitDTO> listProduitslieeSelectedVente) {
		this.listProduitslieeSelectedVente = listProduitslieeSelectedVente;
	}

	public CampagneAgricoleDTO getCampagneDTO() {
		return campagneDTO;
	}

	public void setCampagneDTO(CampagneAgricoleDTO campagneDTO) {
		this.campagneDTO = campagneDTO;
	}

	public float getMontantTotalDeposeEnBanque() {
		return montantTotalDeposeEnBanque;
	}

	public void setMontantTotalDeposeEnBanque(float montantTotalDeposeEnBanque) {
		this.montantTotalDeposeEnBanque = montantTotalDeposeEnBanque;
	}

	public ITresorerieService getTresorerieService() {
		return tresorerieService;
	}

	public void setTresorerieService(ITresorerieService tresorerieService) {
		this.tresorerieService = tresorerieService;
	}

	public String getSelectedAreaOnMap() {
		return selectedAreaOnMap;
	}

	public void setSelectedAreaOnMap(String selectedAreaOnMap) {
		this.selectedAreaOnMap = selectedAreaOnMap;
	}

	public String getMapAllPointDeVenteInJSON() {
		return mapAllPointDeVenteInJSON;
	}

	public void setMapAllPointDeVenteInJSON(String mapAllPointDeVenteInJSON) {
		this.mapAllPointDeVenteInJSON = mapAllPointDeVenteInJSON;
	}

	public List<SubventionDTO> getListSubvention() {
		return listSubvention;
	}

	public void setListSubvention(List<SubventionDTO> listSubvention) {
		this.listSubvention = listSubvention;
	}

	public CreditDTO getSelectedCreditDTO() {
		return selectedCreditDTO;
	}

	public void setSelectedCreditDTO(CreditDTO selectedCreditDTO) {
		this.selectedCreditDTO = selectedCreditDTO;
	}

	public List<AvanceCreditDTO> getListeAvance() {
		return listeAvance;
	}

	public void setListeAvance(List<AvanceCreditDTO> listeAvance) {
		this.listeAvance = listeAvance;
	}

	public AvanceCreditDTO getAvanceDTO() {
		return avanceDTO;
	}

	public void setAvanceDTO(AvanceCreditDTO avanceDTO) {
		this.avanceDTO = avanceDTO;
	}

	public List<PointDeVenteDTO> getAllpointdeVenteOfSelectedRegion() {
		return allpointdeVenteOfSelectedRegion;
	}

	public void setAllpointdeVenteOfSelectedRegion(List<PointDeVenteDTO> allpointdeVenteOfSelectedRegion) {
		this.allpointdeVenteOfSelectedRegion = allpointdeVenteOfSelectedRegion;
	}

	public String getMapAllProduitofPointDeVenteInJSON() {
		return mapAllProduitofPointDeVenteInJSON;
	}

	public void setMapAllProduitofPointDeVenteInJSON(String mapAllProduitofPointDeVenteInJSON) {
		this.mapAllProduitofPointDeVenteInJSON = mapAllProduitofPointDeVenteInJSON;
	}

	public String getMapRegion() {
		return mapRegion;
	}

	public void setMapRegion(String mapRegion) {
		this.mapRegion = mapRegion;
	}

	public String getSelectedPointDeVente() {
		return selectedPointDeVente;
	}

	public void setSelectedPointDeVente(String selectedPointDeVente) {
		this.selectedPointDeVente = selectedPointDeVente;
	}

	public Long getSelectedPointDeVenteId() {
		return selectedPointDeVenteId;
	}

	public void setSelectedPointDeVenteId(Long selectedPointDeVenteId) {
		this.selectedPointDeVenteId = selectedPointDeVenteId;
	}

	public Long getSelectedProduitId() {
		return selectedProduitId;
	}

	public void setSelectedProduitId(Long selectedProduitId) {
		this.selectedProduitId = selectedProduitId;
	}

	public List<VentesDTO> getListeVenteDTOofSelectePV() {
		return listeVenteDTOofSelectePV;
	}

	public void setListeVenteDTOofSelectePV(List<VentesDTO> listeVenteDTOofSelectePV) {
		this.listeVenteDTOofSelectePV = listeVenteDTOofSelectePV;
	}

	public List<CreditDTO> getListecreditDTOofSelectePV() {
		return listecreditDTOofSelectePV;
	}

	public void setListecreditDTOofSelectePV(List<CreditDTO> listecreditDTOofSelectePV) {
		this.listecreditDTOofSelectePV = listecreditDTOofSelectePV;
	}

	public float getListecreditDTOofSelectePVTotal() {
		return listecreditDTOofSelectePVTotal;
	}

	public void setListecreditDTOofSelectePVTotal(float listecreditDTOofSelectePVTotal) {
		this.listecreditDTOofSelectePVTotal = listecreditDTOofSelectePVTotal;
	}

	public List<VersementBanqueDTO> getListeversementBanqueofSelectePV() {
		return listeversementBanqueofSelectePV;
	}

	public void setListeversementBanqueofSelectePV(List<VersementBanqueDTO> listeversementBanqueofSelectePV) {
		this.listeversementBanqueofSelectePV = listeversementBanqueofSelectePV;
	}

	public float getListeversementBanqueofSelectePVTotal() {
		return listeversementBanqueofSelectePVTotal;
	}

	public void setListeversementBanqueofSelectePVTotal(float listeversementBanqueofSelectePVTotal) {
		this.listeversementBanqueofSelectePVTotal = listeversementBanqueofSelectePVTotal;
	}

	public List<VentesDTO> getListeVenteofSelectePV() {
		return listeVenteofSelectePV;
	}

	public void setListeVenteofSelectePV(List<VentesDTO> listeVenteofSelectePV) {
		this.listeVenteofSelectePV = listeVenteofSelectePV;
	}

	public float getListeVentefSelectePVTotal() {
		return listeVentefSelectePVTotal;
	}

	public void setListeVentefSelectePVTotal(float listeVentefSelectePVTotal) {
		this.listeVentefSelectePVTotal = listeVentefSelectePVTotal;
	}

	public float getListeCreditDTOofSelectePVTotal() {
		return listeCreditDTOofSelectePVTotal;
	}

	public void setListeCreditDTOofSelectePVTotal(float listeCreditDTOofSelectePVTotal) {
		this.listeCreditDTOofSelectePVTotal = listeCreditDTOofSelectePVTotal;
	}

	public String getSelectedPointDeVenteCommune() {
		return selectedPointDeVenteCommune;
	}

	public void setSelectedPointDeVenteCommune(String selectedPointDeVenteCommune) {
		this.selectedPointDeVenteCommune = selectedPointDeVenteCommune;
	}

	public String getSelectedPointDeVenteDepartement() {
		return selectedPointDeVenteDepartement;
	}

	public void setSelectedPointDeVenteDepartement(String selectedPointDeVenteDepartement) {
		this.selectedPointDeVenteDepartement = selectedPointDeVenteDepartement;
	}

	public UtilString getUtils() {
		return utils;
	}

	public void setUtils(UtilString utils) {
		this.utils = utils;
	}

	public List<CommissionDTO> getListedesCommission() {
		return listedesCommission;
	}

	public void setListedesCommission(List<CommissionDTO> listedesCommission) {
		this.listedesCommission = listedesCommission;
	}

	public CommissionDTO getSelectedCommisionDTO() {
		return selectedCommisionDTO;
	}

	public void setSelectedCommisionDTO(CommissionDTO selectedCommisionDTO) {
		this.selectedCommisionDTO = selectedCommisionDTO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public AllocationDTO getBudgetAlloue() {
		return budgetAlloue;
	}

	public void setBudgetAlloue(AllocationDTO budgetAlloue) {
		this.budgetAlloue = budgetAlloue;
	}

	public ChequeDTO getSelectedChequeDTO() {
		return selectedChequeDTO;
	}

	public void setSelectedChequeDTO(ChequeDTO selectedChequeDTO) {
		this.selectedChequeDTO = selectedChequeDTO;
	}

	public String getListOfProductFromDBInJSON() {
		return listOfProductFromDBInJSON;
	}

	public void setListOfProductFromDBInJSON(String listOfProductFromDBInJSON) {
		this.listOfProductFromDBInJSON = listOfProductFromDBInJSON;
	}

	public IntrantTypeDTO getIntrantType() {
		return intrantType;
	}

	public void setIntrantType(IntrantTypeDTO intrantType) {
		this.intrantType = intrantType;
	}

	public CommandeDTO getCommandeDTO() {
		return commandeDTO;
	}

	public void setCommandeDTO(CommandeDTO commandeDTO) {
		this.commandeDTO = commandeDTO;
	}

	public List<CommandeDTO> getListBLAvalider() {
		return listBLAvalider;
	}

	public void setListBLAvalider(List<CommandeDTO> listBLAvalider) {
		this.listBLAvalider = listBLAvalider;
	}

	public List<ProduitDTO> getListProduitCommande() {
		return listProduitCommande;
	}

	public void setListProduitCommande(List<ProduitDTO> listProduitCommande) {
		this.listProduitCommande = listProduitCommande;
	}

	public List<CommandeDTO> getListOLAvalider() {
		return listOLAvalider;
	}

	public void setListOLAvalider(List<CommandeDTO> listOLAvalider) {
		this.listOLAvalider = listOLAvalider;
	}

	public IManagerService getManagerService() {
		return managerService;
	}

	public void setManagerService(IManagerService managerService) {
		this.managerService = managerService;
	}

	public Long getOlIdPointdeVente() {
		return olIdPointdeVente;
	}

	public void setOlIdPointdeVente(Long olIdPointdeVente) {
		this.olIdPointdeVente = olIdPointdeVente;
	}

	public int getOlstatut() {
		return olstatut;
	}

	public void setOlstatut(int olstatut) {
		this.olstatut = olstatut;
	}

	public String getOlclient() {
		return olclient;
	}

	public void setOlclient(String olclient) {
		this.olclient = olclient;
	}

	public Long getOlIdProgramme() {
		return olIdProgramme;
	}

	public void setOlIdProgramme(Long olIdProgramme) {
		this.olIdProgramme = olIdProgramme;
	}

	public PointDeCollecteDTO getSelectedPointDeCollecte() {
		return selectedPointDeCollecte;
	}

	public void setSelectedPointDeCollecte(PointDeCollecteDTO selectedPointDeCollecte) {
		this.selectedPointDeCollecte = selectedPointDeCollecte;
	}

	public PointDeCollecteDTO getSelecttedPointDeCollecteDTO() {
		return selecttedPointDeCollecteDTO;
	}

	public void setSelecttedPointDeCollecteDTO(PointDeCollecteDTO selecttedPointDeCollecteDTO) {
		this.selecttedPointDeCollecteDTO = selecttedPointDeCollecteDTO;
	}

	public PointDeVenteDTO getSelectedPv() {
		return selectedPv;
	}

	public int getNbMiseEnplaceEnCours() {
		return nbMiseEnplaceEnCours;
	}

	public void setNbMiseEnplaceEnCours(int nbMiseEnplaceEnCours) {
		this.nbMiseEnplaceEnCours = nbMiseEnplaceEnCours;
	}

	public void setSelectedPv(PointDeVenteDTO selectedPv) {
		this.selectedPv = selectedPv;
	}

}
