package org.cages.moulinette.repository;

import org.cages.moulinette.model.EtatOrdreDeMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("EtatOdmŒRepository")
public interface EtatOrdreDeMissionRepository extends JpaRepository<EtatOrdreDeMission, Long> {
	
	public EtatOrdreDeMission findByCodeEtat(String cod);

	@Query(value = "SELECT count(*) FROM ordre_de_mission odm "
				+ "join etat_ordre_de_mission eodm on eodm.eodm_id = odm.eodm_id where"
				+ " eodm.code_etat = ?1 and odm.deleted = 0", nativeQuery = true)
	public Integer findNbrOdmByEtat(String codeEtat);

}
