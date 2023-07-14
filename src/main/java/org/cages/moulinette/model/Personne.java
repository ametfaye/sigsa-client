package org.cages.moulinette.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "personne")
public class Personne {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pers_id")
	private Long id;

	@Column(length = 50)
	private String nom;

	@Column(length = 50)
	private String prenom;

	@Temporal(TemporalType.DATE)
	private Date datenaissance;

	@Column(length = 50)
	private String lieudenaissance;

	@Column(length = 1)
	private String situationmat; // C : Célibataire M : Marié D : Divorcé V : Veuf

	@Column(length = 50)
	private String nationalite;

	@Column
	private String numeroCNI;

	private char civilite; // 1 : Mr 2 : Mme 3 : Mlle

	@Column(name = "fonction") // 1 : Agent ? : Chauffeur : 5 :
	private Long fonction;

	@OneToOne
	@JoinColumn(name = "adresse_id", nullable = true)
	private Adresse adresse;

	@OneToOne
	@JoinColumn(name = "contact_id", nullable = true)
	private Contact contact;
}
