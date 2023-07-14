package sn.awi.pgca.business.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sn.awi.pgca.business.dao.ModelDAO;
import sn.awi.pgca.business.exception.ConnectionException;
import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.exception.UtilisateurException;
import sn.awi.pgca.business.service.IConnectionService;
import sn.awi.pgca.business.service.mock.ParametreParPays;
import sn.awi.pgca.dataModel.ParametreGlobaux;
import sn.awi.pgca.dataModel.PointDeVente;
import sn.awi.pgca.dataModel.Utilisateur;
import sn.awi.pgca.dataModel.UtilisateurTheme;
import sn.awi.pgca.utils.Password;
import sn.awi.pgca.utils.UtilString;
import sn.awi.pgca.web.bean.ConstantPGCA;
import sn.awi.pgca.web.dto.ConnectionDTO;
import sn.awi.pgca.web.dto.ThemeDTO;

@Named("connectionService")
public class ConnectionServiceImpl implements IConnectionService {

	/**
	 * Logger.
	 */
	private static final Log LOG = LogFactory.getLog(ConnectionServiceImpl.class);

	@Inject
	private ModelDAO modelDAO;

	public ConnectionServiceImpl() {
		super();
	}

	@Override
	public ThemeDTO changethemene(Long idUser, ThemeDTO themedto) throws ConnectionException, EntityDBDAOException {
		Utilisateur user = (Utilisateur) modelDAO.getEntityDBById(idUser, Utilisateur.class);

		if (user != null) {
			UtilisateurTheme userTheme = (UtilisateurTheme) modelDAO.getEntityDBById(themedto.getIdTheme(),
					UtilisateurTheme.class);

			if (userTheme != null)
				user.setTheme(userTheme);
			modelDAO.save(user);

			themedto.setColorTheme(userTheme.getThemeColor());
			themedto.setNomTheme(userTheme.getThemeName());
			themedto.setHeaderTheme(userTheme.getThemeHeader());

			return themedto;
		}

		return null;
	}

