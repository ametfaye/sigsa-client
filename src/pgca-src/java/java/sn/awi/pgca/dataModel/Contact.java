package sn.awi.pgca.dataModel;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cont_contact")
public class Contact implements Serializable, GenericModel {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="cont_id")
	private Long id;

	@Column(length=50)
	private String libelle;

	@Column(length=20)
	private String mobile;

	@Column(length=50)
	private String fixe;
	
	@Column(length=100)
	private String courriel;
	
	@Column(length=20)
	private String fax;
	
	@Column(length=100)
	private String site_web;

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

	public String getFax() {
		return fax;
	}

	public String getSite_web() {
		return site_web;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setSite_web(String site_web) {
		this.site_web = site_web;
	}


	
}
