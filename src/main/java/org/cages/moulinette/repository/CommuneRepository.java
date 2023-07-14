package org.cages.moulinette.repository;

import java.util.List;

import org.cages.moulinette.model.Commune;
import org.cages.moulinette.model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("CommuneRepository")
public interface CommuneRepository extends JpaRepository<Commune, Long> {
	
	List<Commune> findByDepartement(Departement departement);
}
