package sn.awi.pgca.web.bean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Value;

import com.itextpdf.text.DocumentException;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.service.ICommonService;
import sn.awi.pgca.business.service.IProgrammeAgricol;
import sn.awi.pgca.business.service.IRechercheService;
import sn.awi.pgca.business.service.ITresorerieService;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.MiseEnPlaceEffectuerDTO;
import sn.awi.pgca.web.dto.MiseEnplaceDTOPointDeVente;
import sn.awi.pgca.web.dto.RechercheMiseEnPlaceDTO;
import sn.awi.pgca.web.dto.VersementBanqueDTO;
import sn.awi.pgca.web.export.ReportPDFGerenics;

@ManagedBean(name = "rechercheMB")
@SessionScoped
public class RechercheManagedBean implements Serializable {

	private static final long serialVersionUID = -7103834664247383927L;

	private static final org.apache.commons.logging.Log LOG = LogFactory.getLog(RechercheManagedBean.class);

	@ManagedProperty(value = "#{tresorerieService}")
	private ITresorerieService tresorerieService;

	@ManagedProperty(value = "#{commonService}")
	private ICommonService commonService;

	@ManagedProperty(value = "#{rechercheService}")
	private IRechercheService rechercheServices;
	
	@ManagedProperty(value = "#{programmeAgricolService}")
	private IProgrammeAgricol programmAgricoleService;

	VersementBanqueDTO versementDTO = new VersementBanqueDTO();

	@Value("${pgca.gedVersementPath}")
	String pathFileVersement = "/Users/Amet/GED-PGCA/Versement/";
	// String pathFileVersement = "/home/awa/GED-PGCA/Versement/";


	List<MiseEnplaceDTOPointDeVente> listIntrant; // filter
	Long idIntrant;
	
	private List<IntrantDTO> listReferentielfiltred;
	private Long idTypeIntrant;
	
	
	// Critère de recherche pour génération feuille de mise en place
	String searchdateDebut;
	String searchdateFin;
	
	Date dateDebut;
	Long searchidIntrant;
	Long searchidIntrantType;
	Long searchidPointDeVente;
	String searchidIntrantCast ; // concate des deux id type t id intrant 
	String infoMEPDispo;

