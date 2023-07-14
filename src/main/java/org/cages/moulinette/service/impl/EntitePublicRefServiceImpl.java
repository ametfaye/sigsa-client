package org.cages.moulinette.service.impl;

import java.util.List;

import org.cages.moulinette.model.EntitePublicRef;
import org.cages.moulinette.repository.EntitePublicRefRepo;
import org.cages.moulinette.service.IEntitePublicRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("entitePublicRefService")
public class EntitePublicRefServiceImpl implements IEntitePublicRefService {

	@Autowired
	private EntitePublicRefRepo entitePublicRefRepo;

	@Override
	public List<EntitePublicRef> getAllEntitePublicRefs() {
		return entitePublicRefRepo.findAll();
	}

	@Override
	public EntitePublicRef getEntitePublicRefById(long id) {
		return entitePublicRefRepo.findOne(id);
	}

	@Override
	public void createEntitePublique(EntitePublicRef entitePubliqueToCreate) {
		entitePublicRefRepo.save(entitePubliqueToCreate);
	}

	@Override
	public void deleteEntitePublique(long id) {
		entitePublicRefRepo.delete(id);
	}

	@Override
	public void updateEntitePublique(EntitePublicRef entitePublicRef) {
		entitePublicRefRepo.save(entitePublicRef);
	}

	@Override
	public EntitePublicRef getEntitePubliqueByCode(String code) {
		return entitePublicRefRepo.findByCode(code);
	}
}
