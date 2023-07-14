package org.cages.moulinette.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class QuotaMiseEnplaceDTO {
	
	private Long id;
	private String libelle;
	private Double Quota;
	
	private Long idIntant;
	private String libelleIntant;
	private String pictoIntrantBase64;

	private Long libelleeIntant;
	private String categorieIntant;
	private String pictoCategorieIntrantBase64;

	private Long idpointdeVnte;
	private Long libellePointDeVente;



	private Double quantiteMiseEnplace;
	private Double quantiteAMettreEnplace;

	
}
