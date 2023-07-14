package org.cages.moulinette.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Index;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Producteur" ,     indexes = {
	       @Index(name = "NUM_PROD_INDX_0", columnList = "numero_prod"),
	       @Index(name = "NUM_NINEA_INDX_0", columnList = "num_ninea") })
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Producteur {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long prodId;

	@Column(name = "numero_prod")
	private String numeroProd;

	@Column(name = "num_ninea")
	private String numNinea;

	@Column(name = "adresse")
	private String adresse;

	@Column(name = "libelle")
	private String libelle;

	@Column(name = "descriptif")
	private String descriptif;

	@Column(name = "email")
	private String email;

	@Column(name = "type_prod")
	private String typeProd;

	@Column(name = "date_immatriculation")
	private java.sql.Date dateImmatriculation;
	
	@Column(name = "date_derniere_modif")
	private java.sql.Date dateDerniereModif;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "producteur_personne", joinColumns = @JoinColumn(name = "prod_id"), inverseJoinColumns = @JoinColumn(name = "pph_id"))
	private Set<PersonnePhysique> personnePhysiques;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<BienAgricoleProducteur> biensAgricoles = new HashSet();
    
	@OneToOne
	@JoinColumn(name = "adrs_id", nullable = true)
	private Adresse adresseComplete;
    
	@OneToOne
	@JoinColumn(name = "cont_id", nullable = true)
 	private Contact contact;

	public Producteur(String numeroProd, String numNinea, String adresse, String libelle, String descriptif,
			String email, String typeProd, Date dateImmatriculation, Set<PersonnePhysique> personnePhysiques) {
		super();
		this.numeroProd = numeroProd;
		this.numNinea = numNinea;
		this.adresse = adresse;
		this.libelle = libelle;
		this.descriptif = descriptif;
		this.email = email;
		this.typeProd = typeProd;
		//this.dateImmatriculation = dateImmatriculation;
		this.personnePhysiques = personnePhysiques;
	}

}
