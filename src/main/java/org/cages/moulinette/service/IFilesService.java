package org.cages.moulinette.service;

import java.util.Map;

import org.cages.moulinette.dto.AgentDTO;
import org.primefaces.model.UploadedFile;

public interface IFilesService {

	public String createRapportFile(UploadedFile file, Long odmId) throws Exception;

	public Map<String, Object> loadDocumentByPath(String path);

	public String createODMFile(AgentDTO participant, Long odmId) throws Exception;

	public String createASTFile(AgentDTO participant, Long odmId) throws Exception;

	public String replaceODMFile(AgentDTO participant, Long odmId, String oldPath) throws Exception;
	
	public String replaceASTFile(AgentDTO participant, Long odmId, String oldPath) throws Exception;
}
