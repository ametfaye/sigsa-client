package org.cages.moulinette.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SousServiceDTO {

	private Long ssrvId;
	private String code;
	private String nom;
	
	// Sous service de rattachement s'il y'en a
	private String srvId;
	private String srvNom;

}
