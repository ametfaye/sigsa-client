package sn.awi.pgca.business.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sn.awi.pgca.business.dao.ModelDAO;
import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.service.IManagerService;
import sn.awi.pgca.web.dto.VentesDTO;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@Named("managerService")
public class IManagerServiceImpl implements IManagerService , Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8240918928016202888L;
	/**
	 * DAO pour la persistance.
	 */
	@Inject
	private ModelDAO modelDAO;

	
	public ModelDAO getModelDAO() {
		return modelDAO;
	}

	public void setModelDAO(ModelDAO modelDAO) {
		this.modelDAO = modelDAO;
	}

	@Override
	public List<VentesDTO> loadAllVentes() throws EntityDBDAOException {
		 return null;   //c callServiceRecuperationVentes
	}

	@Override
	public int loadOlenAttenteDeValidation(Long idProgramme) throws EntityDBDAOException {
		
		return modelDAO.loadOLenAttenteDeValidation(idProgramme);
	}

	

	/***************   S T O C K  S E R V I C E S********************************/

		
	

}
