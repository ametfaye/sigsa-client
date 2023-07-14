package org.cages.moulinette.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AgentMissionDTO {

	private Long amId;
	private String dateCreation;
	private String dateSuppression;
	private Integer deleted;
	private Long odmId;
	private Long aId;
}
