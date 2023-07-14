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
@Table(name = "type_intrant")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TypeIntrant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "typeIntrantId")
	private Long id;

	@Column
	private String libelle;

	@Column
	private String code;

	@Column(length = 4000)
	private String picto; // Photo TYPE

	public TypeIntrant(String libelle, String code, String pictoCategorieIntrant) {
		super();
		this.libelle = libelle;
		this.code = code;
		this.picto = pictoCategorieIntrant;
	}

}
