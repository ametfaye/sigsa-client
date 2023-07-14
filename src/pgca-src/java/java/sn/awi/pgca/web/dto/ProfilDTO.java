package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.List;

public class ProfilDTO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7838560630950854563L;

	private Long							id;
	private String						code;
	private String						libelle;
	private String						nbUtilisateurs;

	private Long							codeEj;

	private List<String>			actionDTOs;

	private List<String>			menusousmenusDTOs;

	// private Map<String,ActionDTO> mapActions = new HashMap<String,
	// ActionDTO>();

	public List<String> getMenusousmenusDTOs() {
		return menusousmenusDTOs;
	}

	public void setMenusousmenusDTOs(List<String> menusousmenusDTOs) {
		this.menusousmenusDTOs = menusousmenusDTOs;
	}

	public ProfilDTO() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNbUtilisateurs() {
		return nbUtilisateurs;
	}

	public void setNbUtilisateurs(String nbUtilisateurs) {
		this.nbUtilisateurs = nbUtilisateurs;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Long getId() {
		return id;
	}

	public Long getCodeEj() {
		return codeEj;
	}

	public void setCodeEj(Long codeEj) {
		this.codeEj = codeEj;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getActionDTOs() {
		return actionDTOs;
	}

	public void setActionDTOs(List<String> actionDTOs) {
		this.actionDTOs = actionDTOs;
	}

}
