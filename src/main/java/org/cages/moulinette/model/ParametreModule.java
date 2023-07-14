package org.cages.moulinette.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "parametre_module")
@NamedQuery(name = "ParametreModule.findAll", query = "SELECT p FROM ParametreModule p")
public class ParametreModule {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_parametre_module")
	@SequenceGenerator(name = "seq_parametre_module", allocationSize = 1)
	private long id;

	@Column(name = "pam_code")
	private String pamCode;

	@Column(name = "pam_libelle")
	private String pamLibelle;

	@Column(name = "pam_chaine_valeur")
	private String pamChaineValeur;

	@ManyToOne
	@JoinColumn(name = "srv_id", nullable = true)
	private Service service;

	// bi-directional many-to-one association to ModuleApplicatif
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "moduleApplicatif_id")
	private ModuleApplicatif moduleApplicatif;

	public ParametreModule(String pamCode, String pamLibelle, String pamChaineValeur, Service service, ModuleApplicatif moduleApplicatif) {
		super();
		this.pamCode = pamCode;
		this.pamLibelle = pamLibelle;
		this.pamChaineValeur = pamChaineValeur;
		this.service = service;
		this.moduleApplicatif = moduleApplicatif;
	}

}
