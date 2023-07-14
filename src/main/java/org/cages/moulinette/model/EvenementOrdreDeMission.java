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
@Table(name = "EvenementOrdreDeMission")
@Setter
@Getter
@NoArgsConstructor
public class EvenementOrdreDeMission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long eventId;

	@Column
	private String detailsEvenement;

	@Column
	private Date dateEvenement;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserInfos auteur;

	@OneToOne
	@JoinColumn(name = "adrs_id")
	private Adresse adresse;

	@OneToOne
	@JoinColumn(name = "odm_id")
	private OrdreDeMission ordreDeMission;

	@OneToOne
	@JoinColumn(name = "tev_id")
	private TypeEvenement typeEvenement;

}
