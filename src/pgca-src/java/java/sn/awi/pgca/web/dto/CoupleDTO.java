package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class CoupleDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3311897253615919905L;

	private Long clef;
	
	private String valeur;
	
	private String clefValeur;

	
	// Extrat data for User Management Only
	private Long idPointDeVente;
	private String DepartementPv;
	private int Status;
	private Long idAffectation;
	boolean showBlocActif;
	String blocmsg;

	public CoupleDTO(Long clef, String valeur) {
		super();
		this.clef = clef;
		this.valeur = valeur;
	}

	public CoupleDTO(String v, String nom) {
		super();
		this.valeur = v;
		this.clefValeur = nom;
	}
	
	public CoupleDTO(Long clef, String valeur , String cv) {
		super();
		this.clef = clef;
		this.valeur = valeur;
		this.clefValeur = cv;
	}
	
	
	public Long getClef() {
		return clef;
	}

	public void setClef(Long clef) {
		this.clef = clef;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	
	public String toString() {
		return "key = "+clef+", valeur : " + valeur;
	}
	
	public String CastKeytoString() {
		return "" + clef;
	}

	public String getClefValeur() {
		return clefValeur;
	}

	public void setClefValeur(String clefValeur) {
		this.clefValeur = clefValeur;
	}

	public String getDepartementPv() {
		return DepartementPv;
	}

	public void setDepartementPv(String departementPv) {
		DepartementPv = departementPv;
	}

	public Long getIdPointDeVente() {
		return idPointDeVente;
	}

	public void setIdPointDeVente(Long idPointDeVente) {
		this.idPointDeVente = idPointDeVente;
	}

	public int getStatus() {
		return Status;
	}

	public boolean isShowBlocActif() {
		return showBlocActif;
	}


	public void setShowBlocActif(boolean showBlocActif) {
		this.showBlocActif = showBlocActif;
	}


	public String getBlocmsg() {
		return blocmsg;
	}

	public void setBlocmsg(String blocmsg) {
		this.blocmsg = blocmsg;
	}

	public Long getIdAffectation() {
		return idAffectation;
	}

	public void setIdAffectation(Long idAffectation) {
		this.idAffectation = idAffectation;
	}

	public void setStatus(int status) {
		Status = status;
	}
	
}
