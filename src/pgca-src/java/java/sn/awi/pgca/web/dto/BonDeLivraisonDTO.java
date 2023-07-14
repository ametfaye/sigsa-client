package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.List;

import sn.awi.pgca.utils.UtilString;

public class BonDeLivraisonDTO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 9219008770835538193L;

	private Long							id;
	private	int							status ; // 1 : en cours de transport , 2 :  Recupere , 3 : refuse : 4 accepeter avec reserver
	private 	String						statusLibelle;
	private String 						libelle;
	private int 						poids;
	private String						uniteDemesure;
	private String						pointdeVenteid;
	private Long						chauffeurid;
	private String						chauffeurlibelle; 
	private Long						transporteurid;
	private String						transporteurlibelle;
	private String						provenanceBL;
	private Long						camionid;
	private String						camionnumero;
	private String						camionlibelle;
	private String						referenceBL;
	private String						gerantMail;
	private Long 						programmeId;
	private String						pointdeVentelibelle;
	private String						pointdeVenteRegion;
	private String						pointdeVenteDepartement;
	private String						pointdeVenteCommune;
	private String						gerant;
	private Long						idAuteurBl;
	private String 						adresseDestinataire;
	private String 						dateEdition;
	private String 						dateRecpetion;
	private String 						programmeLibelle;
	private String						campagneLibelle;
	private String 						idprogramme;
	private String						idcampagne;
	private Long						idStockSortant;
	private Long						idStockReceptionnaire;
	private Double 						chargeTotal;
	private	String							auteurBl;
	private	String							contactAuteurBl; 
	private	String							contactChauffeur;
	private	String							zoneDeReception;  // point de vente ou de collecte receptionnaire
	private String 							corpsMessageMail ;  // mail de confirmation
	private boolean 						updatecache; // update ou pas des cache apres ajout ref
	private String							corpsMessageMailBlNum; 
	private String							picto; 
	private Long						idPointdeVenteOrCollecteStock; // Id du point de vente ou de collecte qui a envoyé ce BL

	private Long idPoiintdeVenteReceptionnnaireBL;
	
	public String getGerantMail() {
		return gerantMail;
	}

	public void setGerantMail(String gerantMail) {
		this.gerantMail = gerantMail;
	}

	private List<ProduitDTO> 			listProduitsOfBL;

	
	// tarification FACTURE LIEE AU BL
	private Double						prixtotal;
	
