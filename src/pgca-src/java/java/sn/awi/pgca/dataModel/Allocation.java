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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="pgca_allocation")
public class Allocation implements Serializable, GenericModel {
	
	

	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="alloc_id")
	private Long alloc_id;
	
	@Column(length=100)
	private String auteurAllocation;  // manager 
	
	@Column
	private float montantalloue;
	
	@Column
	private float montantUtilise;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date											dateAllcation;
	

	@OneToOne
	@JoinColumn(name = "pers_id", nullable = true)
	private Personne collaborateur;

	
	@OneToOne
	@JoinColumn(name = "pgca_idprog", nullable = false)
	private ProgrammeAgricol programmeConcerne;
	
	
	@OneToOne
	@JoinColumn(name = "pdc_id", nullable = true)
	private PointDeCollecte pointdeCollecteConcerne;
	

	@OneToOne
	@JoinColumn(name = "pgca_referentielIntrant", nullable = true)
	private ReferentialIntrants produitACollecter;

	@Override
	public Long getId() {
		return 	alloc_id;
	}



	public Long getTreso_id() {
		return alloc_id;
	}


	public PointDeCollecte getPointdeCollecteConcerne() {
		return pointdeCollecteConcerne;
	}



	public void setPointdeCollecteConcerne(PointDeCollecte pointdeCollecteConcerne) {
		this.pointdeCollecteConcerne = pointdeCollecteConcerne;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public Long getAlloc_id() {
		return alloc_id;
	}



	public void setAlloc_id(Long alloc_id) {
		this.alloc_id = alloc_id;
	}



	public float getMontantalloue() {
		return montantalloue;
	}



	public void setMontantalloue(float montantalloue) {
		this.montantalloue = montantalloue;
	}



	public float getMontantUtilise() {
		return montantUtilise;
	}



	public void setMontantUtilise(float montantUtilise) {
		this.montantUtilise = montantUtilise;
	}



	public Date getDateAllcation() {
		return dateAllcation;
	}



	public void setDateAllcation(Date dateAllcation) {
		this.dateAllcation = dateAllcation;
	}



	public Personne getCollaborateur() {
		return collaborateur;
	}



	public void setCollaborateur(Personne collaborateur) {
		this.collaborateur = collaborateur;
	}



	public String getAuteurAllocation() {
		return auteurAllocation;
	}



	public void setAuteurAllocation(String auteurAllocation) {
		this.auteurAllocation = auteurAllocation;
	}





	public ProgrammeAgricol getProgrammeConcerne() {
		return programmeConcerne;
	}



	public void setProgrammeConcerne(ProgrammeAgricol programmeConcerne) {
		this.programmeConcerne = programmeConcerne;
	}



	public ReferentialIntrants getProduitACollecter() {
		return produitACollecter;
	}



	public void setProduitACollecter(ReferentialIntrants produitACollecter) {
		this.produitACollecter = produitACollecter;
	}
	


}
