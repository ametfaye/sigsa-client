//package sn.awi.pgca.web.export;
//
///* ====================================================================
//   Licensed to the Apache Software Foundation (ASF) under one or more
//   contributor license agreements.  See the NOTICE file distributed with
//   this work for additional information regarding copyright ownership.
//   The ASF licenses this file to You under the Apache License, Version 2.0
//   (the "License"); you may not use this file except in compliance with
//   the License.  You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
//==================================================================== */
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//import org.junit.Test;
//
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//
//public class itext {
//    private static String FILE = "/Users/Amet/Documents/exportExport/MyFirstPDF.pdf";
////    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
////            Font.BOLD);
////    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
////            Font.NORMAL, BaseColor.RED);
////    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
////            Font.BOLD);
////    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
////            Font.BOLD);
////
////    
//
//    
//    public void createPdf(String filename)
//    		throws DocumentException, IOException {
//
//    	
//    	 Document document = new Document();
//    	 document.setPageSize(PageSize.A4.rotate());
//         PdfWriter.getInstance(document, new FileOutputStream(FILE));
//         
//        
//         document.open();
//         
//         
//         PdfPTable t = new PdfPTable(3);
//        // t.setWidthPercentage(50);
//         t.addCell(getCell("Text to the left", PdfPCell.ALIGN_LEFT));
//         t.addCell(getCell("", PdfPCell.ALIGN_CENTER));
//         t.addCell(getCell("Text to the right", PdfPCell.ALIGN_RIGHT));
//         document.add(t);
//         
//         
//         t.addCell(getCell("Text to the left", PdfPCell.ALIGN_LEFT));
//         t.addCell(getCell("", PdfPCell.ALIGN_CENTER));
//         t.addCell(getCell("Text to the right", PdfPCell.ALIGN_RIGHT));
//         document.add(t);
//         
//         
//      
//
//
//         
////         Image img = Image.getInstance("c:/temp/logo.png");
//// 
////         document.add(img);
//   
//         
//
//       Paragraph preface2 = new Paragraph("Journal de Mise en place de la commune de Dakar"); 
//       
//       
//       preface2.setAlignment(Element.ALIGN_CENTER);
//       document.add(preface2);
//         
//      document.add(new Paragraph("-"));
//      
//      document.add(new Paragraph("-")); 
//       
//         
//         PdfPTable table = new PdfPTable(8);
//         
//         table.addCell("Nom");
//
//         table.addCell("PeNom");
//
//         table.addCell("AgNom");
//
//         table.addCell("AdresNom");
//
//         table.addCell("CommNom");
//         table.addCell("AgNom");
//
//         table.addCell("AdresNom");
//
//         table.addCell("CommNom");
//         table.setHeaderRows(1);
//         table.setSplitRows(false);
//         table.setComplete(false);
//  
//         for (int i = 0; i < 155; i++) {table.addCell("Header " + i);}
//         
//         
//  
//         for (int i = 0; i < 10; i++) {
//             if (i%8 == 0) {
//                 document.add(table);
//     
//             }
//          
//         }
//  
//         table.setComplete(true);
//         document.add(table);
//         
//         
//         	Paragraph prefaceS = new Paragraph("FJournal de Mise en place de la commune de Dakar"); 
//         	prefaceS.setAlignment(Element.ALIGN_RIGHT);
//
//      
//         	
//         
//         
////         img = Image.getInstance("c:/temp/fo.png");
//// 
////         document.add(img);
//         
//         
//         document.close();
//     
//         	
//    
//    
//    	    }
//    
//    
//    
//    public PdfPCell getCell(String text, int alignment) {
//        PdfPCell cell = new PdfPCell(new Phrase(text));
//       // cell.setPadding(0);
//        cell.setHorizontalAlignment(alignment);
//        cell.setBorder(PdfPCell.NO_BORDER);
//        return cell;
//    }
//    
//    
//   // public static void main(String[] args) {
//    @Test
//    public  void mef() {
//    	
//    	try {
//			createPdf(FILE);
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//    }
//
//
//
//
//}
//
