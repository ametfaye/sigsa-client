package org.cages.moulinette.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "commune")
@Setter
@Getter
@NoArgsConstructor
public class Commune {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "commune_id")
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "libelle")
	private String libelle;

	@Column(name = "description", length = 12000)
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "departement_id", nullable = false)
	private Departement departement;

}
