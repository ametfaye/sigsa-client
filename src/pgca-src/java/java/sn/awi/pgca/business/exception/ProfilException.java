package sn.awi.pgca.business.exception;

/**
 * Exception technique lors de la connexion de l'utilisateur.
 *
 * @author AWA Consulting
 */
public class ProfilException extends Exception {
    /**
     * UID de version pour la s�rialisation.
     */



	/**
	 * 
	 */
	private static final long serialVersionUID = -2705633567581510088L;

    /**
     * Constructeur � partir d'un message descriptif.
     *
     * @param msg           Message d�crivant l'erreur.
     */
    public ProfilException(String msg) {
        super(msg);
    }

    /**
     * Constructeur � partir d'une cause.
     *
     * @param cause         Cause � l'origine de l'erreur.
     */
    public ProfilException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructeur � partir d'un message descriptif et d'une cause.
     *
     * @param msg           Message d�crivant l'erreur.
     * @param cause         Cause � l'origine de l'erreur.
     */
    public ProfilException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

