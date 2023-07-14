package org.cages.moulinette.service.impl;

import org.cages.moulinette.model.StatutDocument;
import org.cages.moulinette.repository.StatutDocumentRepository;
import org.cages.moulinette.service.IStatutDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("statutDocumentService")
public class StatutDocumentServiceImpl implements IStatutDocumentService {

	@Autowired
	private StatutDocumentRepository statutDocumentRepository;

	@Override
	public StatutDocument findByCodeStatut(String code) {
		return statutDocumentRepository.findByCode(code);
	}
}

