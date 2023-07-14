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
@Table(name="pgca_credit")
public class Credit implements Serializable, GenericModel {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7244177070249474822L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="cr_id")
	private Long credit_id;
	
	@Column(length=40)
	private String referenceCredit;
	
	@Column(length=200)
	private String resumeCredit; // Credit contracté lors de l'achat du produit X a la date Y 
	
	@Column(length=200)
	private String zone; // Point ou le credit est contratcté 
	
	@Column
	private float montantInitialCredit;
	
	
	@Column
	private float montantRestantApayer;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date											dateContraction;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date											dateDerniereAvanceSurCredit;
	
	@ManyToOne
	@JoinColumn(name = "ventes_id", nullable = true)
	private Ventes venteConcerne;
	
	
	@ManyToOne
	@JoinColumn(name = "cmd_id", nullable = true)
	private Commande commandeConcerne;
	
	@ManyToOne
	@JoinColumn(name = "stock_id", nullable = true)
	private Stock  StockPointdeVente;
	
	@ManyToOne
	@JoinColumn(name = "pers_id", nullable = false)
	private Personne auteur;
	
	@Column(length=60)
	private String souscripteur;
	
	@Column(length=40)
	private String telSouscripteur;
	
	@Column(length=80)
	private String adresseSouscripteur;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return credit_id;
	}

	public Long getCredit_id() {
		return credit_id;
	}

	public void setCredit_id(Long credit_id) {
		this.credit_id = credit_id;
	}

	public String getResumeCredit() {
		return resumeCredit;
	}

	public void setResumeCredit(String resumeCredit) {
		this.resumeCredit = resumeCredit;
	}

	public Date getDateContraction() {
		return dateContraction;
	}

	public void setDateContraction(Date dateContraction) {
		this.dateContraction = dateContraction;
	}

	public Date getDateDerniereAvanceSurCredit() {
		return dateDerniereAvanceSurCredit;
	}

	public void setDateDerniereAvanceSurCredit(Date dateDerniereAvanceSurCredit) {
		this.dateDerniereAvanceSurCredit = dateDerniereAvanceSurCredit;
	}

	public Ventes getVenteConcerne() {
		return venteConcerne;
	}

	public void setVenteConcerne(Ventes venteConcerne) {
		this.venteConcerne = venteConcerne;
	}

	public Personne getAuteur() {
		return auteur;
	}

	public void setAuteur(Personne auteur) {
		this.auteur = auteur;
	}

	public String getSouscripteur() {
		return souscripteur;
	}

	public void setSouscripteur(String souscripteur) {
		this.souscripteur = souscripteur;
	}

	public String getTelSouscripteur() {
		return telSouscripteur;
	}

	public void setTelSouscripteur(String telSouscripteur) {
		this.telSouscripteur = telSouscripteur;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public float getMontantInitialCredit() {
		return montantInitialCredit;
	}

	public void setMontantInitialCredit(float montantInitialCredit) {
		this.montantInitialCredit = montantInitialCredit;
	}

	public float getMontantRestantApayer() {
		return montantRestantApayer;
	}

	public void setMontantRestantApayer(float montantRestantApayer) {
		this.montantRestantApayer = montantRestantApayer;
	}

	public String getReferenceCredit() {
		return referenceCredit;
	}

	public void setReferenceCredit(String referenceCredit) {
		this.referenceCredit = referenceCredit;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public Stock getStockPointdeVente() {
		return StockPointdeVente;
	}

	public void setStockPointdeVente(Stock stockPointdeVente) {
		StockPointdeVente = stockPointdeVente;
	}

	public String getAdresseSouscripteur() {
		return adresseSouscripteur;
	}

	public void setAdresseSouscripteur(String adresseSouscripteur) {
		this.adresseSouscripteur = adresseSouscripteur;
	}

	public Commande getCommandeConcerne() {
		return commandeConcerne;
	}

	public void setCommandeConcerne(Commande commandeConcerne) {
		this.commandeConcerne = commandeConcerne;
	}



}
