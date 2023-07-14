package org.cages.moulinette.mailer.model;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class AdresseEmail {
	private String adresse;

	private String raisonPourLaquelleLadresseEstInvalide;

	public AdresseEmail(String adresse) {
		this.adresse = adresse;
	}

	public String getAdresse() {
		return adresse;
	}

	@Override
	public String toString() {
		return "AdresseEmail{" + "adresse='" + adresse + '\'' + '}';
	}

	public boolean siValide() {
		if (adresse == null) {
			return false;
		}
		try {
			InternetAddress internetAddress = new InternetAddress(adresse);
			internetAddress.validate();
		} catch (AddressException e) {
			raisonPourLaquelleLadresseEstInvalide = e.getMessage();
			return false;
		}

		return true;
	}

	public String getRaisonPourLaquelleLadresseEstInvalide() {
		return raisonPourLaquelleLadresseEstInvalide;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
}
