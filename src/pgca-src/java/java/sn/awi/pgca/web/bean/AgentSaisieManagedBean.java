package sn.awi.pgca.web.bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.LogFactory;
import org.jfree.util.Log;
import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;

import com.itextpdf.text.DocumentException;

import sn.awi.pgca.business.dao.ModelDAOImpl;
import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.exception.ProgrammeException;
import sn.awi.pgca.business.service.ICommonService;
import sn.awi.pgca.business.service.IProgrammeAgricol;
import sn.awi.pgca.business.service.ITresorerieService;
import sn.awi.pgca.business.service.Inotification;
import sn.awi.pgca.dataModel.Intrant;
import sn.awi.pgca.utils.UtilString;
import sn.awi.pgca.web.dto.BonDeLivraisonDTO;
import sn.awi.pgca.web.dto.CamionDTO;
import sn.awi.pgca.web.dto.CommandeDTO;
import sn.awi.pgca.web.dto.CoupleDTO;
import sn.awi.pgca.web.dto.CreditDTO;
import sn.awi.pgca.web.dto.FournisseurDTO;
import sn.awi.pgca.web.dto.IndicateursDashboardDTO;
import sn.awi.pgca.web.dto.InfosCommunesDTO;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.IntrantTypeDTO;
import sn.awi.pgca.web.dto.LitigesDTO;
import sn.awi.pgca.web.dto.MagasinDTO;
import sn.awi.pgca.web.dto.MiseEnPlaceEffectuerDTO;
import sn.awi.pgca.web.dto.MiseEnplaceAgregation;
import sn.awi.pgca.web.dto.MiseEnplaceDTOCommune;
import sn.awi.pgca.web.dto.MiseEnplaceDTOPointDeVente;
import sn.awi.pgca.web.dto.PointDeCollecteDTO;
import sn.awi.pgca.web.dto.PointDeVenteDTO;
import sn.awi.pgca.web.dto.ProduitDTO;
import sn.awi.pgca.web.export.ReportPDFGerenics;

@ManagedBean(name = "agentSaisieMB")
@SessionScoped
public class AgentSaisieManagedBean {

	@ManagedProperty(value = "#{commonService}")
	private ICommonService commonService;
	@ManagedProperty(value = "#{programmeAgricolService}")
	private IProgrammeAgricol programmAgricoleService;
	@ManagedProperty(value = "#{tresorerieService}")
	private ITresorerieService serviceTresorerie;
	@ManagedProperty(value = "#{notificationService}")
	private Inotification notifierService;

	private Long connectedUserProfilID = 0L;

	private CommandeDTO commandeDTO = new CommandeDTO();
	private List<ProduitDTO> listProduitCommande = new ArrayList<ProduitDTO>();
	private List<CommandeDTO> listCommandes;
	private List<CommandeDTO> filtredistCommandes;

	private List<IntrantDTO> listIntrantsOfStock;
	private List<IntrantDTO> filtredlistIntrantsOfStock;

	private List<CamionDTO> listTransporteur = new ArrayList<CamionDTO>();
	private List<CamionDTO> listTransporteurfiltred = new ArrayList<CamionDTO>();

	private MiseEnplaceDTOPointDeVente selectedMiseEnplaceDTOPointDeVente;
	private List<CoupleDTO> filtredIntrant = new ArrayList<CoupleDTO>();
	private MiseEnplaceDTOPointDeVente executionMiseEnPlace;

	private List<PointDeVenteDTO> listRefPointdeVenteDTO;

	private IndicateursDashboardDTO indicateurs;

	private TreeNode root2;
	// PAiement de la commande
	private String modeDePaiement; // 0 = pendant la livraison , 1 : au siége, 2
									// : A credit contracte par le stock sortant
	ProduitDTO produitAajouterAlaCommande = new ProduitDTO();
	ProduitDTO selecteProduiFromCMD = new ProduitDTO();
	Long selecteidProduitoDelete;
	CreditDTO creditLieeAlaCommmande = new CreditDTO();
	UtilString utilsService;
	String referenceCrdit;
	float montantCredit;
	boolean stockCommandeDispo;

	Long filterIdProgramme;
	Long filterIdpv;
	int filterStatus;
	String fileternomClient;

	private Long olIdPointdeVente;
	private Long olIdProgramme;
	private int olstatut;
	private String olclient;

	// cached data
	String listOfCamionFromDBInJSON;
	String listOfChauffeurFromDBInJSON;
	String listOfTransporteurFromDBInJSON;

	public Inotification getNotifierService() {
		return notifierService;
	}

	public void setNotifierService(Inotification notifierService) {
		this.notifierService = notifierService;
	}

	@PostConstruct
	public void init() {

		utilsService = new UtilString();
		executionMiseEnPlace = new MiseEnplaceDTOPointDeVente();
		stockCommandeDispo = true;
		listProduitCommande = new ArrayList<ProduitDTO>();
		filterStatus = -1;
		selectedIntrantDTO = new IntrantDTO();

		// cache ref : transport + camion + driver
		try {
			loadCacheReferential();
		} catch (EntityDBDAOException e1) {
			Log.error("Erreur lors de la recupération des des indicateurs");
		}
		loadCacheRepartionStockFournisseur();

		// list stock connected user
		try {
			listIntrantsOfStock = programmAgricoleService.loadAllIntrantDTOByIdStock(
					SessionManagedBean.getSessionLongValuesDataByTag("connectedUserStockid"));
			indicateurs = programmAgricoleService.loadAllIndicateursDashboard();

			connectedUserProfilID = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserProfilID");
		} catch (Exception e) {
			Log.error("Impossible de recuperer le stock de l'agent connecté");
		}

	}

	public String commandeAddProduit() {
		listProduitCommande.add(produitAajouterAlaCommande);

		return "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=1&showBlocsuggestion=0";
	}

	/*********
	 * MAGASIN
	 * 
	 * @return
	 ***********/
	List<PointDeVenteDTO> listMagasin = null;
	PointDeVenteDTO listMagasinSelectedItem;
	List<PointDeVenteDTO> listMagasinFiltred = null;

	public List<PointDeVenteDTO> loadAllMagasin() {
		if (listMagasin == null) {
			listMagasin = commonService.loadAllPointDeVente(false);
			return listMagasin;
		}
		return listMagasin;
	}

