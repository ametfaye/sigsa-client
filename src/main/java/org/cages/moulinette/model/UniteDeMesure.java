package org.cages.moulinette.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "unite_de_mesure")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UniteDeMesure {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "unite_mesure_id")
	private Long id;

	@Column
	private String libelle;

	@Column
	private String code;

	public UniteDeMesure(String libelle, String code) {
		super();
		this.libelle = libelle;
		this.code = code;
	}

}
