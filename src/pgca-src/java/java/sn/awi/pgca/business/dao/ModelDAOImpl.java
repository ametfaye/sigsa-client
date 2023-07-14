package sn.awi.pgca.business.dao;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.exception.UtilisateurException;
import sn.awi.pgca.dataModel.AffectationsGestionPointDeVentes;
import sn.awi.pgca.dataModel.BonDeLivraison;
import sn.awi.pgca.dataModel.Camion;
import sn.awi.pgca.dataModel.CampagneAgricole;
import sn.awi.pgca.dataModel.Chauffeur;
import sn.awi.pgca.dataModel.Commande;
import sn.awi.pgca.dataModel.CommandeProduitAssocie;
import sn.awi.pgca.dataModel.Commune;
import sn.awi.pgca.dataModel.Credit;
import sn.awi.pgca.dataModel.Departement;
import sn.awi.pgca.dataModel.Fournisseur;
import sn.awi.pgca.dataModel.GenericModel;
import sn.awi.pgca.dataModel.Intrant;
import sn.awi.pgca.dataModel.MiseEnPlaceAEffectuer;
import sn.awi.pgca.dataModel.MiseEnPlaceEffectuer;
import sn.awi.pgca.dataModel.OrdreLivraison;
import sn.awi.pgca.dataModel.OrdreLivraisonProduitAssocie;
import sn.awi.pgca.dataModel.ParametreGlobaux;
import sn.awi.pgca.dataModel.Pays;
import sn.awi.pgca.dataModel.Personne;
import sn.awi.pgca.dataModel.PointDeCollecte;
import sn.awi.pgca.dataModel.PointDeVente;
import sn.awi.pgca.dataModel.Profil;
import sn.awi.pgca.dataModel.ProgrammeAgricol;
import sn.awi.pgca.dataModel.ReferentialIntrants;
import sn.awi.pgca.dataModel.ReferentialTypeIntrants;
import sn.awi.pgca.dataModel.Region;
import sn.awi.pgca.dataModel.Stock;
import sn.awi.pgca.dataModel.StockResiduelPointDeVente;
import sn.awi.pgca.dataModel.Subvention;
import sn.awi.pgca.dataModel.Tarificateur;
import sn.awi.pgca.dataModel.Transporteur;
import sn.awi.pgca.dataModel.Utilisateur;
import sn.awi.pgca.dataModel.Ventes;
import sn.awi.pgca.utils.Password;
import sn.awi.pgca.web.bean.ConstantPGCA;
import sn.awi.pgca.web.bean.SessionManagedBean;
import sn.awi.pgca.web.dto.CoupleDTO;
import sn.awi.pgca.web.dto.FournisseurDTO;
import sn.awi.pgca.web.dto.InfosCommunesDTO;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.MiseEnplaceAgregation;
import sn.awi.pgca.web.dto.RechercheMiseEnPlaceDTO;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@Named
public class ModelDAOImpl extends GenericDAOImpl implements Serializable, ModelDAO {

	/**** Commun Query use by all the method *******/

	private Query query;
	private static final long serialVersionUID = 7479473866554277609L;
	private static final Log logger = LogFactory.getLog(ModelDAOImpl.class);

	@PersistenceContext(unitName = "PUnitPGCA", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;

	/*****
	 * Recuperation de la liste des collaborateur
	 * 
	 * @throws EntityDBDAOException
	 ***/

	@Override
	public List<ProgrammeAgricol> loadProgrammeByStatut(int statut) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(ProgrammeAgricol.class);
		// criteria.add(Restrictions.eq("statut", statut));

		return (List<ProgrammeAgricol>) criteria.list();
	}

	@Override
	public List<Utilisateur> loadAllCollaborateur() throws EntityDBDAOException {
		Query query = em.createNativeQuery("select * from user_utilisateur user1   order by user1.codeutilisateur asc ",
				Utilisateur.class);

		try {
			return query.getResultList();
		} catch (Exception e) {
			logger.error("Erreur récupération des Utilisateurs : " + e.getMessage());
			throw new EntityDBDAOException("Erreur récupération des Utilisateurs", e);
		}
	}

	/***
	 * Recuperation de la liste des utilisateurs : TODO Filtrer les user
	 * desactiv
	 **/
	@SuppressWarnings("unchecked")
	public List<Utilisateur> loadUtilisateur() throws EntityDBDAOException {
		Query query = em.createNativeQuery("select * from user_utilisateur user1   order by user1.codeutilisateur asc ",
				Utilisateur.class);

		try {
			return query.getResultList();
		} catch (Exception e) {
			logger.error("Erreur récupération des Utilisateurs : " + e.getMessage());
			throw new EntityDBDAOException("Erreur récupération des Utilisateurs", e);
		}
	}

	/********* Profil *************/

	@SuppressWarnings("unchecked")
	public List<Profil> loadProfil() throws EntityDBDAOException {
		Query query = null;
		try {
			String cp = SessionManagedBean.getSessionDataByTag("profilCode");
			if (cp.equals("manager"))
				query = em.createNativeQuery("select * from prfl_profil prfl order by prfl.code asc", Profil.class);
			else
				query = em.createNativeQuery(
						"select * from prfl_profil prfl where prfl.code != 'manager' order by prfl.code asc",
						Profil.class);

			return query.getResultList();
		} catch (Exception e) {
			logger.error("Erreur récupération des Profils : " + e.getMessage());
			throw new EntityDBDAOException("Erreur récupération des Profils", e);
		}
	}

	/******* FIn Profil ***********/

	/******* Point de Collecte******************** ***********/

	@Override
	@SuppressWarnings("unchecked")
	public List<PointDeCollecte> loadPointdeCollecte() throws EntityDBDAOException {
		Query query = em.createNativeQuery("select * from pgca_pointDeCollecte pc  order by pc.pdc_id asc ",
				PointDeCollecte.class);

		try {
			return query.getResultList();
		} catch (Exception e) {
			logger.error("Erreur récupération des Points de collecte  : " + e.getMessage());
			throw new EntityDBDAOException("Erreur récupération des point de collecte", e);
		}
	}

	/******************************** FIn PC ***********/

	/*******
	 * Point de Vente ********************
	 * 
	 * @throws EntityDBDAOException
	 ***********/

