package sn.awi.pgca.business.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jfree.util.Log;

import com.itextpdf.text.pdf.codec.wmf.Point;

import sn.awi.pgca.business.dao.ModelDAO;
import sn.awi.pgca.business.dao.StockResiduelDAO;
import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.exception.ProgrammeException;
import sn.awi.pgca.business.service.ICommonService;
import sn.awi.pgca.business.service.IProgrammeAgricol;
import sn.awi.pgca.business.service.ITresorerieService;
import sn.awi.pgca.business.service.Inotification;
import sn.awi.pgca.dataModel.Allocation;
import sn.awi.pgca.dataModel.BonDeLivraison;
import sn.awi.pgca.dataModel.BonDeLivraisonProduit;
import sn.awi.pgca.dataModel.Camion;
import sn.awi.pgca.dataModel.CampagneAgricole;
import sn.awi.pgca.dataModel.Chauffeur;
import sn.awi.pgca.dataModel.CollecteProduits;
import sn.awi.pgca.dataModel.Commande;
import sn.awi.pgca.dataModel.CommandeProduitAssocie;
import sn.awi.pgca.dataModel.Commune;
import sn.awi.pgca.dataModel.Credit;
import sn.awi.pgca.dataModel.Fournisseur;
import sn.awi.pgca.dataModel.Intrant;
import sn.awi.pgca.dataModel.Litiges;
import sn.awi.pgca.dataModel.MiseEnPlaceAEffectuer;
import sn.awi.pgca.dataModel.MiseEnPlaceEffectuer;
import sn.awi.pgca.dataModel.OrdreLivraison;
import sn.awi.pgca.dataModel.OrdreLivraisonProduitAssocie;
import sn.awi.pgca.dataModel.Personne;
import sn.awi.pgca.dataModel.PointDeCollecte;
import sn.awi.pgca.dataModel.PointDeVente;
import sn.awi.pgca.dataModel.ProgrammeAgricol;
import sn.awi.pgca.dataModel.ReferentialIntrants;
import sn.awi.pgca.dataModel.ReferentialTypeIntrants;
import sn.awi.pgca.dataModel.Stock;
import sn.awi.pgca.dataModel.StockResiduelPointDeVente;
import sn.awi.pgca.dataModel.Tarificateur;
import sn.awi.pgca.dataModel.Transporteur;
import sn.awi.pgca.dataModel.VenteProduitAssocie;
import sn.awi.pgca.dataModel.Ventes;
import sn.awi.pgca.utils.UtilString;
import sn.awi.pgca.web.bean.AgentSaisieManagedBean;
import sn.awi.pgca.web.bean.ConstantPGCA;
import sn.awi.pgca.web.bean.SessionManagedBean;
import sn.awi.pgca.web.dto.AllocationDTO;
import sn.awi.pgca.web.dto.BonDeLivraisonDTO;
import sn.awi.pgca.web.dto.CampagneAgricoleDTO;
import sn.awi.pgca.web.dto.CommandeDTO;
import sn.awi.pgca.web.dto.CoupleDTO;
import sn.awi.pgca.web.dto.CreditDTO;
import sn.awi.pgca.web.dto.IndicateursDashboardDTO;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.IntrantTypeDTO;
import sn.awi.pgca.web.dto.LitigesDTO;
import sn.awi.pgca.web.dto.MiseEnPlaceEffectuerDTO;
import sn.awi.pgca.web.dto.MiseEnplaceAgregation;
import sn.awi.pgca.web.dto.MiseEnplaceDTOCommune;
import sn.awi.pgca.web.dto.MiseEnplaceDTOPointDeVente;
import sn.awi.pgca.web.dto.PointDeVenteDTO;
import sn.awi.pgca.web.dto.ProduitDTO;
import sn.awi.pgca.web.dto.ProgrammeAgricolDTO;
import sn.awi.pgca.web.dto.VentesDTO;

/**
 * @author Amet
 *
 */
