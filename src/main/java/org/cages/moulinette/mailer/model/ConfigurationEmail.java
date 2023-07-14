package org.cages.moulinette.mailer.model;

public class ConfigurationEmail {

	private final String identifiant;
	private final String motDePasse;
	private final String host;
	private final String port;
	private final String auth;
	private final String enableStarttls;
	private AdresseEmail expediteur;
	private AdresseEmail replyTo;

	public ConfigurationEmail(String identifiant, String motDePasse, String host, String port, String auth,
			String enableStarttls) {
		this.identifiant = identifiant;
		this.motDePasse = motDePasse;
		this.host = host;
		this.port = port;
		this.auth = auth;
		this.enableStarttls = enableStarttls;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String getAuth() {
		return auth;
	}

	public String getEnableStarttls() {
		return enableStarttls;
	}

	public AdresseEmail getExpediteur() {
		return expediteur;
	}

	public void setExpediteur(AdresseEmail expediteur) {
		this.expediteur = expediteur;
	}

	public AdresseEmail getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(AdresseEmail replyTo) {
		this.replyTo = replyTo;
	}

}
