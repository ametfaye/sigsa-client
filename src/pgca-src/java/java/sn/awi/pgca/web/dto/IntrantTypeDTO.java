package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class IntrantTypeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7838560630950854563L;

	private Long							id;
	private String						 libelle;
	private String						 picto;
	private String						 unitedeMesure;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getPicto() {
		return picto;
	}
	public void setPicto(String picto) {
		this.picto = picto;
	}
	public String getUnitedeMesure() {
		return unitedeMesure;
	}
	public void setUnitedeMesure(String unitedeMesure) {
		this.unitedeMesure = unitedeMesure;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
