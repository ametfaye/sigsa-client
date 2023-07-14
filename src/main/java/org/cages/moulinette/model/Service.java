package org.cages.moulinette.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Services")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Service {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long srvId;

	@Column
	private String code;

	@Column
	private String libelle;

	@OneToOne
	@JoinColumn(name = "cont_id", nullable = true)
	private Contact contact;

	@OneToOne
	@JoinColumn(name = "adrs_id", nullable = true)
	private Adresse destination;

	@OneToOne
	@JoinColumn(name = "ref_id", nullable = true)
	private EntitePublicRef entite;

	public Service(String code, String libelle, Contact contact, Adresse destination, EntitePublicRef entite) {
		super();
		this.code = code;
		this.libelle = libelle;
		this.contact = contact;
		this.destination = destination;
		this.entite = entite;
	}
	
}
