package org.cages.moulinette.mailer.model;

import java.util.ArrayList;
import java.util.List;

public class MessageEmail {

	private final ContenuEmail contenuEmail;
	private final AdresseEmail destinataire;
	private final Header header;

	private List<PieceJointe> piecesJointes;

	MessageEmail(ContenuEmail contenuEmail, AdresseEmail destinataire, Header header) {
		this.contenuEmail = contenuEmail;
		this.destinataire = destinataire;
		this.header = header;
		piecesJointes = new ArrayList<>();
	}

	public MessageEmail(ContenuEmail contenuEmail, AdresseEmail destinataire) {
		this(contenuEmail, destinataire, new Header());
	}

	public void ajouterPieceJointe(PieceJointe pieceJointe) {
		piecesJointes.add(pieceJointe);
	}

	@Override
	public String toString() {
		return "MessageEmail{" + "contenuEmail=" + contenuEmail + ", destinataire=" + destinataire + ", piecesJointes="
				+ piecesJointes + '}';
	}

	public ContenuEmail getContenuEmail() {
		return contenuEmail;
	}

	public AdresseEmail getDestinataire() {
		return destinataire;
	}

	public Header getHeader() {
		return header;
	}

	public List<PieceJointe> getPiecesJointes() {
		return piecesJointes;
	}
}
