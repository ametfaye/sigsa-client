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
@Table(name = "regn_region")
@Setter
@Getter
@NoArgsConstructor
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "regn_id")
	private Long id;

	@Column(length = 100)
	private String libelle;

	@ManyToOne
	@JoinColumn(name = "pays_id", nullable = false)
	private Pays pays;

	@Column(length = 100)
	private String code_region;
	
	@Column(length = 100)
	private String longitude;
	
	@Column(length = 100)
	private String latitude;

}
