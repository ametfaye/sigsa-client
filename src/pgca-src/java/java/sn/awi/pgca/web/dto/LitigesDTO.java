package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class LitigesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5302990311959163736L;

	
	private Long idLitiges;

	private boolean status;
	
	private Double quantiteManquante;
	
	private String receptionnaireAgent;

	private String receptionnaireMagsin;

	private String chauffeur;

	private String detailsLitige;

	private int nombreDesacs;
	
	private Double quantiteTotalLige;
	
	private String refenceBL;


	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getIdLitiges() {
		return idLitiges;
	}

	public boolean isStatus() {
		return status;
	}

	public String getReceptionnaireAgent() {
		return receptionnaireAgent;
	}

	public String getReceptionnaireMagsin() {
		return receptionnaireMagsin;
	}

	public String getChauffeur() {
		return chauffeur;
	}

	public String getDetailsLitige() {
		return detailsLitige;
	}

	public Date getDateEnregistrementLitige() {
		return dateEnregistrementLitige;
	}

	public void setIdLitiges(Long idLitiges) {
		this.idLitiges = idLitiges;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setReceptionnaireAgent(String receptionnaireAgent) {
		this.receptionnaireAgent = receptionnaireAgent;
	}

	public void setReceptionnaireMagsin(String receptionnaireMagsin) {
		this.receptionnaireMagsin = receptionnaireMagsin;
	}

	public void setChauffeur(String chauffeur) {
		this.chauffeur = chauffeur;
	}

	public void setDetailsLitige(String detailsLitige) {
		this.detailsLitige = detailsLitige;
	}

	public Double getQuantiteManquante() {
		return quantiteManquante;
	}

	public void setQuantiteManquante(Double quantiteManquante) {
		this.quantiteManquante = quantiteManquante;
	}

	public void setDateEnregistrementLitige(Date dateEnregistrementLitige) {
		this.dateEnregistrementLitige = dateEnregistrementLitige;
	}

	private Date dateEnregistrementLitige;

	public int getNombreDesacs() {
		return nombreDesacs;
	}

	public Double getQuantiteTotalLige() {
		return quantiteTotalLige;
	}

	public void setNombreDesacs(int nombreDesacs) {
		this.nombreDesacs = nombreDesacs;
	}

	public void setQuantiteTotalLige(Double quantiteTotalLige) {
		this.quantiteTotalLige = quantiteTotalLige;
	}

	public String getRefenceBL() {
		return refenceBL;
	}

	public void setNombreDesacs(Integer nombreDesacs) {
		this.nombreDesacs = nombreDesacs;
	}

	public void setRefenceBL(String refenceBL) {
		this.refenceBL = refenceBL;
	}

	
	
}
