package org.cages.moulinette.repository;

import java.util.Date;
import java.util.List;

import org.cages.moulinette.model.AgentMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("AgentMissionRepository")
public interface AgentMissionRepository extends JpaRepository<AgentMission, Long> {
	
	 @Query(value = "SELECT * FROM agent_mission am join agent ag on am.a_id = ag.a_id "
	 		+ "join ordre_de_mission odm on am.odm_id = odm.odm_id "
	 		+ " WHERE ag.matricule = ?1"
	 		+ " and (odm.date_debut_mission <=?2 and odm.date_fin_mission >=?2 and odm.deleted= 0)"
	 		+ " and am.deleted=0 limit 200", nativeQuery = true)
	 public List<AgentMission> findOdmAg(String matricule,Date dateMission);
	
	 @Query(value = "SELECT * FROM agent_mission am "
	 		+ "join agent ag on am.a_id = ag.a_id"
	 		+ " join ordre_de_mission odm on am.odm_id = odm.odm_id "
	 		+ " WHERE ag.a_id = ?1 "
	 		+ "and odm.odm_id=?2 "
	 		+ "and odm.deleted= 0 limit 1", nativeQuery = true)
	 public AgentMission findOdmAg(long id_ag,Long odm_id);
	 
	 @Query(value = "SELECT * FROM agent_mission am "
	 		+ "join ordre_de_mission odm on am.odm_id = odm.odm_id "
	 		+ " WHERE  odm.odm_id=?1 limit 200", nativeQuery = true)
	 public List<AgentMission> findAmByOdm(Long odm_id);
	 
}
