package org.cages.moulinette.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdresseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -701468399056679642L;

	
	private String id;
	
	private String codepostal;

	private String libelle;
	
	private String ville;
	
	private String quartier;
	
	private String idRegion;
	
	private String libelleRegion;
	
	private String idPays;

	
	
	public    void  cleancacheDto()
	{
		 id =  "";
		 codepostal = "";
		 libelle = "";
		 ville = "";
		 quartier = "";
		 idRegion = "";
		 idPays = "";
	}
	public String getCodepostal() {
		return codepostal;
	}

	public void setCodepostal(String codepostal) {
		this.codepostal = codepostal;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getQuartier() {
		return quartier;
	}

	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}

	public String getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(String idRegion) {
		this.idRegion = idRegion;
	}

	public String getIdPays() {
		return idPays;
	}

	public void setIdPays(String idPays) {
		this.idPays = idPays;
	}
	
	public List<String> incorrectAttribute() {
		List<String> lAttributes = new ArrayList<String>();
		/*if (!UtilString.isCorrect(libelle))
			lAttributes.add("Libellé adresse");
		if (!UtilString.isCorrect(ville))
			lAttributes.add("Ville adresse");*/
		return lAttributes;
	}
	public String getLibelleRegion() {
		return libelleRegion;
	}
	public void setLibelleRegion(String libelleRegion) {
		this.libelleRegion = libelleRegion;
	}
}
