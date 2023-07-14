package org.cages.moulinette.repository;

import java.util.List;

import org.cages.moulinette.model.CampagneAgricole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("campagneRepo")
public interface CampagneAgricoleRepository extends JpaRepository<CampagneAgricole, Long> {


	@Query(value = "SELECT * FROM pgca_campagne_agricole WHERE statut = ?1 ", nativeQuery = true)
	public List<CampagneAgricole> loadCampagneByStatus(int statut); 

}
