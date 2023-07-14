
package sn.awi.pgca.web.bean;

public final class ConstantPGCA {

	/****
	 * SUBVENTION
	 * 
	 * 
	 */
	public static final String PRODUIT_SUBVENTIONNE = "subventionné";
	public static final String PRODUIT_NON_SUBVENTIONNE = "Non subventionné";
	public static final String PRODUIT_NON_TARIFIE = "Non Tarifé";

	public static final String CMD_A_TRAITER = "A traiter";
	public static final String CMD_TRAITE = "Traité";
	public static final String CMD_REFUSE = "Refusé";
	public static final String CMD_EN_COURS_DE_TRAITEMENT = "En cours de traitement";

	public static final int ORD_A_TRAITER = 0;
	public static final String ORD_A_TRAITER_LIBELLE = "En attente de validation";

	public static final int ORD_REFUSE = 1;
	public static final String ORD_REFUSE_LIBELLE = "Refusé";

	public static final int ORD_TRAITE = 2;
	public static final String ORD_TRAITE_LIBELLE = "Traité";

	public static final int ORD_VALIDE = 4;
	public static final String ORD_VALIDE_LIBELLE = "Validé , en attente de traitement";

	public static final String CMD_A_PAYER_A_LA_LIVRAISON = "0";
	public static final String CMD_PAYE_AU_SIEGE = "1";
	public static final String CMD_PAYE_PAR_CREDIT = "2";
	public static final String CMD_PAYE_NATURE = "3";

	public static final String CMD_MODE_PAIEMENT_ALA_LIVRAISON = "Paiement à la livraison";
	public static final String CMD_MODE_PAIEMENT_AU_SIEGE = "Paiement au siège";
	public static final String CMD_MODE_PAIEMENT_A_CREDIT = "Paiement à credit";
	public static final String CMD_MODE_PAIEMENT_PAR_NATURE = "Paiement par nature";

	public static final int PROG_CREE = 0;
	public static final int PROG_POUVERT = 1;
	public static final int PROG_FERMEE = 2;

	public static final String ICON_TRAITE = "fa fa-check";
	public static final String ICON_ENCOURS = "fa fa-spinner";
	public static final String ICON_REFUSE = "fa fa-close";
	
	public static final String ICON_TRAITE_COLOR_RED  = "#bb0101";
	public static final String ICON_ENCOURS_COLOR_GREEN = "#80c347"; 
	public static final String ICON_REFUSE_COLOR_YELLOW  = "#d6ce35";
	

	// Cheque
	public static final String CHEQUE_A_ENCAISSER_Libelle = "A encaisser";
	public static final String CHEQUE_ENCAISSER_LIbelle = "Encaissé";
	public static final int CHEQUE_ENCAISSER = 1;
	public static final int CHEQUE_A_ENCAISSER = 0;

	/****
	 * Message Unite de mesure
	 * 
	 * 
	 */
	
	
	public static final String PASSWORDToChange  = "achanger";

	
	public static final String UNITE_DE_MESURE_TONNE = "Tonnage";
	public static final String UNITE_DE_MESURE_LITRE = "Litre";
	public static final String UNITE_DE_MESURE_PIECE = "Piéce";
	public static final String UNITE_DE_MESURE_FLACON = "Flacon";

	public static final String CMD_STATUT_CLASS_EN_COURS = "badegEncours";

	public static final String CMD_STATUT_CLASS_TRAITE = "badegTraite";

	public static final String CMD_STATUT_CLASS_VALIDE = "badegValide";

	public static final String CMD_STATUT_CLASS_REFUSE = "badegRefuse";

	public static final String CSS_MISEENPLACE_INFOS_POINT_DE_VENTE = "infoClassMiseAPlace";
	public static final String CSS_MISEENPLACE_INFOS_POINT_DE_VENTE_MSG = "Informations de la commune de ";

	public static final String CSS_MISEENPLACE_AUCUN_INFOS_POINT_DE_VENTE = "infoClassMiseAPlaceAucunPointdeVente";
	public static final String CSS_MISEENPLACE_AUCUN_INFOS_POINT_DE_VENTE_MSG = " Aucun point de vente dans la commune de ";

	public static final boolean SESSION_USER_CONNECTED = false;

	// INtrant
	public static final int INTRANT_NODISPO_AU_FOURNISSEUR = -6;
	public static final int QUANTITE_INTRANT_NODISPO_AU_FOURNISSEUR = -2;
	public static final int INTRANT_ERROR = -1;

