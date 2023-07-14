package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImmatriculationPersonneMoraleDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3228382028997218524L;
	
	private long id;

	private Long idEnteteFormulaire;
	
	private Long idDonneesFormulaire;
	
	private String idFormulaire;
	
	private String numerorccm;
	
	private String numeroordre;
	
	private String idTypeDemande;

	private String idEntiteJuridique;
	
	private InfosGeneralesPersonneMoraleDTO infoGenePMDTO;

	private MandataireDTO mandataireDTO;
	
	private EtablissementDTO etablissementDTO;
	
	private String autreEtablissement = "";
	
	private EtablissementDTO etablissementSecSucDTO;
	
	private List<AssocieDTO> associes;

	private AssocieDTO associeDTO;
	
	private AssocieDTO associeDTO1;
	
	private List<CollaborateurDTO> dirigeants;

	private CollaborateurDTO dirigeantDTO;
	
	private CollaborateurDTO dirigeantDTO1;
	
	private List<CommissaireCompteDTO> commiComptes;

	private CommissaireCompteDTO commiCompteDTO;
	
	private CommissaireCompteDTO commiCompteDTO1;
	
	public Long getIdEnteteFormulaire() {
		return idEnteteFormulaire;
	}

	public void setIdEnteteFormulaire(Long idEnteteFormulaire) {
		this.idEnteteFormulaire = idEnteteFormulaire;
	}

	public Long getIdDonneesFormulaire() {
		return idDonneesFormulaire;
	}

	public void setIdDonneesFormulaire(Long idDonneesFormulaire) {
		this.idDonneesFormulaire = idDonneesFormulaire;
	}

	public ImmatriculationPersonneMoraleDTO() {
		mandataireDTO = new MandataireDTO();
		infoGenePMDTO = new InfosGeneralesPersonneMoraleDTO();
		etablissementDTO = new EtablissementDTO();
		etablissementSecSucDTO = new EtablissementDTO();
		associeDTO = new AssocieDTO();
		dirigeantDTO = new CollaborateurDTO();
		commiCompteDTO = new CommissaireCompteDTO();
		associes = new ArrayList<AssocieDTO>();
		dirigeants = new ArrayList<CollaborateurDTO>();
		commiComptes = new ArrayList<CommissaireCompteDTO>();
	}
	
	public String getIdFormulaire() {
		return idFormulaire;
	}

	public void setIdFormulaire(String idFormulaire) {
		this.idFormulaire = idFormulaire;
	}

	public String getNumerorccm() {
		return numerorccm;
	}

	public void setNumerorccm(String numerorccm) {
		this.numerorccm = numerorccm;
	}

	public String getNumeroordre() {
		return numeroordre;
	}

	public void setNumeroordre(String numeroordre) {
		this.numeroordre = numeroordre;
	}

	public String getIdTypeDemande() {
		return idTypeDemande;
	}

	public void setIdTypeDemande(String idTypeDemande) {
		this.idTypeDemande = idTypeDemande;
	}
	
	public String getIdEntiteJuridique() {
		return idEntiteJuridique;
	}

	public void setIdEntiteJuridique(String idEntiteJuridique) {
		this.idEntiteJuridique = idEntiteJuridique;
	}

	public InfosGeneralesPersonneMoraleDTO getInfoGenePMDTO() {
		return infoGenePMDTO;
	}

	public void setInfoGenePMDTO(InfosGeneralesPersonneMoraleDTO infoGenePMDTO) {
		this.infoGenePMDTO = infoGenePMDTO;
	}

	public MandataireDTO getMandataireDTO() {
		if (mandataireDTO ==null)
			mandataireDTO = new MandataireDTO();
		return mandataireDTO;
	}

	public void setMandataireDTO(MandataireDTO mandataireDTO) {
		this.mandataireDTO = mandataireDTO;
	}

	public EtablissementDTO getEtablissementDTO() {
		if (etablissementDTO == null)
			etablissementDTO = new EtablissementDTO();
		return etablissementDTO;
	}

	public void setEtablissementDTO(EtablissementDTO etablissementDTO) {
		this.etablissementDTO = etablissementDTO;
	}

	public String getAutreEtablissement() {
		return autreEtablissement;
	}

	public void setAutreEtablissement(String autreEtablissement) {
		this.autreEtablissement = autreEtablissement;
	}

	public EtablissementDTO getEtablissementSecSucDTO() {
		if (etablissementSecSucDTO == null)
			etablissementSecSucDTO = new EtablissementDTO();
		return etablissementSecSucDTO;
	}

	public void setEtablissementSecSucDTO(EtablissementDTO etablissementSecSucDTO) {
		this.etablissementSecSucDTO = etablissementSecSucDTO;
	}

	public List<AssocieDTO> getAssocies() {
		return associes;
	}

	public void setAssocies(List<AssocieDTO> associes) {
		this.associes = associes;
	}

	public AssocieDTO getAssocieDTO() {
		if (associeDTO==null)
			associeDTO = new AssocieDTO();
		return associeDTO;
	}

	public void setAssocieDTO(AssocieDTO associeDTO) {
		this.associeDTO = associeDTO;
	}

	public List<CollaborateurDTO> getDirigeants() {
		return dirigeants;
	}

	public void setDirigeants(List<CollaborateurDTO> dirigeants) {
		this.dirigeants = dirigeants;
	}

	public CollaborateurDTO getDirigeantDTO() {
		if (dirigeantDTO ==null)
			dirigeantDTO = new CollaborateurDTO();
		return dirigeantDTO;
	}

	public void setDirigeantDTO(CollaborateurDTO dirigeantDTO) {
		this.dirigeantDTO = dirigeantDTO;
	}

	public List<CommissaireCompteDTO> getCommiComptes() {
		return commiComptes;
	}

	public void setCommiComptes(List<CommissaireCompteDTO> commiComptes) {
		this.commiComptes = commiComptes;
	}

	public CommissaireCompteDTO getCommiCompteDTO() {
		if (commiCompteDTO==null)
			commiCompteDTO = new CommissaireCompteDTO();
		return commiCompteDTO;
	}

	public void setCommiCompteDTO(CommissaireCompteDTO commiCompte) {
		this.commiCompteDTO = commiCompte;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public AssocieDTO getAssocieDTO1() {
		if (associeDTO1==null)
			associeDTO1 = new AssocieDTO();
		return associeDTO1;
	}

	public void setAssocieDTO1(AssocieDTO associeDTO1) {
		this.associeDTO1 = associeDTO1;
	}

	public CollaborateurDTO getDirigeantDTO1() {
		if (dirigeantDTO1==null)
			dirigeantDTO1 = new CollaborateurDTO();
		return dirigeantDTO1;
	}

	public void setDirigeantDTO1(CollaborateurDTO dirigeantDTO1) {
		this.dirigeantDTO1 = dirigeantDTO1;
	}

	public CommissaireCompteDTO getCommiCompteDTO1() {
		if (commiCompteDTO1==null)
			commiCompteDTO1 = new CommissaireCompteDTO();
		return commiCompteDTO1;
	}

	public void setCommiCompteDTO1(CommissaireCompteDTO commiCompteDTO1) {
		this.commiCompteDTO1 = commiCompteDTO1;
	}
	
}
