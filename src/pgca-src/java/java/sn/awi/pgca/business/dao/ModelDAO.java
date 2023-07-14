package sn.awi.pgca.business.dao;

import java.util.Date;
import java.util.List;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.exception.UtilisateurException;
import sn.awi.pgca.dataModel.BonDeLivraison;
import sn.awi.pgca.dataModel.Camion;
import sn.awi.pgca.dataModel.CampagneAgricole;
import sn.awi.pgca.dataModel.Chauffeur;
import sn.awi.pgca.dataModel.Commande;
import sn.awi.pgca.dataModel.CommandeProduitAssocie;
import sn.awi.pgca.dataModel.Commune;
import sn.awi.pgca.dataModel.Credit;
import sn.awi.pgca.dataModel.Departement;
import sn.awi.pgca.dataModel.Fournisseur;
import sn.awi.pgca.dataModel.GenericModel;
import sn.awi.pgca.dataModel.Intrant;
import sn.awi.pgca.dataModel.MiseEnPlaceAEffectuer;
import sn.awi.pgca.dataModel.MiseEnPlaceEffectuer;
import sn.awi.pgca.dataModel.OrdreLivraison;
import sn.awi.pgca.dataModel.OrdreLivraisonProduitAssocie;
import sn.awi.pgca.dataModel.ParametreGlobaux;
import sn.awi.pgca.dataModel.Pays;
import sn.awi.pgca.dataModel.Personne;
import sn.awi.pgca.dataModel.PointDeCollecte;
import sn.awi.pgca.dataModel.PointDeVente;
import sn.awi.pgca.dataModel.Profil;
import sn.awi.pgca.dataModel.ProgrammeAgricol;
import sn.awi.pgca.dataModel.ReferentialIntrants;
import sn.awi.pgca.dataModel.Region;
import sn.awi.pgca.dataModel.Stock;
import sn.awi.pgca.dataModel.Subvention;
import sn.awi.pgca.dataModel.Transporteur;
import sn.awi.pgca.dataModel.Utilisateur;
import sn.awi.pgca.web.dto.CoupleDTO;
import sn.awi.pgca.web.dto.FournisseurDTO;
import sn.awi.pgca.web.dto.InfosCommunesDTO;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.MiseEnplaceAgregation;
import sn.awi.pgca.web.dto.RechercheMiseEnPlaceDTO;

public interface ModelDAO extends GenericDAO , StockResiduelDAO {

	public List<Utilisateur> loadAllCollaborateur() throws EntityDBDAOException;
	public List<Commune> loadAllCommune() throws EntityDBDAOException;

	public List<PointDeCollecte> loadPointdeCollecte() throws EntityDBDAOException;
	public List<CampagneAgricole> lireCampagneParAnne(Date debut,  Date fin) throws EntityDBDAOException;
	public void synchroniseWithDB(GenericModel e);
	public Utilisateur getUtilisateurByCodeAndPassword(String codeUtilisateur, String motDePasse, String idProfil) throws EntityDBDAOException;
	public Utilisateur getUtilisateurbyCode(String code) throws UtilisateurException;
	public List<Utilisateur> loadUtilisateur() throws EntityDBDAOException;
	public Profil getProfilbyCode(String code) throws EntityDBDAOException;
	public List<Profil> loadProfil() throws EntityDBDAOException;
	public List<Pays> loadPays() throws EntityDBDAOException;
	public List<PointDeCollecte> loadEntiteJuridique() throws EntityDBDAOException;
	public Utilisateur getUtilisateurByCodeAndPassword(String codeUtilisateur, String motDePasse);
	public List<Region> loadRegion()throws EntityDBDAOException;
	public List<Region> loadRegionByPaysId(String id);
	public List<ParametreGlobaux> loadParametreGlobaux(String idPays) throws EntityDBDAOException;
	public List<ParametreGlobaux> loadParametreGlobaux() throws EntityDBDAOException;
	public List<Chauffeur> getChauffeurBytransporteur(Long idTransporteur) throws EntityDBDAOException;
	
	
	/* Magasinier */
	public List<Intrant> loadStockByIdPointdeVente(Long idPv) throws EntityDBDAOException;
	public List<Personne> loadAllChauffeur() throws EntityDBDAOException;
	public Personne loadChauffeurByIdChauffeur(Long idChauffeur) throws EntityDBDAOException;
	public List<BonDeLivraison> loadABLByIdStock(Long idStock) throws EntityDBDAOException;
	// Recuperer un type de produit a partir d'un stock donee,  
	public Intrant loadTypeOfSpecificProduitOfSpecificStock(Long stock_id, Long idTypeProduit);
	// retourne le nom du departement et de la region from nom commune
	public InfosCommunesDTO getDetailsCommuneFromNomCommune(String nomCommuune);
	// Verifie la disponibilite de la quatite d'un sur tout les point de stock
	public List<Intrant> getDisponibiliteProduitByQuantiteProduit(Long idTypeProduit, float quantiteAvendre);
	/***  POINTE DE VENTE ****/
	public List<PointDeVente> loadPointdeVente() throws EntityDBDAOException;
	public PointDeVente loadPointdeVenteByLibelle(String nomeCommune) throws EntityDBDAOException;;