	public static final int BL_MISE_EN_PLACE_EXIST = -8;
	public static final int LV_MISE_EN_PLACE_EXIST = -9;
	public static final int INTRANT_NON_TARIFIE = -10;


	public static final String Rapport_DIRECTORY_PATH = "/home/awa/GED-PGCA/Rapport/";
	public static final String Rapport_DIRECTORY_PATH_LOCAL = "/Users/Amet/GED-PGCA/Rapport/";

	// Mail attachement
	//public static final String DIRECTORY_PATH_LOGO_MAIL = "file:/home/awa/GED-PGCA/ressources/logo.png";
	public static final String DIRECTORY_PATH_HEADER_MAIL = "file:/home/awa/GED-PGCA/ressources/sedab.png";
	public static final String LDIRECTORY_PATH_HEADER_MAIL = "file:/Users/Amet/GED-PGCA/Versement/sedab.png";

	//public static final String LDIRECTORY_PATH_LOGO_MAIL = "file:/Users/Amet/GED-PGCA/Versement/logo.png";
	
	// REPERTOIRE IKOUALA
	public static final String PICTO_NERICA_MAIL = "file:/root/sedab/nerica5.png";
	public static final String PICTO_NERICA_MAIL_LOCAL = "file:/Users/Amet/GED-PGCA/Versement/nerica5.png";



	// PDF attachement
	public static final String DIRECTORY_PATH_LOGO_PDF = "/home/awa/GED-PGCA/ressources/pdf/logoPDF.png";
	public static final String DIRECTORY_PATH_FOOTER_PDF = "/home/awa/GED-PGCA/ressources/pdf/fo.png";
	public static final String DIRECTORY_PATH_Header_PDF = "/Users/Amet/Documents/exportExport/Fheader.png";
	public static final String DIRECTORY_PATH_TMP_PDF = "/home/awa/GED-PGCA/ressources/pdf/reporting.pdf";   //"/tmp/reporting.pdf"; //
	
	// PDF attachement  -- Local dev
//	public static final String LDIRECTORY_PATH_LOGO_PDF = "/Users/Amet/Documents/exportExport/Fheader.png";
//	public static final String LDIRECTORY_PICTO = "/Users/Amet/Documents/exportExport/nerica1.png";
	public static final String DIRECTORY_PICTOXXXX = "/home/awa/GED-PGCA/ressources/pdf/nlogo.jpg";
	public static final String LDIRECTORY_PATH_FOOTER_PDF = "file:/Users/Amet/Documents/exportExport/nlogo.png";
	
	
	public static final String LDIRECTORY_PICTOXXXX = "file:/Users/Amet/Documents/exportExport/nlogo.png";
	
	public static final String LFENERATEDREPOT = "/Users/Amet/Documents/exportExport/AAAA.pdf";


	

	/// local
	
	public static final String LDIRECTORY_PATH_TMP_PDF = "file:/Users/Amet/Documents/exportExport/mef.pdf";

	
	
//	public static final String LDIRECTORY_PICTO_BlANC = "/Users/Amet/Documents/exportExport/blanc.png";
//
//	public static final String LDIRECTORY_PATH_FOOTER_PDF = "/Users/Amet/Documents/exportExport/fo.png";
//	public static final String LDIRECTORY_PATH_Header_PDF = "/Users/Amet/Documents/exportExport/ff.png";


	public static final String MISE_EN_PLACE_DISPO = " mise en places trouvée (s)";
	public static final String MISE_EN_PLACE_NO_DISPO = "Aucune Mise en place trouvée !";

	public static final String SUCCESS = "success";

	public static final String ERROR = "error";

	public static final String UPDATE = "update";

	public static final String RETURN = "return";

	public static final String DETAILS = "details";

	public static final String LOGOUT = "logout";

	public static final String DEFAULT_MDP = "achanger";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static final String IMMATRICULATION_MORALE = "im";
	
	
	
	
	

	public static final String IMMATRICULATION_MORALE_SUCCESS = "immatriculation_morale_success"; /*
																									 * IMMATRICULATION_MORALE
																									 * +
																									 * SUCCESS
																									 * ;
																									 */

	public static final String IMMATRICULATION_MORALE_ERROR = "immatriculation_morale_error"; /*
																								 * IMMATRICULATION_MORALE
																								 * +
																								 * ERROR
																								 * ;
						
																								 *
																								 *
																								 *
																								 *
																								 *
																								 *
																								 *
																								 *
																								 */

