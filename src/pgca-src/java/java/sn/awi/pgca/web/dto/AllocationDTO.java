package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class AllocationDTO implements Serializable {
	

	private static final long serialVersionUID = 4740612708609554436L;

	private String reference;

	private Long alloc_id;
	private Long idProgramme;
	private String programme;
	private String campagneAgricole;
	private Date   dateAllcation;
	private String collaborateurBeneficiare;
	private String pointdeCollecteBeneficiare;
	private Long idPointDecollecteBeneficiaire;
	private String regionPointDecollecteBeneficiaire;
	private String departementPointDecollecteBeneficiaire;
	private String communePointDecollecteBeneficiaire;

	private String intranACollecter;
	private Long  idIntrantACollecter;
	private Long idColloborateurBeneficiaire;
	private Long idProduitACollecter;
	private Long idCategorieProduitACollecter;
	private String auteurAllocation;  // manager 
	private float montantalloue;
	private float montantUtilise;
	
	// Producteur (Agent de collecte :  => Generate reçu d'achat)
	private String producteurNom;
	private String producteurPrenom;
	private String producteurCNI;
	private String producteurVillage;
	private int producteurPoidsTotal;
	private String producteurValeurCollecte;
	private Long producteurPrixUnitaire;
	private String   producteurDateDepot;
	private Long totalCollecte;
	

	

	public Long getAlloc_id() {
		return alloc_id;
	}

	public void setAlloc_id(Long alloc_id) {
		this.alloc_id = alloc_id;
	}

	public Long getIdPointDecollecteBeneficiaire() {
		return idPointDecollecteBeneficiaire;
	}

	public void setIdPointDecollecteBeneficiaire(Long idPointDecollecteBeneficiaire) {
		this.idPointDecollecteBeneficiaire = idPointDecollecteBeneficiaire;
	}

	public Long getIdColloborateurBeneficiaire() {
		return idColloborateurBeneficiaire;
	}

	public void setIdColloborateurBeneficiaire(Long idColloborateurBeneficiaire) {
		this.idColloborateurBeneficiaire = idColloborateurBeneficiaire;
	}

	public Long getIdProduitACollecter() {
		return idProduitACollecter;
	}

	public void setIdProduitACollecter(Long idProduitACollecter) {
		this.idProduitACollecter = idProduitACollecter;
	}

	public String getAuteurAllocation() {
		return auteurAllocation;
	}

	public void setAuteurAllocation(String auteurAllocation) {
		this.auteurAllocation = auteurAllocation;
	}

	public float getMontantalloue() {
		return montantalloue;
	}

	public void setMontantalloue(float montantalloue) {
		this.montantalloue = montantalloue;
	}

	public float getMontantUtilise() {
		return montantUtilise;
	}

	public void setMontantUtilise(float montantUtilise) {
		this.montantUtilise = montantUtilise;
	}

	public Date getDateAllcation() {
		return dateAllcation;
	}

	public void setDateAllcation(Date dateAllcation) {
		this.dateAllcation = dateAllcation;
	}

	public String getCollaborateurBeneficiare() {
		return collaborateurBeneficiare;
	}

	public void setCollaborateurBeneficiare(String collaborateurBeneficiare) {
		this.collaborateurBeneficiare = collaborateurBeneficiare;
	}

	public String getPointdeCollecteBeneficiare() {
		return pointdeCollecteBeneficiare;
	}

	public void setPointdeCollecteBeneficiare(String pointdeCollecteBeneficiare) {
		this.pointdeCollecteBeneficiare = pointdeCollecteBeneficiare;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getIdProgramme() {
		return idProgramme;
	}

	public void setIdProgramme(Long idProgramme) {
		this.idProgramme = idProgramme;
	}

	public Long getIdCategorieProduitACollecter() {
		return idCategorieProduitACollecter;
	}

	public void setIdCategorieProduitACollecter(Long idCategorieProduitACollecter) {
		this.idCategorieProduitACollecter = idCategorieProduitACollecter;
	}

	public String getIntranACollecter() {
		return intranACollecter;
	}

	public void setIntranACollecter(String intranACollecter) {
		this.intranACollecter = intranACollecter;
	}

	public Long getIdIntrantACollecter() {
		return idIntrantACollecter;
	}

	public void setIdIntrantACollecter(Long idIntrantACollecter) {
		this.idIntrantACollecter = idIntrantACollecter;
	}

	public String getProgramme() {
		return programme;
	}

	public void setProgramme(String programme) {
		this.programme = programme;
	}

	public String getCampagneAgricole() {
		return campagneAgricole;
	}

	public void setCampagneAgricole(String campagneAgricole) {
		this.campagneAgricole = campagneAgricole;
	}

	public String getProducteurNom() {
		return producteurNom;
	}

	public void setProducteurNom(String producteurNom) {
		this.producteurNom = producteurNom;
	}

	public String getProducteurPrenom() {
		return producteurPrenom;
	}

	public void setProducteurPrenom(String producteurPrenom) {
		this.producteurPrenom = producteurPrenom;
	}

	public String getProducteurCNI() {
		return producteurCNI;
	}

	public void setProducteurCNI(String producteurCNI) {
		this.producteurCNI = producteurCNI;
	}

	public String getProducteurVillage() {
		return producteurVillage;
	}

	public void setProducteurVillage(String producteurVillage) {
		this.producteurVillage = producteurVillage;
	}

	public String getProducteurValeurCollecte() {
		return producteurValeurCollecte;
	}

	public void setProducteurValeurCollecte(String producteurValeurCollecte) {
		this.producteurValeurCollecte = producteurValeurCollecte;
	}

	public String getProducteurDateDepot() {
		return producteurDateDepot;
	}

	public void setProducteurDateDepot(String producteurDateDepot) {
		this.producteurDateDepot = producteurDateDepot;
	}


	public Long getProducteurPrixUnitaire() {
		return producteurPrixUnitaire;
	}

	public void setProducteurPrixUnitaire(Long producteurPrixUnitaire) {
		this.producteurPrixUnitaire = producteurPrixUnitaire;
	}

	public int getProducteurPoidsTotal() {
		return producteurPoidsTotal;
	}

	public void setProducteurPoidsTotal(int producteurPoidsTotal) {
		this.producteurPoidsTotal = producteurPoidsTotal;
	}

	public Long getTotalCollecte() {
		return totalCollecte;
	}

	public void setTotalCollecte(Long totalCollecte) {
		this.totalCollecte = totalCollecte;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}


	public String getCommunePointDecollecteBeneficiaire() {
		return communePointDecollecteBeneficiaire;
	}

	public void setCommunePointDecollecteBeneficiaire(String communePointDecollecteBeneficiaire) {
		this.communePointDecollecteBeneficiaire = communePointDecollecteBeneficiaire;
	}

	public String getRegionPointDecollecteBeneficiaire() {
		return regionPointDecollecteBeneficiaire;
	}

	public void setRegionPointDecollecteBeneficiaire(String regionPointDecollecteBeneficiaire) {
		this.regionPointDecollecteBeneficiaire = regionPointDecollecteBeneficiaire;
	}

	public String getDepartementPointDecollecteBeneficiaire() {
		return departementPointDecollecteBeneficiaire;
	}

	public void setDepartementPointDecollecteBeneficiaire(String departementPointDecollecteBeneficiaire) {
		this.departementPointDecollecteBeneficiaire = departementPointDecollecteBeneficiaire;
	}

}
