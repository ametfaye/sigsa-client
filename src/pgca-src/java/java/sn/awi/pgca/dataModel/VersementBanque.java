package sn.awi.pgca.dataModel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.primefaces.model.UploadedFile;

@Entity
@Table(name="pgca_versementBanque")
public class VersementBanque implements Serializable , GenericModel{

	private static final long serialVersionUID = 9125422905791273379L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="id_vers")
	private Long idVersment;

	@Column
	private Date dateVersement;
	
	@Column
	private float montantVersment;
	
	@Column
	private float montantPayeParBLP = 0;
	
	@Column
	private String numeroBLP = "";
	

	@Column(length=60)
	private String moyenVersement;
	
	@Column(length=60)
	private String banque;

	@Column(length=80)
	private String libelleVersment;
	
	@Column
	private String 	pathDocumentJustificatif;

	@OneToOne
	@JoinColumn(name = "ptv_id", nullable = true)
	private PointDeVente pointdeVente;
	
	@OneToOne
	@JoinColumn(name = "pdc_id", nullable = true)
	private PointDeCollecte pointdeCollecte;
	
	@OneToOne
	@JoinColumn(name = "pers_id", nullable = false)
	private Personne auteurVersement;

	@OneToOne
	@JoinColumn(name = "pgca_idprog", nullable = true)
	private ProgrammeAgricol programmeConcernee;
	

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return idVersment;
	}


	public Long getIdVersment() {
		return idVersment;
	}


	public void setIdVersment(Long idVersment) {
		this.idVersment = idVersment;
	}


	public Date getDateVersement() {
		return dateVersement;
	}


	public void setDateVersement(Date dateVersement) {
		this.dateVersement = dateVersement;
	}


	public float getMontantVersment() {
		return montantVersment;
	}


	public void setMontantVersment(float montantVersment) {
		this.montantVersment = montantVersment;
	}


	public String getMoyenVersement() {
		return moyenVersement;
	}


	public void setMoyenVersement(String moyenVersement) {
		this.moyenVersement = moyenVersement;
	}


	public String getLibelleVersment() {
		return libelleVersment;
	}


	public void setLibelleVersment(String libelleVersment) {
		this.libelleVersment = libelleVersment;
	}




	public PointDeVente getPointdeVente() {
		return pointdeVente;
	}


	public void setPointdeVente(PointDeVente pointdeVente) {
		this.pointdeVente = pointdeVente;
	}


	public Personne getAuteurVersement() {
		return auteurVersement;
	}


	public void setAuteurVersement(Personne auteurVersement) {
		this.auteurVersement = auteurVersement;
	}


	public ProgrammeAgricol getProgrammeConcernee() {
		return programmeConcernee;
	}


	public void setProgrammeConcernee(ProgrammeAgricol programmeConcernee) {
		this.programmeConcernee = programmeConcernee;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getPathDocumentJustificatif() {
		return pathDocumentJustificatif;
	}


	public void setPathDocumentJustificatif(String pathDocumentJustificatif) {
		this.pathDocumentJustificatif = pathDocumentJustificatif;
	}


	public String getBanque() {
		return banque;
	}


	public void setBanque(String banque) {
		this.banque = banque;
	}


	public PointDeCollecte getPointdeCollecte() {
		return pointdeCollecte;
	}


	public float getMontantPayeParBLP() {
		return montantPayeParBLP; 
	}


	public String getNumeroBLP() {
		return numeroBLP;
	}


	public void setMontantPayeParBLP(float montantPayeParBLP) {
		this.montantPayeParBLP = montantPayeParBLP;
	}


	public void setNumeroBLP(String numeroBLP) {
		this.numeroBLP = numeroBLP;
	}


	public void setPointdeCollecte(PointDeCollecte pointdeCollecte) {
		this.pointdeCollecte = pointdeCollecte;
	}


}