//
//	public boolean isCorrect() {
//		return (UtilString.isCorrect(libelle) || UtilString.isCorrect(mobile) || UtilString.isCorrect(fixe) || UtilString.isCorrect(courriel) || UtilString.isCorrect(fax) || UtilString.isCorrect(site));
//	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public int getPoids() {
		return poids;
	}

	public void setPoids(int poids) {
		this.poids = poids;
	}

	public String getUniteDemesure() {
		return uniteDemesure;
	}

	public void setUniteDemesure(String uniteDemesure) {
		this.uniteDemesure = uniteDemesure;
	}

	public String getPointdeVenteid() {
		return pointdeVenteid;
	}

	public void setPointdeVenteid(String pointdeVenteid) {
		this.pointdeVenteid = pointdeVenteid;
	}

	public Long getChauffeurid() {
		return chauffeurid;
	}

	public void setChauffeurid(Long chauffeurid) {
		this.chauffeurid = chauffeurid;
	}

	public String getChauffeurlibelle() {
		return chauffeurlibelle;
	}

	public void setChauffeurlibelle(String chauffeurlibelle) {
		this.chauffeurlibelle = chauffeurlibelle;
	}

	public Long getCamionid() {
		return camionid;
	}

	public void setCamionid(Long camionid) {
		this.camionid = camionid;
	}

	public String getPointdeVentelibelle() {
		return pointdeVentelibelle;
	}

	public void setPointdeVentelibelle(String pointdeVentelibelle) {
		this.pointdeVentelibelle = pointdeVentelibelle;
	}

	public String getCorpsMessageMail() {
		return corpsMessageMail;
	}

	public void setCorpsMessageMail(String corpsMessageMail) {
		this.corpsMessageMail = corpsMessageMail;
	}

	public String getPointdeVenteRegion() {
		return pointdeVenteRegion;
	}

	public void setPointdeVenteRegion(String pointdeVenteRegion) {
		this.pointdeVenteRegion = pointdeVenteRegion;
	}

	public String getCorpsMessageMailBlNum() {
		return corpsMessageMailBlNum;
	}

	public void setCorpsMessageMailBlNum(String corpsMessageMailBlNum) {
		this.corpsMessageMailBlNum = corpsMessageMailBlNum;
	}

	public boolean isUpdatecache() {
		return updatecache;
	}

	public void setUpdatecache(boolean updatecache) {
		this.updatecache = updatecache;
	}

	public String getPointdeVenteDepartement() {
		return pointdeVenteDepartement;
	}

	public void setPointdeVenteDepartement(String pointdeVenteDepartement) {
		this.pointdeVenteDepartement = pointdeVenteDepartement;
	}

	public String getZoneDeReception() {
		return zoneDeReception;
	}

	public void setZoneDeReception(String zoneDeReception) {
		this.zoneDeReception = zoneDeReception;
	}

	public String getPointdeVenteCommune() {
		return pointdeVenteCommune;
	}

	public void setPointdeVenteCommune(String pointdeVenteCommune) {
		this.pointdeVenteCommune = pointdeVenteCommune;
	}

	public String getGerant() {
		return gerant;
	}

	public void setGerant(String gerant) {
		this.gerant = gerant;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getTransporteurid() {
		return transporteurid;
	}

	public void setTransporteurid(Long transporteurid) {
		this.transporteurid = transporteurid;
	}

	public String getTransporteurlibelle() {
		return transporteurlibelle;
	}

	public void setTransporteurlibelle(String transporteurlibelle) {
		this.transporteurlibelle = transporteurlibelle;
	}

	public String getCamionnumero() {
		return camionnumero;
	}

	public void setCamionnumero(String camionnumero) {
		this.camionnumero = camionnumero;
	}

	public String getCamionlibelle() {
		return camionlibelle;
	}

	public void setCamionlibelle(String camionlibelle) {
		this.camionlibelle = camionlibelle;
	}

	public String getDateEdition() {
		return dateEdition;
	}

	public void setDateEdition(String dateEdition) {
		this.dateEdition = dateEdition;
	}

	public String getAdresseDestinataire() {
		return adresseDestinataire;
	}

	public void setAdresseDestinataire(String adresseDestinataire) {
		this.adresseDestinataire = adresseDestinataire;
	}


	public String getProgrammeLibelle() {
		return programmeLibelle;
	}

	public void setProgrammeLibelle(String programmeLibelle) {
		this.programmeLibelle = programmeLibelle;
	}

	public void setCampagneLibelle(String libelleCampagne) {
		campagneLibelle =  libelleCampagne;
		
	}

	public String getCampagneLibelle() {
		return campagneLibelle;
	}

	public Double getPrixtotal() {
		return prixtotal;
	}

	public void setPrixtotal(Double prixtotal) {
		this.prixtotal = prixtotal;
	}

	public Double getChargeTotal() {
		return chargeTotal;
	}

	public void setChargeTotal(Double chargeTotal) {
		this.chargeTotal = chargeTotal;
	}

	public String getDateRecpetion() {
		return dateRecpetion;
	}

	public void setDateRecpetion(String dateRecpetion) {
		this.dateRecpetion = dateRecpetion;
	}

	public String getProvenanceBL() {
		return provenanceBL;
	}

	public void setProvenanceBL(String provenanceBL) {
		this.provenanceBL = provenanceBL;
	}

	public String getIdprogramme() {
		return idprogramme;
	}

	public void setIdprogramme(String idprogramme) {
		this.idprogramme = idprogramme;
	}

	public String getIdcampagne() {
		return idcampagne;
	}

	public void setIdcampagne(String idcampagne) {
		this.idcampagne = idcampagne;
	}

	public Long getIdStockSortant() {
		return idStockSortant;
	}

	public void setIdStockSortant(Long idStockSortant) {
		this.idStockSortant = idStockSortant;
	}

	public Long getIdStockReceptionnaire() {
		return idStockReceptionnaire;
	}

	public void setIdStockReceptionnaire(Long idStockReceptionnaire) {
		this.idStockReceptionnaire = idStockReceptionnaire;
	}

	public Long getIdPointdeVenteOrCollecteStock() {
		return idPointdeVenteOrCollecteStock;
	}

	public void setIdPointdeVenteOrCollecteStock(Long idPointdeVenteOrCollecteStock) {
		this.idPointdeVenteOrCollecteStock = idPointdeVenteOrCollecteStock;
	}

	public List<ProduitDTO> getListProduitsOfBL() {
		return listProduitsOfBL;
	}

	public void setListProduitsOfBL(List<ProduitDTO> listProduitsOfBL) {
		this.listProduitsOfBL = listProduitsOfBL;
	}

	public String getAuteurBl() {
		return auteurBl;
	}

	public void setAuteurBl(String auteurBl) {
		this.auteurBl = auteurBl;
	}

	public Long getIdAuteurBl() {
		return idAuteurBl;
	}

	public void setIdAuteurBl(Long idAuteurBl) {
		this.idAuteurBl = idAuteurBl;
	}

	public String getContactAuteurBl() {
		return contactAuteurBl;
	}

	public void setContactAuteurBl(String contactAuteurBl) {
		this.contactAuteurBl = contactAuteurBl;
	}

	public String getContactChauffeur() {
		return contactChauffeur;
	}

	public void setContactChauffeur(String contactChauffeur) {
		this.contactChauffeur = contactChauffeur;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusLibelle() {
		return statusLibelle;
	}

	public void setStatusLibelle(String statusLibelle) {
		this.statusLibelle = statusLibelle;
	}

	public Long getProgrammeId() {
		return programmeId;
	}

	public void setProgrammeId(Long programmeId) {
		this.programmeId = programmeId;
	}

	public String getPicto() {
		return picto;
	}

	public void setPicto(String picto) {
		this.picto = picto;
	}

	public String getReferenceBL() {
		return referenceBL;
	}

	public void setReferenceBL(String referenceBL) {
		this.referenceBL = referenceBL;
	}

	public Long getIdPoiintdeVenteReceptionnnaireBL() {
		return idPoiintdeVenteReceptionnnaireBL;
	}

	public void setIdPoiintdeVenteReceptionnnaireBL(Long idPoiintdeVenteReceptionnnaireBL) {
		this.idPoiintdeVenteReceptionnnaireBL = idPoiintdeVenteReceptionnnaireBL;
	}
}
