package sn.awi.pgca.business.exception;


public class BudgetException extends Exception {


	private static final long serialVersionUID = -6329378015888265247L;


    public BudgetException(String msg) {
        super(msg);
    }


    public BudgetException(Throwable cause) {
        super(cause);
    }


    public BudgetException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

