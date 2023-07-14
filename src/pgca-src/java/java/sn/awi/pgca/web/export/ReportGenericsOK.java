package sn.awi.pgca.web.export;


import org.apache.poi.hssf.util.HSSFColor;
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

public class ReportGenericsOK {

    private static final String FILE_NAME = "/Users/Amet/Documents/exportExport/OK .xlsx";
    
    private static String  TITLE  =  "Liste des ventes du point ";
    private static int  TITLE_MARGING_LEFT  =  3;   // 1/2 du nombre delement a affciher pour le centrer
    private static int  TITLE_MARGING_TOP  =  2;
    
    
    
    public static Object[][]  prepareR(Object[][] datatypes )
    {
    	 List<String>  hh  =  new ArrayList<String>();
         hh.add("titre 1");
         hh.add("titre 2");
         hh.add("titre 3");
         
         hh.add("titre 4");
         hh.add("titre 5");

        // datatypes[datatypes.length - 1] = hh.toArray();

         hh  =  new ArrayList<String>();
         //
        hh.add("xx");
        hh.add("xx");
        hh.add("xxx");
        
        hh.add("xx");
        hh.add("xxx");
        
        
        //Object[] intArray = new Object[] {hh.toArray()};
        
        datatypes[datatypes.length - 1] = hh.toArray();
        
        return datatypes;
    }
    
    public static void main(String[] args) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
        
        
        Row title = sheet.createRow(TITLE_MARGING_TOP);
        Cell titleVal = title.createCell(TITLE_MARGING_LEFT);
        titleVal.setCellValue(TITLE);
        
       List<CoupleDTO>  lp  =  new ArrayList<CoupleDTO>();
       List<String>  tt  =  new ArrayList<String>();
       
       tt.add("ID");
       tt.add("Nom");
       lp.add(new CoupleDTO(1L, "awa"));
       lp.add(new CoupleDTO(2L, "modou"));
       lp.add(new CoupleDTO(3L, "ameth"));
       lp.add(new CoupleDTO(4L, "issa"));
        
//        create methode insert TItle
          Font font3 = workbook.createFont();
          font3.setFontHeightInPoints((short)32);
          font3.setFontName("Times New Roman");
          font3.setColor(IndexedColors.GOLD.getIndex());
          CellStyle style3 = workbook.createCellStyle();
          style3.setFont(font3);

       
        titleVal.setCellStyle(style3);

        Object[][] datatypes = {
                {"Datatype", "Datatype  xxx  ff", "Type ghg  ddgd", "ffh ffh  ggSize(in bytes)"},
                {1, "int", "Primitive", 2},
                {2, "float", "Primitive", 4},
                {3, "double", "Primitive", 8},
                {4, "char", "Primitive", 1},
                {4, "String", "Non-Primitive", "No fixed size"}
        };
        int rowNum = 5;
        
        List<String>  hh  =  new ArrayList<String>();
        hh.add("T1");
        hh.add("T1");
        hh.add("4444");
        
        hh.add("T1");
        hh.add("T1");
        
        
        //Object[] intArray = new Object[] {hh.toArray()};
        
        datatypes[datatypes.length - 1] = hh.toArray();
        
        datatypes =  prepareR(datatypes);
        datatypes =  prepareR(datatypes);
        datatypes =  prepareR(datatypes);
        datatypes =  prepareR(datatypes);
        datatypes =  prepareR(datatypes);
        
        System.out.println("Creating excel");
        	
        
        //CellStyle style  = title.getRowStyle();
       // style.setFillBackgroundColor();
        //title.setHeightInPoints(200);
        //title.setRowStyle(style);

        
        for (Object[] datatype : datatypes) {
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