package org.cages.moulinette.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserInfosDTO {

	private Long idUser;
	private String email;
	private String identifiant;
	private String password;
	private String prenom;
	private String nom;
	private int active;
	private String infosServices;
	private String fonction;
	private Long posteConcerne;
	private Double postePlafond;
	private Double monstantRestantPlafond;
	private String posteConcerneLibelle;
	private String service;
	private String sousService;
	private String entitePublique;
	private Long idProfil;
	private Long idService;
	private Long idSousService;
	private String profil;
	private String statut;
	private String derniereDateConnexion;
	private String telephone;
	private String classCss;

	private List<RoleDTO> roles;

}
