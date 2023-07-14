package org.cages.moulinette.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

public class InfosCommunesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5302990311959163736L;

	
	private String communeLibelle;
	private String communeid;
	

	private String departementLibelle;
	private String departementid;
	
	private String regionLibelle;
	private String regionid;
	
	
	
	private Long idCommune;
	
	public String getCommuneLibelle() {
		return communeLibelle;
	}
	public void setCommuneLibelle(String communeLibelle) {
		this.communeLibelle = communeLibelle;
	}
	public String getCommuneid() {
		return communeid;
	}
	public void setCommuneid(String communeid) {
		this.communeid = communeid;
	}
	public String getDepartementLibelle() {
		return departementLibelle;
	}
	public void setDepartementLibelle(String departementLibelle) {
		this.departementLibelle = departementLibelle;
	}
	public String getDepartementid() {
		return departementid;
	}
	public void setDepartementid(String departementid) {
		this.departementid = departementid;
	}
	public String getRegionLibelle() {
		return regionLibelle;
	}
	public void setRegionLibelle(String regionLibelle) {
		this.regionLibelle = regionLibelle;
	}
	public String getRegionid() {
		return regionid;
	}
	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}
	public Long getIdCommune() {
		return idCommune;
	}
	public void setIdCommune(Long idCommune) {
		this.idCommune = idCommune;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
