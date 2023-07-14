package org.cages.moulinette.managedBean;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.commons.io.FileUtils;
import org.apache.poi.util.IOUtils;
import org.cages.moulinette.dto.DocumentSearchDTO;
import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.service.IExportService;
import org.cages.moulinette.service.IFilesService;
import org.cages.moulinette.service.IUserService;
import org.omnifaces.util.Faces;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "DocumentController")
@SessionScoped
@Getter
@Setter
public class DocumentController {

	private UserInfosDTO connectedUserDetails = new UserInfosDTO();
	private List<DocumentSearchDTO> listDocumentsSearch = new ArrayList<>();

	@ManagedProperty(value = "#{userService}")
	private IUserService userService;

	@ManagedProperty(value = "#{filesService}")
	private IFilesService filesService;

	@ManagedProperty(value = "#{exportService}")
	private IExportService exportService;

	@Value("${org.cages.files.path}")
	private String WORKING_DIRECTORY;

	@PostConstruct
	private void init() {
		connectedUserDetails = retrievedetailsConnectedUser();
	}

	public UserInfosDTO retrievedetailsConnectedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findUserByMatricule(user.getUsername());
	}




	public void downloadFile(String path, long odmId) throws Exception {
		if ("".equals(path)) {
			exporterTDR(odmId);
		} else {
			OutputStream outputStream = null;
			try {
				Map<String, Object> map = filesService.loadDocumentByPath(path);
				File dest = new File(System.getProperty("java.io.tmpdir") + WORKING_DIRECTORY + File.separator + map.get("filename"));
				org.apache.commons.io.FileUtils.copyFile((File) map.get("file"), dest);
				Faces.sendFile(dest, true);
			} catch (Exception e) {
				throw new Exception();
			} finally {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			}
		}
	}
	
	private void exporterTDR(long odmId) throws IOException {
		ByteArrayInputStream bais = exportService.exportTDRPdf(odmId);
		FileUtils.forceMkdir(new File(System.getProperty("java.io.tmpdir") + "/odm/pdf"));
		File file = new File(System.getProperty("java.io.tmpdir") + "/odm/pdf" + File.separator + "TDR_" + odmId + "_" + new Date().getTime() + ".pdf");
		// download file
		OutputStream outputStream = null;
		try {
			if (new InputStreamResource(bais) != null) {
				outputStream = new FileOutputStream(file);
				IOUtils.copy(new InputStreamResource(bais).getInputStream(), outputStream);
				Faces.sendFile(file, true);
			}
		} catch (IOException e) {

		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

}
