package sn.awi.pgca.business.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.exception.ProgrammeException;
import sn.awi.pgca.dataModel.BonDeLivraison;
import sn.awi.pgca.dataModel.Intrant;
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
import sn.awi.pgca.web.dto.MiseEnplaceDTOCommune;
import sn.awi.pgca.web.dto.MiseEnplaceDTOPointDeVente;
import sn.awi.pgca.web.dto.PointDeVenteDTO;
import sn.awi.pgca.web.dto.ProduitDTO;
import sn.awi.pgca.web.dto.ProgrammeAgricolDTO;
import sn.awi.pgca.web.dto.VentesDTO;

public interface IProgrammeAgricol extends IServiceStockResiduel {
	
	/******** Ouverture campagne Agricole :  Chaque campagne peut avoir plusieur programmes ******/ 

		boolean createCampagneAgricole(CampagneAgricoleDTO cdo) throws ProgrammeException;
		boolean removeCampagneAgricole(long idCampagne) throws ProgrammeException;
		boolean createProgammeForCampagneAgricol(ProgrammeAgricolDTO prog) throws ProgrammeException;
		List<CampagneAgricoleDTO> verifierExistanceCampagneParAnnee(Date debut,  Date fin) throws EntityDBDAOException;
		boolean closeCampagneAgricole(CampagneAgricoleDTO cdo) throws ProgrammeException;
		List<CoupleDTO> getlistProgramme() throws ProgrammeException, EntityDBDAOException;
		boolean getinfosDetailsCampagneAgricole(CampagneAgricoleDTO cdo) throws ProgrammeException;
	    List<CampagneAgricoleDTO> getlistCampagneAgricoleWithoutDetails() throws ProgrammeException, EntityDBDAOException;
	    List<CampagneAgricoleDTO> getlistCampagneAgricolewithDetails() throws ProgrammeException, EntityDBDAOException;
		boolean GenrateRapportExcelCampagneAgricole(CampagneAgricoleDTO cdo) throws ProgrammeException;
		
		/****  Gestion des intrants d'une campagne Agricole    INtrant  = Produit 
		 * @throws EntityDBDAOException ************/
		boolean  addIntrantCampagne(IntrantDTO idto) throws EntityDBDAOException;
		boolean  updateIntranCampagne(IntrantDTO idto) throws EntityDBDAOException;
		boolean  updateQuantiteIntrantByPaiementNature(Long idProduit,  CommandeDTO infosCommande);
		
		/****************  INTRANT  
		 * @return 
		 * @throws EntityDBDAOException 
		 * @throws ProgrammeException **********/
		
		
		ProduitDTO loadProduitDTObyIdProduit(Long idProdui) throws ProgrammeException, EntityDBDAOException;
		
		/*** Lecture referentiel produit **/
		ProduitDTO loadProduitDTOReferentielbyIdProduit(Long idProdui) throws ProgrammeException, EntityDBDAOException;

		boolean createBL(BonDeLivraisonDTO bondeLivraisonDTO, List<ProduitDTO> listProduitsDTOtoCreate);
		Long getStockidFromPointDeVente(Long idPointDeVente) throws EntityDBDAOException ;
		List<BonDeLivraisonDTO> loadBondeLivraisonByIdPointDeVente(Long connectedUseridStock) throws EntityDBDAOException;
		// Recuepere tous les produit liée a un BL
		BonDeLivraisonDTO getAllProduitsFromIdBL(Long id) throws EntityDBDAOException;
		// Apres reception d'un BL  , mise a jour du stock de reception 
		boolean updateStockFromBl(BonDeLivraisonDTO selectedBLDTO, Long idStockReceptionnaire, List<ProduitDTO> listProduitsOfBL) throws EntityDBDAOException;
		
		//  update avec création litige pour la qantite manquatane
		boolean updateStockFromBlWithReserve(BonDeLivraisonDTO selectedBLDTO, Long idStockReceptionnaire, List<ProduitDTO> listProduitsOfBL , LitigesDTO litige) throws EntityDBDAOException;

