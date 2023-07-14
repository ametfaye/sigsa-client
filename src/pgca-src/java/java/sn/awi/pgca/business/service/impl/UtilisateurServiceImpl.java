package sn.awi.pgca.business.service.impl;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sn.awi.pgca.business.dao.ModelDAO;
import sn.awi.pgca.business.exception.ConnectionException;
import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.exception.UtilisateurException;
import sn.awi.pgca.business.service.IUtilisateurService;
import sn.awi.pgca.business.service.Inotification;
import sn.awi.pgca.dataMapping.DTOFactory;
import sn.awi.pgca.dataMapping.ModelFactory;
import sn.awi.pgca.dataModel.AffectationsGestionPointDeVentes;
import sn.awi.pgca.dataModel.BonDeLivraison;
import sn.awi.pgca.dataModel.Commune;
import sn.awi.pgca.dataModel.Personne;
import sn.awi.pgca.dataModel.PointDeCollecte;
import sn.awi.pgca.dataModel.PointDeVente;
import sn.awi.pgca.dataModel.Profil;
import sn.awi.pgca.dataModel.ProgrammeAgricol;
import sn.awi.pgca.dataModel.Stock;
import sn.awi.pgca.dataModel.Utilisateur;
import sn.awi.pgca.dataModel.UtilisateurTheme;
import sn.awi.pgca.utils.Password;
import sn.awi.pgca.utils.UtilString;
import sn.awi.pgca.web.bean.ConstantPGCA;
import sn.awi.pgca.web.bean.SessionManagedBean;
import sn.awi.pgca.web.dto.ConnectionDTO;
import sn.awi.pgca.web.dto.CoupleDTO;
import sn.awi.pgca.web.dto.UtilisateurDTO;

