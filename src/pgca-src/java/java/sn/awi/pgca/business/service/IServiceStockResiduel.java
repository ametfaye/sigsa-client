package sn.awi.pgca.business.service;

import java.util.List;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.dataModel.StockResiduelPointDeVente;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.PointDeVenteDTO;
import sn.awi.pgca.web.dto.ProduitDTO;

public interface IServiceStockResiduel {
	
	
		/*****************************************
		 *     GESTION DES STOCKS RESIDUELS 
		 *     
			 *   POINT DE VENTE  : LES PRODUITS MISES EN PLACE DANS LE PV peuvent rentrer dans le stock de SEDAB de nouveau .<br/>
			 * 
			 *    Par exemple l'etat demande de mettre en place 100T Mais ne garantie pas l'achat de la totalité .</br>
			 *    Si  les producteurs n'achétent pas la totalité mise en place,  le Gérant du point de vente doit recupérer le résiduel du stock et de facturer que ce qui est vendu 
			 *    
			 *     DATA -> MiseEnPlaceAEffectuer->MiseEnPlaceEffectuer->Intrant
		
			 *    
			 *    boucler entre mag -> POINT DE VENTE OU LINVERSE 
			 *    
		**********************/
		
		public List<IntrantDTO> loadStockResiduel()  throws EntityDBDAOException;
		
		
		
		
		
		/*****************************************
		 *     GESTION DES STOCKS RESIDUELS d'un intrant spécifique <br/>
		 *     
			 *   Recupération des stock residuel des point de vente.<br/>
			 *   
			 *   Si la quatité mise en place n'est pas totalement vendue le reste est transféré 
			 *    
		**********************/
		
		public List<IntrantDTO> loadStockResiduelByIntrant(Long idIntrant)  throws EntityDBDAOException;
		
		
		
		
		
		/*****************************************
		 *     GESTION DES STOCKS RESIDUELS des intrant dans les communes  :  <br/>
		 *     
			 *  Les communes sont des points de vente temporaires ,.<br/>
			 *   
			 *   Leurs stocks sont mises  à jour après les actions ci dessous 
			 *   
			 *   - Reception de mise en place 
			 *   - Ventes effectués par le representant de sedab
			 *   - Transferts des stock restants a la fin des camapagnes vers les magasins
			 *    
		**********************/
		
		public StockResiduelPointDeVente createOrUpdateStockResiduelByPointdeVente(Long idCommune , Long idIntrant,  Double quantité)  throws EntityDBDAOException;
		
		public IntrantDTO loadStockResiduelByPointdeVente(Long idCommune , Long idIntrant)  throws EntityDBDAOException;

		public List<IntrantDTO> loadStockResiduelByidCommune(Long idCommune ) throws EntityDBDAOException;




		/*****************************************
		 *     GESTION DES INventaires   :  <br/>
		 *     
			 *  Les gerants des points de ventes ou magasin peuvent ajouter directement des intrants dans leurs stock ,.<br/>
			 *   
				(Futur Module de Collecte)
			 *    
		**********************/
		boolean addIntrantCampagneCollecte(IntrantDTO idto) throws EntityDBDAOException;





		/*****************************************
		 *     Transfert de stock de point de vente vers magasin   :  <br/>
		 *     
			 *  Les gerants des points de ventes ou magasin peuvent transferer leurs stock vers des magasin ,.<br/>
			 *   
				
			 *    
		**********************/
		boolean transfererStockFromPointDeVenteToMagasin(IntrantDTO selectedIntrantTotransfer, Long idMagasin,
				Double quantiteAtranferer) throws EntityDBDAOException;

		boolean transfererStockFromPointDeVenteToMagasinModeStock(IntrantDTO selectedIntrantTotransfer, Long idMagasin,
				Double quantiteAtranferer) throws EntityDBDAOException;





		List<IntrantDTO> loadStockResiduelSuperviseurAndCommune(Long idCommune, Long idPointdeCollecteSuperviseur)
				throws EntityDBDAOException;



		/**** UN SUPERVISEUR PEUT FAIRE UNE ERREUR LORS DES SAISIS DE STOCK (module inventaire)  : il doit pouvoir modifier la quantite ou le prix de lintran*/

		boolean updateStockResiduelBySuperviseur(ProduitDTO produitDTO) throws EntityDBDAOException;
	
	
}
