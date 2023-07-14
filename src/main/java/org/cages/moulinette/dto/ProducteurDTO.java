package org.cages.moulinette.dto;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProducteurDTO {

	private Long prodId;
	private String numeroProd;
	private String numeroProdCarte; // format carte CB

	private String numNinea;
	private String mobile;

	private String descriptif;
	private String dateImmatriculation;
	private String nom;
	private String prenom;
	private String email;
	private String numeroCNI;
	private String dateNaissance;
	private String sexe;
	private String civilite;
	private String categorie;

	
	// adresse
	private String commune;
	private String departement;
	private String region;
	private String pays;
	private String adresse;

	// bien agrciles
	private String bienType;
	private String bienTitrePropriete;
	private String bienSuperficie;
	private String bienGPSlongitude;
	private String bienGPSlatitude;
	private String bienJustificatif;
	
	private ArrayList<BienAgricoleProducteurDTO> listBiensAgricoles;

}
