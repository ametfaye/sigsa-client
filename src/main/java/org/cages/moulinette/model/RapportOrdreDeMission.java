package org.cages.moulinette.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RapportOrdreDeMission")
@Setter
@Getter
@NoArgsConstructor
public class RapportOrdreDeMission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long rodmId;
	
	@Column
	private Date dateCreation;

	@Column
	private String texteLibre;

	@OneToOne
	@JoinColumn(name = "odm_id", nullable = false)
	private OrdreDeMission ordreDeMission;
	
	@OneToMany(mappedBy = "rapportOrdreDeMission", fetch = FetchType.LAZY)
	private List<Document> documents;

	public RapportOrdreDeMission(String texteLibre, OrdreDeMission ordreDeMission, Date dateCreation) {
		super();
		this.texteLibre = texteLibre;
		this.ordreDeMission = ordreDeMission;
		this.dateCreation = dateCreation;
	}

}
