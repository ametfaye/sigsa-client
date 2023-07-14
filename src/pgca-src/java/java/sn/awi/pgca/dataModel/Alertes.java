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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="pgca_alerte")
public class Alertes implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)  
	@Column(name="pgca_alerteId")
	private Long pgca_alerteId;

	@OneToOne
	@JoinColumn(name = "pgca_idca", nullable = true)
	private CampagneAgricole campagneConcerne;
	
	
	@OneToOne
	@JoinColumn(name = "ptv_id", nullable = true)
	private PointDeVente pointDeVenteConcerne;
	
	
	@OneToOne
	@JoinColumn(name = "pers_id", nullable = true)
	private PointDeCollecte pointDeCollecteConcerne;


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return pgca_alerteId;
	}


	public Long getMag_id() {
		return pgca_alerteId;
	}


	public void setMag_id(Long mag_id) {
		this.pgca_alerteId = mag_id;
	}


	public CampagneAgricole getCampagneConcerne() {
		return campagneConcerne;
	}


	public void setCampagneConcerne(CampagneAgricole campagneConcerne) {
		this.campagneConcerne = campagneConcerne;
	}


	public PointDeVente getPointDeVenteConcerne() {
		return pointDeVenteConcerne;
	}


	public void setPointDeVenteConcerne(PointDeVente pointDeVenteConcerne) {
		this.pointDeVenteConcerne = pointDeVenteConcerne;
	}


	public PointDeCollecte getPointDeCollecteConcerne() {
		return pointDeCollecteConcerne;
	}


	public void setPointDeCollecteConcerne(PointDeCollecte pointDeCollecteConcerne) {
		this.pointDeCollecteConcerne = pointDeCollecteConcerne;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Long getPgca_alerteId() {
		return pgca_alerteId;
	}


	public void setPgca_alerteId(Long pgca_alerteId) {
		this.pgca_alerteId = pgca_alerteId;
	}
	
	
	
		
}
