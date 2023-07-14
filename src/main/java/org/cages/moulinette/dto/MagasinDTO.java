package org.cages.moulinette.dto;

import java.io.Serializable;

public class MagasinDTO implements Serializable {


 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String  magasinNom;
    String  magasinCommune;
    Long idCommune ;
    double  magasinStockage;
    String  magasinGerantNom;
    String  magasinGeraantPrenom;
    String  magasinGerantTel;
    String  magasinGerantMaill;
    String  magasinDescription;
    Long idMagasin; 
    Long idStockMagasin;
    
	public String getMagasinNom() {
		return magasinNom;
	}
	public void setMagasinNom(String magasinNom) {
		this.magasinNom = magasinNom;
	}
	public String getMagasinCommune() {
		return magasinCommune;
	}
	public void setMagasinCommune(String magasinCommune) {
		this.magasinCommune = magasinCommune;
	}
	public double getMagasinStockage() {
		return magasinStockage;
	}
	public void setMagasinStockage(double magasinStockage) {
		this.magasinStockage = magasinStockage;
	}
	public String getMagasinGerantNom() {
		return magasinGerantNom;
	}
	public void setMagasinGerantNom(String magasinGerantNom) {
		this.magasinGerantNom = magasinGerantNom;
	}
	public String getMagasinGeraantPrenom() {
		return magasinGeraantPrenom;
	}
	public void setMagasinGeraantPrenom(String magasinGeraantPrenom) {
		this.magasinGeraantPrenom = magasinGeraantPrenom;
	}
	public String getMagasinGerantTel() {
		return magasinGerantTel;
	}
	public void setMagasinGerantTel(String magasinGerantTel) {
		this.magasinGerantTel = magasinGerantTel;
	}
	public String getMagasinDescription() {
		return magasinDescription;
	}
	public void setMagasinDescription(String magasinDescription) {
		this.magasinDescription = magasinDescription;
	}
	public Long getIdCommune() {
		return idCommune;
	}
	public void setIdCommune(Long idCommune) {
		this.idCommune = idCommune;
	}
	public Long getIdMagasin() {
		return idMagasin;
	}
	public void setIdMagasin(Long idMagasin) {
		this.idMagasin = idMagasin;
	}
	public Long getIdStockMagasin() {
		return idStockMagasin;
	}
	public void setIdStockMagasin(Long idStockMagasin) {
		this.idStockMagasin = idStockMagasin;
	}
	public String getMagasinGerantMaill() {
		return magasinGerantMaill;
	}
	public void setMagasinGerantMaill(String magasinGerantMaill) {
		this.magasinGerantMaill = magasinGerantMaill;
	}
    
    
	

}
