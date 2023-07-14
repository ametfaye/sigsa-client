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
@Table(name="pgca_cheque")
public class Cheque implements Serializable, GenericModel {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7643121102813291538L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="cheque_id")
	private Long id_cheque;

	@Column(length=100)
	private float montantCheque;
	
	@Column(length=100)
	private String banqueEmetrice;
	
	@Column(length=100)
	private String numeroCheque;
	
	
	@Column(length=100)
	private String description;
	
	@Column(length=100)
	private int statut; // 0 a encaisser  , 1 Encaisser
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date											dateEdition;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date											dateEncaissement;
	
	
	@OneToOne
	@JoinColumn(name = "ptv_id", nullable = true)
	private PointDeVente pointDeVente;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ventes_id", nullable = true)
	private Ventes				VenteConcerne;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "avancecr_id", nullable = true)
	private AvanceSurCredit				avanceConcerne;

	public Long getId_cheque() {
		return id_cheque;
	}

	public void setId_cheque(Long id_cheque) {
		this.id_cheque = id_cheque;
	}

	public float getMontantCheque() {
		return montantCheque;
	}

	public void setMontantCheque(float montantCheque) {
		this.montantCheque = montantCheque;
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatut() {
		return statut;
	}

	public void setStatut(int statut) {
		this.statut = statut;
	}

	

	public Ventes getVenteConcerne() {
		return VenteConcerne;
	}

	public void setVenteConcerne(Ventes venteConcerne) {
		VenteConcerne = venteConcerne;
	}

	public AvanceSurCredit getAvanceConcerne() {
		return avanceConcerne;
	}

	public void setAvanceConcerne(AvanceSurCredit avanceConcerne) {
		this.avanceConcerne = avanceConcerne;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Long getId() {
		return id_cheque;
	}

	public String getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(String numeroCheque) {
		this.numeroCheque = numeroCheque;
	}

	public String getBanqueEmetrice() {
		return banqueEmetrice;
	}

	public void setBanqueEmetrice(String banqueEmetrice) {
		this.banqueEmetrice = banqueEmetrice;
	}

	public Date getDateEdition() {
		return dateEdition;
	}

	public void setDateEdition(Date dateEdition) {
		this.dateEdition = dateEdition;
	}

	public Date getDateEncaissement() {
		return dateEncaissement;
	}

	public void setDateEncaissement(Date dateEncaissement) {
		this.dateEncaissement = dateEncaissement;
	}

	public PointDeVente getPointDeVente() {
		return pointDeVente;
	}

	public void setPointDeVente(PointDeVente pointDeVente) {
		this.pointDeVente = pointDeVente;
	}


	
	
}
