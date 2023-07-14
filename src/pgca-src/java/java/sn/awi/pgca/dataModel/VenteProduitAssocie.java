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
@Table(name="pgca_venteProduitsAssocies")
public class VenteProduitAssocie implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_ventePA")
	private Long id_ventePA;
	
	
	@Column
	private Double quantiteVendu;
	
	@Column
	private float prixUnitaire;
	

	@Column
	private Double montantTotalVendu;
	
	@ManyToOne
	@JoinColumn(name = "produit_id", nullable = false)
	private Intrant ProduitVendu;

	
	@ManyToOne
	@JoinColumn(name = "ventes_id", nullable = false)
	private Ventes VenteAssocie;

	@ManyToOne
	@JoinColumn(name = "pgca_idprog", nullable = true)
	private ProgrammeAgricol programmeAssocie;

	public Long getId_ventePA() {
		return id_ventePA;
	}


	public void setId_ventePA(Long id_ventePA) {
		this.id_ventePA = id_ventePA;
	}


	public Double getQuantiteVendu() {
		return quantiteVendu;
	}


	public void setQuantiteVendu(Double quantiteVendu) {
		this.quantiteVendu = quantiteVendu;
	}


	public float getPrixUnitaire() {
		return prixUnitaire;
	}


	public void setPrixUnitaire(float prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}


	public Double getMontantTotalVendu() {
		return montantTotalVendu;
	}


	public void setMontantTotalVendu(Double montantTotalVendu) {
		this.montantTotalVendu = montantTotalVendu;
	}


	public Intrant getProduitVendu() {
		return ProduitVendu;
	}


	public void setProduitVendu(Intrant produitVendu) {
		ProduitVendu = produitVendu;
	}


	public Ventes getVenteAssocie() {
		return VenteAssocie;
	}


	public void setVenteAssocie(Ventes venteAssocie) {
		VenteAssocie = venteAssocie;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}


	public ProgrammeAgricol getProgrammeAssocie() {
		return programmeAssocie;
	}


	public void setProgrammeAssocie(ProgrammeAgricol programmeAssocie) {
		this.programmeAssocie = programmeAssocie;
	}



	
}
