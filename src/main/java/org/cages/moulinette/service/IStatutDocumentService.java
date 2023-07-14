package org.cages.moulinette.service;

import org.cages.moulinette.model.StatutDocument;

public interface IStatutDocumentService {

	StatutDocument findByCodeStatut(String code);

}
