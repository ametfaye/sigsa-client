package sn.awi.pgca.web.dto;

import java.io.Serializable;


/**
 * DTO pour la connexion de l'utilisateur
 * 
 * @author AWA Consulting
 */
public class ConnectionDTO implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4632300337588605699L;

	
  private long id;
	private String codeUtilisateur;

	private String motDePasse;
	private String newPassword;
	private String confirmPassword;
	private boolean mdpmodifier;
	private String connectedUserTheme;
	private String connectedUserThemeBG;
	private String connectedUserThemeCol;
	private String conntedUserthemeheaderBG;
	private String msgEchecConnection;
	private boolean connection;
	
	// nombre de point de vente affecté !
	private int nbPvaffectes;
	//private String idProfil;

	public String getMotDePasse() {
		return motDePasse;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	public String getCodeUtilisateur() {
		return codeUtilisateur;
	}

	public void setCodeUtilisateur(String codeUtilisateur) {
		this.codeUtilisateur = codeUtilisateur;
	}

	public boolean isConnection() {
		return connection;
	}

	public void setConnection(boolean connection) {
		this.connection = connection;
	}

	/*public String getIdProfil() {
		return idProfil;
	}

	public void setIdProfil(String idProfil) {
		this.idProfil = idProfil;
	}*/
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public boolean isMdpmodifier() {
		return mdpmodifier;
	}

	public String getConnectedUserTheme() {
		return connectedUserTheme;
	}

	public void setConnectedUserTheme(String connectedUserTheme) {
		this.connectedUserTheme = connectedUserTheme;
	}

	public String getConnectedUserThemeBG() {
		return connectedUserThemeBG;
	}

	public void setConnectedUserThemeBG(String connectedUserThemeBG) {
		this.connectedUserThemeBG = connectedUserThemeBG;
	}

	public String getConnectedUserThemeCol() {
		return connectedUserThemeCol;
	}

	public void setConnectedUserThemeCol(String connectedUserThemeCol) {
		this.connectedUserThemeCol = connectedUserThemeCol;
	}

	public String getConntedUserthemeheaderBG() {
		return conntedUserthemeheaderBG;
	}

	public void setConntedUserthemeheaderBG(String conntedUserthemeheaderBG) {
		this.conntedUserthemeheaderBG = conntedUserthemeheaderBG;
	}

	public void setMdpmodifier(boolean mdpmodifier) {
		this.mdpmodifier = mdpmodifier;
	}

	public String getMsgEchecConnection() {
		return msgEchecConnection;
	}

	public int getNbPvaffectes() {
		return nbPvaffectes;
	}

	public void setNbPvaffectes(int nbPvaffectes) {
		this.nbPvaffectes = nbPvaffectes;
	}

	public void setMsgEchecConnection(String msgEchecConnection) {
		this.msgEchecConnection = msgEchecConnection;
	}
}