package sn.awi.pgca.business.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.dataModel.GenericModel;

/**
 * DAO Genrics
 * 
 * @author Mouhamed FAYE
 */
@Transactional(readOnly = true)
public abstract class GenericDAOImpl implements Serializable {

	
	@PersistenceContext(unitName = "PUnitPGCA", type = PersistenceContextType.TRANSACTION)
	private EntityManager				em;
	
	private static final long serialVersionUID = -5427690256257295572L;

	private static final Log LOG = LogFactory.getLog(GenericDAOImpl.class);

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public final void save(GenericModel entity) throws EntityDBDAOException {
		if (entity.getId() == null) {
			getEntityManager().persist(entity);
		} else {
			getEntityManager().merge(entity);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean removeModel(GenericModel model) throws EntityDBDAOException {
		try {
			System.err.println("----- Suppression Entité " +  model.getClass().getName());
			model = getEntityManager().merge(model);
			getEntityManager().remove(model);
			return true;
		} catch (Exception e) {
			LOG.error("Erreur lors de la suppresion du modele " + model.getClass().getName());
		}
		return false;
	}

	public final GenericModel getEntityDBById(Long id, Class<?> classe) throws EntityDBDAOException {
		GenericModel object = null;
		Query query = getEntityManager().createQuery("from " + classe.getSimpleName() + " o where o.id = :id");
		query.setParameter("id", id);
		
		try {
			object = (GenericModel) query.getSingleResult();
		} catch (Exception e) {
			LOG.debug(classe.getSimpleName() + " avec l'id <" + id + "> n'existe pas en base de donnees.");
		}
		return object;
	}

	public final List<GenericModel> getEntityDBByIdAndProgramme(Long idEntity, Class<?> Entity , String stringPrimaKeyName,  Long idProgramme) throws EntityDBDAOException {
		GenericModel object = null;
		
		Query query = getEntityManager().createQuery("from " + Entity.getSimpleName()  + " o where o." + stringPrimaKeyName +" = :idE");

		query.setParameter("idE", idEntity); 
		try {
			return  query.getResultList();
		} catch (Exception e) {
			
			LOG.debug(Entity.getSimpleName() + " avec l'id <" + idEntity + "> n'existe pas en base de donn�es.");
			return null;

		}
	}
	
	
	public abstract EntityManager getEntityManager();

}
