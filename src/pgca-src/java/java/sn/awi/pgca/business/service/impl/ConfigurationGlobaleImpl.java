package sn.awi.pgca.business.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sn.awi.pgca.business.dao.ModelDAO;
import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.service.IConfigurationGlobale;
import sn.awi.pgca.business.service.mock.ParametreParPays;
import sn.awi.pgca.dataMapping.DTOFactory;
import sn.awi.pgca.dataMapping.ModelFactory;
import sn.awi.pgca.dataModel.ParametreGlobaux;
import sn.awi.pgca.utils.UtilString;
import sn.awi.pgca.web.dto.ConfigurationGlobaleDTO;

public class ConfigurationGlobaleImpl implements IConfigurationGlobale {
	/**
	 * Logger.
	 */
	private static final Log	logger	= LogFactory.getLog(ConfigurationGlobaleImpl.class);

	/**
	 * DAO pour la persistance d'un User.
	 */
	private ModelDAO					modelDAO;

	private ModelFactory			modelFactory;

	private DTOFactory				dtoFactory;
	
	@Override
	public ConfigurationGlobaleDTO loadInfoConfGlobale() {
		logger.info("chargement de la configuration globale");
		try {
			List<ParametreGlobaux> lParametreGlobauxs = modelDAO.loadParametreGlobaux();
			ConfigurationGlobaleDTO configurationGlobaleDTO = new ConfigurationGlobaleDTO();
			for(ParametreGlobaux p : lParametreGlobauxs) {
				if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_RESET_SEQ_NUM_RCCM_BY_YEAR)) {
					String valeur = p.getValue_param();
					configurationGlobaleDTO.setRazSequenceNumRccmByYear(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_RESET_SEQ_NUM_ORDRE_BY_YEAR)) {
					String valeur = p.getValue_param();
					configurationGlobaleDTO.setRazSequenceNumOrdreByYear(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_RESET_SEQ_NUM_QUIT_BY_YEAR)) {
					String valeur = p.getValue_param();
					configurationGlobaleDTO.setRazSequenceNumQuittanceByYear(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_RESET_SEQ_NUM_SURETE_BY_YEAR)) {
					String valeur = p.getValue_param();
					configurationGlobaleDTO.setRazSequenceNumSureteByYear(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_LANGUE)) {
					configurationGlobaleDTO.setLangue(p.getValue_param());
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_NB_CARAC_ANNEE)) {
					String valeur = p.getValue_param();
					configurationGlobaleDTO.setNbCaractereAnnee(Integer.parseInt(valeur));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_NB_CARAC_SEQ_NUM_ORDRE)) {
					String valeur = p.getValue_param();
					configurationGlobaleDTO.setNbCaractereSeqNumOrdre(Integer.parseInt(valeur));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_NB_CARAC_SEQ_QUITTANCE)) {
					String valeur = p.getValue_param();
					configurationGlobaleDTO.setNbCaractereSeqQuittance(Integer.parseInt(valeur));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_PREFIX_NUM_ORDRE_BY_YEAR)) {
					String valeur = p.getValue_param();
					configurationGlobaleDTO.setPrefixNumOrdreByYear(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_PREFIX_NUM_QUIT_BY_YEAR)) {
					String valeur = p.getValue_param();
					configurationGlobaleDTO.setPrefixNumQuittanceByYear(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_PREFIX_NUM_RCCM_BY_YEAR)) {
					String valeur = p.getValue_param();
					configurationGlobaleDTO.setPrefixNumRccmByYear(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_PREFIX_NUM_SURETE_BY_YEAR)) {
					String valeur = p.getValue_param();
					configurationGlobaleDTO.setPrefixNumSureteByYear(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_ADD_RCCMTEXT_2_NUM_RCCM)) {
					String valeur = p.getValue_param();
					configurationGlobaleDTO.setAddRCCMTextToNumRccm(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_ADD_RCCMTEXT_2_NUM_SURETE)) {
					String valeur = p.getValue_param();
					configurationGlobaleDTO.setAddRCCMTextToNumSurete(UtilString.equal(valeur, "true"));
				}
			}
			return configurationGlobaleDTO;
		}catch (EntityDBDAOException ede) {
			return null;
		}
	}

	@Override
	public void sauvegarderInfoConfGlobale(ConfigurationGlobaleDTO configurationGlobaleDTO) {
		// TODO Auto-generated method stub

	}

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
