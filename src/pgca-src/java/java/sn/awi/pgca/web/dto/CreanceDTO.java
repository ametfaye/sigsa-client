package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sn.awi.pgca.utils.StringDisplayer;
import sn.awi.pgca.utils.UtilString;

public class CreanceDTO implements Serializable {

	private static final long serialVersionUID = 3018521257769681130L;

	private Long id;
	private String libelle;
	private double montant;
	private Date dateExigibilite;
	private double tauxInteret;
	private String numerotmp;// numero temporraire

	public String generateTooltip() // <a title="#{creancea.libelle}">
									// xxxxxxxxxxxxx</a>
	{
		String tootlip = "";
		int i = 0;

		if (libelle != null) {
			for (i = 40; i > libelle.length(); i += 40) {
				tootlip += libelle.substring(i).concat("</br>");
			}
		}

		return tootlip;
	}

	public String getLibelleCourt() {
		return StringDisplayer.cutString(libelle, 30);
	}

	// TODO deplacer le code metier dans une methode
	public String getLibelleTooltip() {
		char buffer[] = libelle.toCharArray();
		
		if (buffer.length == 0)
				return "";
		String  tmp = "";
		
		for (int i = 0; i < buffer.length; i++) {
	
			 if( i% 50 ==  0 && i != 0)
			{
				 tmp  += buffer[i];
//				 tmp  += " &#013; ";
				i++;
			}
			else
				tmp  += buffer[i];	
		}

		return tmp; 
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public Date getDateExigibilite() {
		return dateExigibilite;
	}

	public void setDateExigibilite(Date dateExigibilite) {
		this.dateExigibilite = dateExigibilite;
	}

	public double getTauxInteret() {
		return tauxInteret;
	}

	public void setTauxInteret(double tauxInteret) {
		this.tauxInteret = tauxInteret;
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
		if (dateExigibilite == null)
			sb.add("Date exigibilité");

		return sb;
	}
}
