package org.cages.moulinette.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PersonnePhysiqueDTO {

	private Long pphId;

	private String prenom;
	private String nom;
	private String email;
	private String sexe;
	private String telephone;
	private String civilite;
	private String numerocni;
	private String dateNaissance;



	public PersonnePhysiqueDTO(Long pphId, String prenom, String nom, String email) {
		super();
		this.pphId = pphId;
		this.prenom = prenom;
		this.nom = nom;
		this.email = email;
	}

	public PersonnePhysiqueDTO(Long pphId, String prenom, String nom) {
		super();
		this.pphId = pphId;
		this.prenom = prenom;
		this.nom = nom;
	}

}
