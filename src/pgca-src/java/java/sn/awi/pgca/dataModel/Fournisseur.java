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
@Table(name="pgca_fournisseur")
public class Fournisseur implements Serializable, GenericModel {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7643121102813291538L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="four_id")
	private Long id;

	@Column(length=100, nullable = false)
	private String libelle;
	
	@Column(length=100)
	private String provenance;
	
	@Column(length=100)
	private String representantCivil;
	
	@Column(length=100)
	private String representantTelephone;
	
	
	
	@ManyToOne
	@JoinColumn(name = "adrs_id", nullable = true)
	private Adresse adresse;
	
	@ManyToOne
	@JoinColumn(name = "cont_id", nullable = true)
	private Contact contact;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public String getRepresentantCivil() {
		return representantCivil;
	}

	public void setRepresentantCivil(String representantCivil) {
		this.representantCivil = representantCivil;
	}

	public String getRepresentantTelephone() {
		return representantTelephone;
	}

	public void setRepresentantTelephone(String representantTelephone) {
		this.representantTelephone = representantTelephone;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