	public static final String SESSION_NOM_UTILISATEUR = "nomUtilisateur";

	public static final String SESSION_USER_AUTHORISE = "userAuthorise";

	public static final String SESSION_HABILITATION = "habilitation";

	public static final String SESSION_ENTITE_JURIDIQUE = "entiteJuridique";

	public static final String SESSION_CODE_3_L_ENJU = "code3LENJU";

	public static final String SESSION_CODE_REGION = "codeRegion"; // avant
																	// :
																	// codeEntitejuridique

	public static final String SESSION_ID_ENTITE_JURIDIQUE = "idEntiteJuridique";

	public static final String SESSION_CODE_PAYS = "codePays";

	public static final String SESSION_REGION = "region";

	public static final String SESSION_CODE_PROFIL = "codeProfil";

	public static final String STATUS[] = { "Traitement Guichetier", "En Saisie", "En attente de validation", "Refusé",
			"Acceptation temporaire", "Refusé", "En cours", "Archivé", "Radie", "En injonction",
			"Acceptation definitive" };

	public static final String SERVICE_EQ = "enregistrerQuittance";
	public static final String SERVICE_EF = "enregistrerFormalite";
	public static final String SESSION_NOM_USER = "nomUser";
	public static final String SESSION_PRENOM_USER = "prenomUser";
	public static final String SESSION_ID_USER = "idUser";
	public static final String SESSION_USER_SIGNATAIRE = "UserEstSignataire";
	public static final String SESSION_PARAMETRE_PAYS = "parametreParPays";
	public static final String MSG_GENERICS_ERROR = "Une erreur technique est survenue lors de l'opération suivante :  ";
	
	
	
	//  CAMPAGNE
	public static final Long CAMPAGNE_DEFAULT = 0L;

	
	
	
	/****************  MSG NOTIFICATION MAIL  **********************/
	
	public static final String MAIL_TITLE = " Rapport d'activités de la SEDAB "; 
	
	public static final String MAIL_MSG = "<br/> Bonjour Mr {NOM}, <br/><br/> Vous recevez ce rapport car vous êtes enregisté en tant que manager de la SEDAB. "
			+ "<br/>  Ce rapport donne une visibilité globale des activités de la SEDAB du <strong>  {PERIOD1} au {PERIOD2}</strong>. <br/>"
			+ " <p>Montant total des ventes enregistées la semaine dernière : <strong> {VENTES} </strong>  ({VENTESNB}).</p>"
			+ " <p>Nombre de mises en place en cours : <strong> {MEP} </strong>  .</p>"
			+ " <p>Montant total des credits en cours:  <strong> {CREDITS} </strong> .</p>"
			+ " <p>Montant total des versements effectués : <strong> {VERSEMENTS} </strong> .</p>"
			+ " <p>Stock résiduel : <strong> {STOCKR}  </strong>. </p>"
			+ " <p style='color : #98362a ; font-size : 22px'>* Pour plus de détails merci d'ouvrir le pdf joint à ce message.</p>";
	
	public static final String MAIL_ALERT_AGENT_SAISIE = "<br/> Bonjour Mr {NOM}, <br/><br/> Vous recevez ce rapport car vous êtes enregistés en tant que manager de la SEDAB. "
			+ " Ce rapport donne une visibilité globale des activités de la SEDAB du <strong>  {PERIOD1} au {PERIOD2}</strong>. <br/>"
			+ " <p>Nombre de mises en place en cours : <strong> {MEP} </strong>  .</p>"
			+ " <p>Stock résiduel : <strong> {STOCKR}  </strong>. </p>"
			+ " <p style='color : #98362a'>* Pour plus de détails merci d'ouvrir le pdf joint à ce message.</p>";
	
	
	
	
	public static final String MAIL_CONFIRMATION_CREATION_UTILISATEUR = " Nous avons le plaisir de vous confirmer la création de votre compte e-SEDAB. <br/> "
			+ " Pour vous connecter une première fois, vous devez utiliser les infos ci-dessous"
			+ " <p>Nom utilisateur  : <b> {USER} </b>  </p>"
			+ " <p>Mot de passe par défaut : <b>   achanger  </b> </p>"
			+ " <p>Lien vers la plateforme :  <b>   <a  style='color : #56880a' href='http://plateforme.sedabsenegal.com:8080/pgca/iu/connexion.xhtml' target='_blank'> Cliquer ici </a> </b> </p> ";



}
