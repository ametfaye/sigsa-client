package org.cages.moulinette.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

public class BudgetDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5302990311959163736L;

	
	private Long id;


	private String libelle;

	private Date datedebut;

	private Date datefin;
	
	private String zone;
	
	private float montantAlloue;
	
	private float montantUtilise;
	
	@Transient
	private float tauxUtilisation;
	
	
	private String collaborateur;
	
	private Long idcollaborateur;

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

	public Date getDatedebut() {
		return datedebut;
	}

	public void setDatedebut(Date datedebut) {
		this.datedebut = datedebut;
	}

	public Date getDatefin() {
		return datefin;
	}

	public void setDatefin(Date datefin) {
		this.datefin = datefin;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public float getMontantAlloue() {
		return montantAlloue;
	}

	public void setMontantAlloue(float montantAlloue) {
		this.montantAlloue = montantAlloue;
	}

	public float getMontantUtilise() {
		return montantUtilise;
	}

	public void setMontantUtilise(float montantUtilise) {
		this.montantUtilise = montantUtilise;
	}

	public float getTauxUtilisation() {
		return tauxUtilisation;
	}

	public void setTauxUtilisation(float tauxUtilisation) {
		this.tauxUtilisation = tauxUtilisation;
	}

	public String getCollaborateur() {
		return collaborateur;
	}

	public void setCollaborateur(String collaborateur) {
		this.collaborateur = collaborateur;
	}

	public Long getIdcollaborateur() {
		return idcollaborateur;
	}

	public void setIdcollaborateur(Long idcollaborateur) {
		this.idcollaborateur = idcollaborateur;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String observations;



}
