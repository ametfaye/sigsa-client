package org.cages.moulinette.service;

import java.util.List;

import org.cages.moulinette.dto.UniteDeMesureDTO;
import org.cages.moulinette.model.UniteDeMesure;

public interface IUniteDeMesureService {

	List<UniteDeMesureDTO> getAllUnitesDeMesure();

	UniteDeMesure getUniteDeMesureById(long uniteDeMesureId);

}
