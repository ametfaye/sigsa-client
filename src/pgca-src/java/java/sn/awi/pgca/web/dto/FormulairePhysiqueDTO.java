package sn.awi.pgca.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sn.awi.pgca.utils.UtilString;

public class FormulairePhysiqueDTO extends FormulaireDTO {

	/**
	 * 
	 */
	private static final long				serialVersionUID											= -6563966986700882580L;

	private String									numeroRCCM;

	private PersonneDTO							personneDTO;

	private AdresseDTO							adressePostaleDTO;

	private AdresseDTO							adresseDomicileDTO;

	private ContactDTO							contactDTO;

	private String									fichierAccuseEnregistrement;

	private Date										dateEnregistrement;

	private String									fichierExtraitNaisOuPieceJustif;

	private boolean									fichierExtraitNaisOuPieceJustifExiste	= false;

	private String									fichierActeMariage;

	private boolean									fichierActeMariageExiste							= false;

	private String									fichierDeclHonneur;

	private boolean									fichierDeclHonneurExiste							= false;

	private String									fichierCertificatResidence;

	private boolean									fichierCertificatResidenceExiste			= false;

	private String									fichierTitreOccupationEts;

	private boolean									fichierTitreOccupationEtsExiste				= false;

	private String									fichierTitreAcqOuLocGerance;

	private boolean									fichierTitreAcqOuLocGeranceExiste			= false;

	private String									fichierAutorExeciceCommerce;

	private boolean									fichierAutorExeciceCommerceExiste			= false;

	private ConjointDTO							conjointDTO;

	private ConjointDTO							conjointDTO1;

	private GedAbstractDocumentDTO	gedAbstractDocumentDTOFormulaireP1;

	private GedAbstractDocumentDTO	gedAbstractDocumentDTOFormulaireP1Bis;

	private List<ConjointDTO>				conjoints;

	public FormulairePhysiqueDTO() {
		super();
		personneDTO = new PersonneDTO();
		adressePostaleDTO = new AdresseDTO();
		adresseDomicileDTO = new AdresseDTO();
		contactDTO = new ContactDTO();
		conjointDTO = new ConjointDTO();
		conjointDTO1 = new ConjointDTO();
		conjoints = new ArrayList<ConjointDTO>();

	}

	public PersonneDTO getPersonneDTO() {
		return personneDTO;
	}

	public void setPersonneDTO(PersonneDTO personneDTO) {
		this.personneDTO = personneDTO;
	}

	public AdresseDTO getAdressePostaleDTO() {
		return adressePostaleDTO;
	}

	public void setAdressePostaleDTO(AdresseDTO adressePostaleDTO) {
		this.adressePostaleDTO = adressePostaleDTO;
	}

	public AdresseDTO getAdresseDomicileDTO() {
		return adresseDomicileDTO;
	}

	public void setAdresseDomicileDTO(AdresseDTO adresseDomicileDTO) {
		this.adresseDomicileDTO = adresseDomicileDTO;
	}

	public ContactDTO getContactDTO() {
		return contactDTO;
	}

