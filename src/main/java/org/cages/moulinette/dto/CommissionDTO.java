package org.cages.moulinette.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommissionDTO implements Serializable {

	private static final long serialVersionUID = 9125422905791273379L;

	private List<CommissionDTO> mock;

	private Long id_com;

	private String personne;

	private float montant;

	private String zone;

	private String nbVentes;
	// numero
	// temporraire

	public List<CommissionDTO> getMock() {
		return mock;
	}

	public void setMock(List<CommissionDTO> mock) {
		this.mock = mock;
	}

	public Long getId_com() {
		return id_com;
	}

	public void setId_com(Long id_com) {
		this.id_com = id_com;
	}

	public String getPersonne() {
		return personne;
	}

	public void setPersonne(String personne) {
		this.personne = personne;
	}

	public float isMontant() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}

	public String isZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getNbVentes() {
		return nbVentes;
	}

	public void setNbVentes(String nbVentes) {
		this.nbVentes = nbVentes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public float getMontant() {
		return montant;
	}

	public String getZone() {
		return zone;
	}

}
