package org.cages.moulinette.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	private String   libelle;

	
	private String   departementPointdeVente;
	private String   codePointDeVente;


	private String   categorie;
	private String   type;

	
	
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
	public IntrantDTO(Long id, String libelle2, Double quantite2, String pictoIntrant) {
		idProduit = id;
		libelle = libelle2;
		quantite = quantite2;
		pictoImages =  pictoIntrant;
	}

	public IntrantDTO() {
		// TODO Auto-generated constructor stub
	}
	
	private String pictoImages = "default.jpg";
	private float prixProducteur;
	private float prixNonSubventionne;
	private String provenance;
	private Long idCategorieIntrant;

	

	/** Collecte  : achat produit **/
	private Long idStock;
	private Long idCampagneProgramme;
	private Long idType;

	


}
