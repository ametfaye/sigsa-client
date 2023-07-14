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
@Table(name = "pays")
@Setter
@Getter
@NoArgsConstructor
public class Pays {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pays_id")
	private Long id;

	@Column(length = 100)
	private String libelle;

	@Column(length = 10)
	private String code_pays;

	@Column(length = 10)
	private String code_monetaire;

	
	@Column(length = 100)
	private String continent;
}
