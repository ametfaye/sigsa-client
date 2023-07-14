package sn.awi.pgca.dataModel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="pgca_affectationpointdeventes")
public class AffectationsGestionPointDeVentes implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id_aff;

	@OneToOne
	@JoinColumn(name = "pgca_idprog", nullable = false)
	private ProgrammeAgricol programmeConcerne;
	
	@OneToOne
	@JoinColumn(name = "ptv_id", nullable = false)
	private PointDeVente  ptv_id; 
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private Utilisateur superviseurPointDeVente;
	
	@Column
	private Date dateAffectation;
	
	@Column
	private Date dateDesaffectation;
	
	@Column
	private Date dateModifiiAffectation;
	
	@Column
	private int statut;

	@Column
	private String auteurAffecttaion;
	
	@Column
	private String auteurDesaffecttaion;

	@Override
	public Long getId() {
		return id_aff;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId_aff() {
		return id_aff;
	}

	public ProgrammeAgricol getProgrammeConcerne() {
		return programmeConcerne;
	}

	public Utilisateur getSuperviseurPointDeVente() {
		return superviseurPointDeVente;
	}

	public Date getDateAffectation() {
		return dateAffectation;
	}

	

	public Date getDateModifiiAffectation() {
		return dateModifiiAffectation;
	}

	public int getStatut() {
		return statut;
	}

	public String getAuteurAffecttaion() {
		return auteurAffecttaion;
	}

	public String getAuteurDesaffecttaion() {
		return auteurDesaffecttaion;
	}

	public void setId_aff(Long id_aff) {
		this.id_aff = id_aff;
	}

	public void setProgrammeConcerne(ProgrammeAgricol programmeConcerne) {
		this.programmeConcerne = programmeConcerne;
	}



	public void setSuperviseurPointDeVente(Utilisateur superviseurPointDeVente) {
		this.superviseurPointDeVente = superviseurPointDeVente;
	}

	public void setDateAffectation(Date dateAffectation) {
		this.dateAffectation = dateAffectation;
	}

	

	public Date getDateDesaffectation() {
		return dateDesaffectation;
	}

	public void setDateDesaffectation(Date dateDesaffectation) {
		this.dateDesaffectation = dateDesaffectation;
	}

	public void setDateModifiiAffectation(Date dateModifiiAffectation) {
		this.dateModifiiAffectation = dateModifiiAffectation;
	}

	public void setStatut(int statut) {
		this.statut = statut;
	}

	public void setAuteurAffecttaion(String auteurAffecttaion) {
		this.auteurAffecttaion = auteurAffecttaion;
	}

	public void setAuteurDesaffecttaion(String auteurDesaffecttaion) {
		this.auteurDesaffecttaion = auteurDesaffecttaion;
	}

	public PointDeVente getPtv_id() {
		return ptv_id;
	}

	public void setPtv_id(PointDeVente ptv_id) {
		this.ptv_id = ptv_id;
	}
	
	
}
