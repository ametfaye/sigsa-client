package sn.awi.pgca.web.dto;

import java.io.Serializable;


public class ProduitDTO implements Serializable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	private Long idProduit;
	private Long idOrdre;
	private Double quantite;
	private Double quantiteProduitAvendre;   
	private float quantiteProduitAvendreFLOAT;   
	private String libelle;  
	private Long idtypeProduit; 
	private Long idStockProduit;
	private Long idCampagne;
	private Long idProgramme;
	private String uniteDeMesure;
	private String tagsProduit;
	private String codeProduit;
	private String stockref;
	private String typeProduit;
	private String libelleTypeProduit;  
	private String libelleProgramme;
	private String libelleCampagne;
	private String libellePointdeStock;
	private String libelleProduit;
	private String pointdeVente;
	private String pointdeCollecte;
	private Long idpointdeVente;
	private String pictoImages;
	private String provenance;
	private Float subvention;
	private String msg; // utils for message de retour
	private String moyenDePaiement;
	//Tarif produit
	private Float prixUnitaire; 
	private Double prixTotal;
	//client achetant un p
	private String client; 
	private String telClient;
	private float montantPayeParCheque;
	private float montantPayeParBonSubvention;
	private float montantPayeParEspeces;
	private float montantPayeParNature;
	boolean payerLeResteParCredit ; 
	boolean deductionSuccess;
	private boolean dejaTarifie; // tarifié ou pas 

	// superviseurAyantCréé l'intrant
	private Long auteurCreationIntrant;

	
	
	public boolean isDeductionSuccess() {
		return deductionSuccess;
	}

	public void setDeductionSuccess(boolean deductionSuccess) {
		this.deductionSuccess = deductionSuccess;
	}

	public Long getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(Long idProduit) {
		this.idProduit = idProduit;
	}

	public Double getQuantite() {
		return quantite;
	}

	public void setQuantite(Double quantite) {
		this.quantite = quantite;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Long getIdtypeProduit() {
		return idtypeProduit;
	}

	public void setIdtypeProduit(Long idtypeProduit) {
		this.idtypeProduit = idtypeProduit;
	}

	public Long getIdStockProduit() {
		return idStockProduit;
	}

	public void setIdStockProduit(Long idStockProduit) {
		this.idStockProduit = idStockProduit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUniteDeMesure() {
		return uniteDeMesure;
	}

	public void setUniteDeMesure(String uniteDeMesure) {
		this.uniteDeMesure = uniteDeMesure;
	}

	public String getTagsProduit() {
		return tagsProduit;
	}

	public void setTagsProduit(String tagsProduit) {
		this.tagsProduit = tagsProduit;
	}

	public String getCodeProduit() {
		return codeProduit;
	}

	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}

	public String getStockref() {
		return stockref;
	}

	public void setStockref(String stockref) {
		this.stockref = stockref;
	}

	public String getTypeProduit() {
		return typeProduit;
	}

	public void setTypeProduit(String typeProduit) {
		this.typeProduit = typeProduit;
	}

	public String getLibelleTypeProduit() {
		return libelleTypeProduit;
	}

	public void setLibelleTypeProduit(String libelleTypeProduit) {
		this.libelleTypeProduit = libelleTypeProduit;
	}

	public String getLibelleProgramme() {
		return libelleProgramme;
	}

	public void setLibelleProgramme(String libelleProgramme) {
		this.libelleProgramme = libelleProgramme;
	}

	public String getLibelleCampagne() {
		return libelleCampagne;
	}

	public void setLibelleCampagne(String libelleCampagne) {
		this.libelleCampagne = libelleCampagne;
	}

	public String getLibellePointdeStock() {
		return libellePointdeStock;
	}

	public void setLibellePointdeStock(String libellePointdeStock) {
		this.libellePointdeStock = libellePointdeStock;
	}

	public String getLibelleProduit() {
		return libelleProduit;
	}

	public void setLibelleProduit(String libelleProduit) {
		this.libelleProduit = libelleProduit;
	}

	public String getPointdeVente() {
		return pointdeVente;
	}

	public void setPointdeVente(String pointdeVente) {
		this.pointdeVente = pointdeVente;
	}

	public String getPointdeCollecte() {
		return pointdeCollecte;
	}

	public void setPointdeCollecte(String pointdeCollecte) {
		this.pointdeCollecte = pointdeCollecte;
	}

	public Long getIdpointdeVente() {
		return idpointdeVente;
	}

	public void setIdpointdeVente(Long idpointdeVente) {
		this.idpointdeVente = idpointdeVente;
	}

	public String getPictoImages() {
		return pictoImages;
	}

	public void setPictoImages(String pictoImages) {
		this.pictoImages = pictoImages;
	}

	public String getProvenance() {
		return provenance;
	}

	public void setProvenance(String provenance) {
		this.provenance = provenance;
	}

	public Long getIdCampagne() {
		return idCampagne;
	}

	public void setIdCampagne(Long idCampagne) {
		this.idCampagne = idCampagne;
	}

	public Float getPrixUnitaire() {
		return prixUnitaire;
	}

	public void setPrixUnitaire(Float prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	public Double getPrixTotal() {
		return prixTotal;
	}

	public void setPrixTotal(Double prixTotal) {
		this.prixTotal = prixTotal;
	}

	public Long getIdProgramme() {
		return idProgramme;
	}

	public void setIdProgramme(Long idProgramme) {
		this.idProgramme = idProgramme;
	}

	public String getMoyenDePaiement() {
		return moyenDePaiement;
	}

	public void setMoyenDePaiement(String moyenDePaiement) {
		this.moyenDePaiement = moyenDePaiement;
	}

	public Double getQuantiteProduitAvendre() {
		return quantiteProduitAvendre;
	}

	public void setQuantiteProduitAvendre(Double quantiteProduitAvendre) {
		this.quantiteProduitAvendre = quantiteProduitAvendre;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public float getMontantPayeParCheque() {
		return montantPayeParCheque;
	}

	public void setMontantPayeParCheque(float montantPayeParCheque) {
		this.montantPayeParCheque = montantPayeParCheque;
	}

	public float getMontantPayeParBonSubvention() {
		return montantPayeParBonSubvention;
	}

	public void setMontantPayeParBonSubvention(float montantPayeParBonSubvention) {
		this.montantPayeParBonSubvention = montantPayeParBonSubvention;
	}

	public float getMontantPayeParEspeces() {
		return montantPayeParEspeces;
	}

	public void setMontantPayeParEspeces(float montantPayeParEspeces) {
		this.montantPayeParEspeces = montantPayeParEspeces;
	}

	public float getMontantPayeParNature() {
		return montantPayeParNature;
	}

	public void setMontantPayeParNature(float montantPayeParNature) {
		this.montantPayeParNature = montantPayeParNature;
	}

	public boolean isPayerLeResteParCredit() {
		return payerLeResteParCredit;
	}

	public void setPayerLeResteParCredit(boolean payerLeResteParCredit) {
		this.payerLeResteParCredit = payerLeResteParCredit;
	}

	public String getTelClient() {
		return telClient;
	}

	public void setTelClient(String telClient) {
		this.telClient = telClient;
	}

	public float getQuantiteProduitAvendreFLOAT() {
		return quantiteProduitAvendreFLOAT;
	}

	public void setQuantiteProduitAvendreFLOAT(float quantiteProduitAvendreFLOAT) {
		this.quantiteProduitAvendreFLOAT = quantiteProduitAvendreFLOAT;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Float getSubvention() {
		return subvention;
	}

	public void setSubvention(Float subvention) {
		this.subvention = subvention;
	}

	public Long getIdOrdre() {
		return idOrdre;
	}

	public void setIdOrdre(Long idOrdre) {
		this.idOrdre = idOrdre;
	}

	public boolean isDejaTarifie() {
		return dejaTarifie;
	}

	public Long getAuteurCreationIntrant() {
		return auteurCreationIntrant;
	}

	public void setAuteurCreationIntrant(Long auteurCreationIntrant) {
		this.auteurCreationIntrant = auteurCreationIntrant;
	}

	public void setDejaTarifie(boolean dejaTarifie) {
		this.dejaTarifie = dejaTarifie;
	}


	
	
}
