package org.cages.moulinette.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "parametrages")
@Setter
@Getter
@NoArgsConstructor
public class Parametrages {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "params_id")
	private Integer params_id;

	/*
	 * Contexte d'execution : Test INT PROD
	 */

	/** indice de référence **/
	private Double indiceMajore;

	/** Cron de lancement automatiques du traitement */
	private String cronExpression;

	private String serveurFTP;
	private String userFTP;
	private String portFTP;
	private String userFTPPass;
	private String directoryFTP;
	private String contexte;

}
