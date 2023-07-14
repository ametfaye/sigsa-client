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
@Table(name = "statut_document")
@Setter
@Getter
@NoArgsConstructor
public class StatutDocument {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "stdoc_id")
	private long stDocId;

	@Column(name = "code")
	private String code;

	@Column(name = "libelle")
	private String libelle;

	public StatutDocument(String code, String libelle) {
		super();
		this.code = code;
		this.libelle = libelle;
	}
	
}