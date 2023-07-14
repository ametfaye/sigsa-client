package sn.awi.pgca.business.service.mock;

import java.io.Serializable;

public class ParametreParPays implements Serializable {

	/**
	 * 
	 */
	private static final long		serialVersionUID									= 8362975492891861553L;

	public static final String	CODE_LANGUE												= "langue";

	public static final String	CODE_RESET_SEQ_NUM_RCCM_BY_YEAR		= "ResetSequenceNumRccmByYear";

	public static final String	CODE_RESET_SEQ_NUM_QUIT_BY_YEAR		= "ResetSequenceNumQuittanceByYear";

	public static final String	CODE_RESET_SEQ_NUM_ORDRE_BY_YEAR	= "ResetSequenceNumOrdreByYear";

	public static final String	CODE_RESET_SEQ_NUM_SURETE_BY_YEAR	= "ResetSequenceNumSureteByYear";

	public static final String	CODE_NB_CARAC_SEQ_NUM_ORDRE				= "NombreCaractereSeqNumOrdre";

	public static final String	CODE_NB_CARAC_SEQ_QUITTANCE				= "NombreCaractereSeqQuittance";

	public static final String	CODE_NB_CARAC_ANNEE								= "NombreCaractereAnnee";

	public static final String	CODE_PREFIX_NUM_ORDRE_BY_YEAR			= "PrefixNumeroOrdreByYear";

	public static final String	CODE_PREFIX_NUM_QUIT_BY_YEAR			= "PrefixNumeroQuittanceByYear";

	public static final String	CODE_PREFIX_NUM_RCCM_BY_YEAR			= "PrefixNumeroRccmByYear";

	public static final String	CODE_PREFIX_NUM_SURETE_BY_YEAR		= "PrefixNumeroSureteByYear";

	public static final String	CODE_ADD_RCCMTEXT_2_NUM_RCCM			= "AddRCCMTexToNumRccm";

	public static final String	CODE_ADD_RCCMTEXT_2_NUM_SURETE		= "AddRCCMTexToNumSurete";

	public static final String	CODE_TARIF_IMMAT									= "CoutQuittanceImmat";

	public static final String	CODE_TARIF_SURETE									= "CoutInscriptionSurete";

	public static final String	CODE_CODE_MONETAIRE								= "CodeMonetaire";

	private String							langue;

	private int									nbCaractereAnnee;

	private int									nbCaractereSeqNumOrdre;

	private int									nbCaractereSeqQuittance;

	private boolean							razSequenceNumRccmByYear;

	private boolean							razSequenceNumSureteByYear;

	private boolean							razSequenceNumQuittanceByYear;

	private boolean							razSequenceNumOrdreByYear;

	private boolean							prefixNumOrdreByYear;

	private boolean							prefixNumQuittanceByYear;

	private boolean							prefixNumRccmByYear;

	private boolean							prefixNumSureteByYear;

	private boolean							addRCCMTextToNumRccm;

	private boolean							addRCCMTextToNumSurete;

	public int getNbCaractereAnnee() {
		return nbCaractereAnnee;
	}

	public void setNbCaractereAnnee(int nbCaractereAnnee) {
		this.nbCaractereAnnee = nbCaractereAnnee;
	}

	public int getNbCaractereSeqNumOrdre() {
		return nbCaractereSeqNumOrdre;
	}

	public void setNbCaractereSeqNumOrdre(int nbCaractereSeqNumOrdre) {
		this.nbCaractereSeqNumOrdre = nbCaractereSeqNumOrdre;
	}

	public int getNbCaractereSeqQuittance() {
		return nbCaractereSeqQuittance;
	}

	public void setNbCaractereSeqQuittance(int nbCaractereSeqQuittance) {
		this.nbCaractereSeqQuittance = nbCaractereSeqQuittance;
	}

	public boolean isRazSequenceNumRccmByYear() {
		return razSequenceNumRccmByYear;
	}

	public void setRazSequenceNumRccmByYear(boolean razSequenceNumRccmByYear) {
		this.razSequenceNumRccmByYear = razSequenceNumRccmByYear;
	}

	public boolean isRazSequenceNumSureteByYear() {
		return razSequenceNumSureteByYear;
	}

	public void setRazSequenceNumSureteByYear(boolean razSequenceNumSureteByYear) {
		this.razSequenceNumSureteByYear = razSequenceNumSureteByYear;
	}

	public boolean isRazSequenceNumQuittanceByYear() {
		return razSequenceNumQuittanceByYear;
	}

	public void setRazSequenceNumQuittanceByYear(boolean razSequenceNumQuittanceByYear) {
		this.razSequenceNumQuittanceByYear = razSequenceNumQuittanceByYear;
	}

	public boolean isRazSequenceNumOrdreByYear() {
		return razSequenceNumOrdreByYear;
	}

	public void setRazSequenceNumOrdreByYear(boolean razSequenceNumOrdreByYear) {
		this.razSequenceNumOrdreByYear = razSequenceNumOrdreByYear;
	}

	public boolean isPrefixNumOrdreByYear() {
		return prefixNumOrdreByYear;
	}

	public void setPrefixNumOrdreByYear(boolean prefixNumOrdreByYear) {
		this.prefixNumOrdreByYear = prefixNumOrdreByYear;
	}

	public boolean isPrefixNumQuittanceByYear() {
		return prefixNumQuittanceByYear;
	}

	public void setPrefixNumQuittanceByYear(boolean prefixNumQuittanceByYear) {
		this.prefixNumQuittanceByYear = prefixNumQuittanceByYear;
	}

	public boolean isPrefixNumRccmByYear() {
		return prefixNumRccmByYear;
	}

	public void setPrefixNumRccmByYear(boolean prefixNumRccmByYear) {
		this.prefixNumRccmByYear = prefixNumRccmByYear;
	}

	public boolean isPrefixNumSureteByYear() {
		return prefixNumSureteByYear;
	}

	public void setPrefixNumSureteByYear(boolean prefixNumSureteByYear) {
		this.prefixNumSureteByYear = prefixNumSureteByYear;
	}

	public boolean isAddRCCMTextToNumRccm() {
		return addRCCMTextToNumRccm;
	}

	public void setAddRCCMTextToNumRccm(boolean addRCCMTextToNumRccm) {
		this.addRCCMTextToNumRccm = addRCCMTextToNumRccm;
	}

	public boolean isAddRCCMTextToNumSurete() {
		return addRCCMTextToNumSurete;
	}

	public void setAddRCCMTextToNumSurete(boolean addRCCMTextToNumSurete) {
		this.addRCCMTextToNumSurete = addRCCMTextToNumSurete;
	}

	public String getLangue() {
		return langue;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}

}
