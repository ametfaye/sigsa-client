package org.cages.moulinette.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "intrant")
@Setter
@Getter
@NoArgsConstructor
public class Intrant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "intrant_id")
	private Long id;

	@Column
	private String libelle;

	@Column
	private Double quantite;

	@Column
	private Double quantiteInitial;

	@Column
	private Double valeurAcquisition; // prix dachat

	@Column(length = 4000)
	private String pictoIntrant; // Photo illustrant l'intrant

	@OneToOne
	@JoinColumn(name = "tarif_id", nullable = true)
	private Tarificateur tarif;

	@OneToOne
	@JoinColumn(name = "categorie_intrant_id", nullable = true)
	private CategorieIntrant categorieIntrant;

	@OneToOne
	@JoinColumn(name = "unite_mesure_id", nullable = true)
	private UniteDeMesure uniteDeMesure;

	public Intrant(String libelle, Double quantite, Double quantiteInitial, Double valeurAcquisition,
			String pictoIntrant, Tarificateur tarif) {
		super();
		this.libelle = libelle;
		this.quantite = quantite;
		this.quantiteInitial = quantiteInitial;
		this.valeurAcquisition = valeurAcquisition;
		this.pictoIntrant = pictoIntrant;
		this.tarif = tarif;
	}

}
