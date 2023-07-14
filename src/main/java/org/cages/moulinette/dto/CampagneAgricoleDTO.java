package org.cages.moulinette.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CampagneAgricoleDTO {

	private Long id_ca;
	private String presentation;
	private String presentationSansHtml;
	private String dateOuverture;
	private String dateFermeture;
	private String status;
	private String campagnePeriode;


}
