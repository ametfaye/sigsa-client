package org.cages.moulinette.service;

import java.util.List;

import org.cages.moulinette.dto.DocumentOdmAstDTO;
import org.cages.moulinette.model.Document;

public interface IDocumentService {

	public void createDocument(Document document);

	public List<DocumentOdmAstDTO> getDocumentsByOdmIdAndType(Long odmId, String type);
	
	public Document getDocumentByOdmIdAndAgentIdAndType(Long odmId, Long aId, String type);

}
