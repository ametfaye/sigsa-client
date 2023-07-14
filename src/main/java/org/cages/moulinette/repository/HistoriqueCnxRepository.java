package org.cages.moulinette.repository;

import org.cages.moulinette.model.HistoriqueDeConnexion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("HistoriqueCnxRepository")
public interface HistoriqueCnxRepository extends JpaRepository<HistoriqueDeConnexion, Long> {

}
