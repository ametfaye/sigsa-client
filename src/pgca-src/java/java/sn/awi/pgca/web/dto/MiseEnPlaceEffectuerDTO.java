package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


public class MiseEnPlaceEffectuerDTO implements Serializable{
	
	private static final long serialVersionUID = 4740612708609554436L;


	private Long id_MEPeff;

	private Long programmeConcerneid;
	

	private Long miseEnplaceConcerneId;
	private String miseEnplaceConcerneLibelle;
	private String transporteur;
	private String chauffeur;
	private String fournisseur;
	private String camion;
	private String bl;
	private String bl_Port;
	private double quantiteAmettreEnplace ; 
	private Date dateMiseEnplaceEffective;
	private String dateMiseEnplaceEffectiveStr;

	private String destinataire;
	private String pointdeVenteLibelle;
	private String pointdeVenteLibelleDepartement;
	private String libelleIntrant;
	private String iconTypeIntrant;
	
	// utils for PDF
	private   String quotaDeReference;
	private   String cumulDeReference;
	private   String reliquatDeReference;
	private   String quantitequotaDeReference;

	
	


	// aggregation  
	private Double tauxCouverture;
	private int nombrePontdeVente;

	
	private String blManuel; 
	private String lvManuel;
	public Long getId_MEPeff() {
		return id_MEPeff;
	}
	public void setId_MEPeff(Long id_MEPeff) {
		this.id_MEPeff = id_MEPeff;
	}
	public Long getProgrammeConcerneid() {
		return programmeConcerneid;
	}
	public void setProgrammeConcerneid(Long programmeConcerneid) {
		this.programmeConcerneid = programmeConcerneid;
	}
	public Long getMiseEnplaceConcerneId() {
		return miseEnplaceConcerneId;
	}
	public void setMiseEnplaceConcerneId(Long miseEnplaceConcerneId) {
		this.miseEnplaceConcerneId = miseEnplaceConcerneId;
	}
	public String getMiseEnplaceConcerneLibelle() {
		return miseEnplaceConcerneLibelle;
	}
	public void setMiseEnplaceConcerneLibelle(String miseEnplaceConcerneLibelle) {
		this.miseEnplaceConcerneLibelle = miseEnplaceConcerneLibelle;
	}
	public String getTransporteur() {
		return transporteur;
	}
	public String getDateMiseEnplaceEffectiveStr() {
		return dateMiseEnplaceEffectiveStr;
	}
	public void setDateMiseEnplaceEffectiveStr(String dateMiseEnplaceEffectiveStr) {
		this.dateMiseEnplaceEffectiveStr = dateMiseEnplaceEffectiveStr;
	}
	public void setTransporteur(String transporteur) {
		this.transporteur = transporteur;
	}
	public String getFournisseur() {
		return fournisseur;
	}
	public void setFournisseur(String fournisseur) {
		this.fournisseur = fournisseur;
	}
	public String getChauffeur() {
		return chauffeur;
	}
	public String getIconTypeIntrant() {
		return iconTypeIntrant;
	}
	public void setIconTypeIntrant(String iconTypeIntrant) {
		this.iconTypeIntrant = iconTypeIntrant;
	}
	public void setChauffeur(String chauffeur) {
		this.chauffeur = chauffeur;
	}
	public String getCamion() {
		return camion;
	}
	public void setCamion(String camion) {
		this.camion = camion;
	}
	public String getBl() {
		return bl;
	}
	public void setBl(String bl) {
		this.bl = bl;
	}
	public String getBl_Port() {
		return bl_Port;
	}
	public void setBl_Port(String bl_Port) {
		this.bl_Port = bl_Port;
	}
	public double getQuantiteAmettreEnplace() {
		return quantiteAmettreEnplace;
	}
	public void setQuantiteAmettreEnplace(double quantiteAmettreEnplace) {
		this.quantiteAmettreEnplace = quantiteAmettreEnplace;
	}

	public String getLibelleIntrant() {
		return libelleIntrant;
	}
	public void setLibelleIntrant(String libelleIntrant) {
		this.libelleIntrant = libelleIntrant;
	}
	public Date getDateMiseEnplaceEffective() {
		return dateMiseEnplaceEffective;
	}
	public String getBlManuel() {
		return blManuel;
	}
	public void setBlManuel(String blManuel) {
		this.blManuel = blManuel;
	}
	public String getLvManuel() {
		return lvManuel;
	}
	public void setLvManuel(String lvManuel) {
		this.lvManuel = lvManuel;
	}
	public void setDateMiseEnplaceEffective(Date dateMiseEnplaceEffective) {
		this.dateMiseEnplaceEffective = dateMiseEnplaceEffective;
	}
	public String getPointdeVenteLibelleDepartement() {
		return pointdeVenteLibelleDepartement;
	}
	public void setPointdeVenteLibelleDepartement(String pointdeVenteLibelleDepartement) {
		this.pointdeVenteLibelleDepartement = pointdeVenteLibelleDepartement;
	}
	public String getDestinataire() {
		return destinataire;
	}
	public void setDestinataire(String destinataire) {
		this.destinataire = destinataire;
	}
	public String getPointdeVenteLibelle() {
		return pointdeVenteLibelle;
	}
	public void setPointdeVenteLibelle(String pointdeVenteLibelle) {
		this.pointdeVenteLibelle = pointdeVenteLibelle;
	}
	public Double getTauxCouverture() {
		return tauxCouverture;
	}
	public void setTauxCouverture(Double tauxCouverture) {
		this.tauxCouverture = tauxCouverture;
	}
	public int getNombrePontdeVente() {
		return nombrePontdeVente;
	}
	public void setNombrePontdeVente(int nombrePontdeVente) {
		this.nombrePontdeVente = nombrePontdeVente;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getQuotaDeReference() {
		return quotaDeReference;
	}
	public void setQuotaDeReference(String quotaDeReference) {
		this.quotaDeReference = quotaDeReference;
	}
	public String getCumulDeReference() {
		return cumulDeReference;
	}
	public void setCumulDeReference(String cumulDeReference) {
		this.cumulDeReference = cumulDeReference;
	}
	public String getReliquatDeReference() {
		return reliquatDeReference;
	}
	public String getQuantitequotaDeReference() {
		return quantitequotaDeReference;
	}
	public void setQuantitequotaDeReference(String quantitequotaDeReference) {
		this.quantitequotaDeReference = quantitequotaDeReference;
	}
	public void setReliquatDeReference(String reliquatDeReference) {
		this.reliquatDeReference = reliquatDeReference;
	}
	
	
	
}
