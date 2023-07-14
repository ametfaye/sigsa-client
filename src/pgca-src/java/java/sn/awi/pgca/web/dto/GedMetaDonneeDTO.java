package sn.awi.pgca.web.dto;

import java.io.Serializable;

/**
 * DTO représentant une métadonnée caractérisant
 * le document à insérer ou récupérer de la GED.
 * Les constantes sont à utiliser pour les métadonnées
 * usuelles.
 * D'autres métadonnées peuvent être ajoutées à titre informatif.
 *
 * @author awaconsulting
 */
public class GedMetaDonneeDTO implements Serializable {
	/**
	 * Code du pays.
	 */
	public static final String CODE_PAYS = "CODE_PAYS";
	/**
	 * Code de la région.
	 */
	public static final String CODE_REGION = "CODE_REGION";
	/**
	 * Code de l'entité juridique.
	 */
	public static final String CODE_ENTITE_JURIDIQUE = "CODE_ENTITE_JURIDIQUE";
	/**
	 * Numéro RCCM.
	 */
	public static final String NUMERO_RCCM = "NUM_RCCM";

	/**
	 * Generated Serial Version UID.
	 */
	private static final long	serialVersionUID	= -5756559358085147760L;

	/**
	 * Identifiant technique.
	 */
	private Long id;
	
	/**
	 * Code de référence de la métadonnée
	 */
	private String code;

	/**
	 * Valeur de la métadonnée.
	 */
	private String valeur;
	


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

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
}
