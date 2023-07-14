package org.cages.moulinette.repository;

import org.cages.moulinette.model.OrdreDeMission;
import org.cages.moulinette.model.RapportOrdreDeMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("RapportOrdreDeMissionRepository")
public interface RapportOdmRepository extends JpaRepository<RapportOrdreDeMission, Long> {

	RapportOrdreDeMission findByOrdreDeMission(OrdreDeMission odm);

}