	// Recherche MEP Recherhe Global
	String intrantMEP ; 
	String intrnatMETDateDEbut;
	String intrnatMETDateFin;
	String intrnatMETDepartement;
	String intrnatMETProg;
	String intrnatMETTransporteur;
	List<MiseEnplaceDTOPointDeVente> intrnatMEResultat;
	int intrnatMEResultatSize = 0;
	boolean showResultBloc ;
	String intrnatMEResultatSizeInfosMessages;
	List<MiseEnplaceDTOPointDeVente> intrnatMEResultatFiltred;

	

	
	public String rechercherMepParPeriode()
	{ 
		Long idIntrant;
		try {
			idIntrant = commonService.loadIntrantIdByName(intrantMEP);
			
			if (intrantMEP == null) {
				showMessage(FacesMessage.SEVERITY_WARN,
						"L'intrant <i>" + intrantMEP + "<i/> n'existe pas dans la campgne actuelle ");
			}
			
		
				
			
			RechercheMiseEnPlaceDTO criteres =  new RechercheMiseEnPlaceDTO();
			
			criteres.setDateDebut(intrnatMETDateDEbut);
			criteres.setDateFin(intrnatMETDateFin);
			criteres.setIdIntrant(idIntrant);
			
			
			if(intrnatMETProg != null && !intrnatMETProg.trim().equals(""))
			{
				Long idProgramme = commonService.loadIProgrammeIdByName(intrnatMETProg);
				if (idProgramme == null) {
					showMessage(FacesMessage.SEVERITY_WARN,
							"Le Programme que vous avez saisie n'existe pas dans la campgne actuelle ");
					return "#";
				}
				else
					criteres.setIdProgr(idProgramme);

			}
			
			
			if(intrnatMETTransporteur != null && ! intrnatMETTransporteur.trim().equals(""))
			{
				Long idTransport = commonService.loadITransporeurByName(intrnatMETTransporteur);
				if (idTransport == null) {
					showMessage(FacesMessage.SEVERITY_ERROR,
							"Le Transporteur  que vous avez saisi n'existe pas dans la plateforme  ");
					return "#";
				}
				else
					criteres.setIdTransporteur((idTransport));

			}
			
			if(intrnatMETDepartement != null && ! intrnatMETDepartement.trim().equals(""))
			{
				Long idDepartement = commonService.loadIDepartementByName(intrnatMETDepartement);
				if (idDepartement == null) {
					showMessage(FacesMessage.SEVERITY_ERROR,
							"Le Departement  que vous avez saisi n'existe pas dans la plateforme  ");
					return "#";
				}
				else
					criteres.setIdDepartement(idDepartement);

			}
			
			
			
			
			intrnatMEResultat = rechercheServices.rechercherMiseEnplaceEffectueParIntrant(criteres);
			
			if(intrnatMEResultat !=   null && intrnatMEResultat.size() > 0)
			{
				showResultBloc = true;
				intrnatMEResultatSize  =  intrnatMEResultat.size();
				intrnatMEResultatSizeInfosMessages =  intrnatMEResultatSize + " mise (s) en place de " + intrantMEP + " trouvée (s) entre la date du " + intrnatMETDateDEbut + " au " + intrnatMETDateFin;
			}
			else
			{
				showResultBloc = false;
				intrnatMEResultatSize = 0;
				intrnatMEResultatSizeInfosMessages = "Aucune mise en place trouvées avec vos critères";
			}
			
			return "recherchepgcaGlobal.xhtml?faces-redirect=true&idBlocToShow=1";


		} catch (EntityDBDAOException e) {
			LOG.error("Une erreur est surveneur lors de la recherche" + e.getMessage());
		}
		return "recherchepgcaGlobal.xhtml?faces-redirect=true&idBlocToShow=1";

	}
	
	
	public void showMessage(Severity severity, String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);

		FacesMessage msg = new FacesMessage(severity, "", message);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	
	public void rereshLisAndfilterIntrantByCriteria()
	{
		try {
			listReferentielfiltred = programmAgricoleService
					.loadReferentielIntrantByType(searchidIntrantType);
			
			// filter list by categorie first !
			filterIntrantByCriteria();
		} catch (EntityDBDAOException e) {
			LOG.error("Une erreur est survenue lors des updates ... ");
			e.printStackTrace();
		}
	}

	/************
	 * EXPORT SERVICES
	 * 
	 * LISTE DES MISES EN PLACES APRES FILTER
	 * 
	 ***********/

	int colNum = 0;
	int totalTonnage = 0;
	int rowNum = 5;
	XSSFWorkbook workbook = new XSSFWorkbook();
	CreationHelper createHelper = workbook.getCreationHelper();

	public String exportListMiseEnplace() throws IOException {

		if (listIntrant == null || listIntrant.size() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERREUR NO_IDMEP",
					"Aucune mise en place trouvée pour l'intrant sélectionné !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesQuotasdeMEP.xhtml";
		}

		String fileToDl;
		String libelleIntant = "";
		if (listIntrant != null && listIntrant.size() > 0) { // ConstantPGCA.Rapport_DIRECTORY_PATH_LOCAL
																// ->
			fileToDl = /* ConstantPGCA.Rapport_DIRECTORY_PATH + */ "Rapport - "
					+ listIntrant.get(0).getLibelleIntrantAMettreEnplace() + ".xlsx";
			libelleIntant = listIntrant.get(0).getLibelleIntrantAMettreEnplace();
		} else
			fileToDl = /* ConstantPGCA.Rapport_DIRECTORY_PATH + */ "RapportVide.xlsx";
		File file = new File(fileToDl);
		if (!file.exists()) {
			file.createNewFile();
		} else {
			FileOutputStream writer = new FileOutputStream(fileToDl);
			writer.write(("").getBytes());
			writer.close();
		}

		XSSFSheet sheet = workbook.createSheet("RAPPORT MISE EN  PLACE - " + libelleIntant);

		Font font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 14);
		Byte byte1 = 100;

