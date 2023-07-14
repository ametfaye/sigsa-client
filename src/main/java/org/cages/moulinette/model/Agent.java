package org.cages.moulinette.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Agent")
@Setter
@Getter
@NoArgsConstructor
public class Agent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long aId;

	@Column(length = 100)
	private String matricule;

	@Column(length = 100)
	private String indice;

	@Column(length = 100)
	private String corps;

	@Column(length = 100)
	private String echelon;

	@Column(length = 100)
	private String hierarchie;

	@Column(name = "deleted")
	private int deleted;

	@Column
	private Date dateModification;

	@Column
	private Date dateSuppression;

	@ManyToOne
	@JoinColumn(name = "pph_id", nullable = false)
	private PersonnePhysique personnePhysique;

	@ManyToOne
	@JoinColumn(name = "f_id", nullable = false)
	private Fonction fonction;

	@ManyToOne
	@JoinColumn(name = "ssrv_id", nullable = true)
	private SousService sousService;
	
	@OneToOne
	@JoinColumn(name = "gr_id", nullable = true)
	private Groupe groupe;

	// Default agent
	public Agent(String m, Fonction f, PersonnePhysique pep) {
		matricule = m;
		fonction = f;
		personnePhysique = pep;
	}

	public Agent(String matricule, String indice, PersonnePhysique personnePhysique, Fonction fonction, SousService sousService) {
		super();
		this.matricule = matricule;
		this.indice = indice;
		this.personnePhysique = personnePhysique;
		this.fonction = fonction;
		this.sousService = sousService;
	}

	public Agent(String matricule, String indice, String corps, String echelon, String hierarchie, int deleted,
			PersonnePhysique personnePhysique, Fonction fonction, SousService sousService, Groupe groupe) {
		super();
		this.matricule = matricule;
		this.indice = indice;
		this.corps = corps;
		this.echelon = echelon;
		this.hierarchie = hierarchie;
		this.deleted = deleted;
		this.personnePhysique = personnePhysique;
		this.fonction = fonction;
		this.sousService = sousService;
		this.groupe = groupe;
	}

}
