package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class QuittanceDTO implements Serializable {

	private static final long serialVersionUID = 3861609163636441516L;

	private String id;
	
	private String numeroOrdre;
	
	private String denomination;
	
	private String numeroQuit;
	
	private String libelleDemande;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumeroOrdre() {
		return numeroOrdre;
	}

	public void setNumeroOrdre(String numeroOrdre) {
		this.numeroOrdre = numeroOrdre;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public String getNumeroQuit() {
		return numeroQuit;
	}

	public void setNumeroQuit(String numeroQuit) {
		this.numeroQuit = numeroQuit;
	}

	public String getLibelleDemande() {
		return libelleDemande;
	}

	public void setLibelleDemande(String libelleDemande) {
		this.libelleDemande = libelleDemande;
	}
	
	
}