		font2.setFontName("Arial");
		font2.setColor(IndexedColors.GREEN.getIndex());

		CellStyle style2 = workbook.createCellStyle();
		style2.setFont(font2);

		Font font3 = workbook.createFont();
		font3.setFontHeightInPoints(byte1);
		font3.setFontName("Times New Roman");
		font3.setColor(IndexedColors.LAVENDER.getIndex());
		CellStyle style3 = workbook.createCellStyle();
		style3.setFont(font3);

		Row title = sheet.createRow(2);
		Cell cellT = title.createCell(2);
		cellT.setCellValue("DATE");
		cellT.setCellStyle(style2);

		cellT = title.createCell(3);
		cellT.setCellValue("Distributeur ");
		cellT.setCellStyle(style2);

		cellT = title.createCell(4);
		cellT.setCellValue("Quantité ");
		cellT.setCellStyle(style2);

		cellT = title.createCell(5);
		cellT.setCellValue(createHelper.createRichTextString("This is a string Transport "));

		cellT.setCellStyle(style2);

		cellT = title.createCell(6);
		cellT.setCellValue("Camion ");
		cellT.setCellStyle(style2);

		cellT = title.createCell(7);
		cellT.setCellValue("Chauffeur ");
		cellT.setCellStyle(style2);

		cellT = title.createCell(8);
		cellT.setCellValue("Destinataire");
		cellT.setCellStyle(style2);

		cellT = title.createCell(9);
		cellT.setCellValue("Point de Vente ");
		cellT.setCellStyle(style2);

		cellT = title.createCell(10);
		cellT.setCellValue("Département ");
		cellT.setCellStyle(style2);

		rowNum = 5;
		colNum = 0;
		totalTonnage = 0;

		for (MiseEnplaceDTOPointDeVente datatype : listIntrant) {
			Row row = sheet.createRow(rowNum++);

			colNum = 2;
			insertData(row, datatype);

			// Cell cell = row.createCell(colNum);
			// cell = row.createCell(colNum++);
			// cell.setCellValue(datatype.getDateMiseEnplace());
			//
			// cell = row.createCell(colNum++);
			// cell.setCellValue(datatype.getFournisseur());
			//
			// cell = row.createCell(colNum++);
			// cell.setCellValue(datatype.getQuantiteIntrantAMettreEnplace());
			// totalTonnage += datatype.getQuantiteIntrantAMettreEnplace();
			//
			// cell = row.createCell(colNum++);
			// cell.setCellValue(datatype.getTransporteur());
			//
			// cell = row.createCell(colNum++);
			// cell.setCellValue(datatype.getCamion());
			//
			// cell = row.createCell(colNum++);
			// cell.setCellValue(datatype.getChauffeur());
			//
			// cell = row.createCell(colNum++);
			// cell.setCellValue(datatype.getNomGerant());
			//
			// cell = row.createCell(colNum++);
			// cell.setCellValue(datatype.getNomPointDeVente());
			//
			// cell = row.createCell(colNum++);
			// cell.setCellValue(datatype.getDepartementPointdeVente());

		}

		Row total = sheet.createRow(rowNum + 1);
		Cell cellTotal = total.createCell((colNum / 2) - 3);

		cellTotal.setCellValue("Total");

		cellTotal.setCellStyle(style2);

		Cell cellTotalV = total.createCell((colNum / 2) - 1);
		cellTotalV.setCellValue(totalTonnage);

