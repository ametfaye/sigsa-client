package org.cages.moulinette.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categorie_intrant")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategorieIntrant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "categorie_intrant_id")
	private Long id;

	@Column
	private String libelle;

	@Column
	private String code;
	
	@Column
	private String picto;
	
	@ManyToOne
	@JoinColumn(name = "type_intrant_id", nullable = true)
	private TypeIntrant type;
	

	@Column(length = 4000)
	private String pictoCategorieIntrant; // Photo illustrant le type

	public CategorieIntrant(String libelle, String code, String pictoCategorieIntrant) {
		super();
		this.libelle = libelle;
		this.code = code;
		this.pictoCategorieIntrant = pictoCategorieIntrant;
	}

}
