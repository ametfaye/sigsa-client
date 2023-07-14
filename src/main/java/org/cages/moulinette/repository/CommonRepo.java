package org.cages.moulinette.repository;

import org.cages.moulinette.model.Commune;
import org.cages.moulinette.model.Departement;
import org.cages.moulinette.model.Region;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("commonRepo")
public interface CommonRepo  {

	
	@Query(value = "SELECT * FROM commune WHERE commune_id = ?1", nativeQuery = true)
	public Commune getCommuneById(Long id);
	
	@Query(value = "SELECT * FROM departement WHERE departement_id = ?1", nativeQuery = true)
	public Departement getDepartemntById(Long id);
	
	@Query(value = "SELECT * FROM region WHERE regn_id = ?1", nativeQuery = true)
	public Region getRegionBy(Long id);
}
