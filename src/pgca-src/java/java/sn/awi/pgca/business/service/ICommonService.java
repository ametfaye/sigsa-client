package sn.awi.pgca.business.service;

import java.util.List;

import javax.faces.application.FacesMessage.Severity;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.dataModel.Fournisseur;
import sn.awi.pgca.dataModel.GenericModel;
import sn.awi.pgca.dataModel.MiseEnPlaceAEffectuer;
import sn.awi.pgca.dataModel.MiseEnPlaceEffectuer;
import sn.awi.pgca.dataModel.PointDeVente;
import sn.awi.pgca.dataModel.ReferentialIntrants;
import sn.awi.pgca.dataModel.Intrant;
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
import sn.awi.pgca.web.dto.VentesDTO;

public interface ICommonService {

	/******** ENtry Point : ******/
	public List<CampagneAgricoleDTO> loadAllCampagneAgricole();

	public GenericModel getEntityModelById(Class<?> modele, Long idEntitity) throws EntityDBDAOException;

	public List<CoupleDTO> loadPays();

	public CoupleDTO getPays(String id);

	public List<PointDeCollecteDTO> loadPointdeCollecteDTOs();

	public List<CoupleDTO> loadRegion();

	public List<CoupleDTO> loadAllDepartement();

	public List<CoupleDTO> loadAllCommun();

	public List<CoupleDTO> loadAllDepartementOfRegion(Long idRegion);

	public List<CoupleDTO> loadAllCommunOfdepartement(Long idDepartement);

	public List<CoupleDTO> loadProfil(); // Filter la liste par profil car les
											// admin ne doivent pas voir les
											// profils Manager

	public CoupleDTO getProfil(String id);

	public List<CoupleDTO> loadRegionbyPays(String id);

	public List<CoupleDTO> loadPointDeCollecte();

	public List<CoupleDTO> loadPointDeVente();

	/************
	 * Produit Genrics (Engrais , Intrant , Semence ....)
	 ***************/

	boolean updateProduit(ProduitDTO pdo);

	public List<IntrantTypeDTO> loadAllTypeIntrants();

	public boolean createIntrant(IntrantDTO idto);

	/************************************/

	/************ Point de vente ***************/
	public List<PointDeVenteDTO> loadAllPointDeVente(boolean refreshList);
	public List<PointDeVenteDTO> loadAllPointDeVenteByCommuneId(Long idCommne) throws EntityDBDAOException; /* Lecture des point de vente d'une commune*/

	/***  Verifier si le point de vent de meme no existe deja sur la commune 
	 * @throws EntityDBDAOException */
	public boolean checkExistingPointDeVenteByName(String libelle, Long idCommune) throws EntityDBDAOException;


	public boolean createPointdeVente(PointDeVenteDTO pv);

	/************ Programme ***************/

	
	
	

	/************ Stock ***************/	
	boolean createMagasin(MagasinDTO dto) throws EntityDBDAOException;

	/************ Programme ***************/
	
	

	
	
	

	public List<CoupleDTO> loadAllProgramme() throws EntityDBDAOException;

	public List<CoupleDTO> loadAllProgramme(int statut) throws EntityDBDAOException;

	public List<CoupleDTO> loadAllStock();

	public List<EngraisDTO> loadAllEngraisDTO();

	boolean deleteEntityModele(Long idEntity, Class<?> c);

	/***** Commun *********/

	public boolean deletePointdeVente(PointDeVenteDTO pdto);

	public boolean updatePointdeVente(PointDeVenteDTO pointDeVente);

	public PointDeVenteDTO loadPointDeVenteById(Long idPv);

	public List<CoupleDTO> loadCamionsBychauffeur(Long idChauffeur);

	public List<CoupleDTO> loadchauffeurByTranporteur(Long idtransporter);

	public List<CoupleDTO> loadAllTransporteur();

	List<PersonneDTO> loadAllChauffeur() throws EntityDBDAOException;

	PersonneDTO loadChauffeurByIdChauffeur(Long idChauffeur) throws EntityDBDAOException;


	List<CoupleDTO> loadAllCamion();

	public List<CollaborateurDTO> loadAllCollaborateurDTOsPersonne();

	public CoupleDTO loadTransnporteurbyId(Long transporteurid) throws EntityDBDAOException;

	public CoupleDTO loadCamionById(Long camionid) throws EntityDBDAOException;

