package org.cages.moulinette.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PersonnePhysique")
@Setter
@Getter
@NoArgsConstructor
public class PersonnePhysique {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pphId;

	@Column(name = "prenom")
	@NotEmpty(message = "Merci de saisir votre prénom")
	private String prenom;

	@Column(name = "nom")
	@NotEmpty(message = "Merci de saisir votre nom")
	private String nom;

	@Column(name = "email")
	private String email;

	@Column(name = "telephone")
	private String telephone;
	
	@Column(name = "dateNaissance")
	private String dateNaissaance;
	
	@Column(name = "numCNI")
	private String numCNI;

	@Column(length = 100)
	private String sexe;
	
	@Column(length = 100)
	private String civilite;

	public PersonnePhysique(String prenom, String nom, String email, String telephone, String sexe) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.email = email;
		this.telephone = telephone;
		this.sexe = sexe;
	}

	
}
