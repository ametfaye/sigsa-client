package sn.awi.pgca.web.dto;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class ChequeDTO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Long id_cheque;


	private float montantCheque;
	
	private String banqueEmetrice;
	
	private Date dateEdition;
	private Date dateEncaissement;
	
	private String numeroCheque;

	private String description;
	private Long idPointdeVente;
	private String pointdevente;

	
	private String libelleStatut;
	private int statut;
	private Date											dateAllcation;
	private Long				idVenteConcerne;

	private Long				idavanceConcerne;

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

	public String getLibelleStatut() {
		return libelleStatut;
	}

	public void setLibelleStatut(String libelleStatut) {
		this.libelleStatut = libelleStatut;
	}

	public int getStatut() {
		return statut;
	}

	public void setStatut(int statut) {
		this.statut = statut;
	}

	public Date getDateAllcation() {
		return dateAllcation;
	}

	public void setDateAllcation(Date dateAllcation) {
		this.dateAllcation = dateAllcation;
	}

	public Long getIdVenteConcerne() {
		return idVenteConcerne;
	}

	public void setIdVenteConcerne(Long idVenteConcerne) {
		this.idVenteConcerne = idVenteConcerne;
	}

	public Long getIdavanceConcerne() {
		return idavanceConcerne;
	}

	public void setIdavanceConcerne(Long idavanceConcerne) {
		this.idavanceConcerne = idavanceConcerne;
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

	public Long getIdPointdeVente() {
		return idPointdeVente;
	}

	public void setIdPointdeVente(Long idPointdeVente) {
		this.idPointdeVente = idPointdeVente;
	}

	public String getPointdevente() {
		return pointdevente;
	}

	public void setPointdevente(String pointdevente) {
		this.pointdevente = pointdevente;
	}

	
}
