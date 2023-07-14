package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class ConfigurationGlobaleDTO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2382132357281609524L;

	private String							langue;//ok

	private int									nbCaractereAnnee;//ok

	private int									nbCaractereSeqNumOrdre;

	private int									nbCaractereSeqQuittance;

	private boolean							razSequenceNumRccmByYear; // ok

	private boolean							razSequenceNumSureteByYear; //ok

	private boolean							razSequenceNumQuittanceByYear; //ok

	private boolean							razSequenceNumOrdreByYear; //ok

	private boolean							prefixNumOrdreByYear; //ok

	private boolean							prefixNumQuittanceByYear; //ok

	private boolean							prefixNumRccmByYear;

	private boolean							prefixNumSureteByYear;

	private boolean							addRCCMTextToNumRccm;

	private boolean							addRCCMTextToNumSurete;

	private double							coutQuittanceRC; //ok

	private double							coutQuittanceCM; //ok

	private String							codeMonetaire; // ok

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

	public String getCodeMonetaire() {
		return codeMonetaire;
	}

	public void setCodeMonetaire(String codeMonetaire) {
		this.codeMonetaire = codeMonetaire;
	}

	public double getCoutQuittanceCM() {
		return coutQuittanceCM;
	}

	public void setCoutQuittanceCM(double coutQuittanceCM) {
		this.coutQuittanceCM = coutQuittanceCM;
	}

	public double getCoutQuittanceRC() {
		return coutQuittanceRC;
	}

	public void setCoutQuittanceRC(double coutQuittanceRC) {
		this.coutQuittanceRC = coutQuittanceRC;
	}

}
