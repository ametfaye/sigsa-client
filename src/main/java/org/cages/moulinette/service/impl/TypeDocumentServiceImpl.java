package org.cages.moulinette.service.impl;

import org.cages.moulinette.model.TypeDocument;
import org.cages.moulinette.repository.TypeDocumentRepository;
import org.cages.moulinette.service.ITypeDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("typeDocumentService")
public class TypeDocumentServiceImpl implements ITypeDocumentService {

	@Autowired
	private TypeDocumentRepository typeDocumentRepository;

	@Override
	public TypeDocument findByCodeDoc(String code) {
		return typeDocumentRepository.findByCodeDoc(code);
	}
}
