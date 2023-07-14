package org.cages.moulinette.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BienAgricoleProducteurDTO {


	private Long ba_id;
	private String categorieBien;
	private String typeDePropriete;
	private String justificatifPropriete;
	private Float superficieBien;
	private String gpsLatitute;
	private String gpsLongitude;

	
	// adresse
	private String commune;
	private String departement;
	private String region;
	private String pays;
	private String adresse;
	
	private Long prodId;
	private String numeroProd;
	private String numNinea;
	private String mobile;
	
}