		List<VentesDTO> loadNbVentesByIdPointDeVente(Long connectedUseridStock) throws EntityDBDAOException;
		// Lecture des credits d'un point de Vente 
		List<CreditDTO> loadNbCreditsByIdPointDeVente(Long connectedUseridStock);
		List<ProduitDTO> getListProduitsVEnduFromIdVente(Long idVEnte) throws ProgrammeException, EntityDBDAOException;

	
		/****************  Commande Produit 
		 * @return 
		 * @throws EntityDBDAOException 
		 * @throws ProgrammeException **********/
	
		boolean enregistrerCommnande(CommandeDTO commandeDTO, List<ProduitDTO> listProduitCommande);	
		public List<CommandeDTO>  getListCommandeByCriteria(Long idProgramme,  int statut);	
		public List<CommandeDTO>  getListCommandeByCriteria(Long idPointDeVente,  Long idProgramme) throws EntityDBDAOException;	
		public List<CommandeDTO>  getListCommandeByCriteria(Long idProgramme,  Long idPoindeVente,  int statut) throws EntityDBDAOException;	
		public List<CommandeDTO>  getListCommandeByCriteria(Long idProgramme);	
		public List<ProduitDTO> loadAallProduitFromIdCMD(Long idCommande) throws EntityDBDAOException;
		public List<CommandeDTO>  getListCommandeEnvoyeByidPointdeCollect(Long idProgramme) throws Exception;	
		
		public boolean  accepterCommande(Long idCommande) throws EntityDBDAOException;	
		
		/****************  ORDRE DE LIVRAISON
		 * @return 
		 * @throws EntityDBDAOException 
		 * @throws ProgrammeException **********/
		// lire ordre de livraison d'un point de vente
		List<CommandeDTO> getListOrdreEnvoyeByidPointdeCollect(Long idpc);
		CommandeDTO loadOrdreInfosById(Long idOrdre) throws EntityDBDAOException;
		List<CommandeDTO> managerLoadOrdresAvaliderByCriteria(Long idProgamme, Long idPointVente, int statusOL,
				String client);




		public Map<Boolean, ProduitDTO> chercherDisponibiliteProduitParPoids(ProduitDTO pdto) throws ProgrammeException, EntityDBDAOException;
		public  String getLibelleCampagneByIdProduit(Long idProduit);
		public Long getidProgrammebyIdProduit(Long idProduit) ;
		String getLibelleProgrammeByIdProduit(Long idProduit);
		List<ProduitDTO> loadAallProduitFromIdOrdre(Long idCommande) throws EntityDBDAOException;

		

		/****************  GESTION DES POINTS DE VENTES
		 ***************************************************************/
		
		List<PointDeVenteDTO> loadAllPointDeVenteByIdProgramme(Long idProgramme);
		// Tous les points de vente d'une region pour un programme donné
		List<PointDeVenteDTO> loadAllPointDeVenteByIdProgrammeAndCodeRegion(Long idProgramme, Long codeRegion) throws EntityDBDAOException;
		
		List<PointDeVenteDTO> loadAllPointdeVenteNational() throws EntityDBDAOException;

		
		
		
		
		

		
		
		/****************  STOCCK
		 ***************************************************************/
		
		List<ProduitDTO>  loadAallProduitFromStockbyIdStock(Long idStock) throws EntityDBDAOException;
		ProduitDTO  loadAProduitFromStockbyIdStockIdProduitAndIdProg(Long idStock, Long idProduit, Long idProg) throws EntityDBDAOException;
		CommandeDTO loadCommandeInfosByIdCMD(Long idCommande) throws EntityDBDAOException;
		boolean substractProduitFromStock(Long idStock, List<ProduitDTO> listProduitsOfBL) throws EntityDBDAOException; /** Deduction produit sur un stock donné **/
		boolean verifStock(Long idStock, List<ProduitDTO> listProduitsOfBL) throws EntityDBDAOException; /** Verif disponibilite produit sur un stock donné 
		 * @throws EntityDBDAOException **/
		Intrant loadProduitbyIdAdnIdFournisseur(Long idProduit , Long idFournisseur) throws EntityDBDAOException;
		Intrant loadProduitbyIdAdnIdMagasin(Long idProduit , Long idStockMagasin) throws EntityDBDAOException;
		
		// Recupération des produit stock au forunisseur 
		public List<IntrantDTO> getlistIntrantDTOFromFournisseur()   throws EntityDBDAOException;
		
