package sn.awi.pgca.web.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jfree.util.Log;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.service.IAffectationService;
import sn.awi.pgca.business.service.ICommonService;
import sn.awi.pgca.business.service.IProgrammeAgricol;
import sn.awi.pgca.business.service.ITresorerieService;
import sn.awi.pgca.dataModel.Intrant;
import sn.awi.pgca.web.dto.AllocationDTO;
import sn.awi.pgca.web.dto.CoupleDTO;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.ProduitDTO;

@ManagedBean(name = "agentCollecteMB")
@SessionScoped
public class AgentCollecteManagedBean {

	
	@ManagedProperty(value = "#{commonService}")
	private ICommonService commonService;

	@ManagedProperty(value = "#{programmeAgricolService}")
	private IProgrammeAgricol programmAgricoleService;

	@ManagedProperty(value = "#{servicesAffectation}")
	private IAffectationService affectationServices;

	@ManagedProperty(value = "#{tresorerieService}")
	private ITresorerieService serviceTresorerie;

	private IntrantDTO intrantDtoToCreate = new IntrantDTO();
	private IntrantDTO selectedIntrantDto;
	private float montantTotalAllocationPV;
	private float montantTotalCollecteUtilise;
	private List<CoupleDTO> communes;
	private AllocationDTO allocationBudget;
	private List<IntrantDTO> stockResiduel;
	private List<IntrantDTO> stockResiduelFiltred;
	private IntrantDTO stockResiduelSelected;

	private boolean connectedUserProfilIsAgentCollecte;
	Long idSCommunepointDeVente;

	public AgentCollecteManagedBean() {

		connectedUserProfilIsAgentCollecte = true;

		try {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			if (request != null) {
				HttpSession session = request.getSession(false);
				idSCommunepointDeVente = (Long) session.getAttribute("idCommuneCordinateur");
			}
			//loadListDesAffestations();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	int stockResiduelSize;
	int nbAffectations;
	List<IntrantDTO> listeAffectations;

	public List<IntrantDTO> loadListDesAffestations() {
		idSCommunepointDeVente = null;
		List<IntrantDTO> affectationsEnCours = new ArrayList();

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			Long idConnectedUser = (Long) session.getAttribute("connectedUserid");
		}
		
		nbAffectations =   affectationsEnCours != null ? affectationsEnCours.size() : 0;
		return affectationsEnCours;
	}

	public List<IntrantDTO> loadStockResiduel() {

		idSCommunepointDeVente = null;

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		if (request != null) {
			HttpSession session = request.getSession(false);
			idSCommunepointDeVente = (Long) session.getAttribute("idCommuneCordinateur");
			Long connectedUserStockid = (Long) session.getAttribute("connectedUserStockid");

			Long idConnectedUser = (Long) session.getAttribute("connectedUserid");

			try {
				stockResiduel = programmAgricoleService.loadStockResiduelByidCommune(idSCommunepointDeVente);

				List<IntrantDTO> listeAffectations = programmAgricoleService
						.loadAllIntrantFromAffectations(idConnectedUser);

				if (listeAffectations != null) {
					nbAffectations = listeAffectations.size();

					// loop aff et check intran mep by commune
				}

				// merge les intrants collecté sur place et les intrants reçus
				// par MISE EN PLACE
				List<ProduitDTO> stockCollecteLocal = programmAgricoleService
						.loadAallProduitFromStockbyIdStock(connectedUserStockid);

				for (ProduitDTO intrant : stockCollecteLocal) {
					IntrantDTO idto = new IntrantDTO();

					idto.setIdProduit(intrant.getIdProduit());
					idto.setLibelleProduit(intrant.getLibelle());
					idto.setLibelleProgramme(intrant.getLibelleProgramme());
					idto.setLibelleCampagne(intrant.getLibelleCampagne());
					idto.setPrixProducteur(intrant.getPrixUnitaire());
					idto.setQuantite(intrant.getQuantite());
					idto.setPictoImages(intrant.getLibelle().replace(" ", "").toLowerCase());

					idto.setSourceType(2);

					stockResiduel.add(idto);
				}

				if (stockResiduel != null)
					stockResiduelSize = stockResiduel.size();

			} catch (EntityDBDAOException e) {
				Log.error("Erreur  survenue lecture  stock residuel" + e.getMessage());
			}
		}
		return stockResiduel;
	}

	IntrantDTO selectedPointDeVenteDTO;
	List<IntrantDTO> listIntrantOfSelectedPV ;
	public String showDetailsPointDeVente() {
		if (selectedPointDeVenteDTO == null ) {
			commonService.showMessage(FacesMessage.SEVERITY_WARN, "Merci de selectionner un point de vente ");
			return "dashboardAgentcollecte.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		// on recupére les intrans du PV selectionné !
		try {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpSession session = request.getSession(false);
			Long idPointDeCollecte = (Long) session.getAttribute("idPointDeCollecte");
			
		
			
			listIntrantOfSelectedPV = programmAgricoleService.loadStockResiduelSuperviseurAndCommune(
					selectedPointDeVenteDTO.getIdCommune() , idPointDeCollecte);
		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "dashboardAgentcollectePointDeVente.xhtml?faces-redirect=true&idBlocToShow=2";
	}
	
	
	
	/******
	 * 
	 * COLLECTES SERVICES
	 * 
	 ***/

	public String initEnregistrementCollecte() {
		if (allocationBudget == null || allocationBudget.getIdIntrantACollecter() == null) {
			commonService.showMessage(FacesMessage.SEVERITY_WARN, "Merci de selectionner une collecte");
			return "ACBudget.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		return "ACBudget.xhtml?faces-redirect=true&idBlocToShow=2";
	}

	public String enregistrertCollecte() {
		try {
			if (programmAgricoleService.enregistrerCollect(allocationBudget)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
						"Collecte " + allocationBudget.getIntranACollecter() + " enregistré avec succès");

				// load info point de collecte pour edition recu

				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "ACBudget.xhtml?faces-redirect=true&idBlocToShow=3";

			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
						"Une erreur est survenue lors de l'ajout de l'intrant");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "ACListeCollecte.xhtml?faces-redirect=true&idBlocToShow=2";
			}
		} catch (Exception e) {
			Log.error("Erreur surveneur lors de l'enregistrement de la collecte "
					+ allocationBudget.getProducteurPrenom() + e.getMessage());
			return "ACListeCollecte.xhtml?faces-redirect=true&idBlocToShow=2";
		}
	}

	public List<IntrantDTO> loadCollecteByPointDeCollecte() {
		Long idPointDecollete = null;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			idPointDecollete = (Long) session.getAttribute("idPointdeCollecte");
		}

		try {
			return programmAgricoleService.loadCollecteByPointDeCollecteAndProg(idPointDecollete, 1L);
		} catch (EntityDBDAOException e) {
			Log.error("Impossible de lire les collectes de la zone " + e.getMessage());
			return null;
		}
	}

	public int loadTotalCollecteByPointDeCollecte() {
		Long idPointDecollete = null;
		int alll = 0;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			idPointDecollete = (Long) session.getAttribute("idPointdeCollecte");
		}

		try {
			List<IntrantDTO> all = programmAgricoleService.loadCollecteByPointDeCollecteAndProg(idPointDecollete, 1L);

			for (IntrantDTO i : all)
				alll += i.getQuantite();

		} catch (EntityDBDAOException e) {
			Log.error("Impossible de lire les collectes de la zone " + e.getMessage());
			return 0;
		}
		return alll;
	}

