package sn.awi.pgca.business.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jfree.util.Log;
import org.springframework.transaction.annotation.Transactional;

import sn.awi.pgca.business.dao.ModelDAO;
import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.service.ICommonService;
import sn.awi.pgca.dataMapping.DTOFactory;
import sn.awi.pgca.dataModel.Adresse;
import sn.awi.pgca.dataModel.BonDeLivraison;
import sn.awi.pgca.dataModel.Camion;
import sn.awi.pgca.dataModel.CampagneAgricole;
import sn.awi.pgca.dataModel.Chauffeur;
import sn.awi.pgca.dataModel.Commune;
import sn.awi.pgca.dataModel.Contact;
import sn.awi.pgca.dataModel.Departement;
import sn.awi.pgca.dataModel.Fournisseur;
import sn.awi.pgca.dataModel.GenericModel;
import sn.awi.pgca.dataModel.Intrant;
import sn.awi.pgca.dataModel.Litiges;
import sn.awi.pgca.dataModel.MiseEnPlaceAEffectuer;
import sn.awi.pgca.dataModel.MiseEnPlaceEffectuer;
import sn.awi.pgca.dataModel.Pays;
import sn.awi.pgca.dataModel.Personne;
import sn.awi.pgca.dataModel.PointDeCollecte;
import sn.awi.pgca.dataModel.PointDeVente;
import sn.awi.pgca.dataModel.Profil;
import sn.awi.pgca.dataModel.ProgrammeAgricol;
import sn.awi.pgca.dataModel.ReferentialIntrants;
import sn.awi.pgca.dataModel.ReferentialTypeIntrants;
import sn.awi.pgca.dataModel.Region;
import sn.awi.pgca.dataModel.Stock;
import sn.awi.pgca.dataModel.Transporteur;
import sn.awi.pgca.dataModel.TypeProduit;
import sn.awi.pgca.dataModel.Utilisateur;
import sn.awi.pgca.dataModel.Ventes;
import sn.awi.pgca.web.dto.CamionDTO;
import sn.awi.pgca.web.dto.CampagneAgricoleDTO;
import sn.awi.pgca.web.dto.CollaborateurDTO;
import sn.awi.pgca.web.dto.CoupleDTO;
import sn.awi.pgca.web.dto.EngraisDTO;
import sn.awi.pgca.web.dto.FournisseurDTO;
import sn.awi.pgca.web.dto.InfosCommunesDTO;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.IntrantTypeDTO;
import sn.awi.pgca.web.dto.LitigesDTO;
import sn.awi.pgca.web.dto.MagasinDTO;
import sn.awi.pgca.web.dto.MiseEnplaceAgregation;
import sn.awi.pgca.web.dto.MiseEnplaceDTOPointDeVente;
import sn.awi.pgca.web.dto.PersonneDTO;
import sn.awi.pgca.web.dto.PointDeCollecteDTO;
import sn.awi.pgca.web.dto.PointDeVenteDTO;
import sn.awi.pgca.web.dto.ProduitDTO;
import sn.awi.pgca.web.dto.ProgrammeAgricolDTO;
import sn.awi.pgca.web.dto.RechercheMiseEnPlaceDTO;
import sn.awi.pgca.web.dto.VentesDTO;

