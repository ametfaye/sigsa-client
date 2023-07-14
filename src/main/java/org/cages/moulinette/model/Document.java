package org.cages.moulinette.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "document")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Document implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "doc_id")
	private long docId;

	@Column
	private Date dateCreation;

	@Column
	private Date dateModification;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doc_type", nullable = false)
	private TypeDocument typeDoc;

	@Column(name = "doc_path")
	private String docPath;

	@OneToOne
	@JoinColumn(name = "stdoc_id", nullable = false)
	private StatutDocument statutDocument;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rodm_id", nullable = true)
	private RapportOrdreDeMission rapportOrdreDeMission;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "odm_id", nullable = false)
	private OrdreDeMission ordreDeMission;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agent_id", nullable = true)
	private Agent agent;

	public Document(TypeDocument typeDoc, Date dateCreation, String docPath, StatutDocument statutDocument,
			OrdreDeMission ordreDeMission) {
		super();
		this.typeDoc = typeDoc;
		this.dateCreation = dateCreation;
		this.docPath = docPath;
		this.statutDocument = statutDocument;
		this.ordreDeMission = ordreDeMission;
	}

}