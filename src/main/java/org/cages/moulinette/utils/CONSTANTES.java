package org.cages.moulinette.utils;

public class CONSTANTES {

	public static final String badgeSUCESS = "badge-success";
	public static final String badgeSECONDARY = "badge-secondary";
	public static final String badgeDANGER = "badge-danger";
	public static final String badgePRIMARY = "badge-primary";
	public static final String badgeWARN = "badge-warning";
	
	public static final String SECONDARY_COLOR = "gray";
	public static final String WARNING_COLOR = "orange";
	public static final String DANGER_COLOR = "red";
	public static final String PRIMARY_COLOR = "blue";
	public static final String SUCESS_COLOR = "green";

	public static final String AlertWARN = "#fff6e4";
	public static final String AlertError = "#fee6eb";
	public static final String AlertValid = "#e1faf0";
	public static final String AlertInfo = "#e3ecffv";
	
	
	/** SERVEUR APPLICATIF ****/
	public static final String SERVER_APPLICATIF = " ";

	/** SERVEUR FTP : CAN BE READ FROM DB IN PRODUCTION ENVIRONNEMENT ****/

	public static final String SERVER_SRVEUR = " ";
	public static final String SERVER_PORT = " ";
	public static final String SERVER_USER = " ";
	public static final String SERVER_PASSWORD = " ";

	/** FICHUERS & EXTENTIONS VALIDES ****/
	public static final String EXTENTION_NONMEDICAL = "FCNM_";
	public static final String EXTENTION_MEDICAL = "FCNM_";
	public static final String EXTENTION = "xml";	
	
	/**   ALERTES DASHBOARD *****/
	public static final String ALERT_WARN = "alert-warning";
	public static final String ALERT_ERROR = "alert-danger";
	public static final String ALERT_SUCCESS = "alert-success";

	/***MSG SERVICE IHM **/
	public static final String MSG_WARN = "Avertissement";
	public static final String MSG_ERROR = "Opps une erreur est survenue  !!";
	public static final String MSG_SUCCESS = "Confirmation";
	
	public static final String MSG_FILENULL = "Le fichier  à traiter est obligatoire";
	public static final String MSG_FILENAMEINVALID = "Le fichier ne respecte pas la nomenclature  attendue. " + EXTENTION_NONMEDICAL + "* pour le traitement non médical." + EXTENTION_MEDICAL + "* pour le traitement médical.";

	// PICTO IHM
	public static final String EcartPositifClass = "fa-chevron-circle-up";
	public static final String EcartPositifColor = "#3c763d";
	
	public static final String EcartNegatifClass = "fa-chevron-circle-down";
	public static final String EcartNegatifColor = "#bd0907";

	
	/******REP TMP pour LECTURE FICHIER ****/
		
	public static final String STATUT_SAISIE_LIBELLE = " En attente de validation ";
	public static final int STATUT_SAISIE = 0;	

	public static final String STATUT_VALIDE_LIBELLE  = " Validé  ";
	public static final int STATUT_VALIDE = 1;	

	public static final String STATUT_SUPPRIME_LIBELLE = " supprimé ";
	public static final int STATUT_SUPPRIME = 2;	

	public static final String STATUT_COMPTABILISE_LIBELLE = " comptabilisé ";
	public static final int STATUT_COMPTABILISE = 3;	

	public static final String STATUT_BROUILLON_LIBELLE = " Brouillon ";
	public static final int STATUT_BROUILLON = 4;
	
	public static final String STATUT_REFUSE_LIBELLE = " Refusé ";
	public static final int STATUT_REFUSE = 5;
	
	// Champs OBLIGATOIRE : DOMAINE AGENT
	public static final String MATRICULE_OBLIGATOIRE = "Le matricule est obligatoire ";
	public static final String NOM_OBLIGATOIRE = "Le nom est obligatoire ";
	public static final String PRENOM_OBLIGATOIRE = "Le prenom est obligatoire ";
	public static final String EMAIL_OBLIGATOIRE = "l'email  est obligatoire ";
	public static final String ECHALON_OBLIGATOIRE = "L'échelon  est obligatoire ";
	public static final String HIERRARCHIE_OBLIGATOIRE = "La hiérarchie  est obligatoire ";
	public static final String CORPS_OBLIGATOIRE = "Le corps  est obligatoire ";
	public static final String SERVICCE_OBLIGATOIRE = "Le service  est obligatoire ";
	public static final String ENTITEP_OBLIGATOIRE = "L'entité publique  est obligatoire ";
	public static final String FONCTION_OBLIGATOIRE = "La fonction   est obligatoire ";

	// CHAMPS FORMAT  : DOMAINE AGENT
	public static final String EMAIL_INVALIDE = "L'adresse email est invalide";

	
	public static final String NO_ERROR = "";


	private String descriptifProgramme;
	
	
	

	public static  Integer NB_PRODUCTEUR = 200;
	
	
	

}
