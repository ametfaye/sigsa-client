package org.cages.moulinette.enumeration;

public enum EnumTypeDocument {

	RAPPORT_DE_MISSION(1L, "RAPPORT_DE_MISSION", "Rapport de mission"),
	ANNEXES_RAPPORT(2L, "ANNEXES_RAPPORT", "Annexes rapport"),
	ODM(3L, "ODM", "Ordre de mission"),
	AST(4L, "AST", "Autorisation de sortie"),
	TDR(5L, "TDR", "Termes de référence");

	private Long id;
	private String code;
	private String libelle;

	EnumTypeDocument(Long id, String code, String libelle) {
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
