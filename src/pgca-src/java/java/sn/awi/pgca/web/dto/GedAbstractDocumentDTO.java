package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import sn.awi.pgca.utils.MimeType;

/**
 * DTO abstrait pour les documents de la GED.
 * Ceux-ci peuvent être soit entrant, soit sortant.
 *
 * @author awaconsulting
 */
public class GedAbstractDocumentDTO implements Serializable {
	/**
	 * Generated Serial Version UID.
	 */
	private static final long	serialVersionUID	= 2194337170762992362L;

	/**
	 * Numéro fonctionnel du document qui est la référence
	 * à utiliser dans les autres entités.
	 */
	private String numeroFonctionnel;

	/**
	 * Le type de document.
	 */
	private GedTypeDocumentDTO typeDocument;

	/**
	 * Nom du fichier tel qu'il doit apparaître pour l'utiliseur.
	 * Il s'agit du nom fonctionnel.
	 */
	private String nomFichier;

	/**
	 * MimeType identifié pour ce document.
	 */
	private MimeType mimeType;

	/**
	 * Date de création du document dans la GED.
	 */
	private Date dateCreation;
	
	/**
	 * Métadonnées relatives au document.
	 */
	private Set<GedMetaDonneeDTO> metadonnees = new HashSet<GedMetaDonneeDTO>();

	/**
	 * Flag indiquant si le fichier est directement disponible dans
	 * l'entité ou non.
	 * Si loaded est à true alors flux est alimenté.
	 */
	private boolean loaded;

	/**
	 * Le contenu du fichier.
	 */
	private byte[] flux;
	
	
	/***
	 * Entite juridique ayant éditée le document
	 */
	private String entiteJuridique;
	
	
	/***
	 * nom de société a qui appartient le document
	 */
	private String nomSociete;
	
	
	/***
	 * Path abolue du document dans le fileSystem 
	 * Selon son type le document est enregistré dans fileQuittance, FileFormulaire ou ...
	 */
	private String repertoireAbsoluDudocument;
	
	/*
	 * Auteur du document 
	 * 
	 */
	private String auteurDocument;
	
	/*
	 * Pays oû le document a été édité
	 */
	private String paysDocument;
	
	
	/*
	 * Region du document  oû le document a été édité
	 */
	private String regionDocument;
	
	/**
	 * Code du document
	 * 
	 */
	private String code;

	private String libelle;

	private boolean playable = false;
		
	/**
	 * Constructeur par défaut.
	 */
	protected GedAbstractDocumentDTO() {
		super();
	}

	public String getEntiteJuridique() {
		return entiteJuridique;
	}

	public void setEntiteJuridique(String entiteJuridique) {
		this.entiteJuridique = entiteJuridique;
	}

	public String getNomSociete() {
		return nomSociete;
	}

	public void setNomSociete(String nomSociete) {
		this.nomSociete = nomSociete;
	}

	public String getRepertoireAbsoluDudocument() {
		return repertoireAbsoluDudocument;
	}

	public void setRepertoireAbsoluDudocument(String repertoireAbsoluDudocument) {
		this.repertoireAbsoluDudocument = repertoireAbsoluDudocument;
	}

	public String getAuteurDocument() {
		return auteurDocument;
	}

	public void setAuteurDocument(String auteurDocument) {
		this.auteurDocument = auteurDocument;
	}

	public String getPaysDocument() {
		return paysDocument;
	}

	public void setPaysDocument(String paysDocument) {
		this.paysDocument = paysDocument;
	}

	public String getRegionDocument() {
		return regionDocument;
	}

	public void setRegionDocument(String regionDocument) {
		this.regionDocument = regionDocument;
	}

	public String getNumeroFonctionnel() {
		return numeroFonctionnel;
	}

	public void setNumeroFonctionnel(String numeroFonctionnel) {
		this.numeroFonctionnel = numeroFonctionnel;
	}

	public GedTypeDocumentDTO getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(GedTypeDocumentDTO typeDocument) {
		this.typeDocument = typeDocument;
	}

	public String getNomFichier() {
		return nomFichier;
	}

	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}

	public MimeType getMimeType() {
		return mimeType;
	}

	public void setMimeType(MimeType mimeType) {
		this.mimeType = mimeType;
		if (MimeType.PDF.equals(mimeType))
			playable = true;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<GedMetaDonneeDTO> getMetadonnees() {
		return metadonnees;
	}

	public void setMetadonnees(Set<GedMetaDonneeDTO> metadonnees) {
		this.metadonnees = metadonnees;
	}

	/**
	 * Méthode permettant de récupérer la valeur d'une métadonnée
	 * en fonction de son code.
	 *
	 * @param code		Le code de la métadonnée.
	 * @return Le code de la métadonnée.
	 */
	public String getMetadonnee(String code) {
		if (StringUtils.isNotBlank(code)) {
			for (GedMetaDonneeDTO metadonnee : metadonnees) {
				if (code.equals(metadonnee.getCode())) {
					return metadonnee.getValeur();
				}
			}
		}
		return null;
	}

	/**
	 * Alimentation d'une nouvelle métadonnée. Dans le cas où le
	 * code fourni était déjà présent dans les métadonnées, la valeur
	 * de celle-ci est écrasé par le paramètre valeur.
	 *
	 * @param code			Le code de la métadonnée à alimenter.
	 * @param valeur		La valeur de la métadonnée.
	 * @return L'ancienne valeur si la métadonnée existait ou null si elle n'existait pas.
	 */
	public String putMetadonnee(String code, String valeur) {
		if (StringUtils.isNotBlank(code)) {
			// Si la métadonnée existe déjà on remplace simplement la valeur.
			for (GedMetaDonneeDTO metadonnee : metadonnees) {
				if (code.equals(metadonnee.getCode())) {
					String previousValeur = metadonnee.getValeur();
					metadonnee.setValeur(valeur);
					return previousValeur;
				}
			}
			// Création d'une nouvelle métadonnées.
			GedMetaDonneeDTO metadonnee = new GedMetaDonneeDTO();
			metadonnee.setCode(code);
			metadonnee.setValeur(valeur);
			metadonnees.add(metadonnee);
		}
		return null;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public byte[] getFlux() {
		return flux;
	}

	public void setFlux(byte[] flux) {
		this.flux = flux;
	}

	public boolean isPlayable() {
		return playable;
	}

	public void setPlayable(boolean playable) {
		this.playable = playable;
	}
}
