package sn.awi.pgca.web.bean;

import java.io.Serializable;

import sn.awi.pgca.web.dto.ConfigurationGlobaleDTO;

public class ConfigurationGlobaleManagedBean implements Serializable {

	private static final long				serialVersionUID	= -5210008992847084415L;

	private ConfigurationGlobaleDTO	configurationGlobaleDTO;

	public ConfigurationGlobaleDTO getConfigurationGlobaleDTO() {
		return configurationGlobaleDTO;
	}

	public void setConfigurationGlobaleDTO(ConfigurationGlobaleDTO configurationGlobaleDTO) {
		this.configurationGlobaleDTO = configurationGlobaleDTO;
	}

	public String initDetailConf() {
		
		return ConstantPGCA.SUCCESS;
	}

	public String sauvegarderDetailConf() {
		
		return ConstantPGCA.SUCCESS;
	}
}
