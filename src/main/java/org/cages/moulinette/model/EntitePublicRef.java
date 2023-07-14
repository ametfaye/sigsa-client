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
@Table(name = "ref_entite")
@Setter
@Getter
@NoArgsConstructor
public class EntitePublicRef {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ref_id")
	private Long redId;

	@Column(length = 10)
	private String code;

	@Column(length = 200)
	private String libelle;

	public EntitePublicRef(String code, String libelle) {
		super();
		this.code = code;
		this.libelle = libelle;
	}

}
