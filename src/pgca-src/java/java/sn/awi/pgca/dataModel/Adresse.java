package sn.awi.pgca.dataModel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="adrs_adresse")
public class Adresse implements Serializable, GenericModel {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="adrs_id")
	private Long id;

	@Column(length=10)
	private String codepostal;

	@Column(length=200)
	private String libelle;

	@Column(length=50)
	private String quartier;
	
	@Column(length=50)
	private String ville;

	@ManyToOne
	@JoinColumn(name = "regn_id", nullable = true) 
	private Region region;
	
	@ManyToOne
	@JoinColumn(name = "departement_id", nullable = true)
	private Departement departement;
	
	@ManyToOne
	@JoinColumn(name = "commune_id", nullable = true)
	private Commune commune;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodepostal() {
		return codepostal;
	}

	public void setCodepostal(String codepostal) {
		this.codepostal = codepostal;
	}


	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getQuartier() {
		return quartier;
	}

	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public void clearCacheDTO() {
			 id = null;
		codepostal = "";
		libelle  = "";
		 quartier = "";
		 ville = "";
		region = null;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	public Commune getCommune() {
		return commune;
	}

	public void setCommune(Commune commune) {
		this.commune = commune;
	}
	
}
