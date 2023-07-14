package sn.awi.pgca.dataModel;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="pgca_ventes")
public class Ventes implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ventes_id")
	private Long ventes_id;
	
	@OneToOne
	@JoinColumn(name = "stock_id", nullable = false)
	private Stock stockVente;
	
	@OneToOne
	@JoinColumn(name = "pers_id", nullable = true)
	private Personne vendeur;
	
	
	
	@Column
	private String client;
	
	@Column
	private String telClient;
	
	
	@Column
	private String adresseClient;
	
	
	
	@Column
	private float montantVente;
	
	@Column
	private float montantEncaisse;
	
	@Column
	private float montantPayeEnEspece;
	
	@Column
	private float montantPayeEnCheque;

	@Column
	private float montantPayeBLP;
	
	@Column
	private String numeroBLP;

	
	@Column
	private float montantPayeEnBonDeSubvention;
	
	@Column
	private String moyenPaiement;
	
	@Column
	private int qunatiteVendu;
	
	@Column
	private String zoneVente;
	
	
	@Column
	private String											dateDeVente;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return ventes_id;
	}

	public Long getVentes_id() {
		return ventes_id;
	}

	public void setVentes_id(Long ventes_id) {
		this.ventes_id = ventes_id;
	}

	public Stock getStockVente() {
		return stockVente;
	}

	public void setStockVente(Stock stockVente) {
		this.stockVente = stockVente;
	}

	public Personne getVendeur() {
		return vendeur;
	}

	public void setVendeur(Personne vendeur) {
		this.vendeur = vendeur;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public float getMontantVente() {
		return montantVente;
	}

	public void setMontantVente(float montantVente) {
		this.montantVente = montantVente;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMoyenPaiement() {
		return moyenPaiement;
	}

	public void setMoyenPaiement(String moyenPaiement) {
		this.moyenPaiement = moyenPaiement;
	}


	public int getQunatiteVendu() {
		return qunatiteVendu;
	}

	public void setQunatiteVendu(int qunatiteVendu) {
		this.qunatiteVendu = qunatiteVendu;
	}

	public float getMontantEncaisse() {
		return montantEncaisse;
	}

	public void setMontantEncaisse(float montantEncaisse) {
		this.montantEncaisse = montantEncaisse;
	}

	public String getTelClient() {
		return telClient;
	}

	public void setTelClient(String telClient) {
		this.telClient = telClient;
	}

	public String getAdresseClient() {
		return adresseClient;
	}

	public void setAdresseClient(String adresseClient) {
		this.adresseClient = adresseClient;
	}

	public String getDateDeVente() {
		return dateDeVente;
	}

	public void setDateDeVente(String dateDeVente) {
		this.dateDeVente = dateDeVente;
	}

	public String getZoneVente() {
		return zoneVente;
	}

	public void setZoneVente(String zoneVente) {
		this.zoneVente = zoneVente;
	}

	public float getMontantPayeEnEspece() {
		return montantPayeEnEspece;
	}

	public void setMontantPayeEnEspece(float montantPayeEnEspece) {
		this.montantPayeEnEspece = montantPayeEnEspece;
	}

	public float getMontantPayeEnCheque() {
		return montantPayeEnCheque;
	}

	public void setMontantPayeEnCheque(float montantPayeEnCheque) {
		this.montantPayeEnCheque = montantPayeEnCheque;
	}

	public float getMontantPayeEnBonDeSubvention() {
		return montantPayeEnBonDeSubvention;
	}

	public float getMontantPayeBLP() {
		return montantPayeBLP;
	}

	public String getNumeroBLP() {
		return numeroBLP;
	}

	public void setMontantPayeBLP(float montantPayeBLP) {
		this.montantPayeBLP = montantPayeBLP;
	}

	public void setNumeroBLP(String numeroBLP) {
		this.numeroBLP = numeroBLP;
	}

	public void setMontantPayeEnBonDeSubvention(float montantPayeEnBonDeSubvention) {
		this.montantPayeEnBonDeSubvention = montantPayeEnBonDeSubvention;
	}


}
