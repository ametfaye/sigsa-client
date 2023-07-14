package org.cages.moulinette.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cages.moulinette.dto.AgentDTO;
import org.cages.moulinette.model.Agent;
import org.cages.moulinette.model.OrdreDeMission;
import org.cages.moulinette.repository.AgentRepository;
import org.cages.moulinette.service.IAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("agentService")
public class AgentServiceImpl implements IAgentService {

	@Autowired
	private AgentRepository agentRepository;
	


	@Override
	public Agent findByMatricule(String mat) {
		return agentRepository.findByMatricule(mat);
	}

	@Override
	public void createAgent(Agent agent) {
		agent.setDeleted(0);
		agentRepository.save(agent);
	}

	@Override
	public List<AgentDTO> findAllNotDeleted() {
		List<Agent> agents = agentRepository.findAllNotDeleted();
		List<AgentDTO> listDTOS = new ArrayList<>();
		if (agents == null)
			return listDTOS;

		agents.stream().forEachOrdered(agent -> {
			AgentDTO agentDTO = new AgentDTO();
			agentDTO.setNom(agent.getPersonnePhysique().getNom());
			agentDTO.setPrenom(agent.getPersonnePhysique().getPrenom());
			agentDTO.setServiceLibelle(agent.getSousService().getLibelle());
			agentDTO.setFonction(agent.getFonction().getLibelle());
			agentDTO.setMatricule(agent.getMatricule());
			agentDTO.setEtablissement(agent.getSousService().getService().getEntite().getLibelle());
			listDTOS.add(agentDTO);
		});
		return listDTOS;
	}

	@Override
	public void delete(Agent agent) {
		Agent agentToDelete = findByMatricule(agent.getMatricule());
		if (agentToDelete != null) {
			agentToDelete.setDeleted(1);
			agentToDelete.setDateSuppression(new Date());
			agentRepository.save(agentToDelete);
		}
	}

	@Override
	public List<AgentDTO> agentsByService(Long servId) {
		List<Agent> agents = agentRepository.loadAgentsByServ(servId);
		List<AgentDTO> agentDTOs = new ArrayList<>();
		if (agents != null && !agents.isEmpty()) {
			agents.forEach(agent -> {
				AgentDTO agentDTO = new AgentDTO(agent.getAId(), agent.getMatricule(), agent.getPersonnePhysique().getNom(),
						agent.getPersonnePhysique().getPrenom(), agent.getFonction().getLibelle(), false);
				agentDTOs.add(agentDTO);
			});
		}
		return agentDTOs;
	}

	@Override
	public void updateAgent(Agent agentToUpdate) {
		agentRepository.save(agentToUpdate);
	}

	@Override
	public List<AgentDTO> findAllAgentByOdm(Long odmId) {
		return null;
	}


	@Override
	public List<Agent> findAllAgentAdd(Long odmId,Long servID) {
		return agentRepository.findAllAgentAdd(odmId,servID);
	}

	@Override
	public List<AgentDTO> findAllAgents() {
		List<Agent> agents = agentRepository.findAllAgents();
		List<AgentDTO> listDTOS = new ArrayList<>();
		if (agents == null)
			return listDTOS;

		agents.stream().forEachOrdered(agent -> {
			AgentDTO agentDTO = new AgentDTO();
			agentDTO.setNom(agent.getPersonnePhysique().getNom());
			agentDTO.setPrenom(agent.getPersonnePhysique().getPrenom());
			agentDTO.setServiceLibelle(agent.getSousService().getLibelle());
			agentDTO.setFonction(agent.getFonction().getLibelle());
			agentDTO.setMatricule(agent.getMatricule());
			agentDTO.setEtablissement(agent.getSousService().getService().getEntite().getLibelle());
			listDTOS.add(agentDTO);
		});
		return listDTOS;
	}

	@Override
	public AgentDTO agentfindByMatricule(String matricule) {
		Agent agent= agentRepository.agentfindByMatricule(matricule);
		AgentDTO agentDTO = new AgentDTO();
		if (agent == null)
			return agentDTO;
		agentDTO.setNom(agent.getPersonnePhysique().getNom());
		agentDTO.setPrenom(agent.getPersonnePhysique().getPrenom());
		agentDTO.setServiceLibelle(agent.getSousService().getLibelle());
		agentDTO.setFonction(agent.getFonction().getLibelle());
		agentDTO.setMatricule(agent.getMatricule());
		agentDTO.setEtablissement(agent.getSousService().getService().getEntite().getLibelle());
		agentDTO.setAId(agent.getAId());
		return agentDTO;
	}


}
