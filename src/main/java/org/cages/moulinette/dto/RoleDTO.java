package org.cages.moulinette.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

	private int id;

	private String code;

	private String libelle;

	private String description;

	private boolean selected;

	public RoleDTO(int id, String code, String libelle, String description) {
		super();
		this.id = id;
		this.code = code;
		this.libelle = libelle;
		this.description = description;
	}

}