	public List<PointDeVente> loadPointDeVenteByCodeRegionAndProgramme(Long idProg, Long idRegion)
			throws EntityDBDAOException;
	public List<PointDeVente> loadPointDeVenteByidCommune(Long idCommune)throws EntityDBDAOException;
	
	// verifier doublon point de vente
	boolean checkExistingPointDeVenteByName(String libelle, Long idCommune) throws EntityDBDAOException;


	/***  COMMANDE ****/
	List<ProgrammeAgricol> loadProgrammeByStatut(int statut) throws EntityDBDAOException;
	public List<CommandeProduitAssocie> loadAallProduitFromIdCMD(Long idCommande) throws EntityDBDAOException;
	public List<Commande> loadCommandeByIdPointdeVenteAndProgId(Long pv ,  Long idProgramme) throws EntityDBDAOException;
	List<Commande> loadCommandeEnAttenteDeValidationByIdPointdeVenteAndProgId(Long pvId, Long idProgramme, int status)
			throws EntityDBDAOException;
	public boolean accepterCommande(Long idCommande);
	public Credit loadCreditByIdCMD(Long columCriteria);


	/***  COMMON ****/
	public Commune loadCommuneFromLibelleCommune(String nomCommuune);
	public Departement loadDepartementFromLibelleDepartement(String nomDepartement);
	
	/***  INTRANT  : Produit  = INtran 
	 * @throws EntityDBDAOException ****/
	public Intrant loadProduitFromLibelle(String libelleProduit) throws EntityDBDAOException;
	public boolean getTypeIntrantByLibelle(String libelle); // eviter deux type de meme nom
	public List<ReferentialIntrants> loadReferentielIntrantByType(Long idProduit);
	boolean getIntrantByLibelle(String libelle);
	Intrant loadIntrantByTypeAndStock(Long idTypeIntrant, Long IdStock);
	// Lecture  specifique d'un produit d'un stock specifique pour un programme specifque
	public Intrant loadProduitByIdProduitIdStockIdProg(Long idStock, Long idProduit, Long idProg);
	
	/***** ORDRE DE LIVRAISON ***/
	// lecture des produits d'un ordre de livraion
	List<OrdreLivraisonProduitAssocie> loadAallProduitFromIdOrdre(Long idOrdre) throws EntityDBDAOException;
	public boolean validerOrdreDeLivraison(Long idCommande) throws EntityDBDAOException;
	public int loadOLenAttenteDeValidation(Long idProgramme);
	// lecture bl en attente de validation pour les points de vente
	List<OrdreLivraison> loadOLenAttenteDeTraitementByPointDeVente(Long idPointdeVente);
	List<OrdreLivraison> rechercheForManager(Long idProgramme, Long idPointdeVente, int Status, String nomClient);
	List<OrdreLivraison> loadOLValidesDeTraitementByPointDeVente(Long idPointdeVente); /** OL valide **/
	
	/***** SUBVENTION  ***/
	List<Subvention> loadAllSubvention() throws EntityDBDAOException;
	
	/***** MISE EN PLACE   ***/
	List<MiseEnPlaceAEffectuer> loadMiseEnPlaceByidCommune(Long idCommune) throws EntityDBDAOException;
	MiseEnPlaceEffectuer loadMiseEnPlaceByidBl(String bl) throws EntityDBDAOException;
	MiseEnPlaceEffectuer loadMiseEnPlaceByidLV(String lv) throws EntityDBDAOException;
	List<MiseEnPlaceAEffectuer> loadMiseEnPlaceAeffectuerByidPointdeVente(Long idPv ,  Long Categorie, Long idIntrant) throws EntityDBDAOException;
    List<MiseEnPlaceAEffectuer> loadListQuotabyIdIntrant(Long idIntrant) throws EntityDBDAOException;

    // Balayage des mise en place effectuee sur un departement pour un intrant donné par période 
	List<MiseEnPlaceEffectuer> loadAllMiseEnPlaceEffectueeByDepartementAndIntrant(Long idDepartement, Long idDepartment,
			String dateDebut, String dateFin) throws EntityDBDAOException;
	
