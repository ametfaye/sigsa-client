package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class PartiePrenanteSureteDTO implements Serializable {

	private static final long	serialVersionUID	= 5178501443954613548L;

	private String						civilite;
	private String						nom;
	private String						prenom;
	private String						denomination;
	private String						numerorccm;
	private String						numerodeclarant;
	private AdresseDTO				adresse;
	private ContactDTO				contact;
	private AdresseDTO				electionDomicile;
	private String						nomGeneriquePartiePrenante;
	private String						libellePartiePrenante;

	public PartiePrenanteSureteDTO() {
		adresse = new AdresseDTO();
		contact = new ContactDTO();
		electionDomicile = new AdresseDTO();
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

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public String getNumerorccm() {
		return numerorccm;
	}

	public void setNumerorccm(String numerorccm) {
		this.numerorccm = numerorccm;
	}

	public String getNumerodeclarant() {
		return numerodeclarant;
	}

	public void setNumerodeclarant(String numerodeclarant) {
		this.numerodeclarant = numerodeclarant;
	}

	public AdresseDTO getAdresse() {
		return adresse;
	}

	public void setAdresse(AdresseDTO adresse) {
		this.adresse = adresse;
	}

	public ContactDTO getContact() {
		return contact;
	}

	public void setContact(ContactDTO contact) {
		this.contact = contact;
	}

	public AdresseDTO getElectionDomicile() {
		return electionDomicile;
	}

	public void setElectionDomicile(AdresseDTO electionDomicile) {
		this.electionDomicile = electionDomicile;
	}

	public String getCivilite() {
		return civilite;
	}

	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}

	public String getNomGeneriquePartiePrenante() {
		return nomGeneriquePartiePrenante;
	}

	public void setNomGeneriquePartiePrenante(String nomGeneriquePartiePrenante) {
		this.nomGeneriquePartiePrenante = nomGeneriquePartiePrenante;
	}

	public String getLibellePartiePrenante() {
		return libellePartiePrenante;
	}

	public void setLibellePartiePrenante(String libellePartiePrenante) {
		this.libellePartiePrenante = libellePartiePrenante;
	}

}
