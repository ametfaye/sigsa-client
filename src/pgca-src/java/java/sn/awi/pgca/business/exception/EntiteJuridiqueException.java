package sn.awi.pgca.business.exception;

/**
 * Exception technique de la creation des entites juridique
 * @author AWA Consulting
 */
public class EntiteJuridiqueException extends Exception {
    /**
     * UID de version pour la sérialisation.
     */



	/**
	 * 
	 */
	private static final long serialVersionUID = -2705633567581510088L;

	/**
     * Constructeur par défaut.
     */
    public EntiteJuridiqueException() {
        super();
    }

    /**
     * Constructeur é partir d'un message descriptif.
     *
     * @param msg           Message décrivant l'erreur.
     */
    public EntiteJuridiqueException(String msg) {
        super(msg);
    }

    /**
     * Constructeur é partir d'une cause.
     *
     * @param cause         Cause é l'origine de l'erreur.
     */
    public EntiteJuridiqueException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructeur é partir d'un message descriptif et d'une cause.
     *
     * @param msg           Message décrivant l'erreur.
     * @param cause         Cause é l'origine de l'erreur.
     */
    public EntiteJuridiqueException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

