package sn.awi.pgca.web.bean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.LogFactory;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Value;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.service.ITresorerieService;
import sn.awi.pgca.utils.UtilString;
import sn.awi.pgca.web.dto.AllocationDTO;
import sn.awi.pgca.web.dto.AvanceCreditDTO;
import sn.awi.pgca.web.dto.BudgetDTO;
import sn.awi.pgca.web.dto.ChequeDTO;
import sn.awi.pgca.web.dto.CollaborateurDTO;
import sn.awi.pgca.web.dto.CreditDTO;
import sn.awi.pgca.web.dto.SubventionDTO;
import sn.awi.pgca.web.dto.VersementBanqueDTO;

@ManagedBean(name = "tresoMB")
@RequestScoped
public class TresoManagedBean implements Serializable {

	private static final long serialVersionUID = -7103834664247383927L;

	private static final org.apache.commons.logging.Log LOG = LogFactory.getLog(TresoManagedBean.class);

	@ManagedProperty(value = "#{tresorerieService}")
	private ITresorerieService tresorerieService;

	@Value("${pgca.gedVersementPath}")
	// String pathFileVersement = "/Users/Amet/GED-PGCA/Versement/"; // TODO :
	
	String pathFileVersement = "/home/awa/GED-PGCA/Versement/";

	private CollaborateurDTO selectedCollaborateurDTO;
	BudgetDTO allocationBudget;
	CreditDTO selectedCreditDTO;
	AvanceCreditDTO avanceDTO;
	UtilString utils = new UtilString();
	VersementBanqueDTO versementDTO = new VersementBanqueDTO();
	static boolean displayBlockEmptyAvnce = false;
	private float montantTotalDeposeEnBanque;
	
	

	

	private ChequeDTO selectedChequeDTO;

	public TresoManagedBean() {
	}

	@PostConstruct
	public void init() {
		allocationBudget = new BudgetDTO();
		selectedCollaborateurDTO = new CollaborateurDTO();
		selectedCreditDTO = new CreditDTO();
		avanceDTO = new AvanceCreditDTO();
	}

