package sn.awi.pgca.business.dao;

import java.util.List;

import javax.persistence.EntityManager;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.dataModel.AffectationsGestionPointDeVentes;
import sn.awi.pgca.dataModel.BonDeLivraison;
import sn.awi.pgca.dataModel.GenericModel;
import sn.awi.pgca.dataModel.Intrant;
import sn.awi.pgca.dataModel.MiseEnPlaceAEffectuer;
import sn.awi.pgca.dataModel.PointDeVente;
import sn.awi.pgca.dataModel.Stock;
import sn.awi.pgca.dataModel.StockResiduelPointDeVente;
import sn.awi.pgca.dataModel.Ventes;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.PointDeVenteDTO;

public interface StockResiduelDAO {

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
	// Tous 
	public List<IntrantDTO> loadStockResiduel()  throws EntityDBDAOException;
	
	// Par commune -> Filtre par gerant
	public List<IntrantDTO> loadStockResiduelByidCommune(Long idCommune)  throws EntityDBDAOException;

	
	
	
	/*****************************************
	 *     GESTION DES STOCKS RESIDUELS d'un intrant spécifique <br/>
	 *     
		 *   Recupération des stock residuel des point de vente.<br/>
		 *   
		 *   Si la quatité mise en place n'est pas totalement vendue le reste est transféré 
		 *    
	**********************/
	public List<IntrantDTO> loadStockResiduelByIntrant(Long idIntrant)   throws EntityDBDAOException;

	
	

	IntrantDTO loadStockResiduelByPointdeVente(Long idCommune, Long idIntrant)
			throws EntityDBDAOException;


	StockResiduelPointDeVente createOrUpdateStockResiduelByPointdeVente(Long idCommune, Long idIntrant, Double quantite)
			throws EntityDBDAOException;


	/*****************************************
	 *     RECUPERER UN STOCK A PARTIT DE L ID DUN POITD DE VENTE <br/>
	 *         
	**********************/
	public Stock getStockFromIdPointDeVente(Long idIntrant)   throws EntityDBDAOException;

	/*****************************************
	 *     RECUPERER LES BL envoyes par le stock connecté <br/>
	 *         
	**********************/
	List<BonDeLivraison> ladBLEnvoyesByStock(Long idStock) throws EntityDBDAOException;

	
	/*****************************************
	 *     Stock TOTAL CUMULE Restant pour un produit donné  (stock total des fournisseurs
	 *         
	**********************/
	Double loadTotalStockRestantByIdIntrantFournisseur(Long idIntrant) throws EntityDBDAOException;

	/*****************************************
	 *     Stock TOTAL CUMULE Restant pour un produit donné  (stock total des point de stock et de vente )
	 *         
	**********************/
	Double loadTotalStockRestantByIdIntrantLocal(Long idIntrant)
			throws EntityDBDAOException;

	List<IntrantDTO> loadStockResiduelSuperviseurAndCommune(Long idCommune, Long idPointdeCollecte)
			throws EntityDBDAOException;

	List<PointDeVente> loadAllPointDeventeOfDepartement(Long idDepartement) throws EntityDBDAOException;

	// recuperer toutes les affectations d'un utilisateur (liste des poinst de ventes )  SUPERVISEUR
	List<AffectationsGestionPointDeVentes> loadAllPointDeVentesAffectationByUser(Long idUser);

	List<PointDeVente> loadAllPointDeventeOfUser(Long idUser) throws EntityDBDAOException;


	/*****************************************
	 *     Les ventes effectues par un superviseur un point 
	 *         
	**********************/
	
	List<Ventes> loadVentesByGerantFournisseurs(Long idSuperviseur) throws EntityDBDAOException;

	


}