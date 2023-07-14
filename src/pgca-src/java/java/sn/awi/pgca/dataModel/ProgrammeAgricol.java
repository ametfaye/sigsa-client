package sn.awi.pgca.dataModel;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "pgca_programme")
public class ProgrammeAgricol implements Serializable, GenericModel, Cloneable {

	private static final long					serialVersionUID	= 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pgca_idprog")
	private Long											pgca_idprog; 
	
	
	@OneToOne
	@JoinColumn(name = "pgca_idca", nullable = false)
	private CampagneAgricole campagne;
	
	@Column(length = 200)
	private String										libelle; 
	
	
	
	@Column(length = 200)
	private String										descriptifProgramme; 
	


	@Column
	private int										   statut;  
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date											dateOuverture;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date											dateFermeture;

	

	@Override
	public Long getId() {
		return pgca_idprog;
	}



	public Long getId_ca() {
		return pgca_idprog;
	}



	public void setId_ca(Long id_ca) {
		this.pgca_idprog = id_ca;
	}



	public CampagneAgricole getCampagne() {
		return campagne;
	}



	public void setCampagne(CampagneAgricole campagne) {
		this.campagne = campagne;
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



	public Long getPgca_idprog() {
		return pgca_idprog;
	}



	public void setPgca_idprog(Long pgca_idprog) {
		this.pgca_idprog = pgca_idprog;
	}
	
	
	public String getDescriptifProgramme() {
		return descriptifProgramme;
	}



	public void setDescriptifProgramme(String descriptifProgramme) {
		this.descriptifProgramme = descriptifProgramme;
	}


}
