package sn.awi.pgca.web.dto;

public class TypeSocieteDTO {

	public TypeSocieteDTO() {
	}

	private Long id;
	
	private String libelle;

	private String code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String toString() {
		String s = "TypeSocieteDTO code : %s, libellé : %s";
		return String.format(s, code, libelle);
	}
}
