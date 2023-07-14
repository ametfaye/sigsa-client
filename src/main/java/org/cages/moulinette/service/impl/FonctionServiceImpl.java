package org.cages.moulinette.service.impl;

import java.util.List;

import org.cages.moulinette.model.Fonction;
import org.cages.moulinette.repository.FonctionRepository;
import org.cages.moulinette.service.IFonctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("fonctionService")
public class FonctionServiceImpl implements IFonctionService{

	@Autowired
	private FonctionRepository fonctionRepository;
	
	@Override
	public List<Fonction> getAllFonctions() {
		return fonctionRepository.findAll();
	}

	@Override
	public Fonction getFonctionById(long id) {
		return fonctionRepository.findOne(id);
	}

}
