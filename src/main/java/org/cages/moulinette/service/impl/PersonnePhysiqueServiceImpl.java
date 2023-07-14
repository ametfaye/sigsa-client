package org.cages.moulinette.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cages.moulinette.dto.PersonnePhysiqueDTO;
import org.cages.moulinette.model.PersonnePhysique;
import org.cages.moulinette.repository.PersonnePhysiqueRepository;
import org.cages.moulinette.service.IPersonnePhysiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("personnePhysiqueService")
public class PersonnePhysiqueServiceImpl implements IPersonnePhysiqueService {

	@Autowired
	private PersonnePhysiqueRepository personnePhysiqueRepository;

	@Override
	public PersonnePhysique createPersonnePhysique(PersonnePhysique personnePhysique) {
		return personnePhysiqueRepository.save(personnePhysique);
	}

	@Override
	public List<PersonnePhysiqueDTO> getAllPersonnePhysiques() {
		List<PersonnePhysique> personnePhysiques =  personnePhysiqueRepository.findAll();
		List<PersonnePhysiqueDTO> personnePhysiqueDTOs = new ArrayList<>();
		if (personnePhysiques != null && !personnePhysiques.isEmpty()) {
			for (PersonnePhysique personnePhysique : personnePhysiques) {
				personnePhysiqueDTOs.add(new PersonnePhysiqueDTO(personnePhysique.getPphId(), personnePhysique.getPrenom(), personnePhysique.getNom()));
			}
		}
		return personnePhysiqueDTOs;
	}

	@Override
	public PersonnePhysique getPersonnePhysiqueById(long id) {
		return personnePhysiqueRepository.findOne(id);
	}

	@Override
	public PersonnePhysiqueDTO getPersonnePhysiqueDtoById(Long id) {
		PersonnePhysique personnePhysique = personnePhysiqueRepository.findOne(id);
		return new PersonnePhysiqueDTO(id, personnePhysique.getPrenom(), personnePhysique.getNom(), personnePhysique.getEmail());
	}

	@Override
	public PersonnePhysique findByEmail(String email) {
		return personnePhysiqueRepository.findByEmail(email);
	}

	@Override
	public void delete(Long id) {
		personnePhysiqueRepository.delete(id);
	}
}
