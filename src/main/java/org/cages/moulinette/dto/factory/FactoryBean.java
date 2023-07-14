package org.cages.moulinette.dto.factory;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.imageio.ImageIO;

import org.cages.moulinette.dto.BienAgricoleProducteurDTO;
import org.cages.moulinette.dto.CampagneAgricoleDTO;
import org.cages.moulinette.dto.EnrollementDTO;
import org.cages.moulinette.dto.ProducteurDTO;
import org.cages.moulinette.dto.ProgrammeAgricolDTO;
import org.cages.moulinette.dto.ServiceDTO;
import org.cages.moulinette.dto.SousServiceDTO;
import org.cages.moulinette.model.Adresse;
import org.cages.moulinette.model.BienAgricoleProducteur;
import org.cages.moulinette.model.CampagneAgricole;
import org.cages.moulinette.model.Contact;
import org.cages.moulinette.model.PersonnePhysique;
import org.cages.moulinette.model.Producteur;
import org.cages.moulinette.model.ProgrammeAgricol;
import org.cages.moulinette.model.Service;
import org.cages.moulinette.model.SousService;
import org.cages.moulinette.utils.CONSTANTES;
import org.cages.moulinette.utils.RandomString;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.styledxmlparser.jsoup.Jsoup;

import net.glxn.qrgen.javase.QRCode;

public class FactoryBean {

	public static ServiceDTO AdresseObjAsAdresseDTO(Service serviceObj, ServiceDTO serviceDTO) {
		if (serviceObj == null)
			return null;
		serviceDTO = new ServiceDTO();
		serviceDTO.setSrvId(serviceObj.getSrvId());
		serviceDTO.setLibelle(serviceObj.getLibelle());
		return serviceDTO;
	}

	

	public static SousServiceDTO sousServiceAsSousServiceDTO(SousService ssObj, SousServiceDTO ssDto) {
		if (null == ssObj)
			return null;
		ssDto = new SousServiceDTO();

		ssDto.setSrvId(ssObj.getSsrvId() + "");
		ssDto.setCode(ssObj.getCode());
		ssDto.setNom(ssObj.getLibelle());
		ssDto.setSrvNom(ssObj.getService() != null ? ssObj.getService().getLibelle() : "");

		return ssDto;
	}



	/******* CAMPAGEN AGRICOLE ****/

	public static CampagneAgricoleDTO mapperObjToDTO(CampagneAgricole obj, CampagneAgricoleDTO dto) {
		if (obj == null)
			return null;
		dto = new CampagneAgricoleDTO();

		if (obj != null) {
			
			SimpleDateFormat df = new SimpleDateFormat("MMMM yyyy");
			Date oneDate = new Date(new java.util.Date().getTime());
			System.out.println(df.format(oneDate));

			dto.setId_ca(obj.getId_ca());
			dto.setDateFermeture(df.format(obj.getDateFermeture()));
			dto.setDateOuverture(df.format(obj.getDateOuverture()));
			dto.setPresentation(obj.getPresentation());
			dto.setStatus(obj.getStatut() + "");
			dto.setPresentationSansHtml(html2text (obj.getPresentation()));
			
			
			Date date = new Date(obj.getDateOuverture().getTime());
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			dto.setCampagnePeriode("Campagne Agricole " +calendar.get(Calendar.YEAR));

		}
		return dto;
	}
	
	public static CampagneAgricole mapperCampgneDTOasCampagneOBJ(CampagneAgricoleDTO dto) throws ParseException {
		
		CampagneAgricole ca  = new CampagneAgricole();

		if (dto != null) {	
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
			ca.setDateFermeture( df.parse(dto.getDateFermeture()));
			ca.setDateOuverture(df.parse(dto.getDateOuverture()));
			ca.setPresentation(dto.getPresentation());
			
			//ca.setStatut(dto.getStatus() + "");
		}
		return ca;
	}
	
	
	
	
	/****** PROGRAMMME ****/
	
	public static ProgrammeAgricol mapperProgrammeDTOasProgrammeOBJ(ProgrammeAgricolDTO dto) throws ParseException {
		
		ProgrammeAgricol pa  = new ProgrammeAgricol();

		if (dto != null) {	
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
			pa.setDateOuverture(df.parse(dto.getDateOuverture() + ""));
			pa.setDateFermeture(df.parse(dto.getDateFermeture()+ ""));
			pa.setDescriptifProgramme(dto.getDescriptifProgramme());
			pa.setStatut(dto.getStatut());
		}
		return pa;
	}
	
	