	public List<VentesDTO> loadAllVentesForManager() throws EntityDBDAOException;

	// Get details departement et region a partir de nom de la commune
	public InfosCommunesDTO getDetailsCommune(String nomCommuune);
	public InfosCommunesDTO getDetailsCommuneById(Long idCommune);
	
	List<ProduitDTO> loadStockProduitByIdPointdeVenteMagasinier(Long idStockPointDeVente) throws EntityDBDAOException;

	// Liste des produits pour auto complemention
	String loadAllProductInJsonFormat(int maxRecords);

	public List<CoupleDTO> loadAllProduitByIdTypeProduit(Long subventionProduit) throws EntityDBDAOException;

	/***** Collaborateur *********/

	public List<CollaborateurDTO> loadAllCollaborateurDTOs();

	public List<PersonneDTO> loadUtilisateurFromPointDeCollecte(Long idPointDeCollecte) throws EntityDBDAOException;

	/***** COMMUN *********/

	void showMessage(Severity severity, String message);

	/***** REFERENCE DATA *********/

	public List<IntrantTypeDTO> loadReferentielTypeIntrant() throws EntityDBDAOException;

	boolean createPointdeCollecte(PointDeVenteDTO pv);

	List<CamionDTO> loadAllCamionDTO();

	
	/***** MISE EN PLACE INTRANT  
	 * @throws EntityDBDAOException *********/
	
	// Liste des Mise en place dune commune
	List<MiseEnPlaceAEffectuer> loadAllMiseEnPlaceByCommuneId(Long idCommune) throws EntityDBDAOException;

	List<CoupleDTO> loadAllBLOfSelectedProgramme(Long idProgramme);
	
	/*** 
	 *  Aggreation des quotas de mise en place
	 *  
	 *  ON addiotion les quota de meme type
	 *  
	 * @return
	 */
	List<MiseEnplaceAgregation> loadAggregationMiseEnPlace();
	
	/***
	 *   CAS  : Dans un departement donné , on cherche toute les mise en place d'uree effectué :
	 *   
	 * @param idDepartlent
	 * @param idIntrant
	 * @param string
	 * @param string2
	 * @return
	 * @throws EntityDBDAOException 
	 */
	
	//Lecture des mises en place du effectue sur un departement specique pour un intrant specifique par periode
	List<MiseEnPlaceEffectuer> loadAllMiseEnPlaceEffectueeByDepartementAndIntrant(Long idDepartlent, Long idIntrant, String DateDebut, String dateFin) throws EntityDBDAOException;
	
	
	// Lecture de tous les mise en place d'un intran par periode (tout les departmeent du SEN)
	public  List<MiseEnplaceDTOPointDeVente> rechercherMiseEnplaceEffectueParIntrant(String searchdateDebut,
			String searchdateFin, Long idIntrant) throws EntityDBDAOException;
	
	
	
	/****  Load mise en place by Aggregation
	 * 
	 *  Apres l'agreagation des intrants , on recupere la liste des quota pour un intrant donné 
	 *  
	 *  PAR EXEMPLE QUOTA UREE 
	 *              30 -> KOLDA  
	 *              70 -> TAMBA
	 *              100 -> KALOACK
	 *              
	 *    l'agregatiin des UREE est de 200 (la somme des trois de meme )   
	 *    
	 *     Ce service recupere a partir de l'id du l'intrant les quotas qui sont liee (KOLDA + TAMBA + KAOLCAK)
	 * @throws EntityDBDAOException 
	 *  ****/
	public List<MiseEnPlaceAEffectuer> loadListQuotabyIdIntrant(Long idIntrant) throws EntityDBDAOException;
	
	List<MiseEnPlaceAEffectuer> loadListQuotabyIdIntrantAndDepartement(Long idIntrant, Long idDepartement)
			throws EntityDBDAOException;
	
	
	public boolean updateQuotaMEP(MiseEnplaceDTOPointDeVente listQuotaMiseEnplaceSelectedQuota) throws EntityDBDAOException;

	
	/******** END INTRANT ***********/

	
	
	/************
	 * Transporteur List
	 ***************/
	List<CoupleDTO> loadAllCamionByIdTransporteur(Long idRegion);

	List<CoupleDTO> loadAllChauffeurByIdTransporteur(Long idCamion);

	
	
	
	/************
	 * Fournisseur Services  **/
	
