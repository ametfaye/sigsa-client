package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class SubventionDTO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1656387068195094496L;

	
	private Long id_programme;
	
	private String libelleProgramme;

	
	private Long id_subvention;  

	private float montantSubvention;
	
	private String detailsSubvention;
	
	private Date dateSubvention;
	
	private String referenceVentes;
	
	// info recupéré depuis la vente
	private String nomClientBenefiaire;
	private String zone;
	
	
	public Long getId_subvention() {
		return id_subvention;
	}

	public void setId_subvention(Long id_subvention) {
		this.id_subvention = id_subvention;
	}

	public float getMontantSubvention() {
		return montantSubvention;
	}

	public void setMontantSubvention(float montantSubvention) {
		this.montantSubvention = montantSubvention;
	}

	public String getDetailsSubvention() {
		return detailsSubvention;
	}

	public void setDetailsSubvention(String detailsSubvention) {
		this.detailsSubvention = detailsSubvention;
	}

	public Date getDateSubvention() {
		return dateSubvention;
	}

	public void setDateSubvention(Date dateSubvention) {
		this.dateSubvention = dateSubvention;
	}

	public String getReferenceVentes() {
		return referenceVentes;
	}

	public void setReferenceVentes(String referenceVentes) {
		this.referenceVentes = referenceVentes;
	}

	public Long getId_programme() {
		return id_programme;
	}

	public void setId_programme(Long id_programme) {
		this.id_programme = id_programme;
	}

	public String getNomClientBenefiaire() {
		return nomClientBenefiaire;
	}

	public void setNomClientBenefiaire(String nomClientBenefiaire) {
		this.nomClientBenefiaire = nomClientBenefiaire;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getLibelleProgramme() {
		return libelleProgramme;
	}

	public void setLibelleProgramme(String libelleProgramme) {
		this.libelleProgramme = libelleProgramme;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
