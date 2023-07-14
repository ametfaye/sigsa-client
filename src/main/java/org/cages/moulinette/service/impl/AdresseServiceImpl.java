package org.cages.moulinette.service.impl;

import java.util.List;

import org.cages.moulinette.model.Adresse;
import org.cages.moulinette.repository.AdresseRepository;
import org.cages.moulinette.service.IAdresseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adresseService")
public class AdresseServiceImpl implements IAdresseService {

	@Autowired
	private AdresseRepository adresseRepository;

	@Override
	public List<Adresse> getAllAdresses() {
		return adresseRepository.findAll();
	}

	@Override
	public Adresse getAdresseById(long id) {
		return adresseRepository.findOne(id);
	}
}
