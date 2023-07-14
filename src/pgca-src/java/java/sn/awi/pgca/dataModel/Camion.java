package sn.awi.pgca.dataModel;

import java.io.Serializable;

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

@Entity
@Table(name="pgca_camion")
public class Camion implements Serializable, GenericModel {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7643121102813291538L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="camion_id")
	private Long id;

	@Column(length=100)
	private String numeroCamion;

	@Column
	private int capaciteMax;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "chauff_id", nullable = false)
	private Chauffeur				chauffeur;
	
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idtransporteur", nullable = false)
	private Transporteur		transporteur;



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNumeroCamion() {
		return numeroCamion;
	}


	public void setNumeroCamion(String numeroCamion) {
		this.numeroCamion = numeroCamion;
	}


	public Chauffeur getChauffeur() {
		return chauffeur;
	}


	public void setChauffeur(Chauffeur chauffeur) {
		this.chauffeur = chauffeur;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public int getCapaciteMax() {
		return capaciteMax;
	}


	public void setCapaciteMax(int capaciteMax) {
		this.capaciteMax = capaciteMax;
	}


	public Transporteur getTransporteur() {
		return transporteur;
	}


	public void setTransporteur(Transporteur transporteur) {
		this.transporteur = transporteur;
	}
	
	
	
}