@Named("programmeAgricolService")
public class ProgrammeAgricoleServiceImpl implements IProgrammeAgricol, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7431468624606631781L;

	/**
	 * DAO pour la persistance.
	 */
	@Inject
	private ModelDAO modelDAO;

	@Inject
	private ITresorerieService tresorerie;

	@Inject
	private Inotification notifierService;

	@Inject
	private ICommonService commonService;

	@Inject
	private StockResiduelDAO stockService;

	private static final org.apache.commons.logging.Log logger = LogFactory.getLog(AgentSaisieManagedBean.class);

	//
	// @Inject
	// private Inotification notifierService;

	public Inotification getNotifierService() {
		return notifierService;
	}

	public void setNotifierService(Inotification notifierService) {
		this.notifierService = notifierService;
	}

	UtilString utils = new UtilString();

	/***************
	 * C A M P A G N E S S E R V I C E S
	 ********************************/

	ProgrammeAgricoleServiceImpl() {

	}

	@Override
	public boolean createCampagneAgricole(CampagneAgricoleDTO cdo) throws ProgrammeException {
		CampagneAgricole ca = new CampagneAgricole();

		ca.setDateOuverture(cdo.getDateOuverture());
		ca.setDateFermeture(cdo.getDateFermeture());
		ca.setLibelle("Camapgne Agricole " + utils.getYeayFromDate(cdo.getDateOuverture()));

		try {
			modelDAO.save(ca);
			modelDAO.synchroniseWithDB(ca); // refresh to get id Campagne
			return true;

		} catch (EntityDBDAOException e) {
			Log.error("Une erreur est surveneur lors de l'ouverture de la campgane");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean createCampagneProgrammeAgricole(CampagneAgricoleDTO cdo)
			throws ProgrammeException, EntityDBDAOException {
		ProgrammeAgricol ca = new ProgrammeAgricol();
		// managerManagedBean.campagneDTO

		CampagneAgricole p = (CampagneAgricole) modelDAO.getEntityDBById(cdo.getIdCampagne(), CampagneAgricole.class);

		// managerManagedBean.campagneDTO
		ca.setDateOuverture(cdo.getDateOuverture());
		ca.setDateFermeture(cdo.getDateFermeture());
		ca.setLibelle(cdo.getLibelleCampagne());
		ca.setDescriptifProgramme(cdo.getDescriptionlibelleCampagne());
		ca.setCampagne(p);

		try {
			modelDAO.save(ca);
			return true;
		} catch (EntityDBDAOException e) {
			Log.error("Une erreur est surveneur lors de l'ouverture de la campgane");
			e.printStackTrace();
		}
		return false;
	}

	private boolean createALLProgramme(CampagneAgricoleDTO ca) throws ProgrammeException {
		boolean notYet = false;

		// Création des programmes de la campagne
		if (ca.isProgrammeArachide()) {
			ProgrammeAgricolDTO dto = new ProgrammeAgricolDTO();

			dto.setCmpagneId(ca.getIdCampagne());
			dto.setDateOuverture(ca.getDateOuverture());
			dto.setDateFermeture(ca.getDateFermeture());
			dto.setLibelle("Prog Arachide " + utils.getYeayFromDate(ca.getDateFermeture()));
			dto.setStatut(1);
			dto.setStatutlibelle("Créé");

			notYet = createProgammeForCampagneAgricol(dto);
		}

		if (ca.isProgrammeCollecte()) {
			ProgrammeAgricolDTO dto = new ProgrammeAgricolDTO();

			dto.setCmpagneId(ca.getIdCampagne());
			dto.setDateOuverture(ca.getDateOuverture());
			dto.setDateFermeture(ca.getDateFermeture());
			dto.setLibelle("Prog Collecte " + utils.getYeayFromDate(ca.getDateFermeture()));
			dto.setStatut(1);
			dto.setStatutlibelle("Créé");

			notYet = createProgammeForCampagneAgricol(dto);
		}

		if (ca.isProgrammePhyto()) {
			ProgrammeAgricolDTO dto = new ProgrammeAgricolDTO();

			dto.setCmpagneId(ca.getIdCampagne());
			dto.setDateOuverture(ca.getDateOuverture());
			dto.setDateFermeture(ca.getDateFermeture());
			dto.setLibelle("Prog Phyto  " + utils.getYeayFromDate(ca.getDateFermeture()));
			dto.setStatut(1);
			dto.setStatutlibelle("Créé");

			notYet = createProgammeForCampagneAgricol(dto);
		}

		if (ca.isProgrammeAutres()) {
			ProgrammeAgricolDTO dto = new ProgrammeAgricolDTO();

			dto.setCmpagneId(ca.getIdCampagne());
			dto.setDateOuverture(ca.getDateOuverture());
			dto.setDateFermeture(ca.getDateFermeture());
			dto.setLibelle("Prog  Semences " + utils.getYeayFromDate(ca.getDateFermeture()));
			dto.setStatut(1);
			dto.setStatutlibelle("Créé");

			notYet = createProgammeForCampagneAgricol(dto);
		}

		return notYet;

	}

	@Override
	public boolean createProgammeForCampagneAgricol(ProgrammeAgricolDTO prog) throws ProgrammeException {
		ProgrammeAgricol p = new ProgrammeAgricol();
		try {
			p.setCampagne((CampagneAgricole) modelDAO.getEntityDBById(prog.getCmpagneId(), CampagneAgricole.class));
			p.setDateFermeture(prog.getDateFermeture());
			p.setDateOuverture(prog.getDateOuverture());
			p.setLibelle(prog.getLibelle());
			p.setStatut(1);

			modelDAO.save(p);

			return true;

		} catch (EntityDBDAOException e) {
			Log.error("Une erreur est surveneur lors de l'ouverture du Programme");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean removeCampagneAgricole(long idCampagne) throws ProgrammeException {
		try {
			CampagneAgricole c = (CampagneAgricole) modelDAO.getEntityDBById(idCampagne, CampagneAgricole.class);
			if (c != null) {
				modelDAO.removeModel(c);
				return true;
			}
		} catch (Exception e) {
			// LOG.error("Impossible de supprimer le profil" + idCampagne + ",
			// car il est lié a des utilisateurs", e);
			return false;
		}
		return false;
	}

	@Override
	public List<PointDeVenteDTO> loadAllPointdeVenteNational() throws EntityDBDAOException {
		List<Commune> ca = modelDAO.loadAllCommune();

		List<PointDeVenteDTO> cadto = new ArrayList<PointDeVenteDTO>();

		for (Commune c : ca) {
			PointDeVenteDTO d = new PointDeVenteDTO();

			d.setCommune(c.getLibelle());
			d.setIdCommune(c.getId());
			d.setIdDepartement(c.getDepartement().getId());
			d.setDepartement(c.getDepartement().getLibelle());
			d.setRegion(c.getDepartement().getRegion().getLibelle());
			cadto.add(d);
		}

		return cadto;
	}

	@Override
	public boolean removeTypeIntrantFromCampagne(long idTypeIntrant) throws ProgrammeException {
		try {
			ReferentialIntrants c = (ReferentialIntrants) modelDAO.getEntityDBById(idTypeIntrant,
					ReferentialIntrants.class);
			if (c != null) {
				modelDAO.removeModel(c);
				return true;
			}
		} catch (Exception e) {
			// LOG.error("Impossible de supprimer le profil" + idCampagne + ",
			// car il est lié a des utilisateurs", e);
			return false;
		}
		return false;
	}

	@Override
	public List<CampagneAgricoleDTO> verifierExistanceCampagneParAnnee(Date debut, Date fin)
			throws EntityDBDAOException {
		List<CampagneAgricole> ca = modelDAO.lireCampagneParAnne(debut, fin);

		List<CampagneAgricoleDTO> cadto = new ArrayList<CampagneAgricoleDTO>();

		for (CampagneAgricole c : ca) {
			CampagneAgricoleDTO d = new CampagneAgricoleDTO();
			d.setDateFermeture(c.getDateOuverture());
			d.setDateFermeture(c.getDateFermeture());
			d.setLibelleCampagne(c.getLibelle());
			cadto.add(d);
		}

		return cadto;
	}

	@Override
	public List<CampagneAgricoleDTO> getlistCampagneAgricoleWithoutDetails()
			throws ProgrammeException, EntityDBDAOException {

		try {
			@SuppressWarnings("unchecked")
			List<CampagneAgricole> caList = (List<CampagneAgricole>) modelDAO
					.genericClassLoader(CampagneAgricole.class);

			List<CampagneAgricoleDTO> caDTOlist = new ArrayList<CampagneAgricoleDTO>();

			for (CampagneAgricole c : caList) {
				CampagneAgricoleDTO caDTO = new CampagneAgricoleDTO();

				caDTO.setIdCampagne(c.getId());
				caDTO.setLibelleCampagne(c.getLibelle());
				caDTO.setDateOuverture(c.getDateOuverture());
				caDTO.setDateFermeture(c.getDateFermeture());
				caDTO.setStatutCampagne(c.getStatut() + "");
				caDTOlist.add(caDTO);
			}
			return caDTOlist;
		} catch (Exception e) {
			Log.error("Une erreur est survenue lors de la lecture des campagne Agricole Cause = " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<ProgrammeAgricolDTO> getlistOfProgramme() throws ProgrammeException, EntityDBDAOException {

		try {
			@SuppressWarnings("unchecked")
			List<ProgrammeAgricol> caList = (List<ProgrammeAgricol>) modelDAO
					.genericClassLoader(ProgrammeAgricol.class);

			List<ProgrammeAgricolDTO> caDTOlist = new ArrayList<ProgrammeAgricolDTO>();

			for (ProgrammeAgricol c : caList) {
				ProgrammeAgricolDTO caDTO = new ProgrammeAgricolDTO();
				caDTO.setProgrammeId(c.getPgca_idprog());
				caDTO.setLibelle(c.getLibelle());
				caDTO.setCampagnelibelle(c.getCampagne().getLibelle());
				caDTOlist.add(caDTO);
			}
			return caDTOlist;
		} catch (Exception e) {
			Log.error("Une erreur est survenue lors de la lecture des campagne Agricole Cause = " + e.getMessage());
			return null;
		}
	}

	@Override
	public Long getStockidFromPointDeVente(Long idPointDeVente) throws EntityDBDAOException {
		PointDeVente pv = (PointDeVente) modelDAO.getEntityDBById(idPointDeVente, PointDeVente.class);

		return (pv.getStock() != null ? pv.getStock().getStock_id() : null);

	}

	@Override
	public List<CoupleDTO> getlistProgramme() throws ProgrammeException, EntityDBDAOException {
		try {
			@SuppressWarnings("unchecked")
			List<ProgrammeAgricol> caList = (List<ProgrammeAgricol>) modelDAO
					.genericClassLoader(ProgrammeAgricol.class);

			List<CoupleDTO> caDTOlist = new ArrayList<CoupleDTO>();

			for (ProgrammeAgricol c : caList) {
				CoupleDTO caDTO = new CoupleDTO(c.getId(), c.getLibelle());

				caDTOlist.add(caDTO);
			}
			return caDTOlist;
		} catch (Exception e) {
			Log.error("Une erreur est survenue lors de la lecture des campagnes Agricoles  = " + e.getMessage());
			return null;
		}

	}

	public ModelDAO getModelDAO() {
		return modelDAO;
	}

	public void setModelDAO(ModelDAO modelDAO) {
		this.modelDAO = modelDAO;
	}

	@Override
	public boolean closeCampagneAgricole(CampagneAgricoleDTO cdo) throws ProgrammeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getinfosDetailsCampagneAgricole(CampagneAgricoleDTO cdo) throws ProgrammeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean GenrateRapportExcelCampagneAgricole(CampagneAgricoleDTO cdo) throws ProgrammeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CampagneAgricoleDTO> getlistCampagneAgricolewithDetails()
			throws ProgrammeException, EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	/***************
	 * I N T R A N T S DE S C A M P A G N E S
	 * 
	 * @throws EntityDBDAOException
	 ********************************/

	@Override
	public boolean addIntrantCampagne(IntrantDTO idto) throws EntityDBDAOException {
		Intrant produit;

		Fournisseur provider = commonService.loadFournisseursByName(idto.getLibelleFournisseur());

		ReferentialIntrants intrant = commonService.loadIntrantByName(idto.getLibelleProduit());

		if (provider == null || intrant == null)
			return false;

		produit = modelDAO.verifierStockRestantAupresFournisseur(intrant.getId(), provider.getId());

		Stock stock = null;
		Personne personne = null;
		ProgrammeAgricol programme = null;
		Tarificateur tarifProduit = new Tarificateur();

		if (produit == null) // on créé le produit sinon on le mets a jour
		{
			produit = new Intrant();

			try {
				stock = (Stock) modelDAO.getEntityDBById(idto.getIdStock(), Stock.class);
				if (stock == null) {
					Log.error("Impossible de recuperer le stock de reference du produit");
					return false;
				}
			} catch (EntityDBDAOException e) {
				Log.error("Impossible de recuperer  le stock  du produit");
				return false;
			}

			try {
				programme = (ProgrammeAgricol) modelDAO.getEntityDBById(0L, ProgrammeAgricol.class); // default
																										// post
			} catch (EntityDBDAOException e) {
				Log.error("Erreur DB : Impossible de recuperer la campagne liée a lintrant");
				return false;
			}

			try {
				personne = (Personne) modelDAO.getEntityDBById(idto.getIdAuteurTarication(), Personne.class);
				if (personne == null) {
					Log.error("Impossible de recuperer l'auteur  du tarificateur de produit");
					return false;
				}
			} catch (EntityDBDAOException e) {
				Log.error("Erreur DB : Impossible de  de recuperer l'auteur  du tarificateur de produit");
				return false;
			}

			try {
				tarifProduit.setAuteurTarification(personne);
				modelDAO.save(tarifProduit);
				produit.setStockRef(stock);
				produit.setIntrant(intrant);
				produit.setProgramme(programme);
				produit.setProvenance(idto.getProvenance());
				produit.setQuantite(idto.getQuantite());
				produit.setQuantiteInitial(idto.getQuantite());
				produit.setLibelle(intrant.getLibelle());
				produit.setFournisseur(provider);
				produit.setStockLibelle("Stock fournisseur " + provider.getLibelle());
				modelDAO.save(produit);
				Log.info("Produit enregistré avec succés : " + intrant.getLibelle());
				idto.setLibelleProduit(intrant.getLibelle());

				idto.setUpdateOrNot(false);
				idto.setInfosDTOLibelleFournisseur(provider.getLibelle());

				return true;

			} catch (Exception e) {
				Log.error("Erreur Persistance Produit" + e.getMessage());
				return false;
			}
		} else // on le met a jour
		{
			try {
				produit.setQuantite(produit.getQuantite() + idto.getQuantite());
				produit.setQuantiteInitial(produit.getQuantiteInitial() + idto.getQuantite());
				modelDAO.save(produit);

				idto.setUpdateOrNot(true);
				idto.setInfosDTOLibelleFournisseur(produit.getFournisseur().getLibelle());

				return true;

			} catch (Exception e) {
				Log.error("Erreur Persistance Produit" + e.getMessage());
				return false;
			}
		}

	}

	/****
	 * Ajouter des intrants depuis un point de vente directement ou un mag
	 ***/

	@Override
	public boolean addIntrantCampagneCollecte(IntrantDTO idto) throws EntityDBDAOException {
		Intrant produit;

		// par defaut pour les intrans collece le fournisseur est SEDAB
		Fournisseur provider = (Fournisseur) modelDAO.getEntityDBById(0L, Fournisseur.class);

		ReferentialIntrants intrant = commonService.loadIntrantByName(idto.getLibelleProduit());

		if (provider == null || intrant == null)
			return false;

		produit = modelDAO.verifierStockRestantAupresMagasin(intrant.getId(), idto.getIdStock());

		Stock stock = null;
		Personne personne = null;
		ProgrammeAgricol programme = null;
		Tarificateur tarifProduit = new Tarificateur();

		if (produit == null) // on créé le produit sinon on le mets a jour
		{
			produit = new Intrant();

			try {
				stock = (Stock) modelDAO.getEntityDBById(idto.getIdStock(), Stock.class);
				if (stock == null) {
					Log.error("Impossible de recuperer le stock de reference du produit");
					return false;
				}
			} catch (EntityDBDAOException e) {
				Log.error("Impossible de recuperer  le stock  du produit");
				return false;
			}

			try {
				programme = (ProgrammeAgricol) modelDAO.getEntityDBById(0L, ProgrammeAgricol.class); // default
																										// post
			} catch (EntityDBDAOException e) {
				Log.error("Erreur DB : Impossible de recuperer la campagne liée a lintrant");
				return false;
			}

			try {
				personne = (Personne) modelDAO.getEntityDBById(idto.getIdAuteurTarication(), Personne.class);
				if (personne == null) {
					Log.error("Impossible de recuperer l'auteur  du tarificateur de produit");
					return false;
				}
			} catch (EntityDBDAOException e) {
				Log.error("Erreur DB : Impossible de  de recuperer l'auteur  du tarificateur de produit");
				return false;
			}

			try {
				tarifProduit.setAuteurTarification(personne);

				tarifProduit.setPrixProducteur(idto.getPrixAcquisition());
				tarifProduit.setPrixNonSubventionne(idto.getPrixAcquisition());
				tarifProduit.setSubventionne(false);
				tarifProduit.setTauxSubvention(0f);
				modelDAO.save(tarifProduit);

				produit.setTarif(tarifProduit);
				produit.setStockRef(stock);
				produit.setIntrant(intrant);
				produit.setProgramme(programme);
				produit.setProvenance(stock.getLibelle());
				produit.setQuantite(idto.getQuantite());
				produit.setQuantiteInitial(idto.getQuantite());
				produit.setLibelle(intrant.getLibelle());
				produit.setFournisseur(provider);
				produit.setStockLibelle(stock.getLibelle());
				modelDAO.save(produit);
				Log.info("Produit enregistré avec succés : " + intrant.getLibelle());
				idto.setLibelleProduit(intrant.getLibelle());

				idto.setUpdateOrNot(false);
				idto.setInfosDTOLibelleFournisseur(provider.getLibelle());

				return true;

			} catch (Exception e) {
				Log.error("Erreur Persistance Produit" + e.getMessage());
				return false;
			}
		} else // on le met a jour
		{
			try {
				produit.setQuantite(produit.getQuantite() + idto.getQuantite());
				produit.setQuantiteInitial(produit.getQuantiteInitial() + idto.getQuantite());
				modelDAO.save(produit);

				idto.setUpdateOrNot(true);
				idto.setInfosDTOLibelleFournisseur(intrant.getLibelle());

				return true;

			} catch (Exception e) {
				Log.error("Erreur Persistance Produit" + e.getMessage());
				return false;
			}
		}
	}

	
	@Override
	public boolean updateStockResiduelBySuperviseur(ProduitDTO updatedData) throws EntityDBDAOException {
		Intrant intran = (Intrant) modelDAO.getEntityDBById(updatedData.getIdProduit(), Intrant.class);
		if(intran != null)
		{
			intran.setQuantite(updatedData.getQuantite());
			modelDAO.save(intran);
		    Tarificateur tarifIntrant = intran.getTarif();
		    tarifIntrant.setPrixProducteur(updatedData.getPrixUnitaire());
		    modelDAO.save(tarifIntrant);
			return true;
		}
		else
			return false;	
	}
	
	
	
	@Override
	public boolean enregistrerCollect(AllocationDTO allocRef) throws EntityDBDAOException {
		Allocation allocReference = (Allocation) modelDAO.getEntityDBById(allocRef.getAlloc_id(), Allocation.class);
		if (allocReference == null)
			return false;
		try {
			CollecteProduits collecte = new CollecteProduits();
			collecte.setAllocationConcerne(allocReference);
			collecte.setProducteurCNI(allocRef.getProducteurCNI());
			collecte.setProducteurDateDepot(allocRef.getProducteurDateDepot());
			collecte.setProducteurNom(allocRef.getProducteurNom());
			collecte.setProducteurPrenom(allocRef.getProducteurPrenom());
			collecte.setProducteurPoidsTotal(allocRef.getProducteurPoidsTotal());
			collecte.setProducteurPrixUnitaire(allocRef.getProducteurPrixUnitaire());
			collecte.setProducteurValeurCollecte(
					allocRef.getProducteurPrixUnitaire() * allocRef.getProducteurPoidsTotal());
			collecte.setProducteurVillage(allocRef.getProducteurVillage());
			collecte.setPointdeCollecte(allocReference.getPointdeCollecteConcerne());

			modelDAO.save(collecte);
			modelDAO.synchroniseWithDB(collecte);
			collecte.setReference("CLT" + collecte.getIdCollecte());
			modelDAO.save(collecte);

			allocRef.setReference(collecte.getReference());

			// Update budget allocation de reference
			allocReference.setMontantUtilise(allocRef.getMontantUtilise()
					+ (allocRef.getProducteurPrixUnitaire() * allocRef.getProducteurPoidsTotal()));
			modelDAO.save(allocReference);
			// Commune commune =
			// modelDAO.loadCommuneFromLibelleCommune(idto.getVendeurCommune());

			allocRef.setProducteurValeurCollecte(
					collecte.getProducteurPrixUnitaire() * collecte.getProducteurPoidsTotal() + "");

			// load info point de vente for recu
			allocRef.setPointdeCollecteBeneficiare(allocReference.getPointdeCollecteConcerne() != null
					? allocReference.getPointdeCollecteConcerne().getLibelle() : "");

			if (allocReference.getPointdeCollecteConcerne() != null
					&& allocReference.getPointdeCollecteConcerne().getAdresse() != null) {
				if (allocReference.getPointdeCollecteConcerne().getAdresse().getRegion() != null)
					allocRef.setRegionPointDecollecteBeneficiaire(
							allocReference.getPointdeCollecteConcerne().getAdresse().getRegion().getLibelle());
				if (allocReference.getPointdeCollecteConcerne().getAdresse().getDepartement() != null)
					allocRef.setDepartementPointDecollecteBeneficiaire(
							allocReference.getPointdeCollecteConcerne().getAdresse().getDepartement().getLibelle());
				if (allocReference.getPointdeCollecteConcerne().getAdresse().getCommune() != null)
					allocRef.setCommunePointDecollecteBeneficiaire(
							allocReference.getPointdeCollecteConcerne().getAdresse().getCommune().getLibelle());
			}

			Log.info("Collecte enregistrée avec succés : ");
			return true;

		} catch (Exception e) {
			Log.error("Erreur Persistance Collecte" + e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IntrantDTO> loadAllIntrantDTOByIdStock(Long connectedUseridStock) {

		List<Intrant> listProduits;
		try {
			String connecdUserProfil = SessionManagedBean.getSessionDataByTag("profilCode");
			// filtre recuperation produit : Tout pour les manager ; filtré par
			// point de vente pour les magasinier

			if (connecdUserProfil.equals(
					"manager") /* || connecdUserProfil.equals("agentsaisie") */)
				listProduits = (List<Intrant>) modelDAO.genericClassLoader(Intrant.class);
			else {
				listProduits = (List<Intrant>) modelDAO.genericSqlClassLoaderById(Intrant.class, "pgca_intrant",
						"stock_id", connectedUseridStock);
			}

			if (listProduits == null)
				return null;

			List<IntrantDTO> listeTypeIntrantDTO = new ArrayList<IntrantDTO>();

			for (Intrant tp : listProduits) {
				IntrantDTO idto = new IntrantDTO();
				idto.setIdProduit(tp.getProduit_id());
				idto.setIdtypeProduit(tp.getIntrant().getRefIntrantId());
				idto.setIdStock(tp.getStockRef().getId());
				idto.setIdCampagneProgramme(tp.getProgramme().getId());
				idto.setLibelleTypeProduit(tp.getIntrant().getTypeIntrantId().getLibelle());

				idto.setLibelleProduit(tp.getIntrant().getLibelle());
				idto.setLibelleProgramme(tp.getProgramme().getLibelle());
				idto.setLibelleCampagne(tp.getProgramme().getCampagne().getLibelle());
				idto.setLibellePointdeStock(tp.getStockLibelle());
				idto.setQuantite(formatDoubleTwoDigi (tp.getQuantite()));
				idto.setQuantiteInitaile(formatDoubleTwoDigi(tp.getQuantiteInitial()));
				idto.setTypeProduit(tp.getIntrant().getTypeIntrantId().getLibelle());
				idto.setProvenance(tp.getProvenance());
				idto.setIdCategorieIntrant(tp.getIntrant().getTypeIntrantId().getTypeIntrantId());

				idto.setPictoImages(tp.getIntrant().getTypeIntrantId().getPictoIntrant());

				if (tp.getTarif() != null) {

					idto.setDejaTarifie(true);
					if (tp.getTarif().getTauxSubvention() > 0) {
						idto.setPrixProducteur(tp.getTarif().getPrixNonSubventionne()
								- (tp.getTarif().getPrixNonSubventionne() * tp.getTarif().getTauxSubvention() / 100));
						idto.setTauxSubvention(tp.getTarif().getTauxSubvention());
						idto.setPrixNonSubventionne(tp.getTarif().getPrixNonSubventionne());
						idto.setTauxSubventionLibelle(idto.getTauxSubvention() + "%");
						idto.setIntranSubventione(true);
						idto.setInfosTarifs((utils.formatFloatToCFA(idto.getPrixProducteur())));
						idto.setInfosTarifsClass(ConstantPGCA.ICON_ENCOURS);
						idto.setInfosTarifsClassColor(ConstantPGCA.ICON_ENCOURS_COLOR_GREEN);
					} else {
						idto.setPrixProducteur(tp.getTarif().getPrixNonSubventionne());
						idto.setTauxSubvention(0L);
						idto.setPrixNonSubventionne(tp.getTarif().getPrixNonSubventionne());
						idto.setTauxSubventionLibelle(ConstantPGCA.PRODUIT_NON_SUBVENTIONNE);
						idto.setIntranSubventione(false);
						idto.setInfosTarifs((utils.formatFloatToCFA(tp.getTarif().getPrixNonSubventionne())));
						idto.setInfosTarifsClass(ConstantPGCA.ICON_TRAITE);
						idto.setInfosTarifsClassColor(ConstantPGCA.ICON_REFUSE_COLOR_YELLOW);
					}

				} else {
					idto.setInfosTarifs(utils.formatFloatToCFA(0));
					idto.setInfosTarifsClass(ConstantPGCA.ICON_REFUSE);
					idto.setInfosTarifsClassColor(ConstantPGCA.ICON_TRAITE_COLOR_RED);
					idto.setDejaTarifie(false);
				}

				if (tp.getStockRef().getId() == 0) { // stock dakar donc dans
														// fournisseur
					idto.setIdFournisseur(tp.getFournisseur().getId());
					idto.setLibelleFournisseur(tp.getFournisseur().getLibelle());
				} else // stock deplace de dakar vers un autre magasin : on met
						// le nom du magasin ou se trouve lintrant
				{
					idto.setIdFournisseur(tp.getFournisseur().getId());
					idto.setLibelleFournisseur(tp.getStockRef().getLibelle());
				}

				// TODO : Ex externaliser sur l'appel de la page de details
				// seulement : Recup info gerant de ce produit pour la ta : T
				PointDeVente pv = (PointDeVente) modelDAO.getEntityDBById(tp.getStockRef().getStock_id(),
						PointDeVente.class);
				if (pv != null) {
					idto.setLieuStockageActuelProduitGerant(
							pv.getGerant() != null ? pv.getGerant().getPrenom() + " " + pv.getGerant().getNom() : "");
					if (pv.getGerant() != null && pv.getGerant().getContact() != null)
						idto.setLieuStockageActuelProduitGerantTel(pv.getGerant().getContact().getMobile());
					if (pv.getAdresse() != null)
						idto.setLieuStockageActuelduProduit(pv.getAdresse().getLibelle());
				}
				listeTypeIntrantDTO.add(idto);
			}
			return listeTypeIntrantDTO;

		} catch (Exception e) {
			Log.error("Erreur lecteur code profil id connecté");
			e.printStackTrace();
			return null;
		}
	}

	private Double formatDoubleTwoDigi(Double d)
	{
		try {
			return  BigDecimal.valueOf(d)
				    .setScale(2, RoundingMode.HALF_UP)
				    .doubleValue();
		
		} catch (Exception e) {
			Log.error("Erreur CAST DOUBLE TWO DIGIT" + e.getMessage());
		}
		return d;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IntrantDTO> getlistIntrantDTOFromFournisseur() {

		List<Intrant> listProduits;
		try {

			listProduits = (List<Intrant>) modelDAO.genericSqlClassLoaderById(Intrant.class, "pgca_intrant", "stock_id",
					0L);

			if (listProduits == null)
				return null;

			List<IntrantDTO> listeTypeIntrantDTO = new ArrayList<IntrantDTO>();

			for (Intrant tp : listProduits) {
				IntrantDTO idto = new IntrantDTO();
				idto.setIdProduit(tp.getProduit_id());
				idto.setIdtypeProduit(tp.getIntrant().getRefIntrantId());
				idto.setIdStock(tp.getStockRef().getId());
				idto.setIdCampagneProgramme(tp.getProgramme().getId());
				idto.setLibelleTypeProduit(tp.getIntrant().getTypeIntrantId().getLibelle());

				idto.setLibelleProduit(tp.getIntrant().getLibelle());
				idto.setLibelleProgramme(tp.getProgramme().getLibelle());
				idto.setLibelleCampagne(tp.getProgramme().getCampagne().getLibelle());
				idto.setLibellePointdeStock(tp.getStockLibelle());
				idto.setQuantite(tp.getQuantite());
				idto.setQuantiteInitaile(tp.getQuantiteInitial());
				idto.setTypeProduit(tp.getIntrant().getTypeIntrantId().getLibelle());
				idto.setProvenance(tp.getProvenance());
				idto.setIdCategorieIntrant(tp.getIntrant().getTypeIntrantId().getTypeIntrantId());

				idto.setPictoImages(tp.getIntrant().getTypeIntrantId().getPictoIntrant());

				if (tp.getTarif() != null) {

					idto.setDejaTarifie(true);
					if (tp.getTarif().getTauxSubvention() > 0) {
						idto.setPrixProducteur(tp.getTarif().getPrixNonSubventionne()
								- (tp.getTarif().getPrixNonSubventionne() * tp.getTarif().getTauxSubvention() / 100));
						idto.setTauxSubvention(tp.getTarif().getTauxSubvention());
						idto.setPrixNonSubventionne(tp.getTarif().getPrixNonSubventionne());
						idto.setTauxSubventionLibelle(idto.getTauxSubvention() + "%");
						idto.setIntranSubventione(true);
						idto.setInfosTarifs((utils.formatFloatToCFA(idto.getPrixProducteur())));
						idto.setInfosTarifsClass(ConstantPGCA.ICON_ENCOURS);
						idto.setInfosTarifsClassColor(ConstantPGCA.ICON_ENCOURS_COLOR_GREEN);
					} else {
						idto.setPrixProducteur(tp.getTarif().getPrixNonSubventionne());
						idto.setTauxSubvention(0L);
						idto.setPrixNonSubventionne(tp.getTarif().getPrixNonSubventionne());
						idto.setTauxSubventionLibelle(ConstantPGCA.PRODUIT_NON_SUBVENTIONNE);
						idto.setIntranSubventione(false);
						idto.setInfosTarifs((utils.formatFloatToCFA(tp.getTarif().getPrixNonSubventionne())));
						idto.setInfosTarifsClass(ConstantPGCA.ICON_TRAITE);
						idto.setInfosTarifsClassColor(ConstantPGCA.ICON_REFUSE_COLOR_YELLOW);
					}

				} else {
					idto.setInfosTarifs(utils.formatFloatToCFA(0));
					idto.setInfosTarifsClass(ConstantPGCA.ICON_REFUSE);
					idto.setInfosTarifsClassColor(ConstantPGCA.ICON_TRAITE_COLOR_RED);
					idto.setDejaTarifie(false);
				}

				if (tp.getStockRef().getId() == 0) { // stock dakar donc dans
														// fournisseur
					idto.setIdFournisseur(tp.getFournisseur().getId());
					idto.setLibelleFournisseur(tp.getFournisseur().getLibelle());
				} else // stock deplace de dakar vers un autre magasin : on met
						// le nom du magasin ou se trouve lintrant
				{
					idto.setIdFournisseur(tp.getFournisseur().getId());
					idto.setLibelleFournisseur(tp.getStockRef().getLibelle());
				}

				// TODO : Ex externaliser sur l'appel de la page de details
				// seulement : Recup info gerant de ce produit pour la ta : T
				PointDeVente pv = (PointDeVente) modelDAO.getEntityDBById(tp.getStockRef().getStock_id(),
						PointDeVente.class);
				if (pv != null) {
					idto.setLieuStockageActuelProduitGerant(
							pv.getGerant() != null ? pv.getGerant().getPrenom() + " " + pv.getGerant().getNom() : "");
					if (pv.getGerant().getContact() != null)
						idto.setLieuStockageActuelProduitGerantTel(pv.getGerant().getContact().getMobile());
					if (pv.getAdresse() != null)
						idto.setLieuStockageActuelduProduit(pv.getAdresse().getLibelle());
				}
				listeTypeIntrantDTO.add(idto);
			}
			return listeTypeIntrantDTO;

		} catch (Exception e) {
			Log.error("Erreur lecteur code profil id connecté");
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IntrantDTO> getlistIntrantDTOFromFournisseurFournisseur() {

		List<Intrant> listProduits;
		try {

			listProduits = (List<Intrant>) modelDAO.genericSqlClassLoaderById(Intrant.class, "pgca_intrant", "stock_id",
					0L);

			if (listProduits == null)
				return null;

			List<IntrantDTO> listeTypeIntrantDTO = new ArrayList<IntrantDTO>();

			for (Intrant tp : listProduits) {
				IntrantDTO idto = new IntrantDTO();
				idto.setIdProduit(tp.getProduit_id());
				idto.setIdtypeProduit(tp.getIntrant().getRefIntrantId());
				idto.setIdStock(tp.getStockRef().getId());
				idto.setIdCampagneProgramme(tp.getProgramme().getId());
				idto.setLibelleTypeProduit(tp.getIntrant().getTypeIntrantId().getLibelle());

				idto.setLibelleProduit(tp.getIntrant().getLibelle());
				idto.setLibelleProgramme(tp.getProgramme().getLibelle());
				idto.setLibelleCampagne(tp.getProgramme().getCampagne().getLibelle());
				idto.setLibellePointdeStock(tp.getStockLibelle());
				idto.setQuantite(tp.getQuantite());
				idto.setQuantiteInitaile(tp.getQuantiteInitial());
				idto.setTypeProduit(tp.getIntrant().getTypeIntrantId().getLibelle());
				idto.setProvenance(tp.getProvenance());
				idto.setIdCategorieIntrant(tp.getIntrant().getTypeIntrantId().getTypeIntrantId());

				idto.setPictoImages(tp.getIntrant().getTypeIntrantId().getPictoIntrant());

				if (tp.getTarif() != null) {

					idto.setDejaTarifie(true);
					if (tp.getTarif().getTauxSubvention() > 0) {
						idto.setPrixProducteur(tp.getTarif().getPrixNonSubventionne()
								- (tp.getTarif().getPrixNonSubventionne() * tp.getTarif().getTauxSubvention() / 100));
						idto.setTauxSubvention(tp.getTarif().getTauxSubvention());
						idto.setPrixNonSubventionne(tp.getTarif().getPrixNonSubventionne());
						idto.setTauxSubventionLibelle(idto.getTauxSubvention() + "%");
						idto.setIntranSubventione(true);
						idto.setInfosTarifs((utils.formatFloatToCFA(idto.getPrixProducteur())));
						idto.setInfosTarifsClass(ConstantPGCA.ICON_ENCOURS);
						idto.setInfosTarifsClassColor(ConstantPGCA.ICON_ENCOURS_COLOR_GREEN);
					} else {
						idto.setPrixProducteur(tp.getTarif().getPrixNonSubventionne());
						idto.setTauxSubvention(0L);
						idto.setPrixNonSubventionne(tp.getTarif().getPrixNonSubventionne());
						idto.setTauxSubventionLibelle(ConstantPGCA.PRODUIT_NON_SUBVENTIONNE);
						idto.setIntranSubventione(false);
						idto.setInfosTarifs((utils.formatFloatToCFA(tp.getTarif().getPrixNonSubventionne())));
						idto.setInfosTarifsClass(ConstantPGCA.ICON_TRAITE);
						idto.setInfosTarifsClassColor(ConstantPGCA.ICON_REFUSE_COLOR_YELLOW);
					}

				} else {
					idto.setInfosTarifs(utils.formatFloatToCFA(0));
					idto.setInfosTarifsClass(ConstantPGCA.ICON_REFUSE);
					idto.setInfosTarifsClassColor(ConstantPGCA.ICON_TRAITE_COLOR_RED);
					idto.setDejaTarifie(false);
				}

				if (tp.getStockRef().getId() == 0) { // stock dakar donc dans
														// fournisseur
					idto.setIdFournisseur(tp.getFournisseur().getId());
					idto.setLibelleFournisseur(tp.getFournisseur().getLibelle());
				} else // stock deplace de dakar vers un autre magasin : on met
						// le nom du magasin ou se trouve lintrant
				{
					idto.setIdFournisseur(tp.getFournisseur().getId());
					idto.setLibelleFournisseur(tp.getStockRef().getLibelle());
				}

				// TODO : Ex externaliser sur l'appel de la page de details
				// seulement : Recup info gerant de ce produit pour la ta : T
				PointDeVente pv = (PointDeVente) modelDAO.getEntityDBById(tp.getStockRef().getStock_id(),
						PointDeVente.class);
				if (pv != null) {
					idto.setLieuStockageActuelProduitGerant(
							pv.getGerant() != null ? pv.getGerant().getPrenom() + " " + pv.getGerant().getNom() : "");
					if (pv.getGerant().getContact() != null)
						idto.setLieuStockageActuelProduitGerantTel(pv.getGerant().getContact().getMobile());
					if (pv.getAdresse() != null)
						idto.setLieuStockageActuelduProduit(pv.getAdresse().getLibelle());
				}
				listeTypeIntrantDTO.add(idto);
			}
			return listeTypeIntrantDTO;

		} catch (Exception e) {
			Log.error("Erreur lecteur code profil id connecté");
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IntrantDTO> getlistIntrantDTOFromMagasin() {

		List<Intrant> listProduits;
		try {

			listProduits = (List<Intrant>) modelDAO.loadAllMagasinStock();

			if (listProduits == null)
				return null;

			List<IntrantDTO> listeTypeIntrantDTO = new ArrayList<IntrantDTO>();

			for (Intrant tp : listProduits) {
				IntrantDTO idto = new IntrantDTO();
				idto.setIdProduit(tp.getProduit_id());
				idto.setIdtypeProduit(tp.getIntrant().getRefIntrantId());
				idto.setIdStock(tp.getStockRef().getId());
				idto.setIdCampagneProgramme(tp.getProgramme().getId());
				idto.setLibelleTypeProduit(tp.getIntrant().getTypeIntrantId().getLibelle());

				idto.setLibelleProduit(tp.getIntrant().getLibelle());
				idto.setLibelleProgramme(tp.getProgramme().getLibelle());
				idto.setLibelleCampagne(tp.getProgramme().getCampagne().getLibelle());
				idto.setLibellePointdeStock(tp.getStockLibelle());
				idto.setQuantite(tp.getQuantite());
				idto.setQuantiteInitaile(tp.getQuantiteInitial());
				idto.setTypeProduit(tp.getIntrant().getTypeIntrantId().getLibelle());
				idto.setProvenance(tp.getProvenance());
				idto.setIdCategorieIntrant(tp.getIntrant().getTypeIntrantId().getTypeIntrantId());

				idto.setPictoImages(tp.getIntrant().getTypeIntrantId().getPictoIntrant());

				if (tp.getTarif() != null) {

					idto.setDejaTarifie(true);
					if (tp.getTarif().getTauxSubvention() > 0) {
						idto.setPrixProducteur(tp.getTarif().getPrixNonSubventionne()
								- (tp.getTarif().getPrixNonSubventionne() * tp.getTarif().getTauxSubvention() / 100));
						idto.setTauxSubvention(tp.getTarif().getTauxSubvention());
						idto.setPrixNonSubventionne(tp.getTarif().getPrixNonSubventionne());
						idto.setTauxSubventionLibelle(idto.getTauxSubvention() + "%");
						idto.setIntranSubventione(true);
						idto.setInfosTarifs((utils.formatFloatToCFA(idto.getPrixProducteur())));
						idto.setInfosTarifsClass(ConstantPGCA.ICON_ENCOURS);
						idto.setInfosTarifsClassColor(ConstantPGCA.ICON_ENCOURS_COLOR_GREEN);
					} else {
						idto.setPrixProducteur(tp.getTarif().getPrixNonSubventionne());
						idto.setTauxSubvention(0L);
						idto.setPrixNonSubventionne(tp.getTarif().getPrixNonSubventionne());
						idto.setTauxSubventionLibelle(ConstantPGCA.PRODUIT_NON_SUBVENTIONNE);
						idto.setIntranSubventione(false);
						idto.setInfosTarifs((utils.formatFloatToCFA(tp.getTarif().getPrixNonSubventionne())));
						idto.setInfosTarifsClass(ConstantPGCA.ICON_TRAITE);
						idto.setInfosTarifsClassColor(ConstantPGCA.ICON_REFUSE_COLOR_YELLOW);
					}

				} else {
					idto.setInfosTarifs(utils.formatFloatToCFA(0));
					idto.setInfosTarifsClass(ConstantPGCA.ICON_REFUSE);
					idto.setInfosTarifsClassColor(ConstantPGCA.ICON_TRAITE_COLOR_RED);
					idto.setDejaTarifie(false);
				}

				if (tp.getStockRef().getId() == 0) { // stock dakar donc dans
														// fournisseur
					idto.setIdFournisseur(tp.getFournisseur().getId());
					idto.setLibelleFournisseur(tp.getFournisseur().getLibelle());
				} else // stock deplace de dakar vers un autre magasin : on met
						// le nom du magasin ou se trouve lintrant
				{
					idto.setIdFournisseur(tp.getFournisseur().getId());
					idto.setLibelleFournisseur(tp.getStockRef().getLibelle());
				}

				// TODO : Ex externaliser sur l'appel de la page de details
				// seulement : Recup info gerant de ce produit pour la ta : T
				PointDeVente pv = (PointDeVente) modelDAO.getEntityDBById(tp.getStockRef().getStock_id(),
						PointDeVente.class);
				if (pv != null) {
					idto.setLieuStockageActuelProduitGerant(
							pv.getGerant() != null ? pv.getGerant().getPrenom() + " " + pv.getGerant().getNom() : "");
					if (pv.getGerant().getContact() != null)
						idto.setLieuStockageActuelProduitGerantTel(pv.getGerant().getContact().getMobile());
					if (pv.getAdresse() != null)
						idto.setLieuStockageActuelduProduit(pv.getAdresse().getLibelle());
				}
				listeTypeIntrantDTO.add(idto);
			}
			return listeTypeIntrantDTO;

		} catch (Exception e) {
			Log.error("Erreur lecteur code profil id connecté");
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IntrantDTO> loadAllProduitOfAllCampagne() {

		List<Intrant> listProduits;
		try {
			listProduits = (List<Intrant>) modelDAO.genericClassLoader(Intrant.class);

			List<IntrantDTO> listeTypeIntrantDTO = new ArrayList<IntrantDTO>();

			for (Intrant tp : listProduits) {
				IntrantDTO idto = new IntrantDTO();
				idto.setIdProduit(tp.getProduit_id());
				idto.setIdtypeProduit(tp.getIntrant().getRefIntrantId());
				idto.setIdStock(tp.getStockRef().getId());
				idto.setIdCampagneProgramme(tp.getProgramme().getId());
				idto.setLibelleTypeProduit(tp.getIntrant().getTypeIntrantId().getLibelle());

				idto.setLibelleProduit(tp.getIntrant().getLibelle());
				idto.setLibelleProgramme(tp.getProgramme().getLibelle());
				idto.setLibelleCampagne(tp.getProgramme().getCampagne().getLibelle());
				idto.setLibellePointdeStock(tp.getStockRef().getLibelle());
				idto.setQuantite(tp.getQuantite());
				idto.setTypeProduit(tp.getIntrant().getTypeIntrantId().getLibelle());
				idto.setProvenance(tp.getProvenance());

				idto.setPictoImages(tp.getIntrant().getTypeIntrantId().getPictoIntrant());

				if (tp.getTarif() != null) {
					idto.setPrixProducteur(tp.getTarif().getPrixProducteur());
					idto.setTauxSubvention(tp.getTarif().getTauxSubvention());

					if (tp.getTarif().getTauxSubvention() == 0)
						idto.setTauxSubventionLibelle(ConstantPGCA.PRODUIT_NON_SUBVENTIONNE);
					else
						idto.setTauxSubventionLibelle(idto.getTauxSubvention() + "%");

					idto.setPrixNonSubventionne(tp.getTarif().getPrixNonSubventionne());

				}

				listeTypeIntrantDTO.add(idto);
			}
			return listeTypeIntrantDTO;

		} catch (Exception e) {
			Log.error("Erreur lecteur code profil id connecté");
			e.printStackTrace();
			return null;

		}
	}

	@Override
	public List<IntrantDTO> loadAllIntrantDTOByidCampagne(Long idCampagne) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateIntranCampagne(IntrantDTO idto) throws EntityDBDAOException {
		Intrant tp = (Intrant) modelDAO.getEntityDBById(idto.getIdProduit(), Intrant.class);

		if (tp == null) {
			Log.error("Impossible de recuperer le produit à modifier");
			return false;
		}

		try {
			ProgrammeAgricol cap = (ProgrammeAgricol) modelDAO.getEntityDBById(idto.getIdCampagneProgramme(),
					ProgrammeAgricol.class);
			if (cap == null) {
				Log.error("Impossible de recuperer la campagne  du produit");
				return false;
			}
			tp.setQuantite(tp.getQuantite() + idto.getQuantite());
			tp.setProvenance(idto.getProvenance());
			modelDAO.save(tp);
			Log.info("Produit modifié  avec succés : " + tp.getIntrant().getTypeIntrantId().getLibelle());
			idto.setLibelleProduit(tp.getIntrant().getLibelle());
			return true;

		} catch (EntityDBDAOException e) {
			Log.error("Impossible de recuperer le programme de la campagne  du produit");
		}

		return false;

	}

	/*******
	 * modification d'un ref d'un stock
	 * 
	 * @throws EntityDBDAOException
	 ***/
	@Override
	public boolean updateIntranOfStock(IntrantDTO idto) throws EntityDBDAOException {
		Intrant tp = null;
		tp = (Intrant) modelDAO.getEntityDBById(idto.getIdProduit(), Intrant.class);

		if (tp == null) {
			Log.error("Impossible de recuperer le produit à modifier");
			return false;
		}

		try {
			ProgrammeAgricol cap = (ProgrammeAgricol) modelDAO.getEntityDBById(idto.getIdCampagneProgramme(),
					ProgrammeAgricol.class);

			Fournisseur f = (Fournisseur) modelDAO.getEntityDBById(idto.getIdFournisseur(), Fournisseur.class);

			tp.setProgramme(cap);
			tp.setFournisseur(f);
			// tp.setQuantite(idto.getQuantite());
			tp.setQuantiteInitial(idto.getQuantite());
			tp.setProvenance(idto.getProvenance());

			modelDAO.save(tp);
			Log.info("Produit modifié  avec succés : " + tp.getIntrant().getTypeIntrantId().getLibelle());
			return true;
		} catch (EntityDBDAOException e) {
			Log.error("Impossible de recuperer le programme de la campagne  du produit");
		}
		return false;
	}

	@Override
	public boolean updateQuantiteIntranCampagne(IntrantDTO idto) {
		Intrant tp = null;

		try {
			tp = (Intrant) modelDAO.loadIntrantByTypeAndStock(idto.getIdProduit(), idto.getIdStock());
			tp.setQuantite(tp.getQuantite() + idto.getQuantite());

			modelDAO.save(tp);
			return true;
		} catch (EntityDBDAOException e) {
			Log.error("Impossible de recuperer le produit de produit");
		}
		return false;
	}

	/*************** PRODUITDTO DTO ***************************/

	@Override
	public ProduitDTO loadProduitDTObyIdProduit(Long idProdui) throws ProgrammeException, EntityDBDAOException {
		Intrant tp = (Intrant) modelDAO.getEntityDBById(idProdui, Intrant.class);

		if (tp == null) {
			Log.error("Impossible de recuperer le produit avec l'id <" + idProdui + ">");
			return null;
		}

		ProduitDTO idto = new ProduitDTO();
		idto.setIdProduit(tp.getProduit_id());
		idto.setIdtypeProduit(tp.getIntrant().getTypeIntrantId().getTypeIntrantId());
		idto.setIdStockProduit(tp.getStockRef().getId());
		idto.setIdCampagne(tp.getProgramme().getId());
		idto.setLibelleProduit(tp.getIntrant().getLibelle());
		idto.setLibelleProgramme(tp.getProgramme().getLibelle());
		idto.setLibelleCampagne(tp.getProgramme().getCampagne().getLibelle());
		idto.setLibellePointdeStock(tp.getStockRef().getLibelle());
		idto.setQuantite(tp.getQuantite());
		idto.setTypeProduit(tp.getIntrant().getTypeIntrantId().getLibelle());
		idto.setProvenance(tp.getProvenance());

		idto.setPictoImages(tp.getIntrant().getTypeIntrantId().getPictoIntrant());

		if (tp.getTarif() != null) {
			if (tp.getTarif().getTauxSubvention() > 0)
				idto.setPrixUnitaire(tp.getTarif().getPrixNonSubventionne()
						- (tp.getTarif().getPrixNonSubventionne() * tp.getTarif().getTauxSubvention() / 100));
			else
				idto.setPrixUnitaire(tp.getTarif().getPrixNonSubventionne());
		} else {
			// produit pas encore tarifié
			idto.setPrixUnitaire(0F);
		}

		return idto;
	}

	// public Inotification getNotifierService() {
	// return notifierService;
	// }
	//
	// public void setNotifierService(Inotification notifierService) {
	// this.notifierService = notifierService;
	// }

	@Override
	public ProduitDTO loadProduitDTOReferentielbyIdProduit(Long idProdui)
			throws ProgrammeException, EntityDBDAOException {
		ReferentialIntrants tp = (ReferentialIntrants) modelDAO.getEntityDBById(idProdui, ReferentialIntrants.class);

		if (tp == null) {
			Log.error("Impossible de recuperer le produit avec l'id <" + idProdui + ">");
			return null;
		}

		ProduitDTO idto = new ProduitDTO();
		idto.setIdProduit(tp.getRefIntrantId());
		idto.setIdtypeProduit(
				tp.getTypeIntrantId().getTypeIntrantId() != null ? tp.getTypeIntrantId().getTypeIntrantId() : null);
		idto.setLibelle(tp.getLibelle());

		return idto;
	}

	@Override
	public boolean createBL(BonDeLivraisonDTO bondeLivraisonDTO, List<ProduitDTO> listeProduitOfBl) {

		try {
			BonDeLivraison blToSave = new BonDeLivraison();
			Intrant produitLieeAuBlduStock;
			Personne chauffeur = (Personne) modelDAO.getEntityDBById(new Long(bondeLivraisonDTO.getChauffeurid()),
					Personne.class);
			Transporteur transporteur = (Transporteur) modelDAO
					.getEntityDBById(new Long(bondeLivraisonDTO.getTransporteurid()), Transporteur.class);
			Camion camionumero = (Camion) modelDAO.getEntityDBById(new Long(bondeLivraisonDTO.getCamionid()),
					Camion.class);

			ProgrammeAgricol programme = (ProgrammeAgricol) modelDAO.getEntityDBById(bondeLivraisonDTO.getProgrammeId(),
					ProgrammeAgricol.class);

			if (chauffeur == null || transporteur == null || camionumero == null || programme == null)
				return false;

			blToSave.setProgramme(programme);
			blToSave.setChauffeurId(chauffeur);
			blToSave.setTransporteurId(transporteur);
			blToSave.setNumeroCamionId(camionumero);
			blToSave.setIdStockReceptionnaire(bondeLivraisonDTO.getIdStockReceptionnaire());
			blToSave.setProvenanceProduit(bondeLivraisonDTO.getProvenanceBL());
			blToSave.setIdStockExpedition(bondeLivraisonDTO.getIdStockSortant());
			blToSave.setIdAuteurBl(bondeLivraisonDTO.getIdAuteurBl());
			blToSave.setBlStatut(0);

			if (bondeLivraisonDTO.getDateEdition() != "") {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date d = sdf.parse(bondeLivraisonDTO.getDateEdition());
				blToSave.setDateEdition(d);
			}

			modelDAO.save(blToSave);
			blToSave.setReferenceBL("LV" + blToSave.getBl_id());
			modelDAO.save(blToSave);
			modelDAO.synchroniseWithDB(blToSave);

			for (ProduitDTO pd : listeProduitOfBl) {
				BonDeLivraisonProduit blp = new BonDeLivraisonProduit();

				blp.setIdProduit(pd.getIdProduit());
				blp.setLibelle(pd.getLibelleProduit());
				blp.setQuantite(pd.getQuantite());
				blp.setLibelleProgramme(pd.getLibelleProgramme());
				blp.setLibelleCampagne(pd.getLibelleCampagne());
				blp.setIdStockSortant(pd.getIdStockProduit());
				blp.setLibelleStockSortant(pd.getProvenance());
				blp.setBlAssocie(blToSave);

				modelDAO.save(blp);

				// On enleve la quantite du BL dans le stock sortant pour tout
				// les produit present sur le BL
				produitLieeAuBlduStock = (Intrant) modelDAO.getEntityDBById(blp.getIdProduit(), Intrant.class);

				produitLieeAuBlduStock.setQuantite(produitLieeAuBlduStock.getQuantite() - blp.getQuantite());

				modelDAO.save(produitLieeAuBlduStock);

				bondeLivraisonDTO.setProgrammeLibelle(programme.getLibelle());
				bondeLivraisonDTO.setCampagneLibelle(
						programme.getCampagne() != null ? programme.getCampagne().getLibelle() : "");
			}

			bondeLivraisonDTO.setReferenceBL(blToSave.getReferenceBL());
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			Log.error("Une erreur est survenue lors de lenregtrement du BL cause" + e.getMessage() + e.getStackTrace());
			return false;

		}
		// TODO Auto-generated method stub
	}

	@Override
	public List<BonDeLivraisonDTO> loadBondeLivraisonByIdPointDeVente(Long connectedUseridStock)
			throws EntityDBDAOException {

		List<BonDeLivraison> bl = modelDAO.loadABLByIdStock(connectedUseridStock);

		List<BonDeLivraisonDTO> bldto = new ArrayList<BonDeLivraisonDTO>();

		for (BonDeLivraison b : bl) {
			BonDeLivraisonDTO dto = new BonDeLivraisonDTO();

			dto.setId(b.getBl_id());
			dto.setLibelle(b.getLibelle());
			dto.setProvenanceBL(b.getProvenanceProduit());
			dto.setCamionnumero(b.getNumeroCamionId().getNumeroCamion());
			dto.setChauffeurlibelle(b.getChauffeurId().getNom() + " " + b.getChauffeurId().getPrenom());
			dto.setTransporteurlibelle(b.getTransporteurId().getLibelle());
			dto.setTransporteurid(b.getTransporteurId().getIdtransporteur());
			dto.setDateEdition(b.getDateEdition() != null ? b.getDateEdition().toString() : "");
			dto.setProgrammeLibelle(b.getProgramme().getLibelle());
			dto.setProgrammeId(b.getProgramme().getPgca_idprog());
			Stock s = (Stock) modelDAO.getEntityDBById(b.getIdStockReceptionnaire(), Stock.class);
			dto.setZoneDeReception(s != null ? s.getLibelle() : "");
			dto.setIdStockReceptionnaire(s != null ? s.getStock_id() : null);

			if (b.getBlStatut() == 0) {
				dto.setStatusLibelle("En attente de validation");
				dto.setStatus(b.getBlStatut());
				;
			} else if (b.getBlStatut() == 1) {
				dto.setStatusLibelle("Accepté");
				dto.setStatus(b.getBlStatut());
				;
			} else if (b.getBlStatut() == 2) {
				dto.setStatusLibelle("Accepter avec réserve");
				dto.setStatus(b.getBlStatut());
				;
			}
			bldto.add(dto);
		}

		return bldto;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BonDeLivraisonDTO getAllProduitsFromIdBL(Long id) throws EntityDBDAOException {

		BonDeLivraison bl = (BonDeLivraison) modelDAO.getEntityDBById(id, BonDeLivraison.class);

		if (bl == null)
			return null;

		List<BonDeLivraisonProduit> blp = (List<BonDeLivraisonProduit>) modelDAO.genericSqlClassLoaderById(
				BonDeLivraisonProduit.class, "pgca_bonDeLivraisonProduit", "bl_id", bl.getBl_id());
		if (blp == null)
			return null;

		String pictoBl = null; // last intra s'il y'a plusieurs sur le BL

		List<ProduitDTO> bldto = new ArrayList<ProduitDTO>();
		Double valeurAppromative = 0.0;
		int poidTotal = 0;

		for (BonDeLivraisonProduit b : blp) {
			// Chaque Produit associè au BL ;
			Intrant pAssocie = (Intrant) modelDAO.getEntityDBById(b.getIdProduit(), Intrant.class);

			if (pAssocie != null) {
				ProduitDTO pdto = new ProduitDTO();
				pdto.setIdProduit(pAssocie.getProduit_id());
				pdto.setLibelle(pAssocie.getIntrant().getLibelle());
				// Type
				pdto.setIdtypeProduit(pAssocie.getIntrant().getTypeIntrantId().getTypeIntrantId());
				pdto.setLibelleTypeProduit(pAssocie.getIntrant().getTypeIntrantId().getLibelle());
				// Programme
				pdto.setIdProgramme(pAssocie.getProgramme().getId_ca());
				pdto.setLibelleProgramme(pAssocie.getProgramme().getLibelle());
				// Campagne
				pdto.setIdCampagne(pAssocie.getProgramme().getId_ca());
				pdto.setLibelleCampagne(pAssocie.getProgramme().getCampagne().getLibelle());
				// Prix
				pdto.setPrixUnitaire(pAssocie.getTarif() != null ? pAssocie.getTarif().getPrixNonSubventionne() : 0f);
				// Stock sortant
				pdto.setIdStockProduit(pAssocie.getStockRef().getStock_id());
				// Quantite A vendre depuis le BL (à déduire sur le Stock de
				// sortienet a ajouter sur le stock de reception)
				pdto.setQuantite(b.getQuantite());
				// pdto.setPrixTotal(b.getQuantite() * (pAssocie.getTarif() !=
				// null ? pAssocie.getTarif().getPrixNonSubventionne() : 0L));
				float prix = pAssocie.getTarif() != null ? pAssocie.getTarif().getPrixNonSubventionne() : 0L;
				float cast = new Float(b.getQuantite());
				prix = prix * cast;
				valeurAppromative += prix;
				poidTotal += b.getQuantite();
				bldto.add(pdto);

				// les images ont le meme noms que les intrant
				pictoBl = pAssocie.getIntrant().getLibelle().replace(" ", "").toLowerCase();
			}
		}
		// // Tous les produit du BL
		BonDeLivraisonDTO btdo = new BonDeLivraisonDTO();

		btdo.setId(bl.getBl_id());
		btdo.setIdStockReceptionnaire(bl.getIdStockReceptionnaire());
		// List produit
		btdo.setListProduitsOfBL(bldto);
		// Poids
		btdo.setPoids(poidTotal);
		btdo.setPrixtotal(valeurAppromative);
		// Contact chauffeur
		// btdo.setAuteurBl(
		// bl.getChauffeurId().getContact() != null ?
		// bl.getChauffeurId().getContact().getMobile() : "Inconnu");

		// Auteur bl
		Personne exp = (Personne) modelDAO.getEntityDBById(bl.getIdAuteurBl(), Personne.class);
		btdo.setAuteurBl(exp != null ? exp.getPrenom() + " " + exp.getNom() : "Non renseigné");
		btdo.setContactAuteurBl(exp.getContact() != null ? exp.getContact().getMobile() : "Non renseigné");

		// Contact chauffeur
		exp = (Personne) modelDAO.getEntityDBById(bl.getChauffeurId().getId(), Personne.class);
		btdo.setContactChauffeur(exp.getContact() != null ? exp.getContact().getMobile() : "Non renseigné");
		btdo.setProvenanceBL(bl.getProvenanceProduit());

		btdo.setPicto(pictoBl);

		return btdo;
	}

	@Override
	public boolean updateStockFromBl(BonDeLivraisonDTO selectedBLDTO, Long idStockReceptionnaire,
			List<ProduitDTO> listProduitsOfBL) throws EntityDBDAOException {
		Stock s = (Stock) modelDAO.getEntityDBById(idStockReceptionnaire, Stock.class);

		boolean success = false;
		if (s == null)
			return false;

		for (ProduitDTO pdto : listProduitsOfBL) {
			Intrant p = (Intrant) modelDAO.getEntityDBById(pdto.getIdProduit(), Intrant.class);

			if (p == null)
				return false;

			// si le type de produit existe deja pour le meme programme , on
			// ajoute la quantite recu sinon on le créé

			// Long idTypeProduit =
			// p.getIntrant().getTypeIntrantId().getTypeIntrantId();
			// Intrant pStock =
			// modelDAO.loadTypeOfSpecificProduitOfSpecificStock(s.getStock_id(),
			// idTypeProduit);

			Intrant pStock = modelDAO.loadIntrantByTypeAndStock(p.getIntrant().getId(), s.getStock_id());

			// si le produit existe on change la quantite : si on ajoute un
			// produit existant avec le meme tarif on garde l'ancien tarif (a
			// confirmer avec niang)
			if (pStock != null) {
				pStock.setQuantite(pStock.getQuantite() + pdto.getQuantite());
				modelDAO.save(pStock);
				success = true;
			} else {
				try {
					Intrant newP = new Intrant();
					Tarificateur tarifdeRef = new Tarificateur();

					if (p.getTarif() != null) {
						tarifdeRef.setAuteurTarification(p.getTarif().getAuteurTarification());
						tarifdeRef.setTotalsubventionEtatArecouvrir(p.getTarif().getTotalsubventionEtatArecouvrir());
						tarifdeRef.setTauxSubvention(p.getTarif().getTauxSubvention());
						tarifdeRef.setSubventionne(p.getTarif().getSubventionne());
						tarifdeRef.setPrixProducteur(p.getTarif().getPrixProducteur());
						tarifdeRef.setPrixNonSubventionne(p.getTarif().getPrixNonSubventionne());
						modelDAO.save(tarifdeRef);

						newP.setTarif(tarifdeRef);

					}

					// p.setProduit_id(null);
					newP.setQuantite(pdto.getQuantite());
					newP.setQuantiteInitial(pdto.getQuantite());
					newP.setStockRef(s);
					newP.setStockLibelle(s.getLibelle());
					newP.setIntrant(p.getIntrant());
					newP.setProgramme(p.getProgramme());
					newP.setFournisseur(p.getFournisseur());
					newP.setStockLibelle(s.getLibelle());

					modelDAO.save(newP);
					success = true;
				} catch (Exception e) {
					success = false;
					Log.error("================= LOG ERROR :  " + e.getMessage());
				}
			}
		}

		if (success) // Updte Status BL a accepter
		{
			BonDeLivraison bl = (BonDeLivraison) modelDAO.getEntityDBById(selectedBLDTO.getId(), BonDeLivraison.class);

			if (bl == null)
				return false;

			bl.setBlStatut(1); // accepeter Bl : changer status BL
			modelDAO.save(bl);
		}
		return success;
	}

	/****** accepter BL avec RESERVE *****/
	@Override
	public boolean updateStockFromBlWithReserve(BonDeLivraisonDTO selectedBLDTO, Long idStockReceptionnaire,
			List<ProduitDTO> listProduitsOfBL, LitigesDTO litige) throws EntityDBDAOException {
		Stock s = (Stock) modelDAO.getEntityDBById(idStockReceptionnaire, Stock.class);

		boolean success = false;
		if (s == null)
			return false;

		for (ProduitDTO pdto : listProduitsOfBL) {
			Intrant p = (Intrant) modelDAO.getEntityDBById(pdto.getIdProduit(), Intrant.class);

			if (p == null)
				return false;

			// si le type de produit existe deja pour le meme programme , on
			// ajoute la quantite recu sinon on le créé

			Long idTypeProduit = p.getIntrant().getTypeIntrantId().getTypeIntrantId();

			Intrant pStock = modelDAO.loadTypeOfSpecificProduitOfSpecificStock(s.getStock_id(), idTypeProduit);

			// si le produit existe on change la quantite : si on ajoute un
			// produit existant avec le meme tarif on garde l'ancien tarif (a
			// confirmer avec niang)
			if (pStock != null) {
				pStock.setQuantite(pStock.getQuantite() + (pdto.getQuantite() - litige.getQuantiteManquante()));
				modelDAO.save(pStock);
				success = true;
			} else {
				try {
					Intrant newP = new Intrant();
					Tarificateur tarifdeRef = new Tarificateur();
					tarifdeRef.setAuteurTarification(p.getTarif().getAuteurTarification());
					tarifdeRef.setTotalsubventionEtatArecouvrir(p.getTarif().getTotalsubventionEtatArecouvrir());
					tarifdeRef.setTauxSubvention(p.getTarif().getTauxSubvention());
					tarifdeRef.setSubventionne(p.getTarif().getSubventionne());
					tarifdeRef.setPrixProducteur(p.getTarif().getPrixProducteur());
					tarifdeRef.setPrixNonSubventionne(p.getTarif().getPrixNonSubventionne());

					modelDAO.save(tarifdeRef);
					newP.setTarif(tarifdeRef);

					// p.setProduit_id(null);
					newP.setQuantite(pdto.getQuantite() - litige.getQuantiteManquante());
					newP.setQuantiteInitial(pdto.getQuantite());
					newP.setStockRef(s);
					newP.setStockLibelle(s.getLibelle());
					newP.setIntrant(p.getIntrant());
					newP.setProgramme(p.getProgramme());
					newP.setFournisseur(p.getFournisseur());
					newP.setStockLibelle(s.getLibelle());

					modelDAO.save(newP);
					success = true;
				} catch (Exception e) {
					success = false;
					Log.error("================= LOG ERROR :  " + e.getMessage());
				}
			}
		}

		if (success) // Updte Status BL a accepter
		{
			BonDeLivraison bl = (BonDeLivraison) modelDAO.getEntityDBById(selectedBLDTO.getId(), BonDeLivraison.class);

			if (bl == null)
				return false;

			bl.setBlStatut(4); // 4 status : accepté avec reserve
			modelDAO.save(bl);

			Litiges l = new Litiges();

			HttpSession session = null;

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			if (request != null)
				session = request.getSession(false);

			l.setDetailsLitige(litige.getDetailsLitige());
			l.setBlReference(bl);
			l.setChauffeur(bl.getChauffeurId().getPrenom() + " " + bl.getChauffeurId().getNom());
			l.setNumeroCamionId(bl.getNumeroCamionId());
			l.setTransporteurId(bl.getTransporteurId());
			l.setProgramme(bl.getProgramme());
			l.setReceptionnaireAgent((String) session.getAttribute("connectedUserName"));
			l.setReceptionnaireMagsin((String) session.getAttribute("connectedUserPointPhysique"));
			l.setChauffeurId(bl.getChauffeurId());
			l.setQuantiteTotalLige(litige.getQuantiteManquante());
			l.setNombreDesacs(litige.getNombreDesacs());
			l.setDateLitiges(new Date());

			modelDAO.save(l);
		}
		return success;
	}

	/**** FIN BL AVEC RESERVES *********/

	/**** Deduction de la quantite des produits sur un stock donné **/

	@Override
	public boolean substractProduitFromStock(Long idStock, List<ProduitDTO> listProduitsOfBL)
			throws EntityDBDAOException {
		boolean success = false;

		Stock s = (Stock) modelDAO.getEntityDBById(idStock, Stock.class);
		if (s == null)
			return false;

		for (ProduitDTO pdto : listProduitsOfBL) {
			Intrant stockActuel = (Intrant) modelDAO.getEntityDBById(pdto.getIdProduit(), Intrant.class);
			// deduction quantité à céder/vendre (OL & Ventes)
			if (stockActuel != null) {
				stockActuel.setQuantite(stockActuel.getQuantite() - pdto.getQuantite());
				modelDAO.save(stockActuel);
				success = true;
			}
		}
		return success;
	}

	@Override
	public boolean verifStock(Long idStock, List<ProduitDTO> listProduitsOfBL) throws EntityDBDAOException {
		
		// Tout les intrants sont dispo
		Boolean AllIntrantDispo = false;
		
		for (ProduitDTO stockAdeduire : listProduitsOfBL) {
			Intrant stockActuel = (Intrant) modelDAO.getEntityDBById(stockAdeduire.getIdProduit(), Intrant.class);
			
			if (stockActuel != null && stockActuel.getQuantite()  >=  stockAdeduire.getQuantite())
				AllIntrantDispo = true;
			else
				AllIntrantDispo = true;
			
			/* UPDATE DK 9/9/2019
			if (p == null)
				return false;

			Long idTypeProduit = p.getIntrant().getTypeIntrantId().getTypeIntrantId();
			Intrant pStock = modelDAO.loadTypeOfSpecificProduitOfSpecificStock(idStock, idTypeProduit);
			if (pStock == null || pStock != null && pStock.getQuantite() > pdto.getQuantite())
				return false; */
		}
		return AllIntrantDispo;
	}

	// Recupearation des ventes effectue sur un stock donné
	@Override
	@SuppressWarnings("unchecked")
	public List<VentesDTO> loadNbVentesByIdPointDeVente(Long connectedUseridStock) throws EntityDBDAOException {

		HttpSession session = null;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null)
			session = request.getSession(false);

		Long profilUserId = (Long) session.getAttribute("connectedUserProfilID");
		Long connectedPersonneUserid = (Long) session.getAttribute("connectedUserPersonneid");

		List<Ventes> ventes;

		if (profilUserId == 4L) {
			// si c un superviseur , on recupere tout les ventes effectué par le
			// cordinateur
			ventes = (List<Ventes>) modelDAO.loadVentesByGerantFournisseurs(connectedPersonneUserid);
		} else {
			// si c magsinier que les ventes lies a son stock
			ventes = (List<Ventes>) modelDAO.genericSqlClassLoaderById(Ventes.class, "pgca_ventes", "stock_id",
					connectedUseridStock);
		}

		if (ventes == null)
			return null;

		List<VentesDTO> ventesDTO = new ArrayList<VentesDTO>();

		for (Ventes v : ventes) {
			VentesDTO dto = new VentesDTO();
			dto.setVentes_id(v.getId());
			dto.setMontantEncaisse(v.getMontantEncaisse());
			dto.setDateVente(v.getDateDeVente());
			dto.setMontantVente(v.getMontantVente());
			String nom = (modelDAO.getEntityDBById(v.getVendeur().getId(), Personne.class)) != null
					? v.getVendeur().getPrenom() + "" + v.getVendeur().getNom() : "";
			dto.setVendeurLibelle(nom);
			dto.setZoneVente(v.getZoneVente());
			dto.setMoyenPaiement(v.getMoyenPaiement() != null ? v.getMoyenPaiement() : "");

			// dto.setLibelleProduitVentes(v.getProduitProduit() != null ?
			// v.getProduitProduit().getLibelleProduit() : "");
			dto.setQuantiteProduitVendu(v.getQunatiteVendu());
			dto.setClient(v.getClient());

			// recuperation des détails du produit Vendu

			List<VenteProduitAssocie> details = (List<VenteProduitAssocie>) modelDAO.genericSqlClassLoaderById(
					VenteProduitAssocie.class, "pgca_venteProduitsAssocies", "ventes_id", v.getVentes_id());

			if (details != null && details.size() > 0) {
				dto.setLibelleProduitVentes(details.get(0).getProduitVendu().getLibelle());
				dto.setQuantiteProduitVendu(details.get(0).getQuantiteVendu().intValue());
				dto.setPrixUnitaire(details.get(0).getPrixUnitaire());
			}

			ventesDTO.add(dto);
		}
		return ventesDTO;
	}

	@Override
	public List<CreditDTO> loadNbCreditsByIdPointDeVente(Long connectedUseridStock) {
		List<Credit> creditsList = (List<Credit>) modelDAO.genericSqlClassLoaderById(Credit.class, "pgca_credit",
				"stock_id", connectedUseridStock);

		if (creditsList == null)
			return null;

		List<CreditDTO> creditDTO = new ArrayList<CreditDTO>();

		for (Credit c : creditsList) {
			CreditDTO cto = new CreditDTO();

			cto.setCredit_id(c.getCredit_id());
			cto.setTelSouscripteur(c.getTelSouscripteur());
			cto.setNomsouscripteur(c.getSouscripteur());
			cto.setMontantInitialCredit(c.getMontantInitialCredit());
			cto.setMontantRestantApayer(c.getMontantRestantApayer());
			cto.setAuteurCreditLibelle(
					c.getAuteur() != null ? c.getAuteur().getPrenom() + " " + c.getAuteur().getNom() : "");

			if (c.getDateContraction() != null) {
				String[] tmp = c.getDateContraction().toString().split(" ");
				cto.setDateContraction(tmp[0]);
			}

			if (c.getDateDerniereAvanceSurCredit() != null) {
				String[] tmp = c.getDateDerniereAvanceSurCredit().toString().split(" ");
				cto.setDateContraction(tmp[0]);
			}

			cto.setReferenceCredit(c.getReferenceCredit());
			cto.setResumeCredit(c.getResumeCredit());
			cto.setZoneCredit(c.getZone());
			cto.setVenteConcerneLibelle(c.getVenteConcerne() + "");

			creditDTO.add(cto);
		}
		return creditDTO;
	}

	@Override
	public List<ProduitDTO> getListProduitsVEnduFromIdVente(Long idVEnte) {
		@SuppressWarnings("unchecked")
		List<VenteProduitAssocie> lp = (List<VenteProduitAssocie>) modelDAO.genericSqlClassLoaderById(
				VenteProduitAssocie.class, "pgca_venteProduitsAssocies", "ventes_id", idVEnte);

		if (lp == null)
			return null;

		List<ProduitDTO> ventes = new ArrayList<ProduitDTO>();

		for (VenteProduitAssocie v : lp) {
			ProduitDTO pdto = new ProduitDTO();

			pdto.setLibelle(v.getProduitVendu().getIntrant().getLibelle());
			pdto.setQuantite(v.getQuantiteVendu());
			pdto.setPrixTotal(v.getMontantTotalVendu());

			ventes.add(pdto);
		}
		return ventes;
	}

	@Override
	public List<MiseEnplaceDTOPointDeVente> loadAllMiseEnPlaceByIdCommune(Long idVEnte) throws EntityDBDAOException {
		@SuppressWarnings("unchecked")
		List<MiseEnPlaceAEffectuer> lp = modelDAO.loadMiseEnPlaceByidCommune(idVEnte);

		if (lp == null)
			return null;

		List<MiseEnplaceDTOPointDeVente> ventes = new ArrayList<MiseEnplaceDTOPointDeVente>();

		for (MiseEnPlaceAEffectuer v : lp) {
			MiseEnplaceDTOPointDeVente pdto = new MiseEnplaceDTOPointDeVente();

			pdto.setIdMiseEnPlace(v.getId_MEPaAF());
			pdto.setIdSlectedMiseEnplace(v.getId_MEPaAF());
			// pdto.setIdPointDeVente(
			// v.getPointdeVenteConcerne() != null ?
			// v.getPointdeVenteConcerne().getPtv_id() : null);
			pdto.setNomPointDeVente(
					v.getPointdeVenteConcerne() != null ? v.getPointdeVenteConcerne().getLibelle() : "Inconnu");

			pdto.setQuotaIntrantAMettreEnplace(v.getQuota());
			// pdto.setNomGerant(v.getPointdeVenteConcerne() != null &&
			// v.getPointdeVenteConcerne().getGerant() != null
			// ? v.getPointdeVenteConcerne().getGerant().getPrenom() + " "
			// + v.getPointdeVenteConcerne().getGerant().getNom()
			// : "Inconnu");
			pdto.setLibelleIntrantAMettreEnplace(
					v.getProduitAmettreEnPlace() != null ? v.getProduitAmettreEnPlace().getLibelle() : "Inconnu");
			pdto.setIdIntrantAMettreEnplace(
					v.getProduitAmettreEnPlace() != null ? v.getProduitAmettreEnPlace().getRefIntrantId() : null);
			pdto.setIdProgramme(v.getProgrammeConcerne().getPgca_idprog());
			pdto.setNomProgramme(v.getProgrammeConcerne().getLibelle());

			// lecture des mise en place deja effectuee
			List<MiseEnPlaceEffectuerDTO> listMEPeffectuee = loadAllMiseEnPlaceEffectueeByidMAP(v.getId_MEPaAF());

			double totalCumul = 0;

			for (MiseEnPlaceEffectuerDTO ll : listMEPeffectuee)
				totalCumul += ll.getQuantiteAmettreEnplace();

			pdto.setCumulIntrantAMettreEnplace(totalCumul);
			pdto.setReliquatIntrantAMettreEnplace(v.getQuota() - totalCumul);

			ventes.add(pdto);
		}
		return ventes;
	}

	@Override
	public int loadAllMiseEnPlaceByTermine() throws EntityDBDAOException {
		@SuppressWarnings("unchecked")
		List<MiseEnPlaceAEffectuer> lp = modelDAO.loadAllMiseEnPlaceByTermine();

		if (lp != null)
			return lp.size();

		return 0;
		/*
		 * List<MiseEnplaceDTOPointDeVente> ventes = new
		 * ArrayList<MiseEnplaceDTOPointDeVente>();
		 * 
		 * for (MiseEnPlaceAEffectuer v : lp) { MiseEnplaceDTOPointDeVente pdto
		 * = new MiseEnplaceDTOPointDeVente();
		 * 
		 * pdto.setIdMiseEnPlace(v.getId_MEPaAF());
		 * pdto.setIdSlectedMiseEnplace(v.getId_MEPaAF()); //
		 * pdto.setIdPointDeVente( // v.getPointdeVenteConcerne() != null ? //
		 * v.getPointdeVenteConcerne().getPtv_id() : null);
		 * pdto.setNomPointDeVente( v.getPointdeVenteConcerne() != null ?
		 * v.getPointdeVenteConcerne().getLibelle() : "Inconnu");
		 * 
		 * pdto.setQuotaIntrantAMettreEnplace(v.getQuota()); //
		 * pdto.setNomGerant(v.getPointdeVenteConcerne() != null && //
		 * v.getPointdeVenteConcerne().getGerant() != null // ?
		 * v.getPointdeVenteConcerne().getGerant().getPrenom() + " " // +
		 * v.getPointdeVenteConcerne().getGerant().getNom() // : "Inconnu");
		 * pdto.setLibelleIntrantAMettreEnplace( v.getProduitAmettreEnPlace() !=
		 * null ? v.getProduitAmettreEnPlace().getLibelle() : "Inconnu");
		 * pdto.setIdIntrantAMettreEnplace( v.getProduitAmettreEnPlace() != null
		 * ? v.getProduitAmettreEnPlace().getRefIntrantId() : null);
		 * pdto.setIdProgramme(v.getProgrammeConcerne().getPgca_idprog());
		 * pdto.setNomProgramme(v.getProgrammeConcerne().getLibelle());
		 * 
		 * // lecture des mise en place deja effectuee
		 * List<MiseEnPlaceEffectuerDTO> listMEPeffectuee =
		 * loadAllMiseEnPlaceEffectueeByidMAP(v.getId_MEPaAF());
		 * 
		 * double totalCumul = 0;
		 * 
		 * for (MiseEnPlaceEffectuerDTO ll : listMEPeffectuee) totalCumul +=
		 * ll.getQuantiteAmettreEnplace();
		 * 
		 * pdto.setCumulIntrantAMettreEnplace(totalCumul);
		 * pdto.setReliquatIntrantAMettreEnplace(v.getQuota() - totalCumul);
		 * 
		 * ventes.add(pdto); } return ventes;
		 */
	}

	@Override
	@SuppressWarnings("unchecked")
	public int loadAllMiseEnPlaceEncours() throws EntityDBDAOException {
		List<MiseEnPlaceAEffectuer> lp = modelDAO.loadAllMiseEnPlaceEncours();

		if (lp != null)
			return lp.size();

		return 0;
		/*
		 * List<MiseEnplaceDTOPointDeVente> ventes = new
		 * ArrayList<MiseEnplaceDTOPointDeVente>();
		 * 
		 * for (MiseEnPlaceAEffectuer v : lp) { MiseEnplaceDTOPointDeVente pdto
		 * = new MiseEnplaceDTOPointDeVente();
		 * 
		 * pdto.setIdMiseEnPlace(v.getId_MEPaAF());
		 * pdto.setIdSlectedMiseEnplace(v.getId_MEPaAF()); //
		 * pdto.setIdPointDeVente( // v.getPointdeVenteConcerne() != null ? //
		 * v.getPointdeVenteConcerne().getPtv_id() : null);
		 * pdto.setNomPointDeVente( v.getPointdeVenteConcerne() != null ?
		 * v.getPointdeVenteConcerne().getLibelle() : "Inconnu");
		 * 
		 * pdto.setQuotaIntrantAMettreEnplace(v.getQuota()); //
		 * pdto.setNomGerant(v.getPointdeVenteConcerne() != null && //
		 * v.getPointdeVenteConcerne().getGerant() != null // ?
		 * v.getPointdeVenteConcerne().getGerant().getPrenom() + " " // +
		 * v.getPointdeVenteConcerne().getGerant().getNom() // : "Inconnu");
		 * pdto.setLibelleIntrantAMettreEnplace( v.getProduitAmettreEnPlace() !=
		 * null ? v.getProduitAmettreEnPlace().getLibelle() : "Inconnu");
		 * pdto.setIdIntrantAMettreEnplace( v.getProduitAmettreEnPlace() != null
		 * ? v.getProduitAmettreEnPlace().getRefIntrantId() : null);
		 * pdto.setIdProgramme(v.getProgrammeConcerne().getPgca_idprog());
		 * pdto.setNomProgramme(v.getProgrammeConcerne().getLibelle());
		 * 
		 * // lecture des mise en place deja effectuee
		 * List<MiseEnPlaceEffectuerDTO> listMEPeffectuee =
		 * loadAllMiseEnPlaceEffectueeByidMAP(v.getId_MEPaAF());
		 * 
		 * double totalCumul = 0;
		 * 
		 * for (MiseEnPlaceEffectuerDTO ll : listMEPeffectuee) totalCumul +=
		 * ll.getQuantiteAmettreEnplace();
		 * 
		 * pdto.setCumulIntrantAMettreEnplace(totalCumul);
		 * pdto.setReliquatIntrantAMettreEnplace(v.getQuota() - totalCumul);
		 * 
		 * ventes.add(pdto); } return ventes;
		 */
	}

	@Override
	public List<MiseEnplaceDTOPointDeVente> getAllMiseEnPlaceEncours() throws EntityDBDAOException {
		@SuppressWarnings("unchecked")
		List<MiseEnPlaceAEffectuer> lp = modelDAO.loadAllMiseEnPlaceEncours();

		List<MiseEnplaceDTOPointDeVente> ventes = new ArrayList<MiseEnplaceDTOPointDeVente>();

		int max = 0;

		int firstElement = 1;
		for (MiseEnPlaceAEffectuer v : lp) {
			MiseEnplaceDTOPointDeVente pdto = new MiseEnplaceDTOPointDeVente();
			pdto.setIdMiseEnPlace(v.getId_MEPaAF());
			pdto.setIdSlectedMiseEnplace(v.getId_MEPaAF());
			// pdto.setIdPointDeVente(
			// v.getPointdeVenteConcerne() != null ?
			// v.getPointdeVenteConcerne().getPtv_id() : null);
			pdto.setNomPointDeVente(
					v.getPointdeVenteConcerne() != null ? v.getPointdeVenteConcerne().getLibelle() : "Inconnu");
			
			Double q = BigDecimal.valueOf(v.getQuota())
				    .setScale(2, RoundingMode.HALF_UP)
				    .doubleValue();
			
			pdto.setQuotaIntrantAMettreEnplace(q);
			// pdto.setNomGerant(v.getPointdeVenteConcerne() != null &&
			// v.getPointdeVenteConcerne().getGerant() != null
			// ? v.getPointdeVenteConcerne().getGerant().getPrenom() + " "
			// + v.getPointdeVenteConcerne().getGerant().getNom()
			// : "Inconnu");
			pdto.setLibelleIntrantAMettreEnplace(
					v.getProduitAmettreEnPlace() != null ? v.getProduitAmettreEnPlace().getLibelle() : "Inconnu");
			pdto.setIdIntrantAMettreEnplace(
					v.getProduitAmettreEnPlace() != null ? v.getProduitAmettreEnPlace().getRefIntrantId() : null);
			pdto.setIdProgramme(v.getProgrammeConcerne().getPgca_idprog());
			pdto.setNomProgramme(v.getProgrammeConcerne().getLibelle());
			pdto.setPictoImages(v.getProduitAmettreEnPlace().getLibelle().replaceAll(" ", "").toLowerCase());

			pdto.setIdDepartement(v.getPointdeVenteConcerne().getDepartement().getId());
			pdto.setDepartement(v.getPointdeVenteConcerne().getDepartement().getLibelle());
			pdto.setRegion(v.getPointdeVenteConcerne().getDepartement().getRegion().getLibelle());
			pdto.setCommuneCertifie(v.getPointdeVenteConcerne().getLibelle());

			// lecture des mise en place deja effectuee
			List<MiseEnPlaceEffectuerDTO> listMEPeffectuee = loadAllMiseEnPlaceEffectueeByidMAP(v.getId_MEPaAF());
			double totalCumul = 0;
			for (MiseEnPlaceEffectuerDTO ll : listMEPeffectuee)
				totalCumul += ll.getQuantiteAmettreEnplace();

			if ((v.getQuota() / 2) > totalCumul) {
				pdto.setStatusText("faible");
				pdto.setStatusIcon("slideWarn");
			} else {
				pdto.setStatusText("normal");
				pdto.setStatusIcon("slideValid");
			}
			
			Double total = BigDecimal.valueOf(totalCumul)
				    .setScale(2, RoundingMode.HALF_UP)
				    .doubleValue();
			
			
			pdto.setCumulIntrantAMettreEnplace(total);
			pdto.setReliquatIntrantAMettreEnplace(v.getQuota() - total);

			// utils pour activer la class slider
			if (firstElement == 1)
				pdto.setActiveCss("active");
			firstElement++;

			/*
			 * infos supplémentaires slider dashboard manager et Agent Saisie et
			 * AS (Ventes + Stock restant de l'intant chez les fournsseurs + et
			 * chez stock local sedad)
			 */
			// on ajoute les intrants dispo dans founisseurs + magasins
			// stockSedabIntrnantEncours stockFournisseurIntrnantEncours
			pdto.setStockSedabIntrnantEncours(
					stockService.loadTotalStockRestantByIdIntrantLocal(v.getProduitAmettreEnPlace().getRefIntrantId()));

			pdto.setStockFournisseurIntrnantEncours(stockService
					.loadTotalStockRestantByIdIntrantFournisseur(v.getProduitAmettreEnPlace().getRefIntrantId()));

			ventes.add(pdto);

			/*** on ne lit que les 50 derniers MEP pour les slider **/
			if (max == 50)
				break;

			max++;
		}
		return ventes;
	}

	/*****
	 * Lecture des mises en place effectue pour un plan de mise en place e
	 * eeffectue
	 **/

	@Override
	public List<MiseEnPlaceEffectuerDTO> loadAllMiseEnPlaceEffectueeByidMAP(Long idMiseEnPlace)
			throws EntityDBDAOException {
		@SuppressWarnings("unchecked")

		List<MiseEnPlaceEffectuer> lp = (List<MiseEnPlaceEffectuer>) modelDAO.genericSqlClassLoaderById(
				MiseEnPlaceEffectuer.class, "pgca_MiseEnPlaceEffectuee", "id_MEPaAF", idMiseEnPlace);

		if (lp == null)
			return null;

		List<MiseEnPlaceEffectuerDTO> totalMiseEnplace = new ArrayList<MiseEnPlaceEffectuerDTO>();

		for (MiseEnPlaceEffectuer v : lp) {
			MiseEnPlaceEffectuerDTO pdto = new MiseEnPlaceEffectuerDTO();

			pdto.setQuotaDeReference("Q" + v.getMiseEnplaceConcerne().getId_MEPaAF());
			pdto.setCumulDeReference(
					v.getMiseEnplaceConcerne().getQuota() - v.getMiseEnplaceConcerne().getReliquat() + "");
			pdto.setReliquatDeReference(v.getMiseEnplaceConcerne().getReliquat() + "");
			pdto.setQuantitequotaDeReference(v.getMiseEnplaceConcerne().getQuota() + "");

			pdto.setQuantiteAmettreEnplace(v.getQuantiteAmettreEnplace());
			pdto.setBl(v != null ? "BL00" + v.getBl().getBl_id() : "Inconnu");
			pdto.setCamion(v.getCamion() != null ? v.getCamion().getNumeroCamion() : "Inconnu");
			pdto.setChauffeur(v.getChauffeur() != null
					? v.getChauffeur().getChauffeur().getPrenom() + " " + v.getChauffeur().getChauffeur().getNom()
					: "Inconnu");
			pdto.setTransporteur(v.getTransporteur() != null ? v.getTransporteur().getLibelle() : "");
			pdto.setDateMiseEnplaceEffective(v.getDateMiseEnplaceEffective());
			pdto.setMiseEnplaceConcerneId(v.getMiseEnplaceConcerne().getId_MEPaAF());
			pdto.setMiseEnplaceConcerneLibelle("Mise en place de " + v.getMiseEnplaceConcerne().getQuota()
					+ " Tonne(s)  de " + v.getMiseEnplaceConcerne().getProduitAmettreEnPlace().getLibelle()
					+ "au point de vente de " + v.getMiseEnplaceConcerne().getPointdeVenteConcerne().getLibelle());
			pdto.setLibelleIntrant(v.getMiseEnplaceConcerne().getProduitAmettreEnPlace().getLibelle());
			pdto.setQuantiteAmettreEnplace(v.getQuantiteAmettreEnplace());
			pdto.setLvManuel(v.getLvManuel());
			pdto.setBlManuel(v.getBlManuel());
			pdto.setPointdeVenteLibelle(v.getPointdeVenteLibelle());
			pdto.setPointdeVenteLibelleDepartement(
					v.getMiseEnplaceConcerne().getPointdeVenteConcerne().getDepartement().getLibelle());

			if (v.getDateMiseEnplaceEffective() != null) {
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy"); // HH:mm:ss
				pdto.setDateMiseEnplaceEffectiveStr((df.format(v.getDateMiseEnplaceEffective())));
			}

			totalMiseEnplace.add(pdto);
		}
		return totalMiseEnplace;
	}

	/***** lecture de toutes mes MEP d'un intrant donnés */

	@Override
	public List<MiseEnPlaceEffectuerDTO> loadAllMiseEnPlaceEffecByTypeIntrant(Long idIntrant)
			throws EntityDBDAOException {
		@SuppressWarnings("unchecked")

		List<MiseEnPlaceEffectuer> lp = (List<MiseEnPlaceEffectuer>) modelDAO
				.loadAllMiseEnPlaceEffecByTypeIntrant(idIntrant);

		if (lp == null)
			return null;

		List<MiseEnPlaceEffectuerDTO> totalMiseEnplace = new ArrayList<MiseEnPlaceEffectuerDTO>();

		for (MiseEnPlaceEffectuer v : lp) {
			MiseEnPlaceEffectuerDTO pdto = new MiseEnPlaceEffectuerDTO();

			pdto.setQuotaDeReference("Q" + v.getMiseEnplaceConcerne().getId_MEPaAF());
			pdto.setQuantiteAmettreEnplace(v.getQuantiteAmettreEnplace());
			pdto.setBl(v != null ? "BL00" + v.getBl().getBl_id() : "Inconnu");
			pdto.setCamion(v.getCamion() != null ? v.getCamion().getNumeroCamion() : "Inconnu");
			pdto.setChauffeur(v.getChauffeur() != null
					? v.getChauffeur().getChauffeur().getPrenom() + " " + v.getChauffeur().getChauffeur().getNom()
					: "Inconnu");
			pdto.setTransporteur(v.getTransporteur() != null ? v.getTransporteur().getLibelle() : "");
			pdto.setDateMiseEnplaceEffective(v.getDateMiseEnplaceEffective());
			pdto.setMiseEnplaceConcerneId(v.getMiseEnplaceConcerne().getId_MEPaAF());
			pdto.setMiseEnplaceConcerneLibelle("Mise en place de " + v.getMiseEnplaceConcerne().getQuota()
					+ " Tonne(s)  de " + v.getMiseEnplaceConcerne().getProduitAmettreEnPlace().getLibelle()
					+ "au point de vente de " + v.getMiseEnplaceConcerne().getPointdeVenteConcerne().getLibelle());
			pdto.setQuantiteAmettreEnplace(v.getQuantiteAmettreEnplace());
			pdto.setLvManuel(v.getLvManuel());
			pdto.setBlManuel(v.getBlManuel());
			pdto.setPointdeVenteLibelle(v.getPointdeVenteLibelle());
			pdto.setPointdeVenteLibelleDepartement(
					v.getMiseEnplaceConcerne().getPointdeVenteConcerne().getDepartement().getLibelle());

			pdto.setIconTypeIntrant(
					v.getMiseEnplaceConcerne().getProduitAmettreEnPlace().getTypeIntrantId().getPictoIntrant());
			pdto.setNombrePontdeVente(pdto.getNombrePontdeVente() + 1);
			totalMiseEnplace.add(pdto);

			if (v.getDateMiseEnplaceEffective() != null) {
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy"); // HH:mm:ss
				pdto.setDateMiseEnplaceEffectiveStr(df.format(v.getDateMiseEnplaceEffective()));
			}
		}
		return totalMiseEnplace;
	}

	/****
	 * Load mise en place by Aggregation
	 * 
	 * Apres l'agreagation des intrants , on recupere la liste des quota pour un
	 * intrant donné
	 * 
	 * PAR EXEMPLE QUOTA UREE 30 -> KOLDA 70 -> TAMBA 100 -> KALOACK
	 * 
	 * l'agregatiin des UREE est de 200 (la somme des trois de meme )
	 * 
	 * Ce service recupere a partir de l'id du l'intrant les quotas qui sont
	 * liee (KOLDA + TAMBA + KAOLCAK)
	 ****/
	@Override
	public List<MiseEnplaceDTOCommune> loadListQuotabyIdIntrant(Long idIntrant) throws EntityDBDAOException {
		@SuppressWarnings("unchecked")

		List<MiseEnPlaceAEffectuer> lp = commonService.loadListQuotabyIdIntrant(idIntrant);
		if (lp == null)
			return null;

		List<MiseEnplaceDTOCommune> repartionQuotaParPV = new ArrayList<MiseEnplaceDTOCommune>();

		for (MiseEnPlaceAEffectuer v : lp) {
			MiseEnplaceDTOCommune pdto = new MiseEnplaceDTOCommune();

			pdto.setIdMiseEnplaceAeffectuer(v.getId_MEPaAF());
			pdto.setTotalQuoto(v.getQuota());
			pdto.setTotalReliquat(v.getReliquat());
			pdto.setTotalCumul(v.getMiseEnplace()); // ?
			pdto.setPointdeVente(v.getPointdeVenteConcerne().getLibelle());

			pdto.setPointdeVenteDepartement(v.getPointdeVenteConcerne().getDepartement().getLibelle());
			pdto.setPointdeVenteDepartementId(v.getPointdeVenteConcerne().getDepartement().getId());
			pdto.setPointdeVenteCommune(v.getPointdeVenteConcerne().getLibelle());
			pdto.setPointdeVenteCommuneId(v.getPointdeVenteConcerne().getId());
			pdto.setPointdeVenteAdresse(v.getPointdeVenteConcerne().getLibelle());

			// if(v.getPointdeVenteConcerne().getGerant() != null)
			// {
			// pdto.setPointdeVenteGerant(v.getPointdeVenteConcerne().getGerant().getPrenom()
			// + " " + (v.getPointdeVenteConcerne().getGerant().getNom()));
			//
			// if (v.getPointdeVenteConcerne().getGerant().getContact() != null)
			// pdto.setPointdeVenteGerantTel(v.getPointdeVenteConcerne().getGerant().getContact()
			// .getMobile());
			// }

			repartionQuotaParPV.add(pdto);
		}
		return repartionQuotaParPV;
	}

	@Override
	public List<MiseEnplaceDTOCommune> loadListQuotabyIdIntrantAndDepartement(Long idIntrant, Long iddept)
			throws EntityDBDAOException {
		@SuppressWarnings("unchecked")

		List<MiseEnPlaceAEffectuer> lp = commonService.loadListQuotabyIdIntrantAndDepartement(idIntrant, iddept);
		if (lp == null)
			return null;

		List<MiseEnplaceDTOCommune> repartionQuotaParPV = new ArrayList<MiseEnplaceDTOCommune>();

		for (MiseEnPlaceAEffectuer v : lp) {
			MiseEnplaceDTOCommune pdto = new MiseEnplaceDTOCommune();

			pdto.setIdMiseEnplaceAeffectuer(v.getId_MEPaAF());
			pdto.setTotalQuoto(v.getQuota());
			pdto.setTotalReliquat(v.getReliquat());
			pdto.setTotalCumul(v.getMiseEnplace()); // ?
			pdto.setPointdeVente(v.getPointdeVenteConcerne().getLibelle());

			pdto.setPointdeVenteDepartement(v.getPointdeVenteConcerne().getDepartement().getLibelle());
			pdto.setPointdeVenteDepartementId(v.getPointdeVenteConcerne().getDepartement().getId());
			pdto.setPointdeVenteCommune(v.getPointdeVenteConcerne().getLibelle());
			pdto.setPointdeVenteCommuneId(v.getPointdeVenteConcerne().getId());
			pdto.setPointdeVenteAdresse(v.getPointdeVenteConcerne().getLibelle());

			repartionQuotaParPV.add(pdto);
		}
		return repartionQuotaParPV;
	}

	/**
	 * Transfert de stock d'un forunisseur vers magasin sedab
	 */
	@Override
	public boolean transfererStock(IntrantDTO selectedIntrantTotransfer, Long idMagasin, Double quantiteAtranferer)
			throws EntityDBDAOException {

		Intrant intrant = (Intrant) modelDAO.getEntityDBById(selectedIntrantTotransfer.getIdProduit(), Intrant.class);
		if (intrant == null)
			return false;
		PointDeVente magasin = (PointDeVente) modelDAO.getEntityDBById(idMagasin, PointDeVente.class);
		if (magasin == null)
			return false;

		try {

			BonDeLivraison blToSave = new BonDeLivraison();
			Transporteur transporteur = modelDAO
					.loadTransporteurByName(selectedIntrantTotransfer.getTransportTotransfert());
			Camion camionumero = modelDAO.loadCamionByName(selectedIntrantTotransfer.getCamionTotransfer());
			Chauffeur chauffeur = modelDAO.loadChauffeurByName(selectedIntrantTotransfer.getChauufeurTotransfer());
			ProgrammeAgricol programme = (ProgrammeAgricol) modelDAO
					.getEntityDBById(intrant.getProgramme().getPgca_idprog(), ProgrammeAgricol.class);

			if (transporteur == null) // créé
			{
				transporteur = new Transporteur();
				transporteur.setLibelle(selectedIntrantTotransfer.getTransportTotransfert());
				modelDAO.save(transporteur);
			}

			chauffeur = createChauffeurifNotExist(chauffeur, selectedIntrantTotransfer, transporteur);
			camionumero = createCamionNotExist(camionumero, chauffeur, selectedIntrantTotransfer, transporteur);

			// blToSave.setProgramme(programme);
			blToSave.setChauffeurId(chauffeur.getChauffeur());
			blToSave.setTransporteurId(transporteur);
			blToSave.setNumeroCamionId(camionumero);
			blToSave.setIdStockReceptionnaire(magasin.getStock().getStock_id());

			blToSave.setProvenanceProduit(
					"De " + intrant.getFournisseur().getLibelle() + " vers le " + magasin.getLibelle());
			// blToSave.setIdStockExpedition(bondeLivraisonDTO.getIdStockSortant());
			blToSave.setIdAuteurBl(SessionManagedBean.getSessionLongValuesDataByTag("connectedUserPersonneid"));
			blToSave.setNumeroBLManuel(selectedIntrantTotransfer.getBlManuel());
			blToSave.setNumeroLVManuel(selectedIntrantTotransfer.getLvManuel());
			blToSave.setBlStatut(0);
			blToSave.setDateEdition(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
			blToSave.setProgramme(programme);

			// déduction sur stock restant auprès du fournisseur
			intrant.setQuantite(intrant.getQuantite() - quantiteAtranferer);
			modelDAO.save(intrant);
			modelDAO.save(blToSave);

			BonDeLivraisonProduit blp = new BonDeLivraisonProduit();

			blp.setIdProduit(intrant.getProduit_id());
			blp.setLibelle(intrant.getIntrant().getLibelle());
			blp.setQuantite(quantiteAtranferer);
			blp.setLibelleProgramme(intrant.getProgramme().getLibelle());
			blp.setLibelleCampagne(intrant.getProgramme().getCampagne().getLibelle());
			blp.setLibelleStockSortant("Transfert - " + selectedIntrantTotransfer.getLibelleFournisseur());
			blp.setBlAssocie(blToSave);
			modelDAO.save(blp);
			return true;

		} catch (Exception e) {
			Log.error("Une erreur techniqu  est surveneue lors du transfert" + e.getMessage());
			return false;
		}
	}

	/**
	 * Transfert de stock d'un Point de Vente vers magasin sedab
	 */
	@Override
	public boolean transfererStockFromPointDeVenteToMagasin(IntrantDTO selectedIntrantTotransfer, Long idMagasin,
			Double quantiteAtranferer) throws EntityDBDAOException {

		Intrant intrant = (Intrant) modelDAO.getEntityDBById(selectedIntrantTotransfer.getIdProduit(), Intrant.class);
		if (intrant == null)
			return false;

		String stockLibelle;
		Long idStock;

		PointDeVente magasin = (PointDeVente) modelDAO.getEntityDBById(idMagasin, PointDeVente.class);
		if (magasin == null) {
			Commune pv = (Commune) modelDAO.getEntityDBById(selectedIntrantTotransfer.getIdPoindCollecte(),
					Commune.class);
			if (pv == null)
				return false;

			Stock s = modelDAO.getStockFromIdPointDeVente(pv.getId());

			if (s == null) {
				s = new Stock();
				s.setCommunePointDevente(pv);
				s.setLibelle("Stock Point de vente " + pv.getLibelle());
				modelDAO.save(s);
			}

			stockLibelle = pv.getLibelle();
			idStock = s.getStock_id();

		} else {
			stockLibelle = magasin.getLibelle();
			idStock = magasin.getStock().getId();
		}

		try {

			BonDeLivraison blToSave = new BonDeLivraison();
			Transporteur transporteur = modelDAO
					.loadTransporteurByName(selectedIntrantTotransfer.getTransportTotransfert());
			Camion camionumero = modelDAO.loadCamionByName(selectedIntrantTotransfer.getCamionTotransfer());
			Chauffeur chauffeur = modelDAO.loadChauffeurByName(selectedIntrantTotransfer.getChauufeurTotransfer());
			ProgrammeAgricol programme = (ProgrammeAgricol) modelDAO
					.getEntityDBById(intrant.getProgramme().getPgca_idprog(), ProgrammeAgricol.class);

			if (transporteur == null) // créé
			{
				transporteur = new Transporteur();
				transporteur.setLibelle(selectedIntrantTotransfer.getTransportTotransfert());
				modelDAO.save(transporteur);
			}

			chauffeur = createChauffeurifNotExist(chauffeur, selectedIntrantTotransfer, transporteur);
			camionumero = createCamionNotExist(camionumero, chauffeur, selectedIntrantTotransfer, transporteur);

			blToSave.setProgramme(programme);
			blToSave.setChauffeurId(chauffeur.getChauffeur());
			blToSave.setTransporteurId(transporteur);
			blToSave.setNumeroCamionId(camionumero);
			blToSave.setIdStockReceptionnaire(idStock);

			blToSave.setProvenanceProduit("De " + intrant.getStockLibelle() + " vers le " + stockLibelle);
			// blToSave.setIdStockExpedition(bondeLivraisonDTO.getIdStockSortant());
			blToSave.setIdAuteurBl(SessionManagedBean.getSessionLongValuesDataByTag("connectedUserPersonneid"));
			blToSave.setIdStockExpedition(SessionManagedBean.getSessionLongValuesDataByTag("connectedUserStockid"));
			blToSave.setNumeroBLManuel(selectedIntrantTotransfer.getBlManuel());
			blToSave.setNumeroLVManuel(selectedIntrantTotransfer.getLvManuel());
			blToSave.setBlStatut(0);
			blToSave.setDateEdition(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
			blToSave.setProgramme(programme);
			blToSave.setDestinationProduit(stockLibelle);

			// déduction sur stock restant auprès du fournisseur
			intrant.setQuantite(intrant.getQuantite() - quantiteAtranferer);
			modelDAO.save(intrant);
			modelDAO.save(blToSave);

			BonDeLivraisonProduit blp = new BonDeLivraisonProduit();

			blp.setIdProduit(intrant.getProduit_id());
			blp.setLibelle(intrant.getIntrant().getLibelle());
			blp.setQuantite(quantiteAtranferer);
			blp.setLibelleProgramme(intrant.getProgramme().getLibelle());
			blp.setLibelleCampagne(intrant.getProgramme().getCampagne().getLibelle());
			blp.setLibelleStockSortant("Transfert - " + selectedIntrantTotransfer.getLibelleFournisseur());
			blp.setBlAssocie(blToSave);
			modelDAO.save(blp);
			return true;

		} catch (Exception e) {
			Log.error("Une erreur techniqu  est surveneue lors du transfert" + e.getMessage());
			return false;
		}
	}

	/**
	 * SI c un deplacemnt sur un stock mise en place et non créé en local , il
	 * faut passer par le stockResiduel du point de vente et faire la deduction
	 */
	@Override
	public boolean transfererStockFromPointDeVenteToMagasinModeStock(IntrantDTO selectedIntrantTotransfer,
			Long idMagasin, Double quantiteAtranferer) throws EntityDBDAOException {

		StockResiduelPointDeVente sr = (StockResiduelPointDeVente) modelDAO
				.getEntityDBById(selectedIntrantTotransfer.getIdStockResiduel(), StockResiduelPointDeVente.class);

		if (sr == null)
			return false;

		String stockLibelle;
		Long idStock;

		PointDeVente magasin = (PointDeVente) modelDAO.getEntityDBById(idMagasin, PointDeVente.class);
		if (magasin == null) {
			Commune pv = (Commune) modelDAO.getEntityDBById(selectedIntrantTotransfer.getIdPoindCollecte(),
					Commune.class);
			if (pv == null)
				return false;

			Stock s = modelDAO.getStockFromIdPointDeVente(pv.getId());

			if (s == null) {
				s = new Stock();
				s.setCommunePointDevente(pv);
				s.setLibelle("Stock Point de vente " + pv.getLibelle());
				modelDAO.save(s);
			}

			stockLibelle = pv.getLibelle();
			idStock = s.getStock_id();

		} else {
			stockLibelle = magasin.getLibelle();
			idStock = magasin.getStock().getId();
		}

		try {

			BonDeLivraison blToSave = new BonDeLivraison();
			Transporteur transporteur = modelDAO
					.loadTransporteurByName(selectedIntrantTotransfer.getTransportTotransfert());
			Camion camionumero = modelDAO.loadCamionByName(selectedIntrantTotransfer.getCamionTotransfer());
			Chauffeur chauffeur = modelDAO.loadChauffeurByName(selectedIntrantTotransfer.getChauufeurTotransfer());
			ProgrammeAgricol programme = (ProgrammeAgricol) modelDAO
					.getEntityDBById(sr.getIntrant().getProgramme().getPgca_idprog(), ProgrammeAgricol.class);

			if (transporteur == null) // créé
			{
				transporteur = new Transporteur();
				transporteur.setLibelle(selectedIntrantTotransfer.getTransportTotransfert());
				modelDAO.save(transporteur);
			}

			chauffeur = createChauffeurifNotExist(chauffeur, selectedIntrantTotransfer, transporteur);
			camionumero = createCamionNotExist(camionumero, chauffeur, selectedIntrantTotransfer, transporteur);

			// blToSave.setProgramme(programme);
			blToSave.setChauffeurId(chauffeur.getChauffeur());
			blToSave.setTransporteurId(transporteur);
			blToSave.setNumeroCamionId(camionumero);
			blToSave.setIdStockReceptionnaire(idStock);

			blToSave.setProvenanceProduit("De Point de vente " + sr.getPointdeVente() != null
					? sr.getPointdeVente().getLibelle() : "" + " vers le " + stockLibelle);
			// blToSave.setIdStockExpedition(bondeLivraisonDTO.getIdStockSortant());
			blToSave.setIdStockExpedition(SessionManagedBean.getSessionLongValuesDataByTag("connectedUserStockid"));
			blToSave.setIdAuteurBl(SessionManagedBean.getSessionLongValuesDataByTag("connectedUserPersonneid"));
			blToSave.setIdStockExpedition(SessionManagedBean.getSessionLongValuesDataByTag("connectedUserStockid"));
			blToSave.setNumeroBLManuel(selectedIntrantTotransfer.getBlManuel());
			blToSave.setNumeroLVManuel(selectedIntrantTotransfer.getLvManuel());
			blToSave.setDestinationProduit(stockLibelle);
			blToSave.setBlStatut(0);
			blToSave.setDateEdition(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
			blToSave.setProgramme(programme);
			blToSave.setDestinationProduit(stockLibelle);

			// déduction sur stock restant auprès du fournisseur
			sr.setTotalStockActuel(sr.getTotalStockActuel() - quantiteAtranferer);
			modelDAO.save(blToSave);
			modelDAO.save(sr);

			BonDeLivraisonProduit blp = new BonDeLivraisonProduit();
			blp.setIdProduit(sr.getIntrant().getProduit_id());
			blp.setLibelle(sr.getIntrant().getLibelle());
			blp.setQuantite(quantiteAtranferer);
			blp.setLibelleProgramme(sr.getIntrant().getProgramme().getLibelle());
			blp.setLibelleCampagne(sr.getIntrant().getProgramme().getCampagne().getLibelle());
			blp.setLibelleStockSortant("Point de Vente - " + sr.getPointdeVente().getLibelle());
			blp.setBlAssocie(blToSave);
			modelDAO.save(blp);
			return true;

		} catch (Exception e) {
			Log.error("Une erreur techniqu  est surveneue lors du transfert" + e.getMessage());
			return false;
		}
	}

	/****************
	 * TRANSFERT DE STOCK ENTRE MAGASIN : Lors du transfert un BL est créé et
	 * transmis au gérant du magasin (Mail & affichage sur les BL)
	 * 
	 * @return
	 * @throws EntityDBDAOException
	 * @throws ProgrammeException
	 **********/

	@Override
	public boolean transfererStockEntreMagasin(IntrantDTO selectedIntrantTotransfer, Long idMagasinSource,
			Long idMagasinCible, Double quantiteAtranferer) throws EntityDBDAOException {

		Intrant intrant = (Intrant) modelDAO.getEntityDBById(selectedIntrantTotransfer.getIdProduit(), Intrant.class);
		if (intrant == null)
			return false;
		PointDeVente magasinDestinataire = (PointDeVente) modelDAO.getEntityDBById(idMagasinCible, PointDeVente.class);
		if (magasinDestinataire == null)
			return false;

		PointDeVente magasinDorigine = (PointDeVente) modelDAO.getEntityDBById(idMagasinSource, PointDeVente.class);
		if (magasinDorigine == null)
			return false;

		try {

			BonDeLivraison blToSave = new BonDeLivraison();
			Transporteur transporteur = modelDAO
					.loadTransporteurByName(selectedIntrantTotransfer.getTransportTotransfert());
			Camion camionumero = modelDAO.loadCamionByName(selectedIntrantTotransfer.getCamionTotransfer());
			Chauffeur chauffeur = modelDAO.loadChauffeurByName(selectedIntrantTotransfer.getChauufeurTotransfer());
			ProgrammeAgricol programme = (ProgrammeAgricol) modelDAO
					.getEntityDBById(intrant.getProgramme().getPgca_idprog(), ProgrammeAgricol.class);

			if (transporteur == null) // créé
			{
				transporteur = new Transporteur();
				transporteur.setLibelle(selectedIntrantTotransfer.getTransportTotransfert());
				modelDAO.save(transporteur);
			}

			if (chauffeur == null)
				chauffeur = createChauffeurifNotExist(chauffeur, selectedIntrantTotransfer, transporteur);

			if (camionumero == null)
				camionumero = createCamionNotExist(camionumero, chauffeur, selectedIntrantTotransfer, transporteur);

			// blToSave.setProgramme(programme);
			blToSave.setChauffeurId(chauffeur.getChauffeur());
			blToSave.setTransporteurId(transporteur);
			blToSave.setNumeroCamionId(camionumero);

			blToSave.setIdStockReceptionnaire(magasinDestinataire.getStock().getStock_id());
			blToSave.setDestinationProduit(magasinDestinataire.getLibelle());

			blToSave.setIdStockExpedition(magasinDorigine.getStock().getStock_id());
			blToSave.setProvenanceProduit(magasinDorigine.getLibelle());

			blToSave.setIdAuteurBl(SessionManagedBean.getSessionLongValuesDataByTag("connectedUserPersonneid"));
			blToSave.setNumeroBLManuel(selectedIntrantTotransfer.getBlManuel());
			blToSave.setNumeroLVManuel(selectedIntrantTotransfer.getLvManuel());
			blToSave.setBlStatut(0);
			blToSave.setDateEdition(new Date());
			blToSave.setProgramme(programme);

			// déduction sur stock restant auprès du fournisseur
			intrant.setQuantite(intrant.getQuantite() - quantiteAtranferer);
			intrant.setLibelle(intrant.getIntrant().getLibelle());
			modelDAO.save(intrant);
			modelDAO.save(blToSave);

			BonDeLivraisonProduit blp = new BonDeLivraisonProduit();

			blp.setIdProduit(intrant.getProduit_id());
			blp.setLibelle(intrant.getIntrant().getLibelle());
			blp.setQuantite(quantiteAtranferer);
			blp.setLibelleProgramme(intrant.getProgramme().getLibelle());
			blp.setLibelleCampagne(intrant.getProgramme().getCampagne().getLibelle());
			blp.setLibelleStockSortant(selectedIntrantTotransfer.getLibelleFournisseur());
			blp.setBlAssocie(blToSave);
			modelDAO.save(blp);
			return true;

		} catch (Exception e) {
			Log.error("Une erreur techniqu  est surveneue lors du transfert" + e.getMessage());
			return false;
		}
	}

	/******* FIN STOCK ENTRE MAGASIN ********/
	private Chauffeur createChauffeurifNotExist(Chauffeur chauffeur, IntrantDTO selectedIntrantTotransfer,
			Transporteur transporteur) throws EntityDBDAOException {
		if (chauffeur == null) // créé
		{
			chauffeur = new Chauffeur();
			Personne p = new Personne();
			String[] tmp = selectedIntrantTotransfer.getChauufeurTotransfer().trim().split(" ");
			p.setPrenom(tmp[0]);
			if (tmp.length > 1)
				p.setNom(tmp[1]);
			p.setCivilite('1');

			modelDAO.save(p);
			if (tmp.length > 1)
				chauffeur.setLibelle(tmp[0] + " " + tmp[1]);
			else
				chauffeur.setLibelle(tmp[0]);
			chauffeur.setTransporteur(transporteur);
			chauffeur.setChauffeur(p);
			modelDAO.save(chauffeur);

			return chauffeur;
		}
		return chauffeur;
	}

	private Camion createCamionNotExist(Camion camionumero, Chauffeur chauffeur, IntrantDTO selectedIntrantTotransfer,
			Transporteur transporteur) throws EntityDBDAOException {
		if (camionumero == null) // créé
		{
			camionumero = new Camion();

			camionumero.setChauffeur(chauffeur);
			camionumero.setTransporteur(transporteur);
			camionumero.setNumeroCamion(selectedIntrantTotransfer.getChauufeurTotransfer());
			modelDAO.save(camionumero);

			return camionumero;
		}
		return camionumero;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<MiseEnplaceDTOPointDeVente> loadAllMiseEnPlaceOfCampagne(Long idProgramme) throws EntityDBDAOException {
		//20/09/2020  : Lenteur Quota  : Load By Campagne 
		List<MiseEnPlaceAEffectuer> lp = (List<MiseEnPlaceAEffectuer>) modelDAO.genericClassLoader(MiseEnPlaceAEffectuer.class);
		
		//List<MiseEnPlaceAEffectuer> lp = (List<MiseEnPlaceAEffectuer>) modelDAO.loadMiseEnPlaceByProgramme(idProgramme);
		
		if (lp == null)	return null;
		
		List<MiseEnplaceDTOPointDeVente> ventes = new ArrayList<MiseEnplaceDTOPointDeVente>();
		for (MiseEnPlaceAEffectuer v : lp) {
			MiseEnplaceDTOPointDeVente pdto = new MiseEnplaceDTOPointDeVente();
			pdto.setIdMiseEnPlace(v.getId_MEPaAF());
			pdto.setIdSlectedMiseEnplace(v.getId_MEPaAF());
			pdto.setNomPointDeVente(
					v.getPointdeVenteConcerne() != null ? v.getPointdeVenteConcerne().getLibelle() : "Inconnu");
			pdto.setQuotaIntrantAMettreEnplace(v.getQuota());
			pdto.setLibelleIntrantAMettreEnplace(
					v.getProduitAmettreEnPlace() != null ? v.getProduitAmettreEnPlace().getLibelle() : "Inconnu");
			pdto.setIdIntrantAMettreEnplace(
					v.getProduitAmettreEnPlace() != null ? v.getProduitAmettreEnPlace().getRefIntrantId() : null);
			pdto.setIdProgramme(v.getProgrammeConcerne().getPgca_idprog());
			pdto.setNomProgramme(v.getProgrammeConcerne().getLibelle());
			pdto.setDepartementPointdeVente(v.getPointdeVenteConcerne().getDepartement().getLibelle());
			pdto.setCommuneCertifie(v.getPointdeVenteConcerne().getLibelle());
			// lecture des mise en place deja effectuee
			List<MiseEnPlaceEffectuerDTO> listMEPeffectuee = loadAllMiseEnPlaceEffectueeByidMAP(v.getId_MEPaAF());
			double totalCumul = 0;
			pdto.setDepartement(v.getPointdeVenteConcerne().getDepartement().getLibelle());
			pdto.setIdDepartement(v.getPointdeVenteConcerne().getDepartement().getId());
			pdto.setRegion(v.getPointdeVenteConcerne().getLibelle());
			pdto.setDateMEPSTR(v.getDateEdition() + "");
			for (MiseEnPlaceEffectuerDTO ll : listMEPeffectuee)
				totalCumul += ll.getQuantiteAmettreEnplace();
			pdto.setCumulIntrantAMettreEnplace(totalCumul);
			pdto.setReliquatIntrantAMettreEnplace(formatDoubleTwoDigi(v.getQuota() - totalCumul));
			ventes.add(pdto);
		}
		return ventes;
	}

	@Override
	public List<MiseEnplaceDTOPointDeVente> listeAggregationMiseenPlaceParIntrant() throws EntityDBDAOException {
		@SuppressWarnings("unchecked")
		List<MiseEnplaceAgregation> lp = (List<MiseEnplaceAgregation>) modelDAO.loadAgregationMiseEnPlace();
		if (lp == null)
			return null;
		List<MiseEnplaceDTOPointDeVente> ventes = new ArrayList<MiseEnplaceDTOPointDeVente>();
		for (MiseEnplaceAgregation v : lp) {
			MiseEnplaceDTOPointDeVente pdto = new MiseEnplaceDTOPointDeVente();
			pdto.setIdIntrantAMettreEnplace(v.getIdIntrant());
			pdto.setLibelleIntrantAMettreEnplace(v.getNomIntrant());
			pdto.setReliquatIntrantAMettreEnplace( formatDoubleTwoDigi(v.getTotalreliquat()));
			pdto.setQuotaIntrantAMettreEnplace(formatDoubleTwoDigi(v.getTotalQuota()));
			pdto.setNombrePontdeVente((v.getNombreDePointDeVente()));
			// pdto.setIconTypeIntrant(v.get)
			pdto.setTauxCouverture(v.getTauxCourverture());
			ventes.add(pdto);
		}
		return ventes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int executeMiseEnplaceById(MiseEnplaceDTOPointDeVente infosMEP, BonDeLivraisonDTO blDTO)
			throws EntityDBDAOException {
		Intrant fromFournisseur = null;
		try {
			MiseEnPlaceAEffectuer planConcernee = (MiseEnPlaceAEffectuer) modelDAO
					.getEntityDBById(infosMEP.getIdSlectedMiseEnplace(), MiseEnPlaceAEffectuer.class);
			MiseEnPlaceEffectuer miseEnPlaceAffectuer = new MiseEnPlaceEffectuer();
			if (planConcernee == null)
				return ConstantPGCA.INTRANT_ERROR;
			Transporteur transporteur = modelDAO.loadTransporteurByName(infosMEP.getTransporteur());
			Camion camionumero = modelDAO.loadCamionByName(infosMEP.getCamion());
			Chauffeur chauffeur = modelDAO.loadChauffeurByName(infosMEP.getChauffeur());

			if (transporteur == null) // créé
			{
				transporteur = new Transporteur();
				transporteur.setLibelle(infosMEP.getTransporteur().trim());
				modelDAO.save(transporteur);
				blDTO.setUpdatecache(true); // update cache ref
			}
			logger.info("---- MEP  TRANSPORTEUR : "  + transporteur);

			if (chauffeur == null) // créé
			{
				chauffeur = new Chauffeur();
				Personne p = new Personne();
				String[] tmp = infosMEP.getChauffeur().trim().split(" ");
				p.setPrenom(tmp[0]);
				if (tmp.length > 1)
					p.setNom(tmp[1]);
				p.setCivilite('1');
				modelDAO.save(p);
				logger.info("---- MEP  chaffeur : "  + p);

				if (tmp.length > 1)
					chauffeur.setLibelle(tmp[0] + " " + tmp[1] != null ? tmp[1] : "");
				else
					chauffeur.setLibelle(tmp[0]);
				chauffeur.setTransporteur(transporteur);
				chauffeur.setChauffeur(p);
				modelDAO.save(chauffeur);
				logger.info("---- MEP  chaffeur : "  + chauffeur);

				blDTO.setUpdatecache(true); // update cache ref
			}
			if (camionumero == null) // créé
			{
				camionumero = new Camion();
				camionumero.setChauffeur(chauffeur);
				camionumero.setTransporteur(transporteur);
				camionumero.setNumeroCamion(infosMEP.getCamion());
				modelDAO.save(camionumero);
				logger.info("---- MEP  chaffeur : "  + camionumero);
				blDTO.setUpdatecache(true); // update cache ref
			}
			Fournisseur fournisseur;
			fournisseur = (Fournisseur) modelDAO.getEntityDBById(infosMEP.getFournisseurId(), Fournisseur.class);
			boolean checkFournisseur = false;
			logger.info("---- MEP  chaffeur : "  + fournisseur);

			// source = pv ou maga
			if (infosMEP.getIdStockdeReference() != null) {
				if (infosMEP.getIdStockdeReference() == 0) {
					fromFournisseur = loadProduitbyIdAdnIdFournisseur(
							planConcernee.getProduitAmettreEnPlace().getRefIntrantId(), fournisseur.getId());
					miseEnPlaceAffectuer.setMagasinSource(fromFournisseur.getLibelle());
				} else {
					fromFournisseur = loadProduitbyIdAdnIdMagasin(
							planConcernee.getProduitAmettreEnPlace().getRefIntrantId(),
							infosMEP.getIdStockdeReference());
					miseEnPlaceAffectuer.setMagasinSource(infosMEP.getNomPointDeVente());
				}
				checkFournisseur = true;
			} else if (infosMEP.getIdCommuneResiduel() != null) 
			{
				checkFournisseur = false;
				StockResiduelPointDeVente resi = (StockResiduelPointDeVente) modelDAO
						.getEntityDBById(infosMEP.getIdCommuneResiduel(), StockResiduelPointDeVente.class);
				if (resi != null) {
					if (resi.getTotalStockActuel() < infosMEP.getQuantiteIntrantAMettreEnplace())
						return ConstantPGCA.QUANTITE_INTRANT_NODISPO_AU_FOURNISSEUR;
					miseEnPlaceAffectuer.setMagasinSource("Stock Residuel de " + resi.getPointdeVente().getLibelle()
							+ " -  " + resi.getIntrant().getProgramme().getLibelle());
				}
			}
			if (fromFournisseur != null) {
				Intrant ref = (Intrant) modelDAO.getEntityDBById(fromFournisseur.getProduit_id(), Intrant.class);
				logger.info("---- MEP  Intrant : "  + ref);

				if (ref != null)
					if (ref.getTarif() == null)
						return ConstantPGCA.INTRANT_NON_TARIFIE;
			}
			// pas de doublon LB ou LV
			MiseEnPlaceEffectuer mepByLV = modelDAO.loadMiseEnPlaceByidLV(infosMEP.getLvMiseEnPlace());
			if (mepByLV != null)
				return ConstantPGCA.LV_MISE_EN_PLACE_EXIST;
			MiseEnPlaceEffectuer mepByBL = modelDAO.loadMiseEnPlaceByidBl(infosMEP.getBlMiseEnPlace());
			if (mepByBL != null)
				return ConstantPGCA.BL_MISE_EN_PLACE_EXIST;
			if (fromFournisseur == null && checkFournisseur)
				return ConstantPGCA.INTRANT_NODISPO_AU_FOURNISSEUR;
			if (checkFournisseur && fromFournisseur.getQuantite() < infosMEP.getQuantiteIntrantAMettreEnplace())
				return ConstantPGCA.QUANTITE_INTRANT_NODISPO_AU_FOURNISSEUR;

			miseEnPlaceAffectuer.setTransporteur(transporteur);
			miseEnPlaceAffectuer.setCamion(camionumero);
			miseEnPlaceAffectuer.setChauffeur(chauffeur);
			miseEnPlaceAffectuer.setMiseEnplaceConcerne(planConcernee);
			if (fournisseur != null)
				miseEnPlaceAffectuer.setFounnisseur(fournisseur);
			else if (fournisseur == null && !checkFournisseur) {
				// stock fait depuis point de vente : on prend le founisseur 0
				// (par defaut) : sedab
				miseEnPlaceAffectuer.setFounnisseur((Fournisseur) modelDAO.getEntityDBById(0L, Fournisseur.class));
			}
			miseEnPlaceAffectuer.setPointdeVenteLibelle(planConcernee.getPointdeVenteConcerne().getLibelle());
			miseEnPlaceAffectuer.setProgrammeConcerne(planConcernee.getProgrammeConcerne());
			miseEnPlaceAffectuer.setQuantiteAmettreEnplace(infosMEP.getQuantiteIntrantAMettreEnplace());
			miseEnPlaceAffectuer.setBlManuel(infosMEP.getBlMiseEnPlace());
			miseEnPlaceAffectuer.setLvManuel(infosMEP.getLvMiseEnPlace());
			if (infosMEP.getDateEffectiveMEP() == null || infosMEP.getDateEffectiveMEP().toString().trim().equals("")) {
				miseEnPlaceAffectuer
						.setDateMiseEnplaceEffective(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
			} else {

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date date = formatter.parse(infosMEP.getDateEffectiveMEP());
				miseEnPlaceAffectuer.setDateMiseEnplaceEffective(new java.sql.Date(date.getTime()));
			}
			// Création implicite du BL
			BonDeLivraison blToSave = new BonDeLivraison();
			HttpSession session = null;
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			if (request != null)
				session = request.getSession(false);
			String connectedUserLocation = (String) session.getAttribute("connectedUserPointPhysique");
			Long connectedUseridStock = (Long) session.getAttribute("connectedUserStockid");
			Long connectedUserid = (Long) session.getAttribute("connectedUserPersonneid");
			// BL implicite avec le produit
			blToSave.setProgramme(planConcernee.getProgrammeConcerne());
			blToSave.setChauffeurId(chauffeur.getChauffeur());
			blToSave.setTransporteurId(transporteur);
			blToSave.setNumeroCamionId(camionumero);
			
			// INIT SQL  :::: Commune ID =  STOCK ID  ()dddd
			blToSave.setIdStockReceptionnaire(planConcernee.getPointDeChute().getStock().getStock_id()); //
			blToSave.setProvenanceProduit(connectedUserLocation);
			blToSave.setIdStockExpedition(connectedUseridStock);
			blToSave.setIdAuteurBl(connectedUserid);
			blToSave.setBlStatut(0);
			blToSave.setNumeroBLManuel(infosMEP.getBlMiseEnPlace());
			blToSave.setNumeroLVManuel(infosMEP.getLvMiseEnPlace());
			blToSave.setDateEdition(new Date());
			
			logger.info("---- MEP  Intrant : "  + blToSave.toString());

			modelDAO.save(blToSave);
			Log.info("---- MEP  Intrant : "  + blToSave);


			// On mets a jour le stock auprès du fournisseur spécifique
			// (déduction quantite mise en place sur le stock du fournisser)
			if (fromFournisseur != null && checkFournisseur) {
				fromFournisseur
						.setQuantite(fromFournisseur.getQuantite() - infosMEP.getQuantiteIntrantAMettreEnplace());
				modelDAO.save(fromFournisseur);
				Log.info("---- MEP  Intrant : "  + fromFournisseur);


				// link bl avec le produit envoyé
				BonDeLivraisonProduit produitAssocie = new BonDeLivraisonProduit();
				produitAssocie.setBlAssocie(blToSave);
				produitAssocie.setLibelle(planConcernee.getProduitAmettreEnPlace().getLibelle());
				produitAssocie.setLibelleStockSortant(fromFournisseur.getLibelle());
				produitAssocie.setQuantite(infosMEP.getQuantiteIntrantAMettreEnplace());
				produitAssocie.setIdProduit(fromFournisseur.getProduit_id());
				modelDAO.save(produitAssocie);
			}

			// modelDAO.synchroniseWithDB(blToSave);
			miseEnPlaceAffectuer.setBl(blToSave);
			modelDAO.save(miseEnPlaceAffectuer);
			Log.info("---- MEP  Intrant : "  + miseEnPlaceAffectuer);


			// On mets a jour le plan : Apres chaque envoi on mets a jour le
			// plan
			if (infosMEP.getQuantiteIntrantAMettreEnplace() > planConcernee.getReliquat())
				return -2;

			planConcernee.setReliquat(planConcernee.getReliquat() - infosMEP.getQuantiteIntrantAMettreEnplace());
			planConcernee.setStockResiduel(planConcernee.getQuota() - planConcernee.getReliquat());
			modelDAO.save(planConcernee);
			Log.info("---- MEP  Intrant : "  + planConcernee);


			// Recupere les infos du BL coté DTO pour la facture a generer
			Log.info("---- MEP  creation BL : "  + blToSave);

			createBLFromPlan(blToSave, blDTO);
			Log.info("---- MEP  creation BL FIN : "  + blToSave);


			/*
			 * String mail = "";
			 * blDTO.setCorpsMessageMail(chauffeur.getLibelle() +
			 * ", chauffeur du camion immatriculé <b>" +
			 * camionumero.getNumeroCamion() +
			 * "</b> est en route vers votre point de vente pour une livraison de "
			 * + infosMEP.getQuantiteIntrantAMettreEnplace() + " (T)  de " +
			 * fromFournisseur.getLibelle() +
			 * ".  <br/><br/>Pour plus de détails ,  merci de vous connecter sur la plateforme e-sedab."
			 * ); blDTO.setGerantMail(mail);
			 * blDTO.setCorpsMessageMailBlNum(infosMEP.getBlMiseEnPlace() + "");
			 * blDTO.setProvenanceBL(connectedUserLocation);
			 * blDTO.setZoneDeReception(planConcernee.getPointdeVenteConcerne()
			 * != null ? planConcernee.getPointdeVenteConcerne().getLibelle() :
			 * "");
			 * blDTO.setPointdeVenteRegion(planConcernee.getPointdeVenteConcerne
			 * () != null ?
			 * planConcernee.getPointdeVenteConcerne().getDepartement().
			 * getRegion().getLibelle() : "");
			 * blDTO.setPointdeVenteDepartement(planConcernee.
			 * getPointdeVenteConcerne() != null ?
			 * planConcernee.getPointdeVenteConcerne().getDepartement().
			 * getLibelle() : "");
			 */

			// mise a jour stock point de vente en cas de source point de vente
			if (infosMEP.getIdCommuneResiduel() == null) 
			{
				StockResiduelPointDeVente resi = modelDAO.createOrUpdateStockResiduelByPointdeVente(
						planConcernee.getPointdeVenteConcerne().getId(), fromFournisseur.getProduit_id(),
						infosMEP.getQuantiteIntrantAMettreEnplace());
				
				logger.info("---- MEP  StockResiduelPointDeVente resi =  : "  + resi);

				modelDAO.save(resi);
				logger.info("---- MEP  Intrant : "  + resi);


			} else // le residuel existe on le mets a jour -> déduction de la
					// mise en place sur le residuel actuel quatite restant
			{
				StockResiduelPointDeVente resi = (StockResiduelPointDeVente) modelDAO
						.getEntityDBById(infosMEP.getIdCommuneResiduel(), StockResiduelPointDeVente.class);
				if (resi != null) {
					Double d = resi.getTotalStockActuel();
					// si la mise en place provient d'un point de on deduit et
					// si le poitn de vente recoit on ajoute
					if (checkFournisseur)
						d += infosMEP.getQuantiteIntrantAMettreEnplace();
					else
						d -= infosMEP.getQuantiteIntrantAMettreEnplace();
					resi.setTotalStockActuel(d);
				}
				modelDAO.save(resi);
				logger.info("---- MEP  Intrant : "  + resi);

			}
			return 0;
		} catch (Exception e) {
			logger.error("Erreur survenur lors de la mise en place : " + e.getMessage() + " +" + e.getStackTrace());
			return -1;
		}
	}

	@Override
	public Intrant loadProduitbyIdAdnIdFournisseur(Long idProduit, Long idFournisseur) throws EntityDBDAOException {
		return modelDAO.verifierStockRestantAupresFournisseur(idProduit, idFournisseur);
	}

	@Override
	public Intrant loadProduitbyIdAdnIdMagasin(Long idProduit, Long idStockMagasin) throws EntityDBDAOException {
		return modelDAO.verifierStockRestantAupresMagasin(idProduit, idStockMagasin);
	}

	@Override
	public BonDeLivraison loadBlByNumeroBl(String lv) throws EntityDBDAOException {
		return modelDAO.loadBlByNumeroBl(lv);
	}

	private String formatDate(String date) throws EntityDBDAOException {
		if (date != null && date.length() > 0) // créé
		{
			String[] tmp = date.trim().split("-");
			if (tmp.length > 2)
				return (tmp[2] + "-" + tmp[1] + "-" + tmp[0]);
		}
		return date;
	}

	/**
	 * Chaque Mise a disposition effectué un BL est automatique créé et associé
	 * à la mise a dispo
	 **/
	private void createBLFromPlan(BonDeLivraison bl, BonDeLivraisonDTO blDTO) {
		blDTO = new BonDeLivraisonDTO();

		blDTO.setId(bl.getBl_id());
		blDTO.setIdprogramme(bl.getProgramme() != null ? bl.getProgramme().getPgca_idprog() + "" : "");
		blDTO.setProgrammeLibelle(bl.getProgramme() != null ? bl.getProgramme().getLibelle() + "" : "");
		blDTO.setChauffeurlibelle(bl.getChauffeurId() != null
				? bl.getChauffeurId().getPrenom() + " " + bl.getChauffeurId().getPrenom() : "");
		blDTO.setTransporteurlibelle(bl.getTransporteurId() != null ? bl.getTransporteurId().getLibelle() : "");
		blDTO.setCamionlibelle(bl.getNumeroCamionId() != null ? bl.getNumeroCamionId().getNumeroCamion() : "");
	}

	@Override
	public List<MiseEnplaceDTOPointDeVente> loadMiseEnPlaceAeffectuerByidPointdeVente(Long idPointdeVente,
			Long Categorie, Long idIntrant) throws EntityDBDAOException {
		@SuppressWarnings("unchecked")
		List<MiseEnPlaceAEffectuer> lp = (List<MiseEnPlaceAEffectuer>) modelDAO
				.loadMiseEnPlaceAeffectuerByidPointdeVente(idPointdeVente, Categorie, idIntrant);

		if (lp == null)
			return null;

		List<MiseEnplaceDTOPointDeVente> ventes = new ArrayList<MiseEnplaceDTOPointDeVente>();
		for (MiseEnPlaceAEffectuer v : lp) {
			MiseEnplaceDTOPointDeVente pdto = new MiseEnplaceDTOPointDeVente();

			mapData(pdto, v);
			// lecture des mise en place deja effectuee
			List<MiseEnPlaceEffectuerDTO> listMEPeffectuee = loadAllMiseEnPlaceEffectueeByidMAP(v.getId_MEPaAF());
			double totalCumul = 0;

			for (MiseEnPlaceEffectuerDTO ll : listMEPeffectuee)
				totalCumul += ll.getQuantiteAmettreEnplace();
			pdto.setCumulIntrantAMettreEnplace(totalCumul);
			pdto.setReliquatIntrantAMettreEnplace(v.getQuota() - totalCumul);
			ventes.add(pdto);
		}

		return ventes;
	}

	private void mapData(MiseEnplaceDTOPointDeVente pdto, MiseEnPlaceAEffectuer v) {
		pdto.setIdMiseEnPlace(v.getId_MEPaAF());
		pdto.setIdSlectedMiseEnplace(v.getId_MEPaAF());
		// pdto.setIdPointDeVente(v.getPointdeVenteConcerne() != null ?
		// v.getPointdeVenteConcerne().getPtv_id() : null);
		// pdto.setNomPointDeVente(
		// v.getPointdeVenteConcerne() != null ?
		// v.getPointdeVenteConcerne().getLibelle() : "Inconnu");
		pdto.setQuotaIntrantAMettreEnplace(v.getQuota());
		// pdto.setNomGerant(v.getPointdeVenteConcerne() != null &&
		// v.getPointdeVenteConcerne().getGerant() != null
		// ? v.getPointdeVenteConcerne().getGerant().getPrenom() + " "
		// + v.getPointdeVenteConcerne().getGerant().getNom()
		// : "Inconnu");
		pdto.setLibelleIntrantAMettreEnplace(
				v.getProduitAmettreEnPlace() != null ? v.getProduitAmettreEnPlace().getLibelle() : "Inconnu");
		pdto.setIdIntrantAMettreEnplace(
				v.getProduitAmettreEnPlace() != null ? v.getProduitAmettreEnPlace().getRefIntrantId() : null);
		pdto.setIdProgramme(v.getProgrammeConcerne().getPgca_idprog());
		pdto.setNomProgramme(v.getProgrammeConcerne().getLibelle());
	}

	/**********************************************************************************************
	 * E-N-D I N T R A N T S DES C A M P A G N E S
	 **********************************************************************************************/

	/**************************
	 * GESTION DES Commandes
	 * 
	 ************************************/

	@Override
	public boolean enregistrerCommnande(CommandeDTO commandeDTO, List<ProduitDTO> listProduitCommande) {

		float montantTotolcmd = 0;
		try {
			Commande cmdToSave = new Commande();

			// TO
			cmdToSave.setDateCMD(new Date().toString());
			cmdToSave.setDateLivraisonSouhaiteCMD(commandeDTO.getDateLivraisonSouhaite());
			cmdToSave.setStatutCMD(0);
			cmdToSave.setMontantTotalCMD(0L);
			cmdToSave.setStatutCMDLibelle(ConstantPGCA.CMD_A_TRAITER);
			cmdToSave.setPaiementCMD(commandeDTO.getModeDePaiement());
			cmdToSave.setMontantEncaisseCMD(commandeDTO.getMontantPayeInitialement());
			cmdToSave.setClientCommuneDechargement(commandeDTO.getCommuneLibelle());
			cmdToSave.setClientNom(commandeDTO.getClientNom());
			cmdToSave.setClientTel(commandeDTO.getClientTel());
			cmdToSave.setClientAddresse(commandeDTO.getClienAdresse());

			PointDeVente pv = (PointDeVente) modelDAO.getEntityDBById(new Long(commandeDTO.getIdPointdeVente()),
					PointDeVente.class);

			ProgrammeAgricol prog = (ProgrammeAgricol) modelDAO.getEntityDBById(new Long(commandeDTO.getIdProgramme()),
					ProgrammeAgricol.class);

			Personne editeurCommandePersonne = (Personne) modelDAO
					.getEntityDBById(new Long(commandeDTO.getAuteurCommandeidPersonne()), Personne.class);

			PointDeCollecte editeurCommandeZone = (PointDeCollecte) modelDAO.getEntityDBById(
					new Long(commandeDTO.getEditeurCommandeIdPointDeCollecte()), PointDeCollecte.class);

			if (pv == null || pv.getGerant() == null || prog == null || editeurCommandePersonne == null
					|| editeurCommandeZone == null)
				return false;

			cmdToSave.setEditeurCommandePersonne(editeurCommandePersonne);
			cmdToSave.setEditeurCommandePointdeCollecte(editeurCommandeZone);
			cmdToSave.setProgrammeConcerne(prog);
			cmdToSave.setPointdeVenteTraitantLaCMD(pv);

			modelDAO.save(cmdToSave);

			for (ProduitDTO pd : listProduitCommande) {
				CommandeProduitAssocie produitAssocie = new CommandeProduitAssocie();

				produitAssocie.setCommandeAssocie(cmdToSave);
				produitAssocie.setPrixUnitaire(new Float(pd.getPrixUnitaire()));
				produitAssocie.setQuantitedelaCommande(pd.getQuantiteProduitAvendre());

				Intrant p = (Intrant) modelDAO.getEntityDBById(pd.getIdProduit(), Intrant.class);
				if (p == null)
					return false;

				produitAssocie.setProduitVendu(p);
				montantTotolcmd += pd.getQuantiteProduitAvendre() * pd.getPrixTotal();
				modelDAO.save(produitAssocie);
			}

			cmdToSave.setMontantTotalCMD(montantTotolcmd);
			cmdToSave.setReferenceCMD("CMD" + cmdToSave.getCmd_id());

			modelDAO.save(cmdToSave);
			modelDAO.synchroniseWithDB(cmdToSave);
			commandeDTO.setIdCommande(cmdToSave.getCmd_id());
			commandeDTO.setReferenceCMD(cmdToSave.getReferenceCMD());

			return true;
		} catch (Exception e) {
			Log.error("Une erreur est survenue lors de l'enregtrement de la commande " + e.getMessage()
					+ e.getStackTrace());
			return false;
		}
	}

	@Override
	public boolean enregistrerOrdreDeLivraison(CommandeDTO commandeDTO, List<ProduitDTO> listProduitCommande) {

		float montantTotolcmd = 0;
		try {
			OrdreLivraison cmdToSave = new OrdreLivraison();

			// TO
			cmdToSave.setDateOrdre(new Date().toString());
			cmdToSave.setDateLivraisonSouhaiteOrdre(commandeDTO.getDateLivraisonSouhaite());
			// cmdToSave.setStatutOrd();
			cmdToSave.setStatutOrd(ConstantPGCA.ORD_A_TRAITER);
			cmdToSave.setClientCommuneDechargement(commandeDTO.getCommuneLibelle());
			cmdToSave.setClientNom(commandeDTO.getClientNom());
			cmdToSave.setClientTel(commandeDTO.getClientTel());
			cmdToSave.setClientAddresse(commandeDTO.getClienAdresse());
			cmdToSave.setNumeroCNIClient(commandeDTO.getClientCNI());
			cmdToSave.setClientAddresse(commandeDTO.getClienAdresse());

			PointDeVente pv = (PointDeVente) modelDAO.getEntityDBById(new Long(commandeDTO.getIdPointdeVente()),
					PointDeVente.class);

			ProgrammeAgricol prog = (ProgrammeAgricol) modelDAO.getEntityDBById(new Long(commandeDTO.getIdProgramme()),
					ProgrammeAgricol.class);

			Personne editeurCommandePersonne = (Personne) modelDAO
					.getEntityDBById(new Long(commandeDTO.getAuteurCommandeidPersonne()), Personne.class);

			PointDeCollecte editeurCommandeZone = (PointDeCollecte) modelDAO.getEntityDBById(
					new Long(commandeDTO.getEditeurCommandeIdPointDeCollecte()), PointDeCollecte.class);

			if (pv == null  /* || pv.getGerant() == null*/ || prog == null || editeurCommandePersonne == null
					|| editeurCommandeZone == null)
				return false;

			cmdToSave.setEditeurCommandePersonne(editeurCommandePersonne);
			cmdToSave.setEditeurCommandePointdeCollecte(editeurCommandeZone);
			cmdToSave.setProgrammeConcerne(prog);
			cmdToSave.setPointdeVenteTraitantLaCMD(pv);

			modelDAO.save(cmdToSave);

			for (ProduitDTO pd : listProduitCommande) {
				OrdreLivraisonProduitAssocie produitAssocie = new OrdreLivraisonProduitAssocie();

				produitAssocie.setCommandeAssocie(cmdToSave);
				produitAssocie.setPrixUnitaire(new Float(pd.getPrixUnitaire()));
				produitAssocie.setQuantitedelaCommande(pd.getQuantiteProduitAvendre());

				Intrant p = (Intrant) modelDAO.getEntityDBById(pd.getIdProduit(), Intrant.class);
				if (p == null)
					return false;

				produitAssocie.setProduitVendu(p);
				montantTotolcmd += pd.getQuantiteProduitAvendre() * pd.getPrixTotal();
				modelDAO.save(produitAssocie);
			}
			// utils pour facture et message de confirmation
			cmdToSave.setMontantTotalOrdre(montantTotolcmd);
			cmdToSave.setReferenceOrdre("ODL" + cmdToSave.getOrd_id());
			commandeDTO.setIdCommande(cmdToSave.getOrd_id());
			commandeDTO.setReferenceCMD(cmdToSave.getReferenceOrdre());
			commandeDTO.setPointDeVenteLibelleProvenance(pv.getLibelle());
			modelDAO.save(cmdToSave);

			try {

				String title = "Demande validation OL N° OL00" + cmdToSave.getOrd_id();
				String messsage = editeurCommandePersonne.getPrenom() + " " + editeurCommandePersonne.getNom()
						+ " vous a envoyé une demande de validation  de l'ordre de  livraion N° OL00" + "<b>"
						+ cmdToSave.getOrd_id() + "</b> .<br/>" + "Après votre validation, le bénéficiaire <b>"
						+ commandeDTO.getClientNom() + " </b>" + " pourra recupérer ses intrants au point de vente de "
						+ pv.getLibelle() + " sur présentation de sa carte d'identité n° " + commandeDTO.getClientCNI()
						+ "<br/> <a href=\"http://localhost:8484/pgca/pages/managerGestionOL.xhtml?idBlocToShow=1\">  Cliquez ici pour valider l'ordre </a>";

				// notif all manager
				notifierService.sendEmailWithoutAttachement("sedabsenegal@gmail.com", title, messsage);
			} catch (Exception e) {
				Log.error("L'email de notifacation n'a pas été envoyé");
			}

			return true;
		} catch (Exception e) {
			Log.error(
					"Une erreur est survenue lors de l'enregreement de l'ordre " + e.getMessage() + e.getStackTrace());
			return false;
		}
	}

	// create beneficiare ordre de livraion et son adresse
	void SaveBeneficiaireOrdre(CommandeDTO commandeDTO, Personne beneficiaireOrdreLivraion)
			throws EntityDBDAOException {
		beneficiaireOrdreLivraion.setNom(commandeDTO.getClientNom());
		beneficiaireOrdreLivraion.setPrenom(commandeDTO.getLibelleProgramme());
		beneficiaireOrdreLivraion.setNumeroCNI(commandeDTO.getClientCNI());

		modelDAO.save(beneficiaireOrdreLivraion);

		/*
		 * Adresse adr = new Adresse();
		 * 
		 * adr.setLibelle(commandeDTO.getClienAdresse());
		 * 
		 * Commune c =
		 * modelDAO.loadCommuneFromLibelleCommune(commandeDTO.getCommuneLibelle(
		 * ));
		 * 
		 * if(c != null) { adr.setCommune(c);
		 * adr.setDepartement(c.getDepartement());
		 * adr.setRegion(c.getDepartement().getRegion()); }
		 * 
		 * modelDAO.save(adr);
		 * 
		 * //modelDAO.save(beneficiaireOrdreLivraion);
		 * 
		 * beneficiaireOrdreLivraion.setAdresse(adr);
		 * modelDAO.save(beneficiaireOrdreLivraion);
		 */

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommandeDTO> getListCommandeEnvoyeByidPointdeCollect(Long idpc) throws Exception {
		@SuppressWarnings("unchecked")

		String code = SessionManagedBean.getSessionDataByTag("profilCode");
		List<Commande> cmdList;

		if (code.equals("manager")) {
			cmdList = (List<Commande>) modelDAO.genericClassLoader(Commande.class);
		} else {
			cmdList = (List<Commande>) modelDAO.genericSqlClassLoaderById(Commande.class, "pgca_commande", "pdc_id",
					idpc);
		}

		if (cmdList == null)
			return null;

		List<CommandeDTO> cmddto = new ArrayList<CommandeDTO>();

		for (Commande c : cmdList) {
			CommandeDTO dto = new CommandeDTO();

			dto.setIdCommande(c.getCmd_id());
			dto.setReferenceCMD(c.getReferenceCMD());
			dto.setIdPointdeVente(
					c.getPointdeVenteTraitantLaCMD() != null ? c.getPointdeVenteTraitantLaCMD().getPtv_id() : -1);
			dto.setIdProgramme(c.getProgrammeConcerne().getPgca_idprog());
			dto.setLibelleProgramme(c.getProgrammeConcerne().getLibelle());
			dto.setDateLivraisonSouhaite(c.getDateLivraisonSouhaiteCMD());
			dto.setStatutCMD(c.getStatutCMDLibelle());
			dto.setAuteurCommandeNom(
					c.getEditeurCommandePersonne().getPrenom() + c.getEditeurCommandePersonne().getNom());
			dto.setClientNom(c.getClientNom());
			dto.setClientTel(c.getClientTel());
			dto.setClienAdresse(c.getClientAddresse());
			dto.setCommuneLibelle(
					c.getPointdeVenteTraitantLaCMD() != null ? c.getPointdeVenteTraitantLaCMD().getLibelle() : "");
			dto.setStatus(c.getStatutCMD());

			if (c.getPointdeVenteTraitantLaCMD() != null && c.getPointdeVenteTraitantLaCMD().getAdresse() != null)
				dto.setDepartementCMD(c.getPointdeVenteTraitantLaCMD().getAdresse().getDepartement() != null
						? c.getPointdeVenteTraitantLaCMD().getAdresse().getDepartement().getLibelle() : "");

			if (c.getStatutCMD() == 0) {
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_EN_COURS);
				dto.setStatusLibelle(ConstantPGCA.CMD_A_TRAITER);
				dto.setIconFontAwesome(ConstantPGCA.ICON_ENCOURS);
			} else if (c.getStatutCMD() == 1) {
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_TRAITE);
				dto.setStatusLibelle(ConstantPGCA.CMD_TRAITE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_REFUSE);

			}

			cmddto.add(dto);
		}
		return cmddto;
	}

	@Override
	public boolean accepterCommande(Long idCommande) throws EntityDBDAOException {

		Commande c = (Commande) modelDAO.getEntityDBById(idCommande, Commande.class);

		if (c != null) {
			c.setStatutCMD(1);

			try {
				modelDAO.save(c);
				return true;
			} catch (EntityDBDAOException e) {
				Log.error("Erreur ");
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public List<CommandeDTO> managerLoadOrdresAvalider() {
		@SuppressWarnings("unchecked")
		// List<OrdreLivraison> cmdList = (List<OrdreLivraison>)
		// modelDAO.genericSqlClassLoaderById(OrdreLivraison.class,
		// "pgca_OrdreLivraison", "statutOrd", 0L);

		List<OrdreLivraison> cmdList = (List<OrdreLivraison>) modelDAO.genericClassLoader(OrdreLivraison.class);

		if (cmdList == null)
			return null;

		List<CommandeDTO> cmddto = new ArrayList<CommandeDTO>();

		for (OrdreLivraison c : cmdList) {
			CommandeDTO dto = new CommandeDTO();

			// dto.setIdCommande(c.getOrd_id());
			dto.setIdOrdre(c.getOrd_id());
			dto.setIdProgramme(c.getProgrammeConcerne().getPgca_idprog());
			dto.setLibelleProgramme(c.getProgrammeConcerne().getLibelle());
			dto.setIdPointdeVente(
					c.getPointdeVenteTraitantLaCMD() != null ? c.getPointdeVenteTraitantLaCMD().getPtv_id() : -1);
			dto.setReferenceCMD(c.getReferenceOrdre());
			dto.setDateLivraisonSouhaite(c.getDateLivraisonSouhaiteOrdre());
			dto.setStatutCMD(c.getStatutOrd() + "");
			dto.setAuteurCommandeNom(
					c.getEditeurCommandePersonne().getPrenom() + c.getEditeurCommandePersonne().getNom());
			dto.setClientNom(c.getClientNom());
			dto.setClientTel(c.getClientTel());
			dto.setClienAdresse(c.getClientAddresse());
			dto.setCommuneLibelle(
					c.getPointdeVenteTraitantLaCMD() != null ? c.getPointdeVenteTraitantLaCMD().getLibelle() : "");
			dto.setClientCNI(c.getNumeroCNIClient());

			if (c.getStatutOrd() == 0) {
				dto.setStatusLibelle(ConstantPGCA.ORD_A_TRAITER_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_ENCOURS);
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_EN_COURS);
			} else if (c.getStatutOrd() == 1) {
				dto.setStatusLibelle(ConstantPGCA.ORD_TRAITE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_TRAITE);
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_TRAITE);
			} else if (c.getStatutOrd() == 2) {
				dto.setStatusLibelle(ConstantPGCA.ORD_TRAITE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_TRAITE);
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_VALIDE);
			} else if (c.getStatutOrd() == 4) {
				dto.setStatusLibelle(ConstantPGCA.ORD_VALIDE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_ENCOURS);
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_TRAITE);

			}
			cmddto.add(dto);
		}
		return cmddto;
	}

	@Override
	public List<CommandeDTO> managerLoadOrdresAvaliderByCriteria(Long idProgamme, Long idPointVente, int statusOL,
			String client) {
		@SuppressWarnings("unchecked")
		// List<OrdreLivraison> cmdList = (List<OrdreLivraison>)
		// modelDAO.genericSqlClassLoaderById(OrdreLivraison.class,
		// "pgca_OrdreLivraison", "statutOrd", 0L);

		List<OrdreLivraison> cmdList = (List<OrdreLivraison>) modelDAO.rechercheForManager(idProgamme, idPointVente,
				statusOL, client);
		if (cmdList == null)
			return null;
		List<CommandeDTO> cmddto = new ArrayList<CommandeDTO>();
		for (OrdreLivraison c : cmdList) {
			CommandeDTO dto = new CommandeDTO();
			dto.setIdOrdre(c.getOrd_id());
			dto.setReferenceCMD(c.getReferenceOrdre());
			dto.setDateLivraisonSouhaite(c.getDateLivraisonSouhaiteOrdre());
			dto.setStatutCMD(c.getStatutOrd() + "");
			dto.setAuteurCommandeNom(
					c.getEditeurCommandePersonne().getPrenom() + c.getEditeurCommandePersonne().getNom());
			dto.setClientNom(c.getClientNom());
			dto.setClientTel(c.getClientTel());
			dto.setClienAdresse(c.getClientAddresse());
			dto.setCommuneLibelle(c.getClientCommuneDechargement());
			dto.setClientCNI(c.getNumeroCNIClient());

			if (c.getStatutOrd() == 0) {
				dto.setStatusLibelle(ConstantPGCA.ORD_A_TRAITER_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_ENCOURS);
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_EN_COURS);
			} else if (c.getStatutOrd() == 1) {
				dto.setStatusLibelle(ConstantPGCA.ORD_TRAITE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_TRAITE);

			} else if (c.getStatutOrd() == 2) {
				dto.setStatusLibelle(ConstantPGCA.ORD_REFUSE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_REFUSE);
			} else if (c.getStatutOrd() == 4) {
				dto.setStatusLibelle(ConstantPGCA.ORD_VALIDE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_ENCOURS);
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_TRAITE);

			}
			cmddto.add(dto);
		}
		return cmddto;
	}

	@Override
	public List<CommandeDTO> getListCommandeByCriteria(Long idPointDeVente, Long idProgramme)
			throws EntityDBDAOException {
		@SuppressWarnings("unchecked")
		List<Commande> cmdList = null;
		try {
			cmdList = (List<Commande>) modelDAO.loadCommandeByIdPointdeVenteAndProgId(idPointDeVente, idProgramme);
		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (cmdList == null)
			return null;

		List<CommandeDTO> cmddto = new ArrayList<CommandeDTO>();

		for (Commande c : cmdList) {
			CommandeDTO dto = new CommandeDTO();

			dto.setIdCommande(c.getCmd_id());
			dto.setReferenceCMD(c.getReferenceCMD());
			dto.setDateLivraisonSouhaite(c.getDateLivraisonSouhaiteCMD());
			dto.setStatutCMD(c.getStatutCMDLibelle());
			dto.setAuteurCommandeNom(
					c.getEditeurCommandePersonne().getPrenom() + " " + c.getEditeurCommandePersonne().getNom());
			if (c.getEditeurCommandePersonne() != null && c.getEditeurCommandePersonne().getContact() != null)
				dto.setAuteurCommandeProvenanceTel(c.getEditeurCommandePersonne().getContact().getMobile());
			if (c.getEditeurCommandePointdeCollecte() != null)
				dto.setAuteurCommandeProvenance(c.getEditeurCommandePointdeCollecte().getLibelle());

			dto.setClientNom(c.getClientNom());
			dto.setClientTel(c.getClientTel());
			dto.setClienAdresse(c.getClientAddresse());
			dto.setCommuneLibelle(c.getClientCommuneDechargement());

			if (c.getStatutCMD() == 0) {
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_EN_COURS);
				dto.setStatusLibelle(ConstantPGCA.CMD_A_TRAITER);
			} else if (c.getStatutCMD() == 1) {
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_TRAITE);
				dto.setStatusLibelle(ConstantPGCA.CMD_TRAITE);
			} else if (c.getStatutCMD() == 2) {
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_REFUSE);
				dto.setStatusLibelle(ConstantPGCA.CMD_REFUSE);
			}

			cmddto.add(dto);
		}

		return cmddto;

	}

	@Override
	public List<CommandeDTO> getListCommandeByCriteria(Long idPoindeVente, Long idProgramme, int statut)
			throws EntityDBDAOException {

		List<Commande> cmdList = null;

		cmdList = (List<Commande>) modelDAO.loadCommandeEnAttenteDeValidationByIdPointdeVenteAndProgId(idPoindeVente,
				idProgramme, statut);

		if (cmdList == null)
			return null;

		List<CommandeDTO> cmddto = new ArrayList<CommandeDTO>();

		for (Commande c : cmdList) {
			CommandeDTO dto = new CommandeDTO();

			dto.setIdCommande(c.getCmd_id());
			dto.setReferenceCMD(c.getReferenceCMD());
			dto.setDateLivraisonSouhaite(c.getDateLivraisonSouhaiteCMD());
			dto.setStatutCMD(c.getStatutCMDLibelle());
			dto.setAuteurCommandeNom(
					c.getEditeurCommandePersonne().getPrenom() + c.getEditeurCommandePersonne().getNom());
			dto.setClientNom(c.getClientNom());
			dto.setClientTel(c.getClientTel());
			dto.setClienAdresse(c.getClientAddresse());
			dto.setCommuneLibelle(c.getClientCommuneDechargement());

			if (c.getStatutCMD() == 0) {
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_EN_COURS);
				dto.setStatusLibelle(ConstantPGCA.CMD_A_TRAITER);
			} else if (c.getStatutCMD() == 1) {
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_TRAITE);
				dto.setStatusLibelle(ConstantPGCA.CMD_TRAITE);
			} else if (c.getStatutCMD() == 2) {
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_REFUSE);
				dto.setStatusLibelle(ConstantPGCA.CMD_REFUSE);
			}

			cmddto.add(dto);
		}

		return cmddto;
	}

	@Override
	public List<CommandeDTO> getListCommandeByCriteria(Long idProgramme, int statut) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommandeDTO> getListCommandeByCriteria(Long idProgramme) {
		// TODO Auto-generated method stub
		return null;
	}

	/*************** END COMMANDE *************/

	/*********** STOCK ******************/

	/*** Mettre à jour le stock de tous les produit à vendre ****/
	public Map<Boolean, String> updateStockProduit(CommandeDTO infosCommande)
			throws ProgrammeException, EntityDBDAOException {
		Map<Boolean, String> verifResult = new HashMap<Boolean, String>();

		for (ProduitDTO dto : infosCommande.getListProduitsDTOtoCreate()) {
			ProduitDTO pbase = loadProduitDTObyIdProduit(dto.getIdProduit());

			if (pbase != null && pbase.getQuantite() < dto.getQuantiteProduitAvendre()) {
				verifResult.put(false, "Le stock de " + pbase.getLibelleProduit() + " restant < " + pbase.getQuantite()
						+ " > est inferieur à la quantité à vendre <" + dto.getQuantiteProduitAvendre() + " >");
			}
		}
		verifResult.put(true, "tous les produit en disponible en stock");

		return verifResult;
	}

	/**** ALL PRODUIT OF A STOCK *****/
	@Override
	public List<ProduitDTO> loadAallProduitFromStockbyIdStock(Long idStock) throws EntityDBDAOException {
		List<Intrant> allstock = modelDAO.loadStockByIdPointdeVente(idStock);

		if (allstock == null)
			return null;

		List<ProduitDTO> allstockDTO = new ArrayList<ProduitDTO>();

		for (Intrant p : allstock) {
			ProduitDTO dtoP = new ProduitDTO();

			dtoP.setIdProduit(p.getProduit_id());
			dtoP.setLibelle(p.getIntrant().getLibelle());
			dtoP.setLibelleProgramme(p.getProgramme().getLibelle());
			dtoP.setIdStockProduit(p.getStockRef() != null ? p.getStockRef().getStock_id() : null);
			dtoP.setQuantite(p.getQuantite());
			dtoP.setIdtypeProduit(p.getIntrant().getTypeIntrantId().getTypeIntrantId());
			dtoP.setIdCampagne(p.getProgramme().getCampagne().getId_ca());
			dtoP.setLibelleCampagne(p.getProgramme().getCampagne().getLibelle());

			dtoP.setIdProgramme(p.getProgramme().getId_ca());
			dtoP.setLibelleProgramme(p.getProgramme().getLibelle());

			if (p.getTarif() != null)
				dtoP.setPrixUnitaire(p.getTarif().getPrixProducteur());
			else
				dtoP.setPrixUnitaire(0f);

			allstockDTO.add(dtoP);
		}

		return allstockDTO;
	}

	/***
	 * VERIFIER S'IL YA ASSEZ DE STOCK POUR LE PRODUIT A VENDRE Pour le stock
	 * local
	 ****/
	@Override
	public Map<Boolean, ProduitDTO> chercherDisponibiliteProduitParPoids(ProduitDTO pdto)
			throws ProgrammeException, EntityDBDAOException {

		return null;
		// Produit produitStockLocal = (Produit)
		// modelDAO.getEntityDBById(pdto.getIdProduit(), Produit.class);
		// String autreStockDisponible = "";
		// String localZone = "";
		//
		// // suggestion autre point de vente
		// Map<Boolean, ProduitDTO> verifResult = new HashMap<Boolean,
		// ProduitDTO>();
		// if (produitStockLocal != null && produitStockLocal.getQuantite() <
		// pdto.getQuantiteProduitAvendre()) {
		// // Verifier si les autres stck ont ce produit pour le proposer à
		// // l'agent
		// List<Produit> autreStock =
		// modelDAO.getDisponibiliteProduitByQuantiteProduit(
		// produitStockLocal.getIntrant().getTypeIntrantId().getTypeIntrantId(),
		// pdto.getQuantiteProduitAvendre());
		//
		// if (autreStock == null) {
		// pdto.setMsg("le stock restant <" + produitStockLocal.getQuantite()
		// + "> est inferieur à la quantité à vendre <" +
		// pdto.getQuantiteProduitAvendre() + ">");
		// verifResult.put(false, pdto);
		//
		// return verifResult;
		// }
		//
		// for (Produit p : autreStock) {
		// if (p.getStockRef().getIdPointdeVente() != null)
		// localZone = p.getStockRef().getIdPointdeVente() + "";
		// else if (p.getStockRef().getIdPointdeCollecte() != null)
		// localZone = p.getStockRef().getIdPointdeCollecte() + "";
		//
		// autreStockDisponible += localZone + "";
		// }
		// pdto.setMsg("le stock resttant <" + produitStockLocal.getQuantite()
		// + "> est inferieur à la quantité à vendre <" +
		// pdto.getQuantiteProduitAvendre() + ">");
		// verifResult.put(false, pdto);
		// } else if (produitStockLocal != null &&
		// produitStockLocal.getQuantite() > pdto.getQuantiteProduitAvendre()) {
		// pdto.setMsg("Stock disponible en local");
		//
		// pdto.setLibelle(produitStockLocal.getIntrant().getLibelle());
		// pdto.setLibelleProduit(produitStockLocal.getIntrant().getLibelle());
		// pdto.setPrixUnitaire(
		// produitStockLocal.getTarif() != null ?
		// produitStockLocal.getTarif().getPrixNonSubventionne() : 0L);
		// pdto.setQuantiteProduitAvendre(pdto.getQuantiteProduitAvendre());
		//
		// verifResult.put(true, pdto);
		// }
		// return verifResult;
	}

	@Override
	public String getLibelleCampagneByIdProduit(Long idProduit) {

		return "test";
		/*
		 * try { Produit produitStockLocal = (Produit)
		 * modelDAO.getEntityDBById(idProduit, Produit.class); return
		 * produitStockLocal.getProgramme() != null &&
		 * produitStockLocal.getProgramme().getCampagne() != null ?
		 * produitStockLocal.getProgramme().getCampagne().getLibelle() : ""; }
		 * catch (EntityDBDAOException e) { Log.
		 * error("Une erreur est survenue lors de la recuperation de la campagne"
		 * + e.getMessage()); return ""; }
		 */
	}

	@Override
	public Long getidProgrammebyIdProduit(Long idProduit) {

		return null;
		/*
		 * try { Produit produitStockLocal = (Produit)
		 * modelDAO.getEntityDBById(idProduit, Produit.class); return
		 * produitStockLocal.getProgramme() != null ?
		 * produitStockLocal.getProgramme().getId_ca() : null; } catch
		 * (EntityDBDAOException e) { Log.
		 * error("Une erreur est survenue lors de la recuperation de la campagne"
		 * + e.getMessage()); return null; }
		 */
	}

	@Override
	public String getLibelleProgrammeByIdProduit(Long idProduit) {

		return "test";
		/*
		 * try { Produit produitStockLocal = (Produit)
		 * modelDAO.getEntityDBById(idProduit, Produit.class); return
		 * produitStockLocal.getProgramme() != null ?
		 * produitStockLocal.getProgramme().getLibelle() : ""; } catch
		 * (EntityDBDAOException e) { Log.
		 * error("Une erreur est survenue lors de la recuperation de la campagne"
		 * + e.getMessage()); return ""; }
		 */
	}

	@Override
	public boolean updateQuantiteIntrantByPaiementNature(Long idProduit, CommandeDTO infosCMD) {

		try {
			Intrant produitStockLocal = (Intrant) modelDAO.getEntityDBById(idProduit, Intrant.class);

			produitStockLocal.setQuantite(produitStockLocal.getQuantite() + infosCMD.getQuantitePaiementNAture());
			modelDAO.save(produitStockLocal);

			infosCMD.setLibelleProduitToc(produitStockLocal.getIntrant().getLibelle());
			return true;

		} catch (EntityDBDAOException e) {
			Log.error("Une erreur est survenue lors  de la mise a jour de la quantite du produit " + e.getMessage());
			return false;
		}
	}

	/**************************
	 * GESTION POINTS DE VENTE DUNE CAMPAGNE DONNE
	 * 
	 ************************************/

	@Override
	public List<PointDeVenteDTO> loadAllPointDeVenteByIdProgramme(Long idProgramme) {

		List<PointDeVente> refreshedListPV = (List<PointDeVente>) modelDAO.genericClassLoader(PointDeVente.class);

		List<PointDeVenteDTO> listPvDto = new ArrayList<PointDeVenteDTO>();

		for (PointDeVente pv : refreshedListPV) {
			PointDeVenteDTO pvdto = new PointDeVenteDTO();
			pvdto.setIdPv(pv.getId());
			pvdto.setLibelle(pv.getLibelle());

			if (pv.getGerant() != null) /* Gerant = Utilisateu */
			{
				pvdto.setIdGerant(pv.getGerant().getId());
				pvdto.setGerant(pv.getGerant().getPrenom() + "  " + pv.getGerant().getNom());

				if (pv.getGerant().getContact() != null)
					pvdto.setContactGerant(pv.getGerant().getContact().getMobile());

			}

			if (pv.getAdresse() != null) /* Adresse Point de Vente **/
			{
				pvdto.setIdAdresse(pv.getAdresse().getId());
				pvdto.setAdresse((pv.getAdresse().getLibelle()));

				if (pv.getAdresse().getRegion() != null) {
					pvdto.setIdRegion(pv.getAdresse().getRegion().getId());
					pvdto.setRegion(pv.getAdresse().getRegion().getLibelle());

					pvdto.setCodeRegion(
							pv.getAdresse().getRegion() != null ? pv.getAdresse().getRegion().getCode_region() : "");
				}

				if (pv.getAdresse().getDepartement() != null) {
					pvdto.setIdDepartement(pv.getAdresse().getDepartement().getId());
					pvdto.setDepartement(pv.getAdresse().getDepartement().getLibelle());
				}

				if (pv.getAdresse().getCommune() != null) {
					pvdto.setIdCommune(pv.getAdresse().getCommune().getId());
					pvdto.setCommune(pv.getAdresse().getCommune().getLibelle());
				}

			}

			if (pv.getStock() != null) {
				pvdto.setIdstockReference(pv.getStock().getId());
			}

			listPvDto.add(pvdto);
		}
		return listPvDto;
	}

	/**************************
	 * GESTION POINTS DE VENTE DUNE CAMPAGNE DONNE
	 * @throws EntityDBDAOException 
	 * 
	 ************************************/

	@Override
	public PointDeVenteDTO loadPointDeVenteById(Long idPv) throws EntityDBDAOException {

		PointDeVente pv = (PointDeVente) modelDAO.getEntityDBById(idPv, PointDeVente.class);

				if(pv == null)
					return null;
		

			PointDeVenteDTO pvdto = new PointDeVenteDTO();
			pvdto.setIdPv(pv.getId());
			pvdto.setLibelle(pv.getLibelle());

			if (pv.getGerant() != null) /* Gerant = Utilisateu */
			{
				pvdto.setIdGerant(pv.getGerant().getId());
				pvdto.setGerant(pv.getGerant().getPrenom() + "  " + pv.getGerant().getNom());

				if (pv.getGerant().getContact() != null)
					pvdto.setContactGerant(pv.getGerant().getContact().getMobile());

			}
			

			if (pv.getCommune() != null) /* Gerant = Utilisateu */
			{
				pvdto.setIdCommune(pv.getCommune().getId());

			}

			
			if (pv.getAdresse() != null) /* Adresse Point de Vente **/
			{
				pvdto.setIdAdresse(pv.getAdresse().getId());
				pvdto.setAdresse((pv.getAdresse().getLibelle()));

				if (pv.getAdresse().getRegion() != null) {
					pvdto.setIdRegion(pv.getAdresse().getRegion().getId());
					pvdto.setRegion(pv.getAdresse().getRegion().getLibelle());

					pvdto.setCodeRegion(
							pv.getAdresse().getRegion() != null ? pv.getAdresse().getRegion().getCode_region() : "");
				}

				if (pv.getAdresse().getDepartement() != null) {
					pvdto.setIdDepartement(pv.getAdresse().getDepartement().getId());
					pvdto.setDepartement(pv.getAdresse().getDepartement().getLibelle());
				}

				if (pv.getAdresse().getCommune() != null) {
					pvdto.setIdCommune(pv.getAdresse().getCommune().getId());
					pvdto.setCommune(pv.getAdresse().getCommune().getLibelle());
				}

			}

			if (pv.getStock() != null) {
				pvdto.setIdstockReference(pv.getStock().getId());
				pvdto.setIdStockReference(pv.getStock().getId());
			}

		return pvdto;
	}

	@Override
	public List<PointDeVenteDTO> loadAllPointDeVenteByIdProgrammeAndCodeRegion(Long idProgramme, Long codeRegion)
			throws EntityDBDAOException {

		List<PointDeVente> refreshedListPV = modelDAO.loadPointDeVenteByCodeRegionAndProgramme(idProgramme, codeRegion);

		List<PointDeVenteDTO> listPvDto = new ArrayList<PointDeVenteDTO>();

		for (PointDeVente pv : refreshedListPV) {
			PointDeVenteDTO pvdto = new PointDeVenteDTO();
			pvdto.setIdPv(pv.getId());
			pvdto.setLibelle(pv.getLibelle());

			List<Intrant> allP = modelDAO.loadStockByIdPointdeVente(pv.getStock().getStock_id());
			List<ProduitDTO> allstockDTO = new ArrayList<ProduitDTO>();

			for (Intrant p : allP) {
				ProduitDTO dtoP = new ProduitDTO();

				dtoP.setIdProduit(p.getProduit_id());
				dtoP.setLibelle(p.getIntrant().getLibelle());
				dtoP.setLibelleProgramme(p.getProgramme().getLibelle());
				dtoP.setQuantite(p.getQuantite());
				dtoP.setIdtypeProduit(p.getIntrant().getTypeIntrantId().getTypeIntrantId());
				dtoP.setIdCampagne(p.getProgramme().getCampagne().getId_ca());
				dtoP.setLibelleProgramme(p.getProgramme().getCampagne().getLibelle());

				dtoP.setIdProgramme(p.getProgramme().getId_ca());
				dtoP.setLibelleProgramme(p.getProgramme().getLibelle());

				dtoP.setPrixUnitaire(p.getTarif() != null ? p.getTarif().getPrixNonSubventionne() : 0f);

				allstockDTO.add(dtoP);
			}
			pvdto.setStockPointDeVente(allstockDTO);

			if (pv.getGerant() != null) /* Gerant = Utilisateu */
			{
				pvdto.setIdGerant(pv.getGerant().getId());
				pvdto.setGerant(pv.getGerant().getPrenom() + "  " + pv.getGerant().getNom());

				if (pv.getGerant().getContact() != null)
					pvdto.setContactGerant(pv.getGerant().getContact().getMobile());

			}

			if (pv.getAdresse() != null) /* Adresse Point de Vente **/
			{
				pvdto.setIdAdresse(pv.getAdresse().getId());
				pvdto.setAdresse((pv.getAdresse().getLibelle()));

				if (pv.getAdresse().getRegion() != null) {
					pvdto.setIdRegion(pv.getAdresse().getRegion().getId());
					pvdto.setRegion(pv.getAdresse().getRegion().getLibelle());

					pvdto.setCodeRegion(
							pv.getAdresse().getRegion() != null ? pv.getAdresse().getRegion().getCode_region() : "");
				}

				if (pv.getAdresse().getDepartement() != null) {
					pvdto.setIdDepartement(pv.getAdresse().getDepartement().getId());
					pvdto.setDepartement(pv.getAdresse().getDepartement().getLibelle());
				}

				if (pv.getAdresse().getCommune() != null) {
					pvdto.setIdCommune(pv.getAdresse().getCommune().getId());
					pvdto.setCommune(pv.getAdresse().getCommune().getLibelle());
				}

			}

			if (pv.getStock() != null) {
				pvdto.setIdstockReference(pv.getStock().getId());
			}

			listPvDto.add(pvdto);
		}
		return listPvDto;
	}

	@Override
	public List<ProduitDTO> loadAallProduitFromIdCMD(Long idCommande) throws EntityDBDAOException {
		List<CommandeProduitAssocie> listP = (List<CommandeProduitAssocie>) modelDAO
				.loadAallProduitFromIdCMD(idCommande);

		List<ProduitDTO> allLispDTO = new ArrayList<ProduitDTO>();

		for (CommandeProduitAssocie p : listP) {
			ProduitDTO dtoP = new ProduitDTO();

			dtoP.setIdProduit(p.getProduitVendu().getProduit_id());
			dtoP.setLibelle(p.getProduitVendu().getIntrant().getLibelle());
			dtoP.setQuantite(p.getQuantitedelaCommande());
			dtoP.setPrixUnitaire(p.getPrixUnitaire());
			allLispDTO.add(dtoP);
		}
		return allLispDTO;
	}

	@Override
	public List<ProduitDTO> loadAallProduitFromIdOrdre(Long idOrdre) throws EntityDBDAOException {

		@SuppressWarnings("unchecked")
		List<OrdreLivraisonProduitAssocie> listP = (List<OrdreLivraisonProduitAssocie>) modelDAO
				.loadAallProduitFromIdOrdre(idOrdre);
		List<ProduitDTO> allLispDTO = new ArrayList<ProduitDTO>();

		for (OrdreLivraisonProduitAssocie p : listP) {
			ProduitDTO dtoP = new ProduitDTO();

			dtoP.setIdOrdre(p.getId_ola());
			dtoP.setIdProduit(p.getProduitVendu().getProduit_id());
			dtoP.setLibelle(p.getProduitVendu().getIntrant().getLibelle());
			dtoP.setQuantite(p.getQuantitedelaCommande());
			dtoP.setPrixUnitaire(p.getPrixUnitaire());

			allLispDTO.add(dtoP);
		}
		return allLispDTO;
	}

	@Override
	public CommandeDTO loadCommandeInfosByIdCMD(Long idCommande) throws EntityDBDAOException {
		Commande c = (Commande) modelDAO.getEntityDBById(idCommande, Commande.class);
		if (c == null)
			return null;
		List<ProduitDTO> produitOfCMD = loadAallProduitFromIdCMD(idCommande);
		if (produitOfCMD == null)
			return null;
		CommandeDTO cmd = new CommandeDTO();
		cmd.setReferenceCMD(c.getReferenceCMD());
		cmd.setLibelleProgramme(c.getProgrammeConcerne().getLibelle());

		if (c.getPointdeVenteTraitantLaCMD() != null) {
			cmd.setPointDeVenteDepartementProvenance(c.getPointdeVenteTraitantLaCMD().getLibelle() != null
					? c.getPointdeVenteTraitantLaCMD().getLibelle() : "");
			cmd.setIdStockSortant(c.getPointdeVenteTraitantLaCMD().getStock() != null
					? c.getPointdeVenteTraitantLaCMD().getStock().getStock_id() : null);
		}

		cmd.setAuteurCommandeNom(c.getEditeurCommandePersonne() != null
				? c.getEditeurCommandePersonne().getPrenom() + " " + c.getEditeurCommandePersonne().getPrenom() : "");

		cmd.setIdCommande(c.getCmd_id());
		cmd.setReferenceCMD(c.getReferenceCMD());
		cmd.setDateLivraisonSouhaite(c.getDateLivraisonSouhaiteCMD());
		cmd.setAuteurCommandeNom(
				c.getEditeurCommandePersonne().getPrenom() + " " + c.getEditeurCommandePersonne().getNom());
		cmd.setClientNom(c.getClientNom());
		cmd.setClientTel(c.getClientTel());
		cmd.setClienAdresse(c.getClientAddresse());
		cmd.setCommuneLibelle(c.getClientCommuneDechargement());
		cmd.setMontantTotaldelaCommande(c.getMontantTotalCMD());

		// Editeur cmd : En généra c le siège
		if (c.getEditeurCommandePersonne() != null && c.getEditeurCommandePersonne().getContact() != null)
			cmd.setAuteurCommandeProvenanceTel(c.getEditeurCommandePersonne().getContact().getMobile());
		if (c.getEditeurCommandePointdeCollecte() != null)
			cmd.setAuteurCommandeProvenance(c.getEditeurCommandePointdeCollecte().getLibelle());

		cmd.setModeDePaiement(c.getPaiementCMD());

		if (c.getStatutCMD() == 0) {
			cmd.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_EN_COURS);
			cmd.setStatutCMD(ConstantPGCA.CMD_A_TRAITER);
		} else if (c.getStatutCMD() == 1) {
			cmd.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_TRAITE);
			cmd.setStatutCMD(ConstantPGCA.CMD_TRAITE);
		} else if (c.getStatutCMD() == 2) {
			cmd.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_REFUSE);
			cmd.setStatutCMD(ConstantPGCA.CMD_REFUSE);
		}

		if (c.getPaiementCMD().equals(ConstantPGCA.CMD_A_PAYER_A_LA_LIVRAISON))
			cmd.setModeDePaiement(ConstantPGCA.CMD_MODE_PAIEMENT_ALA_LIVRAISON);
		else if (c.getPaiementCMD().equals(ConstantPGCA.CMD_PAYE_AU_SIEGE))
			cmd.setModeDePaiement(ConstantPGCA.CMD_MODE_PAIEMENT_AU_SIEGE);
		else if (c.getPaiementCMD().equals(ConstantPGCA.CMD_PAYE_PAR_CREDIT))
			cmd.setModeDePaiement(ConstantPGCA.CMD_MODE_PAIEMENT_A_CREDIT);
		else if (c.getPaiementCMD().equals(ConstantPGCA.CMD_PAYE_NATURE))
			cmd.setModeDePaiement(ConstantPGCA.CMD_MODE_PAIEMENT_PAR_NATURE);

		/***
		 * CREDIT LIEE A LA COMMANDE *** CreditDTO creditLieCMD =
		 * tresorerie.loadAllCreditsFromIdCommande(cmd.getIdCommande());
		 * 
		 * if(creditLieCMD != null ) cmd.setCreditLieeAlaCMD(creditLieCMD);
		 */

		cmd.setAuteurCommandePointdeVenteZone(c.getClientCommuneDechargement());
		cmd.setListProduitsDTOtoCreate(produitOfCMD);
		return cmd;
	}

	@Override
	public CommandeDTO loadOrdreInfosById(Long idOrdre) throws EntityDBDAOException {
		OrdreLivraison c = (OrdreLivraison) modelDAO.getEntityDBById(idOrdre, OrdreLivraison.class);
		if (c == null)
			return null;

		List<ProduitDTO> produitOfCMD = loadAallProduitFromIdOrdre(idOrdre);
		if (produitOfCMD == null)
			return null;
		CommandeDTO cmd = new CommandeDTO();

		cmd.setReferenceCMD(c.getReferenceOrdre());
		cmd.setLibelleProgramme(c.getProgrammeConcerne().getLibelle());

		if (c.getPointdeVenteTraitantLaCMD() != null) {
			cmd.setPointDeVenteDepartementProvenance(c.getPointdeVenteTraitantLaCMD().getLibelle() != null
					? c.getPointdeVenteTraitantLaCMD().getLibelle() : "");
			cmd.setIdStockSortant(c.getPointdeVenteTraitantLaCMD().getStock() != null
					? c.getPointdeVenteTraitantLaCMD().getStock().getStock_id() : null);
		}

		cmd.setAuteurCommandeNom(c.getEditeurCommandePersonne() != null
				? c.getEditeurCommandePersonne().getPrenom() + " " + c.getEditeurCommandePersonne().getPrenom() : "");

		cmd.setIdCommande(c.getOrd_id());
		cmd.setReferenceCMD("ODL" + c.getOrd_id());
		cmd.setDateLivraisonSouhaite(c.getDateLivraisonSouhaiteOrdre());
		cmd.setAuteurCommandeNom(c.getEditeurCommandePersonne().getPrenom() + c.getEditeurCommandePersonne().getNom());
		cmd.setClientNom(c.getClientNom());
		cmd.setClientTel(c.getClientTel());
		cmd.setClienAdresse(c.getClientAddresse());
		cmd.setCommuneLibelle(c.getClientCommuneDechargement());
		cmd.setMontantTotaldelaCommande(c.getMontantTotalOrdre());
		cmd.setClientCNI(c.getNumeroCNIClient());

		if (c.getPointdeVenteTraitantLaCMD() != null && c.getPointdeVenteTraitantLaCMD().getGerant() != null) {
			cmd.setPointDeVenteGerantProvenance(c.getPointdeVenteTraitantLaCMD().getGerant().getPrenom() + " "
					+ c.getPointdeVenteTraitantLaCMD().getGerant().getNom());
			if (c.getPointdeVenteTraitantLaCMD().getGerant().getContact() != null)
				cmd.setPointDeVenteTelProvenance(c.getPointdeVenteTraitantLaCMD().getGerant().getContact().getMobile());
		}

		/*
		 * if (c.getStatutOrd() == 0) {
		 * cmd.setStatusLibelle(ConstantPGCA.ORD_A_TRAITER_LIBELLE);
		 * cmd.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_EN_COURS)
		 * ; cmd.setIconFontAwesome(ConstantPGCA.ICON_ENCOURS); } else if
		 * (c.getStatutOrd() == 1) {
		 * cmd.setStatusLibelle(ConstantPGCA.ORD_TRAITE_LIBELLE);
		 * cmd.setIconFontAwesome(ConstantPGCA.ICON_TRAITE);
		 * cmd.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_TRAITE);
		 * 
		 * } else if (c.getStatutOrd() == 2) {
		 * cmd.setStatusLibelle(ConstantPGCA.ORD_REFUSE_LIBELLE);
		 * cmd.setIconFontAwesome(ConstantPGCA.ICON_REFUSE);
		 * cmd.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_REFUSE);
		 * 
		 * } else if (c.getStatutOrd() == 4) {
		 * cmd.setStatusLibelle(ConstantPGCA.ORD_VALIDE_LIBELLE);
		 * cmd.setIconFontAwesome(ConstantPGCA.ICON_TRAITE);
		 * cmd.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_VALIDE);
		 * 
		 * }
		 */

		if (c.getStatutOrd() == 0) {
			cmd.setStatusLibelle(ConstantPGCA.ORD_A_TRAITER_LIBELLE);
			cmd.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_EN_COURS);
			cmd.setIconFontAwesome(ConstantPGCA.ICON_ENCOURS);
		} else if (c.getStatutOrd() == 1) {
			cmd.setStatusLibelle(ConstantPGCA.ORD_TRAITE_LIBELLE);
			cmd.setIconFontAwesome(ConstantPGCA.ICON_TRAITE);
			cmd.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_TRAITE);

		} else if (c.getStatutOrd() == 2) {
			cmd.setStatusLibelle(ConstantPGCA.ORD_REFUSE_LIBELLE);
			cmd.setIconFontAwesome(ConstantPGCA.ICON_REFUSE);
			cmd.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_REFUSE);

		} else if (c.getStatutOrd() == 4) {
			cmd.setStatusLibelle(ConstantPGCA.ORD_VALIDE_LIBELLE);
			cmd.setIconFontAwesome(ConstantPGCA.ICON_TRAITE);
			cmd.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_VALIDE);

		}

		/*
		 * if (c.getStatutOrd() == 0) {
		 * cmd.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_EN_COURS)
		 * ; cmd.setStatutCMD(ConstantPGCA.ORD_A_TRAITER_LIBELLE);
		 * cmd.setStatusLibelle(ConstantPGCA.ORD_A_TRAITER_LIBELLE);
		 * 
		 * } else if (c.getStatutOrd() == 1) {
		 * cmd.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_TRAITE);
		 * cmd.setStatutCMD(ConstantPGCA.ORD_TRAITE_LIBELLE);
		 * cmd.setStatusLibelle(ConstantPGCA.ORD_A_TRAITER_LIBELLE);
		 * cmd.setIconFontAwesome(ConstantPGCA.ICON_REFUSE);
		 * 
		 * } else if (c.getStatutOrd() == 2) {
		 * cmd.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_REFUSE);
		 * cmd.setStatutCMD(ConstantPGCA.ORD_REFUSE_LIBELLE);
		 * cmd.setStatusLibelle(ConstantPGCA.ORD_A_TRAITER_LIBELLE);
		 * 
		 * } else if (c.getStatutOrd() == 4) {
		 * cmd.setStyleClassCSSstatusCMD(ConstantPGCA.ORD_VALIDE_LIBELLE);
		 * cmd.setStatutCMD(ConstantPGCA.ICON_ENCOURS);
		 * cmd.setStatusLibelle(ConstantPGCA.ORD_VALIDE_LIBELLE);
		 * cmd.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_TRAITE);
		 * 
		 * }
		 */

		cmd.setAuteurCommandePointdeVenteZone(c.getClientCommuneDechargement());
		cmd.setListProduitsDTOtoCreate(produitOfCMD);
		return cmd;
	}

	public UtilString getUtils() {
		return utils;
	}

	public void setUtils(UtilString utils) {
		this.utils = utils;
	}

	@Override
	public List<CommandeDTO> getListOrdreEnvoyeByidPointdeCollect(Long idpc) {
		@SuppressWarnings("unchecked")
		List<OrdreLivraison> cmdList = (List<OrdreLivraison>) modelDAO.genericSqlClassLoaderById(OrdreLivraison.class,
				"pgca_OrdreLivraison", "pdc_id", idpc);

		if (cmdList == null)
			return null;

		List<CommandeDTO> cmddto = new ArrayList<CommandeDTO>();

		for (OrdreLivraison c : cmdList) {
			CommandeDTO dto = new CommandeDTO();

			dto.setIdCommande(c.getOrd_id());
			dto.setReferenceCMD(c.getReferenceOrdre());
			dto.setDateLivraisonSouhaite(c.getDateLivraisonSouhaiteOrdre());
			dto.setStatutCMD(c.getStatutOrd() + "");
			dto.setAuteurCommandeNom(
					c.getEditeurCommandePersonne().getPrenom() + c.getEditeurCommandePersonne().getNom());
			dto.setClientNom(c.getClientNom());
			dto.setClientTel(c.getClientTel());
			dto.setClienAdresse(c.getClientAddresse());
			dto.setCommuneLibelle(c.getClientCommuneDechargement());
			dto.setClientCNI(c.getNumeroCNIClient());

			if (c.getStatutOrd() == 0) {
				dto.setStatusLibelle(ConstantPGCA.ORD_A_TRAITER_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_ENCOURS);
			} else if (c.getStatutOrd() == 1) {
				dto.setStatusLibelle(ConstantPGCA.ORD_TRAITE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_TRAITE);

			} else if (c.getStatutOrd() == 2) {
				dto.setStatusLibelle(ConstantPGCA.ORD_REFUSE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_REFUSE);
			} else if (c.getStatutOrd() == 4) {
				dto.setStatusLibelle(ConstantPGCA.ORD_TRAITE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_TRAITE);
			}
			cmddto.add(dto);
		}
		return cmddto;
	}

	/****************
	 * GESTION MISES EN PLACE
	 ***************************************************************/

	@Override
	public boolean saveMiseEnplace(Long idProgramme, Long idIntrant, Date dateMmp, MiseEnplaceDTOPointDeVente data) {

		try {

			MiseEnPlaceAEffectuer quotaExistant = modelDAO.verifierExistanceQuota(data.getIdPointDeVente(), idIntrant, idProgramme);
			if (quotaExistant != null) {
				quotaExistant.setQuota(quotaExistant.getQuota() + data.getQuotaIntrantAMettreEnplace());
				quotaExistant.setReliquat(quotaExistant.getReliquat() + data.getQuotaIntrantAMettreEnplace());
				// Quick Fixed  : A changer ( Doublon Commune + point de vente ) : Possible unique si Nom Commune =  Nom Point de Vente // Commune.name => PointDeVente.name  (meme nom  )   Pointdevente => Stock.id
				PointDeVente pointdeChute = modelDAO.loadPointdeVenteByLibelle(quotaExistant.getPointdeVenteConcerne().getLibelle());
				quotaExistant.setPointDeChute(pointdeChute);
				modelDAO.save(quotaExistant);
				data.setMsgRetour("Un quota de " + quotaExistant.getProduitAmettreEnPlace().getLibelle()
						+ " existe déjà pour le programme " + quotaExistant.getProgrammeConcerne().getLibelle()
						+ ",  La quantité du quota est mise à jour avec l'ajout de "
						+ data.getQuotaIntrantAMettreEnplace() + "(t).");
				return true;
			} else {
				ProgrammeAgricol prog = (ProgrammeAgricol) modelDAO.getEntityDBById(idProgramme,
						ProgrammeAgricol.class);
				if (prog == null) return false;
				Commune pv = (Commune) modelDAO.getEntityDBById(data.getIdPointDeVente(), Commune.class);
				if (pv == null) return false;
				ReferentialIntrants refintran = (ReferentialIntrants) modelDAO.getEntityDBById(idIntrant,
						ReferentialIntrants.class);
				if (refintran == null) return false;
				MiseEnPlaceAEffectuer m = new MiseEnPlaceAEffectuer();
				java.sql.Date sqlDate = null;
				// Quick Fixed  : A changer ( Doublon Commune + point de vente ) : Possible unique si Nom Commune =  Nom Point de Vente  	// Commune.name => PointDeVente.name  (meme nom  )   Pointdevente => Stock.id
				PointDeVente pointdeChute = modelDAO.loadPointdeVenteByLibelle(pv.getLibelle());
				m.setPointDeChute(pointdeChute);
				if (dateMmp != null)
				sqlDate = new java.sql.Date(dateMmp.getTime());
				m.setDateEdition(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
				m.setDateMiseEnplaceSouhaite(sqlDate);
				m.setProgrammeConcerne(prog);
				m.setProduitAmettreEnPlace(refintran);
				m.setQuota(data.getQuotaIntrantAMettreEnplace());
				m.setReliquat(data.getQuotaIntrantAMettreEnplace());
				m.setPointdeVenteConcerne(pv);
				m.setMiseEnplace(0);
				modelDAO.save(m);
				data.setCreateOrUpdate(true);
				return true;
			}
		} catch (Exception e) {
			Log.error("Une erreur est survebeur lors de la mise en place " + e.getStackTrace());
			return false;
		}
	}

	@Override
	public boolean deleteMiseEnplace(Long idMiseEnplace) {

		try {
			MiseEnPlaceAEffectuer prog = (MiseEnPlaceAEffectuer) modelDAO.getEntityDBById(idMiseEnplace,
					ProgrammeAgricol.class);
			if (prog == null)
				return false;

			Log.info("Suppression du Quoto de mise en place : " + prog.getId() + " - " + prog.getQuota() + " - ");
			modelDAO.removeModel(prog);
			return true;

		} catch (Exception e) {

			Log.error("Une erreur est survenue lors de la suppression lors de la mise en place " + e.getStackTrace());
			return false;
		}

	}

	/****************
	 * GESTION MISES EN PLACE
	 ***************************************************************/

	public ITresorerieService getTresorerie() {
		return tresorerie;
	}

	public void setTresorerie(ITresorerieService tresorerie) {
		this.tresorerie = tresorerie;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IntrantDTO> loadCollecteByPointDeCollecteAndProg(Long idPointDeCollecte, Long idPointDeVente)
			throws EntityDBDAOException {
		List<CollecteProduits> listProduits;
		listProduits = (List<CollecteProduits>) modelDAO.genericSqlClassLoaderById(CollecteProduits.class,
				"pgca_collecteProduits", "pdc_id", idPointDeCollecte);
		if (listProduits == null)
			return null;
		List<IntrantDTO> listeTypeIntrantDTO = new ArrayList<IntrantDTO>();
		for (CollecteProduits tp : listProduits) {
			IntrantDTO idto = new IntrantDTO();
			if (tp.getAllocationConcerne() != null && tp.getAllocationConcerne().getProduitACollecter() != null) {
				idto.setIdProduit(tp.getAllocationConcerne().getProduitACollecter().getRefIntrantId());
				idto.setLibelleProduit(tp.getAllocationConcerne().getProduitACollecter().getLibelle());
				idto.setTypeProduit(tp.getAllocationConcerne().getProduitACollecter().getTypeIntrantId().getLibelle());
				idto.setLibelleProgramme(tp.getAllocationConcerne().getProgrammeConcerne().getLibelle());
				idto.setVendeur(tp.getProducteurPrenom() + " " + tp.getProducteurNom());
				idto.setProvenance(tp.getProducteurPrenom() + " " + tp.getProducteurNom());
				idto.setVendeurDepartement(tp.getProducteurVillage());
				idto.setQuantite(new Double(tp.getProducteurPoidsTotal()));

				if (tp.getProducteurPrixUnitaire() > 0 && tp.getProducteurPoidsTotal() > 0)
					idto.setTotauxGlobalVentesToutlesPV(tp.getProducteurPrixUnitaire() * tp.getProducteurPoidsTotal());
			}
			listeTypeIntrantDTO.add(idto);
		}
		return listeTypeIntrantDTO;
	}

	@Override
	public IntrantDTO loadIntrantByLibelleIntrant(String libelleIntrant) throws EntityDBDAOException {
		Intrant p = modelDAO.loadProduitFromLibelle(libelleIntrant);
		if (p != null) {
			IntrantDTO i = new IntrantDTO();
			i.setIdProduit(p.getProduit_id());
			i.setLibelleProduit(p.getIntrant().getLibelle());
			return i;
		}
		return null;
	}

	@Override
	public IntrantDTO loadIntrantByid(Long idIntrant) throws EntityDBDAOException {
		Intrant p = (Intrant) modelDAO.getEntityDBById(idIntrant, Intrant.class);
		if (p != null) {
			IntrantDTO i = new IntrantDTO();
			i.setIdProduit(p.getIntrant().getRefIntrantId());
			i.setIdtypeProduit(p.getIntrant().getTypeIntrantId().getTypeIntrantId());
			i.setLibelleProduit(p.getIntrant().getLibelle());
			return i;
		}
		return null;
	}

	@Override
	public boolean verifyExistingTypeIntrantByLibelle(String libelle) {
		return modelDAO.getTypeIntrantByLibelle(libelle);
	}

	@Override
	public boolean verifyExistingIntrantByLibelle(String libelle) {
		return modelDAO.getIntrantByLibelle(libelle);
	}

	@Override
	public boolean createTypeIntrant(IntrantTypeDTO intrantType) {
		ReferentialTypeIntrants p = new ReferentialTypeIntrants();
		try {
			p.setLibelle(intrantType.getLibelle());
			p.setUniteDeMesure(Integer.parseInt(intrantType.getUnitedeMesure()));

			modelDAO.save(p);

			return true;

		} catch (EntityDBDAOException e) {
			Log.error("Une erreur est surveneur lors de la creationde du type d'intrant" + intrantType.getLibelle());
			e.printStackTrace();
		}
		return false;
	}

	/*********** INTRANT SERVICES ***********/
	@SuppressWarnings("unchecked")
	@Override
	public List<IntrantDTO> loadReferentielIntrants() {
		List<ReferentialIntrants> listProduits;
		try {
			listProduits = (List<ReferentialIntrants>) modelDAO.genericClassLoader(ReferentialIntrants.class);
			List<IntrantDTO> listeTypeIntrantDTO = new ArrayList<IntrantDTO>();

			for (ReferentialIntrants tp : listProduits) {
				IntrantDTO idto = new IntrantDTO();
				idto.setLibelleProduit(tp.getLibelle());
				idto.setLibelleTypeProduit(tp.getTypeIntrantId().getLibelle());
				idto.setIdProduit(tp.getRefIntrantId());
				idto.setIdtypeProduit(tp.getTypeIntrantId().getTypeIntrantId());
				if (tp.getTypeIntrantId().getUniteDeMesure() == 1)
					idto.setUniteDeMesure(ConstantPGCA.UNITE_DE_MESURE_TONNE);
				else if (tp.getTypeIntrantId().getUniteDeMesure() == 2)
					idto.setUniteDeMesure(ConstantPGCA.UNITE_DE_MESURE_LITRE);
				else if (tp.getTypeIntrantId().getUniteDeMesure() == 3)
					idto.setUniteDeMesure(ConstantPGCA.UNITE_DE_MESURE_PIECE);
				else if (tp.getTypeIntrantId().getUniteDeMesure() == 2)
					idto.setUniteDeMesure(ConstantPGCA.UNITE_DE_MESURE_FLACON);
				listeTypeIntrantDTO.add(idto);
			}
			return listeTypeIntrantDTO;
		} catch (Exception e) {
			Log.error("Erreur lecteur code profil id connecté");
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IntrantDTO> loadReferentielIntrantByType(Long idProduit) throws EntityDBDAOException {
		List<ReferentialIntrants> listDept = (List<ReferentialIntrants>) modelDAO
				.loadReferentielIntrantByType(idProduit);

		if (listDept == null)
			return null;

		List<IntrantDTO> listeDepartementDTO = new ArrayList<IntrantDTO>();

		for (ReferentialIntrants p : listDept) {
			IntrantDTO cdeptdto = new IntrantDTO();

			cdeptdto.setIdProduit(p.getId()); // t3 mef wo
			cdeptdto.setLibelleProduit(p.getLibelle());

			listeDepartementDTO.add(cdeptdto);
		}
		return listeDepartementDTO;
	}

	@Override
	public boolean createIntrant(IntrantDTO selectedIntrantDTO) {
		ReferentialIntrants p = new ReferentialIntrants();
		try {
			ReferentialTypeIntrants type = (ReferentialTypeIntrants) modelDAO
					.getEntityDBById(selectedIntrantDTO.getIdtypeProduit(), ReferentialTypeIntrants.class);

			if (type == null)
				return false;

			p.setTypeIntrantId(type);
			p.setLibelle(selectedIntrantDTO.getLibelleProduit());
			p.setDescriptifIntrant(p.getDescriptifIntrant());
			p.setUniteDeMesure(selectedIntrantDTO.getUniteDeMesure());

			modelDAO.save(p);

			return true;

		} catch (EntityDBDAOException e) {
			Log.error("Une erreur est surveneur lors de la creationde du type d'intrant"
					+ selectedIntrantDTO.getLibelleProduit());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public IntrantDTO loadIntrantByIdTypeAndStock(Long idProduitType, Long idStock) throws EntityDBDAOException {
		Intrant p = modelDAO.loadIntrantByTypeAndStock(idProduitType, idStock);
		if (p != null) {
			IntrantDTO i = new IntrantDTO();
			i.setLibelleProduit(p.getIntrant().getLibelle());
			i.setIdProduit(p.getProduit_id());
			i.setQuantite(p.getQuantite());

			return i;
		} else
			return null;
	}

	@Override
	public ProduitDTO loadAProduitFromStockbyIdStockIdProduitAndIdProg(Long idStock, Long idProduit, Long idProg)
			throws EntityDBDAOException {
		Intrant tp = modelDAO.loadProduitByIdProduitIdStockIdProg(idStock, idProduit, idProg);
		if (tp == null)
			return null;

		ProduitDTO idto = new ProduitDTO();
		idto.setIdProduit(tp.getProduit_id());
		idto.setIdtypeProduit(tp.getIntrant().getTypeIntrantId().getTypeIntrantId());
		idto.setIdStockProduit(tp.getStockRef().getId());
		idto.setIdCampagne(tp.getProgramme().getId());
		idto.setLibelleProduit(tp.getIntrant().getLibelle());
		idto.setLibelleProgramme(tp.getProgramme().getLibelle());
		idto.setLibelleCampagne(tp.getProgramme().getCampagne().getLibelle());
		idto.setLibellePointdeStock(tp.getStockRef().getLibelle());
		idto.setQuantite(tp.getQuantite());
		idto.setTypeProduit(tp.getIntrant().getTypeIntrantId().getLibelle());
		idto.setProvenance(tp.getProvenance());

		idto.setPictoImages(tp.getIntrant().getTypeIntrantId().getPictoIntrant());

		return idto;
	}

	@Override
	public boolean validerOrdreDeLivraisonById(Long idCommande) {
		try {
			OrdreLivraison o = (OrdreLivraison) modelDAO.getEntityDBById(idCommande, OrdreLivraison.class);

			if (o == null)
				return false;
			o.setStatutOrd(ConstantPGCA.ORD_VALIDE);
			modelDAO.save(o);
			return true;
		} catch (EntityDBDAOException e) {
			return false;
		}
	}

	@Override
	public boolean checkExistingBL(String numeroBLManuel) throws EntityDBDAOException {
		if (modelDAO.loadMiseEnPlaceByidBl(numeroBLManuel) != null)
			return true;

		return false;
	}

	@Override
	public boolean checkExistingLV(String numeroBLManuel) throws EntityDBDAOException {
		if (modelDAO.loadMiseEnPlaceByidLV(numeroBLManuel) != null)
			return true;

		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IndicateursDashboardDTO loadAllIndicateursDashboard() throws EntityDBDAOException {

		IndicateursDashboardDTO indicateur = new IndicateursDashboardDTO();

		List<MiseEnPlaceAEffectuer> quotas = (List<MiseEnPlaceAEffectuer>) modelDAO
				.genericClassLoader(MiseEnPlaceAEffectuer.class);
		indicateur.setNbQuotas(quotas != null ? quotas.size() : 0);

		List<MiseEnPlaceEffectuer> mep = (List<MiseEnPlaceEffectuer>) modelDAO
				.genericClassLoader(MiseEnPlaceEffectuer.class);

		indicateur.setNbMiseEnPlace(mep != null ? mep.size() : 0);

		indicateur.setOlEnAttentes(modelDAO.loadOLEnAttentes() != null ? modelDAO.loadOLEnAttentes().size() : 0);
		indicateur.setOlAcceptes(modelDAO.loadOLAcceptes() != null ? modelDAO.loadOLAcceptes().size() : 0);
		indicateur.setOlTraites(modelDAO.loadOLTraites() != null ? modelDAO.loadOLTraites().size() : 0);
		indicateur.setNbLitiges(commonService.loadAllLitiges() != null ? commonService.loadAllLitiges().size() : 0);

		indicateur.setNbLitiges(commonService.loadAllLitiges() != null ? commonService.loadAllLitiges().size() : 0);

		indicateur.setNbBl(modelDAO.loadallBonDeLivraison());

		return indicateur;

	}

	@Override
	public boolean campapgneNameExist(String libelleCampagne, Long idCampagne) {
		ProgrammeAgricol tp = modelDAO.campapgneNameExist(idCampagne, libelleCampagne);

		if (tp != null)
			return true;
		return false;
	}

	@Override
	public List<IntrantDTO> loadStockResiduel() throws EntityDBDAOException {
		return modelDAO.loadStockResiduel();
	}

	@Override
	public List<IntrantDTO> loadStockResiduelByidCommune(Long idCommune) throws EntityDBDAOException {
		return modelDAO.loadStockResiduelByidCommune(idCommune);
	}

	@Override
	public List<IntrantDTO> loadStockResiduelSuperviseurAndCommune(Long idCommune, Long idPointdeCollecteSuperviseur)
			throws EntityDBDAOException {
		return modelDAO.loadStockResiduelSuperviseurAndCommune(idCommune, idPointdeCollecteSuperviseur);
	}

	@Override
	public List<IntrantDTO> loadStockResiduelByIntrant(Long idIntrant) throws EntityDBDAOException {
		return modelDAO.loadStockResiduelByIntrant(idIntrant);
	}

	@Override
	public StockResiduelPointDeVente createOrUpdateStockResiduelByPointdeVente(Long idCommune, Long idIntrant,
			Double quantite) throws EntityDBDAOException {
		return modelDAO.createOrUpdateStockResiduelByPointdeVente(idIntrant, idIntrant, quantite);
	}

	@Override
	public IntrantDTO loadStockResiduelByPointdeVente(Long idCommune, Long idIntrant) throws EntityDBDAOException {
		return modelDAO.loadStockResiduelByPointdeVente(idCommune, idIntrant);
	}

	@Override
	public List<BonDeLivraisonDTO> ladBLEnvoyesByStock(Long connectedUseridStock) throws EntityDBDAOException {

		List<BonDeLivraison> bl = modelDAO.ladBLEnvoyesByStock(connectedUseridStock);

		List<BonDeLivraisonDTO> bldto = new ArrayList<BonDeLivraisonDTO>();

		for (BonDeLivraison b : bl) {
			BonDeLivraisonDTO dto = new BonDeLivraisonDTO();

			dto.setId(b.getBl_id());
			dto.setLibelle(b.getLibelle());
			dto.setProvenanceBL(b.getProvenanceProduit());
			dto.setCamionnumero(b.getNumeroCamionId().getNumeroCamion());
			dto.setChauffeurlibelle(b.getChauffeurId().getNom() + " " + b.getChauffeurId().getPrenom());
			dto.setTransporteurlibelle(b.getTransporteurId().getLibelle());
			dto.setTransporteurid(b.getTransporteurId().getIdtransporteur());
			dto.setDateEdition(b.getDateEdition() != null ? b.getDateEdition().toString() : "");
			dto.setProgrammeLibelle(b.getProgramme().getLibelle());
			dto.setProgrammeId(b.getProgramme().getPgca_idprog());
			Stock s = (Stock) modelDAO.getEntityDBById(b.getIdStockReceptionnaire(), Stock.class);
			dto.setZoneDeReception(s != null ? s.getLibelle() : "");
			dto.setIdStockReceptionnaire(s != null ? s.getStock_id() : null);

			if (b.getBlStatut() == 0) {
				dto.setStatusLibelle("En attente de validation");
				dto.setStatus(b.getBlStatut());
				;
			} else if (b.getBlStatut() == 1) {
				dto.setStatusLibelle("Accepté");
				dto.setStatus(b.getBlStatut());
				;
			} else if (b.getBlStatut() == 2) {
				dto.setStatusLibelle("Accepter avec réserve");
				dto.setStatus(b.getBlStatut());
				;
			}
			bldto.add(dto);
		}

		return bldto;
	}

	public StockResiduelDAO getStockService() {
		return stockService;
	}

	public void setStockService(StockResiduelDAO stockService) {
		this.stockService = stockService;
	}

	// Liste des affectations (chaque supervisier est affcetué a une ou
	// plusieurs commune)

	/***
	 * 
	 * @param idUser
	 * @return
	 * @throws EntityDBDAOException
	 * 
	 *             Ici on recupere l'ensemble des intrants de chaque commune
	 *             affectée a un user (un superviseur gére plusieurs commune
	 *             donc plusieurs stodck)
	 */
	@Override
	public List<IntrantDTO> loadAllIntrantFromAffectations(Long idUser) throws EntityDBDAOException {
		return null;
	}
}
