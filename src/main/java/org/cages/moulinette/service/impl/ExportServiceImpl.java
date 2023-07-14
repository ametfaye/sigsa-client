package org.cages.moulinette.service.impl;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.cages.moulinette.dto.AgentDTO;
import org.cages.moulinette.model.Agent;
import org.cages.moulinette.model.Ampliation;
import org.cages.moulinette.model.OrdreDeMission;
import org.cages.moulinette.repository.AmpliationRepository;
import org.cages.moulinette.service.IAgentService;
import org.cages.moulinette.service.IExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.WebColors;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.styledxmlparser.jsoup.Jsoup;

import net.glxn.qrgen.javase.QRCode;

@Service("exportService")
public class ExportServiceImpl implements IExportService {

	private static final String LOGO_SCEAU = "/images/sceau.png";
	
	private final static Logger LOGGER = Logger.getLogger(ExportServiceImpl.class);	
	
	@Value("${odm.app.url}")
	private String appUrl;
	
	
	@Autowired
	private IAgentService agentService;
	
	@Autowired
	private AmpliationRepository ampliationRepository;
	
	@Override
	public ByteArrayInputStream exportOdmPdf(long odmId) {
		OrdreDeMission ordreDeMission = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			// Initialize PDF writer
			PdfWriter writer = new PdfWriter(out);

			// Initialize PDF document
			PdfDocument pdf = new PdfDocument(writer);

			// Initialize document
			Document document = new Document(pdf,PageSize.A4);

			// create header
			createDocumentHeader(document, pdf, ordreDeMission, null, "ODM");
			
			// infos agent
			//infosAgent(ordreDeMission, document);

			document.add(new Paragraph(""));
			document.add(new Paragraph(""));
			
			// données de mission
			donneesDeMission(ordreDeMission, document);
			
			// create footer
			createDocumentFooter(document);

			document.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	@Override
	public ByteArrayInputStream exportTDRPdf(long odmId) {
		OrdreDeMission ordreDeMission = null;
		List<AgentDTO> participants = agentService.findAllAgentByOdm(odmId);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			// Initialize PDF writer
			PdfWriter writer = new PdfWriter(out);

			// Initialize PDF document
			PdfDocument pdf = new PdfDocument(writer);

			// Initialize document
			Document document = new Document(pdf,PageSize.A4);

			// create header TDR
			createDocumentHeader(document, pdf, ordreDeMission, null, "TDR");
			
			// texte TDR
			if (ordreDeMission.getMotif() != null && !"".equals(ordreDeMission.getMotif())) {
				afficherTexteTDR(document, ordreDeMission.getMotif());	
			}
						
			Paragraph idenBold = new Paragraph("Liste des participants");
			Color myColor = WebColors.getRGBColor("#03d87f");
			idenBold.setTextAlignment(TextAlignment.CENTER).setBold().setFontColor(myColor);
			document.add(idenBold);
						
			// liste des participants
			tableListeParticipants(participants, document);

			document.add(new Paragraph(""));
			document.add(new Paragraph(""));
			
			Paragraph budget = new Paragraph("Budget");
			Color budgetColor = WebColors.getRGBColor("#03d87f");
			budget.setTextAlignment(TextAlignment.CENTER).setBold().setFontColor(budgetColor);
			document.add(budget);
			
			// table budget
			tableBudget(ordreDeMission, document);
			
			// create footer
			createDocumentFooter(document);

			document.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	@Override
	public ByteArrayInputStream exportAstPdf(String matricule, long odmId) {
		OrdreDeMission ordreDeMission = null; // call service
		Agent agent = agentService.findByMatricule(matricule);
		List<Ampliation> ampliations = ampliationRepository.findAll();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			// Initialize PDF writer
			PdfWriter writer = new PdfWriter(out);

			// Initialize PDF document
			PdfDocument pdf = new PdfDocument(writer);

			// Initialize document
			Document document = new Document(pdf,PageSize.A4);

			// create header
			createDocumentHeader(document, pdf, ordreDeMission, agent, "AST");

			document.add(new Paragraph(""));
			document.add(new Paragraph(""));
			
			createAstBody(agent, ordreDeMission, document);
			
			document.add(new Paragraph(""));
			document.add(new Paragraph(""));
			
			ampliationsBloc(ampliations, document);
			
			// create footer
			createDocumentFooter(document);

			document.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	private void donneesDeMission(OrdreDeMission ordreDeMission, Document document) {
		Paragraph odmTitle = new Paragraph("DONNEES DE MISSION");
		Color myColor = WebColors.getRGBColor("#03d87f");
		odmTitle.setTextAlignment(TextAlignment.CENTER).setBold().setFontColor(myColor);
		document.add(odmTitle);
		document.add(new Paragraph(""));
		
		Table table = new Table(new float[3]).useAllAvailableWidth();
		table.setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);

		Stream.of("Destination", "Date début", "Date fin").forEach(headerTitle -> {
			com.itextpdf.layout.element.Cell cell = new com.itextpdf.layout.element.Cell().add(headerTitle);
			Color headerColor = WebColors.getRGBColor("#f1f5fa");
			cell.setBackgroundColor(headerColor);
			cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
			table.addHeaderCell(cell);
		});
		fillDataInTable(table, ordreDeMission);
		document.add(table);
	}

	private void fillDataInTable(Table table, OrdreDeMission ordreDeMission) {
		com.itextpdf.layout.element.Cell dest = new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(ordreDeMission.getDestination().getDestinationLibelle())));
		dest.setVerticalAlignment(VerticalAlignment.MIDDLE);
		dest.setHorizontalAlignment(HorizontalAlignment.CENTER);
		table.addCell(dest);
		
		com.itextpdf.layout.element.Cell dateDebut = new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(new SimpleDateFormat("dd/MM/yyyy").format(ordreDeMission.getDateDebutMission()))));
		dateDebut.setVerticalAlignment(VerticalAlignment.MIDDLE);
		dateDebut.setHorizontalAlignment(HorizontalAlignment.CENTER);
		table.addCell(dateDebut);
		
		com.itextpdf.layout.element.Cell dateFin = new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(new SimpleDateFormat("dd/MM/yyyy").format(ordreDeMission.getDateFinMission()))));
		dateFin.setVerticalAlignment(VerticalAlignment.MIDDLE);
		dateFin.setHorizontalAlignment(HorizontalAlignment.CENTER);
		table.addCell(dateFin);
		
	}
	
	private void tableBudget(OrdreDeMission ordreDeMission, Document document) {
		document.add(new Paragraph(""));
		Table table = new Table(new float[3]).useAllAvailableWidth();
		table.setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);

		Stream.of("Frais Hebergement", "Frais billets avions", "Frais annexes").forEach(headerTitle -> {
			com.itextpdf.layout.element.Cell cell = new com.itextpdf.layout.element.Cell().add(headerTitle);
			Color headerColor = WebColors.getRGBColor("#f1f5fa");
			cell.setBackgroundColor(headerColor);
			cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
			table.addHeaderCell(cell);
		});
		document.add(table);
	}

	private void tableListeParticipants(List<AgentDTO> participants, Document document) {
		document.add(new Paragraph(""));
		Table table = new Table(new float[4]).useAllAvailableWidth();
		table.setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);

		Stream.of("Matricule", "Nom", "Prénom", "Service").forEach(headerTitle -> {
			com.itextpdf.layout.element.Cell cell = new com.itextpdf.layout.element.Cell().add(headerTitle);
			Color headerColor = WebColors.getRGBColor("#f1f5fa");
			cell.setBackgroundColor(headerColor);
			cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
			table.addHeaderCell(cell);
		});
		for (AgentDTO agentDTO : participants) {
			com.itextpdf.layout.element.Cell mat = new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(agentDTO.getMatricule())));
			mat.setVerticalAlignment(VerticalAlignment.MIDDLE);
			mat.setHorizontalAlignment(HorizontalAlignment.CENTER);
			table.addCell(mat);
			
			com.itextpdf.layout.element.Cell nom = new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(agentDTO.getNom())));
			nom.setVerticalAlignment(VerticalAlignment.MIDDLE);
			nom.setHorizontalAlignment(HorizontalAlignment.CENTER);
			table.addCell(nom);
			
			com.itextpdf.layout.element.Cell prenom = new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(agentDTO.getPrenom())));
			prenom.setVerticalAlignment(VerticalAlignment.MIDDLE);
			prenom.setHorizontalAlignment(HorizontalAlignment.CENTER);
			table.addCell(prenom);	
			
			com.itextpdf.layout.element.Cell service = new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(agentDTO.getServiceLibelle())));
			service.setVerticalAlignment(VerticalAlignment.MIDDLE);
			service.setHorizontalAlignment(HorizontalAlignment.CENTER);
			table.addCell(service);	
		}
		document.add(table);
	}
	
	private void afficherTexteTDR(Document document, String motif) throws IOException {
		/*List<IElement> elements = HtmlConverter.convertToElements(motif);
		for (IElement iElement : elements) {
			//document.add(getContent(iElement));
			System.out.println(iElement);
		}*/
		document.add(new Paragraph(Jsoup.parse(motif).text()));
	}
	
	private static Image generateQRCodeImage(String barcodeText, Document document) throws Exception {
	    ByteArrayOutputStream stream = QRCode.from(barcodeText).withSize(125,125).stream();
	    ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());
		RenderedImage bufferedImage = ImageIO.read(bis);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "png", baos);
		Image qrCode = new Image(ImageDataFactory.create(baos.toByteArray()));
		qrCode.setHorizontalAlignment(HorizontalAlignment.RIGHT);
		return qrCode;
	}

	private void createDocumentHeader(Document document, PdfDocument pdf, OrdreDeMission ordreDeMission, Agent agent, String type) throws Exception {
		// Qr Code
		String qrCodeText = "";
		if (ordreDeMission != null) {
			qrCodeText = appUrl+"/odm/passageFrontiereOrder.xhtml?numOdm="+String.valueOf(ordreDeMission.getOdmId());
		}
		if (agent != null) {
			qrCodeText = appUrl+"/odm/passageFrontiereOrder.xhtml?agent="+agent.getMatricule();	
		}
		Image qrCodeImage = generateQRCodeImage(qrCodeText, document);		
		Image logoSceau = new Image(ImageDataFactory.create(getClass().getResource(LOGO_SCEAU)));

		PageSize ps = pdf.getDefaultPageSize();
		qrCodeImage.setFixedPosition(ps.getWidth() - document.getRightMargin() - 102, 720, 125);
		document.add(qrCodeImage);
		
		logoSceau.setFixedPosition(document.getLeftMargin() - (float)18 , 760, 130);
		document.add(logoSceau);
		
		Paragraph p1 = new Paragraph("REPUBLIQUE DU SENEGAL");
		p1.setFontSize(9);
		p1.setFixedPosition(document.getLeftMargin() - (float)18 , 740, 125);
		document.add(p1);

		Paragraph p5 = new Paragraph("Un peuple - Un but - Une foi");
		p5.setFontSize(9);
		p5.setFixedPosition(document.getLeftMargin() - (float)18 , 720, 200);
		document.add(p5);
		
		Paragraph p2 = new Paragraph("MFB/DAGE/PER");
		p2.setFontSize(10);
		p2.setFixedPosition(ps.getWidth() - document.getRightMargin() - 90, 700, 125);
		document.add(p2);
		
		Paragraph p3 = new Paragraph("Numéro:");
		p3.setFontSize(10);
		p3.setFixedPosition(ps.getWidth() - document.getRightMargin() - 90, 680, 125);
		document.add(p3);
		
		Paragraph p4 = new Paragraph("Date:");
		p4.setFontSize(10);
		p4.setFixedPosition(ps.getWidth() - document.getRightMargin() - 90, 660, 125);
		document.add(p4);
		document.add(new Paragraph(""));
		document.add(new Paragraph(""));
		
		Paragraph idenBold;
		Color myColor = WebColors.getRGBColor("#03d87f");
		Color blackColor = WebColors.getRGBColor("#000");
		
		if ("TDR".equals(type)) {
			idenBold = new Paragraph("Objet de la mission: "+ordreDeMission.getObjet());	
			idenBold.setTextAlignment(TextAlignment.CENTER).setBold().setFontColor(myColor);
		} else if ("ODM".equals(type)){
			idenBold = new Paragraph("ORDRE DE MISSION N° "+ordreDeMission.getOdmId());
			idenBold.setFontSize(12);
			idenBold.setTextAlignment(TextAlignment.CENTER).setBold().setFontColor(blackColor).setBorder(new SolidBorder(1));
		} else {
			idenBold = new Paragraph("AUTORISATION DE SORTIE DU TERRITOIRE NATIONAL");
			idenBold.setFontSize(12);
			idenBold.setTextAlignment(TextAlignment.CENTER).setBold().setFontColor(blackColor).setBorder(new SolidBorder(1));
		}
		idenBold.setMarginTop(150);
		document.add(idenBold);
		document.add(new Paragraph(""));
		document.add(new Paragraph(""));
	}

	private void createAstBody(Agent agent, OrdreDeMission ordreDeMission, Document document) {
		String civilite = "M".equals(agent.getPersonnePhysique().getSexe()) ? "MONSIEUR": "MADAME";
		Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(ordreDeMission.getDateDebutMission());
        cal2.setTime(ordreDeMission.getDateFinMission());
		long dureeMission = (cal2.getTimeInMillis() - cal1.getTimeInMillis()) / 86400000;
		String dateDebutMission = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH).format(ordreDeMission.getDateDebutMission());
		Paragraph paragraph = new Paragraph("Une autorisation de sortie du territoire national, d'une durée de "+dureeMission
											+" jours, déductibles de ses droits à congé, pour se rendre à ses frais "+ordreDeMission.getDestination().getDestinationLibelle()
											+" est accordée à compter du "+dateDebutMission
											+" à "+civilite+" "+agent.getPersonnePhysique().getNom()+", "+agent.getFonction().getLibelle()+", matricule de solde "+agent.getMatricule()
											+", en service "+agent.getSousService().getService().getLibelle()+".").setFontSize(11);
		paragraph.setTextAlignment(TextAlignment.CENTER);
		document.add(paragraph);
	}
	
	private void ampliationsBloc(List<Ampliation> ampliations, Document document) {
		if (null != ampliations && !ampliations.isEmpty()) {
			document.add(new Paragraph("Ampliations").setUnderline());
			for (Ampliation ampliation : ampliations) {
				document.add(new Paragraph(ampliation.getLibelle()).setFontSize(10));
			}
		}
	}
	
	private void createDocumentFooter(Document document) {
		Paragraph dateGeneration = new Paragraph("Document généré le "+ new SimpleDateFormat("dd/MM/yyyy").format(new Date()) +" à "+ new SimpleDateFormat("HH:mm").format(new Date())+" par la plateforme SIGMA");
		dateGeneration.setTextAlignment(TextAlignment.RIGHT);
		dateGeneration.setFontSize(9);
		document.add(new Paragraph(""));
		document.add(dateGeneration);
		Paragraph signature = new Paragraph("Cachet et Signature");
		signature.setFontSize(9);
		signature.setTextAlignment(TextAlignment.RIGHT);
		document.add(signature);
	}
}
