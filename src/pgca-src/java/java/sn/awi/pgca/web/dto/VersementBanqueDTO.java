package sn.awi.pgca.web.dto;

import java.io.Serializable;

import org.primefaces.model.UploadedFile;

public class VersementBanqueDTO implements Serializable {

	private static final long serialVersionUID = 9125422905791273379L;

	private Long idVersment;

	private String dateDeVersement;  

	private float montantVersment;

	
	private float blpMontant ;
	private String blpNumero ;
	
	

	private String moyenVersment;
	
	private String banque;

	private String libelleVersment;
	
	private String pathdocumentJustificatif;  
	
	private String documentJustificatifextension;
	
	
	private String zone;

	private UploadedFile 	documentJustificatif;

	private Long idPointdeVente;
	private Long idPointDeCollecte;


	private Long idProgrammeAgricol;

	private Long idPersonneAuteurVersment;
	
	private String libellePersonneAuteurVersment;
	
	


	public Long getIdVersment() {
		return idVersment;
	}

	public void setIdVersment(Long idVersment) {
		this.idVersment = idVersment;
	}


	public float getMontantVersment() {
		return montantVersment;
	}

	public void setMontantVersment(float montantVersment) {
		this.montantVersment = montantVersment;
	}

	public String getMoyenVersment() {
		return moyenVersment;
	}

	public void setMoyenVersment(String moyenVersment) {
		this.moyenVersment = moyenVersment;
	}

	public String getLibelleVersment() {
		return libelleVersment;
	}

	public void setLibelleVersment(String libelleVersment) {
		this.libelleVersment = libelleVersment;
	}

	public Long getIdPointdeVente() {
		return idPointdeVente;
	}

	public void setIdPointdeVente(Long idPointdeVente) {
		this.idPointdeVente = idPointdeVente;
	}

	public Long getIdProgrammeAgricol() {
		return idProgrammeAgricol;
	}

	public void setIdProgrammeAgricol(Long idProgrammeAgricol) {
		this.idProgrammeAgricol = idProgrammeAgricol;
	}

	public Long getIdPersonneAuteurVersment() {
		return idPersonneAuteurVersment;
	}

	public void setIdPersonneAuteurVersment(Long idPersonneAuteurVersment) {
		this.idPersonneAuteurVersment = idPersonneAuteurVersment;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UploadedFile getDocumentJustificatif() {
		return documentJustificatif;
	}

	public void setDocumentJustificatif(UploadedFile documentJustificatif) {
		this.documentJustificatif = documentJustificatif;
	}

	public String getPathdocumentJustificatif() {
		return pathdocumentJustificatif;
	}

	public void setPathdocumentJustificatif(String pathdocumentJustificatif) {
		this.pathdocumentJustificatif = pathdocumentJustificatif;
	}

	public String getLibellePersonneAuteurVersment() {
		return libellePersonneAuteurVersment;
	}

	public void setLibellePersonneAuteurVersment(String libellePersonneAuteurVersment) {
		this.libellePersonneAuteurVersment = libellePersonneAuteurVersment;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getDocumentJustificatifextension() {
		return documentJustificatifextension;
	}

	public void setDocumentJustificatifextension(String documentJustificatifextension) {
		this.documentJustificatifextension = documentJustificatifextension;
	}

	public String getDateDeVersement() {
		return dateDeVersement;
	}

	public void setDateDeVersement(String dateDeVersement) {
		this.dateDeVersement = dateDeVersement;
	}

	public String getBanque() {
		return banque;
	}

	public void setBanque(String banque) {
		this.banque = banque;
	}

	public Long getIdPointDeCollecte() {
		return idPointDeCollecte;
	}

	public void setIdPointDeCollecte(Long idPointDeCollecte) {
		this.idPointDeCollecte = idPointDeCollecte;
	}

	public float getBlpMontant() {
		return blpMontant;
	}

	public String getBlpNumero() {
		return blpNumero;
	}

	public void setBlpMontant(float blpMontant) {
		this.blpMontant = blpMontant;
	}

	public void setBlpNumero(String blpNumero) {
		this.blpNumero = blpNumero;
	}



}
