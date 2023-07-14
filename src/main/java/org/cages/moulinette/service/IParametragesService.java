package org.cages.moulinette.service;

import org.cages.moulinette.dto.ParametragesDTO;

public interface IParametragesService {
	
	public ParametragesDTO loadParametrages();

	public Boolean updateContextParametrage(String contexte, ParametragesDTO dto);

}
