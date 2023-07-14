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
@Table(name="pgca_chauffeur")
public class Chauffeur implements Serializable, GenericModel {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7643121102813291538L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="chauff_id")
	private Long id;

	@Column(length=100)
	private String libelle;
	
	@ManyToOne
	private Transporteur transporteur;
	
	@ManyToOne
	@JoinColumn(name = "pers_id", nullable = false)
	private Personne chauffeur;

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

	
	public Personne getChauffeur() {
		return chauffeur;
	}

	public void setChauffeur(Personne chauffeur) {
		this.chauffeur = chauffeur;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Transporteur getTransporteur() {
		return transporteur;
	}

	public void setTransporteur(Transporteur transporteur) {
		this.transporteur = transporteur;
	}
	
	
}
