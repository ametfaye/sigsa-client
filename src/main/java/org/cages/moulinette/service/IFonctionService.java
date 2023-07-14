package org.cages.moulinette.service;

import java.util.List;

import org.cages.moulinette.model.Fonction;

public interface IFonctionService {

	public List<Fonction> getAllFonctions();
	
	public Fonction getFonctionById(long id);
}
