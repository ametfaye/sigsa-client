package sn.awi.pgca.business.service;

import java.util.List;

import sn.awi.pgca.web.dto.MiseEnplaceDTOPointDeVente;
import sn.awi.pgca.web.dto.RechercheMiseEnPlaceDTO;

public interface IRechercheService {
	
	/**********  Mise en Place  ***********/
	public List<MiseEnplaceDTOPointDeVente>  rechercherMiseEnplace(RechercheMiseEnPlaceDTO criteres);

	// recherche des mise en place qui sont effectué sur un intrant specifique par periode
	List<MiseEnplaceDTOPointDeVente> rechercherMiseEnplaceEffectueParIntrant(RechercheMiseEnPlaceDTO criteres);
}
