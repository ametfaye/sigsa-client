package sn.awi.pgca.dataModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="pgca_stock")
public class Stock implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="stock_id")
	private Long stock_id;   

	@Column(length=120)
	private String libelle;
	
	@Column(length=60)
	private String code;
	
	
	@Column(length=120)
	private Long quantite;
	
	
	@OneToOne
	@JoinColumn(name = "commune_id", nullable = true)
	private Commune communePointDevente;
	
	
	@Column
	private Long idMagasin;
	
	@Column
	private Long idPointdeCollecte;
	
	@Column
	private Long idPointdeVente;
	
	
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "stockRef", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@OrderBy("produit_id")
	private List<Intrant> produits;


	public Long getStock_id() {
		return stock_id;
	}


	public void setStock_id(Long stock_id) {
		this.stock_id = stock_id;
	}



	public List<Intrant> getProduits() {
		return produits;
	}


	public void setProduits(List<Intrant> produits) {
		this.produits = produits;
	}


	@Override
	public Long getId() {
		return stock_id;
	}


	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public Long getIdMagasin() {
		return idMagasin;
	}


	public void setIdMagasin(Long idMagasin) {
		this.idMagasin = idMagasin;
	}


	public Long getIdPointdeCollecte() {
		return idPointdeCollecte;
	}


	public void setIdPointdeCollecte(Long idPointdeCollecte) {
		this.idPointdeCollecte = idPointdeCollecte;
	}


	public Long getIdPointdeVente() {
		return idPointdeVente;
	}


	public void setIdPointdeVente(Long idPointdeVente) {
		this.idPointdeVente = idPointdeVente;
	}


	public Long getQuantite() {
		return quantite;
	}


	public void setQuantite(Long quantite) {
		this.quantite = quantite;
	}


	public Commune getCommunePointDevente() {
		return communePointDevente;
	}


	public void setCommunePointDevente(Commune communePointDevente) {
		this.communePointDevente = communePointDevente;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}

	
}
