package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sn.awi.pgca.utils.UtilString;

public class ConjointDTO implements Serializable {

	private static final long	serialVersionUID	= -2285112851411590238L;

	private Long							idConjoint;

	private PersonneDTO				personne;

	private String						lieumariage;

	private String						strDatemariage;

	private String						strDatemariage2;

	private Date							datemariage;

	private String						demandeSeparationBien;

	private String						optionmat;

	private String						liboptionmat;

	private String						clausesrestrictives;

	private String						regimemat;

	private String						libregimemat;

	public ConjointDTO() {
		personne = new PersonneDTO();
	}

	public PersonneDTO getPersonne() {
		return personne;
	}

	public void setPersonne(PersonneDTO personne) {
		this.personne = personne;
	}

	public Long getIdConjoint() {
		return idConjoint;
	}

	public void setIdConjoint(Long idConjoint) {
		this.idConjoint = idConjoint;
	}

	public String getLieumariage() {
		return lieumariage;
	}

	public void setLieumariage(String lieumariage) {
		this.lieumariage = lieumariage;
	}

	public Date getDatemariage() {
		return datemariage;
	}

	public void setDatemariage(Date datemariage) {
		this.datemariage = datemariage;
	}

	public String getDemandeSeparationBien() {
		return demandeSeparationBien;
	}

	public void setDemandeSeparationBien(String demandeSeparationBien) {
		this.demandeSeparationBien = demandeSeparationBien;
	}

	public String getOptionmat() {
		return optionmat;
	}

	public void setOptionmat(String optionmat) {
		this.optionmat = optionmat;
	}

	public String getClausesrestrictives() {
		return clausesrestrictives;
	}

	public void setClausesrestrictives(String clausesrestrictives) {
		this.clausesrestrictives = clausesrestrictives;
	}

	public String getStrDatemariage() {
		return strDatemariage;
	}

	public void setStrDatemariage(String strDatemariage) {
		this.strDatemariage = strDatemariage;
	}

	public String getRegimemat() {
		return regimemat;
	}

	public void setRegimemat(String regimemat) {
		this.regimemat = regimemat;
	}

	public String getStrDatemariage2() {
		return strDatemariage2;
	}

	public void setStrDatemariage2(String strDatemariage2) {
		this.strDatemariage2 = strDatemariage2;
	}

	public List<String> incorrectAttribute() {
		List<String> sb = new ArrayList<String>();
		if (!UtilString.isCorrect(optionmat))
			sb.add("Option matrimoniale");
		if (!UtilString.isCorrect(regimemat))
			sb.add("Régime matrimoniale");
		if (!UtilString.isCorrect(strDatemariage))
			sb.add("Date de mariage");
		if (!UtilString.isCorrect(demandeSeparationBien))
			sb.add("Demande de séparation de bien");
		if (!UtilString.isCorrect(lieumariage))
			sb.add("Lieu mariage");
		if (!UtilString.isCorrect(clausesrestrictives))
			sb.add("Clauses restrictives");
		return sb;
	}

	public String getLiboptionmat() {
		return liboptionmat;
	}

	public void setLiboptionmat(String liboptionmat) {
		this.liboptionmat = liboptionmat;
	}

	public String getLibregimemat() {
		return libregimemat;
	}

	public void setLibregimemat(String libregimemat) {
		this.libregimemat = libregimemat;
	}

}
