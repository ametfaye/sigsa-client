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
@Table(name = "AgentMission")
@Setter
@Getter
@NoArgsConstructor
public class AgentMission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long amId;

	@Column
	private Date dateCreation;

	@Column
	private Date dateSuppression;

	@Column
	private Integer deleted;

	@OneToOne
	@JoinColumn(name = "odm_id", nullable = false)
	private OrdreDeMission ordreDeMission;

	@OneToOne
	@JoinColumn(name = "a_id", nullable = false)
	private Agent agent;

}
