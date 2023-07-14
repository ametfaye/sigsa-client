package sn.awi.pgca.web.bean;

import java.text.SimpleDateFormat;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jfree.util.Log;
import org.primefaces.model.UploadedFile;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.service.ICommonService;
import sn.awi.pgca.business.service.IMagasinierService;
import sn.awi.pgca.business.service.IProgrammeAgricol;
import sn.awi.pgca.business.service.ITresorerieService;
import sn.awi.pgca.dataModel.PointDeVente;
import sn.awi.pgca.utils.UtilString;
import sn.awi.pgca.web.dto.AvanceCreditDTO;
import sn.awi.pgca.web.dto.BonDeLivraisonDTO;
import sn.awi.pgca.web.dto.ChequeDTO;
import sn.awi.pgca.web.dto.CommandeDTO;
import sn.awi.pgca.web.dto.CreditDTO;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.LitigesDTO;
import sn.awi.pgca.web.dto.MagasinDTO;
import sn.awi.pgca.web.dto.PointDeVenteDTO;
import sn.awi.pgca.web.dto.ProduitDTO;
import sn.awi.pgca.web.dto.VentesDTO;
import sn.awi.pgca.web.dto.VersementBanqueDTO;

@ManagedBean(name = "magasinierMB")
@SessionScoped
public class MagasinierManagedBean {

	@ManagedProperty(value = "#{commonService}")
	private ICommonService commonService;

	@ManagedProperty(value = "#{programmeAgricolService}")
	private IProgrammeAgricol programmAgricoleService;

	@ManagedProperty(value = "#{tresorerieService}")
	private ITresorerieService serviceTresorerie;

	@ManagedProperty(value = "#{magasinierService}")
	private IMagasinierService magasinierServices;

	private Long selectdIdProduit;
	private ProduitDTO selectedProduit;
	private BonDeLivraisonDTO selectedBLDTO;
	private List<ProduitDTO> selectedProduitListAvendre;
	private List<Long> selectedIdProduitListAvendre;
	private List<ProduitDTO> listofBl;
	private Long idselectedBLDTO;
	private int nombreBlAvalider;
	private Long quantiteProduitAvendre = null;
	private int showBlocInfosConfirmation;
	boolean showDetailsSelectedBl;
	private VentesDTO venteDTO;
	private CreditDTO creditDTO;
	private AvanceCreditDTO avanceCredit;
	private Boolean showbtnValidationsCMD;
	private VersementBanqueDTO versementBanque = new VersementBanqueDTO();

	private VersementBanqueDTO versement;

	private CommandeDTO selectedCommandeDTO;

	private float montantPayeParCheque = 0;
	private float montantPayeParBonSubvention = 0;
	private float montantPayeParEspeces = 0;
	private float montantPayeParNature = 0;
	private float montantPayeParBLP = 0;
	private String numeroBLP = "";


	private String numeroCheque;
	private String banqueEmetriceCheque;

	private float montantTotalDeposeEnBanque;

	private String clientNom;
	private String clientPrenom;
	private String clientAdresse;
	private String clientTel;
	private String dateVente;
	private String referenceVentes;
	boolean payerLeResteParCredit;
	private UploadedFile file;

	// Ordre de livraion
	private CommandeDTO commandeDTO;
	private ProduitDTO produiFromORDRES;
	private List<ProduitDTO> listProduitCommande;

	@PostConstruct
	public void init() throws EntityDBDAOException {

		venteDTO = new VentesDTO();
		// versement = new VersementBanqueDTO();
		showBlocInfosConfirmation = 0;
		showBlocInfosErrors = 0;
		showDetailsSelectedBl = false;
		selectedProduitListAvendre = new ArrayList<ProduitDTO>();
		loadStockResiduelByIntrant();

		callServiceRecuperationVentes();
	}

	int nbPointDeVenteAffecte;

