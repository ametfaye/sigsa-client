package sn.awi.pgca.business.service.impl;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sn.awi.pgca.business.dao.ModelDAO;
import sn.awi.pgca.business.service.IAdministrationService;
import sn.awi.pgca.dataMapping.DTOFactory;
import sn.awi.pgca.dataMapping.ModelFactory;

@Named("adminService")
public class AdministrationServiceImpl implements IAdministrationService , Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -31462018701724520L;

	/**
	 * Logger.
	 */
	private static final Log	logger	= LogFactory.getLog(AdministrationServiceImpl.class);

	/**
	 * DAO pour la persistance d'un profil.
	 */
	@Inject
	private ModelDAO					modelDAO;

	/**
	 * Constructeur par défaut.
	 */
	@Inject
	private ModelFactory			modelFactory;

	/**
	 * Constructeur par défaut.
	 */
	@Inject
	private DTOFactory				dtoFactory;







	public ModelDAO getModelDAO() {
		return modelDAO;
	}

	public void setModelDAO(ModelDAO modelDAO) {
		this.modelDAO = modelDAO;
	}

	public ModelFactory getModelFactory() {
		return modelFactory;
	}

	public void setModelFactory(ModelFactory modelFactory) {
		this.modelFactory = modelFactory;
	}

	public DTOFactory getDtoFactory() {
		return dtoFactory;
	}

	public void setDtoFactory(DTOFactory dtoFactory) {
		this.dtoFactory = dtoFactory;
	}

}
