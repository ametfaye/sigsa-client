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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;



@Entity
@Table(name = "pgca_CampagneAgricole")
public class CampagneAgricole implements Serializable, GenericModel, Cloneable {

	private static final long					serialVersionUID	= 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pgca_idca")
	private Long											id_ca;
	
	
	@Column(length = 200)
	private String										libelle; 

	@Column
	private int										statut;  
	
	private Date											dateOuverture;
	
	private Date											dateFermeture;
	
	/******  Chaque CA a un ou plusieurs points de collecte ***/
	
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "campagneConcerne", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@OrderBy("pdc_id")
	private Set<PointDeCollecte> pointdeCollectes;
	
	
	
	
	/*** Chaque CA a un ou plusieurs points de vente ****/
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "campagneConcerne", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@OrderBy("ptv_id")
	private Set<PointDeVente> pointdeVente;
	
	

	@Override
	public Long getId() {
		return id_ca;
	}



	public Long getId_ca() {
		return id_ca;
	}



	public void setId_ca(Long id_ca) {
		this.id_ca = id_ca;
	}



	public String getLibelle() {
		return libelle;
	}



	public void setLibelle(String libelle) {
		this.libelle = libelle;
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



	public Set<PointDeCollecte> getPointdeCollectes() {
		return pointdeCollectes;
	}



	public void setPointdeCollectes(Set<PointDeCollecte> pointdeCollectes) {
		this.pointdeCollectes = pointdeCollectes;
	}



	public Set<PointDeVente> getPointdeVente() {
		return pointdeVente;
	}



	public void setPointdeVente(Set<PointDeVente> pointdeVente) {
		this.pointdeVente = pointdeVente;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}





	public int getStatut() {
		return statut;
	}



	public void setStatut(int statut) {
		this.statut = statut;
	}



}
