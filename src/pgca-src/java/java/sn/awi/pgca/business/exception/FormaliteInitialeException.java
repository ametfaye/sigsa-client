package sn.awi.pgca.business.exception;

/**
 * Exception technique lors de l'immatriculation personne morale ou gie.
 *
 * @author AWA Consulting
 */
public class FormaliteInitialeException extends Exception {
    /**
     * UID de version pour la sérialisation.
     */

	private static final long serialVersionUID = -6329378015888265247L;

	/**
     * Constructeur par défaut.
     */
    public FormaliteInitialeException() {
        super();
    }

    /**
     * Constructeur é partir d'un message descriptif.
     *
     * @param msg           Message décrivant l'erreur.
     */
    public FormaliteInitialeException(String msg) {
        super(msg);
    }

    /**
     * Constructeur é partir d'une cause.
     *
     * @param cause         Cause é l'origine de l'erreur.
     */
    public FormaliteInitialeException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructeur é partir d'un message descriptif et d'une cause.
     *
     * @param msg           Message décrivant l'erreur.
     * @param cause         Cause é l'origine de l'erreur.
     */
    public FormaliteInitialeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

