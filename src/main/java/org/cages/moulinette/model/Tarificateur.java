package org.cages.moulinette.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tarificateur")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Tarificateur {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tarif_id")
	private Long id;

	@Column(length = 50)
	private String libelleProduit;

	@Column(length = 50)
	private Boolean subventionne;

	@Column
	private Float tauxSubvention; // après deduction taux de subvention

	@Column
	private Float prixProducteur; // après deduction taux de subvention

	@Column
	private Float prixNonSubventionne; // après deduction taux de subvention

	@Column
	private Float totalsubventionEtatArecouvrir; // après deduction taux de subvention

	@Column
	private int quantite; // En kilo, convertit avant l'enregistrement pour determiner le prix au Kilo

	@Column
	private float montantDacquisition;

	@Column
	private float fraisTransport;

	@Column
	private float fraisDouanese;

	@Column
	private float fraisAnnexes;

	@Column
	private float prixdeRevientTotal;

	@Column
	private float remiseAppliquee;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEnVigueur;

	@OneToOne
	@JoinColumn(name = "pers_id", nullable = false)
	private Personne auteurTarification;
}
