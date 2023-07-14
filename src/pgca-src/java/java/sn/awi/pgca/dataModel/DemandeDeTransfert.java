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
@Table(name="pgca_DemandesDeTransfert")
public class DemandeDeTransfert implements Serializable, GenericModel, Cloneable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8147363805505994466L;

	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idTransfert;
	
	/*ref = 'TR' + idTransfert   EX: TR001 */
	@Column
	private String referenceTransfert;
	
	/* 1 -> magasin ; 2 -> Fournisseur ; Point de vente */
	@Column (nullable = false)
	private int typeOrigine;
	
	/* 1 = Saise --- 2 = Accepté --- 3 = Refusé  --- 4=confimé reçu  */
	@Column (nullable = false)
	private int statusTransport;
	
	@Column
	private String dateSaisie; 
	
	@Column
	private String dateTransfertSouhaite; 
	
	// la personne qui a saisie la demande de transpert
	@Column (nullable = false)
	private Utilisateur aueurSaisie;
	

	/* 1 -> magasin ; 2 -> Fournisseur ; Point de vente */
	@Column (nullable = false)
	private Long idBl;
	

	// Source (peut être un  magasin, un  founisseur ou la commune d'un point de vente)
	@OneToOne
	@JoinColumn(name = "ptv_id" , insertable = false , updatable=false)
	private PointDeVente  origineMagasin;
	@OneToOne
	@JoinColumn(name = "four_id" , insertable = false , updatable=false)
	private Fournisseur  origineFournisseur;
	@OneToOne
	@JoinColumn(name = "commune_id" , insertable = false , updatable=false)
	private Commune  originePointdeVente;
	

	// destination (peut être un  magasin, un  founisseur ou la commune d'un point de vente)
	@OneToOne
	@JoinColumn(name = "ptv_id" , insertable = false , updatable=false)
	private PointDeVente  destinationMagasin;
	@OneToOne
	@JoinColumn(name = "four_id" ,  insertable = false , updatable=false)
	private Fournisseur  destinationFournisseur;
	@OneToOne
	@JoinColumn(name = "commune_id" , insertable = false , updatable=false)
	private Commune  destinationPointdeVente;
	
	@Column (nullable = false, length = 500)
	private String  descriptifDemandeTransfert;
	
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return idTransfert;
	}
	

}
