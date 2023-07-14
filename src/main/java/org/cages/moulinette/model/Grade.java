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
@Table(name = "Grade")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long gId;

	@Column(length = 100)
	private String code;

	@Column(length = 100)
	private String libelle;

}
