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
@Table(name = "sous_services")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SousService {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ssrv_id")
	private Long ssrvId;

	@Column
	private String code;

	@Column
	private String libelle;

	@OneToOne
	@JoinColumn(name = "srv_id", nullable = true)
	private Service service;

	public SousService(String code, String libelle, Service service) {
		super();
		this.code = code;
		this.libelle = libelle;
		this.service = service;
	}

	
}