	public boolean validate(ConnectionDTO dto) throws ConnectionException {
		Utilisateur utilisateur = null;
		utilisateur = modelDAO.getUtilisateurByCodeAndPassword(dto.getCodeUtilisateur(), dto.getMotDePasse());

		if (utilisateur == null) {
			dto.setMsgEchecConnection("identifiants invalides");
			return false;
		}

		if (utilisateur != null && !utilisateur.isEst_valide()) {
			dto.setMsgEchecConnection("votre compte est vérouillé par l'administrateur");
			return false;
		}

		dto.setId(utilisateur.getId());
		LOG.info("utilisateur " + utilisateur.getCodeutilisateur());
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(true);
			if (session != null) {

				// donnees page details utilisateur
				session.setAttribute("connectedUserName",
						utilisateur.getPersonne().getPrenom() + " " + utilisateur.getPersonne().getNom());
				session.setAttribute("connectedUserid", utilisateur.getId());
				session.setAttribute("connectedUserPersonneid", utilisateur.getPersonne().getId());
				session.setAttribute("connectedUserProfilID", utilisateur.getProfil().getId());

				session.setAttribute("connectedUserPointPhysique", "Sedab"); // par
																				// defaut

				if (utilisateur.getProfil().getId() == 5) // Magasinier
				{
					try {
						List<PointDeVente> listDesPointDeVenteAff = modelDAO
								.loadAllPointDeventeOfUser(utilisateur.getId());

						if (listDesPointDeVenteAff != null) {
							for (PointDeVente pv : listDesPointDeVenteAff) {
								// le premier pv avec un statut affecte (0) est
								// celui par defaut : TODO : prendre le dernier
								// ayant la date la plus ancienne comme default
								if (pv.isEstaffecte()) {
									PointDeVente pvParDefaut = pv;
									session.setAttribute("connectedUserStockid", pvParDefaut.getStock().getStock_id());
									session.setAttribute("connectedUserIdCommune", pvParDefaut.getCommune().getId());
									session.setAttribute("connectedUserStockLibelle",
											pvParDefaut.getStock().getLibelle());

									session.setAttribute("connectedUserPointPhysique", pvParDefaut.getLibelle());
									session.setAttribute("idPointdeVente", pvParDefaut.getPtv_id());

									break;
								}

							}
							dto.setNbPvaffectes(listDesPointDeVenteAff.size());
						}
					} catch (EntityDBDAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (utilisateur.getProfil().getId() == 3) // Agent saisie
																	// ont des
																	// points de
																	// collecte
				{

					if (utilisateur.getPointdeCollecte().getStock() != null) {
						session.setAttribute("connectedUserStockid",
								utilisateur.getPointdeCollecte().getStock().getStock_id());
						session.setAttribute("connectedUserStockLibelle",
								utilisateur.getPointdeCollecte().getStock().getLibelle());
						session.setAttribute("connectedUserPointPhysique",
								utilisateur.getPointdeCollecte().getLibelle());
						session.setAttribute("idPointdeCollecte", utilisateur.getPointdeCollecte().getPdc_id());

					}
				} else if (utilisateur.getProfil().getId() == 4) // MAGASINIER DE STOCK AVEC OPTION VENTE
				{

					if (utilisateur.getPointdeCollecte() != null) {
						session.setAttribute("connectedUserStockid", utilisateur.getPointdeCollecte().getStock().getStock_id());
//						session.setAttribute("connectedUserStockLibelle",
//								"Magasin SEDAB -" + utilisateur.getPointdeCollecte().getLibelle());
						session.setAttribute("connectedUserPointPhysique",
								"Magasin SEDAB -  " + utilisateur.getPointdeCollecte().getLibelle());
						//session.setAttribute("idCommuneCordinateur", utilisateur.getCommuneCordinateur().getId());
						session.setAttribute("idPointDeCollecte", utilisateur.getPointdeCollecte().getPdc_id());
					}

				

					// session.setAttribute("connectedUserStockid",
					// utilisateur.getPointdeCollecte().getStock().getId());
					// session.setAttribute("idPointdeCollecte",
					// utilisateur.getPointdeCollecte().getPdc_id());

				}

				// donnees page details utilisateur
				session.setAttribute("profil", utilisateur.getProfil().getCode());
				session.setAttribute("profilCode", utilisateur.getProfil().getCode());
				session.setAttribute("profilLibelle", utilisateur.getProfil().getLibelle());
				session.setAttribute("theme", utilisateur.getThemeChoisi());

				session.setAttribute(ConstantPGCA.SESSION_USER_AUTHORISE, true);
				session.setAttribute(ConstantPGCA.SESSION_NOM_UTILISATEUR, utilisateur.getCodeutilisateur());
				session.setAttribute(ConstantPGCA.SESSION_NOM_USER, utilisateur.getPersonne().getNom());
				session.setAttribute(ConstantPGCA.SESSION_PRENOM_USER, utilisateur.getPersonne().getPrenom());
				session.setAttribute(ConstantPGCA.SESSION_ID_USER, utilisateur.getId().longValue() + "");
				session.setAttribute("passUser", utilisateur.getMotdepasse());

				LOG.info("Session is OK : utilisateur " + utilisateur.getCodeutilisateur());

				// recuperation des indicateurs

			} else {
				LOG.info("Session is NULL : utilisateur " + utilisateur.getCodeutilisateur());
			}
		}
		dto.setMdpmodifier(utilisateur.isMdpamodifier());

		try {
			if (utilisateur.getNombredeConnexion() != 0)
				utilisateur.setNombredeConnexion(utilisateur.getNombredeConnexion() + 1);
			else
				utilisateur.setNombredeConnexion(1);
			Date today = new Date();
			utilisateur.setDateDerniereConnexion(new java.sql.Timestamp(today.getTime()));
			modelDAO.save(utilisateur);
		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Validation de la connexion du compte de l'utilisateur.
	 * 
	 * @param dto
	 *            Le DTO contenant les informations sur la connexion
	 * @throws ConnectionException
	 *             Exception en cas d'erreur technique lors de la connexion
	 * 
	 */
	public void validateAdmin(ConnectionDTO dto) throws ConnectionException {

	}

	/**
	 * @return the ModelDAO
	 */
	public ModelDAO getModelDAO() {
		return modelDAO;
	}

	/**
	 * @param ModelDAO
	 *            the ModelDAO to set
	 */
	public void setModelDAO(ModelDAO modelDAO) {
		this.modelDAO = modelDAO;
	}

	@Override
	public Utilisateur getUserByCode(String codeUtilisateur) {

		Utilisateur user = null;
		try {

			return user = modelDAO.getUtilisateurbyCode(codeUtilisateur);

		} catch (UtilisateurException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;

	}

	@Override
	public String getUserType(String codeProfil) throws ConnectionException {

		return null;

	}

	@Override
	public void seDeconnecter() throws ConnectionException {
		try {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			if (request != null) {
				HttpSession session = request.getSession(false);
				if (session != null) {
					String nomUser = (String) session.getAttribute("nomUtilisateur");
					LOG.info("Début déconnexion utilisateur " + nomUser + ", session : " + session);

					session.invalidate();

					/*
					 * session.setAttribute("connectedUserStockid", null);
					 * session.setAttribute("connectedUserStockLibelle", null);
					 * session.setAttribute("connectedUserPointPhysique", null);
					 * session.setAttribute("connectedUserid", null);
					 * session.setAttribute("connectedUserPersonneid", null);
					 * 
					 * session.setAttribute("entiteJuridique", null);
					 * session.setAttribute("logged-in", "false");
					 * session.setAttribute(ConstantPGCA.
					 * SESSION_NOM_UTILISATEUR, null);
					 * session.setAttribute(ConstantPGCA.SESSION_USER_AUTHORISE,
					 * null); session.setAttribute("codeUtilisateur", null);
					 * session.setAttribute("entiteJuridique", null);
					 * session.setAttribute("profil", null);
					 * session.setAttribute(ConstantPGCA.SESSION_CODE_3_L_ENJU,
					 * null);
					 * session.setAttribute(ConstantPGCA.SESSION_CODE_REGION,
					 * null); session.setAttribute("codePays", null);
					 * session.setAttribute("UserEstSignataire", null);
					 * 
					 * // donnees page details utilisateur
					 * session.setAttribute("profilUser", null);
					 * session.setAttribute("nomUser", null);
					 * session.setAttribute("prenomUser", null);
					 * session.setAttribute("entiteJuridiqueUserId", null);
					 * session.setAttribute("entiteJuridiqueUser", null);
					 * session.setAttribute("passUser", null);
					 * session.setAttribute("situationMatrimonialUser", null);
					 * session.setAttribute("paysUser", null);
					 * session.setAttribute(ConstantPGCA.SESSION_HABILITATION,
					 * null);
					 * session.setAttribute(ConstantPGCA.SESSION_CODE_PROFIL,
					 * null);
					 * 
					 */

					LOG.info("Fin déconnexion utilisateur " + nomUser + " session : " + session);
				}
			}
		} catch (Exception e) {
			throw new ConnectionException("Error deconnxion utilisateur ", e);
		}
	}

	@Override
	public void updatePassword(ConnectionDTO connectionDTO) throws ConnectionException {
		Utilisateur utilisateur = null;
		try {
			utilisateur = modelDAO.getUtilisateurByCodeAndPassword(connectionDTO.getCodeUtilisateur(),
					connectionDTO.getMotDePasse());
			if (utilisateur == null)
				throw new ConnectionException("Utilisateur non trouvé");
		} catch (ConnectionException ce) {
			LOG.error("Impossible de retrouver l'utilisateur ayant utilisé cet identifiant de connexion", ce);
			throw new ConnectionException(
					"Impossible de retrouver l'utilisateur ayant utilisé cet identifiant de connexion", ce);
		}
		LOG.info("utilisateur " + utilisateur.getCodeutilisateur());

		String pwd = null;
		try {
			Password password = new Password();
			pwd = password.getEncodedPasswordWithoutCharset(connectionDTO.getNewPassword());
		} catch (UnsupportedEncodingException uee) {
			throw new ConnectionException("Erreur init password ", uee);
		}
		try {
			utilisateur.setMotdepasse(pwd);
			utilisateur.setMdpamodifier(false);
			utilisateur.setMotdepasse_clair("");
			modelDAO.save(utilisateur);
			connectionDTO.setMdpmodifier(false);

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			if (request != null) {
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.setAttribute(ConstantPGCA.SESSION_USER_AUTHORISE, !utilisateur.isMdpamodifier());
				}
			}
		} catch (EntityDBDAOException ede) {
			throw new ConnectionException("Impossible de sauvegarder l'utilisateur " + utilisateur.getCodeutilisateur(),
					ede);
		}
	}

	private ParametreParPays loadParametrePays(String idPays) {
		if (!UtilString.isCorrect(idPays))
			return null;
		try {
			List<ParametreGlobaux> lParametreGlobauxs = modelDAO.loadParametreGlobaux(idPays);
			if (lParametreGlobauxs == null || lParametreGlobauxs.size() == 0)
				return null;
			ParametreParPays parametreParPays = new ParametreParPays();
			for (ParametreGlobaux p : lParametreGlobauxs) {
				if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_RESET_SEQ_NUM_RCCM_BY_YEAR)) {
					String valeur = p.getValue_param();
					parametreParPays.setRazSequenceNumRccmByYear(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_RESET_SEQ_NUM_ORDRE_BY_YEAR)) {
					String valeur = p.getValue_param();
					parametreParPays.setRazSequenceNumOrdreByYear(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_RESET_SEQ_NUM_QUIT_BY_YEAR)) {
					String valeur = p.getValue_param();
					parametreParPays.setRazSequenceNumQuittanceByYear(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_RESET_SEQ_NUM_SURETE_BY_YEAR)) {
					String valeur = p.getValue_param();
					parametreParPays.setRazSequenceNumSureteByYear(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_LANGUE)) {
					parametreParPays.setLangue(p.getValue_param());
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_NB_CARAC_ANNEE)) {
					String valeur = p.getValue_param();
					parametreParPays.setNbCaractereAnnee(Integer.parseInt(valeur));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_NB_CARAC_SEQ_NUM_ORDRE)) {
					String valeur = p.getValue_param();
					parametreParPays.setNbCaractereSeqNumOrdre(Integer.parseInt(valeur));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_NB_CARAC_SEQ_QUITTANCE)) {
					String valeur = p.getValue_param();
					parametreParPays.setNbCaractereSeqQuittance(Integer.parseInt(valeur));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_PREFIX_NUM_ORDRE_BY_YEAR)) {
					String valeur = p.getValue_param();
					parametreParPays.setPrefixNumOrdreByYear(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_PREFIX_NUM_QUIT_BY_YEAR)) {
					String valeur = p.getValue_param();
					parametreParPays.setPrefixNumQuittanceByYear(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_PREFIX_NUM_RCCM_BY_YEAR)) {
					String valeur = p.getValue_param();
					parametreParPays.setPrefixNumRccmByYear(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_PREFIX_NUM_SURETE_BY_YEAR)) {
					String valeur = p.getValue_param();
					parametreParPays.setPrefixNumSureteByYear(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_ADD_RCCMTEXT_2_NUM_RCCM)) {
					String valeur = p.getValue_param();
					parametreParPays.setAddRCCMTextToNumRccm(UtilString.equal(valeur, "true"));
				} else if (UtilString.equal(p.getCode_param(), ParametreParPays.CODE_ADD_RCCMTEXT_2_NUM_SURETE)) {
					String valeur = p.getValue_param();
					parametreParPays.setAddRCCMTextToNumSurete(UtilString.equal(valeur, "true"));
				}
			}
			return parametreParPays;
		} catch (EntityDBDAOException ede) {
			LOG.warn("Erreur loading parametre pays ", ede);
			return null;
		} catch (Exception e) {
			LOG.warn("Erreur loading parametre pays ", e);
			return null;
		}
	}

}