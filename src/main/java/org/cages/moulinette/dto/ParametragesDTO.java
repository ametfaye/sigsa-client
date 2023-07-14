package org.cages.moulinette.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ParametragesDTO {

	/** indice de référence **/
	private Double indiceMajore;

	/** Cron de lancement automatiques du traitement */
	private String cronExpression;

	private String serveurFTP;
	private String userFTP;
	private String portFTP;
	private String directoryFTP;

}
