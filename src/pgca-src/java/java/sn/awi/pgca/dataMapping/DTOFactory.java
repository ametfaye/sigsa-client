package sn.awi.pgca.dataMapping;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.jfree.util.Log;

import sn.awi.pgca.dataModel.Adresse;
import sn.awi.pgca.dataModel.Contact;
import sn.awi.pgca.dataModel.Pays;
import sn.awi.pgca.dataModel.PointDeCollecte;
import sn.awi.pgca.dataModel.Profil;
import sn.awi.pgca.dataModel.Region;
import sn.awi.pgca.dataModel.Utilisateur;
import sn.awi.pgca.utils.DateUtils;
import sn.awi.pgca.utils.UtilString;
import sn.awi.pgca.web.dto.AdresseDTO;
import sn.awi.pgca.web.dto.CollaborateurDTO;
import sn.awi.pgca.web.dto.ContactDTO;
import sn.awi.pgca.web.dto.CoupleDTO;
import sn.awi.pgca.web.dto.EntiteJuridiqueDTO;
import sn.awi.pgca.web.dto.ProfilDTO;
import sn.awi.pgca.web.dto.UtilisateurDTO;

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
					Log.error("Argument reflection JAVA  par la JVM:  Id ou Libelle ") ;
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					Log.error("L'acces a l'attribut par  par reflection JAVA est internet par la JVM:  Id ou Libelle ");
					e.printStackTrace();
				}
				
			} catch (NoSuchFieldException e) {
				Log.error("L'attribut a recuperer par reflection JAVA n'existe pas :  Id ou Libelle seulement  < " + keyNAme + " et "  + valueName + "> donné en parametre" );
				e.printStackTrace();
			} catch (SecurityException e) {
				Log.error("L'acces a l'attribut par  par reflection JAVA est internet par la JVM:  Id ou Libelle ");
				e.printStackTrace();
			}
			
		}
		return listCoupleDTOGenerics;
	}

	

	
	
	public List<ProfilDTO> createListProfilDTO(List<Profil> lProfil) {
		List<ProfilDTO> lProfilDTO = new ArrayList<ProfilDTO>();
		for (Profil profil : lProfil) {
			ProfilDTO c = createProfilDTO(profil, false, false);
			if (c != null)
				lProfilDTO.add(c);
		}
		return lProfilDTO;
	}

	public List<UtilisateurDTO> createListUtilisateurDTO(List<Utilisateur> lUtilisateur) {
		List<UtilisateurDTO> lUtilisateurDTO = new ArrayList<UtilisateurDTO>();
		for (Utilisateur utilisateur : lUtilisateur) {
			UtilisateurDTO c = createUtilisateurDTO(utilisateur);
			if (c != null)
				lUtilisateurDTO.add(c);
		}
		return lUtilisateurDTO;
	}



	public List<EntiteJuridiqueDTO> createListEntiteJuridiqueDTO(List<PointDeCollecte> lEntiteJuridique) {
		List<EntiteJuridiqueDTO> lEntiteJuridiqueDTO = new ArrayList<EntiteJuridiqueDTO>();
		for (PointDeCollecte entiteJuridique : lEntiteJuridique) {
			EntiteJuridiqueDTO c = createEntiteJuridiqueDTO(entiteJuridique);
			if (c != null)
				lEntiteJuridiqueDTO.add(c);
		}
		return lEntiteJuridiqueDTO;
	}

	public ProfilDTO createProfilDTO(Profil profil, boolean addAction, boolean addMenu) {
		if (profil == null)
			return null;
		ProfilDTO profilDTO = new ProfilDTO();
		profilDTO.setId(profil.getId());
		profilDTO.setCode(profil.getCode());
		profilDTO.setLibelle(profil.getLibelle());
		//profilDTO.setCodeEj(profil.getEntiteJuridique().getId());
		// actions

		return profilDTO;
	}

	public ProfilDTO createProfilDTO(Profil profil) {
		return createProfilDTO(profil, true, true);
	}

	public UtilisateurDTO createUtilisateurDTO(Utilisateur utilisateur) {
		if (utilisateur == null)
			return null;

		UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
		utilisateurDTO.setId(utilisateur.getId());
		utilisateurDTO.setEmail(utilisateur.getEmail());
		utilisateurDTO.setCode(utilisateur.getCodeutilisateur());
		utilisateurDTO.setPassword(utilisateur.getMotdepasse());
		utilisateurDTO.setNom(utilisateur.getPersonne().getNom());
		utilisateurDTO.setPrenom(utilisateur.getPersonne().getPrenom());
		utilisateurDTO.setNationalite(utilisateur.getPersonne().getNationalite());
		utilisateurDTO.setLieudenaissance(utilisateur.getPersonne().getLieudenaissance());
		utilisateurDTO.setSituationmat(utilisateur.getPersonne().getSituationmat());
		utilisateurDTO.setDatedenaissance(utilisateur.getPersonne().getDatenaissance());
		utilisateurDTO.setDateCreation(utilisateur.getDateCreation());
		utilisateurDTO.setDateDerniereConnexion(utilisateur.getDateDerniereConnexion());
		utilisateurDTO.setNbConnexionReussi(utilisateur.getNombredeConnexion());
		utilisateurDTO.setDateDerniereModificationMDP(utilisateur.getDateDerniereModificationMDP());
		

		
		// TODO : a extanliser  dans les CONST
		if(utilisateur.isEst_valide())
		{
			utilisateurDTO.setStatutUser("Actif");
			utilisateurDTO.setStatusUserIcon("fa fa-unlock fa-2x");
			utilisateurDTO.setStatusUserIconColor("#7fc347"); 
		}
		else
		{
			utilisateurDTO.setStatutUser("Vérouillé");
			utilisateurDTO.setStatusUserIcon("fa fa-lock fa-2x");
			utilisateurDTO.setStatusUserIconColor("red"); 
			
		}
		
		if (utilisateur.getPersonne().getDatenaissance() != null) {
			utilisateurDTO.setStrDatedenaissance(DateUtils.formatDateAAAAMMDD(utilisateur.getPersonne().getDatenaissance()));
			utilisateurDTO.setStrDatedenaissance2(DateUtils.formatDate(utilisateur.getPersonne().getDatenaissance()));
		}

		if (utilisateur.getPointdeCollecte() != null) {
			CoupleDTO ejDTO = new CoupleDTO( utilisateur.getPointdeCollecte().getId().longValue(), utilisateur.getPointdeCollecte().getLibelle());
			utilisateurDTO.setEntiteJuridiqueDTO(ejDTO);
			utilisateurDTO.setEntiteJuridiqueId("" + utilisateur.getPointdeCollecte().getId().longValue());
		}
		
		if (utilisateur.getPointdeVente() != null) {
			CoupleDTO ejDTO = new CoupleDTO( utilisateur.getPointdeVente().getId().longValue(), utilisateur.getPointdeVente().getLibelle());
			utilisateurDTO.setEntiteJuridiqueDTO(ejDTO);
			utilisateurDTO.setEntiteJuridiqueId("" + utilisateur.getPointdeVente().getId().longValue());
		}
		
		if (utilisateur.getProfil() != null) {
			CoupleDTO pDTO = new CoupleDTO(utilisateur.getProfil().getId().longValue(), utilisateur.getProfil().getLibelle());
			utilisateurDTO.setProfilDTO(pDTO);
			utilisateurDTO.setProfilId("" + utilisateur.getProfil().getId().longValue());
			
			

			if(utilisateur.getProfil().getId() == 5)
				utilisateurDTO.setEntiteJuridiqueDTO(  new CoupleDTO(0L, "Zone d'intervention multiple"));
			
		}
		
		if(utilisateur.getPersonne().getAdresse() != null   && utilisateur.getPersonne().getAdresse().getCommune() != null)
			utilisateurDTO.setAdresse(utilisateur.getPersonne().getAdresse().getCommune().getLibelle());
		
		if(utilisateur.getPersonne().getContact() != null )
			utilisateurDTO.setTel(utilisateur.getPersonne().getContact().getMobile());
		
		
		return utilisateurDTO;
	}



	public EntiteJuridiqueDTO createEntiteJuridiqueDTO(PointDeCollecte entiteJuridique) {
		if (entiteJuridique == null)
			return null;

		EntiteJuridiqueDTO entiteJuridiqueDTO = new EntiteJuridiqueDTO();

		entiteJuridiqueDTO.setId(entiteJuridique.getId());
		//entiteJuridiqueDTO.setLabelType(entiteJuridique.getTypeEntiteJuridique().getLibelle());
		entiteJuridiqueDTO.setLibelle(entiteJuridique.getLibelle());
		entiteJuridiqueDTO.setLabelRegion(entiteJuridique.getAdresse().getRegion().getLibelle());
		entiteJuridiqueDTO.setLabelPays(entiteJuridique.getAdresse().getRegion().getPays().getLibelle());
		entiteJuridiqueDTO.setIdRegion("" + entiteJuridique.getAdresse().getRegion().getId().longValue());
		entiteJuridiqueDTO.setIdPays("" + entiteJuridique.getAdresse().getRegion().getPays().getId().longValue());
		//entiteJuridiqueDTO.setIdType("" + entiteJuridique.getTypeEntiteJuridique().getId().longValue());

		if (entiteJuridique.getAdresse() != null) {
			AdresseDTO adto = new AdresseDTO();
			adto.setLibelle(entiteJuridique.getAdresse().getLibelle());
			adto.setCodepostal(entiteJuridique.getAdresse().getCodepostal());
			adto.setQuartier(entiteJuridique.getAdresse().getQuartier());
			adto.setVille(entiteJuridique.getAdresse().getVille());
			entiteJuridiqueDTO.setAdresse(adto);
			entiteJuridiqueDTO.setAdresseId("" + entiteJuridique.getAdresse().getId().longValue());
		}

		if (entiteJuridique.getContact() != null) {
			ContactDTO cdto = new ContactDTO();
			chargerContactDTO(entiteJuridique.getContact(), cdto);
			entiteJuridiqueDTO.setContact(cdto);
			entiteJuridiqueDTO.setContactId("" + entiteJuridique.getAdresse().getId().longValue());
		}

		return entiteJuridiqueDTO;
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

	public List<CoupleDTO> createEntiteJuridiqueListCoupleDTO(List<PointDeCollecte> lEntiteJuridiques) {
		List<CoupleDTO> listEntiteJuridiquesDTOs = new ArrayList<CoupleDTO>();
		for (PointDeCollecte entiteJuridique : lEntiteJuridiques) {
			CoupleDTO coupleDTO = new CoupleDTO(entiteJuridique.getId().longValue(), entiteJuridique.getLibelle());
			listEntiteJuridiquesDTOs.add(coupleDTO);
		}
		return listEntiteJuridiquesDTOs;
	}


	public List<CoupleDTO> createRegionCoupleDTO(List<Region> lRegions) {
		List<CoupleDTO> listRegionDTOs = new ArrayList<CoupleDTO>();
		for (Region region : lRegions) {
			CoupleDTO coupleDTO = new CoupleDTO(region.getId().longValue(), region.getLibelle());
			listRegionDTOs.add(coupleDTO);
		}
		return listRegionDTOs;
	}






	public List<CoupleDTO> createProfilListCoupleDTO(List<Profil> lProfils) {
		List<CoupleDTO> listProfilsDTOs = new ArrayList<CoupleDTO>();
		for (Profil profil : lProfils) {
			CoupleDTO coupleDTO = new CoupleDTO(profil.getId().longValue(), profil.getLibelle());
			listProfilsDTOs.add(coupleDTO);
		}
		return listProfilsDTOs;
	}





	public void chargerAdresse(AdresseDTO adresseDTO, Adresse adresse) {
		// clean adress

		if (adresse == null) {
			adresseDTO.setIdRegion("1");
			adresseDTO.setIdPays("1");
			Log.error("l'adresse   est null , pas la peine d 'essayer de pre remplir les champs adress------");
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
		if (UtilString.equal("M", situationmat))
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


	public List<CollaborateurDTO> createlisteCollaborteurDTOs(List<Utilisateur> coll) {
		List<CollaborateurDTO> listeCollaborateur = new ArrayList<CollaborateurDTO>();
		
		for (Utilisateur utilisateur : coll) {
			CollaborateurDTO c =  new CollaborateurDTO();
			{
				
				c.setNom(utilisateur.getPersonne().getPrenom()
						+ " " + utilisateur.getPersonne().getNom());
				
				c.setIdPersonne(utilisateur.getId());
				// Gerant Point de Vente ou Point de collecte
				/*if (utilisateur.getPointdeVente() != null && utilisateur.getPointdeVente().getGerant() !=  null)
				    c.setIdPersonne(utilisateur.getPointdeVente().getGerant().getId()) ;
				else if (utilisateur.getPointdeCollecte() != null && utilisateur.getPointdeCollecte().getGerant() !=  null)
					 c.setIdPersonne(utilisateur.getPointdeCollecte().getGerant().getId()) ;*/
				
			    c.setFonction(utilisateur.getCodeutilisateur());
				listeCollaborateur.add(c);
			}
	
		}
		return listeCollaborateur;
	}


}