	public static ProgrammeAgricolDTO mapperObjToDTO(ProgrammeAgricol obj, ProgrammeAgricolDTO dto) {
		if (obj == null)
			return null;
		dto = new ProgrammeAgricolDTO();

		if (obj != null) {
			
			SimpleDateFormat df = new SimpleDateFormat("MMMM yyyy");
			Date oneDate = new Date(new java.util.Date().getTime());
			System.out.println(df.format(oneDate));

			dto.setDateFermeture(df.format(obj.getDateFermeture() ));
			dto.setDateOuverture(df.format(obj.getDateOuverture()));
			dto.setDescriptifProgramme(obj.getDescriptifProgramme());
			dto.setStatut(obj.getStatut() );
			//dto.setPresentationSansHtml(html2text (obj.getDescriptifProgramme()));
		}
		return dto;
	}
	
	
	
	
	public java.util.Date formateDateInterval(Date dt) {

		Calendar cal = Calendar.getInstance();
	    cal.setTime(dt);
	    return cal.getTime(); 
	}


	public static String html2text(String html) {
	    return Jsoup.parse(html).text();
	}
	
	public static String castJavaDateToString(Date date) {
		Locale locale = new Locale("fr", "FR");
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
		return dateFormat.format(date);
	}
	
	public static String castSQlDateToString(java.util.Date sqlDate) {
		java.util.Date today=new Date(sqlDate.getTime());		
		SimpleDateFormat simpDate = new SimpleDateFormat("MM-dd-yyyy");
		return simpDate.format(simpDate);
	}

	
	
	
	/*******  Peronne PHYSIQUE MAPPER ****/
	
	public static PersonnePhysique createPersonne(EnrollementDTO dto ) {
		PersonnePhysique p  = new  PersonnePhysique();
		
		p.setNom(dto.getBeneficiaireEnrollement().getNom());
		p.setPrenom(dto.getBeneficiaireEnrollement().getPrenom());
		p.setDateNaissaance(dto.getBeneficiaireEnrollement().getDateNaissance());
		p.setNumCNI(dto.getBeneficiaireEnrollement().getNumerocni());
		p.setSexe(dto.getBeneficiaireEnrollement().getSexe());
		p.setCivilite(dto.getBeneficiaireEnrollement().getCivilite());
	
		return p;
	}
	 
	
	/***** Adresse MAPPER ****/
	public static BienAgricoleProducteur createBienAgricol(EnrollementDTO infosEnrollement) {
		BienAgricoleProducteur bien = new BienAgricoleProducteur();
		
		bien.setCategorieBien(infosEnrollement.getCategorie());
		bien.setSuperficieBien(infosEnrollement.getSuperficieChamp());
		bien.setGpsLatitute(infosEnrollement.getLatitudeChamp() + "");
		bien.setGpsLongitude(infosEnrollement.getLongitudeChamp() + "");
		bien.setTypeDePropriete(infosEnrollement.getTypePiece());
		bien.setJustificatifPropriete(infosEnrollement.getJustiftypePiecebase64());
		
		return bien;
	}
	
	public static Contact createContactl(EnrollementDTO infosEnrollement) {
		Contact c = new Contact();
		
		c.setMobile(infosEnrollement.getTel());
		c.setCourriel(infosEnrollement.getMail());
		
		String s = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
		// set date creation
		
		return c;
	}
	
	public static String generateNumeroNUP(EnrollementDTO infosEnrollement) {
		
		String val = RandomString.digits +  RandomString.upper;
		RandomString numProd = new RandomString(12, new SecureRandom(), val);
		
		String[] tmp = numProd.toString().split("@");
		return tmp[1].toString();
	}


