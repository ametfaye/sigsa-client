package org.cages.moulinette.mailer.model;

public class PieceJointe {

	private final String cheminAbsoluVersLeFichier;

	public PieceJointe(String nomCompletDuFichier) {
		this.cheminAbsoluVersLeFichier = nomCompletDuFichier;
	}

	public String getCheminAbsoluVersLeFichier() {
		return cheminAbsoluVersLeFichier;
	}
}
