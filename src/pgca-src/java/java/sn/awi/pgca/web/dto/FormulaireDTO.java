package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.primefaces.model.UploadedFile;

public class FormulaireDTO implements Serializable {

	/**
	 * 
	 */
	private static final long				serialVersionUID							= -8905330703368459753L;

	private Long										idFormulaire;

	private String									numeroQuittance;

	private String									numeroOrdre;

	private String									numeroPaiement;

	private String									numeroFormalite;

	private String									idTypeFormalite;

	private String									libelleTypeFormalite;

	private String									idTypeDemande;

	private String									libelleTypeDemande;

	private MandataireDTO						mandataireDTO;

	private Date										dateDemande;

	private String									lieudemande;

	private String									idSignataire;

	private String									decision;

	private String									motifRejet;

	private Date										dateDecision;

	private String									nomSignataire;

	private String									prenomSignataire;

	private String									prenomNomSignataire;

	private String									titreSignataire;

	private String									libelleEntiteJuridiqueSignataire;

	private String									lieuSignataire;
	private Date										datePaiement;
	private Date										dateArrivee;

	private String									fichierQuittance;

	private String									strDateDecision;

	private String									strDateDecision2;

	private String									intercalaire;

	private int											nbPageIntercalaire;

	private String									fichierFormulaire;

	private GedAbstractDocumentDTO	gedAbstractDocumentDTOQuittance;

	private GedAbstractDocumentDTO	gedAbstractDocumentDTOAccuseEnr;

	private GedAbstractDocumentDTO	gedAbstractDocumentDTOFormulaireInjonction;

	/** Liste des fichiers a uploadé pour une personne physique **/
	private List<CoupleDTOFichier>	listeDesFichiersAteleverser;

	private UploadedFile						fileToupload;

	private String									IdFichierChoisi;

	private UploadedFile						FichierChoisi;

	public FormulaireDTO() {
		mandataireDTO = new MandataireDTO();
	}

	public Long getIdFormulaire() {
		return idFormulaire;
	}

	public void setIdFormulaire(Long idFormulaire) {
		this.idFormulaire = idFormulaire;
	}

	public String getNumeroQuittance() {
		return numeroQuittance;
	}

	public void setNumeroQuittance(String numeroQuittance) {
		this.numeroQuittance = numeroQuittance;
	}

	public String getNumeroOrdre() {
		return numeroOrdre;
	}

	public void setNumeroOrdre(String numeroOrdre) {
		this.numeroOrdre = numeroOrdre;
	}

	public MandataireDTO getMandataireDTO() {
		return mandataireDTO;
	}

	public void setMandataireDTO(MandataireDTO mandataireDTO) {
		this.mandataireDTO = mandataireDTO;
	}

