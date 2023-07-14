package sn.awi.pgca.dataModel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="pgca_OrdreLivraison")
public class OrdreLivraison implements Serializable, GenericModel, Cloneable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8147363805505994466L;

	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ord_id")
	private Long ord_id;
	
	@Column
	private String referenceOrdre;
	
	@Column
	private String dateOrdre;
	
	@Column
	private String dateLivraisonSouhaiteOrdre;
	
	@Column
	private int statutOrd;
	
	@Column
	private float montantTotalOrdre;
	

	@Column
	private String nomClient;
	


	@Column
	private String numeroCNIClient;
	


	
	@OneToOne
	@JoinColumn(name = "ptv_id", nullable = true)
	private PointDeVente  pointdeVenteTraitantLaCMD;
	
	/*@OneToOne
	@JoinColumn(name = "pdc_id", nullable = true)
	private PointDeCollecte  pointdeCollecteTraitantLaCMD;*/
	
	
	/*** Editeur de la commande ****/
	@OneToOne
	@JoinColumn(name = "pdc_id", nullable = false)  // qu
	private PointDeCollecte  editeurCommandePointdeCollecte;
	
	@OneToOne
	@JoinColumn(name = "pers_id", nullable = false)
	private Personne editeurCommandePersonne;
	
	
	/****  client qui recevra la commnde **/
	@Column
	private String clientCommuneDechargement; 
	
	@Column
	private String clientNom; 
	
	@Column
	private String clientAddresse; 
	
	@Column
	private String clientTel; 
	
	

	@OneToOne
	@JoinColumn(name = "pgca_idprog", nullable = false)
	private ProgrammeAgricol programmeConcerne;



	public Long getOrd_id() {
		return ord_id;
	}



	public void setOrd_id(Long ord_id) {
		this.ord_id = ord_id;
	}



	public String getReferenceOrdre() {
		return referenceOrdre;
	}



	public void setReferenceOrdre(String referenceOrdre) {
		this.referenceOrdre = referenceOrdre;
	}



	public String getDateOrdre() {
		return dateOrdre;
	}



	public void setDateOrdre(String dateOrdre) {
		this.dateOrdre = dateOrdre;
	}



	public String getDateLivraisonSouhaiteOrdre() {
		return dateLivraisonSouhaiteOrdre;
	}



	public void setDateLivraisonSouhaiteOrdre(String dateLivraisonSouhaiteOrdre) {
		this.dateLivraisonSouhaiteOrdre = dateLivraisonSouhaiteOrdre;
	}



	public int getStatutOrd() {
		return statutOrd;
	}



	public void setStatutOrd(int statutOrd) {
		this.statutOrd = statutOrd;
	}



	public float getMontantTotalOrdre() {
		return montantTotalOrdre;
	}



	public void setMontantTotalOrdre(float montantTotalOrdre) {
		this.montantTotalOrdre = montantTotalOrdre;
	}



	public String getNomClient() {
		return nomClient;
	}



	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}





	public String getNumeroCNIClient() {
		return numeroCNIClient;
	}



	public void setNumeroCNIClient(String numeroCNIClient) {
		this.numeroCNIClient = numeroCNIClient;
	}



	public PointDeVente getPointdeVenteTraitantLaCMD() {
		return pointdeVenteTraitantLaCMD;
	}



	public void setPointdeVenteTraitantLaCMD(PointDeVente pointdeVenteTraitantLaCMD) {
		this.pointdeVenteTraitantLaCMD = pointdeVenteTraitantLaCMD;
	}



	public PointDeCollecte getEditeurCommandePointdeCollecte() {
		return editeurCommandePointdeCollecte;
	}



	public void setEditeurCommandePointdeCollecte(PointDeCollecte editeurCommandePointdeCollecte) {
		this.editeurCommandePointdeCollecte = editeurCommandePointdeCollecte;
	}



	public Personne getEditeurCommandePersonne() {
		return editeurCommandePersonne;
	}



	public void setEditeurCommandePersonne(Personne editeurCommandePersonne) {
		this.editeurCommandePersonne = editeurCommandePersonne;
	}



	public String getClientCommuneDechargement() {
		return clientCommuneDechargement;
	}



	public void setClientCommuneDechargement(String clientCommuneDechargement) {
		this.clientCommuneDechargement = clientCommuneDechargement;
	}



	public String getClientNom() {
		return clientNom;
	}



	public void setClientNom(String clientNom) {
		this.clientNom = clientNom;
	}



	public String getClientAddresse() {
		return clientAddresse;
	}



	public void setClientAddresse(String clientAddresse) {
		this.clientAddresse = clientAddresse;
	}



	public String getClientTel() {
		return clientTel;
	}



	public void setClientTel(String clientTel) {
		this.clientTel = clientTel;
	}



	public ProgrammeAgricol getProgrammeConcerne() {
		return programmeConcerne;
	}



	public void setProgrammeConcerne(ProgrammeAgricol programmeConcerne) {
		this.programmeConcerne = programmeConcerne;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public Long getId() {
		return ord_id;
	}

}
