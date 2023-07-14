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
@Table(name = "type_evenement")
@Setter
@Getter
@NoArgsConstructor
public class TypeEvenement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tev_id")
	private Long id;

	@Column(length = 200)
	private String libelle;

	@Column(length = 100)
	private String codeTypeEvenement; 

}
