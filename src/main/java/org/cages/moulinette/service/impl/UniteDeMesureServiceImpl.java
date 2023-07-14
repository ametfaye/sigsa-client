package org.cages.moulinette.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cages.moulinette.dto.UniteDeMesureDTO;
import org.cages.moulinette.model.UniteDeMesure;
import org.cages.moulinette.repository.UniteDeMesureRepository;
import org.cages.moulinette.service.IUniteDeMesureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("uniteDeMesureService")
public class UniteDeMesureServiceImpl implements IUniteDeMesureService{

	@Autowired
	private UniteDeMesureRepository uniteDeMesureRepository;
	
	@Override
	public List<UniteDeMesureDTO> getAllUnitesDeMesure() {
		List<UniteDeMesureDTO> uniteDeMesureDTOs = new ArrayList<>();
		List<UniteDeMesure> uniteDeMesures = uniteDeMesureRepository.findAll();
		for (UniteDeMesure u : uniteDeMesures) {
			UniteDeMesureDTO c = new UniteDeMesureDTO(u.getId(), u.getCode(), u.getLibelle());
			uniteDeMesureDTOs.add(c);
		}
		return uniteDeMesureDTOs;
	}

	@Override
	public UniteDeMesure getUniteDeMesureById(long uniteDeMesureId) {
		return uniteDeMesureRepository.findOne(uniteDeMesureId);
	}

}
