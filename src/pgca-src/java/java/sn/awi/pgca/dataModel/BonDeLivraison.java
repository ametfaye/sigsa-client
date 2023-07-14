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
@Table(name="pgca_bonDeLivraison")
public class BonDeLivraison implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="bl_id")
	private Long bl_id;

	@Column(length=250)
	private String libelle;
	
	@Column
	private int  blStatut;
	 
	@Column(length=150)
	private String provenanceProduit;
	
	@Column(length=150)
	private String destinationProduit;
	
	
	@Column(length=50)
	private String numeroBLManuel;
	
	
	@Column(length=50)
	private String numeroLVManuel;
	
	
	@Column(length=50)
	private String referenceBL;
	
	
	@Column
	private Long  idStockReceptionnaire;
	
	@Column
	private Long  idStockExpedition;
	
	@Column
	private Long  idAuteurBl;
	
	/*
	@OneToOne
	@JoinColumn(name = "stock_id", nullable = false)
	private Stock stockSortant;
	
	@OneToOne
	@JoinColumn(name = "stock_id", nullable = false)
	private Stock stockEntrant;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private Utilisateur auteur;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private Utilisateur destinataire; */
	
	
	@OneToOne
	@JoinColumn(name = "idtransporteur", nullable = false)
	private Transporteur transporteurId;
	
	@OneToOne
	@JoinColumn(name = "pers_id", nullable = false)
	private Personne chauffeurId;
	
	@OneToOne
	@JoinColumn(name = "camion_id", nullable = false)
	private Camion numeroCamionId;
	
	@ManyToOne
	@JoinColumn(name = "pgca_idca", nullable = true)
	private CampagneAgricole campagneConcerne; // Stock auquel appartient le produit

	
	@ManyToOne
	@JoinColumn(name = "pgca_idprog", nullable = true)
	private ProgrammeAgricol programme; // Stock auquel appartient le produit

	@Temporal(TemporalType.TIMESTAMP)
	private Date											dateEdition;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date											dateReception;
	


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return bl_id;
	}



	public Long getBl_id() {
		return bl_id;
	}



	public void setBl_id(Long bl_id) {
		this.bl_id = bl_id;
	}



	public String getLibelle() {
		return libelle;
	}



	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}





	public Transporteur getTransporteurId() {
		return transporteurId;
	}



	public void setTransporteurId(Transporteur transporteurId) {
		this.transporteurId = transporteurId;
	}



	public Personne getChauffeurId() {
		return chauffeurId;
	}



	public void setChauffeurId(Personne chauffeurId) {
		this.chauffeurId = chauffeurId;
	}



	public Camion getNumeroCamionId() {
		return numeroCamionId;
	}



	public void setNumeroCamionId(Camion numeroCamionId) {
		this.numeroCamionId = numeroCamionId;
	}



	public CampagneAgricole getCampagneConcerne() {
		return campagneConcerne;
	}



	public void setCampagneConcerne(CampagneAgricole campagneConcerne) {
		this.campagneConcerne = campagneConcerne;
	}



	public ProgrammeAgricol getProgramme() {
		return programme;
	}



	public void setProgramme(ProgrammeAgricol programme) {
		this.programme = programme;
	}



	public Date getDateEdition() {
		return dateEdition;
	}



	public void setDateEdition(Date dateEdition) {
		this.dateEdition = dateEdition;
	}



	public Date getDateReception() {
		return dateReception;
	}



	public void setDateReception(Date dateReception) {
		this.dateReception = dateReception;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public String getProvenanceProduit() {
		return provenanceProduit;
	}



	public void setProvenanceProduit(String provenanceProduit) {
		this.provenanceProduit = provenanceProduit;
	}



	public Long getIdStockReceptionnaire() {
		return idStockReceptionnaire;
	}



	public void setIdStockReceptionnaire(Long idStockReceptionnaire) {
		this.idStockReceptionnaire = idStockReceptionnaire;
	}



	public Long getIdAuteurBl() {
		return idAuteurBl;
	}



	public void setIdAuteurBl(Long idAuteurBl) {
		this.idAuteurBl = idAuteurBl;
	}

	public Long getIdStockExpedition() {
		return idStockExpedition;
	}

	public void setIdStockExpedition(Long idStockExpedition) {
		this.idStockExpedition = idStockExpedition;
	}



	public int getBlStatut() {
		return blStatut;
	}





	public String getNumeroBLManuel() {
		return numeroBLManuel;
	}



	public String getNumeroLVManuel() {
		return numeroLVManuel;
	}



	public void setNumeroBLManuel(String numeroBLManuel) {
		this.numeroBLManuel = numeroBLManuel;
	}



	public void setNumeroLVManuel(String numeroLVManuel) {
		this.numeroLVManuel = numeroLVManuel;
	}



	public void setBlStatut(int blStatut) {
		this.blStatut = blStatut;
	}



	public String getReferenceBL() {
		return referenceBL;
	}



	public void setReferenceBL(String referenceBL) {
		this.referenceBL = referenceBL;
	}



	public String getDestinationProduit() {
		return destinationProduit;
	}



	public void setDestinationProduit(String destinationProduit) {
		this.destinationProduit = destinationProduit;
	}

}