		sheet.autoSizeColumn(1000, true);
		sheet.setAutobreaks(true);

		try {
			FileOutputStream outputStream = new FileOutputStream(fileToDl);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");

		final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

		// Prepare.
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		// File file = new File(ABSOLUTEPATH, pathVersement.getName());
		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			// Open file.
			input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);

			// Init servlet response.
			response.reset();
			response.setHeader("Content-Type", "application/vnd.ms-excel");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\"" + fileToDl + "\"");
			output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

			// Write file contents to response.
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}

			// Finalize task.
			output.flush();
		} finally {
			// Gently close streams.
			close(output);
			close(input);
		}
		facesContext.responseComplete();
		return "gestionDesQuotasdeMEP.xhtml";
	}



	/*************** REPORT PDF **************/

	public String reportRechercheMiseEnplaceBypdfFormat() throws IOException {
		
		if(intrnatMEResultat  ==  null)
			return "#";
		
		ReportPDFGerenics report = new ReportPDFGerenics(ConstantPGCA.DIRECTORY_PATH_TMP_PDF, "Rapport journalier de mise en place par catégorie");
		try {
			report.generateResultMiseEnPlace(intrnatMEResultat);
			
			File f = new File(ConstantPGCA.DIRECTORY_PATH_TMP_PDF);
			byte[] pdf = IOUtils.toByteArray(new FileInputStream(f));
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			IOUtils.write(pdf, outputStream);
			FacesContext faces = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setContentLength(pdf.length);
			response.setHeader("Content-disposition", "filename=\"" +  "rapport TO DL" + "" + "\"");
			try {
				ServletOutputStream out;
				out = response.getOutputStream();
				out.write(pdf);
			} catch (IOException e) {
				e.printStackTrace();
			}
			faces.responseComplete();
		} catch (DocumentException e) {
			Log.error("----  Erreur surveneue pendant telechargement fichier");
			e.printStackTrace();
		}
		return "gestionDesQuotasdeMEP.xhtml";
	}
	
	
	/*********************/
	
	
	
	
	
	public void filterIntrantByCriteria() {

	}
	
	private void insertData(Row row, MiseEnplaceDTOPointDeVente datatype) {
		if (row != null) {
			Cell cell = row.createCell(colNum);
			cell = row.createCell(colNum++);
			cell.setCellValue(datatype.getDateMiseEnplace());

			cell = row.createCell(colNum++);
			cell.setCellValue(datatype.getFournisseur());

			cell = row.createCell(colNum++);
			cell.setCellValue(datatype.getQuantiteIntrantAMettreEnplace());
			totalTonnage += datatype.getQuantiteIntrantAMettreEnplace();

			cell = row.createCell(colNum++);

			cell.setCellValue(datatype.getTransporteur());

			cell = row.createCell(colNum++);
			cell.setCellValue(datatype.getCamion());

			cell = row.createCell(colNum++);
			cell.setCellValue(datatype.getChauffeur());

			cell = row.createCell(colNum++);
			cell.setCellValue(datatype.getNomGerant());

			cell = row.createCell(colNum++);
			cell.setCellValue(datatype.getNomPointDeVente());

			cell = row.createCell(colNum++);
			cell.setCellValue(datatype.getDepartementPointdeVente());
		}
	}

	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String downloadJustificatif() throws IOException {

		if (versementDTO == null)
			return null;
		
		RechercheMiseEnPlaceDTO criteres  =  new RechercheMiseEnPlaceDTO();
		criteres.setDateDebut(searchdateDebut);
		criteres.setDateFin(searchdateFin);
		criteres.setIdIntrant(searchidIntrant);
		
		listIntrant = rechercheServices.rechercherMiseEnplaceEffectueParIntrant(criteres);

		if (listIntrant == null || listIntrant.size() == 0) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERREUR NO_IDMEP",
					"Aucune mise en place trouvée pour l'intrant sélectionné !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "gestionDesQuotasdeMEP.xhtml";
		}

		String fileToDl;
		String libelleIntant = "";

		// ConstantPGCA.Rapport_DIRECTORY_PATH_LOCAL ->
		// "/Users/Amet/GED-PGCA/Rapport/
		if (listIntrant != null && listIntrant.size() > 0) {
			fileToDl = ConstantPGCA.Rapport_DIRECTORY_PATH + "Rapport - "
					+ listIntrant.get(0).getLibelleIntrantAMettreEnplace() + ".xlsx";
			libelleIntant = listIntrant.get(0).getLibelleIntrantAMettreEnplace();
		} else
			fileToDl = ConstantPGCA.Rapport_DIRECTORY_PATH + "RapportVide.xlsx";

		File file = new File(fileToDl);
		if (!file.exists()) {
			file.createNewFile();
		} else {
			FileOutputStream writer = new FileOutputStream(fileToDl);
			writer.write(("").getBytes());
			writer.close();
		}

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("RAPPORT MISE EN  PLACE - " + libelleIntant);

		Font font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 14);
		Byte byte1 = 100;

		font2.setFontName("Arial");
		font2.setColor(IndexedColors.GREEN.getIndex());

		CellStyle style2 = workbook.createCellStyle();
		style2.setFont(font2);

		Font font3 = workbook.createFont();
		font3.setFontHeightInPoints(byte1);
		font3.setFontName("Times New Roman");
		font3.setColor(IndexedColors.LAVENDER.getIndex());
		CellStyle style3 = workbook.createCellStyle();
		style3.setFont(font3);

		Row title = sheet.createRow(2);
		Cell cellT = title.createCell(2);
		cellT.setCellValue("DATE");
		cellT.setCellStyle(style2);

		cellT = title.createCell(3);
		cellT.setCellValue("Distributeur ");
		cellT.setCellStyle(style2);

		cellT = title.createCell(4);
		cellT.setCellValue("Quantité ");
		cellT.setCellStyle(style2);

		cellT = title.createCell(5);
		cellT.setCellValue("Transport ");
		cellT.setCellStyle(style2);

		cellT = title.createCell(6);
		cellT.setCellValue("Camion ");
		cellT.setCellStyle(style2);

		cellT = title.createCell(7);
		cellT.setCellValue("Chauffeur ");
		cellT.setCellStyle(style2);

		cellT = title.createCell(8);
		cellT.setCellValue("Destinataire");
		cellT.setCellStyle(style2);

		cellT = title.createCell(9);
		cellT.setCellValue("Point de Vente ");
		cellT.setCellStyle(style2);

		cellT = title.createCell(10);
		cellT.setCellValue("Département ");
		cellT.setCellStyle(style2);

		int rowNum = 5;
		int colNum = 0;
		int totalTonnage = 0;

		for (MiseEnplaceDTOPointDeVente datatype : listIntrant) {
			Row row = sheet.createRow(rowNum++);

			colNum = 2;
			Cell cell = row.createCell(colNum++);
			cell.setCellValue(datatype.getDateMiseEnplace());

			cell = row.createCell(colNum++);
			cell.setCellValue(datatype.getFournisseur());

			cell = row.createCell(colNum++);
			cell.setCellValue(datatype.getQuantiteIntrantAMettreEnplace());
			totalTonnage += datatype.getQuantiteIntrantAMettreEnplace();

			cell = row.createCell(colNum++);
			cell.setCellValue(datatype.getTransporteur());

			cell = row.createCell(colNum++);
			cell.setCellValue(datatype.getCamion());

			cell = row.createCell(colNum++);
			cell.setCellValue(datatype.getChauffeur());

			cell = row.createCell(colNum++);
			cell.setCellValue(datatype.getNomGerant());

			cell = row.createCell(colNum++);
			cell.setCellValue(datatype.getNomPointDeVente());

			cell = row.createCell(colNum++);
			cell.setCellValue(datatype.getDepartementPointdeVente());
		}

		Row total = sheet.createRow(rowNum + 1);
		Cell cellTotal = total.createCell((colNum / 2) - 3);

		cellTotal.setCellValue("Total");
		cellTotal.setCellStyle(style2);

		Cell cellTotalV = total.createCell((colNum / 2) - 1);
		cellTotalV.setCellValue(totalTonnage);

		try {
			FileOutputStream outputStream = new FileOutputStream(fileToDl);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");

		final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

		// Prepare.
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		// File file = new File(ABSOLUTEPATH, pathVersement.getName());
		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			// Open file.
			input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);

			// Init servlet response.
			response.reset();
			response.setHeader("Content-Type", "application/vnd.ms-excel");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\"" + fileToDl + "\"");
			output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

			// Write file contents to response.
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}

			// Finalize task.
			output.flush();
		} finally {
			// Gently close streams.
			close(output);
			close(input);
		}
		facesContext.responseComplete();
		return "gestionDesQuotasdeMEP.xhtml";
	}

	public ITresorerieService getTresorerieService() {
		return tresorerieService;
	}

	public void setTresorerieService(ITresorerieService tresorerieService) {
		this.tresorerieService = tresorerieService;
	}

	public List<MiseEnplaceDTOPointDeVente> getIntrnatMEResultatFiltred() {
		return intrnatMEResultatFiltred;
	}


	public String getIntrnatMEResultatSizeInfosMessages() {
		return intrnatMEResultatSizeInfosMessages;
	}


	public void setIntrnatMEResultatSizeInfosMessages(String intrnatMEResultatSizeInfosMessages) {
		this.intrnatMEResultatSizeInfosMessages = intrnatMEResultatSizeInfosMessages;
	}


	public void setIntrnatMEResultatFiltred(List<MiseEnplaceDTOPointDeVente> intrnatMEResultatFiltred) {
		this.intrnatMEResultatFiltred = intrnatMEResultatFiltred;
	}


	public VersementBanqueDTO getVersementDTO() {
		return versementDTO;
	}

	public void setVersementDTO(VersementBanqueDTO versementDTO) {
		this.versementDTO = versementDTO;
	}



	public String getPathFileVersement() {
		return pathFileVersement;
	}

	public void setPathFileVersement(String pathFileVersement) {
		this.pathFileVersement = pathFileVersement;
	}

	public String getSearchdateDebut() {
		return searchdateDebut;
	}

	public void setSearchdateDebut(String searchdateDebut) {
		this.searchdateDebut = searchdateDebut;
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public Long getSearchidIntrantType() {
		return searchidIntrantType;
	}

	public Long getIdTypeIntrant() {
		return idTypeIntrant;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public void setIdTypeIntrant(Long idTypeIntrant) {
		this.idTypeIntrant = idTypeIntrant;
	}

	public Long getIdIntrant() {
		return idIntrant;
	}

	public void setIdIntrant(Long idIntrant) {
		this.idIntrant = idIntrant;
	}

	public void setSearchidIntrantType(Long searchidIntrantType) {
		this.searchidIntrantType = searchidIntrantType;
	}

	public String getSearchdateFin() {
		return searchdateFin;
	}

	public IRechercheService getRechercheServices() {
		return rechercheServices;
	}

	public void setRechercheServices(IRechercheService rechercheServices) {
		this.rechercheServices = rechercheServices;
	}

	public String getSearchidIntrantCast() {
		return searchidIntrantCast;
	}

	public IProgrammeAgricol getProgrammAgricoleService() {
		return programmAgricoleService;
	}

	public void setProgrammAgricoleService(IProgrammeAgricol programmAgricoleService) {
		this.programmAgricoleService = programmAgricoleService;
	}

	public List<IntrantDTO> getListReferentielfiltred() {
		return listReferentielfiltred;
	}

	public void setListReferentielfiltred(List<IntrantDTO> listReferentielfiltred) {
		this.listReferentielfiltred = listReferentielfiltred;
	}

	public boolean isShowResultBloc() {
		return showResultBloc;
	}


	public void setShowResultBloc(boolean showResultBloc) {
		this.showResultBloc = showResultBloc;
	}


	public void setSearchidIntrantCast(String searchidIntrantCast) {
		this.searchidIntrantCast = searchidIntrantCast;
	}

	public XSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(XSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public CreationHelper getCreateHelper() {
		return createHelper;
	}

	public void setCreateHelper(CreationHelper createHelper) {
		this.createHelper = createHelper;
	}

	public void setSearchdateFin(String searchdateFin) {
		this.searchdateFin = searchdateFin;
	}

	public List<MiseEnplaceDTOPointDeVente> getListIntrant() {
		return listIntrant;
	}

	public void setListIntrant(List<MiseEnplaceDTOPointDeVente> listIntrant) {
		this.listIntrant = listIntrant;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public Long getSearchidIntrant() {
		return searchidIntrant;
	}

	public void setSearchidIntrant(Long searchidIntrant) {
		this.searchidIntrant = searchidIntrant;
	}

	public String getInfoMEPDispo() {
		return infoMEPDispo;
	}

	public void setInfoMEPDispo(String infoMEPDispo) {
		this.infoMEPDispo = infoMEPDispo;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getTotalTonnage() {
		return totalTonnage;
	}

	public void setTotalTonnage(int totalTonnage) {
		this.totalTonnage = totalTonnage;
	}

	public Long getSearchidPointDeVente() {
		return searchidPointDeVente;
	}

	public void setSearchidPointDeVente(Long searchidPointDeVente) {
		this.searchidPointDeVente = searchidPointDeVente;
	}


	public String getIntrantMEP() {
		return intrantMEP;
	}


	public void setIntrantMEP(String intrantMEP) {
		this.intrantMEP = intrantMEP;
	}


	public String getIntrnatMETDateDEbut() {
		return intrnatMETDateDEbut;
	}


	public void setIntrnatMETDateDEbut(String intrnatMETDateDEbut) {
		this.intrnatMETDateDEbut = intrnatMETDateDEbut;
	}


	public String getIntrnatMETDateFin() {
		return intrnatMETDateFin;
	}


	public void setIntrnatMETDateFin(String intrnatMETDateFin) {
		this.intrnatMETDateFin = intrnatMETDateFin;
	}


	public List<MiseEnplaceDTOPointDeVente> getIntrnatMEResultat() {
		return intrnatMEResultat;
	}


	public String getIntrnatMETDepartement() {
		return intrnatMETDepartement;
	}


	public void setIntrnatMETDepartement(String intrnatMETDepartement) {
		this.intrnatMETDepartement = intrnatMETDepartement;
	}


	public String getIntrnatMETProg() {
		return intrnatMETProg;
	}


	public void setIntrnatMETProg(String intrnatMETProg) {
		this.intrnatMETProg = intrnatMETProg;
	}


	public String getIntrnatMETTransporteur() {
		return intrnatMETTransporteur;
	}


	public void setIntrnatMETTransporteur(String intrnatMETTransporteur) {
		this.intrnatMETTransporteur = intrnatMETTransporteur;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public static org.apache.commons.logging.Log getLog() {
		return LOG;
	}


	public int getIntrnatMEResultatSize() {
		return intrnatMEResultatSize;
	}


	public void setIntrnatMEResultatSize(int intrnatMEResultatSize) {
		this.intrnatMEResultatSize = intrnatMEResultatSize;
	}


	public void setIntrnatMEResultat(List<MiseEnplaceDTOPointDeVente> intrnatMEResultat) {
		this.intrnatMEResultat = intrnatMEResultat;
	}

}
