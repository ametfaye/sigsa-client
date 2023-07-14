package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.primefaces.model.UploadedFile;

import sn.awi.pgca.utils.DateUtils;
import sn.awi.pgca.utils.UtilString;

public class FormulaireSureteDTO implements Serializable {

	private static final long				serialVersionUID							= -6354652578972287902L;

	private Long										idFormulaire;

	private String									numerosurete;

	private String									numeroordre;

	private String									numeroquittance;

	private String									numeroFormalite;

	private String									numeroPaiement;

	private Date										datePaiement;

	private Date										dateInscription;

	private Date										dateArrivee;

	private String									idTypeFormalite;

	private String									codeTypeFormalite;

	private String									idTypeDemandeSurete;

	private String									libelleTypeDemandeSurete;

	private String									idTypeDeclaration;

	private String									libelleTypeDeclaration;

	private String									libelleMandataire;

	private String									idEntiteJuridique;

	private MandataireDTO						mandataireDTO;

	private PartiePrenanteSureteDTO	beneficiaireSurete;

	private PartiePrenanteSureteDTO	constituantSurete;

	private PartiePrenanteSureteDTO	debiteurSurete;

	private Date										dateDemande;

	private String									strDateDemande;

	private String									lieudemande;

	private String									idSignataire;

	private String									fichierFormulaire;

	private String									decision;

	private String									motifRejet;

	private Date										dateDecision;

	private String									strDateDecision;

	private String									strDateDecision2;

	private String									nomSignataire;

	private String									prenomSignataire;

	private String									prenomNomSignataire;

	private String									titreSignataire;

	private String									libelleEntiteJuridiqueSignataire;

	private String									lieuSignataire;

	private String									intercalaire;

	private int											nbPageIntercalaire;

	private String									fichierFormulaireDepot;

	private boolean									fichierFormulaireDepotExiste	= false;

	private String									fichierQuittancePaye;

	private boolean									fichierQuittancePayeExiste		= false;

	private String									fichierQuittance;

	private String									fichierAccuseInscription;

	private String									intercalaireCreanceActuelle;

	private int											nbPageIntercalaireCreanceActuelle;

	private String									intercalaireCreanceFuture;

	private int											nbPageIntercalaireCreanceFuture;

	private String									intercalaireBien;

	private int											nbPageIntercalaireBien;

	private String									intercalaireSurete;

	private int											nbPageIntercalaireSurete;

	private UploadedFile						fileFormulaireDepot;

	private boolean									beneficiareEstLedebiteur			= false;

	private GedAbstractDocumentDTO	gedAbstractDocumentDTOQuittance;

	private GedAbstractDocumentDTO	gedAbstractDocumentDTOAccuseIns;

	private GedAbstractDocumentDTO	gedAbstractDocumentDTOFormulaireS;

	private GedAbstractDocumentDTO	gedAbstractDocumentDTOFormulaireInjonction;

	/** Liste des fichiers a uploadé**/
	private List<CoupleDTOFichier>	listeDesFichiersAteleverser;

	private UploadedFile						fileToupload;

	private String									IdFichierChoisi;

	private UploadedFile						FichierChoisi;

	public FormulaireSureteDTO() {
		mandataireDTO = new MandataireDTO();
		beneficiaireSurete = new PartiePrenanteSureteDTO();
		constituantSurete = new PartiePrenanteSureteDTO();
		debiteurSurete = new PartiePrenanteSureteDTO();
	}

	public Long getIdFormulaire() {
		return idFormulaire;
	}

	public void setIdFormulaire(Long idFormulaire) {
		this.idFormulaire = idFormulaire;
	}

	public String getNumeroordre() {
		return numeroordre;
	}

	public void setNumeroordre(String numeroordre) {
		this.numeroordre = numeroordre;
	}

	public String getIdTypeDemandeSurete() {
		return idTypeDemandeSurete;
	}

	public void setIdTypeDemandeSurete(String idTypeDemandeSurete) {
		this.idTypeDemandeSurete = idTypeDemandeSurete;
	}

