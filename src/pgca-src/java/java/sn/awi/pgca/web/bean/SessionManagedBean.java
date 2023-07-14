package sn.awi.pgca.web.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "SessionMB")
@SessionScoped
public class SessionManagedBean implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2596445151244695503L;

	private String connedProfilCode;

	private String connectedProfilLabel;

	private String connectedUser;

	private String connectedUserNom;

	private String connectedUserPrenom;

	private String connectedUserPointDeCollecte;

	private String connectedUserPointDeVente;
	

	public SessionManagedBean() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest(); // TODO : Recupérer paramétrage  en base -> load la classe// MessageFrench ou English

		if (request != null) {
			HttpSession session = request.getSession(true);
			 connedProfilCode =   (String) session.getAttribute("profilCode");
		}
		
		connectedUserPointDeCollecte = MessagesFrenchPGCA.unknownPointDeCollecte;
		connectedUserPointDeVente = MessagesFrenchPGCA.unknownPointDeVente;
	}

	public static String getSessionDataByTag(String tag) throws Exception {
		if (tag == null)
			throw new Exception("Error d'acces de session le tag est");
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				if (session.getAttribute(tag) != null) {
					System.out.println("Connected User " + (String) session.getAttribute(tag));
					return (String) session.getAttribute(tag);
				}
			}
		}
		return null;
	}
	
	public static Long getSessionLongValuesDataByTag(String tag) throws Exception {
		if (tag == null)
			throw new Exception("Error d'acces de session le tag est");
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				if (session.getAttribute(tag) != null) {
					//System.out.println("Connected User " + session.getAttribute(tag));
					return (Long) session.getAttribute(tag);
				}
			}
		}
		return null;
	}
	
	
	public String getConnectedProfilLabel() {
		return connectedProfilLabel;
	}

	public void setConnectedProfilLabel(String connectedProfilLabel) {
		this.connectedProfilLabel = connectedProfilLabel;
	}

	public String getConnectedUser() {
		return connectedUser;
	}

	public void setConnectedUser(String connectedUser) {
		this.connectedUser = connectedUser;
	}

	public String getConnectedUserNom() {
		return connectedUserNom;
	}

	public void setConnectedUserNom(String connectedUserNom) {
		this.connectedUserNom = connectedUserNom;
	}

	public String getConnectedUserPrenom() {
		return connectedUserPrenom;
	}

	public void setConnectedUserPrenom(String connectedUserPrenom) {
		this.connectedUserPrenom = connectedUserPrenom;
	}

	public String getConnectedUserPointDeCollecte() {
		return connectedUserPointDeCollecte;
	}

	public void setConnectedUserPointDeCollecte(String connectedUserPointDeCollecte) {
		this.connectedUserPointDeCollecte = connectedUserPointDeCollecte;
	}

	public String getConnectedUserPointDeVente() {
		return connectedUserPointDeVente;
	}



	public void setConnectedUserPointDeVente(String connectedUserPointDeVente) {
		this.connectedUserPointDeVente = connectedUserPointDeVente;
	}


	public String getConnedProfilCode() {
		return connedProfilCode;
	}

	public void setConnedProfilCode(String connedProfilCode) {
		this.connedProfilCode = connedProfilCode;
	}

	

}