	// Lecture de toutes les mises en place effectués sur un intran (Journal)
	public List<MiseEnPlaceEffectuer> loadAllMiseEnPlaceEffecByTypeIntrant(Long idIntrant) throws EntityDBDAOException;

	
	 List<MiseEnPlaceEffectuer>  RechercheMiseEnPlaceDTO(RechercheMiseEnPlaceDTO dto) throws EntityDBDAOException;
	 
	

		
	// Rechercher les mises en place effectuees sur un intrant  pour une période donnée 
	 List<MiseEnPlaceEffectuer>  rechercherMiseEnplaceEffectueParIntrant(RechercheMiseEnPlaceDTO dto) throws EntityDBDAOException;

	// Verif si un produit est dispo chez un fournisseur
	Intrant verifierStockRestantAupresFournisseur(Long idIntrant, Long idFournisseur) throws EntityDBDAOException;
	
	// Verif si un produit est dispo chez un magasin
	Intrant verifierStockRestantAupresMagasin(Long idIntrant, Long idMagasin) throws EntityDBDAOException;

	
	List<MiseEnPlaceAEffectuer> loadAllMiseEnPlaceAEffectuer();
	
	// AGREGATION DES MISES EN QUOTAS DE MEP PAR INTRANT (on additionne les intrant de meme type)
	List<MiseEnplaceAgregation> loadAgregationMiseEnPlace();

	// AGREGATION DES MISES EN QUOTAS DE MEP PAR INTRANT et Departement (on additionne les intrant de meme type dans un meme departement)

	List<MiseEnplaceAgregation> loadAgregationMiseEnPlaceByIntrantAndDepartement();

	// load des points de vente  ayant un intrant  specique pour un dept
	List<MiseEnPlaceAEffectuer> loadListQuotabyIdIntrantAndDepartement(Long idIntrant, Long idDepartement)
			throws EntityDBDAOException;
	
	// chercher si une mise en place existe deja (intrant + point de vente + idProgramme
	MiseEnPlaceAEffectuer verifierExistanceQuota(Long idPv, Long idIntrant, Long idProgramme)
			throws EntityDBDAOException;
	
	
	/***** COMMON REF  ***/
	Transporteur loadTransporteurByName(String libelle);
	Chauffeur loadChauffeurByName(String name);
	Camion loadCamionByName(String name);
	
	/***** FOURNISSEUR  ***/
	List<FournisseurDTO> loadFournisseurByProduit(Long idProduit) throws EntityDBDAOException;
	Fournisseur loadFournisseurByName(String name);
	
	/** Liste des mises en place avec reliquat à executer  
	 * **/
	List<MiseEnPlaceEffectuer> loadMiseEnPlaceHavingReliquat() throws EntityDBDAOException;
	// lecteurs des OL 
	List<OrdreLivraison> loadOLTraites() throws EntityDBDAOException;
	List<OrdreLivraison> loadOLAcceptes() throws EntityDBDAOException;
	List<OrdreLivraison> loadOLEnAttentes() throws EntityDBDAOException;
	
	// Rechercher Programme si exist dans la campagne
	public ProgrammeAgricol campapgneNameExist(Long idCampagne, String libelleCampagne);
	
	// Load MEP TERMINE et En cours
	public	List<MiseEnPlaceAEffectuer> loadAllMiseEnPlaceByTermine() throws EntityDBDAOException;
	List<MiseEnPlaceAEffectuer> loadAllMiseEnPlaceEncours() throws EntityDBDAOException;
	List<Commune> loadAllCommuneHomonyme(String commune) throws EntityDBDAOException;
	public List<MiseEnPlaceAEffectuer> loadMiseEnPlaceByProgramme(Long idPrigramme) throws EntityDBDAOException;

	
	public Commune loadCommuneFromLibelleCommuneAndLibelleDept(String string, String string2);
	public ReferentialIntrants loadIntrantByName(String libelle)  throws EntityDBDAOException;
	public Fournisseur loadIFournisseursByName(String libelle)  throws EntityDBDAOException;
	public ProgrammeAgricol loadIProgrammeByName(String libelle) throws EntityDBDAOException;
	public Long loadIPoitDeVenteIdByName(String intrantName);
	public Integer loadAllFornisseursForAutoCompleteSize() throws EntityDBDAOException;

	public  List<MiseEnPlaceEffectuer> rechercherMiseEnplace(RechercheMiseEnPlaceDTO dto) throws EntityDBDAOException;
	public Transporteur loadITransporteurByName(String libelle) throws EntityDBDAOException;
	public  	Departement loadIDepartementByName(String libelle);
	public  int loadallBonDeLivraison() throws EntityDBDAOException;
	public BonDeLivraison loadBlByNumeroBl(String lv) throws EntityDBDAOException;
	InfosCommunesDTO getDetailsCommuneFromIdCommune(Long idCommune);
	
	// load liste des points affectés a un (magasinier/superviseur)
	public List<CoupleDTO> loadAllIntrantFromAffectations(Long idUser);
	
	
	
	
}