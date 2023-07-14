package sn.awi.pgca.business.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.dataModel.GenericModel;
import sn.awi.pgca.dataModel.Intrant;
import sn.awi.pgca.dataModel.MiseEnPlaceAEffectuer;
import sn.awi.pgca.dataModel.Ventes;

public interface GenericDAO {

	/*****************************************************************  
	 *  Generics Loader Methode pour lire une table de maniere generic
	 * 
	 *  **************************************************************/
	
	public abstract List<?> genericClassLoader(Class<?> classe);
	
	/**  a documenter
 	 * ***/
	public abstract List<?> genericClassLoaderById(Class<?> classe, String ColumPivotName,  Long classID);
	
	public abstract List<?> genericSqlClassLoaderById(Class<?> classe, String classMappingInDB, String ColumPivotName,  Long classID);
	
	
	
	public abstract EntityManager getEntityManager();
	
	public abstract GenericModel getEntityDBById(Long id, Class<?> classe) throws EntityDBDAOException;
	
	public abstract  List<GenericModel> getEntityDBByIdAndProgramme(Long idEntity, Class<?> Entity , String stringPrimaKeyName,  Long idProgramme) throws EntityDBDAOException ;

	public abstract void save(GenericModel entity) throws EntityDBDAOException;

	public abstract boolean removeModel(GenericModel entity) throws EntityDBDAOException;

	public List<Intrant> loadAllMagasinStock();
	
	
	/*****************************************************************  
	 *  Recupération des ventes par période pour les alertes auto
	 * 
	 *  **************************************************************/
	
	public List<Ventes> loadVentesParPeriode(Date dateDebut, Date dateFin) throws EntityDBDAOException;

	

}