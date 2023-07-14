package sn.awi.pgca.web.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import sn.awi.pgca.business.exception.ConnectionException;
import sn.awi.pgca.business.service.IConnectionService;
import sn.awi.pgca.web.dto.ConnectionDTO;

@ManagedBean(name = "authtentificationMB")
@SessionScoped
public class AuthentificationManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6938659451535735261L;

	@ManagedProperty(value = "#{connectionService}")
	private IConnectionService connectionService;

	// Data Transfert Object
	private ConnectionDTO connectionDTO;

	public AuthentificationManagedBean() {
	}

	// Connexion

	public String login() {
		String outcome = "";
		boolean b = true;
		try {
			connectionService.validate(connectionDTO);
		} catch (ConnectionException ce) {
			b = false;
		}
		if (b) {
			outcome = "/pages/creerUtilisateur?faces-redirect=true";
		} else {
			outcome = "/pages/listeUtilisateur?faces-redirect=true";
		}
		return outcome;
	}

	// Deconnexion
	/*
	 * public String logout() { ExternalContext ec =
	 * FacesContext.getCurrentInstance().getExternalContext();
	 * 
	 * HttpServletRequest request = (HttpServletRequest) ec.getRequest();
	 * request.getSession(false).invalidate();
	 * 
	 * return "/pages/authentification.xhtml?faces-redirect=true"; }
	 */

	public IConnectionService getConnectionService() {
		return connectionService;
	}

	public void setConnectionService(IConnectionService connectionService) {
		this.connectionService = connectionService;
	}

	public ConnectionDTO getConnectionDTO() {
		if (connectionDTO == null)
			connectionDTO = new ConnectionDTO();
		return connectionDTO;
	}

	public void setConnectionDTO(ConnectionDTO connectionDTO) {
		this.connectionDTO = connectionDTO;
	}

}
