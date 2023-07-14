package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class MenusousMenusDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Long id;
	
	private String libelle;

	private String code;
	
	private int  menuParentId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getMenuParentId() {
		return menuParentId;
	}

	public void setMenuParentId(int menuSousMenus) {
		this.menuParentId = menuSousMenus;
	}
	
}
