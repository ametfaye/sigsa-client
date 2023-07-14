package org.cages.moulinette.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cages.moulinette.dto.DocumentOdmAstDTO;
import org.cages.moulinette.model.Document;
import org.cages.moulinette.repository.DocumentRepository;
import org.cages.moulinette.service.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("documentService")
public class DocumentServiceImpl implements IDocumentService {

	@Autowired
	private DocumentRepository documentRepository;

	@Override
	public void createDocument(Document document) {
		documentRepository.save(document);
	}

	@Override
	public Document getDocumentByOdmIdAndAgentIdAndType(Long odmId, Long aId, String type) {
		return documentRepository.findByOdmIdAndAgentIdAndType(odmId, aId, type);
	}

	@Override
	public List<DocumentOdmAstDTO> getDocumentsByOdmIdAndType(Long odmId, String type) {
		List<DocumentOdmAstDTO> res = new ArrayList<>();
		List<Document> documents = documentRepository.findByOdmIdAndType(odmId, type);
		if (null != documents && !documents.isEmpty()) {
			for (Document document : documents) {
				DocumentOdmAstDTO dto = new DocumentOdmAstDTO(
						document.getDocId(),
						document.getAgent().getMatricule(),
						document.getAgent().getPersonnePhysique().getNom(),
						document.getAgent().getPersonnePhysique().getPrenom(), 
						document.getDocPath(),
						document.getStatutDocument().getLibelle());
				res.add(dto);
			}
		}
		return res;
	}

}
