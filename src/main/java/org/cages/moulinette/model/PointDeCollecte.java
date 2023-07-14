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
@Table(name="pgca_pointDeCollecte")
public class PointDeCollecte  {
	
	private static final long serialVersionUID = 4740612708609554436L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="pdc_id")
	private Long pdc_id;  

	
	@OneToOne
	@JoinColumn(name = "stock_id", nullable = false)
	private Stock stock;
	
	

	@Column(length=100)
	private String libelle;
	
	@Column
	private Integer superficie ;
	
	@Transient
	private float superficieLibre;
	
	@Transient
	private float superficieOccupe;
	

	
	@ManyToOne
	@JoinColumn(name = "pers_id", nullable = false)
	private Personne gerant;
	
	
	@ManyToOne
	@JoinColumn(name = "adrs_id", nullable = true)
	private Adresse adresse;

	@ManyToOne
	@JoinColumn(name = "cont_id", nullable = true)
	private Contact contact;
	
	
	@ManyToOne
	@JoinColumn(name = "pgca_idca", nullable = true)
	private CampagneAgricole campagneConcerne; // Stock auquel appartient le produit
	

	public Contact getContact() {
		return contact;
	}
  
	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Long getId() {
		return pdc_id;
	}
  
	
	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public Adresse getAdresse() {
		return adresse;
	}


	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public Integer getSuperficie() {
		return superficie;
	}

	public void setSuperficie(Integer superficie) {
		this.superficie = superficie;
	}

	public Personne getGerant() {
		return gerant;
	}

	public void setGerant(Personne gerant) {
		this.gerant = gerant;
	}

	public void setId(Long id) {
		this.pdc_id = id;
	}

	public float isSuperficieLibre() {
		return superficieLibre;
	}

	public void setSuperficieLibre(float superficieLibre) {
		this.superficieLibre = superficieLibre;
	}

	public float isSuperficieOccupe() {
		return superficieOccupe;
	}

	public void setSuperficieOccupe(float superficieOccupe) {
		this.superficieOccupe = superficieOccupe;
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

	public float getSuperficieLibre() {
		return superficieLibre;
	}

	public float getSuperficieOccupe() {
		return superficieOccupe;
	}

	public Long getPdc_id() {
		return pdc_id;
	}

	public void setPdc_id(Long pdc_id) {
		this.pdc_id = pdc_id;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}


}