	public String getDetailsIntrant() {
		if (intrantDtoToCreate == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Veuillez selectionner un intrant");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "ACListeCollecte.xhtml?faces-redirect=true&idBlocToShow=1";
		} else {
			return "ACListeCollecte.xhtml?faces-redirect=true&idBlocToShow=4";
		}
	}

	public String deleteIntrant() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		System.err.println("------ Delete Entity Modele : INtrant DTO");
		if (intrantDtoToCreate == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "",
					"Veuiller selectionner un intrant à supprimer");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "ACListeCollecte.xhtml?faces-redirect=truee&idBlocToShow=1";
		}

		if (commonService.deleteEntityModele(intrantDtoToCreate.getIdProduit(), Intrant.class)) {

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Intrant " + intrantDtoToCreate.getLibelleProduit() + "supprimé avec avec succès");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		return "ACListeCollecte.xhtml?faces-redirect=true&idBlocToShow=1";
	}

	public String initUpdateIntrant() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		System.err.println("------ INIT Update Entity Modele : Intrant");
		if (intrantDtoToCreate == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Modification Point de Vente :",
					"Veuillez selectionner un intrant à modifier");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=1";
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
					"Modification de l'intrant " + intrantDtoToCreate.getLibelleProduit());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesProduits.xhtml?faces-redirect=true&idBlocToShow=3";
		}
	}

	public List<AllocationDTO> callServiceTotalBudgetAlloue() {
		Long idPointDecollete = null;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			idPointDecollete = (Long) session.getAttribute("idPointdeCollecte");
		}

		try {
			List<AllocationDTO> alloc = serviceTresorerie.loadAllAllocationByIdPointDeVente(idPointDecollete, 1L);

			for (AllocationDTO a : alloc)
				montantTotalAllocationPV += a.getMontantalloue();

			return alloc;

		} catch (EntityDBDAOException e) {
			Log.error("Impossible de lire les collectes de la zone " + e.getMessage());
			return null;
		}

	}

	public float getTotalAllocationBudget() {
		Long idPointDecollete = null;
		montantTotalCollecteUtilise = 0;

		float total = 0l;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			idPointDecollete = (Long) session.getAttribute("idPointdeCollecte");
		}

		try {
			List<AllocationDTO> alloc = serviceTresorerie.loadAllAllocationByIdPointDeVente(idPointDecollete, 1L);

			for (AllocationDTO a : alloc) {
				total += a.getMontantalloue();

				montantTotalCollecteUtilise += a.getMontantUtilise();
			}

			return total;

		} catch (EntityDBDAOException e) {
			Log.error("Impossible de lire les collectes de la zone " + e.getMessage());
			return 0l;
		}

	}

	public float getTotalUtilisesurAllocationBudget() {
		Long idPointDecollete = null;
		float total = 0l;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			idPointDecollete = (Long) session.getAttribute("idPointdeCollecte");
		}

		try {
			List<AllocationDTO> alloc = serviceTresorerie.loadAllAllocationByIdPointDeVente(idPointDecollete, 1L);

			for (AllocationDTO a : alloc)
				total += a.getMontantUtilise();

			return total;

		} catch (EntityDBDAOException e) {
			Log.error("Impossible de lire les collectes de la zone " + e.getMessage());
			return 0l;
		}

	}

	public void loadAllCommuneOfDepartement() {
		System.out.println("id dept ............." + intrantDtoToCreate.getVendeurDepartementId());

		communes = commonService.loadAllCommunOfdepartement(intrantDtoToCreate.getVendeurDepartementId());
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

	public ITresorerieService getServiceTresorerie() {
		return serviceTresorerie;
	}

	public void setServiceTresorerie(ITresorerieService serviceTresorerie) {
		this.serviceTresorerie = serviceTresorerie;
	}

	public IntrantDTO getIntrantDtoToCreate() {
		return intrantDtoToCreate;
	}

	public void setIntrantDtoToCreate(IntrantDTO intrantDtoToCreate) {
		this.intrantDtoToCreate = intrantDtoToCreate;
	}

	public IntrantDTO getSelectedIntrantDto() {
		return selectedIntrantDto;
	}

	public void setSelectedIntrantDto(IntrantDTO selectedIntrantDto) {
		this.selectedIntrantDto = selectedIntrantDto;
	}

	public List<CoupleDTO> getCommunes() {
		return communes;
	}

	public void setCommunes(List<CoupleDTO> communes) {
		this.communes = communes;
	}

	public float getMontantTotalAllocationPV() {
		return montantTotalAllocationPV;
	}

	public void setMontantTotalAllocationPV(float montantTotalAllocationPV) {
		this.montantTotalAllocationPV = montantTotalAllocationPV;
	}

	public float getMontantTotalCollecteUtilise() {
		return montantTotalCollecteUtilise;
	}

	public void setMontantTotalCollecteUtilise(float montantTotalCollecteUtilise) {
		this.montantTotalCollecteUtilise = montantTotalCollecteUtilise;
	}

	public AllocationDTO getAllocationBudget() {
		return allocationBudget;
	}

	public List<IntrantDTO> getStockResiduel() {
		return stockResiduel;
	}

	public void setStockResiduel(List<IntrantDTO> stockResiduel) {
		this.stockResiduel = stockResiduel;
	}

	public void setAllocationBudget(AllocationDTO allocationBudget) {
		this.allocationBudget = allocationBudget;
	}

	public Long getIdSCommunepointDeVente() {
		return idSCommunepointDeVente;
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

	public void setIdSCommunepointDeVente(Long idSCommunepointDeVente) {
		this.idSCommunepointDeVente = idSCommunepointDeVente;
	}

	public int getStockResiduelSize() {
		return stockResiduelSize;
	}

	public void setStockResiduelSize(int stockResiduelSize) {
		this.stockResiduelSize = stockResiduelSize;
	}

	public int getNbAffectations() {
		return nbAffectations;
	}

	public List<IntrantDTO> getListeAffectations() {
		return listeAffectations;
	}

	public void setListeAffectations(List<IntrantDTO> listeAffectations) {
		this.listeAffectations = listeAffectations;
	}

	public void setNbAffectations(int nbAffectations) {
		this.nbAffectations = nbAffectations;
	}

	public boolean isConnectedUserProfilIsAgentCollecte() {
		return connectedUserProfilIsAgentCollecte;
	}

	public void setConnectedUserProfilIsAgentCollecte(boolean connectedUserProfilIsAgentCollecte) {
		this.connectedUserProfilIsAgentCollecte = connectedUserProfilIsAgentCollecte;
	}

	public IAffectationService getAffectationServices() {
		return affectationServices;
	}

	public IntrantDTO getSelectedPointDeVenteDTO() {
		return selectedPointDeVenteDTO;
	}

	public void setSelectedPointDeVenteDTO(IntrantDTO selectedPointDeVenteDTO) {
		this.selectedPointDeVenteDTO = selectedPointDeVenteDTO;
	}

	public void setAffectationServices(IAffectationService affectationServices) {
		this.affectationServices = affectationServices;
	}

	public List<IntrantDTO> getListIntrantOfSelectedPV() {
		return listIntrantOfSelectedPV;
	}

	public void setListIntrantOfSelectedPV(List<IntrantDTO> listIntrantOfSelectedPV) {
		this.listIntrantOfSelectedPV = listIntrantOfSelectedPV;
	}

}
