package sn.awi.pgca.business.service;

import java.util.List;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.web.dto.ProduitDTO;
import sn.awi.pgca.web.dto.VentesDTO;

/**
 * Interface offrant tous les services du magasinier 
 * 
 * @author Mouhamed FAYE
 */
public interface IManagerService {

	public List<VentesDTO> loadAllVentes() throws EntityDBDAOException;
	
	
	public int loadOlenAttenteDeValidation(Long idProgramme) throws EntityDBDAOException;

	
}
