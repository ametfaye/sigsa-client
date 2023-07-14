package org.cages.moulinette.service;

import java.util.List;

import org.cages.moulinette.model.Adresse;

public interface IAdresseService {
	
	public List<Adresse> getAllAdresses();

	public Adresse getAdresseById(long id);

}
