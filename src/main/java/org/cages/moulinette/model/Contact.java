package org.cages.moulinette.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="cont_contact")
public class Contact   {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="cont_id")
	private Long id;

	@Column(length=50)
	private String libelle;

	@Column(length=20)
	private String mobile;

	@Column(length=50)
	private String fixe;
	
	@Column(length=100)
	private String courriel;
	
	@Column(length=20)
	private String fax;
	
	@Column(length=100)
	private String site_web;


	
}
