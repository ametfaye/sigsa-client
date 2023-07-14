package org.cages.moulinette.model;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OrdreDeMission")
@Setter
@Getter
@NoArgsConstructor
public class OrdreDeMission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long odmId;

	private String motif;

	@Column
	private String objet;

	@Column
	private String intitule;

	@Column
	private Date dateCreation;

	@Column
	private Date dateModification;

	@Column
	private Date dateDebutMission;

	@Column
	private Date dateFinMission;

	@Column
	private Integer budget;

	@Column
	private String imputation;

	@Column
	private String motifRefusOdm;

	@Column
	private Integer deleted;
	
	@Column
	private Boolean siNational;

	@Column
	private Date dateSuppression;

	@OneToOne
	@JoinColumn(name = "eodm_id", nullable = false)
	private EtatOrdreDeMission etat;

	@OneToOne
	@JoinColumn(name = "dst_id", nullable = false)
	private Destination destination;

	@OneToOne
	@JoinColumn(name = "ssrv_id", nullable = true)
	private SousService sousService;

	@OneToOne(mappedBy = "ordreDeMission")
	private RapportOrdreDeMission rapportOrdreDeMission;
	
	@OneToOne
	@JoinColumn(name = "a_id", nullable = false)
	private Agent chefMission;
	

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "motif_refus_mission", joinColumns = @JoinColumn(name = "odm_id"), inverseJoinColumns = @JoinColumn(name = "mf_id"))
	private Set<MotifRefus> motifs;

}