	public CreditDTO getDetailsCreditByIdCredit(Long idCredit) {
		try {
			System.out.println("Recuperation des details du credits" + selectedCreditDTO.getCredit_id());
			return tresorerieService.getListAvancebyIdCredit(selectedCreditDTO.getCredit_id());

		} catch (EntityDBDAOException e) {
			LOG.info("Recuperation des details du credits <" + idCredit + ">" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public List<AvanceCreditDTO> getlistAvanceByDetailsSelectedCredit() {
		if (selectedCreditDTO == null || selectedCreditDTO.getCredit_id() == null) {

			displayBlockEmptyAvnce = false;
			return null;
		}

		try {
			System.out.println("Recuperation des details du credits" + selectedCreditDTO.getCredit_id());
			List<AvanceCreditDTO> listAvance = tresorerieService
					.getListAvancebyIdCredit(selectedCreditDTO.getCredit_id()).getListAvanceCredits();

			if (listAvance != null) {
				displayBlockEmptyAvnce = true;
				return listAvance;
			}
			displayBlockEmptyAvnce = false;
			return null;
		} catch (EntityDBDAOException e) {
			LOG.info("Recuperation des details du credits <" + ">" + e.getMessage());
			e.printStackTrace();
			displayBlockEmptyAvnce = false;
			return null;
		}

	}
	
	
	

	
	public float callServiceTotalVente() {
		try {
			return tresorerieService.loadMontantTotalDesVentes();
		} catch (EntityDBDAOException e) {
			LOG.error("Erreur recuperation des ventes " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	public float callServiceTotalCredit() {
		try {
			
			return tresorerieService.loadMontantTotalDesCredits();
		} catch (EntityDBDAOException e) {
			LOG.error("Erreur recuperation des credits " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
	
	
	float totalSubvention;
	List<SubventionDTO> listSubvention;

	public float loadAllSubventions() {
		try {
			listSubvention = tresorerieService.loadAllSubventions();

			for (SubventionDTO s : listSubvention)
				totalSubvention += s.getMontantSubvention();

			return totalSubvention;

		} catch (EntityDBDAOException e) {
			Log.error("Erreur de recup des subventions" + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
	

	public float callServiceTotalDepotBanque() {

		return tresorerieService.evaluerMontantTotalDepotBanqueByCampagne();

	}

	public String encaisseCheque() {
		if (selectedChequeDTO == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Merci de selectionner le chèque à encaisser ");
			return "magasinierGestionCheque.xhtml";
		}

		try {
			
			if(selectedChequeDTO.getStatut() == ConstantPGCA.CHEQUE_ENCAISSER)
				{
					showMessage(FacesMessage.SEVERITY_WARN, "Le chèque "  + selectedChequeDTO.getNumeroCheque() + " est  déjà encaissé.");
					return "magasinierGestionCheque.xhtml";
				}
			
			if (tresorerieService.encaisseCheque(selectedChequeDTO.getId_cheque())) {
				showMessage(FacesMessage.SEVERITY_INFO, "Le chèque "  + selectedChequeDTO.getNumeroCheque() + " est  encaissé avec succès.");
				return "magasinierGestionCheque.xhtml";

			} 
		} catch (EntityDBDAOException e) {
			showMessage(FacesMessage.SEVERITY_ERROR, "Une erreur techniqueest surveneur lors de la validation du chèque ");
			
			LOG.error("Une erreur est survenenue lors de la validation du chèque....");
			return "magasinierGestionCheque.xhtml";
			
		}
		
			showMessage(FacesMessage.SEVERITY_ERROR, "Une erreur est surveneur lors de la validation du chèque ");
			return "magasinierGestionCheque.xhtml";
	}

	
	public String encaisseChequeManager() {
		if (selectedChequeDTO == null) {
			showMessage(FacesMessage.SEVERITY_WARN, "Merci de selectionner le chèque à encaisser ");
			return "managerGestionCheques.xhtml";
		}

		try {
			
			if(selectedChequeDTO.getStatut() == ConstantPGCA.CHEQUE_ENCAISSER)
				{
					showMessage(FacesMessage.SEVERITY_WARN, "Le chèque "  + selectedChequeDTO.getNumeroCheque() + " est  déjà encaissé.");
					return "managerGestionCheques.xhtml";
				}
			
			if (tresorerieService.encaisseCheque(selectedChequeDTO.getId_cheque())) {
				showMessage(FacesMessage.SEVERITY_INFO, "Le chèque "  + selectedChequeDTO.getNumeroCheque() + " est  encaissé avec succès.");
				return "managerGestionCheques.xhtml";

			} 
		} catch (EntityDBDAOException e) {
			showMessage(FacesMessage.SEVERITY_ERROR, "Une erreur techniqueest surveneur lors de la validation du chèque ");
			
			LOG.error("Une erreur est survenenue lors de la validation du chèque....");
			return "managerGestionCheques.xhtml";
			
		}
		
			showMessage(FacesMessage.SEVERITY_ERROR, "Une erreur est surveneur lors de la validation du chèque ");
			return "managerGestionCheques.xhtml";
	}

	
	public void callServiceAvanceSurCredit() {
		System.out.println("Enregistrement d'une avance pour le credit" + avanceDTO.getMontanance());
		Map<Long, String> save = tresorerieService.enregistrerAvance(avanceDTO, selectedCreditDTO.getCredit_id());

		Long returnType = null;
		String returnessage = null;

		for (Map.Entry<Long, String> entry : save.entrySet()) {
			returnType = entry.getKey();
			returnessage = entry.getValue();
		}

		if (returnType == 0L) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", "" + returnessage + "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else if (returnType == 1L) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "" + returnessage + "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "" + returnessage + "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public String enregistrerVersementBanque() {
		if (versementDTO == null) {
			showMessage(FacesMessage.SEVERITY_ERROR, "Erreur données ");
			return null;
		}

		if (versementDTO.getMoyenVersment().equals("0")) {
			showMessage(FacesMessage.SEVERITY_ERROR, "Le moyen de versement est obligatoire (Chéque ou Espece)");
			return null;
		}

		if (versementDTO.getBlpMontant() != 0 && versementDTO.getBlpNumero().equals("")) {
			showMessage(FacesMessage.SEVERITY_ERROR, "le numéro de BLP est obligatoire");
			return "magasinierVendreProduit.xhtml?faces-redirect=true&idBlocToShow=1";
		}
		
		
		// verification montant a enregistrer par rapport au montant enregistré
		// en caisse (Le montant especes ou cheque a verser ne doit pas etre
		// superieur au montant declarer a la caisse)

		/*if (versementDTO.getMoyenVersment().equals("1")) // Especes
		{
			float montantTotalEspecesEnCaisse = lireRepartitionCaissePointDeVente("1");
			if (montantTotalEspecesEnCaisse < versementDTO.getMontantVersment()) {
				showMessage(FacesMessage.SEVERITY_WARN,
						"Le montant du versement en espèce ne peut etre superieur au montant enregistré à la caisse qui est de"
								+ utils.formatFloatToCFA(montantTotalEspecesEnCaisse));
				return null;
			}
		} else if (versementDTO.getMoyenVersment().equals("2")) // Cheque
		{
			float montantTotalChequeEnCaisse = lireRepartitionCaissePointDeVente("2");
			if (montantTotalChequeEnCaisse < versementDTO.getMontantVersment()) {
				showMessage(FacesMessage.SEVERITY_WARN,
						"Le montant du versement par chéque ne pas etre superieur au montant cheque de la caisse < "
								+ utils.formatFloatToCFA(montantTotalChequeEnCaisse) + " >");
				return "#";
			}
		} */

		String repertoireDedidiePointDeVente = "";
		Long idPersonneAuteurVerment = null;
		Long idPointdeVente = null;
		Long idPointdeCollecte = null;


		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			repertoireDedidiePointDeVente = (String) session.getAttribute("connectedUserPointPhysique");
			idPersonneAuteurVerment = (Long) session.getAttribute("connectedUserPersonneid");
			idPointdeVente = (Long) session.getAttribute("idPointdeVente");
			idPointdeCollecte =  (Long) session.getAttribute("idPointdeCollecte");
		}
		if (idPersonneAuteurVerment == null) {
			Log.error("Impossible de recuperer le point de vente et l'auteur du versement à créer");
			return "";
		}
		//File pathVersement = new File(pathFileVersement + repertoireDedidiePointDeVente);
//		String fileExtention = FilenameUtils.getExtension(versementDTO.getDocumentJustificatif().getFileName());
//		InputStream input = null;

		versementDTO.setIdPersonneAuteurVersment(idPersonneAuteurVerment);
		versementDTO.setIdPointdeVente(idPointdeVente);
		
		if(idPointdeVente == null && idPointdeCollecte != null)
			versementDTO.setIdPointDeCollecte(idPointdeCollecte);
		
		versementDTO.setZone(repertoireDedidiePointDeVente);
		//versementDTO.setDocumentJustificatifextension(fileExtention);
		//versementDTO.setPathdocumentJustificatif((pathVersement.getAbsolutePath()));

		if (versementDTO.getMoyenVersment().equals("1"))
			versementDTO.setMoyenVersment("Espèces");
		else if (versementDTO.getMoyenVersment().equals("2"))
			versementDTO.setMoyenVersment("Chéque");
		else if (versementDTO.getMoyenVersment().equals("BLP"))
			versementDTO.setMoyenVersment("BLP");

		//	OutputStream output = null;
		if (tresorerieService.enregistrerVersmentBanque(versementDTO)) {
			// ecriture fichier sur disque avec un nom VersementIDVesement :
			// Ex : Versement2001
//				if (!pathVersement.exists())
//					pathVersement.mkdirs();
//
//				output = new FileOutputStream(
//						new File(pathVersement + "/Versement" + versementDTO.getIdVersment() + "." + fileExtention));
			showMessage(FacesMessage.SEVERITY_INFO, "Versement enregistré avec succès");
		} else {
			showMessage(FacesMessage.SEVERITY_ERROR,
					"Une erreur est survenue lors de  l'enregistrement du versement.");
		}
		return "";

	}

	public String enregistrerDepotChequeBanque() {
		if (versementDTO == null) {
			showMessage(FacesMessage.SEVERITY_ERROR, "Erreur données ");
			return null;
		}

		if (versementDTO.getMoyenVersment().equals("0")) {
			showMessage(FacesMessage.SEVERITY_ERROR, "Le moyen de versement est obligatoire (Chéque ou Espece)");
			return null;
		}

		String repertoireDedidiePointDeVente = "";
		Long idPersonneAuteurVerment = null;
		Long idPointdeVente = null;

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			repertoireDedidiePointDeVente = (String) session.getAttribute("connectedUserPointPhysique");
			idPersonneAuteurVerment = (Long) session.getAttribute("connectedUserPersonneid");
			idPointdeVente = (Long) session.getAttribute("idPointdeVente");
		}
		if (idPersonneAuteurVerment == null || idPointdeVente == null) {
			Log.error("Impossible de recuperer le point de vente et l'auteur du versement à créer");
			return "";
		}
		File pathVersement = new File(pathFileVersement + repertoireDedidiePointDeVente);
		String fileExtention = FilenameUtils.getExtension(versementDTO.getDocumentJustificatif().getFileName());
		InputStream input = null;

		versementDTO.setIdPersonneAuteurVersment(idPersonneAuteurVerment);
		versementDTO.setIdPointdeVente(idPointdeVente);
		versementDTO.setZone(repertoireDedidiePointDeVente);
		versementDTO.setDocumentJustificatifextension(fileExtention);
		versementDTO.setPathdocumentJustificatif((pathVersement.getAbsolutePath()));

		versementDTO.setMoyenVersment("Chèque");

		try {
			input = versementDTO.getDocumentJustificatif().getInputstream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		OutputStream output = null;
		try {
			if (tresorerieService.enregistrerVersmentBanque(versementDTO)) {
				// ecriture fichier sur disque avec un nom VersementIDVesement :
				// Ex : Versement2001
				if (!pathVersement.exists())
					pathVersement.mkdirs();

				output = new FileOutputStream(
						new File(pathVersement + "/Versement" + versementDTO.getIdVersment() + "." + fileExtention));
				showMessage(FacesMessage.SEVERITY_INFO, "Versement enregistré avec succès");
			} else {
				showMessage(FacesMessage.SEVERITY_ERROR,
						"Une erreur est survenue lors de  l'enregistrement du versement.");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			IOUtils.copy(input, output);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
		return fileExtention;
	}

	public void showMessage(Severity severity, String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		FacesMessage msg = new FacesMessage(severity, "", message);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public float lireRepartitionCaissePointDeVente(String mode) // 1 cheque , 2
																// Especes , 3
																// Bon
	{
		Long idPointDevente;
		try {
			idPointDevente = SessionManagedBean.getSessionLongValuesDataByTag("idPointdeVente");
			Map<String, Float> allMontant = tresorerieService.evaluerRepartitionCaisseByModePaiement(idPointDevente);

			if (mode.equals("1"))
				return allMontant.get("ESPECES");
			else if (mode.equals("2"))
				return allMontant.get("CHEQUE");
			else if (mode.equals("3"))
				return allMontant.get("BON");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	List<VersementBanqueDTO> filtredVersement;
	public List<VersementBanqueDTO> loadAllDepotPointDeVente() {

		List<VersementBanqueDTO> lv = tresorerieService.recupererAllVersementBanqueByCampagne();
		montantTotalDeposeEnBanque = 0;

		for (VersementBanqueDTO v : lv) {
			montantTotalDeposeEnBanque += v.getMontantVersment();
		}

		return lv;
	}

	public List<AllocationDTO> loadAllAllocations() {
		Long idPointCollecte = null;
		Long idProgramme = 0L;

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			idPointCollecte = (Long) session.getAttribute("idPointdeCollecte");

		}

		try {
			return tresorerieService.loadAllAllocationByIdPointDeVente(idPointCollecte, idProgramme);
		} catch (EntityDBDAOException e) {
			LOG.error("Un erreur est urveneue lors de  la saisir des vommandes" + e.getMessage());
		}
		return null;

	}

	/********** CHEQUE SERVICES ************/
	public List<ChequeDTO> callServiceRecuperationALlCheques() {
		return tresorerieService.recupererAllCheques();
	}

	public int callServiceRecuperationALlNBCheques() {
		return tresorerieService.recupererAllCheques().size();
	}

	public List<CreditDTO> callServiceRecuperationALlCreditsOfCampagne() {
		return tresorerieService.loadAllCreditsOfProgramme();
	}

	public List<AvanceCreditDTO> callServiceRecuperationALlAvanceFromCreditId() {
		return tresorerieService.loadAllAvancesFromCreditsId(selectedCreditDTO.getCredit_id());
	}

	// public boolean AllouerBudget() {
	// try {
	// tresorerieService.AllouerBudget(allocationBudget);
	// return true;
	// } catch (BudgetException | EntityDBDAOException be) {
	// Log.error("Erreur Allocation Budget : " + be.getStackTrace());
	// }
	//
	// return false;
	// }

	// TODO / DEPLACER CETTE FONCTION DANS UTILS FUNCTIONC
	// FIXME DEPLACER CETTE FONCTION DANS UTILS FUNCTIONC

	public String downloadJustificatif() throws IOException {

		if (versementDTO == null)
			return null;

		File pathVersement = new File(versementDTO.getPathdocumentJustificatif());
		// String ABSOLUTEPATH = "/Users/Amet/GED-PGCA/Versement/" +
		// versementDTO.getZone() + "/";
		String ABSOLUTEPATH = pathFileVersement + versementDTO.getZone() + "/";

		final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

		// Prepare.
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		File file = new File(ABSOLUTEPATH, pathVersement.getName());
		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			// Open file.
			input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);

			// Init servlet response.
			response.reset();
			response.setHeader("Content-Type", "application/pdf");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\"" + pathVersement.getName() + "\"");
			output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

			// Write file contents to response.
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}

			// Finalize task.
			output.flush();
		} finally {
			// Gently close streams.
			close(output);
			close(input);
		}

		// Inform JSF that it doesn't need to handle response.
		// This is very important, otherwise you will get the following
		// exception in the logs:
		// java.lang.IllegalStateException: Cannot forward after response has
		// been committed.
		facesContext.responseComplete();
		return "";
	}

	public String action;

	public void setAction(String action) {
		this.action = action;
	}

	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				// Do your thing with the exception. Print it, log it or mail
				// it. It may be useful to
				// know that this will generally only be thrown when the client
				// aborted the download.
				e.printStackTrace();
			}
		}
	}

	// TODO methode à remplacer par fichier properties ou table de ref en base

	public List<String> loadBanqueSenegal() {

		List<String> listBanque = new ArrayList<String>();
		listBanque.add("AWB");
		listBanque.add("CNCA");
		listBanque.add("CNCA SEDAB");
		listBanque.add("CNCA SEDAB-VENTURE");
		listBanque.add("CNCA SEDAB-KEYCARGO");
		listBanque.add("SEDAB - Remise Espèces");
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

	public boolean isNullEntity(Object obj) {
		if (obj == null)
			return true;
		return false;
	}

	public ITresorerieService getTresorerieService() {
		return tresorerieService;
	}

	public void setTresorerieService(ITresorerieService tresorerieService) {
		this.tresorerieService = tresorerieService;
	}

	public BudgetDTO getAllocationBudget() {
		return allocationBudget;
	}

	public void setAllocationBudget(BudgetDTO allocationBudget) {
		this.allocationBudget = allocationBudget;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public float getTotalSubvention() {
		return totalSubvention;
	}

	public void setTotalSubvention(float totalSubvention) {
		this.totalSubvention = totalSubvention;
	}

	public List<SubventionDTO> getListSubvention() {
		return listSubvention;
	}

	public void setListSubvention(List<SubventionDTO> listSubvention) {
		this.listSubvention = listSubvention;
	}

	public static org.apache.commons.logging.Log getLog() {
		return LOG;
	}

	public CollaborateurDTO getSelectedCollaborateurDTO() {
		return selectedCollaborateurDTO;
	}

	public void setSelectedCollaborateurDTO(CollaborateurDTO selectedCollaborateurDTO) {
		this.selectedCollaborateurDTO = selectedCollaborateurDTO;
	}

	public CreditDTO getSelectedCreditDTO() {
		return selectedCreditDTO;
	}

	public void setSelectedCreditDTO(CreditDTO selectedCreditDTO) {
		this.selectedCreditDTO = selectedCreditDTO;
	}

	public AvanceCreditDTO getAvanceDTO() {
		return avanceDTO;
	}

	public void setAvanceDTO(AvanceCreditDTO avanceDTO) {
		this.avanceDTO = avanceDTO;
	}

	public boolean isDisplayBlockEmptyAvnce() {
		return displayBlockEmptyAvnce;
	}

	public void setDisplayBlockEmptyAvnce(boolean displayBlockEmptyAvnce) {
		this.displayBlockEmptyAvnce = displayBlockEmptyAvnce;
	}

	public VersementBanqueDTO getVersementDTO() {
		return versementDTO;
	}

	public void setVersementDTO(VersementBanqueDTO versementDTO) {
		this.versementDTO = versementDTO;
	}

	public float getMontantTotalDeposeEnBanque() {
		return montantTotalDeposeEnBanque;
	}

	public void setMontantTotalDeposeEnBanque(float montantTotalDeposeEnBanque) {
		this.montantTotalDeposeEnBanque = montantTotalDeposeEnBanque;
	}

	public String getPathFileVersement() {
		return pathFileVersement;
	}

	public void setPathFileVersement(String pathFileVersement) {
		this.pathFileVersement = pathFileVersement;
	}

	public UtilString getUtils() {
		return utils;
	}

	public void setUtils(UtilString utils) {
		this.utils = utils;
	}

	public String getAction() {
		return action;
	}

	public ChequeDTO getSelectedChequeDTO() {
		return selectedChequeDTO;
	}

	public void setSelectedChequeDTO(ChequeDTO selectedChequeDTO) {
		this.selectedChequeDTO = selectedChequeDTO;
	}

	public List<VersementBanqueDTO> getFiltredVersement() {
		return filtredVersement;
	}

	public void setFiltredVersement(List<VersementBanqueDTO> filtredVersement) {
		this.filtredVersement = filtredVersement;
	}

}
