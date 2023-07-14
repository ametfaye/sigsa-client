package org.cages.moulinette.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.cages.moulinette.mailer.model.GenericModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***** QUOTA *******/

@Entity
@Table(name = "pgca_quota")
@Setter
@Getter
@NoArgsConstructor
public class Quota implements Serializable, Cloneable {

	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id_q;
	@Column
	private double quota;
	@Column
	private double miseEnplace;
	@Column
	private double reliquat;
	/**
	 * Le stock residuel est le stock déjà mis en place
	 * 
	 * ce stock peut etre déplacé en fin de ventes en cas de restant
	 * 
	 */
	@Column
	private Double stockResiduel;
	@Column
	private Date dateEdition;
	@Column
	private Date dateMiseEnplaceSouhaite;
	@Column
	private Date dateMiseEnplaceEffective;

	@OneToOne
	@JoinColumn(name = "pgca_idprog", nullable = false)
	private ProgrammeAgricol programme;  // Progr-> Commision - Progr/Campagne
}
