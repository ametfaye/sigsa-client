package org.cages.moulinette.model;

import java.io.Serializable;
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

/***** QUOTA *******/

@Entity
@Table(name = "pgca_commission")
@Setter
@Getter
@NoArgsConstructor
public class CommissionAgricole implements Serializable, Cloneable {

	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long idCom;
	
	@Column
	private String reference;
	

	@OneToOne
	@JoinColumn(name = "adresse_id", nullable = true)
	private Adresse adresse;

	@OneToOne
	@JoinColumn(name = "contact_id", nullable = true)
	private Contact contact;
	
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "mem_commission", joinColumns = @JoinColumn(name = "idCom"), inverseJoinColumns = @JoinColumn(name = "pers_id"))
	private Set<Personne> membres;
}