	public String redirectStockMagasin() {
		loadAllMagasin();
		return "agentsaisieGestionStockMagasin.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	/*********
	 * POINT DE VENTE : LES PRODUITS MISES EN PLACE DANS LE PV peuvent rentrer
	 * dans le stock de nouveau
	 * 
	 * l'etat demande de mettre en place 100 et il peut arriver que SEDAB vend
	 * 80 car malgrés la subvention
	 * 
	 * les producteurs n'ont pas achété , dans ce cas la sedab ne facture que 80
	 * et doit reprendre les 20
	 * 
	 * ces 20 sont des stocks residuels qui sedab recollecte de nouveau dans ses
	 * magasin : cela dit un intrant peut
	 * 
	 * boucler entre mag -> POINT DE VENTE OU LINVERSE
	 * 
	 * 
	 * @return
	 ***********/

	List<IntrantDTO> stockResiduel;
	List<IntrantDTO> stockResiduelFiltred;
	IntrantDTO stockResiduelSelected;

	public List<IntrantDTO> loadStockResiduel() {
		if (stockResiduel == null) {
			try {
				stockResiduel = programmAgricoleService.loadStockResiduel();
			} catch (EntityDBDAOException e) {
				Log.error("Erreur  survenue lecture  stock residuel" + e.getMessage());
			}
		}
		return stockResiduel;
	}

	int stockResiduelByIntrantSize;
	List<IntrantDTO> stockResiduelByIntrant;

	List<IntrantDTO> stockResiduelFiltredByIntrant;
	IntrantDTO stockResiduelSelectedByIntrant;

	public List<IntrantDTO> loadAllIntrantResiduelByIntrantId() {

		try {
			if (stockResiduelSelected != null) {
				stockResiduelByIntrant = programmAgricoleService
						.loadStockResiduelByIntrant(stockResiduelSelected.getIdProduit());

				stockResiduelByIntrantSize = stockResiduelByIntrant != null ? stockResiduelByIntrant.size() : 0;
			}

		} catch (EntityDBDAOException e) {
			Log.error("Erreur  survenue lecture  stock residuel" + e.getMessage());
		}
		return stockResiduelByIntrant;
	}

	public String redirectStockPointdeVente() {
		loadStockResiduel();
		return "agentsaisieGestionStockPointdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	MagasinDTO magDTO = new MagasinDTO();

	public String createmagasin() {
		try {

			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);

			String infoError = "";
			if (checckErrorsCreation(infoError)) {

				showMessage(FacesMessage.SEVERITY_WARN, infoError);
				return "agentSaisieREFMagasinsCreation.xhtml?faces-redirect=true";
			}

			InfosCommunesDTO communeMagasin = commonService.getDetailsCommune(magDTO.getMagasinCommune());
			if (communeMagasin == null) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
						"La commune   " + magDTO.getMagasinCommune() + " n'existe pas dans la lateforme...");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "agentSaisieREFMagasinsCreation.xhtml?faces-redirect=true";
			}
			magDTO.setIdCommune(new Long(communeMagasin.getCommuneid()));

			if (commonService.checkExistingPointDeVenteByName(magDTO.getMagasinCommune(),
					new Long(communeMagasin.getCommuneid()))) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
						magDTO.getMagasinCommune() + "Un  magasin nommé " + magDTO.getMagasinNom()
								+ " existe déja dans la commune  " + magDTO.getMagasinCommune());
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "agentSaisieREFMagasins.xhtml?faces-redirect=true	";
			}

			if (commonService.createMagasin(magDTO)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
						"Le magasin " + magDTO.getMagasinNom() + " est créé avec succès  dans la commune de "
								+ magDTO.getMagasinCommune() + " avec succès...");
				FacesContext.getCurrentInstance().addMessage(null, msg);

				listMagasin = commonService.loadAllPointDeVente(false);
				magDTO = new MagasinDTO();
				return "agentSaisieREFMagasins.xhtml?faces-redirect=truee&idBlocToShow=1";

			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, " ",
						"Une erreur est survenue lors de la création du magasin ");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "agentSaisieREFMagasinsCreation.xhtml?faces-redirect=truee&idBlocToShow=1";
			}

		} catch (EntityDBDAOException e) {
			Log.error("Une erreur  technique est survenue lors de la verification de l'existance du magasin"
					+ e.getMessage());
			return "agentSaisieREFMagasinsCreation.xhtml?faces-redirect=truee&idBlocToShow=1";
		}
	}

	private String nomPv;
	private String Communepv;
	private Long idDepartement;

	public String createPointdeVenteCommune() {

		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (Communepv == null || Communepv.trim().equals("")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Le nom du point de vente  est obligatoire  .");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "agentSaisieReferentielPointdeVenteCreation.xhtml?faces-redirect=truee";
		}

		InfosCommunesDTO communeMagasin = commonService.getDetailsCommune(Communepv);

		if (communeMagasin != null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Un point de vente existe déjà avec ce nom.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "agentSaisieReferentielPointdeVenteCreation.xhtml?faces-redirect=true";
		}

		if (commonService.createPointdeVenteCommune(idDepartement, Communepv)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Le point de vente " + nomPv + " est créé avec succès  .");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			try {
				listDesPointsDeVentes = programmAgricoleService.loadAllPointdeVenteNational();
			} catch (EntityDBDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "agentSaisieReferentielPointdeVente.xhtml?faces-redirect=truee&idBlocToShow=1";

		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, " ",
					"Une erreur est survenue lors de la création du point de vente ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "agentSaisieReferentielPointdeVenteCreation.xhtml?faces-redirect=truee";
		}
	}

	public String redirectListeMagasin() {
		// loadAllMagasin();
		return "agentSaisieREFMagasins.xhtml?faces-redirect=truee&idBlocToShow=1";
	}

	private boolean checckErrorsCreation(String msg) {
		if (magDTO.getMagasinNom() == null || magDTO.getMagasinNom().trim().equals("")) {
			msg = "Le nom du magasin est obligatoire ...";
			return true;
		}

		if (magDTO.getMagasinGerantNom() == null || magDTO.getMagasinGerantNom().trim().equals("")) {
			msg = "Le nom du gerant du magasin est obligatoire ...";
			return true;
		}

		if (magDTO.getMagasinGeraantPrenom() == null || magDTO.getMagasinGeraantPrenom().trim().equals("")) {
			msg = "Le prenom du gerant du magasin est obligatoire ...";
			return true;
		}

		if (magDTO.getMagasinGerantTel() == null || magDTO.getMagasinGerantTel().trim().equals("")) {
			msg = "Le tel du gerant du magasin est obligatoire ...";
			return true;
		}
		return false;
	}

	/**** END MAG *******/

	public String commandeDeletProduit() {
		// listProduitCommande.add(produitAajouterAlaCommande);

		for (ProduitDTO p : listProduitCommande) {
			if (p.getIdProduit() == selecteidProduitoDelete) {
				listProduitCommande.remove(p);
				return "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=1&showBlocsuggestion=0";
			}
		}
		return "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=1&showBlocsuggestion=0";
	}

	public String olDeletProduit() {
		// listProduitCommande.add(produitAajouterAlaCommande);
		for (ProduitDTO p : listProduitCommande) {
			if (p.getIdProduit() == selecteidProduitoDelete) {
				listProduitCommande.remove(p);
				return "gestionDesOL.xhtml";
			}
		}
		return "gestionDesOL.xhtml";
	}

	public String ajouterProduitAlaCommande() {
		try {
			Map<Boolean, ProduitDTO> produitAjouter = programmAgricoleService
					.chercherDisponibiliteProduitParPoids(produitAajouterAlaCommande);
			if (produitAjouter.get(true) != null) {
				Collection<ProduitDTO> allValues = produitAjouter.values();
				ProduitDTO newProduit = new ProduitDTO();

				for (ProduitDTO p : allValues) // syntaxe MAP oblige mais la
												// liste contient tjr un item
					newProduit = p;

				listProduitCommande.add(newProduit);

				return "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=1&showBlocsuggestion=0";
			} else // show Bloc suggestion
				return "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=1&showBlocsuggestion=1";

		} catch (ProgrammeException | EntityDBDAOException e) {

			Log.error("Une erreur est survenue durant lajout du projet  " + e.getMessage());
			return "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=2&showBlocsuggestion=0";
		}

		/*
		 * try { // si la quantite existe , sinon on le cherche sur un autre
		 * stock et le le lui suggerre Map<Boolean, String> resultSet =
		 * programmAgricoleService.chercherDisponibiliteProduitParPoids(
		 * commandeDTO.getProduitAjouter().getIdProduit(),
		 * commandeDTO.getProduitAjouter().getQuantite());
		 * 
		 * resultSet.FE((key, value) { if(!key) {
		 * utilsService.showMessage(FacesMessage.SEVERITY_WARN, value);
		 * stockCommandeDispo = false ; } }); if(!stockCommandeDispo) return
		 * "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=1&showBlocsuggestion=1";
		 * // on ajoute le produit car la quantite est dispo
		 * commandeDTO.getListProduitsDTOtoCreate().add(commandeDTO.
		 * getProduitAjouter());
		 * 
		 * } catch (ProgrammeException | EntityDBDAOException e) {
		 * Log.error("Impossible de verifier la disponibilité du produit ");
		 * e.printStackTrace(); }
		 * 
		 * return
		 * "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=1&showBlocsuggestion=1";
		 */
	}

	public String filterBL() {
		listselectedBLDTO = callServiceRecuperationBLEnvoye();

		if (filterIdProgramme > 0) // filtre Programme
		{
			List<BonDeLivraisonDTO> filtredCommande = new ArrayList<BonDeLivraisonDTO>();

			for (BonDeLivraisonDTO c : listselectedBLDTO) {
				if (c.getProgrammeId() == filterIdProgramme)
					filtredCommande.add(c);
			}
			listselectedBLDTO = filtredCommande;
		}

		if (filtredCat >= 0) // filtre Stock
		{
			List<BonDeLivraisonDTO> filtredCommande2 = new ArrayList<BonDeLivraisonDTO>();
			for (BonDeLivraisonDTO c : listselectedBLDTO) {
				if (filtredCat == c.getIdStockReceptionnaire()) // subventionné
					filtredCommande2.add(c);

				listselectedBLDTO = filtredCommande2;
			}

			if (filterStatus > 0) // Filtre transporteur
			{
				List<BonDeLivraisonDTO> filtredCommande3 = new ArrayList<BonDeLivraisonDTO>();
				for (BonDeLivraisonDTO c : listselectedBLDTO) {
					if (c.getTransporteurid() == filterStatus)
						filtredCommande3.add(c);
				}
				listselectedBLDTO = filtredCommande3;
			}
		}
		return "agentSaisieReferentielBL.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public String filterTransporteur() {
		listTransporteur = commonService.loadAllCamionDTO();

		if (filterIdProgramme > 0) // filtre transport
		{
			List<CamionDTO> filtredCommande = new ArrayList<CamionDTO>();

			for (CamionDTO c : listTransporteur) {
				if (c.getIdTransporteur() == filterIdProgramme)
					filtredCommande.add(c);
			}
			listTransporteur = filtredCommande;
		}

		if (filtredCat >= 0) // filtre chauffeur
		{
			List<CamionDTO> filtredCommande2 = new ArrayList<CamionDTO>();
			for (CamionDTO c : listTransporteur) {
				if (filtredCat == c.getIdChauffeur())
					filtredCommande2.add(c);

				listTransporteur = filtredCommande2;
			}

			if (filterStatus > 0) // Filtre transporteur
			{
				List<BonDeLivraisonDTO> filtredCommande3 = new ArrayList<BonDeLivraisonDTO>();
				for (BonDeLivraisonDTO c : listselectedBLDTO) {
					if (c.getTransporteurid() == filterStatus)
						filtredCommande3.add(c);
				}
				listselectedBLDTO = filtredCommande3;
			}
		}
		return "agentSaisieReferentielTransporteurs.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public String filterOrdres() {
		allOLs = programmAgricoleService.managerLoadOrdresAvalider();

		if (olIdProgramme > 0) // filtre Programme
		{
			List<CommandeDTO> filtredCommande = new ArrayList<CommandeDTO>();

			for (CommandeDTO c : allOLs) {
				if (c.getIdProgramme() == olIdProgramme)
					filtredCommande.add(c);
			}
			allOLs = filtredCommande;
		}

		if (olIdPointdeVente >= 0) // filtre Stock
		{
			List<CommandeDTO> filtredCommande2 = new ArrayList<CommandeDTO>();
			for (CommandeDTO c : allOLs) {
				if (olIdPointdeVente == c.getIdPointdeVente()) // subventionné
					filtredCommande2.add(c);

				allOLs = filtredCommande2;
			}

			if (olstatut > 0) // Filtre transporteur
			{
				List<CommandeDTO> filtredCommande3 = new ArrayList<CommandeDTO>();
				for (CommandeDTO c : allOLs) {
					if (c.getStatus() == olstatut)
						filtredCommande3.add(c);
				}
				allOLs = filtredCommande3;
			}
		}
		return "agentSaisieReferentielOL.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public String loadDetailsSelectedOL() {
		if (commandeDTO == null || commandeDTO.getIdOrdre() == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner un ordre de livraison");
			return "agentSaisieReferentielOL.xhtml?faces-redirect=true&idBlocToShow=1b";
		}
		try {
			commandeDTO = programmAgricoleService.loadOrdreInfosById(commandeDTO.getIdOrdre());
			return "agentSaisieReferentielOL.xhtml?faces-redirect=true&idBlocToShow=1b";
		} catch (Exception e) {
			Log.error("Erreur survenue lors de la lecture des details de l'ordre " + e.getMessage());
			e.printStackTrace();
		}

		showMessage(FacesMessage.SEVERITY_WARN, "Impossible de lire les détails de l'ordre");

		return "";
	}

	List<CommandeDTO> allOLs;
	List<CommandeDTO> filtredallOLs;

	public String initloadAllOrdresDeLivraion() {
		loadAllOrdresDeLivraion();
		return "agentSaisieReferentielOL.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public String initloadAllTransporteurs() {
		listTransporteur = commonService.loadAllCamionDTO();

		return "agentSaisieReferentielTransporteurs.xhtml?faces-redirect=true&idBlocToShow=1";

	}

	/****** DEPLACEMENT DE STOCK DE FOURNISSEUR VERS MAGSIN SEDAB ***/

	Long idMagasin;
	IntrantDTO selectedIntrantTotransfer;
	Double quantiteAtranferer;
	String numeroLVManuel;
	String numeroBLManuel;

	String transportTotransfert = "";
	String camionTotransfer = "";
	String chauufeurTotransfer = "";

	public String deplacerStockFromFournisseurToMagasin() {

		boolean valid;
		if (selectedIntrantTotransfer == null)
			if (selectedIntrantTotransfer == null) {
				showMessage(FacesMessage.SEVERITY_WARN, "Merci de selectionner un intrant à transférer");
				return "gestiondestransferts.xhtml?faces-redirect=true&idBlocToShow=1";
			}
		//
		// if (selectedIntrantTotransfer.getIdStock() != 0) { // pas stock siege
		// // (les stock 0
		// // correspond au
		// // stock aux
		// // fournisseurs)
		// showMessage(FacesMessage.SEVERITY_WARN,
		// "Vous n'êtes pas autorisé à transférer le stock d'un magasin , seul
		// les magasiniers peuvent faire des transferts entre magasin. ");
		// return
		// "gestiondestransferts.xhtml?faces-redirect=true&idBlocToShow=1";
		// }

		if (idMagasin == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Merci de selectionner un magasin (destination).");
			return "gestiondestransferts.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (selectedIntrantTotransfer.getQuantite() < quantiteAtranferer) {
			showMessage(FacesMessage.SEVERITY_WARN,
					"La quantité à transférer doit etre inférieure au celle disponible auprès du fournisseur");
			return "gestiondestransferts.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (!selectedIntrantTotransfer.isDejaTarifie()) {
			showMessage(FacesMessage.SEVERITY_WARN,
					"Impossible de transférer un intrant non tarifié vers un point de vente, merci de  tarifier l'intrant dabord.");
			return "gestiondestransferts.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (transportTotransfert.trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le transport  est obligatoire");
			return "gestiondestransferts.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (camionTotransfer.trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le camion  est obligatoire");
			return "gestiondestransferts.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		if (chauufeurTotransfer.trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le chauffeur est obligatoire");
			return "gestiondestransferts.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		try {
			selectedIntrantTotransfer.setTransportTotransfert(transportTotransfert);
			selectedIntrantTotransfer.setCamionTotransfer(camionTotransfer);
			selectedIntrantTotransfer.setChauufeurTotransfer(chauufeurTotransfer);
			selectedIntrantTotransfer.setBlManuel(numeroBLManuel);
			selectedIntrantTotransfer.setLvManuel(numeroLVManuel);

			valid = programmAgricoleService.transfererStock(selectedIntrantTotransfer, idMagasin, quantiteAtranferer);

			if (valid) {
				showMessage(FacesMessage.SEVERITY_INFO, "Le déplacement du stock est enregistré avec succès.");

				getlistIntrantDTO();

				if (null == referentialMagsin)
					referentialMagsin = commonService.loadReferentielMagasin();

				for (MagasinDTO h : referentialMagsin) {

					if (h.getIdStockMagasin() == idMagasin) {
						expediteurMagasin = h.getMagasinNom();
						expediteurMagasinGerant = h.getMagasinGeraantPrenom() + " " + h.getMagasinGerantNom();
						expediteurMagasinGerantTel = h.getMagasinGerantTel();
						expediteurMagasinGerantMail = h.getMagasinGerantMaill();
						break;
					}
				}

				return "gestionDesTransfertsMagToMag.xhtml?faces-redirect=true&idBlocToShow=3";

			} else {
				showMessage(FacesMessage.SEVERITY_ERROR, "Erreur survenue lors du déplacement du stock.");
				return "gestiondestransferts.xhtml?faces-redirect=true&idBlocToShow=1";
			}

		} catch (Exception e) {
			Log.error("Erreur survenue lors de la lecture des details de l'ordre " + e.getMessage());
		}
		showMessage(FacesMessage.SEVERITY_ERROR, "Une erreur technique est survenue");
		return "gestiondestransferts.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	// transfert Mag to Mag , on use directement le stock id au lieu du magasin
	// id

	List<ProduitDTO> listProduiSelectedMagSource;
	Long idmagasinSource;
	Long idMagasinDestination;
	Long idIntrantaTransferer;
	String pointdeVenteAutocompleteDestination;

	public String loadStockMagById() {
		try {
			if (pointdeVenteAutocomplete.trim().equals("")) {
				showMessage(FacesMessage.SEVERITY_WARN, "Le point de vente est obligatoire");
				return "#";
			}
			idmagasinSource = commonService.loadIPoitDeVenteIdByName(pointdeVenteAutocomplete);
			listProduiSelectedMagSource = commonService.loadStockProduitByIdPointdeVenteMagasinier(idmagasinSource);
		} catch (EntityDBDAOException e) {
			// Log.error( e.printStackTrace());
		}
		return "";
	}


	
	String numBlCREE;
	public String deplacerStockFromMagasinToMagasin() {
		boolean valid;

		try {
			idMagasinDestination = commonService.loadIPoitDeVenteIdByName(pointdeVenteAutocompleteDestination);
		} catch (EntityDBDAOException e1) {
			Log.error("Erreur  " + e1.getMessage());
			return "gestionDesTransfertsMagToMag.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (idMagasinDestination == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "La destination est inconnue");
			return "gestionDesTransfertsMagToMag.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (idmagasinSource == idMagasinDestination) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le magasin de destination doit être différent du magasin cible");
			return "gestionDesTransfertsMagToMag.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (idmagasinSource == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le magasin d'origine est obligatoire");
			return "gestionDesTransfertsMagToMag.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (quantiteAtranferer == 0 || quantiteAtranferer == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "La quantité à transférer est incorrect");
			return "gestionDesTransfertsMagToMag.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		// disponibilité quantité a transmettre
		for (ProduitDTO h : listProduiSelectedMagSource) {
			if (h.getIdStockProduit() == idmagasinSource && h.getQuantite() < quantiteAtranferer) {
				showMessage(FacesMessage.SEVERITY_WARN, "La quantité restante au magasin d'origine est inférieure à "
						+ quantiteAtranferer + "Tonne (s)");
				return "gestionDesTransfertsMagToMag.xhtml?faces-redirect=true&idBlocToShow=1";
			}
		}

		if (transportTotransfert.trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le transport  est obligatoire");
			return "gestionDesTransfertsMagToMag.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (camionTotransfer.trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le camion  est obligatoire");
			return "gestionDesTransfertsMagToMag.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		if (chauufeurTotransfer.trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le chauffeur est obligatoire");
			return "gestionDesTransfertsMagToMag.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		try {
			selectedIntrantTotransfer = new IntrantDTO();
			selectedIntrantTotransfer.setTransportTotransfert(transportTotransfert);
			selectedIntrantTotransfer.setCamionTotransfer(camionTotransfer);
			selectedIntrantTotransfer.setChauufeurTotransfer(chauufeurTotransfer);
			selectedIntrantTotransfer.setBlManuel(numeroBLManuel);
			selectedIntrantTotransfer.setLvManuel(numeroLVManuel);
			selectedIntrantTotransfer.setQuantiteAtransferer(quantiteAtranferer);
			selectedIntrantTotransfer.setIdProduit(idIntrantaTransferer);

			/*
			 * Long idMagasinOrigine = null; Long idMagsinDestinataire = null;
			 * 
			 * for( MagasinDTO h : referentialMagsin) { if(h.getIdStockMagasin()
			 * == idmagasinSource) idMagasinOrigine = h.getIdMagasin();
			 * 
			 * if(h.getIdStockMagasin() == idMagasinDestination)
			 * idMagsinDestinataire = h.getIdMagasin();
			 * 
			 * // si on a déja trouvé les deux id on sort if(idMagasinOrigine !=
			 * null && idMagsinDestinataire != null) break; }
			 */

			// on recupere les id des magasin a partir de l'id de leurs stock

			valid = programmAgricoleService.transfererStockEntreMagasin(selectedIntrantTotransfer, idmagasinSource,
					idMagasinDestination, quantiteAtranferer);

			if (valid) {
				showMessage(FacesMessage.SEVERITY_INFO, "Le déplacement du stock est enregistré avec succès.");
				idMagasin = null;
				numBlCREE = selectedIntrantTotransfer.getBlManuel();
				selectedIntrantTotransfer = null;
				quantiteAtranferer = null;
				numeroBLManuel = "";
				transportTotransfert = "";
				camionTotransfer = "";
				chauufeurTotransfer = "";
				numeroLVManuel = "";
				loadInfosMessagesConfirmation(idmagasinSource, idmagasinSource );

				getlistIntrantDTO();

				// loadInfosMessagesConfirmation(idMagasinOrigine,
				// idMagsinDestinataire);
				return "gestionDesTransfertsMagToMag.xhtml?faces-redirect=true&idBlocToShow=2";

			} else {
				showMessage(FacesMessage.SEVERITY_ERROR, "Erreur survenue lors du déplacement du stock.");
				return "gestionDesTransfertsMagToMag.xhtml?faces-redirect=true&idBlocToShow=1";
			}
		} catch (Exception e) {
			Log.error("Erreur survenue lors dU TRANSFERT DE STOCK " + e.getMessage());
		}
		showMessage(FacesMessage.SEVERITY_ERROR, "Une erreur technique est survenue");
		return "gestionDesTransfertsMagToMag.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	List<MagasinDTO> referentialMagsin;

	public List<MagasinDTO> loadReferentielMagasin() {
		if (referentialMagsin == null || referentialMagsin.size() == 0)
			referentialMagsin = commonService.loadReferentielMagasin();
		return referentialMagsin;
	}

	String expediteurMagasin;
	String expediteurMagasinGerant;
	String expediteurMagasinGerantTel;
	String expediteurMagasinGerantMail;

	String receptionnaireMagasin;
	String receptionnaireMagasinGerant;
	String receptionnaireMagasinGerantTel;
	String receptionnaireMagasinGerantMail;

	public void loadInfosMessagesConfirmation(Long idMagasinOrigine, Long idSMagasinDestination) {

		boolean fromok = false;
		boolean toOk = false;

		PointDeVenteDTO origine = commonService.loadPointDeVenteById(idMagasinOrigine);

		if (origine != null) {
			expediteurMagasin = origine.getLibelle();
			expediteurMagasinGerant = origine.getGerant();
			expediteurMagasinGerantTel = origine.getContactGerant();
			expediteurMagasinGerantMail = origine.getContactGerant();
			fromok = true;
		}
		PointDeVenteDTO destination = commonService.loadPointDeVenteById(idSMagasinDestination);

		if (destination != null) {
			receptionnaireMagasin = destination.getLibelle();
			receptionnaireMagasinGerant = destination.getGerant();
			receptionnaireMagasinGerantTel = destination.getContactGerant();
			receptionnaireMagasinGerantMail = destination.getContactGerant();
			toOk = true;
		}
	}

	List<CoupleDTO> referentialDepartement;

	public String redirectTransferMagasin() {
		// referentialMagsin = commonService.loadReferentielMagasin();
		referentialDepartement = commonService.loadAllDepartement();

		return "gestionDesTransfertsMagToMag.xhtml?idBlocToShow=1";
	}

	Long departementOrdigineId;
	List<MagasinDTO> listPVofSelectedDepartement;

	public String filterpointdeVenteByDepartement() {
		try {
			listPVofSelectedDepartement = commonService.loadReferentielMagasinByDepartement(departementOrdigineId);
		} catch (EntityDBDAOException e) {
			Log.error("Erreur recuperation de la liste des point de vente du departement " + e.getMessage());
		}
		return "gestionDesTransfertsMagToMag.xhtml?idBlocToShow=1";
	}

	public MiseEnplaceDTOPointDeVente getSelectedMiseEnplaceDTOPointDeVente() {
		return selectedMiseEnplaceDTOPointDeVente;
	}

	public void setSelectedMiseEnplaceDTOPointDeVente(MiseEnplaceDTOPointDeVente selectedMiseEnplaceDTOPointDeVente) {
		this.selectedMiseEnplaceDTOPointDeVente = selectedMiseEnplaceDTOPointDeVente;
	}

	public List<CoupleDTO> loadAllProgrammeOuvert() {
		try {
			return commonService.loadAllProgramme(ConstantPGCA.PROG_POUVERT);
		} catch (EntityDBDAOException e) {
			Log.error("Erreur recuperation de la liste des Programmes");
		}
		return null;
	}

	
	
	public List<PointDeCollecteDTO> loadAllPointdeCollecte() {
		return commonService.loadPointdeCollecteDTOs();
	}

	public List<PointDeVenteDTO> loadRefererentielPV() {

		if (listRefPointdeVenteDTO == null)
			return commonService.loadAllPointDeVente(false);

		return listRefPointdeVenteDTO;
	}

	public List<PointDeVenteDTO> getListRefPointdeVenteDTO() {
		return listRefPointdeVenteDTO;
	}

	public void setListRefPointdeVenteDTO(List<PointDeVenteDTO> listRefPointdeVenteDTO) {
		this.listRefPointdeVenteDTO = listRefPointdeVenteDTO;
	}

	public List<CoupleDTO> loadAllRegions() {
		return commonService.loadRegion();
	}

	public List<CamionDTO> getListTransporteur() {
		return listTransporteur;
	}

	public void setListTransporteur(List<CamionDTO> listTransporteur) {
		this.listTransporteur = listTransporteur;
	}

	public List<CoupleDTO> loadAllDepartement() {
		return commonService.loadAllDepartement();
	}

	public List<CoupleDTO> loadAllCommune() {
		return commonService.loadRegion();
	}

	public List<ProduitDTO> getListProduiSelectedMagSource() {
		return listProduiSelectedMagSource;
	}

	public void setListProduiSelectedMagSource(List<ProduitDTO> listProduiSelectedMagSource) {
		this.listProduiSelectedMagSource = listProduiSelectedMagSource;
	}

	public void showDetailsSelectedCommune() {
		InfosCommunesDTO cto = commonService.getDetailsCommune(commandeDTO.getCommuneLibelle());

		if (cto != null) {
			commandeDTO.setDepartementLibelle(cto.getDepartementLibelle());
			commandeDTO.setRegionLibelle(cto.getRegionLibelle());
		}
	}

	public String getListOfCamionFromDBInJSON() {
		return listOfCamionFromDBInJSON;
	}

	public void setListOfCamionFromDBInJSON(String listOfCamionFromDBInJSON) {
		this.listOfCamionFromDBInJSON = listOfCamionFromDBInJSON;
	}

	public String getListOfChauffeurFromDBInJSON() {
		return listOfChauffeurFromDBInJSON;
	}

	public void setListOfChauffeurFromDBInJSON(String listOfChauffeurFromDBInJSON) {
		this.listOfChauffeurFromDBInJSON = listOfChauffeurFromDBInJSON;
	}

	public Long getIdIntrantaTransferer() {
		return idIntrantaTransferer;
	}

	public void setIdIntrantaTransferer(Long idIntrantaTransferer) {
		this.idIntrantaTransferer = idIntrantaTransferer;
	}

	public String getListOfTransporteurFromDBInJSON() {
		return listOfTransporteurFromDBInJSON;
	}

	public void setListOfTransporteurFromDBInJSON(String listOfTransporteurFromDBInJSON) {
		this.listOfTransporteurFromDBInJSON = listOfTransporteurFromDBInJSON;
	}

	public void loadAllOrdresDeLivraion() {
		try {
			allOLs = programmAgricoleService.managerLoadOrdresAvalider();
		} catch (Exception e) {
			Log.error("Erreur de recup des ordres" + e.getMessage());
			e.printStackTrace();
		}
	}

	public List<CoupleDTO> getFiltredIntrant() {
		return filtredIntrant;
	}

	public IndicateursDashboardDTO getIndicateurs() {
		return indicateurs;
	}

	public void setIndicateurs(IndicateursDashboardDTO indicateurs) {
		this.indicateurs = indicateurs;
	}

	public void setFiltredIntrant(List<CoupleDTO> filtredIntrant) {
		this.filtredIntrant = filtredIntrant;
	}

	public String SaveAndPreviewCommande() {

		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (listProduitCommande == null || listProduitCommande.size() < 1) {
			showMessage(FacesMessage.SEVERITY_FATAL, "Il faut au minimum un produit pour votre commande");
			return "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		try {

			PointDeVenteDTO pv = commonService.loadPointDeVenteById(commandeDTO.getIdPointdeVente());
			if (pv != null) {

				commandeDTO.setIdPointdeVente(pv.getIdPv());
				commandeDTO.setPointDeVenteLibelleProvenance(pv.getLibelle());
				commandeDTO.setPointDeVenteGerantProvenance(pv.getGerant());
				commandeDTO.setPointDeVentCommuneProvenance(pv.getCommune());
				commandeDTO.setPointDeVenteDepartementProvenance(pv.getDepartement());
				// commandeDTO.setPointDeVenteTelProvenance(pv.ge);
			}

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			if (request != null) {
				HttpSession session = request.getSession(false);

				// Editeur de la commande
				commandeDTO.setAuteurCommandeidPersonne((Long) session.getAttribute("connectedUserPersonneid"));
				commandeDTO.setEditeurCommandeIdPointDeCollecte((Long) session.getAttribute("idPointdeCollecte"));

			}

		} catch (Exception e) {
			Log.error("Erreur de preview de la commande est surveneue");
			return "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		commandeDTO.setModeDePaiement(modeDePaiement);

		// Complement d'info pour si le mode de maiepent est par credit ou par
		// nature
		if (modeDePaiement.equals(ConstantPGCA.CMD_PAYE_PAR_CREDIT))
			return "gestionDesCommandesConfirmation.xhtml?faces-redirect=true&idBlocToShow=1";
		else if (modeDePaiement.equals(ConstantPGCA.CMD_PAYE_NATURE))
			return "gestionDesCommandesConfirmation.xhtml?faces-redirect=true&idBlocToShow=5";
		else if (modeDePaiement.equals(ConstantPGCA.CMD_PAYE_AU_SIEGE))
			return "gestionDesCommandesConfirmation.xhtml?faces-redirect=true&idBlocToShow=4";

		return saveCommande();
	}

	public String SaveAndPreviewOrdre() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		if (listProduitCommande == null || listProduitCommande.size() < 1) {
			showMessage(FacesMessage.SEVERITY_WARN, "Il faut au minimum un produit pour votre ordre de livraison");
			return "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		try {
			// Long idPointDevente =
			// commonService.loadIPoitDeVenteIdByName(pointdeVenteAutocomplete);
			Long idPointDevente = selectedIdPointDeVente;

			if (idPointDevente == null) {
				showMessage(FacesMessage.SEVERITY_WARN, "Le point de vente <i>" + pointdeVenteAutocomplete
						+ "<i/> n'existe pas dans la plateforme ! Merci de l'ajouter dabord");
				return "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=1";
			}

			PointDeVenteDTO pv = commonService
					.loadPointDeVenteById(/* commandeDTO.getIdPointdeVente() */ idPointDevente);

			if (pv != null) {
				commandeDTO.setIdPointdeVente(pv.getIdPv());
				commandeDTO.setPointDeVenteLibelleProvenance(pv.getLibelle());
				commandeDTO.setPointDeVenteGerantProvenance(pv.getGerant() != null ? pv.getGerant() : "Non renseigné");
				commandeDTO.setPointDeVentCommuneProvenance(pv.getCommune());
				commandeDTO.setPointDeVenteDepartementProvenance(pv.getDepartement());

			}

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			if (request != null) {
				HttpSession session = request.getSession(false);

				// Editeur de la commande
				commandeDTO.setAuteurCommandeidPersonne((Long) session.getAttribute("connectedUserPersonneid"));
				commandeDTO.setEditeurCommandeIdPointDeCollecte((Long) session.getAttribute("idPointdeCollecte"));

			}

		} catch (Exception e) {
			Log.error("Erreur de preview de la commande est surveneue");
			return "gestionDesOL.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		commandeDTO.setModeDePaiement(modeDePaiement);

		if (programmAgricoleService.enregistrerOrdreDeLivraison(commandeDTO, listProduitCommande)) {

			// update inducateurs om
			try {
				indicateurs = programmAgricoleService.loadAllIndicateursDashboard();
			} catch (EntityDBDAOException e) {
				Log.error("update  :erreur de recuperation des ol pour update indicateurs");
			}

			return "gestionDesOL.xhtml?faces-redirect=true&idBlocToShow=3";
		}

		showMessage(FacesMessage.SEVERITY_WARN, "Une erreur est survenue lors de l'édition de la commande ");
		return "gestionDesOL.xhtml?faces-redirect=true&idBlocToShow=1";

	}

	// ajout produit a un ordre avec vrification stock qui doit traité la cmd
	public String ajouterProduitAunOrdre() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (produitAajouterAlaCommande == null || produitAajouterAlaCommande.getQuantiteProduitAvendre() == null
				|| produitAajouterAlaCommande.getQuantiteProduitAvendre() == 0) {

			showMessage(FacesMessage.SEVERITY_ERROR, "Impossible d'editer un ordre sans produit");
			return "Impossible de vendre zero tonne sur un produit";
		}

		Double quantiteProduitAjouter = produitAajouterAlaCommande.getQuantiteProduitAvendre();
		ProduitDTO pdto = null;
		try {
			Log.info("Tentative de recuperation du produit en partir de son id <"
					+ produitAajouterAlaCommande.getIdProduit());
			pdto = programmAgricoleService.loadProduitDTObyIdProduit(produitAajouterAlaCommande.getIdProduit());

			// verification stock distant
			// pdto =
			// programmAgricoleService.loadAProduitFromStockbyIdStockIdProduitAndIdProg(quantiteProduitAjouter,
			// quantiteProduitAjouter, quantiteProduitAjouter);
			if (pdto == null) {
				showMessage(FacesMessage.SEVERITY_ERROR, "Impossible de vendre zero tonne sur un produit");
				return "";
			}

			// Si Il y'a pas assez de stock pour le produit selectionne
			if (pdto.getQuantite() == 0 || pdto.getQuantite() < quantiteProduitAjouter) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Le stock de " + pdto.getLibelleProduit() + " restant est inferieur à la quantité du BL",
						" Stock : " + pdto.getQuantite() + " " + pdto.getUniteDeMesure() + " Quantite BL : "
								+ quantiteProduitAjouter + " ");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "";
			}

		} catch (ProgrammeException | EntityDBDAOException e) {
			Log.error(
					"Erreur Recuperation du produit en partir de son id <" + produitAajouterAlaCommande.getIdProduit());
			e.printStackTrace();
		}
		return "";
	}

	/***** Enregistrement cmd Directement Paiement à ala livraion ***/
	public String saveCommande() {

		if (programmAgricoleService.enregistrerCommnande(commandeDTO, listProduitCommande)) {
			return "gestionDesCommandesConfirmation.xhtml?faces-redirect=true&idBlocToShow=3";
		}

		showMessage(FacesMessage.SEVERITY_ERROR, "Une erreur est survenue lors de l'édition de la commande ");
		return "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	/*****
	 * Enregistrement cmd Avec Paiement Partiel ou total au siége
	 * 
	 * Tips : Verification Paiement Total Sinon verifier si option Paiement le
	 * reste par credit est activé ou pas.
	 * 
	 ***/

	public String SaveCommandeWithCredit() {
		if (commandeDTO == null)
			return "";

		if (getmontantTotalpaye() == getmontantTotalpayeDelaCMD())
			return saveCommande();

		// Verif exces Paiement
		if (getmontantTotalpaye() > getmontantTotalpayeDelaCMD()) {
			String msg = "La somme encaissée " + formatFloatToCFA(getmontantTotalpaye())
					+ " est supérieure  au montant total de la commande  "
					+ formatFloatToCFA(getmontantTotalpayeDelaCMD());

			showMessage(FacesMessage.SEVERITY_WARN, msg);

			return "gestionDesCommandesConfirmation.xhtml?faces-redirect=true&idBlocToShow=4";
		}

		// Verifi option credit coché ou pas
		if (getmontantTotalpaye() < getmontantTotalpayeDelaCMD() && !commandeDTO.isPayerLeResteParCredit()) {
			String msg = "La somme encaissée " + formatFloatToCFA(getmontantTotalpaye())
					+ " est inférieure au montant total de la vente  " + formatFloatToCFA(getmontantTotalpayeDelaCMD())
					+ ",  cocher l'option ' Enregistrer le reste comme crédit' pour que le client contracte un crédit  de  "
					+ formatFloatToCFA(getmontantTotalpayeDelaCMD() - getmontantTotalpaye());

			showMessage(FacesMessage.SEVERITY_WARN, msg);

			return "gestionDesCommandesConfirmation.xhtml?faces-redirect=true&idBlocToShow=4";
		}

		// Option credit coché !
		if (getmontantTotalpaye() < getmontantTotalpayeDelaCMD() && commandeDTO.isPayerLeResteParCredit()) {
			if (programmAgricoleService.enregistrerCommnande(commandeDTO, listProduitCommande)) {

				HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
						.getRequest();
				HttpSession session = request.getSession(false);

				creditLieeAlaCommmande.setMontantInitialCredit(getmontantTotalpayeDelaCMD() - getmontantTotalpaye());
				creditLieeAlaCommmande.setAuteurCreditId((Long) session.getAttribute("connectedUserid"));
				creditLieeAlaCommmande.setAuteurCreditLibelle("Credit contracté par " + commandeDTO.getClientNom()
						+ " pour la commande " + commandeDTO.getReferenceCMD());
				creditLieeAlaCommmande.setDateContraction(new Date() + "");
				creditLieeAlaCommmande.setNomsouscripteur(commandeDTO.getClientNom());
				creditLieeAlaCommmande.setMontantRestantApayer(getmontantTotalpayeDelaCMD() - getmontantTotalpaye());
				creditLieeAlaCommmande.setAdresseSouscripteur(commandeDTO.getClienAdresse());
				creditLieeAlaCommmande.setTelSouscripteur(commandeDTO.getClientTel());

				creditLieeAlaCommmande.setCommandeId(commandeDTO.getIdCommande());

				serviceTresorerie.enregistrerCredit(creditLieeAlaCommmande);

				montantCredit = creditLieeAlaCommmande.getMontantInitialCredit();
				referenceCrdit = creditLieeAlaCommmande.getReferenceCredit();

				// showMessage(FacesMessage.SEVERITY_INFO , "Votre commande est
				// bien enregistrée avec un crédit de " +
				// utilsService.formatFloatToCFA(creditLieeAlaCommmande.getMontantInitialCredit())
				// + ", le numéro de référence de votre commande est " +
				// commandeDTO.getReferenceCMD() + " et le numéro de référence
				// du crédit est " +
				// creditLieeAlaCommmande.getReferenceCredit());
				return "gestionDesCommandesConfirmation.xhtml?faces-redirect=true&idBlocToShow=3&showBlocCreditinsideFacture=1";
			}
			return "gestionDesCommandesConfirmation.xhtml?faces-redirect=true&idBlocToShow=3";
		}
		showMessage(FacesMessage.SEVERITY_ERROR, "Une erreur est survenue lors de l'édition de la commande ");
		return "gestionDesCommandesConfirmation.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	/****** COMMANDE AVEC PAIEMENT NATIURE *********/

	public String SaveCommandeWithPaiementNature() {
		if (commandeDTO == null)
			return "";

		if (programmAgricoleService.enregistrerCommnande(commandeDTO, listProduitCommande)) {

			if (programmAgricoleService.updateQuantiteIntrantByPaiementNature(produitAajouterAlaCommande.getIdProduit(),
					commandeDTO))
				return "gestionDesCommandesConfirmation.xhtml?faces-redirect=true&idBlocToShow=3&showButtonNature=1";

		}
		showMessage(FacesMessage.SEVERITY_ERROR, "Une erreur est survenue .....");

		return "gestionDesCommandesConfirmation.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public String getTransportTotransfert() {
		return transportTotransfert;
	}

	public void setTransportTotransfert(String transportTotransfert) {
		this.transportTotransfert = transportTotransfert;
	}

	public String ajouterProduit() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (produitAajouterAlaCommande.getQuantiteProduitAvendre() == null
				|| produitAajouterAlaCommande.getQuantiteProduitAvendre() == 0) {

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "La quantité doit etre supérieure à zero",
					"");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";

		}

		/*
		 * if (!produitAajouterAlaCommande.isDejaTarifie()) {
		 * 
		 * FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
		 * "Impossible d'enregistrer une commande d'intrant non tarifié !  Merci de tarifier l'intrant dabord"
		 * , ""); FacesContext.getCurrentInstance().addMessage(null, msg);
		 * return ""; }
		 */

		if (produitAajouterAlaCommande == null)
			return "";

		Double quantiteProduitAjouter = produitAajouterAlaCommande.getQuantiteProduitAvendre();

		// TODO : verifier si c'est possible qu'un commande cumule des produits
		// de programme diffrent
		/*
		 * commandeDTO.setLibelleCampagane(
		 * programmAgricoleService.getLibelleCampagneByIdProduit(
		 * produitAajouterAlaCommande.getIdProduit()));
		 * commandeDTO.setLibelleProgramme(
		 * programmAgricoleService.getLibelleProgrammeByIdProduit(
		 * produitAajouterAlaCommande.getIdProduit()));
		 * commandeDTO.setIdProgramme(
		 * programmAgricoleService.getidProgrammebyIdProduit(
		 * produitAajouterAlaCommande.getIdProduit()));
		 */

		// Recuperation des autres informations du produit
		ProduitDTO pdto = null;
		try {
			Log.info("Tentative de recuperation du produit en partir de son id <"
					+ produitAajouterAlaCommande.getIdProduit());
			pdto = programmAgricoleService.loadProduitDTObyIdProduit(produitAajouterAlaCommande.getIdProduit());

			if (pdto == null) {
				showMessage(FacesMessage.SEVERITY_ERROR, "Impossible de vendre zero tonne sur un produit");
				return "";
			}

			// Si Il y'a pas assez de stock pour le produit selectionne
			if (pdto.getQuantite() == 0 || pdto.getQuantite() < quantiteProduitAjouter) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Le stock de " + pdto.getLibelleProduit() + " restant est inferieur à la quantité du BL",
						" Stock : " + pdto.getQuantite() + " " + pdto.getUniteDeMesure() + " Quantite BL : "
								+ quantiteProduitAjouter + " ");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "";

			} else // on mets a jour le stock du produit restant en enlevant
			{
				// TODO : Update stock produit
				pdto.setQuantiteProduitAvendre(quantiteProduitAjouter);
				// TARIF PRODUIT
				Double totalPrix = quantiteProduitAjouter * pdto.getPrixUnitaire();
				Float unitairePrix = pdto.getPrixUnitaire();

				pdto.setPrixTotal(totalPrix);
				pdto.setPrixUnitaire(unitairePrix);

				listProduitCommande.add(pdto);

				commandeDTO
						.setMontantTotaldelaCommande(new Float(commandeDTO.getMontantTotaldelaCommande() + totalPrix));

				// TODO tester tout les produits appartienne au meme programme
				// pour coherence coptabilité
				commandeDTO.setLibelleCampagane(pdto.getLibelleCampagne());
				commandeDTO.setLibelleProgramme(pdto.getLibelleProgramme());
			}

		} catch (ProgrammeException | EntityDBDAOException e) {
			Log.error(
					"Erreur Recuperation du produit en partir de son id <" + produitAajouterAlaCommande.getIdProduit());
			e.printStackTrace();
		}
		return "";
	}

	public float getmontantTotalpaye() {
		if (commandeDTO == null)
			return 0;

		return commandeDTO.getMontantPayeParBonSubvention() + commandeDTO.getMontantPayeParCheque()
				+ commandeDTO.getMontantPayeParEspeces() + commandeDTO.getMontantPayeParNature();
	}

	public float getmontantTotalpayeDelaCMD() {
		if (commandeDTO == null)
			return 0;

		return commandeDTO.getMontantTotaldelaCommande();
	}

	/*************** C O M M A N D E S *****************/

	public void loadCommendesEnvoyesByPointDeCollecte() {

		try {
			Long idPointCollecte = null;
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			if (request != null) {
				HttpSession session = request.getSession(false);
				idPointCollecte = (Long) session.getAttribute("connectedUserStockid");
			}
			listCommandes = programmAgricoleService.getListCommandeEnvoyeByidPointdeCollect(idPointCollecte);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String redirectToListCommande() {
		loadCommendesEnvoyesByPointDeCollecte();
		return "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=5";

	}

	LitigesDTO selectedLitigesDTO;
	LitigesDTO filtredLitigesDTO;

	List<LitigesDTO> listLitigesDTO;

	public String redirectToListLitiges() {
		if (listLitigesDTO == null)
			listLitigesDTO = commonService.loadAllLitiges();

		return "gestionDesLitiges.xhtml?faces-redirect=true&idBlocToShow=1";

	}

	public String getNumeroLVManuel() {
		return numeroLVManuel;
	}

	public String getNumeroBLManuel() {
		return numeroBLManuel;
	}

	public void setNumeroLVManuel(String numeroLVManuel) {
		this.numeroLVManuel = numeroLVManuel;
	}

	public void setNumeroBLManuel(String numeroBLManuel) {
		this.numeroBLManuel = numeroBLManuel;
	}

	public String filterCommande() {

		loadCommendesEnvoyesByPointDeCollecte();

		if (filterIdProgramme > 0) // filtre Programme
		{
			List<CommandeDTO> filtredCommande = new ArrayList<CommandeDTO>();

			for (CommandeDTO c : listCommandes) {
				if (c.getIdProgramme() == filterIdProgramme)
					filtredCommande.add(c);
			}
			listCommandes = filtredCommande;
		}

		if (filterIdpv > 0) // filtre point de vente
		{
			List<CommandeDTO> filtredCommande2 = new ArrayList<CommandeDTO>();
			for (CommandeDTO c : listCommandes) {
				if (filterIdpv > 0 && c.getIdPointdeVente() == filterIdpv)
					filtredCommande2.add(c);
			}
			listCommandes = filtredCommande2;
		}

		if (filterStatus > 0) // Status
		{
			List<CommandeDTO> filtredCommande3 = new ArrayList<CommandeDTO>();
			for (CommandeDTO c : listCommandes) {
				if (c.getStatus() == filterStatus)
					filtredCommande3.add(c);
			}
			listCommandes = filtredCommande3;
		}

		return "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=5";
	}

	public String redirectToListOL() {
		loadCommendesEnvoyesByPointDeCollecte();
		return "agentSaisieReferentielCommandes.xhtml?faces-redirect=true&idBlocToShow=1";

	}

	public String filterOL() {

		loadCommendesEnvoyesByPointDeCollecte();

		if (filterIdProgramme > 0) // filtre Programme
		{
			List<CommandeDTO> filtredCommande = new ArrayList<CommandeDTO>();

			for (CommandeDTO c : listCommandes) {
				if (c.getIdProgramme() == filterIdProgramme)
					filtredCommande.add(c);
			}
			listCommandes = filtredCommande;
		}

		if (filterIdpv > 0) // filtre point de vente
		{
			List<CommandeDTO> filtredCommande2 = new ArrayList<CommandeDTO>();
			for (CommandeDTO c : listCommandes) {
				if (filterIdpv > 0 && c.getIdPointdeVente() == filterIdpv)
					filtredCommande2.add(c);
			}
			listCommandes = filtredCommande2;
		}

		if (filterStatus > 0) // Status
		{
			List<CommandeDTO> filtredCommande3 = new ArrayList<CommandeDTO>();
			for (CommandeDTO c : listCommandes) {
				if (c.getStatus() == filterStatus)
					filtredCommande3.add(c);
			}
			listCommandes = filtredCommande3;
		}
		return "agentSaisieReferentielCommandes.xhtml?faces-redirect=true&idBlocToShow=5";
	}

	Long filtredCat;

	public List<CoupleDTO> getReferentialDepartement() {
		return referentialDepartement;
	}

	public void setReferentialDepartement(List<CoupleDTO> referentialDepartement) {
		this.referentialDepartement = referentialDepartement;
	}

	Long filtredInt;

	public List<CommandeDTO> getAllOLs() {
		return allOLs;
	}

	public void setAllOLs(List<CommandeDTO> allOLs) {
		this.allOLs = allOLs;
	}

	/******* lecture liste des intrant du stock connecté ***/
	public List<IntrantDTO> getlistIntrantDTO() {
		Long connectedUseridStock = 0L;
		try {
			connectedUseridStock = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserStockid");
			listIntrantsOfStock = programmAgricoleService.loadAllIntrantDTOByIdStock(connectedUseridStock);
			loadCacheRepartionStockFournisseur(); /// chart update
			return listIntrantsOfStock;
		} catch (Exception e) {
			Log.info("Erreur de recuperation des intrant du point de collecte " + connectedUseridStock);
			e.printStackTrace();
		}
		return listIntrantsOfStock;
	}

	List<IntrantDTO> listIntrantDTOFromMagasin;

	public List<IntrantDTO> getlistIntrantDTOFromMagasin() {

		try {
			if (listIntrantDTOFromMagasin == null)
				listIntrantDTOFromMagasin = programmAgricoleService.getlistIntrantDTOFromMagasin();
			return listIntrantDTOFromMagasin;
		} catch (Exception e) {
			Log.info("Erreur recupération stock magasin " + e.getMessage());
		}
		return listIntrantDTOFromMagasin;
	}

	private PointDeVenteDTO selectedPv;
	private List<ProduitDTO> listProduitslieeSelectedVente;

	List<PointDeVenteDTO> listPointDeVentesFiltred;

	public List<PointDeVenteDTO> loadAllPointDeVentes() {
		return commonService.loadAllPointDeVente(false);
	}

	public List<ProduitDTO> loadContenueSelectedMagasin() {
		try {
			// if(listProduitslieeSelectedVente != null)
			// return listProduitslieeSelectedVente;

			if (selectedPv != null) {
				PointDeVenteDTO pv = commonService.loadPointDeVenteById(selectedPv.getIdPv());
				if (pv != null)
					listProduitslieeSelectedVente = commonService
							.loadStockProduitByIdPointdeVenteMagasinier(pv.getIdstockReference());
			}

		} catch (Exception e) {
			Log.info("Erreur recupération stock magasin " + e.getMessage());
		}
		return listProduitslieeSelectedVente;
	}

	/******* lecture liste des intrant du stock connecté ***/
	public List<IntrantDTO> getlistIntrantDTOLazyMode() {

		if (listIntrantsOfStock != null)
			return listIntrantsOfStock;

		Long connectedUseridStock = 0L;
		try {
			connectedUseridStock = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserStockid");
			listIntrantsOfStock = programmAgricoleService.loadAllIntrantDTOByIdStock(connectedUseridStock);
			loadCacheRepartionStockFournisseur(); /// chart update
			return listIntrantsOfStock;
		} catch (Exception e) {
			Log.info("Erreur de recuperation des intrant du point de collecte " + connectedUseridStock);
			e.printStackTrace();
		}
		return listIntrantsOfStock;
	}

	public String initCreationIntrant() {
		getlistIntrantDTO();
		return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public void filterIntantforAG() {
		getlistIntrantDTO();
	}

	public String loadDetailsSelectedCMD() {

		if (commandeDTO == null || commandeDTO.getIdCommande() == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner une commande");
			return "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=5b";
		}

		try {
			// recuperation des produits de la commande selectionne car les
			// produits sont en LAZY lAZY LOADING
			commandeDTO = programmAgricoleService.loadCommandeInfosByIdCMD(commandeDTO.getIdCommande());

			return "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=5b";
		} catch (Exception e) {
			Log.error("Erreur survenue lors de la lecture des details de la commande " + e.getMessage());
			e.printStackTrace();
		}

		return "";
	}

	public String loadDetailsSelectedOrdre() {

		if (commandeDTO == null || commandeDTO.getIdCommande() == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner un ordre de livraison");
			return "gestionDesOL.xhtml?faces-redirect=true&idBlocToShow=5b";
		}
		try {
			commandeDTO = programmAgricoleService.loadOrdreInfosById(commandeDTO.getIdCommande());
			return "gestionDesOL.xhtml?faces-redirect=true&idBlocToShow=5b";
		} catch (Exception e) {
			Log.error("Erreur survenue lors de la lecture des details de l'ordre " + e.getMessage());
			e.printStackTrace();
		}

		showMessage(FacesMessage.SEVERITY_WARN, "Impossible de lire les détails de l'ordre");

		return "gestionDesOL.xhtml?faces-redirect=true&idBlocToShow=5b";
	}

	/*************** END C O M M A N D E S *****************/

	/*************** O R D R E S D E LIVRAISON *****************/

	public List<CommandeDTO> loadOrdresEnvoyesByPointDeCollecte() {

		try {
			Long idPointCollecte = null;
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			if (request != null) {
				HttpSession session = request.getSession(false);
				idPointCollecte = (Long) session.getAttribute("connectedUserStockid");
			}
			return programmAgricoleService.getListOrdreEnvoyeByidPointdeCollect(idPointCollecte);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// public String loadDetailsSelectedOrdres() {
	//
	// if(commandeDTO == null || commandeDTO.getIdCommande() == null)
	// {
	// showMessage(FacesMessage.SEVERITY_WARN, "Selectionner une commande");
	// return "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=5b";
	// }
	//
	// try {
	// // recuperation des produits de la commande selectionne car les produits
	// sont en LAZY lAZY LOADING
	// commandeDTO =
	// programmAgricoleService.loadCommandeInfosByIdCMD(commandeDTO.getIdCommande());
	//
	// return "gestionDesCommandes.xhtml?faces-redirect=true&idBlocToShow=5b";
	// } catch (Exception e) {
	// Log.error("Erreur survenue lors de la lecture des details de la commande
	// " + e.getMessage());
	// e.printStackTrace();
	// }
	//
	// return "";
	// }
	//

	/*************** END C O M M A N D E S *****************/

	public List<ProduitDTO> loadProduitDisponiblebyIdPointdeVente() {

		try {
			Long connectedUserStockid = null;
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			if (request != null) {
				HttpSession session = request.getSession(false);
				connectedUserStockid = (Long) session.getAttribute("connectedUserStockid");
			}
			return commonService.loadStockProduitByIdPointdeVenteMagasinier(connectedUserStockid);
		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// For OL POP UP
	Long selectedIdPointDeVente;
	List<ProduitDTO> listeIntrantSelectedPv;

	public List<ProduitDTO> loadProduitDisponiblebySelectedIdPointDeVente() {

		try {
			listeIntrantSelectedPv = commonService.loadStockProduitByIdPointdeVenteMagasinier(selectedIdPointDeVente);

			return listeIntrantSelectedPv;
		} catch (EntityDBDAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ProduitDTO> loadProduitDisponibleForOL() {
		try {

			return commonService.loadStockProduitByIdPointdeVenteMagasinier(commandeDTO.getIdPointdeVente());
		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/***********************************************
	 * ************ INTRANT : APPRO FROM FOURNISSEUR (Appro d'un intrant dans un
	 * stock)
	 **********************************************/

	private IntrantDTO selectedIntrantDTO = new IntrantDTO();
	Long idTypeProduit;
	Long idProduit;
	Long idFournisseur;
	Double quantite;

	// Auto complete
	String libelleFournisseur, libelleIntrant = "";

	public String createIntrantForCampagne() {
		selectedIntrantDTO = new IntrantDTO();
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (quantite.longValue() < 0) {
			commonService.showMessage(FacesMessage.SEVERITY_ERROR, "La quantité est invalide");
			return "gestionDesProduitsCreation.xhtml?faces-redirect=true";
		}
		Long connectedUserPersonne;
		try {

			Long four = commonService.loadFournisseuridByName(libelleFournisseur);
			if (four == null) {
				showMessage(FacesMessage.SEVERITY_WARN, "Le fournisseur " + libelleFournisseur
						+ " n'existe pas dans la plateforme , merci de le créer dabord ");
				return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=1";

			}

			Long idIntrant = commonService.loadIntrantIdByName(libelleIntrant);
			if (idIntrant == null) {
				showMessage(FacesMessage.SEVERITY_WARN,
						"L'intrant " + libelleIntrant + " n'existe pas dans la campgne actuelle ");
				return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=1";

			}

			connectedUserPersonne = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserPersonneid");
			Long stockId = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserStockid");
			selectedIntrantDTO.setIdStock(stockId);
			selectedIntrantDTO.setIdtypeProduit(idTypeProduit);
			selectedIntrantDTO.setIdProduit(idProduit);
			selectedIntrantDTO.setIdFournisseur(idFournisseur);
			selectedIntrantDTO.setQuantite(quantite);
			selectedIntrantDTO.setLibelleFournisseur(libelleFournisseur.trim());
			selectedIntrantDTO.setLibelleProduit(libelleIntrant.trim());

			selectedIntrantDTO.setIdAuteurTarication(connectedUserPersonne);
			if (programmAgricoleService.addIntrantCampagne(selectedIntrantDTO)) {

				if (selectedIntrantDTO.isUpdateOrNot()) // création en mode
														// update
				{
					// création from scratch
					commonService.showMessage(FacesMessage.SEVERITY_INFO,
							"L'intrant " + " existe déjà auprès du fournisseur "
									+ selectedIntrantDTO.getInfosDTOLibelleFournisseur()
									+ ",  la quantité est mise à jour.");
				} else {
					// création from scratch
					commonService.showMessage(FacesMessage.SEVERITY_INFO,
							"L'intrant  " + selectedIntrantDTO.getLibelleProduit()
									+ " est enregistré avec succès   auprès du fournisseur "
									+ selectedIntrantDTO.getInfosDTOLibelleFournisseur());
				}
				getlistIntrantDTO();
				libelleFournisseur = "";
				libelleIntrant = "";
				quantite = 0.0;
				return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=1";
			}

		} catch (Exception e) {
			commonService.showMessage(FacesMessage.SEVERITY_ERROR,
					"Une erreur est survenue lors de creation des intrants");
			Log.error("Une erreur est survenue lors de creation des intrants");
		}
		return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public String collecteIntrantFromPointDevente() {
		selectedIntrantDTO = new IntrantDTO();
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (quantite.longValue() < 0) {
			commonService.showMessage(FacesMessage.SEVERITY_ERROR, "La quantité est invalide");
			return "ACInventaire.xhtml?faces-redirect=true";
		}
		Long connectedUserPersonne;
		try {

			Long idIntrant = commonService.loadIntrantIdByName(libelleIntrant);
			if (idIntrant == null) {
				showMessage(FacesMessage.SEVERITY_WARN, "L'intrant " + libelleIntrant
						+ " n'est pas connu dans la base SEDAB, merci de demander à l'agent de saisie de créer d'abord");
				return "ACInventaire.xhtml?faces-redirect=true&idBlocToShow=1";

			}

			connectedUserPersonne = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserPersonneid");
			Long stockId = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserStockid");
			selectedIntrantDTO.setIdStock(stockId);
			// selectedIntrantDTO.setIdtypeProduit(idTypeProduit);
			// selectedIntrantDTO.setIdProduit(idProduit);
			// selectedIntrantDTO.setIdFournisseur(idFournisseur);
			selectedIntrantDTO.setQuantite(quantite);
			// selectedIntrantDTO.setLibelleFournisseur(libelleFournisseur.trim());
			selectedIntrantDTO.setLibelleProduit(libelleIntrant.trim());
			selectedIntrantDTO.setPrixAcquisition(prixUnitaire);

			selectedIntrantDTO.setIdAuteurTarication(connectedUserPersonne);
			if (programmAgricoleService.addIntrantCampagneCollecte(selectedIntrantDTO)) {

				if (selectedIntrantDTO.isUpdateOrNot()) // création en mode
														// update
				{
					// création from scratch
					commonService.showMessage(FacesMessage.SEVERITY_INFO,
							"L'intrant " + " existe déjà auprès dans votre stock "
									+ selectedIntrantDTO.getInfosDTOLibelleFournisseur()
									+ ",  sa quantité est mise à jour.");
				} else {
					// création from scratch
					commonService.showMessage(FacesMessage.SEVERITY_INFO, "L'intrant  "
							+ selectedIntrantDTO.getLibelleProduit()
							+ " est enregistré avec succès dans votre stock, il sera pris en compte pour les collectes des intrants disponibles");
				}
				getlistIntrantDTO();
				libelleFournisseur = "";
				libelleIntrant = "";
				quantite = 0.0;
				return "dashboardMagasinier.xhtml?faces-redirect=true&idBlocToShow=1";
			}

		} catch (Exception e) {
			commonService.showMessage(FacesMessage.SEVERITY_ERROR,
					"Une erreur est survenue lors de creation des intrants");
			Log.error("Une erreur est survenue lors de creation des intrants");
		}
		return "dashboardMagasinier.xhtml?faces-redirect=true&idBlocToShow=1";
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

	/****** REDIRECT INIT ND REDIT£ ****/

	public String redirectToOLEdition() {

		commandeDTO = new CommandeDTO();
		listProduitCommande = new ArrayList<ProduitDTO>();

		return "gestionDesOL.xhtml?idBlocToShow=1";
	}

	public LitigesDTO getFiltredLitigesDTO() {
		return filtredLitigesDTO;
	}

	public void setFiltredLitigesDTO(LitigesDTO filtredLitigesDTO) {
		this.filtredLitigesDTO = filtredLitigesDTO;
	}

	public LitigesDTO getSelectedLitigesDTO() {
		return selectedLitigesDTO;
	}

	public List<LitigesDTO> getListLitigesDTO() {
		return listLitigesDTO;
	}

	public Long getIdIntrantAmettreEnPlace() {
		return idIntrantAmettreEnPlace;
	}

	public void setSelectedLitigesDTO(LitigesDTO selectedLitigesDTO) {
		this.selectedLitigesDTO = selectedLitigesDTO;
	}

	public void setListLitigesDTO(List<LitigesDTO> listLitigesDTO) {
		this.listLitigesDTO = listLitigesDTO;
	}

	public void setIdIntrantAmettreEnPlace(Long idIntrantAmettreEnPlace) {
		this.idIntrantAmettreEnPlace = idIntrantAmettreEnPlace;
	}

	public String redirectToBLEdition() {
		// commandeDTO = new CommandeDTO();
		// listProduitCommande = new ArrayList<ProduitDTO>();

		return "gestionDesBL.xhtml?idBlocToShow=1";
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

	/****** TARIFICATION DUN PRODUIT ******/

	Float isSubventionned = 0f;
	Float prixSubventionne = 0f;
	Float prixNonSubventionne = 0f;
	Float montantArecouvrir = 0f;
	Float prixUnitaire = 0f;
	Float tauxSubvention = 0f;
	private int modeBl; // mode bl ajax affichage

	public String initTarification() {
		if (selectedIntrantDTO == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Veuillez selectionner un intrant à tarifier ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=4";
		} else {
			prixNonSubventionne = selectedIntrantDTO.getPrixNonSubventionne();
			prixSubventionne = selectedIntrantDTO.getPrixProducteur();
			tauxSubvention = selectedIntrantDTO.getTauxSubvention();

			if (selectedIntrantDTO.getTauxSubvention() > 0)
				selectedIntrantDTO.setSubventionne("1");
			else
				selectedIntrantDTO.setSubventionne("2");

			return "gestionDesProduitsTarification.xhtml?faces-redirect=true&idBlocToShow=1";
		}
	}

	public String tarifierProduit() {

		boolean subventionne = false;
		if (selectedIntrantDTO == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Aucun intrant a tarifier");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "#";
		}

		if (prixNonSubventionne == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Le prix Unitaire est invalide", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "#";
		}

		if (selectedIntrantDTO.getSubventionne().equals("1")) {
			subventionne = true;

			if (tauxSubvention == 0) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
						"Le taux de subvention est invalide (Min 1%)");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "#";
			}
			if (tauxSubvention > 100) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
						"Le taux de subvention est invalide (Max 100%)");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "#";
			}
		}

		try {
			if (serviceTresorerie.tarifierProduit(subventionne, tauxSubvention, prixNonSubventionne,
					selectedIntrantDTO.getIdProduit())) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
						"Tarification effectuée avec succès !");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				getlistIntrantDTO();
				return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=1";

			}
		} catch (Exception e) {

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Une erreur est survenenue lors de la tarification !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			Log.error("Une erreur est surveneue de la tarification de l'intrant" + e.getMessage());
		}
		return "#";

	}

	public PointDeVenteDTO getListMagasinSelectedItem() {
		return listMagasinSelectedItem;
	}

	public void setListMagasinSelectedItem(PointDeVenteDTO listMagasinSelectedItem) {
		this.listMagasinSelectedItem = listMagasinSelectedItem;
	}

	/********* FIN TARIFICATION *****************/

	public void initInfosTarification() {
		if (selectedIntrantDTO == null || !selectedIntrantDTO.getSubventionne().equals("1")) {
			montantArecouvrir = 0.0f;
			tauxSubvention = 0.0f;
			prixSubventionne = prixNonSubventionne;
			return;
		}

		prixSubventionne = prixNonSubventionne - (prixNonSubventionne * tauxSubvention) / 100;
		montantArecouvrir = prixNonSubventionne - prixSubventionne;
	}

	/***** Mise a jour intrant d'un stock ***/
	public String updateIntranOfStock() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		try {
			if (programmAgricoleService.updateIntranOfStock(selectedIntrantDTO)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "La modification de l'intrant "
						+ selectedIntrantDTO.getLibelleProduit() + " est effectuée avec succès");
				FacesContext.getCurrentInstance().addMessage(null, msg);

				getlistIntrantDTO();
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

	public void setQuantite(Double quantite) {
		this.quantite = quantite;
	}

	public String redirectToCMDEdition() {

		commandeDTO = new CommandeDTO();
		listProduitCommande = new ArrayList<ProduitDTO>();

		return "gestionDesCommandes.xhtml?idBlocToShow=1";
	}

	public List<BonDeLivraisonDTO> callServiceRecuperationBLEnvoye() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			Long connectedUseridStock = (Long) session.getAttribute("connectedUserStockid");

			try {
				return programmAgricoleService.loadBondeLivraisonByIdPointDeVente(connectedUseridStock);
			} catch (EntityDBDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public int getStockResiduelByIntrantSize() {
		return stockResiduelByIntrantSize;
	}

	public void setStockResiduelByIntrantSize(int stockResiduelByIntrantSize) {
		this.stockResiduelByIntrantSize = stockResiduelByIntrantSize;
	}

	public String loadListCommunes() {
		try {
			return commonService.loadAllCommunAndDepartement();
		} catch (EntityDBDAOException e) {
			Log.error("Uen erreur est survenue pendant la lecture des communes");
		}
		return null;
	}

	String loadListFournisseurs;

	public String loadListFournisseursforAutoCoplete() {
		/*
		 * on ne charge les refs que les agents de saisies et de collecte t si
		 * le ref n'a pas ete mis a jour par une création =
		 * refFournisseurUpdatedByOtherComponent
		 */

		if (connectedUserProfilID == 4 || connectedUserProfilID == 3 || connectedUserProfilID == 2) {
			try {
				/*
				 * si le nb founisseur a changé en base on reloa les founisseur
				 * de l'auto Complet
				 */
				Integer loadListFournisseursSize = commonService.loadAllFornisseursForAutoCompleteSize();

				if (loadListFournisseurs == null || loadListFournisseurs.trim().equals("")
						|| loadListFournisseurs.split("!").length != loadListFournisseursSize) {
					loadListFournisseurs = commonService.loadAllFornisseursForAutoComplete();
					return loadListFournisseurs;
				}
			} catch (EntityDBDAOException e) {
				Log.error("Uen erreur est survenue pendant la lecture des communes");
			}
			return loadListFournisseurs;
		} else
			return "";

	}

	String loadAllIntrantforAutoCoplete;

	public String loadAllIntrantforAutoCoplete() {

		/*
		 * on ne charge les refs que les agents de saisies et de collecte et si
		 * le ref n'a pas ete mis a jour par une création
		 */

		if (connectedUserProfilID == 4 || connectedUserProfilID == 3 || connectedUserProfilID == 2
				|| connectedUserProfilID == 5) {
			try {
				if (loadAllIntrantforAutoCoplete == null || loadAllIntrantforAutoCoplete.trim().equals("")) {
					loadAllIntrantforAutoCoplete = commonService.loadAllIntrantforAutoCoplete();
					return loadAllIntrantforAutoCoplete;
				}
			} catch (EntityDBDAOException e) {
				Log.error("Uen erreur est survenue pendant la lecture des communes");
			}
			return loadAllIntrantforAutoCoplete;
		} else
			return "";

	}

	String loadAllProgrammeforAutoCoplete;

	public String loadAllProgrammeforAutoCoplete() {
		/* on ne charge les refs que les agents de saisies et de collecte */
		if (connectedUserProfilID == 4 || connectedUserProfilID == 3 || connectedUserProfilID == 2) {
			try {
				if (loadAllProgrammeforAutoCoplete == null || loadAllProgrammeforAutoCoplete.trim().equals("")) {
					loadAllProgrammeforAutoCoplete = commonService.loadAllProgrammeforAutoCoplete();
					return loadAllProgrammeforAutoCoplete;
				}
			} catch (EntityDBDAOException e) {
				Log.error("Uen erreur est survenue pendant la lecture des communes");
			}
			return loadAllProgrammeforAutoCoplete;
		}
		return "";
	}

	String loadAllDepartementAutoCoplete;

	public String loadAllDepartementAutoCoplete() {
		/* on ne charge les refs que les agents de saisies et de collecte */
		if (connectedUserProfilID == 4 || connectedUserProfilID == 3 || connectedUserProfilID == 2) {
			try {
				if (loadAllDepartementAutoCoplete == null || loadAllDepartementAutoCoplete.trim().equals("")) {
					loadAllDepartementAutoCoplete = commonService.loadAllDepartementAutoCoplete();
					return loadAllDepartementAutoCoplete;
				}
			} catch (EntityDBDAOException e) {
				Log.error("Uen erreur est survenue pendant la lecture des communes");
			}
			return loadAllDepartementAutoCoplete;
		}
		return "";
	}

	String loadAllTransporteurforAutoCoplete;

	public String loadAllTransporteurforAutoCoplete() {
		/* on ne charge les refs que les agents de saisies et de collecte */
		if (connectedUserProfilID == 4 || connectedUserProfilID == 3 || connectedUserProfilID == 2) {
			try {
				if (loadAllTransporteurforAutoCoplete == null || loadAllTransporteurforAutoCoplete.trim().equals("")) {
					loadAllTransporteurforAutoCoplete = commonService.loadAllTransporteurforAutoCoplete();
					return loadAllTransporteurforAutoCoplete;
				}
			} catch (EntityDBDAOException e) {
				Log.error("Uen erreur est survenue pendant la lecture des communes");
			}
			return loadAllTransporteurforAutoCoplete;
		}
		return "";
	}

	public String redirectToListBon() {

		try {
			listselectedBLDTO = programmAgricoleService.loadBondeLivraisonByIdPointDeVente(0L);
		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "agentSaisieReferentielBL.xhtml?faces-redirect=true&idBlocToShow=1";

	}

	/********** JOURNAL ETAGGREGATION **********/
	List<MiseEnplaceDTOPointDeVente> listeAggregationMiseenPlaceParIntrant;
	MiseEnplaceDTOPointDeVente listeAggregationMiseenPlaceParIntrantSelected;

	public String redirectToJournalMiseEnPlace() {
		if (listeAggregationMiseenPlaceParIntrant == null || listeAggregationMiseenPlaceParIntrant.size() == 0)
			listeAggregationMiseenPlaceParIntrant = listeAggregationMiseenPlaceParIntrant();

		return "journalMiseEnPlaceParZone.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public List<MiseEnplaceDTOPointDeVente> listeAggregationMiseenPlaceParIntrant() {
		try {
			return programmAgricoleService.listeAggregationMiseenPlaceParIntrant();
		} catch (EntityDBDAOException e) {
			Log.error("Erreur lors de l'agregation des Quota  par intrant");
		}
		return listPVOfCommune;
	}

	public void listeMiseenPlaceParIntrant() {
		try {
			if (listeAggregationMiseenPlaceParIntrantSelected == null)
				return;

			listedesMiseEnplacedejaEffectue = programmAgricoleService.loadAllMiseEnPlaceEffecByTypeIntrant(
					listeAggregationMiseenPlaceParIntrantSelected.getIdIntrantAMettreEnplace());

		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/************ EN JOURNAL **************/

	private List<PointDeVenteDTO> listDesPointsDeVentes;
	private List<PointDeVenteDTO> listDesPointsDeVentesFiltred;

	public String redirectToListPointdeVente() {

		if (listDesPointsDeVentes == null || listDesPointsDeVentes.size() == 0)
			try {
				listDesPointsDeVentes = programmAgricoleService.loadAllPointdeVenteNational();
			} catch (EntityDBDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return "agentSaisieReferentielPointdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	private List<BonDeLivraisonDTO> listselectedBLDTO;

	private BonDeLivraisonDTO selectedBLDTO;
	boolean showDetailsSelectedBl;

	public List<IntrantDTO> getFiltredlistIntrantsOfStock() {
		return filtredlistIntrantsOfStock;
	}

	public void setFiltredlistIntrantsOfStock(List<IntrantDTO> filtredlistIntrantsOfStock) {
		this.filtredlistIntrantsOfStock = filtredlistIntrantsOfStock;
	}

	public String initTraitementBL() {

		if (selectedBLDTO == null || selectedBLDTO.getId() == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Aucun OL selectionné");
			return "agentSaisieReferentielBL.xhtml?faces-redirect=true";
		}

		try {
			selectedBLDTO = programmAgricoleService.getAllProduitsFromIdBL(selectedBLDTO.getId());
		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "agentSaisieReferentielBL.xhtml?faces-redirect=true&idBlocToShow=2";

	}

	public void getDetailsOfSelectedBL() throws EntityDBDAOException {
		selectedBLDTO = programmAgricoleService.getAllProduitsFromIdBL(selectedBLDTO.getId());

		if (selectedBLDTO != null)
			showDetailsSelectedBl = true;
	}

	/*************** M I S E S EN P L A C E *****************/

	MiseEnplaceDTOCommune planMiseEnPlace;

	public Long getIdFournisseur() {
		return idFournisseur;
	}

	public void setIdFournisseur(Long idFournisseur) {
		this.idFournisseur = idFournisseur;
	}

	public Long getIdTypeProduit() {
		return idTypeProduit;
	}

	public void setIdTypeProduit(Long idTypeProduit) {
		this.idTypeProduit = idTypeProduit;
	}

	public Long getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(Long idProduit) {
		this.idProduit = idProduit;
	}

	int nbPointeDeVenteDelaCommune;
	int totalReliquatCommune;
	int totalCumulCommune;
	int totalMiseEnPlaceAffectuer;
	ArrayList<MiseEnplaceDTOPointDeVente> listPVOfCommune;
	String nomCommune; // slected Commune

	public List<MiseEnplaceDTOPointDeVente> getListeAggregationMiseenPlaceParIntrant() {
		return listeAggregationMiseenPlaceParIntrant;
	}

	public void setListeAggregationMiseenPlaceParIntrant(
			List<MiseEnplaceDTOPointDeVente> listeAggregationMiseenPlaceParIntrant) {
		this.listeAggregationMiseenPlaceParIntrant = listeAggregationMiseenPlaceParIntrant;
	}

	public MiseEnplaceDTOPointDeVente getListeAggregationMiseenPlaceParIntrantSelected() {
		return listeAggregationMiseenPlaceParIntrantSelected;
	}

	public void setListeAggregationMiseenPlaceParIntrantSelected(
			MiseEnplaceDTOPointDeVente listeAggregationMiseenPlaceParIntrantSelected) {
		this.listeAggregationMiseenPlaceParIntrantSelected = listeAggregationMiseenPlaceParIntrantSelected;
	}

	public Long getIdintrantFromAutoComplete() {
		return idintrantFromAutoComplete;
	}

	public void setIdintrantFromAutoComplete(Long idintrantFromAutoComplete) {
		this.idintrantFromAutoComplete = idintrantFromAutoComplete;
	}

	String nomCommuneClass; // Class message affichant aucun point de vente ou
							// infos point de vente

	/* Lecture des points vente d'une commune ****/
	public void loadListPVByCommuneId() {
		planMiseEnPlace = new MiseEnplaceDTOCommune();
		listPVOfCommune = new ArrayList<>();
		nbPointeDeVenteDelaCommune = 0;

		String[] tmp = nomCommune.split("-");

		if (tmp.length > 0) {

			try {
				Long idPV = Long.parseLong(tmp[0]);
				List<PointDeVenteDTO> listPv = commonService.loadAllPointDeVenteByCommuneId(idPV);
				planMiseEnPlace.setNbPointDeVente(
						listPv != null ? listPv.size() + "Point (s) trouvé (s)" : "Aucun Point de vente ");

				for (PointDeVenteDTO pv : listPv) {
					MiseEnplaceDTOPointDeVente mp = new MiseEnplaceDTOPointDeVente();

					mp.setIdPointDeVente(pv.getIdPv());
					mp.setNomPointDeVente(pv.getLibelle());
					mp.setIdGerant(pv.getIdGerant());
					mp.setNomGerant(pv.getGerant());

					int cumul = 0;
					// Lecture des mises en place dune commune
					List<MiseEnPlaceEffectuerDTO> listMisenPlaceEffectue = programmAgricoleService
							.loadAllMiseEnPlaceEffectueeByidMAP(mp.getIdMiseEnPlace());

					for (MiseEnPlaceEffectuerDTO item : listMisenPlaceEffectue)
						cumul += item.getQuantiteAmettreEnplace();

					mp.setCumulIntrantAMettreEnplace(cumul);
					mp.setQuantiteIntrantAMettreEnplace(mp.getQuotaIntrantAMettreEnplace() - cumul);
					mp.setQuotaIntrantAMettreEnplace(mp.getQuotaIntrantAMettreEnplace());

					listPVOfCommune.add(mp);
					nbPointeDeVenteDelaCommune++;
				}

				if (listPVOfCommune.size() > 0) {
					nomCommune = ConstantPGCA.CSS_MISEENPLACE_INFOS_POINT_DE_VENTE_MSG + tmp[1];
					nomCommuneClass = ConstantPGCA.CSS_MISEENPLACE_INFOS_POINT_DE_VENTE;
				} else {
					nomCommune = ConstantPGCA.CSS_MISEENPLACE_AUCUN_INFOS_POINT_DE_VENTE_MSG + tmp[1];
					nomCommuneClass = ConstantPGCA.CSS_MISEENPLACE_AUCUN_INFOS_POINT_DE_VENTE;
				}
			} catch (EntityDBDAOException e) {
				// TODO Auto-generated catch block
				Log.error("Erreur surveneur lors de la recuperation des point de vente dans la commune ayant l'id  < "
						+ nomCommune);
			}
		}
	}

	public Double getQuantite() {
		return quantite;
	}

	/******
	 * Enregistrement Plan Mise en Place : AU debut des campagnes apres
	 * reception des commandes les agents saissisent le
	 ***/
	String mPcategorieIntran;
	Long mPpointDeVente;
	Long mPintrant;
	String communeCertifie; // Point de vente certifie par letat

	String quantiteAmettreEnPlace;
	String mPprogramme;
	String dateMiseEnPlaceString;

	String programmeFromAutoComplete, intrantFromAutoComplete, pointdeVenteAutocomplete = "";
	Long idintrantFromAutoComplete;

	public String saveAndPreviewPlanMiseEnPlace() {

		Date dateMiseEnPlace = new Date();
		MiseEnplaceDTOPointDeVente pv = new MiseEnplaceDTOPointDeVente();

		if (programmeFromAutoComplete.trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le Programme de mise est obligatoire ");
			return "#";
		}
		if (intrantFromAutoComplete.trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "L'intrant à mettre en place est obligatoire ");
			return "#";
		}
		if (pointdeVenteAutocomplete.trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le point de vente est obligatoire");
			return "#";
		}
		if (quantiteAmettreEnPlace == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "La quantité d'intrant à mettre en place est obligatoire");
			return "#";
		}

		try {
			Long idProgramme = commonService.loadIProgrammeIdByName(programmeFromAutoComplete);
			if (idProgramme == null) {
				showMessage(FacesMessage.SEVERITY_WARN,
						"Le Programme que vous avez saisie n'existe pas dans la campgne actuelle ");
				return "#";
			}

			Long idIntrant = commonService.loadIntrantIdByName(intrantFromAutoComplete);
			if (idIntrant == null) {
				showMessage(FacesMessage.SEVERITY_WARN,
						"L'intrant <i>" + intrantFromAutoComplete + "<i/> n'existe pas dans la campgne actuelle ");
				return "#";
			}
			Long idPointDevente = commonService.loadIPoitDeVenteIdByName(pointdeVenteAutocomplete);
			if (idPointDevente == null) {
				showMessage(FacesMessage.SEVERITY_WARN,
						"Le point de vente <i>" + pointdeVenteAutocomplete + "<i/> n'existe pas dans la plateforme");
				return "#";
			}
			pv.setIdIntrantAMettreEnplace(idIntrant);
			pv.setIdPointDeVente(idPointDevente);
			// pv.setCommuneCertifie(communeCertifie);
			pv.setCumulIntrantAMettreEnplace(0);
			double q = Double.parseDouble(quantiteAmettreEnPlace.replace(",", "."));
			if (0D == q) {
				showMessage(FacesMessage.SEVERITY_WARN, "La quantité es invalide ");
				return "#";
			}
			pv.setQuotaIntrantAMettreEnplace(q);
			if (programmAgricoleService.saveMiseEnplace(idProgramme, idIntrant, dateMiseEnPlace, pv)) {
				listQuotaMiseEnplace = programmAgricoleService.loadAllMiseEnPlaceOfCampagne(selectedProgramme);
				if (pv.isCreateOrUpdate()) {
					showMessage(FacesMessage.SEVERITY_INFO,
							"Le  quota de mise en place de  " + quantiteAmettreEnPlace
									+ " (t) est enregistrè dans la commune de " + pointdeVenteAutocomplete
									+ " pour le programmme  " + programmeFromAutoComplete);
				} else {
					showMessage(FacesMessage.SEVERITY_WARN, pv.getMsgRetour());
				}
				// Form reset manually
				programmeFromAutoComplete = "";
				intrantFromAutoComplete = "";
				pointdeVenteAutocomplete = "";
				executionMiseEnPlace = new MiseEnplaceDTOPointDeVente();
				idintrantFromAutoComplete = 0L;
				quantiteAmettreEnPlace = "";
				// update journal
				listeAggregationMiseenPlaceParIntrant = listeAggregationMiseenPlaceParIntrant();
				// update AGressagtion (PAge MISE EN PLACE)
				listAgregationMiseEnPlace = commonService.loadAggregationMiseEnPlace();
				return "gestionDesQuotasdeMEP.xhtml?faces-redirect=true";
			} else {
				showMessage(FacesMessage.SEVERITY_ERROR, "Une erreur est survenenue lors de l'ajout du quota. ");
				return "gestionDesQuotasdeMEP.xhtml?faces-redirect=true";
			}
		} catch (Exception e) {
			Log.error("Une erreur est survenue lors de l'enregistrement " + e.getMessage());
		}
		showMessage(FacesMessage.SEVERITY_WARN, "Erreur technique survenue pendant l'enregistrement ! ");
		return "gestionDesQuotasdeMEP.xhtml?faces-redirect=true";
	}

	public String deleteMiseEnplace() {
		try {
			if (selectedlistQuotaMiseEnplace != null)
				programmAgricoleService.deleteMiseEnplace(selectedlistQuotaMiseEnplace.getIdMiseEnPlace());
			showMessage(FacesMessage.SEVERITY_INFO, "Quota supprimé avec succès ! ");
			return "gestionDesQuotasdeMEP.xhtml?faces-redirect=true";
		} catch (EntityDBDAOException e) {
			Log.error(ConstantPGCA.MSG_GENERICS_ERROR + "  suppression du quota de mise en place"
					+ selectedlistQuotaMiseEnplace.getIdMiseEnPlace());
		}
		showMessage(FacesMessage.SEVERITY_WARN, "Erreur technique survenue pendant la suppression du quota !");
		return "gestionDesQuotasdeMEP.xhtml?faces-redirect=true";
	}

	public List<CommandeDTO> getFiltredallOLs() {
		return filtredallOLs;
	}

	public void setFiltredallOLs(List<CommandeDTO> filtredallOLs) {
		this.filtredallOLs = filtredallOLs;
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

	public Float getPrixNonSubventionne() {
		return prixNonSubventionne;
	}

	public void setPrixNonSubventionne(Float prixNonSubventionne) {
		this.prixNonSubventionne = prixNonSubventionne;
	}

	public Float getMontantArecouvrir() {
		return montantArecouvrir;
	}

	public List<PointDeVenteDTO> getListDesPointsDeVentes() {
		return listDesPointsDeVentes;
	}

	public void setListDesPointsDeVentes(List<PointDeVenteDTO> listDesPointsDeVentes) {
		this.listDesPointsDeVentes = listDesPointsDeVentes;
	}

	public void setMontantArecouvrir(Float montantArecouvrir) {
		this.montantArecouvrir = montantArecouvrir;
	}

	public List<PointDeVenteDTO> getListDesPointsDeVentesFiltred() {
		return listDesPointsDeVentesFiltred;
	}

	public void setListDesPointsDeVentesFiltred(List<PointDeVenteDTO> listDesPointsDeVentesFiltred) {
		this.listDesPointsDeVentesFiltred = listDesPointsDeVentesFiltred;
	}

	public Float getPrixUnitaire() {
		return prixUnitaire;
	}

	public String getPointdeVenteAutocomplete() {
		return pointdeVenteAutocomplete;
	}

	public void setPointdeVenteAutocomplete(String pointdeVenteAutocomplete) {
		this.pointdeVenteAutocomplete = pointdeVenteAutocomplete;
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

	/***** Recuperation des mise en place d'une commune ***/

	public void loadAllMiseEnPlaceByIdCommune() {
		planMiseEnPlace = new MiseEnplaceDTOCommune();
		listPVOfCommune = new ArrayList<>();
		nbPointeDeVenteDelaCommune = 0;
		totalReliquatCommune = 0;
		totalCumulCommune = 0;
		totalMiseEnPlaceAffectuer = 0;

		String[] tmp = nomCommune.split("-");

		if (tmp.length > 0 && !tmp[0].trim().equals("")) {
			try {
				Long idPV = Long.parseLong(tmp[0]);
				nomCommune = tmp[1];

				List<MiseEnplaceDTOPointDeVente> listPv = programmAgricoleService.loadAllMiseEnPlaceByIdCommune(idPV);

				planMiseEnPlace.setNbPointDeVente(
						listPv != null ? listPv.size() + "Point (s) trouvé (s)" : "Aucun Point de vente ");

				for (MiseEnplaceDTOPointDeVente pv : listPv) {

					listPVOfCommune.add(pv);
					totalReliquatCommune += pv.getReliquatIntrantAMettreEnplace();
					totalCumulCommune += pv.getCumulIntrantAMettreEnplace();
					totalMiseEnPlaceAffectuer += pv.getQuotaIntrantAMettreEnplace();
				}

				if (listPVOfCommune.size() > 0) {
					nomCommune = ConstantPGCA.CSS_MISEENPLACE_INFOS_POINT_DE_VENTE_MSG + tmp[1];
					nomCommuneClass = ConstantPGCA.CSS_MISEENPLACE_INFOS_POINT_DE_VENTE;
				} else {
					nomCommune = ConstantPGCA.CSS_MISEENPLACE_AUCUN_INFOS_POINT_DE_VENTE_MSG + tmp[1];
					nomCommuneClass = ConstantPGCA.CSS_MISEENPLACE_AUCUN_INFOS_POINT_DE_VENTE;
				}

			} catch (EntityDBDAOException e) {
				// TODO Auto-generated catch block
				Log.error("Erreur surveneur lors de la recuperation des point de vente dans la commune ayant l'id  < "
						+ nomCommune);
			}

		}
	}

	List<MiseEnPlaceEffectuerDTO> listedesMiseEnplacedejaEffectue;
	String listedesMiseEnplacedejaEffectueInfos;

	public void updatePopUpData() {
		if (selectedMiseEnplaceDTOPointDeVente != null) {
			destinataire = selectedMiseEnplaceDTOPointDeVente.getNomGerant();
			executionMiseEnPlace = new MiseEnplaceDTOPointDeVente();
			listLVOfSelectedProgramme = commonService
					.loadAllBLOfSelectedProgramme(selectedMiseEnplaceDTOPointDeVente.getIdProgramme());

			try {
				listedesMiseEnplacedejaEffectue = programmAgricoleService
						.loadAllMiseEnPlaceEffectueeByidMAP(selectedMiseEnplaceDTOPointDeVente.getIdMiseEnPlace());

				if (listedesMiseEnplacedejaEffectue.size() > 0)
					listedesMiseEnplacedejaEffectueInfos = listedesMiseEnplacedejaEffectue.get(0)
							.getMiseEnplaceConcerneLibelle();

				// Uodate fournisseur qui ont le produit a mettre en place
				loadAllFornisseurs();
			} catch (EntityDBDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	List<FournisseurDTO> listFoutnisseurSpecificInstrant;

	public void loadAllFornisseurs() {
		listFoutnisseurSpecificInstrant = new ArrayList<FournisseurDTO>();

		if (selectedMiseEnplaceDTOPointDeVente != null)
			listFoutnisseurSpecificInstrant = commonService
					.loadFournisseurByProduit(selectedMiseEnplaceDTOPointDeVente.getIdIntrantAMettreEnplace());

		// List<FournisseurDTO> idProduit = listFoutnisseurSpecificInstrant;
	}

	public IntrantDTO getSelectedIntrantDTO() {
		return selectedIntrantDTO;
	}

	public void setSelectedIntrantDTO(IntrantDTO selectedIntrantDTO) {
		this.selectedIntrantDTO = selectedIntrantDTO;
	}

	/*****
	 * L'execution d'une mise en place c'est le fait 'envoyer des produit vers
	 * une zone : En general BL Port
	 */

	String messageService;
	String idFournisseurAndIdStock; // concate idfourniseur and idStock (deux
									// produit peuvent avoir le meme fournisseur
									// mais avec des stock different (Transfert
									// de Stock vers magasin sedab))
	String messageServiceCSS;
	boolean showmessageService;
	BonDeLivraisonDTO bondeLivraisonDTO;
	UtilString utils = new UtilString();
	FournisseurDTO selectedProvider;

	public String executeMiseEnplace() {

		if (selectedMiseEnplaceAgregation == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Aucune ligne de mise en place selectionnée");
			return "#";
		}

		if (selectedrepartionSelectedQuota == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Aucune ligne de mise en place selectionnée");
			return "#";
		}

		if (selectedrepartionSelectedQuota.getTotalReliquat() < executionMiseEnPlace
				.getQuantiteIntrantAMettreEnplace()) {
			showMessage(FacesMessage.SEVERITY_WARN,
					"Le reliquat est inférieur à la quantité que vous voulez mettre en place");
			return "#";
		}

		if (executionMiseEnPlace.getTransporteur().trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le transporteur est obligatoire");
			return "#";
		}

		if (idFournisseurAndIdStock == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "L'origine de l'intrant  est obligatoire");
			return "#";
		}

		if (executionMiseEnPlace.getQuantiteIntrantAMettreEnplace() == 0) {
			showMessage(FacesMessage.SEVERITY_WARN, "La quantité d'intrant à mettre en place est incorrect ");
			return "#";
		}

		if (executionMiseEnPlace.getChauffeur().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le chauffeur  est obligatoire");
			return "#";
		}
		if (executionMiseEnPlace.getCamion().trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le camion est obligatoire");
			return "#";
		}

		// on a selectionné un fournisseur ou un magasin couple (id Commuen
		// Only)

		try {
			executionMiseEnPlace.setIdSlectedMiseEnplace(selectedrepartionSelectedQuota.getIdMiseEnplaceAeffectuer());
			executionMiseEnPlace.setNomPointDeVente(selectedrepartionSelectedQuota.getPointdeVente());
			executionMiseEnPlace.setNomGerant(selectedrepartionSelectedQuota.getPointdeVenteGerant());

			String[] providerAndStock = idFournisseurAndIdStock.split("-");

			// on a selectionné un fournisseur ou un magasin couple (founisseur
			// & idTockREf)
			if (providerAndStock != null && !providerAndStock[0].trim().equals("")
					&& !providerAndStock[1].trim().equals("")) {
				executionMiseEnPlace.setFournisseurId(new Long(providerAndStock[0]));
				executionMiseEnPlace.setIdStockdeReference(new Long(providerAndStock[1]));
			} else if (providerAndStock != null && providerAndStock[0].trim().equals("")
					&& providerAndStock[1].trim().equals("") && !providerAndStock[2].trim().equals(""))
				executionMiseEnPlace.setIdCommuneResiduel((new Long(providerAndStock[2]))); // on
																							// a
																							// selectionné
																							// un
																							// fournisseur
																							// ou
																							// un
																							// magasin
																							// couple
																							// (id
																							// Commuen
																							// Only)

			bondeLivraisonDTO = new BonDeLivraisonDTO();
			bondeLivraisonDTO.setIdPoiintdeVenteReceptionnnaireBL(selectedrepartionSelectedQuota.getPointdeVenteId());

			int code = programmAgricoleService.executeMiseEnplaceById(executionMiseEnPlace, bondeLivraisonDTO);

			showmessageService = true;
			if (code == ConstantPGCA.INTRANT_ERROR) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
						"Une erreur technique est survenue");

				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "";
			} else if (code == ConstantPGCA.INTRANT_NODISPO_AU_FOURNISSEUR) {

				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
						"L'intrant n'est pas disponible au fournisseur selectionné");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "";
			} else if (code == ConstantPGCA.BL_MISE_EN_PLACE_EXIST) {

				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
						"Le numéro de bordereau de livraion  " + executionMiseEnPlace.getBlMiseEnPlace()
								+ " est déjà utilisé ! ");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "";
			} else if (code == ConstantPGCA.LV_MISE_EN_PLACE_EXIST) {

				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Le numéro de lettre de voiture "
						+ executionMiseEnPlace.getLvMiseEnPlace() + " est déjà utilisé ! ");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "";
			} else if (code == ConstantPGCA.QUANTITE_INTRANT_NODISPO_AU_FOURNISSEUR) {

				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
						"La quantité disponible auprès du fournisseur est inférieure à la quantité que vous souhaitez mettre en place");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "";
			} else if (code == ConstantPGCA.INTRANT_NON_TARIFIE) {

				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
						"L'intrant que vous voulez mettre en place n'est pas encore tarifié , Merci de le tarifier dabord");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "";
			}

			// a Externaliser (Update founisseur apres MEP)
			// programmAgricoleService.loadAllMiseEnPlaceOfCampagne();
			listFoutnisseurSpecificInstrant = commonService
					.loadFournisseurByProduit(selectedMiseEnplaceAgregation.getIdIntrant());
			repartionSelectedQuota = programmAgricoleService
					.loadListQuotabyIdIntrant(selectedMiseEnplaceAgregation.getIdIntrant());
			stockResiduelByIntrant = programmAgricoleService
					.loadStockResiduelByIntrant(selectedMiseEnplaceAgregation.getIdIntrant());
			for (IntrantDTO c : stockResiduelByIntrant) {
				FournisseurDTO f = new FournisseurDTO();

				f.setStockLibelle("Point de vente de " + c.getLibelleCommune());
				f.setQuantiteIntrantRestant(c.getQuantite());
				// si point de vente on a pas de stock de ref et fournisseur
				// (Point temporaire , l'intrant sera transféré en cas de
				// residuel)
				f.setIdFounisseur(null);
				f.setIdStockdeReference(null);
				f.setIdPointdeVenteResiduel(c.getIdCommune());
				f.setIdStockResiduel(c.getIdStockResiduel());
				listFoutnisseurSpecificInstrant.add(f);
			}

			for (FournisseurDTO f : listFoutnisseurSpecificInstrant)
				stockTotalSelectedIntrant += f.getQuantiteIntrantRestant();
			stockTotalSelectedIntrantNbFounrsseur = listFoutnisseurSpecificInstrant != null
					? listFoutnisseurSpecificInstrant.size() : 0;

			// repartition stock
			stockResiduel = programmAgricoleService.loadStockResiduel();

			// fin update

			// notifier par mail le gerant du point de vente de l'arrivé de BL
			notifierService.sendEmailWithoutAttachement(bondeLivraisonDTO.getGerantMail(),
					"BL N° " + bondeLivraisonDTO.getCorpsMessageMailBlNum() + " en route ",
					bondeLivraisonDTO.getCorpsMessageMail());
			// update liste produit fournisseurs
			getlistIntrantDTO();
			// reset form
			executionMiseEnPlace.setTransporteur("");
			executionMiseEnPlace.setCamion("");
			executionMiseEnPlace.setChauffeur("");
			executionMiseEnPlace.setBlMiseEnPlace("");
			executionMiseEnPlace.setLvMiseEnPlace("");
			executionMiseEnPlace.setQuantiteIntrantAMettreEnplace(0L);
			selectedrepartionSelectedQuota = null;
			showMessage(FacesMessage.SEVERITY_INFO, "Mise en place enregistré avec succès, Notification mail envoyée à "
					+ bondeLivraisonDTO.getGerantMail());
			listeAggregationMiseenPlaceParIntrant = listeAggregationMiseenPlaceParIntrant();
			loadCacheReferential();
			loadCacheRepartionStockFournisseur();
			listedesMiseEnplacedejaEffectue = programmAgricoleService
					.loadAllMiseEnPlaceEffectueeByidMAP(selectedrepartionSelectedQuota.getIdMiseEnplaceAeffectuer());

			RequestContext.getCurrentInstance().update("MEPForm:detailComponentMEPEFFECTUE");

			if (selectedMiseEnplaceAgregation != null) {
				// on ajoute les origine Founisseurs et Magasins
				listFoutnisseurSpecificInstrant = commonService
						.loadFournisseurByProduit(selectedMiseEnplaceAgregation.getIdIntrant());
			}

			return "gestionDesQuotaParPointdeVente.xhtml?faces-redirect=true";
		} catch (Exception e) {
			Log.error("Erreur Excution MIse en place " + e.getMessage() + " --" + e.getStackTrace());
			return "";
		}
	}

	public String updatePopUpDataMEPinitData() {
		if (pointdeVenteAutocomplete == null || pointdeVenteAutocomplete.trim().equals("")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Merci de selectionner l'intrant à modifier ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesQuotasdeMEP.xhtml?faces-redirect=true";
		}
		listQuotaMiseEnplaceSelectedQuota.setIdMiseEnPlace(idintrantFromAutoComplete);
		return "gestionDesQuotasdeMEPModification.xhtml?faces-redirect=true";
	}

	public String updateQuotaMEP() {

		try {
			double q = Double.parseDouble(quantiteAmettreEnPlace.replace(",", "."));

			listQuotaMiseEnplaceSelectedQuota.setQuotaIntrantAMettreEnplace(q);
			listQuotaMiseEnplaceSelectedQuota.setNomPointDeVente(listQuotaMiseEnplaceSelectedQuota.getNomPointDeVente()
					+ " (" + listQuotaMiseEnplaceSelectedQuota.getDepartementPointdeVente() + ")");
			listQuotaMiseEnplaceSelectedQuota.setNomProgramme(programmeFromAutoComplete);
			listQuotaMiseEnplaceSelectedQuota.setLibelleIntrantAMettreEnplace(intrantFromAutoComplete);

			if (commonService.updateQuotaMEP(listQuotaMiseEnplaceSelectedQuota)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Quota modifié avec succès");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				listQuotaMiseEnplace = loadMiseEnPlace(selectedProgramme);
				return "gestionDesQuotasdeMEP.xhtml?faces-redirect=true";
			}

			// if
			// (!commonService.updateQuotaMEP(listQuotaMiseEnplaceSelectedQuota)
			// && listQuotaMiseEnplaceSelectedQuota.getMsgRetour() != null) {
			// {
			// FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
			// "", listQuotaMiseEnplaceSelectedQuota.getMsgRetour());
			// FacesContext.getCurrentInstance().addMessage(null, msg);
			// return "gestionDesQuotasdeMEP.xhtml?faces-redirect=true";
			// }
			//
			// }
		} catch (Exception e) {
			Log.info("Erreur mise à jour de QUOTA" + e.getMessage());
		}

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
				"Erreur  surveneue pendant la modfication du quota");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		return "gestionDesQuotasdeMEP.xhtml?faces-redirect=true";

	}

	public void updatePopUpDataMEP() {
		if (listQuotaMiseEnplaceSelectedQuota != null) {

			pointdeVenteAutocomplete = listQuotaMiseEnplaceSelectedQuota.getNomPointDeVente() + " ("
					+ listQuotaMiseEnplaceSelectedQuota.getDepartementPointdeVente() + ")";
			programmeFromAutoComplete = listQuotaMiseEnplaceSelectedQuota.getNomProgramme();
			intrantFromAutoComplete = listQuotaMiseEnplaceSelectedQuota.getLibelleIntrantAMettreEnplace();

			//double q = Double.parseDouble(quantiteAmettreEnPlace.replace(",", "."));

			quantiteAmettreEnPlace = (long) (listQuotaMiseEnplaceSelectedQuota.getQuotaIntrantAMettreEnplace()) + "";
			idintrantFromAutoComplete = listQuotaMiseEnplaceSelectedQuota.getIdMiseEnPlace();

		}
	}

	private void loadCacheReferential() throws EntityDBDAOException {
		String threeRefs = commonService.loadCachedData(listOfCamionFromDBInJSON, listOfChauffeurFromDBInJSON,
				listOfTransporteurFromDBInJSON);
		String[] tmp = threeRefs.split("@");
		listOfCamionFromDBInJSON = tmp[0];
		listOfChauffeurFromDBInJSON = tmp[1];
		listOfTransporteurFromDBInJSON = tmp[2];

		try {

			indicateurs = programmAgricoleService.loadAllIndicateursDashboard();

		} catch (Exception e) {
			Log.info("Impossible de lire les indicateurs" + e.getMessage());
		}

	}

	String StockParFounisseurJSONFomrat;

	private void loadCacheRepartionStockFournisseur() {
		StockParFounisseurJSONFomrat = "";
		Long connectedUseridStock;
		try {
			connectedUseridStock = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserStockid");
			List<IntrantDTO> listStock = programmAgricoleService.loadAllIntrantDTOByIdStock(connectedUseridStock);

			for (IntrantDTO i : listStock)
				StockParFounisseurJSONFomrat += i.getLibelleFournisseur() + ":" + i.getLibelleProduit() + ":"
						+ i.getQuantite() + "|";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	String pictosIntrnat; // nom intrnat en minuscule sans espace

	public void updatelistMiseEnplace() {
		if (selectedMiseEnplaceAgregation == null)
			showMessage(FacesMessage.SEVERITY_WARN, "Aucune ligne de mise en place selectionnée");

		try {
			listedesMiseEnplacedejaEffectue = programmAgricoleService
					.loadAllMiseEnPlaceEffectueeByidMAP(selectedMiseEnplaceAgregation.getIdMep());
			pictosIntrnat = selectedMiseEnplaceAgregation.getNomIntrant().replaceAll(" ", "").toLowerCase();

		} catch (Exception e) {
			Log.error("Erreur lors de lecture des MEP déjà effectué" + e.getMessage());
		}
	}

	public String loadMiseEnPlacedejaEffectuee() {
		if (selectedrepartionSelectedQuota == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Aucune ligne de mise en place selectionnée");
			return "#";
		}

		executionMiseEnPlace.setIdSlectedMiseEnplace(selectedrepartionSelectedQuota.getIdMiseEnplaceAeffectuer());

		try {
			listedesMiseEnplacedejaEffectue = programmAgricoleService
					.loadAllMiseEnPlaceEffectueeByidMAP(selectedrepartionSelectedQuota.getIdMiseEnplaceAeffectuer());

		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "gestionDesMisesEnplaceSuiviParPVDetails.xhtml?faces-redirect=true&idBlocToShow=1";

	}

	private String subventionProduit;

	public String getDetailsIntrant() {
		if (selectedIntrantDTO == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Veuillez selectionner un intrant");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=4";
		} else {
			if (selectedIntrantDTO.getTauxSubvention() > 0)
				subventionProduit = "1";
			return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=4";
		}
	}

	public void verifierStockDisponibleAupresDuFournisseur() {
		if (selectedMiseEnplaceDTOPointDeVente == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Aucune ligne de mise en place selectionnée");

		} else {
			// TODO erreur : changer Produit en ProduitDTO
			Intrant restantStock = commonService.verifierStockRestantAupresFournisseur(
					selectedMiseEnplaceDTOPointDeVente.getIdIntrantAMettreEnplace(),
					executionMiseEnPlace.getFournisseurId());

			if (restantStock != null) {
				executionMiseEnPlace.setFournisseurInfosDisponibilite(restantStock.getQuantite() + " (T) de "
						+ restantStock.getLibelle() + " disponible auprès du fournisseur "
						+ restantStock.getFournisseur().getLibelle() + ".");
				executionMiseEnPlace.setFournisseurInfosDisponibiliteCSS("stockDispo");
			} else {
				executionMiseEnPlace.setFournisseurInfosDisponibilite(
						selectedMiseEnplaceDTOPointDeVente.getLibelleIntrantAMettreEnplace()
								+ " Intrant non disponible auprès du fournisseur sélectionné.  ");
				executionMiseEnPlace.setFournisseurInfosDisponibiliteCSS("stockNotDispo");

			}
		}
	}

	/*** lecture des quota de mises en place ******/

	List<MiseEnplaceDTOPointDeVente> listQuotaMiseEnplace;
	MiseEnplaceDTOPointDeVente listQuotaMiseEnplaceSelectedQuota;
	MiseEnplaceDTOPointDeVente selectedlistQuotaMiseEnplace;
	List<MiseEnplaceDTOPointDeVente> filtredlistQuotaMiseEnplaceFilter;
	List<MiseEnplaceDTOPointDeVente> filteredlistQuotaMiseEnplace;
	Long selectedProgramme = 0L;

	
	public String redirectToListMEP() {
		try {
			selectedProgramme = commonService.loadIProgrammeIdByName(programmeFromAutoComplete);
			if(selectedProgramme !=  null )
			{
				listQuotaMiseEnplace = loadMiseEnPlace(selectedProgramme);
				return "gestionDesQuotasdeMEP.xhtml?faces-redirect=true&idBlocToShow=1";
			}

		} catch (EntityDBDAOException e) {
			Log.error("Erreur recupération du programme " + programmeFromAutoComplete );
		}
		showMessage(FacesMessage.SEVERITY_WARN, "Aucune programme trouvé");

		return "dashboardAgentsaisie.xhtml?faces-redirect=true&idBlocToShow=1";

	}
	/*
	public String redirectToListMEP() {
		listQuotaMiseEnplace = loadMiseEnPlace();
		return "gestionDesQuotasdeMEP.xhtml?faces-redirect=true&idBlocToShow=1";
	}*/

	/// ****** Lecture des aggregation des quotas de mises en place

	private MiseEnplaceAgregation selectedMiseEnplaceAgregation;
	private List<MiseEnplaceAgregation> filtredMiseEnplaceAgregation;

	private List<MiseEnplaceDTOCommune> repartionSelectedQuota;
	private List<MiseEnplaceDTOCommune> filteredrepartionSelectedQuota;
	private MiseEnplaceDTOCommune selectedrepartionSelectedQuota; // slected
	private List<PointDeVenteDTO> filtredMiseEnplaceAgregationByPV;

	private List<MiseEnplaceAgregation> listAgregationMiseEnPlace;

	public List<MiseEnplaceAgregation> loadAggregationMiseEnPlace() {
		try {
			if (listAgregationMiseEnPlace == null)
				listAgregationMiseEnPlace = commonService.loadAggregationMiseEnPlace();

			// on lit les ventes pour recalculer les tock residuels (Les
			// coordinateurs saissisent les ventes )
			List<MiseEnplaceAgregation> listAgregationMiseEnPlaceTemp = new ArrayList<MiseEnplaceAgregation>();
			for (MiseEnplaceAgregation ag : listAgregationMiseEnPlace) {
				ag.setStockResiduel(ag.getTotalQuota() - ag.getTotalreliquat());

				ag.setTotalQuota(formatDouble(ag.getTotalQuota()));
				ag.setTotalreliquat(formatDouble(ag.getTotalreliquat()));
				ag.setStockResiduel(formatDouble(ag.getStockResiduel()));
				listAgregationMiseEnPlaceTemp.add(ag);
			}
			listAgregationMiseEnPlace = listAgregationMiseEnPlaceTemp;

		} catch (Exception e) {
			Log.error("Erreur TEchnique survneue lors de l'aggregation des quotas" + e.getMessage());
		}
		return listAgregationMiseEnPlace;
	}

	private Double formatDouble(Double val) {
		Double truncatedDouble = BigDecimal.valueOf(val).setScale(2, RoundingMode.HALF_UP).doubleValue();

		return truncatedDouble;
	}

	// Critère de recherche pour génération feuille de mise en place
	String searchdateDebut;
	String searchdateFin;
	List<MiseEnplaceDTOPointDeVente> listIntrant; // filter
	List<MiseEnplaceDTOPointDeVente> filtredlistIntrant; // filter

	public List<CommandeDTO> getFiltredistCommandes() {
		return filtredistCommandes;
	}

	public void setFiltredistCommandes(List<CommandeDTO> filtredistCommandes) {
		this.filtredistCommandes = filtredistCommandes;
	}

	public void filterIntrantByCriteria() {
		try {
			listIntrant = commonService.rechercherMiseEnplaceEffectueParIntrant(searchdateDebut, searchdateFin,
					selectedMiseEnplaceAgregation.getIdIntrant());
		} catch (EntityDBDAOException e) {
			Log.error("Error lecture des MEP" + e.getMessage());
		}
	}

	int stockTotalSelectedIntrant;
	int stockTotalSelectedIntrantNbFounrsseur;
	Long idIntrantAmettreEnPlace;

	public String redirectoTolistListPointdeVenteForMiseEnPlace() {
		stockTotalSelectedIntrant = 0;
		stockTotalSelectedIntrantNbFounrsseur = 0;

		if (selectedMiseEnplaceAgregation == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Merci de selectionner l'intrant  à mettre en place");
			return "#";
		}

		try {
			idIntrantAmettreEnPlace = selectedMiseEnplaceAgregation.getIdIntrant();
			repartionSelectedQuota = programmAgricoleService.loadListQuotabyIdIntrantAndDepartement(
					selectedMiseEnplaceAgregation.getIdIntrant(), selectedMiseEnplaceAgregation.getIdDepartement());
			// on ajoute les intrants dispo dans founisseurs + magasins
			listFoutnisseurSpecificInstrant = commonService
					.loadFournisseurByProduit(selectedMiseEnplaceAgregation.getIdIntrant());

			// on ajoute les intrant des stock residuels (intrant mises en place
			// mais pas encore vendu)
			stockResiduelByIntrant = programmAgricoleService
					.loadStockResiduelByIntrant(selectedMiseEnplaceAgregation.getIdIntrant());

			for (IntrantDTO c : stockResiduelByIntrant) {
				FournisseurDTO f = new FournisseurDTO();

				f.setStockLibelle("Point de vente de " + c.getLibelleCommune());
				f.setQuantiteIntrantRestant(c.getQuantite());
				// si point de vente on a pas de stock de ref et fournisseur
				// (Point temporaire , l'intrant sera transféré en cas de
				// residuel)
				f.setIdFounisseur(null);
				f.setIdStockdeReference(null);
				f.setIdPointdeVenteResiduel(c.getIdCommune());
				f.setIdStockResiduel(c.getIdStockResiduel());
				listFoutnisseurSpecificInstrant.add(f);
			}

			for (FournisseurDTO f : listFoutnisseurSpecificInstrant)
				stockTotalSelectedIntrant += f.getQuantiteIntrantRestant();
			stockTotalSelectedIntrantNbFounrsseur = listFoutnisseurSpecificInstrant != null
					? listFoutnisseurSpecificInstrant.size() : 0;

		} catch (EntityDBDAOException e) {
			Log.error("Une erreur est surveneur lors de la lecture des repartition de quotas");
		}
		return "gestionDesQuotaParPointdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	/**** Infos MEP dans le slider */

	List<MiseEnplaceDTOPointDeVente> listPVOfCommuneSlider;

	public List<MiseEnplaceDTOPointDeVente> loadAllMiseEnplace() {
		try {
			if (listPVOfCommuneSlider == null)
				listPVOfCommuneSlider = programmAgricoleService.getAllMiseEnPlaceEncours();
			//
			// if(listPVOfCommuneSlider != null && listPVOfCommuneSlider.size()
			// > 1);
			// listPVOfCommuneSlider.get(0).setActiveCss("active");

		} catch (EntityDBDAOException e) {
			Log.error("Une erreur est survenue lors de lecture des MEP");
		}
		return listPVOfCommuneSlider;
	}

	public List<MiseEnplaceDTOPointDeVente> loadMiseEnPlace(Long idProgramme) {
		try {
			return programmAgricoleService.loadAllMiseEnPlaceOfCampagne(idProgramme);
		} catch (EntityDBDAOException e) {
			e.printStackTrace();
		}
		return listPVOfCommune;
	}

	Long filteridPointdeVente;
	Long filterCommune;
	Long filtrantIntrant;
	Long filtrantIntrantCategorie;
	Long idIntrant;

	public void loadMiseEnPlaceByCriteria() {
		try {
			listQuotaMiseEnplace = programmAgricoleService.loadMiseEnPlaceAeffectuerByidPointdeVente(
					filteridPointdeVente, filtrantIntrantCategorie, idIntrant);

		} catch (EntityDBDAOException e) {
			Log.error("Erreur de filtrage des donnes de mises en place ");
		}
	}

	public void loadMiseEnPlaceByCriteriaIntrant() {
		List<MiseEnplaceDTOPointDeVente> listQuota = loadMiseEnPlace(selectedProgramme);
		List<MiseEnplaceDTOPointDeVente> filtredlistQuota = new ArrayList<MiseEnplaceDTOPointDeVente>();
		listedesMiseEnplacedejaEffectue = null;

		if (filtrantIntrant > 0) // filtre Programme
		{
			for (MiseEnplaceDTOPointDeVente c : listQuota) {
				if (filtrantIntrant == c.getIdIntrantAMettreEnplace()) {
					MiseEnplaceDTOPointDeVente tmp = c;
					filtredlistQuota.add(tmp);
				}
			}
			listQuotaMiseEnplace = filtredlistQuota;
		}
	}

	/*****
	 * Enregister Mise en place sur un element du plan : Apres mise en place des
	 * plans et au fur et mesure de l'avancement des camapgnes, les agents de
	 * saisis executent les mise en place
	 ***/

	String numeroBLPort;
	String numeroBL;

	public MiseEnplaceDTOPointDeVente getListQuotaMiseEnplaceSelectedQuota() {
		return listQuotaMiseEnplaceSelectedQuota;
	}

	public void setListQuotaMiseEnplaceSelectedQuota(MiseEnplaceDTOPointDeVente listQuotaMiseEnplaceSelectedQuota) {
		this.listQuotaMiseEnplaceSelectedQuota = listQuotaMiseEnplaceSelectedQuota;
	}

	public List<FournisseurDTO> getListFoutnisseurSpecificInstrant() {
		return listFoutnisseurSpecificInstrant;
	}

	public void setListFoutnisseurSpecificInstrant(List<FournisseurDTO> listFoutnisseurSpecificInstrant) {
		this.listFoutnisseurSpecificInstrant = listFoutnisseurSpecificInstrant;
	}

	String destinataire;
	String dateMiseEnplace;
	String distributeur;
	// Lectures de toutes les ventes
	private List<IntrantDTO> listReferentielfiltred;
	private Long idSelectedTypeIntrant;

	public List<MiseEnplaceDTOCommune> getFilteredrepartionSelectedQuota() {
		return filteredrepartionSelectedQuota;
	}

	public void setFilteredrepartionSelectedQuota(List<MiseEnplaceDTOCommune> filteredrepartionSelectedQuota) {
		this.filteredrepartionSelectedQuota = filteredrepartionSelectedQuota;
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

	public void deleteQuota() {
		try {
			listQuotaMiseEnplace = programmAgricoleService.loadMiseEnPlaceAeffectuerByidPointdeVente(
					filteridPointdeVente, filtrantIntrantCategorie, idIntrant);

		} catch (EntityDBDAOException e) {
			Log.error("Une erreur est surveneur lors de la suppression du quota ");
		}
	}

	public MiseEnplaceAgregation getSelectedMiseEnplaceAgregation() {
		return selectedMiseEnplaceAgregation;
	}

	public List<PointDeVenteDTO> getFiltredMiseEnplaceAgregationByPV() {
		return filtredMiseEnplaceAgregationByPV;
	}

	public void setFiltredMiseEnplaceAgregationByPV(List<PointDeVenteDTO> filtredMiseEnplaceAgregationByPV) {
		this.filtredMiseEnplaceAgregationByPV = filtredMiseEnplaceAgregationByPV;
	}

	public void setSelectedMiseEnplaceAgregation(MiseEnplaceAgregation selectedMiseEnplaceAgregation) {
		this.selectedMiseEnplaceAgregation = selectedMiseEnplaceAgregation;
	}

	public int getStockTotalSelectedIntrant() {
		return stockTotalSelectedIntrant;
	}

	public void setStockTotalSelectedIntrant(int stockTotalSelectedIntrant) {
		this.stockTotalSelectedIntrant = stockTotalSelectedIntrant;
	}

	public int getStockTotalSelectedIntrantNbFounrsseur() {
		return stockTotalSelectedIntrantNbFounrsseur;
	}

	public void setStockTotalSelectedIntrantNbFounrsseur(int stockTotalSelectedIntrantNbFounrsseur) {
		this.stockTotalSelectedIntrantNbFounrsseur = stockTotalSelectedIntrantNbFounrsseur;
	}

	public String getSearchdateDebut() {
		return searchdateDebut;
	}

	public void setSearchdateDebut(String searchdateDebut) {
		this.searchdateDebut = searchdateDebut;
	}

	public String getSearchdateFin() {
		return searchdateFin;
	}

	public List<IntrantDTO> getListIntrantDTOFromMagasin() {
		return listIntrantDTOFromMagasin;
	}

	public void setListIntrantDTOFromMagasin(List<IntrantDTO> listIntrantDTOFromMagasin) {
		this.listIntrantDTOFromMagasin = listIntrantDTOFromMagasin;
	}

	public void setSearchdateFin(String searchdateFin) {
		this.searchdateFin = searchdateFin;
	}

	public List<MiseEnplaceDTOPointDeVente> getListIntrant() {
		return listIntrant;
	}

	public void setListIntrant(List<MiseEnplaceDTOPointDeVente> listIntrant) {
		this.listIntrant = listIntrant;
	}

	public void refresshListProduitFromTypeProduitSelected() {
		try {
			listReferentielfiltred = programmAgricoleService.loadReferentielIntrantByType(filtrantIntrantCategorie);

			loadMiseEnPlaceByCriteria();

		} catch (EntityDBDAOException e) {
			e.printStackTrace();
		}
	}

	String designation;
	List<CoupleDTO> listLVOfSelectedProgramme;

	public String enregistrerExecutionBL() {

		Long idProg = null;
		Date dateMiseEnPlace = null;

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		if (!dateMiseEnPlaceString.trim().equals(""))
			try {
				dateMiseEnPlace = df.parse(dateMiseEnPlaceString.trim());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		String[] tmpProg = mPprogramme.split("-");

		try {
			idProg = Long.parseLong(tmpProg[0].trim());
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (idProg == null)
			return "";

		for (MiseEnplaceDTOPointDeVente pv : listPVOfCommune) {
			Log.info("-------  Enregistrement Mise en Place " + pv.getNomPointDeVente() + "-- " + pv.getIdPointDeVente()
					+ " / " + pv.getQuotaIntrantAMettreEnplace() + "/ " + pv.getReliquatIntrantAMettreEnplace() + "/"
					+ pv.getIdIntrantAMettreEnplace());

			programmAgricoleService.saveMiseEnplace(idProg, mPintrant, dateMiseEnPlace, pv);

			Log.info("------- 200 OKOK Enregistrement Mise en Place " + pv.getNomPointDeVente() + "-- "
					+ pv.getIdPointDeVente() + " / " + pv.getQuotaIntrantAMettreEnplace() + "/ "
					+ pv.getReliquatIntrantAMettreEnplace() + "/" + pv.getIdIntrantAMettreEnplace());
		}
		return "";
	}

	/*************** REPORT PDF **************/

	public String reportMiseEnplaceBypdfFormat() throws IOException {

		ReportPDFGerenics report = new ReportPDFGerenics(ConstantPGCA.DIRECTORY_PATH_TMP_PDF,
				"Liste des mise en place de Tambaff");
		try {
			report.generateMiseEnPlace(listQuotaMiseEnplace, listedesMiseEnplacedejaEffectue,
					selectedMiseEnplaceDTOPointDeVente);
			File f = new File(ConstantPGCA.DIRECTORY_PATH_TMP_PDF);
			byte[] pdf = IOUtils.toByteArray(new FileInputStream(f));
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			IOUtils.write(pdf, outputStream);
			FacesContext faces = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setContentLength(pdf.length);
			LocalDateTime currentTime = LocalDateTime.now();

			LocalDate date1 = currentTime.toLocalDate();
			response.setHeader("Content-disposition", "filename=\"" + "Rapport de mise en place du " + date1 + "\"");
			try {
				ServletOutputStream out;
				out = response.getOutputStream();
				out.write(pdf);
			} catch (IOException e) {
				e.printStackTrace();
			}
			faces.responseComplete();
		} catch (DocumentException e) {
			Log.error("----  Error survement pendant telechargement fichier");
			e.printStackTrace();
		}
		return "gestionDesQuotasdeMEP.xhtml";
	}

	/*************** REPORT PDF **************/

	public String exportQuotaMiseEnPlace() throws IOException {

		if (listQuotaMiseEnplace == null)
			return "#";

		ReportPDFGerenics report = new ReportPDFGerenics(ConstantPGCA.DIRECTORY_PATH_TMP_PDF,
				"Rapport journalier de mise en place par catégorie");
		try {
			report.exportQuotaMiseEnPlace(listQuotaMiseEnplace);
			File f = new File(ConstantPGCA.DIRECTORY_PATH_TMP_PDF);
			byte[] pdf = IOUtils.toByteArray(new FileInputStream(f));
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			IOUtils.write(pdf, outputStream);
			FacesContext faces = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setContentLength(pdf.length);
			response.setHeader("Content-disposition", "filename=\"" + "rapport TO DL" + "" + "\"");
			try {
				ServletOutputStream out;
				out = response.getOutputStream();
				out.write(pdf);
			} catch (IOException e) {
				e.printStackTrace();
			}
			faces.responseComplete();
		} catch (DocumentException e) {
			Log.error("----  Error survement pendant telechargement fichier" + e.getMessage());
			e.printStackTrace();
		}
		return "gestionDesQuotasdeMEP.xhtml";
	}

	/*************** END REPORT PDF **************/

	/***************
	 * REPORT PDF LISTE MISE EN PLACE DEJA EFFECTUES (POP UP)
	 **************/

	public String exportMiseEnPlacesEffectues() throws IOException {

		if (listedesMiseEnplacedejaEffectue == null)
			return "#";

		ReportPDFGerenics report = new ReportPDFGerenics(ConstantPGCA.DIRECTORY_PATH_TMP_PDF,
				"EXPORT DES MISE EN PLACE ");
		try {
			report.exportMiseEnPlacesEffectues(listedesMiseEnplacedejaEffectue);
			File f = new File(ConstantPGCA.DIRECTORY_PATH_TMP_PDF);
			byte[] pdf = IOUtils.toByteArray(new FileInputStream(f));
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			IOUtils.write(pdf, outputStream);
			FacesContext faces = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setContentLength(pdf.length);
			response.setHeader("Content-disposition", "filename=\"" + "Rapport Mise en place " + "" + "\"");
			try {
				ServletOutputStream out;
				out = response.getOutputStream();
				out.write(pdf);
			} catch (IOException e) {
				e.printStackTrace();
			}
			faces.responseComplete();
		} catch (DocumentException e) {
			Log.error("----  Error survement pendant telechargement fichier" + e.getMessage());
			e.printStackTrace();
		}
		return "gestionDesQuotasdeMEP.xhtml";
	}

	/*************** END REPORT PDF **************/

	/*************** REPORT PDF **************/

	public String exportlistIntrantsOfStock() throws IOException {

		if (listIntrantsOfStock == null)
			return "#";

		ReportPDFGerenics report = new ReportPDFGerenics(ConstantPGCA.DIRECTORY_PATH_TMP_PDF,
				"Rapport journalier de mise en place par catégorie");
		try {
			report.exportlistIntrantsOfStock(listIntrantsOfStock);
			File f = new File(ConstantPGCA.DIRECTORY_PATH_TMP_PDF);
			byte[] pdf = IOUtils.toByteArray(new FileInputStream(f));
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			IOUtils.write(pdf, outputStream);
			FacesContext faces = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setContentLength(pdf.length);
			response.setHeader("Content-disposition", "filename=\"" + "rapport TO DL" + "" + "\"");
			try {
				ServletOutputStream out;
				out = response.getOutputStream();
				out.write(pdf);
			} catch (IOException e) {
				e.printStackTrace();
			}
			faces.responseComplete();
		} catch (DocumentException e) {
			Log.error("----  Error survement pendant telechargement fichier" + e.getMessage());
			e.printStackTrace();
		}
		return "gestionDesQuotasdeMEP.xhtml";
	}

	/***************
	 * END REPORT PDF
	 * 
	 * @throws EntityDBDAOException
	 **************/

	public String creationRapportOL() throws IOException, EntityDBDAOException {

		if (commandeDTO == null || commandeDTO.getIdOrdre() == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner un ordre de livraison");
			return "agentSaisieReferentielOL.xhtml?faces-redirect=true&idBlocToShow=1b";
		}

		ReportPDFGerenics report = new ReportPDFGerenics(ConstantPGCA.DIRECTORY_PATH_TMP_PDF,
				"Rapport journalier de mise en place par catégorie");
		try {
			commandeDTO = programmAgricoleService.loadOrdreInfosById(commandeDTO.getIdOrdre());

			report.exportRecuORdreDeLivraison(listIntrantsOfStock, commandeDTO);
			File f = new File(ConstantPGCA.DIRECTORY_PATH_TMP_PDF);
			byte[] pdf = IOUtils.toByteArray(new FileInputStream(f));
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			IOUtils.write(pdf, outputStream);
			FacesContext faces = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setContentLength(pdf.length);
			response.setHeader("Content-disposition", "filename=\"" + "rapport TO DL" + "" + "\"");
			try {
				ServletOutputStream out;
				out = response.getOutputStream();
				out.write(pdf);
			} catch (IOException e) {
				e.printStackTrace();
			}
			faces.responseComplete();
		} catch (DocumentException e) {
			Log.error("----  Error survenement pendant telechargement fichier" + e.getMessage());
			e.printStackTrace();
		}
		return "gestionDesQuotasdeMEP.xhtml";
	}

	/*************** END M I S E S EN P L A C E **************/

	public void showMessage(Severity severity, String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		FacesMessage msg = new FacesMessage(severity, "", message);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<IntrantTypeDTO> loadAllIntrants() {
		return commonService.loadAllTypeIntrants();
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public IProgrammeAgricol getProgrammAgricoleService() {
		return programmAgricoleService;
	}

	public Long getIdIntrant() {
		return idIntrant;
	}

	public void setIdIntrant(Long idIntrant) {
		this.idIntrant = idIntrant;
	}

	public Long getFiltrantIntrantCategorie() {
		return filtrantIntrantCategorie;
	}

	public void setFiltrantIntrantCategorie(Long filtrantIntrantCategorie) {
		this.filtrantIntrantCategorie = filtrantIntrantCategorie;
	}

	public String getListedesMiseEnplacedejaEffectueInfos() {
		return listedesMiseEnplacedejaEffectueInfos;
	}

	public void setListedesMiseEnplacedejaEffectueInfos(String listedesMiseEnplacedejaEffectueInfos) {
		this.listedesMiseEnplacedejaEffectueInfos = listedesMiseEnplacedejaEffectueInfos;
	}

	public Long getOlIdPointdeVente() {
		return olIdPointdeVente;
	}

	public List<IntrantDTO> getListReferentielfiltred() {
		return listReferentielfiltred;
	}

	public void setListReferentielfiltred(List<IntrantDTO> listReferentielfiltred) {
		this.listReferentielfiltred = listReferentielfiltred;
	}

	public Long getIdSelectedTypeIntrant() {
		return idSelectedTypeIntrant;
	}

	public void setIdSelectedTypeIntrant(Long idSelectedTypeIntrant) {
		this.idSelectedTypeIntrant = idSelectedTypeIntrant;
	}

	public void setOlIdPointdeVente(Long olIdPointdeVente) {
		this.olIdPointdeVente = olIdPointdeVente;
	}

	public Long getOlIdProgramme() {
		return olIdProgramme;
	}

	public Long getFilteridPointdeVente() {
		return filteridPointdeVente;
	}

	public String getCommuneCertifie() {
		return communeCertifie;
	}

	public void setCommuneCertifie(String communeCertifie) {
		this.communeCertifie = communeCertifie;
	}

	public void setFilteridPointdeVente(Long filteridPointdeVente) {
		this.filteridPointdeVente = filteridPointdeVente;
	}

	public Long getFilterCommune() {
		return filterCommune;
	}

	public void setFilterCommune(Long filterCommune) {
		this.filterCommune = filterCommune;
	}

	public List<MiseEnplaceDTOPointDeVente> getListQuotaMiseEnplace() {
		return listQuotaMiseEnplace;
	}

	public Long getFiltrantIntrant() {
		return filtrantIntrant;
	}

	public void setFiltrantIntrant(Long filtrantIntrant) {
		this.filtrantIntrant = filtrantIntrant;
	}

	public List<PointDeVenteDTO> getListMagasinFiltred() {
		return listMagasinFiltred;
	}

	public String getIdFournisseurAndIdStock() {
		return idFournisseurAndIdStock;
	}

	public void setIdFournisseurAndIdStock(String idFournisseurAndIdStock) {
		this.idFournisseurAndIdStock = idFournisseurAndIdStock;
	}

	public void setListMagasinFiltred(List<PointDeVenteDTO> listMagasinFiltred) {
		this.listMagasinFiltred = listMagasinFiltred;
	}

	public void setListQuotaMiseEnplace(List<MiseEnplaceDTOPointDeVente> listQuotaMiseEnplace) {
		this.listQuotaMiseEnplace = listQuotaMiseEnplace;
	}

	public List<MiseEnPlaceEffectuerDTO> getListedesMiseEnplacedejaEffectue() {
		return listedesMiseEnplacedejaEffectue;
	}

	public void setListedesMiseEnplacedejaEffectue(List<MiseEnPlaceEffectuerDTO> listedesMiseEnplacedejaEffectue) {
		this.listedesMiseEnplacedejaEffectue = listedesMiseEnplacedejaEffectue;
	}

	public UtilString getUtils() {
		return utils;
	}

	public void setUtils(UtilString utils) {
		this.utils = utils;
	}

	public void setOlIdProgramme(Long olIdProgramme) {
		this.olIdProgramme = olIdProgramme;
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

	public boolean isShowmessageService() {
		return showmessageService;
	}

	public void setShowmessageService(boolean showmessageService) {
		this.showmessageService = showmessageService;
	}

	public MiseEnplaceDTOPointDeVente getExecutionMiseEnPlace() {
		return executionMiseEnPlace;
	}

	public BonDeLivraisonDTO getBondeLivraisonDTO() {
		return bondeLivraisonDTO;
	}

	public void setBondeLivraisonDTO(BonDeLivraisonDTO bondeLivraisonDTO) {
		this.bondeLivraisonDTO = bondeLivraisonDTO;
	}

	public void setExecutionMiseEnPlace(MiseEnplaceDTOPointDeVente executionMiseEnPlace) {
		this.executionMiseEnPlace = executionMiseEnPlace;
	}

	public void setOlclient(String olclient) {
		this.olclient = olclient;
	}

	public void setProgrammAgricoleService(IProgrammeAgricol programmAgricoleService) {
		this.programmAgricoleService = programmAgricoleService;
	}

	public Long getFiltredCat() {
		return filtredCat;
	}

	public void setFiltredCat(Long filtredCat) {
		this.filtredCat = filtredCat;
	}

	public String getLibelleFournisseur() {
		return libelleFournisseur;
	}

	public void setLibelleFournisseur(String libelleFournisseur) {
		this.libelleFournisseur = libelleFournisseur;
	}

	public String getLibelleIntrant() {
		return libelleIntrant;
	}

	public void setLibelleIntrant(String libelleIntrant) {
		this.libelleIntrant = libelleIntrant;
	}

	public Long getFiltredInt() {
		return filtredInt;
	}

	public String getMessageService() {
		return messageService;
	}

	public void setMessageService(String messageService) {
		this.messageService = messageService;
	}

	public String getMessageServiceCSS() {
		return messageServiceCSS;
	}

	public void setMessageServiceCSS(String messageServiceCSS) {
		this.messageServiceCSS = messageServiceCSS;
	}

	public void setFiltredInt(Long filtredInt) {
		this.filtredInt = filtredInt;
	}

	public String getDateMiseEnPlaceString() {
		return dateMiseEnPlaceString;
	}

	public String getNomCommuneClass() {
		return nomCommuneClass;
	}

	public String getDateMiseEnplace() {
		return dateMiseEnplace;
	}

	public void setDateMiseEnplace(String dateMiseEnplace) {
		this.dateMiseEnplace = dateMiseEnplace;
	}

	public List<IntrantDTO> getStockResiduelByIntrant() {
		return stockResiduelByIntrant;
	}

	public void setStockResiduelByIntrant(List<IntrantDTO> stockResiduelByIntrant) {
		this.stockResiduelByIntrant = stockResiduelByIntrant;
	}

	public List<IntrantDTO> getStockResiduelFiltredByIntrant() {
		return stockResiduelFiltredByIntrant;
	}

	public void setStockResiduelFiltredByIntrant(List<IntrantDTO> stockResiduelFiltredByIntrant) {
		this.stockResiduelFiltredByIntrant = stockResiduelFiltredByIntrant;
	}

	public IntrantDTO getStockResiduelSelectedByIntrant() {
		return stockResiduelSelectedByIntrant;
	}

	public void setStockResiduelSelectedByIntrant(IntrantDTO stockResiduelSelectedByIntrant) {
		this.stockResiduelSelectedByIntrant = stockResiduelSelectedByIntrant;
	}

	public String getDistributeur() {
		return distributeur;
	}

	public void setDistributeur(String distributeur) {
		this.distributeur = distributeur;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getNumeroBLPort() {
		return numeroBLPort;
	}

	public int getModeBl() {
		return modeBl;
	}

	public void setModeBl(int modeBl) {
		this.modeBl = modeBl;
	}

	public void setNumeroBLPort(String numeroBLPort) {
		this.numeroBLPort = numeroBLPort;
	}

	public String getNumeroBL() {
		return numeroBL;
	}

	public void setNumeroBL(String numeroBL) {
		this.numeroBL = numeroBL;
	}

	public String getDestinataire() {
		return destinataire;
	}

	public List<MiseEnplaceDTOPointDeVente> getFiltredlistQuotaMiseEnplace() {
		return filtredlistQuotaMiseEnplaceFilter;
	}

	public void setFiltredlistQuotaMiseEnplace(List<MiseEnplaceDTOPointDeVente> filtredlistQuotaMiseEnplaceFilter) {
		this.filtredlistQuotaMiseEnplaceFilter = filtredlistQuotaMiseEnplaceFilter;
	}

	public Long getSelectedIdPointDeVente() {
		return selectedIdPointDeVente;
	}

	public void setSelectedIdPointDeVente(Long selectedIdPointDeVente) {
		this.selectedIdPointDeVente = selectedIdPointDeVente;
	}

	public void setDestinataire(String destinataire) {
		this.destinataire = destinataire;
	}

	public List<CoupleDTO> getListLVOfSelectedProgramme() {
		return listLVOfSelectedProgramme;
	}

	public void setListLVOfSelectedProgramme(List<CoupleDTO> listLVOfSelectedProgramme) {
		this.listLVOfSelectedProgramme = listLVOfSelectedProgramme;
	}

	public void setNomCommuneClass(String nomCommuneClass) {
		this.nomCommuneClass = nomCommuneClass;
	}

	public void setDateMiseEnPlaceString(String dateMiseEnPlaceString) {
		this.dateMiseEnPlaceString = dateMiseEnPlaceString;
	}

	public CommandeDTO getCommandeDTO() {
		return commandeDTO;
	}

	public Long getConnectedUserProfilID() {
		return connectedUserProfilID;
	}

	public void setConnectedUserProfilID(Long connectedUserProfilID) {
		this.connectedUserProfilID = connectedUserProfilID;
	}

	public String getLoadListFournisseurs() {
		return loadListFournisseurs;
	}

	public void setLoadListFournisseurs(String loadListFournisseurs) {
		this.loadListFournisseurs = loadListFournisseurs;
	}

	public List<ProduitDTO> getListeIntrantSelectedPv() {
		return listeIntrantSelectedPv;
	}

	public void setListeIntrantSelectedPv(List<ProduitDTO> listeIntrantSelectedPv) {
		this.listeIntrantSelectedPv = listeIntrantSelectedPv;
	}

	public String getLoadAllIntrantforAutoCoplete() {
		return loadAllIntrantforAutoCoplete;
	}

	public void setLoadAllIntrantforAutoCoplete(String loadAllIntrantforAutoCoplete) {
		this.loadAllIntrantforAutoCoplete = loadAllIntrantforAutoCoplete;
	}

	public String getLoadAllProgrammeforAutoCoplete() {
		return loadAllProgrammeforAutoCoplete;
	}

	public void setLoadAllProgrammeforAutoCoplete(String loadAllProgrammeforAutoCoplete) {
		this.loadAllProgrammeforAutoCoplete = loadAllProgrammeforAutoCoplete;
	}

	public String getProgrammeFromAutoComplete() {
		return programmeFromAutoComplete;
	}

	public void setProgrammeFromAutoComplete(String programmeFromAutoComplete) {
		this.programmeFromAutoComplete = programmeFromAutoComplete;
	}

	public String getIntrantFromAutoComplete() {
		return intrantFromAutoComplete;
	}

	public void setIntrantFromAutoComplete(String intrantFromAutoComplete) {
		this.intrantFromAutoComplete = intrantFromAutoComplete;
	}

	public void setCommandeDTO(CommandeDTO commandeDTO) {
		this.commandeDTO = commandeDTO;
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

	public List<MiseEnplaceDTOPointDeVente> getFiltredlistIntrant() {
		return filtredlistIntrant;
	}

	public void setFiltredlistIntrant(List<MiseEnplaceDTOPointDeVente> filtredlistIntrant) {
		this.filtredlistIntrant = filtredlistIntrant;
	}

	public void setTotalMiseEnPlaceAffectuer(int totalMiseEnPlaceAffectuer) {
		this.totalMiseEnPlaceAffectuer = totalMiseEnPlaceAffectuer;
	}

	public ProduitDTO getProduitAajouterAlaCommande() {
		return produitAajouterAlaCommande;
	}

	public void setProduitAajouterAlaCommande(ProduitDTO produitAajouterAlaCommande) {
		this.produitAajouterAlaCommande = produitAajouterAlaCommande;
	}

	public UtilString getUtilsService() {
		return utilsService;
	}

	public void setUtilsService(UtilString utilsService) {
		this.utilsService = utilsService;
	}

	public String getNomCommune() {
		return nomCommune;
	}

	public String getmPcategorieIntran() {
		return mPcategorieIntran;
	}

	public void setmPcategorieIntran(String mPcategorieIntran) {
		this.mPcategorieIntran = mPcategorieIntran;
	}

	public Long getmPintrant() {
		return mPintrant;
	}

	public void setmPintrant(Long mPintrant) {
		this.mPintrant = mPintrant;
	}

	public String getmPprogramme() {
		return mPprogramme;
	}

	public void setmPprogramme(String mPprogramme) {
		this.mPprogramme = mPprogramme;
	}

	public int getNbPointeDeVenteDelaCommune() {
		return nbPointeDeVenteDelaCommune;
	}

	public List<MiseEnplaceDTOPointDeVente> getFilteredlistQuotaMiseEnplace() {
		return filteredlistQuotaMiseEnplace;
	}

	public void setFilteredlistQuotaMiseEnplace(List<MiseEnplaceDTOPointDeVente> filteredlistQuotaMiseEnplace) {
		this.filteredlistQuotaMiseEnplace = filteredlistQuotaMiseEnplace;
	}

	public void setNbPointeDeVenteDelaCommune(int nbPointeDeVenteDelaCommune) {
		this.nbPointeDeVenteDelaCommune = nbPointeDeVenteDelaCommune;
	}

	public void setNomCommune(String nomCommune) {
		this.nomCommune = nomCommune;
	}

	public ArrayList<MiseEnplaceDTOPointDeVente> getListPVOfCommune() {
		return listPVOfCommune;
	}

	public void setListPVOfCommune(ArrayList<MiseEnplaceDTOPointDeVente> listPVOfCommune) {
		this.listPVOfCommune = listPVOfCommune;
	}

	public boolean isStockCommandeDispo() {
		return stockCommandeDispo;
	}

	public void setStockCommandeDispo(boolean stockCommandeDispo) {
		this.stockCommandeDispo = stockCommandeDispo;
	}

	public List<ProduitDTO> getListProduitCommande() {
		return listProduitCommande;
	}

	public void setListProduitCommande(List<ProduitDTO> listProduitCommande) {
		this.listProduitCommande = listProduitCommande;
	}

	public String getModeDePaiement() {
		return modeDePaiement;
	}

	public void setModeDePaiement(String modeDePaiement) {
		this.modeDePaiement = modeDePaiement;
	}

	/*** UTILS **/
	public String formatFloatToCFA(float floatValue) {
		int b;
		b = (int) floatValue;
		String converting = b + "";
		String converted = "";
		for (char c : converting.toCharArray()) {
			if (c == '.')
				break;
			converted += c;
		}
		converted = new StringBuilder(converted).reverse().toString();
		String CFAString = "";
		int i = 1;
		for (char c : converted.toCharArray()) {
			CFAString += c;
			if (i % 3 == 0)
				CFAString += ".";
			i++;
		}
		CFAString = new StringBuilder(CFAString).reverse().toString();

		if (CFAString.startsWith("."))
			CFAString = CFAString.substring(1);

		return (CFAString + " F CFA");
	}

	public ITresorerieService getServiceTresorerie() {
		return serviceTresorerie;
	}

	public List<IntrantDTO> getListIntrantsOfStock() {
		return listIntrantsOfStock;
	}

	public BonDeLivraisonDTO getSelectedBLDTO() {
		return selectedBLDTO;
	}

	public void setSelectedBLDTO(BonDeLivraisonDTO selectedBLDTO) {
		this.selectedBLDTO = selectedBLDTO;
	}

	public boolean isShowDetailsSelectedBl() {
		return showDetailsSelectedBl;
	}

	public void setShowDetailsSelectedBl(boolean showDetailsSelectedBl) {
		this.showDetailsSelectedBl = showDetailsSelectedBl;
	}

	public void setListIntrantsOfStock(List<IntrantDTO> listIntrantsOfStock) {
		this.listIntrantsOfStock = listIntrantsOfStock;
	}

	public void setServiceTresorerie(ITresorerieService serviceTresorerie) {
		this.serviceTresorerie = serviceTresorerie;
	}

	public CreditDTO getCreditLieeAlaCommmande() {
		return creditLieeAlaCommmande;
	}

	public void setCreditLieeAlaCommmande(CreditDTO creditLieeAlaCommmande) {
		this.creditLieeAlaCommmande = creditLieeAlaCommmande;
	}

	public String getReferenceCrdit() {
		return referenceCrdit;
	}

	public void setReferenceCrdit(String referenceCrdit) {
		this.referenceCrdit = referenceCrdit;
	}

	public float getMontantCredit() {
		return montantCredit;
	}

	public void setMontantCredit(float montantCredit) {
		this.montantCredit = montantCredit;
	}

	public MiseEnplaceDTOCommune getPlanMiseEnPlace() {
		return planMiseEnPlace;
	}

	public void setPlanMiseEnPlace(MiseEnplaceDTOCommune planMiseEnPlace) {
		this.planMiseEnPlace = planMiseEnPlace;
	}

	public ProduitDTO getSelecteProduiFromCMD() {
		return selecteProduiFromCMD;
	}

	public void setSelecteProduiFromCMD(ProduitDTO selecteProduiFromCMD) {
		this.selecteProduiFromCMD = selecteProduiFromCMD;
	}

	public TreeNode getRoot2() {
		return root2;
	}

	public void setRoot2(TreeNode root2) {
		this.root2 = root2;
	}

	public Long getSelecteidProduitoDelete() {
		return selecteidProduitoDelete;
	}

	public void setSelecteidProduitoDelete(Long selecteidProduitoDelete) {
		this.selecteidProduitoDelete = selecteidProduitoDelete;
	}

	public Long getFilterIdProgramme() {
		return filterIdProgramme;
	}

	public void setFilterIdProgramme(Long filterIdProgramme) {
		this.filterIdProgramme = filterIdProgramme;
	}

	public Long getFilterIdpv() {
		return filterIdpv;
	}

	public void setFilterIdpv(Long filterIdpv) {
		this.filterIdpv = filterIdpv;
	}

	public int getFilterStatus() {
		return filterStatus;
	}

	public void setFilterStatus(int filterStatus) {
		this.filterStatus = filterStatus;
	}

	public String getFileternomClient() {
		return fileternomClient;
	}

	public void setFileternomClient(String fileternomClient) {
		this.fileternomClient = fileternomClient;
	}

	public List<MiseEnplaceDTOCommune> getRepartionSelectedQuota() {
		return repartionSelectedQuota;
	}

	public void setRepartionSelectedQuota(List<MiseEnplaceDTOCommune> repartionSelectedQuota) {
		this.repartionSelectedQuota = repartionSelectedQuota;
	}

	public List<CommandeDTO> getListCommandes() {
		return listCommandes;
	}

	public List<BonDeLivraisonDTO> getListselectedBLDTO() {
		return listselectedBLDTO;
	}

	public void setListselectedBLDTO(List<BonDeLivraisonDTO> listselectedBLDTO) {
		this.listselectedBLDTO = listselectedBLDTO;
	}

	public MiseEnplaceDTOCommune getSelectedrepartionSelectedQuota() {
		return selectedrepartionSelectedQuota;
	}

	public void setSelectedrepartionSelectedQuota(MiseEnplaceDTOCommune selectedrepartionSelectedQuota) {
		this.selectedrepartionSelectedQuota = selectedrepartionSelectedQuota;
	}

	public void setListCommandes(List<CommandeDTO> listCommandes) {
		this.listCommandes = listCommandes;
	}

	public MiseEnplaceDTOPointDeVente getSelectedlistQuotaMiseEnplace() {
		return selectedlistQuotaMiseEnplace;
	}

	public Long getIdMagasin() {
		return idMagasin;
	}

	public void setIdMagasin(Long idMagasin) {
		this.idMagasin = idMagasin;
	}

	public IntrantDTO getSelectedIntrantTotransfer() {
		return selectedIntrantTotransfer;
	}

	public void setSelectedIntrantTotransfer(IntrantDTO selectedIntrantTotransfer) {
		this.selectedIntrantTotransfer = selectedIntrantTotransfer;
	}

	public Double getQuantiteAtranferer() {
		return quantiteAtranferer;
	}

	public void setQuantiteAtranferer(Double quantiteAtranferer) {
		this.quantiteAtranferer = quantiteAtranferer;
	}

	public String getCamionTotransfer() {
		return camionTotransfer;
	}

	public void setCamionTotransfer(String camionTotransfer) {
		this.camionTotransfer = camionTotransfer;
	}

	public String getChauufeurTotransfer() {
		return chauufeurTotransfer;
	}

	public void setChauufeurTotransfer(String chauufeurTotransfer) {
		this.chauufeurTotransfer = chauufeurTotransfer;
	}

	public void setSelectedlistQuotaMiseEnplace(MiseEnplaceDTOPointDeVente selectedlistQuotaMiseEnplace) {
		this.selectedlistQuotaMiseEnplace = selectedlistQuotaMiseEnplace;
	}

	public List<CamionDTO> getListTransporteurfiltred() {
		return listTransporteurfiltred;
	}

	public List<PointDeVenteDTO> getListMagasin() {
		return listMagasin;
	}

	public void setListMagasin(List<PointDeVenteDTO> listMagasin) {
		this.listMagasin = listMagasin;
	}

	public MagasinDTO getMagDTO() {
		return magDTO;
	}

	public void setMagDTO(MagasinDTO magDTO) {
		this.magDTO = magDTO;
	}

	public void setListTransporteurfiltred(List<CamionDTO> listTransporteurfiltred) {
		this.listTransporteurfiltred = listTransporteurfiltred;
	}

	public String getNomPv() {
		return nomPv;
	}

	public void setNomPv(String nomPv) {
		this.nomPv = nomPv;
	}

	public String getCommunepv() {
		return Communepv;
	}

	public String getLoadAllDepartementAutoCoplete() {
		return loadAllDepartementAutoCoplete;
	}

	public void setLoadAllDepartementAutoCoplete(String loadAllDepartementAutoCoplete) {
		this.loadAllDepartementAutoCoplete = loadAllDepartementAutoCoplete;
	}

	public String getLoadAllTransporteurforAutoCoplete() {
		return loadAllTransporteurforAutoCoplete;
	}

	public void setLoadAllTransporteurforAutoCoplete(String loadAllTransporteurforAutoCoplete) {
		this.loadAllTransporteurforAutoCoplete = loadAllTransporteurforAutoCoplete;
	}

	public void setCommunepv(String communepv) {
		Communepv = communepv;
	}

	public Long getIdmagasinSource() {
		return idmagasinSource;
	}

	public void setIdmagasinSource(Long idmagasinSource) {
		this.idmagasinSource = idmagasinSource;
	}

	public Long getIdMagasinDestination() {
		return idMagasinDestination;
	}

	public void setIdMagasinDestination(Long idMagasinDestination) {
		this.idMagasinDestination = idMagasinDestination;
	}

	public List<MagasinDTO> getReferentialMagsin() {
		return referentialMagsin;
	}

	public void setReferentialMagsin(List<MagasinDTO> referentialMagsin) {
		this.referentialMagsin = referentialMagsin;
	}

	public String getExpediteurMagasin() {
		return expediteurMagasin;
	}

	public void setExpediteurMagasin(String expediteurMagasin) {
		this.expediteurMagasin = expediteurMagasin;
	}

	public String getExpediteurMagasinGerant() {
		return expediteurMagasinGerant;
	}

	public void setExpediteurMagasinGerant(String expediteurMagasinGerant) {
		this.expediteurMagasinGerant = expediteurMagasinGerant;
	}

	public String getExpediteurMagasinGerantTel() {
		return expediteurMagasinGerantTel;
	}

	public void setExpediteurMagasinGerantTel(String expediteurMagasinGerantTel) {
		this.expediteurMagasinGerantTel = expediteurMagasinGerantTel;
	}

	public String getExpediteurMagasinGerantMail() {
		return expediteurMagasinGerantMail;
	}

	public void setExpediteurMagasinGerantMail(String expediteurMagasinGerantMail) {
		this.expediteurMagasinGerantMail = expediteurMagasinGerantMail;
	}

	public String getReceptionnaireMagasin() {
		return receptionnaireMagasin;
	}

	public void setReceptionnaireMagasin(String receptionnaireMagasin) {
		this.receptionnaireMagasin = receptionnaireMagasin;
	}

	public String getReceptionnaireMagasinGerant() {
		return receptionnaireMagasinGerant;
	}

	public void setReceptionnaireMagasinGerant(String receptionnaireMagasinGerant) {
		this.receptionnaireMagasinGerant = receptionnaireMagasinGerant;
	}

	public String getReceptionnaireMagasinGerantTel() {
		return receptionnaireMagasinGerantTel;
	}

	public void setReceptionnaireMagasinGerantTel(String receptionnaireMagasinGerantTel) {
		this.receptionnaireMagasinGerantTel = receptionnaireMagasinGerantTel;
	}

	public String getReceptionnaireMagasinGerantMail() {
		return receptionnaireMagasinGerantMail;
	}

	public void setReceptionnaireMagasinGerantMail(String receptionnaireMagasinGerantMail) {
		this.receptionnaireMagasinGerantMail = receptionnaireMagasinGerantMail;
	}

	public PointDeVenteDTO getSelectedPv() {
		return selectedPv;
	}

	public void setSelectedPv(PointDeVenteDTO selectedPv) {
		this.selectedPv = selectedPv;
	}

	public List<ProduitDTO> getListProduitslieeSelectedVente() {
		return listProduitslieeSelectedVente;
	}

	public void setListProduitslieeSelectedVente(List<ProduitDTO> listProduitslieeSelectedVente) {
		this.listProduitslieeSelectedVente = listProduitslieeSelectedVente;
	}

	public List<PointDeVenteDTO> getListPointDeVentesFiltred() {
		return listPointDeVentesFiltred;
	}

	public List<IntrantDTO> getStockResiduel() {
		return stockResiduel;
	}

	public void setStockResiduel(List<IntrantDTO> stockResiduel) {
		this.stockResiduel = stockResiduel;
	}

	public List<IntrantDTO> getStockResiduelFiltred() {
		return stockResiduelFiltred;
	}

	public void setStockResiduelFiltred(List<IntrantDTO> stockResiduelFiltred) {
		this.stockResiduelFiltred = stockResiduelFiltred;
	}

	public IntrantDTO getStockResiduelSelected() {
		return stockResiduelSelected;
	}

	public void setStockResiduelSelected(IntrantDTO stockResiduelSelected) {
		this.stockResiduelSelected = stockResiduelSelected;
	}

	public void setListPointDeVentesFiltred(List<PointDeVenteDTO> listPointDeVentesFiltred) {
		this.listPointDeVentesFiltred = listPointDeVentesFiltred;
	}

	public Long getmPpointDeVente() {
		return mPpointDeVente;
	}

	public String getSubventionProduit() {
		return subventionProduit;
	}

	public void setSubventionProduit(String subventionProduit) {
		this.subventionProduit = subventionProduit;
	}

	public void setmPpointDeVente(Long mPpointDeVente) {
		this.mPpointDeVente = mPpointDeVente;
	}

	public List<MiseEnplaceAgregation> getFiltredMiseEnplaceAgregation() {
		return filtredMiseEnplaceAgregation;
	}

	public FournisseurDTO getSelectedProvider() {
		return selectedProvider;
	}

	public void setSelectedProvider(FournisseurDTO selectedProvider) {
		this.selectedProvider = selectedProvider;
	}

	public void setFiltredMiseEnplaceAgregation(List<MiseEnplaceAgregation> filtredMiseEnplaceAgregation) {
		this.filtredMiseEnplaceAgregation = filtredMiseEnplaceAgregation;
	}

	public String getQuantiteAmettreEnPlace() {
		return quantiteAmettreEnPlace;
	}

	public String getStockParFounisseurJSONFomrat() {
		return StockParFounisseurJSONFomrat;
	}

	public void setStockParFounisseurJSONFomrat(String stockParFounisseurJSONFomrat) {
		StockParFounisseurJSONFomrat = stockParFounisseurJSONFomrat;
	}

	public void setQuantiteAmettreEnPlace(String quantiteAmettreEnPlace) {
		this.quantiteAmettreEnPlace = quantiteAmettreEnPlace;
	}

	public String getPictosIntrnat() {
		return pictosIntrnat;
	}

	public void setPictosIntrnat(String pictosIntrnat) {
		this.pictosIntrnat = pictosIntrnat;
	}

	public List<MiseEnplaceDTOPointDeVente> getListPVOfCommuneSlider() {
		return listPVOfCommuneSlider;
	}

	public void setListPVOfCommuneSlider(List<MiseEnplaceDTOPointDeVente> listPVOfCommuneSlider) {
		this.listPVOfCommuneSlider = listPVOfCommuneSlider;
	}

	public List<MiseEnplaceAgregation> getListAgregationMiseEnPlace() {
		return listAgregationMiseEnPlace;
	}

	public void setListAgregationMiseEnPlace(List<MiseEnplaceAgregation> listAgregationMiseEnPlace) {
		this.listAgregationMiseEnPlace = listAgregationMiseEnPlace;
	}

	public Long getIdDepartement() {
		return idDepartement;
	}

	public List<MiseEnplaceDTOPointDeVente> getFiltredlistQuotaMiseEnplaceFilter() {
		return filtredlistQuotaMiseEnplaceFilter;
	}

	public void setIdDepartement(Long idDepartement) {
		this.idDepartement = idDepartement;
	}

	public Long getDepartementOrdigineId() {
		return departementOrdigineId;
	}

	public String getNumBlCREE() {
		return numBlCREE;
	}

	public void setNumBlCREE(String numBlCREE) {
		this.numBlCREE = numBlCREE;
	}

	public void setDepartementOrdigineId(Long departementOrdigineId) {
		this.departementOrdigineId = departementOrdigineId;
	}

	public List<MagasinDTO> getListPVofSelectedDepartement() {
		return listPVofSelectedDepartement;
	}

	public void setListPVofSelectedDepartement(List<MagasinDTO> listPVofSelectedDepartement) {
		this.listPVofSelectedDepartement = listPVofSelectedDepartement;
	}

	public String getPointdeVenteAutocompleteDestination() {
		return pointdeVenteAutocompleteDestination;
	}

	public void setPointdeVenteAutocompleteDestination(String pointdeVenteAutocompleteDestination) {
		this.pointdeVenteAutocompleteDestination = pointdeVenteAutocompleteDestination;
	}

	public void setFiltredlistQuotaMiseEnplaceFilter(
			List<MiseEnplaceDTOPointDeVente> filtredlistQuotaMiseEnplaceFilter) {
		this.filtredlistQuotaMiseEnplaceFilter = filtredlistQuotaMiseEnplaceFilter;
	}

	public Long getSelectedProgramme() {
		return selectedProgramme;
	}

	public void setSelectedProgramme(Long selectedProgramme) {
		this.selectedProgramme = selectedProgramme;
	}

}