	public String getIdEntiteJuridique() {
		return idEntiteJuridique;
	}

	public void setIdEntiteJuridique(String idEntiteJuridique) {
		this.idEntiteJuridique = idEntiteJuridique;
	}

	public MandataireDTO getMandataireDTO() {
		return mandataireDTO;
	}

	public void setMandataireDTO(MandataireDTO mandataireDTO) {
		this.mandataireDTO = mandataireDTO;
	}

	public String getFichierFormulaire() {
		return fichierFormulaire;
	}

	public void setFichierFormulaire(String fichierFormulaire) {
		this.fichierFormulaire = fichierFormulaire;
	}

	public Date getDateDemande() {
		return dateDemande;
	}

	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
		if (dateDemande != null)
			setStrDateDemande(DateUtils.formatDate(dateDemande));
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

	public String getLibelleEntiteJuridiqueSignataire() {
		return libelleEntiteJuridiqueSignataire;
	}

	public void setLibelleEntiteJuridiqueSignataire(String libelleEntiteJuridiqueSignataire) {
		this.libelleEntiteJuridiqueSignataire = libelleEntiteJuridiqueSignataire;
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

	public String getTitreSignataire() {
		return titreSignataire;
	}

	public void setTitreSignataire(String titreSignataire) {
		this.titreSignataire = titreSignataire;
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

	public String getLieuSignataire() {
		return lieuSignataire;
	}

	public void setLieuSignataire(String lieuSignataire) {
		this.lieuSignataire = lieuSignataire;
	}

	public String getPrenomNomSignataire() {
		return prenomNomSignataire;
	}

	public void setPrenomNomSignataire(String prenomNomSignataire) {
		this.prenomNomSignataire = prenomNomSignataire;
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

	public String getNumerosurete() {
		return numerosurete;
	}

	public void setNumerosurete(String numerosurete) {
		this.numerosurete = numerosurete;
	}

	public PartiePrenanteSureteDTO getBeneficiaireSurete() {
		return beneficiaireSurete;
	}

	public void setBeneficiaireSurete(PartiePrenanteSureteDTO beneficiaireSurete) {
		this.beneficiaireSurete = beneficiaireSurete;
	}

	public PartiePrenanteSureteDTO getConstituantSurete() {
		return constituantSurete;
	}

	public void setConstituantSurete(PartiePrenanteSureteDTO constituantSurete) {
		this.constituantSurete = constituantSurete;
	}

	public PartiePrenanteSureteDTO getDebiteurSurete() {
		return debiteurSurete;
	}

	public void setDebiteurSurete(PartiePrenanteSureteDTO debiteurSurete) {
		this.debiteurSurete = debiteurSurete;
	}

	public String getFichierFormulaireDepot() {
		return fichierFormulaireDepot;
	}

	public void setFichierFormulaireDepot(String fichierFormulaireDepot) {
		this.fichierFormulaireDepot = fichierFormulaireDepot;
		setFichierFormulaireDepotExiste(UtilString.isCorrect(fichierFormulaireDepot));
	}

	public boolean isFichierFormulaireDepotExiste() {
		return fichierFormulaireDepotExiste;
	}

	public void setFichierFormulaireDepotExiste(boolean fichierFormulaireDepotExiste) {
		this.fichierFormulaireDepotExiste = fichierFormulaireDepotExiste;
	}

	public String getIdTypeFormalite() {
		return idTypeFormalite;
	}

	public void setIdTypeFormalite(String idTypeFormalite) {
		this.idTypeFormalite = idTypeFormalite;
	}

	public String getNumeroquittance() {
		return numeroquittance;
	}

	public void setNumeroquittance(String numeroquittance) {
		this.numeroquittance = numeroquittance;
	}

	public String getIdTypeDeclaration() {
		return idTypeDeclaration;
	}

	public void setIdTypeDeclaration(String idTypeDeclaration) {
		this.idTypeDeclaration = idTypeDeclaration;
	}

	public String getLibelleTypeDeclaration() {
		return libelleTypeDeclaration;
	}

	public void setLibelleTypeDeclaration(String libelleTypeDeclaration) {
		this.libelleTypeDeclaration = libelleTypeDeclaration;
	}

	public String getLibelleTypeDemandeSurete() {
		return libelleTypeDemandeSurete;
	}

	public void setLibelleTypeDemandeSurete(String libelleTypeDemandeSurete) {
		this.libelleTypeDemandeSurete = libelleTypeDemandeSurete;
	}

	public String getFichierQuittance() {
		return fichierQuittance;
	}

	public void setFichierQuittance(String fichierQuittance) {
		this.fichierQuittance = fichierQuittance;
	}

	public String getNumeroPaiement() {
		return numeroPaiement;
	}

	public void setNumeroPaiement(String numeroPaiement) {
		this.numeroPaiement = numeroPaiement;
	}

	public Date getDatePaiement() {
		return datePaiement;
	}

	public void setDatePaiement(Date datePaiement) {
		this.datePaiement = datePaiement;
	}

	public Date getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(Date dateInscription) {
		this.dateInscription = dateInscription;
	}

	public String getFichierAccuseInscription() {
		return fichierAccuseInscription;
	}

	public void setFichierAccuseInscription(String fichierAccuseInscription) {
		this.fichierAccuseInscription = fichierAccuseInscription;
	}

	public String getIntercalaireCreanceActuelle() {
		return intercalaireCreanceActuelle;
	}

	public void setIntercalaireCreanceActuelle(String intercalaireCreanceActuelle) {
		this.intercalaireCreanceActuelle = intercalaireCreanceActuelle;
	}

	public int getNbPageIntercalaireCreanceActuelle() {
		return nbPageIntercalaireCreanceActuelle;
	}

	public void setNbPageIntercalaireCreanceActuelle(int nbPageIntercalaireCreanceActuelle) {
		this.nbPageIntercalaireCreanceActuelle = nbPageIntercalaireCreanceActuelle;
	}

	public String getIntercalaireCreanceFuture() {
		return intercalaireCreanceFuture;
	}

	public void setIntercalaireCreanceFuture(String intercalaireCreanceFuture) {
		this.intercalaireCreanceFuture = intercalaireCreanceFuture;
	}

	public int getNbPageIntercalaireCreanceFuture() {
		return nbPageIntercalaireCreanceFuture;
	}

	public void setNbPageIntercalaireCreanceFuture(int nbPageIntercalaireCreanceFuture) {
		this.nbPageIntercalaireCreanceFuture = nbPageIntercalaireCreanceFuture;
	}

	public String getIntercalaireBien() {
		return intercalaireBien;
	}

	public void setIntercalaireBien(String intercalaireBien) {
		this.intercalaireBien = intercalaireBien;
	}

	public int getNbPageIntercalaireBien() {
		return nbPageIntercalaireBien;
	}

	public void setNbPageIntercalaireBien(int nbPageIntercalaireBien) {
		this.nbPageIntercalaireBien = nbPageIntercalaireBien;
	}

	public UploadedFile getFileFormulaireDepot() {
		return fileFormulaireDepot;
	}

	public void setFileFormulaireDepot(UploadedFile fileFormulaireDepot) {
		this.fileFormulaireDepot = fileFormulaireDepot;
	}

	public String getFichierQuittancePaye() {
		return fichierQuittancePaye;
	}

	public void setFichierQuittancePaye(String fichierQuittancePaye) {
		this.fichierQuittancePaye = fichierQuittancePaye;
		setFichierQuittancePayeExiste(UtilString.isCorrect(fichierQuittancePaye));
	}

	public boolean isFichierQuittancePayeExiste() {
		return fichierQuittancePayeExiste;
	}

	public void setFichierQuittancePayeExiste(boolean fichierQuittancePayeExiste) {
		this.fichierQuittancePayeExiste = fichierQuittancePayeExiste;
	}

	public String getCodeTypeFormalite() {
		return codeTypeFormalite;
	}

	public void setCodeTypeFormalite(String codeTypeFormalite) {
		this.codeTypeFormalite = codeTypeFormalite;
	}

	public String getIntercalaireSurete() {
		return intercalaireSurete;
	}

	public void setIntercalaireSurete(String intercalaireSurete) {
		this.intercalaireSurete = intercalaireSurete;
	}

	public int getNbPageIntercalaireSurete() {
		return nbPageIntercalaireSurete;
	}

	public void setNbPageIntercalaireSurete(int nbPageIntercalaireSurete) {
		this.nbPageIntercalaireSurete = nbPageIntercalaireSurete;
	}

	public String getLibelleMandataire() {
		return libelleMandataire;
	}

	public void setLibelleMandataire(String libelleMandataire) {
		this.libelleMandataire = libelleMandataire;
	}

	public boolean isBeneficiareEstLedebiteur() {
		return beneficiareEstLedebiteur;
	}

	public void setBeneficiareEstLedebiteur(boolean beneficiareEstLedebiteur) {
		this.beneficiareEstLedebiteur = beneficiareEstLedebiteur;
	}

	public Date getDateArrivee() {
		return dateArrivee;
	}

	public void setDateArrivee(Date dateArrivee) {
		this.dateArrivee = dateArrivee;
	}

	public String getStrDateDemande() {
		return strDateDemande;
	}

	public void setStrDateDemande(String strDateDemande) {
		this.strDateDemande = strDateDemande;
	}

	public String getNumeroFormalite() {
		return numeroFormalite;
	}

	public void setNumeroFormalite(String numeroFormalite) {
		this.numeroFormalite = numeroFormalite;
	}

	public GedAbstractDocumentDTO getGedAbstractDocumentDTOQuittance() {
		return gedAbstractDocumentDTOQuittance;
	}

	public void setGedAbstractDocumentDTOQuittance(GedAbstractDocumentDTO gedAbstractDocumentDTOQuittance) {
		this.gedAbstractDocumentDTOQuittance = gedAbstractDocumentDTOQuittance;
	}

	public GedAbstractDocumentDTO getGedAbstractDocumentDTOAccuseIns() {
		return gedAbstractDocumentDTOAccuseIns;
	}

	public void setGedAbstractDocumentDTOAccuseIns(GedAbstractDocumentDTO gedAbstractDocumentDTOAccuseIns) {
		this.gedAbstractDocumentDTOAccuseIns = gedAbstractDocumentDTOAccuseIns;
	}

	public GedAbstractDocumentDTO getGedAbstractDocumentDTOFormulaireInjonction() {
		return gedAbstractDocumentDTOFormulaireInjonction;
	}

	public void setGedAbstractDocumentDTOFormulaireInjonction(GedAbstractDocumentDTO gedAbstractDocumentDTOFormulaireInjonction) {
		this.gedAbstractDocumentDTOFormulaireInjonction = gedAbstractDocumentDTOFormulaireInjonction;
	}

	public GedAbstractDocumentDTO getGedAbstractDocumentDTOFormulaireS() {
		return gedAbstractDocumentDTOFormulaireS;
	}

	public void setGedAbstractDocumentDTOFormulaireS(GedAbstractDocumentDTO gedAbstractDocumentDTOFormulaireS) {
		this.gedAbstractDocumentDTOFormulaireS = gedAbstractDocumentDTOFormulaireS;
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

	public String getIdFichierChoisi() {
		return IdFichierChoisi;
	}

	public void setIdFichierChoisi(String idFichierChoisi) {
		IdFichierChoisi = idFichierChoisi;
	}

	public UploadedFile getFichierChoisi() {
		return FichierChoisi;
	}

	public void setFichierChoisi(UploadedFile fichierChoisi) {
		FichierChoisi = fichierChoisi;
	}

}
