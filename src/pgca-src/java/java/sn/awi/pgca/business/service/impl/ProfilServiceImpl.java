package sn.awi.pgca.business.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sn.awi.pgca.business.dao.ModelDAO;
import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.exception.ProfilException;
import sn.awi.pgca.business.service.IBoiteANumero;
import sn.awi.pgca.business.service.IProfilService;
import sn.awi.pgca.dataMapping.DTOFactory;
import sn.awi.pgca.dataMapping.ModelFactory;
import sn.awi.pgca.dataModel.PointDeCollecte;
import sn.awi.pgca.dataModel.Profil;
import sn.awi.pgca.web.bean.ConstantPGCA;
import sn.awi.pgca.web.dto.ProfilDTO;

/** * 
 * @author AWA Consulting
 */
@Named("profilService")
public class ProfilServiceImpl implements IProfilService,  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1498513172536495198L;

	/**
	 * Logger.
	 */
	private static final Log	LOG	= LogFactory.getLog(ProfilServiceImpl.class);

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

	private IBoiteANumero			boiteANumero;

	public ProfilServiceImpl() {
		super();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean creerProfil(ProfilDTO profildto) throws ProfilException {
		try {
			if (profildto.getCode() == null)
				LOG.error(" Le code du profil est null");

			LOG.info("MB ::: Tentative de Recuperation du profil <" + profildto.getLibelle() + "> avec le code< " + profildto.getCode() + ">");
			Profil newprofil = new Profil();
			modelFactory.createProfil(newprofil, profildto);

			/*** code de l'entite juridique */
			
			/*String codeEj = null;
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			if (request != null) {
				HttpSession session = request.getSession(false);
				codeEj = (String) session.getAttribute(ConstantPGCA.SESSION_ID_ENTITE_JURIDIQUE);
			}

			if (codeEj != null) {
				PointDeCollecte ej = (PointDeCollecte) modelDAO.getEntityDBById(new Long(codeEj), PointDeCollecte.class);

				if (ej != null)
					newprofil.setEntiteJuridique(ej);
			}*/
		
			modelDAO.save(newprofil);

		
			LOG.info("Profil  <" + profildto.getLibelle() + ">  a �t� cr�� avec success");
			return true;

		} catch (Exception e) {
			throw new ProfilException("Impossible de creer  le profil");
		}
	}

	@Override
	public ProfilDTO getProfilById(Long id) throws ProfilException {
		try {
			Profil prf = (Profil) modelDAO.getEntityDBById(id, Profil.class);
			return dtoFactory.createProfilDTO(prf);

		} catch (Exception e) {
			throw new ProfilException("Impossible de recuperer le profil avec id <" + id + ">");
		}
	}






	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean removeProfil(Long id) throws ProfilException {
		try {
			// Profil prf = new Profil();
			// prf = (Profil) modelDAO.getProfilbyCode(code);

			Profil prf = (Profil) modelDAO.getEntityDBById(id, Profil.class);
			if (prf != null) {
				LOG.info("Tentative de suppression du profil avec lid <" + id + ">");
				modelDAO.removeModel(prf);
				LOG.info("Profil  supprimé ...");
				return true;
			}
		} catch (Exception e) {
			LOG.error("Impossible de supprimer le profil" + id + ", car il  est lié a des utilisateurs", e);
			return false;
		}
		return false;
	}

	public List<ProfilDTO> recupererAllProfil() throws ProfilException {
		try {
			List<Profil> lProfil = modelDAO.loadProfil();
			return dtoFactory.createListProfilDTO(lProfil);
		} catch (Exception e) {
			throw new ProfilException("error recup all profil", e);
		}
	}

	@Override
	public String getCodeLastProfil() throws ProfilException {
		try {
			return "PROF_" + boiteANumero.getNumeroSequenceCodeProfil();
		} catch (Exception e) {
			LOG.error("Erreur recuperation numero de séquence code profil" + e.getStackTrace());
			throw new ProfilException("Erreur recuperation numero de séquence code profil", e);
		}
	}

	/**
	 * @return the ModelDAO
	 */
	public ModelDAO getModelDAO() {
		return modelDAO;
	}

	/**
	 * @param ModelDAO
	 *          the ModelDAO to set
	 */
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

	@Override
	public ProfilDTO recupProfil(String code) throws ProfilException {
		try {
			Profil profil = modelDAO.getProfilbyCode(code);
			ProfilDTO pDTO = dtoFactory.createProfilDTO(profil);
			return pDTO;
		} catch (EntityDBDAOException ee) {
			throw new ProfilException(ee);
		}
	}

	public IBoiteANumero getBoiteANumero() {
		return boiteANumero;
	}

	public void setBoiteANumero(IBoiteANumero boiteANumero) {
		this.boiteANumero = boiteANumero;
	}

}
