package org.cages.moulinette.service.impl;

import java.util.List;

import org.cages.moulinette.model.SousService;
import org.cages.moulinette.repository.SousServiceRefRepo;
import org.cages.moulinette.service.ISousServiceRefService;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service("sousServiceRefService")
public class SousServiceRefService implements ISousServiceRefService {

	@Autowired
	private SousServiceRefRepo sousServiceRefRepo;

	@Override
	public List<SousService> getAllSousServices() {
		return sousServiceRefRepo.findAll();
	}

	@Override
	public SousService getSousServiceById(long id) {
		return sousServiceRefRepo.findOne(id);
	}

	@Override
	public void createSousService(SousService sousServiceToCreate) {
		sousServiceRefRepo.save(sousServiceToCreate);
	}

	@Override
	public void deleteSousService(long id) {
		sousServiceRefRepo.delete(id);
	}

	@Override
	public void updateSousService(SousService sousService) {
		sousServiceRefRepo.save(sousService);
	}

	@Override
	public SousService getSousServiceByCode(String code) {
		return sousServiceRefRepo.findByCode(code);
	}

	@Override
	public List<SousService> getSousServicesByServiceId(long srvId) {
		return sousServiceRefRepo.findByServiceId(srvId);
	}

}
