package org.cages.moulinette.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
	private int id;

	@Column(name = "role")
	private String role;

	@Column(name = "libelle")
	private String libelle;

	@Column(name = "description")
	private String description;

	public Role(String role, String libelle, String description) {
		super();
		this.role = role;
		this.libelle = libelle;
		this.description = description;
	}
}
