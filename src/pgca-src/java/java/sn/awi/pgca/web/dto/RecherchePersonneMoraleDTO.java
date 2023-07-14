package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.Date;

public class RecherchePersonneMoraleDTO implements Serializable {
	
	private static final long	serialVersionUID	= -6265366482807905546L;

	private String numerorccm;
	
	private String numeroordre;
	
	private String denomination;
	
	private Date dateDebut;
	
	private Date dateFin;

	public String getNumerorccm() {
		return numerorccm;
	}

	public void setNumerorccm(String numerorccm) {
		this.numerorccm = numerorccm;
	}

	public String getNumeroordre() {
		return numeroordre;
	}

	public void setNumeroordre(String numeroordre) {
		this.numeroordre = numeroordre;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
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