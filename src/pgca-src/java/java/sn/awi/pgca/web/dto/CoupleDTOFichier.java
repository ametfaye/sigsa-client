package sn.awi.pgca.web.dto;

import java.io.Serializable;

import org.primefaces.model.UploadedFile;

public class CoupleDTOFichier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6252654728110747843L;

	/**
	 * class dto utilisé pour l'upload des fichiers 
	 * le nom du fichier, le type , l'id  du type
	 */
	private Long id;
	
	private String typeFichier;
	
	private String idTypeFichier;
	
	private UploadedFile fichier;
	
	private String  nomFichier;
	
	private String  numeroFonctionnel;

	public String getTypeFichier() {
		return typeFichier;
	}

	public void setTypeFichier(String typeFichier) {
		this.typeFichier = typeFichier;
	}

	public UploadedFile getFichier() {
		return fichier;
	}

	public void setFichier(UploadedFile fichier) {
		this.fichier = fichier;
	}

	public String getNomFichier() {
		return nomFichier;
	}

	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}

	public String getIdTypeFichier() {
		return idTypeFichier;
	}

	public void setIdTypeFichier(String idTypeFichier) {
		this.idTypeFichier = idTypeFichier;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroFonctionnel() {
		return numeroFonctionnel;
	}

	public void setNumeroFonctionnel(String numeroFonctionnel) {
		this.numeroFonctionnel = numeroFonctionnel;
	}

}