	@SuppressWarnings("unchecked")
	@Override
	public List<PointDeVente> loadPointdeVente() throws EntityDBDAOException {
		Query query = em.createNativeQuery("select * from pgca_pointDeVente pv  order by pv.ptv_id asc ",
				PointDeVente.class);

		try {
			return query.getResultList();
		} catch (Exception e) {
			logger.error("Erreur récupération des Points de vente  : " + e.getMessage());
			throw new EntityDBDAOException("Erreur récupération des point de vente", e);
		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public PointDeVente loadPointdeVenteByLibelle(String nomeCommune) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();	
		Criteria criteria = session.createCriteria(PointDeVente.class);
		criteria.add(Restrictions.eq("libelle", nomeCommune.trim().toUpperCase()));
		criteria.setMaxResults(1);

		PointDeVente pv = (PointDeVente) criteria.uniqueResult();
		return pv;
	}
	
	
	/************ Fin PV ***********/

	@Override
	public List<CommandeProduitAssocie> loadAallProduitFromIdCMD(Long idCommande) throws EntityDBDAOException {
		try {
			Query query = em.createNativeQuery(
					"select * from pgca_commandeProduitsAssocies p   where p.cmd_id = " + idCommande,
					CommandeProduitAssocie.class);
			return query.getResultList();
		} catch (Exception e) {
			logger.error("Erreur recuperation  de produit par année : " + e.getMessage());
			throw new EntityDBDAOException("Erreur récupération des campagnes", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrdreLivraisonProduitAssocie> loadAallProduitFromIdOrdre(Long idOrdre) throws EntityDBDAOException {
		try {
			/*
			 * Query query = em.
			 * createNativeQuery("select * from pgca_OrdreLProduitsAssocies o   where o.ord_id = "
			 * + idOrdre , OrdreLivraisonProduitAssocie.class); return
			 * query.getResultList();
			 */

			Session session = (Session) em.getDelegate();
			Criteria criteria = session.createCriteria(OrdreLivraisonProduitAssocie.class);
			criteria.add(Restrictions.eq("commandeAssocie.ord_id", idOrdre));

			return criteria.list();

		} catch (Exception e) {
			logger.error("Erreur recuperation  des produit de lordre  : " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Commande> loadCommandeByIdPointdeVenteAndProgId(Long pvId, Long idProgramme)
			throws EntityDBDAOException {
		Query query = null;
		try {
			query = em.createNativeQuery("select * from pgca_commande c where c.ptv_id = " + pvId, Commande.class);
			if (query != null)
				return query.getResultList();
		} catch (Exception e) {
			logger.error("Erreur récupération produit : " + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<Commande> loadCommandeEnAttenteDeValidationByIdPointdeVenteAndProgId(Long pvId, Long idProgramme,
			int status) throws EntityDBDAOException {
		Query query = null;
		try {
			query = em.createNativeQuery(
					"select * from pgca_commande c where c.ptv_id = " + pvId + " and c.statutCMD = " + status,
					Commande.class);
			if (query != null)
				return query.getResultList();
		} catch (Exception e) {
			logger.error("Erreur récupération produit : " + e.getMessage(), e);
		}
		return null;
	}

	public synchronized void synchroniseWithDB(GenericModel e) {
		try {
			em.refresh(e);
		} catch (Exception ex) {
		}
	}

	public Profil getProfilbyCode(String code) throws EntityDBDAOException {
		if (code == null) {
			throw new EntityDBDAOException("Le paramétre code est null.");
		}

		logger.info("Récupération d'un profil é partir de son code <" + code + "> en base de données.");
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Profil.class);
		criteria.add(Restrictions.eq("code", code.trim()));
		Profil profil = (Profil) criteria.uniqueResult();

		if (profil != null) {
			logger.info("Le profil avec le  <" + code + ">   trouvé !!! ");
			return profil;
		}

		logger.info("Le profil avec le  <" + code + ">   est introuvable en base !!! ");
		return null;
	}

	@Override
	public Utilisateur getUtilisateurbyCode(String code) throws UtilisateurException {
		// Vérification des paramétres du service
		if (code == null) {
			throw new UtilisateurException("Le paramétre code utilisateur est null.");
		}

		// Trace de débug
		if (logger.isDebugEnabled()) {
			logger.debug("Récupération d'un user é partir de son code <" + code + "> en base de données.");
		}

		Session session = (Session) em.getDelegate();
		try {
			Criteria criteria = session.createCriteria(Utilisateur.class);
			criteria.add(Restrictions.eq("email", code.trim()));
			Utilisateur user = (Utilisateur) criteria.uniqueResult();
			return user;
		} catch (Exception uee) {
			throw new UtilisateurException("Impossible de trouver un utilisateur de code " + code, uee);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Pays> loadPays() throws EntityDBDAOException {
		Query query = null;
		try {
			query = em.createNativeQuery("select * from pays ps order by ps.libelle asc ", Pays.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (query != null)
				return query.getResultList();
		} catch (Exception e) {
			logger.error("Erreur récupération des Pays : " + e.getMessage(), e);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PointDeCollecte> loadEntiteJuridique() throws EntityDBDAOException {
		Query query = null;
		try {
			query = em.createNativeQuery("select * from pgca_pointDeCollecte  enju   order by enju.libelle asc ",
					PointDeCollecte.class);

		} catch (Exception e) {
			logger.error("Erreur récupération des PointDeCollecte : " + e.getMessage(), e);
		}
		try {
			if (query != null)
				return query.getResultList();
		} catch (Exception e) {
			logger.error("Erreur récupération des PointDeCollecte : " + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public Utilisateur getUtilisateurByCodeAndPassword(String codeUtilisateur, String motDePasse) {

		Session session = (Session) em.getDelegate();

		try {
			Password pwd = new Password();
			Criteria criteria = session.createCriteria(Utilisateur.class);
			criteria.add(Restrictions.eq("email", codeUtilisateur));
			criteria.add(Restrictions.eq("motdepasse", pwd.getEncodedPasswordWithoutCharset(motDePasse)));
			// criteria.add(Restrictions.eq("est_valide", Boolean.TRUE));
			Utilisateur user = (Utilisateur) criteria.uniqueResult();
			return user;

		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();

		}
		return null;

	}

	@Override
	public Intrant verifierStockRestantAupresFournisseur(Long idIntrant, Long idFournisseur)
			throws EntityDBDAOException {

		Session session = (Session) em.getDelegate();

		Criteria criteria = session.createCriteria(Intrant.class);
		criteria.add(Restrictions.eq("intrant.refIntrantId", idIntrant));
		criteria.add(Restrictions.eq("fournisseur.id", idFournisseur));
		criteria.add(Restrictions.eq("stockRef.stock_id", 0L)); // les
																// fournisseurs
																// ont stock de
																// reference 0
		return (Intrant) criteria.uniqueResult();
	}

	@Override
	public Double loadTotalStockRestantByIdIntrantFournisseur(Long idIntrant) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Intrant.class);
		criteria.add(Restrictions.eq("intrant.refIntrantId", idIntrant));
		criteria.add(Restrictions.eq("stockRef.stock_id", 0L)); //
		@SuppressWarnings("unchecked")
		List<Intrant> result = (List<Intrant>) criteria.list();

		Double total = 0.0;

		if (result != null)
			for (Intrant i : result)
				total += i.getQuantite();
		return total;
	}

	@Override
	public Double loadTotalStockRestantByIdIntrantLocal(Long idIntrant) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Intrant.class);
		criteria.add(Restrictions.eq("intrant.refIntrantId", idIntrant));
		criteria.add(Restrictions.ge("stockRef.stock_id", 0L)); //
		@SuppressWarnings("unchecked")
		List<Intrant> result = (List<Intrant>) criteria.list();

		Double total = 0.0;

		if (result != null)
			for (Intrant i : result)
				total += i.getQuantite();
		return total;
	}

	@Override
	public Intrant verifierStockRestantAupresMagasin(Long idIntrant, Long idStockMagasin) throws EntityDBDAOException {

		Session session = (Session) em.getDelegate();

		Criteria criteria = session.createCriteria(Intrant.class);
		criteria.add(Restrictions.eq("intrant.refIntrantId", idIntrant));
		criteria.add(Restrictions.eq("stockRef.stock_id", idStockMagasin)); // le
																			// stock
																			// de
																			// magsin
		return (Intrant) criteria.uniqueResult();
	}

	// connexion avec le profil de l'utilisateur
	@Override
	public Utilisateur getUtilisateurByCodeAndPassword(String codeUtilisateur, String motDePasse, String idProfil)
			throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Region> loadRegion() throws EntityDBDAOException {
		Query query = null;
		try {
			query = em.createNativeQuery("select * from regn_region regn order by regn.regn_id asc ", Region.class);

		} catch (Exception e) {
			logger.error("Erreur récupération des regions : " + e.getMessage(), e);
		}
		try {
			if (query != null) {
				return query.getResultList();
			}
		} catch (Exception e) {
			logger.error("Erreur récupération des formes juridiques : " + e.getMessage(), e);
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Region> loadRegionByPaysId(String id) {
		Query query = null;
		try {
			query = em.createNativeQuery(
					"select * from regn_region regn where regn.pays_id = " + id + " order by regn.regn_id asc ",
					Region.class);

		} catch (Exception e) {
			logger.error("Erreur récupération des regions : " + e.getMessage(), e);
		}
		try {
			if (query != null) {
				return query.getResultList();
			}
		} catch (Exception e) {
			logger.error("Erreur récupération des formes juridiques : " + e.getMessage(), e);
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParametreGlobaux> loadParametreGlobaux() throws EntityDBDAOException {
		try {
			Session session = (Session) em.getDelegate();
			Criteria criteria = session.createCriteria(ParametreGlobaux.class);

			return (List<ParametreGlobaux>) criteria.list();
		} catch (Exception e) {
			throw new EntityDBDAOException("Erreur récupération des paramètres globaux par pays ", e);
		}
	}

	@Override
	public Integer loadAllFornisseursForAutoCompleteSize() throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria crit = session.createCriteria(Fournisseur.class);
		crit.setProjection(Projections.rowCount());
		Long longVal = (Long) crit.uniqueResult();

		return longVal != null ? longVal.intValue() : 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Chauffeur> getChauffeurBytransporteur(Long idTransporteur) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Chauffeur.class);
		criteria.add(Restrictions.eq("idtransporteur", idTransporteur));

		return (List<Chauffeur>) criteria.list();
	}

	@Override
	public List<ParametreGlobaux> loadParametreGlobaux(String idPays) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	/**********
	 * Metghode Genenique pour lire tout les elements d'une table donnée : Utils
	 * pour l'affichage des liste d'un element
	 **/

	@Override
	public List<?> genericClassLoader(Class<?> genericClass) {
		Session session = (Session) em.getDelegate();
		Criteria genericsListObject = session.createCriteria(genericClass);
		return genericsListObject.list();
	}

	/*************/

	@Override
	public List<?> genericClassLoaderById(Class<?> genericClass, String columPivotName, Long idgenericClass) {
		Session session = (Session) em.getDelegate();
		Criteria genericsListObject = session.createCriteria(genericClass);

		genericsListObject.add(Restrictions.eq(columPivotName, idgenericClass));
		return genericsListObject.list();
	}

	@Override
	public List<MiseEnPlaceAEffectuer> loadAllMiseEnPlaceAEffectuer() {
		Query query = null;
		try {
			query = em.createNativeQuery("select * from pgca_miseEnPlaceAeffectuee 	");
			return (List<MiseEnPlaceAEffectuer>) query.getResultList();

		} catch (Exception e) {
			logger.error("Erreur récupération des departements : " + e.getMessage(), e);
		}
		return null;
	}

	/**** Aaggregation des quotas de mises en place par intrant ***/

	// Recupération des fournisseur qui ont un produit spécifique
	@SuppressWarnings("unchecked")
	@Override
	public List<MiseEnplaceAgregation> loadAgregationMiseEnPlace() {

		Session s = (Session) em.getDelegate();

		return s.createSQLQuery(
				"SELECT" + " MAX(intrant.refintrantid) AS idIntrant, MAX(intrant.libelle) AS nomIntrant, "
						+ "SUM(mep.quota) AS totalQuota, SUM(mep.reliquat) AS totalreliquat, (SUM(mep.quota) - SUM(mep.reliquat) ) AS tauxCourverture, COUNT(mep.commune_id)"
						+ " AS nombreDePointDeVente FROM pgca_miseenplaceaeffectuee mep INNER JOIN "
						+ "commune pv ON mep.commune_id = pv.commune_id INNER JOIN pgca_referentielIntrant intrant  "
						+ "ON intrant.refIntrantId = mep.refIntrantId GROUP BY  mep.refintrantid;")
				.addScalar("idIntrant", new LongType()).addScalar("nomIntrant", new StringType())
				.addScalar("totalQuota", new DoubleType()).addScalar("totalreliquat", new DoubleType())
				.addScalar("tauxCourverture", new DoubleType()).addScalar("nombreDePointDeVente", new IntegerType())
				.setResultTransformer(Transformers.aliasToBean(MiseEnplaceAgregation.class)).list();

	}

	/****
	 * Aaggregation des quotas de mises en place par intrant et par departement
	 ***/

	@SuppressWarnings("unchecked")
	@Override
	public List<MiseEnplaceAgregation> loadAgregationMiseEnPlaceByIntrantAndDepartement() {
		Session s = (Session) em.getDelegate();

		return s.createSQLQuery("SELECT dept.libelle  AS departementGroup, c.libelle  AS commune,"
				+ "  dept.departement_id AS idDepartement," + " MAX(mep.id_MEPaAF) as  idMep ,"
				+ " MAX(intrant.refintrantid) AS idIntrant, " + " MAX(intrant.libelle) AS nomIntrant, "
				+ " SUM(mep.quota) AS totalQuota, " + " SUM(mep.reliquat) AS totalreliquat,"
				+ "  COUNT(mep.commune_id) AS nombreDePointDeVente " + " FROM pgca_miseenplaceaeffectuee mep "
				+ " INNER JOIN pgca_referentielIntrant intrant ON intrant.refIntrantId = mep.refIntrantId "
				+ " INNER JOIN commune c ON mep.commune_id = c.commune_id "
				+ " INNER JOIN departement dept ON dept.departement_id = c.departement_id "
				+ " GROUP BY    dept.departement_id ,  c.commune_id, mep.refIntrantId")
				.addScalar("idIntrant", new LongType()).addScalar("nomIntrant", new StringType())
				.addScalar("totalQuota", new DoubleType())
				.addScalar("totalreliquat", new DoubleType())
				.addScalar("idDepartement", new LongType()).addScalar("nombreDePointDeVente", new IntegerType())
				.addScalar("idMep", new LongType()).addScalar("departementGroup", new StringType())
				.addScalar("commune", new StringType())

				.setResultTransformer(Transformers.aliasToBean(MiseEnplaceAgregation.class)).list();
	}

	// Recupération des fournisseurs qui ont un produit spécifique
	@SuppressWarnings("unchecked")
	@Override
	public List<FournisseurDTO> loadFournisseurByProduit(Long idProduit) {
		Session s = (Session) em.getDelegate();
		return s.createSQLQuery(
				" " + "SELECT  f.four_id as idFounisseur , f.libelle  as libelle, f.provenance, representantcivil, representanttelephone, "
						+ " adrs_id, cont_id  , p.quantite as quantiteIntrantRestant , p.stock_id  as idStockdeReference,  p.stockLibelle  FROM pgca_fournisseur f right join pgca_intrant p on  f.four_id = p.four_id "
						+ "where p.pgca_referentielintrant = " + idProduit)
				.addScalar("idFounisseur", new LongType()).addScalar("libelle", new StringType())
				.addScalar("quantiteIntrantRestant", new DoubleType()).addScalar("idStockdeReference", new LongType())
				.addScalar("representantCivil", new StringType()).addScalar("representantTelephone", new StringType())
				.addScalar("stockLibelle", new StringType())

				.setResultTransformer(Transformers.aliasToBean(FournisseurDTO.class)).list();
	}

	@Override
	public Transporteur loadTransporteurByName(String libelle) {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Transporteur.class);
		criteria.add(Restrictions.eq("libelle", libelle));
		return (Transporteur) criteria.uniqueResult();
	}

	
	

	
	
	


	@Override
	public Departement loadIDepartementByName(String libelle) {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Departement.class);
		criteria.add(Restrictions.eq("libelle", libelle));
		criteria.setMaxResults(1);
		return (Departement) criteria.uniqueResult();
	}

	@Override
	public Chauffeur loadChauffeurByName(String name) {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Chauffeur.class);
		criteria.add(Restrictions.eq("libelle", name));		
		criteria.setMaxResults(1);
		Chauffeur chauf = (Chauffeur) criteria.uniqueResult();

		return chauf;
		
	}

	@Override
	public Fournisseur loadFournisseurByName(String name) {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Fournisseur.class);
		criteria.add(Restrictions.eq("libelle", name));
		criteria.setMaxResults(1);
		Fournisseur f = (Fournisseur) criteria.uniqueResult();

		return f;
	}

	@Override
	public Camion loadCamionByName(String name) {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Camion.class);
		criteria.add(Restrictions.eq("numeroCamion", name));
		criteria.setMaxResults(1);
		Camion cam = (Camion) criteria.uniqueResult();

		return cam;
	}

	@Override
	public List<?> genericSqlClassLoaderById(Class<?> classe, String TableDbName, String columPivotName,
			Long idgenericClass) {

		Query query = null;
		try {

			query = em.createNativeQuery(
					"select * from " + TableDbName + " where " + columPivotName + "=" + idgenericClass, classe);

		} catch (Exception e) {
			logger.error("Erreur récupération des departements : " + e.getMessage(), e);
		}
		try {
			if (query != null) {
				return query.getResultList();
			}
		} catch (Exception e) {
			logger.error("Erreur récupération des departements" + e.getMessage(), e);
		}
		return null;

	}

	@Override
	public List<Intrant> loadAllMagasinStock() {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Intrant.class);
		criteria.add(Restrictions.sizeGe("stock_id", 0));
		return (List<Intrant>) criteria.list();
	}

	@Override
	public Credit loadCreditByIdCMD(Long columCriteria) {

		/*
		 * Session session = (Session) em.getDelegate(); Criteria criteria =
		 * session.createCriteria(Credit.class);
		 * criteria.add(Restrictions.eq("cmd_id", columCriteria));
		 * 
		 * return (Credit) criteria.uniqueResult();
		 */

		Query query = null;
		try {

			query = em.createNativeQuery("select * from  pgca_credit  where cmd_id = " + columCriteria);
			if (query != null)
				query.setMaxResults(1);

			return (Credit) query.getSingleResult();
		} catch (Exception e) {
			logger.error("Erreur récupération des creditis : " + e.getMessage(), e);
		}

		return null;

	}

	/********************************************************************************************************************************/

	/*********** Magasinier ***************/

	@SuppressWarnings("unchecked")
	@Override
	public List<Intrant> loadStockByIdPointdeVente(Long idStock) throws EntityDBDAOException {

		logger.info("Récupération de stock du point devente<" + idStock + "> en base de données.");
		return (List<Intrant>) genericSqlClassLoaderById(Intrant.class, "pgca_intrant", "stock_id", idStock);
	}

	@Override
	public List<Personne> loadAllChauffeur() throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Personne.class);
		criteria.add(Restrictions.eq("fonction", 5L));

		return (List<Personne>) criteria.list();
	}

	@Override
	public Personne loadChauffeurByIdChauffeur(Long idChauffeur) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Personne.class);
		criteria.add(Restrictions.eq("fonction", 5));
		criteria.add(Restrictions.eq("pers_id", idChauffeur));
		criteria.setMaxResults(1);
		return (Personne) criteria.uniqueResult();
	}

	@Override
	public List<BonDeLivraison> loadABLByIdStock(Long idStock) throws EntityDBDAOException {

		String codeProfil = "";
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			codeProfil = (String) session.getAttribute("profil");
		}

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(BonDeLivraison.class);
		criteria.add(Restrictions.eq("blStatut", 0));

		// Si agent saisie on affiche tout , Sinon filtrer par stock connecté
		if (!codeProfil.equals("agentsaisie"))
			criteria.add(Restrictions.eq("idStockReceptionnaire", idStock));

		return (List<BonDeLivraison>) criteria.list();
	}

	@Override
	public List<BonDeLivraison> ladBLEnvoyesByStock(Long idStock) throws EntityDBDAOException {

		String codeProfil = "";
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			codeProfil = (String) session.getAttribute("profil");
		}

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(BonDeLivraison.class);
		criteria.add(Restrictions.eq("blStatut", 0));

		criteria.add(Restrictions.eq("idStockExpedition", idStock));

		return (List<BonDeLivraison>) criteria.list();
	}

	/*** TODO Nelaw job : fonction a revoir ****/
	@Override
	public Intrant loadTypeOfSpecificProduitOfSpecificStock(Long stock_id, Long idTypeProduit) {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Intrant.class);
		// criteria.add(Restrictions.eq("intrant.typeIntrantId.",
		// idTypeProduit));
		criteria.add(Restrictions.eq("stockRef.stock_id", stock_id));

		@SuppressWarnings("unchecked")
		List<Intrant> lp = (List<Intrant>) criteria.list();

		for (Intrant p : lp)
			if (p.getIntrant().getTypeIntrantId().getTypeIntrantId() == idTypeProduit)
				return p;
		return null;
	}

	@Override
	public InfosCommunesDTO getDetailsCommuneFromNomCommune(String nomCommuune) {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Commune.class);
		criteria.add(Restrictions.eq("libelle", nomCommuune));
		criteria.setMaxResults(1);
		Commune c = (Commune) criteria.uniqueResult();

		if (c == null)
			return null;

		InfosCommunesDTO infos = new InfosCommunesDTO();

		infos.setCommuneid(c.getId() + "");
		infos.setCommuneLibelle(c.getLibelle());

		infos.setDepartementLibelle(c.getDepartement() != null ? c.getDepartement().getLibelle() : "");
		infos.setDepartementid(c.getDepartement() != null ? c.getDepartement().getId() + "" : "");

		infos.setRegionLibelle(
				c.getDepartement().getRegion() != null ? c.getDepartement().getRegion().getLibelle() : "");
		infos.setRegionid(c.getDepartement().getRegion() != null ? c.getDepartement().getRegion().getId() + "" : "");

		return infos;

	}

	@Override
	public InfosCommunesDTO getDetailsCommuneFromIdCommune(Long idCommune) {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Commune.class);
		criteria.add(Restrictions.eq("id", idCommune));

		Commune c = (Commune) criteria.uniqueResult();

		if (c == null)
			return null;

		InfosCommunesDTO infos = new InfosCommunesDTO();

		infos.setCommuneid(c.getId() + "");
		infos.setIdCommune(c.getId());
		infos.setCommuneLibelle(c.getLibelle());

		infos.setDepartementLibelle(c.getDepartement() != null ? c.getDepartement().getLibelle() : "");
		infos.setDepartementid(c.getDepartement() != null ? c.getDepartement().getId() + "" : "");

		infos.setRegionLibelle(
				c.getDepartement().getRegion() != null ? c.getDepartement().getRegion().getLibelle() : "");
		infos.setRegionid(c.getDepartement().getRegion() != null ? c.getDepartement().getRegion().getId() + "" : "");

		return infos;

	}

