package org.cages.moulinette.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "pgca_CampagneAgricole")
@Setter
@Getter
@NoArgsConstructor
public class CampagneAgricole implements Serializable {

	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pgca_idca")
	private Long id_ca;
	@Column(length = 500)
	private String presentation;
	@Column
	private int statut;
	@Column
	private Date dateOuverture;
	@Column
	private Date dateFermeture;

}
