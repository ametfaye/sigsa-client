package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.Date;

public class ActiviteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5302990311959163736L;

	
	private Long id;


	private String natureactivite;


	private Date datedebut;


	private Date datefin;
	
	private String secteurActiviteId;
	
	private String secteurActiviteLibelle;
	
	private String numerotmp ;// numero temporraire

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNatureactivite() {
		return natureactivite;
	}


	public void setNatureactivite(String natureactivite) {
		this.natureactivite = natureactivite;
	}


	public Date getDatedebut() {
		return datedebut;
	}


	public void setDatedebut(Date datedebut) {
		this.datedebut = datedebut;
	}


	public Date getDatefin() {
		return datefin;
	}


	public void setDatefin(Date datefin) {
		this.datefin = datefin;
	}


	public String getSecteurActiviteId() {
		return secteurActiviteId;
	}


	public void setSecteurActiviteId(String secteurActiviteId) {
		this.secteurActiviteId = secteurActiviteId;
	}


	public String getNumerotmp() {
		return numerotmp;
	}


	public void setNumerotmp(String numerotmp) {
		this.numerotmp = numerotmp;
	}


	public String getSecteurActiviteLibelle() {
		return secteurActiviteLibelle;
	}


	public void setSecteurActiviteLibelle(String secteurActiviteLibelle) {
		this.secteurActiviteLibelle = secteurActiviteLibelle;
	}


}
