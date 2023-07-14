package org.cages.moulinette.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="pgca_pointDeVente")
public class PointDeVente implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ptv_id")
	private Long ptv_id;

	@Column(length=50)
	private String libelle;
	
	@Column(length=30)
	private String code;
	


	@OneToOne
	@JoinColumn(name = "stock_id", nullable = false)
	private Stock stock;
	
	@ManyToOne
	@JoinColumn(name = "pers_id", nullable = true)
	private Personne gerant;
	
	@OneToOne
	@JoinColumn(name = "derniere_adressepostale_id", nullable = true)
	private Adresse adresse;

	@OneToOne
	@JoinColumn(name = "dernier_contact_id", nullable = true)
	private Contact contact;
	
	@OneToOne
	@JoinColumn(name = "commune_id", nullable = false)
	private Commune commune;
	
	
	@ManyToOne
	@JoinColumn(name = "pgca_idca", nullable = true)
	private CampagneAgricole campagneConcerne; // Stock auquel appartient le produit
	// TYPE 1  = MAGASIN PERMENANT  ----  TYPE 2  = POINT DE VENTE =  MAGaSIN TEMPORAIRE
	Integer typeMag;
	
	@Column(length=500)
	private String descriptif;
	
	@Column()
	Double stockageMax;
	

	@Transient  
	private boolean estaffecte;
	
	
	public Long getPtv_id() {
		return ptv_id;
	}


	public void setPtv_id(Long ptv_id) {
		this.ptv_id = ptv_id;
	}


	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public Stock getStock() {
		return stock;
	}


	public void setStock(Stock stock) {
		this.stock = stock;
	}



	public Adresse getAdresse() {
		return adresse;
	}


	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}


	public Contact getContact() {
		return contact;
	}


	public void setContact(Contact contact) {
		this.contact = contact;
	}


	public CampagneAgricole getCampagneConcerne() {
		return campagneConcerne;
	}


	public void setCampagneConcerne(CampagneAgricole campagneConcerne) {
		this.campagneConcerne = campagneConcerne;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public Personne getGerant() {
		return gerant;
	}


	public void setGerant(Personne gerant) {
		this.gerant = gerant;
	}


	public Commune getCommune() {
		return commune;
	}


	public void setCommune(Commune commune) {
		this.commune = commune;
	}
	
	

	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}



	public String getDescriptif() {
		return descriptif;
	}


	public double getStockageMax() {
		return stockageMax;
	}


	public void setStockageMax(double stockageMax) {
		this.stockageMax = stockageMax;
	}


	public void setDescriptif(String descriptif) {
		this.descriptif = descriptif;
	}



	public void setStockageMax(Double stockageMax) {
		this.stockageMax = stockageMax;
	}


	public Integer getTypeMag() {
		return typeMag;
	}


	public void setTypeMag(Integer typeMag) {
		this.typeMag = typeMag;
	}


	public boolean isEstaffecte() {
		return estaffecte;
	}


	public void setEstaffecte(boolean estaffecte) {
		this.estaffecte = estaffecte;
	}
	
}
