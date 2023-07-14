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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="pgca_facture")
public class Facture implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="fact_id")
	private Long fact_id;

	@Column(length=50)
	private String libelle;

	@Column(length=50)
	private String NomClient;

	@Column
	private float prixTotalTTC;

	@OneToOne
	@JoinColumn(name = "stock_id", nullable = false)
	private Stock stockSortant;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date											dateEdition;
	

	@ManyToOne
	@JoinColumn(name = "pgca_idca", nullable = true)
	private CampagneAgricole campagneConcerne; // Stock auquel appartient le produit
	
	
	@ManyToOne
	@JoinColumn(name = "pgca_idprog", nullable = true)
	private ProgrammeAgricol programme; // Stock auquel appartient le produit


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}


	public Long getFact_id() {
		return fact_id;
	}


	public void setFact_id(Long fact_id) {
		this.fact_id = fact_id;
	}


	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public String getNomClient() {
		return NomClient;
	}


	public void setNomClient(String nomClient) {
		NomClient = nomClient;
	}


	public float getPrixTotalTTC() {
		return prixTotalTTC;
	}


	public void setPrixTotalTTC(float prixTotalTTC) {
		this.prixTotalTTC = prixTotalTTC;
	}


	public Stock getStockSortant() {
		return stockSortant;
	}


	public void setStockSortant(Stock stockSortant) {
		this.stockSortant = stockSortant;
	}


	public CampagneAgricole getCampagneConcerne() {
		return campagneConcerne;
	}


	public void setCampagneConcerne(CampagneAgricole campagneConcerne) {
		this.campagneConcerne = campagneConcerne;
	}


	public ProgrammeAgricol getProgramme() {
		return programme;
	}


	public void setProgramme(ProgrammeAgricol programme) {
		this.programme = programme;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Date getDateEdition() {
		return dateEdition;
	}


	public void setDateEdition(Date dateEdition) {
		this.dateEdition = dateEdition;
	}

	
}
