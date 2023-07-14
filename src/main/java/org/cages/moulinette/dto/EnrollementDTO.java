package org.cages.moulinette.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollementDTO {

	PersonnePhysiqueDTO beneficiaireEnrollement = new PersonnePhysiqueDTO();

	private Long id_ca;
	private String tel;
	private String mail;
	private String libelle;
	private String ninea;
	
	
	// Adresse benefiaciaire
	private Long idRegion;
	private Long idDepartement;
	private Long idCommune;
	private String adresse;

	//Justif
	private String typePiece;
	private String justiftypePiecebase64;
	private String urltypePiece;

	
	//Bien
	private String categorie;
	private Float superficieChamp;
	private Float longitudeChamp;
	private Float latitudeChamp;
	private String typeTitre;
	private String justiftitrecebase64;
	private String urltitre;


}
