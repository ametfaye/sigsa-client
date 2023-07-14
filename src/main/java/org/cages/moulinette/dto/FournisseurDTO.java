package org.cages.moulinette.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


public class FournisseurDTO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7643121102813291538L;

	private String idProvider;


	private Long idFounisseur;
	private Long idPays;
	private Long idRegion;
	private Long idDepartement;
	private Long idCommune;
	private Long idStockdeReference; // 0 founisser DAKAR sinon magasin
	private Double quantiteIntrantRestant;
	private String libelleIntrantRestant;
	private String stockLibelle; // stock  de ref
		
	// en cas mise en place depuis un stock residuel d'un point de vente
	private Long  idPointdeVenteResiduel;
	private Long idStockResiduel;

	private String libelle;
	

	private String provenance;
	

	private String RepresentantCivil;
	

	private String RepresentantTelephone;
	
	private String adresse;

	public Long getIdFounisseur() {
		return idFounisseur;
	}

	public void setIdFounisseur(Long idFounisseur) {
		this.idFounisseur = idFounisseur;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getProvenance() {
		return provenance;
	}

	public void setProvenance(String provenance) {
		this.provenance = provenance;
	}

	public String getRepresentantCivil() {
		return RepresentantCivil;
	}

	public void setRepresentantCivil(String representantCivil) {
		RepresentantCivil = representantCivil;
	}

	public String getRepresentantTelephone() {
		return RepresentantTelephone;
	}

	public void setRepresentantTelephone(String representantTelephone) {
		RepresentantTelephone = representantTelephone;
	}

	public String getAdresse() {
		return adresse;
	}

	public Long getIdPays() {
		return idPays;
	}

	public void setIdPays(Long idPays) {
		this.idPays = idPays;
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

	public Long getIdStockdeReference() {
		return idStockdeReference;
	}

	public void setIdStockdeReference(Long idStockdeReference) {
		this.idStockdeReference = idStockdeReference;
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

	public String getIdProvider() {
		return idProvider;
	}

	public void setIdProvider(String idProvider) {
		this.idProvider = idProvider;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}



	public Double getQuantiteIntrantRestant() {
		return quantiteIntrantRestant;
	}

	public String getStockLibelle() {
		return stockLibelle;
	}

	public void setStockLibelle(String stockLibelle) {
		this.stockLibelle = stockLibelle;
	}

	public void setQuantiteIntrantRestant(Double quantiteIntrantRestant) {
		this.quantiteIntrantRestant = quantiteIntrantRestant;
	}

	public String getLibelleIntrantRestant() {
		return libelleIntrantRestant;
	}

	public void setLibelleIntrantRestant(String libelleIntrantRestant) {
		this.libelleIntrantRestant = libelleIntrantRestant;
	}

	public Long getIdPointdeVenteResiduel() {
		return idPointdeVenteResiduel;
	}

	public void setIdPointdeVenteResiduel(Long idPointdeVenteResiduel) {
		this.idPointdeVenteResiduel = idPointdeVenteResiduel;
	}

	public Long getIdStockResiduel() {
		return idStockResiduel;
	}

	public void setIdStockResiduel(Long idStockResiduel) {
		this.idStockResiduel = idStockResiduel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
