package sn.awi.pgca.dataModel;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_utilisateur")
public class Utilisateur implements Serializable, GenericModel {
	
	

	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="user_id")
	private Long id;

	@Column(length=40)
	private String codeutilisateur;
	
	@Column(length=100)
	private String motdepasse;

	
	private boolean est_valide;

	private boolean mdpamodifier;
	
	private String motdepasse_clair;
	
	private String themeChoisi;

	private String email;

	
	private Timestamp dateCreation;
	
	private Timestamp dateDerniereConnexion;
	
	private Timestamp dateDerniereModificationMDP;

	@Column(name = "nb_connexion")
	private Integer nombredeConnexion;
	
	
	@ManyToOne
	@JoinColumn(name = "pdc_id", nullable = true)
	private PointDeCollecte pointdeCollecte;
	

	/// uniquement pour les points de vente temporaire (en V2 on aura des affectations avec plusieur commune)
	@OneToOne
	@JoinColumn(name = "commune_id", nullable = true)
	private Commune communeCordinateur;
	
	
	@OneToOne
	@JoinColumn(name = "ptv_id", nullable = true)
	private PointDeVente pointdeVente;
	
	@OneToOne
	@JoinColumn(name = "idTheme", nullable = true)
	private UtilisateurTheme theme;
	
	
	@ManyToOne
	@JoinColumn(name = "prfl_id", nullable = true)
	private Profil profil;
	
	@OneToOne
	@JoinColumn(name = "pers_id", nullable = true)
	private Personne personne;
	
	
	public String getCodeutilisateur() {
		return codeutilisateur;
	}

	public void setCodeutilisateur(String codeUtilisateur) {
		this.codeutilisateur = codeUtilisateur;
	}

	public String getMotdepasse() {
		return motdepasse;
	}

	public void setMotdepasse(String motdepasse) {
		this.motdepasse = motdepasse;
	}

	public boolean isEst_valide() {
		return est_valide;
	}

	public void setEst_valide(boolean est_valide) {
		this.est_valide = est_valide;
	}

	

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public UtilisateurTheme getTheme() {
		return theme;
	}

	public void setTheme(UtilisateurTheme theme) {
		this.theme = theme;
	}

	public Profil getProfil() {
		return profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}

	public boolean isMdpamodifier() {
		return mdpamodifier;
	}

	public void setMdpamodifier(boolean mdpaModifier) {
		this.mdpamodifier = mdpaModifier;
	}

	public String getMotdepasse_clair() {
		return motdepasse_clair;
	}

	public void setMotdepasse_clair(String motdepasse_clair) {
		this.motdepasse_clair = motdepasse_clair;
	}

	public PointDeVente getPointdeVente() {
		return pointdeVente;
	}

	public void setPointdeVente(PointDeVente pointdeVente) {
		this.pointdeVente = pointdeVente;
	}

	public PointDeCollecte getPointdeCollecte() {
		return pointdeCollecte;
	}

	public void setPointdeCollecte(PointDeCollecte pointdeCollecte) {
		this.pointdeCollecte = pointdeCollecte;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getThemeChoisi() {
		return themeChoisi;
	}

	public Commune getCommuneCordinateur() {
		return communeCordinateur;
	}

	public void setCommuneCordinateur(Commune communeCordinateur) {
		this.communeCordinateur = communeCordinateur;
	}

	public Timestamp getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Timestamp dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Timestamp getDateDerniereConnexion() {
		return dateDerniereConnexion;
	}

	public void setDateDerniereConnexion(Timestamp timestamp) {
		this.dateDerniereConnexion = timestamp;
	}

	
	public void setThemeChoisi(String themeChoisi) {
		this.themeChoisi = themeChoisi;
	}

	public Timestamp getDateDerniereModificationMDP() {
		return dateDerniereModificationMDP;
	}

	public int getNombredeConnexion() {
		return nombredeConnexion;
	}

	public void setNombredeConnexion(Integer nombredeConnexion) {
		this.nombredeConnexion = nombredeConnexion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDateDerniereModificationMDP(Timestamp dateDerniereModificationMDP) {
		this.dateDerniereModificationMDP = dateDerniereModificationMDP;
	}
	
}
