package sn.awi.pgca.dataModel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="pgca_stockResiduelPointDeVente")
public class StockResiduelPointDeVente implements Serializable, GenericModel, Cloneable {
	
	// Liste AUDIT :  V2 :  ajouter liste des audits pour historiser les entrés et sortie

	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long stockResiduel_id;
	
	
	@OneToOne
	@JoinColumn(name = "commune_id", nullable = false)
	private Commune  pointdeVente;

	@OneToOne
	@JoinColumn(name = "produit_id", nullable = false)
	private Intrant  intrant;

	
	@OneToOne
	@JoinColumn(name = "pers_id", nullable = true)
	private Personne responsablePointDeVente;
	
	@Column
	private String dateStcok;
	
	@Column
	private String dateDerniereActionStcok;
	
	@Column
	private Double  totalStockActuel;
	
	@Column
	private Double  valeurtotalStockActuel;

	public Long getStockResiduel_id() {
		return stockResiduel_id;
	}

	public void setStockResiduel_id(Long stockResiduel_id) {
		this.stockResiduel_id = stockResiduel_id;
	}

	public Commune getPointdeVente() {
		return pointdeVente;
	}

	public void setPointdeVente(Commune pointdeVente) {
		this.pointdeVente = pointdeVente;
	}

	public Personne getResponsablePointDeVente() {
		return responsablePointDeVente;
	}

	public void setResponsablePointDeVente(Personne responsablePointDeVente) {
		this.responsablePointDeVente = responsablePointDeVente;
	}

	public String getDateStcok() {
		return dateStcok;
	}

	public void setDateStcok(String dateStcok) {
		this.dateStcok = dateStcok;
	}

	public String getDateDerniereActionStcok() {
		return dateDerniereActionStcok;
	}

	public void setDateDerniereActionStcok(String dateDerniereActionStcok) {
		this.dateDerniereActionStcok = dateDerniereActionStcok;
	}

	public Double getTotalStockActuel() {
		return totalStockActuel;
	}

	public void setTotalStockActuel(Double totalStockActuel) {
		this.totalStockActuel = totalStockActuel;
	}

	public Double getValeurtotalStockActuel() {
		return valeurtotalStockActuel;
	}

	public void setValeurtotalStockActuel(Double valeurtotalStockActuel) {
		this.valeurtotalStockActuel = valeurtotalStockActuel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return stockResiduel_id;
	}

	public Intrant getIntrant() {
		return intrant;
	}

	public void setIntrant(Intrant intrant) {
		this.intrant = intrant;
	}
	
	
}
