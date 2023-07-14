package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.Date;

public class FormaliteInitialeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2866518804000888848L;

	private Long							id;

	private String						nomCommercial;
	private String						numeroOrdre;
	private String						numeroQuit;
	private String						numeroPaiement;
	private String						numeroRccm;
	private String						numeroDeclarant;
	private String						denomination;
	private String						denominationGenerique;
	private String						idType;
	private String						libelleStatut;
	private Date							date;
	private Date							datePaiement;
	private int								typeFormalite;
	private String						fichierQuittance;
	private String						fichierAccuseEnregistrement;

	public Date getDatePaiement() {
		return datePaiement;
	}

	public void setDatePaiement(Date datePaiement) {
		this.datePaiement = datePaiement;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNomCommercial() {
		return nomCommercial;
	}

	public void setNomCommercial(String nomCommercial) {
		this.nomCommercial = nomCommercial;
	}

	public String getNumeroOrdre() {
		return numeroOrdre;
	}

	public void setNumeroOrdre(String numeroOrdre) {
		this.numeroOrdre = numeroOrdre;
	}

	public String getNumeroPaiement() {
		return numeroPaiement;
	}

	public void setNumeroPaiement(String numeroPaiement) {
		this.numeroPaiement = numeroPaiement;
	}

	public int getTypeFormalite() {
		return typeFormalite;
	}

	public void setTypeFormalite(int typeFormalite) {
		this.typeFormalite = typeFormalite;
	}

	public String getNumeroRccm() {
		return numeroRccm;
	}

	public void setNumeroRccm(String numeroRccm) {
		this.numeroRccm = numeroRccm;
	}

	public String getNumeroQuit() {
		return numeroQuit;
	}

	public void setNumeroQuit(String numeroQuit) {
		this.numeroQuit = numeroQuit;
	}

	public String getNumeroDeclarant() {
		return numeroDeclarant;
	}

	public void setNumeroDeclarant(String numeroDeclarant) {
		this.numeroDeclarant = numeroDeclarant;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getFichierQuittance() {
		return fichierQuittance;
	}

	public void setFichierQuittance(String fichierQuittance) {
		this.fichierQuittance = fichierQuittance;
	}

	public String getFichierAccuseEnregistrement() {
		return fichierAccuseEnregistrement;
	}

	public void setFichierAccuseEnregistrement(String fichierAccuseEnregistrement) {
		this.fichierAccuseEnregistrement = fichierAccuseEnregistrement;
	}

	public String getLibelleStatut() {
		return libelleStatut;
	}

	public void setLibelleStatut(String libelleStatut) {
		this.libelleStatut = libelleStatut;
	}

	public String getDenominationGenerique() {
		return denominationGenerique;
	}

	public void setDenominationGenerique(String denominationGenerique) {
		this.denominationGenerique = denominationGenerique;
	}

}
