package sn.awi.pgca.business.exception;

/**
 * Exception technique lors de l'accés é un EntityDB en base de données.
 * 
 * @author AWA Consulting
 */
public class EntityDBDAOException extends Exception {
	/**
	 * Version UID.
	 */
	private static final long serialVersionUID = 440967930138364876L;

	/**
	 * Constructeur par défaut.
	 */
	public EntityDBDAOException() {
		super();
	}

	/**
	 * Constructeur é partir d'un message descriptif.
	 * 
	 * @param msg
	 *            Message décrivant l'erreur.
	 */
	public EntityDBDAOException(String msg) {
		super(msg);
	}

	/**
	 * Constructeur é partir d'une cause.
	 * 
	 * @param cause
	 *            Cause é l'origine de l'erreur.
	 */
	public EntityDBDAOException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructeur é partir d'un message descriptif et d'une cause.
	 * 
	 * @param msg
	 *            Message décrivant l'erreur.
	 * @param cause
	 *            Cause é l'origine de l'erreur.
	 */
	public EntityDBDAOException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
