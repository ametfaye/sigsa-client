package org.cages.moulinette.service;

import org.cages.moulinette.model.TypeDocument;

public interface ITypeDocumentService {

	TypeDocument findByCodeDoc(String code);

}
