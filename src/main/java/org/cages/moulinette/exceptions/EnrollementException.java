package org.cages.moulinette.exceptions;

public class EnrollementException extends Exception {

	private static final long serialVersionUID = 1L;

	public EnrollementException() {
	}

	public EnrollementException(String message) {
		super("Erreur Enrollement : " + message);
	}

}
