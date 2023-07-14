package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class IndicateursDashboardDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9005733466102851726L;
	/**
	 * 
	 */	
	
	// Mise en place
	 int nbQuotas; 
	 int nbMiseEnPlace;
	 int nbReliquat;
	 
	 // OL
	 
	 int olTraites;
	 int olAcceptes;
	 int olEnAttentes;
	 int olRefuses;
	 
	 
	 // nb litiges
	 int nbLitiges;
	 
	 // indictuers BL
	 int nbBl;
	 
	 
	 // slider dashboard infos AGREGATIONS 
	 Long   stockIdIntrant;
	 double stockFournisseurIntrnantEncours;
	 double stockSedabIntrnantEncours;
	 float 	ventesTotalIntrnantEncours;
	 
	 
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getNbQuotas() {
		return nbQuotas;
	}
	public int getNbMiseEnPlace() {
		return nbMiseEnPlace;
	}
	public int getNbReliquat() {
		return nbReliquat;
	}
	public int getOlTraites() {
		return olTraites;
	}
	public int getOlAcceptes() {
		return olAcceptes;
	}
	public int getOlEnAttentes() {
		return olEnAttentes;
	}
	public int getOlRefuses() {
		return olRefuses;
	}
	public void setNbQuotas(int nbQuotas) {
		this.nbQuotas = nbQuotas;
	}
	public void setNbMiseEnPlace(int nbMiseEnPlace) {
		this.nbMiseEnPlace = nbMiseEnPlace;
	}
	public void setNbReliquat(int nbReliquat) {
		this.nbReliquat = nbReliquat;
	}
	public void setOlTraites(int olTraites) {
		this.olTraites = olTraites;
	}
	public void setOlAcceptes(int olAcceptes) {
		this.olAcceptes = olAcceptes;
	}
	public void setOlEnAttentes(int olEnAttentes) {
		this.olEnAttentes = olEnAttentes;
	}
	public void setOlRefuses(int olRefuses) {
		this.olRefuses = olRefuses;
	}
	public int getNbLitiges() {
		return nbLitiges;
	}
	public void setNbLitiges(int nbLitiges) {
		this.nbLitiges = nbLitiges;
	}
	public int getNbBl() {
		return nbBl;
	}

	public float getVentesTotalIntrnantEncours() {
		return ventesTotalIntrnantEncours;
	}
	public void setVentesTotalIntrnantEncours(float ventesTotalIntrnantEncours) {
		this.ventesTotalIntrnantEncours = ventesTotalIntrnantEncours;
	}
	public void setNbBl(int nbBl) {
		this.nbBl = nbBl;
	}
	public Long getStockIdIntrant() {
		return stockIdIntrant;
	}
	public void setStockIdIntrant(Long stockIdIntrant) {
		this.stockIdIntrant = stockIdIntrant;
	}
	public double getStockFournisseurIntrnantEncours() {
		return stockFournisseurIntrnantEncours;
	}
	public void setStockFournisseurIntrnantEncours(double stockFournisseurIntrnantEncours) {
		this.stockFournisseurIntrnantEncours = stockFournisseurIntrnantEncours;
	}
	public double getStockSedabIntrnantEncours() {
		return stockSedabIntrnantEncours;
	}
	public void setStockSedabIntrnantEncours(double stockSedabIntrnantEncours) {
		this.stockSedabIntrnantEncours = stockSedabIntrnantEncours;
	}
	 

}
