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
@Table(name = "departement")
@Setter
@Getter
@NoArgsConstructor
public class Departement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "departement_id")
	private Long id;

	@Column(length = 100)
	private String libelle;

	@Column(length = 100)
	private String code;

	@Column(length = 100)
	private String code_dept;

	@ManyToOne
	@JoinColumn(name = "regn_id", nullable = false)
	private Region region;

}
