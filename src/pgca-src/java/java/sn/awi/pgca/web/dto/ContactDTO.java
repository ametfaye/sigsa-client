package sn.awi.pgca.web.dto;

import java.io.Serializable;

import sn.awi.pgca.utils.UtilString;

public class ContactDTO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 9219008770835538193L;

	private Long							id;
	private String						libelle;
	private String						mobile;
	private String						fixe;
	private String						courriel;
	private String						fax;
	private String						site;

	public boolean isCorrect() {
		return (UtilString.isCorrect(libelle) || UtilString.isCorrect(mobile) || UtilString.isCorrect(fixe) || UtilString.isCorrect(courriel) || UtilString.isCorrect(fax) || UtilString.isCorrect(site));
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFixe() {
		return fixe;
	}

	public void setFixe(String fixe) {
		this.fixe = fixe;
	}

	public String getCourriel() {
		return courriel;
	}

	public void setCourriel(String courriel) {
		this.courriel = courriel;
	}
}
