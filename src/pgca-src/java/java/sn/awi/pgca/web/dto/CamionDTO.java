package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class CamionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3311897253615919905L;

	private Long idCamion;

	private Long idTransporteur;
	
	private String imatCamion;
	
	private String nomTransporteur;
	
	private String nomChauffeur;
	
	private Long idChauffeur;

	private float capacite;
	
	private String adresseTranporteur;
	
	private String contactTransporteur;
	
	private String contactChauffeur;
	
	
	
	public float getCapacite() {
		return capacite;
	}

	public void setCapacite(float capacite) {
		this.capacite = capacite;
	}

	public String getAdresseTranporteur() {
		return adresseTranporteur;
	}

	public void setAdresseTranporteur(String adresseTranporteur) {
		this.adresseTranporteur = adresseTranporteur;
	}

	public String getContactTransporteur() {
		return contactTransporteur;
	}

	public void setContactTransporteur(String contactTransporteur) {
		this.contactTransporteur = contactTransporteur;
	}

	public String getContactChauffeur() {
		return contactChauffeur;
	}

	public void setContactChauffeur(String contactChauffeur) {
		this.contactChauffeur = contactChauffeur;
	}

	public Long getIdCamion() {
		return idCamion;
	}

	public void setIdCamion(Long idCamion) {
		this.idCamion = idCamion;
	}

	public Long getIdTransporteur() {
		return idTransporteur;
	}

	public void setIdTransporteur(Long idTransporteur) {
		this.idTransporteur = idTransporteur;
	}

	public String getImatCamion() {
		return imatCamion;
	}

	public void setImatCamion(String imatCamion) {
		this.imatCamion = imatCamion;
	}

	public String getNomTransporteur() {
		return nomTransporteur;
	}

	public void setNomTransporteur(String nomTransporteur) {
		this.nomTransporteur = nomTransporteur;
	}

	public Long getIdChauffeur() {
		return idChauffeur;
	}

	public void setIdChauffeur(Long idChauffeur) {
		this.idChauffeur = idChauffeur;
	}

	public String getNomChauffeur() {
		return nomChauffeur;
	}

	public void setNomChauffeur(String nomChauffeur) {
		this.nomChauffeur = nomChauffeur;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
