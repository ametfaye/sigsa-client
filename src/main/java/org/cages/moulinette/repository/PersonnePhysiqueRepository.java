package org.cages.moulinette.repository;

import org.cages.moulinette.model.PersonnePhysique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("personnePhysiqueRepository")
public interface PersonnePhysiqueRepository extends JpaRepository<PersonnePhysique, Long> {

	PersonnePhysique findByEmail(String email);
}
