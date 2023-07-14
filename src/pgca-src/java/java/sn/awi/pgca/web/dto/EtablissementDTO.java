package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EtablissementDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 148048478857371002L;
	
	private String numerorccm;
	private String denomination;
	private String denominationGenerique;
	private String nomcommercial;
	private String sigle;
	private String enseigne;

	private int duree;
	private String idFormeJuririque;
	private double capitalSocial;
	private double capitalNumeraire;
	private double capitalNature;
	
	private int nombreSalariesPrevu;
	private Date date_de_debut;
	private List<ActiviteDTO> activiteDTOs;
	private AdresseDTO adresseDTO;
	private AdresseDTO adresseSiegeDTO;
	private ContactDTO contactDTO;
	private String activite1;
	private String sactId1;
	private String activite2;
	private String sactId2;
	private String numerotmp2;

	public EtablissementDTO() {
		adresseDTO = new AdresseDTO();
		setAdresseSiegeDTO(new AdresseDTO());
		contactDTO = new ContactDTO();
		activiteDTOs = new ArrayList<ActiviteDTO>();
	}
	
	public String getNumerorccm() {
		return numerorccm;
	}

	public void setNumerorccm(String numerorccm) {
		this.numerorccm = numerorccm;
	}
	
	public AdresseDTO getAdresseDTO() {
		return adresseDTO;
	}
	public void setAdresseDTO(AdresseDTO adresseDTO) {
		this.adresseDTO = adresseDTO;
	}
	public String getNomcommercial() {
		return nomcommercial;
	}
	public void setNomcommercial(String nomcommercial) {
		this.nomcommercial = nomcommercial;
	}
	public String getSigle() {
		return sigle;
	}
	public void setSigle(String sigle) {
		this.sigle = sigle;
	}
	public String getEnseigne() {
		return enseigne;
	}
	public void setEnseigne(String enseigne) {
		this.enseigne = enseigne;
	}
	public int getNombreSalariesPrevu() {
		return nombreSalariesPrevu;
	}
	public void setNombreSalariesPrevu(int nombreSalariesPrevu) {
		this.nombreSalariesPrevu = nombreSalariesPrevu;
	}
	
	public List<ActiviteDTO> getActiviteDTOs() {
		return activiteDTOs;
	}
	public void setActiviteDTOs(List<ActiviteDTO> activiteDTOs) {
		this.activiteDTOs = activiteDTOs;
	}
	public String getActivite1() {
		return activite1;
	}
	public void setActivite1(String activite1) {
		this.activite1 = activite1;
	}
	public String getSactId1() {
		return sactId1;
	}
	public void setSactId1(String sactId1) {
		this.sactId1 = sactId1;
	}
	

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public String getIdFormeJuririque() {
		return idFormeJuririque;
	}

	public void setIdFormeJuririque(String idFormeJuririque) {
		this.idFormeJuririque = idFormeJuririque;
	}

	public double getCapitalSocial() {
		return capitalSocial;
	}

	public void setCapitalSocial(double capitalSocial) {
		this.capitalSocial = capitalSocial;
	}

	public String getActivite2() {
		return activite2;
	}

	public void setActivite2(String activite2) {
		this.activite2 = activite2;
	}

	public String getSactId2() {
		return sactId2;
	}

	public void setSactId2(String sactId2) {
		this.sactId2 = sactId2;
	}

	public double getCapitalNumeraire() {
		return capitalNumeraire;
	}

	public void setCapitalNumeraire(double capitalNumeraire) {
		this.capitalNumeraire = capitalNumeraire;
	}

	public double getCapitalNature() {
		return capitalNature;
	}

	public void setCapitalNature(double capitalNature) {
		this.capitalNature = capitalNature;
	}

	public ContactDTO getContactDTO() {
		return contactDTO;
	}

	public void setContactDTO(ContactDTO contactDTO) {
		this.contactDTO = contactDTO;
	}

	public AdresseDTO getAdresseSiegeDTO() {
		return adresseSiegeDTO;
	}

	public void setAdresseSiegeDTO(AdresseDTO adresseSiegeDTO) {
		this.adresseSiegeDTO = adresseSiegeDTO;
	}

	public String getNumerotmp2() {
		return numerotmp2;
	}

	public Date getDate_de_debut() {
		return date_de_debut;
	}

	public void setDate_de_debut(Date date_de_debut) {
		this.date_de_debut = date_de_debut;
	}

	public void setNumerotmp2(String numerotmp2) {
		this.numerotmp2 = numerotmp2;
	}

	public String getDenominationGenerique() {
		return denominationGenerique;
	}

	public void setDenominationGenerique(String denominationGenerique) {
		this.denominationGenerique = denominationGenerique;
	}
}
