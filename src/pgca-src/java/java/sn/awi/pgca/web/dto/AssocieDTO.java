package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class AssocieDTO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6939524780255140201L;

	private Long							idAyantDroit;

	private PersonneDTO				personne;

	private String						fonctionId;

	private String						fonctionLibelle;

	public AssocieDTO() {
		personne = new PersonneDTO();
	}

	public Long getIdAyantDroit() {
		return idAyantDroit;
	}

	public void setIdAyantDroit(Long idAyantDroit) {
		this.idAyantDroit = idAyantDroit;
	}

	public PersonneDTO getPersonne() {
		return personne;
	}

	public void setPersonne(PersonneDTO personne) {
		this.personne = personne;
	}

	public String getFonctionId() {
		return fonctionId;
	}

	public void setFonctionId(String fonctionId) {
		this.fonctionId = fonctionId;
	}

	public String getFonctionLibelle() {
		return fonctionLibelle;
	}

	public void setFonctionLibelle(String fonctionLibelle) {
		this.fonctionLibelle = fonctionLibelle;
	}

	// public Long getId() {
	// return id;
	// }
	//
	// public void setId(Long id) {
	// this.id = id;
	// }

}
