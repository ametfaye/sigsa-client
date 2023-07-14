package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.Date;

public class RechercheSureteDTO implements Serializable {

	private static final long	serialVersionUID	= 5716322277126142779L;

	private String numeroSurete;
	
	private String numeroOrdre;
	
	private String natureSurete;
	
	private String typeSurete;
	
	private Date dateDebut;
	
	private Date dateFin;

	public String getNumeroSurete() {
		return numeroSurete;
	}

	public void setNumeroSurete(String numeroSurete) {
		this.numeroSurete = numeroSurete;
	}

	public String getNumeroOrdre() {
		return numeroOrdre;
	}

	public void setNumeroOrdre(String numeroOrdre) {
		this.numeroOrdre = numeroOrdre;
	}

	public String getNatureSurete() {
		return natureSurete;
	}

	public void setNatureSurete(String natureSurete) {
		this.natureSurete = natureSurete;
	}

	public String getTypeSurete() {
		return typeSurete;
	}

	public void setTypeSurete(String typeSurete) {
		this.typeSurete = typeSurete;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	
}