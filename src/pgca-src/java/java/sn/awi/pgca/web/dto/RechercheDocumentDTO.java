package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class RechercheDocumentDTO implements Serializable {

	private static final long	serialVersionUID	= 5716322277126142779L;

	
	String dateDebut;
	String dateFin;
	String nomProgramme;
	
	String nomDepartement;
	String nomTransporteur;
	Long idIntrant;
	public String getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}
	public String getDateFin() {
		return dateFin;
	}
	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}
	public String getNomProgramme() {
		return nomProgramme;
	}
	public void setNomProgramme(String nomProgramme) {
		this.nomProgramme = nomProgramme;
	}
	public String getNomDepartement() {
		return nomDepartement;
	}
	public void setNomDepartement(String nomDepartement) {
		this.nomDepartement = nomDepartement;
	}
	public String getNomTransporteur() {
		return nomTransporteur;
	}
	public void setNomTransporteur(String nomTransporteur) {
		this.nomTransporteur = nomTransporteur;
	}
	public Long getIdIntrant() {
		return idIntrant;
	}
	public void setIdIntrant(Long idIntrant) {
		this.idIntrant = idIntrant;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}