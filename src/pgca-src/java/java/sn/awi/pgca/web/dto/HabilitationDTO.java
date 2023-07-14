package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class HabilitationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	private String profilId;
	
	private String codeMenu;

	public String getProfilId() {
		return profilId;
	}

	public void setProfilId(String profilId) {
		this.profilId = profilId;
	}

	public String getCodeMenu() {
		return codeMenu;
	}

	public void setCodeMenu(String codeMenu) {
		this.codeMenu = codeMenu;
	}

}
