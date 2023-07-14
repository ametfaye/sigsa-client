package org.cages.moulinette.repository;

import java.util.List;

import org.cages.moulinette.model.HistoriqueOrdreDeMission;
import org.cages.moulinette.model.OrdreDeMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("historiqueOdmRepository")
public interface HistoriqueOrdreDeMissionRepository extends JpaRepository<HistoriqueOrdreDeMission, Long>{

	 List<HistoriqueOrdreDeMission> findByOrdreDeMissionOrderByDateCreationAsc(OrdreDeMission ordreDeMission);
}
