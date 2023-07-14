package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PointDeVenteDTO implements Serializable {
	
	private static final long serialVersionUID = 4740612708609554436L;
	
	private Long   idPv;
	private String libelle;
	private String adresse;
	private String gerant;
	private String contactGerant;
	private String region; 
	private String departement; 
	private String commune;

	/* Utils for service connexe */
	private Long idGerant;
	private Long idStockReference;
	private Long idAdresse;
	private Long idstockReference;
	private Long idRegion;
	private Long idDepartement;
	private Long idCommune;
	private String libelleAdresse;
	

	
	private String zone;
	private String tagsMagasin;
	private String stockageMax;
	private String stockageOcccupe;
	private String programmmeConcerne; 
	
	
	/***  Infos utilises par les MAP HightChart et autres */
	private String codeRegion;
	private String descPv;
	private String descStock;
	private Map<String,  Long> listProduitOfPointDeVente  =  new HashMap<String, Long>();
	private List<ProduitDTO>  stockPointDeVente ;
	
	
	public Long getIdPv() {
		return idPv;
	}
	public void setIdPv(Long idPv) {
		this.idPv = idPv;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getGerant() {
		return gerant;
	}
	public void setGerant(String gerant) {
		this.gerant = gerant;
	}
	public String getContactGerant() {
		return contactGerant;
	}
	public void setContactGerant(String contactGerant) {
		this.contactGerant = contactGerant;
	}

	
	public Long getIdGerant() {
		return idGerant;
	}
	public void setIdGerant(Long idGerant) {
		this.idGerant = idGerant;
	}
	public Long getIdStockReference() {
		return idStockReference;
	}
	public void setIdStockReference(Long idStockReference) {
		this.idStockReference = idStockReference;
	}
	public Long getIdAdresse() {
		return idAdresse;
	}
	public void setIdAdresse(Long idAdresse) {
		this.idAdresse = idAdresse;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getTagsMagasin() {
		return tagsMagasin;
	}
	public void setTagsMagasin(String tagsMagasin) {
		this.tagsMagasin = tagsMagasin;
	}
	public String getStockageMax() {
		return stockageMax;
	}
	public void setStockageMax(String stockageMax) {
		this.stockageMax = stockageMax;
	}
	public String getStockageOcccupe() {
		return stockageOcccupe;
	}
	public void setStockageOcccupe(String stockageOcccupe) {
		this.stockageOcccupe = stockageOcccupe;
	}
	public String getProgrammmeConcerne() {
		return programmmeConcerne;
	}
	public void setProgrammmeConcerne(String programmmeConcerne) {
		this.programmmeConcerne = programmmeConcerne;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getIdstockReference() {
		return idstockReference;
	}
	public void setIdstockReference(Long idstockReference) {
		this.idstockReference = idstockReference;
	}
	public Long getIdRegion() {
		return idRegion;
	}
	public void setIdRegion(Long idRegion) {
		this.idRegion = idRegion;
	}
	public Long getIdDepartement() {
		return idDepartement;
	}
	public void setIdDepartement(Long idDepartement) {
		this.idDepartement = idDepartement;
	}
	public Long getIdCommune() {
		return idCommune;
	}
	public void setIdCommune(Long idCommune) {
		this.idCommune = idCommune;
	}
	public String getLibelleAdresse() {
		return libelleAdresse;
	}
	public void setLibelleAdresse(String libelleAdresse) {
		this.libelleAdresse = libelleAdresse;
	}
	public String getDepartement() {
		return departement;
	}
	public void setDepartement(String departement) {
		this.departement = departement;
	}
	public String getCommune() {
		return commune;
	}
	public void setCommune(String commune) {
		this.commune = commune;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCodeRegion() {
		return codeRegion;
	}
	public void setCodeRegion(String codeRegion) {
		this.codeRegion = codeRegion;
	}
	public String getDescPv() {
		return descPv;
	}
	public void setDescPv(String descPv) {
		this.descPv = descPv;
	}
	public String getDescStock() {
		return descStock;
	}
	public void setDescStock(String descStock) {
		this.descStock = descStock;
	}
	public Map<String, Long> getListProduitOfPointDeVente() {
		return listProduitOfPointDeVente;
	}
	public void setListProduitOfPointDeVente(Map<String, Long> listProduitOfPointDeVente) {
		this.listProduitOfPointDeVente = listProduitOfPointDeVente;
	}
	public List<ProduitDTO> getStockPointDeVente() {
		return stockPointDeVente;
	}
	public void setStockPointDeVente(List<ProduitDTO> stockPointDeVente) {
		this.stockPointDeVente = stockPointDeVente;
	}

	
	
	
}
