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
@Table(name="pgca_OrdreLProduitsAssocies")
public class OrdreLivraisonProduitAssocie implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id_ola;
	
	
	@Column
	private Double quantitedelaCommande;
	
	@Column
	private float prixUnitaire;
	
	@OneToOne
	@JoinColumn(name = "produit_id", nullable = true)
	private Intrant ProduitVendu;

	
	@OneToOne
	@JoinColumn(name = "ord_id", nullable = false )
	private OrdreLivraison commandeAssocie;


	public Long getId_ola() {
		return id_ola;
	}


	public void setId_ola(Long id_ola) {
		this.id_ola = id_ola;
	}


	public Double getQuantitedelaCommande() {
		return quantitedelaCommande;
	}


	public void setQuantitedelaCommande(Double quantitedelaCommande) {
		this.quantitedelaCommande = quantitedelaCommande;
	}


	public float getPrixUnitaire() {
		return prixUnitaire;
	}


	public void setPrixUnitaire(float prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}


	public Intrant getProduitVendu() {
		return ProduitVendu;
	}


	public void setProduitVendu(Intrant produitVendu) {
		ProduitVendu = produitVendu;
	}


	public OrdreLivraison getCommandeAssocie() {
		return commandeAssocie;
	}


	public void setCommandeAssocie(OrdreLivraison commandeAssocie) {
		this.commandeAssocie = commandeAssocie;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id_ola;
	}

	
}