		// récupération des stock des magasin (tous les produit stocké dans des magsin)
		public List<IntrantDTO> getlistIntrantDTOFromMagasin() throws EntityDBDAOException;;



		
		/****************  Collecte  & INTRANT
		 * @return 
		 * @throws EntityDBDAOException 
		 * @throws ProgrammeException **********/
		List<IntrantDTO> loadCollecteByPointDeCollecteAndProg(Long idPointDeCollecte, Long idPointDeVente) throws EntityDBDAOException;
		boolean enregistrerCollect(AllocationDTO allocationDeReference) throws EntityDBDAOException;
		
		
		/****************  INtrant & Type Intrant 
		 * @return 
		 * @throws EntityDBDAOException 
		 * @throws ProgrammeException **********/
		boolean removeTypeIntrantFromCampagne(long idTypeIntrant) throws ProgrammeException;
		boolean createTypeIntrant(IntrantTypeDTO intrantType);
		boolean verifyExistingTypeIntrantByLibelle(String libelle); // avoid deux type d'intrants de meme nom
		boolean verifyExistingIntrantByLibelle(String libelle);  // Avoid deux intrant de meme nom
		public List<IntrantDTO> loadReferentielIntrants() throws ProgrammeException;
		public List<IntrantDTO> loadReferentielIntrantByType(Long idProduitType) throws EntityDBDAOException;
		boolean createIntrant(IntrantDTO selectedIntrantDTO);
		public IntrantDTO loadIntrantByIdTypeAndStock(Long idProduitType, Long idStock) throws EntityDBDAOException;
		boolean updateQuantiteIntranCampagne(IntrantDTO idto); 
		// lecture de tout les produits d'un stock 
		List<IntrantDTO> loadAllIntrantDTOByIdStock(Long idStock);
		List<IntrantDTO> loadAllIntrantDTOByidCampagne(Long idCampagne);
		List<IntrantDTO> loadAllProduitOfAllCampagne(); // for manager
		IntrantDTO loadIntrantByid(Long idIntrant) throws EntityDBDAOException;
		IntrantDTO loadIntrantByLibelleIntrant(String libelleIntrant) throws EntityDBDAOException;
		List<ProgrammeAgricolDTO> getlistOfProgramme() throws ProgrammeException, EntityDBDAOException;
		boolean enregistrerOrdreDeLivraison(CommandeDTO commandeDTO, List<ProduitDTO> listProduitCommande);
		boolean updateIntranOfStock(IntrantDTO idto) throws EntityDBDAOException; // mise à jour d'un referentiel 
		
		
		/****************  MANAGER LOAD ALL ENTITY
		 * @return 
		 * @throws  
		 * @throws ProgrammeException **********/
		List<CommandeDTO> managerLoadOrdresAvalider();
		boolean validerOrdreDeLivraisonById(Long idCommande);
		
		/****************  MANAGER MISE EN PLACE 
		 * @param idProgramme 
		 * @return 
		 * @throws  
		 * @throws ProgrammeException **********/
		
		List<MiseEnplaceDTOPointDeVente> loadAllMiseEnPlaceOfCampagne(Long idProgramme) throws EntityDBDAOException;
		
		/****************  GESTION MISES EN PLACE 
		 ***************************************************************/
		 // Enregistrement d'une mise en place (data =  quota + reliquat + cumul) //  Attention Pas Produit mais Refintrant
		boolean saveMiseEnplace(Long idProgramme , Long idIntrant, Date  dateMmp,  MiseEnplaceDTOPointDeVente data);
		
		 // Suppression d'une mis en place a partir de son id Technique
		boolean deleteMiseEnplace(Long idProgramme)  throws EntityDBDAOException; 
		
		
		// Recupération des mises  en place a effectuer dans une commune
		List<MiseEnplaceDTOPointDeVente> loadAllMiseEnPlaceByIdCommune(Long idProgramme) throws EntityDBDAOException;
		// envoyé des produits sur une ligne de mis en place donnéee (En général au port via un bl port)
		int executeMiseEnplaceById(MiseEnplaceDTOPointDeVente infosMEP , BonDeLivraisonDTO blDTO) throws EntityDBDAOException;
		// Lecture de tous les mise en place a effectué pour une mise en place donnée
		List<MiseEnPlaceEffectuerDTO> loadAllMiseEnPlaceEffectueeByidMAP(Long idProgramme) throws EntityDBDAOException;
		// Lecture de tout les quota de mep poour un point de vente specifique
		List<MiseEnplaceDTOPointDeVente> loadMiseEnPlaceAeffectuerByidPointdeVente(Long idPv, Long idCategorie, Long idIntrant) throws EntityDBDAOException;
	
