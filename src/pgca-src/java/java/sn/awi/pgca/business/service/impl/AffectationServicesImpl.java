package sn.awi.pgca.business.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import sn.awi.pgca.business.dao.ModelDAO;
import sn.awi.pgca.business.dao.StockResiduelDAO;
import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.service.IAffectationService;
import sn.awi.pgca.business.service.ICommonService;
import sn.awi.pgca.business.service.ITresorerieService;
import sn.awi.pgca.business.service.Inotification;
import sn.awi.pgca.utils.UtilString;
import sn.awi.pgca.web.dto.CoupleDTO;
import sn.awi.pgca.web.dto.IntrantDTO;

/**
 * @author Amet@Faye
 *
 */
@Named("servicesAffectation")
public class AffectationServicesImpl implements IAffectationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7431468624606631781L;

	/**
	 * DAO pour la persistance.
	 */
	@Inject
	private ModelDAO modelDAO;
	@Inject
	private ITresorerieService tresorerie;
	@Inject
	private Inotification notifierService;
	@Inject
	private ICommonService commonService;
	@Inject
	private StockResiduelDAO stockService;

	UtilString utils = new UtilString();

	/***************
	 * AFFECTATIONS DES POINTS DE VENTES  AUX SUPERVISEURS  
	 ********************************/

	AffectationServicesImpl() {
	}

	@Override
	public List<CoupleDTO> loadAffectationsByIdUser(Long idUser) throws EntityDBDAOException {		
		return modelDAO.loadAllIntrantFromAffectations(idUser);
	}
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ModelDAO getModelDAO() {
		return modelDAO;
	}

	public ITresorerieService getTresorerie() {
		return tresorerie;
	}

	public Inotification getNotifierService() {
		return notifierService;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public StockResiduelDAO getStockService() {
		return stockService;
	}

	public UtilString getUtils() {
		return utils;
	}

	public void setModelDAO(ModelDAO modelDAO) {
		this.modelDAO = modelDAO;
	}

	public void setTresorerie(ITresorerieService tresorerie) {
		this.tresorerie = tresorerie;
	}

	public void setNotifierService(Inotification notifierService) {
		this.notifierService = notifierService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public void setStockService(StockResiduelDAO stockService) {
		this.stockService = stockService;
	}

	public void setUtils(UtilString utils) {
		this.utils = utils;
	}

	
	
	
}
