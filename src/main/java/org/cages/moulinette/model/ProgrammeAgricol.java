package org.cages.moulinette.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pgca_programme_agricol")
@Setter
@Getter
@NoArgsConstructor
public class ProgrammeAgricol implements Serializable, Cloneable {

	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pgca_idprog")
	private Long pgca_idprog;
	@OneToOne
	@JoinColumn(name = "pgca_idca", nullable = false)
	private CampagneAgricole campagne;
	@Column(length = 200)
	private String libelle;
	@Column(length = 200)
	private String descriptifProgramme;
	@Column
	private int statut;  // 1: ouvert  - 2 : Fermée // 3 publiée (données publics)
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOuverture;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateFermeture;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "programme_quota", joinColumns = @JoinColumn(name = "pgca_idprog"), inverseJoinColumns = @JoinColumn(name = "id_q"))
	private Set<Quota> motifs;

}
