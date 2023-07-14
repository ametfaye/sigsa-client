package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.Date;

public class AvanceCreditDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5302990311959163736L;

	
	private Long id_avance;

	private String moyenPaiement; 

	private Date dateAvance; 

	
	private float montanance;
	
	private String auteurPaiement;

	public Long getId_avance() {
		return id_avance;
	}

	public void setId_avance(Long id_avance) {
		this.id_avance = id_avance;
	}

	public String getMoyenPaiement() {
		return moyenPaiement;
	}

	public void setMoyenPaiement(String moyenPaiement) {
		this.moyenPaiement = moyenPaiement;
	}

	public Date getDateAvance() {
		return dateAvance;
	}

	public void setDateAvance(Date dateAvance) {
		this.dateAvance = dateAvance;
	}

	public float getMontanance() {
		return montanance;
	}

	public void setMontanance(float montanance) {
		this.montanance = montanance;
	}

	public String getAuteurPaiement() {
		return auteurPaiement;
	}

	public void setAuteurPaiement(String auteurPaiement) {
		this.auteurPaiement = auteurPaiement;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
