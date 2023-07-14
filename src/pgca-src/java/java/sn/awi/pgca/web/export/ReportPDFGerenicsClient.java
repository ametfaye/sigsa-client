//package sn.awi.pgca.web.export;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//
//import com.itextpdf.text.DocumentException;
//
//import sn.awi.pgca.web.dto.MiseEnplaceDTOPointDeVente;
//
//public class ReportPDFGerenicsClient {
//
//
//	@Test
//	public void mef() {
//
//		try {
//			
//			String[] myStringArray = new String[]{"Nom","Prénom","Age", "Adresse", "Telephone", "Code Postal", "Ville", "Pays"};
//			ArrayList<MiseEnplaceDTOPointDeVente> listMeP = new ArrayList<MiseEnplaceDTOPointDeVente>();
//			
//			MiseEnplaceDTOPointDeVente g = new MiseEnplaceDTOPointDeVente();
//			
//			g.setBlMiseEnPlace("UREE");
//			g.setCamion("DK 5465");
//			g.setChauffeur("Abdou Diop");
//			g.setDateMiseEnplace("10 10 2017");
//			g.setDepartementPointdeVente("Dakar");
//			g.setDistributeur("Senegal Tour");
//			g.setFournisseur("ICS");
//			g.setNomGerant("Cheikh Ndiaye");
//			
//			listMeP.add(g);
//			
//			MiseEnplaceDTOPointDeVente g2 = new MiseEnplaceDTOPointDeVente();
//			
//			g2.setBlMiseEnPlace("TS2 G");
//			g2.setCamion("FK 5465");
//			g2.setChauffeur("ISSKETA");
//			g2.setDateMiseEnplace("9 92017");
//			g2.setDepartementPointdeVente("KAlaock");
//			g2.setDistributeur("RATP");
//			g2.setFournisseur("INDIUSSUSY");
//			g2.setNomGerant("Cheikh JEna paul diaz nd");	
//			listMeP.add(g2);
//			ReportPDFGerenics report = new ReportPDFGerenics("/Users/Amet/Documents/exportExport/MyFirstPDF.pdf" , "Liste des mise en place de Tambaff");
//			
//			report.generateMiseEnPlace(myStringArray, listMeP);
//			
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//}