	boolean createFournisseur(FournisseurDTO f);
	List<FournisseurDTO> loadAllFornisseurs();
	// Recherches des fournisseurs qui ont un produit spécfique (Ex :  tous les fournisseurs qui ont du NERICA 1)
	List<FournisseurDTO> loadFournisseurByProduit(Long idProduit);
		
	// verifier si le produit reste auprès du fournisseur ou pas 
	public Intrant verifierStockRestantAupresFournisseur(Long idIntrantAMettreEnplace, Long fournisseurId);
	

	// Apres mise en place on diminue le stock du fournisseur qui a distribué le produit
	ProduitDTO diminuerStockFournissuerApresMEP(Long idIntrantAMettreEnplace, Long fournisseurId,
			Double quantiteAdeduire);
	// mise en cache des donnes pseudo statics (transporteur , driver , chauffeur)
	public String loadCachedData(String listOfCamionFromDBInJSON, String listOfChauffeurFromDBInJSON,
			String listOfTransporteurFromDBInJSON);

	// lire la liste des litiges 
	public List<LitigesDTO> loadAllLitiges();

	List<ProgrammeAgricolDTO> loadAllProgrammeDTO() throws EntityDBDAOException;

	public List<CoupleDTO> loadAllACampagne() throws EntityDBDAOException;

	public String loadAllCommunAndDepartement() throws EntityDBDAOException;

	
	
	
	
	/*********   Methodes utilitairs pour les services d'auto complétion *********.
	 * 
	 * @return  : Liste entité concatés avec des -
	 * 
	 */
	public String loadAllIntrantforAutoCoplete() throws EntityDBDAOException; 
	public ReferentialIntrants  loadIntrantByName(String intrantName) throws EntityDBDAOException;  
	
	// lecture de la liste des fournisseur en auto complete
	public String loadAllFornisseursForAutoComplete() throws EntityDBDAOException;
	public Fournisseur  loadFournisseursByName(String intrantName) throws EntityDBDAOException;
	public String loadAllProgrammeforAutoCoplete() throws EntityDBDAOException;

	// reserver servcie auto complate
	public Long loadFournisseuridByName(String intrantName) throws EntityDBDAOException;
	public Long loadIntrantIdByName(String intrantName) throws EntityDBDAOException;

	public Long loadIProgrammeIdByName(String intrantName) throws EntityDBDAOException;

	public Long loadIPoitDeVenteIdByName(String intrantName) throws EntityDBDAOException;



	// compter le nombre de fournisseur en base : si different du cache on reload
	Integer loadAllFornisseursForAutoCompleteSize() throws EntityDBDAOException;



	
	
	
	/*********   Lecture  d'une class a partir de l'attribut libelle *********/

	

 /**** RG SEDAB  :  Toutes les communes sont par defauts des point de vente 
  * 
  * En plus du réféentiel des communes  du  Sénégal  ,  on  ajoute d'autres points de ventes spécifique à sedab  
  * 
  * et Pour ces point de ventes spécifique , on les créé dans l'entity Commue et on les on tag commme local à sedab 
  * 
  * 			ccommePointDevente.setCode("LOCAL-SEDAB-PV");
  * 
  *   Ceci permettra de filtrer les  communes  officieles du sénégal pendant les filters 

  */
	
	
	// Nouvel comm
	boolean createPointdeVenteCommune(Long idCommune , String nomCommune);


	public String loadAllDepartementAutoCoplete() throws EntityDBDAOException;

	public String loadAllTransporteurforAutoCoplete() throws EntityDBDAOException;

	Long loadITransporeurByName(String intrantName) throws EntityDBDAOException;

	Long loadIDepartementByName(String intrantName) throws EntityDBDAOException;

	
	
	
	
	/*********    *********/

	public List<MagasinDTO> loadReferentielMagasin();

	public List<CoupleDTO> loadAllPointdeVentesOfDepartemnt(Long idDepartement);
	
	public List<CoupleDTO> loadAffectationsByIdUser(Long idUser) throws EntityDBDAOException ;

	public List<PointDeVente> loadAllIntrantFromAffectations(Long id) throws EntityDBDAOException;

	// OL on filtre pas :  lors de l'édition DOL on filtre pas par rapport au stock du de DAKAR
	List<ProduitDTO> loadStockProduitForOL() throws EntityDBDAOException;

	// recupérer tous les point de ventes d'un Departement
	List<MagasinDTO> loadReferentielMagasinByDepartement(Long idDepartement) throws EntityDBDAOException;


}
