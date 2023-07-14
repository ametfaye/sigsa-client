package org.cages.moulinette.service;

import java.util.List;

import org.cages.moulinette.dto.CampagneAgricoleDTO;
import org.cages.moulinette.dto.ProgrammeAgricolDTO;
import org.cages.moulinette.model.CampagneAgricole;
import org.cages.moulinette.model.ProgrammeAgricol;

public interface ICampagneAgricoleServices {

	//CAMPAGNE SERVICE
	public void ouvertureCampagneAgricole(CampagneAgricoleDTO dto);
	public void fermerCampagneAgricole();
	public void evaluerCampagneAgricole(CampagneAgricole ca);	
	public void comparerCampagne(CampagneAgricole a , CampagneAgricole b);
	public List<CampagneAgricoleDTO> loadListCampagneAgricole();
	public List<CampagneAgricoleDTO> loadCampagneByStatus(int status);

	
	//PROGRAMME SERVICE
	public void ouvertureProgramme(ProgrammeAgricolDTO dto);
	public void fermerProgramme();
	public void evaluerProgramme(ProgrammeAgricol prog);	
	public void comparerProgramme(ProgrammeAgricolDTO a , ProgrammeAgricolDTO b);
	public List<ProgrammeAgricolDTO> loadListProgramme();

}
