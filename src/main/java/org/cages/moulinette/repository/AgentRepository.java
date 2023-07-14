package org.cages.moulinette.repository;

import java.util.List;

import org.cages.moulinette.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("AgentRepository")
public interface AgentRepository extends JpaRepository<Agent, Long>, AgentRepositoryCustom {

	public Agent findByMatricule(String mat);

	@Query(value = "SELECT * FROM agent WHERE deleted = 0 limit 200", nativeQuery = true)
	public List<Agent> findAllNotDeleted();

	 @Query(value = "SELECT * FROM agent where ssrv_id= ?1 limit 200", nativeQuery = true)
	 List<Agent> loadAgentsByServ(Long sServID);
	 
	 @Query(value = "SELECT ag.* FROM agent ag "
				 		+ " join agent_mission am on ag.a_id=am.a_id "
				 		+ " join ordre_de_mission odm on am.odm_id = odm.odm_id"
				 		+ " where odm.odm_id= ?1 and am.deleted= 0 limit 200", nativeQuery = true)
	 List<Agent> findAllAgentByOdm(Long odmId);
	 
	 @Query(value = "SELECT ag.* FROM agent ag "
	 				 + " where ag.a_id not in (select am.a_id from agent_mission am where am.odm_id =?1 and am.deleted =0)"
                     + " and ag.ssrv_id =?2 limit 200", nativeQuery = true)
     List<Agent> findAllAgentAdd(Long odmId,Long servID);
	 
	 @Query(value = "SELECT * FROM agent WHERE deleted = 0 limit 200", nativeQuery = true)
	 public List<Agent> findAllAgents();
	
	 @Query(value = "SELECT * FROM agent WHERE matricule=?1 limit 1", nativeQuery = true)
	 public Agent agentfindByMatricule(String matricule);

}
