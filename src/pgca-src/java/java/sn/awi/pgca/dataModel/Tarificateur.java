package sn.awi.pgca.dataModel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="pgca_tarificateur")
public class Tarificateur implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id_tarificateur;

	@Column(length=50)
	private String libelleProduit;
	
	
	
	@Column(length=50)
	private Boolean subventionne;
	
	@Column
	private Float tauxSubvention; // après deduction taux de subvention
	
	@Column
	private Float prixProducteur; // après deduction taux de subvention
	
	@Column
	private Float prixNonSubventionne; // après deduction taux de subvention
	
	
	@Column
	private Float totalsubventionEtatArecouvrir; // après deduction taux de subvention
	
	
	@Column
	private int quantite; // En kilo, convertit avant l'enregistrement pour determiner le prix au Kilo

	@Column
	private float montantDacquisition;
	
	@Column
	private float fraisTransport;
	
	@Column
	private float fraisDouanese;
	
	@Column
	private float fraisAnnexes;
	
	@Column
	private float prixdeRevientTotal;
	
	
	
	@Column
	private float remiseAppliquee;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date	dateEnVigueur;
	
	@OneToOne
	@JoinColumn(name = "pers_id", nullable = false)
	private Personne auteurTarification;

	public Long getId_tarif() {
		return id_tarificateur;
	}

	public void setId_tarif(Long id_tarif) {
		this.id_tarificateur = id_tarif;
	}

	public String getLibelle() {
		return libelleProduit;
	}

	public void setLibelle(String libelle) {
		this.libelleProduit = libelle;
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

	
	public float getRemiseAppliquee() {
		return remiseAppliquee;
	}

	public Float getTotalsubventionEtatArecouvrir() {
		return totalsubventionEtatArecouvrir;
	}

	public void setTotalsubventionEtatArecouvrir(Float totalsubventionEtatArecouvrir) {
		this.totalsubventionEtatArecouvrir = totalsubventionEtatArecouvrir;
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

	public Personne getAuteurTarification() {
		return auteurTarification;
	}

	public void setAuteurTarification(Personne auteurTarification) {
		this.auteurTarification = auteurTarification;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id_tarificateur;
	}

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

	

	public Float getPrixNonSubventionne() {
		return prixNonSubventionne;
	}

	public void setPrixNonSubventionne(Float prixNonSubventionne) {
		this.prixNonSubventionne = prixNonSubventionne;
	}

	public boolean isSubventionne() {
		return subventionne;
	}

	public void setSubventionne(boolean subventionne) {
		this.subventionne = subventionne;
	}


	public Boolean getSubventionne() {
		return subventionne;
	}

	public void setSubventionne(Boolean subventionne) {
		this.subventionne = subventionne;
	}

	public Float getTauxSubvention() {
		return tauxSubvention;
	}

	public void setTauxSubvention(Float tauxSubvention) {
		this.tauxSubvention = tauxSubvention;
	}

	public void setPrixProducteur(Float prixProducteur) {
		this.prixProducteur = prixProducteur;
	}

	public Float getPrixProducteur() {
		return prixProducteur;
	}
	
}
