package org.cages.moulinette.exceptions;


public class EntityPersisitanteException extends Exception {

	private static final long serialVersionUID = 440967930138364876L;

	
	public EntityPersisitanteException() {
		super();
	}

	
	public EntityPersisitanteException(String e) {
		super(e);
	}

	public EntityPersisitanteException(Throwable e) {
		super(e);
	}

	public EntityPersisitanteException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
