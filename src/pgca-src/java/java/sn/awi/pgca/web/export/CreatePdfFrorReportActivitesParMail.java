package sn.awi.pgca.web.export;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import sn.awi.pgca.web.bean.ConstantPGCA;
import sn.awi.pgca.web.dto.AlertesInformationsDTO;
import sn.awi.pgca.web.dto.CreditDTO;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.MiseEnplaceDTOPointDeVente;
import sn.awi.pgca.web.dto.VersementBanqueDTO;

public class CreatePdfFrorReportActivitesParMail {

	BaseColor greyColor = new BaseColor(189, 189, 189);
	BaseColor white = BaseColor.WHITE;
	BaseColor blueCGOS = new BaseColor(159, 213, 239);

	BaseColor greenOfSedab = new BaseColor(67, 138, 86);

	BaseColor greyColorBase = new BaseColor(189, 189, 189);

	Font titre = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	Font titrePDF = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, greenOfSedab);

	Font sousTitre = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);

	Font text = new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);

	Font greyFont = new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD, BaseColor.BLACK);

	public void createFileAttachementForAlert(AlertesInformationsDTO params) throws DocumentException, IOException {

		Paragraph empty = new Paragraph("  ", sousTitre);
		empty.setAlignment(Element.ALIGN_CENTER);
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		SimpleDateFormat DateFormatter = new SimpleDateFormat("dd-MM-yyyy");

		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		document.rightMargin();

		PdfWriter.getInstance(document, new FileOutputStream(ConstantPGCA.LFENERATEDREPOT));
		document.open();

		// PdfContentByte canvas = PdfWriter.getInstance(document, new
		// FileOutputStream(CONSTANTES_MCGOS.logoCGOSPATHLocal)).getDirectContentUnder();

		addHeaderCGOS(document);

		Paragraph preface22 = new Paragraph("RAPPORT  DES ACTIVITES DE LA SEDAB DU  " + DateFormatter.format(params.getDateDebut()) + " au " + DateFormatter.format(params.getDateFin() ), titrePDF);

		preface22.setAlignment(Element.ALIGN_CENTER);
		document.add(preface22);
		document.add(new Paragraph(" "));
		String[] Titles = new String[] { "Période", "Nombre de mises en place ", "Nombre de litiges", "Ordres à signer",
				"Total Versement Banque" , "Subvention à recouvrir" , "chéques à encaisser"  };
		PdfPTable table = new PdfPTable(Titles.length);
		for (int i = 0; i < Titles.length; i++)
			insetCellDataColor(table, Titles[i]);
		// table.addCell(Titles[i]);

		table.setHeaderRows(1);
		table.setSplitRows(false);
		table.setComplete(false);

		table.setWidthPercentage(100);
		// set relative columns width
		// table.setWidths(new float[] { 2.0f, 2.0f, 1.0f, 1.0f, 1.0f , 1.0f});

		insetCellData(table,
				DateFormatter.format(params.getDateDebut()) + " au " + DateFormatter.format(params.getDateFin()));
		insetCellData(table, params.getListeDesMisesEnPlaceAenvoyer() != null
				? params.getListeDesMisesEnPlaceAenvoyer().size() + " en cours" : " Aucune mise en place");
		insetCellData(table, params.getListeDesStockResiduelAenvoyer() != null
				? params.getListeDesStockResiduelAenvoyer().size() + " intrants" : " Aucun intrant");
		insetCellData(table, decimalFormat.format(params.getTotallisteDesCreditsAenvoyer()));
		insetCellData(table, decimalFormat.format(params.getTotallisteDesVersementAenvoyer()));
		insetCellData(table, decimalFormat.format(params.getTotallisteDesVersementAenvoyer()));
		insetCellData(table, decimalFormat.format(params.getTotallisteDesVersementAenvoyer()));

		table.setComplete(true);
		document.add(table);

		// insetCellDataFROMPHP7(document);
		// insetCellDataFROMCGOS(document);

		PdfPTable stable = new PdfPTable(3);
		stable.setWidths(new float[] { 4.0f, 4.0f, 4.0f });
		stable.setWidthPercentage(100);

		// DONNEES FROM Ph7
		PdfPCell celDataPHP7 = new PdfPCell(addListeMisesenPlace(document, params));
		celDataPHP7.setBorderColor(greyColor);
		celDataPHP7.setPaddingLeft(25f);
		celDataPHP7.setPaddingRight(25f);
		stable.addCell(celDataPHP7);

		
		// DONNEES STOCK FOURNISSEURS
		PdfPCell donneesStockFournisseur = new PdfPCell(addStockFournisseurs(document, params));
		donneesStockFournisseur.setBorderColor(greyColor);
		donneesStockFournisseur.setPaddingLeft(25f);
		donneesStockFournisseur.setPaddingRight(25f);
		stable.addCell(donneesStockFournisseur);
		
		// DONNEES STOCK RESIDUEL     
		PdfPCell donnnesStockResiduel = new PdfPCell(addStockResiduel(document, params));
		donnnesStockResiduel.setBorderColor(greyColor);
		donnnesStockResiduel.setPaddingLeft(25f);
		donnnesStockResiduel.setPaddingRight(25f);
		stable.addCell(donnnesStockResiduel);

		document.add(new Paragraph(" "));
		document.add(new Paragraph(" "));

		document.add(empty);
		document.add(stable);
		document.add(empty);

		
		
		
		// AJouter les details des credits
		PdfPCell separtor = new PdfPCell(new Phrase(""));
		separtor.setBorderColorLeft(BaseColor.CYAN);
		stable.addCell(separtor);
		document.add(separtor);
		document.add(new Paragraph(" "));
		Paragraph titleC = new Paragraph(" Liste des crédits en cours", titrePDF);
		titleC.setAlignment(Element.ALIGN_CENTER);
		document.add(titleC);
		document.add(new Paragraph(" "));
		document.add(ajouetrListedesCredits(document , params));
		document.add(new Paragraph(" "));
		document.add(separtor);
		
		
		

		// AJouter les details des versements
		separtor.setBorderColorLeft(BaseColor.CYAN);
		stable.addCell(separtor);
		document.add(separtor);
		document.add(new Paragraph(" "));
		Paragraph titleV = new Paragraph(" Liste des versements effectués", titrePDF);
		titleV.setAlignment(Element.ALIGN_CENTER);
		document.add(titleV);
		document.add(new Paragraph(" "));
		document.add(ajouetrListedesVersements(document , params));
		document.add(new Paragraph(" "));
		document.add(separtor);
		
		document.add(separtor);
		document.add(new Paragraph(" "));
		Paragraph title = new Paragraph(" Détails des mises en place en cours ", titrePDF);

		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);
		document.add(new Paragraph(" "));
		

		
		document.add(addListedesMisesEnPlaceENCours(document , params));
		document.add(separtor);
		
		

		
		// SIGNATURE ET DATE

		PdfPTable sign = new PdfPTable(2);
		sign.setWidths(new float[] { 8.0f, 2.0f });
		sign.setWidthPercentage(100);
		insetCellDataSignature(sign,
				"Document généré par PGCA le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + ".", 0);
		insetCellDataSignature(sign, "Visa SEDAB", 1);

		document.add(sign);

		document.close();
	}

	/************************/

	public PdfPTable addListeMisesenPlace(Document document, AlertesInformationsDTO data) {

		ArrayList<MiseEnplaceDTOPointDeVente> listMEP = (ArrayList<MiseEnplaceDTOPointDeVente>) data
				.getListeDesMisesEnPlaceAenvoyer();

		if (listMEP == null)
			return new PdfPTable(1);

		PdfPTable table = new PdfPTable(/* listMEP.size() */ 4);

		// LIGNE TOTAL DU DERNIER DEPT
		Paragraph header = new Paragraph("LISTE DES MISES EN PLACE EN COURS ", greyFont);
		header.setAlignment(Element.ALIGN_CENTER);
		PdfPCell headerdata = new PdfPCell(header);
		headerdata.setColspan(5);
		headerdata.setPaddingTop(20f);
		headerdata.setPaddingBottom(7f);
		headerdata.setBorderColor(white);
		headerdata.setBorderColorBottom(blueCGOS);
		headerdata.setBackgroundColor(white);
		table.addCell(headerdata);

		
		

		String[] Titles = new String[] { "Intrant", "Quota ", "Mises en place", "Reliquat" };
		for (int i = 0; i < Titles.length; i++) {
			Paragraph rem2 = new Paragraph(Titles[i], text);

			PdfPCell rem = new PdfPCell(rem2);
			rem2.setAlignment(Element.ALIGN_RIGHT);
			// rem.setColspan(3);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			// rem.setBorderColor(greyColor);
			rem.setBackgroundColor(BaseColor.LIGHT_GRAY);
			rem.setBorderColorRight(greyColor);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			rem.setBorderColor(greyColor);
			rem.setBorderColorRight(greyColor);
			table.addCell(rem);
		}
		table.completeRow();

		// ADD DATA AFTER TITLE
		for (MiseEnplaceDTOPointDeVente mep : listMEP) {

			// add key and values
			Paragraph rem2 = new Paragraph(mep.getLibelleIntrantAMettreEnplace() + " ", text);
			PdfPCell rem = new PdfPCell(rem2);
			rem2.setAlignment(Element.ALIGN_RIGHT);
			// rem.setColspan(3);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			rem.setBorderColor(greyColor);
			rem.setBackgroundColor(BaseColor.WHITE);
			rem.setBorderColorRight(greyColor);
			table.addCell(rem);

			Paragraph valeur = null;
			valeur = new Paragraph(
					mep.getQuotaIntrantAMettreEnplace() > 0 ? mep.getQuotaIntrantAMettreEnplace() + " (T) " : "O Tonne",
					text);
			PdfPCell valeurCell = new PdfPCell(valeur);
			// valeurCell.setColspan(2);
			valeurCell.setPaddingTop(5f);
			valeurCell.setPaddingBottom(5f);
			valeurCell.setPaddingLeft(5.0f);
			valeurCell.setBorderColor(greyColor);
			valeurCell.setBackgroundColor(BaseColor.WHITE);
			table.addCell(valeurCell);

			Paragraph totalmiseEnPlace = null;
			totalmiseEnPlace = new Paragraph(
					mep.getQuotaIntrantAMettreEnplace() - mep.getReliquatIntrantAMettreEnplace() + " (T) ", text);
			PdfPCell totalMiseEnPlace = new PdfPCell(totalmiseEnPlace);
			// totalReliquatR.setColspan(2);
			totalMiseEnPlace.setPaddingTop(5f);
			totalMiseEnPlace.setPaddingBottom(5f);
			totalMiseEnPlace.setPaddingLeft(5.0f);
			totalMiseEnPlace.setBorderColor(greyColor);
			totalMiseEnPlace.setBackgroundColor(BaseColor.WHITE);
			table.addCell(totalMiseEnPlace);

			Paragraph totalReliquat = null;
			totalReliquat = new Paragraph(mep.getReliquatIntrantAMettreEnplace() + " (T) ", text);
			PdfPCell totalReliquatR = new PdfPCell(totalReliquat);
			// totalReliquatR.setColspan(2);
			totalReliquatR.setPaddingTop(5f);
			totalReliquatR.setPaddingBottom(5f);
			totalReliquatR.setPaddingLeft(5.0f);
			totalReliquatR.setBorderColor(greyColor);
			totalReliquatR.setBackgroundColor(BaseColor.WHITE);
			table.addCell(totalReliquatR);

		}

		table.setComplete(true);

		// DONNEES INDICES MAJORE

		return table;
	}

	
	
	/** ajouter  les versments .... **/
	
	Double montantTotaldesCrdits = 0.0;   
	public PdfPTable addStockFournisseurs(Document document, AlertesInformationsDTO data) {

		montantTotaldesCrdits = 0.0;
		ArrayList<IntrantDTO> listeStockFournisseur = (ArrayList<IntrantDTO>) 	data.getListeStockFournisseurAenvoyer();

		if (listeStockFournisseur == null)
			return new PdfPTable(1);

		PdfPTable table = new PdfPTable(/* listMEP.size() */ 3);

		// LIGNE TOTAL DU DERNIER DEPT
		Paragraph header = new Paragraph("STOCK DISPONIBLE AUPRES DES FOURNISSEURS ", greyFont);
		header.setAlignment(Element.ALIGN_CENTER);

		PdfPCell headerdata = new PdfPCell(header);

		headerdata.setColspan(3);
		headerdata.setPaddingTop(20f);
		headerdata.setPaddingBottom(7f);
		headerdata.setBorderColor(white);
		headerdata.setBorderColorBottom(blueCGOS);

		headerdata.setBackgroundColor(white);
		table.addCell(headerdata);

		String[] Titles = new String[] { "Intrant", "Quantité ", "Fournisseur"};
		for (int i = 0; i < Titles.length; i++) {
			Paragraph rem2 = new Paragraph(Titles[i], text);

			PdfPCell rem = new PdfPCell(rem2);
			rem2.setAlignment(Element.ALIGN_RIGHT);
			// rem.setColspan(3);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			// rem.setBorderColor(greyColor);
			rem.setBackgroundColor(BaseColor.LIGHT_GRAY);
			rem.setBorderColorRight(greyColor);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			rem.setBorderColor(greyColor);
			rem.setBorderColorRight(greyColor);
			table.addCell(rem);
		}
		table.completeRow();

		// ADD DATA AFTER TITLE
		for (IntrantDTO intrant : listeStockFournisseur) {

			// add key and values
			Paragraph rem2 = new Paragraph(intrant.getLibelleProduit() + " ", text);
			PdfPCell rem = new PdfPCell(rem2);
			rem2.setAlignment(Element.ALIGN_RIGHT);
			// rem.setColspan(3);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			rem.setBorderColor(greyColor);
			rem.setBackgroundColor(BaseColor.WHITE);
			rem.setBorderColorRight(greyColor);
			table.addCell(rem);

			Paragraph valeur = null;
			valeur = new Paragraph(intrant.getQuantite() + "",text);
			PdfPCell valeurCell = new PdfPCell(valeur);
			// valeurCell.setColspan(2);
			valeurCell.setPaddingTop(5f);
			valeurCell.setPaddingBottom(5f);
			valeurCell.setPaddingLeft(5.0f);
			valeurCell.setBorderColor(greyColor);
			valeurCell.setBackgroundColor(BaseColor.WHITE);
			table.addCell(valeurCell);

			Paragraph totalmiseEnPlace = null;
			totalmiseEnPlace = new Paragraph(intrant.getLibelleFournisseur() + "", text);
			PdfPCell totalMiseEnPlace = new PdfPCell(totalmiseEnPlace);
			// totalReliquatR.setColspan(2);
			totalMiseEnPlace.setPaddingTop(5f);
			totalMiseEnPlace.setPaddingBottom(5f);
			totalMiseEnPlace.setPaddingLeft(5.0f);
			totalMiseEnPlace.setBorderColor(greyColor);
			totalMiseEnPlace.setBackgroundColor(BaseColor.WHITE);
			table.addCell(totalMiseEnPlace);
		}
		return table;
	}


	
	
	
	
	public PdfPTable addStockResiduel(Document document, AlertesInformationsDTO data) {

		montantTotaldesCrdits = 0.0;
		ArrayList<IntrantDTO> stockResiduel = (ArrayList<IntrantDTO>) data
				.getListeDesStockResiduelAenvoyer();

		if (stockResiduel == null)
			return new PdfPTable(1);

		PdfPTable table = new PdfPTable(/* listMEP.size() */ 3);

		// LIGNE TOTAL DU DERNIER DEPT
		Paragraph header = new Paragraph("LISTE DES STOCKS RESIDUEL DE LA SEDAB", greyFont);
		header.setAlignment(Element.ALIGN_CENTER);

		PdfPCell headerdata = new PdfPCell(header);

		headerdata.setColspan(3);
		headerdata.setPaddingTop(20f);
		headerdata.setPaddingBottom(7f);
		headerdata.setBorderColor(white);
		headerdata.setBorderColorBottom(blueCGOS);

		headerdata.setBackgroundColor(white);
		table.addCell(headerdata);

		String[] Titles = new String[] { "Intrant", "Quantite ", "Point de vente" };
		for (int i = 0; i < Titles.length; i++) {
			Paragraph rem2 = new Paragraph(Titles[i], text);

			PdfPCell rem = new PdfPCell(rem2);
			rem2.setAlignment(Element.ALIGN_RIGHT);
			// rem.setColspan(3);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			// rem.setBorderColor(greyColor);
			rem.setBackgroundColor(BaseColor.LIGHT_GRAY);
			rem.setBorderColorRight(greyColor);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			rem.setBorderColor(greyColor);

			rem.setBorderColorRight(greyColor);
			table.addCell(rem);
		}
		table.completeRow();

		// ADD DATA AFTER TITLE
		for (IntrantDTO sttockR : stockResiduel) {

			// add key and values
			Paragraph rem2 = new Paragraph(sttockR.getLibelleProduit() + " ", text);
			PdfPCell rem = new PdfPCell(rem2);
			rem2.setAlignment(Element.ALIGN_RIGHT);
			// rem.setColspan(3);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			rem.setBorderColor(greyColor);
			rem.setBackgroundColor(BaseColor.WHITE);
			rem.setBorderColorRight(greyColor);
			table.addCell(rem);

			Paragraph valeur = null;
			valeur = new Paragraph(sttockR.getQuantite() + "",text);
			PdfPCell valeurCell = new PdfPCell(valeur);
			// valeurCell.setColspan(2);
			valeurCell.setPaddingTop(5f);
			valeurCell.setPaddingBottom(5f);
			valeurCell.setPaddingLeft(5.0f);
			valeurCell.setBorderColor(greyColor);
			valeurCell.setBackgroundColor(BaseColor.WHITE);
			table.addCell(valeurCell);

			Paragraph totalmiseEnPlace = null;
			totalmiseEnPlace = new Paragraph(sttockR.getLibelleCommune() + "", text);
			PdfPCell totalMiseEnPlace = new PdfPCell(totalmiseEnPlace);
			// totalReliquatR.setColspan(2);
			totalMiseEnPlace.setPaddingTop(5f);
			totalMiseEnPlace.setPaddingBottom(5f);
			totalMiseEnPlace.setPaddingLeft(5.0f);
			totalMiseEnPlace.setBorderColor(greyColor);
			totalMiseEnPlace.setBackgroundColor(BaseColor.WHITE);
			table.addCell(totalMiseEnPlace);	
		}
		return table;
	}

	
	
	public PdfPTable ajouetrListedesCredits(Document document, AlertesInformationsDTO data) {

		montantTotaldesCrdits = 0.0;
		 ArrayList<CreditDTO> listedesCredits = (ArrayList<CreditDTO>) data
				.getListeDesCreditsAenvoyer();

		if (listedesCredits == null)
			return new PdfPTable(1);

		String[] Titles = new String[] { "Zone ", "Date  contraction" , "Responsable" , "Souscripteur" ,  "Montant initial",  "Montant restant " };
		PdfPTable table = new PdfPTable(Titles.length);

		for (int i = 0; i < Titles.length; i++) {
			Paragraph rem2 = new Paragraph(Titles[i], text);

			PdfPCell rem = new PdfPCell(rem2);
			rem2.setAlignment(Element.ALIGN_RIGHT);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			// rem.setBorderColor(greyColor);
			rem.setBackgroundColor(BaseColor.LIGHT_GRAY);
			rem.setBorderColorRight(greyColor);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			rem.setBorderColor(greyColor);

			rem.setBorderColorRight(greyColor);
			table.addCell(rem);
		}
		
		
		if(listedesCredits.size() == 0)
			{
				Paragraph rem2 = new Paragraph("Aucun crédit enregistré pour cette période. ", text);
	
				PdfPCell rem = new PdfPCell(rem2);
				rem2.setAlignment(Element.ALIGN_CENTER);
				rem.setColspan(7);
				rem.setPaddingTop(5f);
				rem.setPaddingBottom(5f);
				// rem.setBorderColor(greyColor);
				rem.setPaddingTop(5f);
				rem.setPaddingBottom(5f);
				rem.setPaddingLeft(2.0f);
				rem.setBorderColor(greyColor);
	
				table.addCell(rem);
			
			}

//		if(listedesCredits.size() == 0)
//			table.addCell( addEmptyLineToCell("Aucun crédit enregistré pour cette période. " , Titles.length) );	
//		
		
		for (CreditDTO c : listedesCredits) {

			table.addCell(createParagraphe(c.getZoneCredit()));
			table.addCell(createParagraphe(c.getDateContraction()));
			table.addCell(createParagraphe(c.getAuteurCreditLibelle()));
			table.addCell(createParagraphe(c.getNomsouscripteur()));
			table.addCell(createParagraphe(c.getMontantInitialCredit( ) + ""));
			table.addCell(createParagraphe(c.getMontantRestantApayer( ) + ""));
		}
		return table;
	}
	
	
	
	
	public PdfPTable ajouetrListedesVersements(Document document, AlertesInformationsDTO data) {

		montantTotaldesCrdits = 0.0;
		 ArrayList<VersementBanqueDTO> listedesVersements = (ArrayList<VersementBanqueDTO>) data
				.getListeDesVersementAenvoyer();

		if (listedesVersements == null)
			return new PdfPTable(1);

		
		String[] Titles = new String[] {"Auteur" , "Montant",  "Banque ", "Date " , "Type "  };
		PdfPTable table = new PdfPTable(Titles.length);

		for (int i = 0; i < Titles.length; i++) {
			Paragraph rem2 = new Paragraph(Titles[i], text);

			PdfPCell rem = new PdfPCell(rem2);
			rem2.setAlignment(Element.ALIGN_RIGHT);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			// rem.setBorderColor(greyColor);
			rem.setBackgroundColor(BaseColor.LIGHT_GRAY);
			rem.setBorderColorRight(greyColor);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			rem.setBorderColor(greyColor);

			rem.setBorderColorRight(greyColor);
			table.addCell(rem);
		}
		
		
		if(listedesVersements.size() == 0)
			{
				Paragraph rem2 = new Paragraph("Aucun versement enregistré pour cette période. ", text);
	
				PdfPCell rem = new PdfPCell(rem2);
				rem2.setAlignment(Element.ALIGN_CENTER);
				rem.setColspan(7);
				rem.setPaddingTop(5f);
				rem.setPaddingBottom(5f);
				// rem.setBorderColor(greyColor);
				rem.setPaddingTop(5f);
				rem.setPaddingBottom(5f);
				rem.setPaddingLeft(2.0f);
				rem.setBorderColor(greyColor);
	
				table.addCell(rem);
			
			}

		
		for (VersementBanqueDTO v : listedesVersements) {

			table.addCell(createParagraphe(v.getLibellePersonneAuteurVersment( ) + ""));
			table.addCell(createParagraphe(v.getMontantVersment() + ""));

			table.addCell(createParagraphe(v.getBanque()));
			table.addCell(createParagraphe(v.getDateDeVersement()));
			table.addCell(createParagraphe(v.getMoyenVersment()));
		}
		return table;
	}

	
	
	private PdfPCell addEmptyLineToCell(String textEmpty , int sizeTab )
	{
		Paragraph infos = new Paragraph(textEmpty, text);

		PdfPCell rem = new PdfPCell(infos);
		infos.setAlignment(Element.ALIGN_CENTER);
		//rem.setColspan(sizeTab);
		rem.setPaddingTop(5f);
		rem.setPaddingBottom(5f);
		rem.setPaddingLeft(2.0f);
		// rem.setBorderColor(greyColor);
		rem.setPaddingTop(5f);
		rem.setPaddingBottom(5f);
		rem.setPaddingLeft(2.0f);
		rem.setBorderColor(greyColor);

		rem.setBorderColorRight(greyColor);

		return rem;
	}
	
	/*****  AJOUT DETAILS DES MISES MISES EN PLACE EN COURS D EXECUTION ****/
	
	
	public PdfPTable addListedesMisesEnPlaceENCours(Document document, AlertesInformationsDTO data) {

		montantTotaldesCrdits = 0.0;
		ArrayList<MiseEnplaceDTOPointDeVente> listeDESMEP = (ArrayList<MiseEnplaceDTOPointDeVente>) data
				.getListeDesMisesEnPlaceDetaisAenvoyer();

		if (listeDESMEP == null)
			return new PdfPTable(1);
		
		
	
		
		String[] Titles = new String[] { "Référence",  "Intrant ", "Point de vente", "Quota"  , "Mise en place" , "Reliquat" , "Département" , "Commune" };

		PdfPTable table = new PdfPTable(Titles.length);


		for (int i = 0; i < Titles.length; i++) {
			Paragraph rem2 = new Paragraph(Titles[i], text);

			PdfPCell rem = new PdfPCell(rem2);
			rem2.setAlignment(Element.ALIGN_RIGHT);
			// rem.setColspan(3);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			// rem.setBorderColor(greyColor);
			rem.setBackgroundColor(BaseColor.LIGHT_GRAY);
			rem.setBorderColorRight(greyColor);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			rem.setBorderColor(greyColor);

			rem.setBorderColorRight(greyColor);
			table.addCell(rem);
		}
		table.completeRow();

		// ADD DATA AFTER TITLE
		for (MiseEnplaceDTOPointDeVente mep : listeDESMEP) {

	
			table.addCell(createParagraphe ("MEP" + mep.getIdMiseEnPlace()) );	
			table.addCell(createParagraphe ( mep.getLibelleIntrantAMettreEnplace()) );	
			table.addCell(createParagraphe ( mep.getNomPointDeVente()) );	
			table.addCell(createParagraphe (""+ mep.getQuotaIntrantAMettreEnplace()) );	
			
			table.addCell(createParagraphe (mep.getCumulIntrantAMettreEnplace() + "") );
			table.addCell(createParagraphe (mep.getReliquatIntrantAMettreEnplace() + "") );
			table.addCell(createParagraphe (mep.getDepartement() + "") );
			table.addCell(createParagraphe (mep.getCommuneCertifie() + "") );
			
		}
		return table;
	}

	private PdfPCell  createParagraphe( String contenueParagraphe)
	{
		Paragraph p = null;
		p = new Paragraph(contenueParagraphe + "", text);
		PdfPCell pCell = new PdfPCell(p);
		// totalReliquatR.setColspan(2);
		pCell.setPaddingTop(5f);
		pCell.setPaddingBottom(5f);
		pCell.setPaddingLeft(5.0f);
		pCell.setBorderColor(greyColor);
		pCell.setBackgroundColor(BaseColor.WHITE);
		
		return pCell;	
	}
	
	
	/*************/
	
	public void addListeCreditsDetailles(Document document, AlertesInformationsDTO data) {

		montantTotaldesCrdits = 0.0;
		ArrayList<CreditDTO> listeCredits = (ArrayList<CreditDTO>) data
				.getListeDesCreditsAenvoyer();

		if (listeCredits == null)
			return ;

		PdfPTable table = new PdfPTable(/* listMEP.size() */ 4);

		// LIGNE TOTAL DU DERNIER DEPT
		Paragraph header = new Paragraph("LISTE DES CREDITS EN COURS ", greyFont);
		header.setAlignment(Element.ALIGN_CENTER);

		PdfPCell headerdata = new PdfPCell(header);

		headerdata.setColspan(5);
		headerdata.setPaddingTop(20f);
		headerdata.setPaddingBottom(7f);
		headerdata.setBorderColor(white);
		headerdata.setBorderColorBottom(blueCGOS);

		headerdata.setBackgroundColor(white);
		table.addCell(headerdata);

		String[] Titles = new String[] { "Date", "Point de vente ", "Montant initial", "Reliquat" };
		for (int i = 0; i < Titles.length; i++) {
			Paragraph rem2 = new Paragraph(Titles[i], text);

			PdfPCell rem = new PdfPCell(rem2);
			rem2.setAlignment(Element.ALIGN_RIGHT);
			// rem.setColspan(3);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			// rem.setBorderColor(greyColor);
			// rem.setBackgroundColor(BaseColor.WHITE);
			rem.setBorderColorRight(greyColor);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			rem.setBorderColor(greyColor);
			rem.setBackgroundColor(BaseColor.WHITE);
			rem.setBorderColorRight(greyColor);
			table.addCell(rem);
		}
		table.completeRow();

		// ADD DATA AFTER TITLE
		for (CreditDTO credit : listeCredits) {

			// add key and values
			Paragraph rem2 = new Paragraph(credit.getDateContraction() + " ", text);
			PdfPCell rem = new PdfPCell(rem2);
			rem2.setAlignment(Element.ALIGN_RIGHT);
			// rem.setColspan(3);
			rem.setPaddingTop(5f);
			rem.setPaddingBottom(5f);
			rem.setPaddingLeft(2.0f);
			rem.setBorderColor(greyColor);
			rem.setBackgroundColor(BaseColor.WHITE);
			rem.setBorderColorRight(greyColor);
			table.addCell(rem);

			Paragraph valeur = null;
			valeur = new Paragraph(credit.getZoneCredit(),text);
			PdfPCell valeurCell = new PdfPCell(valeur);
			// valeurCell.setColspan(2);
			valeurCell.setPaddingTop(5f);
			valeurCell.setPaddingBottom(5f);
			valeurCell.setPaddingLeft(5.0f);
			valeurCell.setBorderColor(greyColor);
			valeurCell.setBackgroundColor(BaseColor.WHITE);
			table.addCell(valeurCell);

			Paragraph totalmiseEnPlace = null;
			totalmiseEnPlace = new Paragraph(credit.getMontantInitialCredit() + " FCFA", text);
			PdfPCell totalMiseEnPlace = new PdfPCell(totalmiseEnPlace);
			// totalReliquatR.setColspan(2);
			totalMiseEnPlace.setPaddingTop(5f);
			totalMiseEnPlace.setPaddingBottom(5f);
			totalMiseEnPlace.setPaddingLeft(5.0f);
			totalMiseEnPlace.setBorderColor(greyColor);
			totalMiseEnPlace.setBackgroundColor(BaseColor.WHITE);
			table.addCell(totalMiseEnPlace);

			Paragraph totalReliquat = null;
			totalReliquat = new Paragraph(credit.getMontantRestantApayer() + " F CFA ", text);
			PdfPCell totalReliquatR = new PdfPCell(totalReliquat);
			// totalReliquatR.setColspan(2);
			totalReliquatR.setPaddingTop(5f);
			totalReliquatR.setPaddingBottom(5f);
			totalReliquatR.setPaddingLeft(5.0f);
			totalReliquatR.setBorderColor(greyColor);
			totalReliquatR.setBackgroundColor(BaseColor.WHITE);
			table.addCell(totalReliquatR);
			
			montantTotaldesCrdits += credit.getMontantRestantApayer();

		}

		// LIGNE TOTAL DU DERNIER DEPT
		Paragraph LigneTOTAL = new Paragraph("Le montant total des credits en cours s'élève à :   " + montantTotaldesCrdits + " F CFA");
		PdfPCell cellDepartementCenterLT = new PdfPCell(LigneTOTAL);
		LigneTOTAL.setAlignment(Element.ALIGN_RIGHT);

		cellDepartementCenterLT.setColspan(5);
		cellDepartementCenterLT.setPaddingTop(7f);
		cellDepartementCenterLT.setPaddingBottom(7f);
		cellDepartementCenterLT.setPaddingLeft(115.f);
		cellDepartementCenterLT.setBorderColor(greyColor);
		cellDepartementCenterLT.setBackgroundColor(BaseColor.WHITE);
		table.addCell(cellDepartementCenterLT);

		table.completeRow();

		table.setComplete(true);

		// DONNEES INDICES MAJORE

		try {
			document.add(table) ;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	/******/
	private PdfPTable createStockResiduel(Document document, IntrantDTO ecartTraitement) {

		String[] Titles = new String[] { "Entre 1 à 489", "Supérieur à 489", "Sans indice majoré",
				"indice majoré reconstitué" };
		PdfPTable table = new PdfPTable(Titles.length);

		// LIGNE TOTAL DU DERNIER DEPT
		Paragraph header = new Paragraph("INDICE MAJORE", greyFont);
		header.setAlignment(Element.ALIGN_CENTER);

		PdfPCell headerdata = new PdfPCell(header);

		headerdata.setColspan(4);
		headerdata.setPaddingTop(20f);
		headerdata.setPaddingBottom(7f);
		headerdata.setBorderColor(white);
		headerdata.setBorderColorBottom(blueCGOS);

		headerdata.setBackgroundColor(white);
		table.addCell(headerdata);

		table.completeRow();

		for (String title : Titles) {

			// add key and values
			Paragraph rem2 = new Paragraph(title, text);
			PdfPCell rem = new PdfPCell(rem2);
			rem2.setAlignment(Element.ALIGN_CENTER);
			rem.setColspan(3);
			rem.setBorderColor(greyColor);
			rem.setBackgroundColor(BaseColor.WHITE);
			table.addCell(rem);

			Paragraph valeur = null;
			// if (title.equals("Entre 1 à 489"))
			// valeur = new
			// Paragraph(ecartTraitement.getNombreIndiceMajoreEntre1Et489() + "
			// agent (s) ", text);
			// else if (title.equals("Supérieur à 489"))
			// valeur = new Paragraph(
			// ecartTraitement.getNombreIndiceMajoresupA489() + " agent (s)",
			// text);
			// else if (title.equals("Sans indice majoré"))
			// valeur = new Paragraph(
			// ecartTraitement.getNombreIndiceMajoreSansIM() + " agent (s)",
			// text);
			// else if (title.equals("indice majoré reconstitué"))
			// valeur = new Paragraph(
			// ecartTraitement.getNombreIndiceMajoreIMReconstitue()+ " agent
			// (s)", text);

			PdfPCell valeurCell = new PdfPCell(valeur);
			// valeurCell.setColspan(2);
			valeurCell.setPaddingTop(5f);
			valeurCell.setPaddingBottom(5f);
			valeurCell.setPaddingLeft(5.0f);
			valeurCell.setBorderColor(greyColor);
			valeurCell.setBackgroundColor(BaseColor.WHITE);

			table.addCell(valeurCell);
		}

		// LIGNE TOTAL DU DERNIER DEPT
		Paragraph LigneTOTAL = new Paragraph("INFOS :   " + " ");
		PdfPCell cellDepartementCenterLT = new PdfPCell(LigneTOTAL);
		LigneTOTAL.setAlignment(Element.ALIGN_RIGHT);

		cellDepartementCenterLT.setColspan(5);
		cellDepartementCenterLT.setPaddingTop(7f);
		cellDepartementCenterLT.setPaddingBottom(7f);
		cellDepartementCenterLT.setPaddingLeft(115.f);
		cellDepartementCenterLT.setBorderColor(greyColor);
		cellDepartementCenterLT.setBackgroundColor(BaseColor.WHITE);
		table.addCell(cellDepartementCenterLT);

		table.completeRow();

		table.setComplete(true);

		return table;
	}

	
	/*********/
	
	

	public void insetCellData(PdfPTable table, String value) {

		PdfPCell cellDepartementCenter = new PdfPCell(new Phrase(value, text));

		cellDepartementCenter.setPaddingTop(5f);
		cellDepartementCenter.setPaddingBottom(7f);
		// cellDepartementCenter.setBorderColor(greyColor);
		table.addCell(cellDepartementCenter);
	}

	public void insertEmpty(PdfPTable table) {
		PdfPCell cellDepartementCenter = new PdfPCell(new Phrase("", text));
		cellDepartementCenter.setBorderColor(white);
		table.addCell(cellDepartementCenter);
	}

	public PdfPTable loadHeader(Document document) {

		PdfPTable table = new PdfPTable(3);

		// LIGNE TOTAL DU DERNIER DEPT
		Paragraph LigneTOTAL = new Paragraph("INFOS :   " + " ");
		PdfPCell cellDepartementCenterLT = new PdfPCell(LigneTOTAL);
		LigneTOTAL.setAlignment(Element.ALIGN_RIGHT);

		// cellDepartementCenterLT.setColspan(5);
		cellDepartementCenterLT.setBorderColor(white);
		cellDepartementCenterLT.setBackgroundColor(BaseColor.WHITE);
		table.addCell(cellDepartementCenterLT);
		table.addCell(cellDepartementCenterLT);
		table.addCell(cellDepartementCenterLT);

		table.completeRow();

		table.setComplete(true);

		return table;
	}

	public void insertSautLigne(PdfPTable table) {
		// LIGEN HEADER DU DEPARTEMENT
		Paragraph sautLingne = new Paragraph("");
		sautLingne.setAlignment(Element.ALIGN_CENTER);
		PdfPCell cellsautLingne = new PdfPCell(sautLingne);
		cellsautLingne.setColspan(7);
		cellsautLingne.setBorderColor(BaseColor.WHITE);
		table.addCell(cellsautLingne);
	}

	public void insetCellDataColor(PdfPTable table, String value) {
		PdfPCell cellDepartementCenter = new PdfPCell(new Phrase(value, text));
		cellDepartementCenter.setBackgroundColor(BaseColor.GREEN);
		cellDepartementCenter.setPaddingTop(10f);
		cellDepartementCenter.setPaddingBottom(10f);

		table.addCell(cellDepartementCenter);
	}

	public void insetCellDataSignature(PdfPTable table, String value, int border) {
		PdfPCell cellDepartementCenter = new PdfPCell(new Phrase(value, text));
		// cellDepartementCenter.setBorderColor(BaseColor.WHITE);
		// cellDepartementCenter.setBackgroundColor(blueCGOS);
		cellDepartementCenter.setPaddingTop(10f);

		cellDepartementCenter.setBorder(1);
		cellDepartementCenter.setBorderColorTop(BaseColor.BLACK);
		cellDepartementCenter.setBorderColorBottom(BaseColor.WHITE);
		cellDepartementCenter.setBorderColorRight(greyColor);
		cellDepartementCenter.setBorderColorLeft(BaseColor.WHITE);

		table.addCell(cellDepartementCenter);
	}

	private void addHeaderCGOS(Document doc) throws DocumentException, MalformedURLException, IOException {

		PdfPTable stable = new PdfPTable(1);
		//stable.setWidths(new float[] { 2.0f, 1.0f, 2.0f });
		stable.setWidthPercentage(100);

//		PdfPCell left1 = new PdfPCell(new Phrase());
//		left1.setBorderColor(white);
//		stable.addCell(left1);

		PdfPCell left = new PdfPCell(Image.getInstance(ConstantPGCA.DIRECTORY_PATH_Header_PDF));
		left.setBorderColor(white);
		stable.addCell(left);

//		PdfPCell separtor = new PdfPCell(new Phrase());
//		separtor.setBorderColor(white);

//		stable.addCell(separtor);

		doc.add(stable);

	}

}