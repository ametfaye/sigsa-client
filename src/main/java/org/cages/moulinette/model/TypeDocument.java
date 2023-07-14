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
@Table(name = "type_document")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TypeDocument {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "td_id")
	private long tdId;

	@Column(name = "code_doc")
	private String codeDoc;

	@Column(name = "libelle_doc")
	private String libelleDoc;
}
