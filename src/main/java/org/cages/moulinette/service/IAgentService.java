package org.cages.moulinette.service;

import java.util.List;

import org.cages.moulinette.dto.AgentDTO;
import org.cages.moulinette.model.Agent;

public interface IAgentService {

	Agent findByMatricule(String mat);
	
	List<AgentDTO> findAllNotDeleted();

	void createAgent(Agent agent);
	
	List<AgentDTO> agentsByService(Long servId);

	void delete(Agent agent);
	
	List<AgentDTO> findAllAgentByOdm(Long odm_id);
	
	List<Agent> findAllAgentAdd(Long odm_id,Long servID);
	
	List<AgentDTO> findAllAgents();
	
	AgentDTO agentfindByMatricule(String matricule);

	

	void updateAgent(Agent agentToUpdate);
}
