package sn.awi.pgca.web.dto;

import java.io.Serializable;

import sn.awi.pgca.utils.UtilString;

/**
 * Type de document.
 * 
 * @author awaconsulting
 */
public class GedTypeDocumentDTO implements Serializable {
	/**
	 * Generated Serial Version UID.
	 */
	private static final long		serialVersionUID							= -394887421771123961L;
	
	// généré par l'appli
	public static final String	CODE_FORMULAIRE								= "Formulaire";

	public static final String	CODE_FORMULAIRE_BIS						= "FormulaireBis";

	public static final String	CODE_QUITTANCE_A_PAYER				= "Quittance";

	public static final String	CODE_ACCUSE_ENREGISTREMENT		= "AccuseEnr";

	public static final String	CODE_ACCUSE_INSCRIPTION				= "AccuseInsc";

	public static final String	CODE_INJONCTION								= "AccuseInj";

	// Uploader
	public static final String	CODE_FORMULAIRE_DEPOT					= "FormulaireDepot";

	public static final String	CODE_QUITTANCE_PAYE						= "QuittancePaye";

	public static final String	CODE_STATUT										= "Statut";

	public static final String	CODE_ACTE_MARIAGE							= "ActeMariage";

	public static final String	CODE_DECLARATION_REGUL				= "DeclRegul";

	public static final String	CODE_DECLARATION_HONNEUR			= "DeclHonneur";

	public static final String	CODE_LISTE_CERTIFIE_CONFORME	= "CertifieConforme";

	public static final String	CODE_EXTRAIT_NAIS							= "ExtraitNais";

	public static final String	CODE_CERTIFICAT_RESIDENCE			= "CertificatRes";

	public static final String	CODE_TITRE_OCCUPATION					= "TitreOccupation";

	public static final String	CODE_TITRE_ACQUISITION				= "TitreAcquisition";

	public static final String	CODE_AUTORISATION_EXERCICE		= "AutoriExercice";

	/**
	 * Identifiant technique.
	 */
	private Long								id;

	/**
	 * Code technique pour le type de document. Lors de l'enrichissement de
	 * nouveau type on s'assurera qu'il reste bien unique.
	 */
	private String							code;

	/**
	 * Le libellé présenté à l'utilisateur.
	 */
	private String							libelle;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public boolean isFormulaire() {
		return UtilString.equal(getCode(), CODE_FORMULAIRE);
	}
	
	public boolean isFormulaireBis() {
		return UtilString.equal(getCode(), CODE_FORMULAIRE_BIS);
	}

	public boolean isQuittanceAPayer() {
		return UtilString.equal(getCode(), CODE_QUITTANCE_A_PAYER);
	}

	public boolean isAccuseEnregistrement() {
		return UtilString.equal(getCode(), CODE_ACCUSE_ENREGISTREMENT);
	}

	public boolean isAccuseInscription() {
		return UtilString.equal(getCode(), CODE_ACCUSE_INSCRIPTION);
	}

	public boolean isInjonction() {
		return UtilString.equal(getCode(), CODE_INJONCTION);
	}

	public boolean isFormulaireDepot() {
		return UtilString.equal(getCode(), CODE_FORMULAIRE_DEPOT);
	}

	public boolean isQuittancePaye() {
		return UtilString.equal(getCode(), CODE_QUITTANCE_PAYE);
	}

	public boolean isStatut() {
		return UtilString.equal(getCode(), CODE_STATUT);
	}

	public boolean isActeMaraige() {
		return UtilString.equal(getCode(), CODE_ACTE_MARIAGE);
	}

	public boolean isListCertConforme() {
		return UtilString.equal(getCode(), CODE_LISTE_CERTIFIE_CONFORME);
	}

	public boolean isDeclRegul() {
		return UtilString.equal(getCode(), CODE_DECLARATION_REGUL);
	}

	public boolean isDeclHonneur() {
		return UtilString.equal(getCode(), CODE_DECLARATION_HONNEUR);
	}

	public boolean isExtraitNais() {
		return UtilString.equal(getCode(), CODE_EXTRAIT_NAIS);
	}

	public boolean isCertificatRes() {
		return UtilString.equal(getCode(), CODE_CERTIFICAT_RESIDENCE);
	}

	public boolean isTitreOccupation() {
		return UtilString.equal(getCode(), CODE_TITRE_OCCUPATION);
	}

	public boolean isTitreAcquisition() {
		return UtilString.equal(getCode(), CODE_TITRE_ACQUISITION);
	}

	public boolean isAutoriExercice() {
		return UtilString.equal(getCode(), CODE_AUTORISATION_EXERCICE);
	}
	
}