	public static  List<ProducteurDTO> createListProducteur(List<Producteur> lp) {
		List<ProducteurDTO> ll = new ArrayList<>();
		lp.forEach(p ->{
			
			ll.add(producteurAsProducteurDTO(p));
		});
		
		return ll;
	}
	
	
	
	
	public static ProducteurDTO producteurAsProducteurDTO(Producteur p) {
	
			ProducteurDTO dto = new ProducteurDTO();
			
			dto.setProdId(p.getProdId());
			dto.setNumeroProd(p.getNumeroProd());
			dto.setNumeroProdCarte( createCarteFormat (p.getNumeroProd()));
			dto.setDateImmatriculation(p.getDateImmatriculation()+ "");
			
			if(null != p.getTypeProd())
			{
				if(p.getTypeProd().equals("1"))
					dto.setCategorie("Producteur");
				else if (p.getTypeProd().equals("2"))
					dto.setCategorie("Gros Producteur");	
				else if (p.getTypeProd().equals("3"))
					dto.setCategorie("Regroupement / Association ");	
				else
					dto.setCategorie("N/C");	
			}
	
			
			if (p.getPersonnePhysiques().size() > 0)
			{
				dto.setNom(p.getPersonnePhysiques().stream().findFirst().get().getNom());
				dto.setPrenom(p.getPersonnePhysiques().stream().findFirst().get().getPrenom());
				dto.setDateNaissance(p.getPersonnePhysiques().stream().findFirst().get().getDateNaissaance());
				dto.setCivilite(p.getPersonnePhysiques().stream().findFirst().get().getCivilite());
				dto.setSexe(p.getPersonnePhysiques().stream().findFirst().get().getSexe());
				dto.setNumeroCNI(p.getPersonnePhysiques().stream().findFirst().get().getNumCNI());
			}
			
			if(p.getAdresseComplete() != null)
				{
				dto.setAdresse(p.getAdresseComplete().getLibelle());
				dto.setCommune(p.getAdresseComplete().getCommune().getLibelle());
				dto.setDepartement(p.getAdresseComplete().getDepartement().getLibelle());
				dto.setRegion(p.getAdresseComplete().getRegion().getLibelle());
				}
			
			if(p.getContact() != null)
			{
				dto.setEmail(p.getContact().getCourriel());
				dto.setMobile(p.getContact().getMobile());
			}
			
			
			if (p.getBiensAgricoles().size() > 0)
				{
					ArrayList<BienAgricoleProducteurDTO> biens = loadBienAgricolesProducteurDTO(p);
				dto.setListBiensAgricoles(biens);
				/*dto.setBienType(p.getBiensAgricoles().stream().findFirst().get().getCategorieBien());
				dto.setBienTitrePropriete(p.getBiensAgricoles().stream().findFirst().get().getTypeDePropriete());
				dto.setBienSuperficie(p.getBiensAgricoles().stream().findFirst().get().getSuperficieBien() + "");
				dto.setBienGPSlatitude(p.getBiensAgricoles().stream().findFirst().get().getGpsLatitute());
				dto.setBienSuperficie(p.getBiensAgricoles().stream().findFirst().get().getGpsLongitude());
				dto.get*/
				}
			
		return dto;
	}

	public static  String createCarteFormat(String numProd)
	{
		char[] words = numProd.toCharArray();
		String card= "";
		int i = 0;
        for (char c : words) {
            card += c;
            i++;
            
            if(i%4 == 0)
            	 card += "  ";
        }
        
        return card; 
	}


	public static ArrayList<BienAgricoleProducteurDTO>  loadBienAgricolesProducteurDTO(Producteur p) {
	
		Set<BienAgricoleProducteur> lb = p.getBiensAgricoles();
		ArrayList<BienAgricoleProducteurDTO> lbDto = new ArrayList<>();
		Image img = null  ;
		
		if (lb == null)
			return null; 
		
		for (BienAgricoleProducteur b : lb) {
				BienAgricoleProducteurDTO tmp = new BienAgricoleProducteurDTO();
				
				tmp.setBa_id(b.getBa_id());
				
				if(p.getTypeProd()  != null)
				{
					if(p.getTypeProd().equals("1"))
						tmp.setCategorieBien("Exploitation Individuelle");
					else if (p.getTypeProd().equals("2"))
						tmp.setCategorieBien("Exploitation industruelle");
					else if (p.getTypeProd().equals("3"))
						tmp.setCategorieBien("Groupement / Association");
					else
						tmp.setCategorieBien("N/C");
				}
			
				
				
				if(p.getAdresseComplete() != null)
				{
					tmp.setCommune(p.getAdresseComplete().getCommune().getLibelle());
					tmp.setDepartement(p.getAdresseComplete().getDepartement().getLibelle());
					tmp.setRegion(p.getAdresseComplete().getRegion().getLibelle());
					tmp.setAdresse(p.getAdresse());
				}
				
				tmp.setNumeroProd(p.getNumeroProd());
				tmp.setSuperficieBien(b.getSuperficieBien());
				tmp.setGpsLatitute(b.getGpsLatitute());
				tmp.setGpsLongitude(b.getGpsLongitude());
				
				lbDto.add(tmp);
			}
		
		
			try {
				img = generateQRCodeImage("www.google.com");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Float ff = img.getHeight();
			return lbDto	;
		
		}
	
	
	
	private static Image generateQRCodeImage(String barcodeText) throws Exception {
	    ByteArrayOutputStream stream = QRCode.from(barcodeText).withSize(125,125).stream();
	    ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());
		RenderedImage bufferedImage = ImageIO.read(bis);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "png", baos);
		Image qrCode = new Image(ImageDataFactory.create(baos.toByteArray()));
		qrCode.setHorizontalAlignment(HorizontalAlignment.RIGHT);
		
		return qrCode;
	}
	
	
}
