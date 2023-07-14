package org.cages.moulinette.service.impl;

import org.cages.moulinette.model.HistoriqueDeConnexion;
import org.cages.moulinette.repository.HistoriqueCnxRepository;
import org.cages.moulinette.service.IHistoriqueCnxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("historiqueCnxService")
public class HistoriqueCnxServiceImpl implements IHistoriqueCnxService{

	@Autowired
	private HistoriqueCnxRepository historiqueCnxRepository;
	
	@Override
	public HistoriqueDeConnexion save(HistoriqueDeConnexion historiqueDeConnexion) {
		return historiqueCnxRepository.save(historiqueDeConnexion);
	}

}
