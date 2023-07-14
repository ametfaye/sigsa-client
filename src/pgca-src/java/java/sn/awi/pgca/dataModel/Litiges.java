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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="pgca_litiges")
public class Litiges implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="l_id")
	private Long idLitiges;

	@Column
	private boolean status;
	
	@Column(length=250)
	private String receptionnaireAgent;

	@Column(length=100)
	private String receptionnaireMagsin;

	@Column(length=100)
	private String chauffeur;

	@Column(length=1000)
	private String detailsLitige;
	
	@Column
	private int nombreDesacs;
	
	@Column
	private Double quantiteTotalLige;
	
	
	@OneToOne
	@JoinColumn(name = "idtransporteur", nullable = true)
	private Transporteur transporteurId;
	
	@OneToOne
	@JoinColumn(name = "pers_id", nullable = true)
	private Personne chauffeurId;
	
	
	@OneToOne
	@JoinColumn(name = "bl_id", nullable = true)
	private BonDeLivraison blReference;
	
	@OneToOne
	@JoinColumn(name = "camion_id", nullable = true)
	private Camion numeroCamionId;
	
	@ManyToOne
	@JoinColumn(name = "pgca_idca", nullable = true)
	private CampagneAgricole campagneConcerne; // Stock auquel appartient le produit

	
	@ManyToOne
	@JoinColumn(name = "pgca_idprog", nullable = true)
	private ProgrammeAgricol programme; // Stock auquel appartient le produit

	@Temporal(TemporalType.TIMESTAMP)
	private Date											dateLitiges;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date											dateArbitrageLitige;


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Long getIdLitiges() {
		return idLitiges;
	}


	public boolean isStatus() {
		return status;
	}


	public String getReceptionnaireAgent() {
		return receptionnaireAgent;
	}


	public String getReceptionnaireMagsin() {
		return receptionnaireMagsin;
	}


	public String getChauffeur() {
		return chauffeur;
	}


	public String getDetailsLitige() {
		return detailsLitige;
	}


	public Transporteur getTransporteurId() {
		return transporteurId;
	}


	public Personne getChauffeurId() {
		return chauffeurId;
	}


	public BonDeLivraison getBlReference() {
		return blReference;
	}


	public Camion getNumeroCamionId() {
		return numeroCamionId;
	}


	public CampagneAgricole getCampagneConcerne() {
		return campagneConcerne;
	}


	public ProgrammeAgricol getProgramme() {
		return programme;
	}


	public Date getDateLitiges() {
		return dateLitiges;
	}


	public Date getDateArbitrageLitige() {
		return dateArbitrageLitige;
	}


	public void setIdLitiges(Long idLitiges) {
		this.idLitiges = idLitiges;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public void setReceptionnaireAgent(String receptionnaireAgent) {
		this.receptionnaireAgent = receptionnaireAgent;
	}


	public void setReceptionnaireMagsin(String receptionnaireMagsin) {
		this.receptionnaireMagsin = receptionnaireMagsin;
	}


	public void setChauffeur(String chauffeur) {
		this.chauffeur = chauffeur;
	}


	public void setDetailsLitige(String detailsLitige) {
		this.detailsLitige = detailsLitige;
	}


	public void setTransporteurId(Transporteur transporteurId) {
		this.transporteurId = transporteurId;
	}


	public void setChauffeurId(Personne chauffeurId) {
		this.chauffeurId = chauffeurId;
	}


	public void setBlReference(BonDeLivraison blReference) {
		this.blReference = blReference;
	}


	public void setNumeroCamionId(Camion numeroCamionId) {
		this.numeroCamionId = numeroCamionId;
	}


	public void setCampagneConcerne(CampagneAgricole campagneConcerne) {
		this.campagneConcerne = campagneConcerne;
	}


	public void setProgramme(ProgrammeAgricol programme) {
		this.programme = programme;
	}


	public void setDateLitiges(Date dateLitiges) {
		this.dateLitiges = dateLitiges;
	}


	public void setDateArbitrageLitige(Date dateArbitrageLitige) {
		this.dateArbitrageLitige = dateArbitrageLitige;
	}


	@Override
	public Long getId() {
		return idLitiges;
	}


	public int getNombreDesacs() {
		return nombreDesacs;
	}


	public Double getQuantiteTotalLige() {
		return quantiteTotalLige;
	}


	public void setNombreDesacs(int nombreDesacs) {
		this.nombreDesacs = nombreDesacs;
	}


	public void setQuantiteTotalLige(Double quantiteTotalLige) {
		this.quantiteTotalLige = quantiteTotalLige;
	}
	

}
