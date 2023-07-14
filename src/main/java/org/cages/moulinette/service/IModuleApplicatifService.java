package org.cages.moulinette.service;

import org.cages.moulinette.model.ModuleApplicatif;

public interface IModuleApplicatifService {

	public ModuleApplicatif findFirstByMdaCode(String mdaCode);
}
