package org.cages.moulinette.mailer.model;

public class ContenuEmail {

	private final String objet;
	private final String corps;

	public ContenuEmail(String objet, String corps) {
		this.objet = objet;
		this.corps = corps;
	}

	public String getObjet() {
		return objet;
	}

	public String getCorps() {
		return corps;
	}

	@Override
	public String toString() {
		return "ContenuEmail{" + "objet='" + objet + '\'' + '}';
	}
}
