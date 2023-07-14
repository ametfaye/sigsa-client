package org.cages.moulinette.service;

import java.util.List;

import org.cages.moulinette.dto.PersonnePhysiqueDTO;
import org.cages.moulinette.model.PersonnePhysique;

public interface IPersonnePhysiqueService {

	PersonnePhysique createPersonnePhysique(PersonnePhysique personnePhysique);

	List<PersonnePhysiqueDTO> getAllPersonnePhysiques();

	PersonnePhysique getPersonnePhysiqueById(long id);

	PersonnePhysiqueDTO getPersonnePhysiqueDtoById(Long pphIdAdd);

	PersonnePhysique findByEmail(String email);

	void delete(Long id);
}
