package sn.awi.pgca.web.dto;

import java.util.Date;
import java.util.List;

public class FormulaireMoraleDTO extends FormulaireDTO {

	/**
	 * 
	 */
	private static final long					serialVersionUID							= -1445831068988596250L;

	private List<EnteteFormulaireDTO>	listEnteteFormulaire;

	private Long											idEnteteFormulaire;

	private Long											idDonneesFormulaire;

	private String										numerorccm;

	private String										fichierFormulaireM0Bis;

	private AssocieDTO								associeDTO;

	private CollaborateurDTO							dirigeantDTO;

	private CommissaireCompteDTO			commissaireCompteDTO;

	private String										numerorccmEtablissementPrincipal;

	private Date											dateEnregistrement;

	private String										fichierAccuseEnregistrement;

	private GedAbstractDocumentDTO		gedAbstractDocumentDTOFormulaireM0;

	private GedAbstractDocumentDTO		gedAbstractDocumentDTOFormulaireM0Bis;

	public AssocieDTO getAssocieDTO() {
		return associeDTO;
	}

	public void setAssocieDTO(AssocieDTO associeDTO) {
		this.associeDTO = associeDTO;
	}

	public FormulaireMoraleDTO() {
		super();
		associeDTO = new AssocieDTO();
		dirigeantDTO = new CollaborateurDTO();
		commissaireCompteDTO = new CommissaireCompteDTO();
	}

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

	public String getNumerorccm() {
		return numerorccm;
	}

	public void setNumerorccm(String numerorccm) {
		this.numerorccm = numerorccm;
	}

	public List<EnteteFormulaireDTO> getListEnteteFormulaire() {
		return listEnteteFormulaire;
	}

	public void setListEnteteFormulaire(List<EnteteFormulaireDTO> listEnteteFormulaire) {
		this.listEnteteFormulaire = listEnteteFormulaire;
	}

	public String getNumerorccmEtablissementPrincipal() {
		return numerorccmEtablissementPrincipal;
	}

	public void setNumerorccmEtablissementPrincipal(String numerorccmEtablissementPrincipal) {
		this.numerorccmEtablissementPrincipal = numerorccmEtablissementPrincipal;
	}

	public CollaborateurDTO getDirigeantDTO() {
		return dirigeantDTO;
	}

	public void setDirigeantDTO(CollaborateurDTO dirigeantDTO) {
		this.dirigeantDTO = dirigeantDTO;
	}

	public CommissaireCompteDTO getCommissaireCompteDTO() {
		return commissaireCompteDTO;
	}

	public void setCommissaireCompteDTO(CommissaireCompteDTO commissaireCompteDTO) {
		this.commissaireCompteDTO = commissaireCompteDTO;
	}

	public String getFichierAccuseEnregistrement() {
		return fichierAccuseEnregistrement;
	}

	public void setFichierAccuseEnregistrement(String fichierAccuseEnregistrement) {
		this.fichierAccuseEnregistrement = fichierAccuseEnregistrement;
	}

	public Date getDateEnregistrement() {
		return dateEnregistrement;
	}

	public void setDateEnregistrement(Date dateEnregistrement) {
		this.dateEnregistrement = dateEnregistrement;
	}

	public String getFichierFormulaireM0Bis() {
		return fichierFormulaireM0Bis;
	}

	public void setFichierFormulaireM0Bis(String fichierFormulaireM0Bis) {
		this.fichierFormulaireM0Bis = fichierFormulaireM0Bis;
	}

	public GedAbstractDocumentDTO getGedAbstractDocumentDTOFormulaireM0() {
		return gedAbstractDocumentDTOFormulaireM0;
	}

	public void setGedAbstractDocumentDTOFormulaireM0(GedAbstractDocumentDTO gedAbstractDocumentDTOFormulaireM0) {
		this.gedAbstractDocumentDTOFormulaireM0 = gedAbstractDocumentDTOFormulaireM0;
	}

	public GedAbstractDocumentDTO getGedAbstractDocumentDTOFormulaireM0Bis() {
		return gedAbstractDocumentDTOFormulaireM0Bis;
	}

	public void setGedAbstractDocumentDTOFormulaireM0Bis(GedAbstractDocumentDTO gedAbstractDocumentDTOFormulaireM0Bis) {
		this.gedAbstractDocumentDTOFormulaireM0Bis = gedAbstractDocumentDTOFormulaireM0Bis;
	}
	

}
