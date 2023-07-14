package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class InfosGeneralesPersonneMoraleDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -186993031608143663L;

	private String denomination;
	private String nomcommercial;
	private String enseigne;
	private String sigle;
	private int duree;
	private String idFormeJuririque;
	private double capitalSocial;
	private double capitalNumeraire;
	private double capitalNature;
	
	private AdresseDTO adresseDTO;
	
	private ContactDTO contactDTO;
	
	public InfosGeneralesPersonneMoraleDTO() {
		adresseDTO = new AdresseDTO();
		contactDTO = new ContactDTO();
	}
	
	public String getDenomination() {
		return denomination;
	}
	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}
	public String getNomcommercial() {
		return nomcommercial;
	}
	public void setNomcommercial(String nomcommercial) {
		this.nomcommercial = nomcommercial;
	}
	public String getEnseigne() {
		return enseigne;
	}
	public void setEnseigne(String enseigne) {
		this.enseigne = enseigne;
	}
	public String getSigle() {
		return sigle;
	}
	public void setSigle(String sigle) {
		this.sigle = sigle;
	}
	public int getDuree() {
		return duree;
	}
	public void setDuree(int duree) {
		this.duree = duree;
	}
	public String getIdFormeJuririque() {
		return idFormeJuririque;
	}
	public void setIdFormeJuririque(String idFormeJuririque) {
		this.idFormeJuririque = idFormeJuririque;
	}
	public double getCapitalSocial() {
		return capitalSocial;
	}
	public void setCapitalSocial(double capitalSocial) {
		this.capitalSocial = capitalSocial;
	}
	public double getCapitalNumeraire() {
		return capitalNumeraire;
	}
	public void setCapitalNumeraire(double capitalNumeraire) {
		this.capitalNumeraire = capitalNumeraire;
	}
	public double getCapitalNature() {
		return capitalNature;
	}
	public void setCapitalNature(double capitalNature) {
		this.capitalNature = capitalNature;
	}
	public AdresseDTO getAdresseDTO() {
		return adresseDTO;
	}
	public void setAdresseDTO(AdresseDTO adresseDTO) {
		this.adresseDTO = adresseDTO;
	}
	public ContactDTO getContactDTO() {
		return contactDTO;
	}
	public void setContactDTO(ContactDTO contactDTO) {
		this.contactDTO = contactDTO;
	}
}
