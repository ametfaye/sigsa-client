package org.cages.moulinette.dto;

import org.primefaces.model.UploadedFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategorieIntrantDTO {

	private Long id;

	private Long idType;
	private String libelleType;

	private String code;
	private String libelle;
	private String pictoBase64;
	private UploadedFile pictoFile;

	public CategorieIntrantDTO(Long id, String code, String libelle, String pictoBase64) {
		super();
		this.id = id;
		this.code = code;
		this.libelle = libelle;
		this.pictoBase64 = pictoBase64;
	}

}
