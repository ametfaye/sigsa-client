package org.cages.moulinette.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class AgentRepositoryImpl implements AgentRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;



	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
