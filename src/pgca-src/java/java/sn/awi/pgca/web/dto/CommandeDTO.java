package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommandeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5302990311959163736L;

	// Reference 
	private String referenceCMD;
	private String statutCMD;
	private String libelleCampagane;  
	private String libelleProgramme;
	private String departementCMD;
	private Long idProgramme;
	private Long idCommande;
	private Long editeurCommandeIdPointDeCollecte;
	private Long idStockSortant;

	
	// others
	private String styleClassCSSstatusCMD;
	private String iconFontAwesome;
	private String statusLibelle;
	private boolean afficherBlocCredit;
	private float montantCreditContracte;
	
	// Toc Produit
	private String libelleProduitToc ;
	private Long quantitePaiementNAture;
	private int status;
	
	
	
	/*** Point de stock qui traite la commande */
	private String pointDeVenteLibelleProvenance;
	private String pointDeVenteGerantProvenance;
	private String pointDeVentCommuneProvenance;
	private String pointDeVenteDepartementProvenance;
	private String pointDeVenteTelProvenance;
	private Long  idPointdeVente;

	/*** Client qui recoit la cmd */
	private String clientTel;
	private String clientNom;  
	private String clienAdresse;
	private String dateLivraisonSouhaite;  
	private String communeLibelle; 
	private String communeid;
	private String departementLibelle;
	private String departementid;
	private String regionLibelle;
	private String regionid;
	
	// point de stock qui envoi la cmd : en général c'est le siege
	private String auteurCommandeNom;
	private Long   auteurCommandeidPersonne;
	private Long   auteurCommandePointdeVenteid; 
	private String auteurCommandePointdeVenteZone;
	
	// Zone qui envoi la cmd : Dakar -> Kolda
	private String auteurCommandeProvenance; 
	private String auteurCommandeProvenanceTel;
	
	private String   clientCNI; 
	//OL
	private Long idOrdre;
	// Paiement 
	private float montantPayeInitialement;
	private float montantTotaldelaCommande;
	private float montantPayeParCheque = 0;
	private float montantPayeParBonSubvention = 0;
	private float montantPayeParEspeces = 0;
	private float montantPayeParNature = 0;
	boolean payerLeResteParCredit;
	private CreditDTO creditLieeAlaCMD;




	
	// Produit de la commande
	private ProduitDTO ProduitAjouter = new ProduitDTO();
	private float quantiteProduitAvendre; 
	private Long  idProduitAvendre;
	private List<ProduitDTO> listProduitsDTOtoCreate = new ArrayList<ProduitDTO>();
	
	//PAiement de la commande
	private String   modeDePaiement;  // 0 =  pendant la livraison , 1 : au siége, 2 : A credit contracte par le stock sortant  

	
	
	public CommandeDTO() {
		//modeDePaiement  = "0";
	}
	public String getCommuneLibelle() {
		return communeLibelle;
	}
	public void setCommuneLibelle(String communeLibelle) {
		this.communeLibelle = communeLibelle;
	}
	public String getCommuneid() {
		return communeid;
	}
	public void setCommuneid(String communeid) {
		this.communeid = communeid;
	}
	public String getDepartementLibelle() {
		return departementLibelle;
	}
	public void setDepartementLibelle(String departementLibelle) {
		this.departementLibelle = departementLibelle;
	}
	public String getDepartementid() {
		return departementid;
	}
	public void setDepartementid(String departementid) {
		this.departementid = departementid;
	}
	public String getRegionLibelle() {
		return regionLibelle;
	}
	public void setRegionLibelle(String regionLibelle) {
		this.regionLibelle = regionLibelle;
	}
	public String getRegionid() {
		return regionid;
	}
	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDateLivraisonSouhaite() {
		return dateLivraisonSouhaite;
	}
	public void setDateLivraisonSouhaite(String dateLivraisonSouhaite) {
		this.dateLivraisonSouhaite = dateLivraisonSouhaite;
	}
	public String getClientTel() {
		return clientTel;
	}
	public void setClientTel(String clientTel) {
		this.clientTel = clientTel;
	}
	public String getClientNom() {
		return clientNom;
	}
	public void setClientNom(String clientNom) {
		this.clientNom = clientNom;
	}
	public List<ProduitDTO> getListProduitsDTOtoCreate() {
		return listProduitsDTOtoCreate;
	}
	public void setListProduitsDTOtoCreate(List<ProduitDTO> listProduitsDTOtoCreate) {
		this.listProduitsDTOtoCreate = listProduitsDTOtoCreate;
	}

	public ProduitDTO getProduitAjouter() {
		return ProduitAjouter;
	}
	public void setProduitAjouter(ProduitDTO produitAjouter) {
		ProduitAjouter = produitAjouter;
	}
	public float getQuantiteProduitAvendre() {
		return quantiteProduitAvendre;
	}
	public void setQuantiteProduitAvendre(float quantiteProduitAvendre) {
		this.quantiteProduitAvendre = quantiteProduitAvendre;
	}
	public Long getIdProduitAvendre() {
		return idProduitAvendre;
	}
	public void setIdProduitAvendre(Long idProduitAvendre) {
		this.idProduitAvendre = idProduitAvendre;
	}

	public String getClienAdresse() {
		return clienAdresse;
	}
	public void setClienAdresse(String clienAdresse) {
		this.clienAdresse = clienAdresse;
	}
	public Long getIdPointdeVente() {
		return idPointdeVente;
	}
	public void setIdPointdeVente(Long idPointdeVente) {
		this.idPointdeVente = idPointdeVente;
	}
	public String getPointDeVenteLibelleProvenance() {
		return pointDeVenteLibelleProvenance;
	}
	public void setPointDeVenteLibelleProvenance(String pointDeVenteLibelleProvenance) {
		this.pointDeVenteLibelleProvenance = pointDeVenteLibelleProvenance;
	}
	public String getPointDeVenteGerantProvenance() {
		return pointDeVenteGerantProvenance;
	}
	public void setPointDeVenteGerantProvenance(String pointDeVenteGerantProvenance) {
		this.pointDeVenteGerantProvenance = pointDeVenteGerantProvenance;
	}
	public String getPointDeVentCommuneProvenance() {
		return pointDeVentCommuneProvenance;
	}
	public void setPointDeVentCommuneProvenance(String pointDeVentCommuneProvenance) {
		this.pointDeVentCommuneProvenance = pointDeVentCommuneProvenance;
	}
	public String getPointDeVenteDepartementProvenance() {
		return pointDeVenteDepartementProvenance;
	}
	public void setPointDeVenteDepartementProvenance(String pointDeVenteDepartementProvenance) {
		this.pointDeVenteDepartementProvenance = pointDeVenteDepartementProvenance;
	}
	public String getPointDeVenteTelProvenance() {
		return pointDeVenteTelProvenance;
	}
	public void setPointDeVenteTelProvenance(String pointDeVenteTelProvenance) {
		this.pointDeVenteTelProvenance = pointDeVenteTelProvenance;
	}
	public String getAuteurCommandeNom() {
		return auteurCommandeNom;
	}
	public void setAuteurCommandeNom(String auteurCommandeNom) {
		this.auteurCommandeNom = auteurCommandeNom;
	}
	public Long getAuteurCommandeidPersonne() {
		return auteurCommandeidPersonne;
	}
	public void setAuteurCommandeidPersonne(Long auteurCommandeidPersonne) {
		this.auteurCommandeidPersonne = auteurCommandeidPersonne;
	}
	public Long getAuteurCommandePointdeVenteid() {
		return auteurCommandePointdeVenteid;
	}
	public void setAuteurCommandePointdeVenteid(Long auteurCommandePointdeVenteid) {
		this.auteurCommandePointdeVenteid = auteurCommandePointdeVenteid;
	}
	public String getAuteurCommandePointdeVenteZone() {
		return auteurCommandePointdeVenteZone;
	}
	public void setAuteurCommandePointdeVenteZone(String auteurCommandePointdeVenteZone) {
		this.auteurCommandePointdeVenteZone = auteurCommandePointdeVenteZone;
	}
	public float getMontantPayeInitialement() {
		return montantPayeInitialement;
	}
	public void setMontantPayeInitialement(float montantPayeInitialement) {
		this.montantPayeInitialement = montantPayeInitialement;
	}
	public String getReferenceCMD() {
		return referenceCMD;
	}
	public void setReferenceCMD(String referenceCMD) {
		this.referenceCMD = referenceCMD;
	}
	public String getModeDePaiement() {
		return modeDePaiement;
	}
	public void setModeDePaiement(String modeDePaiement) {
		this.modeDePaiement = modeDePaiement;
	}
	public String getLibelleCampagane() {
		return libelleCampagane;
	}
	public void setLibelleCampagane(String libelleCampagane) {
		this.libelleCampagane = libelleCampagane;
	}
	public String getLibelleProgramme() {
		return libelleProgramme;
	}
	public void setLibelleProgramme(String libelleProgramme) {
		this.libelleProgramme = libelleProgramme;
	}
	public Long getIdProgramme() {
		return idProgramme;
	}
	public void setIdProgramme(Long idProgramme) {
		this.idProgramme = idProgramme;
	}
	public float getMontantTotaldelaCommande() {
		return montantTotaldelaCommande;
	}
	public void setMontantTotaldelaCommande(float montantTotaldelaCommande) {
		this.montantTotaldelaCommande = montantTotaldelaCommande;
	}
	public float getMontantPayeParCheque() {
		return montantPayeParCheque;
	}
	public void setMontantPayeParCheque(float montantPayeParCheque) {
		this.montantPayeParCheque = montantPayeParCheque;
	}
	public float getMontantPayeParBonSubvention() {
		return montantPayeParBonSubvention;
	}
	public void setMontantPayeParBonSubvention(float montantPayeParBonSubvention) {
		this.montantPayeParBonSubvention = montantPayeParBonSubvention;
	}
	public float getMontantPayeParEspeces() {
		return montantPayeParEspeces;
	}
	public void setMontantPayeParEspeces(float montantPayeParEspeces) {
		this.montantPayeParEspeces = montantPayeParEspeces;
	}
	public float getMontantPayeParNature() {
		return montantPayeParNature;
	}
	public void setMontantPayeParNature(float montantPayeParNature) {
		this.montantPayeParNature = montantPayeParNature;
	}
	public boolean isPayerLeResteParCredit() {
		return payerLeResteParCredit;
	}
	public void setPayerLeResteParCredit(boolean payerLeResteParCredit) {
		this.payerLeResteParCredit = payerLeResteParCredit;
	}
	public Long getQuantitePaiementNAture() {
		return quantitePaiementNAture;
	}
	public void setQuantitePaiementNAture(Long quantitePaiementNAture) {
		this.quantitePaiementNAture = quantitePaiementNAture;
	}
	public Long getIdCommande() {
		return idCommande;
	}
	public void setIdCommande(Long idCommande) {
		this.idCommande = idCommande;
	}
	public String getLibelleProduitToc() {
		return libelleProduitToc;
	}
	public void setLibelleProduitToc(String libelleProduitToc) {
		this.libelleProduitToc = libelleProduitToc;
	}
	public Long getEditeurCommandeIdPointDeCollecte() {
		return editeurCommandeIdPointDeCollecte;
	}
	public void setEditeurCommandeIdPointDeCollecte(Long editeurCommandeIdPointDeCollecte) {
		this.editeurCommandeIdPointDeCollecte = editeurCommandeIdPointDeCollecte;
	}
	public String getStatutCMD() {
		return statutCMD;
	}
	public void setStatutCMD(String statutCMD) {
		this.statutCMD = statutCMD;
	}
	public String getStyleClassCSSstatusCMD() {
		return styleClassCSSstatusCMD;
	}
	public void setStyleClassCSSstatusCMD(String styleClassCSSstatusCMD) {
		this.styleClassCSSstatusCMD = styleClassCSSstatusCMD;
	}
	public String getStatusLibelle() {
		return statusLibelle;
	}
	public void setStatusLibelle(String statusLibelle) {
		this.statusLibelle = statusLibelle;
	}
	public boolean isAfficherBlocCredit() {
		return afficherBlocCredit;
	}
	public void setAfficherBlocCredit(boolean afficherBlocCredit) {
		this.afficherBlocCredit = afficherBlocCredit;
	}
	public float getMontantCreditContracte() {
		return montantCreditContracte;
	}
	public void setMontantCreditContracte(float montantCreditContracte) {
		this.montantCreditContracte = montantCreditContracte;
	}
	public CreditDTO getCreditLieeAlaCMD() {
		return creditLieeAlaCMD;
	}
	public void setCreditLieeAlaCMD(CreditDTO creditLieeAlaCMD) {
		this.creditLieeAlaCMD = creditLieeAlaCMD;
	}
	public String getIconFontAwesome() {
		return iconFontAwesome;
	}
	public void setIconFontAwesome(String iconFontAwesome) {
		this.iconFontAwesome = iconFontAwesome;
	}
	public Long getIdStockSortant() {
		return idStockSortant;
	}
	public void setIdStockSortant(Long idStockSortant) {
		this.idStockSortant = idStockSortant;
	}
	public String getClientCNI() {
		return clientCNI;
	}
	public void setClientCNI(String clientCNI) {
		this.clientCNI = clientCNI;
	}
	public Long getIdOrdre() {
		return idOrdre;
	}
	public void setIdOrdre(Long idOrdre) {
		this.idOrdre = idOrdre;
	}
	public String getAuteurCommandeProvenance() {
		return auteurCommandeProvenance;
	}
	public void setAuteurCommandeProvenance(String auteurCommandeProvenance) {
		this.auteurCommandeProvenance = auteurCommandeProvenance;
	}
	public String getAuteurCommandeProvenanceTel() {
		return auteurCommandeProvenanceTel;
	}
	public void setAuteurCommandeProvenanceTel(String auteurCommandeProvenanceTel) {
		this.auteurCommandeProvenanceTel = auteurCommandeProvenanceTel;
	}
	public String getDepartementCMD() {
		return departementCMD;
	}
	public void setDepartementCMD(String departementCMD) {
		this.departementCMD = departementCMD;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
