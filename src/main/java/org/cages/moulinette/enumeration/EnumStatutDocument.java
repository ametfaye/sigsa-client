package org.cages.moulinette.enumeration;

public enum EnumStatutDocument {
	GENERE(1L,"GENERE", "Document généré"),
	SIGNE_ELEC(2L, "SIGNE_ELEC", "Signé électroniquement"),
	SIGNE_MANU(3L, "SIGNE_MANU", "Signé manuellement");

	private Long id;
	private String code;
	private String libelle;

	EnumStatutDocument(Long id, String code, String libelle) {
		this.id = id;
		this.code = code;
		this.libelle = libelle;
	}

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getLibelle() {
		return libelle;
	}
}
