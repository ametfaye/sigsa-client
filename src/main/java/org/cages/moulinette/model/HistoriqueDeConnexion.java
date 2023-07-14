package org.cages.moulinette.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HistoriqueDeConnexion")
@Setter
@Getter
@NoArgsConstructor
public class HistoriqueDeConnexion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long hCnxId;

	@Column
	private Date dateConnexion;
	
	@Column
	private String matricule;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserInfos user;

	public HistoriqueDeConnexion(Date dateConnexion, String matricule, UserInfos user) {
		super();
		this.dateConnexion = dateConnexion;
		this.matricule = matricule;
		this.user = user;
	}

}
