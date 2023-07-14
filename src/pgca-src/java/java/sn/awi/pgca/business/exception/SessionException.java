
package sn.awi.pgca.business.exception;


public class SessionException extends RuntimeException 
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 6960128027058770430L;

	public SessionException()
    {
        super("Session expirée");
    }
}
