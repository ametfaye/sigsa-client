package sn.awi.pgca.web.export;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import sn.awi.pgca.web.dto.CoupleDTO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportSimpleExcelMultipleOnglet {

    private static final String FILE_NAME = "/Users/Amet/Documents/exportExport/GenreicsReportl33 .xlsx";
    
    private static String  TITLE  =  "Liste des ventes du point ";
    private static int  TITLE_MARGING_LEFT  =  5;   // 1/2 du nombre delement a affciher pour le centrer
    private static int  TITLE_MARGING_TOP  =  1;

    public static void main(String[] args) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
        
        
        Row title = sheet.createRow(TITLE_MARGING_TOP);
        Cell titleVal = title.createCell(TITLE_MARGING_LEFT);
        titleVal.setCellValue(TITLE);
        
       List<CoupleDTO>  lp  =  new ArrayList<CoupleDTO>();
       
       List<String>  titles  =  new ArrayList<String>();
       titles.add("Point de vente");
       titles.add("Bénéficiaire");
       titles.add("Taux Subvention");
       titles.add("Montant Subvention");
       titles.add("Date opération");
       
       
       lp.add(new CoupleDTO(1L, "awa"));
       lp.add(new CoupleDTO(2L, "modou"));
       lp.add(new CoupleDTO(3L, "hello"));
       lp.add(new CoupleDTO(4L, "issa"));
       lp.add(new CoupleDTO(1L, "awa"));
       lp.add(new CoupleDTO(2L, "ta^ph"));
       lp.add(new CoupleDTO(3L, "loaic"));
       lp.add(new CoupleDTO(4L, "garb"));

        
//        create methode insert TItle
          Font font3 = workbook.createFont();
          font3.setFontHeightInPoints((short)16);
          font3.setFontName("Times New Roman");
          font3.setColor(IndexedColors.GOLD.getIndex());
          CellStyle style3 = workbook.createCellStyle();
          style3.setFont(font3);

       
        titleVal.setCellStyle(style3);

        Object[][] night  = new Object[9][];

        int size = 0;
        night[size] = titles.toArray();
        size++;
        
        for (CoupleDTO c  : lp)
        {
        	  Object n[] = { 3, c.getValeur(), c.getClefValeur(), "ddd" , "ccc" , 2 , "afica", 3336, "dddd"};        	  
        	  night[size++]   = n;
        }
        
        int rowNum = 5;
        System.out.println("Creating excel");
      
        for (Object[] datatype : night) {
            Row row = sheet.createRow(rowNum++);

            int colNum = 5;
            for (Object field : datatype) {
            	
            	
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }

       
        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }
}