@Named("utilisateurService")
public class UtilisateurServiceImpl implements IUtilisateurService, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1687497090159929840L;

	/**
	 * Logger.
	 */
	private static final Log LOG = LogFactory.getLog(UtilisateurServiceImpl.class);

	/**
	 * DAO pour la persistance d'un utilisateur.
	 */
	@Inject
	private ModelDAO modelDAO;

	/**
	 * Constructeur par défaut.
	 */
	@Inject
	private ModelFactory modelFactory;

	/**
	 * Constructeur par défaut.
	 */
	@Inject
	private DTOFactory dtoFactory;

	/**
	 * Chemin de stockage des fichiers pdf de changement de password
	 */

	@Inject
	private Inotification notifierService;

	@Value("${changepassword.dossier}")
	private String filesPath;

	@Value("${report.docgen.password}")
	private String iReportfileJRXMLChangePassword;

	private static final String CODE_TYPE_DOCUMENT = "UserPassword";

	public UtilisateurServiceImpl() {
		super();
	}

	public boolean creerUtilisateur(UtilisateurDTO utilisateurdto) throws UtilisateurException {
		LOG.info("Création de utilisateur PGCA <" + utilisateurdto.getCode());
		try {
			Utilisateur user = modelDAO.getUtilisateurbyCode(utilisateurdto.getEmail());
			if (user != null)
				throw new UtilisateurException(
						"L'utilisateur '" + utilisateurdto.getEmail() + "' existe déjà dans la plateforme.");
		} catch (UtilisateurException ue) {
			throw ue;
		}

		Password password = new Password();
		try {
			String pwd = password.getEncodedPasswordWithoutCharset(ConstantPGCA.DEFAULT_MDP);
			utilisateurdto.setPassword(pwd);
		} catch (UnsupportedEncodingException e) {
			throw new UtilisateurException("Erreur de création de USER", e);
		}

		List<AffectationsGestionPointDeVentes> listAffectationsSuperviseur = new ArrayList<>();

		Personne personne = new Personne();
		modelFactory.createPersonne(personne, utilisateurdto);

		Utilisateur newutilisateur = new Utilisateur();
		modelFactory.createUtilisateur(newutilisateur, utilisateurdto);

		newutilisateur.setMdpamodifier(false); // a faire en lot 2
		newutilisateur.setEst_valide(true);
		try {
			modelDAO.save(personne);
			newutilisateur.setTheme((UtilisateurTheme) modelDAO.getEntityDBById(1L, UtilisateurTheme.class));

		} catch (EntityDBDAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/****** Affectation Profil ***********/
		if (UtilString.isCorrect(utilisateurdto.getProfilId())) {
			Profil p;
			try {
				p = (Profil) modelDAO.getEntityDBById(new Long(utilisateurdto.getProfilId()), Profil.class);
				if (p != null) {
					newutilisateur.setProfil(p);

					// agent de saisie , le point de stock est de BASE EST DAKAR
					if (p.getId() == 3) {
						newutilisateur.setPointdeCollecte(
								(PointDeCollecte) modelDAO.getEntityDBById(0L, PointDeCollecte.class));
					}
				} else {
					LOG.error("Erreur de recupération du profil pendant la création de l'utilisateur");
					return false;
				}
			} catch (NumberFormatException | EntityDBDAOException ex) {
				LOG.error("Erreur de recupération du profil pendant la création de l'utilisateur");
				ex.printStackTrace();
			}
		}
		/****** Affectation Profil OK **********/

		Long typeProfil = Long.parseLong(utilisateurdto.getProfilId());

		/****** Affectation ***********/
		if (typeProfil == 4) {
			try {
				PointDeVente communePV = (PointDeVente) modelDAO
						.getEntityDBById(new Long(utilisateurdto.getPointDeVentetId()), PointDeVente.class);

				if (null != communePV) {
					// UN MAGASIER EST EST UN POINT DE COLLECTE
					PointDeCollecte pc = new PointDeCollecte();
					pc.setLibelle("Stock Magasin " + communePV.getLibelle());
					pc.setStock(communePV.getStock());
					pc.setGerant(personne);
					modelDAO.save(pc);
					newutilisateur.setPointdeCollecte(pc);
				}

			} catch (Exception ex) {
				LOG.error("Erreur de recupération du point de collecte pendant la création de l'utilisateur");
				ex.printStackTrace();
			}
		}
		try {
			LOG.debug("tententive d'enregistrement de utilisateur <" + utilisateurdto.getCode());
			newutilisateur.setPersonne(personne);

			newutilisateur.setDateCreation(new Timestamp(new Date().getTime()));
			modelDAO.save(newutilisateur);

			// Un magasinier/gerant peut avoir un ou plusieur PV (les
			// superviseurs ont acce
			if (typeProfil.equals(5L)) {
				List<CoupleDTO> listAffectations = utilisateurdto.getListAffectationsPV();

				if (null != listAffectations) {

					for (CoupleDTO c : listAffectations) {
						AffectationsGestionPointDeVentes af = new AffectationsGestionPointDeVentes();
						af.setSuperviseurPointDeVente(newutilisateur);
						af.setStatut(0);
						af.setDateAffectation(new Date());
						af.setPtv_id((PointDeVente) modelDAO.getEntityDBById(c.getClef(), PointDeVente.class));

						try {
							af.setAuteurAffecttaion(SessionManagedBean.getSessionDataByTag("connectedUserName"));
						} catch (Exception e) {
							LOG.error("Erreur creation USER <" + utilisateurdto.getCode() + "> :" + e.getMessage(), e);
						}

						af.setProgrammeConcerne((ProgrammeAgricol) modelDAO
								.getEntityDBById(ConstantPGCA.CAMPAGNE_DEFAULT, ProgrammeAgricol.class));
						modelDAO.save(af);
					}
				}
			}

			notifierService.envoyerConfirmation(newutilisateur);
			LOG.info("Creation du user <" + utilisateurdto.getLibelle() + "> OK ");

		} catch (EntityDBDAOException e) {
			LOG.error("Enregistrement utilisateur impossible> :" + e.getMessage(), e);
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

	public void validate(ConnectionDTO connectionDTO) throws ConnectionException {

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

	public List<UtilisateurDTO> recupererAllUtilisateur() throws UtilisateurException {
		try {
			List<Utilisateur> lUtilisateur = modelDAO.loadUtilisateur();
			return dtoFactory.createListUtilisateurDTO(lUtilisateur);
		} catch (Exception e) {
			throw new UtilisateurException("error recup all utilisateur", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean modifierUtilisateur(UtilisateurDTO utilisateurdto) throws Exception {
		Utilisateur userModify;
		try {
			userModify = (Utilisateur) modelDAO.getEntityDBById(utilisateurdto.getId(), Utilisateur.class);
			if (userModify == null)
				throw new UtilisateurException("L'utilisateur de code '" + utilisateurdto.getCode() + "' existe déjà");
		} catch (UtilisateurException ue) {
			throw ue;
		} catch (EntityDBDAOException ue) {
			throw new UtilisateurException(ue);
		}
		/*
		 * Tester unicité codeUser Utilisateur user2 =
		 * modelDAO.getUtilisateurbyCode(utilisateurdto.getCode()); if
		 * (user2.getId().longValue() != userModify.getId().longValue()) throw
		 * new UtilisateurException("Utilisateur de ce code existe déjà"); //
		 * initialisation password Password password = new Password(); try {
		 * String pwd =
		 * password.getEncodedPasswordWithoutCharset(utilisateurdto.getCode());
		 * utilisateurdto.setPassword(pwd); } catch
		 * (UnsupportedEncodingException uee) { throw new
		 * UtilisateurException("Erreur init password ", uee); }
		 * modelFactory.createPersonne(userModify.getPersonne(),
		 * utilisateurdto); modelFactory.createUtilisateur(userModify,
		 * utilisateurdto); if
		 * (UtilString.isCorrect(utilisateurdto.getEntiteJuridiqueId())) { try {
		 * PointDeCollecte ej = (PointDeCollecte) modelDAO .getEntityDBById(new
		 * Long(utilisateurdto.getEntiteJuridiqueId()), PointDeCollecte.class);
		 * if (ej != null) userModify.setPointdeCollecte(ej); } catch
		 * (EntityDBDAOException ede) {
		 * LOG.error("EntitéJuridique impossible pour <" +
		 * utilisateurdto.getEntiteJuridiqueId() + "> :" + ede.getMessage(),
		 * ede); throw new UtilisateurException(
		 * "EntitéJuridique impossible pour <" +
		 * utilisateurdto.getEntiteJuridiqueId() + "> :", ede); } }
		 */

		// Rechercher le profil
		if (UtilString.isCorrect(utilisateurdto.getProfilId())) {
			try {
				Profil p = (Profil) modelDAO.getEntityDBById(new Long(utilisateurdto.getProfilId()), Profil.class);
				if (p != null)
					userModify.setProfil(p);

				// Gestion des points de ventes multiples  0 = ACTIF  / 1 = DESACTIF
				if (p.getId() == 5) {
					List<CoupleDTO> affecationsMaj = utilisateurdto.getListAffectationsPV();

					// mode truncate : delete aand recrate
					List<AffectationsGestionPointDeVentes> affectationsActuels = (List<AffectationsGestionPointDeVentes>) modelDAO
							.loadAllPointDeVentesAffectationByUser(userModify.getId());
					
					/*for (AffectationsGestionPointDeVentes c : affectationsActuels) 
						modelDAO.removeModel(c);*/
					
						for (CoupleDTO newData : affecationsMaj) {
								
								// Existe pas et activé :  donc on le créé 
								if (newData.getStatus() == 0 && !pointDeveVenteDejaAffectee(affectationsActuels , newData.getClef()))
								{
									AffectationsGestionPointDeVentes aff = new AffectationsGestionPointDeVentes();
									
									
									aff.setDateModifiiAffectation(new Timestamp(new Date().getTime()));
									aff.setAuteurAffecttaion(SessionManagedBean.getSessionDataByTag("connectedUserName"));
									aff.setDateAffectation(new Timestamp(new Date().getTime()));
									aff.setSuperviseurPointDeVente(userModify);
									aff.setPtv_id((PointDeVente) modelDAO.getEntityDBById(newData.getClef(),  PointDeVente.class));
									aff.setProgrammeConcerne( (ProgrammeAgricol) modelDAO.getEntityDBById(0L,  ProgrammeAgricol.class));
									aff.setStatut(0);

									modelDAO.save(aff);
								} // existe et désactivé : donc on supprime laffectation sur ce point de vente : chercher l'affectation ayant lid du Pv et le supprimé
								else if(newData.getStatus() == 1 && pointDeveVenteDejaAffectee(affectationsActuels , newData.getClef()))
								{
									AffectationsGestionPointDeVentes	affectationAssuprimer = 
											(AffectationsGestionPointDeVentes)  modelDAO.getEntityDBById(newData.getIdAffectation(), AffectationsGestionPointDeVentes.class);
									modelDAO.removeModel(affectationAssuprimer);
								}
						}
				}
			} catch (EntityDBDAOException ede) {
				LOG.error("EntitéJuridique impossible pour <" + utilisateurdto.getEntiteJuridiqueId() + "> :"
						+ ede.getMessage(), ede);
				throw new UtilisateurException(
						"EntitéJuridique impossible pour <" + utilisateurdto.getEntiteJuridiqueId() + "> :", ede);
			}
		}
		try {
			// modelDAO.save(userModify.getPersonne());
			LOG.debug("tententive d'enregistrement de utilisateur <" + utilisateurdto.getEmail());
			// modelDAO.save(userModify);
			LOG.debug("utilisateur  <" + utilisateurdto.getCode() + "créé avec success");
			return true;
		} catch (Exception e) {
			LOG.error(
					"Enregistrement utilisateur impossible pour <" + utilisateurdto.getCode() + "> :" + e.getMessage(),
					e);
			throw new UtilisateurException(
					"Enregistrement utilisateur impossible pour <" + utilisateurdto.getCode() + "> :", e);
		}
	}

	
	
	boolean pointDeveVenteDejaAffectee(List<AffectationsGestionPointDeVentes> affectationsActuels , Long idAffectation)
	{
		for (AffectationsGestionPointDeVentes c : affectationsActuels) 
		{
			if(c.getId_aff().equals(idAffectation))
				return true;
		}
		
		return false;
	}
	
	
	void verifierEtEnrregistrerMaj(AffectationsGestionPointDeVentes aff, List<CoupleDTO> affecationsMaj)
			throws Exception {
		for (CoupleDTO c : affecationsMaj) {
			if (aff.getId_aff().equals(c.getIdAffectation())) {
				if (aff.getStatut() != c.getStatus()) {
					aff.setStatut(c.getStatus());
					aff.setDateModifiiAffectation(new Timestamp(new Date().getTime()));
					aff.setAuteurAffecttaion(SessionManagedBean.getSessionDataByTag("connectedUserName"));
					if (c.getStatus() == 0)
						aff.setDateAffectation(new Timestamp(new Date().getTime()));

					modelDAO.save(aff);
					break;
				}
			}
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean removeUtilisateur(Long id) throws UtilisateurException {
		try {
			Utilisateur utilisateur = (Utilisateur) modelDAO.getEntityDBById(id, Utilisateur.class);

			if (utilisateur != null) {
				LOG.info("Tentative de suppression du utilisateur <" + id + ">");
				modelDAO.removeModel(utilisateur);
				// utilisateur.setEst_valide(false);
				LOG.info("Utilisateur <" + id + "> supprimé ...");
				return true;
			}
		} catch (Exception e) {
			LOG.error("Impossible de supprimer " + id + "l'utilisateur", e);
		}
		return false;
	}

	@Override
	public UtilisateurDTO recupUtilisateur(String code) throws UtilisateurException {
		try {
			Utilisateur utilisateur = modelDAO.getUtilisateurbyCode(code);
			UtilisateurDTO udto = dtoFactory.createUtilisateurDTO(utilisateur);
			return udto;
		} catch (Exception e) {
			throw new UtilisateurException("error recup du user", e);
		}
	}

	@Override
	public UtilisateurDTO recupUtilisateur(Long id) throws UtilisateurException {
		try {
			Utilisateur utilisateur = (Utilisateur) modelDAO.getEntityDBById(id, Utilisateur.class);
			UtilisateurDTO udto = dtoFactory.createUtilisateurDTO(utilisateur);
			/*
			 * if (utilisateur.isMdpamodifier()) { try {
			 * genererChangePasswordDocument(udto); } catch
			 * (UtilisateurException ue) { throw ue; } }
			 */
			return udto;
		} catch (Exception e) {
			throw new UtilisateurException("error recup du user", e);
		}
	}

	@Override
	public boolean verouillerUtilisateur(Long id) throws UtilisateurException {
		try {
			Utilisateur utilisateur = (Utilisateur) modelDAO.getEntityDBById(id, Utilisateur.class);

			if (utilisateur != null) {
				utilisateur.setEst_valide(false);

				modelDAO.save(utilisateur);
				return true;
			} else
				return false;
		} catch (Exception e) {
			throw new UtilisateurException("error recup du user", e);
		}
	}

	@Override
	public boolean reinitialiserMDP(Long id) throws UtilisateurException {
		try {
			Utilisateur utilisateur = (Utilisateur) modelDAO.getEntityDBById(id, Utilisateur.class);

			if (utilisateur != null) {
				String pwd = null;
				Password password = new Password();
				pwd = password.getEncodedPasswordWithoutCharset(ConstantPGCA.PASSWORDToChange);

				utilisateur.setMotdepasse(pwd);
				modelDAO.save(utilisateur);
				return true;
			} else
				return false;
		} catch (Exception e) {
			throw new UtilisateurException("error recup du user", e);
		}
	}

	@Override
	public boolean deverouillerUtilisateur(Long id) throws UtilisateurException {
		try {
			Utilisateur utilisateur = (Utilisateur) modelDAO.getEntityDBById(id, Utilisateur.class);

			if (utilisateur != null) {
				utilisateur.setEst_valide(true);
				modelDAO.save(utilisateur);
				return true;
			} else
				return false;
		} catch (Exception e) {
			throw new UtilisateurException("error recup du user", e);
		}
	}

	@Override
	public void updateMotDePasse(UtilisateurDTO utilisateurDTO) throws UtilisateurException {
		Utilisateur utilisateur = null;
		try {
			try {
				utilisateur = (Utilisateur) modelDAO.getEntityDBById(utilisateurDTO.getId(), Utilisateur.class);
			} catch (EntityDBDAOException e) {
				throw new UtilisateurException("Impossible de charger l'utilisateur : " + e.getMessage());
			}
			if (utilisateur == null)
				throw new ConnectionException("Utilisateur non trouvé");
		} catch (ConnectionException ce) {
			LOG.error("Impossible de retrouver l'utilisateur ayant utilisé cet identifiant de connexion", ce);
			throw new UtilisateurException(
					"Impossible de retrouver l'utilisateur ayant utilisé cet identifiant de connexion", ce);
		}
		LOG.info("utilisateur " + utilisateur.getCodeutilisateur());

		String pwd = null;
		try {
			Password password = new Password();
			pwd = password.getEncodedPasswordWithoutCharset(utilisateurDTO.getNewPassword());
		} catch (UnsupportedEncodingException uee) {
			throw new UtilisateurException("Erreur init password ", uee);
		}
		try {
			utilisateur.setMotdepasse(pwd);
			utilisateur.setMdpamodifier(true);
			utilisateur.setMotdepasse_clair(utilisateurDTO.getNewPassword());
			Date now = new Date();
			utilisateur.setDateDerniereModificationMDP(new java.sql.Timestamp(now.getTime()));
			modelDAO.save(utilisateur);
			utilisateurDTO.setMdpamodifier(true);

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			if (request != null) {
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.setAttribute(ConstantPGCA.SESSION_USER_AUTHORISE, true);
				}
			}
		} catch (EntityDBDAOException ede) {
			throw new UtilisateurException(
					"Impossible de sauvegarder l'utilisateur " + utilisateur.getCodeutilisateur(), ede);
		}

	}

	/*
	 * @Override public void genererChangePasswordDocument(UtilisateurDTO
	 * utilisateurDTO) throws UtilisateurException { Utilisateur utilisateur =
	 * null; try { utilisateur = (Utilisateur)
	 * modelDAO.getEntityDBById(utilisateurDTO.getId(), Utilisateur.class); }
	 * catch (EntityDBDAOException e) { throw new
	 * UtilisateurException("Utilisateur non trouvé avec id " +
	 * utilisateurDTO.getId(), e); } if (utilisateur == null) throw new
	 * UtilisateurException(
	 * "Impossible de retrouver l'utilisateur ayant utilisé cet identifiant de connexion"
	 * ); if (!utilisateur.isMdpamodifier()) throw new UtilisateurException(
	 * "Impossible de générer le document pour un utilisateur déjà modifié son mot de passe"
	 * ); LOG.info("utilisateur " + utilisateur.getCodeutilisateur());
	 * IReportUtilisateurPassword iReportUtilisateurPassword = new
	 * IReportUtilisateurPassword();
	 * iReportUtilisateurPassword.setCodeUser(utilisateur.getCodeutilisateur());
	 * iReportUtilisateurPassword.setNom(utilisateur.getPersonne().getNom());
	 * iReportUtilisateurPassword.setPrenom(utilisateur.getPersonne().getPrenom(
	 * ));
	 * iReportUtilisateurPassword.setGreffe(utilisateur.getPointdeCollecte().
	 * getLibelle());
	 * iReportUtilisateurPassword.setLieuNais(utilisateur.getPersonne().
	 * getLieudenaissance());
	 * iReportUtilisateurPassword.setProfil(utilisateur.getProfil().getLibelle()
	 * ); if (utilisateur.getPersonne().getDatenaissance() != null)
	 * iReportUtilisateurPassword.setDateNais(DateUtils.formatDate(utilisateur.
	 * getPersonne().getDatenaissance()));
	 * iReportUtilisateurPassword.setOldPassword(utilisateur.getMotdepasse_clair
	 * ()); try {
	 * IReportUtilisateurPassword.addUtilisateurs(iReportUtilisateurPassword);
	 * String nomFichier = "ChangePassword_" + utilisateur.getCodeutilisateur()
	 * + ".pdf"; IReportUtils.generatePdfFile(iReportfileJRXMLChangePassword,
	 * nomFichier, filesPath, IReportUtilisateurPassword.getUtilisateurList());
	 * utilisateurDTO.setFichierChangePassword(nomFichier);
	 * IReportUtilisateurPassword.getUtilisateurList().clear(); byte[] flux =
	 * FileUtils.readFileToByteArray(new File(filesPath, nomFichier));
	 * GedDocumentSortantDTO document = new GedDocumentSortantDTO();
	 * document.setNomFichier(nomFichier); //
	 * document.setTypeDocument(gedService.lireTypeDocument(CODE_TYPE_DOCUMENT))
	 * ; document.putMetadonnee(GedMetaDonneeDTO.CODE_PAYS, "SN");
	 * document.putMetadonnee(GedMetaDonneeDTO.CODE_REGION, "DKR"); try { String
	 * codeEJ = RecupValuesInSession.getCodeEntityJuridiqueDataByTag();
	 * document.putMetadonnee(GedMetaDonneeDTO.CODE_ENTITE_JURIDIQUE, codeEJ); }
	 * catch (Exception e) { throw new
	 * GEDException("Impossible de récupérer le code de l'entité juridique"); }
	 * try { LOG.info("Enregistrement du fichier " + document.getNomFichier() +
	 * " dans la ged."); // GedAbstractDocumentDTO saved = //
	 * gedService.enregistrerDocument(document, flux); //
	 * utilisateur.setNumeroFonctionnel(saved.getNumeroFonctionnel());
	 * modelDAO.save(utilisateur); // utilisateurDTO.setGedDocumentDTO(saved);
	 * // utilisateurDTO.setNumeroFonctionnel(saved.getNumeroFonctionnel()); }
	 * catch (Exception e) { throw new
	 * GEDException("Impossible de sauvegarder dans la GED " + e.getMessage());
	 * } FileUtils.deleteQuietly(new File(filesPath, nomFichier)); } catch
	 * (Exception e) { IReportUtilisateurPassword.getUtilisateurList().clear();
	 * throw new UtilisateurException(
	 * "Impossible de générer le document de changement de password " +
	 * e.getMessage()); } }
	 */

	public String getFilesPath() {
		return filesPath;
	}

	public void setFilesPath(String filesPath) {
		this.filesPath = filesPath;
	}

	public String getIReportfileJRXMLChangePassword() {
		return iReportfileJRXMLChangePassword;
	}

	public void setIReportfileJRXMLChangePassword(String iReportfileJRXMLChangePassword) {
		this.iReportfileJRXMLChangePassword = iReportfileJRXMLChangePassword;
	}

	@Override
	public void genererChangePasswordDocument(UtilisateurDTO utilisateurDTO) throws UtilisateurException {
		// TODO Auto-generated method stub

	}

}