package sn.awi.pgca.business.service;

import java.util.List;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.web.dto.CoupleDTO;
import sn.awi.pgca.web.dto.IntrantDTO;

public interface IAffectationService  {
		
		/****************  AFFECTATIONS 
		 * 
		 * 1 :  Recupérer les affectation d'un utilisateurs (communes affectées à un superviseur )
		 * 
		 ***************************************************************/
		
		List<CoupleDTO> loadAffectationsByIdUser(Long idUser) throws EntityDBDAOException;	
}
