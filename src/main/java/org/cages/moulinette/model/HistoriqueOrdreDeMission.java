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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HistoriqueOrdreDeMission")
@Setter
@Getter
@NoArgsConstructor
public class HistoriqueOrdreDeMission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long hisId;

	@OneToOne
	@JoinColumn(name = "odm_id", nullable = false)
	private OrdreDeMission ordreDeMission;

	@Column
	private Date dateCreation;

	@OneToOne
	@JoinColumn(name = "eodm_id", nullable = false)
	private EtatOrdreDeMission etat;

}
