package org.cages.moulinette.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CoupleDTO {

	private String libelle;

	private String icon;

	private int nombre;

	private Double valeur;

	private Double total;

	private String valeurGenerique;

	private String codeGenerique;

	private Long idGenerique;

	// extra 
	private String departement;
	
	public CoupleDTO(Long code, String valeur) {
		idGenerique = code;
		valeurGenerique = valeur;
	}

}
