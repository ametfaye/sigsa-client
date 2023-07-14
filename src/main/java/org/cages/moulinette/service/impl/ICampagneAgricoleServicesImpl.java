package org.cages.moulinette.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cages.moulinette.dto.CampagneAgricoleDTO;
import org.cages.moulinette.dto.ProgrammeAgricolDTO;
import org.cages.moulinette.dto.factory.FactoryBean;
import org.cages.moulinette.model.CampagneAgricole;
import org.cages.moulinette.model.ProgrammeAgricol;
import org.cages.moulinette.repository.CampagneAgricoleRepository;
import org.cages.moulinette.repository.ProgrammeRepository;
import org.cages.moulinette.service.ICampagneAgricoleServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("campagneServices")
public class ICampagneAgricoleServicesImpl implements ICampagneAgricoleServices {

	private Logger logger = LoggerFactory.getLogger(ICampagneAgricoleServicesImpl.class);

	@Autowired
	private CampagneAgricoleRepository campagneRepo;

	@Autowired
	private ProgrammeRepository programmeRepo;
	
	@Override
	public void ouvertureCampagneAgricole(CampagneAgricoleDTO dto) {
		try {
			logger.info("Création campagne ...");
			CampagneAgricole ca = FactoryBean.mapperCampgneDTOasCampagneOBJ(dto);
			campagneRepo.save(ca);
			logger.info("Campagne créé avec success  ...");
		} catch (Exception e) {
			logger.error("Erreur survenue lors de la création  ...");
		}
	}

	CampagneAgricoleDTO tmp;

	@Override
	public List<CampagneAgricoleDTO> loadListCampagneAgricole() {

		logger.info("Lecture des refs : campagne ...");
		List<CampagneAgricole> listCa = campagneRepo.findAll();
		List<CampagneAgricoleDTO> listCaDTO = new ArrayList<CampagneAgricoleDTO>();

		listCa.forEach(ca -> {
			tmp = FactoryBean.mapperObjToDTO(ca, tmp);
			listCaDTO.add(tmp);
		});
		return listCaDTO;

	}

	@Override
	public void fermerCampagneAgricole() {
		// TODO Auto-generated method stub

	}

	@Override
	public void comparerCampagne(CampagneAgricole a, CampagneAgricole b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void evaluerCampagneAgricole(CampagneAgricole ca) {
		// TODO Auto-generated method stub
		
	}

	
	
	/*********  PROGRAMMME SERVICE ******/
	@Override
	public void ouvertureProgramme(ProgrammeAgricolDTO dto) {
		try {
			logger.info("Création Programme ...");
			ProgrammeAgricol prog = FactoryBean.mapperProgrammeDTOasProgrammeOBJ(dto);
			programmeRepo.save(prog);
			logger.info("Programme créé avec success  ...");
		} catch (Exception e) {
			logger.error("Erreur survenue lors de la création  ...");
		}
	}

	ProgrammeAgricolDTO tmpprog;
	@Override
	public List<ProgrammeAgricolDTO> loadListProgramme() {
		logger.info("Lecture des refs : Programme ...");
		List<ProgrammeAgricol> listCa = programmeRepo.findAll();
		List<ProgrammeAgricolDTO> listCaDTO = new ArrayList<ProgrammeAgricolDTO>();

		listCa.forEach(ca -> {
			tmpprog = FactoryBean.mapperObjToDTO(ca, tmpprog);
			listCaDTO.add(tmpprog);
		});
		return listCaDTO;
	}
	
	

	@Override
	public List<CampagneAgricoleDTO> loadCampagneByStatus(int status) {

		logger.info("Lecture des refs : campagne ...");
		List<CampagneAgricole> listCa = campagneRepo.loadCampagneByStatus(status);
		List<CampagneAgricoleDTO> listCaDTO = new ArrayList<CampagneAgricoleDTO>();

		listCa.forEach(ca -> {
			tmp = FactoryBean.mapperObjToDTO(ca, tmp);
			listCaDTO.add(tmp);
		});
		return listCaDTO;
	}

	
	@Override
	public void fermerProgramme() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void evaluerProgramme(ProgrammeAgricol prog) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void comparerProgramme(ProgrammeAgricolDTO a, ProgrammeAgricolDTO b) {
		// TODO Auto-generated method stub
		
	}

	
}
