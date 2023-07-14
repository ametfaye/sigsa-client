package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.Date;

public class MiseEnplaceDTOPointDeVente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3311897253615919905L;

	private Long idPointDeVente;
	private Long idMiseEnPlace;
	private Long idIntrantAMettreEnplace;
	private String libelleIntrantAMettreEnplace;
	private double  quantiteIntrantAMettreEnplace;
	private double quotaIntrantAMettreEnplace;
	private double cumulIntrantAMettreEnplace;
	private double reliquatIntrantAMettreEnplace;
	private String nomPointDeVente;	
	private Long idGerant;
	private String nomGerant;  
	private Long idProgramme;
	private String nomProgramme; 
	private String idChauffeurWithName;  
	
	private Long idTransporteur;
	private Long idCamion;
	private Long idChauffeur;
	private Long idPointDeVenteReceptionBL;

	
	private Long idSlectedMiseEnplace; 
	private String chauffeur;
	private String camion;
	private String transporteur;
	private String fournisseur;
	private String designation;  
	private String distributeur;  
	private String dateMEPSTR;  
	private String pictoImages = "default";  

	

	// En cas de MEP sue stoc, residual 
	private Long idCommuneResiduel;
	
	// utile pour activer la class ACTIVE DE SLIDER 
	private String activeCss;  
	private String statusText;
	private String statusIcon;
	

	// Mise en place
	private Long fournisseurLibelle;
	private Long fournisseurId;
	private String fournisseurInfosDisponibilite;
	private String fournisseurInfosDisponibiliteCSS;
	private String blMiseEnPlace;
	private String lvMiseEnPlace;
	private String dateMiseEnplace;
	private String departementPointdeVente;
	private String communeCertifie;
	private Long quantiteIntrantRestant;
	
	private String iconTypeIntrant;
	private String dateEffectiveMEP;

	//departement
	private Long idDepartement; 
	private String departement;
	private String region;

	// aggregation  
	private Double tauxCouverture;
	private int nombrePontdeVente;
	private Long idStockdeReference ; // 0 fournisseur : sinon  Magasin
	

	 // slider dashboard infos  
	 Long   stockIdIntrant;
	 double stockFournisseurIntrnantEncours;
	 double stockSedabIntrnantEncours;
	 float 	ventesTotalIntrnantEncours;
	 
	 
	 // msg de retour en cas de modif de MEP
	 String msgRetour;
	 boolean createOrUpdate ;
	 
	
	public Long getIdPointDeVente() {
		return idPointDeVente;
	}
	public void setIdPointDeVente(Long idPointDeVente) {
		this.idPointDeVente = idPointDeVente;
	}
	public Long getIdIntrantAMettreEnplace() {
		return idIntrantAMettreEnplace;
	}
	public void setIdIntrantAMettreEnplace(Long idIntrantAMettreEnplace) {
		this.idIntrantAMettreEnplace = idIntrantAMettreEnplace;
	}
	public String getDateMEPSTR() {
		return dateMEPSTR;
	}
	public void setDateMEPSTR(String dateMEPSTR) {
		this.dateMEPSTR = dateMEPSTR;
	}
	public double getQuantiteIntrantAMettreEnplace() {
		return quantiteIntrantAMettreEnplace;
	}
	public void setQuantiteIntrantAMettreEnplace(double quantiteIntrantAMettreEnplace) {
		this.quantiteIntrantAMettreEnplace = quantiteIntrantAMettreEnplace;
	}

	public double getQuotaIntrantAMettreEnplace() {
		return quotaIntrantAMettreEnplace;
	}
	public void setQuotaIntrantAMettreEnplace(double quotaIntrantAMettreEnplace) {
		this.quotaIntrantAMettreEnplace = quotaIntrantAMettreEnplace;
	}
	public double getCumulIntrantAMettreEnplace() {
		return cumulIntrantAMettreEnplace;
	}
	public Long getIdMiseEnPlace() {
		return idMiseEnPlace;
	}
	public void setIdMiseEnPlace(Long idMiseEnPlace) {
		this.idMiseEnPlace = idMiseEnPlace;
	}
	public void setCumulIntrantAMettreEnplace(double cumulIntrantAMettreEnplace) {
		this.cumulIntrantAMettreEnplace = cumulIntrantAMettreEnplace;
	}
	public double getReliquatIntrantAMettreEnplace() {
		return reliquatIntrantAMettreEnplace;
	}
	public void setReliquatIntrantAMettreEnplace(double reliquatIntrantAMettreEnplace) {
		this.reliquatIntrantAMettreEnplace = reliquatIntrantAMettreEnplace;
	}
	public String getNomPointDeVente() {
		return nomPointDeVente;
	}
	public String getDateMiseEnplace() {
		return dateMiseEnplace;
	}
	public void setDateMiseEnplace(String dateMiseEnplace) {
		this.dateMiseEnplace = dateMiseEnplace;
	}
	public String getDepartementPointdeVente() {
		return departementPointdeVente;
	}
	public void setDepartementPointdeVente(String departementPointdeVente) {
		this.departementPointdeVente = departementPointdeVente;
	}
	public void setNomPointDeVente(String nomPointDeVente) {
		this.nomPointDeVente = nomPointDeVente;
	}
	public String getChauffeur() {
		return chauffeur;
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
	public Long getQuantiteIntrantRestant() {
		return quantiteIntrantRestant;
	}
	public void setQuantiteIntrantRestant(Long quantiteIntrantRestant) {
		this.quantiteIntrantRestant = quantiteIntrantRestant;
	}
	public String getTransporteur() {
		return transporteur;
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
	public String getIdChauffeurWithName() {
		return idChauffeurWithName;
	}
	public void setIdChauffeurWithName(String idChauffeurWithName) {
		this.idChauffeurWithName = idChauffeurWithName;
	}
	public String getLibelleIntrantAMettreEnplace() {
		return libelleIntrantAMettreEnplace;
	}
	public String getFournisseurInfosDisponibiliteCSS() {
		return fournisseurInfosDisponibiliteCSS;
	}
	public void setFournisseurInfosDisponibiliteCSS(String fournisseurInfosDisponibiliteCSS) {
		this.fournisseurInfosDisponibiliteCSS = fournisseurInfosDisponibiliteCSS;
	}
	public Long getIdSlectedMiseEnplace() {
		return idSlectedMiseEnplace;
	}
	public void setIdSlectedMiseEnplace(Long idSlectedMiseEnplace) {
		this.idSlectedMiseEnplace = idSlectedMiseEnplace;
	}
	public String getBlMiseEnPlace() {
		return blMiseEnPlace;
	}
	public void setBlMiseEnPlace(String blMiseEnPlace) {
		this.blMiseEnPlace = blMiseEnPlace;
	}
	public String getLvMiseEnPlace() {
		return lvMiseEnPlace;
	}
	public void setLvMiseEnPlace(String lvMiseEnPlace) {
		this.lvMiseEnPlace = lvMiseEnPlace;
	}
	public void setLibelleIntrantAMettreEnplace(String libelleIntrantAMettreEnplace) {
		this.libelleIntrantAMettreEnplace = libelleIntrantAMettreEnplace;
	}
	public Long getIdGerant() {
		return idGerant;
	}
	public void setIdGerant(Long idGerant) {
		this.idGerant = idGerant;
	}
	public String getNomGerant() {
		return nomGerant;
	}
	public Long getIdProgramme() {
		return idProgramme;
	}
	public void setIdProgramme(Long idProgramme) {
		this.idProgramme = idProgramme;
	}
	public String getNomProgramme() {
		return nomProgramme;
	}
	public void setNomProgramme(String nomProgramme) {
		this.nomProgramme = nomProgramme;
	}
	public Long getIdTransporteur() {
		return idTransporteur;
	}
	public void setIdTransporteur(Long idTransporteur) {
		this.idTransporteur = idTransporteur;
	}
	public Long getIdCamion() {
		return idCamion;
	}
	public Long getFournisseurLibelle() {
		return fournisseurLibelle;
	}
	public void setFournisseurLibelle(Long fournisseurLibelle) {
		this.fournisseurLibelle = fournisseurLibelle;
	}
	public Long getFournisseurId() {
		return fournisseurId;
	}
	public void setFournisseurId(Long fournisseurId) {
		this.fournisseurId = fournisseurId;
	}
	public String getFournisseurInfosDisponibilite() {
		return fournisseurInfosDisponibilite;
	}
	public void setFournisseurInfosDisponibilite(String fournisseurInfosDisponibilite) {
		this.fournisseurInfosDisponibilite = fournisseurInfosDisponibilite;
	}
	public void setIdCamion(Long idCamion) {
		this.idCamion = idCamion;
	}
	public Long getIdChauffeur() {
		return idChauffeur;
	}
	public void setIdChauffeur(Long idChauffeur) {
		this.idChauffeur = idChauffeur;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDistributeur() {
		return distributeur;
	}

	public void setDistributeur(String distributeur) {
		this.distributeur = distributeur;
	}
	public void setNomGerant(String nomGerant) {
		this.nomGerant = nomGerant;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCommuneCertifie() {
		return communeCertifie;
	}
	public Long getIdStockdeReference() {
		return idStockdeReference;
	}
	public void setIdStockdeReference(Long idStockdeReference) {
		this.idStockdeReference = idStockdeReference;
	}
	public void setCommuneCertifie(String communeCertifie) {
		this.communeCertifie = communeCertifie;
	}
	
	public Double getTauxCouverture() {
		return tauxCouverture;
	}
	public String getIconTypeIntrant() {
		return iconTypeIntrant;
	}
	public void setIconTypeIntrant(String iconTypeIntrant) {
		this.iconTypeIntrant = iconTypeIntrant;
	}
	public void setTauxCouverture(Double tauxCouverture) {
		this.tauxCouverture = tauxCouverture;
	}

	public int getNombrePontdeVente() {
		return nombrePontdeVente;
	}
	public String getDateEffectiveMEP() {
		return dateEffectiveMEP;
	}
	public void setDateEffectiveMEP(String dateEffectiveMEP) {
		this.dateEffectiveMEP = dateEffectiveMEP;
	}
	public Long getIdCommuneResiduel() {
		return idCommuneResiduel;
	}
	public void setIdCommuneResiduel(Long idCommuneResiduel) {
		this.idCommuneResiduel = idCommuneResiduel;
	}
	public void setNombrePontdeVente(int nombrePontdeVente) {
		this.nombrePontdeVente = nombrePontdeVente;
	}
	public String getPictoImages() {
		return pictoImages;
	}
	public String getActiveCss() {
		return activeCss;
	}
	public void setActiveCss(String activeCss) {
		this.activeCss = activeCss;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public String getStatusIcon() {
		return statusIcon;
	}
	public void setStatusIcon(String statusIcon) {
		this.statusIcon = statusIcon;
	}
	public Long getStockIdIntrant() {
		return stockIdIntrant;
	}
	public void setStockIdIntrant(Long stockIdIntrant) {
		this.stockIdIntrant = stockIdIntrant;
	}
	public double getStockFournisseurIntrnantEncours() {
		return stockFournisseurIntrnantEncours;
	}
	public void setStockFournisseurIntrnantEncours(double stockFournisseurIntrnantEncours) {
		this.stockFournisseurIntrnantEncours = stockFournisseurIntrnantEncours;
	}
	public double getStockSedabIntrnantEncours() {
		return stockSedabIntrnantEncours;
	}
	public void setStockSedabIntrnantEncours(double stockSedabIntrnantEncours) {
		this.stockSedabIntrnantEncours = stockSedabIntrnantEncours;
	}
	public float getVentesTotalIntrnantEncours() {
		return ventesTotalIntrnantEncours;
	}
	public void setVentesTotalIntrnantEncours(float ventesTotalIntrnantEncours) {
		this.ventesTotalIntrnantEncours = ventesTotalIntrnantEncours;
	}
	public void setPictoImages(String pictoImages) {
		this.pictoImages = pictoImages;
	}
	public boolean isCreateOrUpdate() {
		return createOrUpdate;
	}
	public void setCreateOrUpdate(boolean createOrUpdate) {
		this.createOrUpdate = createOrUpdate;
	}
	public String getMsgRetour() {
		return msgRetour;
	}
	public void setMsgRetour(String msgRetour) {
		this.msgRetour = msgRetour;
	}
	public Long getIdDepartement() {
		return idDepartement;
	}
	public void setIdDepartement(Long idDepartement) {
		this.idDepartement = idDepartement;
	}
	public String getDepartement() {
		return departement;
	}
	public void setDepartement(String departement) {
		this.departement = departement;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public Long getIdPointDeVenteReceptionBL() {
		return idPointDeVenteReceptionBL;
	}
	public void setIdPointDeVenteReceptionBL(Long idPointDeVenteReceptionBL) {
		this.idPointDeVenteReceptionBL = idPointDeVenteReceptionBL;
	}

	
	
	
}