	@Override
	public Commune loadCommuneFromLibelleCommune(String nomCommuune) {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Commune.class);
		criteria.add(Restrictions.eq("libelle", nomCommuune));
		criteria.setMaxResults(1);
		Commune c = (Commune) criteria.uniqueResult();

		return c;
	}

	@Override
	public Departement loadDepartementFromLibelleDepartement(String nomDepartement) {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Departement.class);
		criteria.add(Restrictions.eq("libelle", nomDepartement));
		criteria.setMaxResults(1);

		Departement d = (Departement) criteria.uniqueResult();

		return d;
	}

	/**********
	 * Recherche des points de stock ayant une quantite X a vendre
	 **********/
	@SuppressWarnings("unchecked")
	@Override
	public List<Intrant> getDisponibiliteProduitByQuantiteProduit(Long idTypeProduit, float quantiteAvendre) {
		return null;
		/*
		 * Session session = (Session) em.getDelegate(); Criteria criteria =
		 * session.createCriteria(Produit.class);
		 * criteria.add(Restrictions.eq("quantite", quantiteAvendre));
		 * criteria.add(Restrictions.eq("idTypeProduit", idTypeProduit));
		 * 
		 * return criteria.list();
		 */
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PointDeVente> loadPointDeVenteByCodeRegionAndProgramme(Long idProg, Long idRegion)
			throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(PointDeVente.class).createAlias("adresse", "a")
				.createAlias("a.region", "r").add(Restrictions.eq("r.id", idRegion));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PointDeVente> loadPointDeVenteByidCommune(Long idCommune) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(PointDeVente.class).createAlias("adresse", "a")
				.createAlias("a.commune", "c")

				.add(Restrictions.eq("c.id", idCommune));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PointDeVente> loadAllPointDeventeOfDepartement(Long idDepartement) throws EntityDBDAOException {
		/*
		 * Session session = (Session) em.getDelegate(); Criteria criteria =
		 * session.createCriteria(PointDeVente.class).createAlias("commune",
		 * "c") .createAlias("c.departement", "d")
		 * 
		 * .add(Restrictions.eq("d.id", idDepartement));
		 * 
		 * return criteria.list();
		 */

		/*
		 * Query query = null; List<PointDeVente> result = null ; try {
		 * 
		 * query = em.
		 * createNativeQuery(" Select p.ptv_id, p.libelle  from pgca_pointdevente p inner join commune c on c.commune_id = p.commune_id  inner join departement d on d.departement_id = c.departement_id and d.departement_id = "
		 * + idDepartement , PointDeVente.class); if (query != null) result =
		 * (List<PointDeVente>) query.getResultList(); } catch (Exception e) {
		 * logger.error("Erreur récupération des creditis : " + e.getMessage(),
		 * e); } return null;
		 */

		Session s = (Session) em.getDelegate();
		return s.createSQLQuery(
				"Select p.ptv_id, p.libelle  from pgca_pointdevente p inner join commune c on c.commune_id = p.commune_id  inner join departement d on d.departement_id = c.departement_id and d.departement_id =  "
						+ idDepartement)
				.addScalar("ptv_id", new LongType()).addScalar("libelle", new StringType())
				.setResultTransformer(Transformers.aliasToBean(PointDeVente.class)).list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PointDeVente> loadAllPointDeventeOfUser(Long idUser) throws EntityDBDAOException {
		List<AffectationsGestionPointDeVentes> af;
	
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(AffectationsGestionPointDeVentes.class)
				.createAlias("superviseurPointDeVente", "user").
				add(Restrictions.eq("user.id", idUser));

		
		af = (List<AffectationsGestionPointDeVentes>) criteria.list();

		if (null == af)
			return null;

		List<PointDeVente> allAF = new ArrayList<PointDeVente>();
		PointDeVente pv;

		for (AffectationsGestionPointDeVentes a : af) {

			// on ne prend que ses point de vente ayant un statut 0 : Affecté
			if (a.getStatut() == 0) {
				pv = new PointDeVente();
				pv = a.getPtv_id();
				pv.setEstaffecte(true);
				allAF.add(pv);
			}

		}
		return allAF;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkExistingPointDeVenteByName(String libelle, Long iddept) throws EntityDBDAOException {

		query = em.createNativeQuery("select * from commune c where  c.libelle = '" + libelle + "'", Commune.class);

		// check if departement a deja une commune
		List<Commune> listc = (List<Commune>) query.getResultList();

		for (Commune c : listc) {

			if (c.getDepartement().getId().equals(iddept))
				return true;

		}

		return false;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MiseEnPlaceAEffectuer> loadMiseEnPlaceByidCommune(Long idCommune) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(MiseEnPlaceAEffectuer.class)
				.createAlias("pointdeVenteConcerne", "pv").createAlias("pv.adresse", "a").createAlias("a.commune", "c")

				.add(Restrictions.eq("c.id", idCommune));

		List<MiseEnPlaceAEffectuer> list = (List<MiseEnPlaceAEffectuer>) criteria.list();

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MiseEnPlaceAEffectuer> loadAllMiseEnPlaceByTermine() throws EntityDBDAOException {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(MiseEnPlaceAEffectuer.class);
		criteria.add(Restrictions.eq("reliquat", 0.0));

		List<MiseEnPlaceAEffectuer> list = (List<MiseEnPlaceAEffectuer>) criteria.list();

		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MiseEnPlaceAEffectuer> loadMiseEnPlaceByProgramme(Long idPrigramme) throws EntityDBDAOException {

		Session session = (Session) em.getDelegate();
		Criteria criteriapo = session.createCriteria(ProgrammeAgricol.class);
		criteriapo.add(Restrictions.eq("pgca_idprog", idPrigramme));
		ProgrammeAgricol po = (ProgrammeAgricol) criteriapo.uniqueResult();
		
		Criteria criteria = session.createCriteria(MiseEnPlaceAEffectuer.class);
		criteria.add(Restrictions.eq("programmeConcerne", po));

		List<MiseEnPlaceAEffectuer> list = (List<MiseEnPlaceAEffectuer>) criteria.list();

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MiseEnPlaceAEffectuer> loadAllMiseEnPlaceEncours() throws EntityDBDAOException {

		// 27/09/2020 : lenteru décétecté  : on ne lit que les MEP effectue il y'a plus d'une semaine 
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Date todate1 = cal.getTime();
        
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(MiseEnPlaceAEffectuer.class);
		criteria.add(Restrictions.gt("reliquat", 0.1));
		criteria.add(Restrictions.ge("dateEdition", todate1));

		List<MiseEnPlaceAEffectuer> list = (List<MiseEnPlaceAEffectuer>) criteria.list();

		return list;
		//return new ArrayList<MiseEnPlaceAEffectuer>() ;
	}

	/****
	 * 
	 * Lecture des mises en place du effectue sur un departement specique pour
	 * un intrant specifique par periode
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MiseEnPlaceEffectuer> loadAllMiseEnPlaceEffectueeByDepartementAndIntrant(Long idDepartement,
			Long idIntrant, String dateDebut, String dateFin) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(MiseEnPlaceEffectuer.class).createAlias("miseEnplaceConcerne", "mep")

				.createAlias("mep.pointdeVenteConcerne", "pv").createAlias("mep.ProduitAmettreEnPlace", "intrant")
				.createAlias("pv.adresse", "a").createAlias("a.departement", "dept")
				.add(Restrictions.eq("dept.id", idDepartement)) // id du dept
				.add(Restrictions.eq("intrant.refIntrantId", idIntrant)); // id
																			// de
																			// l'intrant

		List<MiseEnPlaceEffectuer> list = (List<MiseEnPlaceEffectuer>) criteria.list();

		return list;
	}

	// ReferentialIntrants ProduitAmettreEnPlace --> private
	// ReferentialTypeIntrants typeIntrantId; -> typeIntrantId

	@SuppressWarnings("unchecked")
	@Override
	public List<MiseEnPlaceAEffectuer> loadMiseEnPlaceAeffectuerByidPointdeVente(Long idPv, Long categorie,
			Long idIntrant) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();

		Criteria criteria = session.createCriteria(MiseEnPlaceAEffectuer.class);

		// Pas de de Filter
		if (!isCorrect(idPv) && !isCorrect(categorie) && !isCorrect(idIntrant))
			return (List<MiseEnPlaceAEffectuer>) criteria.list();

		// filter pv only
		if (isCorrect(idPv) && !isCorrect(categorie) && !isCorrect(idIntrant)) {
			criteria.createAlias("pointdeVenteConcerne", "pv");
			criteria.add(Restrictions.eq("pv.commune_id", idPv));
			return (List<MiseEnPlaceAEffectuer>) criteria.list();
		}

		// Filter pv et cat
		if (isCorrect(idPv) && isCorrect(categorie) && !isCorrect(idIntrant)) {
			criteria.createAlias("pointdeVenteConcerne", "pv");
			criteria.createAlias("ProduitAmettreEnPlace.typeIntrantId", "TypeIntrant");
			criteria.add(Restrictions.eq("pv.commune_id", idPv));
			criteria.add(Restrictions.eq("TypeIntrant.typeIntrantId", categorie));
			return (List<MiseEnPlaceAEffectuer>) criteria.list();
		}

		// Filter cat + int + pv
		if (isCorrect(idPv) && isCorrect(categorie) && isCorrect(idIntrant)) {
			criteria.createAlias("pointdeVenteConcerne", "pv");
			criteria.createAlias("ProduitAmettreEnPlace.typeIntrantId", "TypeIntrant");
			criteria.add(Restrictions.eq("pv.commune_id", idPv));
			criteria.add(Restrictions.eq("TypeIntrant.typeIntrantId", categorie));
			criteria.add(Restrictions.eq("ProduitAmettreEnPlace.refIntrantId", idIntrant));
			return (List<MiseEnPlaceAEffectuer>) criteria.list();
		}

		// filter categorie et intrant seulement (on ignore le point de vente)
		if (!isCorrect(idPv) && isCorrect(categorie) && isCorrect(idIntrant)) {
			criteria.createAlias("ProduitAmettreEnPlace.typeIntrantId", "TypeIntrant");
			criteria.add(Restrictions.eq("TypeIntrant.typeIntrantId", categorie));
			criteria.add(Restrictions.eq("ProduitAmettreEnPlace.refIntrantId", idIntrant));
			return (List<MiseEnPlaceAEffectuer>) criteria.list();
		}

		// filter categorie (on ignore le point de vente et intrant)
		if (!isCorrect(idPv) && isCorrect(categorie) && !isCorrect(idIntrant)) {
			criteria.createAlias("ProduitAmettreEnPlace.typeIntrantId", "TypeIntrant");
			criteria.add(Restrictions.eq("TypeIntrant.typeIntrantId", categorie));
			return (List<MiseEnPlaceAEffectuer>) criteria.list();
		}

		return null;
	}

	/***
	 * chercher si une mise en place existe deja (intrant + point de vente +
	 * idProgramme
	 * 
	 * @param idPv
	 *            : Id du point de vente
	 * @param categorie
	 *            :
	 * @param idIntrant
	 *            : id de L'intrant
	 * @return
	 * @throws EntityDBDAOException
	 */
	// org.hibernate.QueryException: could not resolve property: commune_id of:
	// sn.awi.pgca.dataModel.Commune
	@SuppressWarnings("unchecked")
	@Override
	public MiseEnPlaceAEffectuer verifierExistanceQuota(Long idPv, Long idIntrant, Long idProgramme)
			throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();

		Criteria criteria = session.createCriteria(MiseEnPlaceAEffectuer.class);

		criteria.createAlias("pointdeVenteConcerne", "commune");
		criteria.createAlias("programmeConcerne", "prog");

		criteria.add(Restrictions.eq("prog.pgca_idprog", idProgramme));
		criteria.add(Restrictions.eq("commune.id", idPv));
		criteria.add(Restrictions.eq("ProduitAmettreEnPlace.refIntrantId", idIntrant));
		MiseEnPlaceAEffectuer result = (MiseEnPlaceAEffectuer) criteria.uniqueResult();

		return result;
	}

	/**** Lecture des quotas aggregé par mise en place ***/
	@SuppressWarnings("unchecked")
	@Override
	public List<MiseEnPlaceAEffectuer> loadListQuotabyIdIntrant(Long idIntrant) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(MiseEnPlaceAEffectuer.class);
		criteria.createAlias("pointdeVenteConcerne", "pv");
		criteria.add(Restrictions.eq("ProduitAmettreEnPlace.refIntrantId", idIntrant));
		return (List<MiseEnPlaceAEffectuer>) criteria.list();
	}

	/**** Lecture des quotas aggregé par mise en place ***/
	@SuppressWarnings("unchecked")
	@Override
	public List<MiseEnPlaceAEffectuer> loadListQuotabyIdIntrantAndDepartement(Long idIntrant, Long idDepartement)
			throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(MiseEnPlaceAEffectuer.class);
		/*
		 * criteria.createAlias("pointdeVenteConcerne", "pv");
		 * criteria.createAlias("pv.adresse", "adr");
		 * criteria.createAlias("adr.departement", "dept");
		 */
		criteria.add(Restrictions.eq("ProduitAmettreEnPlace.refIntrantId", idIntrant));
		// criteria.add(Restrictions.eq("dept.id", idDepartement));

		return (List<MiseEnPlaceAEffectuer>) criteria.list();
	}

	// criteria.createAlias("ProduitAmettreEnPlace.typeIntrantId",
	// "TypeIntrant");

	@Override
	public MiseEnPlaceEffectuer loadMiseEnPlaceByidBl(String bl) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(MiseEnPlaceEffectuer.class);
		criteria.add(Restrictions.eq("blManuel", bl));

		return (MiseEnPlaceEffectuer) criteria.uniqueResult();
	}

	private boolean isCorrect(Long value) {
		return value != null && value > 0 ? true : false;
	}

	@Override
	public MiseEnPlaceEffectuer loadMiseEnPlaceByidLV(String lv) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(MiseEnPlaceEffectuer.class);
		criteria.add(Restrictions.eq("lvManuel", lv));

		return (MiseEnPlaceEffectuer) criteria.uniqueResult();
	}

	@Override
	public BonDeLivraison loadBlByNumeroBl(String lv) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(BonDeLivraison.class);
		criteria.add(Restrictions.eq("numeroBLManuel", lv));

		return (BonDeLivraison) criteria.uniqueResult();
	}

	/**
	 * 
	 * @return Les reliquats restants à exceutr
	 * @throws EntityDBDAOException
	 */
	@Override
	public List<MiseEnPlaceEffectuer> loadMiseEnPlaceHavingReliquat() throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(MiseEnPlaceAEffectuer.class);
		criteria.add(Restrictions.gt("reliquat", 0.0));

		return (List<MiseEnPlaceEffectuer>) criteria.list();
	}

	// TODO filter les bl des prog fermes : prof.status = 0
	@Override
	public int loadallBonDeLivraison() throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(BonDeLivraison.class);
		return criteria.list().size();
	}

	@Override
	public List<OrdreLivraison> loadOLEnAttentes() throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(OrdreLivraison.class);
		criteria.add(Restrictions.eq("statutOrd", ConstantPGCA.ORD_A_TRAITER));

		return (List<OrdreLivraison>) criteria.list();
	}

	@Override
	public List<OrdreLivraison> loadOLAcceptes() throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(OrdreLivraison.class);
		criteria.add(Restrictions.eq("statutOrd", ConstantPGCA.ORD_VALIDE));

		return (List<OrdreLivraison>) criteria.list();
	}

	@Override
	public List<OrdreLivraison> loadOLTraites() throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(OrdreLivraison.class);
		criteria.add(Restrictions.eq("statutOrd", ConstantPGCA.ORD_TRAITE));

		return (List<OrdreLivraison>) criteria.list();
	}

	@Override
	public List<MiseEnPlaceEffectuer> rechercherMiseEnplaceEffectueParIntrant(RechercheMiseEnPlaceDTO dto)
			throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();

		if (dto == null || dto.getIdIntrant() == null)
			return null;

		Date DateDebut = safeCastDate(dto.getDateDebut());
		Date DateFin = safeCastDate(dto.getDateFin());

		Criteria criteria;
		criteria = session.createCriteria(MiseEnPlaceEffectuer.class)
				.add(Restrictions.between("dateMiseEnplaceEffective", DateDebut, DateFin))
				.createAlias("miseEnplaceConcerne", "mep") // mep
				.createAlias("mep.ProduitAmettreEnPlace", "intrant")
				.add(Restrictions.eq("intrant.refIntrantId", dto.getIdIntrant()));
		if (dto.getIdProgr() != null && dto.getIdProgr() > 0L)
			criteria.add(Restrictions.eq("mep.programmeConcerne.pgca_idprog", dto.getIdProgr()));

		if (dto.getIdTransporteur() != null)
			criteria.add(Restrictions.eq("transporteur.idtransporteur", dto.getIdTransporteur()));

		if (dto.getIdDepartement() != null) {
			criteria.createAlias("mep.pointdeVenteConcerne", "pv") // mep
					.createAlias("pv.departement", "departement")
					.add(Restrictions.eq("departement.id", dto.getIdDepartement()));
		}

		return (List<MiseEnPlaceEffectuer>) criteria.list();
	}

	@Override
	public List<MiseEnPlaceEffectuer> loadAllMiseEnPlaceEffecByTypeIntrant(Long idIntrant) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();

		if (idIntrant == null)
			return null;

		Criteria criteria;
		criteria = session.createCriteria(MiseEnPlaceEffectuer.class)
				// .add(Restrictions.between("dateMiseEnplaceEffective",
				// safeCastDate(dateDebut), safeCastDate (dateFin)))
				.createAlias("miseEnplaceConcerne", "mep") // mep
				.createAlias("mep.ProduitAmettreEnPlace", "intrant")
				.add(Restrictions.eq("intrant.refIntrantId", idIntrant));

		return (List<MiseEnPlaceEffectuer>) criteria.list();
	}

	@Override
	public List<MiseEnPlaceEffectuer> rechercherMiseEnplace(RechercheMiseEnPlaceDTO dto) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();

		if (dto != null && dto.getIdIntrant() != null) {

			Criteria criteria = session.createCriteria(MiseEnPlaceEffectuer.class)
					.add(Restrictions.between("dateMiseEnplaceEffective", safeCastDate(dto.getDateFin()),
							safeCastDate(dto.getDateFin())))
					.createAlias("miseEnplaceConcerne", "mep") // mep
					.createAlias("mep.ProduitAmettreEnPlace", "intrant")
					.add(Restrictions.eq("intrant.refIntrantId", dto.getIdIntrant()));

			if (dto.getIdProgr() != null)
				criteria.add(Restrictions.eq("mep.programmeConcerne.pgca_idprog", dto.getIdProgr()));

			if (dto.getIdTransporteur() != null)
				criteria.add(Restrictions.eq("transporteur.idtransporteur", dto.getIdTransporteur()));

			if (dto.getIdDepartement() != null) {
				criteria.createAlias("mep.pointdeVenteConcerne", "pv") // mep
						.createAlias("pv.departement", "departement")
						.add(Restrictions.eq("departement.id", dto.getIdDepartement()));
			}

			return (List<MiseEnPlaceEffectuer>) criteria.list();
		} else {
			Criteria criteria = session.createCriteria(MiseEnPlaceEffectuer.class)
					.add(Restrictions.eq("dateMiseEnplaceEffective", dto.getDateDebut()));
			return (List<MiseEnPlaceEffectuer>) criteria.list();
		}
	}

	private Date safeCastDate(String dateString) {

		Date dateStringFormatDate = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // HH:mm:ss

		try {
			dateStringFormatDate = df.parse(dateString);
		} catch (ParseException e) { // on renvoi la date du jour
			try {
				dateStringFormatDate = df.parse(new Date().toString());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return dateStringFormatDate;
	}

	@Override
	public List<Subvention> loadAllSubvention() throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Subvention.class);

		return (List<Subvention>) criteria.list();
	}

	public static int ajouterRestriction(String sql, String valeur, Criteria criteria, boolean select) {
		if (valeur != null && valeur.trim().length() > 0 && (!select || (select && !valeur.equals("-")))) {
			valeur = "*" + valeur + "*";
			criteria.add(Restrictions.ilike(sql, valeur.replaceAll("\\*", "\\%")));
			return 1;
		}
		return 0;
	}

	public static int ajouterRestriction(String sql, Long valeur, Criteria criteria, boolean select) {
		if (valeur != null) {
			criteria.add(Restrictions.eq(sql, valeur));

			return 1;
		}
		return 0;
	}

	public static int ajouterRestriction(String sql, Boolean valeur, Criteria criteria, boolean select) {
		if (valeur != null) {
			criteria.add(Restrictions.eq(sql, valeur));

			return 1;
		}
		return 0;
	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	public void setEntityManager(EntityManager entityManager) {
		em = entityManager;
	}

	@Override
	public List<CampagneAgricole> lireCampagneParAnne(Date debut, Date fin) throws EntityDBDAOException {
		try {
			Query query = em.createNativeQuery("select * from pgca_CampagneAgricole ca   where ca.dateOuverture >= '"
					+ debut + "'  and  ca.datefermeture  <= '" + fin + "'", CampagneAgricole.class);
			return query.getResultList();

		} catch (Exception e) {
			logger.error("Erreur Recherche de campagne par année : " + e.getMessage());
			throw new EntityDBDAOException("Erreur récupération des campagnes", e);
		}

		/*
		 * Session session = (Session) em.getDelegate(); Criteria criteria =
		 * session.createCriteria(CampagneAgricole.class);
		 * criteria.add(Restrictions.ge("dateouverture", d));
		 * criteria.add(Restrictions.le("datefermeture", d2));
		 * 
		 * CampagneAgricole c = (CampagneAgricole) criteria.uniqueResult();
		 * return c;
		 */

	}

	@Override
	public boolean accepterCommande(Long idCommande) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Intrant loadProduitFromLibelle(String libelleProduit) throws EntityDBDAOException {
		Query query = null;
		try {
			query = em.createNativeQuery(
					"select * from pgca_intrant p where p.libelleProduit = '" + libelleProduit + "'", Intrant.class);

			return (Intrant) query.getResultList();
		} catch (Exception e) {
			logger.error("Erreur récupération Produit avec libelle : <" + libelleProduit + "> " + e.getMessage());
			throw new EntityDBDAOException("Erreur récupération des Profils", e);
		}
	}

	@Override
	public boolean getTypeIntrantByLibelle(String libelle) {
		query = em.createNativeQuery("select * from pgca_referentielTypeIntrant p where p.libelle = '" + libelle + "'",
				ReferentialTypeIntrants.class);

		@SuppressWarnings("unchecked")
		List<ReferentialTypeIntrants> e = (List<ReferentialTypeIntrants>) query.getResultList();

		try {
			ReferentialTypeIntrants e2 = e.get(0);

			if (e2 != null)
				return true;
		} catch (Exception e2) {
			// Index IndexOutOfBoundsException donc pas d'elemenet
			return false;
		}
		return false;
	}

	@Override
	public boolean getIntrantByLibelle(String libelle) {
		query = em.createNativeQuery("select * from pgca_referentielIntrant p where p.libelle = '" + libelle + "'",
				ReferentialIntrants.class);

		@SuppressWarnings("unchecked")
		List<ReferentialIntrants> e = (List<ReferentialIntrants>) query.getResultList();

		try {
			ReferentialIntrants e2 = e.get(0);

			if (e2 != null)
				return true;
		} catch (Exception e2) {
			// Index IndexOutOfBoundsException donc pas d'elemenet
			return false;
		}
		return false;
	}

	@Override
	public ReferentialIntrants loadIntrantByName(String libelle) throws EntityDBDAOException {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(ReferentialIntrants.class);
		criteria.add(Restrictions.eq("libelle", libelle));

		return (ReferentialIntrants) criteria.uniqueResult();

	}

	@Override
	public Fournisseur loadIFournisseursByName(String libelle) throws EntityDBDAOException {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Fournisseur.class);
		criteria.add(Restrictions.eq("libelle", libelle));

		return (Fournisseur) criteria.uniqueResult();
	}

	@Override
	public ProgrammeAgricol loadIProgrammeByName(String libelle) throws EntityDBDAOException {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(ProgrammeAgricol.class);
		criteria.add(Restrictions.eq("libelle", libelle));

		return (ProgrammeAgricol) criteria.uniqueResult();
	}

	@Override
	public Transporteur loadITransporteurByName(String libelle) throws EntityDBDAOException {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Transporteur.class);
		criteria.add(Restrictions.eq("libelle", libelle));

		return (Transporteur) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReferentialIntrants> loadReferentielIntrantByType(Long idTypeProduit) {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(ReferentialIntrants.class);
		criteria.add(Restrictions.eq("typeIntrantId.typeIntrantId", idTypeProduit));
		// criteria.add(Restrictions.eq("type.typeIntrantId", idTypeProduit));

		return criteria.list();
	}

	@Override
	public Intrant loadIntrantByTypeAndStock(Long idTypeIntrant, Long idStock) {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Intrant.class);
		criteria.add(Restrictions.eq("intrant.refIntrantId", idTypeIntrant));
		criteria.add(Restrictions.eq("stockRef.stock_id", idStock));

		@SuppressWarnings("unchecked")
		List<Intrant> lp = (List<Intrant>) criteria.list();

		try {
			return lp.get(0);

		} catch (Exception e2) {
			// Index IndexOutOfBoundsException donc pas d'elemenet
			return null;
		}
	}

	@Override
	public Intrant loadProduitByIdProduitIdStockIdProg(Long idStock, Long idProduit, Long idProg) {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Intrant.class);

		criteria.add(Restrictions.eq("produit_id", idProduit));
		criteria.add(Restrictions.eq("stockRef.stock_id", idStock));
		criteria.add(Restrictions.eq("ProgrammeAgricol.pgca_idca", idProg));

		return (Intrant) criteria.uniqueResult();
	}

	@Override
	public boolean validerOrdreDeLivraison(Long idCommande) throws EntityDBDAOException {
		return false;
	}

	@Override
	public int loadOLenAttenteDeValidation(Long idProgramme) {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(OrdreLivraison.class);
		criteria.add(Restrictions.eq("statutOrd", ConstantPGCA.ORD_A_TRAITER));

		@SuppressWarnings("unchecked")
		List<OrdreLivraison> lp = (List<OrdreLivraison>) criteria.list();

		if (lp == null)
			return 0;
		return lp.size();
	}

	@Override
	public List<OrdreLivraison> loadOLenAttenteDeTraitementByPointDeVente(Long idPointdeVente) {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(OrdreLivraison.class);
		criteria.add(Restrictions.eq("statutOrd", ConstantPGCA.ORD_VALIDE));
		criteria.add(Restrictions.eq("pointdeVenteTraitantLaCMD.ptv_id", idPointdeVente));
		return (List<OrdreLivraison>) criteria.list();
	}

	@Override
	public List<OrdreLivraison> loadOLValidesDeTraitementByPointDeVente(Long idPointdeVente) {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(OrdreLivraison.class);
		criteria.add(Restrictions.eq("statutOrd", 2));
		criteria.add(Restrictions.eq("pointdeVenteTraitantLaCMD.ptv_id", idPointdeVente));
		return (List<OrdreLivraison>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrdreLivraison> rechercheForManager(Long idProgramme, Long idPointdeVente, int status,
			String nomClient) {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(OrdreLivraison.class);
		// criteria.add(Restrictions.eq("nomClient", nomClient));
		criteria.add(Restrictions.eq("pointdeVenteTraitantLaCMD.ptv_id", idPointdeVente));
		criteria.add(Restrictions.eq("programmeConcerne.pgca_idprog", idProgramme));
		criteria.add(Restrictions.eq("statutOrd", status));

		return (List<OrdreLivraison>) criteria.list();

	}

	@Override
	public ProgrammeAgricol campapgneNameExist(Long idCampagne, String libelleCampagne) {
		Session session = (Session) em.getDelegate();

		Criteria criteria = session.createCriteria(ProgrammeAgricol.class);
		criteria.add(Restrictions.eq("libelle", libelleCampagne)).createAlias("campagne", "c") //
				.add(Restrictions.eq("c.id_ca", idCampagne));

		return (ProgrammeAgricol) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Commune> loadAllCommune() throws EntityDBDAOException {

		Query query = em.createNativeQuery("select * from commune order by code  desc ", Commune.class);

		try {
			return query.getResultList();
		} catch (Exception e) {
			logger.error("Erreur récupération des communes  : " + e.getMessage());
			throw new EntityDBDAOException("Erreur récupération des communes", e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IntrantDTO> loadStockResiduel() throws EntityDBDAOException {

		Session session = (Session) em.getDelegate();

		return session
				.createSQLQuery(
						"SELECT MAX(intrant.refintrantid) AS idProduit, MAX(intrant.libelle) AS libelleProduit , "
								+ "SUM(mep.quantiteAmettreEnplace) AS quantite, "
								+ "COUNT(mepAeffectue.commune_id) AS nombredePVDispo,  "
								+ "MAX(pv.commune_id) AS idCommune , MAX(pv.libelle) AS libelleCommune  ,  MAX(typeIntrant.pictointrant) AS pictoImages "
								+ "FROM pgca_MiseEnPlaceEffectuee mep   "
								+ "INNER JOIN pgca_MiseEnPlaceAEffectuee mepAeffectue ON mepAeffectue.id_mepaaf = mep.id_mepaaf   "
								+ "INNER JOIN commune pv ON mepAeffectue.commune_id = pv.commune_id   "
								+ "INNER JOIN pgca_referentielIntrant intrant ON intrant.refIntrantId = mepAeffectue.refIntrantId   "
								+ "INNER JOIN pgca_referentieltypeintrant  typeIntrant ON typeIntrant.typeintrantid  = intrant.typeintrantid "
								+ "GROUP BY (mepAeffectue.refintrantid) ;")
				.addScalar("idProduit", new LongType()).addScalar("libelleProduit", new StringType())
				.addScalar("quantite", new DoubleType()).addScalar("nombredePVDispo", new IntegerType())
				.addScalar("idCommune", new LongType()).addScalar("libelleCommune", new StringType())
				.addScalar("pictoImages", new StringType())
				.setResultTransformer(Transformers.aliasToBean(IntrantDTO.class)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IntrantDTO> loadStockResiduelByidCommune(Long idCommune) throws EntityDBDAOException {

		/*
		 * Session session = (Session) em.getDelegate(); return session
		 * .createSQLQuery(
		 * "SELECT MAX(intrant.refintrantid) AS idProduit, MAX(intrant.libelle) AS libelleProduit , "
		 * + "SUM(mep.quantiteAmettreEnplace) AS quantite, " +
		 * "COUNT(mepAeffectue.commune_id) AS nombredePVDispo,   " +
		 * "MAX(pv.commune_id) AS idCommune , MAX(pv.libelle) AS libelleCommune  ,  MAX(typeIntrant.pictointrant) AS pictoImages "
		 * + "FROM pgca_MiseEnPlaceEffectuee mep   " +
		 * "INNER JOIN pgca_MiseEnPlaceAEffectuee mepAeffectue ON mepAeffectue.id_mepaaf = mep.id_mepaaf   "
		 * +
		 * "INNER JOIN commune pv ON mepAeffectue.commune_id = pv.commune_id   "
		 * +
		 * "INNER JOIN pgca_referentielIntrant intrant ON intrant.refIntrantId = mepAeffectue.refIntrantId   "
		 * +
		 * "INNER JOIN pgca_referentieltypeintrant  typeIntrant ON typeIntrant.typeintrantid  = intrant.typeintrantid "
		 * + " Where pv.commune_id =  " + idCommune + "" +
		 * "GROUP BY (mepAeffectue.refintrantid) ;") .addScalar("idProduit", new
		 * LongType()).addScalar("libelleProduit", new StringType())
		 * .addScalar("quantite", new DoubleType()).addScalar("nombredePVDispo",
		 * new IntegerType()) .addScalar("idCommune", new
		 * LongType()).addScalar("libelleCommune", new StringType())
		 * .addScalar("pictoImages", new StringType())
		 * 
		 * .setResultTransformer(Transformers.aliasToBean(IntrantDTO.class)).
		 * list();
		 */

		query = em.createNativeQuery("select * from pgca_stockResiduelPointDeVente p where p.commune_id = " + idCommune,
				StockResiduelPointDeVente.class);

		List<StockResiduelPointDeVente> list = (List<StockResiduelPointDeVente>) query.getResultList();

		if (list == null)
			return null;

		List<IntrantDTO> tmp = new ArrayList<IntrantDTO>();
		for (StockResiduelPointDeVente p : list) {
			IntrantDTO cdeptdto = new IntrantDTO();

			cdeptdto.setIdStockResiduel(p.getStockResiduel_id());
			cdeptdto.setIdProduit(p.getIntrant().getProduit_id()); // t3 mef wo
			cdeptdto.setLibelleProduit(p.getIntrant().getLibelle()); // t3 mef
																		// wo
			cdeptdto.setLibelleProgramme(p.getIntrant().getProgramme().getLibelle());
			cdeptdto.setLibelleCampagne(p.getIntrant().getProgramme().getCampagne().getLibelle());
			cdeptdto.setPrixProducteur(p.getIntrant().getTarif().getPrixProducteur());
			cdeptdto.setTauxSubvention(p.getIntrant().getTarif().getTauxSubvention());
			cdeptdto.setQuantite(p.getTotalStockActuel());
			cdeptdto.setPictoImages(p.getIntrant().getLibelle().replace(" ", "").toLowerCase());
			cdeptdto.setSourceType(1);

			tmp.add(cdeptdto);
		}
		return tmp;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<IntrantDTO> loadStockResiduelByIntrant(Long
	 * idIntrant) throws EntityDBDAOException {
	 * 
	 * Session session = (Session) em.getDelegate();
	 * 
	 * return session.createSQLQuery(
	 * "SELECT MAX(intrant.refintrantid) AS idProduit, MAX(intrant.libelle) AS libelleProduit , "
	 * + "SUM(mep.quantiteAmettreEnplace) AS quantite, " +
	 * "MAX(prog.libelle) as libelleProgramme ," +
	 * "MAX(prog.pgca_idprog) AS idProg, " +
	 * "MAX(pv.commune_id) AS idCommune , " +
	 * "MAX(pv.libelle) AS libelleCommune " +
	 * "   FROM pgca_MiseEnPlaceEffectuee mep " +
	 * "   INNER JOIN pgca_MiseEnPlaceAEffectuee mepAeffectue ON mepAeffectue.id_mepaaf = mep.id_mepaaf "
	 * + "  INNER JOIN commune pv ON mepAeffectue.commune_id = pv.commune_id  "
	 * +
	 * "   INNER JOIN pgca_referentielIntrant intrant ON intrant.refIntrantId = mepAeffectue.refIntrantId  "
	 * +
	 * "   iNNER JOIN pgca_programme prog ON mepAeffectue.pgca_idprog  = prog.pgca_idprog"
	 * + "   where intrant.refintrantid = " + idIntrant +
	 * "   GROUP BY (mepAeffectue.id_mepaaf ) ") .addScalar("idProduit", new
	 * LongType()).addScalar("libelleProduit", new StringType())
	 * .addScalar("quantite", new DoubleType()).addScalar("libelleProgramme",
	 * new StringType()) .addScalar("idProg", new
	 * LongType()).addScalar("idCommune", new LongType())
	 * .addScalar("libelleCommune", new StringType())
	 * .setResultTransformer(Transformers.aliasToBean(IntrantDTO.class)).list();
	 * }
	 */

	/********* Coordinateur Services *********/

	/**** Recupération des intrants d'un point de vente par fournisseur *******/

	@SuppressWarnings("unchecked")
	@Override
	public List<IntrantDTO> loadStockResiduelSuperviseurAndCommune(Long idCommune, Long idPointdeCollecte)
			throws EntityDBDAOException {

		query = em.createNativeQuery("select * from pgca_stockResiduelPointDeVente p where p.commune_id = " + idCommune,
				StockResiduelPointDeVente.class);

		List<StockResiduelPointDeVente> list = (List<StockResiduelPointDeVente>) query.getResultList();

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(PointDeCollecte.class);
		criteria.add(Restrictions.eq("pdc_id", idPointdeCollecte));
		PointDeCollecte pcReference = (PointDeCollecte) criteria.uniqueResult();

		if (pcReference == null)
			return null;

		if (list == null)
			return null;

		List<IntrantDTO> tmp = new ArrayList<IntrantDTO>();
		for (StockResiduelPointDeVente p : list) {
			IntrantDTO cdeptdto = new IntrantDTO();

			cdeptdto.setIdStockResiduel(pcReference.getStock().getId());
			cdeptdto.setIdProduit(p.getIntrant().getProduit_id()); // t3 mef wo
			cdeptdto.setLibelleProduit(p.getIntrant().getLibelle());
			cdeptdto.setLibelleCommune(pcReference.getLibelle());
			cdeptdto.setLibelleProgramme(p.getIntrant().getProgramme().getLibelle());
			cdeptdto.setLibelleCampagne(p.getIntrant().getProgramme().getCampagne().getLibelle());
			cdeptdto.setPrixProducteur(p.getIntrant().getTarif().getPrixProducteur());
			cdeptdto.setTauxSubvention(p.getIntrant().getTarif().getTauxSubvention());
			cdeptdto.setQuantite(p.getTotalStockActuel());
			cdeptdto.setPictoImages(p.getIntrant().getLibelle().replace(" ", "").toLowerCase());
			cdeptdto.setSourceType(1);

			tmp.add(cdeptdto);
		}
		return tmp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StockResiduelPointDeVente createOrUpdateStockResiduelByPointdeVente(Long idCommune, Long idIntrant,
			Double quantite) throws EntityDBDAOException {

		try {
			Session session = (Session) em.getDelegate();
			Criteria criteria = session.createCriteria(StockResiduelPointDeVente.class);
			criteria.add(Restrictions.eq("pointdeVente.id", idCommune)).createAlias("intrant", "i") //
					.add(Restrictions.eq("i.produit_id", idIntrant));

			StockResiduelPointDeVente stock = (StockResiduelPointDeVente) criteria.uniqueResult();
			Tarificateur tarif;
			Intrant intrantDeReference = (Intrant) getEntityDBById(idIntrant, Intrant.class);
			tarif = intrantDeReference.getTarif() != null ? intrantDeReference.getTarif() : null;

			if (stock == null) {

				stock = new StockResiduelPointDeVente();
				stock.setDateDerniereActionStcok(new Date().toString());
				stock.setPointdeVente((Commune) getEntityDBById(idCommune, Commune.class));
				stock.setIntrant((Intrant) getEntityDBById(idIntrant, Intrant.class));
				stock.setTotalStockActuel(quantite);

				if (tarif != null)
					stock.setValeurtotalStockActuel(tarif.getPrixProducteur() * quantite);

				return stock;
			} else {
				stock.setTotalStockActuel(stock.getTotalStockActuel() + quantite);
				if (tarif != null)
					stock.setValeurtotalStockActuel(tarif.getPrixProducteur() * quantite);
			}
			return stock;
		} catch (Exception e) {
			logger.error("Eeerur mEP" + e.getMessage());
			return null;

		}
	}

	@Override
	public IntrantDTO loadStockResiduelByPointdeVente(Long idCommune, Long idIntrant) throws EntityDBDAOException {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(StockResiduelPointDeVente.class);
		criteria.add(Restrictions.eq("intrant.produit_id", idIntrant));
		criteria.add(Restrictions.eq("pointdeVente.id", idCommune));

		StockResiduelPointDeVente stock = (StockResiduelPointDeVente) criteria.uniqueResult();

		if (stock != null) {
			IntrantDTO temp = new IntrantDTO();

			temp.setIdStockResiduel(stock.getStockResiduel_id());
			temp.setQuantite(stock.getTotalStockActuel());
			temp.setLibelleCommune(stock.getPointdeVente().getLibelle());
			temp.setLibelleProduit(stock.getIntrant().getLibelle());
			temp.setValeurAcquisition(stock.getValeurtotalStockActuel());

			return temp;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IntrantDTO> loadStockResiduelByIntrant(Long idIntrant) throws EntityDBDAOException {

		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(StockResiduelPointDeVente.class).createAlias("intrant", "produit") // mep
				.createAlias("produit.intrant", "i") // intrant
				// .createAlias("i.typeIntrantId", "x") // type

				.add(Restrictions.eq("i.refIntrantId", idIntrant));

		List<StockResiduelPointDeVente> stockList = (List<StockResiduelPointDeVente>) criteria.list();
		List<IntrantDTO> stockListIntrant = new ArrayList<IntrantDTO>();

		for (StockResiduelPointDeVente stock : stockList) {
			IntrantDTO temp = new IntrantDTO();
			temp.setIdStockResiduel(stock.getStockResiduel_id());
			temp.setQuantite(stock.getTotalStockActuel());
			temp.setLibelleCommune(stock.getPointdeVente().getLibelle());
			temp.setLibellePointdeStock(stock.getPointdeVente().getDepartement().getLibelle());
			temp.setLibelleProduit(stock.getIntrant().getLibelle());
			temp.setValeurAcquisition(stock.getValeurtotalStockActuel());

			if (stock.getIntrant().getProgramme() != null)
				temp.setLibelleProgramme(stock.getIntrant().getProgramme().getLibelle());

			stockListIntrant.add(temp);

		}
		return stockListIntrant;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Commune> loadAllCommuneHomonyme(String commune) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();

		Criteria criteria = session.createCriteria(Commune.class);
		criteria.add(Restrictions.eq("libelle", commune));

		return (List<Commune>) criteria.list();

	}

	@Override
	public Commune loadCommuneFromLibelleCommuneAndLibelleDept(String commune, String departement) {

		Session session = (Session) em.getDelegate();

		Criteria criteria = session.createCriteria(Commune.class);
		criteria.add(Restrictions.eq("libelle", commune)).createAlias("departement", "dept")
				.add(Restrictions.eq("dept.libelle", departement));

		return (Commune) criteria.uniqueResult();
	}

	@Override
	public Long loadIPoitDeVenteIdByName(String intrantName) {

		// Format Point de vente homonyme a MLOMP (BIGNONA) - MLOMP (ZIG)
		if (intrantName == null || intrantName.trim().equals(""))
			return null;

		Commune com = null;

		Session session = (Session) em.getDelegate();
		String[] c = intrantName.split("\\(");

		if (c != null && c.length == 1) {

			Criteria criteria = session.createCriteria(Commune.class);
			criteria.add(Restrictions.eq("libelle", c[0]));

			com = (Commune) criteria.uniqueResult();
		} else if (c != null && c.length > 1) {
			String commune = c[0].trim();
			String[] temp = c[1].trim().split("\\)");

			String dept = temp[0];

			com = loadCommuneFromLibelleCommuneAndLibelleDept(commune, dept);
		}

		return com != null ? com.getId() : null;
	}

	@Override
	public List<MiseEnPlaceEffectuer> RechercheMiseEnPlaceDTO(sn.awi.pgca.web.dto.RechercheMiseEnPlaceDTO dto)
			throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stock getStockFromIdPointDeVente(Long idCommune) throws EntityDBDAOException {
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Stock.class);
		criteria.createAlias("communePointDevente", "com");
		criteria.add(Restrictions.eq("com.id", idCommune));
		return (Stock) criteria.uniqueResult();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CoupleDTO> loadAllIntrantFromAffectations(Long idUser) {
		List<AffectationsGestionPointDeVentes> af = (List<AffectationsGestionPointDeVentes>) genericSqlClassLoaderById(
				AffectationsGestionPointDeVentes.class, "pgca_affectationpointdeventes", "user_id", idUser);

		if (null == af)
			return null;

		List<CoupleDTO> allAF = new ArrayList<CoupleDTO>();

		for (AffectationsGestionPointDeVentes a : af) {

			CoupleDTO cdeptdto = new CoupleDTO(a.getId(), a.getPtv_id().getLibelle());
			cdeptdto.setClefValeur(a.getPtv_id().getCommune().getDepartement().getLibelle());
			cdeptdto.setIdPointDeVente(a.getPtv_id().getPtv_id());
			cdeptdto.setDepartementPv(a.getPtv_id().getCommune().getDepartement().getLibelle());
			cdeptdto.setStatus(a.getStatut());
			cdeptdto.setIdAffectation(a.getId_aff());

			if (a.getStatut() == 0) {
				cdeptdto.setShowBlocActif(true);
				cdeptdto.setBlocmsg("Desactiver l'affectation");
			} else {
				cdeptdto.setShowBlocActif(false);
				cdeptdto.setBlocmsg("Activer l'affectation");
			}

			allAF.add(cdeptdto);
		}
		return allAF;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AffectationsGestionPointDeVentes> loadAllPointDeVentesAffectationByUser(Long idUser) {
		return (List<AffectationsGestionPointDeVentes>) genericSqlClassLoaderById(
				AffectationsGestionPointDeVentes.class, "pgca_affectationpointdeventes", "user_id", idUser);
	}

	
	
	/**************  SERVICE SUPERVISEUR ****************/
	@Override
	public List<Ventes> loadVentesByGerantFournisseurs(Long idSuperviseur ) throws EntityDBDAOException {
		try {
			Query query = em.createNativeQuery("select * from pgca_ventes ca   where ca.pers_id = "+ idSuperviseur + "order by datedevente desc"
					, Ventes.class);
			return query.getResultList();

		} catch (Exception e) {
			logger.error("Erreur Recuperation Ventes par année : " + e.getMessage());
			throw new EntityDBDAOException("Erreur récupération des Ventes", e);
		}
	}
	
	
	/**************  SERVICE ALERTES RAPPORT MAILS AUTO FOR MANAGER ****************/

	@Override
	public List<Ventes> loadVentesParPeriode(Date dateDebut, Date dateFin ) throws EntityDBDAOException {
		try {
			Query query = em.createNativeQuery("select * from pgca_ventes where datedevente between '" 
						+ dateDebut +"' AND '" + dateFin +"' order by datedevente asc" , Ventes.class);
			return query.getResultList();

		} catch (Exception e) {
			logger.error("Erreur Recuperation Ventes par periode - alertes mail : " + e.getMessage());
			throw new EntityDBDAOException("Erreur récupération des Ventes", e);
		}
	}
	
	
}
