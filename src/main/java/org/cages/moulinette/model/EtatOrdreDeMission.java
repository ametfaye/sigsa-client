package org.cages.moulinette.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EtatOrdreDeMission")
@Setter
@Getter
@NoArgsConstructor
public class EtatOrdreDeMission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long eodmId;

	@Column
	private String codeEtat;

	@Column
	private String libelleEtat;

}
