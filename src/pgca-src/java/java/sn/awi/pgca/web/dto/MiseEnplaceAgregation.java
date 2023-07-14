package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class MiseEnplaceAgregation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3311897253615919905L;
	private Long id;
	
	private Long idIntrant;
	
	private Long idMep;  // mep de reference

	private String departementGroup;  // intrant groupé par departemnt 
	
	private String commune;  // commune ou se trouve l'intrant

	
	private Long  idDepartement;  // intrant groupé par departemnt 

	private String nomIntrant;
	
	private double totalQuota;
	
	private Double  stockResiduel; // -->  MEP DEJA  EFFECTUES  - (VENTE & TRANSFERT)

	
	private double totalreliquat;
	
	private int nombreDePointDeVente;
	
	
	// taux de couveerture national
	private Double tauxCourverture;
	
	
	public Long getIdIntrant() {
		return idIntrant;
	}

	public void setIdIntrant(Long idIntrant) {
		this.idIntrant = idIntrant;
	}

	public String getNomIntrant() {
		return nomIntrant;
	}

	public void setNomIntrant(String nomIntrant) {
		this.nomIntrant = nomIntrant;
	}

	public double getTotalQuota() {
		return totalQuota;
	}

	public void setTotalQuota(double totalQuota) {
		this.totalQuota = totalQuota;
	}

	public double getTotalreliquat() {
		return totalreliquat;
	}

	public void setTotalreliquat(double totalreliquat) {
		this.totalreliquat = totalreliquat;
	}

	public Long getIdMep() {
		return idMep;
	}

	public void setIdMep(Long idMep) {
		this.idMep = idMep;
	}

	public int getNombreDePointDeVente() {
		return nombreDePointDeVente;
	}

	public void setNombreDePointDeVente(int nombreDePointDeVente) {
		this.nombreDePointDeVente = nombreDePointDeVente;
	}

	public String getDepartementGroup() {
		return departementGroup;
	}

	public void setDepartementGroup(String departementGroup) {
		this.departementGroup = departementGroup;
	}

	public Long getIdDepartement() {
		return idDepartement;
	}

	public void setIdDepartement(Long idDepartement) {
		this.idDepartement = idDepartement;
	}

	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}

	public Double getTauxCourverture() {
		return tauxCourverture;
	}

	public void setTauxCourverture(Double tauxCourverture) {
		this.tauxCourverture = tauxCourverture;
	}

	public Double getStockResiduel() {
		return stockResiduel;
	}

	public void setStockResiduel(Double stockResiduel) {
		this.stockResiduel = stockResiduel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
}
