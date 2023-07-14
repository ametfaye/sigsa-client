package org.cages.moulinette.repository;

import org.cages.moulinette.model.BienAgricoleProducteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("BienAgricolesitory")
public interface BienAgricoleRepository extends JpaRepository<BienAgricoleProducteur, Long> {
	
}
