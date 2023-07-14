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

@Entity
@Table(name="pgca_collecteProduits")
public class CollecteProduits implements Serializable, GenericModel {
	
	

	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long idCollecte;
	
	@OneToOne
	@JoinColumn(name = "alloc_id", nullable = false)
	private Allocation allocationConcerne;
	
	@OneToOne
	@JoinColumn(name = "pdc_id", nullable = true)
	private PointDeCollecte pointdeCollecte;
	
	@Column(length=100)
	private String reference;
	
	@Column(length=100)
	private String producteurNom;
	@Column(length=100)
	private String producteurPrenom;
	@Column(length=100)
	private String producteurCNI;
	@Column(length=100)
	private String producteurVillage;
	@Column(length=100)
	private int producteurPoidsTotal;
	@Column(length=100)
	private float producteurValeurCollecte;
	@Column(length=100)
	private float producteurPrixUnitaire;
	@Column(length=100)
	private String   producteurDateDepot;
	
	
	@Override
	public Long getId() {
		return idCollecte;
	}


	public Long getIdCollecte() {
		return idCollecte;
	}


	public void setIdCollecte(Long idCollecte) {
		this.idCollecte = idCollecte;
	}


	public Allocation getAllocationConcerne() {
		return allocationConcerne;
	}


	public void setAllocationConcerne(Allocation allocationConcerne) {
		this.allocationConcerne = allocationConcerne;
	}


	public String getProducteurNom() {
		return producteurNom;
	}


	public void setProducteurNom(String producteurNom) {
		this.producteurNom = producteurNom;
	}


	public String getProducteurPrenom() {
		return producteurPrenom;
	}


	public void setProducteurPrenom(String producteurPrenom) {
		this.producteurPrenom = producteurPrenom;
	}


	public String getProducteurCNI() {
		return producteurCNI;
	}


	public void setProducteurCNI(String producteurCNI) {
		this.producteurCNI = producteurCNI;
	}


	public String getProducteurVillage() {
		return producteurVillage;
	}


	public void setProducteurVillage(String producteurVillage) {
		this.producteurVillage = producteurVillage;
	}


	public String getProducteurDateDepot() {
		return producteurDateDepot;
	}


	public void setProducteurDateDepot(String producteurDateDepot) {
		this.producteurDateDepot = producteurDateDepot;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public int getProducteurPoidsTotal() {
		return producteurPoidsTotal;
	}


	public void setProducteurPoidsTotal(int producteurPoidsTotal) {
		this.producteurPoidsTotal = producteurPoidsTotal;
	}


	public float getProducteurPrixUnitaire() {
		return producteurPrixUnitaire;
	}


	public void setProducteurPrixUnitaire(float producteurPrixUnitaire) {
		this.producteurPrixUnitaire = producteurPrixUnitaire;
	}


	public float getProducteurValeurCollecte() {
		return producteurValeurCollecte;
	}


	public void setProducteurValeurCollecte(float producteurValeurCollecte) {
		this.producteurValeurCollecte = producteurValeurCollecte;
	}


	public String getReference() {
		return reference;
	}


	public void setReference(String reference) {
		this.reference = reference;
	}


	public PointDeCollecte getPointdeCollecte() {
		return pointdeCollecte;
	}


	public void setPointdeCollecte(PointDeCollecte pointdeCollecte) {
		this.pointdeCollecte = pointdeCollecte;
	}


}
