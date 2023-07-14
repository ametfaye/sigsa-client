//package sn.awi.pgca.web.export;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.faces.context.FacesContext;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import sn.awi.pgca.web.dto.CoupleDTO;
//import sn.awi.pgca.web.dto.SubventionDTO;
//
//public class ManagerExportDataServices {
//
//	private static final String FILE_NAME = "/Users/Amet/Documents/exportExport/GenreicsReportl222 .xlsx";
//	private static String TITLE = "Liste des ventes du point ";
//	private static int TITLE_MARGING_LEFT = 1; // 1/2 du nombre delement a //
//												// affciher pour le centrer
//	private static int TITLE_MARGING_TOP = 1;
//
//	//public void generateListObject(List<String> listSubvention, List<String> titles ,  List<SubventionDTO>  listSubvention) throws IOException {
//	
//
//		String FILE_NAME = "/Users/Amet/Documents/exportExport/sokhna.xlsx";
//		XSSFWorkbook workbook = new XSSFWorkbook();
//		XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
//
//		sheet.setColumnWidth(0, 300);
//		Row title = sheet.createRow(1);
//		Cell titleVal = title.createCell(5);
//		titleVal.setCellValue("   Liste des subventions à encaisser  ");
//	
//
//		// create methode insert TItle
//		Font font3 = workbook.createFont();
//		font3.setFontHeightInPoints((short) 16);
//		font3.setFontName("Times New Roman");
//		font3.setColor(IndexedColors.LIGHT_GREEN.getIndex());
//		CellStyle style3 = workbook.createCellStyle();
//		style3.setFont(font3);
//
//		// fontHeader.setUnderline(mef.toBinaryString(1));
//
//		titleVal.setCellStyle(style3);
//
//		Object[][] night = new Object[listSubvention.size() + 1][];
//
//		int size = 0;
//		night[size] = titles.toArray();
//		size++;
//
//		for (SubventionDTO c : listSubvention) {
//			Object n[] = { c.getZone(), c.getNomClientBenefiaire(), c.getMontantSubvention(), c.getDateSubvention() };
//			night[size++] = n;
//		}
//
//		int rowNum = 5;
//		System.out.println("Creating excel");
//
//		for (Object[] datatype : night) {
//			Row row = sheet.createRow(rowNum++);
//
//			int colNum = 5;
//			for (Object field : datatype) {
//
//				Cell cell = row.createCell(colNum++);
//				sheet.autoSizeColumn(colNum);
//
//				// CellUtil.setCellStyleProperties(cell, properties);
//
//				if (field instanceof String) {
//					cell.setCellValue((String) field);
//					sheet.autoSizeColumn(colNum);
//
//				} else if (field instanceof Integer) {
//					cell.setCellValue((Integer) field);
//					sheet.autoSizeColumn(colNum);
//
//				} else if (field instanceof Date) {
//					cell.setCellValue(field != null ? field.toString() : "");
//					sheet.autoSizeColumn(colNum);
//
//				} else if (field instanceof Float) {
//					cell.setCellValue(field != null ? (String) field.toString() : "");
//					sheet.autoSizeColumn(colNum);
//
//				}
//
//			}
//
//			sheet.autoSizeColumn(0);
//			// sheet.autoSizeColumn(200,true);
//		}
//
//		try {
//			FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
//			workbook.write(outputStream);
//			workbook.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		dowloadFile(FILE_NAME);
//
//	}
//
//	void dowloadFile(String files ) throws IOException {
//
//		File file = new File(files);
//
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//
//		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
//
//		response.reset();
//		response.setHeader("Content-Type", "application/vnd.ms-excel");
//		response.setHeader("Content-Disposition", "attachment; filename=\"my.xlsx\"");
//
//		OutputStream responseOutputStream = response.getOutputStream();
//
//		InputStream fileInputStream = new FileInputStream(file);
//
//		byte[] bytesBuffer = new byte[2048];
//		int bytesRead;
//		while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) {
//			responseOutputStream.write(bytesBuffer, 0, bytesRead);
//		}
//
//		responseOutputStream.flush();
//
//		fileInputStream.close();
//		responseOutputStream.close();
//
//		facesContext.responseComplete();
//
//	}
//
//	public void downloadListSubvention(List<String> listData, String titleD, int left, int top) {
//		XSSFWorkbook workbook = new XSSFWorkbook();
//		XSSFSheet sheet = workbook.createSheet("Liste des Subventions Agricoles");
//
//		Row title = sheet.createRow(top);
//		Cell titleVal = title.createCell(left);
//		titleVal.setCellValue(titleD);
//
//		List<CoupleDTO> lp = new ArrayList<CoupleDTO>();
//		List<String> tt = new ArrayList<String>();
//
//		tt.add("ID");
//		tt.add("Nom");
//		lp.add(new CoupleDTO(1L, "awa"));
//		lp.add(new CoupleDTO(2L, "modou"));
//		lp.add(new CoupleDTO(3L, "ameth"));
//		lp.add(new CoupleDTO(4L, "issa"));
//
//		// create methode insert TItle
//		Font font3 = workbook.createFont();
//		font3.setFontHeightInPoints((short) 16);
//		font3.setFontName("Times New Roman");
//		font3.setColor(IndexedColors.GOLD.getIndex());
//		CellStyle style3 = workbook.createCellStyle();
//		style3.setFont(font3);
//
//		titleVal.setCellStyle(style3);
//
//		Object[][] datatypes = { { "Datatype", "Type", "Size(in bytes)" }, { "int", "Primitive", 2 },
//				{ "float", "Primitive", 4 }, { "double", "Primitive", 8 }, { "char", "Primitive", 1 },
//				{ "String", "Non-Primitive", "No fixed size" } };
//
//		int rowNum = 5;
//
//		for (Object[] datatype : datatypes) {
//			Row row = sheet.createRow(rowNum++);
//
//			int colNum = 5;
//			for (Object field : datatype) {
//				Cell cell = row.createCell(colNum++);
//				if (field instanceof String) {
//					cell.setCellValue((String) field);
//				} else if (field instanceof Integer) {
//					cell.setCellValue((Integer) field * colNum);
//				}
//			}
//		}
//
//		try {
//			FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
//			workbook.write(outputStream);
//			workbook.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//}