	public void setContactDTO(ContactDTO contactDTO) {
		this.contactDTO = contactDTO;
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

	public String getNumeroRCCM() {
		return numeroRCCM;
	}

	public void setNumeroRCCM(String numeroRCCM) {
		this.numeroRCCM = numeroRCCM;
	}

	public String getFichierExtraitNaisOuPieceJustif() {
		return fichierExtraitNaisOuPieceJustif;
	}

	public void setFichierExtraitNaisOuPieceJustif(String fichierExtraitNaisOuPieceJustif) {
		this.fichierExtraitNaisOuPieceJustif = fichierExtraitNaisOuPieceJustif;
		setFichierExtraitNaisOuPieceJustifExiste(UtilString.isCorrect(fichierExtraitNaisOuPieceJustif));
	}

	public boolean isFichierExtraitNaisOuPieceJustifExiste() {
		return fichierExtraitNaisOuPieceJustifExiste;
	}

	public void setFichierExtraitNaisOuPieceJustifExiste(boolean fichierExtraitNaisOuPieceJustifExiste) {
		this.fichierExtraitNaisOuPieceJustifExiste = fichierExtraitNaisOuPieceJustifExiste;
	}

	public String getFichierActeMariage() {
		return fichierActeMariage;
	}

	public void setFichierActeMariage(String fichierActeMariage) {
		this.fichierActeMariage = fichierActeMariage;
		setFichierActeMariageExiste(UtilString.isCorrect(fichierActeMariage));
	}

	public boolean isFichierActeMariageExiste() {
		return fichierActeMariageExiste;
	}

	public void setFichierActeMariageExiste(boolean fichierActeMariageExiste) {
		this.fichierActeMariageExiste = fichierActeMariageExiste;
	}

	public String getFichierDeclHonneur() {
		return fichierDeclHonneur;
	}

	public void setFichierDeclHonneur(String fichierDeclHonneur) {
		this.fichierDeclHonneur = fichierDeclHonneur;
		setFichierDeclHonneurExiste(UtilString.isCorrect(fichierDeclHonneur));
	}

	public boolean isFichierDeclHonneurExiste() {
		return fichierDeclHonneurExiste;
	}

	public void setFichierDeclHonneurExiste(boolean fichierDeclHonneurExiste) {
		this.fichierDeclHonneurExiste = fichierDeclHonneurExiste;
	}

	public String getFichierCertificatResidence() {
		return fichierCertificatResidence;
	}

	public void setFichierCertificatResidence(String fichierCertificatResidence) {
		this.fichierCertificatResidence = fichierCertificatResidence;
		setFichierCertificatResidenceExiste(UtilString.isCorrect(fichierCertificatResidence));
	}

	public boolean isFichierCertificatResidenceExiste() {
		return fichierCertificatResidenceExiste;
	}

	public void setFichierCertificatResidenceExiste(boolean fichierCertificatResidenceExiste) {
		this.fichierCertificatResidenceExiste = fichierCertificatResidenceExiste;
	}

	public String getFichierTitreOccupationEts() {
		return fichierTitreOccupationEts;
	}

	public void setFichierTitreOccupationEts(String fichierTitreOccupationEts) {
		this.fichierTitreOccupationEts = fichierTitreOccupationEts;
		setFichierTitreOccupationEtsExiste(UtilString.isCorrect(fichierTitreOccupationEts));
	}

	public boolean isFichierTitreOccupationEtsExiste() {
		return fichierTitreOccupationEtsExiste;
	}

	public void setFichierTitreOccupationEtsExiste(boolean fichierTitreOccupationEtsExiste) {
		this.fichierTitreOccupationEtsExiste = fichierTitreOccupationEtsExiste;
	}

	public String getFichierTitreAcqOuLocGerance() {
		return fichierTitreAcqOuLocGerance;
	}

	public void setFichierTitreAcqOuLocGerance(String fichierTitreAcqOuLocGerance) {
		this.fichierTitreAcqOuLocGerance = fichierTitreAcqOuLocGerance;
		setFichierTitreAcqOuLocGeranceExiste(UtilString.isCorrect(fichierTitreAcqOuLocGerance));
	}

	public boolean isFichierTitreAcqOuLocGeranceExiste() {
		return fichierTitreAcqOuLocGeranceExiste;
	}

	public void setFichierTitreAcqOuLocGeranceExiste(boolean fichierTitreAcqOuLocGeranceExiste) {
		this.fichierTitreAcqOuLocGeranceExiste = fichierTitreAcqOuLocGeranceExiste;
	}

	public String getFichierAutorExeciceCommerce() {
		return fichierAutorExeciceCommerce;
	}

	public void setFichierAutorExeciceCommerce(String fichierAutorExeciceCommerce) {
		this.fichierAutorExeciceCommerce = fichierAutorExeciceCommerce;
		setFichierAutorExeciceCommerceExiste(UtilString.isCorrect(fichierAutorExeciceCommerce));
	}

	public boolean isFichierAutorExeciceCommerceExiste() {
		return fichierAutorExeciceCommerceExiste;
	}

	public void setFichierAutorExeciceCommerceExiste(boolean fichierAutorExeciceCommerceExiste) {
		this.fichierAutorExeciceCommerceExiste = fichierAutorExeciceCommerceExiste;
	}

	public ConjointDTO getConjointDTO() {
		return conjointDTO;
	}

	public void setConjointDTO(ConjointDTO conjointDTO) {
		this.conjointDTO = conjointDTO;
	}

	public ConjointDTO getConjointDTO1() {
		return conjointDTO1;
	}

	public void setConjointDTO1(ConjointDTO conjointDTO1) {
		this.conjointDTO1 = conjointDTO1;
	}

	public List<ConjointDTO> getConjoints() {
		return conjoints;
	}

	public void setConjoints(List<ConjointDTO> conjoints) {
		this.conjoints = conjoints;
	}

	public GedAbstractDocumentDTO getGedAbstractDocumentDTOFormulaireP1() {
		return gedAbstractDocumentDTOFormulaireP1;
	}

	public void setGedAbstractDocumentDTOFormulaireP1(GedAbstractDocumentDTO gedAbstractDocumentDTOFormulaireP1) {
		this.gedAbstractDocumentDTOFormulaireP1 = gedAbstractDocumentDTOFormulaireP1;
	}

	public GedAbstractDocumentDTO getGedAbstractDocumentDTOFormulaireP1Bis() {
		return gedAbstractDocumentDTOFormulaireP1Bis;
	}

	public void setGedAbstractDocumentDTOFormulaireP1Bis(GedAbstractDocumentDTO gedAbstractDocumentDTOFormulaireP1Bis) {
		this.gedAbstractDocumentDTOFormulaireP1Bis = gedAbstractDocumentDTOFormulaireP1Bis;
	}

}
