package org.cages.moulinette.repository;

import org.cages.moulinette.model.ProgrammeAgricol;
import org.cages.moulinette.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("programRepo")
public interface ProgrammeRepository extends JpaRepository<ProgrammeAgricol, Integer> {

}
