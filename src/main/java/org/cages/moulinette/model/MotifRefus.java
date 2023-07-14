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
@Table(name = "motif_refus")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MotifRefus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long mfId;

	@Column
	private String code;

	@Column
	private String libelle;

	public MotifRefus(String code, String libelle) {
		super();
		this.code = code;
		this.libelle = libelle;
	}

}
