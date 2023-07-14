package org.cages.moulinette.dto;

import java.io.Serializable;

public class CollaborateurDTO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6939524780255140201L;

	private Long							idPersonne;

	private String nom;

	private String prenom;

	private String fonction;

    private String zone;

	public Long getIdPersonne() {
		return idPersonne;
	}

	public void setIdPersonne(Long idPersonne) {
		this.idPersonne = idPersonne;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getFonction() {
		return fonction;
	}

	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	} 


}