@Named("commonService")
public class CommonServiceImpl implements ICommonService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2102757663380602373L;

	public static final String SES_PAYS = "listePays";
	public static final String SES_PROFIL = "listeProfil";
	public static final String SES_REGION = "listeRegion";

	@Inject
	private ModelDAO modelDAO;
	@Inject
	private DTOFactory dtoFactory;
	List<PointDeVenteDTO> LazylistePointDeVente;

	@Override
	public List<CollaborateurDTO> loadAllCollaborateurDTOs() {
		try {
			List<Utilisateur> coll = modelDAO.loadAllCollaborateur();
			return dtoFactory.createlisteCollaborteurDTOs(coll);
		} catch (Exception e) {
			Log.error("Erreur recuperation collaborateur");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ProduitDTO> loadStockProduitByIdPointdeVenteMagasinier(Long idStockPointDeVente)
			throws EntityDBDAOException {
		List<Intrant> allstock = modelDAO.loadStockByIdPointdeVente(idStockPointDeVente);
		if (allstock == null)
			return null;
		List<ProduitDTO> allstockDTO = new ArrayList<ProduitDTO>();
		for (Intrant p : allstock) {
			ProduitDTO dtoP = new ProduitDTO();
			dtoP.setIdProduit(p.getProduit_id());
			dtoP.setLibelle(p.getIntrant().getLibelle());
			dtoP.setLibelleProgramme(p.getProgramme().getLibelle());
			dtoP.setQuantite(p.getQuantite());
			dtoP.setIdtypeProduit(p.getIntrant().getTypeIntrantId().getTypeIntrantId());
			dtoP.setIdCampagne(p.getProgramme().getCampagne().getId_ca());
			dtoP.setLibelleProgramme(p.getProgramme().getCampagne().getLibelle());
			
			dtoP.setIdStockProduit(p.getStockRef()  !=null ? p.getStockRef().getStock_id()  :  null);

			dtoP.setIdProgramme(p.getProgramme().getId_ca());
			dtoP.setLibelleProgramme(p.getProgramme().getLibelle());

			if (p.getTarif() != null) {
				dtoP.setDejaTarifie(true);
				dtoP.setPrixUnitaire(p.getTarif().getPrixProducteur());
			} else {
				dtoP.setDejaTarifie(false);
			}
			allstockDTO.add(dtoP);
		}

		return allstockDTO;
	}

	@Override
	public List<CollaborateurDTO> loadAllCollaborateurDTOsPersonne() {
		try {
			@SuppressWarnings("unchecked")
			List<Personne> lp = (List<Personne>) modelDAO.genericClassLoader(Personne.class);

			List<CollaborateurDTO> ldto = new ArrayList<CollaborateurDTO>();

			for (Personne p : lp) {
				CollaborateurDTO cddto = new CollaborateurDTO();

				cddto.setIdPersonne(p.getId());
				cddto.setNom(p.getPrenom() + " " + p.getNom());

				ldto.add(cddto);
			}
			return ldto;
		} catch (Exception e) {
			Log.error("Erreur recuperation collaborateur");
			e.printStackTrace();
		}
		return null;
	}

	
	@Override
	public List<ProduitDTO> loadStockProduitForOL() throws EntityDBDAOException {
		
		List<Intrant> allstock = (List<Intrant>) modelDAO.genericClassLoader(Intrant.class);
		
		if (allstock == null)
			return null;
		List<ProduitDTO> allstockDTO = new ArrayList<ProduitDTO>();
		for (Intrant p : allstock) {
			ProduitDTO dtoP = new ProduitDTO();
			dtoP.setIdProduit(p.getProduit_id());
			dtoP.setLibelle(p.getIntrant().getLibelle());
			dtoP.setLibelleProgramme(p.getProgramme().getLibelle());
			dtoP.setQuantite(p.getQuantite());
			dtoP.setIdtypeProduit(p.getIntrant().getTypeIntrantId().getTypeIntrantId());
			dtoP.setIdCampagne(p.getProgramme().getCampagne().getId_ca());
			dtoP.setLibelleProgramme(p.getProgramme().getCampagne().getLibelle());
			
			dtoP.setIdStockProduit(p.getStockRef()  !=null ? p.getStockRef().getStock_id()  :  null);

			dtoP.setIdProgramme(p.getProgramme().getId_ca());
			dtoP.setLibelleProgramme(p.getProgramme().getLibelle());

			if (p.getTarif() != null) {
				dtoP.setDejaTarifie(true);
				dtoP.setPrixUnitaire(p.getTarif().getPrixProducteur());
			} else {
				dtoP.setDejaTarifie(false);
			}
			allstockDTO.add(dtoP);
		}

		return allstockDTO;
	}
	
	
	@Override
	public List<CoupleDTO> loadPays() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				@SuppressWarnings("unchecked")
				List<CoupleDTO> lCoupleDTO = (List<CoupleDTO>) session.getAttribute(SES_PAYS);
				if (lCoupleDTO != null)
					return lCoupleDTO;
				try {
					List<Pays> lPays = modelDAO.loadPays();
					lCoupleDTO = dtoFactory.createPaysListCoupleDTO(lPays);
					session.setAttribute(SES_PAYS, lCoupleDTO);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return lCoupleDTO;
			} else {
				try {
					List<Pays> lPays = modelDAO.loadPays();
					return dtoFactory.createPaysListCoupleDTO(lPays);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public CoupleDTO getPays(String id) {
		try {
			Pays pays = (Pays) modelDAO.getEntityDBById(new Long(id), Pays.class);
			return new CoupleDTO(pays.getId().longValue(), pays.getLibelle());
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		} catch (EntityDBDAOException ede) {
			ede.printStackTrace();
		}
		return null;
	}

	@Override
	public List<CoupleDTO> loadProfil() {
		try {
			List<Profil> lProfils = modelDAO.loadProfil();
			return dtoFactory.createProfilListCoupleDTO(lProfils);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*****
	 * Recuperation liste des Point de collecte en CoupleDTO via la méthode
	 * générique createGenericCoupleDTO
	 * 
	 */
	@Override
	public List<CoupleDTO> loadPointDeCollecte() {

		try {
			List<PointDeCollecte> pointCollecte = modelDAO.loadPointdeCollecte();
			return dtoFactory.createGenericCoupleDTO(pointCollecte, "pdc_id", "libelle");
		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MiseEnplaceAgregation> loadAggregationMiseEnPlace() {

		// load aggregaation by dept and intrran
		return modelDAO.loadAgregationMiseEnPlaceByIntrantAndDepartement();

		// return modelDAO.loadAgregationMiseEnPlace();
	}

	@Override
	public List<MiseEnPlaceEffectuer> loadAllMiseEnPlaceEffectueeByDepartementAndIntrant(Long idDept, Long idIntrant,
			String dateDebut, String dateFin) throws EntityDBDAOException {
		return modelDAO.loadAllMiseEnPlaceEffectueeByDepartementAndIntrant(idDept, idIntrant, dateDebut, dateFin);
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
	 * 
	 * @throws EntityDBDAOException
	 ****/
	@Override
	public List<MiseEnPlaceAEffectuer> loadListQuotabyIdIntrant(Long idIntrant) throws EntityDBDAOException {
		return modelDAO.loadListQuotabyIdIntrant(idIntrant);
	}

	@Override
	public List<MiseEnPlaceAEffectuer> loadListQuotabyIdIntrantAndDepartement(Long idIntrant, Long idDepartement)
			throws EntityDBDAOException {
		return modelDAO.loadListQuotabyIdIntrantAndDepartement(idIntrant, idDepartement);
	}

	/*****
	 * Recuperation liste des Point de vente en CoupleDTO via la méthode
	 * générique createGenericCoupleDTO
	 * 
	 */
	@Override
	public List<CoupleDTO> loadPointDeVente() {
		try {
			List<PointDeVente> pointdeVente = modelDAO.loadPointdeVente();
			return dtoFactory.createGenericCoupleDTO(pointdeVente, "ptv_id", "tagsMagasin");
		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * @Override public List<CoupleDTO> loadPointDeVente() { List<PointDeVente>
	 * pointVente = modelDAO.loadPointdeVente(); return
	 * dtoFactory.createProfilListCoupleDTO(lProfils); }
	 */

	@Override
	public List<MiseEnplaceDTOPointDeVente> rechercherMiseEnplaceEffectueParIntrant(String dateDebut, String dateFin,
			Long idIntrant) throws EntityDBDAOException {

			RechercheMiseEnPlaceDTO criteres =  new RechercheMiseEnPlaceDTO();
			
			criteres.setDateDebut(dateDebut);
			criteres.setDateFin(dateFin);
			criteres.setIdIntrant(idIntrant);
			
		try {
			List<MiseEnPlaceEffectuer> mepeffectuees = modelDAO.rechercherMiseEnplaceEffectueParIntrant(criteres);

			List<MiseEnplaceDTOPointDeVente> listeMEP = new ArrayList<MiseEnplaceDTOPointDeVente>();
			for (MiseEnPlaceEffectuer m : mepeffectuees) {
				MiseEnplaceDTOPointDeVente nv = new MiseEnplaceDTOPointDeVente();
				nv.setLibelleIntrantAMettreEnplace(m.getMiseEnplaceConcerne().getProduitAmettreEnPlace().getLibelle());
				nv.setReliquatIntrantAMettreEnplace(m.getMiseEnplaceConcerne().getReliquat());
				nv.setQuotaIntrantAMettreEnplace(m.getMiseEnplaceConcerne().getQuota());
				nv.setQuantiteIntrantAMettreEnplace(m.getQuantiteAmettreEnplace());

				nv.setIdCamion(m.getCamion().getId());
				nv.setCamion(m.getCamion().getNumeroCamion() + "");
				nv.setChauffeur(
						m.getChauffeur().getChauffeur().getPrenom() + " " + m.getChauffeur().getChauffeur().getNom());
				nv.setTransporteur(m.getTransporteur().getLibelle());
				// nv.setFournisseur(m.getFounnisseur().getLibelle());
				nv.setFournisseur(m.getMagasinSource());
				nv.setDateMiseEnplace(m.getDateMiseEnplaceEffective() + "");
				nv.setNomPointDeVente(m.getPointdeVenteLibelle());
				nv.setDepartementPointdeVente(
						m.getMiseEnplaceConcerne().getPointdeVenteConcerne().getDepartement().getLibelle());
				// nv.setNomGerant(m.getMiseEnplaceConcerne().getPointDeVenteOfficiel().getGerant().getPrenom()
				// + " " +
				// m.getMiseEnplaceConcerne().getPointdeVenteConcerne().getGerant().getNom());

				nv.setBlMiseEnPlace(m.getBlManuel());
				nv.setLvMiseEnPlace(m.getLvManuel());
				nv.setFournisseur(m.getFounnisseur().getLibelle());

				listeMEP.add(nv);
			}
			return listeMEP;
		} catch (Exception e) {
			Log.error("Erreur recherche des mise en place" + e.getMessage());
			return null;
		}
	}

	@Override
	public CoupleDTO getProfil(String id) {
		try {
			Profil profil = (Profil) modelDAO.getEntityDBById(new Long(id), Profil.class);
			return new CoupleDTO(profil.getId().longValue(), profil.getLibelle());
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		} catch (EntityDBDAOException ede) {
			ede.printStackTrace();
		}
		return null;
	}

	public ModelDAO getModelDAO() {
		return modelDAO;
	}

	public void setModelDAO(ModelDAO modelDAO) {
		this.modelDAO = modelDAO;
	}

	public DTOFactory getDtoFactory() {
		return dtoFactory;
	}

	public void setDtoFactory(DTOFactory dtoFactory) {
		this.dtoFactory = dtoFactory;
	}

	@Override
	public List<CoupleDTO> loadRegion() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				@SuppressWarnings("unchecked")
				List<CoupleDTO> lCoupleDTO = (List<CoupleDTO>) session.getAttribute(SES_REGION);
				if (lCoupleDTO != null)
					return lCoupleDTO;
				try {
					List<Region> lRegions = modelDAO.loadRegion();
					lCoupleDTO = dtoFactory.createRegionCoupleDTO(lRegions);
					session.setAttribute(SES_REGION, lCoupleDTO);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return lCoupleDTO;
			} else {
				try {
					List<Region> lRegions = modelDAO.loadRegion();
					return dtoFactory.createRegionCoupleDTO(lRegions);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public List<CoupleDTO> loadRegionbyPays(String id) {

		try {
			List<Region> lRegions = modelDAO.loadRegionByPaysId(id);
			return dtoFactory.createRegionCoupleDTO(lRegions);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public InfosCommunesDTO getDetailsCommune(String nomCommuune) {

		try {
			return modelDAO.getDetailsCommuneFromNomCommune(nomCommuune);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/************* INTRANTS **************/
	@Override
	public List<IntrantTypeDTO> loadAllTypeIntrants() {
		@SuppressWarnings("unchecked")
		List<TypeProduit> typeProduits = (List<TypeProduit>) modelDAO.genericClassLoader(TypeProduit.class);

		List<IntrantTypeDTO> listeTypeIntrantDTO = new ArrayList<IntrantTypeDTO>();

		for (TypeProduit tp : typeProduits) {
			IntrantTypeDTO idtotmp = new IntrantTypeDTO();
			idtotmp.setId(tp.getId());
			idtotmp.setLibelle(tp.getLibelle());
			listeTypeIntrantDTO.add(idtotmp);
		}
		return listeTypeIntrantDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PointDeVenteDTO> loadAllPointDeVente(boolean refreshList) {

		List<PointDeVente> refreshedListPV = (List<PointDeVente>) modelDAO.genericClassLoader(PointDeVente.class);
		List<PointDeVenteDTO> listPvDto = new ArrayList<PointDeVenteDTO>();

		for (PointDeVente pv : refreshedListPV) {
			PointDeVenteDTO pvdto = new PointDeVenteDTO();
			pvdto.setIdPv(pv.getPtv_id());
			pvdto.setLibelle(pv.getLibelle());

			if (pv.getCommune() != null) {
				pvdto.setIdCommune(pv.getCommune().getId());
				pvdto.setCommune(pv.getCommune().getLibelle());
				pvdto.setIdDepartement(pv.getCommune().getDepartement().getId());
				pvdto.setDepartement(pv.getCommune().getDepartement().getLibelle());
				pvdto.setRegion(pv.getCommune().getDepartement().getRegion().getLibelle());
			}

			if (pv.getGerant() != null) /* Gerant = Utilisateu */
			{
				pvdto.setIdGerant(pv.getGerant().getId());
				pvdto.setGerant(pv.getGerant().getPrenom() + "  " + pv.getGerant().getNom());
				if (pv.getGerant().getContact() != null)
					pvdto.setContactGerant(pv.getGerant().getContact().getMobile());

			}

			if (pv.getStock() != null) // Stock de reference du point de vente
				pvdto.setIdstockReference(pv.getStock().getId());

			listPvDto.add(pvdto);
		}
		return listPvDto;
	}
	
	
	
	
	// magasin = pv
	@SuppressWarnings("unchecked")
	@Override
	public List<MagasinDTO> loadReferentielMagasin() {

		List<PointDeVente> refreshedListPV = (List<PointDeVente>) modelDAO.genericClassLoader(PointDeVente.class);
		List<MagasinDTO> listPvDto = new ArrayList<MagasinDTO>();

		for (PointDeVente pv : refreshedListPV) {
			MagasinDTO pvdto = new MagasinDTO();
			pvdto.setIdMagasin(pv.getPtv_id());
			pvdto.setMagasinNom(pv.getLibelle());

			pvdto.setIdCommune(pv.getCommune()  !=  null ? pv.getCommune().getId()  :  null);
			pvdto.setMagasinCommune(pv.getCommune() != null ? pv.getCommune().getLibelle()  : "");
			pvdto.setIdStockMagasin(pv.getStock() != null ? pv.getStock().getStock_id() :  null);
			
			if(pv.getGerant() !=  null)
			{
				pvdto.setMagasinGerantNom(pv.getGerant().getNom());
				pvdto.setMagasinGeraantPrenom(pv.getGerant().getPrenom());
			}
			
			if(pv.getContact() != null)
			{
				pvdto.setMagasinGerantMaill(pv.getContact().getCourriel());
				pvdto.setMagasinGerantTel(pv.getContact().getMobile());
			}
			
			
			listPvDto.add(pvdto);
		}
		return listPvDto;
	}
	
	
	
	
	// magasin = pv
	@SuppressWarnings("unchecked")
	@Override
	public List<MagasinDTO> loadReferentielMagasinByDepartement(Long idDepartement) throws EntityDBDAOException {

		List<PointDeVente> refreshedListPV = (List<PointDeVente>) modelDAO.loadAllPointDeventeOfDepartement(idDepartement);

		if(null == refreshedListPV)
			return null;
		
		List<MagasinDTO> listPvDto = new ArrayList<MagasinDTO>();

		for (PointDeVente pv : refreshedListPV) {
			MagasinDTO pvdto = new MagasinDTO();
			pvdto.setIdMagasin(pv.getPtv_id());
			pvdto.setMagasinNom(pv.getLibelle());

			pvdto.setIdCommune(pv.getCommune()  !=  null ? pv.getCommune().getId()  :  null);
			pvdto.setMagasinCommune(pv.getCommune() != null ? pv.getCommune().getLibelle()  : "");
			pvdto.setIdStockMagasin(pv.getStock() != null ? pv.getStock().getStock_id() :  null);
			
			if(pv.getGerant() !=  null)
			{
				pvdto.setMagasinGerantNom(pv.getGerant().getNom());
				pvdto.setMagasinGeraantPrenom(pv.getGerant().getPrenom());
			}
			
			if(pv.getContact() != null)
			{
				pvdto.setMagasinGerantMaill(pv.getContact().getCourriel());
				pvdto.setMagasinGerantTel(pv.getContact().getMobile());
			}
			
			
			listPvDto.add(pvdto);
		}
		return listPvDto;
	}
	


	@SuppressWarnings("unchecked")
	@Override
	public List<FournisseurDTO> loadAllFornisseurs() {

		List<Fournisseur> refreshedListPV = (List<Fournisseur>) modelDAO.genericClassLoader(Fournisseur.class);
		List<FournisseurDTO> listPvDto = new ArrayList<FournisseurDTO>();

		for (Fournisseur pv : refreshedListPV) {
			FournisseurDTO pvdto = new FournisseurDTO();
			pvdto.setIdFounisseur(new Long(pv.getId()));
			pvdto.setLibelle(pv.getLibelle());
			pvdto.setRepresentantCivil(pv.getRepresentantCivil());
			pvdto.setRepresentantTelephone(pv.getRepresentantTelephone());

			listPvDto.add(pvdto);
		}
		return listPvDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String loadAllFornisseursForAutoComplete() throws EntityDBDAOException {

		List<Fournisseur> refreshedListPV = (List<Fournisseur>) modelDAO.genericClassLoader(Fournisseur.class);

		StringBuffer liste = new StringBuffer();

		for (Fournisseur f : refreshedListPV)
			liste.append(f.getLibelle() + "!");

		return liste.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer loadAllFornisseursForAutoCompleteSize() throws EntityDBDAOException {
		return modelDAO.loadAllFornisseursForAutoCompleteSize();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String loadAllIntrantforAutoCoplete() throws EntityDBDAOException {

		List<ReferentialIntrants> refreshedListPV = (List<ReferentialIntrants>) modelDAO
				.genericClassLoader(ReferentialIntrants.class);

		StringBuffer liste = new StringBuffer();

		for (ReferentialIntrants ref : refreshedListPV)
			liste.append(ref.getLibelle() + "!");

		return liste.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String loadAllProgrammeforAutoCoplete() throws EntityDBDAOException {

		List<ProgrammeAgricol> refreshedListPV = (List<ProgrammeAgricol>) modelDAO
				.genericClassLoader(ProgrammeAgricol.class);

		StringBuffer liste = new StringBuffer();

		for (ProgrammeAgricol ref : refreshedListPV)
			liste.append(ref.getLibelle() + "!");

		return liste.toString();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String loadAllDepartementAutoCoplete() throws EntityDBDAOException {

		List<Departement> refreshedListPV = (List<Departement>) modelDAO
				.genericClassLoader(Departement.class);

		StringBuffer liste = new StringBuffer();

		for (Departement ref : refreshedListPV)
			liste.append(ref.getLibelle() + "!");

		return liste.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String loadAllTransporteurforAutoCoplete() throws EntityDBDAOException {

		List<Transporteur> refreshedListPV = (List<Transporteur>) modelDAO
				.genericClassLoader(Transporteur.class);

		StringBuffer liste = new StringBuffer();

		for (Transporteur ref : refreshedListPV)
			liste.append(ref.getLibelle() + "!");

		return liste.toString();
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<PointDeVenteDTO> loadAllPointDeVenteByCommuneId(Long communeId) throws EntityDBDAOException {
		PointDeVenteDTO pvdto;
		List<PointDeVente> refreshedListPV = (List<PointDeVente>) modelDAO.loadPointDeVenteByidCommune(communeId);

		List<PointDeVenteDTO> listPvDto = new ArrayList<PointDeVenteDTO>();

		for (PointDeVente pv : refreshedListPV) {

			pvdto = new PointDeVenteDTO();
			pvdto.setIdPv(pv.getPtv_id());
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
				if (pv.getAdresse()
						.getRegion() != null) /* Region Point de Vente **/
				{
					pvdto.setIdRegion(pv.getAdresse().getRegion().getId());
					pvdto.setRegion(pv.getAdresse().getRegion().getLibelle());
				}
				if (pv.getAdresse().getDepartement() != null) {
					pvdto.setIdDepartement(pv.getAdresse().getDepartement().getId());
					pvdto.setDepartement(pv.getAdresse().getDepartement().getLibelle());
				}
				if (pv.getAdresse()
						.getCommune() != null) /* Commune Point de Vente **/
				{
					pvdto.setIdCommune(pv.getAdresse().getCommune().getId());
					pvdto.setCommune(pv.getAdresse().getCommune().getLibelle());
				}
			}
			//
			// if (pv.getStock() != null) // Stock de reference du point de
			// vente
			// pvdto.setIdstockReference(pv.getStock().getId());

			listPvDto.add(pvdto);
		}
		return listPvDto;
	}

	public boolean checkExistingPointDeVenteByName(String nomCommune, Long communeId) throws EntityDBDAOException {
		return modelDAO.checkExistingPointDeVenteByName(nomCommune, communeId);
	}

	@Override
	public List<PointDeCollecteDTO> loadPointdeCollecteDTOs() {

		@SuppressWarnings("unchecked")
		List<PointDeCollecte> lpc = (List<PointDeCollecte>) modelDAO.genericClassLoader(PointDeCollecte.class);

		List<PointDeCollecteDTO> lpvdto = new ArrayList<PointDeCollecteDTO>();

		for (PointDeCollecte pc : lpc) {
			PointDeCollecteDTO pvdto = new PointDeCollecteDTO();
			pvdto.setIdPointDeCollecte(pc.getPdc_id());
			pvdto.setLibelle(pc.getLibelle());

			if (pc.getGerant() != null) {
				pvdto.setContactGerant(
						pc.getGerant().getContact() != null ? pc.getGerant().getContact().getMobile() : "");
				pvdto.setGerant(
						pc.getGerant() != null ? pc.getGerant().getPrenom() + " " + pc.getGerant().getNom() : "");

			}

			if (pc.getAdresse() != null) {
				pvdto.setCommune(pc.getAdresse().getCommune() != null ? pc.getAdresse().getCommune().getLibelle() : "");
				pvdto.setDepartement(
						pc.getAdresse().getDepartement() != null ? pc.getAdresse().getDepartement().getLibelle() : "");
			}

			lpvdto.add(pvdto);
		}
		return lpvdto;
	}

	@Override
	public boolean createIntrant(IntrantDTO idto) {
		Intrant produit = new Intrant();
		ReferentialIntrants intrant = null;
		Stock stock = null;

		try {
			intrant = (ReferentialIntrants) modelDAO.getEntityDBById(idto.getIdtypeProduit(),
					ReferentialIntrants.class);
			if (intrant == null) {
				Log.error("Impossible de recuperer le type de produit");
				return false;
			}
		} catch (EntityDBDAOException e) {
			Log.error("Impossible de recuperer le type de produit");
		}

		try {
			stock = (Stock) modelDAO.getEntityDBById(idto.getIdtypeProduit(), Stock.class);
			if (stock == null) {
				Log.error("Impossible de recuperer le stock de reference du produit");
				return false;
			}
		} catch (EntityDBDAOException e) {
			Log.error("Impossible de recuperer  le stock  du produit");
		}

		try {
			produit.setStockRef(stock);
			// produit.setIdTypeProduit(tp);
			// produit.setLibelleProduit(idto.getLibelleProduit());
			produit.setProvenance(idto.getProvenance());
			produit.setIntrant(intrant);
			produit.setQuantite(idto.getQuantite());
			modelDAO.save(produit);
			Log.info("Produit de type" + intrant.getLibelle() + "enregistré avec succes");
			return true;

		} catch (Exception e) {
			Log.error("Erreur Persistance Produit");
			return false;
		}

	}

	@Override
	public List<CoupleDTO> loadAllStock() {
		@SuppressWarnings("unchecked")
		List<Stock> listStock = (List<Stock>) modelDAO.genericClassLoader(Stock.class);
		List<CoupleDTO> ldto = new ArrayList<CoupleDTO>();

		for (Stock s : listStock) {
			CoupleDTO cdto = new CoupleDTO(s.getId(), s.getLibelle());
			ldto.add(cdto);
		}
		return ldto;
	}

	/******************************** CREATION ENTITY MODEL ***************/

	/****** POint de Vente ****/

	@Override
	public boolean createPointdeVente(PointDeVenteDTO pv) {
		PointDeVente pvToCreate = new PointDeVente();
		try {
			Log.info("------ >  Création d'un point de vente" + pv.getLibelle());
			pvToCreate.setGerant((Personne) modelDAO.getEntityDBById(pv.getIdGerant(), Personne.class));

			// Addresse
			Adresse adr = new Adresse();
			Stock stockReference = new Stock();
			stockReference.setLibelle("Stock du point de vente " + pv.getLibelle());

			Commune c = (Commune) modelDAO.getEntityDBById(pv.getIdCommune(), Commune.class);
			adr.setLibelle(pv.getLibelle());
			adr.setRegion((Region) modelDAO.getEntityDBById(pv.getIdRegion(), Region.class));
			adr.setDepartement((Departement) modelDAO.getEntityDBById(pv.getIdDepartement(), Departement.class));
			adr.setCommune(c);

			pvToCreate.setLibelle(pv.getLibelle().trim());
			pvToCreate.setCommune(c);

			modelDAO.save(adr);
			modelDAO.save(stockReference);

			pvToCreate.setAdresse(adr);
			pvToCreate.setStock(stockReference);

			modelDAO.save(pvToCreate);
			Log.info("------> Création du point de Vente reussie ");
			pv.setLibelle("Point de vente de " + c.getLibelle());
			return true;
		} catch (EntityDBDAOException e) {
			Log.info("Erreur Création du point de vente");
			e.printStackTrace();
		}
		return false;
	}

	/****** Fournisseur Modele ****/

	@Override
	public boolean createPointdeVenteCommune(Long idDept, String commune ) {
		try {

			Departement dept = (Departement) modelDAO.getEntityDBById(idDept, Departement.class);
			
			if(dept  == null)
				return false;
			
			Commune communeSpecificSedab = new Commune();
			communeSpecificSedab.setLibelle(commune.toUpperCase());
			communeSpecificSedab.setCode("XXX");
			communeSpecificSedab.setDepartement(dept);
			modelDAO.save(communeSpecificSedab);
			
			//FIXME créer point de vente associe 
			Stock stock = new Stock();
			stock.setCode("XXX");
			stock.setCommunePointDevente(communeSpecificSedab);
			stock.setLibelle(commune);
			modelDAO.save(stock);
			
			PointDeVente pvassocie = new PointDeVente();
			pvassocie.setLibelle(communeSpecificSedab.getLibelle());
			pvassocie.setCode("XXXX");
			pvassocie.setCommune(communeSpecificSedab);
			pvassocie.setStock(stock);
			modelDAO.save(pvassocie);
			
			return true;
		} catch (EntityDBDAOException e) {
			Log.info("Erreur Création du point de vente");
			e.printStackTrace();
		}
		return false;
	}

	
	
	

	
	
	@Override
	public boolean createFournisseur(FournisseurDTO f) {
		Fournisseur fournisseurTOCreate = new Fournisseur();
		try {

			// if( f.getIdPays() != null && f.getIdPays() != -1 )
			// {
			// // Addresse
			// Adresse adr = new Adresse();
			// adr.setLibelle("Adresse fournisseur " + f.getLibelle());
			// adr.setRegion((Region) modelDAO.getEntityDBById(f.getIdRegion(),
			// Region.class));
			// adr.setDepartement((Departement)
			// modelDAO.getEntityDBById(f.getIdDepartement(),
			// Departement.class));
			// adr.setCommune( (Commune)
			// modelDAO.getEntityDBById(f.getIdCommune(), Commune.class));
			// modelDAO.save(adr);
			// fournisseurTOCreate.setAdresse(adr);
			// }
			//
			fournisseurTOCreate.setLibelle(f.getLibelle());
			fournisseurTOCreate.setRepresentantCivil(f.getRepresentantCivil());
			fournisseurTOCreate.setRepresentantTelephone(f.getRepresentantTelephone());

			modelDAO.save(fournisseurTOCreate);
			return true;
		} catch (EntityDBDAOException e) {
			Log.info("Erreur Création du point de vente");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean createPointdeCollecte(PointDeVenteDTO pv) {
		PointDeCollecte pvToCreate = new PointDeCollecte();
		try {
			Log.info("------ >  Création d'un point de vente" + pv.getLibelle());
			pvToCreate.setGerant((Personne) modelDAO.getEntityDBById(pv.getIdGerant(), Personne.class));

			// Addresse
			Adresse adr = new Adresse();
			Stock stockReference = new Stock();
			stockReference.setLibelle("Stock du point de collecte " + pv.getLibelle());

			Commune c = (Commune) modelDAO.getEntityDBById(pv.getIdCommune(), Commune.class);
			adr.setLibelle("Point de collecte de " + c.getLibelle());
			adr.setRegion((Region) modelDAO.getEntityDBById(pv.getIdRegion(), Region.class));
			adr.setDepartement((Departement) modelDAO.getEntityDBById(pv.getIdDepartement(), Departement.class));
			adr.setCommune(c);

			pvToCreate.setLibelle("Point de collecte de " + pv.getLibelle());

			modelDAO.save(adr);
			modelDAO.save(stockReference);

			pvToCreate.setAdresse(adr);
			pvToCreate.setStock(stockReference);

			modelDAO.save(pvToCreate);
			Log.info("------> Création du point de Vente reussie ");
			pv.setLibelle("Point de vente de " + c.getLibelle());
			return true;
		} catch (EntityDBDAOException e) {
			Log.info("Erreur Création du point de vente");
			e.printStackTrace();
		}
		return false;
	}

	/**** Magasin */

	@Override
	public boolean createMagasin(MagasinDTO dto) throws EntityDBDAOException {
		try {

			Contact contactGerant = new Contact();
			contactGerant.setMobile(dto.getMagasinGerantTel());
			modelDAO.save(contactGerant);
			
			Personne gerant = new Personne();
			gerant.setNom(dto.getMagasinNom());
			gerant.setPrenom(dto.getMagasinGeraantPrenom());
			gerant.setCivilite('1');
			gerant.setContact(contactGerant);

			modelDAO.save(gerant);

			

			Stock stockReference = new Stock();
			stockReference.setLibelle("Stock du magsin " + dto.getMagasinNom());
			modelDAO.save(stockReference);

			Commune c = (Commune) modelDAO.getEntityDBById(dto.getIdCommune(), Commune.class);
			Adresse adr  = new Adresse();
			adr.setLibelle("Point magasin  " + dto.getMagasinNom());
			adr.setRegion(c.getDepartement().getRegion());
			adr.setDepartement(c.getDepartement());
			adr.setCommune(c);
			modelDAO.save(adr);



			PointDeVente mgasin = new PointDeVente();

			mgasin.setLibelle(dto.getMagasinNom());
			mgasin.setCommune((Commune) modelDAO.getEntityDBById(dto.getIdCommune(), Commune.class));
			mgasin.setContact(contactGerant);
			mgasin.setGerant(gerant);
			mgasin.setStock(stockReference);
			mgasin.setAdresse(adr);
			mgasin.setDescriptif(dto.getMagasinDescription());
			mgasin.setStockageMax(dto.getMagasinStockage());
			modelDAO.save(mgasin);
			return true;
		} catch (EntityDBDAOException e) {
			Log.error(" Erreur Technique survenue" + e.getMessage());
		}
		return false;
	}

	
	
	
	
	/********************** LIST ENTITY MODEL *************************/

	/*** Engrais *****/

	@Override
	public List<EngraisDTO> loadAllEngraisDTO() {
		@SuppressWarnings("unchecked")
		List<Intrant> listP = (List<Intrant>) modelDAO.genericClassLoader(Intrant.class);
		List<EngraisDTO> listdtoEngrais = new ArrayList<EngraisDTO>();

		for (Intrant p : listP) {
			EngraisDTO engDTO = new EngraisDTO();

			engDTO.setIdEngrais(p.getId());
			engDTO.setLibelleProduit(p.getIntrant().getLibelle());
			engDTO.setQuantite(p.getQuantite());
			engDTO.setProvenanceProduit(p.getProvenance());
			engDTO.setLibelleProgramme(p.getProgramme().getLibelle());
			engDTO.setLibelleCampagne(p.getProgramme().getCampagne().getLibelle());

			if (p.getIntrant() != null) {
				engDTO.setTypeEngraislibelle(p.getIntrant().getTypeIntrantId().getLibelle());
				engDTO.setIdTypeEngrais(p.getIntrant().getTypeIntrantId().getTypeIntrantId());
			}

			if (p.getStockRef() != null) {
				engDTO.setNomPointStock(p.getStockRef().getLibelle());
				engDTO.setIdPointStock(p.getStockRef().getId());
			}

			listdtoEngrais.add(engDTO);
		}
		return listdtoEngrais;
	}

	/**** Liste Campagne ***/
	@Override
	public List<CampagneAgricoleDTO> loadAllCampagneAgricole() {
		@SuppressWarnings("unchecked")
		List<CampagneAgricole> listCA = (List<CampagneAgricole>) modelDAO.genericClassLoader(CampagneAgricole.class);
		List<CampagneAgricoleDTO> listdCampagne = new ArrayList<CampagneAgricoleDTO>();

		for (CampagneAgricole ca : listCA) {
			CampagneAgricoleDTO caDTO = new CampagneAgricoleDTO();
			caDTO.setIdCampagne(ca.getId());
			caDTO.setLibelleCampagne(ca.getLibelle());
			// caDTO.setDateFermeture(ca.getDateFermeture());
			// caDTO.setDateOuverture(ca.getDateOuverture());

			if (ca.getStatut() == 1)
				caDTO.setStatutCampagne(
						"<span class='badge'><i class=' fa fa-spinner' aria-hidden='true'></i> En cours</span>");
			else if (ca.getStatut() == 2)
				caDTO.setStatutCampagne(
						"<span class='badge'><i class=' fa fa-key' aria-hidden='true'></i> Cloturé</span>");

			/*
			 * if (ca.isCampagneParDefaut()) caDTO.
			 * setStatutCampagne("<span class='badge'><i class=' fa fa-home' aria-hidden='true'></i> Par default</span>"
			 * );
			 */

			listdCampagne.add(caDTO);
		}
		return listdCampagne;
	}

	/********************** DELETE ENTITY MODEL *************************/

	/*** Engrais *****/
	@Override
	public boolean deleteEntityModele(Long idEntity, Class<?> c) {
		try {
			Log.info("Suppression Entite " + c.getClass().getName());
			Intrant engraisProduit = (Intrant) modelDAO.getEntityDBById(idEntity, c);
			modelDAO.removeModel(engraisProduit);
			Log.info("Suppression Entite OK" + c.getClass().getName());
		} catch (Exception e) {
			Log.error("Suppression Entite KO" + e.getStackTrace());
			return false;
		}
		return false;
	}

	@Override
	public boolean deletePointdeVente(PointDeVenteDTO pv) {
		try {
			Log.info("----> Suppression Entite Point de Vente" + pv.getLibelle());
			PointDeVente p = (PointDeVente) modelDAO.getEntityDBById(pv.getIdPv(), PointDeVente.class);
			modelDAO.removeModel(p);
			Log.info("---- > Suppression du Stock orphelin du point de vente" + pv.getLibelle());
			Stock stockOrphelin = (Stock) modelDAO.getEntityDBById(pv.getIdstockReference(), Stock.class);
			modelDAO.removeModel(stockOrphelin);

			Log.info("Suppression Entite PV OK" + pv.getLibelle());
		} catch (Exception e) {
			Log.error("Suppression Entite KO" + e.getStackTrace());
			return false;
		}
		return false;
	}

	/******************************** UPDATE ENTITY MODEL ***************/

	/*** Engrais *****/
	@Override
	public boolean updateProduit(ProduitDTO pdo) {
		try {
			Log.info("Tentative de modification du produit " + pdo.getLibelle());
			Intrant produitFromBase = (Intrant) modelDAO.getEntityDBById(pdo.getIdProduit(), Intrant.class);

			// produitFromBase.setLibelleProduit(pdo.getLibelle());
			produitFromBase.setQuantite(pdo.getQuantite());
			produitFromBase.setStockRef((Stock) modelDAO.getEntityDBById(pdo.getIdStockProduit(), Stock.class));
			// produitFromBase.setIdTypeProduit((ReferentialTypeIntrants)
			// modelDAO.getEntityDBById(pdo.getIdtypeProduit(),
			// ReferentialTypeIntrants.class));

			modelDAO.save(produitFromBase);
			modelDAO.synchroniseWithDB(produitFromBase);
			Log.info("Modification Produit OK");
			return true;
		} catch (Exception e) {
			Log.error("Suppression Entite KO" + e.getStackTrace());
			return false;
		}
	}

	/**** Point de Vente ***/

	@Override
	public boolean updatePointdeVente(PointDeVenteDTO pointDeVente) {
		try {
			Log.info("Tentative de modification du point de vente " + pointDeVente.getLibelle());
			PointDeVente pv = (PointDeVente) modelDAO.getEntityDBById(pointDeVente.getIdPv(), PointDeVente.class);

			if (pv == null)
				return false;

			pv.setLibelle(pointDeVente.getLibelle());
			pv.setGerant((Personne) modelDAO.getEntityDBById(pointDeVente.getIdGerant(), Personne.class));
			pv.setContact(pv.getGerant().getContact());

			Adresse adrToUpdate = (Adresse) modelDAO.getEntityDBById(pointDeVente.getIdAdresse(), Adresse.class);
			adrToUpdate.setLibelle(pointDeVente.getLibelleAdresse());
			adrToUpdate.setDepartement(
					(Departement) modelDAO.getEntityDBById(pointDeVente.getIdDepartement(), Departement.class));
			adrToUpdate.setCommune((Commune) modelDAO.getEntityDBById(pointDeVente.getIdCommune(), Commune.class));
			modelDAO.save(adrToUpdate);

			pv.setAdresse(adrToUpdate);
			modelDAO.save(pv);
			// modelDAO.synchroniseWithDB(produitFromBase);
			Log.info("Modification Point de Vente  OK 42");
			return true;
		} catch (Exception e) {
			Log.error("Erreur Modification point de Vente  KO 42" + e.getStackTrace());
			return false;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public PointDeVenteDTO loadPointDeVenteById(Long idPv) {

		try {
			PointDeVenteDTO pvDTO = new PointDeVenteDTO();
			PointDeVente pv = null;
			//PointDeVente pv = ((PointDeVente) modelDAO.getEntityDBById(idPv, PointDeVente.class));
			List<PointDeVente> listpv = (List<PointDeVente>) modelDAO.genericSqlClassLoaderById(PointDeVente.class, "pgca_PointDeVente",
					"ptv_id", idPv);
			
			if(null != listpv  && listpv.size() > 0)
				pv = listpv.get(0);
			
			if (pv != null) {
				pvDTO.setIdPv(pv.getPtv_id());
				// Stock
				pvDTO.setIdstockReference(pv.getStock() != null ? pv.getStock().getStock_id() : 0L);
				pvDTO.setLibelle(pv.getLibelle());
				pvDTO.setCommune(pv.getCommune() !=  null ?  pv.getCommune().getLibelle() : "Non renseigné");
				pvDTO.setDepartement(pv.getCommune() !=  null ?  pv.getCommune().getDepartement().getLibelle() : "Non renseigné");


				if (pv.getAdresse() != null) {
					pvDTO.setAdresse(pv.getAdresse().getLibelle());

					if (pv.getAdresse().getRegion() != null) {
						pvDTO.setIdRegion(pv.getAdresse().getRegion().getId());
						pvDTO.setRegion(pv.getAdresse().getRegion().getLibelle());
					}

					if (pv.getAdresse().getCommune() != null) {
						pvDTO.setIdCommune(pv.getAdresse().getCommune().getId());
						pvDTO.setCommune(pv.getAdresse().getCommune().getLibelle());
					}

					if (pv.getAdresse().getDepartement() != null) {
						pvDTO.setIdDepartement(pv.getAdresse().getDepartement().getId());
						pvDTO.setDepartement(pv.getAdresse().getDepartement().getLibelle());
					}
				}
				if (pv.getGerant() != null) {
					pvDTO.setGerant(pv.getGerant().getPrenom() + " " + pv.getGerant().getNom());
				}
			}
			return pvDTO;

		} catch (Exception e) {
			Log.error("Erreur de recupération du point de vente " + e.getMessage());
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CoupleDTO> loadAllDepartement() {
		List<Departement> listDept = (List<Departement>) modelDAO.genericClassLoader(Departement.class);
		if (listDept == null) {
			Log.error("La liste e des departements est null ");
			return null;
		}
		List<CoupleDTO> listeDepartementDTO = new ArrayList<CoupleDTO>();
		for (Departement dept : listDept) {
			CoupleDTO cdeptdto = new CoupleDTO(dept.getId(), dept.getLibelle());
			cdeptdto.setClefValeur(dept.getId() + ":" + dept.getLibelle());
			;
			listeDepartementDTO.add(cdeptdto);
		}
		return listeDepartementDTO;
	}

	@Override
	public List<CoupleDTO> loadAllCommun() {
		@SuppressWarnings("unchecked")
		List<Commune> listDept = (List<Commune>) modelDAO.genericClassLoader(Commune.class);
		if (listDept == null) {
			Log.error("La liste  des commune est null ");
			return null;
		}
		List<CoupleDTO> listeDepartementDTO = new ArrayList<CoupleDTO>();
		for (Commune dept : listDept) {
			CoupleDTO cdeptdto = new CoupleDTO(dept.getId(), dept.getLibelle());
			cdeptdto.setClefValeur(dept.getId() + ":" + dept.getLibelle());
			;
			listeDepartementDTO.add(cdeptdto);
		}
		return listeDepartementDTO;
	}

	@Override
	public String loadAllCommunAndDepartement() throws EntityDBDAOException {
		@SuppressWarnings("unchecked")
		List<Commune> listDept = (List<Commune>) modelDAO.genericClassLoader(Commune.class);
		if (listDept == null) {
			Log.error("La liste  des commune est null ");
			return null;
		}
		StringBuffer allcommunesOfSenegal = new StringBuffer();

		for (Commune c : listDept) {
			List<Commune> CommuneHomonyme = modelDAO.loadAllCommuneHomonyme(c.getLibelle());

			if (CommuneHomonyme != null & CommuneHomonyme.size() > 1)
				allcommunesOfSenegal.append(c.getLibelle() + " (" + c.getDepartement().getLibelle() + ")!");
			else
				allcommunesOfSenegal.append(c.getLibelle() + "!");

		}

		return allcommunesOfSenegal.toString().replace("'", " ");
	}

	@Override
	public List<CoupleDTO> loadAllDepartementOfRegion(Long idRegion) {
		@SuppressWarnings("unchecked")
		List<Departement> listDept = (List<Departement>) modelDAO.genericSqlClassLoaderById(Departement.class,
				"departement", "regn_id", idRegion);
		if (listDept == null) {
			Log.error("La liste  des departements  est null ");
			return null;
		}
		List<CoupleDTO> listeDepartementDTO = new ArrayList<CoupleDTO>();

		for (Departement dept : listDept) {
			CoupleDTO cdeptdto = new CoupleDTO(dept.getId(), dept.getLibelle());
			cdeptdto.setClefValeur(dept.getId() + ":" + dept.getLibelle());
			;
			listeDepartementDTO.add(cdeptdto);
		}
		return listeDepartementDTO;
	}

	@Override
	public List<CoupleDTO> loadAllCamionByIdTransporteur(Long idtransporteur) {
		@SuppressWarnings("unchecked")
		List<Camion> listDept = (List<Camion>) modelDAO.genericSqlClassLoaderById(Camion.class, "pgca_camion",
				"idtransporteur", idtransporteur);
		if (listDept == null) {
			Log.error("La liste  des transporteur   est null ");
			return null;
		}
		List<CoupleDTO> listeDepartementDTO = new ArrayList<CoupleDTO>();

		for (Camion c : listDept) {
			CoupleDTO cdeptdto = new CoupleDTO(c.getId(), c.getNumeroCamion());
			listeDepartementDTO.add(cdeptdto);
		}
		return listeDepartementDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CoupleDTO> loadAllChauffeurByIdTransporteur(Long idCamion) {

		List<Chauffeur> listDept = (List<Chauffeur>) modelDAO.genericSqlClassLoaderById(Chauffeur.class,
				"pgca_chauffeur", "transporteur_idtransporteur", idCamion);
		if (listDept == null) {
			Log.error("La liste  des chauffeurs   est null ");
			return null;
		}
		List<CoupleDTO> listeDepartementDTO = new ArrayList<CoupleDTO>();

		for (Chauffeur c : listDept) {
			CoupleDTO cdeptdto = new CoupleDTO(c.getId(), c.getLibelle());
			listeDepartementDTO.add(cdeptdto);
		}
		return listeDepartementDTO;
	}

	@Override
	public List<CoupleDTO> loadAllCommunOfdepartement(Long idDepartement) {
		@SuppressWarnings("unchecked")
		List<Commune> listDept = (List<Commune>) modelDAO.genericSqlClassLoaderById(Commune.class, "commune",
				"departement_id", idDepartement);
		if (listDept == null) {
			Log.error("La liste  des communes  est null ");
			return null;
		}
		List<CoupleDTO> listeDepartementDTO = new ArrayList<CoupleDTO>();

		for (Commune dept : listDept) {
			CoupleDTO cdeptdto = new CoupleDTO(dept.getId(), dept.getLibelle());
			cdeptdto.setClefValeur(dept.getId() + ":" + dept.getLibelle());
			;
			listeDepartementDTO.add(cdeptdto);
		}
		return listeDepartementDTO;
	}

	@Override
	public List<CoupleDTO> loadAllPointdeVentesOfDepartemnt(Long idDepartement) {
		@SuppressWarnings("unchecked")
		List<PointDeVente> lpv;
		List<CoupleDTO> listeDepartementDTO = null;

		try {
			lpv = (List<PointDeVente>) modelDAO.loadAllPointDeventeOfDepartement(idDepartement);
			if (lpv == null)
				return null;
			listeDepartementDTO = new ArrayList<CoupleDTO>();
			
			for (PointDeVente pv : lpv) {
				CoupleDTO cdeptdto = new CoupleDTO(pv.getId(), pv.getLibelle());
				cdeptdto.setClefValeur(pv.getId() + ":" + pv.getLibelle());
				listeDepartementDTO.add(cdeptdto);
			}
			
		} catch (EntityDBDAOException e) {e.printStackTrace();}	

		return listeDepartementDTO;
	}
	
	@Override
	public List<CoupleDTO> loadCamionsBychauffeur(Long idChauffeur) {
		@SuppressWarnings("unchecked")
		List<Camion> listDept = (List<Camion>) modelDAO.genericClassLoader(Camion.class);
		if (listDept == null) {
			Log.error("La liste  des transpoteurs est null ");
			return null;
		}
		List<CoupleDTO> listeDepartementDTO = new ArrayList<CoupleDTO>();
		for (Camion c : listDept) {
			CoupleDTO cdeptdto = new CoupleDTO(c.getId(), c.getNumeroCamion());
			cdeptdto.setClefValeur(c.getId() + ":" + c.getNumeroCamion());
			listeDepartementDTO.add(cdeptdto);
		}
		return listeDepartementDTO;
	}

	@Override
	public List<CoupleDTO> loadchauffeurByTranporteur(Long idTransporteur) {
		@SuppressWarnings("unchecked")
		List<Chauffeur> listDept;
		try {
			listDept = (List<Chauffeur>) modelDAO.getChauffeurBytransporteur(idTransporteur);
			if (listDept == null) {
				Log.error("La liste  des chauffeurs est null ");
				return null;
			}
			List<CoupleDTO> listeDepartementDTO = new ArrayList<CoupleDTO>();

			for (Chauffeur dept : listDept) {
				CoupleDTO cdeptdto = new CoupleDTO(dept.getId(), dept.getLibelle());
				cdeptdto.setClefValeur(dept.getId() + ":" + dept.getLibelle());
				;
				listeDepartementDTO.add(cdeptdto);
			}
			return listeDepartementDTO;
		} catch (EntityDBDAOException e) {
			Log.error("Erreur recuperation liste des chauffeurs");
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public List<CoupleDTO> loadAllCamion() {
		@SuppressWarnings("unchecked")
		List<Camion> listDept = (List<Camion>) modelDAO.genericClassLoader(Camion.class);
		if (listDept == null) {
			Log.error("La liste  des camions est null ");
			return null;
		}
		List<CoupleDTO> listeDepartementDTO = new ArrayList<CoupleDTO>();
		for (Camion c : listDept) {
			CoupleDTO cdeptdto = new CoupleDTO(c.getId(), c.getNumeroCamion());
			cdeptdto.setClefValeur(c.getId() + ":" + c.getNumeroCamion());
			listeDepartementDTO.add(cdeptdto);
		}
		return listeDepartementDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CoupleDTO> loadAllBLOfSelectedProgramme(Long idProgramme) {
		List<BonDeLivraison> listDept = (List<BonDeLivraison>) modelDAO.genericSqlClassLoaderById(BonDeLivraison.class,
				"pgca_bonDeLivraison", "pgca_idprog", idProgramme);
		if (listDept == null)
			return null;

		List<CoupleDTO> listeDepartementDTO = new ArrayList<CoupleDTO>();

		for (BonDeLivraison b : listDept) {
			CoupleDTO cdeptdto = new CoupleDTO(b.getBl_id(), b.getReferenceBL());

			listeDepartementDTO.add(cdeptdto);
		}
		return listeDepartementDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CamionDTO> loadAllCamionDTO() {

		// return null;

		List<Camion> listDept = (List<Camion>) modelDAO.genericClassLoader(Camion.class);
		if (listDept == null) {
			Log.error("La liste  des camions est null ");
			return null;
		}

		List<CamionDTO> listCamions = new ArrayList<CamionDTO>();
		for (Camion c : listDept) {
			CamionDTO cdto = new CamionDTO();

			cdto.setIdCamion(c.getId());
			cdto.setImatCamion(c.getNumeroCamion());
			cdto.setCapacite(c.getCapaciteMax());
			cdto.setNomChauffeur(c.getChauffeur() != null && c.getChauffeur().getChauffeur() != null
					? c.getChauffeur().getChauffeur().getPrenom() + " " + c.getChauffeur().getChauffeur().getNom()
					: "Non renseigné");
			cdto.setIdChauffeur(c.getChauffeur() != null ? c.getChauffeur().getChauffeur().getId() : null);
			cdto.setContactChauffeur(c.getChauffeur() != null && c.getChauffeur().getChauffeur() != null
					&& c.getChauffeur().getChauffeur().getContact() != null
							? c.getChauffeur().getChauffeur().getContact().getMobile() : "Non renseigné");

			cdto.setIdTransporteur(c.getTransporteur() != null ? c.getTransporteur().getIdtransporteur() : null);
			cdto.setNomTransporteur(c.getTransporteur() != null ? c.getTransporteur().getLibelle() : "Non renseigné ");
			cdto.setContactTransporteur(c.getTransporteur() != null && c.getTransporteur().getContact() != null
					? c.getTransporteur().getContact().getMobile() : "Non renseigné");
			cdto.setAdresseTranporteur(c.getTransporteur() != null && c.getTransporteur().getAdresse() != null
					? c.getTransporteur().getAdresse().getLibelle() : "Non renseigné");

			listCamions.add(cdto);
		}
		return listCamions;
	}

	@Override
	public List<PersonneDTO> loadAllChauffeur() throws EntityDBDAOException {
		@SuppressWarnings("unchecked")
		List<Personne> lp = modelDAO.loadAllChauffeur();
		if (lp == null) {
			Log.error("La liste  des chauffeurs  est null ");
			return null;
		}
		List<PersonneDTO> listeDepartementDTO = new ArrayList<PersonneDTO>();

		for (Personne p : lp) {
			PersonneDTO pdto = new PersonneDTO();
			pdto.setId(p.getId());
			pdto.setNom(p.getNom());
			pdto.setPrenom(p.getPrenom());

			listeDepartementDTO.add(pdto);
		}
		return listeDepartementDTO;
	}

	@Override
	public GenericModel getEntityModelById(Class<?> modele, Long idEntitity) throws EntityDBDAOException {
		return modelDAO.getEntityDBById(idEntitity, modele);
	}

	@Override
	public List<CoupleDTO> loadAllTransporteur() {
		@SuppressWarnings("unchecked")
		List<Transporteur> listDept = (List<Transporteur>) modelDAO.genericClassLoader(Transporteur.class);
		if (listDept == null) {
			Log.error("La liste  des transpoteurs est null ");
			return null;
		}
		List<CoupleDTO> ltransp = new ArrayList<CoupleDTO>();
		for (Transporteur c : listDept) {
			CoupleDTO cdeptdto = new CoupleDTO(c.getIdtransporteur(), c.getLibelle());

			ltransp.add(cdeptdto);
		}
		return ltransp;
	}

	@Override
	public CoupleDTO loadTransnporteurbyId(Long transporteurid) throws EntityDBDAOException {

		Transporteur t = (Transporteur) modelDAO.getEntityDBById(transporteurid, Transporteur.class);

		if (t != null)
			return new CoupleDTO(t.getIdtransporteur(), t.getLibelle());
		return null;
	}

	@Override
	public CoupleDTO loadCamionById(Long camionid) throws EntityDBDAOException {
		Camion c = (Camion) modelDAO.getEntityDBById(camionid, Camion.class);

		if (c != null)
			return new CoupleDTO(c.getId(), c.getNumeroCamion());
		return null;
	}

	@Override
	public PersonneDTO loadChauffeurByIdChauffeur(Long idChauffeur) throws EntityDBDAOException {
		Personne p = (Personne) modelDAO.getEntityDBById(idChauffeur, Personne.class);

		if (p != null) {
			PersonneDTO pdto = new PersonneDTO();

			pdto.setId(p.getId());
			pdto.setNom(p.getNom());
			pdto.setPrenom(p.getNom());

			return pdto;
		}
		return null;
	}

	@Override
	public List<VentesDTO> loadAllVentesForManager() throws EntityDBDAOException {
		List<Ventes> lv = (List<Ventes>) modelDAO.genericClassLoader(Ventes.class);

		if (lv == null)
			return null;

		List<VentesDTO> lvdto = new ArrayList<VentesDTO>();

		for (Ventes v : lv) {
			VentesDTO dto = new VentesDTO();

			dto.setVentes_id(v.getId());
			dto.setDateVente(v.getDateDeVente());
			dto.setMontantVente(v.getMontantVente());
			String nom = (modelDAO.getEntityDBById(v.getVendeur().getId(), Personne.class)) != null
					? v.getVendeur().getPrenom() + " " + v.getVendeur().getNom() : "";
			dto.setVendeurLibelle(nom);
			dto.setZoneVente(v.getZoneVente());
			dto.setClient(v.getClient());

			lvdto.add(dto);
		}

		return lvdto;
	}

	@Override
	public List<CoupleDTO> loadAllProgramme() throws EntityDBDAOException {
		@SuppressWarnings("unchecked")
		List<ProgrammeAgricol> lp = (List<ProgrammeAgricol>) modelDAO.genericClassLoader(ProgrammeAgricol.class);
		List<CoupleDTO> ldto = new ArrayList<CoupleDTO>();

		for (ProgrammeAgricol p : lp) {
			CoupleDTO cddto = new CoupleDTO(p.getPgca_idprog(), p.getLibelle());
			ldto.add(cddto);
		}
		return ldto;
	}

	@Override
	public List<ProgrammeAgricolDTO> loadAllProgrammeDTO() throws EntityDBDAOException {
		@SuppressWarnings("unchecked")
		List<ProgrammeAgricol> lp = (List<ProgrammeAgricol>) modelDAO.genericClassLoader(ProgrammeAgricol.class);
		List<ProgrammeAgricolDTO> ldto = new ArrayList<ProgrammeAgricolDTO>();

		for (ProgrammeAgricol p : lp) {
			ProgrammeAgricolDTO cddto = new ProgrammeAgricolDTO();

			cddto.setProgrammelibelle(p.getLibelle());
			cddto.setProgrammeId(p.getPgca_idprog());
			cddto.setDateOuverture(p.getDateOuverture());
			cddto.setDateFermeture(p.getDateFermeture());
			cddto.setStatut(p.getStatut());
			cddto.setCampagnelibelle(p.getCampagne().getLibelle());
			cddto.setCmpagneId(p.getCampagne().getId_ca());
			ldto.add(cddto);

		}
		return ldto;
	}

	@Override
	public List<CoupleDTO> loadAllACampagne() throws EntityDBDAOException {
		@SuppressWarnings("unchecked")
		List<CampagneAgricole> lp = (List<CampagneAgricole>) modelDAO.genericClassLoader(CampagneAgricole.class);
		List<CoupleDTO> ldto = new ArrayList<CoupleDTO>();

		for (CampagneAgricole p : lp) {
			CoupleDTO cddto = new CoupleDTO(p.getId_ca(), p.getLibelle());
			ldto.add(cddto);
		}
		return ldto;
	}

	@Override
	public List<CoupleDTO> loadAllProgramme(int statut) throws EntityDBDAOException {
		List<ProgrammeAgricol> lispP = (List<ProgrammeAgricol>) modelDAO.loadProgrammeByStatut(statut);
		List<CoupleDTO> ldto = new ArrayList<CoupleDTO>();

		for (ProgrammeAgricol p : lispP) {
			CoupleDTO cddto = new CoupleDTO(p.getPgca_idprog(), p.getLibelle());
			ldto.add(cddto);
		}
		return ldto;

	}

	/**** MIS EN PLACE ***/

	@SuppressWarnings("unchecked")
	@Override
	public List<MiseEnPlaceAEffectuer> loadAllMiseEnPlaceByCommuneId(Long communeId) throws EntityDBDAOException {

		return modelDAO.loadMiseEnPlaceByidCommune(communeId);
	}

	/****** Donnees Statics pour auto Completion ********/

	// Liste des produits
	@SuppressWarnings("unchecked")
	@Override
	public String loadAllProductInJsonFormat(int maxRecords) {
		List<Intrant> lp = (List<Intrant>) modelDAO.genericClassLoader(Intrant.class);

		String allProduit = "";
		// JSONArray allPVinJsonFormat = new JSONArray();

		for (Intrant pv : lp) {
			// JSONObject j = new JSONObject();

			allProduit += pv.getIntrant().getLibelle() + ":";
			// j.put("title", pv.getLibelleProduit());

			// allPVinJsonFormat.put(j);
		}
		// return allPVinJsonFormat.toString();

		return allProduit;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String loadCachedData(String listOfCamionFromDBInJSON, String listOfChauffeurFromDBInJSON,
			String listOfTransporteurFromDBInJSON) {

		List<Camion> listCamions = (List<Camion>) modelDAO.genericClassLoader(Camion.class);
		List<Chauffeur> listChauffeurs = (List<Chauffeur>) modelDAO.genericClassLoader(Chauffeur.class);
		List<Transporteur> listTransporteurs = (List<Transporteur>) modelDAO.genericClassLoader(Transporteur.class);

		String tmp = "";

		for (Camion pv : listCamions) {
			tmp += pv.getNumeroCamion() + ":"; // + pv.getId();
		}
		listOfCamionFromDBInJSON = tmp;

		tmp += "@";
		for (Chauffeur c : listChauffeurs) {
			// tmp += pv.getChauffeur().getPrenom() + " " +
			// pv.getChauffeur().getNom() + ":" ;//+ ":" + pv.getId();
			tmp += c.getLibelle() + ":";
		}
		listOfChauffeurFromDBInJSON = tmp;

		tmp += "@";
		for (Transporteur pv : listTransporteurs) {
			tmp += pv.getLibelle() + ":";// + pv.getId();
		}
		listOfTransporteurFromDBInJSON = tmp;

		return tmp.trim();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CoupleDTO> loadAllProduitByIdTypeProduit(Long idProduit) throws EntityDBDAOException {
		List<Intrant> listDept = (List<Intrant>) modelDAO.genericSqlClassLoaderById(Intrant.class, "pgca_intrant",
				"typeProduit_id", idProduit);
		if (listDept == null)
			return null;
		List<CoupleDTO> listeDepartementDTO = new ArrayList<CoupleDTO>();

		for (Intrant p : listDept) {
			CoupleDTO cdeptdto = new CoupleDTO(p.getId(), p.getIntrant().getLibelle());
			listeDepartementDTO.add(cdeptdto);
		}
		return listeDepartementDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonneDTO> loadUtilisateurFromPointDeCollecte(Long idPointDeCollecte) throws EntityDBDAOException {
		List<Utilisateur> listU = (List<Utilisateur>) modelDAO.genericSqlClassLoaderById(Utilisateur.class,
				"user_utilisateur", "pdc_id", idPointDeCollecte);
		if (listU == null)
			return null;
		List<PersonneDTO> listeCollab = new ArrayList<PersonneDTO>();

		for (Utilisateur u : listU) {
			PersonneDTO p = new PersonneDTO();

			p.setIdUtilisateur(u.getId());
			p.setIdPersonne(u.getPersonne() != null ? u.getPersonne().getId() : null);
			p.setNom(u.getPersonne() != null ? u.getPersonne().getNom() : "");
			p.setPrenom(u.getPersonne() != null ? u.getPersonne().getPrenom() : "");

			listeCollab.add(p);
		}
		return listeCollab;
	}

	/*** Utils commun ***/

	@Override
	public void showMessage(Severity severity, String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		FacesMessage msg = new FacesMessage(severity, "", message);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IntrantTypeDTO> loadReferentielTypeIntrant() throws EntityDBDAOException {
		List<ReferentialTypeIntrants> lv = (List<ReferentialTypeIntrants>) modelDAO
				.genericClassLoader(ReferentialTypeIntrants.class);

		if (lv == null)
			return null;

		List<IntrantTypeDTO> lvdto = new ArrayList<IntrantTypeDTO>();

		for (ReferentialTypeIntrants v : lv) {
			IntrantTypeDTO dto = new IntrantTypeDTO();

			dto.setId(v.getTypeIntrantId());
			dto.setLibelle(v.getLibelle());
			dto.setPicto(v.getPictoIntrant());

			if (v.getUniteDeMesure() == 1)
				dto.setUnitedeMesure("Tonnage");
			else if (v.getUniteDeMesure() == 2)
				dto.setUnitedeMesure("Litre");
			else if (v.getUniteDeMesure() == 3)
				dto.setUnitedeMesure("Unité");
			else if (v.getUniteDeMesure() == 4)
				dto.setUnitedeMesure("Flacon");

			lvdto.add(dto);
		}

		return lvdto;
	}

	@Override
	public Intrant verifierStockRestantAupresFournisseur(Long idIntrantAMettreEnplace, Long fournisseurId) {
		try {
			return modelDAO.verifierStockRestantAupresFournisseur(idIntrantAMettreEnplace, fournisseurId);
		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// Apres chaque mis en place on diminue le stock restant
	@Override
	public ProduitDTO diminuerStockFournissuerApresMEP(Long idIntrantAMettreEnplace, Long fournisseurId,
			Double quantiteAdeduire) {

		ProduitDTO result = new ProduitDTO();

		try {
			Intrant p = modelDAO.verifierStockRestantAupresFournisseur(idIntrantAMettreEnplace, fournisseurId);

			if (p != null) {
				p.setQuantite(p.getQuantite() - quantiteAdeduire);

				result.setQuantite(p.getQuantite());
				result.setDeductionSuccess(true);
				modelDAO.save(p);
				return result;
			}
		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	// Recherche des fournisseurs qui ont un produit spécique
	@Override
	public List<FournisseurDTO> loadFournisseurByProduit(Long idProduit) {
		try {
			return modelDAO.loadFournisseurByProduit(idProduit);
		} catch (EntityDBDAOException e) {
			Log.error("Erreur de lecture des fournisseurs " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<LitigesDTO> loadAllLitiges() {

		List<Litiges> litiges = (List<Litiges>) modelDAO.genericClassLoader(Litiges.class);
		if (litiges == null) {
			Log.error("La liste  des camions est null ");
			return null;
		}

		List<LitigesDTO> ldto = new ArrayList<LitigesDTO>();

		for (Litiges l : litiges) {
			LitigesDTO tmp = new LitigesDTO();

			tmp.setChauffeur(l.getChauffeur());
			tmp.setDetailsLitige(l.getDetailsLitige());
			tmp.setIdLitiges(l.getIdLitiges());
			tmp.setNombreDesacs(l.getNombreDesacs());
			tmp.setQuantiteManquante(l.getQuantiteTotalLige());
			tmp.setQuantiteTotalLige(l.getQuantiteTotalLige());
			tmp.setReceptionnaireAgent(l.getReceptionnaireAgent());
			tmp.setReceptionnaireMagsin(l.getReceptionnaireMagsin());
			tmp.setRefenceBL(l.getBlReference() + "");

			ldto.add(tmp);
		}
		return ldto;
	}

	@Override
	public ReferentialIntrants loadIntrantByName(String intrantName) throws EntityDBDAOException {
		return modelDAO.loadIntrantByName(intrantName);
	}

	@Override
	public Long loadIntrantIdByName(String intrantName) throws EntityDBDAOException {
		ReferentialIntrants i = modelDAO.loadIntrantByName(intrantName);
		return i != null ? i.getId() : null;
	}

	@Override
	public Fournisseur loadFournisseursByName(String intrantName) throws EntityDBDAOException {
		return modelDAO.loadIFournisseursByName(intrantName);
	}

	@Override
	public Long loadFournisseuridByName(String intrantName) throws EntityDBDAOException {
		Fournisseur f = modelDAO.loadIFournisseursByName(intrantName);
		return f != null ? f.getId() : null;
	}

	@Override
	public Long loadIProgrammeIdByName(String intrantName) throws EntityDBDAOException {
		ProgrammeAgricol p = modelDAO.loadIProgrammeByName(intrantName);
		return p != null ? p.getId() : null;

	}

	@Override
	public Long loadITransporeurByName(String intrantName) throws EntityDBDAOException {
		Transporteur p = modelDAO.loadTransporteurByName(intrantName);
		return p != null ? p.getIdtransporteur() : null;

	}
	

	@Override
	public Long loadIDepartementByName(String intrantName) throws EntityDBDAOException {
		Departement p = modelDAO.loadIDepartementByName(intrantName);
		return p != null ? p.getId() : null;

	}
	
	@Override
	public Long loadIPoitDeVenteIdByName(String intrantName) throws EntityDBDAOException {
		return modelDAO.loadIPoitDeVenteIdByName(intrantName);

	}

	@Override
	public boolean updateQuotaMEP(MiseEnplaceDTOPointDeVente m) throws EntityDBDAOException {

		if (m == null)
			return false;

		MiseEnPlaceAEffectuer existingMep = (MiseEnPlaceAEffectuer) modelDAO.getEntityDBById(m.getIdMiseEnPlace(),
				MiseEnPlaceAEffectuer.class);

		if (existingMep == null)
			return false;
		
//		if( null != existingMep.getStockResiduel()   && m.getQuantiteIntrantAMettreEnplace() < existingMep.getStockResiduel())
//			{
//				m.setMsgRetour("La quantité du quota à mettre en place (" + m.getQuantiteIntrantAMettreEnplace() + " (T) "  
//						+ "ne peut pas être inférieure à la quantité déjà mise en place qui est de (" + existingMep.getStockResiduel() + ")");
//				return false;
//			}
		
		/*Long idCommune = modelDAO
				.loadIPoitDeVenteIdByName(m.getCommuneCertifie() + " (" + m.getDepartementPointdeVente() + ")");

		ReferentialIntrants intrant = modelDAO.loadIntrantByName(m.getLibelleIntrantAMettreEnplace());
		Commune com = (Commune) modelDAO.getEntityDBById(idCommune, Commune.class);
		ProgrammeAgricol prog = modelDAO.loadIProgrammeByName(m.getNomProgramme());

		if (intrant == null || com == null || prog == null)
			return false; */

		
		if(existingMep.getQuota() < m.getQuotaIntrantAMettreEnplace())
			{
				double dif =  m.getQuotaIntrantAMettreEnplace() - existingMep.getQuota();
				existingMep.setQuota(m.getQuotaIntrantAMettreEnplace());
				existingMep.setReliquat(existingMep.getReliquat() + dif);
			}
		else
			{
				double dif =    existingMep.getQuota() - m.getQuotaIntrantAMettreEnplace();
				existingMep.setQuota(m.getQuotaIntrantAMettreEnplace());
				existingMep.setReliquat(existingMep.getReliquat() + dif);	
			}
			//existingMep.setStockResiduel( m.getQuotaIntrantAMettreEnplace() );

		

		Log.info("La mise en place " + existingMep.getId() + "est modifié avec nouvlles données :    "
				+ existingMep.getQuota() + " -> " + m.getQuantiteIntrantAMettreEnplace()
				+ existingMep.getProduitAmettreEnPlace() + " -> " + m.getIdIntrantAMettreEnplace()
				+ existingMep.getProgrammeConcerne() + " -> " + m.getNomProgramme());

		modelDAO.save(existingMep);
		return true;
	}

	@Override
	public InfosCommunesDTO getDetailsCommuneById(Long idCommune) {
		 return   modelDAO.getDetailsCommuneFromIdCommune(idCommune);
	}


	public List<CoupleDTO> loadAffectationsByIdUser(Long idUser) throws EntityDBDAOException {		
		return modelDAO.loadAllIntrantFromAffectations(idUser);
	}

	@Override
	public List<PointDeVente> loadAllIntrantFromAffectations(Long id) throws EntityDBDAOException {
		return modelDAO.loadAllPointDeventeOfUser(id);
	}

}
