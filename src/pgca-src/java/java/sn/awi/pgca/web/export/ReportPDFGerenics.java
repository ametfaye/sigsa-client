package sn.awi.pgca.web.export;

/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import sn.awi.pgca.utils.UtilString;
import sn.awi.pgca.web.bean.ConstantPGCA;
import sn.awi.pgca.web.dto.CommandeDTO;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.MiseEnPlaceEffectuerDTO;
import sn.awi.pgca.web.dto.MiseEnplaceDTOPointDeVente;

public class ReportPDFGerenics {
	
	
	UtilString utlisTring  =  new UtilString();
	
	BaseColor greyColor = new BaseColor(189, 189, 189);
	BaseColor greenOfSedab = new BaseColor(67, 138, 86);

	
	BaseColor greyColorBase = new BaseColor(189, 189, 189);
	BaseColor greyColor2 = new BaseColor(75, 160, 92);
	BaseColor colorTitile = new BaseColor(75, 160, 92);

	Font titre = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	Font titrePDF = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, greenOfSedab);

	Font sousTitre = new Font(FontFamily.HELVETICA, 8, Font.BOLDITALIC, BaseColor.BLACK);

	Font text = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);

	Font greyFont = new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD, greenOfSedab);

	private static String dateDay = "Zone From session";
	private static String author = "From session";
	private static String zone = "Zone From session";
	private static String campgne = "Zone From session";
	private static String title = "Title From constructor";

	public ReportPDFGerenics(String FileName, String titreDocument) {
		// FILE = FileName;
		title = titreDocument;
	}

	public void generateMiseEnPlace(List<MiseEnplaceDTOPointDeVente> quota,
			List<MiseEnPlaceEffectuerDTO> mepEffectuesurQuota, MiseEnplaceDTOPointDeVente details)
			throws DocumentException, IOException {

		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		PdfWriter.getInstance(document, new FileOutputStream(ConstantPGCA.DIRECTORY_PATH_TMP_PDF));
		document.open();

		insertHeader(document, details);
		// insertContent(document, quota, mepEffectuesurQuota , details);

		insertContentSummary(document, quota, details);
		Font boldFont = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);
		document.add(new Paragraph(" "));

		if (mepEffectuesurQuota == null) {
			Paragraph preface2 = new Paragraph("Aucune  mise en place  effectuée sur le quota");
			preface2.setAlignment(Element.ALIGN_CENTER);
			preface2.setFont(boldFont);
			document.add(preface2);
			document.add(new Paragraph(" "));
		} else {
			Paragraph preface2 = new Paragraph("Liste des mises en place  effectuées sur le quota");
			preface2.setAlignment(Element.ALIGN_CENTER);
			preface2.setFont(boldFont);
			document.add(preface2);
			document.add(new Paragraph(" "));
			insertContentListMEPdejaEffectue(document, mepEffectuesurQuota);

		}

		insertSignatureWithCurrentDate(document);
		document.close();
	}

	/****** report resultat recherches des ****/

	public void generateResultMiseEnPlace(List<MiseEnplaceDTOPointDeVente> listIntrant)
			throws DocumentException, IOException {

		Double totalDepartement = 0.0;
		Long lastIDdept = 0L; // TODO bug Nian 27/03/2018 QUICK CORRECT A REVOIR


		String intrantLibelle = listIntrant != null && listIntrant.size() > 0
				? listIntrant.get(0).getLibelleIntrantAMettreEnplace() : "";
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		document.rightMargin();
		document.left(10);
		PdfWriter.getInstance(document, new FileOutputStream(ConstantPGCA.DIRECTORY_PATH_TMP_PDF));
		document.open();

		insertHeaderTypeMEP(document);

		Paragraph preface22 = new Paragraph("LISTE DES MISES EN PLACE DE  " + intrantLibelle, titrePDF);

		preface22.setAlignment(Element.ALIGN_CENTER);
		document.add(preface22);
		document.add(new Paragraph(" "));

		String[] Titles = new String[] { "Date", "Point de vente", "Quantite", "N° BL", "N° LV", "Transporteur",
				"Chauffeur", "Camion" };

		PdfPTable table = new PdfPTable(Titles.length);

		for (int i = 0; i < Titles.length; i++)
			insetCellData(table, Titles[i]);
		table.setHeaderRows(1);
		table.setSplitRows(false);
		table.setComplete(false);
		table.setWidthPercentage(100);


		int j = 0;
		Long lastidDept = 0l;

		for (MiseEnplaceDTOPointDeVente m : listIntrant) {

			totalDepartement += m.getQuantiteIntrantAMettreEnplace();

			if (j == 0) {
				Paragraph DEPT = new Paragraph(
						"DEPARTEMENT DE " + m.getDepartement() + " (" + m.getRegion().toUpperCase() + ") ", sousTitre);
				DEPT.setAlignment(Element.ALIGN_CENTER);

				PdfPCell cellDepartementCenter = new PdfPCell(DEPT);
				cellDepartementCenter.setColspan(9);
				cellDepartementCenter.setPaddingTop(7f);
				cellDepartementCenter.setPaddingBottom(7f);
				cellDepartementCenter.setPaddingLeft(180.f);
				cellDepartementCenter.setBorderColor(greyColor);
				cellDepartementCenter.setBackgroundColor(greyColor);
				table.addCell(cellDepartementCenter);
			}

			if (lastidDept != 0 && lastidDept != m.getIdDepartement()  ) {

				// LIGNE TOTAL DU DERNIER DEPT
				Paragraph LigneTOTAL = new Paragraph("TOTAL  " + totalDepartement + " Tonne(s)");
				PdfPCell cellDepartementCenterLT = new PdfPCell(LigneTOTAL);
				LigneTOTAL.setAlignment(Element.ALIGN_RIGHT);

				cellDepartementCenterLT.setColspan(9);
				cellDepartementCenterLT.setPaddingTop(7f);
				cellDepartementCenterLT.setPaddingBottom(7f);
				cellDepartementCenterLT.setPaddingLeft(115.f);
				cellDepartementCenterLT.setBorderColor(greyColor);
				cellDepartementCenterLT.setBackgroundColor(BaseColor.WHITE);
				table.addCell(cellDepartementCenterLT);
				
		
			

				// LIGEN HEADER DU DEPARTEMENT
				Paragraph DEPT = new Paragraph(
						"DEPARTEMENT DE " + m.getDepartement() + " (" + m.getRegion().toUpperCase() + ") ", sousTitre);
				DEPT.setAlignment(Element.ALIGN_RIGHT);
				PdfPCell cellDepartementCenter = new PdfPCell(DEPT);
				cellDepartementCenter.setColspan(9);
				cellDepartementCenter.setPaddingTop(7f);
				cellDepartementCenter.setPaddingBottom(7f);
				cellDepartementCenter.setPaddingLeft(180.f);

				cellDepartementCenter.setBorderColor(BaseColor.WHITE);
				cellDepartementCenter.setBackgroundColor(greyColor);
				table.addCell(cellDepartementCenter);
				totalDepartement = 0.0;
			
			}
			lastIDdept  =  m.getIdDepartement() ;			
			insetCellData(table, m.getDateMEPSTR());
			insetCellData(table, m.getNomPointDeVente());
			insetCellData(table, m.getQuantiteIntrantAMettreEnplace() + "");
			insetCellData(table, m.getBlMiseEnPlace());
			insetCellData(table, m.getLvMiseEnPlace());
			insetCellData(table, m.getTransporteur());
			insetCellData(table, m.getChauffeur());
			insetCellData(table, m.getCamion());
			lastidDept = m.getIdDepartement();
			
			
			
			if(j +1 == listIntrant.size())
			{	
				Double tmp  =  0.0;
				for (MiseEnplaceDTOPointDeVente line : listIntrant) 
					if(line.getIdDepartement() == m.getIdDepartement())
						tmp += line.getQuantiteIntrantAMettreEnplace();
				
				// QUICK  FIX BUG NIANG 3 MARS 2018 LIGNE TOTAL DU DERNIER ELEMENT N ETAIT PAS AFFICHEr
				Paragraph LigneTOTAL2 = new Paragraph("TOTAL  " + tmp + " Tonne(s)");
				PdfPCell cellDepartementCenterLT2 = new PdfPCell(LigneTOTAL2);
				cellDepartementCenterLT2.setColspan(9);
				cellDepartementCenterLT2.setPaddingTop(7f);
				cellDepartementCenterLT2.setPaddingBottom(7f);
				cellDepartementCenterLT2.setPaddingLeft(115.f);
				cellDepartementCenterLT2.setBorderColor(greyColor);
				cellDepartementCenterLT2.setBackgroundColor(BaseColor.WHITE);
				table.addCell(cellDepartementCenterLT2);
			}
			
			j++;
		}

		
		
		
		
		table.setComplete(true);
		document.add(table);

		document.addCreationDate();

		insertSignatureWithCurrentDate(document);//
		document.close();
	}

	/********* FIN DL REPORT RESULT ***/

	/**** EXPORT DES QUOTAS DE MISE EN PLACE ******/

	/****** report resultat recherches des ****/

	public void exportQuotaMiseEnPlace(List<MiseEnplaceDTOPointDeVente> listIntrant)
			throws DocumentException, IOException {

		Double totalDepartement = 0.0;
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		document.rightMargin();
		PdfWriter.getInstance(document, new FileOutputStream(ConstantPGCA.DIRECTORY_PATH_TMP_PDF));
		document.open();
		insertHeaderTypeMEP(document);
		Paragraph preface22 = new Paragraph("LISTE DES QUOTAS DE MISE PAR DEPARTEMENT ", titrePDF);
		preface22.setAlignment(Element.ALIGN_CENTER);
		document.add(preface22);
		document.add(new Paragraph(" "));
		String[] Titles = new String[] { "Référence", "Intrant", "Point de vente", "Quota (T)", "Cumul (T)",
				"Reliquat (T)" };
		PdfPTable table = new PdfPTable(Titles.length);
		for (int i = 0; i < Titles.length; i++)
			insetCellDataColor(table, Titles[i]);
		// table.addCell(Titles[i]);

		table.setHeaderRows(1);
		table.setSplitRows(false);
		table.setComplete(false);

		table.setWidthPercentage(100);
		// set relative columns width
		table.setWidths(new float[] { 0.8f, 2.0f, 2.0f, 1.0f, 1.0f, 1.0f });

		int j = 0;
		Long lastidDept = 0l;

		for (MiseEnplaceDTOPointDeVente m : listIntrant) {

			totalDepartement += m.getQuantiteIntrantAMettreEnplace();

			if (j == 0) {
				insertSautLigne(table);
				Paragraph DEPT = new Paragraph(
						"DEPARTEMENT DE " + m.getDepartement() + " (" + m.getRegion().toUpperCase() + ") ", sousTitre);
				DEPT.setAlignment(Element.ALIGN_CENTER);

				PdfPCell cellDepartementCenter = new PdfPCell(DEPT);
				cellDepartementCenter.setColspan(7);
				cellDepartementCenter.setPaddingTop(10f);
				cellDepartementCenter.setPaddingBottom(7f);
				cellDepartementCenter.setBackgroundColor(BaseColor.WHITE);
				cellDepartementCenter.setBorderColorLeft(BaseColor.WHITE);
				cellDepartementCenter.setBorderColorRight(BaseColor.WHITE);

				table.addCell(cellDepartementCenter);
			}

			if (lastidDept != 0 && lastidDept != m.getIdDepartement()) {

				insertSautLigne(table);
				// LIGEN HEADER DU DEPARTEMENT
				Paragraph DEPT = new Paragraph(
						"DEPARTEMENT DE " + m.getDepartement() + " (" + m.getRegion().toUpperCase() + ") ", sousTitre);
				DEPT.setAlignment(Element.ALIGN_CENTER);
				PdfPCell cellDepartementCenter = new PdfPCell(DEPT);
				cellDepartementCenter.setColspan(7);
				cellDepartementCenter.setPaddingTop(10f);
				cellDepartementCenter.setPaddingBottom(7f);
				cellDepartementCenter.setBackgroundColor(BaseColor.WHITE);
				cellDepartementCenter.setBorderColor(BaseColor.WHITE);
				table.addCell(cellDepartementCenter);
				totalDepartement = 0.0;
			}
			insetCellData(table, "Q" + m.getIdMiseEnPlace());
			insetCellData(table, m.getLibelleIntrantAMettreEnplace() + "");
			insetCellData(table, m.getNomPointDeVente());

			// insetCellData(table, m.getQuantiteIntrantAMettreEnplace() + "");
			insetCellData(table, m.getQuotaIntrantAMettreEnplace() + "");
			insetCellData(table, m.getCumulIntrantAMettreEnplace() + "");
			insetCellData(table, m.getReliquatIntrantAMettreEnplace() + "");

			lastidDept = m.getIdDepartement();
			j++;
		}

		table.setComplete(true);
		document.add(table);
		// document.addCreationDate();
		// insertSignatureWithCurrentDate
		insertSignatureWithCurrentDate(document);// insertSignatureWithCurrentDate
		document.close();
	}

	/*********** FIN EXPORT QUOTA MEP **************/

	/*********** FIN EXPORT DES MEP EFFECTUES SUR UN QUOTA **************/

	
	public void exportMiseEnPlacesEffectues(List<MiseEnPlaceEffectuerDTO> listIntrant)
			throws DocumentException, IOException {

		Double totalDepartement = 0.0;
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		document.rightMargin();
		PdfWriter.getInstance(document, new FileOutputStream(ConstantPGCA.DIRECTORY_PATH_TMP_PDF));
		document.open();
		insertHeaderTypeMEP(document);

		Double totalCumul = 0.0;

		String regQuota = listIntrant != null && listIntrant.size() > 1 ? listIntrant.get(0).getQuotaDeReference() : "";
		String intrant = listIntrant != null && listIntrant.size() > 1 ? listIntrant.get(0).getLibelleIntrant() : "";

		String departementRefrence = listIntrant != null && listIntrant.size() > 1
				? listIntrant.get(0).getPointdeVenteLibelle() : "";

		String cumulQuotaReference = listIntrant != null && listIntrant.size() > 1
				? listIntrant.get(0).getCumulDeReference() : "";
		String reliquatQuotaReference = listIntrant != null && listIntrant.size() > 1
				? listIntrant.get(0).getReliquatDeReference() : "";
		String quotaReference = listIntrant != null && listIntrant.size() > 1
				? listIntrant.get(0).getQuantitequotaDeReference() : "";

		Paragraph preface22 = new Paragraph(
				"LISTE DES MISES EN PLACE DE " + intrant + " AU POINT DE VENTE DE " + departementRefrence, titrePDF);
		preface22.setAlignment(Element.ALIGN_CENTER);
		document.add(preface22);
		document.add(new Paragraph(" "));
		String[] Titles = new String[] { "Date", "Quantité", "N° BL", "N° LV", "Transporteur", "Camion", "Chauffeur" };
		PdfPTable table = new PdfPTable(Titles.length);
		for (int i = 0; i < Titles.length; i++)
			insetCellDataColor(table, Titles[i]);
		// table.addCell(Titles[i]);

		table.setHeaderRows(1);
		table.setSplitRows(false);
		table.setComplete(false);

		table.setWidthPercentage(100);
		// set relative columns width
		table.setWidths(new float[] { 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f });

		for (MiseEnPlaceEffectuerDTO m : listIntrant) {

			totalDepartement += m.getQuantiteAmettreEnplace();

			insetCellData(table, m.getDateMiseEnplaceEffective() + "");
			insetCellData(table, m.getQuantiteAmettreEnplace() + "");
			insetCellData(table, m.getBlManuel());

			insetCellData(table, m.getLvManuel());
			insetCellData(table, m.getTransporteur());
			insetCellData(table, m.getCamion());
			insetCellData(table, m.getChauffeur());

			totalCumul += m.getQuantiteAmettreEnplace();
		}

		insertSautLigne(table);

		Paragraph ref = new Paragraph("Quota de référence :  " + regQuota, text);
		ref.setAlignment(Element.ALIGN_CENTER);
		PdfPCell cellDepartementCenterref = new PdfPCell(ref);
		cellDepartementCenterref.setColspan(7);
		cellDepartementCenterref.setPaddingTop(10f);
		cellDepartementCenterref.setPaddingBottom(7f);
		cellDepartementCenterref.setBackgroundColor(BaseColor.WHITE);
		cellDepartementCenterref.setBorderColor(BaseColor.WHITE);
		table.addCell(cellDepartementCenterref);

		insertSautLigne(table);
		// LIGEN HEADER DU DEPARTEMENT

		Paragraph DEPT0 = new Paragraph("Quantité à mettre en place : " + quotaReference + " (T) ", text);
		DEPT0.setAlignment(Element.ALIGN_CENTER);
		PdfPCell cellDepartementCenter0 = new PdfPCell(DEPT0);
		cellDepartementCenter0.setColspan(7);
		cellDepartementCenter0.setPaddingTop(10f);
		cellDepartementCenter0.setPaddingBottom(7f);
		cellDepartementCenter0.setBackgroundColor(BaseColor.WHITE);
		cellDepartementCenter0.setBorderColor(BaseColor.WHITE);
		table.addCell(cellDepartementCenter0);

		insertSautLigne(table);
		// LIGEN HEADER DU DEPARTEMENT
		Paragraph DEPT = new Paragraph("Cumul des mises en place : " + cumulQuotaReference + " (T) ", text);
		DEPT.setAlignment(Element.ALIGN_CENTER);
		PdfPCell cellDepartementCenter = new PdfPCell(DEPT);
		cellDepartementCenter.setColspan(7);
		cellDepartementCenter.setPaddingTop(10f);
		cellDepartementCenter.setPaddingBottom(7f);
		cellDepartementCenter.setBackgroundColor(BaseColor.WHITE);
		cellDepartementCenter.setBorderColor(BaseColor.WHITE);
		table.addCell(cellDepartementCenter);

		insertSautLigne(table);
		// LIGEN HEADER DU DEPARTEMENT
		Paragraph DEPT2 = new Paragraph("RELIQUAT : " + reliquatQuotaReference + " (T) ", text);
		DEPT2.setAlignment(Element.ALIGN_CENTER);
		PdfPCell cellDepartementCenter2 = new PdfPCell(DEPT2);
		cellDepartementCenter2.setColspan(5);
		cellDepartementCenter2.setPaddingTop(10f);
		cellDepartementCenter2.setPaddingBottom(7f);
		cellDepartementCenter2.setBackgroundColor(BaseColor.WHITE);
		cellDepartementCenter2.setBorderColor(BaseColor.WHITE);
		table.addCell(cellDepartementCenter2);

		Paragraph signature = new Paragraph("Signature - Cachet SEDAB ", text);
		signature.setAlignment(Element.ALIGN_CENTER);
		PdfPCell cellDepartementCentersignature = new PdfPCell(signature);
		cellDepartementCentersignature.setColspan(2);
		cellDepartementCentersignature.setPaddingTop(10f);
		cellDepartementCentersignature.setPaddingBottom(7f);
		cellDepartementCentersignature.setBackgroundColor(BaseColor.WHITE);
		cellDepartementCentersignature.setBorderColor(BaseColor.WHITE);
		table.addCell(cellDepartementCentersignature);

		table.setComplete(true);
		document.add(table);
		insertSignatureWithCurrentDate(document);// insertSignatureWithCurrentDate
		document.close();
	}

	/************************/

	/****** report liste des intrants des fournisseurs recherches des ****/

	public void exportlistIntrantsOfStock(List<IntrantDTO> listIntrant) throws DocumentException, IOException {

		Double totalDepartement = 0.0;
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		document.rightMargin();
		PdfWriter.getInstance(document, new FileOutputStream(ConstantPGCA.DIRECTORY_PATH_TMP_PDF));
		document.open();
		insertHeaderTypeMEP(document);
		Paragraph preface22 = new Paragraph("LISTE DES STOCKS PAR FOURNISSEUR ", titrePDF);
		preface22.setAlignment(Element.ALIGN_CENTER);
		document.add(preface22);
		document.add(new Paragraph(" "));
		String[] Titles = new String[] { "Fournisseur", "Intrant", "Stock initial", "Stock restant", "Prix / Tonnage" };
		PdfPTable table = new PdfPTable(Titles.length);
		for (int i = 0; i < Titles.length; i++)
			insetCellDataColor(table, Titles[i]);
		// table.addCell(Titles[i]);

		table.setHeaderRows(1);
		table.setSplitRows(false);
		table.setComplete(false);

		table.setWidthPercentage(100);
		// set relative columns width
		table.setWidths(new float[] { 2.0f, 2.0f, 1.0f, 1.0f, 1.0f });

		int j = 0;
		Long lastidDept = 0l;

		for (IntrantDTO m : listIntrant) {

			/*
			 * Paragraph DEPT = new Paragraph(m.getLibelleFournisseur() ,
			 * sousTitre); DEPT.setAlignment(Element.ALIGN_CENTER);
			 * 
			 * PdfPCell cellDepartementCenter = new PdfPCell(DEPT);
			 * cellDepartementCenter.setColspan(7);
			 * cellDepartementCenter.setPaddingTop(10f);
			 * cellDepartementCenter.setPaddingBottom(7f);
			 * cellDepartementCenter.setBackgroundColor(BaseColor.WHITE);
			 * cellDepartementCenter.setBorderColorLeft(BaseColor.WHITE);
			 * cellDepartementCenter.setBorderColorRight(BaseColor.WHITE);
			 * cellDepartementCenter.setBorderColor(greyColor);
			 * table.addCell(cellDepartementCenter);
			 */

			/*
			 * if (j == 0) { insertSautLigne(table); Paragraph DEPT = new
			 * Paragraph(m.getLibelleFournisseur() , sousTitre);
			 * DEPT.setAlignment(Element.ALIGN_CENTER);
			 * 
			 * PdfPCell cellDepartementCenter = new PdfPCell(DEPT);
			 * cellDepartementCenter.setColspan(7);
			 * cellDepartementCenter.setPaddingTop(10f);
			 * cellDepartementCenter.setPaddingBottom(7f);
			 * cellDepartementCenter.setBackgroundColor(BaseColor.WHITE);
			 * cellDepartementCenter.setBorderColorLeft(BaseColor.WHITE);
			 * cellDepartementCenter.setBorderColorRight(BaseColor.WHITE);
			 * cellDepartementCenter.setBorderColor(greyColor);
			 * table.addCell(cellDepartementCenter);
			 * 
			 * lastidDept = m.getIdFournisseur(); }
			 * 
			 * 
			 * if(lastidDept != 0 && lastidDept != m.getIdFournisseur()) {
			 * 
			 * insertSautLigne(table); // LIGEN HEADER DU DEPARTEMENT Paragraph
			 * DEPT = new Paragraph( m.getLibelleFournisseur() , sousTitre);
			 * DEPT.setAlignment(Element.ALIGN_CENTER); PdfPCell
			 * cellDepartementCenter = new PdfPCell(DEPT);
			 * cellDepartementCenter.setColspan(7);
			 * cellDepartementCenter.setPaddingTop(10f);
			 * cellDepartementCenter.setPaddingBottom(7f);
			 * cellDepartementCenter.setBackgroundColor(BaseColor.WHITE);
			 * cellDepartementCenter.setBorderColor(BaseColor.WHITE);
			 * cellDepartementCenter.setBorderColor(greyColor);
			 * 
			 * table.addCell(cellDepartementCenter); totalDepartement = 0.0; }
			 */
			insetCellData(table, m.getLibelleFournisseur());
			insetCellData(table, m.getLibelleProduit() + "");
			insetCellData(table, m.getQuantiteInitaile() + " (T) ");
			insetCellData(table, m.getQuantite() + " (T) ");
			insetCellData(table, m.getInfosTarifs() + " ");

			lastidDept = m.getIdFournisseur();
		}

		table.setComplete(true);
		document.add(table);
		// document.addCreationDate();
		// insertSignatureWithCurrentDate
		insertSignatureWithCurrentDate(document);// insertSignatureWithCurrentDate
		document.close();
	}

	/************************/


	public void insetCellData(PdfPTable table, String value) {
		PdfPCell cellDepartementCenter = new PdfPCell(new Phrase(value, text));
		cellDepartementCenter.setBorderColor(greyColor);
		table.addCell(cellDepartementCenter);
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
		cellDepartementCenter.setBorderColor(BaseColor.WHITE);
		cellDepartementCenter.setBackgroundColor(greyColor);
		cellDepartementCenter.setPaddingTop(10f);
		cellDepartementCenter.setPaddingBottom(10f);
		cellDepartementCenter.setBorderColorBottom(BaseColor.RED);
		cellDepartementCenter.setBorder(0);

		table.addCell(cellDepartementCenter);
	}

	public void insertSignatureWithCurrentDate(Document doc) {

		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

		text.setColor(greyColor);
		Paragraph signature = new Paragraph("Document généré par la plateforme PGCA le " + formatter.format(new Date()),
				text);

		try {
			doc.add(signature);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void generateResultMiseEnPlace2(List<MiseEnPlaceEffectuerDTO> listIntrant)
			throws DocumentException, IOException {

		if (listIntrant == null)
			return;
		if (listIntrant.size() == 0)
			return;

		// String intrantLibelle = listIntrant != null && listIntrant.size() > 0
		// ? listIntrant.get(0).get : "";
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		PdfWriter.getInstance(document, new FileOutputStream(ConstantPGCA.DIRECTORY_PATH_TMP_PDF));
		document.open();

		document.add(Image.getInstance(ConstantPGCA.DIRECTORY_PATH_LOGO_PDF));
		Paragraph preface22 = new Paragraph("Liste des mises en place " + listIntrant.get(0).getLibelleIntrant()
				+ "  effectuées au Point de vente " + listIntrant.get(0).getPointdeVenteLibelle());
		preface22.setAlignment(Element.ALIGN_CENTER);
		document.add(preface22);
		document.add(new Paragraph(" "));

		String[] Titles = new String[] { "Quantite", "N° BL", "N° LV", "Transporteur", "Chauffeur", "Camion",
				"Département" };

		PdfPTable table = new PdfPTable(Titles.length);

		for (int i = 0; i < Titles.length; i++)
			table.addCell(Titles[i]);

		table.setHeaderRows(1);
		table.setSplitRows(false);
		table.setComplete(false);

		for (MiseEnPlaceEffectuerDTO m : listIntrant) {
			table.addCell(m.getQuantiteAmettreEnplace() + "");
			// table.addCell("");
			table.addCell(m.getLvManuel() + "");
			table.addCell(m.getBlManuel() + "");
			table.addCell(m.getTransporteur() + "");
			table.addCell(m.getChauffeur() + "");
			table.addCell(m.getCamion() + "");
			table.addCell(m.getPointdeVenteLibelle() + "");
			table.addCell(m.getPointdeVenteLibelleDepartement() + "");
		}

		table.setComplete(true);
		document.add(table);

		insertSignatureWithCurrentDate(document);
		document.close();
	}

	/****** End report result ***/

	List<MiseEnplaceDTOPointDeVente> listIntrant; // filter

	public void generateMiseEnPlaceEffectueesSurQuto(List<MiseEnPlaceEffectuerDTO> mepDejaEffectuee,
			MiseEnplaceDTOPointDeVente recap) throws DocumentException, IOException {

		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		PdfWriter.getInstance(document, new FileOutputStream(ConstantPGCA.DIRECTORY_PATH_TMP_PDF));
		document.open();

		insertHeader(document, recap);
		insertContentListMEPdejaEffectue(document, mepDejaEffectuee);
		insertSignatureWithCurrentDate(document);
		document.close();
	}

	private PdfPCell getCell(String text, int alignment) {
		PdfPCell cell = new PdfPCell(new Phrase(text));
		cell.setPadding(0);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	

	private void insertHeader(Document doc, MiseEnplaceDTOPointDeVente recap)
			throws DocumentException, MalformedURLException, IOException {
		doc.add(Image.getInstance(ConstantPGCA.DIRECTORY_PATH_LOGO_PDF));

		// Get the current date and time
		LocalDateTime currentTime = LocalDateTime.now();
		System.out.println("Current DateTime: " + currentTime);

		LocalDate date1 = currentTime.toLocalDate();

		// Auteur and Date
		PdfPTable t = new PdfPTable(3);
		t.addCell(getCell("Auteur : " + getSessionDatabyTag("connectedUserName"), PdfPCell.ALIGN_LEFT));
		t.addCell(getCell("", PdfPCell.ALIGN_LEFT));
		t.addCell(getCell("Date d'extraction : " + date1, PdfPCell.ALIGN_RIGHT));

		doc.add(t);

		t = new PdfPTable(3);
		t.addCell(getCell("Campagne : " + "2016 - 2017", PdfPCell.ALIGN_LEFT));
		t.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
		t.addCell(getCell("Zone  : " + getSessionDatabyTag("connectedUserPointPhysique"), PdfPCell.ALIGN_RIGHT));
		doc.add(t);

		Font boldFont = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);

		Paragraph preface2;
		if (recap != null)
			preface2 = new Paragraph("Mise en place de " + recap.getLibelleIntrantAMettreEnplace() + " numéro MP000"
					+ recap.getIdMiseEnPlace());
		else
			preface2 = new Paragraph("Situation globale des mises en place de la campagne en cours");

		preface2.setAlignment(Element.ALIGN_CENTER);
		preface2.setFont(boldFont);
		doc.add(preface2);
		doc.add(new Paragraph(" "));
		doc.add(new Paragraph(" "));

	}

	private void insertContentSummary(Document document, List<MiseEnplaceDTOPointDeVente> mepEffectuesurQuota,
			MiseEnplaceDTOPointDeVente recap) throws DocumentException, MalformedURLException, IOException {

		PdfPTable table = new PdfPTable(6);

		table.addCell("Departement");
		table.addCell("Gérant");
		table.addCell("Intrant");
		table.addCell("Quota");
		table.addCell("Cumul");
		table.addCell("Reliquat");

		table.setHeaderRows(1);
		table.setSplitRows(false);
		table.setComplete(false);

		if (recap != null) {
			table.addCell(recap.getDepartementPointdeVente());
			table.addCell(recap.getNomGerant());
			table.addCell(recap.getLibelleIntrantAMettreEnplace());
			table.addCell(recap.getQuotaIntrantAMettreEnplace() + "");
			table.addCell(recap.getCumulIntrantAMettreEnplace() + "");
			table.addCell(recap.getReliquatIntrantAMettreEnplace() + "");
		} else {

			for (MiseEnplaceDTOPointDeVente r : mepEffectuesurQuota) {
				table.addCell(r.getDepartementPointdeVente());
				table.addCell(r.getNomGerant());
				table.addCell(r.getLibelleIntrantAMettreEnplace());
				table.addCell(r.getQuotaIntrantAMettreEnplace() + "");
				table.addCell(r.getCumulIntrantAMettreEnplace() + "");
				table.addCell(r.getReliquatIntrantAMettreEnplace() + "");
			}
		}

		// for (MiseEnplaceDTOPointDeVente m : listMep) {
		// table.addCell(m.getBlMiseEnPlace());
		// table.addCell(m.getCamion());
		// table.addCell(m.getChauffeur());
		// table.addCell(m.getTransporteur());
		// table.addCell(m.getDistributeur());
		// table.addCell(m.getCamion());
		// table.addCell(m.getQuantiteIntrantAMettreEnplace() + "");
		// table.addCell(m.getDateMiseEnplace());
		// }

		table.setComplete(true);
		document.add(table);

		document.add(new Paragraph(""));
		document.add(new Paragraph(""));
		// document.add(new Paragraph(""));
		// document.newPage();
	}

	private void insertContentListMEPdejaEffectue(Document document, List<MiseEnPlaceEffectuerDTO> listMep)
			throws DocumentException, MalformedURLException, IOException {

		if (listMep == null)
			return;

		String[] Titles = new String[] { "Date", "Quantite", "numéro BL", "Numéro LV", "Transporteur", "Chauffeur",
				"Camion" };

		PdfPTable table = new PdfPTable(Titles.length);

		for (int i = 0; i < Titles.length; i++)
			table.addCell(Titles[i]);

		table.setHeaderRows(1);
		table.setSplitRows(false);
		table.setComplete(false);

		for (MiseEnPlaceEffectuerDTO m : listMep) {
			table.addCell(m.getDateMiseEnplaceEffective() + "");
			table.addCell(m.getQuantiteAmettreEnplace() + "");
			table.addCell(m.getBlManuel());
			table.addCell(m.getLvManuel());
			table.addCell(m.getTransporteur());
			table.addCell(m.getChauffeur());
			table.addCell(m.getCamion() + "");
		}

		table.setComplete(true);
		document.add(table);
	}

	public String getSessionDatabyTag(String tag) // connectedUserName
	{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			return (String) session.getAttribute(tag);
		}
		return "";
	}



	private void insertHeaderTypeMEP(Document doc) throws DocumentException, MalformedURLException, IOException {

		PdfPTable table = new PdfPTable(3);
		table.setWidths(new float[] { 2.0f, 1.0f, 2.0f});

	
		table.setHeaderRows(1);
		table.setSplitRows(false);
		table.setComplete(false);

		table.setWidthPercentage(100);
		// set relative columns width

		PdfPCell cellDepartementCenter = new PdfPCell(new Phrase("Immeuble Beau Rivage, ", titre));
		cellDepartementCenter.setBorderColor(greyColor);
		cellDepartementCenter.setBackgroundColor(greyColor);
		cellDepartementCenter.setPaddingTop(3f);
		cellDepartementCenter.setPaddingBottom(3f);
		table.addCell(cellDepartementCenter);
		
		
		PdfPCell telCel2 = new PdfPCell(new Phrase("", greyFont));
		telCel2.setBorderColor(greyColor);
		telCel2.setBackgroundColor(BaseColor.WHITE);
		telCel2.setBorderColorBottom(BaseColor.WHITE);
		telCel2.setPaddingTop(3f);
		telCel2.setPaddingBottom(3f);
		telCel2.setPaddingLeft(40.0f);
		table.addCell(telCel2);
	
		
		PdfPCell faxCell = new PdfPCell(new Phrase("TEL  : 00221 33 832 56 09", titre));
		faxCell.setBorderColor(greyColor);
		faxCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		faxCell.setBackgroundColor(greyColor);
		faxCell.setPaddingTop(3f);
		faxCell.setPaddingBottom(3f);
		table.addCell(faxCell);
		
		
		
		
		
		
		PdfPCell cellDepartementCenter2 = new PdfPCell(new Phrase("Km 5,Bd du centenaire de commune de Dakar ", titre));
		cellDepartementCenter2.setBorderColor(greyColor);
		cellDepartementCenter2.setBackgroundColor(greyColor);
		cellDepartementCenter2.setPaddingTop(3f);
		cellDepartementCenter2.setPaddingBottom(3f);
		table.addCell(cellDepartementCenter2);
		
		
		
		PdfPCell telCel = new PdfPCell(new Phrase("", greyFont));
		telCel.setBorderColor(BaseColor.WHITE);
		telCel.setBackgroundColor(BaseColor.WHITE);
		telCel.setBorderColorTop(BaseColor.WHITE);
		telCel.setPaddingTop(3f);
		telCel.setPaddingBottom(3f);
		table.addCell(telCel);
		
		PdfPCell faxCell3 = new PdfPCell(new Phrase("FAX  : 00221 33 832 27 94", titre));
		faxCell3.setBorderColor(greyColor);
		faxCell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
		faxCell3.setBackgroundColor(greyColor);
		faxCell3.setPaddingTop(3f);
		faxCell3.setPaddingBottom(3f);
		table.addCell(faxCell3);
		
		//end2
		
		PdfPCell cel2 = new PdfPCell(new Phrase("BP : 5361 Fann Dakar ", titre));
		cel2.setBorderColor(greyColor);
		cel2.setBackgroundColor(greyColor);
		cel2.setPaddingTop(5f);
		cel2.setPaddingBottom(3f);
		table.addCell(cel2);
		
		
		
		PdfPCell telCel4 = new PdfPCell(new Phrase("SEDAB - SENEGAL", greyFont));
		telCel4.setBorderColor(BaseColor.WHITE);
		telCel4.setHorizontalAlignment(Element.ALIGN_CENTER);
		telCel4.setBackgroundColor(BaseColor.WHITE);
		telCel4.setPaddingTop(5f);
		telCel4.setPaddingBottom(3f);
		table.addCell(telCel4);
		
		PdfPCell faxCellCel2 = new PdfPCell(new Phrase(" contact@sedab-senegal.com  - www.sedab-senegal.com", titre));
		faxCellCel2.setBorderColor(greyColor);
		faxCellCel2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		faxCellCel2.setBackgroundColor(greyColor);
		faxCellCel2.setPaddingTop(3f);
		faxCellCel2.setPaddingBottom(15f);
		table.addCell(faxCellCel2);
		
		
		// end 
		
		
		PdfPCell left = new PdfPCell(new Phrase("", titre));
		left.setBorderColor(BaseColor.WHITE);
		left.setBorderColorRight(BaseColor.WHITE);
		left.setBackgroundColor(BaseColor.WHITE);
		table.addCell(left);
		
		Image gg = Image.getInstance(ConstantPGCA.LDIRECTORY_PICTOXXXX);
		gg.setAlignment(Element.ALIGN_CENTER);
		gg.setCompressionLevel(5);
		PdfPCell logo = new PdfPCell(gg);
		logo.setBorderColor(greyColor);
		logo.setPaddingLeft(40.0f);
		logo.setPaddingTop(5);
		logo.setHorizontalAlignment(Element.ALIGN_LEFT);
		logo.setBorder(PdfPCell.NO_BORDER);
		table.addCell(logo);

		
		PdfPCell right = new PdfPCell(new Phrase("", titre));
		right.setBorderColor(BaseColor.WHITE);
		right.setHorizontalAlignment(Element.ALIGN_RIGHT);
		right.setBorderColorLeft(BaseColor.WHITE);
		right.setBackgroundColor(BaseColor.WHITE);
		table.addCell(right);
		
		
		//*****
		
	
		


		/*
		 * Paragraph prefaceS = new Paragraph("TEXT LEFT ON TH BC");
		 * prefaceS.setAlignment(Element.ALIGN_LEFT); doc.add(prefaceS);
		 * 
		 * prefaceS = new Paragraph("TEXT RIFGT ON TH BV");
		 * prefaceS.setAlignment(Element.ALIGN_RIGHT); doc.add(prefaceS);
		 * 
		 * prefaceS = new Paragraph("TEXT RIFGT ON TH BV2222");
		 * prefaceS.setAlignment(Element.ALIGN_LEFT); doc.add(prefaceS);
		 * 
		 * prefaceS = new Paragraph("TEXT RIFGT ON TH BV222");
		 * prefaceS.setAlignment(Element.ALIGN_RIGHT);
		 * 
		 * prefaceS.getFont().setStyle("margin-top : -20");
		 * prefaceS.getFont().setColor(BaseColor.GREEN); doc.add(prefaceS);
		 */

		/*
		 * doc.add(Image.getInstance(ConstantPGCA.LDIRECTORY_PICTO_BlANC));
		 * doc.add(Image.getInstance(ConstantPGCA.LDIRECTORY_PICTO));
		 * doc.add(Image.getInstance(ConstantPGCA.LDIRECTORY_PICTO_BlANC));
		 */
		doc.add(table);
		//doc.add(Image.getInstance(ConstantPGCA.LDIRECTORY_PICTO));
	}
	
	
	
	
	
	private void insertHeaderTypeMEPVERYGUDDESIGN(Document doc) throws DocumentException, MalformedURLException, IOException {

		String[] Titles = new String[] { "Immeuble Beau Rivage , Km 5, Bd du" , "" , "FAX 87655443434"  };
		PdfPTable table = new PdfPTable(Titles.length);
		
		
		for (int i = 0; i < Titles.length; i++)
			{
				if(i == 1)
					table.addCell(Image.getInstance(ConstantPGCA.DIRECTORY_PATH_TMP_PDF));	
				table.addCell(Titles[i]);

			}
		
		table.setHeaderRows(1);
		table.setSplitRows(false);
		table.setComplete(false);

		table.setWidthPercentage(100);
		// set relative columns width
		table.setWidths(new float[] { 2.0f, 8.0f, 2.0f});

		PdfPCell cellDepartementCenter = new PdfPCell(new Phrase("Centenaire de commune de Dakar", text));
		cellDepartementCenter.setBorderColor(BaseColor.WHITE);
		cellDepartementCenter.setBackgroundColor(greyColor);
		cellDepartementCenter.setPaddingTop(10f);
		cellDepartementCenter.setPaddingBottom(10f);
		cellDepartementCenter.setBorderColorBottom(BaseColor.RED);
		cellDepartementCenter.setBorder(0);
		table.addCell(cellDepartementCenter);
		
		PdfPCell telCel = new PdfPCell(new Phrase("TEL  : 8888888888", text));
		telCel.setBorderColor(BaseColor.WHITE);
		telCel.setBackgroundColor(greyColor);
		telCel.setPaddingTop(10f);
		telCel.setPaddingBottom(10f);
		telCel.setBorderColorBottom(BaseColor.RED);
		telCel.setBorder(0);
		table.addCell(telCel);
		
		PdfPCell faxCell = new PdfPCell(new Phrase("FAX  : 8888888888", text));
		faxCell.setBorderColor(BaseColor.WHITE);
		faxCell.setBackgroundColor(greyColor);
		faxCell.setPaddingTop(10f);
		faxCell.setPaddingBottom(10f);
		faxCell.setBorderColorBottom(BaseColor.RED);
		faxCell.setBorder(0);
		table.addCell(faxCell);

		doc.add(table);
	}


	
	
	
	
	
	/********   EXPORT PDF FOR OL *********/
	
	
	public void exportRecuORdreDeLivraison(List<IntrantDTO> listIntrant , CommandeDTO commandeDTO) throws DocumentException, IOException {


		Double totalDepartement = 0.0;
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		document.rightMargin();
		PdfWriter.getInstance(document, new FileOutputStream(ConstantPGCA.DIRECTORY_PATH_TMP_PDF));
		document.open();
		insertHeaderTypeMEP(document);
		Paragraph preface22 = new Paragraph("ORDRE DE LIVRAISON N° " + commandeDTO.getReferenceCMD() , titrePDF);
		preface22.setAlignment(Element.ALIGN_CENTER);
		document.add(preface22);
		document.add(new Paragraph(" "));
		String[] Titles = new String[] { "Fournisseur", "Intrant", "Stock initial", "Stock restant", "Prix / Tonnage" };
		PdfPTable table = new PdfPTable(Titles.length);
		for (int i = 0; i < Titles.length; i++)
			insetCellDataColor(table, Titles[i]);
		// table.addCell(Titles[i]);

		table.setHeaderRows(1);
		table.setSplitRows(false);
		table.setComplete(false);

		table.setWidthPercentage(100);
		// set relative columns width
		table.setWidths(new float[] { 2.0f, 2.0f, 1.0f, 1.0f, 1.0f });

		int j = 0;
		Long lastidDept = 0l;

		for (IntrantDTO m : listIntrant) {

			insetCellData(table, m.getLibelleFournisseur());
			insetCellData(table, m.getLibelleProduit() + "");
			insetCellData(table, m.getQuantiteInitaile() + " (T) ");
			insetCellData(table, m.getQuantite() + " (T) ");
			insetCellData(table, m.getInfosTarifs() + " ");

			lastidDept = m.getIdFournisseur();
		}

		table.setComplete(true);
		document.add(table);
		// document.addCreationDate();
		// insertSignatureWithCurrentDate
		insertSignatureWithCurrentDate(document);// insertSignatureWithCurrentDate
		document.close();
	}

	
	
}
