package sn.awi.pgca.dataModel;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/*****  QUOTA *******/

@Entity
@Table(name="pgca_miseEnPlaceAeffectuee")
public class MiseEnPlaceAEffectuer implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id_MEPaAF;

	@OneToOne
	@JoinColumn(name = "pgca_idprog", nullable = false)
	private ProgrammeAgricol programmeConcerne;
	
	
	/* dans une commune, on peut avoir plusieurs point de vente.*/
	@OneToOne
	@JoinColumn(name = "commune_id", nullable = false)
	private Commune  pointdeVenteConcerne;  // Point de vente officiel de letat
	
	/**** Le point de vente ou le transporteur deposera les intrants ***/
	@OneToOne
	@JoinColumn(name = "ptv_id", nullable = false)
	private PointDeVente  pointDeChute;
	
	@ManyToOne
	@JoinColumn(name = "refIntrantId", nullable = false)
	private ReferentialIntrants ProduitAmettreEnPlace;
	
	@Column
	private double quota;
	
	@Column
	private double miseEnplace ; 
	
	@Column
	private double reliquat;
	
	/** Le stock residuel est le stock déjà mis en place 
	 * 
	 * 	ce stock peut etre déplacé en fin de ventes en cas de restant
	 * 
	 * */
	
	@Column
	private Double stockResiduel;
	
	
	@Column
	private Date dateEdition;
	
	// Gestion retard  Date souhaite - effctive  = nb jour de retard a remonté aux manager
	@Column
	private Date dateMiseEnplaceSouhaite;
	
	@Column
	private Date dateMiseEnplaceEffective;

	@Override
	public Long getId() {
		return id_MEPaAF;
	}



	public Long getId_MEPaAF() {
		return id_MEPaAF;
	}



	public void setId_MEPaAF(Long id_MEPaAF) {
		this.id_MEPaAF = id_MEPaAF;
	}



	public ProgrammeAgricol getProgrammeConcerne() {
		return programmeConcerne;
	}



	public void setProgrammeConcerne(ProgrammeAgricol programmeConcerne) {
		this.programmeConcerne = programmeConcerne;
	}



//	public PointDeVente getPointdeVenteConcerne() {
//		return pointdeVenteConcerne;
//	}
//
//
//
//	public void setPointdeVenteConcerne(PointDeVente pointdeVenteConcerne) {
//		this.pointdeVenteConcerne = pointdeVenteConcerne;
//	}



	public double getQuota() {
		return quota;
	}



	public void setQuota(double quota) {
		this.quota = quota;
	}



	public double getMiseEnplace() {
		return miseEnplace;
	}



	public ReferentialIntrants getProduitAmettreEnPlace() {
		return ProduitAmettreEnPlace;
	}



	public void setProduitAmettreEnPlace(ReferentialIntrants produitAmettreEnPlace) {
		ProduitAmettreEnPlace = produitAmettreEnPlace;
	}



	public void setMiseEnplace(double miseEnplace) {
		this.miseEnplace = miseEnplace;
	}



	public double getReliquat() {
		return reliquat;
	}



	public void setReliquat(double reliquat) {
		this.reliquat = reliquat;
	}



	public Date getDateMiseEnplaceSouhaite() {
		return dateMiseEnplaceSouhaite;
	}



	public Date getDateEdition() {
		return dateEdition;
	}



	public void setDateEdition(Date dateEdition) {
		this.dateEdition = dateEdition;
	}



	public void setDateMiseEnplaceSouhaite(Date dateMiseEnplaceSouhaite) {
		this.dateMiseEnplaceSouhaite = dateMiseEnplaceSouhaite;
	}



	public Date getDateMiseEnplaceEffective() {
		return dateMiseEnplaceEffective;
	}



	public void setDateMiseEnplaceEffective(Date dateMiseEnplaceEffective) {
		this.dateMiseEnplaceEffective = dateMiseEnplaceEffective;
	}







	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public Commune getPointdeVenteConcerne() {
		return pointdeVenteConcerne;
	}



	public void setPointdeVenteConcerne(Commune pointdeVenteConcerne) {
		this.pointdeVenteConcerne = pointdeVenteConcerne;
	}



	public double getStockResiduel() {
		return stockResiduel;
	}



	public void setStockResiduel(double stockResiduel) {
		this.stockResiduel = stockResiduel;
	}



	public PointDeVente getPointDeChute() {
		return pointDeChute;
	}



	public void setPointDeChute(PointDeVente pointDeChute) {
		this.pointDeChute = pointDeChute;
	}



	public void setStockResiduel(Double stockResiduel) {
		this.stockResiduel = stockResiduel;
	}
	
}
