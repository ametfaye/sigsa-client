package org.cages.moulinette.service;

import java.util.List;

import org.cages.moulinette.model.ParametreModule;

public interface IParametreModuleService {

	public List<ParametreModule> getAllParametresModules();

	public List<ParametreModule> getParametresModulesMission();

	public void updateParametresModule(ParametreModule parametreModule);

	public ParametreModule getDureeMaximumMission(Long serviceId);

	public ParametreModule getDelaiMaximumMission(Long serviceId);
	
	public ParametreModule getMaximumParticipant(Long serviceId);
	
	public ParametreModule getDureeMaximumMission();

	public ParametreModule getDelaiMaximumMission();
	
	public ParametreModule getMaximumParticipant();

	public ParametreModule getParametreModuleByCode(String code);

	public void createParametreModule(ParametreModule parametreModule);

	public void deleteParametre(ParametreModule parametreModule);
}
