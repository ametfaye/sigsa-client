package org.cages.moulinette.service.impl;

import java.util.List;

import org.cages.moulinette.model.ParametreModule;
import org.cages.moulinette.repository.ParametreModuleRepository;
import org.cages.moulinette.service.IParametreModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("parametreModuleService")
public class ParametreModuleServiceImpl implements IParametreModuleService {

	@Autowired
	private ParametreModuleRepository parametreModuleRepository;

	@Override
	public List<ParametreModule> getAllParametresModules() {
		return parametreModuleRepository.findAll();
	}

	@Override
	public List<ParametreModule> getParametresModulesMission() {
		return parametreModuleRepository.getParametresModulesMission();
	}

	@Override
	public ParametreModule getDureeMaximumMission(Long serviceId) {
		return parametreModuleRepository.getDureeMaximumMission(serviceId);
	}

	@Override
	public ParametreModule getDelaiMaximumMission(Long serviceId) {
		return parametreModuleRepository.getDelaiMaximumMission(serviceId);
	}
	
	@Override
	public ParametreModule getMaximumParticipant(Long serviceId) {
		return parametreModuleRepository.getMaxParticipant(serviceId);
	}

	@Override
	public void updateParametresModule(ParametreModule parametreModule) {
		parametreModuleRepository.save(parametreModule);
	}

	@Override
	public ParametreModule getParametreModuleByCode(String code) {
		return parametreModuleRepository.findFirstByPamCode(code);
	}

	@Override
	public void createParametreModule(ParametreModule parametreModule) {
		parametreModuleRepository.save(parametreModule);
	}

	@Override
	public void deleteParametre(ParametreModule parametreModule) {
		parametreModuleRepository.delete(parametreModule);
	}

	@Override
	public ParametreModule getDureeMaximumMission() {
		return parametreModuleRepository.getDureeMaximumMission();

	}

	@Override
	public ParametreModule getDelaiMaximumMission() {
		return parametreModuleRepository.getDelaiMaximumMission();
	}

	@Override
	public ParametreModule getMaximumParticipant() {
		return parametreModuleRepository.getMaxParticipant();
	}
}