	public List<ProduitDTO> callServiceRecuperationStock() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);

			Long idStockPointDeVente = (Long) session.getAttribute("connectedUserStockid");
			try {
				listofBl = magasinierServices.loadStockProduitByIdPointdeVenteMagasinier(idStockPointDeVente);
			} catch (EntityDBDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return listofBl;
		}
		return null;
	}

	public int getNbBLtoValidate() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			Long connectedUseridStock = (Long) session.getAttribute("connectedUserStockid");
			try {
				return (programmAgricoleService.loadBondeLivraisonByIdPointDeVente(connectedUseridStock) != null
						? programmAgricoleService.loadBondeLivraisonByIdPointDeVente(connectedUseridStock).size() : 0);
			} catch (EntityDBDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nombreBlAvalider;
	}

	public int getNbCommandestoValidate() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			Long connectedPointDeventeId = (Long) session.getAttribute("idPointdeVente");
			Long idProgramme = 0L; // TODO Get idProgramme
			try {

				return (programmAgricoleService.getListCommandeByCriteria(connectedPointDeventeId, idProgramme,
						0) != null
								? programmAgricoleService
										.getListCommandeByCriteria(connectedPointDeventeId, idProgramme, 0).size()
								: 0);
			} catch (EntityDBDAOException e) {
				Log.error("Une erreur est survenue lors de la recup des commandes" + e.getMessage());
				e.printStackTrace();
			}
		}
		return nombreBlAvalider;
	}

	public List<VersementBanqueDTO> callServiceRecuperationDepotBanque() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			Long idPointdeVente = (Long) session.getAttribute("idPointdeVente");

			if (idPointdeVente != null) {
				List<VersementBanqueDTO> lv = serviceTresorerie.recupererVersementBanqueByIdPointDeVente(idPointdeVente);
				montantTotalDeposeEnBanque = 0;
				for (VersementBanqueDTO v : lv) {
					montantTotalDeposeEnBanque += v.getMontantVersment();
				}
				return lv;
			} else { // pour les point de vente on cherche via idpointdecollecte
				Long idPointdeCollecte = (Long) session.getAttribute("idPointdeCollecte");
				List<VersementBanqueDTO> lv = serviceTresorerie.recupererVersementBanqueByIdPointDeCollecte(idPointdeCollecte);
				montantTotalDeposeEnBanque = 0;
				for (VersementBanqueDTO v : lv) {
					montantTotalDeposeEnBanque += v.getMontantVersment();
				}
				return lv;
			}
		}
		return null;
	}

	public Float callTotalServiceRecuperationDepotBanque() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);

			// pour les point de vente on cherche via idpointdecollecte
			Long idPointdeCollecte = (Long) session.getAttribute("idPointdeCollecte");
			List<VersementBanqueDTO> lv = serviceTresorerie
					.recupererVersementBanqueByIdPointDeCollecte(idPointdeCollecte);
			montantTotalDeposeEnBanque = 0;

			for (VersementBanqueDTO v : lv) {
				montantTotalDeposeEnBanque += v.getMontantVersment();
			}

			return montantTotalDeposeEnBanque;

		}
		return null;
	}

	public List<ChequeDTO> callServiceRecuperationCheques() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			Long idPointdeVente = (Long) session.getAttribute("idPointdeVente");

			return serviceTresorerie.recupererChequeByIdPointDeVente(idPointdeVente);
		}
		return null;
	}

	public int getNbventes() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			Long connectedUseridStock = (Long) session.getAttribute("connectedUserStockid");

			try {
				return (programmAgricoleService.loadNbVentesByIdPointDeVente(connectedUseridStock) != null
						? programmAgricoleService.loadNbVentesByIdPointDeVente(connectedUseridStock).size() : 0);
			} catch (EntityDBDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nombreBlAvalider;
	}

	public float getMontantTotalventes() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			Long connectedUseridStock = (Long) session.getAttribute("connectedUserStockid");

			try {
				List<VentesDTO> lv = programmAgricoleService.loadNbVentesByIdPointDeVente(connectedUseridStock);

				if (lv != null) {
					float tmp = 0;
					for (VentesDTO v : lv)
						tmp += v.getMontantEncaisse();

					return tmp;
				} else {
					return 0;
				}

			} catch (EntityDBDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nombreBlAvalider;
	}

	public int getNbCreditstoValidate() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			Long connectedUseridStock = (Long) session.getAttribute("connectedUserStockid");

			return (programmAgricoleService.loadNbCreditsByIdPointDeVente(connectedUseridStock) != null
					? programmAgricoleService.loadNbCreditsByIdPointDeVente(connectedUseridStock).size() : 0);
		}
		return nombreBlAvalider;
	}

	public float getMontantCreditstoValidate() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			Long connectedUseridStock = (Long) session.getAttribute("connectedUserStockid");

			List<CreditDTO> credits = programmAgricoleService.loadNbCreditsByIdPointDeVente(connectedUseridStock);

			if (credits != null) {
				float tmp = 0;

				for (CreditDTO c : credits)
					tmp += c.getMontantRestantApayer();

				return tmp;
			}
		}
		return 0;
	}

	public List<BonDeLivraisonDTO> callServiceRecuperationBL() {
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

	public List<VentesDTO> callServiceRecuperationDetailsVentes() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			Long connectedUseridStock = (Long) session.getAttribute("connectedUserStockid");

			try {
				return programmAgricoleService.loadNbVentesByIdPointDeVente(connectedUseridStock);
			} catch (EntityDBDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	Float totalVentePV = 0f;

	List<VentesDTO> filteredVentes;
	
	public List<VentesDTO> callServiceRecuperationVentes() throws EntityDBDAOException {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false); 
			Long connectedUseridStock = (Long) session.getAttribute("connectedUserStockid");
			totalVentePV = 0f;

			List<VentesDTO> allvente = programmAgricoleService.loadNbVentesByIdPointDeVente(connectedUseridStock);

			if (null != allvente) {
				for (VentesDTO v : allvente)
					totalVentePV += v.getMontantVente();
			}
			return allvente;
		}
		return null;
	}

	public Float gettotalVente() throws EntityDBDAOException {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			Long connectedUseridStock = (Long) session.getAttribute("connectedUserStockid");
			totalVentePV = 0f;

			List<VentesDTO> allvente = programmAgricoleService.loadNbVentesByIdPointDeVente(connectedUseridStock);

			if (null != allvente) {
				for (VentesDTO v : allvente)
					totalVentePV += v.getMontantVente();
			}
			return totalVentePV;
		}
		return 0f;
	}

	public List<CommandeDTO> callServiceRecuperationCommandes() throws EntityDBDAOException {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			Long connectedPointDeventeId = (Long) session.getAttribute("idPointdeVente");
			Long idProgramme = 0L; // TODO Get idProgramme
			try {

				return (programmAgricoleService.getListCommandeByCriteria(connectedPointDeventeId, idProgramme) != null
						? programmAgricoleService.getListCommandeByCriteria(connectedPointDeventeId, idProgramme)
						: null);
			} catch (EntityDBDAOException e) {
				Log.error("Une erreur est survenue lors de la recup des commandes" + e.getMessage());
				e.printStackTrace();
			}
		}
		return null;
	}

	public List<CreditDTO> callServiceRecuperationCreditDTO() throws EntityDBDAOException {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			Long connectedUseridStock = (Long) session.getAttribute("connectedUserStockid");

			return programmAgricoleService.loadNbCreditsByIdPointDeVente(connectedUseridStock);
		}
		return null;
	}

	// accepter un BL : stock a modiifier
	public String accepterBL() throws EntityDBDAOException {
		getDetailsOfSelectedBL();
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (selectedBLDTO == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Aucun Bon de livraison selectioné !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}

		// listofBl = selectedBLDTO.getListProduitsOfBL();
		// agasinierMB.selectedBLDTO.listProduitsOfBL

		if (selectedBLDTO.getListProduitsOfBL() == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Aucun produit lié au Bl selectionné !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}

		if (programmAgricoleService.updateStockFromBl(selectedBLDTO, selectedBLDTO.getIdStockReceptionnaire(),
				selectedBLDTO.getListProduitsOfBL())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Le Bl est pris en compte sur votre stock ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "dashboardMagasinier.xhtml?faces-redirect=true";
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Une erreur est survenue lors du traitement du BL ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "dashboardMagasinier.xhtml?faces-redirect=true";
		}

	}

	LitigesDTO litigeDTO = new LitigesDTO();

	private Float prixUnitaireAmodifier;

	private Double quantiteAmodifier;

	private String libelleIntrantAmodifier;

	private Long connectedUserPersonne;

	public String accepterBLAvecReserve() throws EntityDBDAOException {
		getDetailsOfSelectedBL();
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (selectedBLDTO == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Aucun Bon de livraison selectioné !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}

		if (litigeDTO.getNombreDesacs() < 1) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Le nombre de sac manquant est obligatoire Bon de livraison selectioné !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}

		if (litigeDTO.getQuantiteManquante() < 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"la quantité manquante est obligatoire !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}
		
		if (selectedBLDTO.getListProduitsOfBL().get(0) != null 
				&& selectedBLDTO.getListProduitsOfBL().get(0).getQuantite() < litigeDTO.getQuantiteManquante()  ) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"la quantité manquante ne peut pas etre supérieure à la quantité du BL !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}
		
		if (litigeDTO.getDetailsLitige().trim().equals("")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"le détails du manquant est obligatoire !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}

		if (selectedBLDTO.getListProduitsOfBL() == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Aucun produit lié au Bl selectionné !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}

		if (litigeDTO == null || litigeDTO.getQuantiteManquante() < 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"La quantité manquante est obligatoire");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}

		if (programmAgricoleService.updateStockFromBlWithReserve(selectedBLDTO,
				selectedBLDTO.getIdStockReceptionnaire(), selectedBLDTO.getListProduitsOfBL(), litigeDTO)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
					"Bl pris en compte sur votre stock  !! " + "Un manquant de " + litigeDTO.getNombreDesacs()
							+ " sac (s) pour une quantité totale de " + litigeDTO.getQuantiteManquante()
							+ " est enregistré puis transmis aux managers. ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "dashboardMagasinier.xhtml?faces-redirect=true";
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Une erreur est survenue lors du traitement du BL ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "magasisnierGestionBL.xhtml?faces-redirect=true";
		}

	}

	public String vendreProduit() throws EntityDBDAOException {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (selectedProduitListAvendre.size() == 0) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner au moins un produit à vendre !");
			return "dashboardMagasinier.xhtml?faces-redirect=true";
		}
		showBlocInfosConfirmation = 0;

		clientPrenom = "";
		clientNom = "";
		clientTel = "";
		clientAdresse = "";
		montantPayeParEspeces = 0l;
		montantPayeParNature = 0l;
		montantPayeParBonSubvention = 0l;
		montantPayeParCheque = 0L;
		numeroCheque = "";
		banqueEmetriceCheque = "0";
		return "magasinierVendreProduit.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public String callVendreProduitService() throws EntityDBDAOException {
		showBlocInfosErrors = 0;

		String auteurVAlidation = null;
		Long connectedUseridStock = null;
		Long idConnectedUser = null;

		String zone = null;
		VentesDTO venteDTO = new VentesDTO();

		if (selectedProduitListAvendre == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Selectionner au moins un produit à vendre !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "magasinierVendreProduit.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		// Verif numero Cheque et si valeur cheque saisie
		if (montantPayeParCheque != 0 && numeroCheque.trim().equals("")) {

			displayBlock("error", "le numéro de chéque est obligatoire ");
			return "magasinierVendreProduit.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		// Verif banque emetrice si valeur cheque saisie
		if (montantPayeParCheque != 0 && banqueEmetriceCheque.equals("0")) {
			displayBlock("error", "la  banque émettrice du chéque est obligatoire ");
			return "magasinierVendreProduit.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		
		if (montantPayeParBLP != 0 && numeroBLP.equals("")) {
			displayBlock("error", "le numéro de BLP est obligatoire");
			return "magasinierVendreProduit.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		

		try {
			auteurVAlidation = SessionManagedBean.getSessionDataByTag("connectedUserName");
			idConnectedUser = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserPersonneid");
			zone = SessionManagedBean.getSessionDataByTag("connectedUserPointPhysique");
			connectedUseridStock = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserStockid");

			venteDTO.setIdPointDeVente(SessionManagedBean.getSessionLongValuesDataByTag("idPointdeVente"));
			venteDTO.setMontantVente(getmontantTotalApayer());
			venteDTO.setMontantEncaisse(getmontantTotalpaye());
			venteDTO.setIdstockVente(connectedUseridStock);
			venteDTO.setVendeurLibelle(auteurVAlidation);
			venteDTO.setIdvendeur(idConnectedUser);
			venteDTO.setClient(clientPrenom + " " + clientNom);
			venteDTO.setTelclient(clientTel);
			venteDTO.setAdresseclient(clientAdresse);
			venteDTO.setZoneVente(zone);
			venteDTO.setMontantEncaisseParBonDeSubvention(montantPayeParBonSubvention);
			venteDTO.setMontantEncaisseParCheque(montantPayeParCheque);
			venteDTO.setMontantEncaisseParEspece(montantPayeParEspeces); 
			venteDTO.setMontantEncaisseBLP(montantPayeParBLP);
			venteDTO.setNumeroBLP(numeroBLP);
			

			Date datejour = new Date();
			SimpleDateFormat dt1 = new SimpleDateFormat("dd-mm-yyyy");
			venteDTO.setDateVente(dt1.format(datejour));

			dateVente = dt1.format(datejour);

		} catch (Exception e) {
			Log.error("Impossible de recuperer les donnees de sessions " + e.getMessage());
			e.printStackTrace();
		}

		// Verification quantite produit restant avant vente

		for (ProduitDTO selectedProduit : selectedProduitListAvendre) {
			if (selectedProduit.getQuantite() < selectedProduit.getQuantiteProduitAvendre()) {

				displayBlock("warn", "Le stock du produit restant ( " + selectedProduit.getQuantite()
						+ " Kg ) est inférieur à la quantité saisie !");

				return "magasinierVendreProduit.xhtml?faces-redirect=true&idBlocToShow=2";
			}
		}

		// verif somme a payer manquante jj
		if (Math.floor(getmontantTotalApayer()) > Math.floor(getmontantTotalpaye()) && !isPayerLeResteParCredit()) {

			displayBlock("warn",
					"La somme encaissée " + formatFloatToCFA(getmontantTotalpaye())
							+ " est inférieure au montant de la vente  " + formatFloatToCFA(getmontantTotalApayer())
							+ ",  cocher l'option ' Enregistrer le reste comme crédit' pour que le client contracte un crédit  de  "
							+ formatFloatToCFA(getmontantTotalApayer() - getmontantTotalpaye()));

			return "magasinierVendreProduit.xhtml?faces-redirect=true&idBlocToShow=2";
		}

		// Verification exces encaisssmenet
		if (getmontantTotalApayer() < getmontantTotalpaye()) {

			displayBlock("warn", "La somme encaissée " + formatFloatToCFA(getmontantTotalpaye())
					+ " est supérieure  au montant total à payer  " + formatFloatToCFA(getmontantTotalApayer()));
			return "magasinierVendreProduit.xhtml?faces-redirect=true&idBlocToShow=2";

		}

		// ventes sans credit
		if (getmontantTotalApayer() <= getmontantTotalpaye()) {

			if (serviceTresorerie.vendreProduit(selectedProduitListAvendre, venteDTO)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
						"Vente enregistré avec succès, !  les détails de la vente sont accesibles depuis le module de vente.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				showBlocInfosConfirmation = 1;
				// selectedProduit.setPrixTotal(selectedProduit.getQuantite() *
				// selectedProduit.getPrixUnitaire());

				if (montantPayeParCheque != 0) {
					ChequeDTO chequeAEncaisser = new ChequeDTO();

					chequeAEncaisser.setNumeroCheque(numeroCheque);
					chequeAEncaisser.setBanqueEmetrice(banqueEmetriceCheque);
					chequeAEncaisser.setMontantCheque(montantPayeParCheque);
					chequeAEncaisser.setDescription("Chéque émis par " + clientPrenom + " " + clientNom
							+ " pour la vente numéro " + venteDTO.getVentes_id());

					HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
							.getExternalContext().getRequest();
					if (request != null) {
						HttpSession session = request.getSession(false);
						Long idPointdeVente = (Long) session.getAttribute("idPointdeVente");
						chequeAEncaisser.setIdPointdeVente(idPointdeVente);
					}
					serviceTresorerie.enregistreCheque(chequeAEncaisser);

				}
				referenceVentes = "VTE000" + venteDTO.getVentes_id();
				
				return "dashboardMagasinier.xhtml";
				//return "magasinierVendreProduit.xhtml?faces-redirect=true&idBlocToShow=3"; MAJ FOR FADEL
			}
		}

		// Vente avec Credit
		if (getmontantTotalApayer() > getmontantTotalpaye() && isPayerLeResteParCredit()) {

			// setMoyenPaiement(selectedProduit);

			creditDTO = createDTOCredit(idConnectedUser, auteurVAlidation, zone);
			creditDTO.setAuteurCreditLibelle(auteurVAlidation);

			if (serviceTresorerie.vendreProduitAvecCredit(selectedProduitListAvendre, venteDTO, creditDTO)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
						"Vente avec credit enregistré avec succès, !  les détails de la vente sont accesibles depuis le module de vente.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				showBlocInfosConfirmation = 1;
				// selectedProduit.setPrixTotal(selectedProduit.getQuantite() *
				// selectedProduit.getPrixUnitaire());

				if (montantPayeParCheque != 0) {
					ChequeDTO chequeAEncaisser = new ChequeDTO();

					chequeAEncaisser.setNumeroCheque(numeroCheque);
					chequeAEncaisser.setBanqueEmetrice(banqueEmetriceCheque);
					chequeAEncaisser.setMontantCheque(montantPayeParCheque);
					chequeAEncaisser.setDescription("Chéque émis pour la vente numéro " + venteDTO.getVentes_id());

					HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
							.getExternalContext().getRequest();
					if (request != null) {
						HttpSession session = request.getSession(false);
						Long idPointdeVente = (Long) session.getAttribute("idPointdeVente");
						chequeAEncaisser.setIdPointdeVente(idPointdeVente);
					}

					serviceTresorerie.enregistreCheque(chequeAEncaisser);

				}
				referenceVentes = "VTE000" + venteDTO.getVentes_id();
				
				return "dashboardMagasinier.xhtml";
				//return "magasinierVendreProduit.xhtml?faces-redirect=true&idBlocToShow=3"; MAJ  FADEL
			}
		}

		else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Une erreur est survenue lors de la vente du produit");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "magasinierVendreProduit.xhtml?faces-redirect=true&idBlocToShow=2";
		}
		return zone;
	}
	
	
	
	
	
	
	public String modifierIntrantBySuperviseur() throws EntityDBDAOException {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (selectedProduitListAvendre.size() != 1) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner un seul intrant à modifier");
			return "dashboardMagasinier.xhtml?faces-redirect=true";
		}
		
		// ON verifie le superviseur a le droite de  modifier l'intrant (il ne peut modifier que les intrant quil a crée et tarifié (Intrant -> Tarificateur -> Persone)
		try {
			connectedUserPersonne = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserPersonneid");
			
			if (!selectedProduitListAvendre.get(0).getAuteurCreationIntrant().equals(connectedUserPersonne)) {
				showMessage(FacesMessage.SEVERITY_WARN, "Vous n'avez pas l'autorisation de modifier cet intrant car il ne provient pas de votre stock: Merci de contacter l'administrateur pour le faire");
				return "dashboardMagasinier.xhtml?faces-redirect=true";
			}
			
		} catch (Exception e) { e.printStackTrace();}

		
		libelleIntrantAmodifier  =  selectedProduitListAvendre.get(0).getLibelle();
		quantiteAmodifier = selectedProduitListAvendre.get(0).getQuantite();
		prixUnitaireAmodifier = selectedProduitListAvendre.get(0).getPrixUnitaire();
		
		return "ACInventaire.xhtml?faces-redirect=true&idBlocToShow=2";
	}

	public String modifierIntrantBySuperviseurProcess() throws EntityDBDAOException {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (selectedProduitListAvendre.size() != 1) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner un seul intrant à modifier");
			return "dashboardMagasinier.xhtml?faces-redirect=true";
		}
		
		
		
		selectedProduitListAvendre.get(0).setQuantite(quantiteAmodifier);
		selectedProduitListAvendre.get(0).setPrixUnitaire(prixUnitaireAmodifier);
		
		if (programmAgricoleService.updateStockResiduelBySuperviseur(selectedProduitListAvendre.get(0)))
			return "dashboardMagasinier.xhtml?faces-redirect=true";
		else
			return "ACInventaire.xhtml?faces-redirect=true&idBlocToShow=2";
		
	}
	
	

	

	// TODO : a externaliser ddans le bean de agentCollecte : Demo demain donc
	// pas le temps de refaire le mapping
	IntrantDTO stockResiduelSelectedAgentCollecte;

	public String vendreProduitForAgentCollecte() throws EntityDBDAOException {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		if (stockResiduelSelectedAgentCollecte == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner au moins un produit à vendre !");
			return "dashboardAgentcollecte.xhtml?faces-redirect=true";
		}
		showBlocInfosConfirmation = 0;
		return "coordinateurVenteEnregistrement.xhtml?faces-redirect=true";
	}


	public String callVendreProduitServiceAgentCollecte() throws EntityDBDAOException {
		showBlocInfosErrors = 0;

		String auteurVAlidation = null;
		Long connectedUseridStock = null;
		Long idConnectedUser = null;

		String zone = null;
		VentesDTO venteDTO = new VentesDTO();

		if (stockResiduelSelectedAgentCollecte == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Selectionner au moins un produit à vendre !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "coordinateurVenteEnregistrement.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		// Verif numero Cheque et si valeur cheque saisie
		if (montantPayeParCheque != 0 && numeroCheque.trim().equals("")) {
			
			displayBlock("error", "le numéro de chéque est obligatoire ");
			return "coordinateurVenteEnregistrement.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		// Verif banque emetrice si valeur cheque saisie
		if (montantPayeParCheque != 0 && banqueEmetriceCheque.equals("0")) {
			displayBlock("error", "la  banque émettrice du chéque est obligatoire ");
			return "coordinateurVenteEnregistrement.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		try {
			auteurVAlidation = SessionManagedBean.getSessionDataByTag("connectedUserName");
			idConnectedUser = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserPersonneid");
			zone = SessionManagedBean.getSessionDataByTag("connectedUserPointPhysique");
			
			Long connectedUserProfilID = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserProfilID");
			/**** Les superviseurs ont plusieurs stocks , 
			 * chaque intrant est lié au stock du point de collecte (appelé point de vente par sedab )
			 * 
			 * idstock =  point collecte -> stock du point de collecte -> id point de collecte
			 *  ***/
			if(connectedUserProfilID != null && connectedUserProfilID == 4)
			{
				// si superviseur : zone de vente = point de vente slection 
				connectedUseridStock = stockResiduelSelectedAgentCollecte.getIdStockResiduel(); 
				venteDTO.setIdProduitAssocie(stockResiduelSelectedAgentCollecte.getIdStockResiduel());
				venteDTO.setZoneVente(stockResiduelSelectedAgentCollecte.getLibelleCommune());
			}
			else
			{
				// les autres profil on un seul point de vente : donc c celui par defaut :
				connectedUseridStock = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserStockid");
				venteDTO.setZoneVente(zone);
			}
			
			venteDTO.setIdPointDeVente(SessionManagedBean.getSessionLongValuesDataByTag("idPointdeVente"));
			venteDTO.setMontantVente(getmontantTotalApayer());
			venteDTO.setMontantEncaisse(getmontantTotalpaye());
			venteDTO.setIdstockVente(connectedUseridStock);
			venteDTO.setVendeurLibelle(auteurVAlidation);
			venteDTO.setIdvendeur(idConnectedUser);
			venteDTO.setClient(clientPrenom + " " + clientNom);
			venteDTO.setTelclient(clientTel);
			venteDTO.setAdresseclient(clientAdresse);
			venteDTO.setMontantEncaisseParBonDeSubvention(montantPayeParBonSubvention);
			venteDTO.setMontantEncaisseParCheque(montantPayeParCheque);
			venteDTO.setMontantEncaisseParEspece(montantPayeParEspeces);

			Date datejour = new Date();
			SimpleDateFormat dt1 = new SimpleDateFormat("dd-mm-yyyy");
			venteDTO.setDateVente(dt1.format(datejour));
			
			 dateVente = dt1.format(datejour);  
			 

		} catch (Exception e) {
			Log.error("Impossible de recuperer les donnees de sessions " + e.getMessage());
			e.printStackTrace();
		}

		// Verification quantite produit restant avant vente

			if (stockResiduelSelectedAgentCollecte.getQuantite() < quantiteProduitAvendre) {
				
				displayBlock("warn",  "Le stock du produit restant ( "
						+ selectedProduit.getQuantite() + " Kg ) est inférieur à la quantité saisie !");

				return "coordinateurVenteEnregistrement.xhtml?faces-redirect=true&idBlocToShow=2";
			}

		// verif somme a payer manquante
		if (getmontantTotalApayer() > getmontantTotalpaye() && !isPayerLeResteParCredit()) {
			
			
			displayBlock("warn",  "La somme encaissée " + formatFloatToCFA(getmontantTotalpaye())
			+ " est inférieure au montant de la vente  " + formatFloatToCFA(getmontantTotalApayer())
			+ ",  cocher l'option ' Enregistrer le reste comme crédit' pour que le client contracte un crédit  de  "
			+ formatFloatToCFA(getmontantTotalApayer() - getmontantTotalpaye()));
			
			return "coordinateurVenteEnregistrement.xhtml?faces-redirect=true&idBlocToShow=2";
		}

		// Verification exces encaisssmenet
		Float prixTotalaPAYER =  stockResiduelSelectedAgentCollecte.getPrixProducteur() * quantiteProduitAvendre;
		Float totalEncaisse =  getmontantTotalpaye();

		if (totalEncaisse > prixTotalaPAYER) {
			displayBlock("warn",  "La somme encaissée " + formatFloatToCFA(getmontantTotalpaye())
			+ " est supérieure  au montant total à payer  "
			+ formatFloatToCFA(prixTotalaPAYER));
			return "coordinateurVenteEnregistrement.xhtml?faces-redirect=true&idBlocToShow=2";
		}

		// ventes sans credit
		if (getmontantTotalApayer() <= getmontantTotalpaye()) {
			stockResiduelSelectedAgentCollecte.setQuantiteAtransferer(new Double (quantiteProduitAvendre));// quantiteProduitAvendre
			
			if (serviceTresorerie.vendreProduitFromPointdeVente(stockResiduelSelectedAgentCollecte  , venteDTO)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
						"Produit vendu !  Télécharger la facture");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				showBlocInfosConfirmation = 1;
				// selectedProduit.setPrixTotal(selectedProduit.getQuantite() *
				// selectedProduit.getPrixUnitaire());
				
				if (montantPayeParCheque != 0) {
					ChequeDTO chequeAEncaisser = new ChequeDTO();

					chequeAEncaisser.setNumeroCheque(numeroCheque);
					chequeAEncaisser.setBanqueEmetrice(banqueEmetriceCheque);
					chequeAEncaisser.setMontantCheque(montantPayeParCheque);
					chequeAEncaisser.setDescription("Chéque émis par " + clientPrenom + " " + clientNom + " pour la vente numéro " + venteDTO.getVentes_id());
					
					HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
							.getRequest();
					if (request != null) {
						HttpSession session = request.getSession(false);
						Long idPointdeVente = (Long) session.getAttribute("idPointdeVente");
						chequeAEncaisser.setIdPointdeVente(idPointdeVente);
					}
					serviceTresorerie.enregistreCheque(chequeAEncaisser);

				}
				referenceVentes  = "VTE000" + venteDTO.getVentes_id(); 

				quantiteProduitAvendre  = 0L;
				clientPrenom = "";
				clientNom = "";
				clientTel = "";
				clientAdresse = "";
				stockResiduelSelectedAgentCollecte  = new IntrantDTO();
				montantPayeParEspeces = 0f;
				montantPayeParNature = 0f;
				montantPayeParBonSubvention = 0f;
				montantPayeParCheque = 0f;
				banqueEmetriceCheque = "";
				return "dashboardAgentcollecte.xhtml?faces-redirect=true&idBlocToShow=3";
			}
		}

		// Vente avec Credit
		if (getmontantTotalApayer() > getmontantTotalpaye() && isPayerLeResteParCredit()) {

			// setMoyenPaiement(selectedProduit);

			creditDTO = createDTOCredit(idConnectedUser, auteurVAlidation, zone);
			creditDTO.setAuteurCreditLibelle(auteurVAlidation);

			if (serviceTresorerie.vendreProduitAvecCredit(selectedProduitListAvendre, venteDTO, creditDTO)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
						"Produit vendu !  Télécharger la facture");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				showBlocInfosConfirmation = 1;
				// selectedProduit.setPrixTotal(selectedProduit.getQuantite() *
				// selectedProduit.getPrixUnitaire());

				if (montantPayeParCheque != 0) {
					ChequeDTO chequeAEncaisser = new ChequeDTO();

					chequeAEncaisser.setNumeroCheque(numeroCheque);
					chequeAEncaisser.setBanqueEmetrice(banqueEmetriceCheque);
					chequeAEncaisser.setMontantCheque(montantPayeParCheque);
					chequeAEncaisser.setDescription("Chéque émis pour la vente numéro " + venteDTO.getVentes_id());
					
					HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
							.getRequest();
					if (request != null) {
						HttpSession session = request.getSession(false);
						Long idPointdeVente = (Long) session.getAttribute("idPointdeVente");
						chequeAEncaisser.setIdPointdeVente(idPointdeVente);
					}
					
					serviceTresorerie.enregistreCheque(chequeAEncaisser);

				}
				referenceVentes  = "VTE000" + venteDTO.getVentes_id(); 
				quantiteProduitAvendre  = 0L;
				clientPrenom = "";
				clientNom = "";
				clientTel = "";
				clientAdresse = "";
				stockResiduelSelectedAgentCollecte  = new IntrantDTO();
				montantPayeParEspeces = 0f;
				montantPayeParNature = 0f;
				montantPayeParBonSubvention = 0f;
				montantPayeParCheque = 0f;
				banqueEmetriceCheque = "";
				
				return "coordinateurVenteEnregistrement.xhtml?faces-redirect=true&idBlocToShow=3";
			}
		}

		else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Une erreur est survenue lors de la vente du produit");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "coordinateurVenteEnregistrement.xhtml?faces-redirect=true&idBlocToShow=2";
		}
		return zone;
	}

	public boolean onEdit() throws Exception {
		if (stockResiduelSelectedAgentCollecte != null)
			stockResiduelSelectedAgentCollecte.setLibelleCommune("");
		return true;
	}

	public String initTransfertIntrantFromPointToMagasin() {
		if (stockResiduelSelectedAgentCollecte == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Selectionner au moins un intrant à transferer !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "dashboardAgentcollecte.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		return "coordinateurTransfertIntrantVersMagasin.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	List<BonDeLivraisonDTO> listStock;

	public String initloadBLStock() {
		if (listStock == null)
			callServiceRecuperationBLEnvoye();

		return "ACListeCollecteTransferts.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public String initVersmentAG() {

		return "ACGestionBanque.xhtml?faces-redirect=true&idBlocToShow=2";
	}

	public void callServiceRecuperationBLEnvoye() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			Long connectedUseridStock = (Long) session.getAttribute("connectedUserStockid");

			try {
				listStock = programmAgricoleService.ladBLEnvoyesByStock(connectedUseridStock);
			} catch (EntityDBDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String initTransfertIntrantFromPointToPointdeVente() {
		if (stockResiduelSelectedAgentCollecte == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Selectionner au moins un intrant à transferer !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "dashboardAgentcollecte.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		return "coordinateurTransfertIntrantVersPointdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	/****** DEPLACEMENT DE STOCK DE POint de vente VERS MAGSIN SEDAB ***/

	Long idMagasin;
	IntrantDTO selectedIntrantTotransfer;
	Double quantiteAtranferer;
	String numeroLVManuel;
	String numeroBLManuel;

	String transportTotransfert = "";
	String camionTotransfer = "";
	String chauufeurTotransfer = "";

	public String deplacerStockFromPOINTdEVENTEToMagasin() {

		boolean valid = false;

		if (idMagasin == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Merci de selectionner un magasin (destination).");
			return "coordinateurTransfertIntrantVersMagasin.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (stockResiduelSelectedAgentCollecte.getQuantite() < quantiteAtranferer) {
			showMessage(FacesMessage.SEVERITY_WARN,
					"La quantité à transférer doit etre inférieure au celle disponible dans votre stock");
			return "coordinateurTransfertIntrantVersMagasin.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (transportTotransfert.trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le transport  est obligatoire");
			return "coordinateurTransfertIntrantVersMagasin.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (camionTotransfer.trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le camion  est obligatoire");
			return "coordinateurTransfertIntrantVersMagasin.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		if (chauufeurTotransfer.trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le chauffeur est obligatoire");
			return "coordinateurTransfertIntrantVersMagasin.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		try {
			selectedIntrantTotransfer = new IntrantDTO();

			selectedIntrantTotransfer.setTransportTotransfert(transportTotransfert);
			selectedIntrantTotransfer.setCamionTotransfer(camionTotransfer);
			selectedIntrantTotransfer.setChauufeurTotransfer(chauufeurTotransfer);
			selectedIntrantTotransfer.setBlManuel(numeroBLManuel);
			selectedIntrantTotransfer.setLvManuel(numeroLVManuel);

			selectedIntrantTotransfer.setIdProduit(stockResiduelSelectedAgentCollecte.getIdProduit());
			selectedIntrantTotransfer.setIdStockResiduel(stockResiduelSelectedAgentCollecte.getIdStockResiduel());

			// sourceType = 1 stock residuel reçu via des MEP ////// 2 = intrant
			// collecté/acheté en local
			selectedIntrantTotransfer.setSourceType(stockResiduelSelectedAgentCollecte.getSourceType());
			selectedIntrantTotransfer
					.setIdpointdeVente(SessionManagedBean.getSessionLongValuesDataByTag("connectedUserStockid"));

			if (selectedIntrantTotransfer.getSourceType() == 2)
				valid = programmAgricoleService.transfererStockFromPointDeVenteToMagasin(selectedIntrantTotransfer,
						idMagasin, quantiteAtranferer);
			else if (selectedIntrantTotransfer.getSourceType() == 1)
				valid = programmAgricoleService.transfererStockFromPointDeVenteToMagasinModeStock(
						selectedIntrantTotransfer, idMagasin, quantiteAtranferer);

			if (valid) {
				showMessage(FacesMessage.SEVERITY_INFO, "Le déplacement du stock est enregistré avec succès.");

				return "coordinateurTransfertIntrantVersMagasin.xhtml?faces-redirect=true&idBlocToShow=3";

			} else {
				showMessage(FacesMessage.SEVERITY_ERROR, "Erreur survenue lors du déplacement du stock.");
				return "coordinateurTransfertIntrantVersMagasin.xhtml?faces-redirect=true&idBlocToShow=1";
			}

		} catch (Exception e) {
			Log.error("Erreur survenue lors de la lecture des details de l'ordre " + e.getMessage());
		}
		showMessage(FacesMessage.SEVERITY_ERROR, "Une erreur technique est survenue");
		return "coordinateurTransfertIntrantVersMagasin.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	private String pointdeVenteAutocomplete;

	public String deplacerStockFromPOINTdEVENTEToPointdeVente() {

		boolean valid = false;

		if (stockResiduelSelectedAgentCollecte.getQuantite() < quantiteAtranferer) {
			showMessage(FacesMessage.SEVERITY_WARN,
					"La quantité à transférer doit etre inférieure au celle disponible dans votre stock");
			return "coordinateurTransfertIntrantVersPointdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (numeroBLManuel.trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le numéro de BL  est obligatoire");
			return "coordinateurTransfertIntrantVersPointdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (transportTotransfert.trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le transport  est obligatoire");
			return "coordinateurTransfertIntrantVersPointdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		if (camionTotransfer.trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le camion  est obligatoire");
			return "coordinateurTransfertIntrantVersPointdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		if (chauufeurTotransfer.trim().equals("")) {
			showMessage(FacesMessage.SEVERITY_WARN, "Le chauffeur est obligatoire");
			return "coordinateurTransfertIntrantVersPointdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
		}

		try {

			Long idPointDevente = commonService.loadIPoitDeVenteIdByName(pointdeVenteAutocomplete);
			if (idPointDevente == null) {
				showMessage(FacesMessage.SEVERITY_WARN,
						"Le point de vente <i>" + pointdeVenteAutocomplete + "<i/> n'existe pas dans la plateforme");
				return "coordinateurTransfertIntrantVersPointdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
			}

			if (programmAgricoleService.loadBlByNumeroBl(numeroBLManuel) != null) {
				showMessage(FacesMessage.SEVERITY_WARN,
						"Le numero de BL <i>" + numeroBLManuel + "<i/> existe déjà dans la plateforme");
				return "coordinateurTransfertIntrantVersPointdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
			}

			selectedIntrantTotransfer = new IntrantDTO();

			selectedIntrantTotransfer.setTransportTotransfert(transportTotransfert);
			selectedIntrantTotransfer.setCamionTotransfer(camionTotransfer);
			selectedIntrantTotransfer.setChauufeurTotransfer(chauufeurTotransfer);
			selectedIntrantTotransfer.setBlManuel(numeroBLManuel);
			selectedIntrantTotransfer.setLvManuel(numeroLVManuel);

			selectedIntrantTotransfer.setIdProduit(stockResiduelSelectedAgentCollecte.getIdProduit());
			selectedIntrantTotransfer.setIdStockResiduel(stockResiduelSelectedAgentCollecte.getIdStockResiduel());
			selectedIntrantTotransfer.setSourceType(stockResiduelSelectedAgentCollecte.getSourceType());
			selectedIntrantTotransfer.setIdPoindCollecte(idPointDevente);

			if (selectedIntrantTotransfer.getSourceType() == 2)
				valid = programmAgricoleService.transfererStockFromPointDeVenteToMagasin(selectedIntrantTotransfer,
						idMagasin, quantiteAtranferer);
			else if (selectedIntrantTotransfer.getSourceType() == 1)
				valid = programmAgricoleService.transfererStockFromPointDeVenteToMagasinModeStock(
						selectedIntrantTotransfer, idMagasin, quantiteAtranferer);

			if (valid) {
				showMessage(FacesMessage.SEVERITY_INFO, "Le déplacement du stock est enregistré avec succès.");

				selectedIntrantTotransfer = new IntrantDTO();
				idMagasin = null;
				return "coordinateurTransfertIntrantVersPointdeVente.xhtml?faces-redirect=true&idBlocToShow=3";

			} else {
				showMessage(FacesMessage.SEVERITY_ERROR, "Erreur survenue lors du déplacement du stock.");
				return "coordinateurTransfertIntrantVersPointdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
			}

		} catch (Exception e) {
			Log.error("Erreur survenue lors de la lecture des details de l'ordre " + e.getMessage());
		}
		showMessage(FacesMessage.SEVERITY_ERROR, "Une erreur technique est survenue");
		return "coordinateurTransfertIntrantVersPointdeVente.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	private CreditDTO createDTOCredit(Long idConnectedUser, String auteurVAlidation, String zone) {
		float totalEncaisse = getmontantTotalpaye();
		float totalMontantApayer = getmontantTotalApayer();

		creditDTO = new CreditDTO();
		// creditDTO.setAuteurCreditLibelle(selectedProduit.getClient());
		creditDTO.setNomsouscripteur(clientPrenom + " " + clientNom);
		creditDTO.setTelSouscripteur(clientTel);
		creditDTO.setAuteurCreditId(idConnectedUser);
		creditDTO.setAuteurCreditLibelle(auteurVAlidation);
		creditDTO.setZoneCredit(zone);
		creditDTO.setDateContraction(new Date().toString()); // date du jour
		creditDTO.setMontantInitialCredit(totalMontantApayer - totalEncaisse);
		creditDTO
				.setResumeCredit("Crédit de " + formatFloatToCFA(totalMontantApayer - totalEncaisse) + " contracté par "
						+ clientPrenom + " " + clientNom + " à la date du " + new Date() + " au '" + zone + "'");
		return creditDTO;
	}

	/*****/
	public ProduitDTO setMoyenPaiement(ProduitDTO selectedProduit) throws EntityDBDAOException {

		// Moyen de Paiement
		if (selectedProduit.getMontantPayeParBonSubvention() != 0)
			selectedProduit.setMoyenDePaiement("Bon subvention");

		if (selectedProduit.getMontantPayeParCheque() != 0)
			selectedProduit.setMoyenDePaiement(selectedProduit.getMoyenDePaiement() + "-" + " Chéque ");

		if (selectedProduit.getMontantPayeParEspeces() != 0)
			selectedProduit.setMoyenDePaiement(selectedProduit.getMoyenDePaiement() + "-" + " Espéces ");

		if (selectedProduit.getMontantPayeParNature() != 0)
			selectedProduit.setMoyenDePaiement(selectedProduit.getMoyenDePaiement() + "-" + " Nature ");

		return selectedProduit;
	}

	public void getDetailsOfSelectedBL() throws EntityDBDAOException {
		selectedBLDTO = programmAgricoleService.getAllProduitsFromIdBL(selectedBLDTO.getId());

	}

	CreditDTO selectedCreditDTO;

	public String showDeatilsCredit() {

		if (selectedCreditDTO == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner un crédit ");
			return "magasisnierGestionCredits.xhtml?faces-redirect=true";
		}

		return "magasisnierGestionCredits.xhtml?faces-redirect=true&idBlocToShow=5b";

	}

	public Float getTotalVentePV() {
		return totalVentePV;
	}

	public void setTotalVentePV(Float totalVentePV) {
		this.totalVentePV = totalVentePV;
	}

	List<AvanceCreditDTO> listeAvance;

	public String callEnregistrerCredit() {

		if (selectedCreditDTO == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner un crédit ");
			return "magasisnierGestionCredits.xhtml?faces-redirect=true";
		}

		listeAvance = getlistAvanceByDetailsSelectedCredit(selectedCreditDTO.getCredit_id());
		return "magasisnierGestionCredits.xhtml?faces-redirect=true&idBlocToShow=5c";

	}

	AvanceCreditDTO avanceDTO = new AvanceCreditDTO();

	public String callServiceAvanceSurCredit() {

		Map<Long, String> save = serviceTresorerie.enregistrerAvance(avanceDTO, selectedCreditDTO.getCredit_id());

		Long returnType = null;
		String returnessage = null;

		for (Map.Entry<Long, String> entry : save.entrySet()) {
			returnType = entry.getKey();
			returnessage = entry.getValue();
		}

		if (returnType == 1L) {
			showMessage(FacesMessage.SEVERITY_INFO, returnessage);

			return "magasisnierGestionCredits.xhtml?faces-redirect=true";

		} else {

			showMessage(FacesMessage.SEVERITY_WARN, returnessage);

			return "magasisnierGestionCredits.xhtml?faces-redirect=true&idBlocToShow=5b";
		}
	}

	public List<AvanceCreditDTO> getlistAvanceByDetailsSelectedCredit(Long idCredit) {

		try {
			return serviceTresorerie.getListAvancebyIdCredit(selectedCreditDTO.getCredit_id()).getListAvanceCredits();

		} catch (EntityDBDAOException e) {

			return null;
		}

	}

	public String getDateVente() {
		return dateVente;
	}

	public void setDateVente(String dateVente) {
		this.dateVente = dateVente;
	}

	public String getReferenceVentes() {
		return referenceVentes;
	}

	public void setReferenceVentes(String referenceVentes) {
		this.referenceVentes = referenceVentes;
	}

	public float getmontantTotalApayer() {
		float tmp = 0;
		if (selectedProduitListAvendre != null) {
			for (ProduitDTO p : selectedProduitListAvendre) {
				tmp += p.getQuantiteProduitAvendre() != null ? p.getQuantiteProduitAvendre() * p.getPrixUnitaire() : 0;
				p.setPrixTotal(new Double(tmp));
			}
		}
		 double arrondi = Math.floor(tmp);
		return  (float) (arrondi); 
	}

	public float getmontantTotalpaye() {
		return montantPayeParBonSubvention + montantPayeParCheque + montantPayeParEspeces + montantPayeParNature + montantPayeParBLP;
	}

	public float montantEspcesCaisse(String mode) // 1 cheque , 2 Especes , 3
													// Bon
	{
		Long idPointDevente;
		try {
			idPointDevente = SessionManagedBean.getSessionLongValuesDataByTag("idPointdeVente");
			Map<String, Float> allMontant = serviceTresorerie.evaluerRepartitionCaisseByModePaiement(idPointDevente);

			if (mode.equals(1))
				return allMontant.get("ESPECES");
			else if (mode.equals(2))
				return allMontant.get("CHEQUE");
			else if (mode.equals(3))
				return allMontant.get("BON");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	/********** BL Services ***************/

	public String initTraitementBL() {

		if (selectedBLDTO == null || selectedBLDTO.getId() == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner bl  à voir");
			return "magasisnierGestionBL.xhtml?faces-redirect=true";
		}

		try {
			selectedBLDTO = programmAgricoleService.getAllProduitsFromIdBL(selectedBLDTO.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "magasisnierGestionBL.xhtml?faces-redirect=true&idBlocToShow=2";

	}

	/********** COMMANDES Services ***************/

	public String loadDetailsSelectedCMD() {

		if (selectedCommandeDTO == null || selectedCommandeDTO.getIdCommande() == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner une commande");
			return "magasisnierGestionCommandes.xhtml?faces-redirect=true";
		}

		try {
			// recuperation des produits de la commande selectionne car les
			// produits sont en LAZY lAZY LOADING
			selectedCommandeDTO = programmAgricoleService.loadCommandeInfosByIdCMD(selectedCommandeDTO.getIdCommande());

			if (selectedCommandeDTO != null && selectedCommandeDTO.getStatutCMD().equals(ConstantPGCA.CMD_A_TRAITER))
				showbtnValidationsCMD = true;

			return "magasisnierGestionCommandes.xhtml?faces-redirect=true&idBlocToShow=2";
		} catch (Exception e) {
			Log.error("Erreur survenue lors de la lecture des details de la commande " + e.getMessage());
			e.printStackTrace();
		}

		return "";
	}

	public String accepterCommande() {

		if (selectedCommandeDTO == null || selectedCommandeDTO.getIdCommande() == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner une commande");
			return "magasisnierGestionCommandes.xhtml?faces-redirect=true";
		}

		try {

			if (selectedCommandeDTO.getStatus() == 1) {
				showMessage(FacesMessage.SEVERITY_WARN, "La commande " + selectedCommandeDTO.getReferenceCMD() + "  de "
						+ selectedCommandeDTO.getClientNom() + "est déjà traitée .");

				return "magasisnierGestionCommandes.xhtml";
			}

			// Prerequis Verif stock dispo avant de traitement de la commande
			if (programmAgricoleService.verifStock(selectedCommandeDTO.getIdStockSortant(),
					selectedCommandeDTO.getListProduitsDTOtoCreate())) {
				if (programmAgricoleService.substractProduitFromStock(selectedCommandeDTO.getIdStockSortant(),
						selectedCommandeDTO.getListProduitsDTOtoCreate())) {
					if (programmAgricoleService.accepterCommande(selectedCommandeDTO.getIdCommande())) {

						showMessage(FacesMessage.SEVERITY_INFO, "La commande " + selectedCommandeDTO.getReferenceCMD()
								+ "  de " + selectedCommandeDTO.getClientNom() + "est traitée avec succès.");

						return "magasisnierGestionCommandes.xhtml?faces-redirect=true&idBlocToShow=3";
					}
				}

			} else {
				showMessage(FacesMessage.SEVERITY_WARN,
						"Votre stock actuel ne vous permet pas de traiter la commande (Rupture de stock)");
				return "magasisnierGestionCommandes.xhtml?faces-redirect=true";
			}
		} catch (Exception e) {
			Log.error("Erreur survenue lors de la lecture des details de la commande " + e.getMessage());
			showMessage(FacesMessage.SEVERITY_ERROR,
					"Impossible de traiter la commande car le stock disponible n'est pas suffisant ");
			e.printStackTrace();
		}

		return "";
	}

	/******* OL SERVICES *****/

	public List<CommandeDTO> getAllOrdresDelivraisons() { /**
															 * OL en attente de
															 * traitement
															 **/
		Long idPointdeVente;
		try {
			idPointdeVente = SessionManagedBean.getSessionLongValuesDataByTag("idPointdeVente");

			return magasinierServices.getAllOrdresDelivraisons(idPointdeVente);
		} catch (Exception e) {
			Log.error("Erreur Surveneue pendant la recuperation des OL pour le magasiniers");
			e.printStackTrace();
		}
		return null;

	}

	public List<CommandeDTO> getAllOrdresValideByIdPointDeVente() { /**
																	 * OL en
																	 * valide
																	 **/
		Long idPointdeVente;
		try {
			idPointdeVente = SessionManagedBean.getSessionLongValuesDataByTag("idPointdeVente");

			return magasinierServices.getAllOrdresValideByIdPointDeVente(idPointdeVente);
		} catch (Exception e) {
			Log.error("Erreur Surveneue pendant la recuperation des OL pour le magasiniers");
			e.printStackTrace();
		}
		return null;

	}

	/** OL en attente de traitement **/

	public String loadDetailsSelectedOrdre() {

		if (commandeDTO == null || commandeDTO.getIdCommande() == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner un ordre de livraison");
			return "magasinierGestionOL.xhtml?faces-redirect=true&idBlocToShow=5b";
		}
		try {
			commandeDTO = programmAgricoleService.loadOrdreInfosById(commandeDTO.getIdCommande());
			return "magasinierGestionOL.xhtml?faces-redirect=true&idBlocToShow=5b";
		} catch (Exception e) {
			Log.error("Erreur survenue lors de la lecture des details de l'ordre " + e.getMessage());
			e.printStackTrace();
		}

		showMessage(FacesMessage.SEVERITY_WARN, "Impossible de lire les détails de l'ordre");

		return "magasinierGestionOL.xhtml?faces-redirect=true&idBlocToShow=5b";
	}

	public String validerelectedOrdre() {

		if (commandeDTO == null || commandeDTO.getIdCommande() == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Selectionner un ordre de livraison");
			return "magasinierGestionOL.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		try {
			commandeDTO = programmAgricoleService.loadOrdreInfosById(commandeDTO.getIdCommande());

			Long stockId = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserStockid");

			if (!programmAgricoleService.verifStock(stockId, commandeDTO.getListProduitsDTOtoCreate())) {
				showMessage(FacesMessage.SEVERITY_WARN,
						"L'état actuel de votre stock ne vous permet pas de traiter l'ordre de livraison");
				return "magasinierGestionOL.xhtml?faces-redirect=true&idBlocToShow=1";
			}

			if (programmAgricoleService.substractProduitFromStock(stockId, commandeDTO.getListProduitsDTOtoCreate())) {
				if (magasinierServices.traiterOrdreDeLivraion(commandeDTO.getIdCommande())) {
					// showMessage(FacesMessage.SEVERITY_INFO, "Impossible de
					// lire les détails de l'ordre");
					return "magasinierGestionOL.xhtml?faces-redirect=true&idBlocToShow=2";
				}
			}

		} catch (Exception e) {
			Log.error("Erreur survenue lors de la lecture des details de l'ordre " + e.getMessage());
			e.printStackTrace();
		}

		showMessage(FacesMessage.SEVERITY_ERROR, "Une erreur technique est survenue  sur le serveur ...");

		return "magasinierGestionOL.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public int getNbOLtoValidate() {
		try {
			Long idPointDeVente = SessionManagedBean.getSessionLongValuesDataByTag("idPointdeVente");

			return magasinierServices.getAllOrdresDelivraisonsEnAttente(idPointDeVente).size();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	// Gestion des Ventes sur les mises en place
	List<IntrantDTO> stockResiduel;
	List<IntrantDTO> stockResiduelFiltred;
	IntrantDTO stockResiduelSelected;

	public void loadStockResiduelByIntrant() throws EntityDBDAOException {
		Long idPointdeVente;
		try {
			// connectedUserStockid
			idPointdeVente = SessionManagedBean.getSessionLongValuesDataByTag("connectedUserIdCommune");
			

			
			if (stockResiduel == null)
				stockResiduel = programmAgricoleService.loadStockResiduelByIntrant(idPointdeVente);
		} catch (Exception e) {
			Log.info("Erreur survenue lors de la recup des mep reçu");
			e.printStackTrace();
		}

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

	public float montantEspcesCheque() {
		return montantPayeParBonSubvention + montantPayeParCheque + montantPayeParEspeces + montantPayeParNature;
	}

	public void showMessage(Severity severity, String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		FacesMessage msg = new FacesMessage(severity, "", message);
		FacesContext.getCurrentInstance().addMessage(null, msg);
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

	public void setProgrammAgricoleService(IProgrammeAgricol programmAgricoleService) {
		this.programmAgricoleService = programmAgricoleService;
	}

	public List<AvanceCreditDTO> getListeAvance() {
		return listeAvance;
	}

	public void setListeAvance(List<AvanceCreditDTO> listeAvance) {
		this.listeAvance = listeAvance;
	}

	public IMagasinierService getMagasinierServices() {
		return magasinierServices;
	}

	public void setMagasinierServices(IMagasinierService magasinierServices) {
		this.magasinierServices = magasinierServices;
	}

	public CreditDTO getSelectedCreditDTO() {
		return selectedCreditDTO;
	}

	public void setSelectedCreditDTO(CreditDTO selectedCreditDTO) {
		this.selectedCreditDTO = selectedCreditDTO;
	}

	public Long getSelectdIdProduit() {
		return selectdIdProduit;
	}

	public void setSelectdIdProduit(Long selectdIdProduit) {
		this.selectdIdProduit = selectdIdProduit;
	}

	public AvanceCreditDTO getAvanceDTO() {
		return avanceDTO;
	}

	public void setAvanceDTO(AvanceCreditDTO avanceDTO) {
		this.avanceDTO = avanceDTO;
	}

	public ProduitDTO getSelectedProduit() {
		return selectedProduit;
	}

	public void setSelectedProduit(ProduitDTO selectedProduit) {
		this.selectedProduit = selectedProduit;
	}

	public BonDeLivraisonDTO getSelectedBLDTO() {
		return selectedBLDTO;
	}

	public void setSelectedBLDTO(BonDeLivraisonDTO selectedBLDTO) {
		this.selectedBLDTO = selectedBLDTO;
	}

	public Long getIdselectedBLDTO() {
		return idselectedBLDTO;
	}

	public void setIdselectedBLDTO(Long idselectedBLDTO) {
		this.idselectedBLDTO = idselectedBLDTO;
	}

	public List<ProduitDTO> getListofBl() {
		return listofBl;
	}

	public void setListofBl(List<ProduitDTO> listofBl) {
		this.listofBl = listofBl;
	}

	public int getNombreBlAvalider() {
		return nombreBlAvalider;
	}

	public void setNombreBlAvalider(int nombreBlAvalider) {
		this.nombreBlAvalider = nombreBlAvalider;
	}

	public Long getQuantiteProduitAvendre() {
		return quantiteProduitAvendre;
	}

	public void setQuantiteProduitAvendre(Long quantiteProduitAvendre) {
		this.quantiteProduitAvendre = quantiteProduitAvendre;
	}

	public int getShowBlocInfosConfirmation() {
		return showBlocInfosConfirmation;
	}

	public void setShowBlocInfosConfirmation(int showBlocInfosConfirmation) {
		this.showBlocInfosConfirmation = showBlocInfosConfirmation;
	}

	/***** BLOC INFO:error/warn ****/
	int showBlocInfosErrors;
	String showBlocInfosErrorsData;
	String showBlocInfosStyleClass;
	String showBlocInfosStyleIcon;

	/***
	 * 
	 * @param severite
	 *            : (Erreur , avertissement , confirmation)
	 * @return
	 */
	public String displayBlock(String severite, String msg) {

		showBlocInfosErrors = 1;
		showBlocInfosErrorsData = msg;

		if (severite.equals("error")) {
			showBlocInfosStyleClass = "blockINfoErreur";
			showBlocInfosStyleIcon = "error.ico";
		} else if (severite.equals("warn")) {
			showBlocInfosStyleClass = "blockINfoConfirmation";
			showBlocInfosStyleIcon = "warn.png";
		} else if (severite.equals("conf")) {
		}
		showBlocInfosStyleClass = "blockINfoConfirmation";
		showBlocInfosStyleIcon = "warn.png";

		return showBlocInfosStyleClass;
	}

	public String getShowBlocInfosStyleClass() {
		return showBlocInfosStyleClass;
	}

	public void setShowBlocInfosStyleClass(String showBlocInfosStyleClass) {
		this.showBlocInfosStyleClass = showBlocInfosStyleClass;
	}

	public String getShowBlocInfosErrorsData() {
		return showBlocInfosErrorsData;
	}

	public void setShowBlocInfosErrorsData(String showBlocInfosErrorsData) {
		this.showBlocInfosErrorsData = showBlocInfosErrorsData;
	}

	public int getShowBlocInfosErrors() {
		return showBlocInfosErrors;
	}

	public void setShowBlocInfosErrors(int showBlocInfosErrors) {
		this.showBlocInfosErrors = showBlocInfosErrors;
	}

	public VentesDTO getVenteDTO() {
		return venteDTO;
	}

	public void setVenteDTO(VentesDTO venteDTO) {
		this.venteDTO = venteDTO;
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
	
	
	
	Long switchedPointDeVenteId;
	
	public String stwithPointDeVente() throws EntityDBDAOException {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			
			PointDeVenteDTO pv  = programmAgricoleService.loadPointDeVenteById(switchedPointDeVenteId);
			
			if(pv != null) 
			{
				//  
				session.setAttribute("connectedUserStockid", pv.getIdStockReference() );
				session.setAttribute("idPointdeVente", pv.getIdPv());
				session.setAttribute("connectedUserPointPhysique", pv.getLibelle());
				
				session.setAttribute("connectedUserIdCommune", pv.getIdCommune());
				
			}
			

			init();
		}
		return "dashboardMagasinier.xhtml?faces-redirect=true";
	}
	
	
	
	public String stwithPointDeVenteThiagar() throws EntityDBDAOException {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			
			session.setAttribute("connectedUserStockid", 8L);
			session.setAttribute("idPointdeVente", 4L);
			session.setAttribute("connectedUserPointPhysique", "Thibar");

			init();
		}
		return "dashboardMagasinier.xhtml?faces-redirect=true";
	}
	
	public String stwithPointDeVenteSibasor() throws EntityDBDAOException {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			
			session.setAttribute("connectedUserStockid", 13L);
			session.setAttribute("idPointdeVente", 9L);
			session.setAttribute("connectedUserPointPhysique", "Sibasor");

			
			init();
		}
		return "dashboardMagasinier.xhtml?faces-redirect=true";
	}
	
	

	public CreditDTO getCreditDTO() {
		return creditDTO;
	}

	public void setCreditDTO(CreditDTO creditDTO) {
		this.creditDTO = creditDTO;
	}

	public boolean isShowDetailsSelectedBl() {
		return showDetailsSelectedBl;
	}

	public void setShowDetailsSelectedBl(boolean showDetailsSelectedBl) {
		this.showDetailsSelectedBl = showDetailsSelectedBl;
	}

	public AvanceCreditDTO getAvanceCredit() {
		return avanceCredit;
	}

	public void setAvanceCredit(AvanceCreditDTO avanceCredit) {
		this.avanceCredit = avanceCredit;
	}

	public List<ProduitDTO> getSelectedProduitListAvendre() {
		return selectedProduitListAvendre;
	}

	public void setSelectedProduitListAvendre(List<ProduitDTO> selectedProduitListAvendre) {
		this.selectedProduitListAvendre = selectedProduitListAvendre;
	}

	public List<Long> getSelectedIdProduitListAvendre() {
		return selectedIdProduitListAvendre;
	}

	public void setSelectedIdProduitListAvendre(List<Long> selectedIdProduitListAvendre) {
		this.selectedIdProduitListAvendre = selectedIdProduitListAvendre;
	}

	public float getMontantPayeParCheque() {
		return montantPayeParCheque;
	}

	public void setMontantPayeParCheque(float montantPayeParCheque) {
		this.montantPayeParCheque = montantPayeParCheque;
	}

	public float getMontantPayeParBonSubvention() {
		return montantPayeParBonSubvention;
	}

	public String getShowBlocInfosStyleIcon() {
		return showBlocInfosStyleIcon;
	}

	public void setShowBlocInfosStyleIcon(String showBlocInfosStyleIcon) {
		this.showBlocInfosStyleIcon = showBlocInfosStyleIcon;
	}

	public void setMontantPayeParBonSubvention(float montantPayeParBonSubvention) {
		this.montantPayeParBonSubvention = montantPayeParBonSubvention;
	}

	public float getMontantPayeParEspeces() {
		return montantPayeParEspeces;
	}

	public void setMontantPayeParEspeces(float montantPayeParEspeces) {
		this.montantPayeParEspeces = montantPayeParEspeces;
	}

	public float getMontantPayeParNature() {
		return montantPayeParNature;
	}

	public void setMontantPayeParNature(float montantPayeParNature) {
		this.montantPayeParNature = montantPayeParNature;
	}

	public String getClientNom() {
		return clientNom;
	}

	public void setClientNom(String clientNom) {
		this.clientNom = clientNom;
	}

	public String getClientPrenom() {
		return clientPrenom;
	}

	public void setClientPrenom(String clientPrenom) {
		this.clientPrenom = clientPrenom;
	}

	public String getClientAdresse() {
		return clientAdresse;
	}

	public void setClientAdresse(String clientAdresse) {
		this.clientAdresse = clientAdresse;
	}

	public String getClientTel() {
		return clientTel;
	}

	public void setClientTel(String clientTel) {
		this.clientTel = clientTel;
	}

	public boolean isPayerLeResteParCredit() {
		return payerLeResteParCredit;
	}

	public void setPayerLeResteParCredit(boolean payerLeResteParCredit) {
		this.payerLeResteParCredit = payerLeResteParCredit;
	}

	public ITresorerieService getServiceTresorerie() {
		return serviceTresorerie;
	}

	public void setServiceTresorerie(ITresorerieService serviceTresorerie) {
		this.serviceTresorerie = serviceTresorerie;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public VersementBanqueDTO getVersementBanque() {
		return versementBanque;
	}

	public void setVersementBanque(VersementBanqueDTO versementBanque) {
		this.versementBanque = versementBanque;
	}

	public VersementBanqueDTO getVersement() {
		return versement;
	}

	public void setVersement(VersementBanqueDTO versement) {
		this.versement = versement;
	}

	public float getMontantTotalDeposeEnBanque() {
		return montantTotalDeposeEnBanque;
	}

	public void setMontantTotalDeposeEnBanque(float montantTotalDeposeEnBanque) {
		this.montantTotalDeposeEnBanque = montantTotalDeposeEnBanque;
	}

	public CommandeDTO getSelectedCommandeDTO() {
		return selectedCommandeDTO;
	}

	public void setSelectedCommandeDTO(CommandeDTO selectedCommandeDTO) {
		this.selectedCommandeDTO = selectedCommandeDTO;
	}

	public Boolean getShowbtnValidationsCMD() {
		return showbtnValidationsCMD;
	}

	public void setShowbtnValidationsCMD(Boolean showbtnValidationsCMD) {
		this.showbtnValidationsCMD = showbtnValidationsCMD;
	}

	public String getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(String numeroCheque) {
		this.numeroCheque = numeroCheque;
	}

	public String getBanqueEmetriceCheque() {
		return banqueEmetriceCheque;
	}

	public void setBanqueEmetriceCheque(String banqueEmetriceCheque) {
		this.banqueEmetriceCheque = banqueEmetriceCheque;
	}

	public CommandeDTO getCommandeDTO() {
		return commandeDTO;
	}

	public void setCommandeDTO(CommandeDTO commandeDTO) {
		this.commandeDTO = commandeDTO;
	}

	public ProduitDTO getProduiFromORDRES() {
		return produiFromORDRES;
	}

	public void setProduiFromORDRES(ProduitDTO produiFromORDRES) {
		this.produiFromORDRES = produiFromORDRES;
	}

	public LitigesDTO getLitigeDTO() {
		return litigeDTO;
	}

	public void setLitigeDTO(LitigesDTO litigeDTO) {
		this.litigeDTO = litigeDTO;
	}

	public List<ProduitDTO> getListProduitCommande() {
		return listProduitCommande;
	}

	public void setListProduitCommande(List<ProduitDTO> listProduitCommande) {
		this.listProduitCommande = listProduitCommande;
	}

	public List<IntrantDTO> getStockResiduel() {
		return stockResiduel;
	}

	public void setStockResiduel(List<IntrantDTO> stockResiduel) {
		this.stockResiduel = stockResiduel;
	}

	public void setStockResiduelSelected(IntrantDTO stockResiduelSelected) {
		this.stockResiduelSelected = stockResiduelSelected;
	}

	public int getStockResiduelByIntrantSize() {
		return stockResiduelByIntrantSize;
	}

	public void setStockResiduelByIntrantSize(int stockResiduelByIntrantSize) {
		this.stockResiduelByIntrantSize = stockResiduelByIntrantSize;
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

	public IntrantDTO getStockResiduelSelected() {
		return stockResiduelSelected;
	}

	public List<IntrantDTO> getStockResiduelFiltred() {
		return stockResiduelFiltred;
	}

	public void setStockResiduelFiltred(List<IntrantDTO> stockResiduelFiltred) {
		this.stockResiduelFiltred = stockResiduelFiltred;
	}

	public IntrantDTO getStockResiduelSelectedAgentCollecte() {
		return stockResiduelSelectedAgentCollecte;
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

	public String getNumeroLVManuel() {
		return numeroLVManuel;
	}

	public void setNumeroLVManuel(String numeroLVManuel) {
		this.numeroLVManuel = numeroLVManuel;
	}

	public String getNumeroBLManuel() {
		return numeroBLManuel;
	}

	public void setNumeroBLManuel(String numeroBLManuel) {
		this.numeroBLManuel = numeroBLManuel;
	}

	public String getTransportTotransfert() {
		return transportTotransfert;
	}

	public void setTransportTotransfert(String transportTotransfert) {
		this.transportTotransfert = transportTotransfert;
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

	public String getPointdeVenteAutocomplete() {
		return pointdeVenteAutocomplete;
	}

	public void setPointdeVenteAutocomplete(String pointdeVenteAutocomplete) {
		this.pointdeVenteAutocomplete = pointdeVenteAutocomplete;
	}

	public List<BonDeLivraisonDTO> getListStock() {
		return listStock;
	}

	public void setListStock(List<BonDeLivraisonDTO> listStock) {
		this.listStock = listStock;
	}

	public List<VentesDTO> getFilteredVentes() {
		return filteredVentes;
	}

	public void setFilteredVentes(List<VentesDTO> filteredVentes) {
		this.filteredVentes = filteredVentes;
	}

	public Long getSwitchedPointDeVenteId() {
		return switchedPointDeVenteId;
	}



	public void setSwitchedPointDeVenteId(Long switchedPointDeVenteId) {
		this.switchedPointDeVenteId = switchedPointDeVenteId;
	}

	public int getNbPointDeVenteAffecte() {
		return nbPointDeVenteAffecte;
	}

	public void setNbPointDeVenteAffecte(int nbPointDeVenteAffecte) {
		this.nbPointDeVenteAffecte = nbPointDeVenteAffecte;
	}

	public void setStockResiduelSelectedAgentCollecte(IntrantDTO stockResiduelSelectedAgentCollecte) {
		this.stockResiduelSelectedAgentCollecte = stockResiduelSelectedAgentCollecte;
	}

	public Float getPrixUnitaireAmodifier() {
		return prixUnitaireAmodifier;
	}

	public Double getQuantiteAmodifier() {
		return quantiteAmodifier;
	}

	public String getLibelleIntrantAmodifier() {
		return libelleIntrantAmodifier;
	}

	public void setPrixUnitaireAmodifier(Float prixUnitaireAmodifier) {
		this.prixUnitaireAmodifier = prixUnitaireAmodifier;
	}

	public float getMontantPayeParBLP() {
		return montantPayeParBLP;
	}


	public Long getConnectedUserPersonne() {
		return connectedUserPersonne;
	}

	public void setMontantPayeParBLP(float montantPayeParBLP) {
		this.montantPayeParBLP = montantPayeParBLP;
	}



	public void setConnectedUserPersonne(Long connectedUserPersonne) {
		this.connectedUserPersonne = connectedUserPersonne;
	}

	public void setQuantiteAmodifier(Double quantiteAmodifier) {
		this.quantiteAmodifier = quantiteAmodifier;
	}

	public void setLibelleIntrantAmodifier(String libelleIntrantAmodifier) {
		this.libelleIntrantAmodifier = libelleIntrantAmodifier;
	}

	public String getNumeroBLP() {
		return numeroBLP;
	}

	public void setNumeroBLP(String numeroBLP) {
		this.numeroBLP = numeroBLP;
	}

}
