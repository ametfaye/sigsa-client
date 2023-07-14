package org.cages.moulinette.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentOdmAstDTO {

	private long id;
	private String matricule;
	private String nom;
	private String prenom;
	private String path;
	private String statut;

}
