package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EntrepriseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long						serialVersionUID	= -2340837228206657900L;

	private EtablissementDTO						etablissementDTO;

	private List<EtablissementDTO>			etablissementSecDtos;

	private EtablissementDTO						etablissementPrincipalDTO;

	private EtablissementDTO						etablissementSecSucDTO;

	private AssocieDTO									associeDTO;

	private CollaborateurDTO								dirigeantDTO;

	private CollaborateurDTO								dirigeantDTO1;

	private CommissaireCompteDTO				commiCompteDTO;

	private CommissaireCompteDTO				commiCompteDTO1;

	private AssocieDTO									associeDTO1;

	private List<AssocieDTO>						associes;

	private List<CollaborateurDTO>					dirigeants;

	private List<CommissaireCompteDTO>	commiComptes;

	private String											id;
	private String											origine;

	private String											haveEs						= "0";										// Par
																																									// defaut
																																									// l'entreprise
																																									// n'a
																																									// pas
																																									// d'etablissement
																																									// secondaire

	private String											nomPrecedentExploitant;
	private String											prenomPrecedentExploitant;
	private String											denominationPrecedentExploitant;
	private String											numerorccmPrecedentExploitant;
	private AdresseDTO									adresseDTOPE;
	private String											nomLoueurFonds;

	private String											prenomLoueurFonds;
	private String											denominationLoueurFonds;
	private String											numerorccmLoueurFonds;
	private AdresseDTO									adresseDTOLF;
	private int													autreEtablissement;
	private String											fichierFormulaire;
	private String											idLoueurdeFond;
	private String											idPrecedentExplotant;
	private String											idEtablissementSecondaire;
	private AdresseDTO									adresseDTOES;
	private String											autreOrigine;

	private AyantDroitDTO								ayantDroitEngageantPPDTO;

	private AyantDroitDTO								ayantDroitEngageantPPDTO1;

	private List<AyantDroitDTO>					ayantDroitEngageantPersonnePhysiques;

	public EntrepriseDTO() {
		etablissementDTO = new EtablissementDTO();
		etablissementSecDtos = new ArrayList<EtablissementDTO>();
		etablissementPrincipalDTO = new EtablissementDTO();
		etablissementSecSucDTO = new EtablissementDTO();
		associeDTO = new AssocieDTO();
		associeDTO1 = new AssocieDTO();
		associes = new ArrayList<AssocieDTO>();
		dirigeantDTO = new CollaborateurDTO();
		dirigeantDTO1 = new CollaborateurDTO();
		dirigeants = new ArrayList<CollaborateurDTO>();
		commiCompteDTO = new CommissaireCompteDTO();
		commiCompteDTO1 = new CommissaireCompteDTO();
		commiComptes = new ArrayList<CommissaireCompteDTO>();
		adresseDTOLF = new AdresseDTO();
		adresseDTOPE = new AdresseDTO();
		adresseDTOES = new AdresseDTO();
		ayantDroitEngageantPPDTO = new AyantDroitDTO();
		ayantDroitEngageantPPDTO1 = new AyantDroitDTO();
		ayantDroitEngageantPersonnePhysiques = new ArrayList<AyantDroitDTO>();
	}

	public EtablissementDTO getEtablissementDTO() {
		return etablissementDTO;
	}

	public String getIdPrecedentExplotant() {
		return idPrecedentExplotant;
	}

	public void setIdPrecedentExplotant(String idPrecedentExplotant) {
		this.idPrecedentExplotant = idPrecedentExplotant;
	}

	public void setEtablissementDTO(EtablissementDTO etablissementDTO) {
		this.etablissementDTO = etablissementDTO;
	}

	public List<EtablissementDTO> getEtablissementSecDtos() {
		return etablissementSecDtos;
	}

	public void setEtablissementSecDtos(List<EtablissementDTO> etablissementSecDtos) {
		this.etablissementSecDtos = etablissementSecDtos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EtablissementDTO getEtablissementPrincipalDTO() {
		return etablissementPrincipalDTO;
	}

	public void setEtablissementPrincipalDTO(EtablissementDTO etablissementPrincipalDTO) {
		this.etablissementPrincipalDTO = etablissementPrincipalDTO;
	}

	public List<AssocieDTO> getAssocies() {
		return associes;
	}

	public void setAssocies(List<AssocieDTO> associes) {
		this.associes = associes;
	}

	public List<CollaborateurDTO> getDirigeants() {
		return dirigeants;
	}

	public void setDirigeants(List<CollaborateurDTO> dirigeants) {
		this.dirigeants = dirigeants;
	}

	public List<CommissaireCompteDTO> getCommiComptes() {
		return commiComptes;
	}

	public void setCommiComptes(List<CommissaireCompteDTO> commiComptes) {
		this.commiComptes = commiComptes;
	}

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}

	public String getNomPrecedentExploitant() {
		return nomPrecedentExploitant;
	}

	public void setNomPrecedentExploitant(String nomPrecedentExploitant) {
		this.nomPrecedentExploitant = nomPrecedentExploitant;
	}

	public String getPrenomPrecedentExploitant() {
		return prenomPrecedentExploitant;
	}

	public void setPrenomPrecedentExploitant(String prenomPrecedentExploitant) {
		this.prenomPrecedentExploitant = prenomPrecedentExploitant;
	}

	public AdresseDTO getAdresseDTOPE() {
		return adresseDTOPE;
	}

	public void setAdresseDTOPE(AdresseDTO adresseDTOPE) {
		this.adresseDTOPE = adresseDTOPE;
	}

	public String getNomLoueurFonds() {
		return nomLoueurFonds;
	}

	public void setNomLoueurFonds(String nomLoueurFonds) {
		this.nomLoueurFonds = nomLoueurFonds;
	}

	public String getIdLoueurdeFond() {
		return idLoueurdeFond;
	}

	public void setIdLoueurdeFond(String idLoueurdeFond) {
		this.idLoueurdeFond = idLoueurdeFond;
	}

	public String getDenominationLoueurFonds() {
		return denominationLoueurFonds;
	}

	public void setDenominationLoueurFonds(String denominationLoueurFonds) {
		this.denominationLoueurFonds = denominationLoueurFonds;
	}

	public AdresseDTO getAdresseDTOLF() {
		return adresseDTOLF;
	}

	public void setAdresseDTOLF(AdresseDTO adresseDTOLF) {
		this.adresseDTOLF = adresseDTOLF;
	}

	public EtablissementDTO getEtablissementSecSucDTO() {
		return etablissementSecSucDTO;
	}

	public void setEtablissementSecSucDTO(EtablissementDTO etablissementSecSucDTO) {
		this.etablissementSecSucDTO = etablissementSecSucDTO;
	}

	public int getAutreEtablissement() {
		return autreEtablissement;
	}

	public String getFichierFormulaire() {
		return fichierFormulaire;
	}

	public void setFichierFormulaire(String fichierFormulaire) {
		this.fichierFormulaire = fichierFormulaire;
	}

	public void setAutreEtablissement(int autreEtablissement) {
		this.autreEtablissement = autreEtablissement;
	}

	public String getDenominationPrecedentExploitant() {
		return denominationPrecedentExploitant;
	}

	public void setDenominationPrecedentExploitant(String denominationPrecedentExploitant) {
		this.denominationPrecedentExploitant = denominationPrecedentExploitant;
	}

	public String getNumerorccmPrecedentExploitant() {
		return numerorccmPrecedentExploitant;
	}

	public void setNumerorccmPrecedentExploitant(String numerorccmPrecedentExploitant) {
		this.numerorccmPrecedentExploitant = numerorccmPrecedentExploitant;
	}

	public String getPrenomLoueurFonds() {
		return prenomLoueurFonds;
	}

	public void setPrenomLoueurFonds(String prenomLoueurFonds) {
		this.prenomLoueurFonds = prenomLoueurFonds;
	}

	public String getNumerorccmLoueurFonds() {
		return numerorccmLoueurFonds;
	}

	public void setNumerorccmLoueurFonds(String numerorccmLoueurFonds) {
		this.numerorccmLoueurFonds = numerorccmLoueurFonds;
	}

	public void cleancacheDTO() {
		origine = "";
		nomPrecedentExploitant = "";
		prenomPrecedentExploitant = "";
		denominationPrecedentExploitant = "";
		numerorccmPrecedentExploitant = "";
		adresseDTOPE = null;
		nomLoueurFonds = "";
		prenomLoueurFonds = "";
		denominationLoueurFonds = "";
		numerorccmLoueurFonds = "";
		adresseDTOLF = null;
		autreEtablissement = 0;
		fichierFormulaire = null;

		etablissementDTO.setCapitalSocial(0);
		etablissementDTO.setCapitalNumeraire(0);
		etablissementDTO.setCapitalNature(0);
		etablissementDTO.setDuree(0);
	}

	public AssocieDTO getAssocieDTO() {
		return associeDTO;
	}

	public void setAssocieDTO(AssocieDTO associeDTO) {
		this.associeDTO = associeDTO;
	}

	public CollaborateurDTO getDirigeantDTO() {
		return dirigeantDTO;
	}

	public void setDirigeantDTO(CollaborateurDTO dirigeantDTO) {
		this.dirigeantDTO = dirigeantDTO;
	}

	public AssocieDTO getAssocieDTO1() {
		return associeDTO1;
	}

	public void setAssocieDTO1(AssocieDTO associeDTO1) {
		this.associeDTO1 = associeDTO1;
	}

	public CommissaireCompteDTO getCommiCompteDTO() {
		return commiCompteDTO;
	}

	public void setCommiCompteDTO(CommissaireCompteDTO commiCompteDTO) {
		this.commiCompteDTO = commiCompteDTO;
	}

	public CollaborateurDTO getDirigeantDTO1() {
		return dirigeantDTO1;
	}

	public void setDirigeantDTO1(CollaborateurDTO dirigeantDTO1) {
		this.dirigeantDTO1 = dirigeantDTO1;
	}

	public CommissaireCompteDTO getCommiCompteDTO1() {
		return commiCompteDTO1;
	}

	public void setCommiCompteDTO1(CommissaireCompteDTO commiCompteDTO1) {
		this.commiCompteDTO1 = commiCompteDTO1;
	}

	public String getIdEtablissementSecondaire() {
		return idEtablissementSecondaire;
	}

	public void setIdEtablissementSecondaire(String idEtablissementSecondaire) {
		this.idEtablissementSecondaire = idEtablissementSecondaire;
	}

	public AdresseDTO getAdresseDTOES() {
		return adresseDTOES;
	}

	public void setAdresseDTOES(AdresseDTO adresseDTOES) {
		this.adresseDTOES = adresseDTOES;
	}

	public String getHaveEs() {
		return haveEs;
	}

	public void setHaveEs(String haveEs) {
		this.haveEs = haveEs;
	}

	public String getAutreOrigine() {
		return autreOrigine;
	}

	public void setAutreOrigine(String autreOrigine) {
		this.autreOrigine = autreOrigine;
	}

	public AyantDroitDTO getAyantDroitEngageantPPDTO() {
		return ayantDroitEngageantPPDTO;
	}

	public void setAyantDroitEngageantPPDTO(AyantDroitDTO ayantDroitEngageantPPDTO) {
		this.ayantDroitEngageantPPDTO = ayantDroitEngageantPPDTO;
	}

	public AyantDroitDTO getAyantDroitEngageantPPDTO1() {
		return ayantDroitEngageantPPDTO1;
	}

	public void setAyantDroitEngageantPPDTO1(AyantDroitDTO ayantDroitEngageantPPDTO1) {
		this.ayantDroitEngageantPPDTO1 = ayantDroitEngageantPPDTO1;
	}

	public List<AyantDroitDTO> getAyantDroitEngageantPersonnePhysiques() {
		return ayantDroitEngageantPersonnePhysiques;
	}

	public void setAyantDroitEngageantPersonnePhysiques(List<AyantDroitDTO> ayantDroitEngageantPersonnePhysiques) {
		this.ayantDroitEngageantPersonnePhysiques = ayantDroitEngageantPersonnePhysiques;
	}

}
