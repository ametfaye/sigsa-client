package sn.awi.pgca.business.service;

import java.util.List;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.web.dto.CommandeDTO;
import sn.awi.pgca.web.dto.ProduitDTO;

/**
 * Interface offrant tous les services du magasinier 
 * 
 * @author Mouhamed FAYE
 */
public interface IMagasinierService {

	public List<ProduitDTO> loadStockProduitByIdPointdeVenteMagasinier(Long idPointdeVente) throws EntityDBDAOException;
	
	public boolean enregistrerVenteByIdPointdeVenteMagasinier(ProduitDTO produit, Long idStock) throws EntityDBDAOException;

	List<CommandeDTO> getAllOrdresDelivraisons(Long idPointDeVente);

	List<CommandeDTO> getAllOrdresDelivraisonsEnAttente(Long idPointDeVente);

	// traiter Ol  (Verif stock disponible puis sortir la facture, sinon message derreur stock insuffisant)
	public boolean traiterOrdreDeLivraion(Long idCommande) throws EntityDBDAOException;

	List<CommandeDTO> getAllOrdresValideByIdPointDeVente(Long idPointDeVente);
	
}
