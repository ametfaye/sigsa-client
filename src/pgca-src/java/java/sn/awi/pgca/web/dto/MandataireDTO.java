package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.Date;

public class MandataireDTO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7357709201738148656L;

	private String						id;
	private String						civilite;
	private String						nom;
	private String						prenom;
	private String						idTitre;
	private String						titre;
	private String						lieu;
	private Date							date;
	private String 						tel;
	private String						fichierSignature;
	private String						typeDemande;
	private AdresseDTO				    adresseDTO;
	private ContactDTO					contactDTO;

	public MandataireDTO() {
		adresseDTO = new AdresseDTO();
	}

	public String getCivilite() {
		return civilite;
	}

	public void setCivilite(String civilite) {
		this.civilite = civilite;
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

	public String getIdTitre() {
		return idTitre;
	}

	public void setIdTitre(String idTitre) {
		this.idTitre = idTitre;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ContactDTO getContactDTO() {
		return contactDTO;
	}

	public void setContactDTO(ContactDTO contactDTO) {
		this.contactDTO = contactDTO;
	}

	public String getFichierSignature() {
		return fichierSignature;
	}

	public String getId() {
		return id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFichierSignature(String fichierSignature) {
		this.fichierSignature = fichierSignature;
	}

	public String getTypeDemande() {
		return typeDemande;
	}

	public void setTypeDemande(String typeDemande) {
		this.typeDemande = typeDemande;
	}

	public AdresseDTO getAdresseDTO() {
		return adresseDTO;
	}

	public void setAdresseDTO(AdresseDTO adresseDTO) {
		this.adresseDTO = adresseDTO;
	}

	public void cleancacheDto() {
		id = "";
		civilite = "";
		nom = "";
		prenom = "";
		idTitre = "";
		titre = "";
		lieu = "";
		date = null;
		fichierSignature = "";
		typeDemande = "";
	}

}
