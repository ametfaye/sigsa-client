package sn.awi.pgca.business.service;

import java.util.List;

import sn.awi.pgca.business.exception.EntiteJuridiqueException;
import sn.awi.pgca.web.dto.EntiteJuridiqueDTO;

/**
 * Interface offrant tous les services liés é la gestion des profils.
 * création de profil
 * paramétrage de droit d'accés au menu ou sous-menu é un profil
 * paramétrage d'actions possible pour un profil
 *
 * @author AWA Consulting
 */
public interface IEntiteJuridiqueService {
	
	/**
	 * methode d'une entite juridique
	 * */
	
	 public boolean creerEntiteJuridique(EntiteJuridiqueDTO entite) throws EntiteJuridiqueException;

	 
		/**
		 * methode pour modifier une entite juridiqe
		 * */
	 
	 public boolean modifierEntiteJuridique(EntiteJuridiqueDTO enjuDTO) throws EntiteJuridiqueException;
	 
	 
		/**
		 * methode pour supprimer une entite juridiqe
		 * */
	 
	 	public boolean supprimerEntiteJuridique(Long id) throws EntiteJuridiqueException;


		public List<EntiteJuridiqueDTO> recupererAllEntiteJuridique() throws EntiteJuridiqueException;


		public EntiteJuridiqueDTO recupEntiteJuridique(Long id)throws EntiteJuridiqueException;
}
