package sn.awi.pgca.dataModel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="pgca_intrant")
public class Intrant implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="produit_id")
	private Long produit_id;
	
	@JoinColumn
	private String libelle;
	
	@OneToOne
	@JoinColumn(name = "pgca_referentielIntrant", nullable = false) 
	private ReferentialIntrants intrant;
	
	@OneToOne
	@JoinColumn(name = "id_tarificateur", nullable = true)
	private Tarificateur tarif;
	
	@OneToOne
	@JoinColumn(name = "four_id", nullable = true)
	private Fournisseur fournisseur;
	
	@ManyToOne
	@JoinColumn(name = "stock_id", nullable = true) 
	private Stock stockRef; // Stock auquel appartient le produit
	
	@JoinColumn
	private String stockLibelle;
	
	@ManyToOne
	@JoinColumn(name = "id_com", nullable = true) 
	private Commission commission;
	
	@ManyToOne
	@JoinColumn(name = "pgca_idca", nullable = false)
	private ProgrammeAgricol programme;
	
	@Column
	private Double quantite;
	
	
	@Column
	private Double quantiteInitial;
	
	
	@Column
	private Double valeurAcquisition; // prix dachat
	
	@Column(length=150)
	private String provenance;

	
	public Long getId() {
		return produit_id;
	}

	
	public void setId(Long id) {
		this.produit_id = id;
	}


	public Long getProduit_id() {
		return produit_id;
	}


	public void setProduit_id(Long produit_id) {
		this.produit_id = produit_id;
	}	

	public Stock getStockRef() {
		return stockRef;
	}


	public Double getQuantite() {
		return quantite;
	}


	public void setQuantite(Double quantite) {
		this.quantite = quantite;
	}


	public void setStockRef(Stock stockRef) {
		this.stockRef = stockRef;
	}




	public String getProvenance() {
		return provenance;
	}


	public void setProvenance(String provenance) {
		this.provenance = provenance;
	}


	public ProgrammeAgricol getProgramme() {
		return programme;
	}


	public void setProgramme(ProgrammeAgricol programme) {
		this.programme = programme;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Tarificateur getTarif() {
		return tarif;
	}


	public void setTarif(Tarificateur tarif) {
		this.tarif = tarif;
	}


	public Fournisseur getFournisseur() {
		return fournisseur;
	}


	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}


	public Commission getCommission() {
		return commission;
	}


	public void setCommission(Commission commission) {
		this.commission = commission;
	}


	public Double getQuantiteInitial() {
		return quantiteInitial;
	}


	public void setQuantiteInitial(Double quantiteInitial) {
		this.quantiteInitial = quantiteInitial;
	}


	public ReferentialIntrants getIntrant() {
		return intrant;
	}


	public void setIntrant(ReferentialIntrants intrant) {
		this.intrant = intrant;
	}


	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public Double getValeurAcquisition() {
		return valeurAcquisition;
	}


	public String getStockLibelle() {
		return stockLibelle;
	}


	public void setStockLibelle(String stockLibelle) {
		this.stockLibelle = stockLibelle;
	}


	public void setValeurAcquisition(Double valeurAcquisition) {
		this.valeurAcquisition = valeurAcquisition;
	}


}
