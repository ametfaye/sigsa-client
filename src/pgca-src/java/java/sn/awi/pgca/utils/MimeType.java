package sn.awi.pgca.utils;


/**
 * Enumération des MimeTypes gérés par l'application pour les
 * documents présent dans RCCM.
 *
 * @author awaconsulting
 */
public enum MimeType {
	/**
	 * Fichier CSV.
	 */
	CSV(".csv", "text/csv"),
	/**
	 * Fichier GIF.
	 */
	GIF(".gif", "image/gif"),
	/**
	 * Fichier JPEG.
	 */
	JPEG(".jpeg", "image/jpeg"),
	/**
	 * Fichier PDF.
	 */
	PDF(".pdf", "application/pdf"),
	/**
	 * Fichier PNG.
	 */
	PNG(".png", "image/png"),
	/**
	 * Fichier de type inconnu.
	 */
	UNKNOWN(null, "application/octet-stream"),
	/**
	 * Fichier ZIP
	 */
	ZIP(".zip", "application/zip");

	/**
	 * Extension du fichier pour simplifier l'identification.
	 */
	private String extension;

	/**
	 * Chaine de mimetype à renvoyer au navigateur
	 * pour l'interprétation correcte du type de
	 * document.
	 */
	private String mimetype;

	/**
	 * Constructeur par défaut prenant l'extension et le mimetype
	 * textuel associé.
	 *
	 * @param extension		Le type d'extension pour le fichier.
	 * @param mimetype		Le type de mimetype à renvoyer au navigateur.
	 */
	private MimeType(String extension, String mimetype) {
		this.extension = extension;
		this.mimetype = mimetype;
	}

	/**
	 * Cette méthode calcule le mimetype le plus cohérent possible
	 * par rapport au nom de fichier qui a été fourni.
	 *
	 * @param filename		Le nom du fichier pour lequel on souhaite calculer le mimetype.
	 * @return Le MimeType déduit à partir du nom de fichier.
	 */
	public static MimeType getMimeTypeByFilename(String filename) {
		// Précondition pour s'assurer qu'il y a bien un nom de fichier.
		if (!UtilString.isCorrect(filename)) {
			return UNKNOWN;
		}
		
		for (MimeType t : values()) {
			if (UtilString.isCorrect(t.getExtension()) && filename.toLowerCase().endsWith(t.getExtension())) {
				return t;
			}
		}
		// A défaut si on n'a rien trouvé, on renvoit le type inconnu.
		return UNKNOWN;
	}

	public String getExtension() {
		return extension;
	}

	public String getMimetype() {
		return mimetype;
	}
}
