package sn.awi.pgca.dataModel;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="pers_personne")
public class Personne implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="pers_id")
	private Long id;

	@Column(length=50)
	private String nom;

	@Column(length=50)
	private String prenom;

	@Temporal(TemporalType.DATE)
	private Date datenaissance;
	
	@Column(length=50)
	private String lieudenaissance;

	@Column(length=1)
	private String situationmat; // C : Célibataire M : Marié D : Divorcé V : Veuf

	@Column(length=50)
	private String nationalite;
	
	
	@Column
	private String numeroCNI;
	

	private char civilite; // 1 : Mr 2 : Mme 3 : Mlle
	
	@Column(name="fonction")  // 1 : Agent ? :  Chauffeur  : 5 : 
	private Long fonction;
	
	@OneToOne
	@JoinColumn(name = "adresse_id", nullable = true)
	private Adresse adresse; 


	@OneToOne
	@JoinColumn(name = "contact_id", nullable = true)
	private Contact contact;


	
	public Personne clone() {
	    Personne personne = new Personne();
	    
	    return personne;
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

	public Date getDatenaissance() {
		return datenaissance;
	}

	public void setDatenaissance(Date datenaissance) {
		this.datenaissance = datenaissance;
	}

	public String getLieudenaissance() {
		return lieudenaissance;
	}

	public void setLieudenaissance(String lieudenaissance) {
		this.lieudenaissance = lieudenaissance;
	}

	public String getSituationmat() {
		return situationmat;
	}

	public void setSituationmat(String situationmat) {
		this.situationmat = situationmat;
	}

	public String getNationalite() {
		return nationalite;
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}
	
	public char getCivilite() {
		return civilite;
	}

	public void setCivilite(char civilite) {
		this.civilite = civilite;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Long getFonction() {
		return fonction;
	}

	public void setFonction(Long fonction) {
		this.fonction = fonction;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNumeroCNI() {
		return numeroCNI;
	}

	public void setNumeroCNI(String numeroCNI) {
		this.numeroCNI = numeroCNI;
	}

}
