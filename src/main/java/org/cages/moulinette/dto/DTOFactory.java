package org.cages.moulinette.dto;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.cages.moulinette.model.Adresse;
import org.cages.moulinette.model.Contact;
import org.cages.moulinette.model.Pays;

@Named("dtoFactory")
public class DTOFactory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5677080281573106860L;
	// private static final Log LOG = LogFactory.getLog(DTOFactory.class);
	//
	// private static String YES = "true";
	// private static String NO = "false";
	SimpleDateFormat	sdf	= new SimpleDateFormat("dd/MM/yyyy");

	public DTOFactory() {
		super();
	}

	
	/******
	 *   Methode generique de recuperation  de tous type d'objets par REflection JAVA
	 *   Cette methode utilise la reflection Java pour recuperer la valeur des attributs keyNAme & valueName
	 *   de n'importe quel classe JAVA pour en créer un CoupleDTO
	 *   
	 * @param listOfGenericsObject  
	 * @param keyNAme
	 * @param valueName
	 * @return
	 */

	public List<CoupleDTO> createGenericCoupleDTO(List<?> listOfGenericsObject , String keyNAme , String valueName) {
		
		Field key; 
		Field val;
		
		List<CoupleDTO> listCoupleDTOGenerics = new ArrayList<CoupleDTO>();
		for (Object instance : listOfGenericsObject) {
			
			try {
				try {
					key =  instance.getClass().getDeclaredField(keyNAme);
					key.setAccessible(true);
					Long keyData = (Long) key.get(instance);
					
					val =  instance.getClass().getDeclaredField(valueName);
					val.setAccessible(true);
					String valData = val.get(instance).toString();
					
					CoupleDTO coupleDTO = new CoupleDTO(keyData , valData);
					listCoupleDTOGenerics.add(coupleDTO);
				} catch (IllegalArgumentException e) {
					//Log.error("Argument reflection JAVA  par la JVM:  Id ou Libelle ") ;
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					//Log.error("L'acces a l'attribut par  par reflection JAVA est internet par la JVM:  Id ou Libelle ");
					e.printStackTrace();
				}
				
			} catch (NoSuchFieldException e) {
				//Log.error("L'attribut a recuperer par reflection JAVA n'existe pas :  Id ou Libelle seulement  < " + keyNAme + " et "  + valueName + "> donné en parametre" );
				e.printStackTrace();
			} catch (SecurityException e) {
				//Log.error("L'acces a l'attribut par  par reflection JAVA est internet par la JVM:  Id ou Libelle ");
				e.printStackTrace();
			}
			
		}
		return listCoupleDTOGenerics;
	}

	





	public void chargerContactDTO(Contact obj, ContactDTO cdto) {
		cdto.setCourriel(obj.getCourriel());
		cdto.setFax(obj.getFax());
		cdto.setFixe(obj.getFixe());
		cdto.setLibelle(obj.getLibelle());
		cdto.setMobile(obj.getMobile());
		cdto.setSite(obj.getSite_web());
	}

	public List<CoupleDTO> createPaysListCoupleDTO(List<Pays> lPays) {
		List<CoupleDTO> listPaysDTOs = new ArrayList<CoupleDTO>();
		for (Pays pays : lPays) {
			CoupleDTO coupleDTO = new CoupleDTO( pays.getId().longValue(), pays.getLibelle());
			listPaysDTOs.add(coupleDTO);
		}
		return listPaysDTOs;
	}

	




	public void chargerAdresse(AdresseDTO adresseDTO, Adresse adresse) {
		// clean adress

		if (adresse == null) {
			adresseDTO.setIdRegion("1");
			adresseDTO.setIdPays("1");
			//Log.error("l'adresse   est null , pas la peine d 'essayer de pre remplir les champs adress------");
			return;
		}
		adresseDTO.setId(adresse.getId().longValue() + "");
		adresseDTO.setCodepostal(adresse.getCodepostal());
		adresseDTO.setLibelle(adresse.getLibelle());
		adresseDTO.setQuartier(adresse.getQuartier());
		adresseDTO.setVille(adresse.getVille());
		if (adresse.getRegion() != null) {
			adresseDTO.setIdRegion(adresse.getRegion().getId().toString());
			adresseDTO.setIdPays(adresse.getRegion().getPays().getId().toString());
		}
	}


	// C : Célibataire M : Marié D : Divorcé V : Veuf
	public String getLibelleSituationMat(String situationmat, char civilite) {
	/*	if (UtilString.equal("M", situationmat))
			if (civilite == '1')
				return "Marié";
			else
				return "Mariée";
		else if (UtilString.equal("C", situationmat))
			return "Célibataire";
		else if (UtilString.equal("D", situationmat))
			if (civilite == '1')
				return "Divorcé";
			else
				return "Divorcée";
		else if (UtilString.equal("V", situationmat))
			if (civilite == '1')
				return "Veuf";
			else
				return "Veuve";
		else
			return "";*/
		return "";
	}

	// 1 : Mr 2 : Mme 3 : Mlle
	public String getLibelleCivite(char civilite) {
		switch (civilite) {
		case '1':
			return "Mr";
		case '2':
			return "Mme";
		case '3':
			return "Mlle";
		default:
			return "Inconnu";
		}
	}

	public String getLibelleSituationMat(char civilite) {
		switch (civilite) {
		case 'C':
			return "Célibataire";
		case 'M':
			return "Marié(e)";
		case 'D':
			return "Divorcé(e)";
		case 'V':
			return "Veuf(ve)";
		default:
			return "Inconnu";
		}
	}

	// 1 : monogamie 2 : polygamie
	public String getLibelleOptionMat(char civilite) {
		switch (civilite) {
		case '1':
			return "Monogamie";
		case '2':
			return "Polygamie";
		default:
			return "Inconnu";
		}
	}

	// 1 : Séparation de biens, 2 : régime dotal 3 : la participation aux meubles
	// et acquêts
	public String getLibelleRegimeMat(char civilite) {
		switch (civilite) {
		case '1':
			return "Séparation de biens";
		case '2':
			return "Régime dotal";
		case '3':
			return "Participation aux meubles et acquêts";
		default:
			return "Inconnu";
		}
	}





}
