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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="pgca_creditAvance")
public class AvanceSurCredit implements Serializable, GenericModel {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7643121102813291538L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="avancecr_id")
	private Long avanceCredit_id;

	@ManyToOne
	@JoinColumn(name = "cr_id", nullable = false)
	private Credit creditConcerne;
	
	@Column(length=40)
	private String moyenPaiement;
	

	@Column
	private float valeurAvance;
	
	
	@Column(length=40)
	private String auteurPaiement;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date											dateAvance;


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return avanceCredit_id;
	}


	public Long getAvanceCredit_id() {
		return avanceCredit_id;
	}


	public void setAvanceCredit_id(Long avanceCredit_id) {
		this.avanceCredit_id = avanceCredit_id;
	}


	public Credit getCreditConcerne() {
		return creditConcerne;
	}


	public void setCreditConcerne(Credit creditConcerne) {
		this.creditConcerne = creditConcerne;
	}


	public String getMoyenPaiement() {
		return moyenPaiement;
	}


	public void setMoyenPaiement(String moyenPaiement) {
		this.moyenPaiement = moyenPaiement;
	}


	public float getValeurAvance() {
		return valeurAvance;
	}


	public void setValeurAvance(float valeurAvance) {
		this.valeurAvance = valeurAvance;
	}


	public Date getDateAvance() {
		return dateAvance;
	}


	public void setDateAvance(Date dateAvance) {
		this.dateAvance = dateAvance;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getAuteurPaiement() {
		return auteurPaiement;
	}


	public void setAuteurPaiement(String auteurPaiement) {
		this.auteurPaiement = auteurPaiement;
	}
	
	
}