	public Date getDateDemande() {
		return dateDemande;
	}

	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
	}

	public String getLieudemande() {
		return lieudemande;
	}

	public void setLieudemande(String lieudemande) {
		this.lieudemande = lieudemande;
	}

	public String getIdSignataire() {
		return idSignataire;
	}

	public void setIdSignataire(String idSignataire) {
		this.idSignataire = idSignataire;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getMotifRejet() {
		return motifRejet;
	}

	public void setMotifRejet(String motifRejet) {
		this.motifRejet = motifRejet;
	}

	public Date getDateDecision() {
		return dateDecision;
	}

	public void setDateDecision(Date dateDecision) {
		this.dateDecision = dateDecision;
	}

	public String getNomSignataire() {
		return nomSignataire;
	}

	public void setNomSignataire(String nomSignataire) {
		this.nomSignataire = nomSignataire;
	}

	public String getPrenomSignataire() {
		return prenomSignataire;
	}

	public void setPrenomSignataire(String prenomSignataire) {
		this.prenomSignataire = prenomSignataire;
	}

	public String getPrenomNomSignataire() {
		return prenomNomSignataire;
	}

	public void setPrenomNomSignataire(String prenomNomSignataire) {
		this.prenomNomSignataire = prenomNomSignataire;
	}

	public String getTitreSignataire() {
		return titreSignataire;
	}

	public void setTitreSignataire(String titreSignataire) {
		this.titreSignataire = titreSignataire;
	}

	public String getLibelleEntiteJuridiqueSignataire() {
		return libelleEntiteJuridiqueSignataire;
	}

	public void setLibelleEntiteJuridiqueSignataire(String libelleEntiteJuridiqueSignataire) {
		this.libelleEntiteJuridiqueSignataire = libelleEntiteJuridiqueSignataire;
	}

	public String getLieuSignataire() {
		return lieuSignataire;
	}

	public void setLieuSignataire(String lieuSignataire) {
		this.lieuSignataire = lieuSignataire;
	}

	public Date getDatePaiement() {
		return datePaiement;
	}

	public void setDatePaiement(Date datePaiement) {
		this.datePaiement = datePaiement;
	}

	public Date getDateArrivee() {
		return dateArrivee;
	}

	public void setDateArrivee(Date dateArrivee) {
		this.dateArrivee = dateArrivee;
	}

	public String getIdTypeFormalite() {
		return idTypeFormalite;
	}

	public void setIdTypeFormalite(String idTypeFormalite) {
		this.idTypeFormalite = idTypeFormalite;
	}

	public String getLibelleTypeFormalite() {
		return libelleTypeFormalite;
	}

	public void setLibelleTypeFormalite(String libelleTypeFormalite) {
		this.libelleTypeFormalite = libelleTypeFormalite;
	}

	public String getIdTypeDemande() {
		return idTypeDemande;
	}

	public void setIdTypeDemande(String idTypeDemande) {
		this.idTypeDemande = idTypeDemande;
	}

	public String getLibelleTypeDemande() {
		return libelleTypeDemande;
	}

	public void setLibelleTypeDemande(String libelleTypeDemande) {
		this.libelleTypeDemande = libelleTypeDemande;
	}

	public String getNumeroPaiement() {
		return numeroPaiement;
	}

	public void setNumeroPaiement(String numeroPaiement) {
		this.numeroPaiement = numeroPaiement;
	}

	public String getFichierQuittance() {
		return fichierQuittance;
	}

	public void setFichierQuittance(String fichierQuittance) {
		this.fichierQuittance = fichierQuittance;
	}

	public String getIntercalaire() {
		return intercalaire;
	}

	public void setIntercalaire(String intercalaire) {
		this.intercalaire = intercalaire;
	}

	public int getNbPageIntercalaire() {
		return nbPageIntercalaire;
	}

	public void setNbPageIntercalaire(int nbPageIntercalaire) {
		this.nbPageIntercalaire = nbPageIntercalaire;
	}

	public String getStrDateDecision() {
		return strDateDecision;
	}

	public void setStrDateDecision(String strDateDecision) {
		this.strDateDecision = strDateDecision;
	}

	public String getStrDateDecision2() {
		return strDateDecision2;
	}

	public void setStrDateDecision2(String strDateDecision2) {
		this.strDateDecision2 = strDateDecision2;
	}

	public String getFichierFormulaire() {
		return fichierFormulaire;
	}

	public void setFichierFormulaire(String fichierFormulaire) {
		this.fichierFormulaire = fichierFormulaire;
	}

	public GedAbstractDocumentDTO getGedAbstractDocumentDTOQuittance() {
		return gedAbstractDocumentDTOQuittance;
	}

	public void setGedAbstractDocumentDTOQuittance(GedAbstractDocumentDTO gedAbstractDocumentDTOQuittance) {
		this.gedAbstractDocumentDTOQuittance = gedAbstractDocumentDTOQuittance;
	}

	public GedAbstractDocumentDTO getGedAbstractDocumentDTOAccuseEnr() {
		return gedAbstractDocumentDTOAccuseEnr;
	}

	public void setGedAbstractDocumentDTOAccuseEnr(GedAbstractDocumentDTO gedAbstractDocumentDTOAccuseEnr) {
		this.gedAbstractDocumentDTOAccuseEnr = gedAbstractDocumentDTOAccuseEnr;
	}

	public GedAbstractDocumentDTO getGedAbstractDocumentDTOFormulaireInjonction() {
		return gedAbstractDocumentDTOFormulaireInjonction;
	}

	public void setGedAbstractDocumentDTOFormulaireInjonction(GedAbstractDocumentDTO gedAbstractDocumentDTOFormulaireInjonction) {
		this.gedAbstractDocumentDTOFormulaireInjonction = gedAbstractDocumentDTOFormulaireInjonction;
	}

	public String getNumeroFormalite() {
		return numeroFormalite;
	}

	public void setNumeroFormalite(String numeroFormalite) {
		this.numeroFormalite = numeroFormalite;
	}

	public UploadedFile getFichierChoisi() {
		return FichierChoisi;
	}

	public void setFichierChoisi(UploadedFile fichierChoisi) {
		FichierChoisi = fichierChoisi;
	}

	public String getIdFichierChoisi() {
		return IdFichierChoisi;
	}

	public void setIdFichierChoisi(String idFichierChoisi) {
		IdFichierChoisi = idFichierChoisi;
	}

	public List<CoupleDTOFichier> getListeDesFichiersAteleverser() {
		return listeDesFichiersAteleverser;
	}

	public void setListeDesFichiersAteleverser(List<CoupleDTOFichier> listeDesFichiersAteleverser) {
		this.listeDesFichiersAteleverser = listeDesFichiersAteleverser;
	}

	public UploadedFile getFileToupload() {
		return fileToupload;
	}

	public void setFileToupload(UploadedFile fileToupload) {
		this.fileToupload = fileToupload;
	}

}
