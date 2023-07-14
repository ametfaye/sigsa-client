package sn.awi.pgca.dataModel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="pgca_subvention")
public class Subvention implements Serializable, GenericModel {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7643121102813291538L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id_sub; 
	
//	@OneToOne
//	@JoinColumn(name = "pgca_idprog", nullable = false) 
//	private ProgrammeAgricol programmeConcerne;
	
	@Column
	private float montantSubvention;
	
	@Column(length=200)
	private String detailsSubvention;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date											dateSubvention;
	
	
	@OneToOne
	@JoinColumn(name = "ptv_id", nullable = true)
	private PointDeVente pointDeVente;
	
	// pour les point de collecte et point de vente  on renseigne les stock car n'ayant pas de mag
	@OneToOne
	@JoinColumn(name = "stock_id", nullable = true)
	private Stock stock ;
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ventes_id", nullable = false)
	private Ventes				VenteConcerne;


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id_sub;
	}


	public Long getId_sub() {
		return id_sub;
	}


	public void setId_sub(Long id_sub) {
		this.id_sub = id_sub;
	}


	public float getMontantSubvention() {
		return montantSubvention;
	}


	public void setMontantSubvention(float montantSubvention) {
		this.montantSubvention = montantSubvention;
	}


	public String getDetailsSubvention() {
		return detailsSubvention;
	}


	public void setDetailsSubvention(String detailsSubvention) {
		this.detailsSubvention = detailsSubvention;
	}


	


	public PointDeVente getPointDeVente() {
		return pointDeVente;
	}


	public void setPointDeVente(PointDeVente pointDeVente) {
		this.pointDeVente = pointDeVente;
	}


	public Ventes getVenteConcerne() {
		return VenteConcerne;
	}


	public void setVenteConcerne(Ventes venteConcerne) {
		VenteConcerne = venteConcerne;
	}


//	public ProgrammeAgricol getProgrammeConcerne() {
//		return programmeConcerne;
//	}
//
//
//	public void setProgrammeConcerne(ProgrammeAgricol programmeConcerne) {
//		this.programmeConcerne = programmeConcerne;
//	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Date getDateSubvention() {
		return dateSubvention;
	}


	public void setDateSubvention(Date dateSubvention) {
		this.dateSubvention = dateSubvention;
	}


	public Stock getStock() {
		return stock;
	}


	public void setStock(Stock stock) {
		this.stock = stock;
	}

	
}
