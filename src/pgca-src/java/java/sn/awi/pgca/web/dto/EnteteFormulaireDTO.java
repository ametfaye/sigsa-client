package sn.awi.pgca.web.dto;

import java.io.Serializable;


public class EnteteFormulaireDTO implements Serializable {
	
	private static final long	serialVersionUID	= -9152493625429094170L;
	private String idEnteteFormulaire;
	private String numero_rccm;
	private String numeroOrdre;
	private String numeroFormalite;
	private String numeroQuittance;
	private String nomCommercial;
	private String denomination;
	private String datearrivee;
	private String datedemande;
	private String libelleEtat;
	private String libelleDeclaration;
	private String libelleMandataire;
	private String datepaiement;
	private String dateenregistrement;
	private String codeTypeFormulaire;

	public String getNumero_rccm() {
		return numero_rccm;
	}

	public void setNumero_rccm(String numero_rccm) {
		this.numero_rccm = numero_rccm;
	}

	public String getIdEnteteFormulaire() {
		return idEnteteFormulaire;
	}

	public void setIdEnteteFormulaire(String idEnteteFormulaire) {
		this.idEnteteFormulaire = idEnteteFormulaire;
	}

	public String getNomCommercial() {
		return nomCommercial;
	}

	public void setNomCommercial(String nomCommercial) {
		this.nomCommercial = nomCommercial;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}
	
	public void  cleanData()
	{
		  numero_rccm  = "";
		  setNumeroOrdre("");
		  nomCommercial  = "";
		  denomination  = "";
		  datearrivee  = "";
	}

	public String getLibelleEtat() {
		return libelleEtat;
	}

	public void setLibelleEtat(String libelleEtat) {
		this.libelleEtat = libelleEtat;
	}

	public String getLibelleDeclaration() {
		return libelleDeclaration;
	}

	public void setLibelleDeclaration(String libelleDeclaration) {
		this.libelleDeclaration = libelleDeclaration;
	}

	public String getLibelleMandataire() {
		return libelleMandataire;
	}

	public void setLibelleMandataire(String libelleMandataire) {
		this.libelleMandataire = libelleMandataire;
	}

	public String getDatedemande() {
		return datedemande;
	}

	public void setDatedemande(String datedemande) {
		this.datedemande = datedemande;
	}

	public String getDatearrivee() {
		return datearrivee;
	}

	public void setDatearrivee(String datearrivee) {
		this.datearrivee = datearrivee;
	}

	public String getDatepaiement() {
		return datepaiement;
	}

	public void setDatepaiement(String datepaiement) {
		this.datepaiement = datepaiement;
	}

	public String getDateenregistrement() {
		return dateenregistrement;
	}

	public void setDateenregistrement(String dateenregistrement) {
		this.dateenregistrement = dateenregistrement;
	}

	public String getCodeTypeFormulaire() {
		return codeTypeFormulaire;
	}

	public void setCodeTypeFormulaire(String codeTypeFormulaire) {
		this.codeTypeFormulaire = codeTypeFormulaire;
	}

	public String getNumeroOrdre() {
		return numeroOrdre;
	}

	public void setNumeroOrdre(String numeroOrdre) {
		this.numeroOrdre = numeroOrdre;
	}

	public String getNumeroQuittance() {
		return numeroQuittance;
	}

	public void setNumeroQuittance(String numeroQuittance) {
		this.numeroQuittance = numeroQuittance;
	}

	public String getNumeroFormalite() {
		return numeroFormalite;
	}

	public void setNumeroFormalite(String numeroFormalite) {
		this.numeroFormalite = numeroFormalite;
	}
}
