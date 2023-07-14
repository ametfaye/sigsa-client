package sn.awi.pgca.business.exception;

/**
 * Exception technique lors de l'ouverture , la  fermuture ou l'accès aux campagnes Agricoles
 *
 * @author MF
 */
public class ProgrammeException extends Exception {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProgrammeException(String msg) {
        super(msg);
    }
    public ProgrammeException(Throwable cause) {
        super(cause);
    }
    public ProgrammeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

