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
@Table(name="pgca_commande")
public class Commande implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="cmd_id")
	private Long cmd_id;
	
	@Column
	private String referenceCMD;
	
	@Column
	private String dateCMD;
	
	@Column
	private String dateLivraisonSouhaiteCMD;
	
	@Column
	private int statutCMD;
	
	@Column
	private float montantTotalCMD;
	
	@Column
	private float montantEncaisseCMD;
	
	@Column
	private String statutCMDLibelle;
	
	@Column
	private String paiementCMD;

	
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
	

	@Override
	public Long getId() {
		return cmd_id;
	}

	public Long getCmd_id() {
		return cmd_id;
	}

	public void setCmd_id(Long cmd_id) {
		this.cmd_id = cmd_id;
	}

	public String getDateCMD() {
		return dateCMD;
	}

	public void setDateCMD(String dateCMD) {
		this.dateCMD = dateCMD;
	}

	public int getStatutCMD() {
		return statutCMD;
	}

	public void setStatutCMD(int statutCMD) {
		this.statutCMD = statutCMD;
	}

	public String getStatutCMDLibelle() {
		return statutCMDLibelle;
	}

	public void setStatutCMDLibelle(String statutCMDLibelle) {
		this.statutCMDLibelle = statutCMDLibelle;
	}

	public String getPaiementCMD() {
		return paiementCMD;
	}

	public void setPaiementCMD(String paiementCMD) {
		this.paiementCMD = paiementCMD;
	}



	public PointDeVente getPointdeVenteTraitantLaCMD() {
		return pointdeVenteTraitantLaCMD;
	}

	public void setPointdeVenteTraitantLaCMD(PointDeVente pointdeVenteTraitantLaCMD) {
		this.pointdeVenteTraitantLaCMD = pointdeVenteTraitantLaCMD;
	}


	public float getMontantTotalCMD() {
		return montantTotalCMD;
	}

	public void setMontantTotalCMD(float montantTotalCMD) {
		this.montantTotalCMD = montantTotalCMD;
	}

	public float getMontantEncaisseCMD() {
		return montantEncaisseCMD;
	}

	public void setMontantEncaisseCMD(float montantEncaisseCMD) {
		this.montantEncaisseCMD = montantEncaisseCMD;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDateLivraisonSouhaiteCMD() {
		return dateLivraisonSouhaiteCMD;
	}

	public void setDateLivraisonSouhaiteCMD(String dateLivraisonSouhaiteCMD) {
		this.dateLivraisonSouhaiteCMD = dateLivraisonSouhaiteCMD;
	}

	public String getReferenceCMD() {
		return referenceCMD;
	}

	public void setReferenceCMD(String referenceCMD) {
		this.referenceCMD = referenceCMD;
	}


	public ProgrammeAgricol getProgrammeConcerne() {
		return programmeConcerne;
	}

	public void setProgrammeConcerne(ProgrammeAgricol programmeConcerne) {
		this.programmeConcerne = programmeConcerne;
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
	
	

}
