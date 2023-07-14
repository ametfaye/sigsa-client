package org.cages.moulinette.service;

import java.util.List;

import org.cages.moulinette.model.EntitePublicRef;

public interface IEntitePublicRefService {

	public List<EntitePublicRef> getAllEntitePublicRefs();
	
	public EntitePublicRef getEntitePublicRefById(long id);

	public void createEntitePublique(EntitePublicRef entitePubliqueToCreate);

	public void deleteEntitePublique(long id);

	public void updateEntitePublique(EntitePublicRef entitePublicRef);

	public EntitePublicRef getEntitePubliqueByCode(String code);

}
