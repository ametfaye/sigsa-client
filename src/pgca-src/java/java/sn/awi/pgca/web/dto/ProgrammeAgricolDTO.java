package sn.awi.pgca.web.dto;
import java.io.Serializable;
import java.util.Date;


public class ProgrammeAgricolDTO implements Serializable {

	private static final long					serialVersionUID	= 4740612708609554436L;

	
	private Long											programmeId; 
	
	

	private Long cmpagneId;
	
	
	private String										libelle; 
	
	private int										  	 statut;  

	private String										statutlibelle; 
	
	private String										campagnelibelle; 
	private String										programmelibelle; 


	

	private Date											dateOuverture;
	private Date											dateFermeture;
	
	public Long getProgrammeId() {
		return programmeId;
	}
	public void setProgrammeId(Long programmeId) {
		this.programmeId = programmeId;
	}
	public Long getCmpagneId() {
		return cmpagneId;
	}
	public void setCmpagneId(Long cmpagneId) {
		this.cmpagneId = cmpagneId;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public int getStatut() {
		return statut;
	}
	public void setStatut(int statut) {
		this.statut = statut;
	}
	public String getStatutlibelle() {
		return statutlibelle;
	}
	public void setStatutlibelle(String statutlibelle) {
		this.statutlibelle = statutlibelle;
	}
	public Date getDateOuverture() {
		return dateOuverture;
	}
	public void setDateOuverture(Date dateOuverture) {
		this.dateOuverture = dateOuverture;
	}
	public Date getDateFermeture() {
		return dateFermeture;
	}
	public void setDateFermeture(Date dateFermeture) {
		this.dateFermeture = dateFermeture;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCampagnelibelle() {
		return campagnelibelle;
	}
	public void setCampagnelibelle(String campagnelibelle) {
		this.campagnelibelle = campagnelibelle;
	}
	public String getProgrammelibelle() {
		return programmelibelle;
	}
	public void setProgrammelibelle(String programmelibelle) {
		this.programmelibelle = programmelibelle;
	}

	

}
