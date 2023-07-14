package org.cages.moulinette.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UtilisateurDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4350660425120902595L;

	private Long id;
	private Long pointDecollectId;
	private String pointDeVentetId;

	private String statutUser;
	private String statusUserIcon;
	private String statusUserIconColor;

	private List<CoupleDTO> listAffectationsPV;
	
	private Date dateCreation;
	private Date dateDerniereConnexion;
	private int nbConnexionReussi;
	private Date dateDerniereModificationMDP;
	
	private String code;
	private String email;
	private String password;
	private String passwordClair;
	private String nom;
	private String prenom;
	private String nationalite;
	private String situationmat;
	private String lieudenaissance;
	private Date datedenaissance;
	private String strDatedenaissance;
	private String strDatedenaissance2;
	private String libelle;
	private String estSignataire;
	private String adresse;
	private String tel;
	
	private String themeChoisi;
	private boolean mdpamodifier = true;

	private String newPassword;
	private String confirmPassword;
	private String fichierChangePassword;
	private boolean fichierChangePasswordExiste = false;



	// private List<ProfilDTO> profilDTOs;
	private CoupleDTO profilDTO;

	private String profilId;

	private CoupleDTO entiteJuridiqueDTO;

	private String entiteJuridiqueId;

	public String getEstSignataire() {
		return estSignataire;
	}

	public boolean isMdpamodifier() {
		return mdpamodifier;
	}

	public void setMdpamodifier(boolean mdpamodifier) {
		this.mdpamodifier = mdpamodifier;
	}

	public void setEstSignataire(String estSignataire) {
		this.estSignataire = estSignataire;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/* private NumeroReferentiel Code_genere; */

	public String getEntiteJuridiqueId() {
		return entiteJuridiqueId;
	}

	public void setEntiteJuridiqueId(String entiteJuridiqueId) {
		this.entiteJuridiqueId = entiteJuridiqueId;
	}

	/* private List<EntiteJuridiqueDTO> entiteJuridiqueDTOs; */
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/*
	 * public List<ProfilDTO> getProfilDTOs() { return profilDTOs; }
	 * 
	 * public void setProfilDTOs(List<ProfilDTO> profilDTOs) { this.profilDTOs =
	 * profilDTOs; }
	 */

	public String getNationalite() {
		return nationalite;
	}

	public CoupleDTO getProfilDTO() {
		return profilDTO;
	}

	public void setProfilDTO(CoupleDTO profilDTO) {
		this.profilDTO = profilDTO;
	}

	public String getProfilId() {
		return profilId;
	}

	public void setProfilId(String profilId) {
		this.profilId = profilId;
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}

	public Long getPointDecollectId() {
		return pointDecollectId;
	}

	public void setPointDecollectId(Long pointDecollectId) {
		this.pointDecollectId = pointDecollectId;
	}

	public String getPointDeVentetId() {
		return pointDeVentetId;
	}

	public void setPointDeVentetId(String pointDeVentetId) {
		this.pointDeVentetId = pointDeVentetId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSituationmat() {
		return situationmat;
	}

	public void setSituationmat(String situationmat) {
		this.situationmat = situationmat;
	}

	public String getLieudenaissance() {
		return lieudenaissance;
	}

	public void setLieudenaissance(String lieudenaissance) {
		this.lieudenaissance = lieudenaissance;
	}

	public CoupleDTO getEntiteJuridiqueDTO() {
		return entiteJuridiqueDTO;
	}

	public Date getDatedenaissance() {
		return datedenaissance;
	}

	public void setDatedenaissance(Date datedenaissance) {
		this.datedenaissance = datedenaissance;
	}

	public void setEntiteJuridiqueDTO(CoupleDTO entiteJuridiqueDTO) {
		this.entiteJuridiqueDTO = entiteJuridiqueDTO;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getStrDatedenaissance() {
		return strDatedenaissance;
	}

	public void setStrDatedenaissance(String strDatedenaissance) {
		this.strDatedenaissance = strDatedenaissance;
	}

	public String getStrDatedenaissance2() {
		return strDatedenaissance2;
	}

	public void setStrDatedenaissance2(String strDatedenaissance2) {
		this.strDatedenaissance2 = strDatedenaissance2;
	}

	public String getFichierChangePassword() {
		return fichierChangePassword;
	}


	public String getPasswordClair() {
		return passwordClair;
	}

	public void setPasswordClair(String passwordClair) {
		this.passwordClair = passwordClair;
	}

	public boolean isFichierChangePasswordExiste() {
		return fichierChangePasswordExiste;
	}

	public void setFichierChangePasswordExiste(boolean fichierChangePasswordExiste) {
		this.fichierChangePasswordExiste = fichierChangePasswordExiste;
	}




	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getThemeChoisi() {
		return themeChoisi;
	}

	public void setThemeChoisi(String themeChoisi) {
		this.themeChoisi = themeChoisi;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getTel() {
		return tel;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateDerniereConnexion() {
		return dateDerniereConnexion;
	}

	public void setDateDerniereConnexion(Date dateDerniereConnexion) {
		this.dateDerniereConnexion = dateDerniereConnexion;
	}

	public int getNbConnexionReussi() {
		return nbConnexionReussi;
	}

	public void setNbConnexionReussi(int nbConnexionReussi) {
		this.nbConnexionReussi = nbConnexionReussi;
	}

	public Date getDateDerniereModificationMDP() {
		return dateDerniereModificationMDP;
	}

	public void setDateDerniereModificationMDP(Date dateDerniereModificationMDP) {
		this.dateDerniereModificationMDP = dateDerniereModificationMDP;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getStatutUser() {
		return statutUser;
	}

	public void setStatutUser(String statutUser) {
		this.statutUser = statutUser;
	}

	public String getStatusUserIcon() {
		return statusUserIcon;
	}

	public void setStatusUserIcon(String statusUserIcon) {
		this.statusUserIcon = statusUserIcon;
	}

	public List<CoupleDTO> getListAffectationsPV() {
		return listAffectationsPV;
	}

	public void setListAffectationsPV(List<CoupleDTO> listAffectationsPV) {
		this.listAffectationsPV = listAffectationsPV;
	}

	public String getStatusUserIconColor() {
		return statusUserIconColor;
	}

	public void setStatusUserIconColor(String statusUserIconColor) {
		this.statusUserIconColor = statusUserIconColor;
	}

	/*
	 * public NumeroReferentiel getCode_genere() { return Code_genere; }
	 * 
	 * public void setCode_genere(NumeroReferentiel code_genere) { Code_genere =
	 * code_genere; }
	 */

	/*
	 * public List<EntiteJuridiqueDTO> getentiteJuridiqueDTOs() { return
	 * entiteJuridiqueDTOs; }
	 * 
	 * public void setEntiteJuridiqueDTOs(List<EntiteJuridiqueDTO>
	 * EntiteJuridiqueDTOs, List<EntiteJuridiqueDTO> entiteJuridiqueDTOs) {
	 * this.entiteJuridiqueDTOs = entiteJuridiqueDTOs; }
	 */

}
