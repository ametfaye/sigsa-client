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
@Table(name = "ampliation")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ampliation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;

	@Column
	private String code;

	@Column
	private String libelle;
	
	@OneToOne
	@JoinColumn(name = "ssrv_id", nullable = true)
	private SousService sousService;
	

	public Ampliation(String code, String libelle) {
		super();
		this.code = code;
		this.libelle = libelle;
	}

}
