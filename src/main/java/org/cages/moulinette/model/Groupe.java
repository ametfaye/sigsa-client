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
@Table(name = "Groupe")
@Setter
@Getter
@NoArgsConstructor
public class Groupe {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long grId;

	@Column(length = 100)
	private String code;

	@Column(length = 100)
	private String libelle;

}
