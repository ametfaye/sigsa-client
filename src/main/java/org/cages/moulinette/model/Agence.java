package org.cages.moulinette.model;

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
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Agence")
@Setter
@Getter
@NoArgsConstructor
public class Agence {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long agcId;

	@Column
	private String code;

	@Column
	private String libelle;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "agence_compagnies", joinColumns = @JoinColumn(name = "agc_id"), inverseJoinColumns = @JoinColumn(name = "com_id"))
	private Set<Compagnie> listCompagnieAeriennes;

}
