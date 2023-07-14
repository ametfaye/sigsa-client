package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class PieceJointeDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -874747716355152297L;

	private Long id;
	
	private String code;
	
	private String libelle;
	
	private String nomFichier;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getNomFichier() {
		return nomFichier;
	}

	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}
	
}
