package sn.awi.pgca.business.exception;

/**
 * Exception technique lors de l'immatriculation personne morale ou gie.
 *
 * @author AWA Consulting
 */
public class RechercheException extends Exception {
    /**
     * UID de version pour la s�rialisation.
     */

	private static final long serialVersionUID = -6329378015888265247L;

    /**
     * Constructeur � partir d'un message descriptif.
     *
     * @param msg           Message d�crivant l'erreur.
     */
    public RechercheException(String msg) {
        super(msg);
    }

    /**
     * Constructeur � partir d'une cause.
     *
     * @param cause         Cause � l'origine de l'erreur.
     */
    public RechercheException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructeur � partir d'un message descriptif et d'une cause.
     *
     * @param msg           Message d�crivant l'erreur.
     * @param cause         Cause � l'origine de l'erreur.
     */
    public RechercheException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

