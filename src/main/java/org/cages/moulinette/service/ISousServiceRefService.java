package org.cages.moulinette.service;

import java.util.List;

import org.cages.moulinette.model.SousService;

public interface ISousServiceRefService {

	public List<SousService> getAllSousServices();
	
	public SousService getSousServiceById(long id);

	public void createSousService(SousService sousServiceToCreate);

	public void deleteSousService(long id);

	public void updateSousService(SousService sousService);

	public SousService getSousServiceByCode(String code);
	
	public List<SousService> getSousServicesByServiceId(long srvId);

}