		// A partir d'un idINtrant , on les recupere tout les quota de aynt un idIntrant donné en parametre
		List<MiseEnplaceDTOCommune> loadListQuotabyIdIntrant(Long idTrant) throws EntityDBDAOException;
		// meme que la precedente avec groupage par departement
		List<MiseEnplaceDTOCommune> loadListQuotabyIdIntrantAndDepartement(Long idIntrant, Long iddept)
				throws EntityDBDAOException;
	
		// verifier si le BL & LV existe déja
		boolean checkExistingBL(String numeroBLManuel) throws EntityDBDAOException;
		boolean checkExistingLV(String numeroBLManuel) throws EntityDBDAOException;
		
		
		// lecteurs des indicateurs du dashbaorad agent de saisie
		IndicateursDashboardDTO loadAllIndicateursDashboard() throws EntityDBDAOException;
		boolean createCampagneProgrammeAgricole(CampagneAgricoleDTO cdo) throws ProgrammeException, EntityDBDAOException;
		
		// voir si le programmme n'existe pas dans la campagne avant de la créer 
		boolean campapgneNameExist(String libelleCampagne, Long idCampagne);
		
		
		
		
		/****************  MISE EN PLACE (MEP)  
		 * @return 
		 * @throws EntityDBDAOException 
		 * @throws ProgrammeException **********/
		
		// Lecture des MEP termines
		public int  loadAllMiseEnPlaceByTermine() throws EntityDBDAOException;
		public int loadAllMiseEnPlaceEncours() throws EntityDBDAOException;
		public List<MiseEnplaceDTOPointDeVente> getAllMiseEnPlaceEncours() throws EntityDBDAOException;
		
		// aggregé les Quota de par intrant 
		public List<MiseEnplaceDTOPointDeVente> listeAggregationMiseenPlaceParIntrant() throws EntityDBDAOException;
		// lecture MEP effectué sur un intran spécifique
		List<MiseEnPlaceEffectuerDTO> loadAllMiseEnPlaceEffecByTypeIntrant(Long idIntrant) throws EntityDBDAOException;
		
		
		
		/****************  TRANSFERT DE STOCK ENTRE Fournisseur -> Magasin  : 
		 *  Lors du transfert un BL est créé et transmis au gérant du magasin (Mail & affichage sur les BL)
		 * @return 
		 * @throws EntityDBDAOException 
		 * @throws ProgrammeException **********/
		
		// Déplacer un stock d'intrant d'un fournisseur vers un magasin
		boolean transfererStock(IntrantDTO selectedIntrantTotransfer, Long idMagasin, Double quantiteAtranferer) throws EntityDBDAOException;
		

		/****************  TRANSFERT DE STOCK ENTRE MAGASIN  : 
		 *  Lors du transfert un BL est créé et transmis au gérant du magasin (Mail & affichage sur les BL)
		 * @return 
		 * @throws EntityDBDAOException 
		 * @throws ProgrammeException **********/
		boolean transfererStockEntreMagasin(IntrantDTO selectedIntrantTotransfer, Long idMagasinSource, Long idMagasinCible,
				Double quantiteAtranferer) throws EntityDBDAOException;
		
		
		/****************  BL   : 
		 	**/
		BonDeLivraison loadBlByNumeroBl(String lv) throws EntityDBDAOException;
		
		/* LEcture des bl envoyes par un user connecte**/
		List<BonDeLivraisonDTO> ladBLEnvoyesByStock(Long connectedUseridStock) throws EntityDBDAOException;
		List<IntrantDTO> getlistIntrantDTOFromFournisseurFournisseur();
		
		
		
		/****************  AFFECTATIONS    : 
		 	**/
		
		
		List<IntrantDTO> loadAllIntrantFromAffectations(Long idUser) throws EntityDBDAOException;
		PointDeVenteDTO loadPointDeVenteById(Long idPv) throws EntityDBDAOException;
			
}
