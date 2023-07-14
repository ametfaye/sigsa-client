package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class VerificationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6898138444715471660L;

	/**
	 * 
	 */

	private  String nomCommercial;
	
	private String denomination;
	
	private int numeroOrdre;
	
	private String  typeFormalite;
	
	private int numeroQuittance;
	
	public String getNomCommercial() {
		return nomCommercial;
	}

	public void setNomCommercial(String nomCommercial) {
		this.nomCommercial = nomCommercial;
	}

	public String getCodeProfil() {
		return codeProfil;
	}

	public void setCodeProfil(String codeProfil) {
		this.codeProfil = codeProfil;
	}

	public String getCodeutilisateur() {
		return codeutilisateur;
	}

	public void setCodeutilisateur(String codeutilisateur) {
		this.codeutilisateur = codeutilisateur;
	}

	private  String codeProfil;
	
	private  String codeutilisateur;

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public int getNumeroOrdre() {
		return numeroOrdre;
	}

	public void setNumeroOrdre(int numeroOrdre) {
		this.numeroOrdre = numeroOrdre;
	}

	public String getTypeFormalite() {
		return typeFormalite;
	}

	public void setTypeFormalite(String typeFormalite) {
		this.typeFormalite = typeFormalite;
	}

	public int getNumeroQuittance() {
		return numeroQuittance;
	}

	public void setNumeroQuittance(int numeroQuittance) {
		this.numeroQuittance = numeroQuittance;
	}
	
	
	
}
