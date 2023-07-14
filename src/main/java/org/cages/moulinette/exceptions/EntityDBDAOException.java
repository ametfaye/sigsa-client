package org.cages.moulinette.exceptions;


public class EntityDBDAOException extends Exception {

	private static final long serialVersionUID = 440967930138364876L;

	
	public EntityDBDAOException() {
		super();
	}

	
	public EntityDBDAOException(String e) {
		super(e);
	}

	public EntityDBDAOException(Throwable e) {
		super(e);
	}

	public EntityDBDAOException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
