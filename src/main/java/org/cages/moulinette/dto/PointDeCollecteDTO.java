package org.cages.moulinette.dto;

import java.io.Serializable;

public class PointDeCollecteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6939524780255140201L;
	
	private long 	idPointdeCollecte ;

	private String							idPersonne;
	
	private long							idPointDeCollecte;

	
	private String departement;
	private String commune;
	private String contactGerant;
	
	private String nom;


	private String type;
	
    private String code;

	private String libelle;

    private String gerant;
    
    private String tel;

    private String mail;
    
    private String adresse;
    
    private String region;
    
    private String superficie;

	public String getIdPersonne() {
		return idPersonne;
	}

	public void setIdPersonne(String idPersonne) {
		this.idPersonne = idPersonne;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getGerant() {
		return gerant;
	}

	public void setGerant(String gerant) {
		this.gerant = gerant;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSuperficie() {
		return superficie;
	}

	public void setSuperficie(String superficie) {
		this.superficie = superficie;
	}

	public long getIdPointDeCollecte() {
		return idPointDeCollecte;
	}

	public void setIdPointDeCollecte(long idPointDeCollecte) {
		this.idPointDeCollecte = idPointDeCollecte;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getIdPointdeCollecte() {
		return idPointdeCollecte;
	}

	public void setIdPointdeCollecte(long idPointdeCollecte) {
		this.idPointdeCollecte = idPointdeCollecte;
	}

	public String getDepartement() {
		return departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}

	public String getContactGerant() {
		return contactGerant;
	}

	public void setContactGerant(String contactGerant) {
		this.contactGerant = contactGerant;
	}
    

}
