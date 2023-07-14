package org.cages.moulinette.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BienAgricoleProducteur")
@Setter
@Getter
@NoArgsConstructor
public class BienAgricoleProducteur {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ba_id;

	@Column
	private String categorieBien;

	@Column
	private String typeDePropriete;
	
	@Column
	private String justificatifPropriete;


	@Column
	private Float superficieBien;
	
	@Column(name = "gpsLatitude")
	private String gpsLatitute;
	
	@Column(name = "gpsLongitude")
	private String gpsLongitude;

	@OneToOne
	@JoinColumn(name = "adrs_id", nullable = false)
	private Adresse adresse;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "prod_id", nullable = false)
	private Producteur proprietaire;
	
}
