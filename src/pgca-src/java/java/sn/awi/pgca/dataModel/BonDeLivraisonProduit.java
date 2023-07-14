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
@Table(name="pgca_bonDeLivraisonProduit")
public class BonDeLivraisonProduit implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="idProduitAssocie")
	private Long idProduitBL;

	@Column(length=250)
	private String libelle;
	
	@Column(length=50)
	private Long idProduit;
	
	@Column
	private Double quantite;
	
	@Column
	private Long idStockSortant;
	
	@Column
	private Long idStockEntrant;
	
	@Column
	private Long idProgramme;
	
	@Column
	private Long idCampagne;
	
	@Column
	private String libelleStockSortant;
	
	@Column
	private String libelleStockEntrant;
	
	@Column(length=50)
	private String libelleCampagne;
	
	@Column(length=50)
	private String libelleProgramme;
	
	
	@ManyToOne
	@JoinColumn(name = "bl_id", nullable = false)
	private BonDeLivraison blAssocie;


	public Long getIdProduitBL() {
		return idProduitBL;
	}


	public void setIdProduitBL(Long idProduitBL) {
		this.idProduitBL = idProduitBL;
	}


	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public Long getIdProduit() {
		return idProduit;
	}


	public void setIdProduit(Long idProduit) {
		this.idProduit = idProduit;
	}


	public Double getQuantite() {
		return quantite;
	}


	public void setQuantite(Double quantite) {
		this.quantite = quantite;
	}


	public Long getIdStockSortant() {
		return idStockSortant;
	}


	public void setIdStockSortant(Long idStockSortant) {
		this.idStockSortant = idStockSortant;
	}


	public Long getIdStockEntrant() {
		return idStockEntrant;
	}


	public void setIdStockEntrant(Long idStockEntrant) {
		this.idStockEntrant = idStockEntrant;
	}


	public String getLibelleStockSortant() {
		return libelleStockSortant;
	}


	public void setLibelleStockSortant(String libelleStockSortant) {
		this.libelleStockSortant = libelleStockSortant;
	}


	public String getLibelleStockEntrant() {
		return libelleStockEntrant;
	}


	public void setLibelleStockEntrant(String libelleStockEntrant) {
		this.libelleStockEntrant = libelleStockEntrant;
	}


	public BonDeLivraison getBlAssocie() {
		return blAssocie;
	}


	public void setBlAssocie(BonDeLivraison blAssocie) {
		this.blAssocie = blAssocie;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return idProduitBL;
	}


	public String getLibelleCampagne() {
		return libelleCampagne;
	}


	public void setLibelleCampagne(String libelleCampagne) {
		this.libelleCampagne = libelleCampagne;
	}


	public String getLibelleProgramme() {
		return libelleProgramme;
	}


	public void setLibelleProgramme(String libelleProgramme) {
		this.libelleProgramme = libelleProgramme;
	}


	public Long getIdProgramme() {
		return idProgramme;
	}


	public void setIdProgramme(Long idProgramme) {
		this.idProgramme = idProgramme;
	}


	public Long getIdCampagne() {
		return idCampagne;
	}


	public void setIdCampagne(Long idCampagne) {
		this.idCampagne = idCampagne;
	}

	
}
