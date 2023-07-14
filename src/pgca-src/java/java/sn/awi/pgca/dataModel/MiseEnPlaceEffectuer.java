package sn.awi.pgca.dataModel;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.xpath.FoundIndex;

@Entity
@Table(name="pgca_MiseEnPlaceEffectuee")
public class MiseEnPlaceEffectuer implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id_MEPeff;

	@OneToOne
	@JoinColumn(name = "pgca_idprog", nullable = false)
	private ProgrammeAgricol programmeConcerne;
	
	@OneToOne
	@JoinColumn(name = "id_MEPaAF", nullable = false)
	private MiseEnPlaceAEffectuer miseEnplaceConcerne;
	
	@OneToOne
	@JoinColumn(name = "idtransporteur", nullable = false)
	private Transporteur transporteur;
	
	@OneToOne
	@JoinColumn(name = "chauff_id", nullable = false)
	private Chauffeur chauffeur;
	
	@OneToOne
	@JoinColumn(name = "camion_id", nullable = false)
	private Camion camion;
	
	
	@OneToOne
	@JoinColumn(name = "four_id", nullable = false)
	private Fournisseur founnisseur;
	
	/* Apres deplacement de stock la provenance des intrant n'est plus le fournisseur mais le magasin de stockage*/
	@Column
	private String magasinSource;
	
	@OneToOne
	@JoinColumn(name = "bl_id", nullable = false)
	private BonDeLivraison bl;
	
	
	@Column
	private String bl_Port;
	
	@Column
	private String blManuel; // documents saisie manuellement sur le terrain
	@Column
	private String lvManuel;
	
	
	
	@Column
	private double quantiteAmettreEnplace ; 
	

	
	
	@Column
	private Date dateMiseEnplaceEffective;
	
	@Column
	private String destinataire;
	
	@Column
	private String pointdeVenteLibelle;
	
	
	

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Long getId_MEPeff() {
		return id_MEPeff;
	}


	public void setId_MEPeff(Long id_MEPeff) {
		this.id_MEPeff = id_MEPeff;
	}


	public ProgrammeAgricol getProgrammeConcerne() {
		return programmeConcerne;
	}


	public void setProgrammeConcerne(ProgrammeAgricol programmeConcerne) {
		this.programmeConcerne = programmeConcerne;
	}


	public MiseEnPlaceAEffectuer getMiseEnplaceConcerne() {
		return miseEnplaceConcerne;
	}


	public void setMiseEnplaceConcerne(MiseEnPlaceAEffectuer miseEnplaceConcerne) {
		this.miseEnplaceConcerne = miseEnplaceConcerne;
	}


	public Transporteur getTransporteur() {
		return transporteur;
	}


	public void setTransporteur(Transporteur transporteur) {
		this.transporteur = transporteur;
	}



	public Chauffeur getChauffeur() {
		return chauffeur;
	}


	public void setChauffeur(Chauffeur chauffeur) {
		this.chauffeur = chauffeur;
	}


	public String getBlManuel() {
		return blManuel;
	}


	public Fournisseur getFounnisseur() {
		return founnisseur;
	}


	public void setFounnisseur(Fournisseur founnisseur) {
		this.founnisseur = founnisseur;
	}


	public void setBlManuel(String blManuel) {
		this.blManuel = blManuel;
	}


	public String getLvManuel() {
		return lvManuel;
	}


	public void setLvManuel(String lvManuel) {
		this.lvManuel = lvManuel;
	}


	public Camion getCamion() {
		return camion;
	}


	public void setCamion(Camion camion) {
		this.camion = camion;
	}


	public BonDeLivraison getBl() {
		return bl;
	}


	public void setBl(BonDeLivraison bl) {
		this.bl = bl;
	}




	public double getQuantiteAmettreEnplace() {
		return quantiteAmettreEnplace;
	}


	public String getMagasinSource() {
		return magasinSource;
	}


	public void setMagasinSource(String magasinSource) {
		this.magasinSource = magasinSource;
	}


	public void setQuantiteAmettreEnplace(double quantiteAmettreEnplace) {
		this.quantiteAmettreEnplace = quantiteAmettreEnplace;
	}


	public Date getDateMiseEnplaceEffective() {
		return dateMiseEnplaceEffective;
	}


	public void setDateMiseEnplaceEffective(Date dateMiseEnplaceEffective) {
		this.dateMiseEnplaceEffective = dateMiseEnplaceEffective;
	}


	public String getDestinataire() {
		return destinataire;
	}


	public void setDestinataire(String destinataire) {
		this.destinataire = destinataire;
	}


	public String getPointdeVenteLibelle() {
		return pointdeVenteLibelle;
	}


	public void setPointdeVenteLibelle(String pointdeVenteLibelle) {
		this.pointdeVenteLibelle = pointdeVenteLibelle;
	}


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id_MEPeff;
	}


	public String getBl_Port() {
		return bl_Port;
	}


	public void setBl_Port(String bl_Port) {
		this.bl_Port = bl_Port;
	}
	
}
