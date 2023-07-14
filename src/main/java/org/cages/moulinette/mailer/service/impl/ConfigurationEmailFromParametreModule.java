package org.cages.moulinette.mailer.service.impl;

import org.cages.moulinette.mailer.model.AdresseEmail;
import org.cages.moulinette.mailer.model.ConfigurationEmail;
import org.cages.moulinette.mailer.service.ConfigurationEmailRepository;
import org.cages.moulinette.model.ModuleApplicatif;
import org.cages.moulinette.model.ParametreModule;
import org.cages.moulinette.repository.ModuleApplicatifRepository;
import org.cages.moulinette.repository.ParametreModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("configurationEmailRepository")
public class ConfigurationEmailFromParametreModule implements ConfigurationEmailRepository {

	private static final String IDENTIFIANT = "IDENTIFIANT";
	private static final String MOT_DE_PASSE = "MOT_DE_PASSE";
	private static final String HOST = "HOST";
	private static final String PORT = "PORT";
	private static final String AUTH = "AUTH";
	private static final String ENABLESTARTTLS = "ENABLESTARTTLS";
	private static final String EXPEDITEURETB = "EXPEDITEURETB";
	private static final String REPLY_TO = "REPLY_TO";
	private static final String MODULE_APPLICATIF_CODE_SMTPT = "SMTPT";

	@Autowired
	private ParametreModuleRepository parametreModuleRepository;

	@Autowired
	private ModuleApplicatifRepository moduleApplicatifRepository;

	@Override
	public ConfigurationEmail getConfigurationEmailSendInBlue() {
		ModuleApplicatif moduleApplicatif = moduleApplicatifRepository.findFirstByMdaCode(MODULE_APPLICATIF_CODE_SMTPT);
		String identifiant = getValeur(IDENTIFIANT, moduleApplicatif);
		String motDePasse = getValeur(MOT_DE_PASSE, moduleApplicatif);
		String host = getValeur(HOST, moduleApplicatif);
		String port = getValeur(PORT, moduleApplicatif);
		String auth = getValeur(AUTH, moduleApplicatif);
		String enableStarttls = getValeur(ENABLESTARTTLS, moduleApplicatif);
		AdresseEmail expediteur = new AdresseEmail(getValeur(EXPEDITEURETB, moduleApplicatif));
		AdresseEmail replyTo = new AdresseEmail(getValeur(REPLY_TO, moduleApplicatif));
		ConfigurationEmail configurationEmail = new ConfigurationEmail(identifiant, motDePasse, host, port, auth, enableStarttls);
		configurationEmail.setExpediteur(expediteur);
		configurationEmail.setReplyTo(replyTo);
		return configurationEmail;
	}

	private String getValeur(String code, ModuleApplicatif moduleApplicatif) {
		ParametreModule parametre = parametreModuleRepository.findFirstByPamCodeAndModuleApplicatif(code,moduleApplicatif);
		return parametre != null ? parametre.getPamChaineValeur() : "";
	}
}
