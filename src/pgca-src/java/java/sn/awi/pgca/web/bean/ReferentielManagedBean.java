package sn.awi.pgca.web.bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jfree.util.Log;
import org.primefaces.event.CaptureEvent;

import com.itextpdf.text.DocumentException;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.exception.ProgrammeException;
import sn.awi.pgca.business.service.ICommonService;
import sn.awi.pgca.business.service.IProgrammeAgricol;
import sn.awi.pgca.business.service.ITresorerieService;
import sn.awi.pgca.dataModel.PointDeVente;
import sn.awi.pgca.dataModel.Intrant;
import sn.awi.pgca.utils.UtilString;
import sn.awi.pgca.web.dto.BonDeLivraisonDTO;
import sn.awi.pgca.web.dto.CampagneAgricoleDTO;
import sn.awi.pgca.web.dto.CollaborateurDTO;
import sn.awi.pgca.web.dto.CoupleDTO;
import sn.awi.pgca.web.dto.CreditDTO;
import sn.awi.pgca.web.dto.EngraisDTO;
import sn.awi.pgca.web.dto.FournisseurDTO;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.IntrantTypeDTO;
import sn.awi.pgca.web.dto.PersonneDTO;
import sn.awi.pgca.web.dto.PointDeCollecteDTO;
import sn.awi.pgca.web.dto.PointDeVenteDTO;
import sn.awi.pgca.web.dto.ProduitDTO;
import sn.awi.pgca.web.dto.ProgrammeAgricolDTO;
import sn.awi.pgca.web.dto.VentesDTO;
import sn.awi.pgca.web.dto.VersementBanqueDTO;
import sn.awi.pgca.web.export.ReportPDFGerenics;

