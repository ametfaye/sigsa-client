package sn.awi.pgca.business.service;

import sn.awi.pgca.web.dto.ConfigurationGlobaleDTO;

public interface IConfigurationGlobale {

	public ConfigurationGlobaleDTO loadInfoConfGlobale();
	
	public void sauvegarderInfoConfGlobale(ConfigurationGlobaleDTO configurationGlobaleDTO);
}
