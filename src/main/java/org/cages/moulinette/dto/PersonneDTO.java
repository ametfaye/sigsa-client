package org.cages.moulinette.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO pour informations relative a la personne
 * 
 * @author AWA Consulting
 */
public class PersonneDTO implements Serializable {

	private static final long serialVersionUID = 3635592962851840689L;

	private Long id;
	
	private Long idUtilisateur;
	
	private Long idPersonne;

	private String civilite;

	private String nom;

	private String prenom;

	private String fonction;

	private String nationalite;

	private String lieunaissance;

	private String situationmat;

	private Date dateNais;

	private String strDateNais;

	private String strDateNais2;

	private AdresseDTO adresse;

	private String numeroDocumentIdentification;

	private Long idDocumentIdentification;

	private String typeDocumentIdentification;

	private Date dateDebutDocumentIdentification;

	private Date dateFinDocumentIdentification;

	private String numerotmp;

	private String civilitePrenomNom;

	public PersonneDTO() {
		adresse = new AdresseDTO();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getNationalite() {
		return nationalite;
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}

	public String getLieunaissance() {
		return lieunaissance;
	}

	public void setLieunaissance(String lieunaissance) {
		this.lieunaissance = lieunaissance;
	}

	public String getSituationmat() {
		return situationmat;
	}

	public void setSituationmat(String situationmat) {
		this.situationmat = situationmat;
	}

	public String getCivilite() {
		return civilite;
	}

	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}

	public Date getDateNais() {
		return dateNais;
	}

	public void setDateNais(Date dateNais) {
		this.dateNais = dateNais;
	}

	public AdresseDTO getAdresse() {
		return adresse;
	}

	public void setAdresse(AdresseDTO adresse) {
		this.adresse = adresse;
	}

	public Long getIdDocumentIdentification() {
		return idDocumentIdentification;
	}

	public String getFonction() {
		return fonction;
	}

	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	public void setIdDocumentIdentification(Long idDocumentIdentification) {
		this.idDocumentIdentification = idDocumentIdentification;
	}

	public String getNumeroDocumentIdentification() {
		return numeroDocumentIdentification;
	}

	public void setNumeroDocumentIdentification(String numeroDocumentIdentification) {
		this.numeroDocumentIdentification = numeroDocumentIdentification;
	}

	public String getTypeDocumentIdentification() {
		return typeDocumentIdentification;
	}

	public void setTypeDocumentIdentification(String typeDocumentIdentification) {
		this.typeDocumentIdentification = typeDocumentIdentification;
	}





	public String getNumerotmp() {
		return numerotmp;
	}

	public void setNumerotmp(String numerotmp) {
		this.numerotmp = numerotmp;
	}

	public String getStrDateNais() {
		return strDateNais;
	}

	public void setStrDateNais(String strDateNais) {
		this.strDateNais = strDateNais;
	}

	public String getStrDateNais2() {
		return strDateNais2;
	}

	public void setStrDateNais2(String strDateNais2) {
		this.strDateNais2 = strDateNais2;
	}

	public String getCivilitePrenomNom() {
		return civilitePrenomNom;
	}

	public void setCivilitePrenomNom(String civilitePrenomNom) {
		this.civilitePrenomNom = civilitePrenomNom;
	}

	public Date getDateDebutDocumentIdentification() {
		return dateDebutDocumentIdentification;
	}

	public void setDateDebutDocumentIdentification(Date dateDebutDocumentIdentification) {
		this.dateDebutDocumentIdentification = dateDebutDocumentIdentification;
	}

	public Date getDateFinDocumentIdentification() {
		return dateFinDocumentIdentification;
	}

	public void setDateFinDocumentIdentification(Date dateFinDocumentIdentification) {
		this.dateFinDocumentIdentification = dateFinDocumentIdentification;
	}

	public Long getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public Long getIdPersonne() {
		return idPersonne;
	}

	public void setIdPersonne(Long idPersonne) {
		this.idPersonne = idPersonne;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}