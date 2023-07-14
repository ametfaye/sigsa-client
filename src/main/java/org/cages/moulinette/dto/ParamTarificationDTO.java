package org.cages.moulinette.dto;

import java.io.Serializable;

public class ParamTarificationDTO implements Serializable {
	
	private static final long	serialVersionUID	= -1759155716990529781L;

	private Long id;
	
	private String idEntiteJuridique;
	
	private String idTypeFormulaire;
	
	private String libelleTypeFormulaire;
	
	private String typeUniteBase;
	
	private double coutBase;
	
	private double uniteDeBase;
	
	private double coutParUniteBase;
	
	private double fraisDeGestion;
	
	private String strCoutBase;
	
	private String strUniteDeBase;
	
	private String strCoutParUniteBase;
	
	private String strFraisDeGestion;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdEntiteJuridique() {
		return idEntiteJuridique;
	}

	public void setIdEntiteJuridique(String idEntiteJuridique) {
		this.idEntiteJuridique = idEntiteJuridique;
	}

	public String getIdTypeFormulaire() {
		return idTypeFormulaire;
	}

	public void setIdTypeFormulaire(String idTypeFormulaire) {
		this.idTypeFormulaire = idTypeFormulaire;
	}

	public String getLibelleTypeFormulaire() {
		return libelleTypeFormulaire;
	}

	public void setLibelleTypeFormulaire(String libelleTypeFormulaire) {
		this.libelleTypeFormulaire = libelleTypeFormulaire;
	}

	public String getTypeUniteBase() {
		return typeUniteBase;
	}

	public void setTypeUniteBase(String typeUniteBase) {
		this.typeUniteBase = typeUniteBase;
	}

	public double getCoutBase() {
		return coutBase;
	}

	public void setCoutBase(double coutBase) {
		this.coutBase = coutBase;
	}

	public double getUniteDeBase() {
		return uniteDeBase;
	}

	public void setUniteDeBase(double uniteDeBase) {
		this.uniteDeBase = uniteDeBase;
	}

	public double getCoutParUniteBase() {
		return coutParUniteBase;
	}

	public void setCoutParUniteBase(double coutParUniteBase) {
		this.coutParUniteBase = coutParUniteBase;
	}

	public double getFraisDeGestion() {
		return fraisDeGestion;
	}

	public void setFraisDeGestion(double fraisDeGestion) {
		this.fraisDeGestion = fraisDeGestion;
	}

	public String getStrCoutBase() {
		return strCoutBase;
	}

	public void setStrCoutBase(String strCoutBase) {
		this.strCoutBase = strCoutBase;
	}

	public String getStrUniteDeBase() {
		return strUniteDeBase;
	}

	public void setStrUniteDeBase(String strUniteDeBase) {
		this.strUniteDeBase = strUniteDeBase;
	}

	public String getStrCoutParUniteBase() {
		return strCoutParUniteBase;
	}

	public void setStrCoutParUniteBase(String strCoutParUniteBase) {
		this.strCoutParUniteBase = strCoutParUniteBase;
	}

	public String getStrFraisDeGestion() {
		return strFraisDeGestion;
	}

	public void setStrFraisDeGestion(String strFraisDeGestion) {
		this.strFraisDeGestion = strFraisDeGestion;
	}
	
}
