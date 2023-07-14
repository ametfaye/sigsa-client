package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class IntrantDTO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7838560630950854563L;

	private Long	idProduit;
	private Long	idProg;
	private long    idtypeProduit;
	private Double 	quantite;
	private Double 	quantiteInitaile;
	private Long	idFournisseur;
	private String 	libelleFournisseur;
	private String lieuStockageActuelduProduit;
	private String lieuStockageActuelProduitGerant;
	private String lieuStockageActuelProduitGerantTel;
	private String lieuStockageActuelProduitGerantAdresse;
	private String   dateMiseEnplace;
	private String   dateMiseEnPlaceString;
	private Float PrixAcquisition ;
	private boolean dejaTarifie; // tarifié ou pas 
	
	private String   departementPointdeVente;
	private String   codePointDeVente;

	
	// gestion des stock residuel
	private int   nombredePVDispo; // nombre de pv ou il ya le produit
	private Long	idCommune;
	private String   libelleCommune;
	private Long  idStockResiduel;


	// SourceType 1 = Stock resi From MEP , 2 =  Stock from collecte locale
	private int sourceType ;
	private Long idStockPointdeVente;


	private String tagsProduit;
	private String codeProduit;
	private String stockref;
	private String typeProduit;
	private String libelleTypeProduit;
	private String libelleProgramme;
	private String libelleCampagne;
	private String libellePointdeStock;
	private String libelleProduit; 

	private String pointdeVente;
	private String pointdeCollecte;
	private Long idpointdeVente;
	private Double valeurAcquisition;

	private String infosTarifs;
	private String infosTarifsClass;
	private String infosTarifsClassColor;
	
	// pendant le transfert de stock de founissseur vers magasin
	String transportTotransfert;
	String camionTotransfer;
	String chauufeurTotransfer;
	String lvManuel;
	String blManuel;
	private Double 	quantiteAtransferer;

	
	private boolean updateOrNot ; // pendant la création dire si le produit est créé à zero ou bien update existant
	private String infosDTOLibelleFournisseur;
	public boolean isUpdateOrNot() {
		return updateOrNot;
	}

	public void setUpdateOrNot(boolean updateOrNot) {
		this.updateOrNot = updateOrNot;
	}

	public Long getIdFournisseur() {
		return idFournisseur;
	}

	public void setIdFournisseur(Long idFournisseur) {
		this.idFournisseur = idFournisseur;
	}

	private String pictoImages = "default.jpg";
	private float prixProducteur;
	private float prixNonSubventionne;
	private String provenance;
	private Long idCategorieIntrant;

	public Long getIdCategorieIntrant() {
		return idCategorieIntrant;
	}

	public Double getQuantiteInitaile() {
		return quantiteInitaile;
	}

	public void setQuantiteInitaile(Double quantiteInitaile) {
		this.quantiteInitaile = quantiteInitaile;
	}

	public void setIdCategorieIntrant(Long idCategorieIntrant) {
		this.idCategorieIntrant = idCategorieIntrant;
	}

	/** Collecte  : achat produit **/
	private Long idStock;
	private Long idCampagneProgramme;
	private Long idType;


	public String getLvManuel() {
		return lvManuel;
	}

	public String getBlManuel() {
		return blManuel;
	}

	public void setLvManuel(String lvManuel) {
		this.lvManuel = lvManuel;
	}

	public void setBlManuel(String blManuel) {
		this.blManuel = blManuel;
	}

	private String vendeur;  
	public String getDateMiseEnPlaceString() {
		return dateMiseEnPlaceString;
	}

	public void setDateMiseEnPlaceString(String dateMiseEnPlaceString) {
		this.dateMiseEnPlaceString = dateMiseEnPlaceString;
	}

	public String getInfosTarifs() {
		return infosTarifs;
	}

	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}

	public void setInfosTarifs(String infosTarifs) {
		this.infosTarifs = infosTarifs;
	}

	private String vendeurTel;
	private String vendeurAdress;
	public String getInfosTarifsClassColor() {
		return infosTarifsClassColor;
	}

	public void setInfosTarifsClassColor(String infosTarifsClassColor) {
		this.infosTarifsClassColor = infosTarifsClassColor;
	}

	private String vendeurDepartement;
	public String getInfosDTOLibelleFournisseur() {
		return infosDTOLibelleFournisseur;
	}

	public void setInfosDTOLibelleFournisseur(String infosDTOLibelleFournisseur) {
		this.infosDTOLibelleFournisseur = infosDTOLibelleFournisseur;
	}

	private String vendeurCommune;	
	public String getInfosTarifsClass() {
		return infosTarifsClass;
	}

	public void setInfosTarifsClass(String infosTarifsClass) {
		this.infosTarifsClass = infosTarifsClass;
	}

	private String vendeurIntrantVendu;	
	private Long vendeurDepartementId;   
	private Long vendeurCommuneId;
	private int prixUnitaire;
	public Double getValeurAcquisition() {
		return valeurAcquisition;
	}

	public void setValeurAcquisition(Double valeurAcquisition) {
		this.valeurAcquisition = valeurAcquisition;
	}

	private Long idPoindCollecte;
	private String uniteDeMesure;
	private String descriptifIntrant;
	private float totauxGlobalVentesToutlesPV;
	
	private float tauxSubvention;
	private String tauxSubventionLibelle;
	private String subventionne;
	
	private boolean intranSubventione;
	public String getLibelleFournisseur() {
		return libelleFournisseur;
	}

	public void setLibelleFournisseur(String libelleFournisseur) {
		this.libelleFournisseur = libelleFournisseur;
	}

	public String getDepartementPointdeVente() {
		return departementPointdeVente;
	}

	public String getCodePointDeVente() {
		return codePointDeVente;
	}

	public void setDepartementPointdeVente(String departementPointdeVente) {
		this.departementPointdeVente = departementPointdeVente;
	}

	public void setCodePointDeVente(String codePointDeVente) {
		this.codePointDeVente = codePointDeVente;
	}

	private Long idAuteurTarication;
	
	public Double getQuantite() {
		return quantite;
	}

	public void setQuantite(Double quantite) {
		this.quantite = quantite;
	}

	public String getLibelleProduit() {
		return libelleProduit;
	}

	public void setLibelleProduit(String libelleProduit) {
		this.libelleProduit = libelleProduit;
	}

	public String getTagsProduit() {
		return tagsProduit;
	}

	public void setTagsProduit(String tagsProduit) {
		this.tagsProduit = tagsProduit;
	}

	public String getCodeProduit() {
		return codeProduit;
	}

	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}

	public String getStockref() {
		return stockref;
	}

	public void setStockref(String stockref) {
		this.stockref = stockref;
	}

	public String getTypeProduit() {
		return typeProduit;
	}

	public void setTypeProduit(String typeProduit) {
		this.typeProduit = typeProduit;
	}

	public String getPointdeVente() {
		return pointdeVente;
	}

	public void setPointdeVente(String pointdeVente) {
		this.pointdeVente = pointdeVente;
	}

	public Long getIdpointdeVente() {
		return idpointdeVente;
	}

	public long getIdtypeProduit() {
		return idtypeProduit;
	}

	public String getPointdeCollecte() {
		return pointdeCollecte;
	}

	public void setPointdeCollecte(String pointdeCollecte) {
		this.pointdeCollecte = pointdeCollecte;
	}

	public void setIdtypeProduit(long idtypeProduit) {
		this.idtypeProduit = idtypeProduit;
	}

	public Long getIdStock() {
		return idStock;
	}

	public void setIdStock(Long idStock) {
		this.idStock = idStock;
	}

	public String getPictoImages() {
		return pictoImages;
	}

	public void setPictoImages(String pictoImages) {
		this.pictoImages = pictoImages;
	}

	public void setIdpointdeVente(Long idpointdeVente) {
		this.idpointdeVente = idpointdeVente;
	}

	public String getProvenance() {
		return provenance;
	}

	public String getDateMiseEnplace() {
		return dateMiseEnplace;
	}

	public void setDateMiseEnplace(String dateMiseEnplace) {
		this.dateMiseEnplace = dateMiseEnplace;
	}
	
	public void setProvenance(String provenance) {
		this.provenance = provenance;
	}

	public Long getIdCampagneProgramme() {
		return idCampagneProgramme;
	}

	public void setIdCampagneProgramme(Long idCampagneProgramme) {
		this.idCampagneProgramme = idCampagneProgramme;
	}

	public Long getIdType() {
		return idType;
	}

	public void setIdType(Long idType) {
		this.idType = idType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLibelleProgramme() {
		return libelleProgramme;
	}

	public void setLibelleProgramme(String libelleProgramme) {
		this.libelleProgramme = libelleProgramme;
	}

	public String getLibelleTypeProduit() {
		return libelleTypeProduit;
	}

	public void setLibelleTypeProduit(String libelleTypeProduit) {
		this.libelleTypeProduit = libelleTypeProduit;
	}

	public String getLibellePointdeStock() {
		return libellePointdeStock;
	}

	public void setLibellePointdeStock(String libellePointdeStock) {
		this.libellePointdeStock = libellePointdeStock;
	}

	public Float getPrixAcquisition() {
		return PrixAcquisition;
	}

	public void setPrixAcquisition(Float prixAcquisition) {
		PrixAcquisition = prixAcquisition;
	}

	public Long getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(Long idProduit) {
		this.idProduit = idProduit;
	}

	public String getLibelleCampagne() {
		return libelleCampagne;
	}

	public void setLibelleCampagne(String libelleCampagne) {
		this.libelleCampagne = libelleCampagne;
	}

	public float getTotauxGlobalVentesToutlesPV() {
		return totauxGlobalVentesToutlesPV;
	}

	public void setTotauxGlobalVentesToutlesPV(float totauxGlobalVentesToutlesPV) {
		this.totauxGlobalVentesToutlesPV = totauxGlobalVentesToutlesPV;
	}

	public String getVendeur() {
		return vendeur;
	}

	public void setVendeur(String vendeur) {
		this.vendeur = vendeur;
	}

	public String getVendeurTel() {
		return vendeurTel;
	}

	public void setVendeurTel(String vendeurTel) {
		this.vendeurTel = vendeurTel;
	}

	public String getVendeurAdress() {
		return vendeurAdress;
	}

	public void setVendeurAdress(String vendeurAdress) {
		this.vendeurAdress = vendeurAdress;
	}

	public String getVendeurDepartement() {
		return vendeurDepartement;
	}

	public void setVendeurDepartement(String vendeurDepartement) {
		this.vendeurDepartement = vendeurDepartement;
	}

	public String getVendeurCommune() {
		return vendeurCommune;
	}

	public void setVendeurCommune(String vendeurCommune) {
		this.vendeurCommune = vendeurCommune;
	}

	public Long getVendeurDepartementId() {
		return vendeurDepartementId;
	}

	public void setVendeurDepartementId(Long vendeurDepartementId) {
		this.vendeurDepartementId = vendeurDepartementId;
	}

	public Long getVendeurCommuneId() {
		return vendeurCommuneId;
	}

	public void setVendeurCommuneId(Long vendeurCommuneId) {
		this.vendeurCommuneId = vendeurCommuneId;
	}

	public int getPrixUnitaire() {
		return prixUnitaire;
	}

	public void setPrixUnitaire(int prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	public Long getIdPoindCollecte() {
		return idPoindCollecte;
	}

	public void setIdPoindCollecte(Long idPoindCollecte) {
		this.idPoindCollecte = idPoindCollecte;
	}

	public float getTauxSubvention() {
		return tauxSubvention;
	}

	public void setTauxSubvention(float tauxSubvention) {
		this.tauxSubvention = tauxSubvention;
	}

	public boolean isIntranSubventione() {
		return intranSubventione;
	}

	public void setIntranSubventione(boolean intranSubventione) {
		this.intranSubventione = intranSubventione;
	}

	public Long getIdAuteurTarication() {
		return idAuteurTarication;
	}

	public void setIdAuteurTarication(Long idAuteurTarication) {
		this.idAuteurTarication = idAuteurTarication;
	}

	public String getVendeurIntrantVendu() {
		return vendeurIntrantVendu;
	}

	public void setVendeurIntrantVendu(String vendeurIntrantVendu) {
		this.vendeurIntrantVendu = vendeurIntrantVendu;
	}

	public String getUniteDeMesure() {
		return uniteDeMesure;
	}

	public void setUniteDeMesure(String uniteDeMesure) {
		this.uniteDeMesure = uniteDeMesure;
	}

	public String getDescriptifIntrant() {
		return descriptifIntrant;
	}

	public void setDescriptifIntrant(String descriptifIntrant) {
		this.descriptifIntrant = descriptifIntrant;
	}

	public float getPrixProducteur() {
		return prixProducteur;
	}

	public void setPrixProducteur(float prixProducteur) {
		this.prixProducteur = prixProducteur;
	}

	public String getTauxSubventionLibelle() {
		return tauxSubventionLibelle;
	}

	public void setTauxSubventionLibelle(String tauxSubventionLibelle) {
		this.tauxSubventionLibelle = tauxSubventionLibelle;
	}

	public String getLieuStockageActuelduProduit() {
		return lieuStockageActuelduProduit;
	}

	public void setLieuStockageActuelduProduit(String lieuStockageActuelduProduit) {
		this.lieuStockageActuelduProduit = lieuStockageActuelduProduit;
	}

	public String getLieuStockageActuelProduitGerant() {
		return lieuStockageActuelProduitGerant;
	}

	public void setLieuStockageActuelProduitGerant(String lieuStockageActuelProduitGerant) {
		this.lieuStockageActuelProduitGerant = lieuStockageActuelProduitGerant;
	}

	public String getLieuStockageActuelProduitGerantTel() {
		return lieuStockageActuelProduitGerantTel;
	}

	public void setLieuStockageActuelProduitGerantTel(String lieuStockageActuelProduitGerantTel) {
		this.lieuStockageActuelProduitGerantTel = lieuStockageActuelProduitGerantTel;
	}

	public String getLieuStockageActuelProduitGerantAdresse() {
		return lieuStockageActuelProduitGerantAdresse;
	}

	public void setLieuStockageActuelProduitGerantAdresse(String lieuStockageActuelProduitGerantAdresse) {
		this.lieuStockageActuelProduitGerantAdresse = lieuStockageActuelProduitGerantAdresse;
	}

	public String getTransportTotransfert() {
		return transportTotransfert;
	}

	public void setTransportTotransfert(String transportTotransfert) {
		this.transportTotransfert = transportTotransfert;
	}

	public String getCamionTotransfer() {
		return camionTotransfer;
	}

	public void setCamionTotransfer(String camionTotransfer) {
		this.camionTotransfer = camionTotransfer;
	}

	public String getChauufeurTotransfer() {
		return chauufeurTotransfer;
	}

	public void setChauufeurTotransfer(String chauufeurTotransfer) {
		this.chauufeurTotransfer = chauufeurTotransfer;
	}

	public float getPrixNonSubventionne() {
		return prixNonSubventionne;
	}

	public void setPrixNonSubventionne(float prixNonSubventionne) {
		this.prixNonSubventionne = prixNonSubventionne;
	}

	public String getSubventionne() {
		return subventionne;
	}

	public void setSubventionne(String subventionne) {
		this.subventionne = subventionne;
	}

	public boolean isDejaTarifie() {
		return dejaTarifie;
	}

	public void setDejaTarifie(boolean dejaTarifie) {
		this.dejaTarifie = dejaTarifie;
	}

	public Double getQuantiteAtransferer() {
		return quantiteAtransferer;
	}

	public void setQuantiteAtransferer(Double quantiteAtransferer) {
		this.quantiteAtransferer = quantiteAtransferer;
	}

	public int getNombredePVDispo() {
		return nombredePVDispo;
	}

	public Long getIdCommune() {
		return idCommune;
	}

	public void setIdCommune(Long idCommune) {
		this.idCommune = idCommune;
	}

	public String getLibelleCommune() {
		return libelleCommune;
	}

	public Long getIdStockPointdeVente() {
		return idStockPointdeVente;
	}

	public void setIdStockPointdeVente(Long idStockPointdeVente) {
		this.idStockPointdeVente = idStockPointdeVente;
	}

	public void setLibelleCommune(String libelleCommune) {
		this.libelleCommune = libelleCommune;
	}

	public Long getIdProg() {
		return idProg;
	}

	public void setIdProg(Long idProg) {
		this.idProg = idProg;
	}

	public void setNombredePVDispo(int nombredePVDispo) {
		this.nombredePVDispo = nombredePVDispo;
	}

	public Long getIdStockResiduel() {
		return idStockResiduel;
	}

	public void setIdStockResiduel(Long idStockResiduel) {
		this.idStockResiduel = idStockResiduel;
	}


	
	


}
