package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.Date;

public class TarificateurProdutiDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	private Long id_tarificateur;


	private String libelleProduit;
	
	
	private int quantite; // En kilo, convertit avant l'enregistrement pour determiner le prix au Kilo


	private float montantDacquisition;
	

	private float fraisTransport;
	

	private float fraisDouanese;
	
	
	private float fraisAnnexes;
	

	private float prixdeRevientTotal;
	
	
	private float tauxBeneficeEscompte;
	
	
	private float prixdeVenteSouahite;
	

	private float prixdeVenteEffective;
	
	
	private float remiseAppliquee;
	

	private Date	dateEnVigueur;
	
	
	private Long idauteurTarification;
	
	private String libelleAuteurTarification;

	public Long getId_tarificateur() {
		return id_tarificateur;
	}

	public void setId_tarificateur(Long id_tarificateur) {
		this.id_tarificateur = id_tarificateur;
	}

	public String getLibelleProduit() {
		return libelleProduit;
	}

	public void setLibelleProduit(String libelleProduit) {
		this.libelleProduit = libelleProduit;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public float getMontantDacquisition() {
		return montantDacquisition;
	}

	public void setMontantDacquisition(float montantDacquisition) {
		this.montantDacquisition = montantDacquisition;
	}

	public float getFraisTransport() {
		return fraisTransport;
	}

	public void setFraisTransport(float fraisTransport) {
		this.fraisTransport = fraisTransport;
	}

	public float getFraisDouanese() {
		return fraisDouanese;
	}

	public void setFraisDouanese(float fraisDouanese) {
		this.fraisDouanese = fraisDouanese;
	}

	public float getFraisAnnexes() {
		return fraisAnnexes;
	}

	public void setFraisAnnexes(float fraisAnnexes) {
		this.fraisAnnexes = fraisAnnexes;
	}

	public float getPrixdeRevientTotal() {
		return prixdeRevientTotal;
	}

	public void setPrixdeRevientTotal(float prixdeRevientTotal) {
		this.prixdeRevientTotal = prixdeRevientTotal;
	}

	public float getTauxBeneficeEscompte() {
		return tauxBeneficeEscompte;
	}

	public void setTauxBeneficeEscompte(float tauxBeneficeEscompte) {
		this.tauxBeneficeEscompte = tauxBeneficeEscompte;
	}

	public float getPrixdeVenteSouahite() {
		return prixdeVenteSouahite;
	}

	public void setPrixdeVenteSouahite(float prixdeVenteSouahite) {
		this.prixdeVenteSouahite = prixdeVenteSouahite;
	}

	public float getPrixdeVenteEffective() {
		return prixdeVenteEffective;
	}

	public void setPrixdeVenteEffective(float prixdeVenteEffective) {
		this.prixdeVenteEffective = prixdeVenteEffective;
	}

	public float getRemiseAppliquee() {
		return remiseAppliquee;
	}

	public void setRemiseAppliquee(float remiseAppliquee) {
		this.remiseAppliquee = remiseAppliquee;
	}

	public Date getDateEnVigueur() {
		return dateEnVigueur;
	}

	public void setDateEnVigueur(Date dateEnVigueur) {
		this.dateEnVigueur = dateEnVigueur;
	}

	public Long getIdauteurTarification() {
		return idauteurTarification;
	}

	public void setIdauteurTarification(Long idauteurTarification) {
		this.idauteurTarification = idauteurTarification;
	}

	public String getLibelleAuteurTarification() {
		return libelleAuteurTarification;
	}

	public void setLibelleAuteurTarification(String libelleAuteurTarification) {
		this.libelleAuteurTarification = libelleAuteurTarification;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
