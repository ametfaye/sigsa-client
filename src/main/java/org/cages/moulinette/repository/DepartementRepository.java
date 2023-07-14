package org.cages.moulinette.repository;

import java.util.Set;

import org.cages.moulinette.model.Departement;
import org.cages.moulinette.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("departementRepository")
public interface DepartementRepository extends JpaRepository<Departement, Long> {
	
		Set<Departement> findByRegion(Region region);
}
