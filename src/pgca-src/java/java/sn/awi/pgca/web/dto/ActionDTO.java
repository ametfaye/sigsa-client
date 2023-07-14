package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class ActionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9005733466102851726L;
	/**
	 * 
	 */	
	
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String libelle;

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private String code;
	

}
