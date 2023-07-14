package org.cages.moulinette.repository;

import java.util.List;

import org.cages.moulinette.model.SousService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("sousServicePublic")
public interface SousServiceRefRepo extends JpaRepository<SousService, Long> {

	SousService findByCode(String code);

	@Query(value = "SELECT * FROM sous_services WHERE srv_id = ?1 limit 200", nativeQuery = true)
	public List<SousService> findByServiceId(long srvId);

}
