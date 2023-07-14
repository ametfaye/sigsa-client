package org.cages.moulinette.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.cages.moulinette.dto.AgentDTO;
import org.cages.moulinette.service.IExportService;
import org.cages.moulinette.service.IFilesService;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("filesService")
public class FilesServiceImpl implements IFilesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FilesServiceImpl.class);

	@Autowired
	private IExportService exportService;

	@Value("${org.cages.files.path}")
	private String WORKING_DIRECTORY;

	@Override
	public String createRapportFile(UploadedFile file, Long odmId) throws Exception {
		return createFileInStorage(file.getFileName(), file.getInputstream(), odmId, null);
	}

	@Override
	public Map<String, Object> loadDocumentByPath(String path) {
		Map<String, Object> res = new HashMap<>();
		File file = new File(WORKING_DIRECTORY + path);
		res.put("filename", path.substring(path.lastIndexOf("/"), path.length()));
		res.put("file", file);
		return res;
	}

	@Override
	public String createODMFile(AgentDTO participant, Long odmId) throws Exception {
		ByteArrayInputStream bais = exportService.exportOdmPdf(odmId);
		createTempFilesDir();
		return createFileInStorage("ODM.pdf", bais, odmId, participant);
	}

	@Override
	public String createASTFile(AgentDTO participant, Long odmId) throws Exception {
		ByteArrayInputStream bais = exportService.exportAstPdf(participant.getMatricule(), odmId);
		createTempFilesDir();
		return createFileInStorage("AST.pdf", bais, odmId, participant);
	}

	@Override
	public String replaceODMFile(AgentDTO participant, Long odmId, String oldPath) throws Exception {
		String path = createODMFile(participant, odmId);
		// delete old file
		Files.deleteIfExists(Paths.get(WORKING_DIRECTORY + oldPath));
		return path;
	}

	@Override
	public String replaceASTFile(AgentDTO participant, Long odmId, String oldPath) throws Exception {
		String path = createASTFile(participant, odmId);
		// delete old file
		Files.deleteIfExists(Paths.get(WORKING_DIRECTORY + oldPath));
		return path;
	}

	private String createFileInStorage(String fileName, InputStream inputStream, Long odmId, AgentDTO participant) throws Exception {
		// generate file path
		LocalDate today = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String dateString = today.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		String fileNameGenerated = createFileName();
		String agentId = "";
		if (null != participant) 
			agentId = "/"+participant.getMatricule();
		String pathFile = "/" + dateString + "/" + odmId + agentId + "/" + fileNameGenerated + fileName.substring(fileName.lastIndexOf('.'), fileName.length());
		String pathFileWithoutFileName = "/" + dateString + "/" + odmId + agentId;
		File fileDest = new File(WORKING_DIRECTORY + pathFileWithoutFileName);
		try {
			// check if file exist then create it
			if (!fileDest.exists()) {
				FileUtils.forceMkdir(fileDest);
			}

			// create file in system
			createFileInDirectory(fileNameGenerated, fileName.substring(fileName.lastIndexOf('.'), fileName.length()), inputStream, fileDest);
			LOGGER.info("Fichier créé avec succès.");
			return pathFile;
		} catch (Exception e) {
			throw new Exception("Erreur interne lors du dépôt du fichier: " + e.getMessage());
		}
	}

	private void createFileInDirectory(String fileName, String fileType, InputStream in, File fileDest) {
		try {
			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(fileDest + "/" + fileName + fileType);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			in.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void createTempFilesDir() throws IOException {
		FileUtils.forceMkdir(new File(System.getProperty("java.io.tmpdir") + "/odm/pdf"));
	}

	private String createFileName() {
		return String.valueOf(ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault()).toInstant().toEpochMilli());
	}
}
