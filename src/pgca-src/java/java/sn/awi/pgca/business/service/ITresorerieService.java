
package sn.awi.pgca.business.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import sn.awi.pgca.business.exception.BudgetException;
import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.dataModel.Cheque;
import sn.awi.pgca.web.dto.AllocationDTO;
import sn.awi.pgca.web.dto.AvanceCreditDTO;
import sn.awi.pgca.web.dto.BudgetDTO;
import sn.awi.pgca.web.dto.ChequeDTO;
import sn.awi.pgca.web.dto.CreditDTO;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.ProduitDTO;
import sn.awi.pgca.web.dto.SubventionDTO;
import sn.awi.pgca.web.dto.VentesDTO;
import sn.awi.pgca.web.dto.VersementBanqueDTO;

public interface ITresorerieService {

	/**********  BUDGET ***********/
	public boolean AllouerBudget(AllocationDTO allocationBudget) throws BudgetException, EntityDBDAOException;
	public List<AllocationDTO> loadAllAllocationByIdPointDeVente(Long idPoindDeVente, Long idProgramme) throws EntityDBDAOException;
	List<AllocationDTO> loadAllAllocation(Long idProgramme) throws EntityDBDAOException;

	
	
	
	
	/**********  TARIFICATION  
	 * @throws EntityDBDAOException ***********/
	public boolean tarifierProduit(boolean subventionne,Float tauxSubvention,  Float prixAvantSubvention, Long idIntrant) throws EntityDBDAOException;
	


	/**********  CREDIT ***********/
	boolean enregistrerCredit(CreditDTO cred);
	public List<CreditDTO> loadAllCreditsOfProgramme();
	public CreditDTO loadAllCreditsFromIdCommande(Long idCommande);
	public CreditDTO getListAvancebyIdCredit(Long idCredit) throws EntityDBDAOException; 	// Retourne les detils sun credits avec les avance effectue sur le credits
	public Map<Long, String>  enregistrerAvance(AvanceCreditDTO avanceDTO, Long credit_id);
	float loadMontantTotalDesCredits() throws EntityDBDAOException;
	public List<CreditDTO> loadAllCreditsByIdPointDeVente(Long idPoindDeVente, Long idProgramme) throws EntityDBDAOException;



	
	/**********  VENTE ***********/
    List<AvanceCreditDTO> loadAllAvancesFromCreditsId(Long credit_id);
	boolean vendreProduit(List<ProduitDTO> selectedProduit, VentesDTO infosVentes) throws EntityDBDAOException;
	boolean vendreProduitAvecCredit(List<ProduitDTO> selectedProduit, VentesDTO infosVentes, CreditDTO infoCredit) throws EntityDBDAOException;
	float loadMontantTotalDesVentes() throws EntityDBDAOException;
	float loadMontantTotalDesVentesByIdProduit(Long idProduit) throws EntityDBDAOException;
	public List<VentesDTO> loadAllVentesByIdPointDeVente(Long idPoindDeVente, Long idProgramme);
	//Pour les rapports mails automatisés par semaine (chaque lundi matin)
	public List<VentesDTO> loadAllVentesParPeriode(Date debutDebut , Date dateFin) throws EntityDBDAOException;


	
	/**********  BANQUE ***********/
	boolean enregistrerVersmentBanque(VersementBanqueDTO  v);
	public float  evaluerMontantTotalDepotBanqueByCampagne();	
	public List<VersementBanqueDTO> loadAllVersementBanqueByIdPointDeVente(Long idPoindDeVente, Long idProgramme);
	List<VersementBanqueDTO> recupererVersementBanqueByIdPointDeCollecte(Long idPointdeCollecte);

	public List<VersementBanqueDTO> recupererVersementBanqueByIdPointDeVente(Long idPointdeVente); /* magasinier restricted access*/
	public List<VersementBanqueDTO> recupererAllVersementBanqueByCampagne();  /* Manager full access*/

	
	/********  CAISSE POINT DE VENTE **********/
	// Repartion montant total caiss (X en bon , Y , en cheqque et Z en especes
	Map<String, Float>  evaluerRepartitionCaisseByModePaiement(Long idPointdeVente);


	
	/****** CHEQUE *******/
	boolean enregistreCheque(ChequeDTO dto);
	
	List<ChequeDTO> recupererChequeByIdPointDeVente(Long idPointdeVente);
	List<ChequeDTO> recupererAllCheques(); // manager
	public boolean encaisseCheque(Long id_cheque) throws EntityDBDAOException;
	
	
	
	/****** SUBVENTION *******/

	List<SubventionDTO> loadAllSubventions() throws EntityDBDAOException;
	
	
	/****** VENTES PRODUITS  DANS LES POINTS DE VENTES  *******/

	boolean vendreProduitFromPointdeVente( IntrantDTO infosIntrant,
			VentesDTO infoVente) throws EntityDBDAOException;
	
}
