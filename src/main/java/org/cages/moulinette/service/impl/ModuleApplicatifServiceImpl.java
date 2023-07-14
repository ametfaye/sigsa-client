package org.cages.moulinette.service.impl;

import org.cages.moulinette.model.ModuleApplicatif;
import org.cages.moulinette.repository.ModuleApplicatifRepository;
import org.cages.moulinette.service.IModuleApplicatifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("moduleApplicatifService")
public class ModuleApplicatifServiceImpl implements IModuleApplicatifService {

	@Autowired
	private ModuleApplicatifRepository moduleApplicatifRepository;

	@Override
	public ModuleApplicatif findFirstByMdaCode(String mdaCode) {
		return moduleApplicatifRepository.findFirstByMdaCode(mdaCode);
	}
}
