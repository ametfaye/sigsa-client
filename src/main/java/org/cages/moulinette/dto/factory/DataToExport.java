package org.cages.moulinette.dto.factory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DataToExport {

	private int nbAgentLus;

	private int nbAgentTraites;

	private int nbAgentsExlus;

	private int nbAgentAvecIndiceReconstitue;

	private Double indiceDeReference;

}
