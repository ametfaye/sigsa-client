package org.cages.moulinette.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DocumentSearchDTO {

	private long odmId;
	private String typeDocument;
	private String statutDocument;
	private Date dateCreation;
	private String dateCreationFormatter;
	private String path;

	public DocumentSearchDTO(long odmId, String typeDocument, String statutDocument, Date dateCreation, String path) {
		super();
		this.odmId = odmId;
		this.typeDocument = typeDocument;
		this.statutDocument = statutDocument;
		this.dateCreation = dateCreation;
		this.path = path;
	}

	public DocumentSearchDTO(long odmId, String typeDocument, String statutDocument, Date dateCreation, String dateCreationFormatter, String path) {
		super();
		this.odmId = odmId;
		this.typeDocument = typeDocument;
		this.statutDocument = statutDocument;
		this.dateCreation = dateCreation;
		this.dateCreationFormatter = dateCreationFormatter;
		this.path = path;
	}

}
