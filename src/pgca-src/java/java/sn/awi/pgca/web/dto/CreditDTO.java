package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import sn.awi.pgca.dataModel.Ventes;


public class CreditDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long credit_id;

	private String referenceCredit;
	
	private String resumeCredit; // Credit contracté lors de l'achat du produit X a la date Y 
	private float montantInitialCredit;
	
	private float montantRestantApayer;
	
	private String											dateContraction;  
	private String											dateDerniereAvanceSurCredit;
	private Long venteConcerneiD;
	private String venteConcerneLibelle;
	
	private String zoneCredit; 
	private Long auteurCreditId;
	private String auteurCreditLibelle;
	
	private String Nomsouscripteur;
	private String telSouscripteur;
	private String adresseSouscripteur;
	
	private Long commandeId;

	
	private List<AvanceCreditDTO> listAvanceCredits  ;
	
	public Long getCredit_id() {
		return credit_id;
	}
	public void setCredit_id(Long credit_id) {
		this.credit_id = credit_id;
	}
	public String getReferenceCredit() {
		return referenceCredit;
	}
	public void setReferenceCredit(String referenceCredit) {
		this.referenceCredit = referenceCredit;
	}
	public String getResumeCredit() {
		return resumeCredit;
	}
	public void setResumeCredit(String resumeCredit) {
		this.resumeCredit = resumeCredit;
	}
	public float getMontantInitialCredit() {
		return montantInitialCredit;
	}
	public void setMontantInitialCredit(float montantInitialCredit) {
		this.montantInitialCredit = montantInitialCredit;
	}
	public float getMontantRestantApayer() {
		return montantRestantApayer;
	}
	public void setMontantRestantApayer(float montantRestantApayer) {
		this.montantRestantApayer = montantRestantApayer;
	}
	public String getDateContraction() {
		return dateContraction;
	}
	public void setDateContraction(String dateContraction) {
		this.dateContraction = dateContraction;
	}
	public String getDateDerniereAvanceSurCredit() {
		return dateDerniereAvanceSurCredit;
	}
	public void setDateDerniereAvanceSurCredit(String dateDerniereAvanceSurCredit) {
		this.dateDerniereAvanceSurCredit = dateDerniereAvanceSurCredit;
	}
	public Long getVenteConcerneiD() {
		return venteConcerneiD;
	}
	public void setVenteConcerneiD(Long venteConcerneiD) {
		this.venteConcerneiD = venteConcerneiD;
	}
	public String getVenteConcerneLibelle() {
		return venteConcerneLibelle;
	}
	public void setVenteConcerneLibelle(String venteConcerneLibelle) {
		this.venteConcerneLibelle = venteConcerneLibelle;
	}
	public Long getAuteurCreditId() {
		return auteurCreditId;
	}
	public void setAuteurCreditId(Long auteurCreditId) {
		this.auteurCreditId = auteurCreditId;
	}
	public String getAuteurCreditLibelle() {
		return auteurCreditLibelle;
	}
	public void setAuteurCreditLibelle(String auteurCreditLibelle) {
		this.auteurCreditLibelle = auteurCreditLibelle;
	}
	public String getNomsouscripteur() {
		return Nomsouscripteur;
	}
	public void setNomsouscripteur(String nomsouscripteur) {
		Nomsouscripteur = nomsouscripteur;
	}
	public String getTelSouscripteur() {
		return telSouscripteur;
	}
	public void setTelSouscripteur(String telSouscripteur) {
		this.telSouscripteur = telSouscripteur;
	}
	public String getZoneCredit() {
		return zoneCredit;
	}
	public void setZoneCredit(String zoneCredit) {
		this.zoneCredit = zoneCredit;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<AvanceCreditDTO> getListAvanceCredits() {
		return listAvanceCredits;
	}
	public void setListAvanceCredits(List<AvanceCreditDTO> listAvanceCredits) {
		this.listAvanceCredits = listAvanceCredits;
	}
	public String getAdresseSouscripteur() {
		return adresseSouscripteur;
	}
	public void setAdresseSouscripteur(String adresseSouscripteur) {
		this.adresseSouscripteur = adresseSouscripteur;
	}
	public Long getCommandeId() {
		return commandeId;
	}
	public void setCommandeId(Long commandeId) {
		this.commandeId = commandeId;
	}

}
