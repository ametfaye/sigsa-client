package org.cages.moulinette.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="adrs_adresse")
@Setter
@Getter
@NoArgsConstructor
public class Adresse  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="adrs_id")
	private Long id;

	@Column(length=10)
	private String codepostal;

	@Column(length=200)
	private String libelle;

	@Column(length=50)
	private String quartier;
	
	@Column(length=50)
	private String ville;

	@ManyToOne
	@JoinColumn(name = "regn_id", nullable = true) 
	private Region region;
	
	@ManyToOne
	@JoinColumn(name = "departement_id", nullable = true)
	private Departement departement;
	
	@ManyToOne
	@JoinColumn(name = "commune_id", nullable = true)
	private Commune commune;
	
}
