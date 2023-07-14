package sn.awi.pgca.web.export;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenricsReporting {

    private static final String FILE_NAME = "/Users/Amet/GED-PGCA/Rapport/MyFirstExcel.xlsx";

    public static void main(String[] args) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
        XSSFSheet sheet1 = workbook.createSheet("Vente");
        XSSFSheet sheet2 = workbook.createSheet("Credit");
        XSSFSheet sheet3 = workbook.createSheet("Versement");

        Object[][] datatypes = {
                {"Datatype", "Type", "Size(in bytes)"},
                {"int", "Primitive", 2},
                {"float", "Primitive", 4},
                {"double", "Primitive", 8},
                {"char", "Primitive", 1},
                {"String", "Non-Primitive", "No fixed size"}
        };

        int rowNum = 5;
        System.out.println("Creating excel");
        
        Row title = sheet.createRow(rowNum);
        
        CellStyle style  = title.getRowStyle();
        
       // style.setFillBackgroundColor();
        title.setHeightInPoints(200);
        title.setRowStyle(style);

        
        for (Object[] datatype : datatypes) {
            Row row = sheet.createRow(rowNum++);

            int colNum = 5;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field * colNum);
                }
            }
        }

        rowNum = 5;
        for (Object[] datatype : datatypes) {
            Row row2 = sheet1.createRow(rowNum++);
            int colNum = 5;
            for (Object field : datatype) {
                Cell cell = row2.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field * colNum);
                }
            }
        }
        
         rowNum = 5;
        for (Object[] datatype : datatypes) {
           Row row3 = sheet2.createRow(rowNum++);
            int colNum = 5;
            for (Object field : datatype) {
                Cell cell = row3.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field  * colNum);
                }
            }
        }
        
        rowNum = 5; 
        for (Object[] datatype : datatypes) {
           Row row4 = sheet3.createRow(rowNum++);
            int colNum = 5;
            for (Object field : datatype) {
                Cell cell = row4.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field * colNum);
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