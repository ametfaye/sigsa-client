package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class RechercheMiseEnPlaceDTO implements Serializable {

	private static final long	serialVersionUID	= 5716322277126142779L;
	
	String dateDebut;
	String dateFin;
	String nomProgramme;
	Long idProgr;
	
	String nomDepartement;
	Long idDepartement; 
	
	String nomTransporteur;
	Long idTransporteur;
	
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
	public Long getIdProgr() {
		return idProgr;
	}
	public Long getIdDepartement() {
		return idDepartement;
	}
	public void setIdDepartement(Long idDepartement) {
		this.idDepartement = idDepartement;
	}
	public Long getIdTransporteur() {
		return idTransporteur;
	}
	public void setIdTransporteur(Long idTransporteur) {
		this.idTransporteur = idTransporteur;
	}
	public void setIdProgr(Long idProgr) {
		this.idProgr = idProgr;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}