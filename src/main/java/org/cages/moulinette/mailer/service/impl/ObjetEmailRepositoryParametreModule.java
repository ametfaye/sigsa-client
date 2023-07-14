package org.cages.moulinette.mailer.service.impl;

import javax.annotation.PostConstruct;

import org.cages.moulinette.mailer.service.ObjetEmailRepository;
import org.cages.moulinette.model.ModuleApplicatif;
import org.cages.moulinette.model.ParametreModule;
import org.cages.moulinette.repository.ModuleApplicatifRepository;
import org.cages.moulinette.repository.ParametreModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjetEmailRepositoryParametreModule implements ObjetEmailRepository {

	private static final String MODULE_APPLICATIF_CODE_EMAIL = "EMAIL";

	@Autowired
	private ParametreModuleRepository parametreModuleRepository;

	@Autowired
	private ModuleApplicatifRepository moduleApplicatifRepository;

	private ModuleApplicatif moduleApplicatif;

	@PostConstruct
	public void init() {
		this.moduleApplicatif = moduleApplicatifRepository.findFirstByMdaCode(MODULE_APPLICATIF_CODE_EMAIL);

	}

	public String getObjet(String code) {
		ParametreModule parametre = parametreModuleRepository.findFirstByPamCodeAndModuleApplicatif(code,this.moduleApplicatif);
		return parametre != null ? parametre.getPamChaineValeur() : "";
	}

}
