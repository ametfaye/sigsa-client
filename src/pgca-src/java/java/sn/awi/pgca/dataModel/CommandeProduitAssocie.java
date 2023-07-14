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
@Table(name="pgca_commandeProduitsAssocies")
public class CommandeProduitAssocie implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_cmPA")
	private Long id_cmPA;
	
	
	@Column
	private Double quantitedelaCommande;
	
	@Column
	private float prixUnitaire;
	
	
	@ManyToOne
	@JoinColumn(name = "produit_id", nullable = false)
	private Intrant ProduitVendu;

	
	@ManyToOne
	@JoinColumn(name = "cmd_id", nullable = false)
	private Commande commandeAssocie;


	public Long getId_ventePA() {
		return id_cmPA;
	}


	public void setId_ventePA(Long id_ventePA) {
		this.id_cmPA = id_ventePA;
	}


	@Override
	public Long getId() {
		return id_cmPA;
	}


	public Long getId_cmPA() {
		return id_cmPA;
	}


	public void setId_cmPA(Long id_cmPA) {
		this.id_cmPA = id_cmPA;
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


	public Commande getCommandeAssocie() {
		return commandeAssocie;
	}


	public void setCommandeAssocie(Commande commandeAssocie) {
		this.commandeAssocie = commandeAssocie;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Double getQuantitedelaCommande() {
		return quantitedelaCommande;
	}


	public void setQuantitedelaCommande(Double quantitedelaCommande) {
		this.quantitedelaCommande = quantitedelaCommande;
	}



	
}
