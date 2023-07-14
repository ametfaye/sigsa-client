package org.cages.moulinette.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ServiceDTO {

	private Long srvId;
	private String code;
	private String libelle;
	private String etatId;
	private String etatLibelle;
	private String destinationId;
	private String destinationLibelle;

}