@ManagedBean(name = "referentielMB")
@SessionScoped
public class ReferentielManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8787460764909265230L;

	@ManagedProperty(value = "#{commonService}")
	private ICommonService commonService;

	@ManagedProperty(value = "#{programmeAgricolService}")
	private IProgrammeAgricol programmAgricoleService;
	
	
	@ManagedProperty(value = "#{tresorerieService}")
	private ITresorerieService tresoreieService;

	List<String> listBanque;

	private SessionManagedBean sessionMB;

	private List<CoupleDTO> paysDtos;

	private List<CoupleDTO> regionDtos;

	private List<CoupleDTO> departements;

	private List<CoupleDTO> listeAllProgramme;

	private List<CoupleDTO> listeProgrammeOuvert;

	private CampagneAgricoleDTO selectedCampagneAgricoleDTO;

	private List<CoupleDTO> communes;

	private List<CollaborateurDTO> collaborateursDTOs;

	private List<PointDeCollecteDTO> pointdeCollecteDTOs;

	private PointDeCollecteDTO selectedPointdeCollecte;

	private PointDeVenteDTO pointDeVente;

	private String pvTest;

	private String libelleIntrantAmettreEnplace;

	private EngraisDTO selectedEngraisDTO;

	private IntrantDTO selectedIntrantDTO  = new IntrantDTO();

	private BonDeLivraisonDTO bondeLivraisonDTO;

	private List<ProduitDTO> listProduitsDTOtoCreate;

	private ProduitDTO produitDTOtoCreate;

	private UtilString utils = new UtilString();

	Long selecteidProduitoDelete;
	
	String dateMiseEnPlaceString;
	

	private Class<?> genericsSelectElement; /*
											 * Onselect Genrecs pour tout les
											 * Ptab
											 */

	private IntrantDTO engraisDTO;

	private CoupleDTO selectedTransporteurDTO = new CoupleDTO("", "");
	private CoupleDTO selectedChauffeur = new CoupleDTO("", "");
	private CoupleDTO selectedCamion = new CoupleDTO("", "");

	private CoupleDTO stockDTO = new CoupleDTO("", "");

	private int modeBl; // mode bl ajax affichage

	private String subventionProduit;
	public String getLibelleIntrantAmettreEnplace() {
		return libelleIntrantAmettreEnplace;
	}

	public void setLibelleIntrantAmettreEnplace(String libelleIntrantAmettreEnplace) {
		this.libelleIntrantAmettreEnplace = libelleIntrantAmettreEnplace;
	}

	private boolean showBlocTauxSubvention;

	private List<CoupleDTO> listProductofSelectedTypeProduit;
	private List<IntrantDTO> listReferentielfiltred;
	
	private List<IntrantDTO> listIntrantsOfStock; 

	@PostConstruct
	public void init() {

		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		sessionMB = new SessionManagedBean();
		engraisDTO = new IntrantDTO();
		selectedIntrantDTO = new IntrantDTO();
		paysDtos = commonService.loadPays();
		pointDeVente = new PointDeVenteDTO();
		bondeLivraisonDTO = new BonDeLivraisonDTO();
		produitDTOtoCreate = new ProduitDTO();
		listProduitsDTOtoCreate = new ArrayList<ProduitDTO>();

	}

	/*******************************
	 * IPROGRAMMME AGRICOL
	 **********************************************/

	public List<CoupleDTO> loadAllProgrammeAgricole() {
		try {
			return commonService.loadAllProgramme();
		} catch (EntityDBDAOException e) {
			Log.error("Une erreur est survenue lors sde la recuperation des programme Agricol " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	private ProgrammeAgricolDTO selectedProgrammeAgricolDTO;
	
	public List<ProgrammeAgricolDTO> loadAllProgrammeAgricoleDTO() {
		try {
			return commonService.loadAllProgrammeDTO();
		} catch (EntityDBDAOException e) {
			Log.error("Une erreur est survenue lors sde la recuperation des programme Agricol " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	
	

	public List<IntrantTypeDTO> loadReferentielTypeIntrant() {
		try {
			return commonService.loadReferentielTypeIntrant();
		} catch (EntityDBDAOException e) {
			Log.error("Une erreur est survenue lors sde la recuperation des referentiels dintrant" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public List<IntrantDTO> loadReferentielIntrants() {
		try {
			return programmAgricoleService.loadReferentielIntrants();
		} catch (ProgrammeException e) {
			Log.error("Une erreur est survenenue lors de ");
			e.printStackTrace();
		}
		return null;
	}

	public List<PersonneDTO> loadAllCollaborateurFromPointdeCollecte(Long idPointDeCollecte) {
		try {
			return commonService.loadUtilisateurFromPointDeCollecte(idPointDeCollecte);
		} catch (EntityDBDAOException e) {
			Log.error("Une erreur est survenue lors sde la recuperation des collaborateurs du point de collecte"
					+ e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public List<CoupleDTO> loadAllProgrammeAgricole(int statut) {
		try {
			return commonService.loadAllProgramme(statut);
		} catch (EntityDBDAOException e) {
			Log.error("Une erreur est survenue lors sde la recuperation des programme Agricol " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	

	
	/***********************************************
	 * ************ INTRANT : APPRO FROM FOURNISSEUR  (Appro d'un intrant dans un stock)
	 **********************************************/
	public String createIntrantForCampagne() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (selectedIntrantDTO == null || selectedIntrantDTO.getQuantite().longValue() < 0) {
			commonService.showMessage(FacesMessage.SEVERITY_ERROR,
					"Une erreur est survenue lors de la création de l'intrant , Merci de vérifier si tous les champs sont valides");
			return "gestionDesProduitsCreation.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		Long connectedUserPersonne;
		try {
			connectedUserPersonne = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserPersonneid");
			Long stockId = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserStockid");
			selectedIntrantDTO.setIdStock(stockId);
			
			selectedIntrantDTO.setIdAuteurTarication(connectedUserPersonne);
			if (programmAgricoleService.addIntrantCampagne(selectedIntrantDTO)) {
				
				if(selectedIntrantDTO.isUpdateOrNot()) // création en mode update
				{
					 // création from scratch
					commonService.showMessage(FacesMessage.SEVERITY_INFO, "L'intrant "
							+ selectedIntrantDTO.getLibelleProduit() + " existe déjà auprès du fournisseur "+ selectedIntrantDTO.getInfosDTOLibelleFournisseur() +" ,  la quantité est mise à jour");
					listIntrantsOfStock  = getlistIntrantDTO();
					return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=1";
				}
				else
				{
					 // création from scratch
					commonService.showMessage(FacesMessage.SEVERITY_INFO, "L'intrant "
							+ selectedIntrantDTO.getLibelleProduit() + " disponible auprès du fournisseur " + selectedIntrantDTO.getInfosDTOLibelleFournisseur() + " est ajouté à la campagne avec succès");
					listIntrantsOfStock  = getlistIntrantDTO();
					return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=1";
				} 
			}
			
		} catch (Exception e) {
			commonService.showMessage(FacesMessage.SEVERITY_ERROR,
					"Une erreur est survenue lors de creation des intrants");
			Log.error("Une erreur est survenue lors de creation des intrants");
		}
		return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	/***********************************************
	 * ************ INTRANT : MAJ
	 **********************************************/

	public String updateIntrantForCampagne() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);


		try {
			if (programmAgricoleService.updateIntranCampagne(selectedIntrantDTO)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "La modification de l'intrant "
						+ selectedIntrantDTO.getLibelleProduit() + "est effectuée avec succès");
				FacesContext.getCurrentInstance().addMessage(null, msg);

				return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=1";
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
						"Une erreur est survenue lors de la modification de l'intrant");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=2";
			}
		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
				"Une erreur est survenue lors de la modification de l'intrant");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=2";
	}
	
	
	
	/****** TARIFICATION DUN PRODUIT ******/
	
	Float isSubventionned = 0f;
	Float prixSubventionne  = 0f;
	Float prixNonSubventionne  = 0f;
	Float montantArecouvrir  = 0f;
	Float prixUnitaire  = 0f;
	Float tauxSubvention  = 0f; 
	
	public void initInfosTarification()
	{
		if(selectedIntrantDTO  == null || !selectedIntrantDTO.getSubventionne().equals("1"))
			{
			montantArecouvrir  = 0.0f;
			tauxSubvention = 0.0f;
			prixSubventionne = prixNonSubventionne ;
			return;
			}

		
		prixSubventionne = prixNonSubventionne  - (prixNonSubventionne * tauxSubvention) / 100;
		montantArecouvrir  = prixNonSubventionne  - prixSubventionne;
	}
	
	
	public String tarifierProduit() {
	
		boolean subventionne = false;
		if(selectedIntrantDTO ==  null)
		{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Aucun intrant a tarifier");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "#";	
		}

		if (prixNonSubventionne == 0)
		{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,  "Le prix Unitaire est invalide" , "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "#";	
		}
		
		if (selectedIntrantDTO.getSubventionne().equals("1"))
		{
			subventionne  = true;
			
			if(tauxSubvention ==  0)
			{
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Le taux de subvention est invalide (Min 1%)");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "#";		
			}
			if(tauxSubvention >  100)
			{
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Le taux de subvention est invalide (Max 100%)");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "#";		
			}	
		}
		
		
		try {
			if (tresoreieService.tarifierProduit(subventionne, tauxSubvention, prixNonSubventionne, selectedIntrantDTO.getIdProduit() )) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Tarification effectuée avec succès !");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "#";
			} 
		} catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Une erreur est survenenue lors de la tarification !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			Log.error("Une erreur est surveneue de la tarification de l'intrant" + e.getMessage());
		}
		return "#";
		
	}
	
	/*********  FIN TARIFICATION *****************/
	
	

	/***** Mise a jour intrant d'un stock ***/
	public String updateIntranOfStock() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);


		try {
			if (programmAgricoleService.updateIntranOfStock(selectedIntrantDTO)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "La modification de l'intrant "
						+ selectedIntrantDTO.getLibelleProduit() + " est effectuée avec succès");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				listIntrantsOfStock = getlistIntrantDTO();	
				return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=1";
			} 
		} catch (Exception e) {
			
			Log.error("Une erreur est surveneue lors de la mise a jour de l'intran");
		}
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
				"Une erreur est survenue lors de la modification de l'intrant");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		return "gestionDesProduitsModification.xhtml?faces-redirect=true&idBlocToShow=1";
	}
	
	public List<IntrantTypeDTO> getAllTypeIntrantDTO() {
		return commonService.loadAllTypeIntrants();
	}

	public List<IntrantTypeDTO> loadAllIntrants() {
		return commonService.loadAllTypeIntrants();
	}

	// ********* Produit (Engrais , Intrans, autres .....)

	public List<IntrantDTO> loadListeCollecteByIdCollect() {

		Long connectedUseridStock = 0L;
		try {
			connectedUseridStock = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserStockid");
			return programmAgricoleService.loadAllIntrantDTOByIdStock(connectedUseridStock);

		} catch (Exception e) {
			Log.info("Erreur de recuperation des intrant du point de collecte " + connectedUseridStock);
			e.printStackTrace();
		}
		return null;
	}

	String StockParFounisseurJSONFomrat;
	
	public String  initCreationIntrant() {
	
		StockParFounisseurJSONFomrat  =  "";
		listIntrantsOfStock = getlistIntrantDTO();	
		
		for(IntrantDTO i : listIntrantsOfStock)
		{
			StockParFounisseurJSONFomrat +=  i.getLibelleFournisseur() + ":"  + i.getQuantite();
		}
			
		return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=1";
	
	}
	
	
	public String  redirectStockMagasin() {
		
		StockParFounisseurJSONFomrat  =  "";
		listIntrantsOfStock = getlistIntrantDTO();	
		
		for(IntrantDTO i : listIntrantsOfStock)
		{
			StockParFounisseurJSONFomrat +=  i.getLibelleFournisseur() + ":"  + i.getQuantite();
		}
			
		return "agentsaisieGestionStockMagasin.xhtml?faces-redirect=true&idBlocToShow=1";
	
	}
	
	/****  Filtre ****/
	
	Long filtredCat ; 
	Long filtredInt;
	Long filterIdProgramme; 
	int filterStatus;
	
	public String getDateMiseEnPlaceString() {
		return dateMiseEnPlaceString;
	}

	public void setDateMiseEnPlaceString(String dateMiseEnPlaceString) {
		this.dateMiseEnPlaceString = dateMiseEnPlaceString;
	}

	public String filterIntrant() {
		listIntrantsOfStock = getlistIntrantDTO();	

		if (filterIdProgramme > 0) // filtre Programme
		{
			List<IntrantDTO> filtredCommande = new ArrayList<IntrantDTO>();

			for (IntrantDTO c : listIntrantsOfStock) {
				if (c.getIdCampagneProgramme() == filterIdProgramme)
					filtredCommande.add(c);
			}
			listIntrantsOfStock = filtredCommande;
		}

		if (filterStatus >= 0) // filtre subevention
		{
			List<IntrantDTO> filtredCommande2 = new ArrayList<IntrantDTO>();
			for (IntrantDTO c : listIntrantsOfStock) { 
				if (filterStatus ==  1)  //subventionné
				{
					if ( c.getTauxSubvention() > 0)
						filtredCommande2.add(c);
				}
				else if(filterStatus ==  0)  //Non subventionne
				{
					if (c.getTauxSubvention() == 0)
						filtredCommande2.add(c);
				}
			}
			listIntrantsOfStock = filtredCommande2;
		}

		if (filtredCat > 0) // Filtre categorie
		{
			List<IntrantDTO> filtredCommande3 = new ArrayList<IntrantDTO>();
			for (IntrantDTO c : listIntrantsOfStock) {
				if (c.getIdCategorieIntrant() == filtredCat)
					filtredCommande3.add(c);
			}
			listIntrantsOfStock = filtredCommande3;
		}

		return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=1";
	}
	
	List<IntrantDTO> listIntrant;
	List<IntrantDTO> listIntrantFiltred;
	public List<IntrantDTO> getlistIntrantDTO() {
		Long connectedUseridStock = 0L;
		try {
			
			if(listIntrant != null)
				return listIntrant;
			
			connectedUseridStock = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserStockid");
			listIntrant  = programmAgricoleService.loadAllIntrantDTOByIdStock(connectedUseridStock);
			return   listIntrant;
		} catch (Exception e) {
			Log.info("Erreur de recuperation des intrant du point de collecte " + connectedUseridStock);
			e.printStackTrace();
		}
		return null;
	}
	
	
	// recupération des inrant stocké chez les fourniseurs
	List<IntrantDTO> listIntrantFromFournisseur;
	List<IntrantDTO> listIntrantFiltredFromFournisseur;
	public List<IntrantDTO> getlistIntrantDTOFromFournisseur() {
		try {
			
			if(listIntrantFromFournisseur != null)
				return listIntrantFromFournisseur;
			
			listIntrantFromFournisseur  = programmAgricoleService.getlistIntrantDTOFromFournisseur();
			return   listIntrant;
		} catch (Exception e) {
			Log.info("Erreur de recuperation du stock des fournisseurs " );
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<IntrantDTO> getlistIntrantDTOFromFournisseurfORmANAGER() {
		try {
			
			if(listIntrantFromFournisseur == null || listIntrantFromFournisseur.size() == 0)
				listIntrantFromFournisseur  = programmAgricoleService.getlistIntrantDTOFromFournisseurFournisseur();
			
		} catch (Exception e) {
			Log.info("Erreur de recuperation du stock des fournisseurs " );
			e.printStackTrace();
		}
		return   listIntrantFromFournisseur;

	}
	
	
	
	

	public List<IntrantDTO> getlistIntrantDTOForManager() {
		return programmAgricoleService.loadAllProduitOfAllCampagne();
	}

	public List<IntrantTypeDTO> loadTypesIntrant() {
		return commonService.loadAllTypeIntrants();
	}

	public List<CoupleDTO> loadTypesIntrant2() {
		return commonService.loadPointDeCollecte();
	}

	public String loadDetailsSelectedIntrant() {
		return "gestionDesIntrants.xhtml?faces-redirect=true&idBlocToShow=3";
	}

	public String redirectToBlEdition() {

		listProduitsDTOtoCreate = new ArrayList<ProduitDTO>();
		pointDeVente = new PointDeVenteDTO();

		return "gestionDesBL.xhtml?idBlocToShow=1";
	}

	public ProgrammeAgricolDTO getSelectedProgrammeAgricolDTO() {
		return selectedProgrammeAgricolDTO;
	}

	public void setSelectedProgrammeAgricolDTO(ProgrammeAgricolDTO selectedProgrammeAgricolDTO) {
		this.selectedProgrammeAgricolDTO = selectedProgrammeAgricolDTO;
	}

	public String redirectToOL() {

		listProduitsDTOtoCreate = new ArrayList<ProduitDTO>();
		pointDeVente = new PointDeVenteDTO();

		return "agentSaisieReferentielCommandes.xhtml?idBlocToShow=1";
	}
	
	
	
	
	/******* Commun Element ********/

	public List<CoupleDTO> loadtransporteur() {
		return commonService.loadAllTransporteur();
	}

	public List<CoupleDTO> loadCampagneOuvert() {
		try {
			return commonService.loadAllProgramme(1);
		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return communes;
	}

	public ITresorerieService getTresoreieService() {
		return tresoreieService;
	}

	public void setTresoreieService(ITresorerieService tresoreieService) {
		this.tresoreieService = tresoreieService;
	}

	public List<CoupleDTO> loadAllCamions() {
		return commonService.loadAllCamion();
	}

	public Float getIsSubventionned() {
		return isSubventionned;
	}

	public void setIsSubventionned(Float isSubventionned) {
		this.isSubventionned = isSubventionned;
	}

	public Float getPrixSubventionne() {
		return prixSubventionne;
	}

	public void setPrixSubventionne(Float prixSubventionne) {
		this.prixSubventionne = prixSubventionne;
	}

	public String getStockParFounisseurJSONFomrat() {
		return StockParFounisseurJSONFomrat;
	}

	public void setStockParFounisseurJSONFomrat(String stockParFounisseurJSONFomrat) {
		StockParFounisseurJSONFomrat = stockParFounisseurJSONFomrat;
	}

	public Float getPrixNonSubventionne() {
		return prixNonSubventionne;
	}

	public void setPrixNonSubventionne(Float prixNonSubventionne) {
		this.prixNonSubventionne = prixNonSubventionne;
	}

	public Float getMontantArecouvrir() {
		return montantArecouvrir;
	}

	public void setMontantArecouvrir(Float montantArecouvrir) {
		this.montantArecouvrir = montantArecouvrir;
	}

	public Float getPrixUnitaire() {
		return prixUnitaire;
	}

	public void setPrixUnitaire(Float prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	public Float getTauxSubvention() {
		return tauxSubvention;
	}

	public void setTauxSubvention(Float tauxSubvention) {
		this.tauxSubvention = tauxSubvention;
	}

	public List<CoupleDTO> loadAllPointCollecte() {
		return commonService.loadAllCamion();
	}

	public List<IntrantDTO> getListIntrantsOfStock() {
		return listIntrantsOfStock;
	}

	public void setListIntrantsOfStock(List<IntrantDTO> listIntrantsOfStock) {
		this.listIntrantsOfStock = listIntrantsOfStock;
	}

	/* MODULE  TRANSPORTEUR - CAMION - CHAUFFEUR AVEC REFRESH AJAX */
	Long idTransporteur;
	Long idChauffeur;
	Long idCamion;
	String transporteur;
	String chauffeur;
	String camion;
	
	
	String libelleTransporteur;
	String libelleChauffeur;
	String libelleidCamion;
	List<CoupleDTO> listTransporteur ;
	List<CoupleDTO> listChauffeur ;
	List<CoupleDTO> listCamion ;
	
	public List<CoupleDTO> loadAllTransporteur() {
		return commonService.loadAllTransporteur();
	}
	
	public void updateListCamionByTransporteur() {
		listCamion = commonService.loadAllCamionByIdTransporteur(idTransporteur);
		listChauffeur = commonService.loadAllChauffeurByIdTransporteur(idTransporteur);
	}
	
	public void updateListChauffeurByidCamion() {
		listChauffeur = commonService.loadAllChauffeurByIdTransporteur(idTransporteur);
	}
	

//	public void updateListChauffeurByidTransporteur() {
//		listChauffeur =	 commonService.loadAllChauffeurByIdTransporteur(idTransporteur);
//	}

	
/******************/
	
	public List<PersonneDTO> loadAllChauffeur() {
		try {
			return commonService.loadAllChauffeur();

		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			Log.error("Erreur de recuperation des chauffeurs");
			e.printStackTrace();
			return null;
		}
	}
	
	public List<CoupleDTO> loadCamionsBychauffeur() {
		return commonService.loadCamionsBychauffeur(selectedChauffeur.getClef());
	}

	public List<CoupleDTO> loadAllRegions() {
		return commonService.loadRegion();
	}

	
	
	public List<CoupleDTO> loadAllDepartement() {
		return commonService.loadAllDepartement();
	}

	public List<CoupleDTO> loadAllCommune() {
		return commonService.loadRegion();
	}

	public void refreshRegionByPointdeVente() {
		

		departements = commonService.loadAllDepartementOfRegion(pointDeVente.getIdRegion());
	}

	public void refreshDepartementByRegionId() {
		
		if(pointDeVente != null && pointDeVente.getIdRegion() != null &&  !pointDeVente.getIdRegion().equals("-1"))
			departements = commonService.loadAllDepartementOfRegion(pointDeVente.getIdRegion());

	}

	public void refreshCommunesByDepratementId() {
		communes = commonService.loadAllCommunOfdepartement(pointDeVente.getIdDepartement());
	}

	List<CoupleDTO> pointdeVentesOfSelectedDepartement ;
	public void loadAllPointdeVentesOfDepartemnt() {
		pointdeVentesOfSelectedDepartement = commonService.loadAllPointdeVentesOfDepartemnt(pointDeVente.getIdDepartement());
	}
	
	public void refresPointdeVenteDetails() {
		

		PointDeVenteDTO dtopv = commonService.loadPointDeVenteById(pointDeVente.getIdPv());

		if (dtopv != null) {
			pointDeVente.setLibelle(dtopv.getLibelle());
			pointDeVente.setRegion(dtopv.getRegion());
			pointDeVente.setDepartement(dtopv.getDepartement());
			pointDeVente.setCommune(dtopv.getCommune());
			pointDeVente.setGerant(dtopv.getGerant());

			bondeLivraisonDTO.setLibelle(dtopv.getLibelle());
			bondeLivraisonDTO.setAdresseDestinataire(pointDeVente.getAdresse());
		}
	}

	public String createIntrant() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (selectedIntrantDTO == null) {
			commonService.showMessage(FacesMessage.SEVERITY_WARN,
					"Erreur erreur est survenue ,Merci de verifier les données saisies ");
			return "managerGestionDesRerefentielsCreationIntrant.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (selectedIntrantDTO.getIdtypeProduit() == 0) {
			commonService.showMessage(FacesMessage.SEVERITY_WARN, "Le type d'intrant est obligatoire");
			return "managerGestionDesRerefentielsCreationIntrant.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (selectedIntrantDTO.getLibelleProduit().trim().equals("")) {
			commonService.showMessage(FacesMessage.SEVERITY_WARN, "Le libelle de l'intrant est obligatoire");
			return "managerGestionDesRerefentielsCreationIntrant.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (selectedIntrantDTO.getUniteDeMesure().trim().equals("")) {
			commonService.showMessage(FacesMessage.SEVERITY_WARN, "L'unite de mesure  est obligatoire");
			return "managerGestionDesRerefentielsCreationIntrant.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		Log.info("création d'un  intrant ............." + selectedIntrantDTO.getLibelleProduit());
		if (programmAgricoleService.verifyExistingIntrantByLibelle(selectedIntrantDTO.getLibelleProduit())) {
			commonService.showMessage(FacesMessage.SEVERITY_WARN,
					"L'intrant  " + selectedIntrantDTO.getLibelleProduit() + " existe déjà");
			return "managerGestionDesRerefentielsCreationIntrant.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		if (programmAgricoleService.createIntrant(selectedIntrantDTO)) {

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"L'intrant  " + selectedIntrantDTO.getLibelleProduit() + " est créé avec succès");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			return "managerGestionDesRerefentiels.xhtml?faces-redirect=true&idBlocToShow=4";
		} else {
			commonService.showMessage(FacesMessage.SEVERITY_ERROR,
					"Une erreur est survenur lors de L'edition de l'intrant ");
			return "managerGestionDesRerefentielsCreationIntrant.xhtml?faces-redirect=true&idBlocToShow=1";
		}
	}

	// Lectures de toutes les ventes

	public void refresshListProduitFromTypeProduitSelected() {
		try {
			listReferentielfiltred = programmAgricoleService
					.loadReferentielIntrantByType(selectedIntrantDTO.getIdtypeProduit());
		} catch (EntityDBDAOException e) {
			Log.error("Error de recupération des produits ");
		}
	}

	public void refreshBlocTauxSubvention() {

		if (subventionProduit.equals("1"))
			showBlocTauxSubvention = true;
		else if (subventionProduit.equals("0") || subventionProduit.equals("2"))
			showBlocTauxSubvention = false;
	}

	public void switchModeBL() {
		departements = commonService.loadAllDepartementOfRegion(pointDeVente.getIdRegion());
	}

	/******* Stock de vente ********/

	public List<CoupleDTO> loadAllStock() {
		return commonService.loadAllStock();
	}

	/******* Fin Point de vente ********/

	/*** Campagne Agricole : Programme **/
	public List<CampagneAgricoleDTO> loadAllProgrammeAgricol() {
		try {
			return programmAgricoleService.getlistCampagneAgricoleWithoutDetails();
		} catch (ProgrammeException | EntityDBDAOException e) {
			Log.error("Erreur de recuperation des campagnes agricols");
			e.printStackTrace();
		}
		return null;
	}

	public String getTransporteur() {
		return transporteur;
	}

	public void setTransporteur(String transporteur) {
		this.transporteur = transporteur;
	}

	public String getChauffeur() {
		return chauffeur;
	}

	public void setChauffeur(String chauffeur) {
		this.chauffeur = chauffeur;
	}

	public String getCamion() {
		return camion;
	}

	public void setCamion(String camion) {
		this.camion = camion;
	}

	public List<ProgrammeAgricolDTO> getlistOfProgramme() {
		try {
			return programmAgricoleService.getlistOfProgramme();
		} catch (ProgrammeException | EntityDBDAOException e) {
			Log.error("Erreur de recuperation des campagnes agricols");
			e.printStackTrace();
		}
		return null;
	}

	public List<CoupleDTO> getlistProgramme() {
		try {
			return programmAgricoleService.getlistProgramme();
		} catch (ProgrammeException | EntityDBDAOException e) {
			Log.error("Erreur de recuperation des campagnes agricols");
			e.printStackTrace();
		}
		return null;
	}
	
	public List<FournisseurDTO> getlistFournisseur() {
		return commonService.loadAllFornisseurs();
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

	public Long getIdTransporteur() {
		return idTransporteur;
	}

	public void setIdTransporteur(Long idTransporteur) {
		this.idTransporteur = idTransporteur;
	}

	public Long getIdChauffeur() {
		return idChauffeur;
	}

	public void setIdChauffeur(Long idChauffeur) {
		this.idChauffeur = idChauffeur;
	}

	public Long getIdCamion() {
		return idCamion;
	}

	public void setIdCamion(Long idCamion) {
		this.idCamion = idCamion;
	}

	public String getLibelleTransporteur() {
		return libelleTransporteur;
	}

	public void setLibelleTransporteur(String libelleTransporteur) {
		this.libelleTransporteur = libelleTransporteur;
	}

	public String getLibelleChauffeur() {
		return libelleChauffeur;
	}

	public void setLibelleChauffeur(String libelleChauffeur) {
		this.libelleChauffeur = libelleChauffeur;
	}

	public String getLibelleidCamion() {
		return libelleidCamion;
	}

	public void setLibelleidCamion(String libelleidCamion) {
		this.libelleidCamion = libelleidCamion;
	}

	public List<CoupleDTO> getListTransporteur() {
		return listTransporteur;
	}

	public void setListTransporteur(List<CoupleDTO> listTransporteur) {
		this.listTransporteur = listTransporteur;
	}

	public List<CoupleDTO> getListChauffeur() {
		return listChauffeur;
	}

	public void setListChauffeur(List<CoupleDTO> listChauffeur) {
		this.listChauffeur = listChauffeur;
	}

	public List<CoupleDTO> getListCamion() {
		return listCamion;
	}

	public void setListCamion(List<CoupleDTO> listCamion) {
		this.listCamion = listCamion;
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

	/**** UPDATE POINT DE VENTE ***/

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

	public String initUpdateIntrant() {
		if (selectedIntrantDTO == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, " :",
					"Veuillez selectionner un intrant à modifier");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		return "gestionDesProduitsModification.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public String getDetailsIntrant() {
		if (selectedIntrantDTO == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Veuillez selectionner un intrant");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=4";
		} else {
			if(selectedIntrantDTO.getTauxSubvention() > 0)
				subventionProduit = "1";
			return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=4";
		}
	}
	
	
	public String initTarification() {
		if (selectedIntrantDTO == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Veuillez selectionner un intrant à tarifier ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=4";
		} else {
			prixNonSubventionne  =  selectedIntrantDTO.getPrixNonSubventionne();
			prixSubventionne  =  selectedIntrantDTO.getPrixProducteur();
			tauxSubvention =  selectedIntrantDTO.getTauxSubvention();
			
			if(selectedIntrantDTO.getTauxSubvention() > 0)
				selectedIntrantDTO.setSubventionne("1"); 
			else
				selectedIntrantDTO.setSubventionne("2"); 
			
			return "gestionDesProduitsTarification.xhtml?faces-redirect=true&idBlocToShow=1";
		}
	}
	
	public String initCreation() {
			selectedIntrantDTO = new IntrantDTO();
			return "gestionDesProduitsEdition.xhtml?faces-redirect=true";
			
	}
	
	
	private String filename;
    
    private String getRandomImageName() {
        int i = (int) (Math.random() * 10000000);
         
        return String.valueOf(i);
    }
 
    public String getFilename() {
        return filename;
    }
     
    
	public void oncapture(CaptureEvent captureEvent) {
		
//		File pathVersement = new File(pathFileVersement + repertoireDedidiePointDeVente);
//		String fileExtention = FilenameUtils.getExtension(versementDTO.getDocumentJustificatif().getFileName());
//		InputStream input = null;
//		
//		
        filename = getRandomImageName();
        byte[] data = captureEvent.getData();
 
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String newFileName = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" +
                                    File.separator + "images" + File.separator + "photocam" + File.separator + filename + ".jpeg";
         
        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        }
        catch(IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }
    }
	

	public String deleteIntrant() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		System.err.println("------ Delete Entity Modele : INtrant DTO");
		if (selectedIntrantDTO == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
					"Veuiller selectionner un intrant à supprimer");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesProduits.xhtml?faces-redirect=truee&idBlocToShow=1";
		}

		if (commonService.deleteEntityModele(selectedIntrantDTO.getIdProduit(), Intrant.class)) {

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Intrant " + selectedIntrantDTO.getLibelleProduit() + "supprimé avec avec succès");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=2";
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

	// erreur a conception a corriger : ce service renvoir une liste de user au
	// lieu de personne
	public List<CollaborateurDTO> loadAllCollaborateurDTOsPersonne() {
		return commonService.loadAllCollaborateurDTOsPersonne();
	}

	public List<PointDeCollecteDTO> loadPointdeCollecteDTOs() {
		return commonService.loadPointdeCollecteDTOs();
	}

	/************
	 * POINT DE VENTE *****
	 * 
	 *****************************************************************/
	
	List<PointDeVenteDTO>  filtredPointdeVente;
	public List<PointDeVenteDTO> loadAllPointDeVentes(String mode) {
		if (mode.equals('1')) {
			System.out.println("------- Recuperation Point de Vente en mode Lazy " + mode);
			return commonService.loadAllPointDeVente(false);
		} else {
			System.out.println("------- Recuperation Point de Vente en mode refreh " + mode);
			return commonService.loadAllPointDeVente(true);
		}
	}
	
	
	public List<FournisseurDTO> loadAllFornisseurs() {
			return commonService.loadAllFornisseurs();
	}

	public List<PointDeCollecteDTO> loadAllPointDeCollecte() {
		return commonService.loadPointdeCollecteDTOs();
		/*
		 * if (mode.equals('1')) { System.out.
		 * println("------- Recuperation Point de Vente en mode Lazy " + mode);
		 * return commonService.loadAllPointDeVente(false); } else { System.out.
		 * println("------- Recuperation Point de Vente en mode refreh " +
		 * mode); return commonService.loadAllPointDeVente(true); }
		 */
	}

	public String createPointdeVente() {

		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		
		try {
			if (commonService.checkExistingPointDeVenteByName(pointDeVente.getLibelle() , pointDeVente.getIdCommune())) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
						"magasin" + pointDeVente.getLibelle() + " existe déja dans la commune selectionnée ...");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "gestionDesPointsdeVenteCreation.xhtml?faces-redirect=truee&idBlocToShow=2	";
			}
		} catch (EntityDBDAOException e) {
			Log.error("Une erreur  technique est survenue lors de la verification de l'existance du point de vente"  + e.getMessage());
		} 
		
		
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
	
	
//	
//	
//	public String createPointdeVenteCommune() {
//
//		FacesContext context = FacesContext.getCurrentInstance();
//		context.getExternalContext().getFlash().setKeepMessages(true);
//		
//		try {
//			if (commonService.checkExistingPointDeVenteByName(pointDeVente.getLibelle() , pointDeVente.getIdCommune())) {
//				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
//						"Le point de vente " + pointDeVente.getLibelle() + " existe déja dans la commune selectionnée ...");
//				FacesContext.getCurrentInstance().addMessage(null, msg);
//				return "gestionDesPointsdeVenteCreation.xhtml?faces-redirect=truee&idBlocToShow=2	";
//			}
//		} catch (EntityDBDAOException e) {
//			Log.error("Une erreur  technique est survenue lors de la verification de l'existance du point de vente"  + e.getMessage());
//		} 
//		
//		
//		if (commonService.createPointdeVenteCommune(pointDeVente)) {
//			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
//					"Le point de vente " + pointDeVente.getLibelle() + " est créé avec succès ...");
//			FacesContext.getCurrentInstance().addMessage(null, msg);
//			System.out.println("création d'un Point de vente" + pointDeVente.getLibelle() + " OK 42");
//			return "agentSaisieReferentielPointdeVente.xhtml?faces-redirect=truee&idBlocToShow=1";
//
//		} else {
//			System.out.println("création d'un Point de vente" + pointDeVente.getLibelle() + " KO 42");
//			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, " ",
//					"Une erreur est survenue lors de la création du point de vente ");
//			FacesContext.getCurrentInstance().addMessage(null, msg);
//			return "agentSaisieReferentielPointdeVente.xhtml?faces-redirect=truee&idBlocToShow=1";
//		}
//	}
	
	
	
	FournisseurDTO providerDTO  = new  FournisseurDTO();
	public String createFournisseur() {

		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (commonService.createFournisseur(providerDTO)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Le fournisseur " + providerDTO.getLibelle() + " est créé avec succès ...");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			System.out.println("création d'un Point de vente" + pointDeVente.getLibelle() + " OK 42");
			
		
			return "gestionDesFournisseurs.xhtml?faces-redirect=truee&idBlocToShow=1";

		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, " ",
					"Une erreur est survenue lors de la création du fournisseur ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesDesFournisseursCreation.xhtml?faces-redirect=truee&idBlocToShow=2";
		}
	}
	
	
	public String createPointdeCollecte() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (commonService.createPointdeCollecte(pointDeVente)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Le point de collecte " + pointDeVente.getLibelle() + " est créé avec succès ...");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			System.out.println("création d'un Point de collecte" + pointDeVente.getLibelle() + " OK 42");
			return "gestionDesPointsdeCollecte.xhtml?faces-redirect=truee&idBlocToShow=1";

		} else {
			System.out.println("création d'un Point de collecte" + pointDeVente.getLibelle() + " KO 42");
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, " ",
					"Une erreur est survenue lors de la création du point de collecte ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesPointsdeCollecteCreation.xhtml";
		}
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

	public void AjaxUpdatePointdeVenteDetail() {
		System.out.println(" Point de vente : " + pointDeVente.getLibelle() + "R = " + pointDeVente.getRegion() + "D = "
				+ pointDeVente.getDepartement() + "C = " + pointDeVente.getCommune());
	}

	/*************** PV END ***************/

	public List<CoupleDTO> refreshProfilDTOs() {
		return commonService.loadProfil();
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

	public FournisseurDTO getProviderDTO() {
		return providerDTO;
	}

	public void setProviderDTO(FournisseurDTO providerDTO) {
		this.providerDTO = providerDTO;
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

	public void setEngraisDTO(IntrantDTO engraisDTO) {
		this.engraisDTO = engraisDTO;
	}

	public EngraisDTO getSelectedEngraisDTO() {
		return selectedEngraisDTO;
	}

	public void setSelectedEngraisDTO(EngraisDTO selectedEngraisDTO) {
		this.selectedEngraisDTO = selectedEngraisDTO;
	}

	public Class<?> getGenericsSelectElement() {
		return genericsSelectElement;
	}

	public void setGenericsSelectElement(Class<?> genericsSelectElement) {
		this.genericsSelectElement = genericsSelectElement;
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

	public CoupleDTO getSelectedCamion() {
		return selectedCamion;
	}

	public void setSelectedCamion(CoupleDTO selectedCamion) {
		this.selectedCamion = selectedCamion;
	}

	public CoupleDTO getSelectedChauffeur() {
		return selectedChauffeur;
	}

	public void setSelectedChauffeur(CoupleDTO selectedChauffeur) {
		this.selectedChauffeur = selectedChauffeur;
	}

	public CoupleDTO getSelectedTransporteurDTO() {
		return selectedTransporteurDTO;
	}

	public void setSelectedTransporteurDTO(CoupleDTO selectedTransporteurDTO) {
		this.selectedTransporteurDTO = selectedTransporteurDTO;
	}

	public String previewBL() {
		// bondeLivraisonDTO.setPrixtotal(0L);
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (listProduitsDTOtoCreate == null || listProduitsDTOtoCreate.size() < 1) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Il faut au moins un produit pour votre BL");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesBL.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (bondeLivraisonDTO == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "le BL est vide ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesBL.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		try {
			PersonneDTO chauffeur = commonService.loadChauffeurByIdChauffeur(bondeLivraisonDTO.getChauffeurid());
			if (chauffeur != null) {
				bondeLivraisonDTO.setChauffeurid(chauffeur.getId());
				bondeLivraisonDTO.setChauffeurlibelle(chauffeur.getPrenom() + " " + chauffeur.getNom());
			}

			CoupleDTO transporteur = commonService.loadTransnporteurbyId(bondeLivraisonDTO.getTransporteurid());
			if (chauffeur != null) {
				bondeLivraisonDTO.setTransporteurid(transporteur.getClef());
				bondeLivraisonDTO.setTransporteurlibelle(transporteur.getValeur());
			}

			CoupleDTO camion = commonService.loadCamionById(bondeLivraisonDTO.getCamionid());
			if (chauffeur != null) {
				bondeLivraisonDTO.setCamionid(camion.getClef());
				bondeLivraisonDTO.setCamionnumero(camion.getValeur());
			}

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			if (request != null) {
				HttpSession session = request.getSession(false);
				String connectedUserLocation = (String) session.getAttribute("connectedUserPointPhysique");
				Long connectedUseridStock = (Long) session.getAttribute("connectedUserStockid");
				Long connectedUserid = (Long) session.getAttribute("connectedUserPersonneid");

				Long idStockReceptionnaire = programmAgricoleService.getStockidFromPointDeVente(pointDeVente.getIdPv());

				bondeLivraisonDTO.setIdStockReceptionnaire(idStockReceptionnaire);
				bondeLivraisonDTO.setProvenanceBL(connectedUserLocation);
				bondeLivraisonDTO.setIdStockSortant(connectedUseridStock);
				bondeLivraisonDTO.setIdAuteurBl(connectedUserid);
			}

		} catch (Exception e) {
			Log.error("Erreur de preview du BL");
			return "gestionDesBL.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (programmAgricoleService.createBL(bondeLivraisonDTO, listProduitsDTOtoCreate)) {
			return "gestionDesBL.xhtml?faces-redirect=true&idBlocToShow=2";

		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Une erreur est survenue lors de la création du BL");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			return "gestionDesBL.xhtml?faces-redirect=true&idBlocToShow=1";

		}
	}

	public String ajouterProduit() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		Double quantiteProduitAjouter = produitDTOtoCreate.getQuantite();

		// Recuperation des autres informations du produit
		ProduitDTO pdto = null;
		try {
			Log.info("Tentative de recuperation du produit en partir de son id <" + produitDTOtoCreate.getIdProduit());
			pdto = programmAgricoleService.loadProduitDTObyIdProduit(produitDTOtoCreate.getIdProduit());

			// Si Il y'a pas assez de stock pour le produit selectionne
			if (pdto.getQuantite() == null || pdto.getQuantite() == 0 || pdto.getQuantite() < quantiteProduitAjouter) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Le stock de " + pdto.getLibelleProduit() + " restant est inferieur à la quantité du BL",
						" Stock : " + pdto.getQuantite() + " " + pdto.getUniteDeMesure() + " Quantite BL : "
								+ quantiteProduitAjouter + " ");
				FacesContext.getCurrentInstance().addMessage(null, msg);

			} else // on mets a jour le stock du produit restant en enlevant
			{

				/*
				 * for(ProduitDTO p : listProduitsDTOtoCreate) { if
				 * (p.getIdProduit() == produitDTOtoCreate.getIdProduit()) // le
				 * produit existe deja sur la liste tmp ; on le mets a jour {
				 * pdto.setQuantite(quantiteProduitAjouter); float totalPrix =
				 * quantiteProduitAjouter * pdto.getPrixUnitaire(); float
				 * unitairePrix = pdto.getPrixUnitaire();
				 * pdto.setPrixTotal(totalPrix);
				 * pdto.setPrixUnitaire(unitairePrix);
				 * bondeLivraisonDTO.setPrixtotal(bondeLivraisonDTO.getPrixtotal
				 * () + totalPrix);
				 * bondeLivraisonDTO.setChargeTotal(bondeLivraisonDTO.
				 * getChargeTotal() + quantiteProduitAjouter);
				 * 
				 * listProduitsDTOtoCreate.remove(p);
				 * listProduitsDTOtoCreate.add(pdto);
				 * bondeLivraisonDTO.setProgrammeLibelle(pdto.
				 * getLibelleProgramme());
				 * bondeLivraisonDTO.setCampagneLibelle(pdto.getLibelleCampagne(
				 * ));
				 * 
				 * return ""; }
				 * 
				 * }
				 */

				pdto.setQuantite(quantiteProduitAjouter);
				Double totalPrix = quantiteProduitAjouter * pdto.getPrixUnitaire();
				Float unitairePrix = pdto.getPrixUnitaire();
				pdto.setPrixTotal(totalPrix);
				pdto.setPrixUnitaire(unitairePrix);
				//bondeLivraisonDTO.setPrixtotal(bondeLivraisonDTO.getPrixtotal() + totalPrix);
				//bondeLivraisonDTO.setChargeTotal(bondeLivraisonDTO.getChargeTotal() + quantiteProduitAjouter);
				listProduitsDTOtoCreate.add(pdto);

				bondeLivraisonDTO.setProgrammeLibelle(pdto.getLibelleProgramme());
				bondeLivraisonDTO.setCampagneLibelle(pdto.getLibelleCampagne());
			}

		} catch (ProgrammeException | EntityDBDAOException e) {
			Log.error("Erreur Recuperation du produit en partir de son id <" + produitDTOtoCreate.getIdProduit());
			e.printStackTrace();
		}

		produitDTOtoCreate.setQuantite(0.0);
		// produitDTOtoCreate.setIdProduit(idProduit);

		return "";

	}

	// for
	public String lettredeVoitureDeletProduit() {
		// listProduitCommande.add(produitAajouterAlaCommande);

		for (ProduitDTO p : listProduitsDTOtoCreate) {
			if (p.getIdProduit() == selecteidProduitoDelete) {
				listProduitsDTOtoCreate.remove(p);
				return "gestionDesBL.xhtml";
			}
		}
		return "gestionDesBL.xhtml";
	}

	// Suppression produit a creer pendant ledition dune lettre de voiture
	public void delecteProduitofBl(String libelle, String quantite) {
		int index = 0;
		for (ProduitDTO p : listProduitsDTOtoCreate) {
			if (p.getLibelle() == libelle && p.getQuantite().toString() == quantite) {
				break;
			}
			index++;
		}
		listProduitsDTOtoCreate.remove(index--);

	}

	
	
	
	
	
	
	/*************** REDIRECTION INVENTAIRE CONTENU MAGASIN**************/

	float listeCreditDTOofSelectePVTotal ;
	float listeversementBanqueofSelectePVTotal ;
	float listeVentefSelectePVTotal ;
	float selectedPointDeVenteId;
	private List<ProduitDTO> listProduitslieeSelectedVente;
	private int nbIntrantDispo;
	
	public String redirectSpecifiquePointdeVente() {
	
			
			if(pointDeVente == null)
			{
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
						"Merci de selectionner un magasin ...");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "gestionDesMagasins.xhtml";
			}
			
			try {
				listProduitslieeSelectedVente = commonService.loadStockProduitByIdPointdeVenteMagasinier(pointDeVente.getIdstockReference());
				
				nbIntrantDispo =  listProduitslieeSelectedVente != null ? listProduitslieeSelectedVente.size() : 0;
			} catch (EntityDBDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
//			/*
//			listeCreditDTOofSelectePVTotal = 0;
//			listeversementBanqueofSelectePVTotal = 0;
//			listeVentefSelectePVTotal = 0l;
//			Long idProg = 0L;
//
//			PointDeVenteDTO pv = commonService.loadPointDeVenteById(pointDeVente.getIdPv());
//
//			if (pv != null)
//				selectedPointDeVenteId = pv.getIdstockReference();
//
//			listProduitslieeSelectedVente = commonService
//					.loadStockProduitByIdPointdeVenteMagasinier(selectedPointDeVenteId);
//
//			listecreditDTOofSelectePV = tresorerieService.loadAllCreditsByIdPointDeVente(selectedPointDeVenteId,
//					idProg);
//			for (CreditDTO c : listecreditDTOofSelectePV)
//				listeCreditDTOofSelectePVTotal += c.getMontantRestantApayer();
//
//			listeversementBanqueofSelectePV = tresorerieService
//					.loadAllVersementBanqueByIdPointDeVente(selectedPointDeVenteId, idProg);
//			for (VersementBanqueDTO v : listeversementBanqueofSelectePV)
//				listeversementBanqueofSelectePVTotal += v.getMontantVersment();
//
//			listeVenteofSelectePV = tresorerieService.loadAllVentesByIdPointDeVente(selectedPointDeVenteId, idProg);
//			for (VentesDTO v : listeVenteofSelectePV)
//				listeVentefSelectePVTotal += v.getMontantEncaisse();
//
//		} catch (EntityDBDAOException e) {
//			Log.error("Erreur de recuperation de la liste  des produits" + e.getMessage());
//			e.printStackTrace();
//		}
//		 listProduitslieeSelectedVente =
//		 programmAgricoleService.getListProduitsVEnduFromIdVente(selectedVenteDTO.getVentes_id()); */

		return "agentSaisieDetailsSelectedRegionPointDeVente.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	
	
	/*************** END REPORT PDF **************/

	/*************** END M I S E S EN P L A C E **************/

	
	
	
	
	
	
	
	
	
	
	
	
	
	/*********  Mise en Place ***/
	
	String libelleAndProg ;
	public String initMiseEnplace()
	{
		if(selectedIntrantDTO != null )
		{
			try {
				ProduitDTO intrantAmettreENplace  = programmAgricoleService.loadProduitDTOReferentielbyIdProduit(selectedIntrantDTO.getIdProduit());
				
				libelleIntrantAmettreEnplace = intrantAmettreENplace != null ? intrantAmettreENplace.getLibelle()  : "";
				return "gestionDesMisesEnplace.xhtml?faces-redirect=true&idBlocToShow=1";

			} catch (EntityDBDAOException | ProgrammeException e) {
				// TODO LOG
				return "gestionDesMisesEnplace.xhtml?faces-redirect=true&idBlocToShow=111";

			} 
		}
		return "gestionDesMisesEnplace.xhtml?faces-redirect=true&idBlocToShow=1";	
	}
	
	
	
	
	
	
	
	
	
	
	
	/********************EXPORT PDF *******************/
	

	
	
	
	
	
	
	
	public BonDeLivraisonDTO getBondeLivraisonDTO() {
		return bondeLivraisonDTO;
	}

	public void setBondeLivraisonDTO(BonDeLivraisonDTO bondeLivraisonDTO) {
		this.bondeLivraisonDTO = bondeLivraisonDTO;
	}

	public int getModeBl() {
		return modeBl;
	}

	public void setModeBl(int modeBl) {
		this.modeBl = modeBl;
	}

	public List<ProduitDTO> getListProduitsDTOtoCreate() {
		return listProduitsDTOtoCreate;
	}

	public void setListProduitsDTOtoCreate(List<ProduitDTO> listProduitsDTOtoCreate) {
		this.listProduitsDTOtoCreate = listProduitsDTOtoCreate;
	}

	public ProduitDTO getProduitDTOtoCreate() {
		return produitDTOtoCreate;
	}

	public void setProduitDTOtoCreate(ProduitDTO produitDTOtoCreate) {
		this.produitDTOtoCreate = produitDTOtoCreate;
	}

	public UtilString getUtils() {
		return utils;
	}

	public void setUtils(UtilString utils) {
		this.utils = utils;
	}

	public String getPvTest() {
		return pvTest;
	}

	public List<ProduitDTO> getListProduitslieeSelectedVente() {
		return listProduitslieeSelectedVente;
	}

	public void setListProduitslieeSelectedVente(List<ProduitDTO> listProduitslieeSelectedVente) {
		this.listProduitslieeSelectedVente = listProduitslieeSelectedVente;
	}

	public void setPvTest(String pvTest) {
		this.pvTest = pvTest;
	}

	public List<String> getListBanque() {

		return listBanque;
	}

	public void setListBanque(List<String> listBanque) {
		this.listBanque = listBanque;
	}

	// TODO methode à remplacer par fichier properties ou table de ref en base

	public List<String> loadBanqueSenegal() {

		List<String> listBanque = new ArrayList<String>();
		listBanque.add("AWB");
		listBanque.add("SGBS");
		listBanque.add("CITIBANK");
		listBanque.add("BICIS");
		listBanque.add("CBAO");
		listBanque.add("BHS");
		listBanque.add("ICB");
		listBanque.add("BRS");
		listBanque.add("BOA");
		listBanque.add("BIS");
		listBanque.add("BAS");
		listBanque.add("BIMAO");
		listBanque.add("BSIC");
		listBanque.add("LOCAFRIQUE");
		listBanque.add("SOCRES");
		listBanque.add("CLS");
		listBanque.add("Ecobank");
		return listBanque;
	}

	public List<CoupleDTO> getListeAllProgramme() {
		return listeAllProgramme;
	}

	public void setListeAllProgramme(List<CoupleDTO> listeAllProgramme) {
		this.listeAllProgramme = listeAllProgramme;
	}

	public List<CoupleDTO> getListeProgrammeOuvert() {
		return listeProgrammeOuvert;
	}

	public void setListeProgrammeOuvert(List<CoupleDTO> listeProgrammeOuvert) {
		this.listeProgrammeOuvert = listeProgrammeOuvert;
	}

	public String getLibelleAndProg() {
		return libelleAndProg;
	}

	public void setLibelleAndProg(String libelleAndProg) {
		this.libelleAndProg = libelleAndProg;
	}

	public String getSubventionProduit() {
		return subventionProduit;
	}

	public void setSubventionProduit(String subventionProduit) {
		this.subventionProduit = subventionProduit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isShowBlocTauxSubvention() {
		return showBlocTauxSubvention;
	}

	public void setShowBlocTauxSubvention(boolean showBlocTauxSubvention) {
		this.showBlocTauxSubvention = showBlocTauxSubvention;
	}

	public List<CoupleDTO> getListProductofSelectedTypeProduit() {
		return listProductofSelectedTypeProduit;
	}

	public void setListProductofSelectedTypeProduit(List<CoupleDTO> listProductofSelectedTypeProduit) {
		this.listProductofSelectedTypeProduit = listProductofSelectedTypeProduit;
	}

	public List<IntrantDTO> getListReferentielfiltred() {
		return listReferentielfiltred;
	}

	public void setListReferentielfiltred(List<IntrantDTO> listReferentielfiltred) {
		this.listReferentielfiltred = listReferentielfiltred;
	}

	public Long getSelecteidProduitoDelete() {
		return selecteidProduitoDelete;
	}

	public void setSelecteidProduitoDelete(Long selecteidProduitoDelete) {
		this.selecteidProduitoDelete = selecteidProduitoDelete;
	}

	public Long getFiltredCat() {
		return filtredCat;
	}

	public void setFiltredCat(Long filtredCat) {
		this.filtredCat = filtredCat;
	}

	public Long getFiltredInt() {
		return filtredInt;
	}

	public void setFiltredInt(Long filtredInt) {
		this.filtredInt = filtredInt;
	}

	public List<PointDeVenteDTO> getFiltredPointdeVente() {
		return filtredPointdeVente;
	}

	public void setFiltredPointdeVente(List<PointDeVenteDTO> filtredPointdeVente) {
		this.filtredPointdeVente = filtredPointdeVente;
	}

	public Long getFilterIdProgramme() {
		return filterIdProgramme;
	}

	public void setFilterIdProgramme(Long filterIdProgramme) {
		this.filterIdProgramme = filterIdProgramme;
	}

	public int getFilterStatus() {
		return filterStatus;
	}

	public void setFilterStatus(int filterStatus) {
		this.filterStatus = filterStatus;
	}

	public float getListeCreditDTOofSelectePVTotal() {
		return listeCreditDTOofSelectePVTotal;
	}

	public float getListeversementBanqueofSelectePVTotal() {
		return listeversementBanqueofSelectePVTotal;
	}

	public float getListeVentefSelectePVTotal() {
		return listeVentefSelectePVTotal;
	}

	public float getSelectedPointDeVenteId() {
		return selectedPointDeVenteId;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setListeCreditDTOofSelectePVTotal(float listeCreditDTOofSelectePVTotal) {
		this.listeCreditDTOofSelectePVTotal = listeCreditDTOofSelectePVTotal;
	}

	public void setListeversementBanqueofSelectePVTotal(float listeversementBanqueofSelectePVTotal) {
		this.listeversementBanqueofSelectePVTotal = listeversementBanqueofSelectePVTotal;
	}

	public void setListeVentefSelectePVTotal(float listeVentefSelectePVTotal) {
		this.listeVentefSelectePVTotal = listeVentefSelectePVTotal;
	}

	public int getNbIntrantDispo() {
		return nbIntrantDispo;
	}

	public void setNbIntrantDispo(int nbIntrantDispo) {
		this.nbIntrantDispo = nbIntrantDispo;
	}

	public void setSelectedPointDeVenteId(float selectedPointDeVenteId) {
		this.selectedPointDeVenteId = selectedPointDeVenteId;
	}

	public List<IntrantDTO> getListIntrant() {
		return listIntrant;
	}

	public void setListIntrant(List<IntrantDTO> listIntrant) {
		this.listIntrant = listIntrant;
	}

	public List<IntrantDTO> getListIntrantFiltred() {
		return listIntrantFiltred;
	}

	public void setListIntrantFiltred(List<IntrantDTO> listIntrantFiltred) {
		this.listIntrantFiltred = listIntrantFiltred;
	}

	public List<CoupleDTO> getPointdeVentesOfSelectedDepartement() {
		return pointdeVentesOfSelectedDepartement;
	}

	public void setPointdeVentesOfSelectedDepartement(List<CoupleDTO> pointdeVentesOfSelectedDepartement) {
		this.pointdeVentesOfSelectedDepartement = pointdeVentesOfSelectedDepartement;
	}




}
