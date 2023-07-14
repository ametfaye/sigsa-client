package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AlertesInformationsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9005733466102851726L;
	/**
	 * 
	 */	
	Date dateDebut;
	Date dateFin;
	
	List<MiseEnplaceDTOPointDeVente> listeDesMisesEnPlaceAenvoyer  ;
	List<MiseEnplaceDTOPointDeVente> listeDesMisesEnPlaceDetaisAenvoyer ;

	List<IntrantDTO> listeDesStockResiduelAenvoyer ;
	
	List<CreditDTO> listeDesCreditsAenvoyer;
	Double totallisteDesCreditsAenvoyer;

	// MEP AGGREGE PAR INTRANT
	List<VersementBanqueDTO> listeDesVersementAenvoyer ;
	Double  totallisteDesVersementAenvoyer ;
	
	// MEP EN DETAILS
	List<IntrantDTO> listeStockResiduelAEnvoyer;
	List<IntrantDTO> listeStockFournisseurAenvoyer;
	
	

	
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

	public List<IntrantDTO> getListeDesStockResiduelAenvoyer() {
		return listeDesStockResiduelAenvoyer;
	}
	public void setListeDesStockResiduelAenvoyer(List<IntrantDTO> listeDesStockResiduelAenvoyer) {
		this.listeDesStockResiduelAenvoyer = listeDesStockResiduelAenvoyer;
	}
	public List<CreditDTO> getListeDesCreditsAenvoyer() {
		return listeDesCreditsAenvoyer;
	}
	public void setListeDesCreditsAenvoyer(List<CreditDTO> listeDesCreditsAenvoyer) {
		this.listeDesCreditsAenvoyer = listeDesCreditsAenvoyer;
	}
	public List<VersementBanqueDTO> getListeDesVersementAenvoyer() {
		return listeDesVersementAenvoyer;
	}
	public void setListeDesVersementAenvoyer(List<VersementBanqueDTO> listeDesVersementAenvoyer) {
		this.listeDesVersementAenvoyer = listeDesVersementAenvoyer;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Double getTotallisteDesCreditsAenvoyer() {
		return totallisteDesCreditsAenvoyer;
	}
	public void setTotallisteDesCreditsAenvoyer(Double totallisteDesCreditsAenvoyer) {
		this.totallisteDesCreditsAenvoyer = totallisteDesCreditsAenvoyer;
	}
	public Double getTotallisteDesVersementAenvoyer() {
		return totallisteDesVersementAenvoyer;
	}
	public void setTotallisteDesVersementAenvoyer(Double totallisteDesVersementAenvoyer) {
		this.totallisteDesVersementAenvoyer = totallisteDesVersementAenvoyer;
	}
	public List<MiseEnplaceDTOPointDeVente> getListeDesMisesEnPlaceAenvoyer() {
		return listeDesMisesEnPlaceAenvoyer;
	}
	public void setListeDesMisesEnPlaceAenvoyer(List<MiseEnplaceDTOPointDeVente> listeDesMisesEnPlaceAenvoyer) {
		this.listeDesMisesEnPlaceAenvoyer = listeDesMisesEnPlaceAenvoyer;
	}
	public List<IntrantDTO> getListeStockResiduelAEnvoyer() {
		return listeStockResiduelAEnvoyer;
	}
	public void setListeStockResiduelAEnvoyer(List<IntrantDTO> listeStockResiduelAEnvoyer) {
		this.listeStockResiduelAEnvoyer = listeStockResiduelAEnvoyer;
	}
	public List<IntrantDTO> getListeStockFournisseurAenvoyer() {
		return listeStockFournisseurAenvoyer;
	}

	public List<MiseEnplaceDTOPointDeVente> getListeDesMisesEnPlaceDetaisAenvoyer() {
		return listeDesMisesEnPlaceDetaisAenvoyer;
	}
	public void setListeDesMisesEnPlaceDetaisAenvoyer(List<MiseEnplaceDTOPointDeVente> listeDesMisesEnPlaceDetaisAenvoyer) {
		this.listeDesMisesEnPlaceDetaisAenvoyer = listeDesMisesEnPlaceDetaisAenvoyer;
	}
	public void setListeStockFournisseurAenvoyer(List<IntrantDTO> listeStockFournisseurAenvoyer) {
		this.listeStockFournisseurAenvoyer = listeStockFournisseurAenvoyer;
	}


}
