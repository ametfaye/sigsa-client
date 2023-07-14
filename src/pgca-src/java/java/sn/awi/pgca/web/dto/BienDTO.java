package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sn.awi.pgca.utils.UtilString;

public class BienDTO implements Serializable {

	private static final long	serialVersionUID	= 9125422905791273379L;

	private Long							id;

	private String						libelle;

	private boolean						deplacable;

	private boolean						alienable;

	private String						idTypeBien;

	private String						libelleTypeBien;

	private AdresseDTO				localisationFuture;

	private String						numerotmp;																// numero
																																			// temporraire

	public BienDTO() {
		localisationFuture = new AdresseDTO();
	}
	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public boolean isDeplacable() {
		return deplacable;
	}

	public void setDeplacable(boolean deplacable) {
		this.deplacable = deplacable;
	}

	public boolean isAlienable() {
		return alienable;
	}

	public void setAlienable(boolean alienable) {
		this.alienable = alienable;
	}

	public String getIdTypeBien() {
		return idTypeBien;
	}

	public void setIdTypeBien(String idTypeBien) {
		this.idTypeBien = idTypeBien;
	}

	public String getLibelleTypeBien() {
		return libelleTypeBien;
	}

	public void setLibelleTypeBien(String libelleTypeBien) {
		this.libelleTypeBien = libelleTypeBien;
	}

	public AdresseDTO getLocalisationFuture() {
		return localisationFuture;
	}

	public void setLocalisationFuture(AdresseDTO localisationFuture) {
		this.localisationFuture = localisationFuture;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumerotmp() {
		return numerotmp;
	}

	public void setNumerotmp(String numerotmp) {
		this.numerotmp = numerotmp;
	}

	public List<String> incorrectAttribute() {
		List<String> sb = new ArrayList<String>();
		if (!UtilString.isCorrect(libelle))
			sb.add("Libellé");
		if (!UtilString.isCorrect(idTypeBien))
			sb.add("Type de bien");
		return sb;
	}

}
