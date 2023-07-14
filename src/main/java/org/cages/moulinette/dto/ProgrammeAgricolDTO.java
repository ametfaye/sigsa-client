package org.cages.moulinette.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.cages.moulinette.model.CampagneAgricole;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProgrammeAgricolDTO implements Serializable, Cloneable {

	private static final long serialVersionUID = 4740612708609554436L;
	private Long pgca_idprog;
	private CampagneAgricole campagne;
	private String libelle;
	private String referenceProgramme; // reg PROG + REF CAMPGANE  ( nécessaire pour les fichier excel

	private String descriptifProgramme;
	private int statut;
	private String dateOuverture;
	private String dateFermeture;
	
	
	// Création Programme
	private Long idCampagne;

	private Long idCommision;
	private String commission;
	List<QuotaMiseEnplaceDTO> listQuotas;

	

}
